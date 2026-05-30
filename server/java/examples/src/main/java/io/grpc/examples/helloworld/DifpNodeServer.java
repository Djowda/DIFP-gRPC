/*
 * DIFP — Djowda Interconnected Food Protocol  ·  v0.4
 * gRPC Node Server  ·  Skull Implementation
 *
 * All DB interactions are placeholder functions that return
 * realistic dummy data.  Replace with your actual storage
 * layer (PostgreSQL + PostGIS, Firebase, etc.) incrementally.
 *
 * Services implemented:
 *   DifpNodeService     — main coordination service (§5-7, §10)
 *   DifpRegistryService — lobby registry (§25-27)
 *
 * Spec: https://djowda.com/difp/
 * License: CC-BY 4.0
 */

package io.grpc.examples.helloworld;

import com.djowda.difp.*;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DifpNodeServer {

    private static final Logger logger = Logger.getLogger(DifpNodeServer.class.getName());

    // ── Constants ─────────────────────────────────────────────────────────────
    private static final int    NODE_PORT    = 50051;
    private static final int    THREAD_POOL  = 8;
    private static final String NODE_ID      = "node-oran-01";
    private static final String NODE_VERSION = "0.4";
    private static final String PROTOCOL     = "DIFP";

    // MinMax99 grid constants (§3.1)
    private static final long   EARTH_WIDTH_METERS  = 40_075_000L;
    private static final long   EARTH_HEIGHT_METERS = 20_000_000L;
    private static final int    CELL_SIZE_METERS    = 500;
    private static final long   NUM_COLUMNS         = 82_000L;
    private static final long   NUM_ROWS            = 42_000L;

    // Lobby constants (§24.1)
    private static final int  LOBBY_SIZE         = 41;
    private static final long NUM_LOBBY_COLUMNS  = 2_000L;
    private static final long NUM_LOBBY_ROWS     = 1_025L;

    private Server server;
    private final ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL);

    // ── Shared in-memory state (placeholder DB) ───────────────────────────────
    // In production: replace with your storage backend.
    private final Map<String, PresenceRecord>   presenceDb  = new ConcurrentHashMap<>();
    private final Map<String, TradeMessage>     tradeDb     = new ConcurrentHashMap<>();
    // lobbyId(long) → Set<nodeEndpoint>
    private final Map<Long, Set<String>>        registryDb  = new ConcurrentHashMap<>();
    private final AtomicLong                    nonceGen    = new AtomicLong(1000);

    // Seed with dummy presence data for testing
    private void seedDummyPresence() {
        // Algiers cell: 1,711,767,603
        presenceDb.put("difp://1711767603/f/ali-farm-01",
            PresenceRecord.newBuilder()
                .setDid("difp://1711767603/f/ali-farm-01")
                .setComponentName("Ali's Farm")
                .setPhoneNumber("+213555000001")
                .setCellId(1_711_767_603L)
                .setComponentType("f")
                .setStatus(PresenceStatus.OPEN)
                .setWorkingTime("07:00-18:00")
                .setLastUpdate(Instant.now().toEpochMilli())
                .setUserId("uid-ali-001")
                .build());

        presenceDb.put("difp://1711767603/s/safeway-dz-042",
            PresenceRecord.newBuilder()
                .setDid("difp://1711767603/s/safeway-dz-042")
                .setComponentName("Safeway Algiers Centre")
                .setPhoneNumber("+213555000042")
                .setCellId(1_711_767_603L)
                .setComponentType("s")
                .setStatus(PresenceStatus.OPEN)
                .setWorkingTime("08:00-22:00")
                .setLastUpdate(Instant.now().toEpochMilli())
                .setUserId("uid-safeway-042")
                .build());

        presenceDb.put("difp://1711767603/r/restaurant-kabyle-01",
            PresenceRecord.newBuilder()
                .setDid("difp://1711767603/r/restaurant-kabyle-01")
                .setComponentName("Restaurant Kabyle")
                .setPhoneNumber("+213555000099")
                .setCellId(1_711_767_603L)
                .setComponentType("r")
                .setStatus(PresenceStatus.BUSY)
                .setWorkingTime("12:00-23:00")
                .setLastUpdate(Instant.now().toEpochMilli())
                .setUserId("uid-kabyle-01")
                .build());

        logger.info("[DB] Seeded " + presenceDb.size() + " dummy presence records");
    }

    // =========================================================================
    //  §3  SPATIAL ALGORITHMS  ·  MinMax99 Grid  (must be bit-for-bit correct)
    // =========================================================================

    /** §3.2 — canonical geoToCellNumber implementation */
    public static long geoToCellNumber(double latitude, double longitude) {
        double x = (longitude + 180.0) * ((double) EARTH_WIDTH_METERS / 360.0);
        double y = (EARTH_HEIGHT_METERS / 2.0)
                 - Math.log(Math.tan(Math.PI / 4.0 + Math.toRadians(latitude) / 2.0))
                   * (EARTH_HEIGHT_METERS / (2.0 * Math.PI));

        long xCell = (long) (x / CELL_SIZE_METERS);
        long yCell = (long) (y / CELL_SIZE_METERS);

        xCell = Math.max(0, Math.min(xCell, NUM_COLUMNS - 1));
        yCell = Math.max(0, Math.min(yCell, NUM_ROWS    - 1));

        return xCell * NUM_ROWS + yCell;
    }

    /** §3.3 — reverse: cellId → (xCell, yCell) */
    public static long[] cellNumberToXY(long cellId) {
        long xCell = cellId / NUM_ROWS;
        long yCell = cellId % NUM_ROWS;
        return new long[]{ xCell, yCell };
    }

    /** §3.4 — neighbor cell IDs within radius */
    public static List<Long> getNearbyCells(long centerCellId, int radius) {
        long[] xy = cellNumberToXY(centerCellId);
        long xC = xy[0], yC = xy[1];
        List<Long> result = new ArrayList<>();
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                long x = Math.max(0, Math.min(xC + dx, NUM_COLUMNS - 1));
                long y = Math.max(0, Math.min(yC + dy, NUM_ROWS    - 1));
                result.add(x * NUM_ROWS + y);
            }
        }
        return result;
    }

    /** §24.3 — cellId → lobbyId (Layer 1) */
    public static long cellIdToLobbyId(long cellId) {
        long[] xy = cellNumberToXY(cellId);
        long lobbyX = xy[0] / LOBBY_SIZE;
        long lobbyY = xy[1] / LOBBY_SIZE;
        return lobbyX * NUM_LOBBY_ROWS + lobbyY;
    }

    /** §24.3 — local position within lobby */
    public static int[] cellIdToLocalXY(long cellId) {
        long[] xy = cellNumberToXY(cellId);
        return new int[]{ (int)(xy[0] % LOBBY_SIZE), (int)(xy[1] % LOBBY_SIZE) };
    }

    // =========================================================================
    //  PLACEHOLDER DB HELPERS
    //  These functions simulate your storage layer.  Each is clearly marked
    //  with what the real implementation would do.
    // =========================================================================

    /** PLACEHOLDER: look up presence records for a cell (and optional radius).
     *  Real impl: query DB indexed by cell_id. */
    private List<PresenceRecord> db_queryPresenceByCell(long cellId, int radius, String typeFilter) {
        Set<Long> queryCells = new HashSet<>(getNearbyCells(cellId, radius));
        return presenceDb.values().stream()
            .filter(r -> queryCells.contains(r.getCellId()))
            .filter(r -> typeFilter == null || typeFilter.isEmpty() || r.getComponentType().equals(typeFilter))
            .collect(Collectors.toList());
    }

    /** PLACEHOLDER: store or update a presence record.
     *  Real impl: upsert into presence table. */
    private void db_upsertPresence(PresenceRecord record) {
        presenceDb.put(record.getDid(), record);
        logger.info("[DB] Upsert presence: " + record.getDid());
    }

    /** PLACEHOLDER: remove presence record (component left/offline).
     *  Real impl: delete or mark offline in DB. */
    private void db_removePresence(String did) {
        presenceDb.remove(did);
        logger.info("[DB] Remove presence: " + did);
    }

    /** PLACEHOLDER: create a new trade record.
     *  Real impl: atomic fan-out write to TD/, T/sType/sId/o/, T/rType/rId/i/, TA/ (§9.1). */
    private String db_createTrade(TradeMessage trade) {
        String tradeId = "trade-" + System.currentTimeMillis() + "-" + (int)(Math.random()*9999);
        TradeMessage stored = trade.toBuilder().setTradeId(tradeId).build();
        tradeDb.put(tradeId, stored);
        logger.info("[DB] Created trade: " + tradeId + " type=" + trade.getTradeType());
        return tradeId;
    }

    /** PLACEHOLDER: update trade status.
     *  Real impl: atomic multi-path write to TD/, inbox summary, outbox summary, TA/ (§9.2). */
    private boolean db_updateTradeStatus(String tradeId, TradeStatus newStatus, String reason) {
        TradeMessage trade = tradeDb.get(tradeId);
        if (trade == null) return false;
        tradeDb.put(tradeId, trade.toBuilder()
            .setStatus(newStatus)
            .setDenialCause(reason != null ? reason : "")
            .setLastUpdated(Instant.now().toEpochMilli())
            .build());
        logger.info("[DB] Trade " + tradeId + " → " + newStatus);
        return true;
    }

    /** PLACEHOLDER: register a new component (§4.2).
     *  Real impl: compute cell ID, assign unique componentId, store DID, issue signed token. */
    private RegisterResponse db_registerComponent(IdentityRegisterPayload req) {
        long cellId = geoToCellNumber(
            req.getLocation().getLatitude(),
            req.getLocation().getLongitude()
        );
        String componentId = req.getComponentType() + "-" + UUID.randomUUID().toString().substring(0, 8);
        String did = "difp://" + cellId + "/" + req.getComponentType() + "/" + componentId;
        String token = "jwt-placeholder-" + UUID.randomUUID();    // Real impl: Ed25519-signed JWT

        PresenceRecord rec = PresenceRecord.newBuilder()
            .setDid(did)
            .setComponentName(req.getComponentName())
            .setPhoneNumber(req.getPhoneNumber())
            .setCellId(cellId)
            .setComponentType(req.getComponentType())
            .setStatus(PresenceStatus.OPEN)
            .setLastUpdate(Instant.now().toEpochMilli())
            .setUserId(componentId)
            .build();
        db_upsertPresence(rec);

        logger.info("[DB] Registered: " + did);
        return RegisterResponse.newBuilder()
            .setSuccess(true)
            .setDid(did)
            .setToken(token)
            .build();
    }

    /** PLACEHOLDER: registry — store lobby→node mapping (§25.1). */
    private void db_upsertRegistryEntry(String nodeEndpoint, List<Long> lobbyIds) {
        for (Long lobbyId : lobbyIds) {
            registryDb.computeIfAbsent(lobbyId, k -> ConcurrentHashMap.newKeySet())
                      .add(nodeEndpoint);
        }
        logger.info("[Registry] Node " + nodeEndpoint + " announced " + lobbyIds.size() + " lobbies");
    }

    /** PLACEHOLDER: registry — query nodes for a lobby (§25.3). */
    private List<String> db_queryRegistryByLobby(long lobbyId) {
        Set<String> nodes = registryDb.get(lobbyId);
        if (nodes == null) {
            // Placeholder: return this node as a fallback
            return Collections.singletonList("grpc://localhost:" + NODE_PORT);
        }
        return new ArrayList<>(nodes);
    }

    // =========================================================================
    //  ENVELOPE HELPERS
    // =========================================================================

    private DifpEnvelope makeResponseEnvelope(String type, DifpEnvelope request,
                                               String targetDid) {
        return DifpEnvelope.newBuilder()
            .setId("msg-" + Instant.now().toEpochMilli() + "-" + nonceGen.incrementAndGet())
            .setType(type)
            .setVersion(NODE_VERSION)
            .setFrom(MessageSender.newBuilder()
                .setDid("difp://0/a/" + NODE_ID)
                .setNode(NODE_ID)
                .setRole(SenderRole.NODE)
                .build())
            .setTarget(MessageTarget.newBuilder()
                .setType(TargetType.DIRECT)
                .setValue(targetDid)
                .build())
            .setMode(MessageMode.RESPONSE)
            .setCell("0")
            .setTimestamp(Instant.now().toString())
            .setTtl(60)
            .setNonce(nonceGen.incrementAndGet())
            .setContext(MessageContext.newBuilder()
                .setParentId(request.getId())
                .setTraceId(request.getContext().getTraceId())
                .build())
            .build();
    }

    // =========================================================================
    //  SERVICE 1 — DifpNodeServiceImpl
    // =========================================================================

    class DifpNodeServiceImpl extends DifpNodeServiceGrpc.DifpNodeServiceImplBase {

        /**
         * §15 / §18 — Main bidirectional channel.
         * Every incoming DifpEnvelope is processed through the 6-step pipeline:
         *   1. Structural validation   (basic proto completeness)
         *   2. Semantic validation     (ttl, timestamp, nonce, known type)
         *   3. Cryptographic check     (SKULL: skipped — placeholder)
         *   4. Payload validation      (type-specific)
         *   5. Processing              (handler dispatch)
         *   6. Propagation             (placeholder: log only)
         */
        @Override
        public StreamObserver<DifpEnvelope> connect(StreamObserver<DifpEnvelope> responseObserver) {

            logger.info("[Connect] New client stream opened");

            return new StreamObserver<DifpEnvelope>() {

                @Override
                public void onNext(DifpEnvelope envelope) {
                    logger.info("[Connect] ← " + envelope.getType()
                        + " | from=" + envelope.getFrom().getDid()
                        + " | mode=" + envelope.getMode());

                    // ── Step 1: Structural validation ──────────────────────
                    if (envelope.getId().isEmpty() || envelope.getType().isEmpty()) {
                        logger.warning("[Connect] Dropped: missing id or type");
                        return;
                    }

                    // ── Step 2: Semantic validation ─────────────────────────
                    if (envelope.getTtl() <= 0) {
                        logger.warning("[Connect] Dropped: ttl=" + envelope.getTtl());
                        return;
                    }

                    // ── Step 3: Crypto (SKULL — skipped) ───────────────────
                    // TODO: verify Ed25519 signature when keys are provisioned

                    // ── Step 4 + 5: Payload validation & Processing ─────────
                    DifpEnvelope response = dispatchEnvelope(envelope);

                    // ── Step 6: Propagation (SKULL — log only) ──────────────
                    // TODO: forward cell-targeted events to neighbor nodes
                    //       broadcast to federated nodes on BROADCAST target

                    if (response != null) {
                        logger.info("[Connect] → " + response.getType());
                        responseObserver.onNext(response);
                    }
                }

                @Override
                public void onError(Throwable t) {
                    logger.log(Level.WARNING, "[Connect] Stream error", t);
                }

                @Override
                public void onCompleted() {
                    logger.info("[Connect] Client stream closed");
                    responseObserver.onCompleted();
                }
            };
        }

        /** Route envelope to the correct type-specific handler. */
        private DifpEnvelope dispatchEnvelope(DifpEnvelope e) {
            switch (e.getType()) {
                // ── Presence ──────────────────────────────────────────────
                case "presence.announce":
                    return handlePresenceAnnounce(e);
                case "presence.update":
                    return handlePresenceUpdate(e);
                case "presence.leave":
                    handlePresenceLeave(e);
                    return null;

                // ── Trade ─────────────────────────────────────────────────
                case "trade.ask":
                    return handleTradeAsk(e);
                case "trade.offer":
                    return handleTradeOffer(e);
                case "trade.donate":
                    return handleTradeDonate(e);
                case "trade.accept":
                case "trade.reject":
                case "trade.complete":
                case "trade.cancel":
                    return handleTradeAction(e);

                // ── Query ─────────────────────────────────────────────────
                case "query.cell":
                    return handleQueryCell(e);
                case "query.resource":
                    return handleQueryResource(e);

                // ── Registry ──────────────────────────────────────────────
                case "registry.announce":
                    handleRegistryAnnounce(e);
                    return null;
                case "registry.query":
                    return handleRegistryQuery(e);

                // ── Node ──────────────────────────────────────────────────
                case "node.ping":
                    return handleNodePing(e);
                case "node.sync":
                    return handleNodeSync(e);

                // ── Identity ──────────────────────────────────────────────
                case "identity.register":
                    return handleIdentityRegister(e);

                default:
                    logger.warning("[Dispatch] Unknown type: " + e.getType() + " — forwarding as-is");
                    return null;
            }
        }

        // ── Presence handlers ─────────────────────────────────────────────────

        private DifpEnvelope handlePresenceAnnounce(DifpEnvelope e) {
            PresenceAnnouncePayload p = e.getPresenceAnnounce();
            db_upsertPresence(p.getRecord());
            // No response required for EVENT mode
            return null;
        }

        private DifpEnvelope handlePresenceUpdate(DifpEnvelope e) {
            PresenceUpdatePayload p = e.getPresenceUpdate();
            PresenceRecord existing = presenceDb.get(p.getDid());
            if (existing != null) {
                db_upsertPresence(existing.toBuilder()
                    .setStatus(p.getStatus())
                    .setLastUpdate(p.getLastUpdate())
                    .build());
            }
            return null;
        }

        private void handlePresenceLeave(DifpEnvelope e) {
            db_removePresence(e.getPresenceLeave().getDid());
        }

        // ── Trade handlers ────────────────────────────────────────────────────

        private DifpEnvelope handleTradeAsk(DifpEnvelope e) {
            String tradeId = db_createTrade(e.getTradeAsk().getTrade());
            // EVENT mode — broadcast to cell, no direct response to sender
            // SKULL: just log; real impl would push to receiver's inbox stream
            return null;
        }

        private DifpEnvelope handleTradeOffer(DifpEnvelope e) {
            db_createTrade(e.getTradeOffer().getTrade());
            return null;
        }

        private DifpEnvelope handleTradeDonate(DifpEnvelope e) {
            String tradeId = db_createTrade(e.getTradeDonate().getTrade());
            logger.info("[Trade] Donation created: " + tradeId);
            return null;
        }

        private DifpEnvelope handleTradeAction(DifpEnvelope e) {
            TradeActionPayload p = e.getTradeAction();
            TradeStatus newStatus;
            switch (e.getType()) {
                case "trade.accept":   newStatus = TradeStatus.ACCEPTED;   break;
                case "trade.reject":   newStatus = TradeStatus.DENIED;     break;
                case "trade.complete": newStatus = TradeStatus.COMPLETED;  break;
                case "trade.cancel":   newStatus = TradeStatus.CANCELLED;  break;
                default:               return null;
            }
            boolean ok = db_updateTradeStatus(p.getTradeId(), newStatus, p.getReason());
            if (!ok) logger.warning("[Trade] Unknown tradeId: " + p.getTradeId());
            return null;
        }

        // ── Query handlers ────────────────────────────────────────────────────

        private DifpEnvelope handleQueryCell(DifpEnvelope e) {
            QueryCellPayload q = e.getQueryCell();
            List<PresenceRecord> results = db_queryPresenceByCell(
                q.getCellId(), q.getRadius(), q.getComponentType());

            QueryResponsePayload response = QueryResponsePayload.newBuilder()
                .addAllParticipants(results)
                .setCellId(q.getCellId())
                .setTotalCount(results.size())
                .build();

            return makeResponseEnvelope("query.response", e, e.getFrom().getDid())
                .toBuilder()
                .setQueryResponse(response)
                .build();
        }

        private DifpEnvelope handleQueryResource(DifpEnvelope e) {
            QueryResourcePayload q = e.getQueryResource();
            // PLACEHOLDER: no live catalog index yet — return all open participants in radius
            List<PresenceRecord> results = db_queryPresenceByCell(
                q.getCellId(), q.getRadius(), null);

            QueryResponsePayload response = QueryResponsePayload.newBuilder()
                .addAllParticipants(results)
                .setCellId(q.getCellId())
                .setTotalCount(results.size())
                .build();

            return makeResponseEnvelope("query.response", e, e.getFrom().getDid())
                .toBuilder()
                .setQueryResponse(response)
                .build();
        }

        // ── Registry handlers (via stream) ────────────────────────────────────

        private void handleRegistryAnnounce(DifpEnvelope e) {
            RegistryAnnouncePayload p = e.getRegistryAnnounce();
            db_upsertRegistryEntry(p.getNodeEndpoint(), p.getLobbiesList());
        }

        private DifpEnvelope handleRegistryQuery(DifpEnvelope e) {
            RegistryQueryPayload q = e.getRegistryQuery();
            List<String> nodes = db_queryRegistryByLobby(q.getLobbyId());

            RegistryResponsePayload response = RegistryResponsePayload.newBuilder()
                .setLobbyId(q.getLobbyId())
                .addAllNodes(nodes)
                .build();

            return makeResponseEnvelope("registry.response", e, e.getFrom().getDid())
                .toBuilder()
                .setRegistryResponse(response)
                .build();
        }

        // ── Node management handlers ──────────────────────────────────────────

        private DifpEnvelope handleNodePing(DifpEnvelope e) {
            NodePingPayload ping = e.getNodePing();

            NodePongPayload pong = NodePongPayload.newBuilder()
                .setNodeId(NODE_ID)
                .setSentAt(ping.getSentAt())
                .setReceivedAt(Instant.now().toEpochMilli())
                .build();

            return makeResponseEnvelope("node.pong", e, e.getFrom().getDid())
                .toBuilder()
                .setNodePong(pong)
                .build();
        }

        private DifpEnvelope handleNodeSync(DifpEnvelope e) {
            NodeSyncPayload sync = e.getNodeSync();
            // PLACEHOLDER: return presence records for the requested cell updated since timestamp
            List<PresenceRecord> delta = db_queryPresenceByCell(sync.getCellId(), 0, null)
                .stream()
                .filter(r -> r.getLastUpdate() >= sync.getSinceTimestamp())
                .collect(Collectors.toList());

            // Pack delta into a query.response envelope (re-using the type for symmetry)
            QueryResponsePayload response = QueryResponsePayload.newBuilder()
                .addAllParticipants(delta)
                .setCellId(sync.getCellId())
                .setTotalCount(delta.size())
                .build();

            return makeResponseEnvelope("query.response", e, e.getFrom().getDid())
                .toBuilder()
                .setQueryResponse(response)
                .build();
        }

        private DifpEnvelope handleIdentityRegister(DifpEnvelope e) {
            RegisterResponse reg = db_registerComponent(e.getIdentityRegister());
            // Return as a direct message — type "identity.registered" (custom extension)
            return makeResponseEnvelope("identity.registered", e, e.getFrom().getDid())
                .toBuilder()
                // We embed the DID in the presence.announce so the client can update its store
                .setPresenceAnnounce(PresenceAnnouncePayload.newBuilder()
                    .setRecord(presenceDb.get(reg.getDid()))
                    .build())
                .build();
        }

        // ── Unary RPCs ────────────────────────────────────────────────────────

        /** §4.2 — Register component, compute cell ID, return DID + token. */
        @Override
        public void registerComponent(IdentityRegisterPayload req,
                                      StreamObserver<RegisterResponse> obs) {
            try {
                RegisterResponse response = db_registerComponent(req);
                obs.onNext(response);
                obs.onCompleted();
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "[registerComponent] Error", ex);
                obs.onError(Status.INTERNAL.withDescription(ex.getMessage()).asException());
            }
        }

        /** §3.2 — Stateless cell + lobby computation. */
        @Override
        public void computeCell(GeoCellRequest req, StreamObserver<GeoCellResponse> obs) {
            try {
                double lat = req.getLocation().getLatitude();
                double lng = req.getLocation().getLongitude();
                long cellId  = geoToCellNumber(lat, lng);
                long lobbyId = cellIdToLobbyId(cellId);
                int[] local  = cellIdToLocalXY(cellId);

                obs.onNext(GeoCellResponse.newBuilder()
                    .setCellId(cellId)
                    .setLobbyId(lobbyId)
                    .setLocalX(local[0])
                    .setLocalY(local[1])
                    .build());
                obs.onCompleted();
            } catch (Exception ex) {
                obs.onError(Status.INTERNAL.withDescription(ex.getMessage()).asException());
            }
        }

        /** §5 — Direct unary presence query. */
        @Override
        public void queryCell(QueryCellPayload req, StreamObserver<QueryResponsePayload> obs) {
            try {
                List<PresenceRecord> results = db_queryPresenceByCell(
                    req.getCellId(), req.getRadius(), req.getComponentType());

                obs.onNext(QueryResponsePayload.newBuilder()
                    .addAllParticipants(results)
                    .setCellId(req.getCellId())
                    .setTotalCount(results.size())
                    .build());
                obs.onCompleted();
            } catch (Exception ex) {
                obs.onError(Status.INTERNAL.withDescription(ex.getMessage()).asException());
            }
        }

        /** §10.1 — Node well-known info. */
        @Override
        public void getNodeInfo(NodeInfoRequest req, StreamObserver<NodeInfoResponse> obs) {
            // Compute this node's owned lobbies from the presence DB
            Set<Long> ownedLobbies = presenceDb.values().stream()
                .map(r -> cellIdToLobbyId(r.getCellId()))
                .collect(Collectors.toSet());

            obs.onNext(NodeInfoResponse.newBuilder()
                .setProtocol(PROTOCOL)
                .setVersion(NODE_VERSION)
                .setNodeId(NODE_ID)
                .addAllCoverageLobbies(ownedLobbies)
                .setContact("info@djowda.com")
                .addFederates("grpc://node-algiers-02.difp:50051")   // placeholder peer
                .build());
            obs.onCompleted();
        }
    }

    // =========================================================================
    //  SERVICE 2 — DifpRegistryServiceImpl  (§25-27)
    // =========================================================================

    class DifpRegistryServiceImpl extends DifpRegistryServiceGrpc.DifpRegistryServiceImplBase {

        /** §27.1 — Node announces its lobby coverage. */
        @Override
        public void announceLobbies(RegistryAnnouncePayload req,
                                    StreamObserver<AckResponse> obs) {
            try {
                db_upsertRegistryEntry(req.getNodeEndpoint(), req.getLobbiesList());
                obs.onNext(AckResponse.newBuilder().setAccepted(true)
                    .setMessage("Lobbies registered: " + req.getLobbiesCount()).build());
                obs.onCompleted();
            } catch (Exception ex) {
                obs.onError(Status.INTERNAL.withDescription(ex.getMessage()).asException());
            }
        }

        /** §27.2 / §25.3 — Single lobby query. */
        @Override
        public void queryLobby(RegistryQueryPayload req,
                               StreamObserver<RegistryResponsePayload> obs) {
            try {
                List<String> nodes = db_queryRegistryByLobby(req.getLobbyId());
                obs.onNext(RegistryResponsePayload.newBuilder()
                    .setLobbyId(req.getLobbyId())
                    .addAllNodes(nodes)
                    .build());
                obs.onCompleted();
            } catch (Exception ex) {
                obs.onError(Status.INTERNAL.withDescription(ex.getMessage()).asException());
            }
        }

        /** §26.2 — Batch lobby query (for radius discovery). */
        @Override
        public void queryLobbiesBatch(RegistryQueryPayload req,
                                      StreamObserver<RegistryResponsePayload> obs) {
            try {
                RegistryResponsePayload.Builder builder = RegistryResponsePayload.newBuilder();
                for (long lobbyId : req.getLobbyIdsList()) {
                    List<String> nodes = db_queryRegistryByLobby(lobbyId);
                    builder.putBatchResults(String.valueOf(lobbyId),
                        NodeList.newBuilder().addAllNodes(nodes).build());
                }
                obs.onNext(builder.build());
                obs.onCompleted();
            } catch (Exception ex) {
                obs.onError(Status.INTERNAL.withDescription(ex.getMessage()).asException());
            }
        }

        /** §25.3 — Known peer registries. */
        @Override
        public void getRegistryPeers(EmptyRequest req,
                                     StreamObserver<RegistryPeersResponse> obs) {
            obs.onNext(RegistryPeersResponse.newBuilder()
                // PLACEHOLDER: return known peer registries
                .addRegistries("grpc://registry-global.difp:50051")
                .build());
            obs.onCompleted();
        }
    }

    // =========================================================================
    //  SERVER LIFECYCLE
    // =========================================================================

    private void start() throws IOException {
        seedDummyPresence();

        server = Grpc.newServerBuilderForPort(NODE_PORT, InsecureServerCredentials.create())
            .executor(executor)
            .addService(new DifpNodeServiceImpl())
            .addService(new DifpRegistryServiceImpl())
            .build()
            .start();

        logger.info("═══════════════════════════════════════════");
        logger.info("  DIFP Node Server  ·  v" + NODE_VERSION);
        logger.info("  Node ID : " + NODE_ID);
        logger.info("  Port    : " + NODE_PORT);
        logger.info("  Threads : " + THREAD_POOL);
        logger.info("═══════════════════════════════════════════");

        // Self-announce to registry on startup (§25.4)
        announceToRegistry();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** Shutting down DIFP node (JVM shutdown)");
            try {
                DifpNodeServer.this.stop();
            } catch (InterruptedException e) {
                if (server != null) server.shutdownNow();
                e.printStackTrace(System.err);
            } finally {
                executor.shutdown();
            }
            System.err.println("*** DIFP node down");
        }));
    }

    /** §25.4 — Compute owned lobbies and announce to registry on startup. */
    private void announceToRegistry() {
        Set<Long> ownedLobbies = presenceDb.values().stream()
            .map(r -> cellIdToLobbyId(r.getCellId()))
            .collect(Collectors.toSet());

        // PLACEHOLDER: self-register into our own in-memory registry
        db_upsertRegistryEntry("grpc://localhost:" + NODE_PORT, new ArrayList<>(ownedLobbies));
        logger.info("[Registry] Self-announced " + ownedLobbies.size() + " lobbies");
        // Real impl: POST registry.announce to external registry endpoints
    }

    private void stop() throws InterruptedException {
        if (server != null) server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) server.awaitTermination();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final DifpNodeServer node = new DifpNodeServer();
        node.start();
        node.blockUntilShutdown();
    }
}

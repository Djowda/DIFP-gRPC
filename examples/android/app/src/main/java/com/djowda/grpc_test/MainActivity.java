package com.djowda.grpc_test;


import android.content.Context;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;

import io.grpc.StatusRuntimeException;
import io.grpc.okhttp.OkHttpChannelBuilder;
import io.grpc.stub.StreamObserver;

import com.djowda.difp.DifpNodeServiceGrpc;
import com.djowda.difp.IdentityRegisterPayload;
import com.djowda.difp.RegisterResponse;
import com.djowda.difp.QueryCellPayload;
import com.djowda.difp.QueryResponsePayload;
import com.djowda.difp.RegistryResponsePayload;
import com.djowda.difp.DifpRegistryServiceGrpc;
import com.djowda.difp.RegistryQueryPayload;
import com.djowda.difp.DifpEnvelope;
import com.djowda.difp.PresenceRecord;
import com.djowda.difp.GeoPoint;
import com.djowda.difp.NodePongPayload;
import com.djowda.difp.MessageSender;
import com.djowda.difp.MessageTarget;
import com.djowda.difp.SenderRole;
import com.djowda.difp.TargetType;
import com.djowda.difp.MessageMode;
import com.djowda.difp.NodePingPayload;
import com.djowda.difp.TradeMessage;
import com.djowda.difp.TradeType;
import com.djowda.difp.TradeStatus;
import com.djowda.difp.OrderLine;
import com.djowda.difp.TradeContactInfo;
import com.djowda.difp.MessageContext;
import com.djowda.difp.TradeAskPayload;

/**
 * Test client UI
 * <p>
 * Layout (activity_difp_test.xml — wire up these view IDs):
 * <p>
 * EditText  : et_host          (default "10.0.2.2" for Android emulator → localhost)
 * EditText  : et_port          (default "50051")
 * EditText  : et_latitude      (default "36.7538"  — Algiers)
 * EditText  : et_longitude     (default "3.0588")
 * EditText  : et_comp_type     (default "f"  — farmer)
 * EditText  : et_comp_name     (default "Test Farm")
 * Spinner   : sp_flow          (items: computeCell | register | queryCell | ping | tradeAsk | registryQuery)
 * Button    : btn_run
 * TextView  : tv_result        (scrollable)
 */

public class MainActivity extends AppCompatActivity {


    // ── UI ────────────────────────────────────────────────────────────────────
    private EditText etHost, etPort, etLat, etLng, etType, etName;
    private Spinner spFlow;
    private Button btnRun;
    private TextView tvResult;

    // ── Threading ─────────────────────────────────────────────────────────────
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    // ── State ─────────────────────────────────────────────────────────────────
    /**
     * DID received after registration — reused for subsequent stream messages.
     */
    private String myDid = "difp://unknown/f/test-client-01";

    // =========================================================================
    //  MinMax99 — client-side cell computation (§3.2)
    //  Must match the server implementation bit-for-bit.
    // =========================================================================

    private static final long EARTH_WIDTH_METERS = 40_075_000L;
    private static final long EARTH_HEIGHT_METERS = 20_000_000L;
    private static final int CELL_SIZE_METERS = 500;
    private static final long NUM_COLUMNS = 82_000L;
    private static final long NUM_ROWS = 42_000L;
    private static final int LOBBY_SIZE = 41;
    private static final long NUM_LOBBY_ROWS = 1_025L;

    public static long geoToCellNumber(double lat, double lng) {
        double x = (lng + 180.0) * ((double) EARTH_WIDTH_METERS / 360.0);
        double y = (EARTH_HEIGHT_METERS / 2.0)
                - Math.log(Math.tan(Math.PI / 4.0 + Math.toRadians(lat) / 2.0))
                * (EARTH_HEIGHT_METERS / (2.0 * Math.PI));
        long xCell = Math.max(0, Math.min((long) (x / CELL_SIZE_METERS), NUM_COLUMNS - 1));
        long yCell = Math.max(0, Math.min((long) (y / CELL_SIZE_METERS), NUM_ROWS - 1));
        return xCell * NUM_ROWS + yCell;
    }

    public static long cellIdToLobbyId(long cellId) {
        long xCell = cellId / NUM_ROWS;
        long yCell = cellId % NUM_ROWS;
        long lobbyX = xCell / LOBBY_SIZE;
        long lobbyY = yCell / LOBBY_SIZE;
        return lobbyX * NUM_LOBBY_ROWS + lobbyY;
    }

    // =========================================================================
    //  LIFECYCLE
    // =========================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etHost = findViewById(R.id.et_host);
        etPort = findViewById(R.id.et_port);
        etLat = findViewById(R.id.et_latitude);
        etLng = findViewById(R.id.et_longitude);
        etType = findViewById(R.id.et_comp_type);
        etName = findViewById(R.id.et_comp_name);
        spFlow = findViewById(R.id.sp_flow);
        btnRun = findViewById(R.id.btn_run);
        tvResult = findViewById(R.id.tv_result);

        tvResult.setMovementMethod(new android.text.method.ScrollingMovementMethod());

        // Populate flow spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"computeCell", "register", "queryCell",
                        "ping", "tradeAsk", "registryQuery"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFlow.setAdapter(adapter);

        btnRun.setOnClickListener(v -> runFlow());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }

    // =========================================================================
    //  UI DISPATCH
    // =========================================================================

    private void runFlow() {
        hideSoftKeyboard();
        btnRun.setEnabled(false);

        String host = etHost.getText().toString().trim();
        int port = parsePort(etPort.getText().toString().trim());
        double lat = parseDouble(etLat.getText().toString().trim(), 36.7538);
        double lng = parseDouble(etLng.getText().toString().trim(), 3.0588);
        String type = etType.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String flow = (String) spFlow.getSelectedItem();

        setResult("⏳ Running: " + flow + " …");

        executor.execute(() -> {
            String result = dispatchFlow(host, port, lat, lng, type, name, flow);
            mainHandler.post(() -> {
                setResult(result);
                btnRun.setEnabled(true);
            });
        });
    }

    private String dispatchFlow(String host, int port,
                                double lat, double lng,
                                String type, String name, String flow) {
        switch (flow) {
            case "computeCell":
                return flowComputeCell(lat, lng);
            case "register":
                return flowRegister(host, port, lat, lng, type, name);
            case "queryCell":
                return flowQueryCell(host, port, lat, lng, type);
            case "ping":
                return flowPing(host, port);
            case "tradeAsk":
                return flowTradeAsk(host, port, lat, lng, type);
            case "registryQuery":
                return flowRegistryQuery(host, port, lat, lng);
            default:
                return "❓ Unknown flow: " + flow;
        }
    }

    // =========================================================================
    //  FLOW 1 — computeCell (client-side, no network)
    //  §3.2 — validate against test vectors from §3.3
    // =========================================================================

    private String flowComputeCell(double lat, double lng) {
        long cellId = geoToCellNumber(lat, lng);
        long lobbyId = cellIdToLobbyId(cellId);

        StringBuilder sb = new StringBuilder();
        sb.append("📐 Cell Computation (client-side)\n");
        sb.append("──────────────────────────────────\n");
        sb.append("Input     : (" + lat + ", " + lng + ")\n");
        sb.append("Cell ID   : " + cellId + "\n");
        sb.append("Lobby ID  : " + lobbyId + "\n\n");

        sb.append("§3.3 Reference Vectors:\n");
        sb.append(checkVector("Algiers", 36.7538, 3.0588, 1_711_767_603L));
        sb.append(checkVector("Paris", 48.8566, 2.3522, 1_705_129_761L));
        sb.append(checkVector("Tokyo", 35.6895, 139.6917, 2_989_365_749L));
        sb.append(checkVector("New York", 40.7128, -74.0060, 991_131_039L));
        return sb.toString();
    }

    private String checkVector(String name, double lat, double lng, long expected) {
        long got = geoToCellNumber(lat, lng);
        boolean ok = got == expected;
        return (ok ? "  ✅ " : "  ❌ ") + name + ": " + got
                + (ok ? "" : " (expected " + expected + ")") + "\n";
    }

    // =========================================================================
    //  FLOW 2 — register  (Unary RPC)
    //  §4.2 — register component, receive DID + token
    // =========================================================================

    private String flowRegister(String host, int port,
                                double lat, double lng,
                                String type, String name) {
        if (!testTcp(host, port)) return tcpError();

        ManagedChannel channel = buildChannel(host, port);
        try {
            DifpNodeServiceGrpc.DifpNodeServiceBlockingStub stub =
                    DifpNodeServiceGrpc.newBlockingStub(channel)
                            .withDeadlineAfter(10, TimeUnit.SECONDS);

            IdentityRegisterPayload req = IdentityRegisterPayload.newBuilder()
                    .setLocation(GeoPoint.newBuilder().setLatitude(lat).setLongitude(lng).build())
                    .setComponentType(type.isEmpty() ? "f" : type)
                    .setComponentName(name.isEmpty() ? "Test Node" : name)
                    .setPhoneNumber("+213555000000")
                    .build();

            RegisterResponse resp = stub.registerComponent(req);

            if (resp.getSuccess()) {
                myDid = resp.getDid();
                return "✅ Registered!\n"
                        + "DID   : " + resp.getDid() + "\n"
                        + "Token : " + resp.getToken().substring(0, 20) + "…";
            } else {
                return "❌ Registration failed: " + resp.getError();
            }

        } catch (StatusRuntimeException e) {
            return grpcError(e);
        } finally {
            shutdownChannel(channel);
        }
    }

    // =========================================================================
    //  FLOW 3 — queryCell (Unary RPC)
    //  §5 — discover participants in a cell + radius
    // =========================================================================

    private String flowQueryCell(String host, int port,
                                 double lat, double lng, String typeFilter) {
        if (!testTcp(host, port)) return tcpError();

        ManagedChannel channel = buildChannel(host, port);
        try {
            DifpNodeServiceGrpc.DifpNodeServiceBlockingStub stub =
                    DifpNodeServiceGrpc.newBlockingStub(channel)
                            .withDeadlineAfter(10, TimeUnit.SECONDS);

            long cellId = geoToCellNumber(lat, lng);

            QueryCellPayload req = QueryCellPayload.newBuilder()
                    .setCellId(cellId)
                    .setRadius(1)                            // 3×3 = 9 cells (~2.25 km²)
                    .setComponentType(typeFilter)            // "" = all types
                    .build();

            QueryResponsePayload resp = stub.queryCell(req);

            StringBuilder sb = new StringBuilder();
            sb.append("🔍 Query Cell\n");
            sb.append("Cell ID : ").append(cellId).append(" | radius=1\n");
            sb.append("Found   : ").append(resp.getTotalCount()).append(" participants\n");
            sb.append("────────────────────────────────\n");
            for (PresenceRecord p : resp.getParticipantsList()) {
                sb.append("• ").append(p.getComponentName())
                        .append(" [").append(p.getComponentType()).append("] — ")
                        .append(p.getStatus().name()).append("\n");
                sb.append("  DID: ").append(p.getDid()).append("\n");
            }
            return sb.toString();

        } catch (StatusRuntimeException e) {
            return grpcError(e);
        } finally {
            shutdownChannel(channel);
        }
    }

    // =========================================================================
    //  FLOW 4 — ping  (Bidirectional stream)
    //  §16 — node.ping → node.pong via Connect stream
    // =========================================================================

    private String flowPing(String host, int port) {
        if (!testTcp(host, port)) return tcpError();

        ManagedChannel channel = buildChannel(host, port);
        CountDownLatch done = new CountDownLatch(1);
        StringBuilder result = new StringBuilder();

        try {
            DifpNodeServiceGrpc.DifpNodeServiceStub asyncStub =
                    DifpNodeServiceGrpc.newStub(channel)
                            .withDeadlineAfter(10, TimeUnit.SECONDS);

            StreamObserver<DifpEnvelope> requestStream =
                    asyncStub.connect(new StreamObserver<DifpEnvelope>() {
                        @Override
                        public void onNext(DifpEnvelope resp) {
                            if ("node.pong".equals(resp.getType())) {
                                NodePongPayload pong = resp.getNodePong();
                                long rtt = System.currentTimeMillis() - pong.getSentAt();
                                result.append("🏓 PONG received!\n")
                                        .append("Node    : ").append(pong.getNodeId()).append("\n")
                                        .append("RTT     : ").append(rtt).append(" ms\n")
                                        .append("Sent at : ").append(pong.getSentAt()).append("\n")
                                        .append("Recv at : ").append(pong.getReceivedAt()).append("\n");
                            }
                        }

                        @Override
                        public void onError(Throwable t) {
                            result.append("❌ Stream error: ").append(t.getMessage());
                            done.countDown();
                        }

                        @Override
                        public void onCompleted() {
                            done.countDown();
                        }
                    });

            // Send node.ping
            long sentAt = System.currentTimeMillis();
            DifpEnvelope ping = DifpEnvelope.newBuilder()
                    .setId("msg-" + sentAt + "-ping")
                    .setType("node.ping")
                    .setVersion("0.4")
                    .setFrom(MessageSender.newBuilder()
                            .setDid(myDid).setNode("android-client").setRole(SenderRole.CLIENT).build())
                    .setTarget(MessageTarget.newBuilder()
                            .setType(TargetType.NODE_TARGET).setValue("node-oran-01").build())
                    .setMode(MessageMode.REQUEST)
                    .setCell(String.valueOf(geoToCellNumber(36.7538, 3.0588)))
                    .setTimestamp(Instant.now().toString())
                    .setTtl(30)
                    .setNonce(sentAt)
                    .setNodePing(NodePingPayload.newBuilder()
                            .setNodeId("android-client")
                            .setSentAt(sentAt)
                            .build())
                    .build();

            requestStream.onNext(ping);
            requestStream.onCompleted();

            done.await(10, TimeUnit.SECONDS);
            return result.length() > 0 ? result.toString() : "⏱ Timeout — no pong received";

        } catch (Exception e) {
            return "❌ Error: " + e.getMessage();
        } finally {
            shutdownChannel(channel);
        }
    }

    // =========================================================================
    //  FLOW 5 — tradeAsk  (Bidirectional stream)
    //  §7 — broadcast a demand signal (trade.ask) via Connect stream
    // =========================================================================

    private String flowTradeAsk(String host, int port,
                                double lat, double lng, String type) {
        if (!testTcp(host, port)) return tcpError();

        ManagedChannel channel = buildChannel(host, port);
        CountDownLatch done = new CountDownLatch(1);
        StringBuilder result = new StringBuilder();

        try {
            DifpNodeServiceGrpc.DifpNodeServiceStub asyncStub =
                    DifpNodeServiceGrpc.newStub(channel)
                            .withDeadlineAfter(10, TimeUnit.SECONDS);

            long cellId = geoToCellNumber(lat, lng);

            StreamObserver<DifpEnvelope> requestStream =
                    asyncStub.connect(new StreamObserver<DifpEnvelope>() {
                        @Override
                        public void onNext(DifpEnvelope resp) {
                            result.append("📨 Server reply: ").append(resp.getType()).append("\n");
                        }

                        @Override
                        public void onError(Throwable t) {
                            result.append("❌ ").append(t.getMessage());
                            done.countDown();
                        }

                        @Override
                        public void onCompleted() {
                            done.countDown();
                        }
                    });

            // Build a minimal TradeMessage (Ask type, §7.2)
            String tradeId = "trade-" + UUID.randomUUID().toString().substring(0, 8);
            long now = System.currentTimeMillis();

            TradeMessage ask = TradeMessage.newBuilder()
                    .setTradeId(tradeId)
                    .setSenderDid(myDid)
                    .setSenderType(type.isEmpty() ? "u" : type)
                    .setSenderCell(String.valueOf(cellId))
                    .setReceiverDid("broadcast")
                    .setReceiverType("s")
                    .setReceiverCell(String.valueOf(cellId))
                    .setTradeType(TradeType.ASK)
                    .setStatus(TradeStatus.PENDING)
                    // Items: flat map { item_id: 1 } for Ask (§7.2)
                    .putItems("difp:item:dz:vegetables:tomato_kg:v1",
                            OrderLine.newBuilder().setQuantity(1).build())
                    .putItems("difp:item:dz:grains:semolina_kg:v1",
                            OrderLine.newBuilder().setQuantity(1).build())
                    .setListSize(2)
                    .setCreatedAt(now)
                    .setLastUpdated(now)
                    .setInfo(TradeContactInfo.newBuilder()
                            .setPhone("+213555999999")
                            .setComment("Need urgently — looking for local suppliers")
                            .build())
                    .build();

            DifpEnvelope envelope = DifpEnvelope.newBuilder()
                    .setId("msg-" + now + "-ask")
                    .setType("trade.ask")
                    .setVersion("0.4")
                    .setFrom(MessageSender.newBuilder()
                            .setDid(myDid).setNode("android-client").setRole(SenderRole.CLIENT).build())
                    .setTarget(MessageTarget.newBuilder()
                            .setType(TargetType.CELL).setValue(String.valueOf(cellId)).build())
                    .setMode(MessageMode.EVENT)
                    .setCell(String.valueOf(cellId))
                    .setTimestamp(Instant.now().toString())
                    .setTtl(300)
                    .setNonce(now)
                    .setContext(MessageContext.newBuilder()
                            .setTraceId("trace-" + UUID.randomUUID().toString().substring(0, 8))
                            .build())
                    .setTradeAsk(TradeAskPayload.newBuilder().setTrade(ask).build())
                    .build();

            requestStream.onNext(envelope);
            requestStream.onCompleted();

            done.await(5, TimeUnit.SECONDS);

            String status = result.length() > 0 ? result.toString() : "(EVENT mode — no reply expected)";
            return "📢 trade.ask sent!\n"
                    + "Trade ID  : " + tradeId + "\n"
                    + "Cell ID   : " + cellId + "\n"
                    + "Items     : tomato_kg, semolina_kg\n"
                    + "Mode      : event (broadcast to cell)\n"
                    + "Server    : " + status;

        } catch (Exception e) {
            return "❌ Error: " + e.getMessage();
        } finally {
            shutdownChannel(channel);
        }
    }

    // =========================================================================
    //  FLOW 6 — registryQuery (Unary RPC — DifpRegistryService)
    //  §27.2 — discover which nodes serve a lobby
    // =========================================================================

    private String flowRegistryQuery(String host, int port, double lat, double lng) {
        if (!testTcp(host, port)) return tcpError();

        ManagedChannel channel = buildChannel(host, port);
        try {
            DifpRegistryServiceGrpc.DifpRegistryServiceBlockingStub stub =
                    DifpRegistryServiceGrpc.newBlockingStub(channel)
                            .withDeadlineAfter(10, TimeUnit.SECONDS);

            long cellId = geoToCellNumber(lat, lng);
            long lobbyId = cellIdToLobbyId(cellId);

            RegistryQueryPayload req = RegistryQueryPayload.newBuilder()
                    .setLobbyId(lobbyId)
                    .build();

            RegistryResponsePayload resp = stub.queryLobby(req);

            StringBuilder sb = new StringBuilder();
            sb.append("🗺 Registry Query\n");
            sb.append("Cell ID  : ").append(cellId).append("\n");
            sb.append("Lobby ID : ").append(lobbyId).append("\n");
            sb.append("Nodes    : ").append(resp.getNodesCount()).append("\n");
            sb.append("────────────────────────────────\n");
            for (String node : resp.getNodesList()) {
                sb.append("• ").append(node).append("\n");
            }
            return sb.toString();

        } catch (StatusRuntimeException e) {
            return grpcError(e);
        } finally {
            shutdownChannel(channel);
        }
    }

    // =========================================================================
    //  HELPERS
    // =========================================================================

    private ManagedChannel buildChannel(String host, int port) {
        return OkHttpChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
    }

    private void shutdownChannel(ManagedChannel channel) {
        try {
            channel.shutdown().awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {
        }
    }

    private boolean testTcp(String host, int port) {
        try (java.net.Socket s = new java.net.Socket()) {
            s.connect(new java.net.InetSocketAddress(host, port), 3000);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String tcpError() {
        return "❌ TCP connection failed — is the server running?";
    }

    private String grpcError(StatusRuntimeException e) {
        io.grpc.Status status = io.grpc.Status.fromThrowable(e);
        return "❌ gRPC Error\nCode : " + status.getCode().name()
                + "\nDesc : " + status.getDescription();
    }

    private int parsePort(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 50051;
        }
    }

    private double parseDouble(String s, double def) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    private void setResult(String text) {
        tvResult.setText(text);
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View focus = getCurrentFocus();
        if (imm != null && focus != null) imm.hideSoftInputFromWindow(focus.getWindowToken(), 0);
    }
}

# DIFP-gRPC

> **Djowda Interconnected Food Protocol — gRPC Transport Profile**  
> Specification v0.4 · Skull Implementation

[![License: CC-BY 4.0](https://img.shields.io/badge/License-CC--BY%204.0-green.svg)](https://creativecommons.org/licenses/by/4.0/)
[![Protocol](https://img.shields.io/badge/DIFP-v0.4-teal.svg)](https://djowda.com/difp/)
[![Status](https://img.shields.io/badge/status-skull%20preview-orange.svg)]()

This repository contains the **gRPC transport implementation** of [DIFP v0.4](https://djowda.com/difp/) — the Djowda Interconnected Food Protocol. It is an official working preview, not a production release. Every placeholder is clearly marked for incremental replacement.

---

## Repository layout

```
DIFP-gRPC/
│
├── proto/
│   └── difp.proto                          ← Single source of truth for the entire protocol
│
├── server/
│   └── java/
│       └── examples/                       ← Gradle project root (build.gradle lives here)
│           ├── build.gradle
│           ├── gradlew.bat
│           └── src/main/
│               ├── java/io/grpc/examples/helloworld/
│               │   └── DifpNodeServer.java
│               └── proto/                  ← symlink → ../../../../proto/difp.proto
│
├── examples/
│   └── android/                            ← Android test client — open this in Android Studio
│       ├── build.gradle
│       ├── settings.gradle
│       └── app/src/main/
│           ├── java/com/djowda/difp/
│           │   └── MainActivity.java
│           └── res/layout/
│               └── activity_difp_test.xml
│
├── docs/
│   ├── architecture.md                     ← Skull roadmap & next iteration targets
│   └── proto-guide.md                      ← Wire format notes & message type reference
│
└── .github/
    └── workflows/build.yml                 ← CI: compile proto → build server → build android
```

**The `proto/` directory is the single source of truth.** Both the server and the Android example symlink `difp.proto` from there — no copies, no drift.

---

## What is implemented (skull v0.4)

| DIFP Section | Coverage |
|---|---|
| §3 MinMax99 Grid | `geoToCellNumber`, `getNearbyCells` — bit-for-bit correct, validated against §3.3 vectors |
| §4 Component Identity | `RegisterComponent` RPC — computes cell ID, assigns DID, placeholder token |
| §5 Presence & Discovery | `QueryCell` RPC + `presence.*` stream handlers, in-memory store |
| §7 Trade Protocol | `trade.ask/offer/donate/accept/reject/complete/cancel` — all routed via bidirectional stream |
| §15 Message Envelope | `DifpEnvelope` with `oneof payload` covering all 30+ message types |
| §18 Node Pipeline | 6-step processing pipeline (crypto step marked TODO) |
| §24 Lobby Layer | `cellIdToLobbyId` — Layer 1 spatial addressing |
| §25-27 Registry | `DifpRegistryService` — in-memory lobby→node registry, all 4 RPCs |

### Placeholder functions

Every DB interaction is a clearly named placeholder:

```java
db_queryPresenceByCell()   // → replace with PostGIS / Firebase spatial query
db_createTrade()           // → replace with §9.1 atomic fan-out write
db_updateTradeStatus()     // → replace with §9.2 atomic fan-out write
db_registerComponent()     // → replace with auth provider + persistent store
db_upsertRegistryEntry()   // → replace with Redis SADD or registry DB
```

---

## Quick start

### 1. Run the server

Navigate to the Gradle project root and build the distribution:

```powershell
cd server\java\examples
.\gradlew.bat installDist
```

Then launch the node:

```powershell
.\build\install\examples\bin\Difp-Node-Server
# INFO: Server started, listening on 50051
# INFO: [DB] Seeded 3 dummy presence records (Algiers cell 1,711,767,603)
```

### 2. Run the Android client

Open the folder `examples\android\` as a project in **Android Studio**.

Default connection settings (pre-filled in the UI):

| Field | Default | Notes |
|---|---|---|
| Host | `10.0.2.2` | Android emulator → host machine localhost |
| Port | `50051` | Matches the server default |
| Location | `36.7538, 3.0588` | Algiers — matches seeded dummy data |

Select a flow from the spinner and tap **Run Flow**:

| Flow | What it tests |
|---|---|
| `computeCell` | §3.3 reference vectors — shows ✅/❌ per city, no network needed |
| `register` | Unary RPC — registers a component, receives a real DID back |
| `queryCell` | Lists nearby participants from the in-memory seed data |
| `ping` | Bidirectional stream: `node.ping` → `node.pong` + RTT |
| `tradeAsk` | Full `trade.ask` envelope broadcast via stream |
| `registryQuery` | Lobby → node lookup via `DifpRegistryService` |

### 3. Connect emulator to local server (ADB port forwarding)

The Android emulator runs in an isolated network. You must forward the gRPC port from the emulator back to your machine **after** the emulator boots.

Open a terminal inside Android Studio and run:

```powershell
adb reverse tcp:50051 tcp:50051
```

If you have multiple devices or see a port conflict, find the emulator's ADB port first (shown in the emulator window title, e.g. `5554`) and use the specific serial:

```powershell
# Find connected devices
adb devices

# Forward for a specific emulator serial
adb -s emulator-5554 reverse tcp:50051 tcp:50051
```

> **If the connection still fails after the first launch**, try running the command again — the emulator sometimes resets port rules between boots. Run the app again after re-forwarding.

---

## Building from source

### Prerequisites

- JDK 17+
- Gradle 8+ (or use the included `gradlew.bat` wrapper — no install needed)
- Android Studio Hedgehog+ (for the Android example)
- `protoc` is **not** required locally — the Gradle protobuf plugin downloads it automatically

### Server

```powershell
cd server\java\examples

.\gradlew.bat installDist          # compile proto + build distribution
.\gradlew.bat test                 # run reference vector tests

# Run the built distribution
.\build\install\examples\bin\Difp-Node-Server
```

### Android example

Open `examples\android\` in Android Studio and run via the IDE, or build from the command line:

```powershell
cd examples\android
.\gradlew.bat assembleDebug
# APK: app\build\outputs\apk\debug\app-debug.apk
```

---

## Proto update workflow

When `difp.proto` changes:

```powershell
# 1. Edit the canonical file
notepad proto\difp.proto   # or your editor of choice

# 2. Rebuild both projects — symlinks mean no manual copy needed
cd server\java\examples  ; .\gradlew.bat generateProto ; cd ..\..\..
cd examples\android      ; .\gradlew.bat generateProto ; cd ..\..

# 3. Commit everything together
git add proto\difp.proto server\ examples\android\
git commit -m "proto: <describe the change>"
```

---

## Next iteration targets

1. **Stream broadcasting** — push `trade.ask` to all connected clients in the same cell
2. **Ed25519 signing** — §15.3 canonical hashing + signature verification
3. **Real DB** — replace placeholder functions with your storage backend
4. **Federation** — `registry.announce` to external registry nodes on startup (§25.4)
5. **PAD endpoints** — `/.well-known/difp/pad/latest` for catalog distribution (§22.5)
6. **TradeEngine** — accept/reject/complete flows with atomic §9 fan-out writes

See [`docs/architecture.md`](docs/architecture.md) for the full skull-to-production roadmap.

---

## Contributing

DIFP is an open protocol (CC-BY 4.0). Contributions to the gRPC transport profile are welcome:

- Bug reports and spec clarifications → open an Issue
- Client implementations in other languages → open a PR adding `examples/{language}/`
- Protocol questions → [spec@djowda.com](mailto:spec@djowda.com)

---

## License

**CC-BY 4.0** — free to implement, fork, and commercialize with attribution.  
See [LICENSE](LICENSE) · [djowda.com/difp](https://djowda.com/difp/)
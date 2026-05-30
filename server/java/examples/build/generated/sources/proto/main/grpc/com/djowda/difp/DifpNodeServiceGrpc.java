package com.djowda.difp;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * ============================================================
 *  Service 1 — DifpNodeService
 *  The main DIFP node.  Clients connect via bidirectional
 *  stream (Connect) for real-time coordination.
 *  Unary shortcuts for simple request/response flows.
 * ============================================================
 * </pre>
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class DifpNodeServiceGrpc {

  private DifpNodeServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "difp.v04.DifpNodeService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.djowda.difp.DifpEnvelope,
      com.djowda.difp.DifpEnvelope> getConnectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Connect",
      requestType = com.djowda.difp.DifpEnvelope.class,
      responseType = com.djowda.difp.DifpEnvelope.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<com.djowda.difp.DifpEnvelope,
      com.djowda.difp.DifpEnvelope> getConnectMethod() {
    io.grpc.MethodDescriptor<com.djowda.difp.DifpEnvelope, com.djowda.difp.DifpEnvelope> getConnectMethod;
    if ((getConnectMethod = DifpNodeServiceGrpc.getConnectMethod) == null) {
      synchronized (DifpNodeServiceGrpc.class) {
        if ((getConnectMethod = DifpNodeServiceGrpc.getConnectMethod) == null) {
          DifpNodeServiceGrpc.getConnectMethod = getConnectMethod =
              io.grpc.MethodDescriptor.<com.djowda.difp.DifpEnvelope, com.djowda.difp.DifpEnvelope>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Connect"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.djowda.difp.DifpEnvelope.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.djowda.difp.DifpEnvelope.getDefaultInstance()))
              .setSchemaDescriptor(new DifpNodeServiceMethodDescriptorSupplier("Connect"))
              .build();
        }
      }
    }
    return getConnectMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.djowda.difp.IdentityRegisterPayload,
      com.djowda.difp.RegisterResponse> getRegisterComponentMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RegisterComponent",
      requestType = com.djowda.difp.IdentityRegisterPayload.class,
      responseType = com.djowda.difp.RegisterResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.djowda.difp.IdentityRegisterPayload,
      com.djowda.difp.RegisterResponse> getRegisterComponentMethod() {
    io.grpc.MethodDescriptor<com.djowda.difp.IdentityRegisterPayload, com.djowda.difp.RegisterResponse> getRegisterComponentMethod;
    if ((getRegisterComponentMethod = DifpNodeServiceGrpc.getRegisterComponentMethod) == null) {
      synchronized (DifpNodeServiceGrpc.class) {
        if ((getRegisterComponentMethod = DifpNodeServiceGrpc.getRegisterComponentMethod) == null) {
          DifpNodeServiceGrpc.getRegisterComponentMethod = getRegisterComponentMethod =
              io.grpc.MethodDescriptor.<com.djowda.difp.IdentityRegisterPayload, com.djowda.difp.RegisterResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RegisterComponent"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.djowda.difp.IdentityRegisterPayload.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.djowda.difp.RegisterResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DifpNodeServiceMethodDescriptorSupplier("RegisterComponent"))
              .build();
        }
      }
    }
    return getRegisterComponentMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.djowda.difp.GeoCellRequest,
      com.djowda.difp.GeoCellResponse> getComputeCellMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ComputeCell",
      requestType = com.djowda.difp.GeoCellRequest.class,
      responseType = com.djowda.difp.GeoCellResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.djowda.difp.GeoCellRequest,
      com.djowda.difp.GeoCellResponse> getComputeCellMethod() {
    io.grpc.MethodDescriptor<com.djowda.difp.GeoCellRequest, com.djowda.difp.GeoCellResponse> getComputeCellMethod;
    if ((getComputeCellMethod = DifpNodeServiceGrpc.getComputeCellMethod) == null) {
      synchronized (DifpNodeServiceGrpc.class) {
        if ((getComputeCellMethod = DifpNodeServiceGrpc.getComputeCellMethod) == null) {
          DifpNodeServiceGrpc.getComputeCellMethod = getComputeCellMethod =
              io.grpc.MethodDescriptor.<com.djowda.difp.GeoCellRequest, com.djowda.difp.GeoCellResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ComputeCell"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.djowda.difp.GeoCellRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.djowda.difp.GeoCellResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DifpNodeServiceMethodDescriptorSupplier("ComputeCell"))
              .build();
        }
      }
    }
    return getComputeCellMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.djowda.difp.QueryCellPayload,
      com.djowda.difp.QueryResponsePayload> getQueryCellMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryCell",
      requestType = com.djowda.difp.QueryCellPayload.class,
      responseType = com.djowda.difp.QueryResponsePayload.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.djowda.difp.QueryCellPayload,
      com.djowda.difp.QueryResponsePayload> getQueryCellMethod() {
    io.grpc.MethodDescriptor<com.djowda.difp.QueryCellPayload, com.djowda.difp.QueryResponsePayload> getQueryCellMethod;
    if ((getQueryCellMethod = DifpNodeServiceGrpc.getQueryCellMethod) == null) {
      synchronized (DifpNodeServiceGrpc.class) {
        if ((getQueryCellMethod = DifpNodeServiceGrpc.getQueryCellMethod) == null) {
          DifpNodeServiceGrpc.getQueryCellMethod = getQueryCellMethod =
              io.grpc.MethodDescriptor.<com.djowda.difp.QueryCellPayload, com.djowda.difp.QueryResponsePayload>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QueryCell"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.djowda.difp.QueryCellPayload.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.djowda.difp.QueryResponsePayload.getDefaultInstance()))
              .setSchemaDescriptor(new DifpNodeServiceMethodDescriptorSupplier("QueryCell"))
              .build();
        }
      }
    }
    return getQueryCellMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.djowda.difp.NodeInfoRequest,
      com.djowda.difp.NodeInfoResponse> getGetNodeInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetNodeInfo",
      requestType = com.djowda.difp.NodeInfoRequest.class,
      responseType = com.djowda.difp.NodeInfoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.djowda.difp.NodeInfoRequest,
      com.djowda.difp.NodeInfoResponse> getGetNodeInfoMethod() {
    io.grpc.MethodDescriptor<com.djowda.difp.NodeInfoRequest, com.djowda.difp.NodeInfoResponse> getGetNodeInfoMethod;
    if ((getGetNodeInfoMethod = DifpNodeServiceGrpc.getGetNodeInfoMethod) == null) {
      synchronized (DifpNodeServiceGrpc.class) {
        if ((getGetNodeInfoMethod = DifpNodeServiceGrpc.getGetNodeInfoMethod) == null) {
          DifpNodeServiceGrpc.getGetNodeInfoMethod = getGetNodeInfoMethod =
              io.grpc.MethodDescriptor.<com.djowda.difp.NodeInfoRequest, com.djowda.difp.NodeInfoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetNodeInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.djowda.difp.NodeInfoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.djowda.difp.NodeInfoResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DifpNodeServiceMethodDescriptorSupplier("GetNodeInfo"))
              .build();
        }
      }
    }
    return getGetNodeInfoMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DifpNodeServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DifpNodeServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DifpNodeServiceStub>() {
        @java.lang.Override
        public DifpNodeServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DifpNodeServiceStub(channel, callOptions);
        }
      };
    return DifpNodeServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static DifpNodeServiceBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DifpNodeServiceBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DifpNodeServiceBlockingV2Stub>() {
        @java.lang.Override
        public DifpNodeServiceBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DifpNodeServiceBlockingV2Stub(channel, callOptions);
        }
      };
    return DifpNodeServiceBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DifpNodeServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DifpNodeServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DifpNodeServiceBlockingStub>() {
        @java.lang.Override
        public DifpNodeServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DifpNodeServiceBlockingStub(channel, callOptions);
        }
      };
    return DifpNodeServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DifpNodeServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DifpNodeServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DifpNodeServiceFutureStub>() {
        @java.lang.Override
        public DifpNodeServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DifpNodeServiceFutureStub(channel, callOptions);
        }
      };
    return DifpNodeServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * ============================================================
   *  Service 1 — DifpNodeService
   *  The main DIFP node.  Clients connect via bidirectional
   *  stream (Connect) for real-time coordination.
   *  Unary shortcuts for simple request/response flows.
   * ============================================================
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * ── Main channel ──────────────────────────────────────────
     * Full-duplex: client sends DifpEnvelopes, server streams
     * back events, query responses, and broadcast messages.
     * All message types route through this single stream.
     * </pre>
     */
    default io.grpc.stub.StreamObserver<com.djowda.difp.DifpEnvelope> connect(
        io.grpc.stub.StreamObserver<com.djowda.difp.DifpEnvelope> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getConnectMethod(), responseObserver);
    }

    /**
     * <pre>
     * ── Identity (§4) ─────────────────────────────────────────
     * Register a new component.  Server computes cell ID from
     * GPS, assigns DID, issues token.  Placeholder DB in v0.skull.
     * </pre>
     */
    default void registerComponent(com.djowda.difp.IdentityRegisterPayload request,
        io.grpc.stub.StreamObserver<com.djowda.difp.RegisterResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterComponentMethod(), responseObserver);
    }

    /**
     * <pre>
     * ── Spatial (§3) ──────────────────────────────────────────
     * Stateless utility — compute cell + lobby IDs from lat/lng.
     * </pre>
     */
    default void computeCell(com.djowda.difp.GeoCellRequest request,
        io.grpc.stub.StreamObserver<com.djowda.difp.GeoCellResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getComputeCellMethod(), responseObserver);
    }

    /**
     * <pre>
     * ── Presence (§5) ─────────────────────────────────────────
     * Direct query for participants in a cell / radius.
     * </pre>
     */
    default void queryCell(com.djowda.difp.QueryCellPayload request,
        io.grpc.stub.StreamObserver<com.djowda.difp.QueryResponsePayload> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQueryCellMethod(), responseObserver);
    }

    /**
     * <pre>
     * ── Node meta ─────────────────────────────────────────────
     * Returns the node's well-known info (§10.1).
     * </pre>
     */
    default void getNodeInfo(com.djowda.difp.NodeInfoRequest request,
        io.grpc.stub.StreamObserver<com.djowda.difp.NodeInfoResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetNodeInfoMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service DifpNodeService.
   * <pre>
   * ============================================================
   *  Service 1 — DifpNodeService
   *  The main DIFP node.  Clients connect via bidirectional
   *  stream (Connect) for real-time coordination.
   *  Unary shortcuts for simple request/response flows.
   * ============================================================
   * </pre>
   */
  public static abstract class DifpNodeServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return DifpNodeServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service DifpNodeService.
   * <pre>
   * ============================================================
   *  Service 1 — DifpNodeService
   *  The main DIFP node.  Clients connect via bidirectional
   *  stream (Connect) for real-time coordination.
   *  Unary shortcuts for simple request/response flows.
   * ============================================================
   * </pre>
   */
  public static final class DifpNodeServiceStub
      extends io.grpc.stub.AbstractAsyncStub<DifpNodeServiceStub> {
    private DifpNodeServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DifpNodeServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DifpNodeServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * ── Main channel ──────────────────────────────────────────
     * Full-duplex: client sends DifpEnvelopes, server streams
     * back events, query responses, and broadcast messages.
     * All message types route through this single stream.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<com.djowda.difp.DifpEnvelope> connect(
        io.grpc.stub.StreamObserver<com.djowda.difp.DifpEnvelope> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getConnectMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * ── Identity (§4) ─────────────────────────────────────────
     * Register a new component.  Server computes cell ID from
     * GPS, assigns DID, issues token.  Placeholder DB in v0.skull.
     * </pre>
     */
    public void registerComponent(com.djowda.difp.IdentityRegisterPayload request,
        io.grpc.stub.StreamObserver<com.djowda.difp.RegisterResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterComponentMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * ── Spatial (§3) ──────────────────────────────────────────
     * Stateless utility — compute cell + lobby IDs from lat/lng.
     * </pre>
     */
    public void computeCell(com.djowda.difp.GeoCellRequest request,
        io.grpc.stub.StreamObserver<com.djowda.difp.GeoCellResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getComputeCellMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * ── Presence (§5) ─────────────────────────────────────────
     * Direct query for participants in a cell / radius.
     * </pre>
     */
    public void queryCell(com.djowda.difp.QueryCellPayload request,
        io.grpc.stub.StreamObserver<com.djowda.difp.QueryResponsePayload> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQueryCellMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * ── Node meta ─────────────────────────────────────────────
     * Returns the node's well-known info (§10.1).
     * </pre>
     */
    public void getNodeInfo(com.djowda.difp.NodeInfoRequest request,
        io.grpc.stub.StreamObserver<com.djowda.difp.NodeInfoResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetNodeInfoMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service DifpNodeService.
   * <pre>
   * ============================================================
   *  Service 1 — DifpNodeService
   *  The main DIFP node.  Clients connect via bidirectional
   *  stream (Connect) for real-time coordination.
   *  Unary shortcuts for simple request/response flows.
   * ============================================================
   * </pre>
   */
  public static final class DifpNodeServiceBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<DifpNodeServiceBlockingV2Stub> {
    private DifpNodeServiceBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DifpNodeServiceBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DifpNodeServiceBlockingV2Stub(channel, callOptions);
    }

    /**
     * <pre>
     * ── Main channel ──────────────────────────────────────────
     * Full-duplex: client sends DifpEnvelopes, server streams
     * back events, query responses, and broadcast messages.
     * All message types route through this single stream.
     * </pre>
     */
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/10918")
    public io.grpc.stub.BlockingClientCall<com.djowda.difp.DifpEnvelope, com.djowda.difp.DifpEnvelope>
        connect() {
      return io.grpc.stub.ClientCalls.blockingBidiStreamingCall(
          getChannel(), getConnectMethod(), getCallOptions());
    }

    /**
     * <pre>
     * ── Identity (§4) ─────────────────────────────────────────
     * Register a new component.  Server computes cell ID from
     * GPS, assigns DID, issues token.  Placeholder DB in v0.skull.
     * </pre>
     */
    public com.djowda.difp.RegisterResponse registerComponent(com.djowda.difp.IdentityRegisterPayload request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getRegisterComponentMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ── Spatial (§3) ──────────────────────────────────────────
     * Stateless utility — compute cell + lobby IDs from lat/lng.
     * </pre>
     */
    public com.djowda.difp.GeoCellResponse computeCell(com.djowda.difp.GeoCellRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getComputeCellMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ── Presence (§5) ─────────────────────────────────────────
     * Direct query for participants in a cell / radius.
     * </pre>
     */
    public com.djowda.difp.QueryResponsePayload queryCell(com.djowda.difp.QueryCellPayload request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getQueryCellMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ── Node meta ─────────────────────────────────────────────
     * Returns the node's well-known info (§10.1).
     * </pre>
     */
    public com.djowda.difp.NodeInfoResponse getNodeInfo(com.djowda.difp.NodeInfoRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getGetNodeInfoMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service DifpNodeService.
   * <pre>
   * ============================================================
   *  Service 1 — DifpNodeService
   *  The main DIFP node.  Clients connect via bidirectional
   *  stream (Connect) for real-time coordination.
   *  Unary shortcuts for simple request/response flows.
   * ============================================================
   * </pre>
   */
  public static final class DifpNodeServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<DifpNodeServiceBlockingStub> {
    private DifpNodeServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DifpNodeServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DifpNodeServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * ── Identity (§4) ─────────────────────────────────────────
     * Register a new component.  Server computes cell ID from
     * GPS, assigns DID, issues token.  Placeholder DB in v0.skull.
     * </pre>
     */
    public com.djowda.difp.RegisterResponse registerComponent(com.djowda.difp.IdentityRegisterPayload request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterComponentMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ── Spatial (§3) ──────────────────────────────────────────
     * Stateless utility — compute cell + lobby IDs from lat/lng.
     * </pre>
     */
    public com.djowda.difp.GeoCellResponse computeCell(com.djowda.difp.GeoCellRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getComputeCellMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ── Presence (§5) ─────────────────────────────────────────
     * Direct query for participants in a cell / radius.
     * </pre>
     */
    public com.djowda.difp.QueryResponsePayload queryCell(com.djowda.difp.QueryCellPayload request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQueryCellMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * ── Node meta ─────────────────────────────────────────────
     * Returns the node's well-known info (§10.1).
     * </pre>
     */
    public com.djowda.difp.NodeInfoResponse getNodeInfo(com.djowda.difp.NodeInfoRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetNodeInfoMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service DifpNodeService.
   * <pre>
   * ============================================================
   *  Service 1 — DifpNodeService
   *  The main DIFP node.  Clients connect via bidirectional
   *  stream (Connect) for real-time coordination.
   *  Unary shortcuts for simple request/response flows.
   * ============================================================
   * </pre>
   */
  public static final class DifpNodeServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<DifpNodeServiceFutureStub> {
    private DifpNodeServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DifpNodeServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DifpNodeServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * ── Identity (§4) ─────────────────────────────────────────
     * Register a new component.  Server computes cell ID from
     * GPS, assigns DID, issues token.  Placeholder DB in v0.skull.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.djowda.difp.RegisterResponse> registerComponent(
        com.djowda.difp.IdentityRegisterPayload request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterComponentMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * ── Spatial (§3) ──────────────────────────────────────────
     * Stateless utility — compute cell + lobby IDs from lat/lng.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.djowda.difp.GeoCellResponse> computeCell(
        com.djowda.difp.GeoCellRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getComputeCellMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * ── Presence (§5) ─────────────────────────────────────────
     * Direct query for participants in a cell / radius.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.djowda.difp.QueryResponsePayload> queryCell(
        com.djowda.difp.QueryCellPayload request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQueryCellMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * ── Node meta ─────────────────────────────────────────────
     * Returns the node's well-known info (§10.1).
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.djowda.difp.NodeInfoResponse> getNodeInfo(
        com.djowda.difp.NodeInfoRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetNodeInfoMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER_COMPONENT = 0;
  private static final int METHODID_COMPUTE_CELL = 1;
  private static final int METHODID_QUERY_CELL = 2;
  private static final int METHODID_GET_NODE_INFO = 3;
  private static final int METHODID_CONNECT = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REGISTER_COMPONENT:
          serviceImpl.registerComponent((com.djowda.difp.IdentityRegisterPayload) request,
              (io.grpc.stub.StreamObserver<com.djowda.difp.RegisterResponse>) responseObserver);
          break;
        case METHODID_COMPUTE_CELL:
          serviceImpl.computeCell((com.djowda.difp.GeoCellRequest) request,
              (io.grpc.stub.StreamObserver<com.djowda.difp.GeoCellResponse>) responseObserver);
          break;
        case METHODID_QUERY_CELL:
          serviceImpl.queryCell((com.djowda.difp.QueryCellPayload) request,
              (io.grpc.stub.StreamObserver<com.djowda.difp.QueryResponsePayload>) responseObserver);
          break;
        case METHODID_GET_NODE_INFO:
          serviceImpl.getNodeInfo((com.djowda.difp.NodeInfoRequest) request,
              (io.grpc.stub.StreamObserver<com.djowda.difp.NodeInfoResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CONNECT:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.connect(
              (io.grpc.stub.StreamObserver<com.djowda.difp.DifpEnvelope>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getConnectMethod(),
          io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
            new MethodHandlers<
              com.djowda.difp.DifpEnvelope,
              com.djowda.difp.DifpEnvelope>(
                service, METHODID_CONNECT)))
        .addMethod(
          getRegisterComponentMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.djowda.difp.IdentityRegisterPayload,
              com.djowda.difp.RegisterResponse>(
                service, METHODID_REGISTER_COMPONENT)))
        .addMethod(
          getComputeCellMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.djowda.difp.GeoCellRequest,
              com.djowda.difp.GeoCellResponse>(
                service, METHODID_COMPUTE_CELL)))
        .addMethod(
          getQueryCellMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.djowda.difp.QueryCellPayload,
              com.djowda.difp.QueryResponsePayload>(
                service, METHODID_QUERY_CELL)))
        .addMethod(
          getGetNodeInfoMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.djowda.difp.NodeInfoRequest,
              com.djowda.difp.NodeInfoResponse>(
                service, METHODID_GET_NODE_INFO)))
        .build();
  }

  private static abstract class DifpNodeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DifpNodeServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.djowda.difp.DifpProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("DifpNodeService");
    }
  }

  private static final class DifpNodeServiceFileDescriptorSupplier
      extends DifpNodeServiceBaseDescriptorSupplier {
    DifpNodeServiceFileDescriptorSupplier() {}
  }

  private static final class DifpNodeServiceMethodDescriptorSupplier
      extends DifpNodeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    DifpNodeServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (DifpNodeServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DifpNodeServiceFileDescriptorSupplier())
              .addMethod(getConnectMethod())
              .addMethod(getRegisterComponentMethod())
              .addMethod(getComputeCellMethod())
              .addMethod(getQueryCellMethod())
              .addMethod(getGetNodeInfoMethod())
              .build();
        }
      }
    }
    return result;
  }
}

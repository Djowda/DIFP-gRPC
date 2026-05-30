package com.djowda.difp;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * ============================================================
 *  Service 2 — DifpRegistryService
 *  Implements the §25 Node Registry — maps lobby IDs to nodes.
 *  Exposes the /.well-known/difp/registry/&#42; contract over gRPC.
 * ============================================================
 * </pre>
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class DifpRegistryServiceGrpc {

  private DifpRegistryServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "difp.v04.DifpRegistryService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.djowda.difp.RegistryAnnouncePayload,
      com.djowda.difp.AckResponse> getAnnounceLobbiesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AnnounceLobbies",
      requestType = com.djowda.difp.RegistryAnnouncePayload.class,
      responseType = com.djowda.difp.AckResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.djowda.difp.RegistryAnnouncePayload,
      com.djowda.difp.AckResponse> getAnnounceLobbiesMethod() {
    io.grpc.MethodDescriptor<com.djowda.difp.RegistryAnnouncePayload, com.djowda.difp.AckResponse> getAnnounceLobbiesMethod;
    if ((getAnnounceLobbiesMethod = DifpRegistryServiceGrpc.getAnnounceLobbiesMethod) == null) {
      synchronized (DifpRegistryServiceGrpc.class) {
        if ((getAnnounceLobbiesMethod = DifpRegistryServiceGrpc.getAnnounceLobbiesMethod) == null) {
          DifpRegistryServiceGrpc.getAnnounceLobbiesMethod = getAnnounceLobbiesMethod =
              io.grpc.MethodDescriptor.<com.djowda.difp.RegistryAnnouncePayload, com.djowda.difp.AckResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AnnounceLobbies"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.djowda.difp.RegistryAnnouncePayload.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.djowda.difp.AckResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DifpRegistryServiceMethodDescriptorSupplier("AnnounceLobbies"))
              .build();
        }
      }
    }
    return getAnnounceLobbiesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.djowda.difp.RegistryQueryPayload,
      com.djowda.difp.RegistryResponsePayload> getQueryLobbyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryLobby",
      requestType = com.djowda.difp.RegistryQueryPayload.class,
      responseType = com.djowda.difp.RegistryResponsePayload.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.djowda.difp.RegistryQueryPayload,
      com.djowda.difp.RegistryResponsePayload> getQueryLobbyMethod() {
    io.grpc.MethodDescriptor<com.djowda.difp.RegistryQueryPayload, com.djowda.difp.RegistryResponsePayload> getQueryLobbyMethod;
    if ((getQueryLobbyMethod = DifpRegistryServiceGrpc.getQueryLobbyMethod) == null) {
      synchronized (DifpRegistryServiceGrpc.class) {
        if ((getQueryLobbyMethod = DifpRegistryServiceGrpc.getQueryLobbyMethod) == null) {
          DifpRegistryServiceGrpc.getQueryLobbyMethod = getQueryLobbyMethod =
              io.grpc.MethodDescriptor.<com.djowda.difp.RegistryQueryPayload, com.djowda.difp.RegistryResponsePayload>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QueryLobby"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.djowda.difp.RegistryQueryPayload.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.djowda.difp.RegistryResponsePayload.getDefaultInstance()))
              .setSchemaDescriptor(new DifpRegistryServiceMethodDescriptorSupplier("QueryLobby"))
              .build();
        }
      }
    }
    return getQueryLobbyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.djowda.difp.RegistryQueryPayload,
      com.djowda.difp.RegistryResponsePayload> getQueryLobbiesBatchMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryLobbiesBatch",
      requestType = com.djowda.difp.RegistryQueryPayload.class,
      responseType = com.djowda.difp.RegistryResponsePayload.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.djowda.difp.RegistryQueryPayload,
      com.djowda.difp.RegistryResponsePayload> getQueryLobbiesBatchMethod() {
    io.grpc.MethodDescriptor<com.djowda.difp.RegistryQueryPayload, com.djowda.difp.RegistryResponsePayload> getQueryLobbiesBatchMethod;
    if ((getQueryLobbiesBatchMethod = DifpRegistryServiceGrpc.getQueryLobbiesBatchMethod) == null) {
      synchronized (DifpRegistryServiceGrpc.class) {
        if ((getQueryLobbiesBatchMethod = DifpRegistryServiceGrpc.getQueryLobbiesBatchMethod) == null) {
          DifpRegistryServiceGrpc.getQueryLobbiesBatchMethod = getQueryLobbiesBatchMethod =
              io.grpc.MethodDescriptor.<com.djowda.difp.RegistryQueryPayload, com.djowda.difp.RegistryResponsePayload>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "QueryLobbiesBatch"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.djowda.difp.RegistryQueryPayload.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.djowda.difp.RegistryResponsePayload.getDefaultInstance()))
              .setSchemaDescriptor(new DifpRegistryServiceMethodDescriptorSupplier("QueryLobbiesBatch"))
              .build();
        }
      }
    }
    return getQueryLobbiesBatchMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.djowda.difp.EmptyRequest,
      com.djowda.difp.RegistryPeersResponse> getGetRegistryPeersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetRegistryPeers",
      requestType = com.djowda.difp.EmptyRequest.class,
      responseType = com.djowda.difp.RegistryPeersResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.djowda.difp.EmptyRequest,
      com.djowda.difp.RegistryPeersResponse> getGetRegistryPeersMethod() {
    io.grpc.MethodDescriptor<com.djowda.difp.EmptyRequest, com.djowda.difp.RegistryPeersResponse> getGetRegistryPeersMethod;
    if ((getGetRegistryPeersMethod = DifpRegistryServiceGrpc.getGetRegistryPeersMethod) == null) {
      synchronized (DifpRegistryServiceGrpc.class) {
        if ((getGetRegistryPeersMethod = DifpRegistryServiceGrpc.getGetRegistryPeersMethod) == null) {
          DifpRegistryServiceGrpc.getGetRegistryPeersMethod = getGetRegistryPeersMethod =
              io.grpc.MethodDescriptor.<com.djowda.difp.EmptyRequest, com.djowda.difp.RegistryPeersResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetRegistryPeers"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.djowda.difp.EmptyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.djowda.difp.RegistryPeersResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DifpRegistryServiceMethodDescriptorSupplier("GetRegistryPeers"))
              .build();
        }
      }
    }
    return getGetRegistryPeersMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DifpRegistryServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DifpRegistryServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DifpRegistryServiceStub>() {
        @java.lang.Override
        public DifpRegistryServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DifpRegistryServiceStub(channel, callOptions);
        }
      };
    return DifpRegistryServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static DifpRegistryServiceBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DifpRegistryServiceBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DifpRegistryServiceBlockingV2Stub>() {
        @java.lang.Override
        public DifpRegistryServiceBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DifpRegistryServiceBlockingV2Stub(channel, callOptions);
        }
      };
    return DifpRegistryServiceBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DifpRegistryServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DifpRegistryServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DifpRegistryServiceBlockingStub>() {
        @java.lang.Override
        public DifpRegistryServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DifpRegistryServiceBlockingStub(channel, callOptions);
        }
      };
    return DifpRegistryServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DifpRegistryServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DifpRegistryServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DifpRegistryServiceFutureStub>() {
        @java.lang.Override
        public DifpRegistryServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DifpRegistryServiceFutureStub(channel, callOptions);
        }
      };
    return DifpRegistryServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * ============================================================
   *  Service 2 — DifpRegistryService
   *  Implements the §25 Node Registry — maps lobby IDs to nodes.
   *  Exposes the /.well-known/difp/registry/&#42; contract over gRPC.
   * ============================================================
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Node → Registry: declare lobby coverage (§27.1)
     * </pre>
     */
    default void announceLobbies(com.djowda.difp.RegistryAnnouncePayload request,
        io.grpc.stub.StreamObserver<com.djowda.difp.AckResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAnnounceLobbiesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Client/Node → Registry: who serves lobby X? (§27.2 / §25.3)
     * </pre>
     */
    default void queryLobby(com.djowda.difp.RegistryQueryPayload request,
        io.grpc.stub.StreamObserver<com.djowda.difp.RegistryResponsePayload> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQueryLobbyMethod(), responseObserver);
    }

    /**
     * <pre>
     * Batch lobby lookup (§26.2)
     * </pre>
     */
    default void queryLobbiesBatch(com.djowda.difp.RegistryQueryPayload request,
        io.grpc.stub.StreamObserver<com.djowda.difp.RegistryResponsePayload> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getQueryLobbiesBatchMethod(), responseObserver);
    }

    /**
     * <pre>
     * Federation: known peer registries (§25.3)
     * </pre>
     */
    default void getRegistryPeers(com.djowda.difp.EmptyRequest request,
        io.grpc.stub.StreamObserver<com.djowda.difp.RegistryPeersResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetRegistryPeersMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service DifpRegistryService.
   * <pre>
   * ============================================================
   *  Service 2 — DifpRegistryService
   *  Implements the §25 Node Registry — maps lobby IDs to nodes.
   *  Exposes the /.well-known/difp/registry/&#42; contract over gRPC.
   * ============================================================
   * </pre>
   */
  public static abstract class DifpRegistryServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return DifpRegistryServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service DifpRegistryService.
   * <pre>
   * ============================================================
   *  Service 2 — DifpRegistryService
   *  Implements the §25 Node Registry — maps lobby IDs to nodes.
   *  Exposes the /.well-known/difp/registry/&#42; contract over gRPC.
   * ============================================================
   * </pre>
   */
  public static final class DifpRegistryServiceStub
      extends io.grpc.stub.AbstractAsyncStub<DifpRegistryServiceStub> {
    private DifpRegistryServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DifpRegistryServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DifpRegistryServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Node → Registry: declare lobby coverage (§27.1)
     * </pre>
     */
    public void announceLobbies(com.djowda.difp.RegistryAnnouncePayload request,
        io.grpc.stub.StreamObserver<com.djowda.difp.AckResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAnnounceLobbiesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Client/Node → Registry: who serves lobby X? (§27.2 / §25.3)
     * </pre>
     */
    public void queryLobby(com.djowda.difp.RegistryQueryPayload request,
        io.grpc.stub.StreamObserver<com.djowda.difp.RegistryResponsePayload> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQueryLobbyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Batch lobby lookup (§26.2)
     * </pre>
     */
    public void queryLobbiesBatch(com.djowda.difp.RegistryQueryPayload request,
        io.grpc.stub.StreamObserver<com.djowda.difp.RegistryResponsePayload> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getQueryLobbiesBatchMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Federation: known peer registries (§25.3)
     * </pre>
     */
    public void getRegistryPeers(com.djowda.difp.EmptyRequest request,
        io.grpc.stub.StreamObserver<com.djowda.difp.RegistryPeersResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetRegistryPeersMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service DifpRegistryService.
   * <pre>
   * ============================================================
   *  Service 2 — DifpRegistryService
   *  Implements the §25 Node Registry — maps lobby IDs to nodes.
   *  Exposes the /.well-known/difp/registry/&#42; contract over gRPC.
   * ============================================================
   * </pre>
   */
  public static final class DifpRegistryServiceBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<DifpRegistryServiceBlockingV2Stub> {
    private DifpRegistryServiceBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DifpRegistryServiceBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DifpRegistryServiceBlockingV2Stub(channel, callOptions);
    }

    /**
     * <pre>
     * Node → Registry: declare lobby coverage (§27.1)
     * </pre>
     */
    public com.djowda.difp.AckResponse announceLobbies(com.djowda.difp.RegistryAnnouncePayload request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getAnnounceLobbiesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Client/Node → Registry: who serves lobby X? (§27.2 / §25.3)
     * </pre>
     */
    public com.djowda.difp.RegistryResponsePayload queryLobby(com.djowda.difp.RegistryQueryPayload request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getQueryLobbyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Batch lobby lookup (§26.2)
     * </pre>
     */
    public com.djowda.difp.RegistryResponsePayload queryLobbiesBatch(com.djowda.difp.RegistryQueryPayload request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getQueryLobbiesBatchMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Federation: known peer registries (§25.3)
     * </pre>
     */
    public com.djowda.difp.RegistryPeersResponse getRegistryPeers(com.djowda.difp.EmptyRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getGetRegistryPeersMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service DifpRegistryService.
   * <pre>
   * ============================================================
   *  Service 2 — DifpRegistryService
   *  Implements the §25 Node Registry — maps lobby IDs to nodes.
   *  Exposes the /.well-known/difp/registry/&#42; contract over gRPC.
   * ============================================================
   * </pre>
   */
  public static final class DifpRegistryServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<DifpRegistryServiceBlockingStub> {
    private DifpRegistryServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DifpRegistryServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DifpRegistryServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Node → Registry: declare lobby coverage (§27.1)
     * </pre>
     */
    public com.djowda.difp.AckResponse announceLobbies(com.djowda.difp.RegistryAnnouncePayload request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAnnounceLobbiesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Client/Node → Registry: who serves lobby X? (§27.2 / §25.3)
     * </pre>
     */
    public com.djowda.difp.RegistryResponsePayload queryLobby(com.djowda.difp.RegistryQueryPayload request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQueryLobbyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Batch lobby lookup (§26.2)
     * </pre>
     */
    public com.djowda.difp.RegistryResponsePayload queryLobbiesBatch(com.djowda.difp.RegistryQueryPayload request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getQueryLobbiesBatchMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Federation: known peer registries (§25.3)
     * </pre>
     */
    public com.djowda.difp.RegistryPeersResponse getRegistryPeers(com.djowda.difp.EmptyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetRegistryPeersMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service DifpRegistryService.
   * <pre>
   * ============================================================
   *  Service 2 — DifpRegistryService
   *  Implements the §25 Node Registry — maps lobby IDs to nodes.
   *  Exposes the /.well-known/difp/registry/&#42; contract over gRPC.
   * ============================================================
   * </pre>
   */
  public static final class DifpRegistryServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<DifpRegistryServiceFutureStub> {
    private DifpRegistryServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DifpRegistryServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DifpRegistryServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Node → Registry: declare lobby coverage (§27.1)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.djowda.difp.AckResponse> announceLobbies(
        com.djowda.difp.RegistryAnnouncePayload request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAnnounceLobbiesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Client/Node → Registry: who serves lobby X? (§27.2 / §25.3)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.djowda.difp.RegistryResponsePayload> queryLobby(
        com.djowda.difp.RegistryQueryPayload request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQueryLobbyMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Batch lobby lookup (§26.2)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.djowda.difp.RegistryResponsePayload> queryLobbiesBatch(
        com.djowda.difp.RegistryQueryPayload request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getQueryLobbiesBatchMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Federation: known peer registries (§25.3)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.djowda.difp.RegistryPeersResponse> getRegistryPeers(
        com.djowda.difp.EmptyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetRegistryPeersMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ANNOUNCE_LOBBIES = 0;
  private static final int METHODID_QUERY_LOBBY = 1;
  private static final int METHODID_QUERY_LOBBIES_BATCH = 2;
  private static final int METHODID_GET_REGISTRY_PEERS = 3;

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
        case METHODID_ANNOUNCE_LOBBIES:
          serviceImpl.announceLobbies((com.djowda.difp.RegistryAnnouncePayload) request,
              (io.grpc.stub.StreamObserver<com.djowda.difp.AckResponse>) responseObserver);
          break;
        case METHODID_QUERY_LOBBY:
          serviceImpl.queryLobby((com.djowda.difp.RegistryQueryPayload) request,
              (io.grpc.stub.StreamObserver<com.djowda.difp.RegistryResponsePayload>) responseObserver);
          break;
        case METHODID_QUERY_LOBBIES_BATCH:
          serviceImpl.queryLobbiesBatch((com.djowda.difp.RegistryQueryPayload) request,
              (io.grpc.stub.StreamObserver<com.djowda.difp.RegistryResponsePayload>) responseObserver);
          break;
        case METHODID_GET_REGISTRY_PEERS:
          serviceImpl.getRegistryPeers((com.djowda.difp.EmptyRequest) request,
              (io.grpc.stub.StreamObserver<com.djowda.difp.RegistryPeersResponse>) responseObserver);
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
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getAnnounceLobbiesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.djowda.difp.RegistryAnnouncePayload,
              com.djowda.difp.AckResponse>(
                service, METHODID_ANNOUNCE_LOBBIES)))
        .addMethod(
          getQueryLobbyMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.djowda.difp.RegistryQueryPayload,
              com.djowda.difp.RegistryResponsePayload>(
                service, METHODID_QUERY_LOBBY)))
        .addMethod(
          getQueryLobbiesBatchMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.djowda.difp.RegistryQueryPayload,
              com.djowda.difp.RegistryResponsePayload>(
                service, METHODID_QUERY_LOBBIES_BATCH)))
        .addMethod(
          getGetRegistryPeersMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.djowda.difp.EmptyRequest,
              com.djowda.difp.RegistryPeersResponse>(
                service, METHODID_GET_REGISTRY_PEERS)))
        .build();
  }

  private static abstract class DifpRegistryServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DifpRegistryServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.djowda.difp.DifpProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("DifpRegistryService");
    }
  }

  private static final class DifpRegistryServiceFileDescriptorSupplier
      extends DifpRegistryServiceBaseDescriptorSupplier {
    DifpRegistryServiceFileDescriptorSupplier() {}
  }

  private static final class DifpRegistryServiceMethodDescriptorSupplier
      extends DifpRegistryServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    DifpRegistryServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (DifpRegistryServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DifpRegistryServiceFileDescriptorSupplier())
              .addMethod(getAnnounceLobbiesMethod())
              .addMethod(getQueryLobbyMethod())
              .addMethod(getQueryLobbiesBatchMethod())
              .addMethod(getGetRegistryPeersMethod())
              .build();
        }
      }
    }
    return result;
  }
}

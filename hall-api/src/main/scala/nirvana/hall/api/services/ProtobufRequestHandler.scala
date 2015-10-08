package nirvana.hall.api.services

import nirvana.hall.protocol.sys.CommonProto.{BaseResponse, BaseRequest}

/**
 * handle {@link BaseRequest}
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-05-22
 */
trait ProtobufRequestHandler {
  /**
   * process protobuf request
   * @param protobufRequest request instance
   * @param responseBuilder response builder
   * @return true if handle
   */
  def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder): Boolean
}
trait ProtobufRequestFilter {
  def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean
}

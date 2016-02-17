package nirvana.hall.v70.internal.filter.stamp

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.MsgBase64
import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse, ResponseStatus}
import nirvana.hall.protocol.sys.stamp.QueryPortraitProto.{PortraitInfo, QueryPortraitRequest, QueryPortraitResponse}
import nirvana.hall.v70.jpa.GafisGatherPortrait
import nirvana.hall.v70.services.stamp.GatherPortraitService

/**
 * Created by wangjue on 2015/11/13.
 */
class QueryPortraitRequestFilter (gatherPortraitService : GatherPortraitService)
  extends ProtobufRequestFilter
  with LoggerSupport {

  override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
    if (protobufRequest.hasExtension(QueryPortraitRequest.cmd)) {
      val request = protobufRequest.getExtension(QueryPortraitRequest.cmd)
      val personId = request.getPersonId
      val builder = QueryPortraitResponse.newBuilder()
      val portraits  = GafisGatherPortrait.find_by_personid(personId)
      val b = PortraitInfo.newBuilder()
      if (portraits.size > 0) {
        for (p <- portraits) {
          b.setPersonid(p.personid)
          b.setFgp(p.fgp)
          val byte = p.gatherData.getBytes(129,p.gatherData.length().toInt)
          val data = MsgBase64.toBase64(byte)
          b.setGatherData(data)
          builder.addPortraitInfo(b)
        }
        responseBuilder.setExtension(QueryPortraitResponse.cmd, builder.build())
      } else {
        responseBuilder.setStatus(ResponseStatus.FAIL)
        responseBuilder.setMessage("no data");
      }
      true
    } else {
      handler.handle(protobufRequest, responseBuilder)
    }

  }
}

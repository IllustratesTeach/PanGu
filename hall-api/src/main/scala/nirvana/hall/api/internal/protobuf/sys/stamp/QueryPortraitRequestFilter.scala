package nirvana.hall.api.internal.protobuf.sys.stamp

import java.io.PrintWriter

import monad.support.services.LoggerSupport
import nirvana.hall.api.entities.GafisGatherPortrait
import nirvana.hall.api.internal.{MsgBase64, ScalaUtils}
import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.api.services.stamp.GatherPortraitService
import nirvana.hall.protocol.sys.CommonProto.{ResponseStatus, BaseResponse, BaseRequest}
import nirvana.hall.protocol.sys.stamp.QueryPortraitProto.{PortraitInfo, QueryPortraitResponse, QueryPortraitRequest}
import scalikejdbc._

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
      val portraits : List[GafisGatherPortrait] = GafisGatherPortrait.findAllBy(sqls.eq(GafisGatherPortrait.column.personid,personId))
      val b = PortraitInfo.newBuilder()
      if (portraits.size > 0) {
        for (p <- portraits) {
          b.setPersonid(p.personid.get)
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

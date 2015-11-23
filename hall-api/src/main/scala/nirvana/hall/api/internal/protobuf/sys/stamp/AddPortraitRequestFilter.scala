package nirvana.hall.api.internal.protobuf.sys.stamp

import monad.support.services.LoggerSupport
import nirvana.hall.api.entities.GafisGatherPortrait
import nirvana.hall.api.services.{ProtobufRequestHandler, ProtobufRequestFilter}
import nirvana.hall.api.services.stamp.{GatherPortraitService, GatherPersonService}
import nirvana.hall.protocol.sys.CommonProto.{ResponseStatus, BaseResponse, BaseRequest}
import nirvana.hall.protocol.sys.stamp.SavePortraitProto.{SavePortraitResponse, SavePortraitRequest}
import scalikejdbc._

/**
 * Created by wangjue on 2015/11/13.
 */
class AddPortraitRequestFilter (gatherPortraitService : GatherPortraitService)
  extends ProtobufRequestFilter
  with LoggerSupport{

  override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
    if (protobufRequest.hasExtension(SavePortraitRequest.cmd)) {
      val request = protobufRequest.getExtension(SavePortraitRequest.cmd)
      val builder = SavePortraitResponse.newBuilder()
      val personid = request.getPersonId
      val gatherData = request.getGatherData
      val portrait =  gatherPortraitService.analysisGatherPortrait(personid,gatherData)
      builder.setNext("true")
      responseBuilder.setExtension(SavePortraitResponse.cmd,builder.build())
      /*if (person.personid!=null) {
        ScalaUtils.convertScalaToProtobuf(person,b)
        builder.setNext("true")
        responseBuilder.setExtension(protobufRequest.cmd,builder.build())
      } else {
        responseBuilder.setStatus(ResponseStatus.FAIL)
        responseBuilder.setMessage("no data");
      }*/
      true
    } else {
      handler.handle(protobufRequest, responseBuilder)
    }

  }

}

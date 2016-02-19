package nirvana.hall.v70.internal.filter.stamp

import monad.rpc.protocol.CommandProto.BaseCommand
import monad.support.services.LoggerSupport
import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.protocol.sys.stamp.SavePortraitProto.{SavePortraitRequest, SavePortraitResponse}
import nirvana.hall.v70.services.stamp.GatherPortraitService

/**
 * Created by wangjue on 2015/11/13.
 */
class AddPortraitRequestFilter (gatherPortraitService : GatherPortraitService)
  extends ProtobufRequestFilter
  with LoggerSupport{

  override def handle(protobufRequest: BaseCommand, responseBuilder: BaseCommand.Builder, handler: ProtobufRequestHandler): Boolean = {
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

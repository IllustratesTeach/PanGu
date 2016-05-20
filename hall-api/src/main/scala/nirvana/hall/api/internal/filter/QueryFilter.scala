package nirvana.hall.api.internal.filter

import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{CommandResponse, RpcServerMessageHandler, RpcServerMessageFilter}
import nirvana.hall.api.services.QueryService
import nirvana.hall.protocol.api.HallMatchRelationProto.{MatchRelationGetResponse, MatchRelationGetRequest}
import nirvana.hall.protocol.api.QueryProto.{QueryGetRequest, QueryGetResponse, QuerySendRequest, QuerySendResponse}
import nirvana.hall.protocol.fpt.MatchRelationProto.{MatchRelationTT, MatchRelation}

/**
 * Created by songpeng on 15/12/9.
 */
class QueryFilter(queryService: QueryService) extends RpcServerMessageFilter {

  override def handle(commandRequest: BaseCommand, commandResponse: CommandResponse, handler: RpcServerMessageHandler): Boolean = {
    if (commandRequest.hasExtension(QuerySendRequest.cmd)) {
      val request = commandRequest.getExtension(QuerySendRequest.cmd)
      val response = queryService.sendQuery(request)
      commandResponse.writeMessage(commandRequest, QuerySendResponse.cmd, response)
      true
    } else if (commandRequest.hasExtension(QueryGetRequest.cmd)) {
      val request = commandRequest.getExtension(QueryGetRequest.cmd)
      val response = queryService.getQuery(request)
      commandResponse.writeMessage(commandRequest, QueryGetResponse.cmd, response)
      true
    } else if(commandRequest.hasExtension(MatchRelationGetRequest.cmd)){
      val request = commandRequest.getExtension(MatchRelationGetRequest.cmd)
//      val response = queryService.getMatchRelation(request)
      //TODO
      val baseRelation = MatchRelation.newBuilder()
      val matchSysInfo = baseRelation.getMatchSysInfoBuilder
      matchSysInfo.setMatchUnitCode("100000000000")
      matchSysInfo.setMatchUnitName("上海市公安局")
      matchSysInfo.setMatcher("match")
      matchSysInfo.setMatchDate("20160501")
      matchSysInfo.setRemark("remark")
      matchSysInfo.setInputUnitCode("123456789012")
      matchSysInfo.setInputUnitName("东方金指")
      matchSysInfo.setInputer("sp")
      matchSysInfo.setInputDate("20160501")
      matchSysInfo.setApprover("jcai")
      matchSysInfo.setApproveDate("20160501")
      matchSysInfo.setRecheckUnitCode("20000000000")
      matchSysInfo.setRechecker("复核")
      matchSysInfo.setRecheckDate("20160501")

      val tt = MatchRelationTT.newBuilder()
      tt.setPersonId1(request.getCardId)
      tt.setPersonId2("12345654321")
      baseRelation.setExtension(MatchRelationTT.data, tt.build())
      val response = MatchRelationGetResponse.newBuilder()
      response.setMatchType(request.getMatchType)
      response.addMatchRelation(baseRelation.build())
      commandResponse.writeMessage(commandRequest, MatchRelationGetResponse.cmd, response.build())
      true
    } else {
      handler.handle(commandRequest, commandResponse)
    }
  }

}

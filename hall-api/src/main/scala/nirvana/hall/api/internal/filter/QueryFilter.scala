package nirvana.hall.api.internal.filter

import javax.servlet.http.HttpServletRequest

import monad.rpc.protocol.CommandProto.BaseCommand
import monad.rpc.services.{CommandResponse, RpcServerMessageFilter, RpcServerMessageHandler}
import nirvana.hall.api.{HallApiConstants, HallApiErrorConstants}
import nirvana.hall.api.config.QueryDBConfig
import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.api.services.{QueryService, SyncInfoLogManageService}
import nirvana.hall.protocol.api.QueryProto.{QueryGetRequest, QueryGetResponse, QuerySendRequest, QuerySendResponse}

/**
 * Created by songpeng on 15/12/9.
 */
class QueryFilter(httpServletRequest: HttpServletRequest, queryService: QueryService
                  ,syncInfoLogManageService: SyncInfoLogManageService) extends RpcServerMessageFilter with LoggerSupport{

  override def handle(commandRequest: BaseCommand, commandResponse: CommandResponse, handler: RpcServerMessageHandler): Boolean = {
    if (commandRequest.hasExtension(QuerySendRequest.cmd)) {
      val request = commandRequest.getExtension(QuerySendRequest.cmd)
      val uuid = request.getUuid
      var typ_add=""
      var match_task_orasid=""
      val ip = request.getIp
      try {
        match_task_orasid=request.getMatchTask.getObjectId.toString
        val ip = httpServletRequest.getRemoteAddr
        val queryDBConfig = getQueryDBConfig(httpServletRequest)
        val oraSid = queryService.addMatchTask(request.getMatchTask, queryDBConfig)

        syncInfoLogManageService.recordSyncDataIdentifyLog(uuid,match_task_orasid,HallApiConstants.REMOTE_TYPE_MATCH_TASK+"-PUT",ip,"1","1")
        val response = QuerySendResponse.newBuilder().setOraSid(oraSid)
        commandResponse.writeMessage(commandRequest, QuerySendResponse.cmd, response.build())
        } catch {
          case e:Exception =>
            val eInfo = ExceptionUtil.getStackTraceInfo(e)
            error("Remote_Sync_Type_Match_Task-ResponseData fail,uuid{};match_task_orasid:{};错误堆栈信息:{};错误信息:{}",uuid,match_task_orasid,eInfo,e.getMessage)
            syncInfoLogManageService.recordSyncDataLog(uuid, match_task_orasid, null, eInfo, 2, HallApiErrorConstants.SYNC_RESPONSE_UNKNOWN + HallApiConstants.REMOTE_TYPE_MATCH_TASK)
      }
      true
    } else if (commandRequest.hasExtension(QueryGetRequest.cmd)) {
      val request = commandRequest.getExtension(QueryGetRequest.cmd)
      val uuid = request.getUuid
      var typ_add=""
      var match_task_orasid = ""
      val ip = request.getIp
      try{
        match_task_orasid = request.getOraSid.toString
        val matchResult = queryService.getMatchResult(request.getOraSid)
        val response = QueryGetResponse.newBuilder()
        response.setIsComplete(false)
        matchResult.foreach{result =>
          response.setMatchResult(result)
          response.setIsComplete(true)
        }
        syncInfoLogManageService.recordSyncDataIdentifyLog(uuid, match_task_orasid, HallApiConstants.REMOTE_TYPE_MATCH_RESULT,ip, "1", "1")
        commandResponse.writeMessage(commandRequest, QueryGetResponse.cmd, response.build())
        } catch {
          case e:Exception =>
            val eInfo = ExceptionUtil.getStackTraceInfo(e)
            error("Remote_Sync_Type_Match_Result-ResponseData fail,uuid{};match_task_orasid:{};错误堆栈信息:{};错误信息:{}",uuid,match_task_orasid,eInfo,e.getMessage)
            syncInfoLogManageService.recordSyncDataLog(uuid, match_task_orasid, null, eInfo, 2, HallApiErrorConstants.SYNC_RESPONSE_UNKNOWN + HallApiConstants.REMOTE_TYPE_MATCH_RESULT)
      }
      true
    } else {
      handler.handle(commandRequest, commandResponse)
    }
  }

  /**
   * 获取数据库的配置
   * @param httpServletRequest
   * @return
   */
  private def getQueryDBConfig(httpServletRequest: HttpServletRequest): QueryDBConfig ={
    val destDB = httpServletRequest.getHeader(HallApiConstants.HTTP_HEADER_QUERY_DEST_DBID)
    val srcDB = httpServletRequest.getHeader(HallApiConstants.HTTP_HEADER_QUERY_SRC_DBID)
    val dbid = httpServletRequest.getHeader(HallApiConstants.HTTP_HEADER_DBID)

    QueryDBConfig(
      if(dbid != null) Option(dbid.toShort) else None,
      if(srcDB != null) Option(srcDB.toShort) else None,
      if(destDB != null) Option(destDB.toShort) else None)
  }

}

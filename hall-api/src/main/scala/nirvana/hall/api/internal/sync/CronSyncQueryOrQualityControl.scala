package nirvana.hall.api.internal.sync

import java.nio.ByteOrder
import java.sql.Timestamp
import java.util.{Date, UUID}

import monad.core.services.{CronScheduleWithStartModel, StartAtDelay}
import monad.support.services.LoggerSupport
import nirvana.hall.api.HallApiConstants
import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.api.services.sync.{CronSyncQueryOrQualityService, SyncCronService}
import nirvana.hall.api.services.{QueryService, SyncInfoLogManageService, TPCardService}
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.c.services.kernel.mnt_checker_def.MNTDISPSTRUCT
import nirvana.hall.extractor.jni.NativeExtractor
import nirvana.hall.protocol.api.QueryProto.{QuerySendRequest, QuerySendResponse}
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.support.services.RpcHttpClient
import nirvana.hall.v70.internal.HttpHeaderUtils
import nirvana.hall.v70.internal.adapter.common.jpa.{GafisFingerAudit, GafisNormalqueryQueryque, GafisPerson}
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.internal.txserver.jpa.{SevenReceiveRecord, TaskLog, TaskRelation, TxConfig}
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor

import scala.util.control.Breaks._

/**
  * @author <a href="yuchen_1997_200486@126.com">yuchen</a>.
  * @since 2019/3/4
  */
class CronSyncQueryOrQualityControl(apiConfig: HallApiConfig,
                                    queryService: QueryService,
                                    rpcHttpClient: RpcHttpClient,
                                    tpCardService:TPCardService,
                                    syncInfoLogManageService: SyncInfoLogManageService) extends CronSyncQueryOrQualityService with LoggerSupport{

  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, syncCronService: SyncCronService): Unit = {
    if(apiConfig.sync.syncCron != null){
      periodicExecutor.addJob(new CronScheduleWithStartModel(apiConfig.sync.syncCron , StartAtDelay), "sync-cron", new Runnable {
        override def run(): Unit = {
          info("begin sync-cron")
          try{
            doWork
          }catch{
            case e:Exception =>
              error("CronSyncQueryOrQualityControl error:{}",e.getMessage,e)
          }
          info("end sync-cron")
        }
      })
    }
  }
  /**
    * 定时任务调用方法
    */
  def doWork(): Unit = {
    //获取通讯服务器获取的数据
    val tpRecordList = SevenReceiveRecord.find_by_flag(0)
    tpRecordList.foreach{
      tpRecord =>
        if(inspectQuality(tpRecord)){   //判断指纹质量，如果通过质检返回true
          //发送7.0 TL,TT查询
          val ttQueryId = queryService.sendQueryByCardIdAndMatchType(tpRecord.cardId,MatchType.FINGER_TT)
          val tlQueryId = queryService.sendQueryByCardIdAndMatchType(tpRecord.cardId,MatchType.FINGER_TL)
          val ttGafisQuery = GafisNormalqueryQueryque.find_by_oraSid(ttQueryId).head
          val tlGafisQuery = GafisNormalqueryQueryque.find_by_oraSid(tlQueryId).head

          var uuid = UUID.randomUUID().toString
          var taskId = ""
          try {
            //获取远程查询配置
            val txConfig = TxConfig.find_by_stationCode(tpRecord.stationCode).head
            //TT远程查询
            val ttMatchTask = ProtobufConverter.convertGafisNormalqueryQueryque2MatchTask(ttGafisQuery)
            val ttRequest = QuerySendRequest.newBuilder().setMatchTask(ttMatchTask)
            ttRequest.setUuid(uuid)
            taskId = ttMatchTask.getMatchId
            val ttheaderMap = HttpHeaderUtils.getHeaderMapOfQueryConfig(txConfig.config, ttMatchTask.getMatchType)
            val ttRespnose = rpcHttpClient.call(txConfig.urlSix, QuerySendRequest.cmd, ttRequest.build(), ttheaderMap)
            val ttQuerySendResponse = ttRespnose.getExtension(QuerySendResponse.cmd)
            val ttRemoteQueryId = ttQuerySendResponse.getOraSid   //6.2远程tt查询任务号
            new TaskRelation(UUID.randomUUID().toString,ttRemoteQueryId,ttQueryId,0,new Date(),tpRecord.cardId,0.asInstanceOf[Integer]).save()
            new TaskLog(UUID.randomUUID().toString, taskId, tpRecord.stationCode, ttRemoteQueryId.toInt, ttQueryId.toInt, ttGafisQuery.querytype.toInt, 0, new Date()).save()

            //TL远程查询
            uuid = UUID.randomUUID().toString
            val tlMatchTask = ProtobufConverter.convertGafisNormalqueryQueryque2MatchTask(tlGafisQuery)
            val tlRequest = QuerySendRequest.newBuilder().setMatchTask(tlMatchTask)
            tlRequest.setUuid(uuid)
            taskId = tlMatchTask.getMatchId
            val tlheaderMap = HttpHeaderUtils.getHeaderMapOfQueryConfig(txConfig.config, tlMatchTask.getMatchType)
            val tlRespnose = rpcHttpClient.call(txConfig.urlSix, QuerySendRequest.cmd, tlRequest.build(), tlheaderMap)
            val tlQuerySendResponse = tlRespnose.getExtension(QuerySendResponse.cmd)
            val tlRemoteQueryId = tlQuerySendResponse.getOraSid   //6.2tl远程查询任务号
            new TaskRelation(UUID.randomUUID().toString,tlRemoteQueryId,tlQueryId,0,new Date(),tpRecord.cardId,1.asInstanceOf[Integer]).save()
            new TaskLog(UUID.randomUUID().toString, taskId, tpRecord.stationCode, tlRemoteQueryId.toInt, tlQueryId.toInt, tlGafisQuery.querytype.toInt, 0, new Date()).save()

            val flag:Integer = 1
            SevenReceiveRecord.update.set(flag= flag).where(SevenReceiveRecord.id === tpRecord.id).execute
          }
          catch {
            case e: nirvana.hall.support.internal.CallRpcException =>
              val eInfo = ExceptionUtil.getStackTraceInfo(e)
              error("MatchTask-RequestData fail,uuid:{};taskId:{};错误堆栈信息:{};错误信息:{}",uuid,taskId,eInfo,e.getMessage)
              syncInfoLogManageService.recordSyncDataLog(uuid, taskId, null, eInfo, 2, SyncErrorConstants.SEND_REMOTE_RESPONSE_UNKNOWN + HallApiConstants.REMOTE_TYPE_MATCH_TASK)
            case e: Exception =>
              //发送比对异常，状态更新为失败
              error(e.getMessage, e)
              val eInfo = ExceptionUtil.getStackTraceInfo(e)
              syncInfoLogManageService.recordSyncDataLog(uuid, taskId, "", eInfo, 2, SyncErrorConstants.SEND_REMOTE_TASK_FAIL +
                HallApiConstants.REMOTE_TYPE_MATCH_TASK)
          }
        }else{
          var personInfo = GafisPerson.find(tpRecord.cardId)
          if(null != personInfo){
            personInfo.approval = "2"
            personInfo.gatherFingerNum += 1
            personInfo.gatherTypeId = "8a20fb6666baa8488884bab86f2d0066"
            personInfo.status = "2"
            personInfo.schedule = "2/2"
            personInfo.gatherFingerMode = "0"
            personInfo.save()
          }

        }

    }

  }

  /**
    * 捺印质检方法
    */
  def inspectQuality(tpRecord : SevenReceiveRecord): Boolean = {
    var result = true
    val tpCard = tpCardService.getTPCard(tpRecord.cardId)
    val iter = tpCard.getBlobList.iterator()
    while (iter.hasNext) {
      val blob = iter.next()
      val mntByte =  blob.getStMntBytes.toByteArray
      val fgp = blob.getFgp
      val gAFISIMAGESTRUCT = new GAFISIMAGESTRUCT().fromByteArray(mntByte)
      val mntDisp = gafisMnt2DisMnt(gAFISIMAGESTRUCT)
      if(mntDisp.FeatQlev.bIsPlain.toInt == 0){   //滚指指纹特征质检
        if(mntDisp.FeatQlev.RpQlev.toInt < 50){
          result = false
          //记录gafis_finger_audit表
          val gafisFingerAudit = GafisFingerAudit.find_by_personId_and_fgp_and_fgpCase(tpCard.getStrPersonID, fgp.toString, "0").orderBy(GafisFingerAudit.num[java.lang.Integer].desc).headOption
          var num :Integer = 0
          if(gafisFingerAudit != None && gafisFingerAudit!=null)  num = gafisFingerAudit.get.num
          new GafisFingerAudit(UUID.randomUUID().toString.replaceAll("-", ""), tpCard.getStrPersonID, fgp.toString, "0", num+1, "3", "", "", new Timestamp(System.currentTimeMillis()))
        }
      }
    }
    result
  }

  /**
    * gafis特征转换为显示特征
    * @return
    */
  private def gafisMnt2DisMnt(gafisMnt: GAFISIMAGESTRUCT):MNTDISPSTRUCT = {
    val mntDispBytes = (new MNTDISPSTRUCT).toByteArray(byteOrder=ByteOrder.LITTLE_ENDIAN)
    NativeExtractor.GAFIS_MntStdToMntDisp(gafisMnt.bnData, mntDispBytes, 1)//1???

    val mntDisp = new MNTDISPSTRUCT().fromByteArray(mntDispBytes, byteOrder=ByteOrder.LITTLE_ENDIAN)
    mntDisp
  }

}

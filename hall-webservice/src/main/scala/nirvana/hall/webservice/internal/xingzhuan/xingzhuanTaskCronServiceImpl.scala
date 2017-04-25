package nirvana.hall.webservice.internal.xingzhuan

import java.io.{File, FileInputStream}
import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.api.services._
import nirvana.hall.api.webservice.services.xingzhuan.xingzhuanTaskCronService
import nirvana.hall.api.webservice.util.{AFISConstant, FPTConvertToProtoBuffer}
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPTFile
import org.apache.commons.io.{FileUtils, IOUtils}
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}

/**
  * Created by yuchen on 2017/4/20.
  */
class xingzhuanTaskCronServiceImpl(hallApiConfig: HallApiConfig,
                                   tPCardService: TPCardService,
                                   lPCardService: LPCardService,
                                   caseInfoService: CaseInfoService,
                                   queryService: QueryService,
                                   assistCheckRecordService: AssistCheckRecordService,
                                   implicit val dataSource: DataSource)extends xingzhuanTaskCronService with LoggerSupport{

  /**
    * 定时器，获取比对任务
    * @param periodicExecutor
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if(hallApiConfig.sync.syncCron != null){
      periodicExecutor.addJob(new CronSchedule(hallApiConfig.sync.syncCron), "sync-cron", new Runnable {
        override def run(): Unit = {
          info("sync-cron-getTask started")
          try{
            doWork
          }catch{
            case e:Exception =>
              error(ExceptionUtil.getStackTraceInfo(e))
          }
          info("sync-cron-getTask end")
        }
      })
    }
  }

  /**
    * 定时任务调用方法
    */
  override def doWork(): Unit = {
    val fileList = FileUtils.listFiles(new File("url"),Array[String]("fpt", "FPT", "fptt"), true)
    val it = fileList.iterator()
    while(it.hasNext){
      val file = it.next
      val is = new FileInputStream(it.next)
      val result = FPTFile.parseFromInputStream(is,AncientConstants.GBK_ENCODING)
      IOUtils.closeQuietly(is)
      val bytes = result match{
        case Left(fpt3)=>
            throw new Exception("Not support FPT V3.0")
        case Right(fpt4)=>
          val taskId = fpt4.sid
          var oraSid = 0L
            if(fpt4.logic02Recs.length>0){
            fpt4.logic02Recs.foreach{ sLogic02Rec =>
              val tpCard = FPTConvertToProtoBuffer.TPFPT2ProtoBuffer(sLogic02Rec, hallApiConfig.hallImageUrl)
              tPCardService.addTPCard(tpCard)
            }
            val matchTask = FPTConvertToProtoBuffer.FPT2MatchTaskProtoBuffer(fpt4)
            oraSid = queryService.sendQuery(matchTask)
            assistCheckRecordService.recordAssistCheck(taskId,oraSid.toString,"",matchTask.getMatchId,AFISConstant.XINGZHUAN)
            return
          }else if(fpt4.logic03Recs.length>0){
            val lPCard = FPTConvertToProtoBuffer.FPT2LPProtoBuffer(fpt4)
            val caseInfo = FPTConvertToProtoBuffer.FPT2CaseProtoBuffer(fpt4)
            val matchTask = FPTConvertToProtoBuffer.FPT2MatchTaskCaseProtoBuffer(fpt4)
            lPCardService.addLPCard(lPCard)
            caseInfoService.addCaseInfo(caseInfo)
              oraSid = queryService.sendQuery(matchTask)
            assistCheckRecordService.recordAssistCheck(taskId,oraSid.toString,caseInfo.getStrCaseID,"",AFISConstant.XINGZHUAN)
            return
          }else{
            info("success:xingzhuanTaskCronServiceImpl:返回空FPT文件")
            return
          }
      }
      FileUtils.writeByteArrayToFile(new File(file.getAbsolutePath+".converted"),bytes)
    }
  }
}

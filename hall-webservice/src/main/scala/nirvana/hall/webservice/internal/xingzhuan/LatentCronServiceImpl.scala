package nirvana.hall.webservice.internal.xingzhuan

import java.util.UUID
import javax.sql.DataSource

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.api.services.LPCardService
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.bjwcsy.WsFingerService
import nirvana.hall.webservice.services.xingzhuan.{FetchLPCardExportService, LatentCronService}
import nirvana.hall.webservice.util.xingzhuan.FPTUtil
import nirvana.hall.webservice.{HallWebserviceConstants, Upload}
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}

/**
  * Created by sjr on 2017/5/20.
  */
class LatentCronServiceImpl(implicit dataSource: DataSource,
                            hallWebserviceConfig: HallWebserviceConfig,
                            hallV62Config:HallV62Config,
                            fetchLPCardService_Report:FetchLPCardExportService,
                            lPCardService: LPCardService,
                            wsFingerService: WsFingerService
                                       ) extends LatentCronService with LoggerSupport {

  final val Report_BATCH_SIZE = 10

  final val Report_DBID = Some(hallV62Config.caseTable.dbId.toString)

  final val Report_TYPE = "Latent"

  /**
    * 定时器，获取比对任务
    *
    * @param periodicExecutor
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if (hallWebserviceConfig.union4pfmip.cron != null) {
      periodicExecutor.addJob(new CronSchedule(hallWebserviceConfig.union4pfmip.cron), "report-latent", new Runnable {
        override def run(): Unit = {
          info("report-latent started")
          try {
            doWork()
          } catch {
            case e: Exception =>
              error(ExceptionUtil.getStackTraceInfo(e))
          }
          info("report-latent end")
        }
      })
    }
  }

  /**
    * 上报本地数据
    */
  override def doWork(): Unit = {
    val cardIdList = fetchLPCardService_Report.fetchCardIdListBySize_AssistCheck(Report_BATCH_SIZE, Report_DBID)
    cardIdList.foreach { cardId =>
        try {
            val dataHandler = wsFingerService.getLatentFinger("", "", cardId)
            val path = FPTUtil.saveFPTPath(HallWebserviceConstants.LocalLatent, dataHandler.getInputStream, cardId, Upload.UPLOAD_STATUS_CREATED, hallWebserviceConfig, dataSource)
            fetchLPCardService_Report.saveResult(UUID.randomUUID().toString.replace("-", ""), cardId, FPTUtil.Latent, Upload.UPLOAD_STATUS_CREATED, "", path)
        }catch {
        case e: Exception =>
          error("Latent export error:" + ExceptionUtil.getStackTraceInfo(e))
          fetchLPCardService_Report.saveResult(UUID.randomUUID().toString.replace("-",""),cardId,FPTUtil.Latent,-1,"","")
      }
    }
  }
}

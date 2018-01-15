package nirvana.hall.webservice.internal.survey.gafis62

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.webservice.config.HallWebserviceConfig
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}

/**
  * Created by songpeng on 2017/12/24.
  * 现场勘验系统接口定时服务
  */
class FPT50HandprintServiceCron(hallWebserviceConfig: HallWebserviceConfig,
                                fPT50HandprintServiceClient: FPT50HandprintServiceClient, fPT5Service: FPT5Service) extends LoggerSupport{

  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if(hallWebserviceConfig.handprintService.cron!= null){
      periodicExecutor.addJob(new CronSchedule(hallWebserviceConfig.handprintService.cron), "sync-cron", new Runnable {
        override def run(): Unit = {
          try {
            info("begin FPT50HandprintServiceCron ")
            doWork
            info("end FPT50HandprintServiceCron")
          } catch {
            case e: Exception =>
          }
        }
      })
    }
  }

  def doWork: Unit ={
    //TODO 添加日志，异常判断和处理，完善业务逻辑,目前是完全理想状态下的代码逻辑
    //获取系统时间
    val systemDateTime = fPT50HandprintServiceClient.getSystemDateTime()
    info("系统时间{}",systemDateTime)
    //获取待发送现场指掌纹数量
    val latentCount = fPT50HandprintServiceClient.getLatentCount(null, "A", null, null)
    if(latentCount.toInt > 0){
      //获取待发送现场指掌纹列表
      val fingerPrintListResponse = fPT50HandprintServiceClient.getLatentList(null, "A", null, null, null, 1, 10)
      fingerPrintListResponse.list.foreach{k=>
        //根据现堪编号	获取现场指掌纹信息
        val latentPackage = fPT50HandprintServiceClient.getLatentPackage(k.xcwzbh)
        //保存数据
        fPT5Service.addLatentPackage(latentPackage)
        //更新状态
        fPT50HandprintServiceClient.sendFBUseCondition(k.xcwzbh, FPT50HandprintServiceConstants.RESULT_TYPE_ADD)
      }

    }


  }

}

package nirvana.hall.webservice.internal.xingzhuan

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.services.TaskHandlerService
import nirvana.hall.webservice.services.xingzhuan.xingzhuanTaskCronService
import nirvana.hall.webservice.util.{AFISConstant, WebServicesClient_AssistCheck}
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}

import scala.util.{Failure, Success, Try}

/**
  * Created by yuchen on 2017/4/20.
  */
class xingzhuanTaskCronServiceImpl(hallWebserviceConfig: HallWebserviceConfig,
                                   taskHandlerService:TaskHandlerService) extends xingzhuanTaskCronService with LoggerSupport{



  val url = hallWebserviceConfig.union4pfmip.url
  val targetNamespace = hallWebserviceConfig.union4pfmip.targetNamespace
  val opAddEntryArgs: Array[AnyRef] = new Array[AnyRef](2)
  opAddEntryArgs(0) = hallWebserviceConfig.union4pfmip.user
  opAddEntryArgs(1) = hallWebserviceConfig.union4pfmip.password
  /**
    * 定时器，获取比对任务
    * @param periodicExecutor
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if(hallWebserviceConfig.union4pfmip.cron != null){
      periodicExecutor.addJob(new CronSchedule(hallWebserviceConfig.union4pfmip.cron), "sync-cron", new Runnable {
        override def run(): Unit = {
          info("sync-cron-getTask started")
          try{
            callGetXCTask
            callGetTenprintRecord
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
    * 调用公安部刑专webservice--getXCTask方法，获得外省协查任务
    * 现场案件
    */
  override def callGetXCTask(): Unit = {
    val methodName = "getXCTask"
    Try{
      val dataHandler = WebServicesClient_AssistCheck.callHallWebServiceTypeOfDataHandler(url,targetNamespace,opAddEntryArgs,methodName)
      if(null != dataHandler){
        taskHandlerService.queryTaskHandler(dataHandler,AFISConstant.XINGZHUAN)
      }
    }match {
      case Success(r) => info("call success:methodName:{};Time:{}",methodName,AFISConstant.SERVER_TIME)
      case Failure(ex) => error("call fail:methodName:{};message:{};Time:{}",methodName,s"${ex.getMessage}",AFISConstant.SERVER_TIME)
    }
  }

  /**
    * 调用公安部刑专webservice--getTenprintRecord方法，获得外省追逃任务
    * 十指捺印
    */
  override def callGetTenprintRecord(): Unit = {

    val methodName = "getTenprintRecord"
    Try{
      val dataHandler = WebServicesClient_AssistCheck.callHallWebServiceTypeOfDataHandler(url,targetNamespace,opAddEntryArgs,methodName)
      if(null != dataHandler){
        taskHandlerService.queryTaskHandler(dataHandler,AFISConstant.XINGZHUAN)
      }
    }match{
      case Success(r) => info("call success:methodName:{};Time:{}",methodName,AFISConstant.SERVER_TIME)
      case Failure(ex) => error("call fail:methodName:{};message:{};Time:{}",methodName,s"${ex.getMessage}",AFISConstant.SERVER_TIME)
    }

  }
}

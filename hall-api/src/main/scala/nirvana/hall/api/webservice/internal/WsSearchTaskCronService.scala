package nirvana.hall.api.webservice.internal

import monad.support.services.LoggerSupport
import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.services.{CaseInfoService, LPCardService, QueryService, TPCardService}
import nirvana.hall.api.webservice.services.WsSearchTaskService
import nirvana.hall.c.services.gfpt4lib.FPTFile
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import stark.webservice.services.StarkWebServiceClient

/**
  * 互查系统定时任务
  */
class WsSearchTaskCronService(hallApiConfig: HallApiConfig,
                              tPCardService: TPCardService,
                              lPCardService: LPCardService,
                              caseInfoService: CaseInfoService,
                              queryService: QueryService) extends LoggerSupport{

  val cronSchedule = "0 0/5 * * * ? *" //TODO 可配置
  /**
    * 定时器，获取比对任务
    * @param periodicExecutor
    */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
      periodicExecutor.addJob(new CronSchedule(cronSchedule), "SearchTaskWsService-cron", new Runnable {
        override def run(): Unit = {
          //发送请求获取任务
          val url = "http://127.0.0.1:8080/ws/WsSearchTaskService?wsdl"
          val targetNamespace = "http://www.egfit.cn/"
          val userid = ""
          val password = ""

          val searchTaskService = StarkWebServiceClient.createClient(classOf[WsSearchTaskService], url, targetNamespace)
          val taskDataHandler = searchTaskService.getSearchTask(userid, password)
          val taskFpt = FPTFile.parseFromInputStream(taskDataHandler.getInputStream)

          //解析比对任务FPT
          val taskControlID = taskFpt.right.get.sid
          //图像解压
          //提取特征
          //数据入库
          //发送查询任务
          //发送更新比对任务状态请求, TODO 如果以上流程失败需要捕获异常，发送失败请求
          searchTaskService.setSearchTaskStatus(userid, password, taskControlID, true)

        }
      })
  }
}

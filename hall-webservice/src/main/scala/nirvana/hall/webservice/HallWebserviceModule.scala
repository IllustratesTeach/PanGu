package nirvana.hall.webservice

import monad.core.MonadCoreSymbols
import monad.core.internal.MonadConfigFileUtils
import monad.support.services.XmlLoader
import nirvana.hall.api.internal.fpt.FPTServiceImpl
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.internal.TaskHandlerServiceImpl
import nirvana.hall.webservice.internal.bjwcsy.{SendMatchResultService, Union4pfmipCronService, WsFingerServiceImpl}
import nirvana.hall.webservice.internal.xingzhuan.{SendCheckinServiceImpl, UploadCheckinServiceImpl, xingzhuanTaskCronServiceImpl}
import nirvana.hall.webservice.services.TaskHandlerService
import nirvana.hall.webservice.services.bjwcsy.WsFingerService
import nirvana.hall.webservice.services.xingzhuan.{SendCheckinService, UploadCheckinService, xingzhuanTaskCronService}
import org.apache.tapestry5.ioc.ServiceBinder
import org.apache.tapestry5.ioc.annotations.Symbol

/**
  * Created by songpeng on 2017/4/24.
  */
object HallWebserviceModule {
  def buildHallWebserviceConfig(@Symbol(MonadCoreSymbols.SERVER_HOME) serverHome: String) = {
    val content = MonadConfigFileUtils.readConfigFileContent(serverHome, "hall-webservice.xml")
    XmlLoader.parseXML[HallWebserviceConfig](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/webservice/webservice.xsd")))
  }

  def bind(binder: ServiceBinder) {
    binder.bind(classOf[TaskHandlerService],classOf[TaskHandlerServiceImpl])
    binder.bind(classOf[FPTService], classOf[FPTServiceImpl])
    binder.bind(classOf[WsFingerService], classOf[WsFingerServiceImpl]).withSimpleId()
//    binder.bind(classOf[Union4pfmipCronService], classOf[Union4pfmipCronService]).eagerLoad()
//    binder.bind(classOf[SendMatchResultService], classOf[SendMatchResultService]).eagerLoad()
    binder.bind(classOf[xingzhuanTaskCronService],classOf[xingzhuanTaskCronServiceImpl]).eagerLoad()
    binder.bind(classOf[SendCheckinService], classOf[SendCheckinServiceImpl]).eagerLoad()
    binder.bind(classOf[UploadCheckinService], classOf[UploadCheckinServiceImpl]).eagerLoad()


  }
}

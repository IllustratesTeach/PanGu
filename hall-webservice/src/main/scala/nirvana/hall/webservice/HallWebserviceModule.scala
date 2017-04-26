package nirvana.hall.webservice

import monad.core.MonadCoreSymbols
import monad.core.internal.MonadConfigFileUtils
import monad.support.services.XmlLoader
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.internal.TaskHandlerServiceImpl
import nirvana.hall.webservice.internal.bjwcsy.{SendMatchResultService, Union4pfmipCronService, WsFingerServiceImpl}
import nirvana.hall.webservice.internal.xingzhuan.xingzhuanTaskCronServiceImpl
import nirvana.hall.webservice.services.TaskHandlerService
import nirvana.hall.webservice.services.bjwcsy.WsFingerService
import nirvana.hall.webservice.services.xingzhuan.xingzhuanTaskCronService
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
    binder.bind(classOf[WsFingerService], classOf[WsFingerServiceImpl]).withSimpleId()
    binder.bind(classOf[Union4pfmipCronService], classOf[Union4pfmipCronService]).eagerLoad()
    binder.bind(classOf[SendMatchResultService], classOf[SendMatchResultService]).eagerLoad()
    binder.bind(classOf[xingzhuanTaskCronService],classOf[xingzhuanTaskCronServiceImpl]).eagerLoad()

  }
}

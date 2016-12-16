package nirvana.hall.v62

import nirvana.hall.v62.fingerInfoInteractive.liaoning.{DataServiceImpl, FingerInfoServiceImpl}
import nirvana.hall.v62.fingerInfoInteractive.liaoning.internal.LiaoNingInteractiveCron
import nirvana.hall.v62.fingerInfoInteractive.liaoning.services.{DataService, FingerInfoService}
import org.apache.tapestry5.ioc.ServiceBinder

/**
  * Created by yuchen on 2016/12/16.
  */
object LocalV62LiaoNingServiceModule {
  def bind(binder: ServiceBinder): Unit = {

    binder.bind(classOf[LiaoNingInteractiveCron]).eagerLoad()
    binder.bind(classOf[FingerInfoService],classOf[FingerInfoServiceImpl])
    binder.bind(classOf[DataService],classOf[DataServiceImpl])
  }
}

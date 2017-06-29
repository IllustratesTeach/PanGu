package nirvana.hall.v62

import nirvana.hall.v62.fingerInfoInteractive.shanghai.DataServiceImpl
import nirvana.hall.v62.fingerInfoInteractive.shanghai.internal.ShangHaiInteractiveCron
import nirvana.hall.v62.fingerInfoInteractive.shanghai.services.DataService
import org.apache.tapestry5.ioc.ServiceBinder
/**
  * Created by ssj on 2017/03/09.
  */
object LocalV62ShangHaiServiceModule {
  def bind(binder: ServiceBinder): Unit = {

    binder.bind(classOf[ShangHaiInteractiveCron]).eagerLoad()
    binder.bind(classOf[DataService],classOf[DataServiceImpl])
  }
}
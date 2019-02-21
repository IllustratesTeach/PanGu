package nirvana.hall.webservice

import nirvana.hall.webservice.internal.colligategather.sync.SyncCornGetTemplateFingerPackage
import org.apache.tapestry5.ioc.ServiceBinder

/**
  * @author <a href="yuchen_1997_200486@126.com">yuchen</a>.
  * @since 2018/12/27
  */
object HallWebserviceShanDongZongCaiModule {

  def bind(serviceBinder: ServiceBinder):Unit = {
    serviceBinder.bind(classOf[SyncCornGetTemplateFingerPackage]).eagerLoad()
  }
}

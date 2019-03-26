package nirvana.hall.webservice

import nirvana.hall.webservice.internal.greathand.SyncCronFingerDataService
import org.apache.tapestry5.ioc.{Configuration, ServiceBinder}


object HallWebserviceGreatHandTechModule {

  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[SyncCronFingerDataService]).eagerLoad()
  }

  def contributeEntityManagerFactory(configuration: Configuration[String]): Unit = {
    configuration.add("nirvana.hall.webservice.internal.greathand.jpa")
  }
}
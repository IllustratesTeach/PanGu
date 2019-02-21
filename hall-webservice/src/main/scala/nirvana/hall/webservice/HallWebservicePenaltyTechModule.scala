package nirvana.hall.webservice

import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.internal.penaltytech.V62ServiceSupport
import nirvana.hall.webservice.internal.penaltytech.sync._
import org.apache.tapestry5.ioc.{Configuration, ServiceBinder}

/**
  * Created by liukai on 2018/12/10.
  */
object HallWebservicePenaltyTechModule {

  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[SyncCronLatentFingerService]).eagerLoad()
    //binder.bind(classOf[SyncCronLLHitService]).eagerLoad()
    //binder.bind(classOf[SyncCronLTHitService]).eagerLoad()
    //binder.bind(classOf[SyncCronTemplateFingerService]).eagerLoad()
    //binder.bind(classOf[SyncCronTLHitService]).eagerLoad()
    //binder.bind(classOf[SyncCronTTHitService]).eagerLoad()
    binder.bind(classOf[V62ServiceSupport])//.eagerLoad()

  }

  def contributeEntityManagerFactory(configuration: Configuration[String]): Unit = {
    configuration.add("nirvana.hall.webservice.internal.penaltytech.jpa")
  }
}
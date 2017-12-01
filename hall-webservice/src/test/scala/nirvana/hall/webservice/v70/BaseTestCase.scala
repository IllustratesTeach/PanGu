package nirvana.hall.webservice.v70

import javax.persistence.EntityManagerFactory

import monad.support.services.XmlLoader
import nirvana.hall.api.internal.FeatureExtractorImpl
import nirvana.hall.api.internal.fpt.FPTServiceImpl
import nirvana.hall.api.internal.remote._
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.api.services.remote._
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.image.internal.{FirmDecoderImpl, ImageEncoderImpl}
import nirvana.hall.image.services.{FirmDecoder, ImageEncoder}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v70.config.HallV70Config
import nirvana.hall.webservice.config.HallWebserviceConfig
import nirvana.hall.webservice.internal.haixin.{StrategyServiceImpl, WsHaiXinFingerServiceImpl}
import nirvana.hall.webservice.services.haixin.{StrategyService, WsHaiXinFingerService}
import org.apache.tapestry5.ioc.{Configuration, Registry, RegistryBuilder, ServiceBinder}
import org.junit.{After, Before}
import org.springframework.orm.jpa.{EntityManagerFactoryUtils, EntityManagerHolder}
import org.springframework.transaction.support.TransactionSynchronizationManager

import scala.io.Source
import scala.reflect._

/**
  * Created by songpeng on 2017/4/25.
  */
class BaseTestCase {
  private var registry:Registry = _
  protected def getService[T:ClassTag]:T={
    registry.getService(classTag[T].runtimeClass.asInstanceOf[Class[T]])
  }
  @Before
  def setup: Unit ={
    val modules = Seq[String](
      //v62
//      "nirvana.hall.v62.LocalV62ServiceModule",
//      "nirvana.hall.v62.LocalV62DataSourceModule",
//      "nirvana.hall.webservice.TestV62Module",
      //v70
      "nirvana.hall.v70.LocalV70ServiceModule",
      "nirvana.hall.v70.LocalDataSourceModule",
      "nirvana.hall.webservice.TestV70Module",

      "monad.rpc.LocalRpcModule",
      "nirvana.hall.api.LocalProtobufModule",
      "nirvana.hall.webservice.TestWebserviceModule",
      "stark.activerecord.StarkActiveRecordModule"
    ).map(Class.forName)
    registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)
    val entityManagerFactory= getService[EntityManagerFactory]
    val emHolder= new EntityManagerHolder(entityManagerFactory.createEntityManager())
    TransactionSynchronizationManager.bindResource(entityManagerFactory, emHolder)
  }
  @After
  def down: Unit ={
    val emf: EntityManagerFactory = registry.getService(classOf[EntityManagerFactory])
    val emHolder: EntityManagerHolder = TransactionSynchronizationManager.unbindResource(emf).asInstanceOf[EntityManagerHolder]
    EntityManagerFactoryUtils.closeEntityManager(emHolder.getEntityManager)

    registry.shutdown()
  }
}
object TestWebserviceModule{
  def buildHallWebserviceConfig = {
    val content = Source.fromInputStream(getClass.getResourceAsStream("/test-webservice.xml"),"utf8").mkString
    XmlLoader.parseXML[HallWebserviceConfig](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/webservice/webservice.xsd")))
  }
  def bind(binder: ServiceBinder): Unit = {
    //
    binder.bind(classOf[CaseInfoRemoteService], classOf[CaseInfoRemoteServiceImpl])
    binder.bind(classOf[LPPalmRemoteService], classOf[LPPalmRemoteServiceImpl])
    binder.bind(classOf[LPCardRemoteService], classOf[LPCardRemoteServiceImpl])
    binder.bind(classOf[TPCardRemoteService], classOf[TPCardRemoteServiceImpl])
    binder.bind(classOf[QueryRemoteService], classOf[QueryRemoteServiceImpl])
    binder.bind(classOf[FeatureExtractor], classOf[FeatureExtractorImpl])
    binder.bind(classOf[FirmDecoder],classOf[FirmDecoderImpl]).withId("FirmDecoder")
    binder.bind(classOf[ImageEncoder],classOf[ImageEncoderImpl]).withId("ImageEncoder")
    binder.bind(classOf[HallImageRemoteService], classOf[HallImageRemoteServiceImpl])
    binder.bind(classOf[FPTService], classOf[FPTServiceImpl])
    binder.bind(classOf[StrategyService], classOf[StrategyServiceImpl])
    binder.bind(classOf[WsHaiXinFingerService], classOf[WsHaiXinFingerServiceImpl]).withSimpleId()
    //=======刑专测试时，加载============//
//    binder.bind(classOf[FetchFPTService],classOf[FetchFPTServiceImpl])
//    binder.bind(classOf[TenPrinterExportService],classOf[TenPrinterExportServiceImpl])
//    binder.bind(classOf[SendQueryService],classOf[SendQueryServiceImpl])
//    binder.bind(classOf[LocalCheckinService],classOf[LocalCheckinServiceImpl])
  }
}
object TestV62Module{
  def buildHallV62Config={
    val content = Source.fromInputStream(getClass.getResourceAsStream("/test-v62.xml"),"utf8").mkString
    XmlLoader.parseXML[HallV62Config](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/v62/v62.xsd")))
  }
  def contributeEntityManagerFactory(configuration:Configuration[String]): Unit ={
    configuration.add("nirvana.hall.api.jpa")
  }
}
object TestV70Module{
  def buildHallV70Config={
    val content = Source.fromInputStream(getClass.getResourceAsStream("/test-v70.xml"),"utf8").mkString
    XmlLoader.parseXML[HallV70Config](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/v70/v70.xsd")))
  }
  def contributeEntityManagerFactory(configuration:Configuration[String]): Unit ={
    configuration.add("nirvana.hall.v70.jpa")
    configuration.add("nirvana.hall.api.jpa")
  }
}

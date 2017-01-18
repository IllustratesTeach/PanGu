package nirvana.hall.api.app

import java.io.{File, FileOutputStream}
import javax.persistence.EntityManagerFactory

import com.google.protobuf.ExtensionRegistry
import monad.core.MonadCoreSymbols
import monad.core.internal.MonadConfigFileUtils
import monad.core.services.{BootstrapTextSupport, GlobalLoggerConfigurationSupport}
import monad.rpc.services.ProtobufExtensionRegistryConfiger
import monad.support.services.{JettyServerSupport, SystemEnvDetectorSupport, XmlLoader}
import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.internal.AuthServiceImpl
import nirvana.hall.api.services.AuthService
import nirvana.hall.api.webservice.services.WsFingerService
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v70.config.HallV70Config
import org.apache.tapestry5.ioc.annotations.{EagerLoad, Symbol}
import org.apache.tapestry5.ioc.{Configuration, Registry, RegistryBuilder, ServiceBinder}
import org.slf4j.LoggerFactory
import org.springframework.orm.jpa.{EntityManagerFactoryUtils, EntityManagerHolder}
import org.springframework.transaction.support.TransactionSynchronizationManager

import scala.io.Source
import scala.reflect._

/**
  * Created by songpeng on 2017/1/14.
  * fpt 批量导出小程序
  * 1.将需要导出的卡号列表写入到config/fpt_export_cardid_list.txt
  * 2.配置config/hall-v70.xml或config/hall-v62.xml
  * 3.加载so文件（ldconfig），windows系统将dll放到环境变量目录下
  * 4.启动程序 bin/hall-api start
  * 导出的fpt数据会在fpt目录下
  */
object FPTExportApp extends JettyServerSupport
  with GlobalLoggerConfigurationSupport
  with SystemEnvDetectorSupport
  with BootstrapTextSupport {

  private var registry:Registry = _
  protected def getService[T:ClassTag]:T={
    registry.getService(classTag[T].runtimeClass.asInstanceOf[Class[T]])
  }
  def main(args: Array[String]): Unit = {
    //TODO 支持v62, 支持案件导出，支持比中关系导出, 支持文件输入路径参数和fpt输出路径参数
    val file = new File("support/config/fpt_export_cardid_list.txt")
    if(!file.exists()){
      println("fpt_export_cardid_list.txt not exist")
      return
    }

    val serverHome = System.getProperty(MonadCoreSymbols.SERVER_HOME, "support")
    System.setProperty(MonadCoreSymbols.SERVER_HOME, serverHome)
    val logger = LoggerFactory getLogger getClass
    logger.info("starting export fpt server ....")
    //加载jni
    loadJni()

    setup

    try{
      //fpt导出目录，如果没有则创建
      val filePath = new File("support/fpt")
      if(!filePath.exists()){
        filePath.mkdir()
      }
      val service = getService[WsFingerService]
      //读取文件信息
      val source = Source.fromFile(file)
      val iter = source.getLines()
      for(cardId <- iter){
        logger.info("export fpt cardId: "+ cardId)
        val fptDataHandle = service.getTenprintFinger("", "", cardId.toString)
        //fpt数据写入文件
        fptDataHandle.writeTo(new FileOutputStream("support/fpt/%s.fpt".format(cardId)))
      }
      source.close()

      //文件重命名
      file.renameTo(new File("support/config/fpt_export_cardid_list_sucess.txt"))

    }finally {
      down
    }

  }

  //启动registry
  def setup: Unit ={
    //v70
    val modules = Seq[String](
      "stark.activerecord.StarkActiveRecordModule",
      "nirvana.hall.v70.LocalV70ServiceModule",
      "nirvana.hall.v70.LocalDataSourceModule",
      "nirvana.hall.api.LocalProtobufModule",
      "nirvana.hall.api.LocalApiWebServiceModule",
      "nirvana.hall.api.app.FPTExportV70Module"
    ).map(Class.forName)
    //v62
/*    val modules = Seq[String](
      "stark.activerecord.StarkActiveRecordModule",
      "nirvana.hall.api.LocalProtobufModule",
      "nirvana.hall.v62.LocalV62ServiceModule",
      "nirvana.hall.v62.LocalV62DataSourceModule",
      "nirvana.hall.api.LocalApiWebServiceModule",
      "nirvana.hall.api.app.FPTExportV62Module"
    ).map(Class.forName)*/
    registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)
    //OpenSession In Thread
    val entityManagerFactory= getService[EntityManagerFactory]
    val emHolder= new EntityManagerHolder(entityManagerFactory.createEntityManager())
    TransactionSynchronizationManager.bindResource(entityManagerFactory, emHolder)
  }
  //关闭registry
  def down: Unit ={
    val emf: EntityManagerFactory = registry.getService(classOf[EntityManagerFactory])
    val emHolder: EntityManagerHolder = TransactionSynchronizationManager.unbindResource(emf).asInstanceOf[EntityManagerHolder]
    EntityManagerFactoryUtils.closeEntityManager(emHolder.getEntityManager)
    registry.shutdown()
  }

  //加载jni
  def loadJni() {
    val file = new File("support")
    if (file.exists()){
      nirvana.hall.extractor.jni.JniLoader.loadJniLibrary("support", "stderr")
      nirvana.hall.image.jni.JniLoader.loadJniLibrary("support", "stderr")
    }
    else{
      nirvana.hall.extractor.jni.JniLoader.loadJniLibrary("../support", "stderr")
      nirvana.hall.image.jni.JniLoader.loadJniLibrary("../support", "stderr")
    }
  }
}

object FPTExportV70Module{
  def buildHallV70Config(@Symbol(MonadCoreSymbols.SERVER_HOME) serverHome: String) = {
    val content = MonadConfigFileUtils.readConfigFileContent(serverHome, "hall-v70.xml")
    XmlLoader.parseXML[HallV70Config](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/v70/v70.xsd")))
  }
  def buildHallApiConfig={
    new HallApiConfig
  }
  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[AuthService], classOf[AuthServiceImpl])
  }
  def contributeEntityManagerFactory(configuration:Configuration[String]): Unit ={
    configuration.add("nirvana.hall.v70.jpa")
    configuration.add("nirvana.hall.api.jpa")
  }
  @EagerLoad
  def buildProtobufRegistroy(configruation: java.util.Collection[ProtobufExtensionRegistryConfiger]) = {
    val registry = ExtensionRegistry.newInstance()
    val it = configruation.iterator()
    while (it.hasNext)
      it.next().config(registry)

    registry
  }
}
object FPTExportV62Module{
  def buildHallV62Config(@Symbol(MonadCoreSymbols.SERVER_HOME) serverHome: String) = {
    val content = MonadConfigFileUtils.readConfigFileContent(serverHome, "hall-v62.xml")
    XmlLoader.parseXML[HallV62Config](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/v62/v62.xsd")))
  }
  def buildHallApiConfig={
    new HallApiConfig
  }
  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[AuthService], classOf[AuthServiceImpl])
  }
  def contributeEntityManagerFactory(configuration:Configuration[String]): Unit ={
    configuration.add("nirvana.hall.api.jpa")
  }
  @EagerLoad
  def buildProtobufRegistroy(configruation: java.util.Collection[ProtobufExtensionRegistryConfiger]) = {
    val registry = ExtensionRegistry.newInstance()
    val it = configruation.iterator()
    while (it.hasNext)
      it.next().config(registry)

    registry
  }
}

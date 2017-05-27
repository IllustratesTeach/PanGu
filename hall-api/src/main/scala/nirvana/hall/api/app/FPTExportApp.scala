package nirvana.hall.api.app

import java.io.{File, FileOutputStream}
import javax.persistence.EntityManagerFactory

import com.google.protobuf.ExtensionRegistry
import monad.core.MonadCoreSymbols
import monad.core.internal.MonadConfigFileUtils
import monad.core.services.{BootstrapTextSupport, GlobalLoggerConfigurationSupport}
import monad.rpc.services.ProtobufExtensionRegistryConfiger
import monad.support.services.{JettyServerSupport, LoggerSupport, SystemEnvDetectorSupport, XmlLoader}
import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.internal.fpt.FPTServiceImpl
import nirvana.hall.api.internal.remote.HallImageRemoteServiceImpl
import nirvana.hall.api.internal.{AuthServiceImpl, FeatureExtractorImpl}
import nirvana.hall.api.services.AuthService
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.api.services.remote.HallImageRemoteService
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPT4File.FPT4File
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.image.internal.{FirmDecoderImpl, ImageEncoderImpl}
import nirvana.hall.image.services.{FirmDecoder, ImageEncoder}
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
  * 配置动态库so or dll
  * 配置config/hall-v62.xml or config/hall-v70.xml
  * 修改bin/hall-api
  *   SERVER_MAIN=nirvana.hall.api.app.FPTExportApp
  *   SERVER_ARGS="-m=? -t=? -f=? -p=?"
  */
object FPTExportApp extends JettyServerSupport
  with GlobalLoggerConfigurationSupport
  with SystemEnvDetectorSupport
  with BootstrapTextSupport with LoggerSupport{

  private var registry:Registry = _
  protected def getService[T:ClassTag]:T={
    registry.getService(classTag[T].runtimeClass.asInstanceOf[Class[T]])
  }
  private val MODULE = "m"
  private val CONFIG = "c"
  private val FILE = "f"
  private val PATH = "p"
  private val TYPE = "t"

  private val MODULE_V70 = "v70"
  private val MODULE_GZ = "gz"
  private val MODULE_V62 = "v62"
  private val TYPE_TP = "tp"
  private val TYPE_LP = "lp"

  def main(args: Array[String]): Unit = {
    val serverHome = System.getProperty(MonadCoreSymbols.SERVER_HOME, "support")
    System.setProperty(MonadCoreSymbols.SERVER_HOME, serverHome)
    configLogger(serverHome+"/log/hall.FPTExport.log", "API", "egf", "nirvana.hall.api")
    val logger = LoggerFactory getLogger getClass
    logger.info("starting export fpt server ....")

    //joptsimple处理命令行参数
    /*
    val parser = new OptionParser("m:c:f:t:p:")
    parser.accepts(MODULE).withRequiredArg().describedAs("module: v62, v70, gz").required()
//    parser.accepts(CONFIG).withRequiredArg().describedAs("config: hall-v62.xml or hall-v70.xml")
    parser.accepts(FILE).withRequiredArg().describedAs("cardid list file").required()
    parser.accepts(TYPE).withRequiredArg().describedAs("tp or lp").required()
    parser.accepts(PATH).withRequiredArg().describedAs("output fpt file dir").required()

    var options:OptionSet = null
    try {
      options = parser.parse(args:_ *)
      if(options.has("?") || options.has("h")){//帮助信息
        parser.printHelpOn(System.out)
        return
      }
    }catch {
      case e: OptionException =>
        logger.error(e.getMessage)
        parser.printHelpOn(System.out)
        return
    }
    //读取参数
    val module = options.valueOf(MODULE).asInstanceOf[String]
//    val config = options.valueOf(CONFIG).asInstanceOf[String]
    val filePath = options.valueOf(FILE).asInstanceOf[String]
    val tpe = options.valueOf(TYPE).asInstanceOf[String]
    val fptOutputPath = options.valueOf(PATH).asInstanceOf[String]

    val modules = module match {
      case MODULE_V70 =>
        Seq[String](
          "stark.activerecord.StarkActiveRecordModule",
          "nirvana.hall.v70.LocalV70ServiceModule",
          "nirvana.hall.v70.LocalDataSourceModule",
          "nirvana.hall.api.LocalProtobufModule",
          "nirvana.hall.api.app.FPTExportV70Module"
        )
      case MODULE_GZ =>
        Seq[String](
          "stark.activerecord.StarkActiveRecordModule",
          "nirvana.hall.v70.gz.LocalV70ServiceModule",
          "nirvana.hall.v70.LocalDataSourceModule",
          "nirvana.hall.api.LocalProtobufModule",
          "nirvana.hall.api.app.FPTExportV70Module"
        )

      case MODULE_V62 =>
        Seq[String](
          "stark.activerecord.StarkActiveRecordModule",
          "nirvana.hall.api.LocalProtobufModule",
          "nirvana.hall.v62.LocalV62ServiceModule",
          "nirvana.hall.v62.LocalV62DataSourceModule",
          "nirvana.hall.api.app.FPTExportV62Module"
        )
      case other =>
        return
    }
    //配置文件
//    val configFile = new File(config)
//    if(!configFile.exists()){
//      logger.error("config: %s is not exist".format(config))
//      return
//    }
    //读取文件信息
    val file = new File(filePath)
    if(!file.exists()){
      logger.error("file: %s is not exist".format(filePath))
      return
    }
    //fpt导出目录校验
    val fptFilePath = new File(fptOutputPath)
    if(!fptFilePath.exists() && !fptFilePath.isDirectory){
      logger.error("fptOutputPath: %s is not dir".format(fptOutputPath))
      return
    }*/

    /*暂时不使用命令行参数，使用hall-api相同的运行方式，
      fpt文件放在./fpt路径下
      捺印卡号输入文件./config/TPCardId.txt
     */
    val filePath = serverHome + "/config"
    val file = new File(filePath+"/TPCardId.txt")
    if(!file.exists()){
      file.createNewFile()
    }
    val modules = Seq[String](
      "stark.activerecord.StarkActiveRecordModule",
      "nirvana.hall.v70.gz.LocalV70ServiceModule",
      "nirvana.hall.v70.LocalDataSourceModule",
      "nirvana.hall.api.LocalProtobufModule",
      "nirvana.hall.api.app.FPTExportV70Module"
    )
    val tpe = TYPE_TP
    val fptOutputPath = serverHome+"/fpt"

    val fptFilePath = new File(fptOutputPath)
    if(!fptFilePath.exists()) {
      fptFilePath.mkdir()
    }

    //加载jni
    loadJni()
    //启动registry
    setup(modules)

    //TODO 支持比中关系导出
    try{
      //读入卡号列表
      val source = Source.fromFile(file)
      val cardIdList= source.getLines()

      val service = getService[FPTService]
      //类型判断，捺印or现场
      if(TYPE_TP.equals(tpe)){
        for(cardId <- cardIdList){
          logger.info("export fpt tp cardId: "+ cardId)
          try {
            val logic02Rec = service.getLogic02Rec(cardId.toString)
            if(logic02Rec != null){
              val fpt4File = new FPT4File
              fpt4File.logic02Recs = Array(logic02Rec)
              //fpt数据写入文件
              new FileOutputStream(fptOutputPath + "/%s.fpt".format(cardId)).write(fpt4File.build().toByteArray(AncientConstants.GBK_ENCODING))
            }else{
              logger.warn("cardId:{}", cardId)
            }
          } catch {
            case e:Exception =>
              logger.error("cardId:{}", cardId)
          }
        }
      }else if(TYPE_LP.equals(tpe)){
        for(cardId <- cardIdList){
          logger.info("export fpt lp cardId: "+ cardId)
          val logic03Rec = service.getLogic03Rec(cardId.toString)
          val fpt4File = new FPT4File
          fpt4File.logic03Recs = Array(logic03Rec)
          //fpt数据写入文件
          new FileOutputStream(fptOutputPath+"/%s.fpt".format(cardId)).write(fpt4File.build().toByteArray(AncientConstants.GBK_ENCODING))
        }
      }else{
        logger.error("unknown type: %s".format(tpe))
        return
      }

      source.close()
    }finally {
      down
    }

  }

  //启动registry
  def setup(modules: Seq[String]): Unit ={
    //v70
    val moduleClasses = modules.map(Class.forName)
    registry = RegistryBuilder.buildAndStartupRegistry(moduleClasses: _*)
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
      nirvana.hall.extractor.jni.JniLoader.loadJniLibrary(".", "stderr")
      nirvana.hall.image.jni.JniLoader.loadJniLibrary(".", "stderr")
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

    //FPT导出依赖的service
    binder.bind(classOf[FPTService], classOf[FPTServiceImpl])
    binder.bind(classOf[FeatureExtractor], classOf[FeatureExtractorImpl])
    binder.bind(classOf[FirmDecoder],classOf[FirmDecoderImpl]).withId("FirmDecoder")
    binder.bind(classOf[ImageEncoder],classOf[ImageEncoderImpl]).withId("ImageEncoder")
    binder.bind(classOf[HallImageRemoteService], classOf[HallImageRemoteServiceImpl])
  }
  def contributeEntityManagerFactory(configuration:Configuration[String]): Unit ={
//    configuration.add("nirvana.hall.v70.jpa")
    configuration.add("nirvana.hall.v70.gz.jpa")//贵州数据库
//    configuration.add("nirvana.hall.api.jpa")
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
//    configuration.add("nirvana.hall.api.jpa")
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

package nirvana.hall.api.app

import java.io.{ByteArrayInputStream, File, FileInputStream, FileOutputStream}
import java.text.{DecimalFormat, SimpleDateFormat}
import java.util.Date
import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}
import javax.imageio.ImageIO
import javax.imageio.spi.IIORegistry
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

import com.google.protobuf.ExtensionRegistry
import monad.core.MonadCoreSymbols
import monad.core.internal.MonadConfigFileUtils
import monad.core.services.{BootstrapTextSupport, GlobalLoggerConfigurationSupport}
import monad.rpc.services.ProtobufExtensionRegistryConfiger
import monad.support.services.{JettyServerSupport, SystemEnvDetectorSupport, XmlLoader}
import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.internal.AuthServiceImpl
import nirvana.hall.api.services.AuthService
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gfpt4lib.FPT4File.FPT4File
import nirvana.hall.c.services.gfpt4lib.fpt4code._
import nirvana.hall.c.services.gfpt4lib.{FPT4File, FPTFile}
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.support.services.{GAFISImageReaderSpi, JdbcDatabase}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v70.config.HallV70Config
import org.apache.commons.io.{FileUtils, IOUtils}
import org.apache.commons.lang.StringUtils
import org.apache.tapestry5.ioc.annotations.{EagerLoad, Symbol}
import org.apache.tapestry5.ioc.{Configuration, Registry, RegistryBuilder, ServiceBinder}
import org.slf4j.LoggerFactory
import org.springframework.orm.jpa.{EntityManagerFactoryUtils, EntityManagerHolder}
import org.springframework.transaction.support.TransactionSynchronizationManager

import scala.reflect._

/**
  * Created by yuchen on 2017/2/20.
  * FPT文件过滤器的主要用途是，将FPT导出程序导出的FPT文件，
  * 按照刑专部的相关要求的规则进行规范化的处理，使得FPT文件可以具备正确上报的要求
  */
object FPTFilter extends JettyServerSupport
  with GlobalLoggerConfigurationSupport
  with SystemEnvDetectorSupport
  with BootstrapTextSupport{
  private var registry: Registry = _

  protected def getService[T: ClassTag]: T = {
    registry.getService(classTag[T].runtimeClass.asInstanceOf[Class[T]])
  }

  var sendUnitCode = ""
  var sendUnitName = ""
  var senderName = ""
  //class FPTFilter(implicit dataSource: DataSource)
  def main(args: Array[String]): Unit = {

    val args: Array[String] = new Array[String](5)

    args(0) = "D:\\FPT\\"
    args(1) = "D:\\filtered\\"
    args(2) = "520000000000"
    args(3) = "贵州省公安厅"
    args(4) = "管理员"
    sendUnitCode = args(2)
    sendUnitName = args(3)
    senderName = args(4)
    //doWork(args(0))

    val modules = Seq[String](
      //"stark.activerecord.StarkActiveRecordModule",
      //"nirvana.hall.api.LocalProtobufModule",
      //"nirvana.hall.v62.LocalV62ServiceModule",
      "nirvana.hall.v62.LocalV62DataSourceModule"
      //"nirvana.hall.api.LocalApiWebServiceModule",
      //"nirvana.hall.api.app.FPTExportV62Module"
    )


    val logger = LoggerFactory getLogger getClass

    setup(modules)

    //connectDataBase(1, 1)
    //    val runnable: Runnable = new Runnable() {
    //      def run: Unit = {
    //
    //        doWork(args(0),args(1))
    //      }
    //    }
    //    val service: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor
    //    service.scheduleAtFixedRate(runnable, 1, 1, TimeUnit.SECONDS)
  }


  def connectDataBase(implicit dataSource: DataSource,dbId: Int, tableid: Int): Unit = {
    val sql = "select t.TABLENAME from TABLECATLOG t where t.DBID =? and t.TABLEID =?"
    JdbcDatabase.queryFirst(sql) { ps =>
      ps.setInt(1, dbId)
      ps.setInt(2, tableid)
    }{_.getString(1)}.get
  }

  def doWork(sourcePath: String, filtedPath: String): Unit = {
    var file: File = null
    try {
      val fileList = FileUtils.listFiles(new File(sourcePath), Array[String]("fpt", "FPT", "fptt"), true)
      val it = fileList.iterator()
      while (it.hasNext) {
        file = it.next()
        val is = new FileInputStream(file)
        val fptFile = FPTFile.parseFromInputStream(is, AncientConstants.GBK_ENCODING)
        IOUtils.closeQuietly(is)
        val bytes = fptFile match {
          case Right(fpt4) =>
            //produceBMPByFPTFile(fpt4)
            filter(fpt4)
            fpt4.toByteArray(AncientConstants.GBK_ENCODING)
        }
        FileUtils.writeByteArrayToFile(new File(filtedPath + file.getName), bytes)
        file.renameTo(new File(sourcePath(0) + file.getName + ".converted"))
      }
    }
    catch {
      case ex: Exception => {
        println(ex.getMessage)
        file.renameTo(new File(sourcePath(0) + file.getName + ".error"))
      }
    }
  }

  /**
    * 解析FPT文件，按照规则生成新的FPT文件
    */
  def filter(fPT4File: FPT4File): FPT4File = {
    var fPTFilterResult: FPT4File = null
    if (fPT4File.logic02Recs.length > 0) {
      fPTFilterResult = filterTPFPTFile(fPT4File)
    } else if (fPT4File.logic03Recs.length > 0) {
      //fPTFilterResult = filterTPFPTFile(fPT4File)
      throw new UnsupportedOperationException("not support LP FilterHandler")
    }
    fPTFilterResult
  }

  /**
    * 过滤捺印FPT方法
    *
    * @param fPT4File
    * @return
    */
  def filterTPFPTFile(fPT4File: FPT4File): FPT4File = {

    if (StringUtils.isBlank(fPT4File.logic02Recs.head.personId)) {
      throw new Exception("personId is empty")
    }
    if (StringUtils.isBlank(fPT4File.logic02Recs.head.cardId)) {
      throw new Exception("cardId is empty")
    }
    if (StringUtils.isBlank(fPT4File.logic02Recs.head.index)) {
      //序号	不能为空
      throw new Exception("index is empty")
    }
    if (fPT4File.logic02Recs.head.fingers.head.imgData == null || fPT4File.logic02Recs.head.fingers.head.imgData.length <= 0) {
      throw new Exception("image is empty");
    }
    if (StringUtils.isBlank(fPT4File.logic02Recs.head.fingers.head.imgDataLength)
      || Integer.valueOf(fPT4File.logic02Recs.head.fingers.head.imgDataLength) <= 0) {
      fPT4File.logic02Recs.head.fingers.head.imgDataLength = fPT4File.logic02Recs.head.fingers.head.imgData.length.toString
    }


    fPT4File.dataType = "02" //逻辑记录类型  必须为02
    fPT4File.logic02Recs.head.systemType = "1900" //系统类型	不能为空

    if (fPT4File.logic02Recs.head.personId.length != 23) {
      //人员编号	必须为23位长度
      throw new Exception("personid 's length is not 23")
    }
    if (StringUtils.isBlank(fPT4File.logic02Recs.head.personName)) {
      //姓名	不能为空
      fPT4File.logic02Recs.head.personName = "未知"
    }
    if (StringUtils.isBlank(fPT4File.logic02Recs.head.gender)) {
      fPT4File.logic02Recs.head.gender = "0"
    }
    if (StringUtils.isBlank(fPT4File.logic02Recs.head.birthday)) {
      fPT4File.logic02Recs.head.birthday = "未知"
    }
    if (StringUtils.isBlank(fPT4File.logic02Recs.head.door)) {
      fPT4File.logic02Recs.head.door = "未知"
    }
    if (StringUtils.isBlank(fPT4File.logic02Recs.head.doorDetail)) {
      fPT4File.logic02Recs.head.doorDetail = "未知"
    }
    if (StringUtils.isBlank(fPT4File.logic02Recs.head.isCriminal)) {
      fPT4File.logic02Recs.head.isCriminal = "2" //前科库标识 1：有；2：无
    }
    if (StringUtils.isBlank(fPT4File.logic02Recs.head.gatherUnitCode)) {
      //捺印单位代码	不能同时和捺印单位名称为空
      fPT4File.logic02Recs.head.gatherUnitCode = "未知"
    }
    if (StringUtils.isBlank(fPT4File.logic02Recs.head.gatherUnitName)) {
      //捺印单位名称	不能同时和捺印单位代码为空
      fPT4File.logic02Recs.head.gatherUnitName = "未知"
    }
    if (StringUtils.isBlank(fPT4File.logic02Recs.head.gatherName)) {
      //捺印人姓名		不能为空
      fPT4File.logic02Recs.head.gatherName = "管理员"
    }
    if (StringUtils.isBlank(fPT4File.logic02Recs.head.gatherDate)) {
      //捺印日期		不能为空
      fPT4File.logic02Recs.head.gatherDate = String.valueOf(new Date())
    }
    //发送指纹个数
    if (StringUtils.isBlank(fPT4File.logic02Recs.head.sendFingerCount)
      || fPT4File.logic02Recs.head.sendFingerCount.length <= 0) {
      fPT4File.logic02Recs.head.sendFingerCount = fPT4File.logic02Recs.head.fingers.length.toString
    }
    if (fPT4File.logic02Recs.head.sendFingerCount.toInt <= 0) {
      //发送指纹个数	不能为空，不能为0（0代表索引数据）
      throw new Exception("send finger num is null")
    }
    //指纹信息长度 指纹信息长度	必须为数字
    val fingersCount = fPT4File.logic02Recs.head.fingers.length
    if (fingersCount > 0) {
      fPT4File.logic02Recs.head.fingers.foreach((f: FPT4File.FingerTData)
      => (f.dataLength = f.toByteArray(AncientConstants.GBK_ENCODING).length.toString))
    }

    fPT4File.logic02Recs.head.fingers.head.sendNo = "01" //发送序号	不能为空

    var fgp = 1
    fPT4File.logic02Recs.head.fingers.foreach((f: FPT4File.FingerTData)
    => if (StringUtils.isBlank(f.fgp))
        f.fgp = new DecimalFormat("0").format(Integer.valueOf(fgp))
    )

    //特征点为空的指位的个数 特征点个数	不能为空 特征点	不能超过4个以上为空
    var featureIsNullCount = 0
    fPT4File.logic02Recs.head.fingers.foreach((f: FPT4File.FingerTData)
    => (if (StringUtils.isBlank(f.feature)) featureIsNullCount += 1))
    if (featureIsNullCount > 4) {
      throw new Exception("cardNo:" + fPT4File.logic02Recs.head.cardId + " No feature point refers to the number of more than 5:" + featureIsNullCount)
    }
    //自定义信息长度	不能为空 必须为数字
    if (Integer.valueOf(fPT4File.logic02Recs.head.fingers.head.customInfoLength) <= 0) {
      fPT4File.logic02Recs.head.fingers.head.customInfoLength = "0"
    }

    fPT4File.logic02Recs.head.fingers.head.imgHorizontalLength = "640" //    图像水平方向长度	必须为640
    fPT4File.logic02Recs.head.fingers.head.imgVerticalLength = "640" //      图像垂直方向长度	必须为640
    fPT4File.logic02Recs.head.fingers.head.dpi = "500" //      图像分辨率	必须为500
    if (!fPT4File.logic02Recs.head.fingers.head.imgCompressMethod.startsWith("14")) {
      //      图像压缩方法代码	不能为空 必须为14开头的WSQ压缩方法
      fPT4File.logic02Recs.head.fingers.head.imgCompressMethod = "1419"
    }
    //    fPT4File.sendTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date)
    //    fPT4File.sendUnitCode = sendUnitCode
    //    fPT4File.sendUnitName = sendUnitName
    //    fPT4File.sender = senderName
    fPT4File
  }


  //  def produceBMPByFPTFile(fPT4File: FPT4File): Unit ={
  //    val cardId = fPT4File.logic02Recs(0).cardId
  //    fPT4File.logic02Recs(0).fingers.foreach{
  //          f => outPutBMP(f,f.fgp,cardId)
  //    }
  //  }
  //
  //
  //  private def outPutBMP(tData: FPTFingerData,fgp:String,cardId:String): Unit ={
  //    val gafisStruts = FPTFileHandler.fingerDataToGafisImage(tData)
  //    val image = FPTFileHandler.callHallImageDecompressionImage("http://192.168.1.214:9009",gafisStruts)
  //    val iioRegistry = IIORegistry.getDefaultInstance
  //    iioRegistry.registerServiceProvider(new GAFISImageReaderSpi)
  //    val img = ImageIO.read(new ByteArrayInputStream(image.toByteArray()))
  //    val out = new FileOutputStream("D:\\filtered\\" + "_" + fgp + "_" + cardId + ".bmp")
  //    ImageIO.write(img, "bmp", out)
  //    out.close
  //  }

  //启动registry
  def setup(modules: Seq[String]): Unit = {
    //v70
    val moduleClasses = modules.map(Class.forName)
    registry = RegistryBuilder.buildAndStartupRegistry(moduleClasses: _*)
    //OpenSession In Thread
    val entityManagerFactory = getService[EntityManagerFactory]
    val emHolder = new EntityManagerHolder(entityManagerFactory.createEntityManager())
    TransactionSynchronizationManager.bindResource(entityManagerFactory, emHolder)
  }

  //关闭registry
  def down: Unit = {
    val emf: EntityManagerFactory = registry.getService(classOf[EntityManagerFactory])
    val emHolder: EntityManagerHolder = TransactionSynchronizationManager.unbindResource(emf).asInstanceOf[EntityManagerHolder]
    EntityManagerFactoryUtils.closeEntityManager(emHolder.getEntityManager)
    registry.shutdown()
  }

  object FPTExportV70Module {
    def buildHallV70Config(@Symbol(MonadCoreSymbols.SERVER_HOME) serverHome: String) = {
      val content = MonadConfigFileUtils.readConfigFileContent(serverHome, "hall-v70.xml")
      XmlLoader.parseXML[HallV70Config](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/v70/v70.xsd")))
    }

    def buildHallApiConfig = {
      new HallApiConfig
    }

    def bind(binder: ServiceBinder): Unit = {
      binder.bind(classOf[AuthService], classOf[AuthServiceImpl])
    }

    def contributeEntityManagerFactory(configuration: Configuration[String]): Unit = {
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

  object FPTExportV62Module {
    def buildHallV62Config(@Symbol(MonadCoreSymbols.SERVER_HOME) serverHome: String) = {
      val content = MonadConfigFileUtils.readConfigFileContent(serverHome, "hall-v62.xml")
      XmlLoader.parseXML[HallV62Config](content, xsd = Some(getClass.getResourceAsStream("/nirvana/hall/v62/v62.xsd")))
    }

    def buildHallApiConfig = {
      new HallApiConfig
    }

    def bind(binder: ServiceBinder): Unit = {
      binder.bind(classOf[AuthService], classOf[AuthServiceImpl])
    }

    def contributeEntityManagerFactory(configuration: Configuration[String]): Unit = {
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

}

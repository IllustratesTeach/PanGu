package nirvana.hall.matcher.internal.adapter.gz

import java.io.File
import javax.sql.DataSource

import monad.core.services.{CronScheduleWithStartModel, StartAtDelay}
import monad.support.services.LoggerSupport
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.extractor.services.FeatureExtractor
import nirvana.hall.image.config.HallImageConfig
import nirvana.hall.image.internal.FirmDecoderImpl
import nirvana.hall.image.services.RawImageDataType
import nirvana.hall.matcher.config.HallMatcherConfig
import nirvana.hall.matcher.service.AutoExtractFeatureService
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.support.services.JdbcDatabase
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor

import scala.collection.mutable.ArrayBuffer
import scala.io.Source
class AutoExtractFeatureServiceImpl(config: HallMatcherConfig, featureExtractor: FeatureExtractor, implicit val dataSource: DataSource) extends AutoExtractFeatureService with  LoggerSupport {
  nirvana.hall.image.jni.JniLoader.loadJniLibrary("support", config.logFile)
  val firmDecoder = new FirmDecoderImpl("support",new HallImageConfig)
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor, autoExtractorService: AutoExtractFeatureService): Unit = {
    //先处理配置的人员编号
    val personidList = getPersonIdByFile()
    personidList.foreach(reExtractFeature)
    periodicExecutor.addJob(new CronScheduleWithStartModel("0 0 0 * * ? *", StartAtDelay), "AutoExtractFeatureService-cron", new Runnable {
      override def run(): Unit = {
        val personidList = getPersonIdByDB()
        personidList.foreach(reExtractFeature)
      }
    })
  }
  case class FingerImageData(fgp: Int, fgpCase: Int, imageData: Array[Byte])

  private def deleteFingerMntData(personId: String): Unit={
    logger.info("deleteFingerMntData personid:{}", personId)
    val sql = "delete from gafis_gather_finger t where t.logtype=2 and t.person_id=?"
    JdbcDatabase.update(sql){ps=>ps.setString(1, personId)}
  }
  private def getFingerImageData(personId: String): Seq[FingerImageData]={
    val result = new ArrayBuffer[FingerImageData]()
    val sql = "select t.fgp, t.fgp_case, t.gather_data from gafis_gather_finger t where t.group_id=1 and t.person_id=?"
    JdbcDatabase.queryWithPsSetter(sql){ps=>
      ps.setString(1, personId)
    }{rs=>
      val data = rs.getBytes("gather_data")
      val fgp = rs.getInt("fgp")
      val fgpCase = rs.getInt("fgp_case")
      result += FingerImageData(fgp, fgpCase, data)
    }
    result
  }

  override def reExtractFeature(personid: String): Unit = {
    logger.info("reExtractFeature personid:{}", personid)
    deleteFingerMntData(personid)
    val fingerImages = getFingerImageData(personid)
    fingerImages.foreach{img=>
      try {
        val gafisImage = new GAFISIMAGESTRUCT().fromByteArray(img.imageData)
        gafisImage.stHead.nCompressMethod match {
          case glocdef.GAIMG_CPRMETHOD_DEFAULT |
               glocdef.GAIMG_CPRMETHOD_WSQ |
               glocdef.GAIMG_CPRMETHOD_GA10 |
               glocdef.GAIMG_CPRMETHOD_XGW |
               glocdef.GAIMG_CPRMETHOD_GFS =>
            //图像解压
            val rawImage = firmDecoder.decode(gafisImage, RawImageDataType)
            //提取特征
            val mntData = featureExtractor.extractByGAFISIMG(rawImage, FingerPosition.valueOf(img.fgp), FeatureType.FingerTemplate)
            //保存数据, 特征
            JdbcDatabase.update("insert into gafis_gather_finger (pk_id,person_id,fgp,fgp_case,group_id,lobtype,inputtime,gather_data)" +
              " values(sys_guid(),?,?,?,0,2,sysdate,?)") { ps =>
              ps.setString(1, personid)
              ps.setInt(2, img.fgp)
              ps.setInt(3, img.fgpCase)
              ps.setBytes(4, mntData._1.toByteArray(AncientConstants.GBK_ENCODING))
            }
            //纹线
            JdbcDatabase.update("insert into gafis_gather_finger (pk_id,person_id,fgp,fgp_case,group_id,lobtype,inputtime,gather_data)" +
              " values(sys_guid(),?,?,?,4,2,sysdate,?)") { ps =>
              ps.setString(1, personid)
              ps.setInt(2, img.fgp)
              ps.setInt(3, img.fgpCase)
              ps.setBytes(4, mntData._2.toByteArray(AncientConstants.GBK_ENCODING))
            }
          case other =>
            logger.warn("unsupport compressMethod:{} personid:{}", other, personid)
        }
      } catch {
        case e: Exception =>
          logger.error("reExtractFeature personid:{} msg:{}", personid,e.getMessage)
      }
    }
  }

  private def getPersonIdByFile(): Seq[String]={
    val personids = new ArrayBuffer[String]()
    val file = new File("support/config/extract_personid_list.txt")
    if(file.exists()){
      logger.info("reExtractFeature by file:"+ file.getPath)
      val personIdList= Source.fromFile(file).getLines()
      for(personid <- personIdList){
        if(personid.toString.trim.length > 0){
          personids += personid.toString.trim
        }
      }
      //文件重命名
      file.renameTo(new File("support/config/extract_personid_list.txt.ok"))
    }

    personids
  }
  private def getPersonIdByDB(): Seq[String]={
    val personids = new ArrayBuffer[String]()
    val sql = "SELECT P.PERSONID" +
      "  FROM GAFIS_PERSON P, SYS_DEPART D" +
      " WHERE P.GATHER_ORG_CODE = D.CODE" +
      "   AND D.INTEGRATION_TYPE <> '01'" +
      "   AND P.PERSONID IN" +
      "       (SELECT A.PERSON_ID" +
      "          FROM (SELECT F.PERSON_ID, F.LOBTYPE" +
      "                  FROM GAFIS_GATHER_FINGER F" +
      "                 WHERE F.INPUTTIME >= (sysdate - 2)" +
      "                 AND F.INPUTTIME < sysdate "+
      "                 GROUP BY F.PERSON_ID, F.LOBTYPE) A" +
      "         GROUP BY A.PERSON_ID" +
      "        HAVING COUNT(*) < 2)" +
      "   AND P.DELETAG = 1"
    JdbcDatabase.queryWithPsSetter(sql){ps=>
    }{rs=>
      personids += rs.getString("personid")
    }

    personids
  }
}

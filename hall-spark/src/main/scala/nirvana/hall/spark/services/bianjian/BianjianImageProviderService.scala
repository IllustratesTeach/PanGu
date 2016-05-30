package nirvana.hall.spark.services.bianjian

import javax.sql.DataSource

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.spark.config.{DatabaseConfig, NirvanaSparkConfig}
import nirvana.hall.spark.services.SparkFunctions.{StreamEvent, _}
import nirvana.hall.support.services.JdbcDatabase
import org.apache.commons.io.IOUtils

import scala.collection.mutable.ArrayBuffer

/**
 * request remote fpt file
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-02-09
 */
object BianjianImageProviderService {
  private var databaseConfig:Option[DatabaseConfig] = None
  private lazy implicit val dataSource = buildCsDataSource()
  val querySql = "select t.zp from T_PC_A_CS t where t.csid =? "

  case class RequestRemoteFileError(streamEvent: StreamEvent,message:String) extends StreamError(streamEvent) {
    override def getMessage: String = "R|"+message
  }
  def requestWsqData(parameter:NirvanaSparkConfig,csid:String): Seq[(StreamEvent,GAFISIMAGESTRUCT)] ={
    BianjianPartitionRecordsSaver.databaseConfig=Some(parameter.db)

    val buffer = ArrayBuffer[(StreamEvent, GAFISIMAGESTRUCT)]()
    JdbcDatabase.queryFirst(querySql){ps=>
      ps.setString(1, csid)
    }{rs=>
      val zp = rs.getBinaryStream("zp")
      val gafisImg = new GAFISIMAGESTRUCT
      gafisImg.stHead.bIsCompressed = 1
      gafisImg.stHead.nCompressMethod = glocdef.GAIMG_CPRMETHOD_WSQ.toByte
      gafisImg.bnData = IOUtils.toByteArray(zp)
      gafisImg.stHead.nImgSize = gafisImg.bnData.length
      IOUtils.closeQuietly(zp)
      buffer += createImageEvent(csid, gafisImg)
    }
    buffer.toSeq
  }

  def createImageEvent(csid: String, gafisImg: GAFISIMAGESTRUCT): (StreamEvent,GAFISIMAGESTRUCT) = {
    val event = new StreamEvent(csid, csid, FeatureType.FingerTemplate, FingerPosition.FINGER_R_THUMB,"","","")
    (event,gafisImg)
  }

  def buildCsDataSource(): DataSource = {
    databaseConfig match{
      case Some(config) =>
        val hikariConfig = new HikariConfig()
        //针对oracle特别处理
        hikariConfig.setConnectionTestQuery("select 1 from dual")
        hikariConfig.setDriverClassName(config.driver)

//        hikariConfig.setJdbcUrl(System.getProperty(BianjianTestSymobls.IMG_JDBC_URL))
//        hikariConfig.setUsername(System.getProperty(BianjianTestSymobls.IMG_JDBC_USER))
//        hikariConfig.setPassword(System.getProperty(BianjianTestSymobls.IMG_JDBC_PASS))
//        hikariConfig.setJdbcUrl(config.url)
//        hikariConfig.setUsername(config.user)
//        hikariConfig.setPassword(config.password)
        hikariConfig.setJdbcUrl("jdbc:oracle:thin:@127.0.0.1:1521:xe")
        hikariConfig.setUsername("pcsys_cs")
        hikariConfig.setPassword("11")
        //设置自动提交事务
        hikariConfig.setAutoCommit(false)

        hikariConfig.addDataSourceProperty("cachePrepStmts", "true")
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250")
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
        hikariConfig.setMaximumPoolSize(15)
        //hikariConfig.addDataSourceProperty("maximumPoolSize", "5")

        new HikariDataSource(hikariConfig)
      case None=>
        throw new IllegalStateException("database configuration not set")
    }
  }
}

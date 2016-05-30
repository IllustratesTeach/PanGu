package nirvana.hall.spark.services.bianjian

import javax.sql.DataSource

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.spark.config.{DatabaseConfig, NirvanaSparkConfig}
import nirvana.hall.spark.services.SparkFunctions
import nirvana.hall.spark.services.SparkFunctions.{StreamError, StreamEvent}
import nirvana.hall.support.services.JdbcDatabase

import scala.util.control.NonFatal

/**
 * partition records saver
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-02-09
 */
object BianjianPartitionRecordsSaver {
  var databaseConfig:Option[DatabaseConfig] = None
  private lazy implicit val dataSource = buildMntDataSource()
  case class DbError(streamEvent: StreamEvent,message:String) extends StreamError(streamEvent) {
    override def getMessage: String = "S|"+message
  }
  def savePartitionRecords(parameter: NirvanaSparkConfig)(records:Iterator[(StreamEvent, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)]):Unit = {
      databaseConfig = Some(parameter.db)
      records.foreach { case (event, mnt,bin) =>
        try {
          //保存人员信息
          saveGafisMnt(event.personId, mnt.toByteArray())
        } catch {
          case NonFatal(e) =>
            e.printStackTrace(System.err)
            SparkFunctions.reportError(parameter, DbError(event, e.toString))
        }
      }
  }

  //查询人员主表信息
  def queryCsid(csid: String) : Option[String] = {
      JdbcDatabase.queryFirst("select t.csid from T_PC_A_CS where csid = ?"){ps =>
        ps.setString(1,csid)
      }{rs=>
        rs.getString("csid")
      }
  }

  //保存人员指纹特征信息
  private def saveGafisMnt(csid: String,mnt : Array[Byte]): Unit = {
    val sql: String = "insert into gafis_mnt (csid, mnt, seq) values(?,?, gafis_mnt_seq.nextval)"
    JdbcDatabase.update(sql){ps=>
      ps.setString(1, csid)
      ps.setBytes(2, mnt)
    }
  }


  def buildMntDataSource(): DataSource = {
    databaseConfig match{
      case Some(config) =>
        val hikariConfig = new HikariConfig()
        //针对oracle特别处理
        hikariConfig.setConnectionTestQuery("select 1 from dual")
        hikariConfig.setDriverClassName(config.driver)

        hikariConfig.setJdbcUrl(config.url)
        hikariConfig.setUsername(config.user)
        hikariConfig.setPassword(config.password)
        //设置自动提交事务
        hikariConfig.setAutoCommit(false)

        hikariConfig.addDataSourceProperty("cachePrepStmts", "true")
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250")
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
        hikariConfig.setMaximumPoolSize(5)
        //hikariConfig.addDataSourceProperty("maximumPoolSize", "5")

        new HikariDataSource(hikariConfig)
      case None=>
      throw new IllegalStateException("database configuration not set")
    }
  }

}

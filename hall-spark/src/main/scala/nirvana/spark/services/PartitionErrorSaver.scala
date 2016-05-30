package nirvana.spark.services

import javax.sql.DataSource

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.support.services.JdbcDatabase
import nirvana.spark.config.{NirvanaSparkConfig, DatabaseConfig}
import nirvana.spark.services.SparkFunctions.StreamEvent

/**
 * Created by wangjue on 2016/2/22.
 */
object PartitionErrorSaver {

  private var databaseConfig:Option[DatabaseConfig] = None
  private lazy implicit val dataSource = buildErrorDataSource()

  def savePartitionErrors(parameter: NirvanaSparkConfig)(errors:Iterator[(String)]):Unit = {
    databaseConfig = Some(parameter.db)
    errors.foreach(item=> {
      val error = item
      //println("-----------------------------------"+error)
      saveErrorInfo(error)
    }
    )

  }

  //save error info to database
  private def saveErrorInfo(error:String): Unit = {
    val arr = error.split('|')
    val fpt_path = arr(0)//error file
    val keyId = arr(1)//key no
    var featureType = arr(5)// template or latent
    var direction = ""
    var position = ""
    var errorType = ""
    var cardId = ""
    if ("template".equals(featureType)) {
      val group = arr(2).split('_')
      errorType = arr(3)

      if (group!=null) {
        if (group.length == 3) {
          featureType = group(0)
          direction = group(1) //left or right hand
          position = group(2) //position
        } else if (group.length == 2) {//valid position
          featureType = group(0)
          position = group(1)
        }
      }
    } else
        cardId = arr(2)


    val errorDetail = arr(4)//error details


    val saveErrorSql = "INSERT INTO GAFIS_DAKU_ERROR(KEY_ID,FPT_PATH,FEATURE_TYPE,DIRECTION,POSITION,ERROR_TYPE,ERROR_DETAIL,CARDID,CREATE_TIME) VALUES(?,?,?,?,?,?,?,?,sysdate)"
    JdbcDatabase.update(saveErrorSql) { ps =>
      ps.setString(1,keyId)
      ps.setString(2,fpt_path)
      ps.setString(3,featureType)
      ps.setString(4,direction)
      ps.setString(5,position)
      ps.setString(6,errorType)
      ps.setString(7,errorDetail)
      ps.setString(8,cardId)
    }
  }

  def buildErrorDataSource(): DataSource = {
    databaseConfig match{
      case Some(config) =>
        val hikariConfig = new HikariConfig()

        hikariConfig.setConnectionTestQuery("select 1 from dual")
        hikariConfig.setDriverClassName(config.driver)

        /*hikariConfig.setJdbcUrl(System.getProperty("daku.mnt.jdbc.url"))
        hikariConfig.setUsername(System.getProperty("daku.mnt.jdbc.user"))
        hikariConfig.setPassword(System.getProperty("daku.mnt.jdbc.pass"))*/

        hikariConfig.setJdbcUrl(config.url)
        hikariConfig.setUsername(config.user)
        hikariConfig.setPassword(config.password)
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




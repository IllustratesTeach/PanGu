package nirvana.spark.services.bianjian

import javax.sql.DataSource

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import nirvana.hall.support.services.JdbcDatabase
import nirvana.spark.config.{DatabaseConfig, NirvanaSparkConfig}

/**
 * Created by wangjue on 2016/2/22.
 */
object BianjianPartitionErrorSaver {

  private var databaseConfig:Option[DatabaseConfig] = None
  private lazy implicit val dataSource = buildErrorDataSource()

  def savePartitionErrors(parameter: NirvanaSparkConfig)(errors:Iterator[(String)]):Unit = {
    databaseConfig = Some(parameter.db)
    errors.foreach(item=> {
      val error = item
      println("-----------------------------------"+error)
      saveErrorInfo(error)
    }
    )

  }

  //save error info to database
  private def saveErrorInfo(error:String): Unit = {
    val arr = error.split('|')
    val fpt_path = arr(0)//error file
    val keyId = arr(1)//key no
    val group = arr(2).split('_')
    val errorType = arr(3)
    var featureType = ""
    var direction = ""
    var position = ""
    if (!errorType.equals("R")) {
      featureType = group(0) //template or case
      direction = group(1) //left or right hand
      position = group(2) //position
    }

    val errorDetail = arr(4)//error details


    val saveErrorSql = "INSERT INTO GAFIS_ERROR(csid,ERROR_TYPE,ERROR_DETAIL) VALUES(?,?,?)"
    JdbcDatabase.update(saveErrorSql) { ps =>
      ps.setString(1,keyId)
      ps.setString(2,errorType)
      ps.setString(3,errorDetail)
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




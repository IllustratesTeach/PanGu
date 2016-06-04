package nirvana.hall.spark.services

import java.util
import javax.sql.DataSource

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import nirvana.hall.spark.config.{DatabaseConfig, NirvanaSparkConfig, SparkConfigProperty}

import scala.collection.convert.decorateAsScala._
/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-30
  */
object SysProperties{
  private var sysProperties:Map[String,String] = _
  private var dataSources :Map[String,DataSource] = _
  private def initProperties(properties: util.List[SparkConfigProperty]): Unit ={
    if(sysProperties == null) {
      val data = new util.HashMap[String, String](properties.size())
      val it = properties.iterator()
      while (it.hasNext) {
        val property = it.next()
        data.put(property.name, property.value)
      }
      sysProperties = data.asScala.toMap
    }
  }
  def setConfig(config:NirvanaSparkConfig){
    initProperties(config.properties)
    initDataSource(config.database)
  }
  def getPropertyOption(name:String): Option[String] ={
    sysProperties.get(name)
  }
  def initDataSource(databaseConfigList:util.List[DatabaseConfig]): Unit ={
    if(dataSources == null) {
      val data = new util.HashMap[String, DataSource](databaseConfigList.size())
      val it = databaseConfigList.iterator()
      while(it.hasNext) {
        val databaseConfig = it.next()
        data.put(databaseConfig.poolName, buildDataSource(databaseConfig))
      }
      dataSources = data.asScala.toMap
    }else{
      println("data source has been initialized!")
    }
  }

  def getDataSource(name:String):DataSource = {
    dataSources.getOrElse(name, throw new IllegalArgumentException("database config %s not found".format(name)))
  }
  def getBoolean(name:String,defaultValue:Boolean):Boolean={
    getPropertyOption(name) match{
      case Some(value)=>
        value.toBoolean
      case None=>
        defaultValue
    }
  }
  def buildDataSource(databaseConfig: DatabaseConfig): DataSource={
    val hikariConfig = new HikariConfig()
    //针对oracle特别处理
    hikariConfig.setConnectionTestQuery("select 1 from dual")
    hikariConfig.setDriverClassName(databaseConfig.driver)

    hikariConfig.setJdbcUrl(databaseConfig.url)
    hikariConfig.setUsername(databaseConfig.user)
    hikariConfig.setPassword(databaseConfig.password)
    //设置自动提交事务
    hikariConfig.setAutoCommit(false)

    hikariConfig.addDataSourceProperty("cachePrepStmts", "true")
    hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250")
    hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
    hikariConfig.setMaximumPoolSize(databaseConfig.max)
    //hikariConfig.addDataSourceProperty("maximumPoolSize", "5")

    new HikariDataSource(hikariConfig)
  }
}

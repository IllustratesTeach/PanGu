package nirvana.hall.spark.services

import java.util
import java.util.concurrent.locks.ReentrantLock
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
  private var dataSources :Map[String,DataSource] = Map()
  private var databaseConfigs:List[DatabaseConfig] = _
  private val locker = new ReentrantLock()
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
      databaseConfigs = databaseConfigList.asScala.toList
  }

  def getDataSource(name:String):DataSource = {
    var dsOpt = dataSources.get(name)
    dsOpt match{
      case Some(ds)=>
        ds
      case None=>
        try {
          locker.lock()
          dsOpt = dataSources.get(name)
          dsOpt match{
            case Some(ds)=>
              ds
            case None=>
              val configOpt = databaseConfigs.find(_.poolName == name)
              configOpt match{
                case Some(config)=>
                  val ds  = buildDataSource(config)
                  dataSources = dataSources+ (name->ds)
                  ds
                case None=>
                  throw new IllegalArgumentException("database config %s not found".format(name))
              }
          }

        }finally{
          locker.unlock()
        }
    }
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

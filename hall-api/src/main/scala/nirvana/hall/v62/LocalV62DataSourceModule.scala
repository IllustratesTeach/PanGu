package nirvana.hall.v62

import java.sql.Connection
import javax.sql.DataSource

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import net.sf.log4jdbc.ConnectionSpy
import nirvana.hall.api.services.sync._
import nirvana.hall.api.services._
import nirvana.hall.api.services.fpt.exchange.FPTExchangeService
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal._
import nirvana.hall.v62.internal.sync._
import nirvana.hall.v62.services.GetPKIDServiceImpl
import org.apache.tapestry5.ioc.annotations.{EagerLoad, ServiceId}
import org.apache.tapestry5.ioc.services.RegistryShutdownHub
import org.apache.tapestry5.ioc.{Configuration, ServiceBinder}
import org.slf4j.Logger
import stark.migration.{DatabaseAdapter, InstallAllMigrations, Migrator, Vendor}

/**
 * 针对数据的操作，绑定数据同步的service
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-06
 */
object LocalV62DataSourceModule {
  @EagerLoad
  @ServiceId("V62DataSource")
  def buildDataSource(config: HallV62Config ,hub: RegistryShutdownHub, logger: Logger): DataSource = {
    val hikariConfig = new HikariConfig()
    //针对oracle特别处理
    hikariConfig.setConnectionTestQuery("select 1 from dual")
    hikariConfig.setDriverClassName(config.db.driver)
    hikariConfig.setJdbcUrl(config.db.url)
    hikariConfig.setUsername(config.db.user)
    hikariConfig.setPassword(config.db.password)
    //设置自动提交事务
    hikariConfig.setAutoCommit(false)

    hikariConfig.addDataSourceProperty("cachePrepStmts", "true")
    hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250")
    hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
    hikariConfig.setMaximumPoolSize(5)
    //hikariConfig.addDataSourceProperty("maximumPoolSize", "5")

//    new HikariDataSource(hikariConfig)
    val dataSource = new HikariDataSource(hikariConfig){
      override def getConnection: Connection = {
        new ConnectionSpy(super.getConnection)
      }
    }
    hub.addRegistryShutdownListener(new Runnable {
      override def run(): Unit = {
        dataSource.close()
      }
    })
    //用之前先升级数据库
    val driverClassName: String = config.db.driver
    val vendor = Vendor.forDriver(driverClassName)
    val databaseAdapter = DatabaseAdapter.forVendor(vendor, Option(config.db.user)) //Some(config.db.user))
    val migrator = new Migrator(dataSource, databaseAdapter)
    migrator.migrate(InstallAllMigrations, "nirvana.hall.api.migration", searchSubPackages = false)
    migrator.migrate(InstallAllMigrations, "nirvana.hall.v62.migration", searchSubPackages = false)

    dataSource
  }

  def bind(binder:ServiceBinder): Unit ={
    //同步数据服务器类
    binder.bind(classOf[FetchTPCardService], classOf[FetchTPCardServiceImpl])
    binder.bind(classOf[FetchLPCardService], classOf[FetchLPCardServiceImpl])
    binder.bind(classOf[FetchLPPalmService], classOf[FetchLPPalmServiceImpl])
    binder.bind(classOf[FetchCaseInfoService], classOf[FetchCaseInfoServiceImpl])
    binder.bind(classOf[FetchQueryService], classOf[FetchQueryServiceImpl])
    //其他Service
    binder.bind(classOf[FetchMatchRelationService],classOf[FetchMatchRelationServiceImpl])
    binder.bind(classOf[AssistCheckRecordService],classOf[AssistCheckRecordServiceImpl])
    binder.bind(classOf[GetPKIDService], classOf[GetPKIDServiceImpl])
    binder.bind(classOf[SyncInfoLogManageService], classOf[SyncInfoLogManageServiceImpl])
    binder.bind(classOf[LogicDBJudgeService], classOf[LogicDBJudgeServiceImpl])
    binder.bind(classOf[FPTFilterService],classOf[FPTFilterServiceImpl])
    binder.bind(classOf[FPTExchangeService],classOf[FPTExchangeServiceImpl])

  }
  def contributeEntityManagerFactory(configuration:Configuration[String]): Unit ={
    configuration.add("nirvana.hall.api.jpa")
  }
}

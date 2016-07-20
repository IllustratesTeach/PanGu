package nirvana.hall.v62

import javax.sql.DataSource

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import nirvana.hall.api.services.sync.{SyncCaseInfoService, SyncLPCardService, SyncTPCardService}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.sync.{SyncCaseInfoServiceImpl, SyncLPCardServiceImpl, SyncTPCardServiceImpl}
import org.apache.tapestry5.ioc.ServiceBinder
import org.apache.tapestry5.ioc.annotations.{EagerLoad, ServiceId}
import org.apache.tapestry5.ioc.services.RegistryShutdownHub
import org.slf4j.Logger

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

    new HikariDataSource(hikariConfig)
  }

  def bind(binder:ServiceBinder): Unit ={
    //同步数据服务器类
    binder.bind(classOf[SyncTPCardService], classOf[SyncTPCardServiceImpl])
    binder.bind(classOf[SyncLPCardService], classOf[SyncLPCardServiceImpl])
    binder.bind(classOf[SyncCaseInfoService], classOf[SyncCaseInfoServiceImpl])
  }
}

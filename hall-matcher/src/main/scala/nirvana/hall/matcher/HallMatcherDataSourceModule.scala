package nirvana.hall.matcher

import javax.sql.DataSource

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import nirvana.hall.matcher.config.HallMatcherConfig
import org.apache.tapestry5.ioc.annotations.{EagerLoad, ServiceId}
import org.apache.tapestry5.ioc.services.RegistryShutdownHub
import org.slf4j.Logger

/**
 * Created by songpeng on 16/3/30.
 */
object HallMatcherDataSourceModule {
  @EagerLoad
  @ServiceId("DataSource")
  def buildDataSource(config: HallMatcherConfig ,hub: RegistryShutdownHub, logger: Logger): DataSource = {
    val hikariConfig = new HikariConfig();
    //针对oracle特别处理
    hikariConfig.setConnectionTestQuery("select 1 from dual")
//    hikariConfig.setDriverClassName("oracle.jdbc.driver.OracleDriver")
//    hikariConfig.setJdbcUrl(System.getProperty(HallMatcherSymobls.JDBC_URL))
//    hikariConfig.setUsername(System.getProperty(HallMatcherSymobls.JDBC_USER))
//    hikariConfig.setPassword(System.getProperty(HallMatcherSymobls.JDBC_USER))
    hikariConfig.setDriverClassName(config.db.driver)
    hikariConfig.setJdbcUrl(config.db.url)
    hikariConfig.setUsername(config.db.user)
    hikariConfig.setPassword(config.db.password)
    //设置自动提交事务
    hikariConfig.setAutoCommit(false)

    hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
    hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
    hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    hikariConfig.setMaximumPoolSize(15)
    //hikariConfig.addDataSourceProperty("maximumPoolSize", "5")

    val dataSource = new HikariDataSource(hikariConfig)
    /*val dataSource = new HikariDataSource(hikariConfig){
      override def getConnection: Connection = {
        new ConnectionSpy(super.getConnection)
      }
    }*/
    hub.addRegistryShutdownListener(new Runnable {
      override def run(): Unit = {
        dataSource.close()
      }
    })

    dataSource
  }
}

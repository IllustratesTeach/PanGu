package nirvana.hall.matcher

import javax.sql.DataSource

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import org.apache.tapestry5.ioc.annotations.{EagerLoad, ServiceId}
import org.apache.tapestry5.ioc.services.RegistryShutdownHub
import org.slf4j.Logger

/**
 * Created by songpeng on 16/3/30.
 */
object HallMatcherDataSourceModule {
  @EagerLoad
  @ServiceId("DataSource")
  def buildDataSource(hub: RegistryShutdownHub, logger: Logger): DataSource = {
    val hikariConfig = new HikariConfig();
    //针对oracle特别处理
    hikariConfig.setConnectionTestQuery("select 1 from dual")
    hikariConfig.setDriverClassName("oracle.jdbc.driver.OracleDriver")

    hikariConfig.setJdbcUrl(System.getProperty(HallMatcherSymobls.JDBC_URL))
    hikariConfig.setUsername(System.getProperty(HallMatcherSymobls.JDBC_USER))
    hikariConfig.setPassword(System.getProperty(HallMatcherSymobls.JDBC_USER))
//    hikariConfig.setJdbcUrl("jdbc:oracle:thin:@10.1.7.213:1521:gafisdb")
//    hikariConfig.setUsername("gafis")
//    hikariConfig.setPassword("gafis")
    //设置自动提交事务
    hikariConfig.setAutoCommit(false)

    hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
    hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
    hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    hikariConfig.setMaximumPoolSize(5)
    //hikariConfig.addDataSourceProperty("maximumPoolSize", "5")

    new HikariDataSource(hikariConfig)
  }
}

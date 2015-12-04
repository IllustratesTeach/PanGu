package nirvana.hall.api

import java.net.URI
import java.sql.Connection
import javax.sql.DataSource

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import monad.migration.{DatabaseAdapter, InstallAllMigrations, Migrator, Vendor}
import net.sf.log4jdbc.ConnectionSpy
import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.internal.TransactionAdvice
import nirvana.hall.api.services.AutoSpringDataSourceSession
import org.apache.tapestry5.ioc.MethodAdviceReceiver
import org.apache.tapestry5.ioc.annotations.{EagerLoad, Local, Match, Startup}
import org.apache.tapestry5.ioc.services.{PerthreadManager, RegistryShutdownHub}
import org.slf4j.Logger
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.{AnnotationTransactionAttributeSource, Transactional}
import org.springframework.transaction.interceptor.TransactionInterceptor
import scalikejdbc.{GlobalSettings, LoggingSQLAndTimeSettings}

/**
 * 针对数据的操作
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-06
 */
object LocalDataSourceModule {
  @Startup
  def provideAutoSpringDataSource(dataSource: DataSource,perthreadManager: PerthreadManager): Unit ={
    AutoSpringDataSourceSession.dataSource = dataSource
    AutoSpringDataSourceSession.perthreadManager = perthreadManager
  }

  @EagerLoad
  def buildPlatformTransactionManager(dataSource: DataSource):PlatformTransactionManager = {
    new DataSourceTransactionManager(dataSource)
  }
  def buildTransactionInterceptor(@Local transactionManager: PlatformTransactionManager): TransactionInterceptor = {
    val transactionAttributeSource: AnnotationTransactionAttributeSource = new AnnotationTransactionAttributeSource
    val transactionInterceptor: TransactionInterceptor = new TransactionInterceptor(transactionManager, transactionAttributeSource)
    transactionInterceptor.afterPropertiesSet()
    transactionInterceptor
  }

  @Match(Array("*"))
  def adviseTransactional(receiver: MethodAdviceReceiver,
                          @Local transactionInterceptor: TransactionInterceptor) {
    for (m <- receiver.getInterface.getMethods) {
      if (receiver.getMethodAnnotation(m, classOf[Transactional]) != null) {
        receiver.adviseMethod(m, new TransactionAdvice(transactionInterceptor, m))
      }
    }
  }
  @EagerLoad
  def buildDataSource(config: HallApiConfig, hub: RegistryShutdownHub, logger: Logger): DataSource = {
    val hikariConfig = new HikariConfig();
    //针对heroku的mysql特别处理
    if (config.api.db.url.startsWith("mysql")) {
      val dbUri = new URI(config.api.db.url)
      val username = dbUri.getUserInfo.split(":")(0)
      val password = dbUri.getUserInfo.split(":")(1)
      val dbUrl = "jdbc:mysql://" + dbUri.getHost + dbUri.getPath + "?useUnicode=true&characterEncoding=utf-8"
      //hikariConfig.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource")
      hikariConfig.setDriverClassName(config.api.db.driver)
      hikariConfig.setJdbcUrl(dbUrl)
      hikariConfig.setUsername(username)
      hikariConfig.setPassword(password)
      hikariConfig.setConnectionTestQuery("select 1")

    }
    else {
      //针对oracle特别处理
      if(config.api.db.driver.startsWith("oracle")){
        hikariConfig.setConnectionTestQuery("select 1 from dual")
      }
      hikariConfig.setDriverClassName(config.api.db.driver)
      hikariConfig.setJdbcUrl(config.api.db.url)
      if (config.api.db.user != null)
        hikariConfig.setUsername(config.api.db.user);
      if (config.api.db.password != null)
        hikariConfig.setPassword(config.api.db.password);
    }
    //设置自动提交事务
    hikariConfig.setAutoCommit(false)

    hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
    hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
    hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    hikariConfig.setMaximumPoolSize(5)
    //hikariConfig.addDataSourceProperty("maximumPoolSize", "5")

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
    val driverClassName: String = config.api.db.driver
    val vendor = Vendor.forDriver(driverClassName)
    val databaseAdapter = DatabaseAdapter.forVendor(vendor, None) //Some(config.api.db.user))
    val migrator = new Migrator(dataSource, databaseAdapter)
    //migrator.migrate(RemoveAllMigrations, "nirvana.hall.api.migration", false)
    migrator.migrate(InstallAllMigrations, "nirvana.hall.api.migration", searchSubPackages = false)
    //构建ScalaJdbc的数据库pool
    //ConnectionPool.singleton(new DataSourceConnectionPool(dataSource))
    GlobalSettings.loggingSQLAndTime = new LoggingSQLAndTimeSettings(
      enabled = true,
      singleLineMode = true,
      logLevel = 'DEBUG)
    GlobalSettings.jtaDataSourceCompatible = true

    AutoSpringDataSourceSession.driverName = Some(config.api.db.driver)

    dataSource
  }
}

package nirvana.hall.database

import java.net.URI
import java.sql.Connection
import javax.sql.DataSource

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import net.sf.log4jdbc.ConnectionSpy
import nirvana.hall.database.config.HallDatabaseConfig
import org.apache.tapestry5.ioc.annotations.EagerLoad
import org.apache.tapestry5.ioc.services.RegistryShutdownHub
import org.slf4j.Logger
import stark.migration.{DatabaseAdapter, InstallAllMigrations, Migrator, Vendor}

object HallDatabaseModule {

  def buildHallDatabaseConfig={
    val driver = System.getProperty("jdbc.driver")
    val url = System.getProperty("jdbc.url")
    val user = System.getProperty("jdbc.user")
    val password = System.getProperty("jdbc.pass")
    val config = new HallDatabaseConfig
    config.db.driver = driver
    config.db.url = url
    config.db.user = user
    config.db.password = password

    config
  }
  @EagerLoad
  def buildDataSource(config: HallDatabaseConfig, hub : RegistryShutdownHub, logger: Logger): DataSource = {
    val hikariConfig = new HikariConfig();
    //针对heroku的mysql特别处理
    if (config.db.url.startsWith("mysql")) {
      val dbUri = new URI(config.db.url)
      val username = dbUri.getUserInfo.split(":")(0)
      val password = dbUri.getUserInfo.split(":")(1)
      val dbUrl = "jdbc:mysql://" + dbUri.getHost + dbUri.getPath + "?useUnicode=true&characterEncoding=utf-8"
      //hikariConfig.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource")
      hikariConfig.setDriverClassName(config.db.driver)
      hikariConfig.setJdbcUrl(dbUrl)
      hikariConfig.setUsername(username)
      hikariConfig.setPassword(password)
      hikariConfig.setConnectionTestQuery("select 1")

    }
    else {
      //针对oracle特别处理
      if(config.db.driver.startsWith("oracle")){
        hikariConfig.setConnectionTestQuery("select 1 from dual")
      }
      hikariConfig.setDriverClassName(config.db.driver)
      hikariConfig.setJdbcUrl(config.db.url)
      if (config.db.user != null)
        hikariConfig.setUsername(config.db.user);
      if (config.db.password != null)
        hikariConfig.setPassword(config.db.password);
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
    val driverClassName: String = config.db.driver
    val vendor = Vendor.forDriver(driverClassName)
    val databaseAdapter = DatabaseAdapter.forVendor(vendor, Option(config.db.user)) //Some(config.db.user)); 如果是H2不设置schema（None）
    val migrator = new Migrator(dataSource, databaseAdapter)
    migrator.migrate(InstallAllMigrations, "nirvana.hall.database.migration", searchSubPackages = false)

    dataSource
  }
}

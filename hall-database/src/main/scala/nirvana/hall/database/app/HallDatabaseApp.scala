package nirvana.hall.database.app

import java.net.URI

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import joptsimple.{OptionException, OptionParser, OptionSet}
import monad.core.config.DatabaseConfig
import stark.migration.{DatabaseAdapter, InstallAllMigrations, Migrator, Vendor}

object HallDatabaseApp {

  def main(args: Array[String]): Unit = {
    val parser = new OptionParser()
    parser.accepts("url").withRequiredArg().describedAs("jdbc url").required()
    parser.accepts("user").withRequiredArg().describedAs("jdbc user").required()
    parser.accepts("pass").withRequiredArg().describedAs("jdbc password").required()
    var options:OptionSet = null
    try {
      options = parser.parse(args:_ *)
      if(options.has("?") || options.has("h")){//帮助信息
        parser.printHelpOn(System.out)
        return
      }
    }catch {
      case e: OptionException =>
        parser.printHelpOn(System.out)
        return
    }
    val url = options.valueOf("url").asInstanceOf[String]
    val user = options.valueOf("user").asInstanceOf[String]
    val pass = options.valueOf("pass").asInstanceOf[String]

    val db = new DatabaseConfig
    db.driver = "oracle.jdbc.driver.OracleDriver"
    db.url = url
    db.user = user
    db.password = pass
    val dataSource = buildHikariDataSource(db)
    try{
      //升级数据库
      val driverClassName: String = db.driver
      val vendor = Vendor.forDriver(driverClassName)
      val databaseAdapter = DatabaseAdapter.forVendor(vendor, Option(db.user))
      val migrator = new Migrator(dataSource, databaseAdapter)
      migrator.migrate(InstallAllMigrations, "nirvana.hall.database.migration", searchSubPackages = false)
    }finally {
      dataSource.close()
    }
  }
  def buildHikariDataSource(db: DatabaseConfig): HikariDataSource = {
    val hikariConfig = new HikariConfig();
    //针对heroku的mysql特别处理
    if (db.url.startsWith("mysql")) {
      val dbUri = new URI(db.url)
      val username = dbUri.getUserInfo.split(":")(0)
      val password = dbUri.getUserInfo.split(":")(1)
      val dbUrl = "jdbc:mysql://" + dbUri.getHost + dbUri.getPath + "?useUnicode=true&characterEncoding=utf-8"
      hikariConfig.setDriverClassName(db.driver)
      hikariConfig.setJdbcUrl(dbUrl)
      hikariConfig.setUsername(username)
      hikariConfig.setPassword(password)
      hikariConfig.setConnectionTestQuery("select 1")

    } else {
      //针对oracle特别处理
      if(db.driver.startsWith("oracle")){
        hikariConfig.setConnectionTestQuery("select 1 from dual")
      }
      hikariConfig.setDriverClassName(db.driver)
      hikariConfig.setJdbcUrl(db.url)
      if (db.user != null)
        hikariConfig.setUsername(db.user);
      if (db.password != null)
        hikariConfig.setPassword(db.password);
    }
    //设置自动提交事务
    hikariConfig.setAutoCommit(false)

    hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
    hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
    hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    hikariConfig.setMaximumPoolSize(5)

    new HikariDataSource(hikariConfig)
  }
}

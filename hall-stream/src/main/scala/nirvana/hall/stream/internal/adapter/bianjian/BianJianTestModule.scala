package nirvana.hall.stream.internal.adapter.bianjian

import javax.sql.DataSource

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import nirvana.hall.stream.config.{NirvanaHallStreamConfig, HallStreamConfig}
import nirvana.hall.stream.internal.{HttpExtractService, HttpDecompressService}
import nirvana.hall.stream.services.{ExtractService, DecompressService, FeatureSaverService}
import nirvana.hall.support.services.RpcHttpClient
import org.apache.tapestry5.ioc.ServiceBinder
import org.apache.tapestry5.ioc.annotations._
import org.apache.tapestry5.ioc.services.RegistryShutdownHub
import org.slf4j.Logger
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource
import org.springframework.transaction.interceptor.TransactionInterceptor

/**
 * 针对数据的操作
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-06
 */
object BianjianTestModule {
  @Startup
  def startStream(bianjianStream:BianjianStream): Unit ={
    bianjianStream.startStream()
  }
  def bind(binder:ServiceBinder): Unit ={
    binder.bind(classOf[BianjianStream]).withId("BianjianStream").eagerLoad()
    binder.bind(classOf[FeatureSaverService],classOf[BianjianFeatureSaverService]).withId("BianjianFeatureSaverService")
  }
  @EagerLoad
  def buildDecompressService(rpcHttpClient:RpcHttpClient): DecompressService={
    val url = System.getProperty(BianjianTestSymobls.RPC_IMAGE_URL)
    new HttpDecompressService(url,rpcHttpClient)
  }
  @EagerLoad
  def buildExtractService(rpcHttpClient: RpcHttpClient,config:NirvanaHallStreamConfig):ExtractService={
    val url = System.getProperty(BianjianTestSymobls.RPC_EXTRACT_URL)
    new HttpExtractService(url,rpcHttpClient,config)
  }
  @EagerLoad
  def buildPlatformTransactionManager(@InjectService("MntDataSource") dataSource: DataSource):PlatformTransactionManager = {
    new DataSourceTransactionManager(dataSource)
  }
  def buildTransactionInterceptor(@Local transactionManager: PlatformTransactionManager): TransactionInterceptor = {
    val transactionAttributeSource: AnnotationTransactionAttributeSource = new AnnotationTransactionAttributeSource
    val transactionInterceptor: TransactionInterceptor = new TransactionInterceptor(transactionManager, transactionAttributeSource)
    transactionInterceptor.afterPropertiesSet()
    transactionInterceptor
  }

  @EagerLoad
  @ServiceId("ImgDataSource")
  def buildDataSource(hub: RegistryShutdownHub, logger: Logger): DataSource = {
    val hikariConfig = new HikariConfig();
    //针对oracle特别处理
    hikariConfig.setConnectionTestQuery("select 1 from dual")
    hikariConfig.setDriverClassName("oracle.jdbc.driver.OracleDriver")

    hikariConfig.setJdbcUrl(System.getProperty(BianjianTestSymobls.IMG_JDBC_URL))
    hikariConfig.setUsername(System.getProperty(BianjianTestSymobls.IMG_JDBC_USER))
    hikariConfig.setPassword(System.getProperty(BianjianTestSymobls.IMG_JDBC_PASS))
    //设置自动提交事务
    hikariConfig.setAutoCommit(false)

    hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
    hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
    hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    hikariConfig.setMaximumPoolSize(5)
    //hikariConfig.addDataSourceProperty("maximumPoolSize", "5")

    new HikariDataSource(hikariConfig)
  }
  @EagerLoad
  @ServiceId("MntDataSource")
  def buildMntDataSource(hub: RegistryShutdownHub, logger: Logger): DataSource = {
    val hikariConfig = new HikariConfig();
    //针对oracle特别处理
    hikariConfig.setConnectionTestQuery("select 1 from dual")
    hikariConfig.setDriverClassName("oracle.jdbc.driver.OracleDriver")

    hikariConfig.setJdbcUrl(System.getProperty(BianjianTestSymobls.MNT_JDBC_URL))
    hikariConfig.setUsername(System.getProperty(BianjianTestSymobls.MNT_JDBC_USER))
    hikariConfig.setPassword(System.getProperty(BianjianTestSymobls.MNT_JDBC_PASS))
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

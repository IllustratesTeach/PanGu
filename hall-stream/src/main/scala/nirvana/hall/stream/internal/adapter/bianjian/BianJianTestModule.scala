package nirvana.hall.stream.internal.adapter.bianjian

import javax.sql.DataSource

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import nirvana.hall.stream.internal.{HttpExtractService, HttpDecompressService}
import nirvana.hall.stream.services.{ExtractService, RpcHttpClient, DecompressService, FeatureSaverService}
import org.apache.tapestry5.ioc.ServiceBinder
import org.apache.tapestry5.ioc.annotations.{EagerLoad, Local}
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
  def bind(binder:ServiceBinder): Unit ={
    binder.bind(classOf[BianjianStream]).withId("BianjianStream")
    binder.bind(classOf[FeatureSaverService],classOf[BianjianFeatureSaverService]).withId("BianjianFeatureSaverService")
  }
  def buildDecompressService(rpcHttpClient:RpcHttpClient): DecompressService={
    val url = System.getProperty(BianjianTestSymobls.RPC_IMAGE_URL)
    new HttpDecompressService(url,rpcHttpClient)
  }
  def buildExtractService(rpcHttpClient: RpcHttpClient):ExtractService={
    val url = System.getProperty(BianjianTestSymobls.RPC_EXTRACT_URL)
    new HttpExtractService(url,rpcHttpClient)
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

  @EagerLoad
  def buildDataSource(hub: RegistryShutdownHub, logger: Logger): DataSource = {
    val hikariConfig = new HikariConfig();
    //针对oracle特别处理
    hikariConfig.setConnectionTestQuery("select 1 from dual")
    hikariConfig.setDriverClassName("com.oracle.jdbc.Driver")

    hikariConfig.setJdbcUrl(System.getProperty(BianjianTestSymobls.JDBC_URL))
    hikariConfig.setUsername(System.getProperty(BianjianTestSymobls.JDBC_USER))
    hikariConfig.setPassword(System.getProperty(BianjianTestSymobls.JDBC_PASS))
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

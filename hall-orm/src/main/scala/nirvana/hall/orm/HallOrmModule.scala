package nirvana.hall.orm

import java.util
import java.util.Properties
import javax.persistence.{EntityManager, EntityManagerFactory}
import javax.sql.DataSource

import nirvana.hall.orm.config.HallOrmConfigSupport
import nirvana.hall.orm.internal.{EntityManagerTransactionAdvice, EntityServiceImpl}
import nirvana.hall.orm.services.{ActiveRecord, EntityService}
import org.apache.tapestry5.ioc.annotations._
import org.apache.tapestry5.ioc.{MethodAdviceReceiver, ObjectLocator, ServiceBinder}
import org.slf4j.Logger
import org.springframework.orm.jpa.support.SharedEntityManagerBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.orm.jpa.{JpaTransactionManager, LocalContainerEntityManagerFactoryBean}
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.{AnnotationTransactionAttributeSource, Transactional}
import org.springframework.transaction.interceptor.TransactionInterceptor

/**
 * hall orm module
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-02
 */
object HallOrmModule {
  def bind(binder:ServiceBinder): Unit ={
    binder.bind(classOf[EntityService],classOf[EntityServiceImpl])
  }
  def buildEntityManagerFactory(dataSource: DataSource,
                                configuration: util.Collection[String],
                                ormConfig:HallOrmConfigSupport,
                                objectLocator: ObjectLocator):EntityManagerFactory= {
    val entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean
    val adapter = new HibernateJpaVendorAdapter

    entityManagerFactoryBean.setJpaVendorAdapter(adapter)
    entityManagerFactoryBean.setDataSource(dataSource)

    val packages = configuration.toArray(new Array[String](configuration.size()))
    entityManagerFactoryBean.setPackagesToScan(packages:_*)

    val properties = new Properties
    val it = ormConfig.jpaProperties.iterator()
    while(it.hasNext){
      val jpaProperty = it.next()
      properties.put(jpaProperty.name,jpaProperty.value)
    }

    ActiveRecord.objectLocator = objectLocator
    entityManagerFactoryBean.setJpaProperties(properties)
    entityManagerFactoryBean.afterPropertiesSet()
    entityManagerFactoryBean.getObject
  }

  //@Scope(ScopeConstants.PERTHREAD)
  def buildEntityManager(logger: Logger,@Local entityManagerFactory: EntityManagerFactory):EntityManager={
    val shared = new SharedEntityManagerBean()
    shared.setEntityManagerFactory(entityManagerFactory)
    shared.setEntityManagerInterface(classOf[EntityManager])
    shared.afterPropertiesSet()

    shared.getObject
  }
  @Startup
  def provideObjectLocator(objectLocator: ObjectLocator)={
    ActiveRecord.objectLocator = objectLocator
  }
  def buildJpaTransactionManager(entityManagerFactory:EntityManagerFactory,@Local entityManager: EntityManager):PlatformTransactionManager={
    val transactionManager = new JpaTransactionManager()
    transactionManager.setEntityManagerFactory(entityManagerFactory)
    transactionManager.afterPropertiesSet()

    transactionManager
  }

   def buildTransactionInterceptor(transactionManager: PlatformTransactionManager): TransactionInterceptor = {
    val transactionAttributeSource = new AnnotationTransactionAttributeSource
    val transactionInterceptor = new TransactionInterceptor(transactionManager, transactionAttributeSource)
    transactionInterceptor.afterPropertiesSet()
    transactionInterceptor
  }

  @Match(Array("*"))
  def adviseTransactional(receiver: MethodAdviceReceiver, transactionInterceptor: TransactionInterceptor) {
    for (m <- receiver.getInterface.getMethods) {
      if (receiver.getMethodAnnotation(m, classOf[Transactional]) != null)
        receiver.adviseMethod(m, new EntityManagerTransactionAdvice(transactionInterceptor, m))
    }
  };
}

package nirvana.hall.orm

import java.io.File
import javax.persistence._
import javax.sql.DataSource

import nirvana.hall.orm.config.{HallOrmConfigSupport, JpaProperty}
import nirvana.hall.orm.services.{ActiveRecord, ActiveRecordInstance}
import org.apache.tapestry5.ioc.{Configuration, Registry, RegistryBuilder}
import org.junit.Before
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.{EntityManagerFactoryUtils, EntityManagerHolder}
import org.springframework.transaction.support.TransactionSynchronizationManager
import org.springframework.util.FileSystemUtils

import scala.reflect.{ClassTag, classTag}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-03
 */
class BaseOrmTestCase {

  private var registry:Registry = _
  protected def getService[T:ClassTag]:T={
    registry.getService(classTag[T].runtimeClass.asInstanceOf[Class[T]])
  }
  @Before
  def setup: Unit ={
    val modules = Seq[String](
      "nirvana.hall.orm.HallOrmModule",
      "nirvana.hall.orm.TestDataModule").map(Class.forName)
    registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)
    //OpenSession In Thread
    val entityManagerFactory= getService[EntityManagerFactory]
    val em = getService[EntityManager]
    val emHolder= new EntityManagerHolder(em)
    TransactionSynchronizationManager.bindResource(entityManagerFactory, emHolder)
  }
  def down: Unit ={
    val emf: EntityManagerFactory = registry.getService(classOf[EntityManagerFactory])
    val emHolder: EntityManagerHolder = TransactionSynchronizationManager.unbindResource(emf).asInstanceOf[EntityManagerHolder]
    EntityManagerFactoryUtils.closeEntityManager(emHolder.getEntityManager)

    registry.shutdown()
  }
}
object ModelA extends ActiveRecordInstance[ModelA]{
}
@Entity
@Table(name = "model_a")
@javax.persistence.SequenceGenerator(name = "entity_a_seq", sequenceName = "entity_a_seq")
class ModelA extends ActiveRecord{
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity_a_seq")
  @Column(name = "id")
  var id:Int = _
  var name:String = _
}
object TestDataModule{
  def buildDataSource: DataSource ={
    val dbPath = "target/test.db"
    FileSystemUtils.deleteRecursively(new File(dbPath))
    val dataSource = new DriverManagerDataSource("jdbc:h2:file:"+dbPath+"/xx","sa",null)
    dataSource
  }
  def contributeEntityManagerFactory(configuration:Configuration[String]): Unit ={
    configuration.add("nirvana.hall.orm")
  }
  def buildHallOrmConfigSupport: HallOrmConfigSupport={
    val support = new HallOrmConfigSupport {}
    var jpaProperty = new JpaProperty
    jpaProperty.name = "hibernate.show_sql"
    jpaProperty.value="true"
    support.jpaProperties.add(jpaProperty)
    jpaProperty = new JpaProperty
    jpaProperty.name = "hibernate.hbm2ddl.auto"
    jpaProperty.value="create"
    support.jpaProperties.add(jpaProperty)

    support
  }
}
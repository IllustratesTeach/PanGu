package nirvana.hall.database.app

import javax.persistence.EntityManagerFactory

import org.apache.tapestry5.ioc.RegistryBuilder
import org.springframework.orm.jpa.{EntityManagerFactoryUtils, EntityManagerHolder}
import org.springframework.transaction.support.TransactionSynchronizationManager

object HallDatabaseApp {

  def main(args: Array[String]): Unit = {
    val modules = Seq[String](
      "stark.activerecord.StarkActiveRecordModule",
      "nirvana.hall.database.HallDatabaseModule"
    ).map(Class.forName)
    System.setProperty("jdbc.driver","oracle.jdbc.driver.OracleDriver")
    System.setProperty("jdbc.url","jdbc:oracle:thin:@192.168.1.10:1521:xe")
    System.setProperty("jdbc.user","gafis_nj")
    System.setProperty("jdbc.pass","gafis")

    val registry = RegistryBuilder.buildAndStartupRegistry(modules: _*)

    val emf: EntityManagerFactory = registry.getService(classOf[EntityManagerFactory])
    val emHolder: EntityManagerHolder = TransactionSynchronizationManager.unbindResource(emf).asInstanceOf[EntityManagerHolder]
    EntityManagerFactoryUtils.closeEntityManager(emHolder.getEntityManager)

    registry.shutdown()
  }
}

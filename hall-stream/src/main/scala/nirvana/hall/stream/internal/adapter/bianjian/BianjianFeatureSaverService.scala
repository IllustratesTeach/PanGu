package nirvana.hall.stream.internal.adapter.bianjian

import javax.sql.DataSource

import com.google.protobuf.ByteString
import monad.support.services.LoggerSupport
import nirvana.hall.stream.services.FeatureSaverService
import nirvana.hall.support.services.JdbcDatabase
import org.apache.tapestry5.ioc.annotations.InjectService
import org.springframework.transaction.PlatformTransactionManager

/**
 * bianjian feature saver test
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-16
 */
class BianjianFeatureSaverService(@InjectService("MntDataSource") dataSource:DataSource,platformTransactionManager: PlatformTransactionManager)
  extends FeatureSaverService
  with LoggerSupport {
  /**
   * save feature data
   * @param id data id
   * @param feature feature data
   */
  override def save(id: Any, feature: ByteString): Unit = {
    debug("save record with id {}",id)
    val sql: String = "insert into gafis_mnt (csid, mnt, seq) values(?,?, gafis_mnt_seq.nextval)"
    implicit val ds = dataSource
    JdbcDatabase.update(sql){ps=>
      ps.setLong(1, id.asInstanceOf[Long])
      ps.setBytes(2, feature.toByteArray)
    }
  }
}

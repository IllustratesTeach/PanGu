package nirvana.hall.stream.internal.adapter.bianjian

import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.stream.internal.JdbcDatabase
import nirvana.hall.stream.services.FeatureSaverService
import org.apache.tapestry5.ioc.annotations.InjectService

/**
 * bianjian feature saver test
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-16
 */
class BianjianFeatureSaverService(@InjectService("MntDataSource") dataSource:DataSource) extends FeatureSaverService{
  /**
   * save feature data
   * @param id data id
   * @param feature feature data
   */
  override def save(id: Any, feature: ByteString): Unit = {
    val sql: String = "insert into gafis_mnt (csid, mnt, seq) values(?,?,select FROM gafis_mnt_seq.nextval)"
    implicit val ds = dataSource
    JdbcDatabase.update(sql){ps=>
      ps.setLong(1, id.asInstanceOf[Long])
      ps.setBytes(2, feature.toByteArray)
    }
  }
}

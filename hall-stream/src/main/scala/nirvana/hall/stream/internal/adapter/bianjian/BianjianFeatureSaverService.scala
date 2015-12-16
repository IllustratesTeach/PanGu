package nirvana.hall.stream.internal.adapter.bianjian

import java.sql.{Connection, PreparedStatement}
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.stream.services.FeatureSaverService

/**
 * bianjian feature saver test
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-16
 */
class BianjianFeatureSaverService(dataSource:DataSource) extends FeatureSaverService{
  /**
   * save feature data
   * @param id data id
   * @param feature feature data
   */
  override def save(id: Any, feature: ByteString): Unit = {
    val conn: Connection = dataSource.getConnection()
    var ps: PreparedStatement = null
    val sql: String = "insert into gafis_mnt (csid, mnt, seq) values(?,?,select FROM gafis_mnt_seq.nextval)"
    ps = conn.prepareStatement(sql)
    ps.setLong(1, id.asInstanceOf[Long])
    ps.setBytes(2, feature.toByteArray)
    ps.executeUpdate
    ps.close()
    conn.close()
  }
}

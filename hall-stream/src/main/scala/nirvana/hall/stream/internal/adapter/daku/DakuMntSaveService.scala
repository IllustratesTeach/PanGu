package nirvana.hall.stream.internal.adapter.daku

import java.io.{File, PrintWriter}
import javax.sql.DataSource

import com.google.protobuf.ByteString
import monad.support.services.LoggerSupport
import nirvana.hall.stream.services.FeatureSaverService
import nirvana.hall.support.services.JdbcDatabase
import org.apache.tapestry5.ioc.annotations.InjectService
import org.springframework.transaction.PlatformTransactionManager

/**
 * Created by wangjue on 2015/12/29.
 */
class DakuMntSaveService(@InjectService("MntDataSource") dataSource:DataSource,platformTransactionManager: PlatformTransactionManager)
  extends FeatureSaverService
  with LoggerSupport {

    /**
     * save feature data
     * @param id data id
     * @param feature feature data
     */
    override def save(id: Any, feature: ByteString): Unit = {
      val saveFingerSql : String = "insert into gafis_gather_finger(pk_id,person_id,fgp,fgp_case,group_id,lobtype,inputtime,seq,gather_data)" +
        "values(sys_guid(),?,?,?,0,2,sysdate,gafis_gather_finger_seq.nextval,?)"
      debug("save record with id {}",id)
      val arr = id.asInstanceOf[String].split("_")
      val personId = arr(0)
      val fgp = arr(1).toInt
      var fgpDB = fgp
      if (fgpDB > 10) fgpDB -= 10
      var fgpCase = 0
      if (fgp > 10) fgpCase = 1
      implicit val ds = dataSource
      JdbcDatabase.update(saveFingerSql) { ps =>
        ps.setString(1,personId)
        ps.setInt(2,fgpDB)
        ps.setInt(3,fgpCase)
        ps.setBytes(4,feature.toByteArray)
      }
      
    }



}

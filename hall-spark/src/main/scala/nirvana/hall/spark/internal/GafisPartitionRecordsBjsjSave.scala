package nirvana.hall.spark.internal

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.FptPropertiesConverter.{PersonConvert, TemplateFingerConvert}
import nirvana.hall.spark.services.{FingerConstants, PartitionRecordsSaver, SysProperties}
import nirvana.hall.spark.services.SparkFunctions.{StreamError, StreamEvent}
import nirvana.hall.support.services.JdbcDatabase
import org.slf4j.LoggerFactory

/**
  * Created by wangjue on 2017/12/14.
  */
class GafisPartitionRecordsBjsjSave extends PartitionRecordsSaver{
  import GafisPartitionRecordsBjsjSave._
  override def savePartitionRecords(parameter: NirvanaSparkConfig)(records: Iterator[(StreamEvent, TemplateFingerConvert, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)]): Unit = {
    if (records != null) {
      records.foreach{ case(event,image,mnt,bin) =>
        if (image.gatherData != null && image.gatherData.length > 0)
          saveTemplatePalm(image)
        if (mnt.bnData != null && mnt.bnData.length > 0) {
          image.gatherData = mnt.toByteArray()
          image.groupId = FingerConstants.GROUP_MNT
          image.lobType = FingerConstants.LOBTYPE_MNT
          saveTemplatePalm(image)
        }
      }
    }
  }
}

object GafisPartitionRecordsBjsjSave {
  private val logger = LoggerFactory getLogger getClass
  lazy implicit val dataSource = SysProperties.getDataSource("gafis")

  case class DbError(streamEvent: StreamEvent, message: String) extends StreamError(streamEvent) {
    override def getMessage: String = "S|" + message
  }

  def queryPalmByPersonId(personId : String) : List[Array[Int]] = {
    var list : List[Array[Int]] = List()
    JdbcDatabase.queryWithPsSetter("SELECT t.fgp,t.group_id FROM gafis_gather_palm t WHERE t.person_id = ? "){ps =>
      ps.setString(1,personId)
    }{rs=>
      val array = Array(rs.getInt("fgp"),rs.getInt("group_id"))
      list = array :: list
    }
    list
  }

  //查询人员主表信息
  def queryPersonById(personId: String): Option[String] = {
    JdbcDatabase.queryFirst("select personid from gafis_person where personid = ?") { ps =>
      ps.setString(1, personId)
    } { rs =>
      rs.getString("personid")
    }
  }

  def savePersonInfo(person : PersonConvert): Unit = {
    val savePersonSql = "insert into gafis_person(personid,sid,seq,deletag,data_sources,fingershow_status,inputtime,gather_date,data_in,fpt_path) " +
      "values(?,gafis_person_sid_seq.nextval,gafis_person_seq.nextval,1,5,1,sysdate,sysdate,2,?)"
    JdbcDatabase.update(savePersonSql) { ps =>
      ps.setString(1, person.personId)
      ps.setString(2, person.fptPath)
    }
  }

  def saveTemplatePalm(palm : TemplateFingerConvert): Unit = {
    val saveFingerSql: String = "insert into gafis_gather_palm(pk_id,person_id,fgp,group_id,lobtype,inputtime,seq,gather_data)" +
      "values(sys_guid(),?,?,?,?,sysdate,gafis_gather_palm_seq.nextval,?)"
    //保存指纹特征信息
    JdbcDatabase.update(saveFingerSql) { ps =>
      ps.setString(1, palm.personId)
      ps.setString(2, palm.fgp)
      ps.setString(3, palm.groupId)
      ps.setString(4,palm.lobType)
      ps.setBytes(5, palm.gatherData)
    }
  }

}
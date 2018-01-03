package nirvana.hall.spark.internal

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.FptPropertiesConverter._
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

  def queryPalmByPersonIdAndGroupIdAndFgp(personId : String, groupId : String, fgp : String): Option[String] ={
    JdbcDatabase.queryFirst("SELECT t.person_id FROM gafis_gather_palm t WHERE t.person_id = ? AND t.group_id = ? AND t.fgp = ?") { ps =>
      ps.setString(1, personId)
      ps.setString(2, groupId)
      ps.setString(3, fgp)
    } { rs =>
      rs.getString("person_id")
    }
  }

  def saveTemplatePalm(palm : TemplateFingerConvert): Unit = {
    val hasPalm = queryPalmByPersonIdAndGroupIdAndFgp(palm.personId,palm.groupId,palm.fgp)
    if (hasPalm.isEmpty) {
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


  /***************************************************************************LATENT******************************************************************************/
  def saveLatent(latentCaseConvert: LatentCaseConvert): Unit ={
    val hasLatentCase = updateLatentCase(latentCaseConvert.caseId)
    if (hasLatentCase==0) saveLatnetCase(latentCaseConvert)
    latentCaseConvert.latentFingers.foreach { finger =>
      val hasLatentFinger = updateLatentPalm(finger.fingerId)
      if (hasLatentFinger==0 && finger.imgData != null) saveLatentFinger(finger)
      finger.LatentFingerFeatures.foreach{ feature =>
        val hasLatentFingerFeature = updateLatentPalmFeature(feature.fingerId,feature.fingerMnt)
        if (hasLatentFingerFeature==0 && feature.fingerMnt != null) saveLatnentFingerFeature(feature)
      }
    }
  }

  def updateLatentCase(caseId : String) : Int = {
    val updateLatentCaseSql = "UPDATE gafis_case t SET t.modifiedtime = SYSDATE WHERE t.case_id = ?"
    JdbcDatabase.update(updateLatentCaseSql) { ps =>
      ps.setString(1,caseId)
    }
  }

  def saveLatnetCase(latentCaseConvert: LatentCaseConvert): Unit ={
    val saveCaseSql = "insert into GAFIS_CASE(CASE_ID,Inputtime,DELETAG,Case_Source,Is_Checked) values(?,sysdate,1,5,0)"
    JdbcDatabase.update(saveCaseSql) { ps =>
      ps.setString(1, latentCaseConvert.caseId)
    }
  }

  def updateLatentPalm(palmId : String) : Int = {
    val updateLatnetFingerSql = "UPDATE gafis_case_palm t SET t.modifiedtime = SYSDATE WHERE t.palm_id = ?"
    JdbcDatabase.update(updateLatnetFingerSql) { ps =>
      ps.setString(1,palmId)
    }
  }

  def saveLatentFinger(latentFingerConvert : LatentFingerConvert): Unit ={
    val saveCasePalmSql = "insert into GAFIS_CASE_PALM(SEQ_NO,PALM_ID,CASE_ID,PALM_IMG,INPUTTIME,Deletag,Sid,CREATOR_UNIT_CODE,INPUTPSN)" +
      "values(?,?,?,?,sysdate,1,GAFIS_CASE_FINGER_PALM_SEQ.NEXTVAL,'520000000000','1')"
    JdbcDatabase.update(saveCasePalmSql) { ps =>
      ps.setString(1, latentFingerConvert.seqNo)
      ps.setString(2, latentFingerConvert.fingerId)
      ps.setString(3, latentFingerConvert.caseId)
      ps.setBytes(4, latentFingerConvert.imgData)
    }
  }

  def updateLatentPalmFeature(palmId : String,palmMnt : Array[Byte]) : Int = {
    val updateLatnetpalmFeatureSql = "UPDATE gafis_case_palm_mnt t SET t.modifiedtime = SYSDATE WHERE t.palm_id = ? AND t.is_main_mnt = 1"
    JdbcDatabase.update(updateLatnetpalmFeatureSql) { ps =>
      ps.setString(1,palmId)
    }
  }

  def saveLatnentFingerFeature(latentFingerFeatureConvert: LatentFingerFeatureConvert): Unit ={
    val saveLatentFingerFeatureSql = "INSERT INTO gafis_case_palm_mnt(pk_id,palm_id,palm_mnt,Is_Main_Mnt,Inputtime) " +
      "VALUES(sys_guid(),?,?,'1',SYSDATE)"
    JdbcDatabase.update(saveLatentFingerFeatureSql){ ps =>
      ps.setString(1,latentFingerFeatureConvert.fingerId)
      ps.setBytes(2,latentFingerFeatureConvert.fingerMnt)
    }



  }

}
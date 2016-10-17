package nirvana.hall.spark.internal

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.{PartitionRecordsSaver, SysProperties, FingerConstants}
import nirvana.hall.spark.services.FptPropertiesConverter._
import nirvana.hall.spark.services.SparkFunctions.{StreamError, StreamEvent}
import nirvana.hall.support.services.JdbcDatabase
import org.slf4j.LoggerFactory

/**
  * Created by wangjue on 2016/9/29.
  */
class

GafisPartitionRecordsDakuSaver extends PartitionRecordsSaver  {
  import GafisPartitionRecordsFullSaver._
  override def savePartitionRecords(parameter: NirvanaSparkConfig)(records: Iterator[(StreamEvent, TemplateFingerConvert, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)]): Unit = {
    records.foreach{ case(event,image,mnt,bin) =>
      if (image.gatherData!=null)
        saveTemplateFinger(image)
      saveTemplateFingerMntAndBin(image,FingerConstants.GROUP_MNT,FingerConstants.LOBTYPE_MNT,mnt.toByteArray())//object,groupId,lobType,date
      if (bin!=null && parameter.isExtractorBin)
        saveTemplateFingerMntAndBin(image,FingerConstants.GROUP_BIN,FingerConstants.LOBTYPE_MNT,bin.toByteArray())
    }
  }
}

object GafisPartitionRecordsDakuSaver {
  private val logger = LoggerFactory getLogger getClass
  lazy implicit val dataSource = SysProperties.getDataSource("gafis")
  case class DbError(streamEvent: StreamEvent,message:String) extends StreamError(streamEvent) {
    override def getMessage: String = "S|"+message
  }



  //查询人员主表信息
  def queryPersonById(personId : String) : Option[String] = {
    JdbcDatabase.queryFirst("select personid from gafis_person where personid = ?"){ps =>
      ps.setString(1,personId)
    }{rs=>
      rs.getString("personid")
    }
  }


  //数据是否存在
  def queryFingerFgpAndFgpCaseByPersonId(personId : String) : List[Array[Int]] = {
    var list : List[Array[Int]] = List()
    JdbcDatabase.queryWithPsSetter("select t.fgp,t.fgp_case from gafis_gather_finger t where t.person_id = ? and t.group_id = 0"){ps =>
      ps.setString(1,personId)
    }{rs=>
      val array = Array(rs.getInt("fgp"),rs.getInt("fgp_case"))
      list = array :: list
    }
    list
  }

  def saveFullPersonInfo(person : PersonConvert): Unit ={
    val saveFullPersonInfo = "INSERT INTO GAFIS_PERSON(PERSONID,CARDID,NAME,ALIASNAME,SEX_CODE,BIRTHDAYST,IDCARDNO,DOOR,DOORDETAIL," +
      "ADDRESS,ADDRESSDETAIL,PERSON_CATEGORY,CASE_CLASSES,GATHERDEPARTCODE,GATHERDEPARTNAME,GATHERUSERNAME," +
      "GATHER_DATE,REMARK,NATIVEPLACE_CODE,NATION_CODE,ASSIST_LEVEL,ASSIST_BONUS,ASSIST_PURPOSE,ASSIST_REF_PERSON," +
      "ASSIST_REF_CASE,ASSIST_VALID_DATE,ASSIST_EXPLAIN,ASSIST_DEPT_CODE,ASSIST_DEPT_NAME,ASSIST_DATE,ASSIST_CONTACTS," +
      "ASSIST_NUMBER,ASSIST_APPROVAL,ASSIST_SIGN,ISFINGERREPEAT,DATA_SOURCES,FINGERSHOW_STATUS,DELETAG,INPUTTIME,SEQ,SID,FPT_PATH) " +
      "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysDate,gafis_person_seq.nextval,gafis_person_sid_seq.nextval,?)"

    JdbcDatabase.update(saveFullPersonInfo) { ps =>
      ps.setString(1, person.personId)
      ps.setString(2, person.cardId)
      ps.setString(3, person.name)
      ps.setString(4, person.aliasName)
      ps.setString(5, person.sexCode)
      ps.setDate(6,person.birthday)
      ps.setString(7, person.idCardNo)
      ps.setString(8, person.door)
      ps.setString(9, person.doorDetail)
      ps.setString(10, person.address)
      ps.setString(11, person.addressDetail)
      ps.setString(12, person.personCategory)
      ps.setString(13, person.caseClasses)
      ps.setString(14, person.gatherDepartCode)
      ps.setString(15, person.gatherDepartName)
      ps.setString(16, person.gatherUserName)
      ps.setDate(17, person.gatherDate)
      ps.setString(18, person.remark)
      ps.setString(19, person.nativePlaceCode)
      ps.setString(20, person.nationCode)
      ps.setString(21, person.assistLevel)
      ps.setString(22, person.assistBonus)
      ps.setString(23, person.assistPurpose)
      ps.setString(24, person.assistRefPerson)
      ps.setString(25, person.assistRefCase)
      ps.setString(26, person.assistValidate)
      ps.setString(27, person.assistExplain)
      ps.setString(28, person.assistDeptCode)
      ps.setString(29, person.assistDeptName)
      ps.setDate(30, person.assistDate)
      ps.setString(31, person.assistContacts)
      ps.setString(32, person.assistNumber)
      ps.setString(33, person.assistApproval)
      ps.setString(34, person.assistSign)
      ps.setString(35, person.isFingerRepeat)
      ps.setInt(36, person.dataSource)
      ps.setInt(37, person.fingerShowStatus)
      ps.setString(38, person.delTag)
      ps.setString(39,person.fptPath)
    }
  }

  def savePortrait(portrait : PortraitConvert): Unit ={
    val savePortraitSql = "INSERT INTO GAFIS_GATHER_PORTRAIT(PK_ID,PERSONID,FGP,GATHER_DATA,INPUTPSN,INPUTTIME,DELETAG)" +
      "VALUES(SYS_GUID(),?,?,?,'1',SYSDATE,?)"
    JdbcDatabase.update(savePortraitSql){ ps =>
      ps.setString(1,portrait.personId)
      ps.setString(2,portrait.fgp)
      ps.setBytes(3,portrait.gatherData)
      ps.setString(4,portrait.delTag)
    }
  }

  def saveTemplateFinger(finger : TemplateFingerConvert): Unit ={
    val saveFingerSql: String = "insert into gafis_gather_finger(pk_id,person_id,fgp,fgp_case,group_id,lobtype,inputtime,seq,gather_data,fpt_path,main_pattern,vice_pattern)" +
      "values(sys_guid(),?,?,?,?,?,sysdate,gafis_gather_finger_seq.nextval,?,?,?,?)"
    //保存指纹特征信息
    JdbcDatabase.update(saveFingerSql) { ps =>
      ps.setString(1, finger.personId)
      ps.setString(2, finger.fgp)
      ps.setString(3, finger.fgpCase)
      ps.setString(4, finger.groupId)
      ps.setString(5,finger.lobType)
      ps.setBytes(6, finger.gatherData)
      ps.setString(7, finger.path)
      ps.setString(8, finger.mainPattern)
      ps.setString(9, finger.vicePattern)
    }
  }


  def saveTemplateFingerMntAndBin(finger : TemplateFingerConvert,groupId : String, lobType : String, data : Array[Byte]): Unit ={
    val saveFingerSql: String = "insert into gafis_gather_finger(pk_id,person_id,fgp,fgp_case,group_id,lobtype,inputtime,seq,gather_data,fpt_path,main_pattern,vice_pattern)" +
      "values(sys_guid(),?,?,?,?,?,sysdate,gafis_gather_finger_seq.nextval,?,?,?,?)"
    //保存指纹特征信息
    JdbcDatabase.update(saveFingerSql) { ps =>
      ps.setString(1, finger.personId)
      ps.setString(2, finger.fgp)
      ps.setString(3, finger.fgpCase)
      ps.setString(4, groupId)
      ps.setString(5,lobType)
      ps.setBytes(6, data)
      ps.setString(7, finger.path)
      ps.setString(8, finger.mainPattern)
      ps.setString(9, finger.vicePattern)
    }
  }

  /***************************************************************************LATENT******************************************************************************/
  def saveLatent(latentCaseConvert: LatentCaseConvert): Unit ={
    val hasLatentCase = updateLatentCase(latentCaseConvert.caseId)
    if (hasLatentCase==0) saveLatnetCase(latentCaseConvert)
    latentCaseConvert.latentFingers.foreach { finger =>
      val hasLatentFinger = updateLatentFinger(finger.fingerId,finger.imgData)
      if (hasLatentFinger==0) saveLatentFinger(finger)
      finger.LatentFingerFeatures.foreach{ feature =>
        val hasLatentFingerFeature = updateLatentFingerFeature(feature.fingerId,feature.fingerMnt)
        if (hasLatentFingerFeature==0) saveLatnentFingerFeature(feature)
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

  def updateLatentFinger(fingerId : String,fingerImg : Array[Byte]) : Int = {
    val updateLatnetFingerSql = "UPDATE gafis_case_finger t SET t.modifiedtime = SYSDATE,t.finger_img = ? WHERE t.finger_id = ?"
    JdbcDatabase.update(updateLatnetFingerSql) { ps =>
      ps.setBytes(1,fingerImg)
      ps.setString(2,fingerId)
    }
  }

  def saveLatentFinger(latentFingerConvert : LatentFingerConvert): Unit ={
    val saveCaseFingerSql = "insert into GAFIS_CASE_FINGER(SEQ_NO,FINGER_ID,CASE_ID,Finger_Img,INPUTTIME,Deletag,Data_In,Data_Matcher,Sid,Fpt_Path)" +
      "values(?,?,?,?,sysdate,1,2,1,GAFIS_CASE_SID_SEQ.NEXTVAL,?)"
    JdbcDatabase.update(saveCaseFingerSql) { ps =>
      ps.setString(1, latentFingerConvert.seqNo)
      ps.setString(2, latentFingerConvert.fingerId)
      ps.setString(3, latentFingerConvert.caseId)
      ps.setBytes(4, latentFingerConvert.imgData)
      ps.setString(5, latentFingerConvert.fptPath)
    }
  }

  def updateLatentFingerFeature(fingerId : String,fingerMnt : Array[Byte]) : Int = {
    val updateLatnetFingerFeatureSql = "UPDATE gafis_case_finger_mnt t SET t.modifiedtime = SYSDATE,t.finger_mnt = ? WHERE t.finger_id = ? AND t.is_main_mnt = 1"
    JdbcDatabase.update(updateLatnetFingerFeatureSql) { ps =>
      ps.setBytes(1,fingerMnt)
      ps.setString(2,fingerId)
    }
  }

  def saveLatnentFingerFeature(latentFingerFeatureConvert: LatentFingerFeatureConvert): Unit ={
    val saveLatentFingerFeatureSql = "INSERT INTO gafis_case_finger_mnt(pk_id,finger_id,finger_mnt,Is_Main_Mnt,Inputtime) " +
      "VALUES(sys_guid(),?,?,'1',SYSDATE)"
    JdbcDatabase.update(saveLatentFingerFeatureSql){ ps =>
      ps.setString(1,latentFingerFeatureConvert.fingerId)
      ps.setBytes(2,latentFingerFeatureConvert.fingerMnt)
    }



  }
}

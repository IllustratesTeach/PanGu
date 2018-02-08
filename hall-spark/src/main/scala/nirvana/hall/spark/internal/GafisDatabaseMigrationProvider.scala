package nirvana.hall.spark.internal

import nirvana.hall.spark.services.FptPropertiesConverter._
import nirvana.hall.spark.services.SysProperties
import nirvana.hall.support.services.JdbcDatabase
import scala.collection.mutable.ArrayBuffer

object GafisDatabaseMigrationProvider {
  lazy implicit val dataSource = SysProperties.getDataSource("gafis_sh_px")

  def findSuitablePersonId: Unit = {
    JdbcDatabase.queryWithPsSetter("SELECT DISTINCT(f.person_id) pid FROM gafis_gather_finger f WHERE f.person_id IN (SELECT t.personid FROM gafis_person t WHERE t.deletag = 1)"){ ps =>}{ rs =>
      val personId = rs.getString("pid")
      println(personId)
    }
  }

  def importProvider(pid : String) : Seq[Any] = {
    if (pid.startsWith("A")) importLatentFinger(pid)
    else if (pid.startsWith("R") || pid.startsWith("P")) importTemplateFinger(pid)
    else ArrayBuffer.empty
  }

  def importTemplateFinger(personId : String): Seq[TemplateFingerConvert] = {
    var buffer = ArrayBuffer[TemplateFingerConvert]()
    val person = GafisPartitionRecordsDakuSaver.queryPersonById(personId)
    if (person.isEmpty) {
      val personConvert = new PersonConvert()
      personConvert.personId = personId
      GafisPartitionRecordsDakuSaver.savePersonInfo(personConvert)
      JdbcDatabase.queryWithPsSetter("SELECT t.person_id,t.fgp,t.fgp_case,t.group_id,t.lobtype,t.gather_data FROM gafis_gather_finger t WHERE t.person_id = ?"){ ps =>
        ps.setString(1,personId)
      }{ rs =>
        val personId = rs.getString("person_id")
        val fgp = rs.getString("fgp")
        val fgpCase = rs.getString("fgp_case")
        val groupId = rs.getString("group_id")
        val lobType = rs.getString("lobtype")
        val gatherData = rs.getBytes("gather_data")
        if (!"4".equals(groupId)) {//纹线不要
          val templateFingerConvert = new TemplateFingerConvert
          templateFingerConvert.personId = personId
          templateFingerConvert.fgp = fgp
          templateFingerConvert.fgpCase = fgpCase
          templateFingerConvert.groupId = groupId
          templateFingerConvert.lobType = lobType
          templateFingerConvert.gatherData = gatherData
          buffer += templateFingerConvert
        }
      }
    }
    buffer
  }

  def importLatentFinger(cardId : String) : Seq[LatentCaseConvert] = {
    val caseId = cardId.substring(0,cardId.length-2)
    val buffer = ArrayBuffer[LatentCaseConvert]()
    val latentCaseConvert = new LatentCaseConvert
    latentCaseConvert.caseId = caseId
    JdbcDatabase.queryWithPsSetter("SELECT t.seq_no,t.finger_id,t.case_id,t.finger_img FROM gafis_case_finger t WHERE t.case_id = ?"){ ps =>
      ps.setString(1,caseId)
    }{ rs =>
      val seqNo = rs.getString("seq_no")
      val fingerId = rs.getString("finger_id")
      val caseId = rs.getString("case_id")
      val imageData = rs.getBytes("finger_img")
      val latentFingerConvert = new LatentFingerConvert
      latentFingerConvert.seqNo = seqNo
      latentFingerConvert.fingerId = fingerId
      latentFingerConvert.caseId = caseId
      latentFingerConvert.imgData = imageData

      JdbcDatabase.queryWithPsSetter("SELECT t.finger_mnt,t.is_main_mnt FROM gafis_case_finger_mnt t WHERE t.finger_id = ?"){ ps =>
        ps.setString(1,fingerId)
      }{ rs =>
        val fingerMnt = rs.getBytes("finger_mnt")
        val isMainMnt = rs.getString("is_main_mnt")
        val latentFingerFeatureConvert = new LatentFingerFeatureConvert
        latentFingerFeatureConvert.isMainMnt = isMainMnt
        latentFingerFeatureConvert.fingerId = fingerId
        latentFingerFeatureConvert.fingerMnt = fingerMnt
        if (latentFingerConvert.LatentFingerFeatures == null)
          latentFingerConvert.LatentFingerFeatures =  latentFingerFeatureConvert :: Nil
        else
          latentFingerConvert.LatentFingerFeatures = latentFingerConvert.LatentFingerFeatures ::: latentFingerFeatureConvert :: Nil
      }
      if (latentCaseConvert.latentFingers == null)
        latentCaseConvert.latentFingers = latentFingerConvert :: Nil
      else
        latentCaseConvert.latentFingers = latentCaseConvert.latentFingers ::: latentFingerConvert :: Nil
    }
    buffer += latentCaseConvert
    buffer
  }
}

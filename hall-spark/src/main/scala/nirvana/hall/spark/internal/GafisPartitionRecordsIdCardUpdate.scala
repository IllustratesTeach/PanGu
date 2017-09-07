package nirvana.hall.spark.internal

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.spark.config.NirvanaSparkConfig
import nirvana.hall.spark.services.FptPropertiesConverter.TemplateFingerConvert
import nirvana.hall.spark.services.{PartitionRecordsSaver, SparkFunctions, SysProperties}
import nirvana.hall.spark.services.SparkFunctions.{StreamError, StreamEvent}
import nirvana.hall.support.services.JdbcDatabase

import scala.util.control.NonFatal

/**
  * Created by wangjue on 2016/11/23.
  */
class GafisPartitionRecordsIdCardUpdate extends PartitionRecordsSaver {
  import GafisPartitionRecordsIdCardUpdate._
  def savePartitionRecords(parameter: NirvanaSparkConfig)(records:Iterator[(StreamEvent, TemplateFingerConvert, GAFISIMAGESTRUCT, GAFISIMAGESTRUCT)]):Unit = {
    if (records != null && !records.isEmpty) {
      records.foreach { case (event, fingerImg, fingerMnt, fingerBin) =>
        try {
          if (fingerImg != null && fingerImg.idCardID != null && !"".equals(fingerImg.idCardID) && fingerMnt != null && fingerMnt.bnData.length > 0) {
            updatePersonFingerMntInfo(event.path, fingerMnt.toByteArray(), fingerImg.gatherData)
            if (event.path != null && !"".equals(event.path)) {
              val updateResult = updateExtractLogInfo(event.path, "1", "")
              if (updateResult == 0)
                addExtractLogInfo(event.path, fingerImg.idCardNO, fingerImg.name, "1", "")
            }
          }
        } catch {
          case NonFatal(e) =>
            e.printStackTrace(System.err)
            SparkFunctions.reportError(parameter, DbError(event, e.toString))
            var result = e.getMessage
            if (result ==null) result = ""
            else {
              if (result.length > 80)
                result = result.substring(0,80)
            }
            if (event.path != null && !"".equals(event.path) && fingerImg!=null && fingerImg.idCardID != null && !"".equals(fingerImg.idCardID)) {
              val updateResult = updateExtractLogInfo(event.path, "1", "")
              if (updateResult == 0)
                addExtractLogInfo(event.path, fingerImg.idCardNO, fingerImg.name, "2", result)
            }
        }
      }
    }
  }

}

object GafisPartitionRecordsIdCardUpdate{
  lazy implicit val dataSourceName = SysProperties.getPropertyOption("data.source.name").get
  lazy implicit val dataSource = SysProperties.getDataSource(dataSourceName)
  case class DbError(streamEvent: StreamEvent,message:String) extends StreamError(streamEvent) {
    override def getMessage: String = "S|"+message
  }


  private def updatePersonFingerMntInfo(pkId : String,newFeature : Array[Byte],restoreImage : Array[Byte]): Int = {
    val updateFeatureSql: String = "UPDATE GUIZHOU_IDCARDFINGERINFO t SET t.restore_data=?,t.extract_data= ?,t.update_date=sysdate WHERE t.pk_id = ?"
    JdbcDatabase.update(updateFeatureSql) { ps =>
      ps.setBytes(1, restoreImage)
      ps.setBytes(2, newFeature)
      ps.setString(3, pkId)
    }
  }

  def updateRestoreLogInfo(idCardInfoID : String, isSuccess : String, restoreResult : String) : Int = {
    val updateRestoreLogInfoSql : String = "UPDATE GUIZHOU_RESTORE_LOGINFO t set t.IS_SUCCESS=?,t.RESTORE_RESULT=?,t.RESTORE_DATE=sysdate WHERE t.IDCARDINFO_ID=? "
    JdbcDatabase.update(updateRestoreLogInfoSql) { ps =>
      ps.setString(1,isSuccess)
      ps.setString(2,restoreResult)
      ps.setString(3,idCardInfoID)
    }
  }

  /**
    * log for restore image
    */
  def addRestoreLogInfo(idCardInfoID : String, idcardNO : String, name : String, isSuccess : String, restoreResult : String) : Int = {
    val addRestoreLogInfoSql: String = "INSERT INTO GUIZHOU_RESTORE_LOGINFO(RESTORE_SEQS,IDCARDINFO_ID,IDCARDNO,NAME,RESTORE_DATE,IS_SUCCESS,RESTORE_RESULT) VALUES(GUIZHOU_RESTORE_LOGINFO_SEQ.NEXTVAL,?,?,?,SYSDATE,?,?)"
    JdbcDatabase.update(addRestoreLogInfoSql) { ps =>
      ps.setString(1, idCardInfoID)
      ps.setString(2, idcardNO)
      ps.setString(3, name)
      ps.setString(4, isSuccess)
      ps.setString(5, restoreResult)
    }

  }

  def updateExtractLogInfo(idCardInfoID : String,  isSuccess : String, extractResult : String) : Int = {
    val updateExtractLogInfoSql : String = "UPDATE GUIZHOU_EXTRACT_LOGINFO t set t.IS_SUCCESS=?,t.EXTRACT_RESULT=?,t.EXTRACT_DATE=sysdate WHERE t.IDCARDINFO_ID=?"
    JdbcDatabase.update(updateExtractLogInfoSql) { ps =>
      ps.setString(1,isSuccess)
      ps.setString(2,extractResult)
      ps.setString(3,idCardInfoID)
    }
  }

  /**
    * log for extract feature
    */
  def addExtractLogInfo(idCardInfoID : String, idCardNO : String, name : String, isSuccess : String, extractResult : String) : Int = {
    val addExtractLogInfoSql: String = "INSERT INTO GUIZHOU_EXTRACT_LOGINFO(EXTRACT_SEQS,IDCARDINFO_ID,IDCARDNO,NAME,EXTRACT_DATE,IS_SUCCESS,EXTRACT_RESULT) VALUES(GUIZHOU_EXTRACT_LOGINFO_SEQ.NEXTVAL,?,?,?,SYSDATE,?,?)"
    JdbcDatabase.update(addExtractLogInfoSql) { ps =>
      ps.setString(1, idCardInfoID)
      ps.setString(2, idCardNO)
      ps.setString(3, name)
      ps.setString(4, isSuccess)
      ps.setString(5, extractResult)
    }

  }


}
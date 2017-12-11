package nirvana.hall.api.internal.fpt.exchange

import javax.sql.DataSource

import nirvana.hall.api.internal.fpt.vo.{FingerPrintTaskInfo, LatentTaskInfo}
import nirvana.hall.api.services.fpt.exchange.{FPTExchangeService}
import nirvana.hall.support.services.JdbcDatabase

/**
  * Created by yuchen on 2017/12/9.
  */
class FPTExchangeServiceImpl(implicit dataSource: DataSource) extends FPTExchangeService{

  override def getLatentTaskInfo(taskId:String): LatentTaskInfo = {
    var latentTaskInfo:LatentTaskInfo = null
    val sql = s"SELECT RWBH" +
                    s",ZZHWBDRWLXDM" +
                    s",ZWBDXTLXMS" +
                    s",YSXT_ASJBH" +
                    s",ASJBH" +
                    s",XCKYBH" +
                    s",YSXT_XCZZHWBH" +
                    s",XCWZBH" +
                    s",XCZZHWKBH" +
                    s",to_char(TJSJ,'yyyyMMddHHmmss') TJSJ" +
                    s"  FROM FPT_LPHIT_REQUEST t " +
                    s"  WHERE t.RWBH = ?"
    JdbcDatabase.queryWithPsSetter2(sql){ps=>
      ps.setString(1,taskId)
    }{rs=>
      while (rs.next()){
        latentTaskInfo = new LatentTaskInfo
        latentTaskInfo.taskId = rs.getString("RWBH")
        latentTaskInfo.matchTaskTypeCode = rs.getString("ZZHWBDRWLXDM")
        latentTaskInfo.matchSystemTypeDescript = rs.getString("ZWBDXTLXMS")
        latentTaskInfo.originSystemCaseId = rs.getString("YSXT_ASJBH")
        latentTaskInfo.caseId = rs.getString("ASJBH")
        latentTaskInfo.surveyId = rs.getString("XCKYBH")
        latentTaskInfo.originSystemFingerId = rs.getString("YSXT_XCZZHWBH")
        latentTaskInfo.latentPhysicalId = rs.getString("XCWZBH")
        latentTaskInfo.latentCardId = rs.getString("XCZZHWKBH")
        latentTaskInfo.submitTime = rs.getString("TJSJ")
      }
    }
    latentTaskInfo
  }

  override def addLatentTaskInfo(latentTaskInfo: LatentTaskInfo): Unit = {
    val sql = s"INSERT INTO FPT_LPHIT_REQUEST(RWBH" +
                                            s",ZZHWBDRWLXDM" +
                                            s",ZWBDXTLXMS" +
                                            s",YSXT_ASJBH" +
                                            s",ASJBH" +
                                            s",XCKYBH" +
                                            s",YSXT_XCZZHWBH" +
                                            s",XCWZBH" +
                                            s",XCZZHWKBH" +
                                            s",TJSJ) VALUES(?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-MM-dd HH24:mi:ss'))"
    JdbcDatabase.update(sql){
      ps =>
        ps.setString(1,latentTaskInfo.taskId)
        ps.setString(2,latentTaskInfo.matchTaskTypeCode)
        ps.setString(3,latentTaskInfo.matchSystemTypeDescript)
        ps.setString(4,latentTaskInfo.originSystemCaseId)
        ps.setString(5,latentTaskInfo.caseId)
        ps.setString(6,latentTaskInfo.surveyId)
        ps.setString(7,latentTaskInfo.originSystemFingerId)
        ps.setString(8,latentTaskInfo.latentPhysicalId)
        ps.setString(9,latentTaskInfo.latentCardId)
        ps.setString(10,latentTaskInfo.submitTime)
    }
  }

  override def getFingerPrintTaskInfo(taskId: String): FingerPrintTaskInfo = {
    var fingerPrintTaskInfo:FingerPrintTaskInfo = null
    val sql = s"SELECT RWBH" +
                    s",ZZHWBDRWLXDM" +
                    s",ZWBDXTLXMS" +
                    s",YSXT_ASJXGRYBH" +
                    s",JZRYBH" +
                    s",ASJXGRYBH" +
                    s",ZZHWKBH" +
                    s",to_char(TJSJ,'yyyyMMddHHmmss')TJSJ " +
                    s"FROM FPT_TPHIT_REQUEST t " +
                    s"WHERE t.RWBH = ?"
    JdbcDatabase.queryWithPsSetter2(sql){ps=>
      ps.setString(1,taskId)
    }{rs=>
      while (rs.next()){
        fingerPrintTaskInfo = new FingerPrintTaskInfo
        fingerPrintTaskInfo.taskId = rs.getString("RWBH")
        fingerPrintTaskInfo.matchTaskTypeCode = rs.getString("ZZHWBDRWLXDM")
        fingerPrintTaskInfo.matchSystemTypeDescript = rs.getString("ZWBDXTLXMS")
        fingerPrintTaskInfo.originSystemCasePersonId = rs.getString("YSXT_ASJXGRYBH")
        fingerPrintTaskInfo.jingZongPersonId = rs.getString("JZRYBH")
        fingerPrintTaskInfo.personId = rs.getString("ASJXGRYBH")
        fingerPrintTaskInfo.CardId = rs.getString("ZZHWKBH")
        fingerPrintTaskInfo.submitTime = rs.getString("TJSJ")
      }
    }
    fingerPrintTaskInfo
  }

  override def addFingerPrintTaskInfo(fingerPrintTaskInfo: FingerPrintTaskInfo): Unit = {
    val sql = s"INSERT INTO FPT_TPHIT_REQUEST(RWBH" +
      s",ZZHWBDRWLXDM" +
      s",ZWBDXTLXMS" +
      s",YSXT_ASJXGRYBH" +
      s",JZRYBH" +
      s",ASJXGRYBH" +
      s",ZZHWKBH" +
      s",TJSJ) VALUES(?,?,?,?,?,?,?,to_date(?,'yyyy-MM-dd HH24:mi:ss'))"
    JdbcDatabase.update(sql){
      ps =>
        ps.setString(1,fingerPrintTaskInfo.taskId)
        ps.setString(2,fingerPrintTaskInfo.matchTaskTypeCode)
        ps.setString(3,fingerPrintTaskInfo.matchSystemTypeDescript)
        ps.setString(4,fingerPrintTaskInfo.personId)
        ps.setString(5,fingerPrintTaskInfo.jingZongPersonId)
        ps.setString(6,fingerPrintTaskInfo.personId)
        ps.setString(7,fingerPrintTaskInfo.CardId)
        ps.setString(8,fingerPrintTaskInfo.submitTime)
    }
  }
}

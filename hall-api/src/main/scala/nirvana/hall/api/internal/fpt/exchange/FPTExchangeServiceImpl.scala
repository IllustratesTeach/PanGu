package nirvana.hall.api.internal.fpt.exchange

import javax.sql.DataSource

import nirvana.hall.api.internal.fpt.vo.{FingerPrintTaskInfo, LatentTaskInfo}
import nirvana.hall.c.services.gfpt5lib.{LlHitResultPackage, LtHitResultPackage, TtHitResultPackage}
import nirvana.hall.api.services.fpt.exchange.FPTExchangeService
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


  override def getTtHitResultPackage(oraSid: String): TtHitResultPackage = {
    var ttHitResultPackage:TtHitResultPackage = null
    val sql = s"SELECT RWBH" +
      s",ZWBDXTLXMS" +
      s",ZZHWCCBZLXDM" +
      s",YSXT_ASJXGRYBH" +
      s",JZRYBH" +
      s",ASJXGRYBH" +
      s",ZZHWKBH" +
      s",SFZW_PDBZ" +
      s",BZJG_YSXT_ASJXGRYBH" +
      s",BZJG_JZRYBH" +
      s",BZJG_ASJXGRYBH" +
      s",BZJG_ZZHWKBH" +
      s",BZDW_GAJGJGDM" +
      s",BZDW_GAJGMC" +
      s",BZR_XM" +
      s",BZR_GMSFHM" +
      s",BZR_LXDH" +
      s",to_char(BZSJ,'yyyyMMddHHmmss')BZSJ" +
      s",FHDW_GAJGJGDM" +
      s",FHDW_GAJGMC" +
      s",FHR_XM" +
      s",FHR_GMSFHM" +
      s",FHR_LXDH" +
      s",to_char(FHSJ,'yyyyMMddHHmmss')FHSJ" +
      s",BZ " +
      s"FROM FPT_TTHIT_INFO t " +
      s"WHERE t.RWBH = ?"
    JdbcDatabase.queryWithPsSetter2(sql){ps=>
      ps.setString(1,oraSid)
    }{rs=>
      while (rs.next()){
        ttHitResultPackage = new TtHitResultPackage
        ttHitResultPackage.taskId = rs.getString("RWBH")
        ttHitResultPackage.comparisonSystemTypeDescript = rs.getString("ZWBDXTLXMS")
        ttHitResultPackage.ttHitTypeCode = rs.getString("ZZHWCCBZLXDM")
        ttHitResultPackage.originalPersonId = rs.getString("YSXT_ASJXGRYBH")
        ttHitResultPackage.jingZongPersonId = rs.getString("JZRYBH")
        ttHitResultPackage.personId = rs.getString("ASJXGRYBH")
        ttHitResultPackage.cardId = rs.getString("ZZHWKBH")
        ttHitResultPackage.whetherFingerJudgmentMark = rs.getString("SFZW_PDBZ")
        ttHitResultPackage.resultOriginalSystemPersonId = rs.getString("BZJG_YSXT_ASJXGRYBH")
        ttHitResultPackage.resultjingZongPersonId = rs.getString("BZJG_JZRYBH")
        ttHitResultPackage.resultPersonId = rs.getString("BZJG_ASJXGRYBH")
        ttHitResultPackage.resultCardId = rs.getString("BZJG_ZZHWKBH")
        ttHitResultPackage.hitUnitCode = rs.getString("BZDW_GAJGJGDM")
        ttHitResultPackage.hitUnitName = rs.getString("BZDW_GAJGMC")
        ttHitResultPackage.hitPersonName = rs.getString("BZR_XM")
        ttHitResultPackage.hitPersonIdCard = rs.getString("BZR_GMSFHM")
        ttHitResultPackage.hitPersonTel = rs.getString("BZR_LXDH")
        ttHitResultPackage.hitDateTime = rs.getString("BZSJ")
        ttHitResultPackage.checkUnitCode = rs.getString("FHDW_GAJGJGDM")
        ttHitResultPackage.checkUnitName = rs.getString("FHDW_GAJGMC")
        ttHitResultPackage.checkPersonName = rs.getString("FHR_XM")
        ttHitResultPackage.checkPersonIdCard = rs.getString("FHR_GMSFHM")
        ttHitResultPackage.checkPersonTel = rs.getString("FHR_LXDH")
        ttHitResultPackage.checkDateTime = rs.getString("FHSJ")
        ttHitResultPackage.memo = rs.getString("BZ")
      }
    }
    ttHitResultPackage
  }

  override def addTtHitResultPackage(ttHitResultPackage: TtHitResultPackage): Unit = {
    val sql = s"INSERT INTO FPT_TTHIT_INFO(RWBH" +
      s",ZWBDXTLXMS" +
      s",ZZHWCCBZLXDM" +
      s",YSXT_ASJXGRYBH" +
      s",JZRYBH" +
      s",ASJXGRYBH" +
      s",ZZHWKBH" +
      s",SFZW_PDBZ" +
      s",BZJG_YSXT_ASJXGRYBH" +
      s",BZJG_JZRYBH" +
      s",BZJG_ASJXGRYBH" +
      s",BZJG_ZZHWKBH" +
      s",BZDW_GAJGJGDM" +
      s",BZDW_GAJGMC" +
      s",BZR_XM" +
      s",BZR_GMSFHM" +
      s",BZR_LXDH" +
      s",BZSJ" +
      s",FHDW_GAJGJGDM" +
      s",FHDW_GAJGMC" +
      s",FHR_XM" +
      s",FHR_GMSFHM" +
      s",FHR_LXDH" +
      s",FHSJ" +
      s",BZ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-MM-dd HH24:mi:ss'),?,?,?,?,?,to_date(?,'yyyy-MM-dd HH24:mi:ss'),?)"
    JdbcDatabase.update(sql){
      ps =>
        ps.setString(1,ttHitResultPackage.taskId)
        ps.setString(2,ttHitResultPackage.comparisonSystemTypeDescript)
        ps.setString(3,ttHitResultPackage.ttHitTypeCode)
        ps.setString(4,ttHitResultPackage.originalPersonId)
        ps.setString(5,ttHitResultPackage.jingZongPersonId)
        ps.setString(6,ttHitResultPackage.personId)
        ps.setString(7,ttHitResultPackage.cardId)
        ps.setString(8,ttHitResultPackage.whetherFingerJudgmentMark)
        ps.setString(9,ttHitResultPackage.resultOriginalSystemPersonId)
        ps.setString(10,ttHitResultPackage.resultjingZongPersonId)
        ps.setString(11,ttHitResultPackage.resultPersonId)
        ps.setString(12,ttHitResultPackage.resultCardId)
        ps.setString(13,ttHitResultPackage.hitUnitCode)
        ps.setString(14,ttHitResultPackage.hitUnitName)
        ps.setString(15,ttHitResultPackage.hitPersonName)
        ps.setString(16,ttHitResultPackage.hitPersonIdCard)
        ps.setString(17,ttHitResultPackage.hitPersonTel)
        ps.setString(18,ttHitResultPackage.hitDateTime)
        ps.setString(19,ttHitResultPackage.checkUnitCode)
        ps.setString(20,ttHitResultPackage.checkUnitName)
        ps.setString(21,ttHitResultPackage.checkPersonName)
        ps.setString(22,ttHitResultPackage.checkPersonIdCard)
        ps.setString(23,ttHitResultPackage.checkPersonTel)
        ps.setString(24,ttHitResultPackage.checkDateTime)
        ps.setString(25,ttHitResultPackage.memo)
    }
  }

  override def getLTHitResultPackage(oraSid: String): LtHitResultPackage = {
    var ltHitResultPackage:LtHitResultPackage = null
    val sql = s"SELECT RWBH" +
      s",ZWBDXTLXMS" +
      s",XCZW_ASJBH" +
      s",XCZW_YSXT_ASJBH" +
      s",XCZW_XCKYBH" +
      s",XCZW_YSXT_XCZZHWBH" +
      s",XCZW_XCWZBH" +
      s",XCZW_XCZZHWKBH" +
      s",NYZW_YSXT_ASJXGRYBH" +
      s",NYZW_JZRYBH" +
      s",NYZW_ASJXGRYBH" +
      s",NYZW_ZZHWKBH" +
      s",NYZW_ZZHWDM" +
      s",NYZW_ZZHWBDFFDM" +
      s",BZDW_GAJGJGDM" +
      s",BZDW_GAJGMC" +
      s",BZR_XM" +
      s",BZR_GMSFHM" +
      s",BZR_LXDH" +
      s",to_char(BZSJ,'yyyyMMddHHmmss')BZSJ" +
      s",FHDW_GAJGJGDM" +
      s",FHDW_GAJGMC" +
      s",FHR_XM" +
      s",FHR_GMSFHM" +
      s",FHR_LXDH" +
      s",to_char(FHSJ,'yyyyMMddHHmmss')FHSJ" +
      s",BZ " +
      s"FROM FPT_LTHIT_INFO t " +
      s"WHERE t.RWBH = ?"
    JdbcDatabase.queryWithPsSetter2(sql){ps=>
      ps.setString(1,oraSid)
    }{rs=>
      while (rs.next()){
        ltHitResultPackage = new LtHitResultPackage
        ltHitResultPackage.taskId = rs.getString("RWBH")
        ltHitResultPackage.comparisonSystemTypeDescript = rs.getString("ZWBDXTLXMS")
        ltHitResultPackage.latentFingerCaseId = rs.getString("XCZW_ASJBH")
        ltHitResultPackage.latentFingerOriginalSystemCaseId = rs.getString("XCZW_YSXT_ASJBH")
        ltHitResultPackage.latentFingerLatentSurveyId = rs.getString("XCZW_XCKYBH")
        ltHitResultPackage.latentFingerOriginalSystemFingerId = rs.getString("XCZW_YSXT_XCZZHWBH")
        ltHitResultPackage.latentFingerLatentPhysicalId = rs.getString("XCZW_XCWZBH")
        ltHitResultPackage.latentFingerCardId = rs.getString("XCZW_XCZZHWKBH")
        ltHitResultPackage.fingerPrintOriginalSystemPersonId = rs.getString("NYZW_YSXT_ASJXGRYBH")
        ltHitResultPackage.fingerPrintJingZongPersonId = rs.getString("NYZW_JZRYBH")
        ltHitResultPackage.fingerPrintPersonId = rs.getString("NYZW_ASJXGRYBH")
        ltHitResultPackage.fingerPrintCardId = rs.getString("NYZW_ZZHWKBH")
        ltHitResultPackage.fingerPrintPostionCode = rs.getString("NYZW_ZZHWDM")
        ltHitResultPackage.fingerPrintComparisonMethodCode = rs.getString("NYZW_ZZHWBDFFDM")
        ltHitResultPackage.hitUnitCode = rs.getString("BZDW_GAJGJGDM")
        ltHitResultPackage.hitUnitName = rs.getString("BZDW_GAJGMC")
        ltHitResultPackage.hitPersonName = rs.getString("BZR_XM")
        ltHitResultPackage.hitPersonIdCard = rs.getString("BZR_GMSFHM")
        ltHitResultPackage.hitPersonTel = rs.getString("BZR_LXDH")
        ltHitResultPackage.hitDateTime = rs.getString("BZSJ")
        ltHitResultPackage.checkUnitCode = rs.getString("FHDW_GAJGJGDM")
        ltHitResultPackage.checkUnitName = rs.getString("FHDW_GAJGMC")
        ltHitResultPackage.checkPersonName = rs.getString("FHR_XM")
        ltHitResultPackage.checkPersonIdCard = rs.getString("FHR_GMSFHM")
        ltHitResultPackage.checkPersonTel = rs.getString("FHR_LXDH")
        ltHitResultPackage.checkDateTime = rs.getString("FHSJ")
        ltHitResultPackage.memo = rs.getString("BZ")
      }
    }
    ltHitResultPackage
  }

  override def addLTHitResultPackage(ltHitResultPackage: LtHitResultPackage): Unit = {
    val sql = s"INSERT INTO FPT_LTHIT_INFO(RWBH" +
      s",ZWBDXTLXMS" +
      s",XCZW_ASJBH" +
      s",XCZW_YSXT_ASJBH" +
      s",XCZW_XCKYBH" +
      s",XCZW_YSXT_XCZZHWBH" +
      s",XCZW_XCWZBH" +
      s",XCZW_XCZZHWKBH" +
      s",NYZW_YSXT_ASJXGRYBH" +
      s",NYZW_JZRYBH" +
      s",NYZW_ASJXGRYBH" +
      s",NYZW_ZZHWKBH" +
      s",NYZW_ZZHWDM" +
      s",NYZW_ZZHWBDFFDM" +
      s",BZDW_GAJGJGDM" +
      s",BZDW_GAJGMC" +
      s",BZR_XM" +
      s",BZR_GMSFHM" +
      s",BZR_LXDH" +
      s",BZSJ" +
      s",FHDW_GAJGJGDM" +
      s",FHDW_GAJGMC" +
      s",FHR_XM" +
      s",FHR_GMSFHM" +
      s",FHR_LXDH" +
      s",FHSJ" +
      s",BZ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-MM-dd HH24:mi:ss'),?,?,?,?,?,to_date(?,'yyyy-MM-dd HH24:mi:ss'),?)"
    JdbcDatabase.update(sql){
      ps =>
        ps.setString(1,ltHitResultPackage.taskId)
        ps.setString(2,ltHitResultPackage.comparisonSystemTypeDescript)
        ps.setString(3,ltHitResultPackage.latentFingerCaseId)
        ps.setString(4,ltHitResultPackage.latentFingerOriginalSystemCaseId)
        ps.setString(5,ltHitResultPackage.latentFingerLatentSurveyId)
        ps.setString(6,ltHitResultPackage.latentFingerOriginalSystemFingerId)
        ps.setString(7,ltHitResultPackage.latentFingerLatentPhysicalId)
        ps.setString(8,ltHitResultPackage.latentFingerCardId)
        ps.setString(9,ltHitResultPackage.fingerPrintOriginalSystemPersonId)
        ps.setString(10,ltHitResultPackage.fingerPrintJingZongPersonId)
        ps.setString(11,ltHitResultPackage.fingerPrintPersonId)
        ps.setString(12,ltHitResultPackage.fingerPrintCardId)
        ps.setString(13,ltHitResultPackage.fingerPrintPostionCode)
        ps.setString(14,ltHitResultPackage.fingerPrintComparisonMethodCode)
        ps.setString(15,ltHitResultPackage.hitUnitCode)
        ps.setString(16,ltHitResultPackage.hitUnitName)
        ps.setString(17,ltHitResultPackage.hitPersonName)
        ps.setString(18,ltHitResultPackage.hitPersonIdCard)
        ps.setString(19,ltHitResultPackage.hitPersonTel)
        ps.setString(20,ltHitResultPackage.hitDateTime)
        ps.setString(21,ltHitResultPackage.checkUnitCode)
        ps.setString(22,ltHitResultPackage.checkUnitName)
        ps.setString(23,ltHitResultPackage.checkPersonName)
        ps.setString(24,ltHitResultPackage.checkPersonIdCard)
        ps.setString(25,ltHitResultPackage.checkPersonTel)
        ps.setString(26,ltHitResultPackage.checkDateTime)
        ps.setString(27,ltHitResultPackage.memo)
    }
  }

  override def getLlHitResultPackage(oraSid: String): LlHitResultPackage = {
    var llHitResultPackage:LlHitResultPackage = null
    val sql = s"SELECT RWBH" +
      s",ZWBDXTLXMS" +
      s",YSXT_ASJBH" +
      s",ASJBH" +
      s",XCKYBH" +
      s",YSXT_XCZZHWBH" +
      s",XCWZBH" +
      s",XCZZHWKBH" +
      s",BZJG_YSXT_ASJBH" +
      s",BZJG_ASJBH" +
      s",BZJG_XCKYBH" +
      s",BZJG_YSXT_XCZZHWBH" +
      s",BZJG_XCWZBH" +
      s",BZJG_XCZZHWKBH" +
      s",BZDW_GAJGJGDM" +
      s",BZDW_GAJGMC" +
      s",BZR_XM" +
      s",BZR_GMSFHM" +
      s",BZR_LXDH" +
      s",to_char(BZSJ,'yyyyMMddHHmmss')BZSJ" +
      s",FHDW_GAJGJGDM" +
      s",FHDW_GAJGMC" +
      s",FHR_XM" +
      s",FHR_GMSFHM" +
      s",FHR_LXDH" +
      s",to_char(FHSJ,'yyyyMMddHHmmss')FHSJ" +
      s",BZ " +
      s"FROM FPT_LLHIT_INFO t " +
      s"WHERE t.RWBH = ?"
    JdbcDatabase.queryWithPsSetter2(sql){ps=>
      ps.setString(1,oraSid)
    }{rs=>
      while (rs.next()){
        llHitResultPackage = new LlHitResultPackage
        llHitResultPackage.taskId = rs.getString("RWBH")
        llHitResultPackage.comparisonSystemTypeDescript = rs.getString("ZWBDXTLXMS")
        llHitResultPackage.originalSystemCaseId = rs.getString("YSXT_ASJBH")
        llHitResultPackage.caseId = rs.getString("ASJBH")
        llHitResultPackage.latentSurveyId = rs.getString("XCKYBH")
        llHitResultPackage.originalSystemLatentFingerId = rs.getString("YSXT_XCZZHWBH")
        llHitResultPackage.latentPhysicalId = rs.getString("XCWZBH")
        llHitResultPackage.cardId = rs.getString("XCZZHWKBH")
        llHitResultPackage.resultOriginalSystemCaseId = rs.getString("BZJG_YSXT_ASJBH")
        llHitResultPackage.resultCaseId = rs.getString("BZJG_ASJBH")
        llHitResultPackage.resultLatentSurveyId = rs.getString("BZJG_XCKYBH")
        llHitResultPackage.resultOriginalSystemLatentPersonId = rs.getString("BZJG_YSXT_XCZZHWBH")
        llHitResultPackage.resultLatentPhysicalId = rs.getString("BZJG_XCWZBH")
        llHitResultPackage.resultCardId = rs.getString("BZJG_XCZZHWKBH")
        llHitResultPackage.hitUnitCode = rs.getString("BZDW_GAJGJGDM")
        llHitResultPackage.hitUnitName = rs.getString("BZDW_GAJGMC")
        llHitResultPackage.hitPersonName = rs.getString("BZR_XM")
        llHitResultPackage.hitPersonIdCard = rs.getString("BZR_GMSFHM")
        llHitResultPackage.hitPersonTel = rs.getString("BZR_LXDH")
        llHitResultPackage.hitDateTime = rs.getString("BZSJ")
        llHitResultPackage.checkUnitCode = rs.getString("FHDW_GAJGJGDM")
        llHitResultPackage.checkUnitName = rs.getString("FHDW_GAJGMC")
        llHitResultPackage.checkPersonName = rs.getString("FHR_XM")
        llHitResultPackage.checkPersonIdCard = rs.getString("FHR_GMSFHM")
        llHitResultPackage.checkPersonTel = rs.getString("FHR_LXDH")
        llHitResultPackage.checkDateTime = rs.getString("FHSJ")
        llHitResultPackage.memo = rs.getString("BZ")
      }
    }
    llHitResultPackage
  }

  override def addLlHitResultPackage(llHitResultPackage: LlHitResultPackage): Unit = {
    val sql = s"INSERT INTO FPT_LLHIT_INFO(RWBH" +
      s",ZWBDXTLXMS" +
      s",YSXT_ASJBH" +
      s",ASJBH" +
      s",XCKYBH" +
      s",YSXT_XCZZHWBH" +
      s",XCWZBH" +
      s",XCZZHWKBH" +
      s",BZJG_YSXT_ASJBH" +
      s",BZJG_ASJBH" +
      s",BZJG_XCKYBH" +
      s",BZJG_YSXT_XCZZHWBH" +
      s",BZJG_XCWZBH" +
      s",BZJG_XCZZHWKBH" +
      s",BZDW_GAJGJGDM" +
      s",BZDW_GAJGMC" +
      s",BZR_XM" +
      s",BZR_GMSFHM" +
      s",BZR_LXDH" +
      s",BZSJ" +
      s",FHDW_GAJGJGDM" +
      s",FHDW_GAJGMC" +
      s",FHR_XM" +
      s",FHR_GMSFHM" +
      s",FHR_LXDH" +
      s",FHSJ" +
      s",BZ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-MM-dd HH24:mi:ss'),?,?,?,?,?,to_date(?,'yyyy-MM-dd HH24:mi:ss'),?)"
    JdbcDatabase.update(sql){
      ps =>
        ps.setString(1,llHitResultPackage.taskId)
        ps.setString(2,llHitResultPackage.comparisonSystemTypeDescript)
        ps.setString(3,llHitResultPackage.originalSystemCaseId)
        ps.setString(4,llHitResultPackage.caseId )
        ps.setString(5,llHitResultPackage.latentSurveyId)
        ps.setString(6,llHitResultPackage.originalSystemLatentFingerId)
        ps.setString(7,llHitResultPackage.latentPhysicalId)
        ps.setString(8,llHitResultPackage.cardId)
        ps.setString(9,llHitResultPackage.resultOriginalSystemCaseId)
        ps.setString(10,llHitResultPackage.resultCaseId)
        ps.setString(11,llHitResultPackage.resultLatentSurveyId)
        ps.setString(12,llHitResultPackage.resultOriginalSystemLatentPersonId)
        ps.setString(13,llHitResultPackage.resultLatentPhysicalId)
        ps.setString(14,llHitResultPackage.resultCardId)
        ps.setString(15,llHitResultPackage.hitUnitCode)
        ps.setString(16,llHitResultPackage.hitUnitName)
        ps.setString(17,llHitResultPackage.hitPersonName)
        ps.setString(18,llHitResultPackage.hitPersonIdCard)
        ps.setString(19,llHitResultPackage.hitPersonTel)
        ps.setString(20,llHitResultPackage.hitDateTime)
        ps.setString(21,llHitResultPackage.checkUnitCode)
        ps.setString(22,llHitResultPackage.checkUnitName)
        ps.setString(23,llHitResultPackage.checkPersonName)
        ps.setString(24,llHitResultPackage.checkPersonIdCard)
        ps.setString(25,llHitResultPackage.checkPersonTel)
        ps.setString(26,llHitResultPackage.checkDateTime)
        ps.setString(27,llHitResultPackage.memo)
    }
  }
}

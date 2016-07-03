package nirvana.hall.v70.internal

import java.util.Date

import nirvana.hall.api.config.DBConfig
import nirvana.hall.api.services.CaseInfoService
import nirvana.hall.protocol.api.FPTProto.Case
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa.{GafisLogicDbCase, GafisLogicDb, GafisCase, SysUser}
import org.springframework.beans.BeanUtils
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/1/26.
 */
class CaseInfoServiceImpl extends CaseInfoService{
  /**
   * 新增案件信息
   * @param caseInfo
   * @return
   */
  @Transactional
  override def addCaseInfo(caseInfo: Case, dBConfig: DBConfig): Unit = {
    val gafisCase = ProtobufConverter.convertCase2GafisCase(caseInfo)
    gafisCase.inputpsn = Gafis70Constants.INPUTPSN
    gafisCase.inputtime = new Date()
    gafisCase.deletag = Gafis70Constants.DELETAG_USE
    gafisCase.caseSource = Gafis70Constants.DATA_SOURCE_GAFIS6.toString
    gafisCase.save()
    val logicDb:GafisLogicDb = if(dBConfig == null){
      GafisLogicDb.where(GafisLogicDb.logicCategory === "1").and(GafisLogicDb.logicIsdefaulttag === "1").headOption.get
    }else{
      GafisLogicDb.find(dBConfig.dbId.right.get)
    }
    //逻辑库
    val logicDbCase = new GafisLogicDbCase()
    logicDbCase.pkId = CommonUtils.getUUID()
    logicDbCase.logicDbPkid = logicDb.pkId
    logicDbCase.casePkid = gafisCase.caseId
    logicDbCase.save()
  }

  /**
   * 更新案件信息
   * @param caseInfo
   * @return
   */
  @Transactional
  override def updateCaseInfo(caseInfo: Case, dBConfig: DBConfig): Unit = {
    val gafisCaseNew = ProtobufConverter.convertCase2GafisCase(caseInfo)
    val gafisCase = GafisCase.find(gafisCaseNew.caseId)
    BeanUtils.copyProperties(gafisCaseNew, gafisCase)
    val sysUser = SysUser.find(Gafis70Constants.INPUTPSN)
    gafisCase.modifiedpsn = Gafis70Constants.INPUTPSN
    gafisCase.modifiedtime = new Date()
    gafisCase.deletag = Gafis70Constants.DELETAG_USE
    gafisCase.createUnitCode = sysUser.departCode
    gafisCase.caseSource = Gafis70Constants.DATA_SOURCE_GAFIS6.toString
    gafisCase.save()
  }

  /**
   * 获取案件信息
   * @param caseId
   * @return
   */
  override def getCaseInfo(caseId: String, dBConfig: DBConfig): Case= {
    val gafisCase = GafisCase.findOption(caseId)
    if(gafisCase.isEmpty){
      throw new RuntimeException("记录不存在!");
    }
    ProtobufConverter.convertGafisCase2Case(gafisCase.get)
  }

  /**
   * 删除案件信息
   * @param caseId
   * @return
   */
  @Transactional
  override def delCaseInfo(caseId: String): Unit = {
    GafisCase.find(caseId).delete
  }

  /**
   * 验证案件编号是否已存在
   * @param caseId
   * @return
   */
  override def isExist(caseId: String, dBConfig: DBConfig): Boolean = {
    GafisCase.findOption(caseId).nonEmpty
  }
}

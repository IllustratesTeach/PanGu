package nirvana.hall.v70.internal

import nirvana.hall.api.config.DBConfig
import nirvana.hall.api.services.CaseInfoService
import nirvana.hall.protocol.api.FPTProto.Case
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa.{GafisCase, GafisLogicDb, GafisLogicDbCase, SysUser}
import nirvana.hall.v70.services.sys.UserService
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/1/26.
 */
class CaseInfoServiceImpl(userService: UserService) extends CaseInfoService{
  /**
   * 新增案件信息
   * @param caseInfo
   * @return
   */
  @Transactional
  override def addCaseInfo(caseInfo: Case, dBConfig: DBConfig): Unit = {
    val gafisCase = ProtobufConverter.convertCase2GafisCase(caseInfo)
    var user = userService.findSysUserByLoginName(gafisCase.inputpsn)
    if (user.isEmpty){//找不到对应的用户，使用管理员用户
      user = Option(SysUser.find(Gafis70Constants.INPUTPSN))
    }
    gafisCase.inputpsn = user.get.pkId
    gafisCase.createUnitCode = user.get.departCode
    val modUser = userService.findSysUserByLoginName(gafisCase.modifiedpsn)
    if(modUser.nonEmpty){
      gafisCase.modifiedpsn = modUser.get.pkId
    }

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
    val gafisCase = GafisCase.find(caseInfo.getStrCaseID)
    ProtobufConverter.convertCase2GafisCase(caseInfo, gafisCase)

    var user = userService.findSysUserByLoginName(gafisCase.inputpsn)
    if (user.isEmpty){//找不到对应的用户，使用管理员用户
      user = Option(SysUser.find(Gafis70Constants.INPUTPSN))
    }
    gafisCase.inputpsn = user.get.pkId
    gafisCase.createUnitCode = user.get.departCode
    val modUser = userService.findSysUserByLoginName(gafisCase.modifiedpsn)
    if(modUser.nonEmpty){
      gafisCase.modifiedpsn = modUser.get.pkId
    }

    gafisCase.deletag = Gafis70Constants.DELETAG_USE
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

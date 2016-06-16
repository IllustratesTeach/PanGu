package nirvana.hall.v70.internal

import java.util.Date

import nirvana.hall.api.services.CaseInfoService
import nirvana.hall.protocol.api.FPTProto.Case
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa.GafisCase
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
  override def addCaseInfo(caseInfo: Case): Unit = {
    val gafisCase = ProtobufConverter.convertCase2GafisCase(caseInfo)
    gafisCase.inputpsn = Gafis70Constants.INPUTPSN
    gafisCase.inputtime = new Date()
    gafisCase.deletag = Gafis70Constants.DELETAG_USE
    gafisCase.save()
  }

  /**
   * 更新案件信息
   * @param caseInfo
   * @return
   */
  @Transactional
  override def updateCaseInfo(caseInfo: Case): Unit = {
    val gafisCase = ProtobufConverter.convertCase2GafisCase(caseInfo)
    gafisCase.modifiedpsn = Gafis70Constants.INPUTPSN
    gafisCase.modifiedtime = new Date()
    gafisCase.deletag = Gafis70Constants.DELETAG_USE
    gafisCase.save()
  }

  /**
   * 获取案件信息
   * @param caseId
   * @return
   */
  override def getCaseInfo(caseId: String): Case= {
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
  override def isExist(caseId: String): Boolean = {
    GafisCase.findOption(caseId).nonEmpty
  }
}

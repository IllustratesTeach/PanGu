package nirvana.hall.webservice.internal.survey

import nirvana.hall.c.services.gloclib.survey
import nirvana.hall.webservice.jpa.{LogGetfingerdetail, _}
import org.springframework.transaction.annotation.Transactional
/**
  * Created by yuchen on 2018/8/3.
  */
class PlatformOperatorInfoProvider{

  /**
    * 添加调用现勘系统获取指掌纹数量记录
    * @param logGetfingercount
    */
  def addLogGetFingerCount(logGetfingercount:LogGetfingercount):Unit = {
    logGetfingercount.save()
  }

  /**
    * 添加调用现勘系统获取指掌纹列表记录
    * @param logGetfingerlist
    */
  def addLogGetFingerList(logGetfingerlist:LogGetfingerlist):Unit = {
    logGetfingerlist.save()
  }

  /**
    * 添加调用现勘系统发送现场指掌纹状态记录
    * @param logPutfingerstatus
    */
  def addLogPutFingerStatus(logPutfingerstatus:LogPutfingerstatus):Unit = {
    logPutfingerstatus.save()
  }

  def addLogGetReceptionNo(logGetReceptionNo:LogGetReceptionno):Unit = {
    logGetReceptionNo.save()
  }

  def addLogGetCaseNo(logGetCaseNo:LogGetCaseno):Unit = {
    logGetCaseNo.save()
  }

  @Transactional
  def changeHitResultInfo(logPuthitresult:LogPuthitresultDetail):Unit = {
    val logGetFingerDetail = LogGetfingerdetail.find_by_xcwzbh(logPuthitresult.xczwXcwzbh).head
    logGetFingerDetail.hitrecordReportStatus = survey.SURVEY_STATE_SUCCESS.toString
    logGetFingerDetail.save()
    logPuthitresult.save()
  }

  def addLogGetFingerDetail(logGetfingerdetail:LogGetfingerdetail):Unit = {
    logGetfingerdetail.save()
  }


}

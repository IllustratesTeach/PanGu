package nirvana.hall.webservice.pages

import javax.inject.Inject

import nirvana.hall.webservice.internal.survey.gafis62.SurveyTableMaintenanceService
import org.apache.tapestry5.annotations.Environmental
import org.apache.tapestry5.services.Request
import org.apache.tapestry5.util.TextStreamResponse
import org.apache.tapestry5.{RenderSupport, StreamResponse}

/**
  * Created by yuchen on 2017/9/6.
  */
class Update {

  @Environmental
  var renderSupport:RenderSupport = _

  @Inject
  private var request:Request= _

  @Inject
  private var surveyTableMaintenanceService:SurveyTableMaintenanceService = _


  private val LIST:String = "list"
  private val HIT:String = "hit"

  def onActivate: StreamResponse = {
    var result = ""
    val modifyType = request.getParameter("type")
    val xcwzbh = request.getParameter("xcwzbh")
    val fingerId = request.getParameter("fingerId")
    val hitFingerId = request.getParameter("hitFingerId")
    val hitFgp = request.getParameter("hitFgp")
    val values = request.getParameter("v")

    try {
      if (modifyType.isEmpty) {
        new Exception("type can not empty")
      } else {
        modifyType match {
          case LIST =>
            if (Option(xcwzbh).isEmpty || Option(xcwzbh.trim).get.isEmpty) {
              throw new Exception("xcwzbh can not empty")
            }
            val sid = surveyTableMaintenanceService.getSurveyRecordSid(xcwzbh)
            if (sid.nonEmpty) {
              surveyTableMaintenanceService.updateSurveyRecordStateByOraSid(sid.get, values)
            }
          case HIT =>
            if (Option(fingerId).isEmpty || Option(fingerId.trim).get.isEmpty) {
              throw new Exception("fingerId can not empty")
            }
            if (Option(hitFingerId).isEmpty || Option(hitFingerId.trim).get.isEmpty) {
              throw new Exception("hitFingerId can not empty")
            }
            if (Option(hitFgp).isEmpty || Option(hitFgp.trim).get.isEmpty) {
              throw new Exception("hitFgp can not empty")
            }
            val sid = surveyTableMaintenanceService.getSurveyHitResultSid(fingerId, hitFingerId, hitFgp.toInt)
            if (sid.nonEmpty) {
              surveyTableMaintenanceService.updateSurveyHitResultStateByOraSid(sid.get, values)
            }
          case _ => new Exception("input's type error:" + modifyType)
        }
        result = "OK"
      }
    } catch {
      case e: Exception =>
        result = e.getMessage
    }
    new TextStreamResponse("text/html", result)
  }

}

package nirvana.hall.webservice.pages

import javax.inject.Inject

import nirvana.hall.webservice.internal.survey.gafis62.{SurveyTableMaintenanceService}
import org.apache.tapestry5.annotations.Environmental
import org.apache.tapestry5.services.Request
import org.apache.tapestry5.util.TextStreamResponse
import org.apache.tapestry5.{RenderSupport, StreamResponse}

/**
  * Created by yuchen on 2017/9/6.
  */
class Index {

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
    val sid = request.getParameter("sid")
    val values = request.getParameter("v")

    try{
      if(Option(modifyType).isEmpty || Option(modifyType.trim).get.isEmpty){
        throw new Exception("type can not empty")
      }
      if(Option(sid).isEmpty || Option(sid.trim).get.isEmpty){
        throw new Exception("sid can not empty")
      }
      if(Option(values).isEmpty || Option(values.trim).get.isEmpty){
        throw new Exception("v can not empty")
      }
      if(modifyType.equals(LIST)){
        surveyTableMaintenanceService.updateSurveyRecordStateByOraSid(sid,values)
      }else if(modifyType.equals(HIT)){
        surveyTableMaintenanceService.updateSurveyHitResultStateByOraSid(sid,values)
      }else{
        throw new Exception("input's type error:" + modifyType);
      }

      result = "OK"
    }catch{
      case e:Exception =>
        result = e.getMessage
    }
    new TextStreamResponse("text/html",result)
  }

}

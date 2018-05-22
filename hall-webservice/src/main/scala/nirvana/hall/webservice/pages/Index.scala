package nirvana.hall.webservice.pages

import javax.inject.Inject

import nirvana.hall.webservice.internal.survey.gafis62.SurveyTableMainService
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
  private var surveyTableMainService:SurveyTableMainService = _




  def onActivate: StreamResponse = {
    var result = ""
    val dbid = request.getParameter("dbid")
    val tableid = request.getParameter("tid")
    val orasid = request.getParameter("sid")
    val values = request.getParameter("v")

    try{
      if(null == dbid || dbid.length <=0){
        throw new Exception("dbid can not empty")
      }
      if(null == tableid || tableid.length <=0){
        throw new Exception("tid can not empty")
      }
      if(null == orasid || orasid.length <=0){
        throw new Exception("sid can not empty")
      }
      if(null == values || values.length <=0){
        throw new Exception("v can not empty")
      }
      surveyTableMainService.updateSurveyHitResultStateByOraSid(dbid.toShort,tableid.toShort,orasid,values)
      result = "OK"
    }catch{
      case e:Exception =>
        result = e.getMessage
    }
    new TextStreamResponse("text/html",result)
  }

}

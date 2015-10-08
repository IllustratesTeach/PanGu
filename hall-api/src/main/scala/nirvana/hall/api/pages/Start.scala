package nirvana.hall.api.pages

import javax.inject.Inject

import nirvana.hall.api.services.SystemService
import org.apache.tapestry5.json.JSONArray
import org.apache.tapestry5.services.Response
import org.apache.tapestry5.util.TextStreamResponse

/**
 * default page
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-08
 */
class Start {
  @Inject
  private var systemService: SystemService = _
  @Inject
  private var response:Response= _
  def onActivate = {
    val jsonArray = systemService.findAllProtocol().foldLeft(new JSONArray) {
      (arr, e) =>
        arr.put(e)
    }
    response.setHeader("Access-Control-Allow-Origin","*")
    response.setHeader("Access-Control-Allow-Headers","X-Requested-With,Content-Type,X-Hall-Request")
    new TextStreamResponse("text/plain", "Welcome to Hall api server! \n" + jsonArray.toString)
  }
}

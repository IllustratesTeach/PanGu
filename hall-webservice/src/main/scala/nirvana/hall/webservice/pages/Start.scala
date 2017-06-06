package nirvana.hall.webservice.pages

import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.services.Response
import org.apache.tapestry5.util.TextStreamResponse

/**
  * Created by songpeng on 2017/4/24.
  */
class Start {
  @Inject
  private var response:Response= _
  def onActivate = {
    new TextStreamResponse("text/plain", "Welcome to Hall Webservice server!")
  }
}

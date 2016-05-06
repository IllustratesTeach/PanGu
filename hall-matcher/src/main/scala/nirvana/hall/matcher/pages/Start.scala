package nirvana.hall.matcher.pages

import javax.inject.Inject

import org.apache.tapestry5.services.Response
import org.apache.tapestry5.util.TextStreamResponse

/**
 * default page
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-08
 */
class Start {
  @Inject
  private var response:Response= _
  def onActivate = {
    response.setHeader("Access-Control-Allow-Origin","*")
    response.setHeader("Access-Control-Allow-Headers","X-Requested-With,Content-Type,X-Hall-Request")
    new TextStreamResponse("text/plain", "Welcome to Nirvana Hall Matcher Server!" )
  }
}

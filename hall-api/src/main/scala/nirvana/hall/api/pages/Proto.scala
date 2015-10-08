package nirvana.hall.api.pages

import java.io.InputStream
import javax.inject.Inject
import javax.servlet.http.HttpServletRequest

import org.apache.tapestry5.services.{AssetSource, Response}
import org.apache.tapestry5.{ComponentResources, EventContext, StreamResponse}

/**
 * 得到协议文件
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-22
 */
class Proto {
  @Inject
  private var assetSource:AssetSource = _
  @Inject
  private var componentResources:ComponentResources = _
  @Inject
  private var request:HttpServletRequest = _;
  def onActivate(eventContext: EventContext)={
    val path = "/proto/"+eventContext.toStrings.mkString("/").replaceAll("\\.proto$",".json")
    val baseUrl = request.getRequestURL().toString().replaceFirst(request.getRequestURI(), "");
    new StreamResponse {
      override def getStream: InputStream = assetSource.getClasspathAsset(path).getResource.openStream()

      override def getContentType: String = "text/plain"

      override def prepareResponse(response: Response): Unit = {
        response.setHeader("Access-Control-Allow-Origin","*")
      }
    }
  }
}

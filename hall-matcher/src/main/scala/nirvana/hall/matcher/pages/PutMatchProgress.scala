package nirvana.hall.matcher.pages

import java.io.{ByteArrayInputStream, InputStream}
import javax.inject.Inject
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import nirvana.hall.matcher.service.PutMatchProgressService
import nirvana.protocol.MatchProgressProto.MatchProgressRequest
import org.apache.tapestry5.StreamResponse
import org.apache.tapestry5.services.Response

/**
 * Created by songpeng on 16/4/12.
 */
class PutMatchProgress {
  @Inject
  private var request: HttpServletRequest = _
  @Inject
  private var response: HttpServletResponse = _
  @Inject
  private var putMatchProgressService: PutMatchProgressService = _

  def onActivate: StreamResponse = {
    val matchProgressRequest = MatchProgressRequest.parseFrom(request.getInputStream)
    val matchProgressResponse = putMatchProgressService.putMatchProgress(matchProgressRequest)

    new StreamResponse {
      override def getStream: InputStream = new ByteArrayInputStream(matchProgressResponse.toByteArray)

      override def getContentType: String = "application/protobuf"

      override def prepareResponse(response: Response): Unit = {
        response.setHeader("Access-Control-Allow-Origin","*")
        response.setHeader("Access-Control-Allow-Headers","X-Requested-With,Content-Type,X-Hall-Request")
      }
    }
  }
}

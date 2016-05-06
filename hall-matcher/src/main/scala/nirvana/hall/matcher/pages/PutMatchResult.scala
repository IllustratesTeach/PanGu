package nirvana.hall.matcher.pages

import java.io.{ByteArrayInputStream, InputStream}
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import nirvana.hall.matcher.service.PutMatchResultService
import nirvana.protocol.MatchResult.MatchResultRequest
import org.apache.tapestry5.StreamResponse
import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.services.Response

/**
 * Created by songpeng on 16/3/20.
 */
class PutMatchResult {
  @Inject
  private var request: HttpServletRequest = _
  @Inject
  private var response: HttpServletResponse = _
  @Inject
  private var putMatchResultService: PutMatchResultService = _

  def onActivate: StreamResponse = {
    val matchResultRequest = MatchResultRequest.parseFrom(request.getInputStream)
    val matchResultResponse = putMatchResultService.putMatchResult(matchResultRequest)

    new StreamResponse {
      override def getStream: InputStream = new ByteArrayInputStream(matchResultResponse.toByteArray)

      override def getContentType: String = "application/protobuf"

      override def prepareResponse(response: Response): Unit = {
        response.setHeader("Access-Control-Allow-Origin","*")
        response.setHeader("Access-Control-Allow-Headers","X-Requested-With,Content-Type,X-Hall-Request")
      }
    }
  }
}

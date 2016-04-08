package nirvana.hall.matcher.pages

import java.io.{ByteArrayInputStream, InputStream}
import javax.inject.Inject
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import nirvana.hall.matcher.service.SyncDataService
import nirvana.protocol.SyncDataProto.SyncDataRequest
import org.apache.tapestry5.StreamResponse
import org.apache.tapestry5.services.Response

/**
 * Created by songpeng on 16/3/20.
 */
class SyncData {
  @Inject
  private var request: HttpServletRequest = _
  @Inject
  private var response: HttpServletResponse = _
  @Inject
  private var syncDataService: SyncDataService = _

  def onActivate:StreamResponse = {
    val syncDataRequest = SyncDataRequest.parseFrom(request.getInputStream)
    val syncDataResponse = syncDataService.syncData(syncDataRequest)
    new StreamResponse {
      override def getStream: InputStream = new ByteArrayInputStream(syncDataResponse.toByteArray)

      override def getContentType: String = "text/plain"

      override def prepareResponse(response: Response): Unit = {
//        response.setHeader()
      }
    }
  }
}

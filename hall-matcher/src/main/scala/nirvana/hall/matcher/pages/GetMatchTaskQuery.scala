package nirvana.hall.matcher.pages

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import nirvana.hall.matcher.service.GetMatchTaskService
import nirvana.protocol.MatchTaskQueryProto.MatchTaskQueryRequest
import org.apache.tapestry5.ioc.annotations.Inject

/**
 * Created by songpeng on 16/3/20.
 */
class GetMatchTaskQuery {

  @Inject
  private var request: HttpServletRequest = _
  @Inject
  private var response: HttpServletResponse = _
  @Inject
  private var getMatchTaskService: GetMatchTaskService = _

  def onActivate = {
    val matchTaskQueryRequest = MatchTaskQueryRequest.parseFrom(request.getInputStream)
    val matchTaskQueryResponse = getMatchTaskService.getMatchTask(matchTaskQueryRequest)
    matchTaskQueryResponse.writeTo(response.getOutputStream)
  }
}

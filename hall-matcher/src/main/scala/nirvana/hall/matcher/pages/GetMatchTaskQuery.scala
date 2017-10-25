package nirvana.hall.matcher.pages

import java.io.{ByteArrayInputStream, InputStream}
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import monad.support.services.LoggerSupport
import nirvana.hall.matcher.service.GetMatchTaskService
import nirvana.protocol.MatchTaskQueryProto.MatchTaskQueryRequest
import nirvana.protocol.NirvanaTypeDefinition.MatchType
import org.apache.tapestry5.StreamResponse
import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.services.Response

/**
 * Created by songpeng on 16/3/20.
 */
class GetMatchTaskQuery extends LoggerSupport{
  @Inject
  private var request: HttpServletRequest = _
  @Inject
  private var response: HttpServletResponse = _
  @Inject
  private var getMatchTaskService: GetMatchTaskService = _

  def onActivate: StreamResponse = {
    val matchTaskQueryRequest = MatchTaskQueryRequest.parseFrom(request.getInputStream)
    val matchTaskQueryResponse = getMatchTaskService.getMatchTask(matchTaskQueryRequest)
    //输出日志信息
    if(matchTaskQueryResponse.getMatchTaskCount > 0){
      val iter = matchTaskQueryResponse.getMatchTaskList.iterator()
      while (iter.hasNext){
        val matchTask = iter.next()
        info("getMatchTask sid:{} matchId:{} type:{} ", matchTask.getObjectId, matchTask.getMatchId, matchTask.getMatchType)
        matchTask.getMatchType match {
          case MatchType.FINGER_TT | MatchType.FINGER_TL | MatchType.PALM_TT | MatchType.PALM_TL =>
            info("matchId:{} TextQuery:{}",matchTask.getMatchId, matchTask.getTData(0).getTextQuery)
          case MatchType.FINGER_LT | MatchType.FINGER_LL | MatchType.PALM_LT | MatchType.PALM_LL  =>
            info("matchId:{} TextQuery:{}",matchTask.getMatchId, matchTask.getLData(0).getTextQuery)
          case other =>
        }
      }
    }
    new StreamResponse {
      override def getStream: InputStream = new ByteArrayInputStream(matchTaskQueryResponse.toByteArray)

      override def getContentType: String = "application/protobuf"

      override def prepareResponse(response: Response): Unit = {
        response.setHeader("Access-Control-Allow-Origin","*")
        response.setHeader("Access-Control-Allow-Headers","X-Requested-With,Content-Type,X-Hall-Request")
      }
    }
  }
}

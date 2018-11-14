package nirvana.hall.matcher.pages

import java.io.{ByteArrayInputStream, InputStream}
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import monad.support.services.LoggerSupport
import nirvana.hall.matcher.service.{PutMatchProgressService, PutMatchResultService}
import nirvana.protocol.MatchProgressProto.MatchProgressRequest
import nirvana.protocol.MatchResult.MatchResultResponse.MatchResultResponseStatus
import nirvana.protocol.MatchResult.{MatchResultRequest, MatchResultResponse}
import org.apache.tapestry5.StreamResponse
import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.services.Response

/**
 * Created by songpeng on 16/3/20.
 */
class PutMatchResult extends LoggerSupport{
  @Inject
  private var request: HttpServletRequest = _
  @Inject
  private var response: HttpServletResponse = _
  @Inject
  private var putMatchResultService: PutMatchResultService = _
  @Inject
  private var putMatchProgressService: PutMatchProgressService = _

  /**
   * corePoolSize 核心线程池大小
   * maximumPoolSize 最大线程池大小
   * keepAliveTime 线程池中超过corePoolSize数目的空闲线程最大存活时间
   * TimeUnit keepAliveTime时间单位----TimeUnit.MINUTES
   * workQueue 阻塞队列
   */
//  private val executor: ThreadPoolExecutor = new ThreadPoolExecutor(100, 200, 60, TimeUnit.MINUTES,new LinkedBlockingQueue[Runnable]())

  def onActivate: StreamResponse = {
    val matchResultRequest = MatchResultRequest.parseFrom(request.getInputStream)
    info("PutMatchResult matchId:{} candNum:{}", matchResultRequest.getMatchId, matchResultRequest.getCandidateNum)
//    val matchResultResponse = putMatchResultService.putMatchResult(matchResultRequest)
    //异步处理
//    executor.execute(new PutMatchResultThread(matchResultRequest))
    new PutMatchResultThread(matchResultRequest).start()
    val matchResultResponse = MatchResultResponse.newBuilder()
    matchResultResponse.setStatus(MatchResultResponseStatus.OK)

    new StreamResponse {
      override def getStream: InputStream = new ByteArrayInputStream(matchResultResponse.build().toByteArray)

      override def getContentType: String = "application/protobuf"

      override def prepareResponse(response: Response): Unit = {
        response.setHeader("Access-Control-Allow-Origin","*")
        response.setHeader("Access-Control-Allow-Headers","X-Requested-With,Content-Type,X-Hall-Request")
      }
    }
  }

  /**
   * 处理比对结果线程类, 异步处理比对结果
   * @param matchResultRequest
   */
  class PutMatchResultThread(matchResultRequest: MatchResultRequest) extends Thread{
    override def run(){
      //更新比对进度99%
      val progressRequestBuilder = MatchProgressRequest.newBuilder()
      progressRequestBuilder.setMatchId(matchResultRequest.getMatchId)
      progressRequestBuilder.setProgress(99)
      putMatchProgressService.putMatchProgress(progressRequestBuilder.build())
      //写入比对结果
      putMatchResultService.putMatchResult(matchResultRequest)
    }
  }
}

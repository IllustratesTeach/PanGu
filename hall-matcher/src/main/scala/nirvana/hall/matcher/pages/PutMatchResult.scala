package nirvana.hall.matcher.pages

import java.io.{ByteArrayInputStream, InputStream}
import java.util.concurrent.{LinkedBlockingQueue, ThreadPoolExecutor, TimeUnit}
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import nirvana.hall.matcher.service.PutMatchResultService
import nirvana.protocol.MatchResult.MatchResultResponse.MatchResultResponseStatus
import nirvana.protocol.MatchResult.{MatchResultRequest, MatchResultResponse}
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

  /**
   * corePoolSize 核心线程池大小
   * maximumPoolSize 最大线程池大小
   * keepAliveTime 线程池中超过corePoolSize数目的空闲线程最大存活时间
   * TimeUnit keepAliveTime时间单位----TimeUnit.MINUTES
   * workQueue 阻塞队列
   */
  private val executor: ThreadPoolExecutor = new ThreadPoolExecutor(5, 15, 10, TimeUnit.MINUTES,new LinkedBlockingQueue[Runnable]())

  def onActivate: StreamResponse = {
    val matchResultRequest = MatchResultRequest.parseFrom(request.getInputStream)
//    val matchResultResponse = putMatchResultService.putMatchResult(matchResultRequest)
    //异步处理
    executor.execute(new PutMatchResultThread(matchResultRequest))
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
      putMatchResultService.putMatchResult(matchResultRequest)
    }
  }
}

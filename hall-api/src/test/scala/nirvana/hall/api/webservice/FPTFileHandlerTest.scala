package nirvana.hall.api.webservice


import monad.support.services.LoggerSupport
import nirvana.hall.api.services.{CaseInfoService, LPCardService, QueryService, TPCardService}
import nirvana.hall.api.webservice.util.FPTConvertToProtoBuffer
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.protocol.api.FPTProto.TPCard
import nirvana.hall.v62.BaseV62TestCase
import org.junit.Test

/**
  * Created by yuchen on 2016/12/7.
  */
class FPTFileHandlerTest() extends BaseV62TestCase with LoggerSupport{

  val hall_image_url = "http://192.168.1.215:80"
  /**
    * 处理TPCard数据
    */
  @Test
  def handlerTPcardData(): Unit ={
    val taskFpt = FPTFile.parseFromInputStream(
      getClass.getResourceAsStream("/_20161222031739323.fpt"))
    var tPCard:TPCard = null
    val tPCardService = getService[TPCardService]
    val queryService =  getService[QueryService]

    taskFpt match {
      case Left(fpt3) => throw new Exception("Not Support FPT-V3.0")
      case Right(fpt4) =>
        fpt4.logic02Recs.foreach{ sLogic02Rec =>
          tPCard = FPTConvertToProtoBuffer.TPFPT2ProtoBuffer(sLogic02Rec, hall_image_url)
          tPCardService.addTPCard(tPCard)
        }
        //TODO 如果一个fpt有多个捺印信息？？？
        val matchTask = FPTConvertToProtoBuffer.FPT2MatchTaskProtoBuffer(fpt4)
        queryService.sendQuery(matchTask)
    }
    assert(true)
  }

  /**
    * 处理LPCard数据
    */
  @Test
  def handlerLPCardData(): Unit ={

    val taskFpt = FPTFile.parseFromInputStream(
      getClass.getResourceAsStream("/_20161222034511422.fpt"))
    taskFpt match {
      case Left(fpt3) => throw new Exception("Not Support FPT-V3.0")
      case Right(fpt4) =>
        val lPCard = FPTConvertToProtoBuffer.FPT2LPProtoBuffer(fpt4)
        val caseInfo = FPTConvertToProtoBuffer.FPT2CaseProtoBuffer(fpt4)
        val matchTask = FPTConvertToProtoBuffer.FPT2MatchTaskCaseProtoBuffer(fpt4)
        val lPCardService = getService[LPCardService]
        val queryService =  getService[QueryService]
        val caseInfoService = getService[CaseInfoService]
        lPCardService.addLPCard(lPCard)
        caseInfoService.addCaseInfo(caseInfo)
        queryService.sendQuery(matchTask)
        println("任务完成")
    }
  }

}

package nirvana.hall.api.webservice


import java.io.{FileInputStream, FileOutputStream}
import java.util.Date
import javax.activation.DataHandler
import javax.xml.namespace.QName

import com.google.protobuf.ByteString
import monad.support.services.LoggerSupport
import nirvana.hall.api.services.{CaseInfoService, LPCardService, QueryService, TPCardService}
import nirvana.hall.api.webservice.util.{FPTConvertToProtoBuffer, FPTFileHandler}
import nirvana.hall.c.services.gfpt4lib.FPT4File.{FPT4File, Logic02Rec}
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.protocol.api.FPTProto.{FingerFgp, ImageType, TPCard}
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition
import nirvana.hall.v62.BaseV62TestCase
import org.apache.axis2.addressing.EndpointReference
import org.apache.axis2.client.Options
import org.apache.axis2.rpc.client.RPCServiceClient
import org.junit.Test

/**
  * Created by yuchen on 2016/12/7.
  */
class FPTFileHandlerTest extends BaseV62TestCase with LoggerSupport{

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
        fpt4.logic02Recs.foreach( sLogic02Rec =>
          tPCard = FPTConvertToProtoBuffer.TPFPT2ProtoBuffer(sLogic02Rec,fpt4)
        )
        tPCardService.addTPCard(tPCard)
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

package nirvana.hall.v62.internal.c.grmtlib

import java.util.concurrent.atomic.AtomicInteger

import monad.support.services.LoggerSupport
import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.ghpcbase.gnopcode._
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.{GNETANSWERHEADOBJECT, GNETREQUESTHEADOBJECT}
import nirvana.hall.c.services.grmtlib.grmtcode
import nirvana.hall.protocol.api.CaseProto.{CaseAddRequest, CaseUpdateRequest}
import nirvana.hall.protocol.api.LPCardProto.{LPCardAddRequest, LPCardDelRequest, LPCardUpdateRequest}
import nirvana.hall.protocol.api.QueryProto.QuerySendRequest
import nirvana.hall.protocol.api.TPCardProto.{TPCardAddRequest, TPCardDelRequest, TPCardUpdateRequest}
import nirvana.hall.v62.internal.AncientClientSupport
import nirvana.hall.v62.internal.c.gbaselib.gitempkg
import nirvana.hall.v62.internal.c.gloclib.{galoclpConverter, galocpkg, galoctpConverter, gaqryqueConverter}
import nirvana.hall.v62.internal.c.gnetlib.reqansop
import nirvana.hall.v62.proxy.LocalServiceFinder

import scala.concurrent.ExecutionContext

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-18
  */
trait grmtsvr {
  this:AncientClientSupport
    with LoggerSupport
    with galocpkg
    with gitempkg
    with LocalServiceFinder
    with grmtcsr
    with grmtpkg
    with reqansop =>
  private implicit val executionContext = ExecutionContext.global
  private val testSeq = new AtomicInteger(0)

  /**
   * 处理对TP的请求操作
   * @param pReq
   * @param pstRecvPkg
   * @param bIsReq50
   * @return
   */
  def GAFIS_RMTLIB_TPSVR_Server(pReq:GNETREQUESTHEADOBJECT,
                                pstRecvPkg:GBASE_ITEMPKG_OPSTRUCT,
                                bIsReq50:Int=0):Boolean={
    val pAns = new GNETANSWERHEADOBJECT
    val nOpClass = NETREQ_GetOpClass(pReq);
    val nOpCode = NETREQ_GetOpCode(pReq);
    val nDBID	 = NETREQ_GetDBID(pReq);
    val nTableID = NETREQ_GetTableID(pReq);
    val nOption	 = NETREQ_GetOption(pReq);
    val nRmtOpt	 = NETREQ_GetRetVal(pReq);
    val nZipMethod = pReq.bnData(48);
    val nZipRatio  = pReq.bnData(49);


    nOpCode match{
        //增加捺印卡信息
      case OP_TPLIB_ADD=>
        /*
         * 主干程序，
         * 1. 解析收到的包，解析出来档案数据
         * 2. 保存档案数据
         * 3. 返回结果
         */
        val stTPCardOpt = GAFIS_PKG_GetTpCard(pstRecvPkg)
        //TODO 通过拿到的stTPCard来保存到v70数据库或者转发v62的通信服务器
        //saveStTPCardToV70.....
        //n 为保存成功的个数
        var n = 0
        stTPCardOpt.map{tpCard=>
          //测试使用，本机上报本机
//          println("================>>>>>>> "+tpCard.szCardID)
//          tpCard.szCardID=testSeq.incrementAndGet()+"-"+tpCard.szCardID
          galoctpConverter.convertGTPCARDINFOSTRUCT2ProtoBuf(tpCard)
        }.foreach{ card =>
            val request = TPCardAddRequest.newBuilder()
            request.setCard(card)
            findTPCardService.addTPCard(request.build())
            n += 1
        }

        NETANS_SetRetVal(pAns,n)
        val stSendPkg = GBASE_ITEMPKG_New
        GAFIS_PKG_AddRmtAnswer(stSendPkg,pAns)
        GAFIS_RMTLIB_SendPkgInServer(stSendPkg)

        true
        //更新捺印卡数据
      case OP_TPLIB_UPDATE=>
        var n = 0
        val stTPCardOpt = GAFIS_PKG_GetTpCard(pstRecvPkg)
        stTPCardOpt.map{ tpCard=>
          galoctpConverter.convertGTPCARDINFOSTRUCT2ProtoBuf(tpCard)
        }.foreach{ card=>
          val request = TPCardUpdateRequest.newBuilder()
          request.setCard(card)
          findTPCardService.updateTPCard(request.build())
          n += 1
        }

        NETANS_SetRetVal(pAns,0)
        val stSendPkg = GBASE_ITEMPKG_New
        GAFIS_PKG_AddRmtAnswer(stSendPkg,pAns)
        GAFIS_RMTLIB_SendPkgInServer(stSendPkg)

        true
        //判断捺印编号是否存在
      case OP_TPLIB_EXIST=>
        //获取编号,去除后边的空字符
        val cardId = new String(pReq.bnData, "GB2312").trim
        println(cardId)

        val n = if(findTPCardService.isExist(cardId)) 1 else 0
        NETANS_SetRetVal(pAns,n)
        val stSendPkg = GBASE_ITEMPKG_New
        GAFIS_PKG_AddRmtAnswer(stSendPkg,pAns)
        GAFIS_RMTLIB_SendPkgInServer(stSendPkg)

        true
      case OP_TPLIB_DEL=>
        val cardId = new String(pReq.bnData, "GB2312").trim
        val request = TPCardDelRequest.newBuilder()
        request.setCardId(cardId)

        findTPCardService.delTPCard(request.build())
        NETANS_SetRetVal(pAns,1)
        val stSendPkg = GBASE_ITEMPKG_New
        GAFIS_PKG_AddRmtAnswer(stSendPkg,pAns)
        GAFIS_RMTLIB_SendPkgInServer(stSendPkg)

        true
      case other=>
        false
    }
  }

  /**
   * 处理Query查询请求
   * @param pReq
   * @param pstRecvPkg
   * @return
   */
  def GAFIS_RMTLIB_QUERY_Server(pReq:GNETREQUESTHEADOBJECT, pstRecvPkg:GBASE_ITEMPKG_OPSTRUCT): Boolean ={
    val pAns = new GNETANSWERHEADOBJECT
    val nOpClass = NETREQ_GetOpClass(pReq);
    val nOpCode = NETREQ_GetOpCode(pReq);
    val nDBID	 = NETREQ_GetDBID(pReq);
    val nTableID = NETREQ_GetTableID(pReq);
    val nOption	 = NETREQ_GetOption(pReq);
    val nRmtOpt	 = NETREQ_GetRetVal(pReq);

    nOpCode match{
      case grmtcode.OP_RMTLIB_QUERY_ADD=>
        val stQuery = GAFIS_PKG_GetQuery(pstRecvPkg)
        //nAddRet为成功发送查询ID
        var nAddRet = 0
        stQuery.map{query =>
          gaqryqueConverter.convertGAQUERYSTRUCT2MatchTask(query)
        }.foreach{matchTask =>
          val request = QuerySendRequest.newBuilder()
          request.setMatchTask(matchTask)
          val response = findQueryService.sendQuery(request.build())
          nAddRet = response.getOraSid.toInt
        }

        NETANS_SetRetVal(pAns,nAddRet);
        val stSendPkg = GBASE_ITEMPKG_New
        GAFIS_PKG_AddRmtAnswer(stSendPkg,pAns)
        GAFIS_RMTLIB_SendPkgInServer(stSendPkg)

        true
      case 	grmtcode.OP_RMTLIB_QUERY_GETRESULT=>
        val stQuery = new GAQUERYSTRUCT
        stQuery.stSimpQry.nQueryID = pReq.bnData
//        val oraSid = new String(pReq.bnData, "GB2312").trim
//        val request = QueryGetRequest.newBuilder()
//        request.setOraSid(oraSid.toLong)
//        val response = findQueryService.getQuery(request.build())
//        if(response.getIsComplete){
//          val candList = response.getMatchResult.getCandidateResultList
//        }

        //TODO 利用得到的SID进行查询候选队列
        val nRet = 1 //如果有候选队列


        val stSendPkg = GBASE_ITEMPKG_New
        NETANS_SetRetVal(pAns,nRet);

        GAFIS_PKG_AddRmtAnswer(stSendPkg,pAns)
        if(nRet > 0 )
          GAFIS_PKG_Query2Pkg(stQuery,stSendPkg)
        GAFIS_RMTLIB_SendPkgInServer(stSendPkg)

        true
      case other =>
        false
    }
  }

  /**
   * 处理对LP的请求操作
   * @param pReq
   * @param pstRecvPkg
   * @param bIsReq50
   * @return
   */
  def GAFIS_RMTLIB_LPSVR_Server(pReq:GNETREQUESTHEADOBJECT,
                                pstRecvPkg:GBASE_ITEMPKG_OPSTRUCT,
                                bIsReq50:Int=0): Boolean ={

    val pAns = new GNETANSWERHEADOBJECT
    val nOpClass = NETREQ_GetOpClass(pReq);
    val nOpCode = NETREQ_GetOpCode(pReq);
    val nDBID	 = NETREQ_GetDBID(pReq);
    val nTableID = NETREQ_GetTableID(pReq);
    val nOption	 = NETREQ_GetOption(pReq);
    val nRmtOpt	 = NETREQ_GetRetVal(pReq);
    val nZipMethod = pReq.bnData(48);
    val nZipRatio  = pReq.bnData(49);

    nOpCode match {
      case OP_LPLIB_ADD =>
        val stLPCard = GAFIS_PKG_GetLpCard(pstRecvPkg)
        var n = 0
        stLPCard.map{ lpCard=>
          galoclpConverter.convertGLPCARDINFOSTRUCT2ProtoBuf(lpCard)
        }.foreach{ card =>
          val request = LPCardAddRequest.newBuilder()
          request.setCard(card)
          findLPCardService.addLPCard(request.build())
          n += 1
        }
        NETANS_SetRetVal(pAns,n)
        val stSendPkg = GBASE_ITEMPKG_New
        GAFIS_PKG_AddRmtAnswer(stSendPkg,pAns)
        GAFIS_RMTLIB_SendPkgInServer(stSendPkg)
        true
      case OP_LPLIB_DEL =>
        val cardId = new String(pReq.bnData, "GB2312").trim
        val request = LPCardDelRequest.newBuilder()
        request.setCardId(cardId)
        findLPCardService.delLPCard(request.build())

        NETANS_SetRetVal(pAns,1)
        val stSendPkg = GBASE_ITEMPKG_New
        GAFIS_PKG_AddRmtAnswer(stSendPkg,pAns)
        GAFIS_RMTLIB_SendPkgInServer(stSendPkg)

        true
      case OP_LPLIB_UPDATE =>
        val stLPCard = GAFIS_PKG_GetLpCard(pstRecvPkg)
        var n = 0
        stLPCard.map{ lpCard=>
          galoclpConverter.convertGLPCARDINFOSTRUCT2ProtoBuf(lpCard)
        }.foreach{ card =>
          val request = LPCardUpdateRequest.newBuilder()
          request.setCard(card)
          findLPCardService.updateLPCard(request.build())
          n += 1
        }
        NETANS_SetRetVal(pAns,n)
        val stSendPkg = GBASE_ITEMPKG_New
        GAFIS_PKG_AddRmtAnswer(stSendPkg,pAns)
        GAFIS_RMTLIB_SendPkgInServer(stSendPkg)
        true
      case OP_LPLIB_EXIST =>
        val cardId = new String(pReq.bnData,"GB2312").trim
        val n = if (findLPCardService.isExist(cardId)) 1 else 0

        NETANS_SetRetVal(pAns,n)
        val stSendPkg = GBASE_ITEMPKG_New
        GAFIS_PKG_AddRmtAnswer(stSendPkg,pAns)
        GAFIS_RMTLIB_SendPkgInServer(stSendPkg)

        true
      case other =>
        false
    }
  }

  /**
   * 处理Case案件信息请求
   * @param pReq
   * @param pstRecvPkg
   * @param bIsReq50
   * @return
   */
  def GAFIS_RMTLIB_CASE_Server(pReq:GNETREQUESTHEADOBJECT,
                                pstRecvPkg:GBASE_ITEMPKG_OPSTRUCT,
                                bIsReq50:Int=0): Boolean ={
    val pAns = new GNETANSWERHEADOBJECT
    val nOpClass = NETREQ_GetOpClass(pReq);
    val nOpCode = NETREQ_GetOpCode(pReq);
    val nDBID	 = NETREQ_GetDBID(pReq);
    val nTableID = NETREQ_GetTableID(pReq);
    val nOption	 = NETREQ_GetOption(pReq);
    val nRmtOpt	 = NETREQ_GetRetVal(pReq);
    val nZipMethod = pReq.bnData(48);
    val nZipRatio  = pReq.bnData(49);

    nOpCode match {
      case OP_CASE_ADD =>
        val stCase = GAFIS_PKG_GetCase(pstRecvPkg)
        var n = 0
        stCase.map{caseInfo =>
          galoclpConverter.convertGCASEINFOSTRUCT2Protobuf(caseInfo)
        }.foreach{caseInfo =>
          val request = CaseAddRequest.newBuilder()
          request.setCase(caseInfo)
          findCaseInfoService.addCaseInfo(request.build())
          n += 1
        }

        NETANS_SetRetVal(pAns,n)
        val stSendPkg = GBASE_ITEMPKG_New
        GAFIS_PKG_AddRmtAnswer(stSendPkg,pAns)
        GAFIS_RMTLIB_SendPkgInServer(stSendPkg)
        true
      case OP_CASE_UPDATE =>
        val stCase = GAFIS_PKG_GetCase(pstRecvPkg)
        var n = 0
        stCase.map{caseInfo =>
          galoclpConverter.convertGCASEINFOSTRUCT2Protobuf(caseInfo)
        }.foreach{caseInfo =>
          val request = CaseUpdateRequest.newBuilder()
          request.setCase(caseInfo)
          findCaseInfoService.updateCaseInfo(request.build())
          n += 1
        }

        NETANS_SetRetVal(pAns,n)
        val stSendPkg = GBASE_ITEMPKG_New
        GAFIS_PKG_AddRmtAnswer(stSendPkg,pAns)
        GAFIS_RMTLIB_SendPkgInServer(stSendPkg)
        true
      case OP_CASE_EXIST =>
        val cardId = new String(pReq.bnData,"GB2312").trim
        val n = if (findCaseInfoService.isExist(cardId)) 1 else 0

        NETANS_SetRetVal(pAns,n)
        val stSendPkg = GBASE_ITEMPKG_New
        GAFIS_PKG_AddRmtAnswer(stSendPkg,pAns)
        GAFIS_RMTLIB_SendPkgInServer(stSendPkg)
        true
      case other =>
        false
    }
  }
}

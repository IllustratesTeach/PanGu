package nirvana.hall.v62.internal.c.grmtlib

import java.util.concurrent.atomic.AtomicInteger

import monad.support.services.LoggerSupport
import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.ghpcbase.gnopcode._
import nirvana.hall.c.services.gloclib.gaqryque.{GAQUERYCANDSTRUCT, GAQUERYSTRUCT}
import nirvana.hall.c.services.gloclib.glocndef.{GNETANSWERHEADOBJECT, GNETREQUESTHEADOBJECT}
import nirvana.hall.c.services.grmtlib.grmtcode
import nirvana.hall.v62.internal.c.gbaselib.gitempkg
import nirvana.hall.v62.internal.c.gloclib.{galoclpConverter, galocpkg, galoctpConverter, gaqryqueConverter}
import nirvana.hall.v62.internal.c.gnetlib.reqansop
import nirvana.hall.v62.internal.{AncientClientSupport, V62Facade}
import nirvana.hall.v62.proxy.LocalServiceFinder

import scala.collection.mutable.ArrayBuffer
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
        //n 为保存成功的个数
        var n = 0
        stTPCardOpt.map{tpCard=>
          galoctpConverter.convertGTPCARDINFOSTRUCT2ProtoBuf(tpCard)
        }.foreach{ card =>
            findTPCardService.addTPCard(card)
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
          findTPCardService.updateTPCard(card)
          n += 1
        }

        NETANS_SetRetVal(pAns,n)
        val stSendPkg = GBASE_ITEMPKG_New
        GAFIS_PKG_AddRmtAnswer(stSendPkg,pAns)
        GAFIS_RMTLIB_SendPkgInServer(stSendPkg)

        true
        //判断捺印编号是否存在
      case OP_TPLIB_EXIST=>
        //获取编号,去除后边的空字符
        val cardId = new String(pReq.bnData, "GB2312").trim

        val n = if(findTPCardService.isExist(cardId)) 1 else 0
        NETANS_SetRetVal(pAns,n)
        val stSendPkg = GBASE_ITEMPKG_New
        GAFIS_PKG_AddRmtAnswer(stSendPkg,pAns)
        GAFIS_RMTLIB_SendPkgInServer(stSendPkg)

        true
      case OP_TPLIB_DEL=>
        val cardId = new String(pReq.bnData, "GB2312").trim
        findTPCardService.delTPCard(cardId)
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
        //6.2代码有很多业务逻辑，这里暂时不做处理，只保存查询任务
        val stQuery = GAFIS_PKG_GetQuery(pstRecvPkg)
        //nAddRet为成功发送查询ID
        var nAddRet = 0
        stQuery.map{query =>
          gaqryqueConverter.convertGAQUERYSTRUCT2MatchTask(query)
        }.foreach{matchTask =>
          val oraSid = findQueryService.addMatchTask(matchTask)
          nAddRet = oraSid.toInt
        }

        NETANS_SetLongRetVal(pAns, nAddRet)
        val stSendPkg = GBASE_ITEMPKG_New
        GAFIS_PKG_AddRmtAnswer(stSendPkg,pAns)
        GAFIS_RMTLIB_SendPkgInServer(stSendPkg)

        true
      case grmtcode.OP_RMTLIB_QUERY_GET =>
        var n = 0
        val oraSid = gaqryqueConverter.convertSixByteArrayToLong(pReq.bnData)
        val stQuery = findQueryService.getGAQUERYSTRUCT(oraSid)
        if(stQuery != null){
          n = 1
        }
        NETANS_SetRetVal(pAns,n)
        val stSendPkg = GBASE_ITEMPKG_New

        GAFIS_PKG_AddRmtAnswer(stSendPkg,pAns)
        if(n > 0 )
          GAFIS_PKG_Query2Pkg(stQuery,stSendPkg)
        GAFIS_RMTLIB_SendPkgInServer(stSendPkg)

        true
      case 	grmtcode.OP_RMTLIB_QUERY_GETRESULT=>
        val oraSid = gaqryqueConverter.convertSixByteArrayToLong(pReq.bnData)
        val stQuery = new GAQUERYSTRUCT
        stQuery.stSimpQry.nQueryID = pReq.bnData
        //TODO 利用得到的SID进行查询候选队列
        val matchResultOpt = findQueryService.getMatchResult(oraSid, Option(nDBID.toString))
        val candListBuffer = new ArrayBuffer[GAQUERYCANDSTRUCT]()
        matchResultOpt.foreach{matchResult =>
          val iter = matchResult.getCandidateResultList.iterator()
          while (iter.hasNext){
            val matchResultObject = iter.next()
            candListBuffer += gaqryqueConverter.convertMatchResultObject2GAQUERYCANDSTRUCT(matchResultObject)
          }
          stQuery.pstCand_Data = candListBuffer.toArray
        }

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
          findLPCardService.addLPCard(card)
          n += 1
        }
        NETANS_SetRetVal(pAns,n)
        val stSendPkg = GBASE_ITEMPKG_New
        GAFIS_PKG_AddRmtAnswer(stSendPkg,pAns)
        GAFIS_RMTLIB_SendPkgInServer(stSendPkg)
        true
      case OP_LPLIB_DEL =>
        val cardId = new String(pReq.bnData, "GB2312").trim
        findLPCardService.delLPCard(cardId)

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
          findLPCardService.updateLPCard(card)
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
          findCaseInfoService.addCaseInfo(caseInfo)
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
          findCaseInfoService.updateCaseInfo(caseInfo)
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

  /**
    * 处理其他远程服务
    * @param pReq
    * @param pstRecvPkg
    * @return
    */
  def GAFIS_RMTLIB_REMOTE_Server(pReq:GNETREQUESTHEADOBJECT,
                                 pstRecvPkg:GBASE_ITEMPKG_OPSTRUCT): Boolean={

    val pAns = new GNETANSWERHEADOBJECT
    val nOpClass = NETREQ_GetOpClass(pReq);
    val nOpCode = NETREQ_GetOpCode(pReq);
    val nDBID	 = NETREQ_GetDBID(pReq);
    val nTableID = NETREQ_GetTableID(pReq);
    val nOption	 = NETREQ_GetOption(pReq);
    val nRmtOpt	 = NETREQ_GetRetVal(pReq);

    nOpCode match {
      case grmtcode.OP_RMTLIB_QRYANDDATACTRL_GET =>
        false
      case grmtcode.OP_RMTLIB_RETRFINISHEDQRY =>
        false
      case grmtcode.OP_RMTLIB_GETINT1VALUEBYCOLNAME =>
        /*
        接口功能:根据DBID，TID，通过sid和colName查询字段值
        6.2通讯服务器通过该接口查询任务状态，然后根据状态判断是否查询比对信息
         */
        //SID
        val sidArr = new Array[Byte](6)
        System.arraycopy(pReq.bnData, 40, sidArr, 0, sidArr.length)
        val oraSid = gaqryqueConverter.convertSixByteArrayToLong(sidArr)
        //COL_NAME
        val colArr = new Array[Byte](32)
        System.arraycopy(pReq.bnData, 0, colArr, 0, 32)
        val colName = new String(colArr).trim
        //如果是获取查询状态，交给queryService处理
        if(nDBID == V62Facade.DBID_QRY_DEFAULT && "Status".equals(colName)){
          val n = 1
          val status = findQueryService.getStatusBySid(oraSid, Option(nDBID.toString))

          pAns.bnData = new Array[Byte](64)
          pAns.bnData(0) = status.toByte

          NETANS_SetRetVal(pAns,n)
          val stSendPkg = GBASE_ITEMPKG_New
          GAFIS_PKG_AddRmtAnswer(stSendPkg,pAns)
          GAFIS_RMTLIB_SendPkgInServer(stSendPkg)

          true
        }else{
          false
        }
      case other =>
        false
    }
  }
}

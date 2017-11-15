package nirvana.hall.v62.internal.c.gnetlib

import nirvana.hall.c.services.gbaselib.gbasedef.GAKEYSTRUCT
import nirvana.hall.c.services.ghpcbase.gnopcode._
import nirvana.hall.c.services.gloclib.galoclp.{GAFIS_LPGROUPSTRUCT, GCASEINFOSTRUCT}
import nirvana.hall.c.services.gloclib.glocndef.GNETANSWERHEADOBJECT
import nirvana.hall.v62.internal.{AncientClientSupport, NoneResponse}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-10
 */
trait ganetlp {
  this:AncientClientSupport with gnetcsr with reqansop=>
  def NET_GAFIS_CASE_Del(nDBID:Short,nTableID:Short,pszKey:String,nOption:Int = 0):Unit=executeInChannel{channel=>
    val pReq = createRequestHeader

    pReq.nOption = nOption
    pReq.nDBID = nDBID
    pReq.nTableID = nTableID
    pReq.nOpClass = OP_CLASS_CASE.asInstanceOf[Short]
    pReq.nOpCode = OP_CASE_DEL.asInstanceOf[Short]
    pReq.bnData = pszKey.getBytes

    val response =channel.writeMessage[GNETANSWERHEADOBJECT](pReq)
    validateResponse(channel,response)
  }
  def NET_GAFIS_CASE_Get(nDBID:Short,nTableID:Short,caseId:String,nOption:Int=0):GCASEINFOSTRUCT=executeInChannel{channel=>
    val pReq = createRequestHeader

    pReq.nOption = nOption
    pReq.nDBID = nDBID
    pReq.nTableID = nTableID
    pReq.nOpClass = OP_CLASS_CASE.asInstanceOf[Short]
    pReq.nOpCode = OP_CASE_GET.asInstanceOf[Short]

    pReq.bnData = caseId.getBytes

    val response = channel.writeMessage[GNETANSWERHEADOBJECT](pReq)
    validateResponse(channel,response)

    GAFIS_NETSCR_RecvCaseInfo(channel,response)
  }


  def NET_GAFIS_CASE_Update(nDBID:Short,nTableID:Short,
                         pstCase:GCASEINFOSTRUCT, nOption:Int = 0):Unit=executeInChannel{channel=>

    val pReq = createRequestHeader

    pReq.nOption = nOption
    pReq.nDBID = nDBID
    pReq.nTableID = nTableID
    pReq.nOpClass = OP_CLASS_CASE.asInstanceOf[Short]
    pReq.nOpCode = OP_CASE_UPDATE.asInstanceOf[Short]

    channel.writeMessage[NoneResponse](pReq)

    GAFIS_NETSCR_SendCaseInfo(channel, pstCase)
    val response = channel.receive[GNETANSWERHEADOBJECT]()
    validateResponse(channel,response)
  }
  def NET_GAFIS_CASE_Add(nDBID:Short,nTableID:Short,
    pstCase:GCASEINFOSTRUCT, nOption:Int = 0):Unit=executeInChannel{channel=>

    val pReq = createRequestHeader

    pReq.nOption = nOption
    pReq.nDBID = nDBID
    pReq.nTableID = nTableID
    pReq.nOpClass = OP_CLASS_CASE.asInstanceOf[Short]
    pReq.nOpCode = OP_CASE_ADD.asInstanceOf[Short]

    channel.writeMessage[NoneResponse](pReq)

    GAFIS_NETSCR_SendCaseInfo(channel, pstCase)
    val response = channel.receive[GNETANSWERHEADOBJECT]()
    validateResponse(channel,response)
  }

  /**
   * 案件编号是否已经存在
   * @param nDBID
   * @param nTableID
   * @param caseId
   * @param nOption
   * @return
   */
  def NET_GAFIS_CASE_Exist(nDBID:Short,nTableID:Short, caseId: String, nOption:Int = 0):Boolean =executeInChannel{channel=>
    val pReq = createRequestHeader
    pReq.nOption = nOption
    pReq.nDBID = nDBID
    pReq.nTableID = nTableID
    pReq.nOpClass = OP_CLASS_CASE.asInstanceOf[Short]
    pReq.nOpCode = OP_CASE_EXIST.asInstanceOf[Short]
    pReq.bnData = caseId.getBytes

    val response =channel.writeMessage[GNETANSWERHEADOBJECT](pReq)
    validateResponse(channel,response)

    response.nReturnValue > 0
  }

  /**
    * 获取现场关联信息
    * @param nDBID
    * @param nTableID
    * @param pstRec
    * @param nOption
    */
  def NET_GAFIS_LPGROUP_Get(nDBID:Short,nTableID:Short, pstRec: GAFIS_LPGROUPSTRUCT, nOption:Int = 0): Unit ={
    NET_GAFIS_LPGROUP_Op(nDBID, nTableID, pstRec, nOption, OP_LPGROUP_GET)
  }

  /**
    * normallp_lpgroup表（现场关联信息）操作
    * @param nDBID
    * @param nTID
    * @param pstRec
    * @param nOption
    * @param nOp
    */
  def NET_GAFIS_LPGROUP_Op(nDBID:Short, nTID:Short,pstRec:GAFIS_LPGROUPSTRUCT, nOption:Int, nOp:Int): Unit = executeInChannel{ pstCon=>
    val pReq = createRequestHeader
    val pAns = new GNETANSWERHEADOBJECT
    NETREQ_SetOption(pReq, nOption)
    NETREQ_SetDBID(pReq, nDBID)
    NETREQ_SetTableID(pReq, nTID)
    NETREQ_SetOpClass(pReq, OP_CLASS_LPLIB)
    NETREQ_SetOpCode(pReq, nOp)
    nOp match {
      case OP_LPGROUP_ADD | OP_LPGROUP_UPDATE =>
        NETOP_SENDREQ(pstCon, pReq)
        GAFIS_NETSCR_SendLPGroup(pstCon, pAns, pstRec)
        NETOP_RECVANS(pstCon, pAns)
        validateResponse(pstCon, pAns)
      case OP_LPGROUP_DEL =>
        NETOP_SENDREQ(pstCon, pReq)
        val groupId = new GAKEYSTRUCT
        groupId.szKey = pstRec.szGroupID
        NETOP_SENDDATA(pstCon, groupId)
        NETOP_RECVANS(pstCon, pAns)
      case OP_LPGROUP_GET =>
        NETOP_SENDREQ(pstCon, pReq)
        val groupId = new GAKEYSTRUCT
        groupId.szKey = pstRec.szGroupID
        NETOP_SENDDATA(pstCon, groupId)
        NETOP_RECVANS(pstCon, pAns)
        validateResponse(pstCon, pAns)
        GAFIS_NETSCR_RecvLPGroup(pstCon, pAns, pstRec)
    }

  }
}

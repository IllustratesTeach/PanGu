package nirvana.hall.v62.internal.c.gnetlib

import nirvana.hall.c.services.ghpcbase.gnopcode._
import nirvana.hall.c.services.gloclib.galoclp.GCASEINFOSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.GNETANSWERHEADOBJECT
import nirvana.hall.v62.internal.{AncientClientSupport, NoneResponse}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-10
 */
trait ganetlp {
  this:AncientClientSupport with gnetcsr =>
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
}

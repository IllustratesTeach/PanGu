package nirvana.hall.v62.internal.c.gnetlib

import java.nio.ByteBuffer

import nirvana.hall.c.services.GADB_RETVAL
import nirvana.hall.c.services.ganumia.gadbdef.GADB_KEYARRAY
import nirvana.hall.c.services.ghpcbase.gnopcode._
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.GNETANSWERHEADOBJECT
import nirvana.hall.v62.internal.{AncientClientSupport, NoneResponse}

/**
 * gnetflib
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-10
 */
trait ganetqry {
  this:AncientClientSupport with gnetcsr with reqansop=>
  def NET_GAFIS_QUERY_Get(
    nDBID:Short,
    nTableID:Short,
    pstQry:GAQUERYSTRUCT, //queryId
    nOption:Int=0
    ):GAQUERYSTRUCT= executeInChannel{ channel=>
    val header = createRequestHeader
    header.nOpClass = OP_CLASS_QUERY.asInstanceOf[Short]
    header.nOpCode= OP_QUERY_GET.asInstanceOf[Short]
    header.nDBID = nDBID
    header.nTableID = nTableID

    channel.writeMessage[NoneResponse](header)

    GAFIS_NETSCR_SendQueryInfo(channel, pstQry)
    val response = channel.receive[GNETANSWERHEADOBJECT]()
    validateResponse(channel,response)

    GAFIS_NETSCR_RecvQueryInfo(channel,response)
  }

  def NET_GAFIS_QUERY_Add(nQryDBID:Short,
                          nQryTID:Short,
                          pstQry:GAQUERYSTRUCT,
                          nOption:Int = 0):Long= executeInChannel{channel=>

    val request = createRequestHeader

    request.nOption = nOption
    request.nDBID = nQryDBID
    request.nTableID = nQryTID
    request.nOpClass = OP_CLASS_QUERY.asInstanceOf[Short]
    request.nOpCode = OP_QUERY_ADD.asInstanceOf[Short]

    channel.writeMessage[NoneResponse](request)


    GAFIS_NETSCR_SendQueryInfo(channel,pstQry)

    val response = channel.receive[GNETANSWERHEADOBJECT]()
    validateResponse(channel,response)

    ByteBuffer.wrap(response.nReturnValueRes).getInt
  }
  def NET_GAFIS_QUERY_Submit(nQryDBID:Short,nQryTID:Short,
                             pstKey:GADB_KEYARRAY,
                             pstQry:GAQUERYSTRUCT,
                             pnIdx:Array[Byte],
                             nOption:Int = 0
                              ):Array[GADB_RETVAL]= executeInChannel{channel=>

    val request = createRequestHeader
    request.nOption = nOption
    request.nDBID = nQryDBID
    request.nTableID = nQryTID
    request.nOpClass = OP_CLASS_QUERY.asInstanceOf[Short]
    request.nOpCode = OP_QUERY_SUBMIT.asInstanceOf[Short]

    val byteBuffer = ByteBuffer.allocate(7)
      .putInt(pstKey.nKeyCount)
      .putShort(pstKey.nKeySize)
      .put(pnIdx.length.asInstanceOf[Byte])

    request.bnData = byteBuffer.array()
    var response = channel.writeMessage[GNETANSWERHEADOBJECT](request)
    validateResponse(channel,response)


    channel.writeByteArray[NoneResponse](pstKey.pKey_Data) //send key
    if(pnIdx.length>0) channel.writeByteArray[NoneResponse](pnIdx)

    GAFIS_NETSCR_SendQueryInfo(channel,pstQry)

    response = channel.receive[GNETANSWERHEADOBJECT]()//.writeMessage[GNETANSWERHEADOBJECT](pstQry)
    validateResponse(channel,response)
    0.until(pstKey.nKeyCount).map{ i=>
      channel.receive[GADB_RETVAL]()
    }.toArray
  }
  def NET_GAFIS_QUERY_Update(nDBID:Short,nTableID:Short,pstQry:GAQUERYSTRUCT,nOption:Int = 0) =executeInChannel{channel=>
    val pAns = new GNETANSWERHEADOBJECT
    val pReq = createRequestHeader
    NETREQ_SetOption(pReq, nOption);
    NETREQ_SetDBID(pReq, nDBID);
    NETREQ_SetTableID(pReq, nTableID);
    NETREQ_SetOpClass(pReq, OP_CLASS_QUERY);
    NETREQ_SetOpCode(pReq, OP_QUERY_UPDATE);

    channel.writeMessage(pReq)
    GAFIS_NETSCR_SendQueryInfo(channel, pstQry)

    NETOP_RECVANS(channel, pAns);
    validateResponse(channel,pAns)
  }

  def NET_GAFIS_QUERY_Del(nDBID:Short,nTableID:Short,pstQry:GAQUERYSTRUCT,nOption:Int = 0) =executeInChannel { channel =>
    val pAns = new GNETANSWERHEADOBJECT
    val pReq = createRequestHeader
    NETREQ_SetOption(pReq, nOption)
    NETREQ_SetDBID(pReq, nDBID)
    NETREQ_SetTableID(pReq, nTableID)
    NETREQ_SetOpClass(pReq, OP_CLASS_QUERY)
    NETREQ_SetOpCode(pReq, OP_QUERY_DEL)

    channel.writeMessage(pReq)
    GAFIS_NETSCR_SendQueryInfo(channel, pstQry)

    NETOP_RECVANS(channel, pAns)
    validateResponse(channel,pAns)
  }

  def NET_GAFIS_QUERY_FinishCheck(nDBID:Short,nTableID:Short,pstQry:GAQUERYSTRUCT,nOption:Int = 0) =executeInChannel { channel =>
    val pAns = new GNETANSWERHEADOBJECT
    val pReq = createRequestHeader
    NETREQ_SetOption(pReq, nOption)
    NETREQ_SetDBID(pReq, nDBID)
    NETREQ_SetTableID(pReq, nTableID)
    NETREQ_SetOpClass(pReq, OP_CLASS_QUERY)
    NETREQ_SetOpCode(pReq, OP_QUERY_FINISHCHECK)

    channel.writeMessage(pReq)
    GAFIS_NETSCR_SendQueryInfo(channel, pstQry)

    NETOP_RECVANS(channel, pAns)
    validateResponse(channel,pAns)
  }

}

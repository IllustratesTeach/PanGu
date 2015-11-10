package nirvana.hall.v62.internal.c.gnetlib

import java.nio.ByteBuffer

import nirvana.hall.v62.internal.c.GADB_RETVAL
import nirvana.hall.v62.internal.c.ganumia.gadbdef.GADB_KEYARRAY
import nirvana.hall.v62.internal.c.ghpcbase.gnopcode._
import nirvana.hall.v62.internal.c.gloclib.gaqryque.GAQUERYSTRUCT
import nirvana.hall.v62.internal.c.gloclib.glocndef.{GNETANSWERHEADOBJECT, GNETREQUESTHEADOBJECT}
import nirvana.hall.v62.internal.{AncientClientSupport, NoneResponse}
import nirvana.hall.v62.services.V62ServerAddress

/**
 * gnetflib
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-10
 */
trait ganetqry {
  this:AncientClientSupport with gnetcsr =>
  def NET_GAFIS_QUERY_Get(
    nDBID:Short,
    nTableID:Short,
    pstQry:GAQUERYSTRUCT,
    nOption:Int=0
    )= executeInChannel{ channel=>
    val header = createRequestHeader
    header.nOpClass = OP_CLASS_QUERY.asInstanceOf[Short]
    header.nOpCode= OP_QUERY_GET.asInstanceOf[Short]
    header.nDBID = nDBID
    header.nTableID = nTableID

    channel.writeMessage[NoneResponse](header)

    GAFIS_NETSCR_SendQueryInfo(channel, pstQry)
    val response = channel.receive[GNETANSWERHEADOBJECT]()
    validateResponse(channel,response)

    if(response.nReturnValue != 0)
      GAFIS_NETSCR_RecvQueryInfo(channel,response, pstQry)
  }

  def NET_GAFIS_QUERY_Add(nQryDBID:Short,
                          nQryTID:Short,
                          pstQry:GAQUERYSTRUCT,
                          nOption:Int = 0):Long= executeInChannel{channel=>

    val request = new GNETREQUESTHEADOBJECT
    request.cbSize = 192
    request.nMajorVer = 6
    request.nMinorVer = 1
    request.szUserName=serverAddress.user
    serverAddress.password.foreach(request.szUserPass = _ )
    request.nOption = nOption
    request.nDBID = nQryDBID
    request.nTableID = nQryTID
    request.nOpClass = OP_CLASS_QUERY.asInstanceOf[Short]
    request.nOpCode = OP_QUERY_ADD.asInstanceOf[Short]

    channel.writeMessage[NoneResponse](request)


    GAFIS_NETSCR_SendQueryInfo(channel,pstQry)

    val response = channel.receive[GNETANSWERHEADOBJECT]()
    validateResponse(channel,response)

    response.nReturnValue
  }
  def NET_GAFIS_QUERY_Submit(address:V62ServerAddress,nQryDBID:Short,nQryTID:Short,
                             pstKey:GADB_KEYARRAY,
                             pstQry:GAQUERYSTRUCT,
                             pnIdx:Array[Byte],
                             nOption:Int = 0
                              ):Array[GADB_RETVAL]= executeInChannel{channel=>

    val request = new GNETREQUESTHEADOBJECT
    request.cbSize = 192
    request.nMajorVer = 6
    request.nMinorVer = 1
    request.szUserName=address.user
    address.password.foreach(request.szUserPass = _ )
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

  final val GAFIS_KEYLIST_GetName = "KeyList"
  final val GAFIS_QRYPARAM_GetName = "QryParam"
  final val GAFIS_QRYFILTER_GetName = "QryFilter"
  final val GAFIS_CANDKEYFILTER_GetName = "CandKeyFilter"
  final val GAFIS_TEXTSQL_GetName = "TextSql"
}

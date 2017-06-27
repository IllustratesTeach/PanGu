package nirvana.hall.v62.internal.c.gnetlib

import java.nio.ByteBuffer

import nirvana.hall.c.services.ganumia.gadbrec.{GADB_SELRESULT, GADB_SELSTATEMENT}
import nirvana.hall.c.services.ghpcbase.gnopcode
import nirvana.hall.c.services.gloclib.glocndef.GNETANSWERHEADOBJECT
import nirvana.hall.v62.internal.AncientClientSupport

/**
 * clone from nettable.c
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-24
 */
trait nettable {
    this:AncientClientSupport with gnetcsr with reqansop=>

  def NET_GAFIS_TABLE_Select(nDBID:Short,nTID:Short,pstRes:GADB_SELRESULT,pstStmt:GADB_SELSTATEMENT):Int =  executeInChannel{channel=>

    val pReq = createRequestHeader
    pReq.nDBID = nDBID
    pReq.nTableID = nTID

    pReq.nOpClass = gnopcode.OP_CLASS_TABLE.toShort
    pReq.nOpCode = gnopcode.OP_TABLE_SELECT.toShort


    var pAns = channel.writeMessage[GNETANSWERHEADOBJECT](pReq)
    validateResponse(channel,pAns)

    GAFIS_NETSCR_SendSelItemToSelect(channel, pstRes)

    pAns = channel.writeMessage[GNETANSWERHEADOBJECT](pstStmt)
    validateResponse(channel,pAns)

    val n = pAns.nReturnValue
    if ( n>0 ) {
      GAFIS_NETSCR_RecvSelResult(channel, pstRes)
    }
    return n;
  }

  def NET_GAFIS_TABLE_UTIL_AddUpdate(nDBID:Short,nTID:Short,pstRes:GADB_SELRESULT,pstStmt:GADB_SELSTATEMENT, nOption:Int = 0, isUpdate: Boolean = false):Int =  executeInChannel { channel =>
    if(pstRes.nRecGot <= 0){
      return 0
    }
    val pReq = createRequestHeader
    pReq.nDBID = nDBID
    pReq.nTableID = nTID
    pReq.nOpClass = gnopcode.OP_CLASS_TABLE.toShort
    pReq.nOption = nOption
    if(isUpdate){
      pReq.nOpCode = gnopcode.OP_TABLE_UPDATEBYSELRES.toShort
    }else{
      pReq.nOpCode = gnopcode.OP_TABLE_ADDBYSELRES.toShort
    }
    pReq.bnData = ByteBuffer.allocate(4).putInt(pstRes.nRecGot).array()

    val pAns = channel.writeMessage[GNETANSWERHEADOBJECT](pReq)
    validateResponse(channel,pAns)

    GAFIS_NETSCR_SendSelResult(channel, pReq, pAns, pstRes)
    NETOP_RECVANS(channel, pAns)
    val n = pAns.nReturnValue
    println(n)
    n
  }
}

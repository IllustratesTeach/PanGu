package nirvana.hall.v62.internal.c.gnetlib

import nirvana.hall.v62.internal.AncientClientSupport
import nirvana.hall.v62.internal.c.ganumia.gadbrec.{GADB_SELSTATEMENT, GADB_SELRESULT}
import nirvana.hall.v62.internal.c.ghpcbase.gnopcode
import nirvana.hall.v62.internal.c.gloclib.glocndef.{GNETANSWERHEADOBJECT, GNETREQUESTHEADOBJECT}

/**
 * clone from nettable.c
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-24
 */
trait nettable {
    this:AncientClientSupport with gnetcsr =>

  def NET_GAFIS_TABLE_Select(nDBID:Short,nTID:Short,pstRes:GADB_SELRESULT,pstStmt:GADB_SELSTATEMENT):Unit=  executeInChannel{channel=>

    val pReq = new GNETREQUESTHEADOBJECT
    pReq.nDBID = nDBID
    pReq.nTableID = nTID

    pReq.nOpClass = gnopcode.OP_CLASS_TABLE.toShort
    pReq.nOpCode = gnopcode.OP_TABLE_SELECT.toShort


    var pAns = channel.receive[GNETANSWERHEADOBJECT]()
    validateResponse(channel,pAns)

    GAFIS_NETSCR_SendSelItemToSelect(channel, pstRes)

    pAns = channel.writeMessage[GNETANSWERHEADOBJECT](pstStmt)
    validateResponse(channel,pAns)

    val n = pAns.nReturnValue
    if ( n>0 ) {
      GAFIS_NETSCR_RecvSelResult(channel, pstRes)
    }
  }
}

package nirvana.hall.v62.internal.c.gnetlib

import nirvana.hall.c.services.ghpcbase.gnopcode._
import nirvana.hall.c.services.gloclib.galocdbp.GAFIS_DEFDBIDSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.{GNETANSWERHEADOBJECT, GNETREQUESTHEADOBJECT}
import nirvana.hall.v62.internal.AncientClientSupport

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-14
  */
trait netmisc {
  this:AncientClientSupport with gnetcsr with reqansop=>
  def NET_GAFIS_MISC_GetDefDBID():GAFIS_DEFDBIDSTRUCT=executeInChannel{channel=>
    val pReq = createRequestHeader
    val pAns = new GNETANSWERHEADOBJECT
    NETREQ_SetOpClass(pReq, OP_CLASS_MISC);
    NETREQ_SetOpCode(pReq, OP_MISC_GETDEFDBID);
    NETOP_SENDREQ(channel, pReq);
    NETOP_RECVANS(channel, pAns);
    validateResponse(channel,pAns)

    new GAFIS_DEFDBIDSTRUCT().fromByteArray(pAns.bnData)
  }

}

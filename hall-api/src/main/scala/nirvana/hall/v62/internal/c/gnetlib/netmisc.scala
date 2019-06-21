package nirvana.hall.v62.internal.c.gnetlib

import java.net.Socket

import nirvana.hall.api.internal.DateConverter
import nirvana.hall.c.services.gbaselib.gbasedef.AFISDateTime
import nirvana.hall.c.services.ghpcbase.gnopcode._
import nirvana.hall.c.services.gloclib.galocdbp.GAFIS_DEFDBIDSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.{GAPPCONNECTIONSTRUCT, GNETANSWERHEADOBJECT, GNETREQUESTHEADOBJECT}
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

  def NET_GAFIS_MISC_GetServerTime():AFISDateTime = executeInChannel{channel=>
    val pReq = new GNETREQUESTHEADOBJECT
    val pstCon = new GAPPCONNECTIONSTRUCT
    val pAns = new GNETANSWERHEADOBJECT
    //GAPPCONNECTIONSTRUCT stAppCon, *pstCon;
    //GNETANSWERHEADOBJECT stAns, *pAns;
    //GNETREQUESTHEADOBJECT stReq, *pReq;
    NETREQ_SetOpClass(pReq, OP_CLASS_MISC);
    NETREQ_SetOpCode(pReq, OP_MISC_GETSERVERTIME);
    NETOP_SENDREQ(channel, pReq);
    NETOP_RECVANS(channel, pAns);
    var retval = NETANS_GetRetVal(pAns)
    if(retval > 0) {
      return new AFISDateTime().fromByteArray(pAns.bnData)
    }else{
      return new AFISDateTime
    }
  }

}

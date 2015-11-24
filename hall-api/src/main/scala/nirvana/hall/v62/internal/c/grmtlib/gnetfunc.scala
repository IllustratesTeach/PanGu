package nirvana.hall.v62.internal.c.grmtlib

import nirvana.hall.v62.internal.AncientClientSupport
import nirvana.hall.v62.internal.c.gloclib.glocndef.{GNETANSWERHEADOBJECT, GNETREQUESTHEADOBJECT}
import nirvana.hall.v62.internal.c.gnetlib.gnetcsr
import nirvana.hall.v62.internal.c.grmtlib.gserver.RMTSERVERSTRUCT
import nirvana.hall.v62.internal.c.grmtlib.grmtcode._

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-24
 */
trait gnetfunc {
  this:AncientClientSupport with gnetcsr =>
  def NET_GAFIS_RMTLIB_SERVER_Add(nDBID:Short, nTableID:Short, pServer:RMTSERVERSTRUCT, nOption:Int = 0):Unit=
    executeInChannel{pstCon=>
      val pReq = new GNETREQUESTHEADOBJECT
      val pAns = new GNETANSWERHEADOBJECT

      NETREQ_SetOpClass(pReq,OP_CLASS_RMTLIB);
      NETREQ_SetOpCode(pReq, OP_RMTLIB_SERVER_ADD);
      NETREQ_SetDBID(pReq, nDBID);
      NETREQ_SetTableID(pReq, nTableID);
      NETREQ_SetOption(pReq, nOption);

      NETOP_SENDREQ(pstCon, pReq);		//send request
      NETOP_SENDDATA(pstCon, pServer);
      NETOP_RECVANS(pstCon, pAns);		//receive count of server

      validateResponse(pstCon,pAns)
  }
}

package nirvana.hall.v62.internal.c.grmtlib

import nirvana.hall.v62.internal.AncientClientSupport
import nirvana.hall.c.services.gloclib.glocndef.{GNETANSWERHEADOBJECT, GNETREQUESTHEADOBJECT}
import nirvana.hall.c.services.grmtlib.gserver.RMTSERVERSTRUCT
import nirvana.hall.c.services.grmtlib.grmtcode._
import nirvana.hall.v62.internal.c.gnetlib.{reqansop, gnetcsr}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-24
 */
trait gnetfunc {
  this:AncientClientSupport with gnetcsr with reqansop =>
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
  def NET_GAFIS_RMTLIB_SERVER_Get(nDBID:Short, nTableID:Short, pServer:RMTSERVERSTRUCT, nOption:Int):Unit=
    executeInChannel{pstCon =>
      val pReq = new GNETREQUESTHEADOBJECT

      NETREQ_SetOpClass(pReq, OP_CLASS_RMTLIB);
      NETREQ_SetOpCode(pReq, OP_RMTLIB_SERVER_GET);
      NETREQ_SetDBID(pReq, nDBID);
      NETREQ_SetTableID(pReq, nTableID);
      NETREQ_SetOption(pReq, nOption);
      pReq.bnData = pServer.szUnitCode.getBytes

      val pAns = new GNETANSWERHEADOBJECT

      NETOP_SENDREQ(pstCon, pReq);		//send request
      NETOP_RECVANS(pstCon, pAns);		//receive count of server
      NETOP_RETVAL_LT_FIN(pstCon,pAns);
      NETOP_RECVDATA(pstCon, pServer);
    }

}

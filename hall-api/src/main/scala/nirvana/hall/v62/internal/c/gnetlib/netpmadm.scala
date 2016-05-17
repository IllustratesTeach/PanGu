package nirvana.hall.v62.internal.c.gnetlib

import java.nio.ByteBuffer

import nirvana.hall.c.services.gbaselib.paramadm.GBASE_PARAM_NETITEM
import nirvana.hall.c.services.ghpcbase.gnopcode._
import nirvana.hall.c.services.gloclib.glocndef.{GNETANSWERHEADOBJECT, GNETREQUESTHEADOBJECT}
import nirvana.hall.v62.internal.AncientClientSupport

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-17
  */
trait netpmadm {
  this:AncientClientSupport with gnetcsr with reqansop=>
  // get parameter.
  def NET_GAFIS_PMADM_Get(pstItems:Array[GBASE_PARAM_NETITEM],nOption:Int=0 ):Unit=
    executeInChannel{ pstCon =>
      val pReq = new GNETREQUESTHEADOBJECT
      val pAns= new GNETANSWERHEADOBJECT
      NETREQ_SetOpClass(pReq, OP_CLASS_PARAMADM);
      NETREQ_SetOpCode(pReq, OP_PARAMADM_GET);
      NETREQ_SetOption(pReq, nOption)
      pReq.bnData = ByteBuffer.allocate(4).putInt(pstItems.length).array()
      NETOP_SENDREQ(pstCon, pReq)
      NETOP_SENDDATA(pstCon, pstItems)
      NETOP_RECVANS(pstCon, pAns);	// receive ans.
      validateResponse(pstCon,pAns)
      NETOP_RECVDATA(pstCon, pstItems)
      NETANS_GetRetVal(pAns);
    }
}

package nirvana.hall.v62.internal.c.gnetlib

import nirvana.hall.c.services.ghpcbase.gnopcode
import nirvana.hall.c.services.gloclib.gadbprop.GADBPROPSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.{GNETANSWERHEADOBJECT, GNETREQUESTHEADOBJECT}
import nirvana.hall.v62.internal.AncientClientSupport

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-10
 */
trait ganetdbp {
  this:AncientClientSupport with reqansop=>

  def NET_GAFIS_SYS_GetDBByID(nDBID:Short,nOption:Int=0):GADBPROPSTRUCT=executeInChannel {channel=>
    val pReq = new GNETREQUESTHEADOBJECT
    pReq.nOpClass = gnopcode.OP_CLASS_SYS.asInstanceOf[Short]
    pReq.nOpCode = gnopcode.OP_SYS_GETDBBYID.asInstanceOf[Short]

    pReq.nDBID = nDBID

    val response = channel.writeMessage[GNETANSWERHEADOBJECT](pReq)
    validateResponse(channel,response)
    channel.receive[GADBPROPSTRUCT]()
  }
}

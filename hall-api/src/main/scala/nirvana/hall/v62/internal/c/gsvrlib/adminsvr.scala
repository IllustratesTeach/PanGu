package nirvana.hall.v62.internal.c.gsvrlib

import java.nio.ByteBuffer

import nirvana.hall.c.services.gbaselib.paramadm.GBASE_PARAM_NETITEM
import nirvana.hall.c.services.ghpcbase.gnopcode
import nirvana.hall.c.services.gloclib.glocndef.GNETREQUESTHEADOBJECT
import nirvana.hall.v62.internal.AncientClientSupport
import nirvana.hall.v62.internal.c.gbaselib.gitempkg
import nirvana.hall.v62.internal.c.gloclib.galocpkg
import nirvana.hall.v62.internal.c.gnetlib.{gnetcsr, reqansop}
import nirvana.hall.v62.internal.c.grmtlib.{grmtcsr, grmtpkg}
import nirvana.hall.v62.internal.proxy.GbaseProxyClient
import nirvana.hall.v62.services.ChannelOperator

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-24
  */
trait adminsvr {
  this: AncientClientSupport
    with galocpkg
    with gitempkg
    with grmtcsr
    with grmtpkg
    with gnetcsr
    with reqansop =>
  // DBLOG operations
  def GAFIS_PARAMADM_Server(channelOperator:ChannelOperator,pReq: GNETREQUESTHEADOBJECT): Boolean = {
    val nOption = NETREQ_GetOption(pReq);
    val nopcode = NETREQ_GetOpCode(pReq);
    nopcode match{
      case gnopcode.OP_PARAMADM_GET =>
        val n = ByteBuffer.wrap(pReq.bnData).getInt
        val params = Range(0,n).map(x=>new GBASE_PARAM_NETITEM).toArray
        NETOP_RECVDATA(channelOperator,params)

        //代理
        val client =  new GbaseProxyClient
        val ans = client.executeInChannel{channel=>
          NETOP_SENDDATA(channel,pReq)
          NETOP_SENDDATA(channel,params)
//          NETOP_RECVDATA[GNETANSWERHEADOBJECT](channel)
        }
//        NETOP_SENDANS(channelOperator,ans)

        true
      case other=>
        false
    }
  }
}

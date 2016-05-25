package nirvana.hall.v62.internal.c.gsvrlib

import java.nio.ByteBuffer

import monad.support.services.LoggerSupport
import nirvana.hall.c.services.gbaselib.paramadm.GBASE_PARAM_NETITEM
import nirvana.hall.c.services.ghpcbase.gnopcode
import nirvana.hall.c.services.gloclib.glocndef.{GNETANSWERHEADOBJECT, GNETREQUESTHEADOBJECT}
import nirvana.hall.v62.internal.AncientClientSupport
import nirvana.hall.v62.internal.c.gbaselib.gitempkg
import nirvana.hall.v62.internal.c.gloclib.galocpkg
import nirvana.hall.v62.internal.c.gnetlib.{gnetcsr, reqansop}
import nirvana.hall.v62.internal.c.grmtlib.{grmtcsr, grmtpkg}
import nirvana.hall.v62.internal.proxy.GbaseProxyClient
import org.jboss.netty.buffer.ChannelBuffer

import scala.concurrent.{ExecutionContext, Promise}

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
    with LoggerSupport
    with gnetcsr
    with reqansop =>
  implicit val executeContext= ExecutionContext.global
  // DBLOG operations
  def GAFIS_PARAMADM_Server(pReq: GNETREQUESTHEADOBJECT): Boolean = {
    val nOption = NETREQ_GetOption(pReq);
    val nopcode = NETREQ_GetOpCode(pReq);
    nopcode match{
      case gnopcode.OP_PARAMADM_GET =>
        val n = ByteBuffer.wrap(pReq.bnData).getInt
        val params = Range(0,n).map(x=>new GBASE_PARAM_NETITEM).toArray
        val len = params.map(_.getDataSize).sum
        val promise = Promise[ChannelBuffer]()
        executeInChannel{channelOperator=>
          promise.future.foreach{buffer=>
            params.foreach(_.fromStreamReader(buffer))
            //代理
            val client =  new GbaseProxyClient
            val ans = new GNETANSWERHEADOBJECT
            client.executeInChannel{channel=>
              NETOP_SENDDATA(channel,pReq)
              NETOP_SENDDATA(channel,params)
              NETOP_RECVDATA[GNETANSWERHEADOBJECT](channel,ans)
            }
            NETOP_SENDANS(channelOperator,ans)
            NETOP_SENDDATA(channelOperator,params)
            error("<=========== direct data for opClass:"+pReq.nOpClass+" opCode:"+pReq.nOpCode+" finished!")
          }
          channelOperator.writePromise(len,promise)
        }


        true
      case other=>
        false
    }
  }
}

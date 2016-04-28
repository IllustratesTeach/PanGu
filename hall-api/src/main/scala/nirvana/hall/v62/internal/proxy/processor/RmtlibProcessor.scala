package nirvana.hall.v62.internal.proxy.processor

import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.{GNETANSWERHEADOBJECT, GNETREQUESTHEADOBJECT}
import nirvana.hall.c.services.grmtlib.grmtcode
import org.jboss.netty.channel.Channel

/**
  * 处理代码为 OP_CLASS_RMTLIB
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-28
  */
class RmtlibProcessor extends BaseAncientProcessor {
  /**
    * 处理不同的数据
    *
    * @param request  请求
    * @param pkg      数据包
    * @param channel  连接通道
    */
  override def process(request: GNETREQUESTHEADOBJECT, pkg: GBASE_ITEMPKG_OPSTRUCT, channel: Channel): Unit = {
    request.nOpCode match{
      case grmtcode.OP_RMTLIB_GETLOCUNITCODE=>
        //TODO 此处需要实现业务
        val ans = new GNETANSWERHEADOBJECT
        ans.bnData="123".getBytes()
        ans.nReturnValue = 1

        GAFIS_PKG_AddRmtAnswer(pkg,ans)
        GAFIS_RMTLIB_SendPkg(pkg)
      case other=>
        throw new UnsupportedOperationException("%s not supported,pls contact spy song!".format(other))
    }

  }
}

package nirvana.hall.v62.internal.proxy.processor

import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.GNETREQUESTHEADOBJECT
import nirvana.hall.v62.internal.proxy.GbaseProxyClient
import org.jboss.netty.channel.Channel

/**
  * 针对通信服务器的请求进行处理
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-28
  */
class AncientUserProcessor
  extends BaseAncientProcessor{

  /**
    * 处理不同的数据
    *
    * @param request  请求
    * @param pkg      数据包
    * @param channel  连接通道
    */
  override def process(request: GNETREQUESTHEADOBJECT, pkg: GBASE_ITEMPKG_OPSTRUCT, channel: Channel): Unit = {
    info("user processor...")
    val client = new GbaseProxyClient()

    /*
    request.nOpCode match{
      case gnopcode.OP_USER_LOGIN=>
        val pkg = GBASE_ITEMPKG_New


        //TODO 连接后端的6.2应用服务器来进行校验是否能够登录
        //TODO NET_GAFIS_USER_LoginEx ?
        val ans = new GNETANSWERHEADOBJECT
        ans.nReturnValue = 1

        GAFIS_PKG_AddRmtAnswer(pkg,ans)
        GAFIS_RMTLIB_SendPkgInServer(pkg)
    }
    */
  }
}

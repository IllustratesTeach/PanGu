package nirvana.hall.v62.internal.proxy.processor

import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.GNETREQUESTHEADOBJECT
import nirvana.hall.v62.internal.proxy.GbaseProxyClient
import org.jboss.netty.channel.Channel

/**
 * Created by songpeng on 16/5/3.
 */
class DefaultProcessor extends BaseAncientProcessor{
  /**
   * 处理不同的数据
   *
   * @param request 请求
   * @param pkg 数据包
   * @param channel 连接通道
   */
  override def process(request: GNETREQUESTHEADOBJECT, pkg: GBASE_ITEMPKG_OPSTRUCT, channel: Channel): Unit = {
    val client = new GbaseProxyClient()
    val backendPkg = client.sendAndReceiveFromBackend(pkg)
    info("from backend received")
    GAFIS_RMTLIB_SendPkgInServer(backendPkg)
  }
}

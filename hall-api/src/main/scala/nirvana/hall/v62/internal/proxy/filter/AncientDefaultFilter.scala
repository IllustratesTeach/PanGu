package nirvana.hall.v62.internal.proxy.filter

import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.GNETREQUESTHEADOBJECT
import nirvana.hall.v62.internal.proxy.{GbaseProxyClient, GbaseItemPkgHandler}

/**
 * Created by songpeng on 16/5/4.
 * 默认filter，如果没有filter处理请求，转给6.2处理
 */
class AncientDefaultFilter extends BaseAncientFilter{
  override def handle(request: GNETREQUESTHEADOBJECT, pkg: GBASE_ITEMPKG_OPSTRUCT, handler: GbaseItemPkgHandler): Boolean = {
    val client = new GbaseProxyClient()
    val backendPkg = client.sendAndReceiveFromBackend(pkg)
    info("from backend received")
    GAFIS_RMTLIB_SendPkgInServer(backendPkg)
    true
  }
}

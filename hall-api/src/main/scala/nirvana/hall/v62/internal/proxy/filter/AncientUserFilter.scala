package nirvana.hall.v62.internal.proxy.filter

import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.ghpcbase.gnopcode
import nirvana.hall.c.services.gloclib.glocndef.GNETREQUESTHEADOBJECT
import nirvana.hall.v62.internal.proxy.{GbaseProxyClient, GbaseItemPkgHandler}

/**
 * Created by songpeng on 16/5/4.
 */
class AncientUserFilter extends BaseAncientFilter{
  override def handle(request: GNETREQUESTHEADOBJECT, pkg: GBASE_ITEMPKG_OPSTRUCT, handler: GbaseItemPkgHandler): Boolean = {
    if(request.nOpCode == gnopcode.OP_USER_LOGOUT){
      val client = new GbaseProxyClient()
      client.sendFromBackend(pkg)

      true
    }else{
      handler.handle(request, pkg)
    }
  }
}

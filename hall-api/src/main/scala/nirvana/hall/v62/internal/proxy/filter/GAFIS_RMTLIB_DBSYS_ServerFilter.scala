package nirvana.hall.v62.internal.proxy.filter

import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.ghpcbase.gnopcode
import nirvana.hall.c.services.gloclib.glocndef.GNETREQUESTHEADOBJECT
import nirvana.hall.v62.internal.proxy.GbaseItemPkgHandler

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-25
  */
class GAFIS_RMTLIB_DBSYS_ServerFilter extends BaseAncientFilter{
  override def handle(request: GNETREQUESTHEADOBJECT, pkg: GBASE_ITEMPKG_OPSTRUCT, handler: GbaseItemPkgHandler): Boolean = {
    val nOpClass = NETREQ_GetOpClass(request)
    val executed = nOpClass == gnopcode.OP_CLASS_SYS && GAFIS_RMTLIB_DBSYS_Server(request)
    executed || handler.handle(request,pkg)
  }
}

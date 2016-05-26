package nirvana.hall.v62.internal.proxy.filter

import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.ghpcbase.gnopcode
import nirvana.hall.c.services.gloclib.glocndef.GNETREQUESTHEADOBJECT
import nirvana.hall.v62.internal.proxy.GbaseItemPkgHandler

/**
  * 响应对TP进行操作
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-24
  */
class GAFIS_RMTLIB_TPSVR_ServerFilter extends BaseAncientFilter{
  override def handle(pReq: GNETREQUESTHEADOBJECT, pkg: GBASE_ITEMPKG_OPSTRUCT, handler: GbaseItemPkgHandler): Boolean = {
    val nOpClass = NETREQ_GetOpClass(pReq)
    val executed = nOpClass == gnopcode.OP_CLASS_TPLIB && GAFIS_RMTLIB_TPSVR_Server(pReq,pkg)
    executed || handler.handle(pReq,pkg)
  }
}

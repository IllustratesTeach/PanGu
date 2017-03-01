package nirvana.hall.v62.proxy.filter

import nirvana.hall.api.services.QueryService
import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.GNETREQUESTHEADOBJECT
import nirvana.hall.c.services.grmtlib.grmtcode
import nirvana.hall.v62.proxy.GbaseItemPkgHandler

/**
  * 响应对LP的操作
  * Created by songpeng on 16/5/29.
  */
class GAFIS_RMTLIB_QUERYSVR_ServerFilter(queryService: QueryService) extends BaseAncientFilter{

   override def findQueryService: QueryService =  queryService

   override def handle(request: GNETREQUESTHEADOBJECT, pkg: GBASE_ITEMPKG_OPSTRUCT, handler: GbaseItemPkgHandler): Boolean = {
     val nOpClass = NETREQ_GetOpClass(request)
     val nOpCode = NETREQ_GetOpCode(request);
//     val executed = nOpClass == gnopcode.OP_CLASS_QUERY &&  GAFIS_RMTLIB_QUERY_Server(request,pkg)
     val executed = nOpClass == grmtcode.OP_CLASS_RMTLIB &&
     ( nOpCode match {
       case grmtcode.OP_RMTLIB_QUERY_ADD |
            grmtcode.OP_RMTLIB_QUERY_UPDATE |
            grmtcode.OP_RMTLIB_QUERY_GET |
            grmtcode.OP_RMTLIB_QUERY_DEL |
            grmtcode.OP_RMTLIB_QUERY_RETR |
            grmtcode.OP_RMTLIB_QUERY_GETESTIMATEFINTIME |
            grmtcode.OP_RMTLIB_QUERY_GETRESULT =>
         GAFIS_RMTLIB_QUERY_Server(request, pkg)
       case other => false
     })

     executed || handler.handle(request,pkg)
   }
 }

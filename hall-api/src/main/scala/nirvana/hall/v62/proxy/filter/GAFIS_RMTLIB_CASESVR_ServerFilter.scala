package nirvana.hall.v62.proxy.filter

import nirvana.hall.api.services.CaseInfoService
import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.ghpcbase.gnopcode
import nirvana.hall.c.services.gloclib.glocndef.GNETREQUESTHEADOBJECT
import nirvana.hall.v62.proxy.GbaseItemPkgHandler

/**
  * 响应对LP的操作
  * Created by songpeng on 16/5/29.
  */
class GAFIS_RMTLIB_CASESVR_ServerFilter(caseInfoService: CaseInfoService) extends BaseAncientFilter{

   override def findCaseInfoService: CaseInfoService = caseInfoService

   override def handle(request: GNETREQUESTHEADOBJECT, pkg: GBASE_ITEMPKG_OPSTRUCT, handler: GbaseItemPkgHandler): Boolean = {
     val nOpClass = NETREQ_GetOpClass(request)
     val executed = nOpClass == gnopcode.OP_CLASS_CASE &&  GAFIS_RMTLIB_CASE_Server(request,pkg)
     executed || handler.handle(request,pkg)
   }
 }

package nirvana.hall.v62.internal.c.grmtlib

import nirvana.hall.api.internal.DataConverter
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.GNETANSWERHEADOBJECT
import nirvana.hall.c.services.grmtlib.grmtcode._
import nirvana.hall.v62.internal.AncientClientSupport
import nirvana.hall.v62.internal.c.gbaselib.gitempkg
import nirvana.hall.v62.internal.c.gloclib.galocpkg
import nirvana.hall.v62.internal.c.gnetlib.reqansop

trait gtxfunc {
  this:AncientClientSupport with gitempkg with grmtpkg with galocpkg with grmtcsr with reqansop=>
  def NET_GAFIS_TXSVR_QUERY_Add(nDBID: Short, nTableID: Short, pstQuery: GAQUERYSTRUCT, nOption: Int = 0, nRmtOpt: Int): Unit =executeInChannel{ channel=>
    val request = createRequestHeader
    request.nOption = nOption
    request.nDBID = nDBID
    request.nTableID = nTableID
    request.nOpClass = OP_CLASS_RMTLIB.asInstanceOf[Short]
    request.nOpCode = OP_RMTLIB_QUERY_ADD.asInstanceOf[Short]
    request.nOption = nOption
    request.nRetVal = nRmtOpt

    val stSendPkg = GBASE_ITEMPKG_New
    GAFIS_PKG_AddRmtRequest(stSendPkg,request)
    GAFIS_PKG_AddUnitCode(stSendPkg, "210000")//单位代码
    GAFIS_PKG_Query2Pkg(pstQuery, stSendPkg)
    GAFIS_RMTLIB_SendPkgInClient(channel, stSendPkg)
    val itemPkg = GAFIS_RMTLIB_RecvPkg(channel)
    val stAns = GAFIS_PKG_GetRmtAnswer(itemPkg)
    val pAns = new GNETANSWERHEADOBJECT
    val retval = NETANS_GetLongRetVal(pAns)
    if(retval > 0){
      pstQuery.stSimpQry.nQueryID = DataConverter.convertLongAsSixByteArray(retval)
      if(pAns.bnData.length > 0){
//        strcpy( pstQuery->szRmtQryKey, (char*)pAns->bnData);
//        pstQuery->szRmtQryKey[sizeof(pstQuery->szRmtQryKey) -1] = '\0';
        pstQuery.szRmtQryKey = new String(pAns.bnData, AncientConstants.GBK_ENCODING)
      }
    }

  }

}

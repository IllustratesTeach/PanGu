package nirvana.hall.v62.internal.c.grmtlib

import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.ghpcbase.gnopcode
import nirvana.hall.c.services.gloclib.glocndef.{GNETANSWERHEADOBJECT, GNETREQUESTHEADOBJECT}
import nirvana.hall.v62.internal.AncientClientSupport
import nirvana.hall.v62.internal.c.gbaselib.gitempkg
import nirvana.hall.v62.internal.c.gnetlib.{reqansop, gnetcsr}

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-18
  */
trait grmtsvr {
  this:AncientClientSupport
    with gitempkg
    with grmtcsr
    with grmtpkg
    with gnetcsr
    with reqansop =>
  def GAFIS_RMTLIB_TPSVR_Server(nClientNo:Int,
                                pReq:GNETREQUESTHEADOBJECT,
                                pAns:GNETANSWERHEADOBJECT,
                                pstRecvPkg:GBASE_ITEMPKG_OPSTRUCT,
                                bIsReq50:Int){
    val nOpClass = NETREQ_GetOpClass(pReq);
    val nOpCode = NETREQ_GetOpCode(pReq);
    val nDBID	 = NETREQ_GetDBID(pReq);
    val nTableID = NETREQ_GetTableID(pReq);
    val nOption	 = NETREQ_GetOption(pReq);
    val nRmtOpt	 = NETREQ_GetRetVal(pReq);
    val nZipMethod = pReq.bnData(48);
    val nZipRatio  = pReq.bnData(49);

    nOpCode match{
      case gnopcode.OP_TPLIB_ADD=>
//        GAFIS_PKG_GetTpCard(pstRecvPkg,&stTPCard)
    }
    //判断用户是否有权利
    //NET_GAFIS_USER_HasRight


  }

}

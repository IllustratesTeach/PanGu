package nirvana.hall.v62.internal.c.grmtlib

import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.ghpcbase.gnopcode._
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.{GNETANSWERHEADOBJECT, GNETREQUESTHEADOBJECT}
import nirvana.hall.c.services.grmtlib.grmtcode
import nirvana.hall.v62.internal.AncientClientSupport
import nirvana.hall.v62.internal.c.gbaselib.gitempkg
import nirvana.hall.v62.internal.c.gloclib.galocpkg
import nirvana.hall.v62.internal.c.gnetlib.{gnetcsr, reqansop}

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-18
  */
trait grmtsvr {
  this:AncientClientSupport
    with galocpkg
    with gitempkg
    with grmtcsr
    with grmtpkg
    with gnetcsr
    with reqansop =>
  def GAFIS_RMTLIB_TPSVR_Server(pReq:GNETREQUESTHEADOBJECT,
                                pstRecvPkg:GBASE_ITEMPKG_OPSTRUCT,
                                bIsReq50:Int=0):Boolean={
    val pAns = new GNETANSWERHEADOBJECT
    val nOpClass = NETREQ_GetOpClass(pReq);
    val nOpCode = NETREQ_GetOpCode(pReq);
    val nDBID	 = NETREQ_GetDBID(pReq);
    val nTableID = NETREQ_GetTableID(pReq);
    val nOption	 = NETREQ_GetOption(pReq);
    val nRmtOpt	 = NETREQ_GetRetVal(pReq);
    val nZipMethod = pReq.bnData(48);
    val nZipRatio  = pReq.bnData(49);

    nOpCode match{
      case OP_TPLIB_ADD=>
        /*
         * 主干程序，
         * 1. 解析收到的包，解析出来档案数据
         * 2. 保存档案数据
         * 3. 返回结果
         */

        val stTPCard = GAFIS_PKG_GetTpCard(pstRecvPkg)
        //TODO 通过拿到的stTPCard来保存到v70数据库或者转发v62的通信服务器
        //saveStTPCardToV70.....
        //n 为保存成功的个数
        val n = stTPCard.length

        NETANS_SetRetVal(pAns,n);
        val stSendPkg = GBASE_ITEMPKG_New
        GAFIS_PKG_AddRmtAnswer(stSendPkg,pAns)
        GAFIS_RMTLIB_SendPkgInServer(stSendPkg)

        true
      case OP_TPLIB_EXIST=>
        //TODO 判断卡片是否存在

        NETANS_SetRetVal(pAns,0);
        val stSendPkg = GBASE_ITEMPKG_New
        GAFIS_PKG_AddRmtAnswer(stSendPkg,pAns)
        GAFIS_RMTLIB_SendPkgInServer(stSendPkg)

        true

      case other=>
        false
    }
  }
  def GAFIS_RMTLIB_QUERY_Server(pReq:GNETREQUESTHEADOBJECT, pstRecvPkg:GBASE_ITEMPKG_OPSTRUCT): Boolean ={
    val pAns = new GNETANSWERHEADOBJECT
    val nOpClass = NETREQ_GetOpClass(pReq);
    val nOpCode = NETREQ_GetOpCode(pReq);
    val nDBID	 = NETREQ_GetDBID(pReq);
    val nTableID = NETREQ_GetTableID(pReq);
    val nOption	 = NETREQ_GetOption(pReq);
    val nRmtOpt	 = NETREQ_GetRetVal(pReq);

    nOpCode match{
      case grmtcode.OP_RMTLIB_QUERY_ADD=>
        val stQuery = GAFIS_PKG_GetQuery(pstRecvPkg)
        //TODO 通过得到的stQuery来进行发送查询


        //nAddRet为成功发送查询ID
        val nAddRet = 1;

        NETANS_SetRetVal(pAns,nAddRet);
        val stSendPkg = GBASE_ITEMPKG_New
        GAFIS_PKG_AddRmtAnswer(stSendPkg,pAns)
        GAFIS_RMTLIB_SendPkgInServer(stSendPkg)

        true
      case 	grmtcode.OP_RMTLIB_QUERY_GETRESULT=>
        val stQuery = new GAQUERYSTRUCT
        stQuery.stSimpQry.nQueryID = pReq.bnData

        //TODO 利用得到的SID进行查询候选队列
        val nRet = 1 //如果有候选队列


        val stSendPkg = GBASE_ITEMPKG_New
        NETANS_SetRetVal(pAns,nRet);

        GAFIS_PKG_AddRmtAnswer(stSendPkg,pAns)
        if(nRet > 0 )
          GAFIS_PKG_Query2Pkg(stQuery,stSendPkg)
        GAFIS_RMTLIB_SendPkgInServer(stSendPkg)

        true
      case other =>
        false
    }
  }
}

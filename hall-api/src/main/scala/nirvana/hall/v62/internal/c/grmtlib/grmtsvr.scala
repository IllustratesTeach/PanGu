package nirvana.hall.v62.internal.c.grmtlib

import java.io.ByteArrayOutputStream

import monad.support.services.LoggerSupport
import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.ghpcbase.gnopcode._
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.{GNETANSWERHEADOBJECT, GNETREQUESTHEADOBJECT}
import nirvana.hall.c.services.grmtlib.grmtcode
import nirvana.hall.protocol.api.TPCardProto.TPCardAddRequest
import nirvana.hall.v62.internal.AncientClientSupport
import nirvana.hall.v62.internal.c.gbaselib.gitempkg
import nirvana.hall.v62.internal.c.gloclib.{galoctpConverter, galocpkg}
import nirvana.hall.v62.internal.c.gnetlib.reqansop
import nirvana.hall.v70.jpa.GafisPerson
import nirvana.hall.v62.proxy.LocalServiceFinder

import scala.concurrent.ExecutionContext

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-18
  */
trait grmtsvr {
  this:AncientClientSupport
    with LoggerSupport
    with galocpkg
    with gitempkg
    with LocalServiceFinder
    with grmtcsr
    with grmtpkg
    with reqansop =>
  private implicit val executionContext = ExecutionContext.global
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
        val stTPCardOpt = GAFIS_PKG_GetTpCard(pstRecvPkg)
        //TODO 通过拿到的stTPCard来保存到v70数据库或者转发v62的通信服务器
        //saveStTPCardToV70.....
        /*stTPCard.foreach{gtpCard =>
          val tpCard = galoctpConverter.convertGTPCARDINFOSTRUCT2ProtoBuf(gtpCard)
          val person = ProtobufConverter.convertTPCard2GafisPerson(tpCard)
          val portraits = ProtobufConverter.convertTPCard2GafisGatherPortrait(tpCard)
          val fingers = ProtobufConverter.convertTPCard2GafisGatherFinger(tpCard)
          person.save()
          portraits.foreach(_.save())
          fingers.foreach(_.save())
        }*/
        //n 为保存成功的个数
        var n = 0
        stTPCardOpt.map{tpCard=>
          //测试使用，本机上报本机
//          tpCard.szCardID=System.currentTimeMillis().toString
          galoctpConverter.convertGTPCARDINFOSTRUCT2ProtoBuf(tpCard)
        }.foreach{ card =>
          try {
            val request = TPCardAddRequest.newBuilder()
            request.setCard(card)
            findTPCardService.addTPCard(request.build())
            n += 1
          }catch{
            case e:Throwable=>
              error("fail to save tpcard "+card.getStrCardID+" ---> "+e.getMessage,e)
          }
        }

        NETANS_SetRetVal(pAns,n)
        val stSendPkg = GBASE_ITEMPKG_New
        GAFIS_PKG_AddRmtAnswer(stSendPkg,pAns)
        GAFIS_RMTLIB_SendPkgInServer(stSendPkg)

        true
      case OP_TPLIB_EXIST=>
        //获取编号,去除后边的空字符
        val cardIdBuf = new ByteArrayOutputStream()
        pReq.bnData.foreach(c => if(c != 0) cardIdBuf.write(c))
        val cardId = cardIdBuf.toString("GB2312")
        //根据人员编号查询7.0数据库
        val person = GafisPerson.findOption(cardId)
        println(person)
        //如果人员编号存在返回1，不存在0
        val n = if(person != None) 1 else 0

        NETANS_SetRetVal(pAns,n)
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

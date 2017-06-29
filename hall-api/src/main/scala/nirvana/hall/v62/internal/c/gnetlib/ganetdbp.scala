package nirvana.hall.v62.internal.c.gnetlib

import nirvana.hall.c.services.ganumia.gadbcol._
import nirvana.hall.c.services.ghpcbase.gnopcode._
import nirvana.hall.c.services.gloclib.gadbprop.GADBPROPSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.{GNETANSWERHEADOBJECT, GNETREQUESTHEADOBJECT}
import nirvana.hall.v62.internal.AncientClientSupport

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-10
 */
trait ganetdbp {
  this:AncientClientSupport with reqansop=>
  def NET_GAFIS_SYS_GetAllDB(nOption:Int=0):Array[GADBPROPSTRUCT]=
    executeInChannel{pstCon=>

      val pReq = createRequestHeader
      val stAns = new GNETANSWERHEADOBJECT
      NETREQ_SetOpClass(pReq, OP_CLASS_SYS);	// set op class
      NETREQ_SetOpCode(pReq, OP_SYS_GETALLDB);	// set op code
      NETOP_SENDREQ(pstCon, pReq);
      NETOP_RECVANS(pstCon, stAns);
      validateResponse(pstCon,stAns)
      val retval = NETANS_GetRetVal(stAns)
      if ( retval>0 ) {

        NETANS_SetRetVal(stAns, 1);
        NETOP_SENDANS(pstCon, stAns);
        Range(0,retval).map(x=>new GADBPROPSTRUCT()).map(NETOP_RECVDATA(pstCon,_)).toArray
      }else{
        Array[GADBPROPSTRUCT]()
      }
  }

  def NET_GAFIS_SYS_GetDBByID(nDBID:Short,nOption:Int=0):GADBPROPSTRUCT=executeInChannel {channel=>
    val pReq = createRequestHeader
    pReq.nOpClass = OP_CLASS_SYS.asInstanceOf[Short]
    pReq.nOpCode = OP_SYS_GETDBBYID.asInstanceOf[Short]

    pReq.nDBID = nDBID

    val response = channel.writeMessage[GNETANSWERHEADOBJECT](pReq)
    validateResponse(channel,response)
    channel.receive[GADBPROPSTRUCT]()
  }
  def NET_GAFIS_SYS_GetTableSchema(nDBID:Short, nTableID:Short,nOption:Int=0):Array[GACOLUMNPROPSTRUCT]= executeInChannel { channel =>
    val pReq = createRequestHeader
    NETREQ_SetOption(pReq, nOption);
    NETREQ_SetDBID(pReq, nDBID);
    NETREQ_SetTableID(pReq, nTableID);
    NETREQ_SetOpClass(pReq, OP_CLASS_SYS);
    NETREQ_SetOpCode(pReq, OP_SYS_GETTABLESCHEMA);
    NETOP_SENDREQ(channel, pReq);

    val pAns = new GNETANSWERHEADOBJECT
    NETOP_RECVANS(channel, pAns);

    validateResponse(channel,pAns)
    val n = pAns.nReturnValue
    NETANS_SetRetVal(pAns, 1);
    NETOP_SENDANS(channel, pAns);

    val ret = Range(0,n).map{col=>
      val col = new GACOLUMNPROPSTRUCT
      NETOP_RECVDATA(channel,col);
    }

    return ret.toArray
  }
}

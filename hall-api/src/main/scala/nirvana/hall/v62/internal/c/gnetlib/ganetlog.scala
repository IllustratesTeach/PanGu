package nirvana.hall.v62.internal.c.gnetlib

import nirvana.hall.c.services.ghpcbase.gnopcode
import nirvana.hall.c.services.gloclib.galoclog.{GAFIS_VERIFYLOGRETRSTRUCT, GAFIS_VERIFYLOGSTRUCT}
import nirvana.hall.c.services.gloclib.glocndef.GNETANSWERHEADOBJECT
import nirvana.hall.v62.internal.AncientClientSupport
import nirvana.hall.v62.internal.c.gloclib.gaqryqueConverter
import nirvana.hall.v62.services.ChannelOperator

/**
  * Created by songpeng on 2017/6/21.
  */
trait ganetlog {
  this: AncientClientSupport with gnetcsr with reqansop =>

  def NET_GAFIS_VERIFYLOG_Op(channel: ChannelOperator, pstLog: GAFIS_VERIFYLOGSTRUCT, pstRetr: GAFIS_VERIFYLOGRETRSTRUCT, nOp: Int): Unit = {
    val pAns = new GNETANSWERHEADOBJECT
    val pReq = createRequestHeader

    pReq.nOpClass = gnopcode.OP_CLASS_VERIFYLOG.toShort
    pReq.nOpCode = nOp.toShort

    nOp match {
      case gnopcode.OP_ADM_VERIFYLOG_RETR =>
        throw new UnsupportedOperationException("UnsupportedOperationException opCode:" + nOp)
      case gnopcode.OP_ADM_VERIFYLOG_UPDATE | gnopcode.OP_ADM_VERIFYLOG_ADD =>
        NETOP_SENDREQ(channel, pReq)
        GAFIS_NETSCR_SendVerifyLog(channel, pstLog)
        NETANS_GetRetVal(pAns)
      case gnopcode.OP_ADM_VERIFYLOG_GET =>
        //        pReq.bnData = ByteBuffer.allocate(32).put(pstLog.nSID)
        //          .putShort(0).put(pstLog.szBreakID.getBytes()).array()
        pReq.bnData = pstLog.nSID
        NETOP_SENDREQ(channel, pReq)
        NETOP_RECVANS(channel, pAns)
        val retval = NETANS_GetRetVal(pAns)
        if (retval > 0) {
          GAFIS_NETSCR_RecvVerifyLog(channel, pstLog)
        }
      case gnopcode.OP_ADM_VERIFYLOG_DEL =>
        pReq.bnData = pstLog.nSID
        NETOP_SENDREQ(channel, pReq)
        NETOP_RECVANS(channel, pAns)
        val retval = NETANS_GetRetVal(pAns)
    }
  }

  def NET_GAFIS_VERIFYLOG_Add(pstLog: GAFIS_VERIFYLOGSTRUCT): Unit = executeInChannel { channel =>
    NET_GAFIS_VERIFYLOG_Op(channel, pstLog, null, gnopcode.OP_ADM_VERIFYLOG_ADD)
  }

  def NET_GAFIS_VERIFYLOG_Update(pstLog: GAFIS_VERIFYLOGSTRUCT): Unit = executeInChannel { channel =>
    NET_GAFIS_VERIFYLOG_Op(channel, pstLog, null, gnopcode.OP_ADM_VERIFYLOG_UPDATE)
  }

  def NET_GAFIS_VERIFYLOG_Get(sid: Long): GAFIS_VERIFYLOGSTRUCT = executeInChannel { channel =>
    val pstLog = new GAFIS_VERIFYLOGSTRUCT
    pstLog.nSID = gaqryqueConverter.convertLongAsSixByteArray(sid)
    NET_GAFIS_VERIFYLOG_Op(channel, pstLog, null, gnopcode.OP_ADM_VERIFYLOG_GET)
    return pstLog
  }
  def NET_GAFIS_VERIFYLOG_Del(sid: Long): Unit = executeInChannel { channel =>
    val pstLog = new GAFIS_VERIFYLOGSTRUCT
    pstLog.nSID = gaqryqueConverter.convertLongAsSixByteArray(sid)
    NET_GAFIS_VERIFYLOG_Op(channel, pstLog, null, gnopcode.OP_ADM_VERIFYLOG_GET)
  }

  /*
    * int		NET_GAFIS_VERIFYLOG_Del(HGNETCONNECTIONOBJECT hCon, ga_uint8 nSID)
{
	GAFIS_VERIFYLOGSTRUCT	stLog, *pstLog;

	pstLog = &stLog;
	IntToSID(nSID, stLog.nSID);
	return	NET_GAFIS_VERIFYLOG_Op(hCon, &pstLog, NULL, OP_ADM_VERIFYLOG_DEL);
}
    */

}
/**
import nirvana.hall.c.services.ghpcbase.gnopcode._
import nirvana.hall.c.services.gloclib.galoclog.GAFIS_VERIFYLOGSTRUCT
import nirvana.hall.v62.internal.{AncientClientSupport, NoneResponse}

  * Created by win-20161010 on 2017/6/21.
trait ganetlog {
  this:AncientClientSupport with gnetcsr with reqansop=>
  def NET_GAFIS_VERIFY_Add(nDBID:Short,nTableID:Short,
                           pItemInfo:GAFIS_VERIFYLOGSTRUCT,
                           nOption:Int=0):Unit= NET_GAFIS_VERIFY_AddOrUpdate(Action_verify.ADD,nDBID,nTableID,pItemInfo)
  def NET_GAFIS_VERIFY_Update(nDBID:Short,nTableID:Short,
                              pItemInfo:GAFIS_VERIFYLOGSTRUCT,
                              nOption:Int=0):Unit= NET_GAFIS_VERIFY_AddOrUpdate(Action_verify.UPDATE,nDBID,nTableID,pItemInfo)

  object Action_verify extends Enumeration{
    type tpe = Value
    val ADD,UPDATE = Value
  }

  private def NET_GAFIS_VERIFY_AddOrUpdate(action:Action_verify.tpe,
                                           nDBID:Short,nTableID:Short,
                                           pItemInfo:GAFIS_VERIFYLOGSTRUCT,
                                           nOption:Int=0)=executeInChannel { channel =>
    val pReq = createRequestHeader
    pReq.nOption = nOption
    pReq.nDBID = nDBID
    pReq.nTableID = nTableID


    val isAdd = action == Action_verify.ADD

    pReq.nOpClass = OP_CLASS_VERIFYLOG.asInstanceOf[Short]

    if(isAdd)
      pReq.nOpCode = OP_ADM_VERIFYLOG_ADD.asInstanceOf[Short]
    else
      pReq.nOpCode = OP_ADM_VERIFYLOG_UPDATE.asInstanceOf[Short]

    channel.writeMessage[NoneResponse](pReq)
    GAFIS_NETSCR_SendVerifyLog(channel, pItemInfo)
  }

  def NET_GAFIS_VERIFY_DEL(nDBID:Short,nTableID:Short,
                           pItemInfo:GAFIS_VERIFYLOGSTRUCT,
                           nOption:Int=0)=executeInChannel { channel =>
    val pReq = createRequestHeader
    pReq.nOption = nOption
    pReq.nDBID = nDBID
    pReq.nTableID = nTableID


    pReq.nOpClass = OP_CLASS_VERIFYLOG.asInstanceOf[Short]
    pReq.nOpCode = OP_ADM_VERIFYLOG_DEL.asInstanceOf[Short]
    channel.writeMessage[NoneResponse](pReq)
    GAFIS_NETSCR_SendVerifyLog(channel, pItemInfo)
  }
}
  */

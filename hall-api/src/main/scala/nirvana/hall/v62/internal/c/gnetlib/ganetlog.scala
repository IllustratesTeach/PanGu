package nirvana.hall.v62.internal.c.gnetlib


import nirvana.hall.c.services.ghpcbase.gnopcode._
import nirvana.hall.c.services.gloclib.galoclog.GAFIS_VERIFYLOGSTRUCT
import nirvana.hall.v62.internal.{AncientClientSupport, NoneResponse}

/**
  * Created by win-20161010 on 2017/6/21.
  */
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

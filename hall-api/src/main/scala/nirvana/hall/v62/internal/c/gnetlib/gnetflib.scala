package nirvana.hall.v62.internal.c.gnetlib

import java.nio.ByteBuffer

import nirvana.hall.v62.internal.c.ghpcbase.gnopcode._
import nirvana.hall.v62.internal.c.gloclib.gadbprop.GADBPROPSTRUCT
import nirvana.hall.v62.internal.c.gloclib.galoclp.GLPCARDINFOSTRUCT
import nirvana.hall.v62.internal.c.gloclib.galoctp.{GCARDITEMOBJECT, GTPCARDINFOSTRUCT}
import nirvana.hall.v62.internal.c.gloclib.glocndef.GNETANSWERHEADOBJECT
import nirvana.hall.v62.internal.c.gloclib.{gadbprop, galoclp, galoctp, glocdef}
import nirvana.hall.v62.internal.{AncientClientSupport, NoneResponse}
import nirvana.hall.v62.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-10
 */
trait gnetflib {
  this:AncientClientSupport with gnetcsr with ganetdbp =>

  object Action extends Enumeration{
    type tpe = Value
    val ADD,UPDATE = Value
  }
  def NET_GAFIS_FLIB_Get(nDBID:Short,nTableID:Short,
    pszKey:String,
    pItemInd:Array[GCARDITEMOBJECT],
    pItemInfo:AncientData,
    nOption:Int)=executeInChannel {channel=>
    val pReq = createRequestHeader

    var option = nOption
    pItemInfo match{
      case item:GTPCARDINFOSTRUCT =>
        if((item.nItemFlag & galoctp.TPCARDINFO_ITEMFLAG_ADMDATA) > 0)  option |= glocdef.FLIBGETOPT_GETADMDATA
        if((item.nItemFlag & galoctp.TPCARDINFO_ITEMFLAG_INFOEX) > 0)  option |= glocdef.FLIBGETOPT_GETINFOEX

        pReq.nOpClass = OP_CLASS_TPLIB.asInstanceOf[Short]
        pReq.nOpCode = OP_TPLIB_GET.asInstanceOf[Short]

      case item:GLPCARDINFOSTRUCT =>
        if((item.nItemFlag & galoclp.LPCARDINFO_ITEMFLAG_ADMDATA) > 0)  option |= glocdef.FLIBGETOPT_GETADMDATA
        if((item.nItemFlag & galoclp.LPCARDINFO_ITEMFLAG_EXTRAINFO) > 0)  option |= glocdef.FLIBGETOPT_GETINFOEX

        pReq.nOpClass = OP_CLASS_LPLIB.asInstanceOf[Short]
        pReq.nOpCode = OP_LPLIB_GET.asInstanceOf[Short]
    }
    pReq.nOption = option
    pReq.nDBID = nDBID
    pReq.nTableID = nTableID

    val nIndCount = if(pItemInd == null) 0 else pItemInd.length
    pReq.bnData =  ByteBuffer.allocate(48 + 4).put(pszKey.getBytes).putInt(48,nIndCount).array()
    channel.writeMessage[NoneResponse](pReq)
    if ( nIndCount >0 ) {
      val pAns = channel.receive[GNETANSWERHEADOBJECT]()
      validateResponse(channel,pAns)
      pItemInd.foreach(channel.writeMessage[NoneResponse](_))
    }
    val pAns= channel.receive[GNETANSWERHEADOBJECT]()
    validateResponse(channel,pAns)

    pItemInfo match {
      case item: GTPCARDINFOSTRUCT =>
        GAFIS_NETSCR_RecvTPCardInfo(channel,pAns,item)
      case item: GLPCARDINFOSTRUCT =>
        GAFIS_NETSCR_RecvLPCardInfo(channel,pAns,item)
    }
  }


  // adding db cached admin.
  private def NET_GAFIS_SYS_GetDBType(nDBID:Short,nOption:Int=0):GADBPROPSTRUCT= {
    //TODO cache db properties
    NET_GAFIS_SYS_GetDBByID(nDBID)
  }

  def NET_GAFIS_FLIB_Del(nDBID:Short,nTableID:Short,pszKey:String,nOption:Int):Unit=executeInChannel{channel=>
    val pReq = createRequestHeader

    pReq.bnData = pszKey.getBytes
    pReq.nOption = nOption
    pReq.nDBID = nDBID
    pReq.nTableID = nTableID

    val stDBProp = NET_GAFIS_SYS_GetDBType(nDBID)

    stDBProp.nType  match {
      case gadbprop.GADBPROP_TYPE_TENPRINT =>
        // tenprint database
        pReq.nOpClass = OP_CLASS_TPLIB.asInstanceOf[Short]
        pReq.nOpCode = OP_TPLIB_DEL.asInstanceOf[Short]
      case gadbprop.GADBPROP_TYPE_LATENT =>
        // latent
        pReq.nOpClass = OP_CLASS_LPLIB.asInstanceOf[Short]
        pReq.nOpCode = OP_LPLIB_DEL.asInstanceOf[Short]
      case other=>
        throw new IllegalArgumentException("dbType"+stDBProp.nType)
    }

    val response = channel.writeMessage[GNETANSWERHEADOBJECT](pReq)
    validateResponse(channel,response)
  }

  def NET_GAFIS_FLIB_Add(nDBID:Short,nTableID:Short,
                         pszKey:String,
                         pItemInfo:AncientData,
                         nOption:Int=0):Unit= NET_GAFIS_FLIB_AddOrUpdate(Action.ADD,nDBID,nTableID,pszKey,pItemInfo)
  def NET_GAFIS_FLIB_Update(nDBID:Short,nTableID:Short,
                         pszKey:String,
                         pItemInfo:AncientData,
                         nOption:Int=0):Unit= NET_GAFIS_FLIB_AddOrUpdate(Action.UPDATE,nDBID,nTableID,pszKey,pItemInfo)
  private def NET_GAFIS_FLIB_AddOrUpdate(action:Action.tpe,
                         nDBID:Short,nTableID:Short,
                         pszKey:String,
                         pItemInfo:AncientData,
                         nOption:Int=0)=executeInChannel { channel =>
    val pReq = createRequestHeader
    pReq.bnData = pszKey.getBytes
    pReq.nOption = nOption
    pReq.nDBID = nDBID
    pReq.nTableID = nTableID
    val isAdd = action == Action.ADD

    pItemInfo match {
      case tp: GTPCARDINFOSTRUCT =>
        pReq.nOpClass = OP_CLASS_TPLIB.asInstanceOf[Short]

        if(isAdd)
          pReq.nOpCode = OP_TPLIB_ADD.asInstanceOf[Short]
        else
          pReq.nOpCode = OP_TPLIB_UPDATE.asInstanceOf[Short]

        channel.writeMessage[NoneResponse](pReq)
        GAFIS_NETSCR_SendTPCardInfo(channel, tp)
      case lp: GLPCARDINFOSTRUCT =>
        pReq.nOpClass = OP_CLASS_LPLIB.asInstanceOf[Short]
        if(isAdd)
          pReq.nOpCode = OP_LPLIB_ADD.asInstanceOf[Short]
        else
          pReq.nOpCode = OP_LPLIB_UPDATE.asInstanceOf[Short]
        channel.writeMessage[NoneResponse](pReq)
        GAFIS_NETSCR_SendLPCardInfo(channel, lp)
      case other =>
        throw new IllegalArgumentException("wrong data type," + other.getClass)
    }

    val response = channel.receive[GNETANSWERHEADOBJECT]()
    validateResponse(channel, response)
  }
}

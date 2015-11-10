package nirvana.hall.v62.internal.c.gnetlib

import nirvana.hall.v62.internal.c.gloclib.galoclp.{GAFIS_LP_EXTRAINFO, GLPCARDINFOSTRUCT}
import nirvana.hall.v62.internal.c.gloclib.galoctp.{GAFIS_TPADMININFO_EX, GTPCARDINFOSTRUCT}
import nirvana.hall.v62.internal.c.gloclib.gaqryque.{GAFIS_QUERYINFO, GAQUERYSTRUCT}
import nirvana.hall.v62.internal.c.gloclib.glocdef.{GATEXTITEMSTRUCT, GAFISMICSTRUCT}
import nirvana.hall.v62.internal.c.gloclib.glocndef.GNETANSWERHEADOBJECT
import nirvana.hall.v62.internal.{AncientClientSupport, NoneResponse}
import nirvana.hall.v62.services.ChannelOperator

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-10
 */
trait gnetcsr {
  this: AncientClientSupport =>
  protected def GAFIS_NETSCR_RecvLPCardInfo(channel:ChannelOperator,pAns:GNETANSWERHEADOBJECT,pstCard:GLPCARDINFOSTRUCT) {
    val card = channel.receive[GLPCARDINFOSTRUCT]()
    pstCard.bMicCanBeFreed = 0;
    pstCard.bTextCanBeFreed = 0;
    pstCard.nMicItemCount = card.nMicItemCount
    pstCard.nTextItemCount = card.nTextItemCount
    pstCard.nExtraInfoLen = card.nExtraInfoLen


    pAns.nReturnValue = 1
    pAns.bnData = "$version=001$".getBytes

    channel.writeMessage[NoneResponse](pAns)

    // now we will receive all the structure's
    if ( pstCard.nMicItemCount > 0 ) {
      val mics = 0 until pstCard.nMicItemCount map(x=>channel.receive[GAFISMICSTRUCT]())
      pstCard.pstMIC_Data = mics.toArray
    }
    if ( pstCard.nTextItemCount > 0 ) {
      val items = 0 until pstCard.nTextItemCount map(x=>channel.receive[GATEXTITEMSTRUCT]())
      pstCard.pstText_Data = items.toArray
    }

    pAns.nReturnValue = 1
    channel.writeMessage[NoneResponse](pAns)

    pstCard.pstMIC_Data.foreach(GAFIS_NETSCR_RecvMICStruct(channel,_))
    pstCard.pstText_Data.filter(_.bIsPointer == 1).foreach(x=>x.stData.textContent = channel.receiveByteArray(x.nItemLen).array())

    if ( pstCard.nExtraInfoLen > 0 ) {
      pstCard.pstExtraInfo_Data = channel.receive[GAFIS_LP_EXTRAINFO]()
    }
  }
  protected def GAFIS_NETSCR_RecvTPCardInfo(channel:ChannelOperator,pAns:GNETANSWERHEADOBJECT,pstCard:GTPCARDINFOSTRUCT) {
    val card = channel.receive[GTPCARDINFOSTRUCT]()
    pstCard.bMicCanBeFreed = 0;
    pstCard.bTextCanBeFreed = 0;
    pstCard.nMicItemCount = card.nMicItemCount
    pstCard.nTextItemCount = card.nTextItemCount
    pstCard.nInfoExLen = card.nInfoExLen


    pAns.nReturnValue = 1
    pAns.bnData = "$version=001$".getBytes

    channel.writeMessage[NoneResponse](pAns)

    // now we will receive all the structure's
    if ( pstCard.nMicItemCount > 0 ) {
      val mics = 0 until pstCard.nMicItemCount map(x=>channel.receive[GAFISMICSTRUCT]())
      pstCard.pstMIC_Data = mics.toArray
    }
    if ( pstCard.nTextItemCount > 0 ) {
      val items = 0 until pstCard.nTextItemCount map(x=>channel.receive[GATEXTITEMSTRUCT]())
      pstCard.pstText_Data = items.toArray
    }

    pAns.nReturnValue = 1
    channel.writeMessage[NoneResponse](pAns)

    pstCard.pstMIC_Data.foreach(GAFIS_NETSCR_RecvMICStruct(channel,_))
    pstCard.pstText_Data.filter(_.bIsPointer == 1).foreach(x=>x.stData.textContent = channel.receiveByteArray(x.nItemLen).array())

    if ( pstCard.nInfoExLen > 0 ) {
      pstCard.pstInfoEx_Data = channel.receive[GAFIS_TPADMININFO_EX]()
    }
  }

  protected def GAFIS_NETSCR_SendTPCardInfo(channel:ChannelOperator,pstCard:GTPCARDINFOSTRUCT){
    var response = channel.writeMessage[GNETANSWERHEADOBJECT](pstCard)
    validateResponse(channel,response)
    
    var ninfoexlen = pstCard.nInfoExLen
    if (response.bnData!=null&&new String(response.bnData).indexOf("$version=001$")==0 ) {
      // need send info ex structure.
    } else {
      ninfoexlen = 0;
    }
    if ( pstCard.nMicItemCount>0 ) {
      pstCard.pstMIC_Data.foreach(channel.writeMessage[NoneResponse](_))
    }
    if ( pstCard.nTextItemCount >0 ) {
      pstCard.pstText_Data.foreach(channel.writeMessage[NoneResponse](_))
    }
    response = channel.receive[GNETANSWERHEADOBJECT]()

    if ( pstCard.nMicItemCount > 0 )  pstCard.pstMIC_Data.foreach(GAFIS_NETSCR_SendMICStruct(channel,_))
    if ( pstCard.nTextItemCount > 0 ) {
      pstCard.pstText_Data.filter(_.bIsPointer == 1).foreach { txt =>
        channel.writeByteArray[NoneResponse](txt.stData.textContent, 0, txt.nItemLen)
      }
    }

    if ( ninfoexlen >0 ) {
      channel.writeMessage[NoneResponse](pstCard.pstInfoEx_Data)
    }
  }

  protected def GAFIS_NETSCR_SendLPCardInfo(channel:ChannelOperator,pstCard:GLPCARDINFOSTRUCT): Unit ={
    var response = channel.writeMessage[GNETANSWERHEADOBJECT](pstCard)
    validateResponse(channel,response)

    var ninfoexlen = pstCard.nExtraInfoLen
    if (response.bnData!=null&&new String(response.bnData).indexOf("$version=001$")==0 ) {
      // need send info ex structure.
    } else {
      ninfoexlen = 0;
    }
    if ( pstCard.nMicItemCount>0 ) {
      pstCard.pstMIC_Data.foreach(channel.writeMessage[NoneResponse](_))
    }
    if ( pstCard.nTextItemCount >0 ) {
      pstCard.pstText_Data.foreach(channel.writeMessage[NoneResponse](_))
    }
    response = channel.receive[GNETANSWERHEADOBJECT]()

    if ( pstCard.nMicItemCount > 0 )  pstCard.pstMIC_Data.foreach(GAFIS_NETSCR_SendMICStruct(channel,_))
    if ( pstCard.nTextItemCount > 0 ) {
      pstCard.pstText_Data.filter(_.bIsPointer == 1).foreach { txt =>
        channel.writeByteArray[NoneResponse](txt.stData.textContent, 0, txt.nItemLen)
      }
    }

    if ( ninfoexlen>0 ) {
      channel.writeMessage[NoneResponse](pstCard.pstExtraInfo_Data)
    }
  }
  def GAFIS_NETSCR_RecvQueryInfo(channel:ChannelOperator,response:GNETANSWERHEADOBJECT,pstQry:GAQUERYSTRUCT):Unit= {
    val qry = channel.receive[GAQUERYSTRUCT]()
    // alloc memory for all data
    pstQry.nCandHeadLen = qry.nCandHeadLen
    pstQry.nCandLen = qry.nCandLen
    pstQry.nQryCondLen = qry.nQryCondLen

    pstQry.nMICCount = qry.nMICCount
    pstQry.nSvrListLen = qry.nSvrListLen
    pstQry.nMISCondLen = qry.nMISCondLen
    pstQry.nTextSqlLen = qry.nTextSqlLen
    pstQry.nCommentLen = qry.nCommentLen
    pstQry.nQryInfoLen = qry.nQryInfoLen

    val ncandhead = pstQry.nCandHeadLen
    val ncand = pstQry.nCandLen
    val nqrycond = pstQry.nQryCondLen

    val nMicCount = pstQry.nMICCount
    val nSvrListLen = pstQry.nSvrListLen
    val nMisCondLen = pstQry.nMISCondLen
    val nTxtSqlLen = pstQry.nTextSqlLen
    val nCommentLen = pstQry.nCommentLen
    val nqryinfolen = pstQry.nQryInfoLen

    if ( ncand<=0 && ncandhead<=0 && nqrycond<=0 && nMicCount<=0 && nSvrListLen<=0 &&
      nMisCondLen<=0 && nTxtSqlLen<=0 && nCommentLen<=0 && nqryinfolen<=0 ) {
      throw new IllegalArgumentException("wrong data,data is null")
    }

    if(nMicCount > 0)
    {
      pstQry.bMICCanBeFree = 1;
    }
    if(nSvrListLen > 0)
    {
      pstQry.bSvrListCanBeFree = 1;
    }

    if ( ncand >0 ) {
      pstQry.bCandCanBeFree = 1;
    }
    if ( ncandhead >0) {
      pstQry.bCandHeadCanBeFree = 1;
    }
    if ( nqrycond >0) {
      pstQry.bQryCondCanBeFree = 1;
    }
    if ( nMisCondLen>0 ) {
      pstQry.bMISCondCanBeFree = 1;
    }

    if ( nCommentLen >0) {
      pstQry.bCommentCanBeFree = 1;
    }

    if ( nTxtSqlLen >0) {
      pstQry.bTextSqlCanBeFree = 1;	// can be free.
    }

    if ( nqryinfolen >0) {
      pstQry.bQryInfoCanBeFree = 1;
    }

    response.nReturnValue = 1
    channel.writeMessage[NoneResponse](response)

    var bClearMicPt = 0
    if( nMicCount > 0)
    {
      bClearMicPt = 1;
      val mics = 0 until nMicCount map{i=>
        channel.receive[GAFISMICSTRUCT]()
      }
      pstQry.pstMIC_Data = mics.toArray
      bClearMicPt = 0
      pstQry.pstMIC_Data.foreach(GAFIS_NETSCR_RecvMICStruct(channel,_))
    }

    if( nSvrListLen > 0 )  pstQry.pstSvrList_Data = channel.receiveByteArray(nSvrListLen).array()
    if( ncandhead> 0 )  pstQry.pstSvrList_Data = channel.receiveByteArray(ncandhead).array()
    if( ncand> 0 )  pstQry.pstSvrList_Data = channel.receiveByteArray(ncand).array()

    if ( nqrycond > 0) pstQry.pstQryCond_Data = channel.receiveByteArray(nqrycond).array()
    if ( nMisCondLen >0) pstQry.pstMISCond_Data = channel.receiveByteArray(nMisCondLen).array()
    if ( nTxtSqlLen > 0) pstQry.pstTextSql_Data = channel.receiveByteArray(nTxtSqlLen).array()
    if ( nCommentLen > 0) pstQry.pszComment_Data = channel.receiveByteArray(nCommentLen).array()
    if ( nqryinfolen > 0) pstQry.pstInfo_Data = channel.receive[GAFIS_QUERYINFO]()
  }

  protected def GAFIS_NETSCR_SendQueryInfo(channel: ChannelOperator, pstQry: GAQUERYSTRUCT) {
    val ncandhead = pstQry.nCandHeadLen
    val ncand = pstQry.nCandLen
    val nqrycond = pstQry.nQryCondLen
    val nMicCount = pstQry.nMICCount;
    val nSvrListLen = pstQry.nSvrListLen;
    val nMisCondLen = pstQry.nMISCondLen;
    val nTxtSqlLen = pstQry.nTextSqlLen;
    val nCommentLen = pstQry.nCommentLen;
    // comment length.
    val nqryinfolen = pstQry.nQryInfoLen;

    if (ncand <= 0 && ncandhead <= 0 && nqrycond <= 0 && nMicCount <= 0 && nSvrListLen <= 0 &&
      nMisCondLen <= 0 && nTxtSqlLen <= 0 && nCommentLen <= 0 && nqryinfolen <= 0) {
      throw new IllegalArgumentException("data is null");
    }
    val response = channel.writeMessage[GNETANSWERHEADOBJECT](pstQry)
    validateResponse( channel,response)

    if (nMicCount > 0) {
      pstQry.pstMIC_Data.foreach(channel.writeMessage[NoneResponse](_))
      pstQry.pstMIC_Data.foreach(GAFIS_NETSCR_SendMICStruct(channel,_))
    }
    if (nSvrListLen > 0) channel.writeByteArray(pstQry.pstSvrList_Data, 0, nSvrListLen);

    if (ncandhead > 0) pstQry.pstCandHead_Data.foreach(channel.writeMessage[NoneResponse](_))
    if (ncand > 0) pstQry.pstCand_Data.foreach(channel.writeMessage[NoneResponse](_))

    if (nqrycond > 0) channel.writeByteArray[NoneResponse](pstQry.pstQryCond_Data, 0, nqrycond)
    if (nMisCondLen > 0) channel.writeByteArray[NoneResponse](pstQry.pstMISCond_Data, 0, nMisCondLen)
    if (nTxtSqlLen > 0) channel.writeByteArray[NoneResponse](pstQry.pstTextSql_Data, 0, nTxtSqlLen);
    if (nCommentLen > 0) channel.writeByteArray[NoneResponse](pstQry.pszComment_Data, 0, nCommentLen);
    if (nqryinfolen > 0) channel.writeMessage[NoneResponse](pstQry.pstInfo_Data)
  }
}

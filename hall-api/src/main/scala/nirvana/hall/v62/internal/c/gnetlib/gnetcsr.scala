package nirvana.hall.v62.internal.c.gnetlib

import java.nio.ByteBuffer

import nirvana.hall.c.services.ganumia.gadbrec
import nirvana.hall.c.services.ganumia.gadbrec._
import nirvana.hall.c.services.gbaselib.gbasedef.GAKEYSTRUCT
import nirvana.hall.c.services.gloclib.galoclog.GAFIS_VERIFYLOGSTRUCT
import nirvana.hall.c.services.gloclib.galoclp._
import nirvana.hall.c.services.gloclib.galoctp.{GAFIS_DUPCARDSTRUCT, GAFIS_TPADMININFO_EX, GPERSONINFOSTRUCT, GTPCARDINFOSTRUCT}
import nirvana.hall.c.services.gloclib.gaqryque.{GAFIS_QUERYINFO, GAQUERYCANDHEADSTRUCT, GAQUERYCANDSTRUCT, GAQUERYSTRUCT}
import nirvana.hall.c.services.gloclib.glocdef.{GAFISMICSTRUCT, GATEXTITEMSTRUCT}
import nirvana.hall.c.services.gloclib.glocndef.{GNETANSWERHEADOBJECT, GNETREQUESTHEADOBJECT}
import nirvana.hall.v62.internal.c.CodeHelper
import nirvana.hall.v62.internal.{AncientClientSupport, NoneResponse}
import nirvana.hall.v62.services.ChannelOperator
import org.jboss.netty.buffer.ChannelBuffers

import scala.collection.mutable

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-10
 */
trait gnetcsr {
  this: AncientClientSupport with reqansop =>
  protected def GAFIS_NETSCR_RecvMICStruct(channel:ChannelOperator,mic:GAFISMICSTRUCT):Unit={
    // we assume we know the exact size and the memory has been allocated
    if ( mic.nMntLen >0 ) mic.pstMnt_Data = channel.receiveByteArray(mic.nMntLen).array()
    if ( mic.nImgLen >0 ) mic.pstImg_Data = channel.receiveByteArray(mic.nImgLen).array()
    if ( mic.nCprLen >0 ) mic.pstCpr_Data = channel.receiveByteArray(mic.nCprLen).array()
    if ( mic.nBinLen >0 ) mic.pstBin_Data = channel.receiveByteArray(mic.nBinLen).array()
  }
  protected def GAFIS_NETSCR_SendMICStruct(channel:ChannelOperator,mic:GAFISMICSTRUCT): Unit ={
    if(mic.nMntLen > 0) channel.writeByteArray[NoneResponse](mic.pstMnt_Data)
    if(mic.nImgLen > 0) channel.writeByteArray[NoneResponse](mic.pstImg_Data)
    if(mic.nCprLen > 0) channel.writeByteArray[NoneResponse](mic.pstCpr_Data)
    if(mic.nBinLen > 0) channel.writeByteArray[NoneResponse](mic.pstBin_Data)
  }

  def GAFIS_NETSCR_RecvCaseInfo(channel:ChannelOperator, pAns:GNETANSWERHEADOBJECT):GCASEINFOSTRUCT={
    val pstCase = channel.receive[GCASEINFOSTRUCT]()
    val nfing = pstCase.nFingerCount;
    val npalm = pstCase.nPalmCount;
    val ntext = pstCase.nTextItemCount;
    val nextrainfolen = pstCase.nExtraInfoLen;
    val bfreecase = 1;	// case can be freed.
    if ( nfing<=0 && ntext<=0 && npalm<=0 && nextrainfolen<=0 ) {
      throw new IllegalArgumentException("data is null")
    }
    if ( nfing>0 ) {
      pstCase.bFingerIDCanBeFreed = 1;
    }
    if ( npalm>0 ) {
      pstCase.bPalmIDCanBeFreed = 1;
    }
    if ( ntext>0 ) {
      pstCase.bTextCanBeFreed = 1;
    }
    if ( nextrainfolen>0 ) {
      pstCase.bExtraInfoCanBeFreed = 1;
    }

    pAns.nReturnValue = 1

    var bExtraInfoFirst = 0
    if (UTIL_GNETLIB_IsNewClientVersion(pAns)){
      bExtraInfoFirst = 1;
    }else{
      pAns.bnData = "$version=001$".getBytes
    }

    channel.writeMessage[NoneResponse](pAns)
    if ( nfing>0 ) {
      pstCase.pstFingerID_Data = 0 until nfing map(i=>channel.receive[GAKEYSTRUCT]())  toArray
    }
    if ( npalm>0 ) {
      pstCase.pstPalmID_Data = 0 until npalm map(i=>channel.receive[GAKEYSTRUCT]()) toArray
    }
    if ( ntext>0 ) {
      pstCase.pstText_Data = 0 until ntext map(i=>channel.receive[GATEXTITEMSTRUCT]()) toArray
    }

    if ( bExtraInfoFirst >0 && ( nextrainfolen > 0 ) ) {
      pstCase.pstExtraInfo_Data =  GAFIS_CASE_EXTRAINFO_Recv(channel,pAns)
      pstCase.pstExtraInfo_Data.cbSize = nextrainfolen
    }

    if(pstCase.pstText_Data.exists(x=>x.bIsPointer == 1 && x.nItemLen >0)){

      pAns.nReturnValue = 1
      channel.writeMessage[NoneResponse](pAns)

      pstCase.pstText_Data.filter(_.bIsPointer == 1).foreach{x=>
        x.stData.textContent =  channel.receiveByteArray(x.nItemLen).array()
      }
    }

    if ( bExtraInfoFirst < 0 && ( nextrainfolen > 0 ) ) {
      pstCase.pstExtraInfo_Data.cbSize = nextrainfolen
      pstCase.pstExtraInfo_Data =  GAFIS_CASE_EXTRAINFO_Recv(channel,pAns)
    }

    pstCase
  }

  def GAFIS_NETSCR_SendCaseInfo(channel: ChannelOperator, pstCase:GCASEINFOSTRUCT) {

    // send the case structure to peer
    if ( pstCase.pstFingerID_Data == null) {
      pstCase.nFingerCount = 0
    }
    val nfing = pstCase.nFingerCount
    val npalm = pstCase.nPalmCount
    val ntext = pstCase.nTextItemCount
    var nextrainfolen = pstCase.nExtraInfoLen
    // no other info to be sent, quit
    if ( nfing<=0 && npalm<=0 && ntext<=0 && nextrainfolen<=0 ) {
      throw new IllegalArgumentException("data is null")
    }
    // have other info to be sent, wait peer to alloc memory and return values
    var pAns = channel.writeMessage[GNETANSWERHEADOBJECT](pstCase)
    validateResponse(channel,pAns)

    val bnDataString = new String(pAns.bnData)
    var bExtraInfoFirst = 0
    if (bnDataString.indexOf("$version=002$")==0 ) {
      bExtraInfoFirst = 1
    }

    else if ( bnDataString.indexOf("$version=001$")==0 ) {
      // need send extra info.
      bExtraInfoFirst = 0;
    } else {
      // client does not know extra info, so clear it.
      nextrainfolen = 0;
    }
    if ( nfing>0 && pstCase.pstFingerID_Data != null ) {
      // send finger list
      pstCase.pstFingerID_Data.foreach(channel.writeMessage[NoneResponse](_))
    }
    if ( npalm>0 && pstCase.pstPalmID_Data != null) {
      // send palm id list
      pstCase.pstPalmID_Data.foreach(channel.writeMessage[NoneResponse](_))
    }
    if ( ntext>0 && pstCase.pstText_Data != null ) {
      // send text id list
      pstCase.pstText_Data.foreach(channel.writeMessage[NoneResponse](_))
    }

    if ( bExtraInfoFirst>0 && ( nextrainfolen > 0 ) ) {
      GAFIS_CASE_EXTRAINFO_Send(channel, pstCase.pstExtraInfo_Data)
    }

    // text need to be transfered
    pAns = channel.receive[GNETANSWERHEADOBJECT]()
    validateResponse(channel,pAns)
    pstCase.pstText_Data.filter(_.bIsPointer == 1).foreach(x=>channel.writeByteArray[NoneResponse](x.stData.textContent))

    if ( bExtraInfoFirst < 0 && ( nextrainfolen>0 ) ) {
      channel.writeMessage[NoneResponse](pstCase.pstExtraInfo_Data)
    }
  }
  private def GAFIS_CASE_EXTRAINFO_Send(channel:ChannelOperator,
    pstInfo:GAFIS_CASE_EXTRAINFO) {
    channel.writeMessage[NoneResponse](pstInfo)
    if(pstInfo.nItemSize > 0){
      val pAns = channel.receive[GNETANSWERHEADOBJECT]()
      validateResponse(channel,pAns)
      channel.writeMessage[NoneResponse](pstInfo.pstItemEntry_Data)
    }
  }
  def GAFIS_CASE_EXTRAINFO_Recv(channel:ChannelOperator,pAns:GNETANSWERHEADOBJECT):GAFIS_CASE_EXTRAINFO= {
    val pstInfo = channel.receive[GAFIS_CASE_EXTRAINFO]()
    pAns.nReturnValue = 1
//    channel.writeMessage[NoneResponse](pAns)
//    pstInfo.pstItemEntry_Data = channel.receive[GAFIS_CASEITEMENTRY]()
    pstInfo
  }




  protected def GAFIS_NETSCR_RecvLPCardInfo(channel:ChannelOperator,pAns:GNETANSWERHEADOBJECT,pstCard:GLPCARDINFOSTRUCT) {
    val card = channel.receive[GLPCARDINFOSTRUCT]()
    pstCard.szCardID = card.szCardID
    pstCard.bMicCanBeFreed = 0;
    pstCard.bTextCanBeFreed = 0;
    pstCard.nMicItemCount = card.nMicItemCount
    pstCard.nTextItemCount = card.nTextItemCount
    pstCard.nExtraInfoLen = card.nExtraInfoLen
    pstCard.stAdmData = card.stAdmData//操作信息


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
    pstCard.szCardID = card.szCardID
    pstCard.bMicCanBeFreed = 0
    pstCard.bTextCanBeFreed = 0
    pstCard.nMicItemCount = card.nMicItemCount
    pstCard.nTextItemCount = card.nTextItemCount
    pstCard.nInfoExLen = card.nInfoExLen
    pstCard.stAdmData= card.stAdmData //操作信息

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

    //判断是否有图像数据
    if(pstCard.nMicItemCount > 0)
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
    //validateResponse(channel,response)

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
  def GAFIS_NETSCR_RecvQueryInfo(channel:ChannelOperator,response:GNETANSWERHEADOBJECT):GAQUERYSTRUCT= {
    val pstQry= channel.receive[GAQUERYSTRUCT]()

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
    if( ncandhead> 0 )  { //receive candidate head struct
      pstQry.pstCandHead_Data =  channel.receive[GAQUERYCANDHEADSTRUCT]()
    }
    if( ncand> 0 )  {//receive candidate data
      val num = pstQry.pstCandHead_Data.nCandidateNum & 0x0000ffff
      pstQry.pstCand_Data = Range(0,num).map(i=>channel.receive[GAQUERYCANDSTRUCT]()).toArray
    }

    if ( nqrycond > 0) pstQry.pstQryCond_Data = channel.receiveByteArray(nqrycond).array()
    if ( nMisCondLen >0) pstQry.pstMISCond_Data = channel.receiveByteArray(nMisCondLen).array()
    if ( nTxtSqlLen > 0) pstQry.pstTextSql_Data = channel.receiveByteArray(nTxtSqlLen).array()
    if ( nCommentLen > 0) pstQry.pszComment_Data = channel.receiveByteArray(nCommentLen).array()
    if ( nqryinfolen > 0) pstQry.pstInfo_Data = channel.receive[GAFIS_QUERYINFO]()

    pstQry
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

    channel.writeMessage(pstQry)

    if (ncand <= 0 && ncandhead <= 0 && nqrycond <= 0 && nMicCount <= 0 && nSvrListLen <= 0 &&
      nMisCondLen <= 0 && nTxtSqlLen <= 0 && nCommentLen <= 0 && nqryinfolen <= 0) {
      return
      //throw new IllegalArgumentException("data is null");
    }
    val response = channel.receive[GNETANSWERHEADOBJECT]()
    validateResponse( channel,response)

    if (nMicCount > 0) {
      pstQry.pstMIC_Data.foreach(channel.writeMessage[NoneResponse](_))
      pstQry.pstMIC_Data.foreach(GAFIS_NETSCR_SendMICStruct(channel,_))
    }
    if (nSvrListLen > 0) channel.writeByteArray(pstQry.pstSvrList_Data, 0, nSvrListLen);

    if (ncandhead > 0) channel.writeMessage[NoneResponse](pstQry.pstCandHead_Data)
    if (ncand > 0) pstQry.pstCand_Data.foreach(channel.writeMessage[NoneResponse](_))

    if (nqrycond > 0) channel.writeByteArray[NoneResponse](pstQry.pstQryCond_Data, 0, nqrycond)
    if (nMisCondLen > 0) channel.writeByteArray[NoneResponse](pstQry.pstMISCond_Data, 0, nMisCondLen)
    if (nTxtSqlLen > 0) channel.writeByteArray[NoneResponse](pstQry.pstTextSql_Data, 0, nTxtSqlLen);
    if (nCommentLen > 0) channel.writeByteArray[NoneResponse](pstQry.pszComment_Data, 0, nCommentLen);
    if (nqryinfolen > 0) channel.writeMessage[NoneResponse](pstQry.pstInfo_Data)
  }
  def GAFIS_NETSCR_UTIL_SendAllocData(channel:ChannelOperator,pData:Array[Byte]){
    val response = channel.receive[GNETANSWERHEADOBJECT]()
    validateResponse(channel,response)
    channel.writeByteArray(pData)
  }
  def GAFIS_NETSCR_SendSelItemToSelect(channel:ChannelOperator,pstRes:GADB_SELRESULT) {
    channel.writeMessage(pstRes)
    if(pstRes.nResItemCount > 0 ){
      val response = channel.receive[GNETANSWERHEADOBJECT]()
      validateResponse(channel,response)
      if(pstRes.pstItem_Data == null) {
        throw new IllegalArgumentException("nResItemCount(%s) greater than 0,but  pstItem_Data is null".format(pstRes.nResItemCount))
      }
      pstRes.pstItem_Data.foreach(channel.writeMessage[NoneResponse](_))
    }
  }
  def GAFIS_NETSCR_RecvSelResult(channel:ChannelOperator,pstRes:GADB_SELRESULT){
    channel.receive[GADB_SELRESULT](pstRes)
    pstRes.nFreeOption = 0

    var nRowCnt = pstRes.nRecGot
    var nRowSize = pstRes.nSegSize
    var nDataBufLen = 0

    if ( (pstRes.nFlag & gadbrec.SELRES_FLAG_FLATMEMORY) > 0) {
      // flat memory
      nDataBufLen = pstRes.nNextAvailBlobPos
    } else {
      nDataBufLen = nRowCnt*nRowSize
    }
    val nResItemLen = pstRes.nResItemCount
    val nReadFlagLen = pstRes.nReadFlagRowSize * nRowCnt
    var bNeedSend = 0


    var response = new GNETANSWERHEADOBJECT
    response.nReturnValue = 1
    channel.writeMessage(response)


    if ( (pstRes.nItemFlag & SELRES_ITEM_RESITEM) > 0 && nResItemLen > 0 ) {
      val itemData = 0 until nResItemLen map(x=>channel.receive[GADB_SELRESITEM]())
      pstRes.pstItem_Data = itemData.toArray
    }
    if ( (pstRes.nItemFlag & SELRES_ITEM_READFLAG) > 0 && nReadFlagLen > 0 ) {
      pstRes.pReadFlag_Data = channel.receiveByteArray(nReadFlagLen).array()
    }
    if ( (pstRes.nItemFlag & SELRES_ITEM_DATABUF) > 0 && nDataBufLen>0 ) {
      pstRes.pDataBuf_Data = channel.receiveByteArray(nDataBufLen).array()

      if ( (pstRes.nFlag & SELRES_FLAG_BLOBHASDATA) >0 &&
        !((pstRes.nFlag & SELRES_FLAG_FLATMEMORY)>0) && nDataBufLen>0 ) {
        nRowCnt = pstRes.nRecGot
        nRowSize = pstRes.nSegSize
        var nBlobSize = 0
        val buf = mutable.Buffer[GADB_MEMBLOB]()
        do {
          response = channel.receive[GNETANSWERHEADOBJECT]()
          val i = CodeHelper.convertAsInt(response.bnData);	// column
          val k = CodeHelper.convertAsInt(response.bnData,4);	// row
          nBlobSize = CodeHelper.convertAsInt(response.bnData,8);
          if ( nBlobSize > 0 ) {
            bNeedSend = 1
            val n = pstRes.pstItem_Data(i).nDataOffset
            val pstBlob= new  GADB_MEMBLOB
            val buffer = ChannelBuffers.wrappedBuffer(pstRes.pDataBuf_Data,k*nRowSize + n,pstBlob.getDataSize)
            pstBlob.fromStreamReader(buffer)

            //val pstBlob = (GADB_MEMBLOB *)(pstRes -> pDataBuf + k * nRowSize + n);
            //ZMALLOC_GOTOFIN(pstBlob -> u.pData, UCHAR *, nBlobSize);
            bNeedSend = 0
            response.nReturnValue = 1
            channel.writeMessage(response)

            pstBlob.pData_Data = channel.receiveByteArray(nBlobSize).array()

            buf += pstBlob
          }
        } while(nBlobSize>0 )

        //set result
        pstRes.pDataBuf_Result = buf.toArray
      }
    }

    if(bNeedSend > 0) {
      response.nReturnValue = -1
      channel.writeMessage(response)
    }

  }

  def GAFIS_NETSCR_RecvPersonInfo(pstCon:ChannelOperator,pReq:GNETREQUESTHEADOBJECT ,
    pAns:GNETANSWERHEADOBJECT , pstPerson:GPERSONINFOSTRUCT)
  {
    NETOP_RECVDATA(pstCon, pstPerson)


    val n = pstPerson.nTextItemCount
    val nc = pstPerson.nCardCount
    // alloc memory
    if ( nc>0 ) {
      pstPerson.pstID_Data = Range(0,nc).map(x=>new GAFIS_DUPCARDSTRUCT()).toArray
    }
    if ( n>0 ) {
      pstPerson.pstText_Data = Range(0,n).map(x=> new GATEXTITEMSTRUCT).toArray
    }
    NETANS_SetRetVal(pAns, 1);	// we allocate first step memory
    NETOP_SENDANS(pstCon, pAns);
    // now we will receive all the structure's
    if ( nc>0 ) {
      NETOP_RECVDATA(pstCon, pstPerson.pstID_Data)
    }
    if ( n>0 ) {
      NETOP_RECVDATA(pstCon, pstPerson.pstText_Data)
    }
    NETANS_SetRetVal(pAns, 1);
    NETOP_SENDANS(pstCon, pAns);
    // now all memories have been allocated, we'll receive the data
    if ( n>0 ) {
      pstPerson.pstText_Data.foreach{text=>
        if(text.bIsPointer == 1){
          text.stData.textContent =NETOP_RECVDATA(pstCon,text.nItemLen).array()
        }
      }
    }
  }

  protected def GAFIS_NETSCR_SendSelResult(pstCon:ChannelOperator,pReq:GNETREQUESTHEADOBJECT, pAns:GNETANSWERHEADOBJECT, pstRes:GADB_SELRESULT): Unit ={
    GAFIS_NETSCR_SendSelResultEx(pstCon, pReq, pAns, pstRes, true)
  }
  protected def GAFIS_NETSCR_SendSelResultEx(pstCon:ChannelOperator,pReq:GNETREQUESTHEADOBJECT, pAns:GNETANSWERHEADOBJECT, pstRes:GADB_SELRESULT, bSendRes:Boolean): Unit ={
    if(bSendRes){
      // we must assure that the size is equal
      NETOP_SENDDATA(pstCon, pstRes)
    }
    // wait for other side to alloc memory
    NETOP_RECVANS(pstCon, pAns)
    if(NETANS_GetRetVal(pAns) < 0){
      return
    }
    if((pstRes.nItemFlag & SELRES_ITEM_RESITEM) > 0){
      NETOP_SENDDATA(pstCon, pstRes.pstItem_Data)
    }
    if ((pstRes.nItemFlag & SELRES_ITEM_READFLAG) > 0 ) {
      NETOP_SENDDATA(pstCon, pstRes.pReadFlag_Data)
    }
    if ((pstRes.nItemFlag & SELRES_ITEM_DATABUF) > 0) {
      // send data
      var n = 0:Int
      if ( (pstRes.nFlag & gadbrec.SELRES_FLAG_FLATMEMORY) > 0) {
        n = pstRes.nNextAvailBlobPos
      } else {
        n = pstRes.nSegSize * pstRes.nRecGot
      }
      NETOP_SENDDATA(pstCon, pstRes.pDataBuf_Data)
      if (((pstRes.nFlag & SELRES_FLAG_BLOBHASDATA) > 0) &&
        ((pstRes.nFlag & SELRES_FLAG_FLATMEMORY) < 0) && n > 0) {
        val nItemCnt = pstRes.nResItemCount
        val nRowCnt = pstRes.nRecGot
//        val nRowSice = pstRes.nSegSize
        for (i <- (0 until nItemCnt)){
          val pstItem = pstRes.pstItem_Data(i)
          if(pstItem.nFormFlag == SELRESITEM_FFLAG_ISMEMBLOB){
            val buffer = ChannelBuffers.wrappedBuffer(pstRes.pDataBuf_Data)
//            val nOffset = pstItem.nDataOffset
            for(k <- (0 until nRowCnt)){
//              if ( GADB_MEMFORMROW_IsColNULL(pstRes. +k*nRowSize, i) ) continue;	// column is null
//              new GADB_MEMBLOB().fromByteArray()
                val pstBlob = new GADB_MEMBLOB().fromStreamReader(buffer)
                val nBlobSize = pstBlob.stLobInfo.nBlobSize
                if(nBlobSize > 0 && pstBlob.pData_Data != null){
                  val buffer = ByteBuffer.allocate(12)
                  buffer.putInt(i)
                  buffer.putInt(k)
                  buffer.putInt(nBlobSize)
                  pAns.bnData = buffer.array()
                  NETOP_SENDANS(pstCon, pAns)
                  NETOP_RECVANS(pstCon, pAns)
                  if ( NETANS_GetRetVal(pAns)<0 ) {
                    return
                  }
                  NETOP_SENDDATA(pstCon, pstBlob.pData_Data)
                }
            }
            NETOP_SENDANS(pstCon, pAns)
          }
        }
      }
    }
  }
  protected def GAFIS_NETSCR_SendVerifyLog(pstCon:ChannelOperator, pstv: GAFIS_VERIFYLOGSTRUCT): Unit ={
//    val n = 16 //galoclog.GAFIS_VERIFYLOG_PtCnt pstLog.bDataCanBeFree.length
//    (0 until n).foreach { i =>
//    }
    //设置其他数据长度为0，不发送其他数据
    pstv.nDataLen = new Array[Byte](64)
    NETOP_SENDDATA(pstCon, pstv)
    //这里一次发送全部pbnData_Data数据
//    if(pstv.pbnData_Data != null && pstv.pbnData_Data.length > 0){
//      NETOP_SENDDATA(pstCon, pstv.pbnData_Data)
//    }
  }
  protected def GAFIS_NETSCR_RecvVerifyLog(pstCon:ChannelOperator, pstv: GAFIS_VERIFYLOGSTRUCT): Unit= {
    NETOP_RECVDATA(pstCon, pstv)
    val dataLenBuffer = ChannelBuffers.wrappedBuffer(pstv.nDataLen)
    val dataBuf = ChannelBuffers.dynamicBuffer()
//    ByteArrayOutputStream
    val n = 16 //n = GAFIS_VERIFYLOG_PtCnt();
    (0 until n).foreach{i =>
      val len = dataLenBuffer.readInt()
      if(len > 0){
        dataBuf.writeBytes(pstCon.receiveByteArray(len))
      }
    }
    pstv.pbnData_Data = dataBuf.array()
  }
/*  protected def GAFIS_NETSCR_SendVerifyLog(channel:ChannelOperator,pstVerifyLog:GAFIS_VERIFYLOGSTRUCT){
    val response = channel.writeMessage[GNETANSWERHEADOBJECT](pstVerifyLog)
    validateResponse(channel,response)

  }*/

  protected def GAFIS_NETSCR_RecvLPGroup(pstCon: ChannelOperator, pAns: GNETANSWERHEADOBJECT, pRec: GAFIS_LPGROUPSTRUCT): Unit ={
    NETOP_RECVDATA(pstCon, pRec)
    NETANS_SetRetVal(pAns, 1)
    NETOP_SENDANS(pstCon, pAns)
    val n = pRec.nKeyListLength / new GAFIS_LPGROUPENTRY().toByteArray().length
    if(n > 0){
      pRec.pstKeyList_Data = Range(0, n).map(x=>new GAFIS_LPGROUPENTRY).toArray
      NETOP_RECVDATA(pstCon, pRec.pstKeyList_Data)
    }
  }

  protected def GAFIS_NETSCR_SendLPGroup(pstCon: ChannelOperator, pAns: GNETANSWERHEADOBJECT, pRec: GAFIS_LPGROUPSTRUCT): Unit ={
    pRec.nKeyListLength = pRec.pstKeyList_Data.length * new GAFIS_LPGROUPENTRY().toByteArray().length
    NETOP_SENDDATA(pstCon, pRec)
    NETOP_RECVANS(pstCon, pAns)
    validateResponse(pstCon, pAns)
    NETOP_SENDDATA(pstCon, pRec.pstKeyList_Data)
  }

  /**
    * 是否新版本验证, 这是使用pAns验证，免去了UTIL_GNETLIB_SetNewClientVersionFlag
    * @param pAns
    * @return
    */
  def UTIL_GNETLIB_IsNewClientVersion(pAns: GNETANSWERHEADOBJECT): Boolean ={
    "$version=002$".equals(new String(pAns.bnData).trim)
  }
}

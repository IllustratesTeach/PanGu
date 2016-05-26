package nirvana.hall.v62.internal.c.gloclib

import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gbaselib.gbasedef
import nirvana.hall.c.services.gbaselib.gbasedef.{GAKEYSTRUCT,GBASE_UTIL_ALIGN}
import nirvana.hall.c.services.gbaselib.gitempkg.{GBASE_ITEMPKG_ITEMSTRUCT, GBASE_ITEMPKG_OPSTRUCT}
import nirvana.hall.c.services.gloclib.galoclp.{GAFIS_CASE_EXTRAINFO, GCASEINFOSTRUCT, GAFIS_LP_EXTRAINFO, GLPCARDINFOSTRUCT}
import nirvana.hall.c.services.gloclib.gaqryque.{GAFIS_QUERYINFO, GAQUERYCANDSTRUCT, GAQUERYCANDHEADSTRUCT, GAQUERYSTRUCT}
import nirvana.hall.c.services.gloclib.{galocpkg, glocdef, galoclp, galoctp}
import nirvana.hall.c.services.gloclib.galoctp.{GAFIS_TPADMININFO_EX, GTPCARDINFOSTRUCT}
import nirvana.hall.c.services.gloclib.glocdef.{GATEXTITEMSTRUCT, GAFISMICSTRUCT}
import nirvana.hall.v62.internal.c.gbaselib.gitempkg
import org.jboss.netty.buffer.{ChannelBuffers, ChannelBuffer}

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-18
  */
trait galocpkg {
  this :gitempkg with galoctp =>
  //package type string
  //val pszDefault	= "Default";
  val pszTpCard	= "TpCard";
  val pszLpCard	= "LpCard";
  val pszCase		= "Case";
  val pszQuery	= "Query";
  //val pszFptFile	= "FptFile";

  //package item type
  val PKG_ITEMTYPE_THIS	=	0x0001
  val PKG_ITEMTYPE_MIC	=	0x0002
  val PKG_ITEMTYPE_TEXT	=	0x0003
  val PKG_ITEMTYPE_FINGERID = 	0x0004
  val PKG_ITEMTYPE_PALMID	 =	0x0005
  val PKG_ITEMTYPE_EXTRAINFO	 =0x0006
  val PKG_ITEMTYPE_OTHER	= 0x00FF

  //package item type string
  val pszThis		= "This";
  val pszMic		= "Mic";
  val pszText		= "Text";
  val pszFingerID	= "FingerID";
  val pszPalmID	= "PalmID";
  val pszExtraInfo= "ExtraInfo";
  //val pszOther	= "Other";



  def GAFIS_PKG_Query2Pkg(pstQuery:GAQUERYSTRUCT,pstPkg:GBASE_ITEMPKG_OPSTRUCT)
  {

    val bytes = GAFIS_QUERY_Struct2Stream(pstQuery);

    val pstItem = new GBASE_ITEMPKG_ITEMSTRUCT
    pstItem.stHead.nItemLen = bytes.size
    pstItem.stHead.nItemType = PKG_ITEMTYPE_THIS
    pstItem.stHead.szItemName = pszThis
    pstItem.bnRes = bytes

    pstPkg.addItem(pstItem)

    pstPkg.head.nPkgType = galocpkg.PKG_TYPE_QUERY
    pstPkg.head.szPkgTypeStr = pszQuery

  }


  def GAFIS_PKG_GetTpCard(pstPkg:GBASE_ITEMPKG_OPSTRUCT):List[GTPCARDINFOSTRUCT]= //,GTPCARDINFOSTRUCT* pstCard)
  {


    val headers = GBASE_ITEMPKG_GetItemDir(pstPkg)
    if(headers.size < 1 ){
      throw new IllegalStateException("item dir is empty")
    }

    headers.flatMap{header=>
      val pstCard = new GTPCARDINFOSTRUCT
      var pstCardOpt:Option[GTPCARDINFOSTRUCT] = Some(pstCard)
      val pstItem = GBASE_ITEMPKG_GetItem(pstPkg,header.szItemName).getOrElse(throw new IllegalStateException("item not found"))
      val buffer = ChannelBuffers.wrappedBuffer(pstItem.bnRes)
//      println("bnResLength "+ pstItem.bnRes.length +" dataLength " + pstItem.stHead.nItemLen)
      header.nItemType match {
        case PKG_ITEMTYPE_THIS =>
          pstCard.fromByteArray(pstItem.bnRes)
        case PKG_ITEMTYPE_MIC =>
          val pstMics = GAFIS_MIC_GetMicArrayFromStream(buffer)
          pstCard.pstMIC_Data = pstMics.toArray
          pstCard.nMicItemCount = pstMics.length.toByte

          GAFIS_PKG_UnZipMicArray(pstCard.pstMIC_Data)


        case PKG_ITEMTYPE_TEXT =>
          val pstTextItems = GAFIS_TEXT_GetTextArrayFromStream(buffer)
          pstCard.pstText_Data = pstTextItems
          pstCard.nTextItemCount = pstCard.pstText_Data.length.toShort
        //  [12/8/2006]
        case PKG_ITEMTYPE_EXTRAINFO =>
          val nCount = pstItem.stHead.nItemLen
          if (nCount > 0) {
            pstCard.bInfoExCanBeFreed = 1;

            pstCard.pstInfoEx_Data = new GAFIS_TPADMININFO_EX().fromStreamReader(buffer)
            pstCard.nInfoExLen = nCount.toShort

            //set itemflag for add or update  [12/18/2006]
            pstCard.nItemFlag = (pstCard.nItemFlag | galoctp.TPCARDINFO_ITEMFLAG_INFOEX).toByte
            if (pstCard.pstInfoEx_Data.nItemFlag == 0) pstCard.pstInfoEx_Data.nItemFlag = 0xFF.toByte
            if (pstCard.pstInfoEx_Data.stFpx.nItemFlag == 0) pstCard.pstInfoEx_Data.stFpx.nItemFlag = 0xFF.toByte
          }
        case other=>
          pstCardOpt = None
      }
      pstCardOpt
    }.toList

    //TODO 实现对pstCard的校验
//    GAFIS_PKG_UTIL_CheckTPCard(pstCard);

  }

  def GAFIS_PKG_GetLpCard(pstPkg:GBASE_ITEMPKG_OPSTRUCT):List[GLPCARDINFOSTRUCT]=
  {

    val headers = GBASE_ITEMPKG_GetItemDir(pstPkg)


    headers.flatMap {header=>
      val pstItem = GBASE_ITEMPKG_GetItem(pstPkg,header.szItemName).getOrElse(throw new IllegalStateException("item not found"))
      val buffer = ChannelBuffers.wrappedBuffer(pstItem.bnRes)

      val pstCard = new GLPCARDINFOSTRUCT()
      var pstCardOpt:Option[GLPCARDINFOSTRUCT] = Some(pstCard)
      header.nItemType match{
        case PKG_ITEMTYPE_THIS=>
        //			pstLpCard = (GLPCARDINFOSTRUCT*)pstItem.bnRes;
        //			memcpy(pstCard.szCardID,pstLpCard.szCardID,sizeof(pstCard.szCardID));
        //			memcpy(&pstCard.stAdmData,&pstLpCard.stAdmData,sizeof(pstCard.stAdmData));
        //			pstCard.nItemFlag |= pstLpCard.nItemFlag;

          pstCard.fromStreamReader(buffer)

        case PKG_ITEMTYPE_MIC =>
        val pstMic = GAFIS_MIC_GetMicArrayFromStream(buffer)
        pstCard.pstMIC_Data = pstMic
        pstCard.nMicItemCount	= pstMic.length.toByte
        pstCard.bMicCanBeFreed	= 1

        GAFIS_PKG_UnZipMicArray(pstCard.pstMIC_Data)

        case PKG_ITEMTYPE_TEXT =>
          val pstTextItems = GAFIS_TEXT_GetTextArrayFromStream(buffer)
          pstCard.pstText_Data = pstTextItems
          pstCard.nTextItemCount = pstCard.pstText_Data.length.toShort
        //  [12/8/2006]
        case PKG_ITEMTYPE_EXTRAINFO =>
          val nCount = pstItem.stHead.nItemLen
          if (nCount > 0) {

            pstCard.pstExtraInfo_Data = new GAFIS_LP_EXTRAINFO().fromStreamReader(buffer)
            pstCard.nExtraInfoLen = nCount.toShort

            //set itemflag for add or update  [12/18/2006]
            pstCard.nItemFlag = (pstCard.nItemFlag | galoclp.LPCARDINFO_ITEMFLAG_EXTRAINFO).toByte
            if(pstCard.pstExtraInfo_Data.nItemFlag == 0)	pstCard.pstExtraInfo_Data.nItemFlag = 0xFF.toByte
            if(pstCard.pstExtraInfo_Data.stFpx.nItemFlag == 0)	pstCard.pstExtraInfo_Data.stFpx.nItemFlag = 0xFF.toByte
          }
        case other=>
          pstCardOpt = None
      }

      pstCardOpt
    }.toList
  }

  def GAFIS_PKG_GetCase(pstPkg:GBASE_ITEMPKG_OPSTRUCT):List[GCASEINFOSTRUCT]=
  {
    val headers = GBASE_ITEMPKG_GetItemDir(pstPkg)

    var nCount = 0

    headers.flatMap {pstItemHead=>
      val pstItem = GBASE_ITEMPKG_GetItem(pstPkg,pstItemHead.szItemName).getOrElse(throw new IllegalStateException("item not found"))
      val buffer = ChannelBuffers.wrappedBuffer(pstItem.bnRes)
      val pstCase = new GCASEINFOSTRUCT
      var pstCaseOpt:Option[GCASEINFOSTRUCT] = Some(pstCase)
      nCount = pstItem.stHead.nItemLen
      pstItemHead.nItemType match{
        case PKG_ITEMTYPE_THIS=>
        //			pstCaseInfo = (GCASEINFOSTRUCT*)pstItem.bnRes;
        //			memcpy(pstCase.nGroupID,pstCaseInfo.nGroupID,sizeof(pstCase.nGroupID));
        //			memcpy(pstCase.szCaseID,pstCaseInfo.szCaseID,sizeof(pstCase.szCaseID));
        //			memcpy(pstCase.nSID,pstCaseInfo.nSID,sizeof(pstCase.nSID));
        //			memcpy(pstCase.bnUUID,pstCaseInfo.bnUUID,sizeof(pstCase.bnUUID));
        //			memcpy(pstCase.szMISCaseID,pstCaseInfo.szMISCaseID,sizeof(pstCase.szMISCaseID));
        //			pstCase.nItemFlag = pstCaseInfo.nItemFlag;
        //			pstCase.nItemFlagEx = pstCaseInfo.nItemFlagEx;

          pstCase.fromStreamReader(buffer)

        case PKG_ITEMTYPE_FINGERID=>
          pstCase.pstFingerID_Data = Range(0,nCount).map(x=>new GAKEYSTRUCT().fromStreamReader(buffer)).toArray
          pstCase.nFingerCount = pstCase.pstFingerID_Data.length.toShort
          pstCase.nFingerIDLen = nCount.toShort

          pstCase.nItemFlag	 = (pstCase.nItemFlag | galoclp.GCIS_ITEMFLAG_FINGERCOUNT| galoclp.GCIS_ITEMFLAG_FINGERID).toByte
        case PKG_ITEMTYPE_PALMID=>
          pstCase.pstPalmID_Data = Range(0,nCount).map(x=>new GAKEYSTRUCT().fromStreamReader(buffer)).toArray
          pstCase.nPalmCount = pstCase.pstPalmID_Data.length.toShort
          pstCase.nPalmIDLen = nCount.toShort
          pstCase.nItemFlag	 = (pstCase.nItemFlag | galoclp.GCIS_ITEMFLAG_FINGERCOUNT|galoclp.GCIS_ITEMFLAG_FINGERID).toByte

        case PKG_ITEMTYPE_TEXT=>
          val pstTextItems = GAFIS_TEXT_GetTextArrayFromStream(buffer)
          pstCase.pstText_Data = pstTextItems
          pstCase.nTextItemCount = pstCase.pstText_Data.length.toShort
          pstCase.nItemFlag = (pstCase.nItemFlag | galoclp.GCIS_ITEMFLAG_TEXT).toByte
        //  [12/8/2006]
        case PKG_ITEMTYPE_EXTRAINFO=>
          if (nCount > 0) {

            pstCase.pstExtraInfo_Data = new GAFIS_CASE_EXTRAINFO().fromStreamReader(buffer)
            pstCase.nExtraInfoLen = nCount.toShort

            //set itemflag for add or update  [12/18/2006]
            pstCase.nItemFlagEx = (pstCase.nItemFlagEx | galoclp.GCIS_ITEMFLAGEX_EXTRAINFO).toByte
            if(pstCase.pstExtraInfo_Data.nItemFlag == 0)	pstCase.pstExtraInfo_Data.nItemFlag = 0xFF.toByte
            if(pstCase.pstExtraInfo_Data.stFpx.nItemFlag == 0)	pstCase.pstExtraInfo_Data.stFpx.nItemFlag = 0xFF.toByte
          }
        case other=>
          pstCaseOpt = None
      }

      pstCaseOpt
    }.toList

  }

  def GAFIS_PKG_GetQuery(pstPkg:GBASE_ITEMPKG_OPSTRUCT):Array[GAQUERYSTRUCT]=
  {

    val pstItemHeads = GBASE_ITEMPKG_GetItemDir(pstPkg)
    pstItemHeads.flatMap{pstItemHead=>

      val pstItem = GBASE_ITEMPKG_GetItem(pstPkg,pstItemHead.szItemName).getOrElse(throw new IllegalStateException("item not found"))
      val buffer = ChannelBuffers.wrappedBuffer(pstItem.bnRes)
      pstItemHead.nItemType match {
        case PKG_ITEMTYPE_THIS=>
         Some(GAFIS_QUERY_Stream2Struct(buffer))
        case other=>
//          println(pstItemHead.szItemName+" type "+other+" not found ")
          None
      }
    }
  }

  /*
  def GAFIS_PKG_GetPkgType(pstPkg:GBASE_ITEMPKG_OPSTRUCT)
  {
    val pstHead = pstPkg.head
    val nPkgType = pstHead.nPkgType
    nPkgType match
    {
      case PKG_TYPE_TPCARD=>

      ZMALLOC_GOTOFIN(pstTpCard,GTPCARDINFOSTRUCT*,sizeof(*pstTpCard));
      if( GAFIS_PKG_GetTpCard(pstPkg,pstTpCard) < 0)	ERRFAILFINISHEXIT();
      *ppData = pstTpCard;
      retval = nPkgType;
      break;
      case PKG_TYPE_LPCARD=>
      ZMALLOC_GOTOFIN(pstLpCard,GLPCARDINFOSTRUCT*,sizeof(*pstLpCard));
      if( GAFIS_PKG_GetLpCard(pstPkg,pstLpCard) < 0)	ERRFAILFINISHEXIT();
      *ppData = pstLpCard;
      retval = nPkgType;
      break;
      case PKG_TYPE_CASE=>
      ZMALLOC_GOTOFIN(pstCase,GCASEINFOSTRUCT*,sizeof(*pstCase));
      if( GAFIS_PKG_GetCase(pstPkg,pstCase) < 0)	ERRFAILFINISHEXIT();
      *ppData = pstCase;
      retval = nPkgType;
      break;
      case PKG_TYPE_QUERY=>
      ZMALLOC_GOTOFIN(pstQuery,GAQUERYSTRUCT*,sizeof(*pstQuery));
      if( GAFIS_PKG_GetQuery(pstPkg,pstQuery) < 0)	ERRFAILFINISHEXIT();
      *ppData = pstQuery;
      retval = nPkgType;
      break;

      case PKG_TYPE_FPTFILE:
      default:
      *ppData = null;
      retval = 0;	break;
    }

    Finish_Exit:
    if(retval < 0)
    {
      if(pstTpCard)
      {
        GAFIS_TPCARD_Free(pstTpCard);
        free(pstTpCard);
      }
      if(pstLpCard)
      {
        GAFIS_LPCARD_Free(pstLpCard);
        free(pstLpCard);
      }
      if(pstCase)
      {
        GAFIS_CASE_Free(pstCase);
        free(pstCase);
      }

      if(pstQuery)
      {
        GAFIS_QUERY_Free(pstQuery);
        free(pstQuery);
      }
    }
    return	retval;
  }
  */
  def GAFIS_TEXT_GetTextArrayFromStream(pszStream:ChannelBuffer):Array[GATEXTITEMSTRUCT]={
    val nTextItemCount =  pszStream.readInt()

    val list = Range(0,nTextItemCount).map(x=>new GATEXTITEMSTRUCT().fromStreamReader(pszStream,AncientConstants.GBK_ENCODING))
    list.map { pstItem =>
        if (pstItem.bIsPointer > 0) {
          val nSize = pstItem.nItemLen
          pstItem.stData.textContent = pszStream.readBytes(nSize).array()
          val blankSize = gbasedef.GBASE_UTIL_ALIGN(nSize, 4)
          pszStream.skipBytes(blankSize-nSize)
        }
        pstItem
      }.toArray
  }
  def GAFIS_PKG_UnZipMicArray(pstMIC:Array[GAFISMICSTRUCT]){
  }
  def GAFIS_QUERY_GetStreamLen(pstQuery:GAQUERYSTRUCT):Int=
  {

    var nStreamSize = pstQuery.getDataSize

    var nSize = pstQuery.nCandHeadLen;
    if(nSize>0 && pstQuery.pstCandHead_Data!=null)
      nStreamSize += GBASE_UTIL_ALIGN(nSize,4);

    nSize = (pstQuery.nCandLen);
    if(nSize > 0 && pstQuery.pstCand_Data != null)
      nStreamSize += GBASE_UTIL_ALIGN(nSize,4);

    nSize = (pstQuery.nQryCondLen);
    if(nSize>0 && pstQuery.pstQryCond_Data != null)
      nStreamSize += GBASE_UTIL_ALIGN(nSize,4);

    nSize = (pstQuery.nMISCondLen);
    if(nSize > 0 && pstQuery.pstMISCond_Data != null)
      nStreamSize += GBASE_UTIL_ALIGN(nSize,4);

    nSize = (pstQuery.nSvrListLen);
    if(nSize > 0 && pstQuery.pstSvrList_Data != null)
      nStreamSize += GBASE_UTIL_ALIGN(nSize,4);

    nSize = (pstQuery.nTextSqlLen);
    if(nSize > 0 && pstQuery.pstTextSql_Data != null)
      nStreamSize += GBASE_UTIL_ALIGN(nSize,4);

    nSize = (pstQuery.nMICCount);
    if(nSize > 0 && pstQuery.pstMIC_Data != null)
      nStreamSize += GAFIS_MIC_MicStreamLen(pstQuery.pstMIC_Data);

    nSize = (pstQuery.nCommentLen);
    if( nSize > 0 && pstQuery.pszComment_Data != null)
      nStreamSize += GBASE_UTIL_ALIGN(nSize, 4);

    nSize = (pstQuery.nQryInfoLen);
    if( nSize > 0 && pstQuery.pstInfo_Data != null )
      nStreamSize += GBASE_UTIL_ALIGN(nSize, 4);

    nStreamSize;
  }

  def GAFIS_QUERY_Struct2Stream(pstQuery:GAQUERYSTRUCT):Array[Byte]=
  {
    val nStreamSize = GAFIS_QUERY_GetStreamLen(pstQuery);

    val buffer = ChannelBuffers.buffer(nStreamSize)

    pstQuery.writeToStreamWriter(buffer)


    var nSize = (pstQuery.nCandHeadLen);
    if(nSize> 0  && pstQuery.pstCandHead_Data != null)
    {
      pstQuery.pstCandHead_Data.writeToStreamWriter(buffer)
      buffer.writeZero(GBASE_UTIL_ALIGN(nSize,4) - nSize)
    }

    nSize = (pstQuery.nCandLen);
    if(nSize >0  && pstQuery.pstCand_Data != null)
    {
      pstQuery.pstCand_Data.foreach(_.writeToStreamWriter(buffer))
      buffer.writeZero(GBASE_UTIL_ALIGN(nSize,4) - nSize)
    }

    nSize = (pstQuery.nQryCondLen);
    if(nSize > 0 && pstQuery.pstQryCond_Data != null)
    {
      buffer.writeBytes(pstQuery.pstQryCond_Data)
      buffer.writeZero(GBASE_UTIL_ALIGN(nSize,4) - nSize)
    }

    nSize = (pstQuery.nMISCondLen);
    if(nSize > 0 && pstQuery.pstMISCond_Data != null)
    {
      buffer.writeBytes(pstQuery.pstMISCond_Data)
      buffer.writeZero(GBASE_UTIL_ALIGN(nSize,4) - nSize)
    }

    nSize = (pstQuery.nSvrListLen);
    if(nSize > 0 && pstQuery.pstSvrList_Data != null)
    {
      buffer.writeBytes(pstQuery.pstSvrList_Data)
      buffer.writeZero(GBASE_UTIL_ALIGN(nSize,4) - nSize)
    }

    nSize = (pstQuery.nTextSqlLen);
    if(nSize >0  && pstQuery.pstTextSql_Data != null)
    {
      buffer.writeBytes(pstQuery.pstTextSql_Data)
      buffer.writeZero(GBASE_UTIL_ALIGN(nSize,4) - nSize)
    }

    nSize = (pstQuery.nMICCount);
    if(nSize > 0 && pstQuery.pstMIC_Data != null)
    {
      GAFIS_MIC_MicArray2Stream(pstQuery.pstMIC_Data,buffer)
    }

    nSize = (pstQuery.nCommentLen);
    if( nSize > 0 && pstQuery.pszComment_Data != null )
    {
      buffer.writeBytes(pstQuery.pszComment_Data)
      buffer.writeZero(GBASE_UTIL_ALIGN(nSize,4) - nSize)
    }

    nSize = (pstQuery.nQryInfoLen);
    if( nSize > 0 && pstQuery.pstInfo_Data != null )
    {
      pstQuery.pstInfo_Data.writeToStreamWriter(buffer)
      buffer.writeZero(GBASE_UTIL_ALIGN(nSize,4) - nSize)
    }

    buffer.array()
  }

  def GAFIS_QUERY_Stream2Struct(pszStream:ChannelBuffer):GAQUERYSTRUCT=
  {
    val pstQuery = new GAQUERYSTRUCT
    pstQuery.fromStreamReader(pszStream)

    var nSize = pstQuery.nCandHeadLen
    if(nSize>0) {
      pstQuery.pstCandHead_Data = new GAQUERYCANDHEADSTRUCT().fromStreamReader(pszStream)
      pszStream.skipBytes(gbasedef.GBASE_UTIL_ALIGN(nSize,4) - nSize)
    }

    nSize = pstQuery.nCandLen
    if(nSize > 0 ) {
      val num = nSize / new GAQUERYCANDSTRUCT().getDataSize
      pstQuery.pstCand_Data = Range(0,num).map(x=>new GAQUERYCANDSTRUCT().fromStreamReader(pszStream)).toArray
      pszStream.skipBytes(gbasedef.GBASE_UTIL_ALIGN(nSize,4) - nSize)
    }

    nSize = pstQuery.nQryCondLen
    if( nSize >0 ) {
      pstQuery.pstQryCond_Data = pszStream.readBytes(nSize).array()
      pszStream.skipBytes(gbasedef.GBASE_UTIL_ALIGN(nSize,4) - nSize)
    }

    nSize = pstQuery.nMISCondLen
    if( nSize > 0 )
    {
      pstQuery.pstMISCond_Data = pszStream.readBytes(nSize).array()
      pszStream.skipBytes(gbasedef.GBASE_UTIL_ALIGN(nSize,4) - nSize)
    }

    nSize = pstQuery.nSvrListLen
    if( nSize >0)
    {
      pstQuery.pstSvrList_Data = pszStream.readBytes(nSize).array()
      pszStream.skipBytes(gbasedef.GBASE_UTIL_ALIGN(nSize,4) - nSize)
    }

    nSize = pstQuery.nTextSqlLen
    if( nSize > 0 )
    {
      pstQuery.pstTextSql_Data = pszStream.readBytes(nSize).array()
      pszStream.skipBytes(gbasedef.GBASE_UTIL_ALIGN(nSize,4) - nSize)
    }

    nSize = pszStream.readableBytes()
    if(nSize > 0 )
    {
//      val beginReaderIndex = pszStream.readerIndex()
      pstQuery.pstMIC_Data = GAFIS_MIC_GetMicArrayFromStream(pszStream)
      pstQuery.nMICCount = pstQuery.pstMIC_Data.length
      val bIsLat =
      if( pstQuery.stSimpQry.nQueryType == glocdef.LTMATCH || pstQuery.stSimpQry.nQueryType== glocdef.LLMATCH )
        1
      else
        0


      pstQuery.pstMIC_Data.foreach(_.bIsLatent = bIsLat.toByte)
      /*
      val readerIndex = pszStream.readerIndex()
      val micsLength = GAFIS_MIC_MicStreamLen(pstQuery.pstMIC_Data)
       pszStream.skipBytes(micsLength - (readerIndex - beginReaderIndex))
       */
    }

    nSize = pstQuery.nCommentLen
    if( nSize > 0 )
    {
      pstQuery.pszComment_Data = pszStream.readBytes(nSize).array()
      pszStream.skipBytes(gbasedef.GBASE_UTIL_ALIGN(nSize,4) - nSize)
    }

    nSize = pstQuery.nQryInfoLen
    if( nSize > 0 )
    {
      pstQuery.pstInfo_Data  = new GAFIS_QUERYINFO
      pstQuery.pstInfo_Data.fromStreamReader(pszStream)
      pszStream.skipBytes(gbasedef.GBASE_UTIL_ALIGN(nSize,4) - nSize)
      /*
      ZMALLOC_GOTOFIN(pstQuery.pstInfo, GAFIS_QUERYINFO*, nSize);
      memcpy(pstQuery.pstInfo, pos, nSize);
      pstQuery.bQryInfoCanBeFree = 1;
      pos += GBASE_UTIL_ALIGN(nSize,4);
      */
    }

    pstQuery
  }
  def GAFIS_MIC_MicStreamLen(mics:Array[GAFISMICSTRUCT]):Int={
    var n = 0;
    mics.foreach { pmic =>
      n += 160 + 36; // 4 items  and one  mic name.
      if (pmic.nIndex > 0) {
        n += 40; // has index items
      }
      n += 36 + gbasedef.GBASE_UTIL_ALIGN(pmic.nMntLen, 4)
      n += 36 + gbasedef.GBASE_UTIL_ALIGN(pmic.nImgLen, 4)
      n += 36 + gbasedef.GBASE_UTIL_ALIGN(pmic.nCprLen, 4)
      n += 36 + gbasedef.GBASE_UTIL_ALIGN(pmic.nBinLen, 4)
    }

    n
  }
}

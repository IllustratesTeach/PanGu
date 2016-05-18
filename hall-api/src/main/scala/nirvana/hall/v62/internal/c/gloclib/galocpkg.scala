package nirvana.hall.v62.internal.c.gloclib

import nirvana.hall.v62.internal.c.gbaselib.gitempkg

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

  /*
  def GAFIS_PKG_GetTpCard(pstPkg:GBASE_ITEMPKG_OPSTRUCT) //,GTPCARDINFOSTRUCT* pstCard)
  {

    GBASE_ITEMPKG_ITEMHEADSTRUCT*	pstItemHead	= NULL;
    GBASE_ITEMPKG_ITEMSTRUCT*		pstItem		= NULL;
    GAFISMICSTRUCT*					pstMic		= NULL;
    GATEXTITEMSTRUCT*				pstTextItem	= NULL;

    int	retval,nItemCount,i,j,nCount;

    retval = -1;

    val headers = GBASE_ITEMPKG_GetItemDir(pstPkg)
    if(headers.size < 1 ){
      throw new IllegalStateException("item dir is empty")
    }

    val pstCard = new GTPCARDINFOSTRUCT
    headers.foreach{header=>
      val pstItem = GBASE_ITEMPKG_GetItem(pstPkg,header.szItemName).getOrElse(throw new IllegalStateException("item not found"))
      header match {
        case PKG_ITEMTYPE_THIS=>
          pstCard.fromByteArray(pstItem.bnRes)
        case PKG_ITEMTYPE_MIC =>
        val pstMics = GAFIS_MIC_GetMicArrayFromStream(pstItem.bnRes)
        pstCard.pstMIC_Data = pstMics.toArray
        pstCard.nMicItemCount	= pstMics.length

        if( GAFIS_PKG_UnZipMicArray(pstCard->pstMIC, nCount, 0) < 0 ) ERRFAILFINISHEXIT();

        break;

        case PKG_ITEMTYPE_TEXT:
        nCount = GAFIS_TEXT_GetTextArrayFromStream(pstItem->bnRes,Char4To_uint4(pstItem->stHead.nItemLen),&pstTextItem);
        if(nCount < 0)	ERRFAILFINISHEXIT();
        pstCard->pstText = pstTextItem;
        uint2ToChar2((uint2)(uint4)nCount,pstCard->nTextItemCount);
        pstCard->bTextCanBeFreed	= 1;
        break;

        //  [12/8/2006]
        case PKG_ITEMTYPE_EXTRAINFO:
        nCount = Char4To_uint4(pstItem->stHead.nItemLen);
        if( nCount > 0 )
        {
          ZMALLOC_GOTOFIN(pstCard->pstInfoEx, GAFIS_TPADMININFO_EX*, nCount);
          memcpy(pstCard->pstInfoEx, pstItem->bnRes, nCount);
          pstCard->bInfoExCanBeFreed = 1;
          uint2ToChar2((uint2)(uint4)nCount, pstCard->nInfoExLen);
          //set itemflag for add or update  [12/18/2006]
          pstCard->nItemFlag |= TPCARDINFO_ITEMFLAG_INFOEX;
          if(pstCard->pstInfoEx->nItemFlag == 0)	pstCard->pstInfoEx->nItemFlag = 0xFF;
          if(pstCard->pstInfoEx->stFpx.nItemFlag == 0)	pstCard->pstInfoEx->stFpx.nItemFlag = 0xFF;
        }
        break;
        default:	break;
      }
    }

    GAFIS_Err_ClearError();
    GAFIS_PKG_UTIL_CheckTPCard(pstCard);
    retval = 1;
    Finish_Exit:
    if(pstItemHead)	GAFIS_free(pstItemHead);
    if(retval < 0)	GAFIS_TPCARD_Free(pstCard);
    return retval;
  }

  int	GAFIS_PKG_GetLpCard(GBASE_ITEMPKG_OPSTRUCT* pstPkg,GLPCARDINFOSTRUCT*	pstCard)
  {
    GBASE_ITEMPKG_ITEMHEADSTRUCT*	pstItemHead	= NULL;
    GBASE_ITEMPKG_ITEMSTRUCT*		pstItem		= NULL;
    GAFISMICSTRUCT*					pstMic		= NULL;
    GATEXTITEMSTRUCT*				pstTextItem	= NULL;

    int	retval,nItemCount,i,j,nCount;

    retval = -1;

    nItemCount = GBASE_ITEMPKG_GetItemDir(pstPkg,&pstItemHead);
    if(nItemCount < 1)	ERRFAILFINISHEXIT();

    for( i = 0; i < nItemCount; ++i)
    {
      if( GBASE_ITEMPKG_GetItem(pstPkg,pstItemHead[i].szItemName,&pstItem) < 0 ) ERRFAILFINISHEXIT();
      switch(Char4To_uint4(pstItemHead[i].nItemType))
      {
        case PKG_ITEMTYPE_THIS:
        //			pstLpCard = (GLPCARDINFOSTRUCT*)pstItem->bnRes;
        //			memcpy(pstCard->szCardID,pstLpCard->szCardID,sizeof(pstCard->szCardID));
        //			memcpy(&pstCard->stAdmData,&pstLpCard->stAdmData,sizeof(pstCard->stAdmData));
        //			pstCard->nItemFlag |= pstLpCard->nItemFlag;
        memcpy(pstCard, pstItem->bnRes, sizeof(*pstCard));
        GAFIS_PKG_UTIL_LPCARD_ClearPoint(pstCard);
        break;

        case PKG_ITEMTYPE_MIC:
        nCount = GAFIS_MIC_GetMicArrayFromStream(pstItem->bnRes,Char4To_uint4(pstItem->stHead.nItemLen),&pstMic);
        if(nCount < 0)	ERRFAILFINISHEXIT();
        for( j = 0; j <	nCount; ++j)
        {
          if(pstMic[j].pstBin)	pstMic[j].bBinCanBeFreed = 1;
          if(pstMic[j].pstCpr)	pstMic[j].bCprCanBeFreed = 1;
          if(pstMic[j].pstImg)	pstMic[j].bImgCanBeFreed = 1;
          if(pstMic[j].pstMnt)	pstMic[j].bMntCanBeFreed = 1;
          pstMic->bIsLatent = 1;
        }
        pstCard->pstMIC = pstMic;
        pstCard->nMicItemCount	= (char)nCount;
        pstCard->bMicCanBeFreed	= 1;

        if( GAFIS_PKG_UnZipMicArray(pstCard->pstMIC, nCount, 0) < 0 ) ERRFAILFINISHEXIT();

        break;
        case PKG_ITEMTYPE_TEXT:
        nCount = GAFIS_TEXT_GetTextArrayFromStream(pstItem->bnRes,Char4To_uint4(pstItem->stHead.nItemLen),&pstTextItem);
        if(nCount < 0)	ERRFAILFINISHEXIT();
        pstCard->pstText = pstTextItem;
        uint2ToChar2((int2)nCount,pstCard->nTextItemCount);
        pstCard->bTextCanBeFreed	= 1;
        break;

        //  [12/8/2006]
        case PKG_ITEMTYPE_EXTRAINFO:
        nCount = Char4To_uint4(pstItem->stHead.nItemLen);
        if( nCount > 0 )
        {
          ZMALLOC_GOTOFIN(pstCard->pstExtraInfo, GAFIS_LP_EXTRAINFO*, nCount);
          memcpy(pstCard->pstExtraInfo, pstItem->bnRes, nCount);
          pstCard->bExtraInfoCanBeFreed = 1;
          uint2ToChar2((uint2)(uint4)nCount, pstCard->nExtraInfoLen);
          //set itemflag for add or update  [12/18/2006]
          pstCard->nItemFlag |= LPCARDINFO_ITEMFLAG_EXTRAINFO;
          if(pstCard->pstExtraInfo->nItemFlag == 0)	pstCard->pstExtraInfo->nItemFlag = 0xFF;
          if(pstCard->pstExtraInfo->stFpx.nItemFlag == 0)	pstCard->pstExtraInfo->stFpx.nItemFlag = 0xFF;
        }
        break;
        default:	break;
      }

      //AFIS_FREE(pstItem);
    }

    GAFIS_Err_ClearError();
    GAFIS_PKG_UTIL_CheckLPCard(pstCard);
    retval = 1;
    Finish_Exit:
    if(pstItemHead)	free(pstItemHead);
    //if(pstItem)		free(pstItem);
    if(retval < 0)	GAFIS_LPCARD_Free(pstCard);
    return retval;
  }

  int	GAFIS_PKG_GetCase(GBASE_ITEMPKG_OPSTRUCT* pstPkg,GCASEINFOSTRUCT*	pstCase)
  {
    GBASE_ITEMPKG_ITEMHEADSTRUCT*	pstItemHead	= NULL;
    GBASE_ITEMPKG_ITEMSTRUCT*		pstItem		= NULL;
    GATEXTITEMSTRUCT*				pstTextItem	= NULL;
    GAKEYSTRUCT*					pstKey		= NULL;
    int	retval,nItemCount,i,nCount;

    retval = -1;

    nItemCount = GBASE_ITEMPKG_GetItemDir(pstPkg,&pstItemHead);
    if(nItemCount < 1)	ERRFAILFINISHEXIT();

    for( i = 0; i < nItemCount; ++i)
    {
      if( GBASE_ITEMPKG_GetItem(pstPkg,pstItemHead[i].szItemName,&pstItem) < 0 ) ERRFAILFINISHEXIT();
      switch(Char4To_uint4(pstItemHead[i].nItemType))
      {
        case PKG_ITEMTYPE_THIS:
        //			pstCaseInfo = (GCASEINFOSTRUCT*)pstItem->bnRes;
        //			memcpy(pstCase->nGroupID,pstCaseInfo->nGroupID,sizeof(pstCase->nGroupID));
        //			memcpy(pstCase->szCaseID,pstCaseInfo->szCaseID,sizeof(pstCase->szCaseID));
        //			memcpy(pstCase->nSID,pstCaseInfo->nSID,sizeof(pstCase->nSID));
        //			memcpy(pstCase->bnUUID,pstCaseInfo->bnUUID,sizeof(pstCase->bnUUID));
        //			memcpy(pstCase->szMISCaseID,pstCaseInfo->szMISCaseID,sizeof(pstCase->szMISCaseID));
        //			pstCase->nItemFlag = pstCaseInfo->nItemFlag;
        //			pstCase->nItemFlagEx = pstCaseInfo->nItemFlagEx;
        memcpy(pstCase, pstItem->bnRes, sizeof(*pstCase));
        GAFIS_CASE_ClearPointer(pstCase);
        break;

        case PKG_ITEMTYPE_FINGERID:
        nCount = Char4To_uint4(pstItem->stHead.nItemLen);
        ZMALLOC_GOTOFIN(pstKey,GAKEYSTRUCT*,nCount);
        memcpy(pstKey,pstItem->bnRes,nCount);
        pstCase->pstFingerID = pstKey;
        pstCase->bFingerIDCanBeFreed = 1;
        uint2ToChar2((int2)(nCount/sizeof(GAKEYSTRUCT)),pstCase->nFingerCount);
        uint4ToChar4(nCount,pstCase->nFingerIDLen);
        pstCase->nItemFlag	|= (GCIS_ITEMFLAG_FINGERCOUNT|GCIS_ITEMFLAG_FINGERID);
        pstKey	= NULL;
        break;
        case PKG_ITEMTYPE_PALMID:
        nCount = Char4To_uint4(pstItem->stHead.nItemLen);
        ZMALLOC_GOTOFIN(pstKey,GAKEYSTRUCT*,nCount);
        memcpy(pstKey,pstItem->bnRes,nCount);
        pstCase->pstPalmID = pstKey;
        pstCase->bPalmIDCanBeFreed = 1;
        uint2ToChar2((int2)(nCount/sizeof(GAKEYSTRUCT)),pstCase->nPalmCount);
        uint4ToChar4(nCount,pstCase->nPalmIDLen);
        pstCase->nItemFlag	|= (GCIS_ITEMFLAG_PALMCOUNT|GCIS_ITEMFLAG_PALMID);
        pstKey	= NULL;
        break;

        case PKG_ITEMTYPE_TEXT:
        nCount = GAFIS_TEXT_GetTextArrayFromStream(pstItem->bnRes,Char4To_uint4(pstItem->stHead.nItemLen),&pstTextItem);
        if(nCount < 0)	ERRFAILFINISHEXIT();
        pstCase->pstText = pstTextItem;
        uint2ToChar2((int2)nCount,pstCase->nTextItemCount);
        pstCase->bTextCanBeFreed	= 1;
        pstCase->nItemFlag |= GCIS_ITEMFLAG_TEXT;
        break;

        //  [12/8/2006]
        case PKG_ITEMTYPE_EXTRAINFO:
        nCount = Char4To_uint4(pstItem->stHead.nItemLen);
        if( nCount > 0 )
        {
          ZMALLOC_GOTOFIN(pstCase->pstExtraInfo, GAFIS_CASE_EXTRAINFO*, nCount);
          memcpy(pstCase->pstExtraInfo, pstItem->bnRes, nCount);
          pstCase->bExtraInfoCanBeFreed = 1;
          uint2ToChar2((uint2)(uint4)nCount, pstCase->nExtraInfoLen);
          //set itemflag for add or update  [12/18/2006]
          pstCase->nItemFlagEx |= GCIS_ITEMFLAGEX_EXTRAINFO;
          if(pstCase->pstExtraInfo->nItemFlag == 0)	pstCase->pstExtraInfo->nItemFlag = 0xFF;
          if(pstCase->pstExtraInfo->stFpx.nItemFlag == 0)	pstCase->pstExtraInfo->stFpx.nItemFlag = 0xFF;
        }
        break;

        default:	break;
      }
    }

    GAFIS_Err_ClearError();
    GAFIS_PKG_UTIL_CheckCase(pstCase);
    retval = 1;
    Finish_Exit:
    if(pstItemHead)	GAFIS_free(pstItemHead);
    if(retval < 0)	GAFIS_CASE_Free(pstCase);
    return retval;
  }

  int	GAFIS_PKG_GetQuery(GBASE_ITEMPKG_OPSTRUCT* pstPkg,GAQUERYSTRUCT* pstQuery)
  {
    GBASE_ITEMPKG_ITEMHEADSTRUCT*	pstItemHead	= NULL;
    GBASE_ITEMPKG_ITEMSTRUCT*		pstItem		= NULL;

    int	retval,nItemCount,i;

    retval = -1;

    nItemCount = GBASE_ITEMPKG_GetItemDir(pstPkg,&pstItemHead);
    if(nItemCount < 1)	ERRFAILFINISHEXIT();

    for( i = 0; i < nItemCount; ++i)
    {
      if( GBASE_ITEMPKG_GetItem(pstPkg,pstItemHead[i].szItemName,&pstItem) < 0 ) ERRFAILFINISHEXIT();
      switch(Char4To_uint4(pstItemHead[i].nItemType))
      {
        case PKG_ITEMTYPE_THIS:
        if( GAFIS_QUERY_Stream2Struct((UCHAR*)pstItem->bnRes,Char4To_uint4(pstItem->stHead.nItemLen),pstQuery) < 0 ) ERRFAILFINISHEXIT();
        break;
        default:	break;
      }
    }

    GAFIS_Err_ClearError();
    retval = 1;
    Finish_Exit:
    if(pstItemHead)	free(pstItemHead);
    if(retval < 0)	GAFIS_QUERY_Free(pstQuery);
    return retval;
  }

  int	GAFIS_PKG_GetPkgType(GBASE_ITEMPKG_OPSTRUCT* pstPkg,void** ppData)
  {
    GBASE_ITEMPKG_HEADSTRUCT*	pstHead = NULL;
    GTPCARDINFOSTRUCT*			pstTpCard	= NULL;
    GLPCARDINFOSTRUCT*			pstLpCard	= NULL;
    GCASEINFOSTRUCT*			pstCase		= NULL;
    GAQUERYSTRUCT*				pstQuery	= NULL;
    int	retval,nPkgType;

    if( !pstPkg || !ppData || !GBASE_ITEMPKG_IsValidHead(pstPkg) )
    {
      GAFIS_GAFISERR_SET(GAFISERR_PARAMETER_INVALID,0,0);
      return -1;
    }

    retval = -1;

    pstHead = (GBASE_ITEMPKG_HEADSTRUCT*)pstPkg->pbnPkg;
    nPkgType = Char4To_uint4(pstHead->nPkgType);
    switch(nPkgType)
    {
      case PKG_TYPE_TPCARD:
      ZMALLOC_GOTOFIN(pstTpCard,GTPCARDINFOSTRUCT*,sizeof(*pstTpCard));
      if( GAFIS_PKG_GetTpCard(pstPkg,pstTpCard) < 0)	ERRFAILFINISHEXIT();
      *ppData = pstTpCard;
      retval = nPkgType;
      break;
      case PKG_TYPE_LPCARD:
      ZMALLOC_GOTOFIN(pstLpCard,GLPCARDINFOSTRUCT*,sizeof(*pstLpCard));
      if( GAFIS_PKG_GetLpCard(pstPkg,pstLpCard) < 0)	ERRFAILFINISHEXIT();
      *ppData = pstLpCard;
      retval = nPkgType;
      break;
      case PKG_TYPE_CASE:
      ZMALLOC_GOTOFIN(pstCase,GCASEINFOSTRUCT*,sizeof(*pstCase));
      if( GAFIS_PKG_GetCase(pstPkg,pstCase) < 0)	ERRFAILFINISHEXIT();
      *ppData = pstCase;
      retval = nPkgType;
      break;
      case PKG_TYPE_QUERY:
      ZMALLOC_GOTOFIN(pstQuery,GAQUERYSTRUCT*,sizeof(*pstQuery));
      if( GAFIS_PKG_GetQuery(pstPkg,pstQuery) < 0)	ERRFAILFINISHEXIT();
      *ppData = pstQuery;
      retval = nPkgType;
      break;

      case PKG_TYPE_FPTFILE:
      default:
      *ppData = NULL;
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



}

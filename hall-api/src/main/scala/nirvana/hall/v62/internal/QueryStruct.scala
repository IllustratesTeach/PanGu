package nirvana.hall.v62.internal

import nirvana.hall.v62.annotations.Length

/**
 * compat old system GAQUERYSTRUCT
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-30
 */
class QueryStruct extends AncientData{
  var	stSimpQry:GAQUERYSIMPSTRUCT = _					// 192 bytes
  var	nStartLibID:Int = _ 		// int4 start lib id
  var	nEndLibID:Int = _ 		// int4 end lib id
  var	tRmtAddTime:Int = _ 	// distinguish different queries
  var	nItemFlagA:Byte = _ 			// GQIFA_XXX
  var	nItemFlagB:Byte = _ 			// GQIFB_xxx
  var	nItemFlagC:Byte = _ 			// GQIFC_xxx
  var	nItemFlagD:Byte = _ 			// GAIFD_XXX
  var	nItemFlagE:Byte = _ 			// GAIFE_XXX
  var	nItemFlagF:Byte = _ 			// GAIFF_XXX
  var	nItemFlagG:Byte = _ 			// GAIFG_XXX
  var	bQryInfoCanBeFree:Byte = _ ;
  var	nCommentLen:Int = _ 		// length of comment.
  var	pszCommentPointer:Long = _ ;		// some comments.
  @Length(2)
  var	stKeyRange:Array[KeyRangeStruct] = _ 		// 128 bytes, to here is 256+64 bytes
  var	pstCandHead:Long = _ 		//GAQUERYCANDHEADSTRUCT// pointer to candidate head, no substructure
  var	pstCand:Long = _ //GAQUERYCANDSTRUCT		*pstCand;			// pointer to candidate list, no substructure
  var	pstMIC:Long = _ //GAFISMICSTRUCT			*pstMIC;			// pointer to micstructure, contain many informations, has substructure
  var	pstQryCond:Long = _		// points to GBASE_ITEMPKG structure
  var	pstMISCond:Long = _;		// mis query condition, no substructure
  var	pstSvrList:Long = _;		// server list, no substructure
  var	pstTextSql:Long = _;		// pointer to text sql statement, no substructure
  // to here is 320+56+32=408 bytes
  var	nQryCondLen:Int = _ ;		// qry condition length
  var	nCandHeadLen:Int = _ ;	// candidate head len
  var	nCandLen:Int = _ ;		// candidate length
  var	nSvrListLen:Int = _ ;
  var	nMISCondLen:Int = _ ;
  var	nTextSqlLen:Int = _ ;
  var	nMICCount:Int = _		// # of mics
  // to here is 376+28+32 = 436 bytes
  var	bCandHeadCanBeFree:Byte = _
  var	bCandCanBeFree:Byte = _
  var	bMICCanBeFree:Byte = _
  var	bQryCondCanBeFree:Byte = _
  var	bMISCondCanBeFree:Byte = _
  var	bSvrListCanBeFree:Byte = _
  var	bTextSqlCanBeFree:Byte = _
  var	bCommentCanBeFree:Byte = _
  var	nQryInfoLen:Int= _ 		// qry info length.
  var	nItemFlagH:Byte = _ 		// GAIFH_XXX
  @Length(11)
  var	bnRes3:Array[Byte]= _;			// reserved
  var	nFifoQueDBID:Short = _ ;
  var	nFifoQueTID:Short = _ ;
  @Length(6)
  var	nFifoQueSID:Array[Byte]= _
  var	bnRes_FifoQueSID:Short = _
  var	pstInfo:Long = _ //GAFIS_QUERYINFO			*pstInfo;			// query info.
  @Length(32)
  var	szRmtQryKey:Array[Byte] = _	// used by remote server to add heterogeneous systems


}
class GAQUERYSIMPSTRUCT extends AncientData {
  var	cbSize = 0;
  @Length(32)
  var	szKeyID:String = _ 	// original key(finger id)
  @Length(16)
  var	szUserName:String = _		// user name(to here is 52 bytes)
  var	nQueryType:Byte =  _			// querytype, QUERYTYPE_LT, TT, TL, LL
  var	nPriority:Byte = _ ;			// priority
  var	nHitPossibility:Byte = _;	// hit possibility(0-100)
  var	nStatus:Byte = _;			// status(to search, searching, to check , checking, checked, nullstate)(to here is 56 bytes)
  var	nFlag:Byte = _ ;				// GAQRY_FLAG_XXX
  var	nRmtFlag:Byte = _;			// identify type of the query GAQRY_RMTFLAG_XX
  var	nStage:Byte = _ ;				// stage( for two step or multi step method, we can expand to k step methods)
  var	nFlagEx:Byte = _ ;			// Extended flag
  var	nQueryID_old:Int = _ 		// query id(to here is 64 bytes)
  var	stSrcDB:DBIDStruct = _ ;	// where the data come from
  @Length(4)
  var	stDestDB:Array[DBIDStruct]= _	// at most we can search 4 database at the same time
  var	nTimeUsed:Int = _ 		// in seconds
  var	nMaxCandidateNum:Int = _ 	// maximum candidate num
  var	nCurCandidateNum:Int = _ 	// current candidate num(to here is 96 bytes)
  var	tSubmitTime:GafisDateTime = _	// submit time
  var	tFinishTime:GafisDateTime = _	// finish time
  var	tCheckTime:GafisDateTime = _ 		// check time
  @Length(16)
  var	szCheckUserName:String = _	// this user name is in user id list
  ///////////////to here is 96+40=136 bytes
  var	nVerifyResult:Byte = _ 	// verify result, not match, match, undetermined, GAQRY_VERIFYRES_XXX
  var	nRmtState:Byte = _ 		// remote query state, GAQRY_RMTSTATE_xxxx
  var	nMISState:Byte = _ 		// mis query state, GAQRY_MISSTATE_XXX
  var	nDestDBCount:Byte = _
  var	nGroupID:Int= _ 	// group id
  var	bIsFifoQue:Byte = _
  var	nVerifyPri:Byte = _ 		// verify priority.
  var	nFlag3:Byte = _ 			// add two more flags GARAY_FLAG3_XXX
  var	nFlag4:Byte = _ 			// GAQRY_FLAG4_XXX
  var	nFlag5:Byte = _ 			// GAQRY_FLAG5_XXX
  var	nFlag6:Byte = _ 			// GAQRY_FLAG6_XXX, for 查询的多点下载
  var	nFlag7:Byte = _ 			// GAQRY_FLAG7_XXX，TT自动认定使用的标志。
  var	nFlag8:Byte = _ 			// 2008.04.12 未使用，为扩展保留；2010.05.09：增加一个标记，表明是广东LT衰减率测试
  @Length(6)
  var	nQueryID:Array[Byte] = _
  var	bnRes_sid:Short= _ 		// reserved
  var	nMinScore:Int= _ 	// the score in candidate list at least this value.
  var	nSchCandCnt:Int= _ 	// search candidate #.
  @Length(16)
  var	szReCheckUserName:String = _ 	// rechecker user name
  var	tReCheckDate:GafisDateTime = _ 			// re check date
}
class DBIDStruct extends AncientData{
  var nDBID:Short = _
  var nTableID:Short = _;
}
class GafisTime extends AncientData{
  var tMilliSec:Short = _ 	// millisecond.	[0, 999]
  var tMin:Byte = _			// minute. [0, 59]
  var tHour:Byte = _ 			// hour,   [0, 23]
}	// size is 4 bytes long

class GafisDate extends AncientData{
  var tDay:Byte = _ 			// day, [1, 31]
  var tMonth:Byte = _ 			// month, [0, 11]
  var tYear:Short = _ 		// short int year.
}	// size is 4 bytes long.

class GafisDateTime extends AncientData {
  var tTime:GafisTime = _
  var tDate:GafisDate = _ 	// date
}

class KeyRangeStruct extends AncientData  {
  @Length(32)
  var szStartKey:String = _
  @Length(32)
  var szEndKey:String = _
}

// the following structure need not be changed frequently
class tagGAQUERYCANDHEADSTRUCT extends AncientData {
  var cbSize:Int = _ 	// size of this structure
  @Length(32)
  var szKey:String = _ 	// key of the original data
  @Length(16)
  var szUserName:String = _ 	// user name
  var nQueryType:Byte= _ 	// query type
  var CPUCoef:Byte= _ 	// cpu coefficient
  var nSrcDBID:Short = _ 	// dbid
  var nTableID:Short = _ 	// table id
  var bIsPalm:Byte = _ 		// is palm match
  @Length(3)
  var bnRes:Array[Byte] = _ 	// to here is 64 bytes
  @Length(6)
  var nQueryID:Array[Byte] = _ // query id
  var nCandidateNum:Int = _ 	// current candidate num
  var tSubmitTime:GafisDateTime = _ 	// submit time
  var tFinishTime:GafisDateTime = _ 	// finish time
  var nRecSearched:Long = _		// record number searched, to here is 96 bytes
  @Length(32)
  var bnRes2:Array[Byte] = _ 			// reserved
} 	// size of this structure is 128 bytes


class tagGAQUERYCANDSTRUCT extends AncientData  {
  var	cbSize:Int = _ 		// size of this structure
  var	nScore:Int = _ 		// score
  @Length(32)
  var varszKey:String = _ 		// key
  var	nServerID:Short = _ 	// server id
  var	nDBID:Short = _ 		// dbid
  var	nTableID:Short = _ 	// table id
  var	nFileID:Short = _ 		// file id
  var	nCheckState:Byte = _ 	// check state, GAQRYCAND_CHKSTATE_XXX
  @Length(3)
  var	bnRes_nRID:Array[Byte] = _ 		// record id, to here is 52 bytes
  var	nStatus:Byte = _ 		// status,GAQRYCAND_STATUS_XXX
  var	nGetCandFailCnt:Byte = _ 	// get fail count
  var	bIsDataCached:Byte = _ 		// is data cached
  var	nTxPassedCount:Byte = _ 		// tx passed count
  var	nIndex:Byte = _ ;				// 1-10, finger, 1-2 palm
  var	nFlag:Byte = _ ;				// GAQRYCAND_FLAG_XXX
  var	nFinalServerID:Short = _ 	// final server id, to here is 60 bytes
  var	nSrcKeyIndex:Byte = _ 		// if src have multi key., using this flag to indicate which one match
  /**
   * 增加两个字段：
   *	nMatchAlgorithm：用来表示该候选是通过那个比对算法得到的，值为GAFISMATCH_ALGORITHM_xxx
   *	nGroupid：用来表示候选中的重卡信息，nGroupid=0表示没有重卡，nGroupid>0并且相等的候选表示属于同一个重卡组
   * 2010.05.12：又增加了一个字段nDupCardCnt，用来记录属于该groupid的重卡个数
   */
  var nMatchAlgorithm:Byte = _
  var nGroupid:Byte = _
  var nDupCardCnt:Byte = _
  //UCHAR	bnRes2[1];			// reserved(64 bytes to here)
  var tFinishTime:GafisDateTime = _ 	// finished time
  var nServerDBID:Short = _ 		// at by xiaxinfeng.
  var nServerTableID:Short = _

  /**
   *	2011年8月2日：新增两个字段，替换了下面的bnRes3[4]字段，
   * 这两个字段分别用来表示该合并候选在许公望候选和王曙光候选中的排名（从1开始）
   * 这两个字段是否有意义由nMatchAlgorithm的值决定
   */
  //UCHAR	bnRes3[4];				// reserved
  var nXgwRank:Short = _
  var nWsgRank:Short = _
  @Length(6)
  var nSID:Array[Byte] = _
  var bnRes_SID:Short = _
  /**
   * 增加两个字段，分别用来标记该候选在一次比对时的得分和排名（从1开始）
   * On Apr. 6, 2010
   */
  var nStepOneScore:Short = _
  var nStepOneRank:Int = _
  var bnRes4:Short = _ 		// reserved
} // size of this structure is 96 bytes

class tagGAFISMICSTRUCT extends AncientData {
  var cbSize:Int = _ 		// size of this structure
  var nItemFlag:Byte = _ 		// which item is used, GAMIC_ITEMFLAG_XXX, where xxx is MNT, BIN, IMG OR CPR
  var nItemType:Byte = _ 		// type, palm or finger, GAMIC_ITEMTYPE_FINGER, GAMIC_ITEMTYPE_PALM, FACE, DATA
  var nItemData:Byte = _ 		// if is finger, then it's finger index(1-10), if it's palm it's 1(right)2(left)
  // exactly for palm its GTPIO_ITEMINDEX_PALM_XXXX
  var bIsLatent:Byte = _ ;		// is latent or not, do not use this flag
  var nBinLen:Int = _ ;		// binary data length
  var nMntLen:Int = _ ;		// minutia length
  var nImgLen:Int = _ ;		// image length
  var nCprLen:Int = _ ;		// to here is 24 bytes(included)
  var bMntCanBeFreed:Byte = _ ;
  var bImgCanBeFreed:Byte = _;
  var bCprCanBeFreed:Byte = _;
  var bBinCanBeFreed:Byte = _;
  var nIndex:Byte = _ ;
  @Length(3)
  var bnRes2:Array[Byte]=_;		// 3 bytes reserved(to here is 32 bytes)

  var pstMnt:Long = _
  var pstImg:Long = _
  var pstCpr:Long = _
  var pstBin:Long = _
  /*GAFISIMAGESTRUCT *	pstMnt;			// pointer to mnt, pstMnt->bnData is the actual minutia
  #if	POINTER_SIZE==4
  UCHAR	bnRes_pstMnt[4];
  #endif
  GAFISIMAGESTRUCT *	pstImg;	// pointer to image
  #if	POINTER_SIZE==4
  UCHAR	bnRes_pstImg[4];
  #endif
  GAFISIMAGESTRUCT *	pstCpr;	// pointer to compressed image
  #if	POINTER_SIZE==4
  UCHAR	bnRes_pstCpr[4];
  #endif
  GAFISIMAGESTRUCT *	pstBin;	// pointer to binary data
  #if	POINTER_SIZE==4
  UCHAR	bnRes_pstBin[4];
  #endif
  */
} // size of this structure is 64 bytes

// the following structure represents a general image
class tagGAFISIMAGESTRUCT extends AncientData {
  var stHead:tagGAFISIMAGEHEADSTRUCT = _ ;	// image head structure
  var bnData:Long = _ ;	// image followed
} // size of this structure depends on the image size(32-2GB)
class tagGAFISIMAGEHEADSTRUCT extends AncientData  {

  var nSize:Int = _;			// size of this structure, 4 bytes int
  var nWidth:Short = _ ;			// width of the image, 2 bytes int
  var nHeight:Short = _ ;			// height of the image, 2 bytes int
  var nBits:Byte = _ ;				// bit per pixel, 8, 16, 24, no pallete, 1 byte
  var bIsCompressed:Byte = _ ;		// whether is compressed, 1 byte
  var nCompressMethod:Byte = _ ;	// compressed method, if compressed, 1 byte, GAIMG_CPRMETHOD_XXX
  var nCaptureMethod:Byte = _ ;		// image capture method GA_IMGCAPTYPE_XXX, 1 byte
  var nImgSize:Int = _;		// size of image, does not include head size, 4 bytes int
  var nResolution:Short = _ ;		// resolution, 2 bytes int
  var bIsPlain:Byte = _;			// plain type or non plain type
  var nImageType:Byte = _ ;			// GAIMG_IMAGETYPE_XXX
  var nFingerIndex:Byte = _;		// used only by exf.
  var nFlag:Byte = _;				// flag, reserved.
  var nSignature:Short = _ ;		// somes times the user submit and query and then delete the original
  // image and replace with a new one but keep the old key,
  // so when identifying whether hit the result, the user
  // got a totally different image for original minutia.
  // and we add a signature to reduce the conflict.
  var nQualDesc:Byte = _ ;			// quality description. GAFIS_QUALDESC_xxx
  var nMntFormat:Byte = _;			//!< 特征数据格式，标记了特征提取算法，值为GAFIS_MNTFORMAT_xxx
  @Length(6)
  var bnRes2:Array[Byte] = _ ;			// reserved
  @Length(32)
  var szName:String = _ ;			// image name
} 		// total length of this structure is 64 bytes

// dec. 12. 09, 2004. the GAQUERYSTRUCT is too small, and can not hold too many info
// so we add another sub structure to hold it.
class tagGAFIS_QUERYINFO extends AncientData {
  var cbSize:Int = _ ;	// size now is 256 bytes long.
  var bnRes:Int = _ ;	// reserved.
  @Length(16)
  var szUserUnitCode:String = _
  @Length(16)
  var szCheckerUnitCode:String = _
  @Length(16)
  var szReCheckerUnitCode:String = _
  // to here is 48+8 bytes long.
  var nRecSearched:Int = _ 	// how many records has been searched.
  var bnRes0:Int = _
  // to here is 64 bytes long.
  @Length(16)
  var bnIP:String = _ 	// for ipv4, using first 4 bytes.
  @Length(32)
  var szComputerName:String = _ 	// computer name.
  @Length(32)
  var szQryUID:String = _ 		// query uuid.
  // to here is 128+16 bytes long.
  @Length(32)
  var szFPXForeignTaskID:String = _
  @Length(16)
  var szFPXVerifyUnitCode:String = _
  // to here is 128+64 bytes long.
  var nFPXStatus:Byte = _
  var nFPXVerifyResult:Byte = _
  var nItemFlag:Byte = _ ;	// QUERYINFO_ITEMFLAG_XXX
  @Length(5)
  var bnRes1:Array[Byte] = _ ;
  @Length(56)
  var bnRes2:Array[Byte] = _ 	// reserved.
} // 256 bytes long.

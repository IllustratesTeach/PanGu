package nirvana.hall.v62.internal

import nirvana.hall.v62.annotations.{IgnoreTransfer, Length}
import nirvana.hall.v62.services.AncientData

/**
 * synchronize data
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-03
 */
class DataSyncStruct {
  def convertProtoBuf2TPCard(): Unit ={

  }
}

// the following structure represents a person's tenprint card data
// it can include :
// 10 fingers(mnt, image, compressed image, bin data)
// 2 palms( mnt, image, compressed image, bin data)(may have 8 parts)
// 1-4 part plain fingers(image only, compressed format, we recommend 4 parts)
// 1-3 face images(image only)
// 1-8 card image area(image only)
// the structure is easy to analyze when storing
// for getting data from database, we need to identify which part we want to get
// we define a set of char id to identify what we want

class tagGTPCARDINFOSTRUCT  extends AncientData {
  var cbSize:Int = _ 				// size of this structure 4 bytes int
  var nMicItemCount:Byte = _ 			// count of structure of GAFISMICSTRUCT  1 byte int
  var nItemFlag:Byte = _				// which item is used, TPCARDINFO_ITEMFLAG_XXX
  var nTextItemCount:Short = _ 		// text item count
  @Length(32)
  var szCardID:String = _ 			// card id, key words(to here is 40 bytes)
  //////////////to here is 40 bytes//////////////
  var bnRes:Int = _ 				// reserved
  var bnRes2:Int = _				// 4 bytes reserved, to here is 48 bytes
  ///////////////to here is 48 bytes
  var pstMIC:Long =  _	// pointer to micstruct(may not have minutia)
  var pstText:Long = _ 	// pointer to text, the structure is GATEXTITEMSTRUCT.
  var pstInfoEx:Long = _
  @Length(8)
  var bnRes3:Array[Byte] = _			// 8 bytes reserved, to here is 80 bytes
  ////////////////to here is 80 bytes
  var bMicCanBeFreed:Byte = _
  var bTextCanBeFreed:Byte = _
  var bInfoExCanBeFreed:Byte = _
  @Length(3)
  var bnRes4:Array[Byte]= _			// reserved
  var nInfoExLen:Short = _
  @Length(8)
  var bnRes5:Array[Byte] = _
  @Length(32)
  var szCaseID:Array[Byte]= _	// this field is used for
  // dealing with mobile case solving situation.
  // if a case occurred in one small village, the police
  // goto there and take some latent fingers and tenprint
  // fingers of related person(the victim and other people)
  // and want to rule out latent fingers that belong to victim
  var stAdmData = new tagGTPCARDADMINFOSTRUCT()	
} // size of this structure is 384 bytes

class tagGATEXTITEMSTRUCT extends AncientData{
  @Length(32)
  var szItemName:String = _ 	// item name
  var	nItemLen:Int = _	// item length
  var	bIsPointer:Byte= _		// is pointer or not
  var	bIsNull:Byte = _		// no values
  var	bCanBeFree:Byte = _ 		// can be free
  var	nFlag:Byte = _		// 1 byte reserved, TEXTITEM_FLAG_XXX
  var stData:tagGATEXTPTSTRUCT = _	// 88 bytes data, or pointer
  @IgnoreTransfer
  var textContent:Array[Byte]= _
} 	// size of this structure is 128 bytes

// the following structure is used to satisfy variable need for text info
// only some very case we need to allocate pointer to date
//union	tagGATEXTPTSTRUCT
class tagGATEXTPTSTRUCT extends AncientData{
  @Length(88)
  var bnData:Array[Byte]= _
}
class tagGAFIS_TPADMININFO_EX  extends AncientData {
  var cbSize:Int = _ 
  var bnRes:Int = _ 
  @Length(16)
  var szOrgScanner:String = _ 
  @Length(16)
  var szOrgScanUnitCode:String = _ 
  var szOrgAFISType:Int = _ 		// fill in AFIS type code.
  var nRollDigitizeMethod:Byte = _ 
  var nTPlainDigitizeMethod:Byte = _ 
  var nPalmDigitizeMethod:Byte = _
  var nItemFlag:Byte = _				// TPINFO_EX_ITEMFLAG_xxx
  var tDigitizedTime:GafisDateTime = _ 	//<! 卡片被数字化的时间
  @Length(8)
  var bnRes0:Array[Byte]= _ 
  // to here is 64 bytes long.
  // following fields used for finger print transfer platform for GA. total 128 bytes long.
  var stFpx:tagGAFIS_FPX_STATUS = new tagGAFIS_FPX_STATUS 	// 128 bytes long.
  // above fields for finger transfer between heterogeneous AFIS systems.
  // to here is 128+64=184 bytes long.
  @Length(64)
  var bnResx:Array[Byte]= _
} // 256 bytes long.


// structure for finger print transfer for GA.
class tagGAFIS_FPX_STATUS  extends AncientData {
  var cbSize:Int = _ 
  var bnRes:Int = _ 
  var nFPXState:Byte = _       // TPLP_FPX_STATUS_
  var bFPXIsForeign:Byte = _    // 0 or 1
  var nFPXPurpose:Byte = _    // TPLP_FPX_PURPOSE_
  @Length(5)
  var bnFPXRes:Array[Byte]= _ 
  // to here is 16 bytes long.
  var nItemFlag:Byte = _  // FPX_ITEMFLAG_XXX, indicates which item has valid values.
  @Length(7)
  var bnRes1:Array[Byte]= _ 
  @Length(16)
  var szFPXForeignUnitCode:String = _ 
  @Length(24)
  var bnResx:String = _
  @Length(64)
  var bnResy:String = _
}  // 128 bytes long.



class tagGTPCARDADMINFOSTRUCT  extends AncientData {
  var cbSize:Int = _ 				// size of this structure
  @Length(32)
  var szPersonID:String = _ 			// person id, this value is used to identify the duplicate barcode
  @Length(32)
  var szMISPersonID:String = _ 		// mis person id
  @Length(16)
  var szCUserName:String = _ 		// user name of create this record
  @Length(16)
  var szMUserName:String = _		// user name of modify this record
  var tCDateTime:GafisDateTime = _		// create date time
  var tMDateTime:GafisDateTime = _		// modify date time
  @Length(12)
  var szScanCardConfigID:String = _ 	// scan card configuration id(may be a very short string)
  @Length(12)
  var szDispCardConfigID:String = _ 	// display configuration id(may be a very short string)
  var nItemFlag:Int = _ 			// TPADMIN_ITEMFLAG_0_XXX
  @Length(16)
  var bnUUID:String = _ 				// UUID, readonly
  var nAccuTLCount:Byte = _	// accumulate TL search count
  var nAccuTTCount:Byte = _	// accumulate TT search count
  var nTLCount:Byte = _		// TL search count after mnt was modified
  var nTTCount:Byte = _		// TT search count after mnt was modified
  @Length(16)
  var szPersonType:String = _ 	// we can store 7 Chinese characters
  var nGroupID:Int = _		// group id
  var nEditCount:Byte = _			// # of times edited
  var nPersonState:Byte = _		// TPPERSONSTATE_XXX, unknown, free, detain, escaped, dead,
  @Length(6)
  var bnRes:Array[Byte]= _ 
  var szGroupCode:Long = _		// belong to this group. has code table.
  @Length(6)
  var nSID:Array[Byte]=_		// sid, readonly
  @Length(2)
  var bnRes1:Array[Byte] = _
  var tSubmitTLDate:GafisDateTime = _
  var tSubmitTTDate:GafisDateTime = _
  @Length(16)
  var szTLUserName:String = _ 
  @Length(16)
  var szTTUserName:String = _ 
} 	// size of this structure is 256 bytes

/////////////////// data structure for latent

// the structure is used to store both finger and palm data
class tagGLPCARDINFOSTRUCT  extends AncientData {
  var cbSize:Int = _ 			// size of this structure
  var nTextItemCount:Short = _ 	// text item count
  var nMicItemCount:Byte = _ 		// can be 0 or 1
  var nItemFlag:Byte = _ 			// reserved, LPCARDINFO_ITEMFLAG_XXX
  @Length(32)
  var szCardID:String = _			// key  of this item in database
  var pstMIC:Long = _ 		// pointer to mic struct, can hold many items(because a lp can have many mnts).
  var pstText:Long = _	// lp card text info
  var pstExtraInfo:Long = _
  @IgnoreTransfer
  var pstExtraInfoData= new tagGAFIS_LP_EXTRAINFO()
  ////////////////// to here is 64 bytes//////////
  var bMicCanBeFreed:Byte = _		// whether pstMic can be freed
  var bTextCanBeFreed:Byte = _	// whether pstText can be freed
  //	UCHAR	bPalmCard;			// whether the structure stores palmdata
  var nCardType:Byte = _			// change bPalmCard to this value. LPCARDTYPE_XXX
  var bExtraInfoCanBeFreed:Byte = _
  var nExtraInfoLen:Short = _ 		// length of pstExtraInfo.
  @Length(10)
  var bnRes2:Array[Byte]= _ 				// reserved bytes.
  @Length(16)
  var szReChecker:String = _		// recheck user.
  @Length(16)
  var szBrokenUser:String = _		// broken user. store actual user name.
  @Length(16)
  var szBrokenUnitCode:String = _	// broken unit code.
  var	stAdmData:tagGLPCARDADMINFOSTRUCT = new tagGLPCARDADMINFOSTRUCT
} ;	// size of this structure is 384 bytes

// store info can not be hold by GLPCARDINFOSTRUCT.
class tagGAFIS_LP_EXTRAINFO  extends AncientData {
  var cbSize:Int = _ ;
  var bnRes:Int = _ ;
  @Length(8)
  var bnRes1:Array[Byte]= _ ;
  @Length(16)
  var szOrgScanner:String = _ ;
  @Length(16)
  var szOrgScanUnitCode:String = _;
  var szOrgAFISType:Int = _ ;		// fill in AFIS type code.
  var nDigitizeMethod:Byte = _;
  var nItemFlag:Byte = _;		// LP_EXTRAINFO_ITEMFLAG_xxx
  @Length(10)
  var bnRes3:Array[Byte] = _
  // to here is 64 bytes long.
  var stFpx = new tagGAFIS_FPX_STATUS		// 128 bytes long.
  @Length(32)
  var szGroupID:String = _
  @Length(32)
  var bnResx:Array[Byte] = _
} ;	// 256 bytes long.

class tagGLPCARDADMINFOSTRUCT  extends AncientData {
  var cbSize:Int = _ ;	// size of this structure
  @Length(32)
  var szPersonID:String = _			// all finger's with the same personid are identified as a person's same finger
  @Length(32)
  var szCaseID:String = _			// case id
  @Length(10)
  var nGuessedFingerIndex:String = _	// guessed finger index, if the correspond byte is non-zero, for palm using first two bytes
  var nItemFlag3:Short = _ 				// LPADMIN_ITEMFLAG3_0_XXX, to here is 80 bytes long.
  @Length(16)
  var szCUserName:String = _		// user name of create this record
  @Length(16)
  var szMUserName:String = _		// user name of modify this record, to here is 112 bytes
  var tCDateTime:GafisDateTime = _		// create date time
  var tMDateTime:GafisDateTime = _		// modify date time
  var nFgGroup:Byte = _
  var nFgIndex:Byte = _
  var nItemFlag:Byte = _		// LPADMIN_ITEMFLAG_XXX
  var nItemFlagEx:Byte = _	// LPADMIN_ITEMFLAGEX_XXX
  @Length(16)
  var bnUUID:String = _				// UUID, readonly
  var nAccuLTCount:Byte = _	// accumulate lt search count
  var nAccuLLCount:Byte = _	// accumulate ll search count
  var nLTCount:Byte = _		// lt search count after mnt was modified
  var nLLCount:Byte = _		// ll search count after mnt was modified
  @Length(16)
  var szFingerType:String = _	// we can store 7 Chinese characters
  var nGroupID:Int = _ 		// group id
  var nEditCount:Byte = _			// # of times edited
  var bIsBroken:Byte = _			// whether the lp finger has matched(szPersonID is the card id)
  var bIsLTBroken:Byte = _		// is LT or TL checked.
  @Length(9)
  var szBrokenDate:String = _		//
  @Length(8)
  var bnRes2:Array[Byte]= _
  //	AFISDateTime	tBrokenDate;		// broken date.
  var szGroupCode:Long = _ 		// belong to this group. has code table.
  @Length(6)
  var nSID:Array[Byte] = _ // sid, readonly
  var nHitPersonState:Byte = _	// person state of matched tp finger(lp or tl used)
  // this flag control the searching filter method
  // if the person has been captured, the the lp finger
  // may not be searched.
  // currently may be
  // unknown : 0,
  // non captured 1 ,
  // captured     2
  var bnRes3:Byte = _ ;
  var tSubmitLTDate:GafisDateTime = _ ;
  var tSubmitLLDate:GafisDateTime = _ ;
  @Length(16)
  var szLTUserName:String = _
  @Length(16)
  var szLLUserName:String = _
} 	// size of this structure is 256 bytes


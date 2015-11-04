package nirvana.hall.v62.internal

import nirvana.hall.v62.annotations.{IgnoreTransfer, Length}
import nirvana.hall.v62.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-03
 */
class CaseStruct {

}
// case info structure
class tagGCASEINFOSTRUCT  extends AncientData {
  var cbSize:Int = _ ;		// size of this structure
  var nGroupID:Int = _ ;	// group id
  @Length(32)
  var szCaseID:String = _	// case id
  var nFingerCount:Short = _ ;	// finger count in this case
  var nPalmCount:Short = _ ;		// palm count in this case
  var nTextItemCount:Short = _ ;	// text item count bytes
  var nItemFlag:Byte = _			// which item in this structure is used, GCIS_ITEMFLAG_XXX
  var nItemFlagEx:Byte = _		// GCIS_ITEMFLAGEX_XXX
  // to here is 48 bytes
  var pstFingerID:Long = _;	// pointer to finger id
  var pstPalmID:Long = _ ;		// pointer to palm id
  var pstText:Long = _;	// pointer to text
  @IgnoreTransfer
  var pstFingerIdData:Array[GAKEYSTRUCT] = _
  @IgnoreTransfer
  var pstPalmIdData:Array[GAKEYSTRUCT] = _
  @IgnoreTransfer
  var pstTextData:Array[tagGATEXTITEMSTRUCT]= _;	// pointer to text

  var	bFingerIDCanBeFreed:Byte = _
  var	bPalmIDCanBeFreed:Byte = _
  var	bTextCanBeFreed:Byte = _
  var	bIsBroken:Byte = _			// whether the case has been broken.
  var	bHasPersonKilled:Byte = _
  var	nPersonKilledCnt:Byte = _	// how many people killed.[0 255]
  var	bIsLTBroken:Byte = _
  var	bExtraInfoCanBeFreed:Byte = _	// 2006.08.04
  @Length(16)
  var	bnUUID:String = _		// UUID, readonly
  @Length(32)
  var	szMISCaseID:String = _
  var	nFingerIDLen:Int = _ 	// temp use
  var	nPalmIDLen:Int = _ 		// temp use
  @Length(6)
  var	nSID:Array[Byte] = _ 		// sid, readonly
  @Length(2)
  var bnRes4:Array[Byte] = _ ;			// ensure sid occupy 8 byte.
  //	AFISDateTime	tBrokenDate;
  var pstExtraInfo:Long = _
  @IgnoreTransfer
  var pstExtraInfoData:tagGAFIS_CASE_EXTRAINFO =  _
  @Length(16)
  var szBrokenUser:String = _		// broken user. store actual user name.
  @Length(16)
  var szReChecker:String = _
  @Length(16)
  var szBrokenUnitCode:String = _	// broken unit code.
  var szGroupCode:Long = _;			// group code.
  @Length(9)
  var szBrokenDate:String = _
  var 	nItemFlag3:Byte= _;				// GCIS_ITEMFLAG3_XXX
  var 	nExtraInfoLen:Short = _ 		// length of extra info.
  @Length(4)
  var 	bnRes7:Array[Byte] = _
  var tCreateDateTime:GafisDateTime = _
  var tUpdateDateTime:GafisDateTime = _
  @Length(16)
  var bnRes3:Array[Byte] = _
} // size of this structure is 256 bytes

class GAKEYSTRUCT extends AncientData {
  @Length(32)
  var key:String = _
}
// the data object for latent is simpler than tenprint
// there are only finger and palm data's

// for store face, voice and etc list.
class tagGAFIS_CASEITEMENTRY  extends AncientData {
  var	cbSize:Int = _
  var	bnRes:Int = _
  @Length(32)
  var	szKey:String = _
  @Length(16)
  var	szUserName:String = _
  @Length(16)
  var	szUnitCode:String = _
  var tDateTime:GafisDateTime = _
  // to here is 80 bytes long.
  var	nKeyType:Byte = _ 	// LPCARDTYPE_xxxx
  @Length(47)
  var	bnRes2:Array[Byte] = _
} ;	// 128 bytes long.

// the GCASEINFOSTRUCT can not hold enough data, we need expand
// it's size, but for compatibility reason, we add an extra
// structure to hold other info. 2006.08.04
class tagGAFIS_CASE_EXTRAINFO  extends AncientData {
  var	cbSize:Int = _
  var	bnRes:Int = _
  @Length(32)
  var	szCaseGroupID:String = _	// case group id.
  @Length(8)
  var	bnRes1:Array[Byte]= _
  // to here is 48 bytes long.
  var pstItemEntry:Long = _ 	// for store item key list(except lp finger and palm).
  @IgnoreTransfer
  var pstItemEntryData:tagGAFIS_CASEITEMENTRY= _
  @Length(24)
  var	bnRes2:Array[Byte] = _
  // to here is 48+32=80 bytes long. above buffer should be used for store pointer.
  @Length(16)
  var	szOrgScanner:String = _
  @Length(16)
  var	szOrgScanUnitCode:String = _
  var	szOrgAFISType:Int = _ 		// fill in AFIS type code.
  var	nItemFlag:Byte = _;				// CASE_EXTRAINFO_ITEMFLAG_XXX
  @Length(3)
  var	bnRes3:Array[Byte] = _
  var	nItemSize:Int = _ 		// length of pstItemEntry(count*sizeof(GAFIS_CASEITEMENTRY))
  // ease for loading and saving.
  @Length(4)
  var	bnRes4:Array[Byte] = _;
  // to here is 128 bytes long.
  var stFpx = new tagGAFIS_FPX_STATUS()	;	// 128 bytes long.
  ///
  //var	szMISConnectCaseID[32];	// mis connect id. has another szMISCaseID
  @Length(256)
  var	bnResx:Array[Byte]= _
} ;	// size is 512 bytes long.

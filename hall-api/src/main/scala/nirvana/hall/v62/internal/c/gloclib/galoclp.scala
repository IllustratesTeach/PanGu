package nirvana.hall.v62.internal.c.gloclib

import nirvana.hall.v62.annotations.{IgnoreTransfer, Length}
import nirvana.hall.v62.internal.c.gbaselib.gbasedef.GAKEYSTRUCT
import nirvana.hall.v62.internal.c.ghpcbase.ghpcdef.AFISDateTime
import nirvana.hall.v62.internal.c.ghpcbase.glocdef.GAFIS_FPX_STATUS
import nirvana.hall.v62.internal.c.gloclib.glocdef.{GAFISMICSTRUCT, GATEXTITEMSTRUCT}
import nirvana.hall.v62.internal.c._
import nirvana.hall.v62.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-07
 */
object galoclp {

  // the data object for latent is simpler than tenprint
  // there are only finger and palm data's

  // for store face, voice and etc list.
  class GAFIS_CASEITEMENTRY extends AncientData
  {
    var cbSize:Int = _ ;
    @Length(4)
    var bnRes:Array[Byte] = _ ;
    @Length(32)
    var szKey:String = _ ;
    @Length(16)
    var szUserName:String = _ ;
    @Length(16)
    var szUnitCode:String = _ ;
    var tDateTime = new AFISDateTime;
    // to here is 80 bytes long.
    var nKeyType:Byte = _ ;	// LPCARDTYPE_xxxx
  @Length(47)
  var bnRes2:Array[Byte] = _ ;
  } // GAFIS_CASEITEMENTRY;	// 128 bytes long.

  // the GCASEINFOSTRUCT can not hold enough data, we need expand
  // it's size, but for compatibility reason, we add an extra
  // structure to hold other info. 2006.08.04
  class GAFIS_CASE_EXTRAINFO extends AncientData
  {
    var cbSize:Int = _ ;
    @Length(4)
    var bnRes:Array[Byte] = _ ;
    @Length(32)
    var szCaseGroupID:String = _ ;	// case group id.
  @Length(8)
  var bnRes1:Array[Byte] = _ ;
    // to here is 48 bytes long.
    var pstItemEntry_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstItemEntry_Data:Array[GAFIS_CASEITEMENTRY] = _ // for pstItemEntry pointer ,struct:GAFIS_CASEITEMENTRY;	// for store item key list(except lp finger and palm).
  // so can store face and voice key list.
  @Length(4)
  var bnRes_ItemEntry:Array[Byte] = _ ;
    @Length(24)
    var bnRes2:Array[Byte] = _ ;
    // to here is 48+32=80 bytes long. above buffer should be used for store pointer.
    @Length(16)
    var szOrgScanner:String = _ ;
    @Length(16)
    var szOrgScanUnitCode:String = _ ;
    var szOrgAFISType:Int = _ ;		// fill in AFIS type code.
  var nItemFlag:Byte = _ ;				// CASE_EXTRAINFO_ITEMFLAG_XXX
  @Length(3)
  var bnRes3:Array[Byte] = _ ;
    var nItemSize:Int = _ ;		// length of pstItemEntry(count*sizeof(GAFIS_CASEITEMENTRY))
  // ease for loading and saving.
  @Length(4)
  var bnRes4:Array[Byte] = _ ;
    // to here is 128 bytes long.
    var stFpx = new GAFIS_FPX_STATUS;	// 128 bytes long.
  ///
  //UCHAR	szMISConnectCaseID[32];	// mis connect id. has another szMISCaseID
  @Length(256)
  var bnResx:Array[Byte] = _ ;
  } // GAFIS_CASE_EXTRAINFO;	// size is 512 bytes long.

  // GAFIS_CASE_EXTRAINFO::nItemFlag
  final val CASE_EXTRAINFO_ITEMFLAG_CASEGROUPID = 0x1
  final val CASE_EXTRAINFO_ITEMFLAG_ITEMENTRY = 0x2
  final val CASE_EXTRAINFO_ITEMFLAG_ORGSCANNER = 0x4
  final val CASE_EXTRAINFO_ITEMFLAG_ORGSCANUNIT = 0x8
  final val CASE_EXTRAINFO_ITEMFLAG_ORGAFISTYPE = 0x10
  final val CASE_EXTRAINFO_ITEMFLAG_MISCASEID = 0x20


  // case info structure
  class GCASEINFOSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;		// size of this structure
  var nGroupID:Int = _ ;	// group id
  @Length(32)
  var szCaseID:String = _ ;	// case id
  var nFingerCount:Short = _ ;	// finger count in this case
  var nPalmCount:Short = _ ;		// palm count in this case
  var nTextItemCount:Short = _ ;	// text item count bytes
  var nItemFlag:Byte = _ ;			// which item in this structure is used, GCIS_ITEMFLAG_XXX
  var nItemFlagEx:Byte = _ ;		// GCIS_ITEMFLAGEX_XXX
  // to here is 48 bytes
  var pstFingerID_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstFingerID_Data:Array[GAKEYSTRUCT] = _ // for pstFingerID pointer ,struct:GAKEYSTRUCT;	// pointer to finger id
  var pstPalmID_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstPalmID_Data:Array[GAKEYSTRUCT] = _ // for pstPalmID pointer ,struct:GAKEYSTRUCT;		// pointer to palm id
  var pstText_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstText_Data:Array[GATEXTITEMSTRUCT] = _ // for pstText pointer ,struct:GATEXTITEMSTRUCT;	// pointer to text
  @Length(3*4)
  var bnRes_AllPt:Array[Byte] = _ ;
    var bFingerIDCanBeFreed:Byte = _ ;
    var bPalmIDCanBeFreed:Byte = _ ;
    var bTextCanBeFreed:Byte = _ ;
    var bIsBroken:Byte = _ ;			// whether the case has been broken.
  var bHasPersonKilled:Byte = _ ;
    var nPersonKilledCnt:Byte = _ ;	// how many people killed.[0 255]
  var bIsLTBroken:Byte = _ ;
    var bExtraInfoCanBeFreed:Byte = _ ;	// 2006.08.04
  @Length(16)
  var bnUUID:Array[Byte] = _ ;		// UUID, readonly
  @Length(32)
  var szMISCaseID:String = _ ;
    var nFingerIDLen:Int = _ ;	// temp use
  var nPalmIDLen:Int = _ ;		// temp use
  @Length(SID_SIZE)
  var nSID:String = _ ;		// sid, readonly
  /*
  @Length(2)
  var bnRes_SID:Array[Byte] = _ ;
  */
    @Length(2)
    var bnRes4:Array[Byte] = _ ;			// ensure sid occupy 8 byte.
  //	AFISDateTime	tBrokenDate;
  var pstExtraInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstExtraInfo_Data:Array[GAFIS_CASE_EXTRAINFO] = _ // for pstExtraInfo pointer ,struct:GAFIS_CASE_EXTRAINFO;
  @Length(4)
  var bnRes_ExtraInfo:Array[Byte] = _ ;
    @Length(16)
    var szBrokenUser:String = _ ;		// broken user. store actual user name.
  @Length(16)
  var szReChecker:String = _ ;
    @Length(16)
    var szBrokenUnitCode:String = _ ;	// broken unit code.
  var szGroupCode:Long = _ ;			// group code.
  @Length(9)
  var szBrokenDate:String = _ ;
    var nItemFlag3:Byte = _ ;				// GCIS_ITEMFLAG3_XXX
  //
  var nExtraInfoLen:Short = _ ;		// length of extra info.
  @Length(4)
  var bnRes7:Array[Byte] = _ ;
    var tCreateDateTime = new AFISDateTime;
    var tUpdateDateTime = new AFISDateTime;
    @Length(16)
    var bnRes3:Array[Byte] = _ ;
  } // GCASEINFOSTRUCT;	// size of this structure is 256 bytes

  // GCASEINFOSTRUCT::nItemFlag
  final val GCIS_ITEMFLAG_FINGERCOUNT = 0x1
  final val GCIS_ITEMFLAG_PALMCOUNT = 0x2
  final val GCIS_ITEMFLAG_FINGERID = 0x4
  final val GCIS_ITEMFLAG_PALMID = 0x8
  final val GCIS_ITEMFLAG_TEXT = 0x10
  final val GCIS_ITEMFLAG_MISCASEID = 0x20
  final val GCIS_ITEMFLAG_GROUPID = 0x40
  final val GCIS_ITEMFLAG_ADMDATA = 0x80

  // GCASEINFOSTRUCT::nItemFlagEx
  final val GCIS_ITEMFLAGEX_EXTRAINFO = 0x1
  final val GCIS_ITEMFLAGEX_ISBROKEN = 0x2
  final val GCIS_ITEMFLAGEX_HASPERSONKILLED = 0x4
  final val GCIS_ITEMFLAGEX_PERSONKILLEDCNT = 0x8
  final val GCIS_ITEMFLAGEX_ISLTBROKEN = 0x10
  final val GCIS_ITEMFLAGEX_BROKENUSER = 0x20
  final val GCIS_ITEMFLAGEX_RECHECKER = 0x40
  final val GCIS_ITEMFLAGEX_SINGLEITEM = 0x80	// 如果此标志为真，则标明，我们存储的时候不使用
  // GCIS_ITEMFLAG_ADMDATA，而是使用其他的独立的标志。

  // GCASEINFOSTRUCT::nItemFlag3
  final val GCIS_ITEMFLAG3_BROKENUNITCODE = 0x1
  final val GCIS_ITEMFLAG3_GROUPCODE = 0x2
  final val GCIS_ITEMFLAG3_BROKENDATE = 0x4
  final val GCIS_ITEMFLAG3_CREATEDATETIME = 0x8
  final val GCIS_ITEMFLAG3_UPDATEDATETIME = 0x10

  /*
  // commented by xcg on Oct. 4 2006
  // structure for latent finger image and palm image
  class GLATMICSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;			// size of this structure
    @Length(4)
    var bnRes:Array[Byte] = _ ;			// not used
    @Length(32)
    var szID:String = _ ;			// key  of this item in database
    var pstMic_Ptr:Int = _ //using 4 byte as pointer
    @IgnoreTransfer
    var pstMic_Data:Array[GAFISMICSTRUCT] = _ // for pstMic pointer ,struct:GAFISMICSTRUCT;	// pointer to mic struct
    @Length(4)
    var bnRes_pstMic:Array[Byte] = _ ;
    @Length(16)
    var bnRes2:Array[Byte] = _ ;			// 16 bytes reserved
  } // GLATMICSTRUCT;	// size of this structure is 64 bytes, this structure is not used
  */

  class GLPCARDADMINFOSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure
  @Length(32)
  var szPersonID:String = _ ;			// all finger's with the same personid are identified as a person's same finger
  @Length(32)
  var szCaseID:String = _ ;			// case id
  @Length(10)
  var nGuessedFingerIndex:String = _ ;	// guessed finger index, if the correspond byte is non-zero, for palm using first two bytes
  var nItemFlag3:Short = _ ;				// LPADMIN_ITEMFLAG3_0_XXX, to here is 80 bytes long.
  @Length(16)
  var szCUserName:String = _ ;		// user name of create this record
  @Length(16)
  var szMUserName:String = _ ;		// user name of modify this record, to here is 112 bytes
  var tCDateTime = new AFISDateTime;		// create date time
  var tMDateTime = new AFISDateTime;		// modify date time
  var nFgGroup:Byte = _ ;
    var nFgIndex:Byte = _ ;
    var nItemFlag:Byte = _ ;		// LPADMIN_ITEMFLAG_XXX
  var nItemFlagEx:Byte = _ ;	// LPADMIN_ITEMFLAGEX_XXX
  @Length(16)
  var bnUUID:Array[Byte] = _ ;				// UUID, readonly
  var nAccuLTCount:Byte = _ ;	// accumulate lt search count
  var nAccuLLCount:Byte = _ ;	// accumulate ll search count
  var nLTCount:Byte = _ ;		// lt search count after mnt was modified
  var nLLCount:Byte = _ ;		// ll search count after mnt was modified
  @Length(16)
  var szFingerType:String = _ ;	// we can store 7 Chinese characters
  var nGroupID:Int = _ ;		// group id
  var nEditCount:Byte = _ ;			// # of times edited
  var bIsBroken:Byte = _ ;			// whether the lp finger has matched(szPersonID is the card id)
  var bIsLTBroken:Byte = _ ;		// is LT or TL checked.
  @Length(9)
  var szBrokenDate:String = _ ;		//
  @Length(8)
  var bnRes2:Array[Byte] = _ ;
    //	AFISDateTime	tBrokenDate;		// broken date.
    var szGroupCode:Long = _ ;		// belong to this group. has code table.
  @Length(SID_SIZE)
  var nSID:String = _ ;		// sid, readonly
  @Length(2)
  var bnRes_SID:Array[Byte] = _ ;
    var nHitPersonState:Byte = _ ;	// person state of matched tp finger(lp or tl used)
  // this flag control the searching filter method
  // if the person has been captured, the the lp finger
  // may not be searched.
  // currently may be
  // unknown : 0,
  // non captured 1 ,
  // captured     2
  @Length(1)
  var bnRes3:Array[Byte] = _ ;
    var tSubmitLTDate = new AFISDateTime;
    var tSubmitLLDate = new AFISDateTime;
    @Length(16)
    var szLTUserName:String = _ ;
    @Length(16)
    var szLLUserName:String = _ ;
  } // GLPCARDADMINFOSTRUCT;		// size of this structure is 256 bytes

  // GLPCARDADMINFOSTRUCT::nItemFlag
  final val LPADMIN_ITEMFLAG_FGGROUP = 0x1
  final val LPADMIN_ITEMFLAG_FGINDEX = 0x2
  final val LPADMIN_ITEMFLAG_CASEID = 0x8
  final val LPADMIN_ITEMFLAG_GUESSEDFI = 0x10	// nGuessedFingerIndex
  final val LPADMIN_ITEMFLAG_CUSERNAME = 0x20
  final val LPADMIN_ITEMFLAG_MUSERNAME = 0x40
  final val LPADMIN_ITEMFLAG_EDITCOUNT = 0x80

  // GLPCARDADMINFOSTRUCT::nItemFlagEx
  final val LPADMIN_ITEMFLAGEX_CDATETIME = 0x1
  final val LPADMIN_ITEMFLAGEX_MDATETIME = 0x2
  final val LPADMIN_ITEMFLAGEX_ACCULTCOUNT = 0x4
  final val LPADMIN_ITEMFLAGEX_ACCULLCOUNT = 0x8
  final val LPADMIN_ITEMFLAGEX_LTCOUNT = 0x10	// nGuessedFingerIndex
  final val LPADMIN_ITEMFLAGEX_LLCOUNT = 0x20
  final val LPADMIN_ITEMFLAGEX_FINGERTYPE = 0x40
  final val LPADMIN_ITEMFLAGEX_GROUPID = 0x80

  // GLPCARDADMINFOSTRUCT::nItemFlag3[0]
  final val LPADMIN_ITEMFLAG3_0_ISBROKEN = 0x1
  final val LPADMIN_ITEMFLAG3_0_ISLTBROKEN = 0x2
  final val LPADMIN_ITEMFLAG3_0_BROKENDATE = 0x4
  final val LPADMIN_ITEMFLAG3_0_GROUPCODE = 0x8
  final val LPADMIN_ITEMFLAG3_0_HITPERSONSTATE = 0x10	// nGuessedFingerIndex
  final val LPADMIN_ITEMFLAG3_0_SUBMITLTDATE = 0x20
  final val LPADMIN_ITEMFLAG3_0_SUBMITLLDATE = 0x40

  // GLPCARDADMINFOSTRUCT::nItemFlag3[1]
  final val LPADMIN_ITEMFLAG3_1_LTUSERNAME = 0x1
  final val LPADMIN_ITEMFLAG3_1_LLUSERNAME = 0x2
  final val LPADMIN_ITEMFLAG3_1_PERSONID = 0x4

  // store info can not be hold by GLPCARDINFOSTRUCT.
  class GAFIS_LP_EXTRAINFO extends AncientData
  {
    var cbSize:Int = _ ;
    @Length(4)
    var bnRes:Array[Byte] = _ ;
    @Length(8)
    var bnRes1:Array[Byte] = _ ;
    @Length(16)
    var szOrgScanner:String = _ ;
    @Length(16)
    var szOrgScanUnitCode:String = _ ;
    var szOrgAFISType:Int = _ ;		// fill in AFIS type code.
  var nDigitizeMethod:Byte = _ ;
    var nItemFlag:Byte = _ ;		// LP_EXTRAINFO_ITEMFLAG_xxx
  @Length(10)
  var bnRes3:Array[Byte] = _ ;
    // to here is 64 bytes long.
    var stFpx = new GAFIS_FPX_STATUS;	// 128 bytes long.
  @Length(32)
  var szGroupID:String = _ ;
    @Length(32)
    var bnResx:Array[Byte] = _ ;
  } // GAFIS_LP_EXTRAINFO;	// 256 bytes long.

  // GAFIS_LP_EXTRAINFO::nItemFlag
  final val LP_EXTRAINFO_ITEMFLAG_ORGSCANNER = 0x1
  final val LP_EXTRAINFO_ITEMFLAG_ORGSCANUNIT = 0x2
  final val LP_EXTRAINFO_ITEMFLAG_ORGAFISTYPE = 0x4
  final val LP_EXTRAINFO_ITEMFLAG_DIGITIZEMETHOD = 0x8
  final val LP_EXTRAINFO_ITEMFLAG_GROUPID = 0x10

  // lp record can most have this # of different minutia.
  final val LP_MAX_MICCNT = 9

  // the structure is used to store both finger and palm data
  class GLPCARDINFOSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;			// size of this structure
  var nTextItemCount:Short = _ ;	// text item count
  var nMicItemCount:Byte = _ ;		// can be 0 or 1
  var nItemFlag:Byte = _ ;			// reserved, LPCARDINFO_ITEMFLAG_XXX
  @Length(32)
  var szCardID:String = _ ;			// key  of this item in database
  var pstMIC_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstMIC_Data:Array[GAFISMICSTRUCT] = _ // for pstMIC pointer ,struct:GAFISMICSTRUCT;		// pointer to mic struct, can hold many items(because a lp can have many mnts).
  var pstText_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstText_Data:Array[GATEXTITEMSTRUCT] = _ // for pstText pointer ,struct:GATEXTITEMSTRUCT;	// lp card text info
  @Length(2*4)
  var bnRes_Pointer:Array[Byte] = _ ;
    var pstExtraInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstExtraInfo_Data:Array[GAFIS_LP_EXTRAINFO] = _ // for pstExtraInfo pointer ,struct:GAFIS_LP_EXTRAINFO;
  @Length(4)
  var bnRes_ExtraInfo:Array[Byte] = _ ;
    ////////////////// to here is 64 bytes//////////
    var bMicCanBeFreed:Byte = _ ;		// whether pstMic can be freed
  var bTextCanBeFreed:Byte = _ ;	// whether pstText can be freed
  //	UCHAR	bPalmCard;			// whether the structure stores palmdata
  var nCardType:Byte = _ ;			// change bPalmCard to this value. LPCARDTYPE_XXX
  var bExtraInfoCanBeFreed:Byte = _ ;
    var nExtraInfoLen:Short = _ ;		// length of pstExtraInfo.
  @Length(2)
  var bnRes2:Array[Byte] = _ ;				// reserved bytes.
  @Length(8)
  var bnRes3:Array[Byte] = _ ;
    @Length(16)
    var szReChecker:String = _ ;		// recheck user.
  @Length(16)
  var szBrokenUser:String = _ ;		// broken user. store actual user name.
  @Length(16)
  var szBrokenUnitCode:String = _ ;	// broken unit code.
  var stAdmData = new GLPCARDADMINFOSTRUCT;
  } // GLPCARDINFOSTRUCT;	// size of this structure is 384 bytes

  // GLPCARDINFOSTRUCT::nItemFlag
  final val LPCARDINFO_ITEMFLAG_ADMDATA = 0x1
  final val LPCARDINFO_ITEMFLAG_EXTRAINFO = 0x2
  final val LPCARDINFO_ITEMFLAG_SINGLEITEM = 0x4	// 如果此标志为真，在此结构中的其他项目都要逐项检查。
  final val LPCARDINFO_ITEMFLAG_RECHECKER = 0x8
  final val LPCARDINFO_ITEMFLAG_BROKENUSER = 0x10
  final val LPCARDINFO_ITEMFLAG_BROKENUNITCODE = 0x20

  // FOLLOWING LPCARDTYPE_ MACRO added on sep. 20, 2005
  // GLPCARDINFOSTRUCT::nCardType
  final val LPCARDTYPE_FINGER = 0x0
  final val LPCARDTYPE_PALM = 0x1
  final val LPCARDTYPE_FACE = 0x2
  final val LPCARDTYPE_VOICE = 0x3

  // we submit some simple condition to retrieve
  class GCASERETRSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;		// size of this structure
  @Length(44)
  var szKeyWild:String = _ ;	// wild key, 44 bytes
  var tLastEditTime = new AFISDateTime;	// last edit time
  var tAddTime = new AFISDateTime;		// add time
  var nMaxToGet:Int = _ ;	// at most at this values of records
  var nItemFlag:Byte = _ ;		// indicate which item to use, GCASERETR_ITEMFLAG_XXX
  var nFlag:Byte = _ ;			// flag. GCASERETR_FLAG_XXX
  @Length(2)
  var bnRes:Array[Byte] = _ ;		// bytes reserved
  var nStartSID:Int = _ ;
    var nEndSID:Int = _ ;
    var tEndEditTime = new AFISDateTime;
    var tEndAddTime = new AFISDateTime;
    @Length(32)
    var szUserNameWild:String = _ ;
  } // GCASERETRSTRUCT;	// size is 128 bytes

  //GCASERETRSTRUCT::nItemFlag
  final val GCASERETR_ITEMFLAG_KEY = 0x1
  final val GCASERETR_ITEMFLAG_ADDTIME = 0x2
  final val GCASERETR_ITEMFLAG_EDITTIME = 0x4
  final val GCASERETR_ITEMFLAG_MAXGET = 0x8
  final val GCASERETR_ITEMFLAG_NAMEWILD = 0x10

  // GCASERETRSTRUCT::nFlag
  final val GCASERETR_FLAG_CREATORNAME = 0x1	// szUserNameWild is creator.

  ///////////////////////////////////////ADD ON OCT. 4, 2006

  class GAFIS_LPGROUPENTRY extends AncientData
  {
    var cbSize:Int = _ ;
    @Length(4)
    var bnRes:Array[Byte] = _ ;
    @Length(32)
    var szKey:String = _ ;
    var nKeyType:Byte = _ ;	// LPCARDTYPE_
  var nFgGroup:Byte = _ ;	// the finger may belong to same person, but not same finger.
  // but if nFgGroup is same then finger belong to one person's same finger.
  var nFgIndex:Byte = _ ;	// global finger index. if we know the exact finger.
  var nFlag:Byte = _ ;		// LPGROUPENTRY_FLAG_XXX
  @Length(4)
  var bnRes2:Array[Byte] = _ ;
    // to here is 48 bytes long.
    @Length(16)
    var szUserName:String = _ ;	// who add this key to this group.
  @Length(16)
  var szUnitCode:String = _ ;	// unit code
  var tDateTime = new AFISDateTime;		// when this key is added to this group.
  // to here is 88 bytes long.
  @Length(40)
  var bnRes3:Array[Byte] = _ ;
  } // GAFIS_LPGROUPENTRY;	// 128 bytes long.

  class GAFIS_LPGROUPSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;
    @Length(4)
    var bnRes:Array[Byte] = _ ;
    //
    var pstKeyList_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstKeyList_Data:Array[GAFIS_LPGROUPENTRY] = _ // for pstKeyList pointer ,struct:GAFIS_LPGROUPENTRY;
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;
    // to here is 16 bytes long.
    var nKeyListLength:Int = _ ;	// in bytes.
  var bKeyListCanBeFree:Byte = _ ;
    var nFlag:Byte = _ ;		// LPGROUP_FLAG_XX
  @Length(1)
  var bnRes1:Array[Byte] = _ ;
    var nItemFlag:Byte = _ ;	// LPGROUP_ITEMFLAG_XXX
  @Length(SID_SIZE)
  var nSID:String = _ ;
    @Length(8-SID_SIZE)
    var bnRes_SID:Array[Byte] = _ ;
    // to here is 32 bytes long.
    @Length(16)
    var szCreateUserName:String = _ ;
    @Length(16)
    var szCreateUnitCode:String = _ ;
    @Length(16)
    var szUpdateUserName:String = _ ;
    @Length(16)
    var szUpdateUnitCode:String = _ ;
    var tCreateDateTime = new AFISDateTime;
    var tUpdateDateTime = new AFISDateTime;
    // to here is 80+32=112 bytes long.
    @Length(16)
    var bnRes3:Array[Byte] = _ ;
    // to here is 128 bytes long.
    @Length(32)
    var szGroupID:String = _ ;
    @Length(96)
    var bnResx:Array[Byte] = _ ;
  } // GAFIS_LPGROUPSTRUCT;	// 256 bytes long.

  // GAFIS_LPGROUPSTRUCT :: nItemFlag
  final val LPGROUP_ITEMFLAG_KEYLIST = 0x1
  final val LPGROUP_ITEMFLAG_UPDATEUNITCODE = 0x2
  final val LPGROUP_ITEMFLAG_FLAG = 0x4


  // GAFIS_LPGROUPSTRUCT::nFlag
  final val LPGROUP_FLAG_MULTITPGROUP = 0x1	// see GPIS_FLAG_MULTILPGROUP.
  final val LPGROUP_FLAG_SAMETPGROUPID = 0x2	// see GPIS_FLAG_SAMELPGROUPID

  class GAFIS_CASEGROUPENTRY extends AncientData
  {
    var cbSize:Int = _ ;
    @Length(4)
    var bnRes:Array[Byte] = _ ;
    @Length(32)
    var szKey:String = _ ;
    var nFlag:Byte = _ ;		// CASEGROUPENTRY_FLAG_XXX
  @Length(7)
  var bnRes2:Array[Byte] = _ ;
    // to here is 48 bytes long.
    @Length(16)
    var szUserName:String = _ ;	// who add this key to this group.
  @Length(16)
  var szUnitCode:String = _ ;	// unit code
  var tDateTime = new AFISDateTime;		// when this key is added to this group.
  // to here is 88 bytes long.
  @Length(40)
  var bnRes3:Array[Byte] = _ ;
  } // GAFIS_CASEGROUPENTRY;	// 128 bytes long.

  class GAFIS_CASEGROUPSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;
    @Length(4)
    var bnRes:Array[Byte] = _ ;
    //
    var pstKeyList_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstKeyList_Data:Array[GAFIS_CASEGROUPENTRY] = _ // for pstKeyList pointer ,struct:GAFIS_CASEGROUPENTRY;
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;
    // to here is 16 bytes long.
    var nKeyListLength:Int = _ ;	// in bytes.
  var bKeyListCanBeFree:Byte = _ ;
    @Length(2)
    var bnRes1:Array[Byte] = _ ;
    var nItemFlag:Byte = _ ;	// CASEGROUP_ITEMFLAG_XXX
  @Length(SID_SIZE)
  var nSID:String = _ ;
    @Length(8-SID_SIZE)
    var bnRes_SID:Array[Byte] = _ ;
    // to here is 32 bytes long.
    @Length(16)
    var szCreateUserName:String = _ ;
    @Length(16)
    var szCreateUnitCode:String = _ ;
    @Length(16)
    var szUpdateUserName:String = _ ;
    @Length(16)
    var szUpdateUnitCode:String = _ ;
    var tCreateDateTime = new AFISDateTime;
    var tUpdateDateTime = new AFISDateTime;
    // to here is 80+32=112 bytes long.
    @Length(16)
    var bnRes3:Array[Byte] = _ ;
    // to here is 128 bytes long.
    @Length(32)
    var szGroupID:String = _ ;
    @Length(96)
    var bnResx:Array[Byte] = _ ;
  } // GAFIS_CASEGROUPSTRUCT;	// 256 bytes long.


  // GAFIS_CASEGROUPSTRUCT :: nItemFlag
  final val CASEGROUP_ITEMFLAG_KEYLIST = 0x1
  final val CASEGROUP_ITEMFLAG_UPDATEUNITCODE = 0x2



  // GAFIS_LPCARD_Update::nOption
  final val LPCARD_UPDATEOPT_EXF = 0x1	// exf called update.

  //////////// functions declared in glocfh.h

}

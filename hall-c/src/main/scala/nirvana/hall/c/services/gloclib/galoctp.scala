package nirvana.hall.c.services.gloclib

import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.ghpcbase.ghpcdef.AFISDateTime
import nirvana.hall.c.services.ghpcbase.glocdef.GAFIS_FPX_STATUS
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services._
import nirvana.hall.c.services.gloclib.glocdef._

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-07
 */
object galoctp {


  class GCARDITEMOBJECT extends AncientData
  {
    var nItemType:Byte = _ ;		// item type, finger, palm, plain finger, face, card img area, text, GTPIO_ITEMTYPE_xx, GLPIO_ITEMTYPE_XX
  var nItemIndex:Byte = _ ;		// for finger is 1 to 10, for palm is 1(R) 2(L) AND 3-8 face(1:f, 2:l,3:r)
  var nItemOption:Byte = _ ;	// for finger or palm use only, GTPIO_ITEMOPTION_
  var nItemExist:Byte = _ ;		// same type as nItemOption.
  } // GCARDITEMOBJECT;

  final val GTPIO_ITEMTYPE_FINGER = GAMIC_ITEMTYPE_FINGER		// 0x1 [1, 10]
  final val GTPIO_ITEMTYPE_PALM = GAMIC_ITEMTYPE_PALM			// 0x2 [1, 8], best for [1, 2] other part set to HANDPART.
  final val GTPIO_ITEMTYPE_PLAINFINGER = GAMIC_ITEMTYPE_PLAINFINGER	// 0x3 [1, 4]
  final val GTPIO_ITEMTYPE_FACE = GAMIC_ITEMTYPE_FACE			// 0x4 [1, 3]
  final val GTPIO_ITEMTYPE_CARDIMG = GAMIC_ITEMTYPE_DATA			// 0x5 [1, 8]
  final val GTPIO_ITEMTYPE_TEXT = 0x6
  final val GTPIO_ITEMTYPE_SIGNATURE = GAMIC_ITEMTYPE_SIGNATURE	// 0x7 [1, 1]
  final val GTPIO_ITEMTYPE_TPLAIN = GAMIC_ITEMTYPE_TPLAIN		// 0x8	// plain finger used to do searching., [1, 10]
  final val GTPIO_ITEMTYPE_EXTRAFINGER = GAMIC_ITEMTYPE_EXTRAFINGER	// extra finger.[1, 1]
  final val GTPIO_ITEMTYPE_HANDPART = GAMIC_ITEMTYPE_HANDPART		// 0xa[2 10]
  final val GTPIO_ITEMTYPE_VOICE = GAMIC_ITEMTYPE_VOICE		// 0xb[1-4]

  // nItemOption have the following values
  final val GTPIO_ITEMOPTION_MNT = 0x1	// get mnt
  final val GTPIO_ITEMOPTION_IMG = 0x2	// get img
  final val GTPIO_ITEMOPTION_CPR = 0x4	// get cpr
  final val GTPIO_ITEMOPTION_BIN = 0x8	// get bin


  // if nItemType==GTPIO_ITEMTYPE_FACE, then nItemIndex can have the following values
  final val GTPIO_ITEMINDEX_FACEFRONT = 0x1
  final val GTPIO_ITEMINDEX_FACENOSELEFT = 0x2
  final val GTPIO_ITEMINDEX_FACENOSERIGHT = 0x3
  // 3 other items on June 1st, we want to add face and voice recognition into afis system.
  final val GTPIO_ITEMINDEX_FACE1 = 0x4
  final val GTPIO_ITEMINDEX_FACE2 = 0x5
  final val GTPIO_ITEMINDEX_FACE3 = 0x6

  // if nItemType==GTPIO_ITEMTYPE_VOICE then nItemIndex can have the following values.
  // one user can have at most 4 segments of sound voice.
  final val GTPIO_ITEMINDEX_VOICE1 = 0x1
  final val GTPIO_ITEMINDEX_VOICE2 = 0x2
  final val GTPIO_ITEMINDEX_VOICE3 = 0x3
  final val GTPIO_ITEMINDEX_VOICE4 = 0x4

  final val GLPIO_ITEMTYPE_FINGER = 0x1
  final val GLPIO_ITEMTYPE_PALM = 0x2
  final val GLPIO_ITEMTYPE_TEXT = 0x6
  final val GLPIO_ITEMTYPE_FACE = GAMIC_ITEMTYPE_FACE
  final val GLPIO_ITEMTYPE_VOICE = GAMIC_ITEMTYPE_VOICE

  // if nItemType==GTPIO_ITEMTYPE_PALM then nItemIndex can be the following values
  // nItemIndex-1 is the index in name array g_stCN.stTPm
  final val GTPIO_ITEMINDEX_PALM_RIGHT = 0x1
  final val GTPIO_ITEMINDEX_PALM_LEFT = 0x2
  final val GTPIO_ITEMINDEX_PALM_RFINGER = 0x3
  final val GTPIO_ITEMINDEX_PALM_LFINGER = 0x4
  final val GTPIO_ITEMINDEX_PALM_RTHUMBLOW = 0x5
  final val GTPIO_ITEMINDEX_PALM_RTHUMBUP = 0x6
  final val GTPIO_ITEMINDEX_PALM_LTHUMBLOW = 0x7
  final val GTPIO_ITEMINDEX_PALM_LTHUMBUP = 0x8
  final val GTPIO_ITEMINDEX_PALM_RWRITER = 0x9	// 9		// 右侧掌.
  final val GTPIO_ITEMINDEX_PALM_LWRITER = 0xa	// 10		// 左侧掌
  final val GTPIO_ITEMINDEX_PALM_WHOLERIGHT = 0xb	// 11		// not stored.
  final val GTPIO_ITEMINDEX_PALM_WHOLELEFT = 0xc	// 12		// not stored.

  final val GTPIO_ITEMINDEX_FINGER_RTHUMB = 1
  final val GTPIO_ITEMINDEX_FINGER_RFORE = 2
  final val GTPIO_ITEMINDEX_FINGER_RMIDDLE = 3
  final val GTPIO_ITEMINDEX_FINGER_RRING = 4
  final val GTPIO_ITEMINDEX_FINGER_RLITTL = 5
  final val GTPIO_ITEMINDEX_FINGER_LTHUMB = 6
  final val GTPIO_ITEMINDEX_FINGER_LFORE = 7
  final val GTPIO_ITEMINDEX_FINGER_LMIDDLE = 8
  final val GTPIO_ITEMINDEX_FINGER_LRING = 9
  final val GTPIO_ITEMINDEX_FINGER_LLITTL = 10
  //
  final val GTPIO_ITEMINDEX_SIGNATURE_CRIMINAL = 0x1
  final val GTPIO_ITEMINDEX_SIGNATURE_PRINTER = 0x2


  final val GTPIO_ITEMINDEX_PLAINFINGER_RTHUMB = 1
  final val GTPIO_ITEMINDEX_PLAINFINGER_RFOUR = 2
  final val GTPIO_ITEMINDEX_PLAINFINGER_LTHUMB = 3
  final val GTPIO_ITEMINDEX_PLAINFINGER_LFOUR = 4
  final val GTPIO_ITEMINDEX_PLAINFINGER_TWOTHUMBS = 5

  // nItemOption have the following values
  final val GLPIO_ITEMOPTION_MNT = 0x1	// get mnt
  final val GLPIO_ITEMOPTION_IMG = 0x2	// get img
  final val GLPIO_ITEMOPTION_CPR = 0x4	// get cpr
  final val GLPIO_ITEMOPTION_BIN = 0x8	// get bin
  final val GLPIO_ITEMOPTION_TEXT = 0x10// get text

  class GTPCARDADMINFOSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;				// size of this structure
  @Length(32)
  var szPersonID:String = _ ;			// person id, this value is used to identify the duplicate barcode
  @Length(32)
  var szMISPersonID:String = _ ;		// mis person id
  @Length(16)
  var szCUserName:String = _ ;		// user name of create this record
  @Length(16)
  var szMUserName:String = _ ;		// user name of modify this record
  var tCDateTime = new AFISDateTime;		// create date time
  var tMDateTime = new AFISDateTime;		// modify date time
  @Length(12)
  var szScanCardConfigID:String = _ ;	// scan card configuration id(may be a very short string)
  @Length(12)
  var szDispCardConfigID:String = _ ;	// display configuration id(may be a very short string)
  var nItemFlag:Int = _ ;			// TPADMIN_ITEMFLAG_0_XXX
  @Length(16)
  var bnUUID:Array[Byte] = _ ;				// UUID, readonly
  var nAccuTLCount:Byte = _ ;	// accumulate TL search count
  var nAccuTTCount:Byte = _ ;	// accumulate TT search count
  var nTLCount:Byte = _ ;		// TL search count after mnt was modified
  var nTTCount:Byte = _ ;		// TT search count after mnt was modified
  @Length(16)
  var szPersonType:String = _ ;	// we can store 7 Chinese characters
  var nGroupID:Int = _ ;		// group id
  var nEditCount:Byte = _ ;			// # of times edited
  var nPersonState:Byte = _ ;		// TPPERSONSTATE_XXX, unknown, free, detain, escaped, dead,
  @Length(6)
  var bnRes:Array[Byte] = _ ;
    var szGroupCode:Long = _ ;		// belong to this group. has code table.
  @Length(SID_SIZE)
  var nSID:String = _ ;		// sid, readonly
    @Length(2)
    var bnRes1:Array[Byte] = _ ;
    var tSubmitTLDate = new AFISDateTime;
    var tSubmitTTDate = new AFISDateTime;
    @Length(16)
    var szTLUserName:String = _ ;
    @Length(16)
    var szTTUserName:String = _ ;
  } // GTPCARDADMINFOSTRUCT;	// size of this structure is 256 bytes

  /**
   * 与人员分类、状态相关的几个字段
   *	1、PersonClass：被捺印指纹人员类别，按照公安部标准GA 777.4-2010填写，保存的是代码，数据类型为字符串
   *	2、PersonState：被捺印指纹人员状态，这些状态值是根据业务要求自定义的，用户可修改该状态，数据类型为微整型
   *	3、PersonType： 该字段在2009年4月8日以前一直没被使用过，其数据类型为字符串，目前只在重庆采集
   *					活体指纹时使用，主要用在通讯服务器上报卡片时判断是否自动发送TT、TL查询。
   *					从2011年5月3日起，该字段将填写“被捺印指纹人员在采集指纹时的状态”，
   */

  /**
   * Added by beagle on Apr. 8, 2009
   * szPersonType到目前为止一直没有被使用过，结合重庆活体需求(被采集人员分为嫌疑人和资料人员)，
   * 可以把该字段用来区分被采集人员的类别，然后通讯服务器在上报的时候，根据人员类别来判断是否
   * 自动发送TT、TL查询
   */
  // GTPCARDADMINFOSTRUCT::szPersonType[16]
  final val TPPERSONTYPE_CRIMINAL = "0"		// 前科人员，如果szPersonType为空，也缺省认为是一个前科人员
  final val TPPERSONTYPE_INFOMATION = "1"		// 资料人员，目前对这类人员只是上报数据，禁止自动发送TT、TL查询(只是重庆的情况下是这样的）
  final val TPPERSONTYPE_SECURITYGAUARD = "2"		// 保安员申请人，目前对这类人员只是上报数据，禁止自动发送TT、TL查询(只是重庆的情况下是这样的）
  final val TPPERSONTYPE_TERRORIST = "3"		// 涉恐人员（新疆指纹识别系统新需求）

  // GTPCARDADMINFOSTRUCT::nItemFlag[0]
  final val TPADMIN_ITEMFLAG_0_PERSONID = 0x1
  final val TPADMIN_ITEMFLAG_0_MISPERSONID = 0x2
  final val TPADMIN_ITEMFLAG_0_CUSERNAME = 0x4
  final val TPADMIN_ITEMFLAG_0_MUSERNAME = 0x8
  final val TPADMIN_ITEMFLAG_0_CDATETIME = 0x10
  final val TPADMIN_ITEMFLAG_0_MDATETIME = 0x20
  final val TPADMIN_ITEMFLAG_0_SCCID = 0x40	// szScanCardConfigID
  final val TPADMIN_ITEMFLAG_0_DCCID = 0x80	// szDispCardConfigID

  // GTPCARDADMINFOSTRUCT::nItemFlag[1]
  final val TPADMIN_ITEMFLAG_1_ACCUTLCOUNT = 0x2
  final val TPADMIN_ITEMFLAG_1_ACCUTTCOUNT = 0x4
  final val TPADMIN_ITEMFLAG_1_TLCOUNT = 0x8
  final val TPADMIN_ITEMFLAG_1_TTCOUNT = 0x10
  final val TPADMIN_ITEMFLAG_1_PERSONTYPE = 0x20
  final val TPADMIN_ITEMFLAG_1_GROUPID = 0x40
  final val TPADMIN_ITEMFLAG_1_EDITCOUNT = 0x80

  // GTPCARDADMINFOSTRUCT::nItemFlag[2]
  final val TPADMIN_ITEMFLAG_2_PERSONSTATE = 0x1
  final val TPADMIN_ITEMFLAG_2_GROUPCODE = 0x2
  final val TPADMIN_ITEMFLAG_2_SUBMITTLDATE = 0x8
  final val TPADMIN_ITEMFLAG_2_SUBMITTTDATE = 0x10
  final val TPADMIN_ITEMFLAG_2_TLUSERNAME = 0x20
  final val TPADMIN_ITEMFLAG_2_TTUSERNAME = 0x40


  class GAFIS_TPADMININFO_EX extends AncientData
  {
    var cbSize:Int = _ ;
    @Length(4)
    var bnRes:Array[Byte] = _ ;
    @Length(16)
    var szOrgScanner:String = _ ;
    @Length(16)
    var szOrgScanUnitCode:String = _ ;
    var szOrgAFISType:Int = _ ;		// fill in AFIS type code.
  //
  var nRollDigitizeMethod:Byte = _ ;
    var nTPlainDigitizeMethod:Byte = _ ;
    var nPalmDigitizeMethod:Byte = _ ;
    var nItemFlag:Byte = _ ;				// TPINFO_EX_ITEMFLAG_xxx
  var tDigitizedTime = new AFISDateTime;	//<! 卡片被数字化的时间
  @Length(8)
  var bnRes0:Array[Byte] = _ ;
    // to here is 64 bytes long.
    // following fields used for finger print transfer platform for GA. total 128 bytes long.
    var stFpx = new GAFIS_FPX_STATUS;	// 128 bytes long.
  // above fields for finger transfer between heterogeneous AFIS systems.
  // to here is 128+64=184 bytes long.
  @Length(64)
  var bnResx:Array[Byte] = _ ;
  } // GAFIS_TPADMININFO_EX;	// 256 bytes long.

  // GAFIS_TPCARDINFO_EX::nItemFlag
  final val TPINFO_EX_ITEMFLAG_ORGSCANNER = 0x1
  final val TPINFO_EX_ITEMFLAG_ORGSCANUNITCODE = 0x2
  final val TPINFO_EX_ITEMFLAG_ORGAFISTYPE = 0x4
  final val TPINFO_EX_ITEMFLAG_ROLLDIGIMETHOD = 0x8
  final val TPINFO_EX_ITEMFLAG_TPLAINDIGIMETHOD = 0x10
  final val TPINFO_EX_ITEMFLAG_PALMDIGIMETHOD = 0x20
  final val TPINFO_EX_ITEMFLAG_DIGITIZEDTIME = 0x40

  //GTPCARDADMINFOSTRUCT::nPersonState
  final val TPPERSONSTATE_UNKNOWN = 0
  final val TPPERSONSTATE_FREE = 1
  final val TPPERSONSTATE_DETAIN = 2
  final val TPPERSONSTATE_ESCAPED = 3
  final val TPPERSONSTATE_DEAD = 4

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

  class GTPCARDINFOSTRUCT extends AncientData
  {
    var cbSize:Int = 384 ;				// size of this structure 4 bytes int
  var nMicItemCount:Byte = _ ;			// count of structure of GAFISMICSTRUCT  1 byte int
  var nItemFlag:Byte = _ ;				// which item is used, TPCARDINFO_ITEMFLAG_XXX
  var nTextItemCount:Short = _ ;		// text item count
  @Length(32)
  var szCardID:String = _ ;			// card id, key words(to here is 40 bytes)
  //////////////to here is 40 bytes//////////////
  @Length(4)
  var bnRes:Array[Byte] = _ ;				// reserved
  @Length(4)
  var bnRes2:Array[Byte] = _ ;				// 4 bytes reserved, to here is 48 bytes
  ///////////////to here is 48 bytes
  var pstMIC_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstMIC_Data:Array[GAFISMICSTRUCT] = _ // for pstMIC pointer ,struct:GAFISMICSTRUCT;	// pointer to micstruct(may not have minutia)
  var pstText_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstText_Data:Array[GATEXTITEMSTRUCT] = _ // for pstText pointer ,struct:GATEXTITEMSTRUCT;	// pointer to text, the structure is GATEXTITEMSTRUCT.
  @Length(2*4)
  var bnRes_AllPt:Array[Byte] = _ ;	// 8 bytes reserved
  var pstInfoEx_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstInfoEx_Data:GAFIS_TPADMININFO_EX = _ // for pstInfoEx pointer ,struct:GAFIS_TPADMININFO_EX;
  @Length(4)
  var bnRes_InfoEx:Array[Byte] = _ ;
    @Length(8)
    var bnRes3:Array[Byte] = _ ;			// 8 bytes reserved, to here is 80 bytes
  ////////////////to here is 80 bytes
  var bMicCanBeFreed:Byte = _ ;
    var bTextCanBeFreed:Byte = _ ;
    var bInfoExCanBeFreed:Byte = _ ;
    @Length(3)
    var bnRes4:Array[Byte] = _ ;			// reserved
  var nInfoExLen:Short = _ ;
    @Length(8)
    var bnRes5:Array[Byte] = _ ;
    @Length(32)
    var szCaseID:String = _ ;	// this field is used for
  // dealing with mobile case solving situation.
  // if a case occurred in one small village, the police
  // goto there and take some latent fingers and tenprint
  // fingers of related person(the victim and other people)
  // and want to rule out latent fingers that belong to victim
  var stAdmData = new GTPCARDADMINFOSTRUCT;
  } // GTPCARDINFOSTRUCT;	// size of this structure is 384 bytes

  // GTPCARDINFOSTRUCT::nItemFlag
  final val TPCARDINFO_ITEMFLAG_ADMDATA = 0x1
  final val TPCARDINFO_ITEMFLAG_INFOEX = 0x2
  final val TPCARDINFO_ITEMFLAG_SINGLEITEM = 0x4	// 是否支持任何数据项更新
  final val TPCARDINFO_ITEMFLAG_CASEID = 0x8

  // person info is actually duplicated card info
  class GAFIS_DUPCARDSTRUCT extends AncientData
  {
    @Length(32)
    var szCardID:String = _ ;		// card id
  @Length(16)
  var szUserName:String = _ ;		// user that identifies two cards of the same
  var tCheckTime = new AFISDateTime;	// time that identifies that two cards are the same
  @Length(72)
  var szComment:String = _ ;
  } // GAFIS_DUPCARDSTRUCT;	// 128 bytes long.

  // person info structure
  class GPERSONINFOSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;		// size of this structure
  var nFlag:Byte = _ ;			// record flag. GPIS_FLAG_XXX
  @Length(1)
  var bnRes0:Array[Byte] = _ ;		// reserved count
  var bIDCanBeFreed:Byte = _ ;
    var bTextCanBeFreed:Byte = _ ;// text can be freed
  @Length(32)
  var szPersonID:String = _ ;	// person id
  var pstID_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstID_Data:Array[GAFIS_DUPCARDSTRUCT] = _ // for pstID pointer ,struct:GAFIS_DUPCARDSTRUCT;	// pointer to card id and other things
  @Length(4)
  var bnRes_pstCard:Array[Byte] = _ ;	// 4 bytes reserved
  var pstText_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstText_Data:Array[GATEXTITEMSTRUCT] = _ // for pstText pointer ,struct:GATEXTITEMSTRUCT;	// pointer to text
  @Length(4)
  var bnRes_pstText:Array[Byte] = _ ;
    var nTextItemCount:Short = _ ;	// text item count
  var nItemFlag:Byte = _ ;			// indicates which item is used, GPIS_ITEMFLAG_xxx
  var nItemFlag2:Byte = _ ;			// indicates which item is used. GPIS_ITEMFLAG2_XXX
  var nCardCount:Int = _ ;		// card count.
  var nIDDataLen:Int = _ ;		// for inner use only
  @Length(SID_SIZE)
  var nSID:String = _ ;			// SID of this record
  @Length(8-SID_SIZE)
  var bnRes_SID:Array[Byte] = _ ;
    // following 2 items are added on Sep. 5, 2006.
    var nLPGroupDBID:Short = _ ;
    var nLPGroupTID:Short = _ ;
    var tCreateDateTime = new AFISDateTime;
    var tUpdateDateTime = new AFISDateTime;
    @Length(16)
    var szCreatorName:String = _ ;
    @Length(16)
    var szUpdatorName:String = _ ;
  } // GPERSONINFOSTRUCT;	// size of this structure is 128 bytes

  // GPERSONINFOSTRUCT::nItemFlag
  final val GPIS_ITEMFLAG_CARDCOUNT = 0x1
  final val GPIS_ITEMFLAG_CARDID = 0x2
  final val GPIS_ITEMFLAG_TEXT = 0x4
  final val GPIS_ITEMFLAG_CREATEDATETIME = 0x10
  final val GPIS_ITEMFLAG_UPDATEDATETIME = 0x20
  final val GPIS_ITEMFLAG_CREATORNAME = 0x40
  final val GPIS_ITEMFLAG_UPDATORNAME = 0x80

  // GPERSONINFOSTRUCT::nItemFlag2
  final val GPIS_ITEMFLAG2_LPGROUPDBID = 0x1
  final val GPIS_ITEMFLAG2_LPGROUPTID = 0x2
  final val GPIS_ITEMFLAG2_FLAG = 0x4


  // GPERSONINFOSTRUCT::nFlag 2006.09.10
  final val GPIS_FLAG_MULTILPGROUP = 0x1	// there has more than one latent group
  // connected with this person.
  // the latent group may belong to different
  // database.
  final val GPIS_FLAG_SAMELPGROUPID = 0x2	// if on person associates with more than
  // one latent group, those latent group
  // may have different groupid, but if this
  // set then all the latent group have same
  // group id. so need not to retrieve all
  // group id from administration lib.

  // we submit some simple condition to retrieve
  class GTPLPRETRSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;		// size of this structure
  @Length(44)
  var szKeyWild:String = _ ;	// wild key, 44 bytes
  var tLastEditTime = new AFISDateTime;	// last edit time
  var tAddTime = new AFISDateTime;		// add time
  var nMaxToGet:Int = _ ;	// at most at this values of records
  var nItemFlag:Byte = _ ;		// indicate which item to use, GTLRETR_ITEMFLAG_XXX
  var bUserIsCreator:Byte = _ ;	// user name wild is creator
  @Length(2)
  var bnRes:Array[Byte] = _ ;		// bytes reserved
  var nStartSID:Int = _ ;
    var nEndSID:Int = _ ;
    var tEndLastEditTime = new AFISDateTime;
    var tEndAddTime = new AFISDateTime;
    @Length(32)
    var szUserNameWild:String = _ ;	// create user or modifier
  } // GTPLPRETRSTRUCT;	// size is 128 bytes

  final val GTLRETR_ITEMFLAG_KEYWILD = 0x1
  final val GTLRETR_ITEMFLAG_ADDTIME = 0x2
  final val GTLRETR_ITEMFLAG_LASTEDITTIME = 0x4
  final val GTLRETR_ITEMFLAG_MAXTOGET = 0x8
  final val GTLRETR_ITEMFLAG_NAMEWILD = 0x10

  // key retr structure
  type GKEYRETRSTRUCT = GTPLPRETRSTRUCT

  // we submit some simple condition to retrieve
  class GPERSONRETRSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;		// size of this structure
  @Length(44)
  var szKeyWild:String = _ ;	// wild key, 44 bytes
  var tLastEditTime = new AFISDateTime;	// last edit time
  var tAddTime = new AFISDateTime;		// add time
  var nMaxToGet:Int = _ ;	// at most at this values of records
  var nItemFlag:Byte = _ ;		// indicate which item to use, GPERSONRETR_ITEMFLAG_XXX
  @Length(23)
  var bnRes:Array[Byte] = _ ;		// bytes reserved
  @Length(32)
  var szUserNameWild:String = _ ;
  } // GPERSONRETRSTRUCT;	// size is 128 bytes

  final val GPERSONRETR_ITEMFLAG_KEYWILD = 0x1
  final val GPERSONRETR_ITEMFLAG_ADDTIME = 0x2
  final val GPERSONRETR_ITEMFLAG_LASTEDITTIME = 0x4
  final val GPERSONRETR_ITEMFLAG_MAXTOGET = 0x8
  final val GPERSONRETR_ITEMFLAG_NAMEWILD = 0x10


  class GADB_MICSTREAMNAMESTRUCT extends AncientData
  {
    var pszMICName_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMICName_Data:String = "$$MIC" // for pszMICName pointer ,struct:char;
  var pszItemFlag_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszItemFlag_Data:String = "$$ItemFlag" // for pszItemFlag pointer ,struct:char;	// first byte Flag, second Type, third Data, fourth bIsLatent
  var pszItemType_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszItemType_Data:String = "$$ItemType" // for pszItemType pointer ,struct:char;
  var pszItemData_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszItemData_Data:String = "$$ItemData" // for pszItemData pointer ,struct:char;
  var pszIsLatent_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszIsLatent_Data:String = "$$IsLatent" // for pszIsLatent pointer ,struct:char;
  var pszMntName_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszMntName_Data:String = "$$Mnt" // for pszMntName pointer ,struct:char;
  var pszImgName_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszImgName_Data:String = "$$Img" // for pszImgName pointer ,struct:char;
  var pszCprName_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszCprName_Data:String = "$$Cpr" // for pszCprName pointer ,struct:char;
  var pszBinName_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszBinName_Data:String = "$$Bin" // for pszBinName pointer ,struct:char;
  var pszItemIndex_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszItemIndex_Data:String = "$$ItemIndex" // for pszItemIndex pointer ,struct:char;
  } // GADB_MICSTREAMNAMESTRUCT;

  // GAFIS_TPCARD_Update::nOption
  final val TPCARD_UPDATEOPT_EXF = 0x1	// exf called update.
  /**
   * 增加更新捺印数据时把数据添加到处理队列的操作选项
   */
  final val TPCARD_UPDATEOPT_ADDTOEXFQUE = 0x2
  final val TPCARD_UPDATEOPT_USECPR = 0x4
  final val TPCARD_UPDATEOPT_NOCOMPRESS = 0x8
  final val TPCARD_UPDATEOPT_SKIPTEXTINPUTQUE = 0x10
  final val TPCARD_UPDATEOPT_EXTFACEMNT = 0x20	//!< 提取人像特征


  ///////// function declared in glocfh.h
}

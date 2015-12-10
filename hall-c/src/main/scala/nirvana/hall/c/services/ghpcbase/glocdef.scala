package nirvana.hall.c.services.ghpcbase

import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.ghpcbase.ghpcdef.AFISDateTime
import nirvana.hall.c.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-07
 */
object glocdef {

  final val GA_TRUE = 1
  final val GA_FALSE = 0

  final val SID_SIZE = 6	// to set sid size to 6


  // the following structure represents a general image's head, we
  // may apply many methods to it
  class GAFISIMAGEHEADSTRUCT extends AncientData
  {
    var nSize:Int = _ ;			// size of this structure, 4 bytes int
  var nWidth:Short = _ ;			// width of the image, 2 bytes int
  var nHeight:Short = _ ;			// height of the image, 2 bytes int
  var nBits:Byte = _ ;				// bit per pixel, 8, 16, 24, no pallete, 1 byte
  var bIsCompressed:Byte = _ ;		// whether is compressed, 1 byte
  var nCompressMethod:Byte = _ ;	// compressed method, if compressed, 1 byte, GAIMG_CPRMETHOD_XXX
  var nCaptureMethod:Byte = _ ;		// image capture method GA_IMGCAPTYPE_XXX, 1 byte
  var nImgSize:Int = _ ;		// size of image, does not include head size, 4 bytes int
  var nResolution:Short = _ ;		// resolution, 2 bytes int
  var bIsPlain:Byte = _ ;			// plain type or non plain type
  var nImageType:Byte = _ ;			// GAIMG_IMAGETYPE_XXX
  var nFingerIndex:Byte = _ ;		// used only by exf.
  var nFlag:Byte = _ ;				// flag, reserved.
  var nSignature:Short = _ ;		// somes times the user submit and query and then delete the original
  // image and replace with a new one but keep the old key,
  // so when identifying whether hit the result, the user
  // got a totally different image for original minutia.
  // and we add a signature to reduce the conflict.
  var nQualDesc:Byte = _ ;			// quality description. GAFIS_QUALDESC_xxx
  var nMntFormat:Byte = _ ;			//!< 特征数据格式，标记了特征提取算法，值为GAFIS_MNTFORMAT_xxx
  @Length(6)
  var bnRes2:Array[Byte] = _ ;			// reserved
  @Length(32)
  var szName:String = _ ;			// image name
  } // GAFISIMAGEHEADSTRUCT;			// total length of this structure is 64 bytes

  /*
   * comment on compress method:
   * the compressed image data is stored in bnData part, and the data can be compressed or
   * uncomrpessed by many methods, so which functions call should we call? so we now
   * use nCompressMethod to do this work
   * if nCompressMethod is zero, then we call routines supplied by XGW, and if not zero
   * we call corresponding routines.
   */

  final val GAIMG_CPRMETHOD_DEFAULT = 0	// by xgw supplied method
  final val GAIMG_CPRMETHOD_XGW = 1	// by xgw supplied method.
  final val GAIMG_CPRMETHOD_XGW_EZW = 2	// 许公望的EZW压缩方法：适合低倍率高保真的压缩.
  final val GAIMG_CPRMETHOD_GA10 = 10	// 公安部10倍压缩方法
  final val GAIMG_CPRMETHOD_GFS = 19	// golden
  final val GAIMG_CPRMETHOD_PKU = 13	// call pku's compress method
  final val GAIMG_CPRMETHOD_COGENT = 101	// cogent compress method
  final val GAIMG_CPRMETHOD_WSQ = 102	// was method
  final val GAIMG_CPRMETHOD_NEC = 103	// nec compress method
  final val GAIMG_CPRMETHOD_TSINGHUA = 104	// tsing hua
  final val GAIMG_CPRMETHOD_BUPT = 105	// beijing university of posts and telecommunications
  final val GAIMG_CPRMETHOD_RMTZIP = 106	// compressmethod provide by communication server(GAFIS)
  final val GAIMG_CPRMETHOD_LCW = 107	// compress method provide by lucas wang.
  final val GAIMG_CPRMETHOD_JPG = 108	// jpeg method.

  final val GAIMG_IMAGETYPE_UNKNOWN = 0x0
  final val GAIMG_IMAGETYPE_FINGER = 0x1
  final val GAIMG_IMAGETYPE_PALM = 0x2
  final val GAIMG_IMAGETYPE_FACE = 0x3
  final val GAIMG_IMAGETYPE_CARDIMG = 0x4
  final val GAIMG_IMAGETYPE_VOICE = 0x5	// voice, view it as image.

  final val GA_IMGCAPTYPE_SCANCARD = 0x1
  final val GA_IMGCAPTYPE_LIVESCAN = 0x2
  final val GA_IMGCAPTYPE_CPRGEN = 0x3	// image is generated from decompress of cpr data, on
  // which cpr format we can refer to nCompressMethod flag
  // originally, for uncompressed image, this flag is not
  // used


  // GAFISIMAGEHEADSTRUCT::nQualDesc
  final val GAFIS_QUALDESC_NORMAL = 0	// normal image, quality description not set.
  final val GAFIS_QUALDESC_SCAR = 1	// scar
  final val GAFIS_QUALDESC_DESQUAMATE = 2	// skin come off. Tuo Pi
  final val GAFIS_QUALDESC_OTHER = 9	// other reason not previous one.

  // the following structure represents a general image
  class GAFISIMAGESTRUCT extends AncientData
  {
    var stHead = new GAFISIMAGEHEADSTRUCT;	// image head structure
  @Length(8)
  var bnData:Array[Byte] = _ ;	// image followed
  } // GAFISIMAGESTRUCT;	// size of this structure depends on the image size(32-2GB)

  // the following structure represents a image which has four parts:
  // minutiae, some binary data(blurred area, etc.) compressed format
  // and uncompressed format. in afis system
  // only finger and palm has these data.
  // if an item is indicate exists(GAMIC_ITEMFLAG_XX), but the corresponding pointer is null
  // the on update the old item will be deleted
  class GAFISMICSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;		// size of this structure
  var nItemFlag:Byte = _ ;		// which item is used, GAMIC_ITEMFLAG_XXX, where xxx is MNT, BIN, IMG OR CPR
  var nItemType:Byte = _ ;		// type, palm or finger, GAMIC_ITEMTYPE_FINGER, GAMIC_ITEMTYPE_PALM, FACE, DATA
  var nItemData:Byte = _ ;		// if is finger, then it's finger index(1-10), if it's palm it's 1(right)2(left)
  // exactly for palm its GTPIO_ITEMINDEX_PALM_XXXX
  var bIsLatent:Byte = _ ;		// is latent or not, do not use this flag
  var nBinLen:Int = _ ;		// binary data length
  var nMntLen:Int = _ ;		// minutia length
  var nImgLen:Int = _ ;		// image length
  var nCprLen:Int = _ ;		// to here is 24 bytes(included)
  var bMntCanBeFreed:Byte = _ ;
    var bImgCanBeFreed:Byte = _ ;
    var bCprCanBeFreed:Byte = _ ;
    var bBinCanBeFreed:Byte = _ ;
    var nIndex:Byte = _ ;
    @Length(3)
    var bnRes2:Array[Byte] = _ ;		// 3 bytes reserved(to here is 32 bytes)

    var pstMnt_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstMnt_Data:Array[GAFISIMAGESTRUCT] = _ // for pstMnt pointer ,struct:GAFISIMAGESTRUCT;			// pointer to mnt, pstMnt->bnData is the actual minutia
  @Length(4)
  var bnRes_pstMnt:Array[Byte] = _ ;
    var pstImg_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstImg_Data:Array[GAFISIMAGESTRUCT] = _ // for pstImg pointer ,struct:GAFISIMAGESTRUCT;	// pointer to image
  @Length(4)
  var bnRes_pstImg:Array[Byte] = _ ;
    var pstCpr_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstCpr_Data:Array[GAFISIMAGESTRUCT] = _ // for pstCpr pointer ,struct:GAFISIMAGESTRUCT;	// pointer to compressed image
  @Length(4)
  var bnRes_pstCpr:Array[Byte] = _ ;
    var pstBin_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstBin_Data:Array[GAFISIMAGESTRUCT] = _ // for pstBin pointer ,struct:GAFISIMAGESTRUCT;	// pointer to binary data
  @Length(4)
  var bnRes_pstBin:Array[Byte] = _ ;
  } // GAFISMICSTRUCT;		// size of this structure is 64 bytes

  // can be or'ed
  final val GAMIC_ITEMFLAG_MNT = 0x1
  final val GAMIC_ITEMFLAG_IMG = 0x2
  final val GAMIC_ITEMFLAG_CPR = 0x4
  final val GAMIC_ITEMFLAG_BIN = 0x8

  // cann't be or'ed
  final val GAMIC_ITEMTYPE_FINGER = 0x1	// rolled finger.
  final val GAMIC_ITEMTYPE_PALM = 0x2	// palm part.
  final val GAMIC_ITEMTYPE_PLAINFINGER = 0x3	// plain finger using for information store.
  final val GAMIC_ITEMTYPE_FACE = 0x4	// ITEMDATA:1-FRONT, 2 noseleftSIDE, 3 noserightSIDE, GTPIO_ITEMINDEX_XXX
  final val GAMIC_ITEMTYPE_DATA = 0x5	// card bin data.
  // 0x6 is not used, it's reserved for text, refer to GTPIO_ITEMTYPE
  final val GAMIC_ITEMTYPE_SIGNATURE = 0x7
  final val GAMIC_ITEMTYPE_TPLAIN = 0x8	// plain finger, PLAINFINGER is used to store data.
  // and TPLAIN is used to do search
  final val GAMIC_ITEMTYPE_EXTRAFINGER = 0x9	// six finger
  final val GAMIC_ITEMTYPE_HANDPART = 0xa	// hand part except palm
  final val GAMIC_ITEMTYPE_VOICE = 0xb


  // the following structure is used to satisfy variable need for text info
  // only some very case we need to allocate pointer to date
  class GATEXTPTSTRUCT extends AncientData
  {
    @Length(88)
    var data:Array[Byte] = _
    /*
    UCHAR	bnData[88];
    void	*pData;
    */
  } //GATEXTPTSTRUCT;

  class GATEXTITEMSTRUCT extends AncientData
  {
    @Length(32)
    var szItemName:String = _ ;	// item name
  var nItemLen:Int = _ ;	// item length
  var bIsPointer:Byte = _ ;		// is pointer or not
  var bIsNull:Byte = _ ;		// no values
  var bCanBeFree:Byte = _ ;		// can be free
  var nFlag:Byte = _ ;		// 1 byte reserved, TEXTITEM_FLAG_XXX
  var stData = new GATEXTPTSTRUCT;	// 88 bytes data, or pointer
  } // GATEXTITEMSTRUCT;	// size of this structure is 128 bytes

  final val TEXTITEM_FLAG_TEXTISDEFAULT = 0x0
  final val TEXTITEM_FLAG_TEXTISCODE = 0x1
  final val TEXTITEM_FLAG_TEXTISTEXT = 0x2


  final val FLIBADDOPT_ADDTOEXFQUE = 0x1
  final val FLIBADDOPT_USECPR = 0x2
  final val FLIBADDOPT_NOCOMPRESS = 0x4
  final val FLIBADDOPT_SKIPTEXTINPUTQUE = 0x8	// 2007.01.25, skip textinput que.
  final val FLIBADDOPT_EXTFACEMNT = 0x10	//!< 提取人像特征

  final val FLIBGETOPT_GETADMDATA = 0x1
  final val FLIBGETOPT_GETINFOEX = 0x2

  final val GAFISCPR_METHOD_GENERAL = GAIMG_CPRMETHOD_DEFAULT
  final val GAFISCPR_METHOD_XGW = GAIMG_CPRMETHOD_XGW
  final val GAFISCPR_METHOD_WSQ = GAIMG_CPRMETHOD_WSQ


  final val GAFIS_DATATYPE_TPFINGER = 1
  final val GAFIS_DATATYPE_TPPALM = 2
  final val GAFIS_DATATYPE_LATFINGER = 3
  final val GAFIS_DATATYPE_LATPALM = 4


  //tpcard info
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
  final val GTPIO_ITEMINDEX_PALM_WHOLERIGHT = 0x9	// 9		// not stored.
  final val GTPIO_ITEMINDEX_PALM_WHOLELEFT = 0xa	// 10		// not stored.

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
  var bnRes_SID:Array[Byte] = _ ;
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
   * Added by beagle on Apr. 8, 2009
   * szPersonType到目前为止一直没有被使用过，结合重庆活体需求(被采集人员分为嫌疑人和资料人员)，
   * 可以把该字段用来区分被采集人员的类别，然后通讯服务器在上报的时候，根据人员类别来判断是否
   * 自动发送TT、TL查询
   */
  // GTPCARDADMINFOSTRUCT::szPersonType[16]
  final val TPPERSONTYPE_CRIMINAL = "0"		// 前科人员，如果szPersonType为空，也缺省认为是一个前科人员
  final val TPPERSONTYPE_INFOMATION = "1"		// 资料人员，目前对这类人员只是上报数据，禁止自动发送TT、TL查询

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


  // structure for finger print transfer for GA.
  class GAFIS_FPX_STATUS extends AncientData
  {
    var cbSize:Int = _ ;
    @Length(4)
    var bnRes:Array[Byte] = _ ;
    var nFPXState:Byte = _ ;			// TPLP_FPX_STATUS_
  var bFPXIsForeign:Byte = _ ;		// 0 or 1
  var nFPXPurpose:Byte = _ ;		// TPLP_FPX_PURPOSE_
  @Length(5)
  var bnFPXRes:Array[Byte] = _ ;
    // to here is 16 bytes long.
    var nItemFlag:Byte = _ ;	// FPX_ITEMFLAG_XXX, indicates which item has valid values.
  @Length(7)
  var bnRes1:Array[Byte] = _ ;
    @Length(16)
    var szFPXForeignUnitCode:String = _ ;
    @Length(24)
    var bnResx:Array[Byte] = _ ;
    @Length(64)
    var bnResy:Array[Byte] = _ ;
  } // GAFIS_FPX_STATUS;	// 128 bytes long.

  // GAFIS_FPX_STATUS::nItemFlag
  final val FPX_ITEMFLAG_STATE = 0x1
  final val FPX_ITEMFLAG_ISFOREIGN = 0x2
  final val FPX_ITEMFLAG_PURPOSE = 0x4
  final val FPX_ITEMFLAG_FOREIGNUNITCODE = 0x8

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
    var cbSize:Int = _ ;				// size of this structure 4 bytes int
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
  var pstInfoEx_Data:Array[GAFIS_TPADMININFO_EX] = _ // for pstInfoEx pointer ,struct:GAFIS_TPADMININFO_EX;
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


}

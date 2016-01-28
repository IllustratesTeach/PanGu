package nirvana.hall.c.services.gloclib

import java.nio.charset.Charset

import nirvana.hall.c.AncientConstants
import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.AncientData.{StreamReader, StreamWriter}
import nirvana.hall.c.services.ghpcbase.ghpcdef.GAFIS_UUIDStruct
import nirvana.hall.c.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-06
 */
object glocdef {

      final val GA_TRUE = 1
      final val GA_FALSE = 0



      // the following structure represents a general image's head, we
      // may apply many methods to it
      class GAFISIMAGEHEADSTRUCT extends AncientData
      {
      var nSize:Int = 64 ;			// size of this structure, 4 bytes int
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


      final val GAIMG_FLAG_WHITERIDGE = 0x01	// the ridge's color in the image is white if set this flag

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
      final val GAIMG_CPRMETHOD_MORPHO = 109	//!< 广东测试时提供的压缩算法，MORPHO(SAGEM)
        final val GAIMG_CPRMETHOD_MAXVALUE = 109

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
          @IgnoreTransfer
        var bnData:Array[Byte] = _ ;	// image followed
          /**
           * serialize to channel buffer
           * @param stream netty channel buffer
           */
          override def writeToStreamWriter[T](stream: T)(implicit converter: (T) => StreamWriter): T = {
            if(bnData == null)
              throw new IllegalStateException("bnData is null")
            if(bnData.length != stHead.nImgSize)
              throw new IllegalArgumentException("bnData's length (%s) != head's nImgSize(%s)".format(bnData.length,stHead.nImgSize))
            super.writeToStreamWriter(stream)
            if(bnData != null)
              stream.writeBytes(bnData)

            stream
          }

          /**
           * convert channel buffer data as object
           * @param dataSource netty channel buffer
           */
          override def fromStreamReader(dataSource: StreamReader,encoding:Charset=AncientConstants.UTF8_ENCODING): this.type = {
            super.fromStreamReader(dataSource,encoding)
            if(stHead.nImgSize > 0)
              bnData = readBytesFromStreamReader(dataSource,stHead.nImgSize)

            this
          }

          /**
           * calculate data size and return.
           * @return data size
           */
          override def getDataSize: Int = {
            stHead.getDataSize + bnData.length
          }
        } // GAFISIMAGESTRUCT;	// size of this structure depends on the image size(32-2GB)

        // the following structure represents a image which has four parts:
        // minutiae, some binary data(blurred area, etc.) compressed format
        // and uncompressed format. in afis system
        // only finger and palm has these data.
        // if an item is indicate exists(GAMIC_ITEMFLAG_XX), but the corresponding pointer is null
        // the on update the old item will be deleted
        class GAFISMICSTRUCT extends AncientData
        {
        var cbSize:Int = 64 ;		// size of this structure
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
        var pstMnt_Data:Array[Byte] = _;			// pointer to mnt, pstMnt->bnData is the actual minutia
        @Length(4)
        var bnRes_pstMnt:Array[Byte] = _ ;
        var pstImg_Ptr:Int = _ //using 4 byte as pointer
        @IgnoreTransfer
        var pstImg_Data:Array[Byte] = _;	// pointer to image
        @Length(4)
        var bnRes_pstImg:Array[Byte] = _ ;
        var pstCpr_Ptr:Int = _ //using 4 byte as pointer
        @IgnoreTransfer
        var pstCpr_Data:Array[Byte] = _;	// pointer to compressed image
        @Length(4)
        var bnRes_pstCpr:Array[Byte] = _ ;
        var pstBin_Ptr:Int = _ //using 4 byte as pointer
        @IgnoreTransfer
        var pstBin_Data:Array[Byte] = _;	// pointer to binary data
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

        class CONNECTIONCOUNTER extends AncientData
        {
        var nTotalClient:Int = _;	/* time of requests */
        var nCurClient:Int = _;		/* client logined */
        var nCurThreadNum:Int = _;	/* total threads */
        } // CONNECTIONCOUNTER;

        /* queue flag */
        final val READY = 0
        final val WORKING = 1
        final val FINISHED = 2
        final val ERRDATA = 3
        final val DELETED = 4
        final val WAITCENSOR = 5
        final val CANNOTSUBMIT = 6
        final val CHECKED = 7
        final val CHECKING = 8
        final val WAITRECHECK = 9
        final val RECHECKING = 10
        final val RECHECKED = 11
        final val NOQUEUE = 99
        final val TOTAL = 98

        final val TTMATCH = 0
        final val TLMATCH = 1
        final val LTMATCH = 2
        final val LLMATCH = 3
        final val LLSEARCH = LLMATCH
        final val TTSEARCH = TTMATCH

        /* Define get all data macro, when parameter fgnum is GETALL */
        final val GAFIS_GETALL = -1

        final val TTQUERY = "TT"
        final val LLQUERY = "LL"
        final val LTQUERY = "LT"
        final val TLQUERY = "TL"

        // the following structure is used to satisfy variable need for text info
        // only some very case we need to allocate pointer to date
        class GATEXTPTSTRUCT extends AncientData
        {
          @Length(88)
          var bnData:Array[Byte] = _
          @IgnoreTransfer
          var textContent:Array[Byte] = _
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

  // the standard we need to satisfy
  class GAFIS_STANDARD extends Enumeration
  {
    val STD_CHINA = Value	// china standard
    val STD_US = Value		// us standard
  } //GAFIS_STANDARD;

  // if one item does not exist.
  class GAFIS_MICBSIGNATURE extends AncientData
  {
  var nFingerIndex:Byte = _ ;	// for latent is not used. for tenprint 1-10 finger.
  // 21-30 plain finger(match used). 11-12 palm, global finger index.
  var nItemFlag:Byte = _ ;	// which item is used.GAFIS_MICBSIGFLAG_XXX
  @Length(2)
  var bnRes:Array[Byte] = _ ;	// reserved 2 bytes.
  @Length(12)
  var nSig:String = _ ;	// GAFIS_MICBSIGIDX_XXX
  } // GAFIS_MICBSIGNATURE;	// size 16 bytes long.

  // GAFIS_MICBSIGNATURE::nItemFlag
  final val GAFIS_MICBSIGFLAG_MNT = 0x1
  final val GAFIS_MICBSIGFLAG_IMG = 0x2
  final val GAFIS_MICBSIGFLAG_CPR = 0x4
  final val GAFIS_MICBSIGFLAG_BIN = 0x8

  // GAFIS_MICBSIGNATURE::nSig
  final val GAFIS_MICBSIGIDX_MNT = 0
  final val GAFIS_MICBSIGIDX_IMG = 1
  final val GAFIS_MICBSIGIDX_CPR = 2
  final val GAFIS_MICBSIGIDX_BIN = 3


  class GAFIS_SERVERINFO extends AncientData
  {
  var stUUID = new GAFIS_UUIDStruct;	// uuid of the server. 16 bytes long.
  @Length(64)
  var szName:String = _ ;			// name of the server.
  var nMajorVer:Short = _ ;		// major version.
  var nMinorVer:Short = _ ;		// minor version.
  var nCpuCnt:Byte = _ ;			// # of cpu.
  var nOsType:Byte = _ ;
  var nRole:Byte = _ ;
  var nOsClass:Byte = _ ;
  var nMemSizeInMB:Int = _ ;	// memory size in mb.
  var nTcpPort:Short = _ ;		// tcp port.
  var nUdpPort:Short = _ ;		// udp port.
  var nPID:Int = _ ;			// process id.
  @Length(28)
  var bnRes0:Array[Byte] = _ ;			// reserved.
  } // GAFIS_SERVERINFO;	// server info. size is 128 bytes long.

  // GAFIS_IMGIDXSTRING is a replacement for tenstring. but
  // normal tenstring is space consuming and many image index can
  // not be specified, the following one, can represent 12*8=96
  // images, using routine GAFIS_IMGIDXSTRING_XXX to get and set
  // image index. image index [1, 96] are allowed only.
  class GAFIS_IMGIDXSTRING extends AncientData
  {
  @Length(12)
  var bnStr:Array[Byte] = _ ;
  } // GAFIS_IMGIDXSTRING;	// size is 12 bytes long.


  // define biometric type, can not be or'ed
  final val GA_BIOTYPE_FINGER = 0x1		// finger
  final val GA_BIOTYPE_PALM = 0x3		// palm
  final val GA_BIOTYPE_VOICE = 0x4		// voice
  final val GA_BIOTYPE_FACE = 0x5		// face.


  // define biometric flag, can be or'ed
  final val GA_BIOFLAG_FINGER = 0x1
  final val GA_BIOFLAG_PALM = 0x2
  final val GA_BIOFLAG_VOICE = 0x4
  final val GA_BIOFLAG_FACE = 0x8	// face flag.

  final val GA_BIOIDX_FINGER = 0
  final val GA_BIOIDX_PALM = 1
  final val GA_BIOIDX_VOICE = 2
  final val GA_BIOIDX_FACE = 3


  class GAFIS_PLATFORMID extends AncientData
  {
  var cbSize:Int = _ ;

  var nOsClass:Byte = _ ;	// GA_OSCLASS_XXX
  var nCpuType:Byte = _ ;	// GA_CPUTYPE_XXX
  @Length(2)
  var bnRes:Array[Byte] = _ ;
  // to here is 8 bytes long.
  var nOsMajorVer:Short = _ ;
  var nOsMinorVer:Short = _ ;
  var nGAFISMajorVer:Short = _ ;
  var nGAFISMinorVer:Short = _ ;
  // to here is 16 bytes long.
  var nWinPlatformID:Byte = _ ;	// GA_WINPLATFORM_XXX
  @Length(3)
  var bnRes1:Array[Byte] = _ ;
  var nLangID:Int = _ ;		// langid GA_LANGID_XXX
  var nLocationID:Int = _ ;	// not used.
  @Length(4)
  var bnRes4:Array[Byte] = _ ;
  } // GAFIS_PLATFORMID;	// 32 bytes long.


  // finger digitize method.
  final val DIGIMETHOD_UNKNOWN = 0	// unknown
  final val DIGIMETHOD_SCANPRTINK = 1	// scanning printing ink.
  final val DIGIMETHOD_OPTICALLIVESCAN = 2	// optical live scan.
  final val DIGIMETHOD_CMOS = 3	// cmos scan
  final val DIGIMETHOD_SUPPERSONIC = 4	// supper sonic
  final val DIGIMETHOD_PHOTOGRAPH = 5	// photograph


}

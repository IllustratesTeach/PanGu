package nirvana.hall.c.services.ganumia

import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services._
import nirvana.hall.c.services.ganumia.gadbcommon.GADB_FILEHEADSCHEMA
import nirvana.hall.c.services.gbaselib.gathrdop.GAFIS_CRITSECT
import nirvana.hall.c.services.gbaselib.gfileop.GFILEOBJECT

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-23
 */
object bmfop {



  // (*FILEMAPSTRUCTURE*)
  class GADB_BLOCKHEADSTRUCT extends AncientData
  {
    var bIsBlockUsed:Byte = _ ;	// is the block used?
  var bIsDirty:Byte = _ ;		// for memory used only.
  var nFileID:Short = _ ;		// file id
  var nBlockID:Int = _ ;	// block id, 4 bytes int, seq in file
  var nUsedEntryCount:Short = _ ;	// used entry count, for higher level app used only
  @Length(SID_SIZE)
  var nBkSID:String = _ ;		// block SID. used for recovery.
  } // GADB_BLOCKHEADSTRUCT;	// 16 bytes

  // (*FILEMAPSTRUCTURE*)
  class GADB_BLOCKSTRUCT extends AncientData
  {
    var stHead = new GADB_BLOCKHEADSTRUCT;
    @Length(32)
    var stData:String = _ ;
  } // GADB_BLOCKSTRUCT;	// block will be multiple of 1KB.

  //final val GADB_BMF_PAGE_HEAD_SIZE = sizeof(GADB_BLOCKHEADSTRUCT)

  // (*FILEMAPSTRUCTURE*)
  class GADB_BMHEADSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;		// size of this structure
  var nBlockSize:Int = _ ;	// size for each block
  var bHasBMBlock:Byte = _ ;	// has bm block or not
  @Length(3)
  var bnRes0:Array[Byte] = _ ;		// reserved
  var nUsedBlockCount:Int = _ ;	// used block count
  // used for file without bitmap
  var nMaxFileSize:Long = _ ;	// maximum allowed file size, in bytes. by default we expand
  // file when necessary, but if set file limit, we will not expand.
  @Length(40)
  var bnRes:Array[Byte] = _ ;		// reserved
  } // GADB_BMHEADSTRUCT;	// size of this structure is 64 bytes

  // (*FILEMAPSTRUCTURE*)
  // please define a structure to contain the following structure. the structure
  // size you define must not less than 1024 bytes
  class GADB_BMFILEHEADSTRUCT extends AncientData
  {
    var stFileHead = new GADB_FILEHEADSCHEMA;	// size is 64 bytes
  var stBmHead = new GADB_BMHEADSTRUCT;	// size is 64 bytes
    //UCHAR					bnRes[384+512];	// we can put other information in this area
  } // GADB_BMFILEHEAD;	// size of this structure is 128 bytes
  type GADB_BMFILEHEAD = GADB_BMFILEHEADSTRUCT

  // the following structure will be in memory only
  class GADB_BMBUFSTRUCT extends AncientData
  {
    var pBlock_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pBlock_Data:Array[GADB_BLOCKSTRUCT] = _ // for pBlock pointer ,struct:GADB_BLOCKSTRUCT;
  @Length(4)
  var bnRes_Pointer:Array[Byte] = _ ;
    var bHasData:Int = _;
    var nBMID:Int = _;
    var nEffectiveByteCount:Int = _;	// must be multiple of 1
  var bNeedSave:Byte = _ ;	// tempory used only
  var bIsDirty:Byte = _ ;	//
  @Length(2)
  var bnRes:Array[Byte] = _ ;
  } // GADB_BMBUFSTRUCT;	// size is 24 bytes

  class GADB_BMFILE_READ_FILE_HANDLE extends AncientData
  {
    var pstFile_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstFile_Data:Array[GFILEOBJECT] = _ // for pstFile pointer ,struct:GFILEOBJECT;
  @Length(4)
  var bnRes_pt:Array[Byte] = _ ;
    var nRefCnt:Int = _;	//<! 引用计数
  var nLastReadTime:Int = _;	//!< 上次使用的时间。
  } // GADB_BMFILE_READ_FILE_HANDLE;	// 16 bytes long.

  /**
   * 我们在进行数据的读取的时候，如果使用一个文件句柄，要么加锁，要么采用
   *        异步I/O。但是由于NUMINA最一开始不是设计为异步I/O，所以，如果修改为异步
   *        I/O话，导致修改太多，因此考虑增加一些只读的句柄，来提高I/O的速度。
   * @note
   */
  class GADB_BMFILE_READ_MGR extends AncientData
  {
    @Length(64)
    var stHandle:Array[GADB_BMFILE_READ_FILE_HANDLE] = _;
    var nHighWaterMark:Int = _;
    var nRes:Int = _;
  } // GADB_BMFILE_READ_MGR;

  // the following file is used in memory only
  // if a block can hold 512 records, then a byte can represent 4k record
  // for a 50M record file we only need 50M/4k = 12.5k
  // if a block can hold 32 records, then a byte can represent 256 record
  // for a 50MB record file we only need 50*1000kb/0.25kb = 200kb.
  // usually a bm block is enough
  class GADB_BMFILEMEMMAP extends AncientData
  {
    // bit index file head, may contain other informations. you can replace the following item
    // with any type data(especially with your own file head). but you must
    // set GADB_BMFILEHEAD as the first item of you own head.
    var pstHead_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstHead_Data:Array[GADB_BMFILEHEAD] = _ // for pstHead pointer ,struct:GADB_BMFILEHEAD;
  var pstFile_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstFile_Data:Array[GFILEOBJECT] = _ // for pstFile pointer ,struct:GFILEOBJECT;	// pointer to the file
  var pBMBuf_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pBMBuf_Data:Array[GADB_BMBUFSTRUCT] = _ // for pBMBuf pointer ,struct:GADB_BMBUFSTRUCT;	// bitmap buffer
  @Length(4*3)
  var bnRes_ForPointer:Array[Byte] = _ ;
    var bHeadCanBeFreed:Byte = _ ;
    var bBMBufCanBeFreed:Byte = _ ;
    var bDelayWrite:Byte = _ ;		// delay write file.
  var bFileHeadIsDirty:Byte = _ ;
    @Length(3)
    var bnRes0:Array[Byte] = _ ;
    var bCritInited:Byte = _ ;		// to here is 32 bytes
  var nDiskWriteByte:Long = _;
    var nDiskReadByte:Long = _;
    var nBMCount:Int = _;		// effective count in pBMBuf and it's the actual bm block count
  var nBMBufCount:Int = _;	// size of pBMBuf
  var nUnusedBlockCount:Int = _;	// how many unused blocks in this file, it's value is initialized we open the file
  var nDataBlockCount:Int = _;	// # of total data block counts, including used and unused
  @Length(80)
  var szShortName:String = _ ;	// this member is used for transaction
  @Length(8)
  var bnRes1:Array[Byte] = _ ;			// reserved
  var pstReadFileMgr_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstReadFileMgr_Data:Array[GADB_BMFILE_READ_MGR] = _ // for pstReadFileMgr pointer ,struct:GADB_BMFILE_READ_MGR;
  @Length(4)
  var bnRes_FileMgrPt:Array[Byte] = _ ;
    var stCrit = new GAFIS_CRITSECT;				// critical section to access the file, in windows this record size is 24
    @Length(96 - 8)
  var bnRes:Array[Byte] = _
  } // GADB_BMFILEMEMMAP;	// size of this structure is 256 bytes

  final val GADB_INVALIDBKID =  - 1

  class GADB_BMFEXPANDOPT extends AncientData
  {
    var bExpanded:Int = _;		// whether expanded
  var nOrgBlockCount:Int = _;	// Original block count if expanded
  var nOption:Int = _;		// option		GADB_BMFOPT_XXXX
  var nBKID:Int = _;			// block id, alloc one block used only and if block id does not transferred
  // through parameters
  @Length(8)
  var bnRes:Array[Byte] = _ ;
    var pstMap_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstMap_Data:Array[GADB_BMFILEMEMMAP] = _ // for pstMap pointer ,struct:GADB_BMFILEMEMMAP;
  @Length(4)
  var bnRes_Pointer:Array[Byte] = _ ;
  } // GADB_BMFEXPANDOPT;	// size of this structure is 32 bytes

  final val GADB_BMFOPT_ALLOC_CLEAR_BLOCK = 0x1
  final val GADB_BMFOPT_ALLOC_NOTRANSACTION = 0x2	// no transaction
  final val GADB_BMFOPT_ALLOC_FIXEDBKID = 0x4	// the caller will specifiy the block with bkis will
  // be alloced
  // GADB_BMF_LoadRowDataEx used only
  class GADB_LOADROWPERFCOUNTER extends AncientData
  {
    var nWaitTime:Long = _;
    var nReadDiskTime:Long = _;
    var nDataLen:Int = _;
    var nReadSpeed:Int = _;		// speed KB/sec.
  } // GADB_LOADROWPERFCOUNTER;	//

  class GADB_BLOCKPART extends AncientData
  {
    var nOffsetInBlock:Int = _;
    var nSize:Int = _;
  } // GADB_BLOCKPART;	// block part 8 bytes long.

}




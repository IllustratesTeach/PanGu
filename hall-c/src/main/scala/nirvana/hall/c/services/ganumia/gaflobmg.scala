package nirvana.hall.c.services.ganumia

import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services._
import nirvana.hall.c.services.ganumia.gadbcommon.GADB_FILEHEADSCHEMA
import nirvana.hall.c.services.ganumia.gadbtbl.GADB_TABLEOBJECT
import nirvana.hall.c.services.ganumia.gaitemop.GADB_COLSIMPPROPSTRUCT
import nirvana.hall.c.services.gbaselib.gathrdop.{GBASE_CRIT, GAFIS_CRITSECT}
import nirvana.hall.c.services.gbaselib.gbasedef.GAKEYSTRUCT
import nirvana.hall.c.services.gbaselib.gfileop.GFILEOBJECT
import nirvana.hall.c.services.{SID_TYPE, AncientData}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-23
 */
object gaflobmg {

  //extern	struct	tagGADB_TABLEOBJECT;

  // block content type
  final val GLOB_BKCNTTYPE_NOTHING = 0	// not used by any object
  final val GLOB_BKCNTTYPE_FREEPTDIR = 1	// free block directory
  final val GLOB_BKCNTTYPE_LOBDATA = 2	// tree lob data
  final val GLOB_BKCNTTYPE_PTDIR = 3	// lob occupied block directory
  final val GLOB_BKCNTTYPE_COLDIR = 4	// column directory
  final val GLOB_BKCNTTYPE_KEYDATA = 5	// store key data.

  // maximum block size
  final val GLOB_WHOLE_BLOCK_SIZE = (512*1024)
  final val GLOB_BLOCK_SIZE_LEVEL = 8	// # of block size level.
  final val GLOB_BKCNTTYPE_COUNT = 6	// 6 type count.

  class GLOB_BKSIZEINFO extends AncientData
  {
    var nSizeType:Short = _;	// size type, from 0 to 7
  var nRatio:Short = _;		// =size of current type / size of next type.
  var nSize:Int = _;		// size in bytes
  } // GLOB_BKSIZEINFO;	// size is 8 byte long

  /*
   * file block pointer, points to block address
   */

  class GLOB_BKPT extends AncientData
  {
    var nBKID:Int = _ ;		// large block id.
  var nSubBKID:Short = _ ;	// sub block id
  var nBkSizeType:Byte = _ ;	// block size type, GLOB_BKSIZETYPE_XX, from 0, to 7.
  var nBkCntType:Byte = _ ;		// block content type, GLOB_BKCNTTYPE_XX
  } // GLOB_BKPT;	// block pointer, size is 8 bytes long

  // (nDevID, nDFID, stBkPt) determines the unique position of lob for each row.
  class GADB_LOBPT extends AncientData
  {
    var stBkPt = new GLOB_BKPT;		// block pointer
  var nDFID:Int = _ ;	// size is 4 bytes long, data file id
  var nDevID:Byte = _ ;		// device id
  var nRowDevID:Byte = _ ;	// row data file device id.
  @Length(1)
  var bnRes:Array[Byte] = _ ;	// reserved
  var bIsUsed:Byte = _ ;	// whether blob pointer has been used.
  } // GADB_LOBPT;	// lob pointer, size is 16 bytes long

  class GADB_SIDLOBPT extends AncientData
  {
    var stLobPt = new GADB_LOBPT;	// 16 bytes long.
  var nSID = new SID_TYPE;		// 8 bytes long.
  @Length(8)
  var bnRes:Array[Byte] = _ ;
  } // GADB_SIDLOBPT;	// 32 bytes long.

  class GLOB_BKINFO extends AncientData
  {
    var nOffset:Long = _;	// offset in file
  var nBkSize:Int = _;	// block size
  var nRes:Int = _;		// reserved
  } // GLOB_BKINFO;	// block info, size is 16 bytes long

  class GLOB_BKPTPAIR extends AncientData
  {
    var stFirst = new GLOB_BKPT;
    var stLast = new GLOB_BKPT;
  } // GLOB_BKPTPAIR;	// block pointer pair. size is 16 bytes long

  // block head magic
  final val GLOB_BKHEAD_MAGIC = 0xBAF9CD7E

  // block head(for all types blocks)
  class GLOB_BKHEAD extends AncientData
  {
    var cbSize:Int = _ ;	// size of this record
  var nMagic:Int = _ ;	// magic of the block, must GLOB_BKHEAD_MAGIC
  var nMajorVer:Byte = _ ;
    var nMinorVer:Byte = _ ;
    var nBkSizeType:Byte = _ ;	// block size type, GLOB_BKSIZETYPE_XX
  var nBkCntType:Byte = _ ;		// block content type, GLOB_BKCNTTYPE_XX
  var bIsLinkHead:Byte = _ ;	// whether this block is the first block in the link list.
  @Length(3)
  var bnRes:Array[Byte] = _ ;
    @Length(8)
    var bnRes2:Array[Byte] = _ ;		// 8 bytes reserved.
  var stNextBkPt = new GLOB_BKPT;	// next block pointer
  } // GLOB_BKHEAD;	// block head, stored in file and in mem, size is 32 bytes long

  // LOB FILE parameters
  class GLOB_FILEPARAM extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure
  var nMajorVer:Byte = _ ;
    var nMinorVer:Byte = _ ;
    @Length(2)
    var bnRes:Array[Byte] = _ ;	// reserved
  var bIsPtUsed:Long = _ ;	// whether pointer is used or not
  var nNeedChkCnt:Int = _ ;	// need check count
  var bNeedSetBkPtInIsa:Byte = _ ;
    @Length(11)
    var bnRes2:Array[Byte] = _ ;	// reserved
  // to here is 32 bytes long
  @Length(8)
  var stPt:Array[GLOB_BKPTPAIR] = _;	// 8 pairs of block pointers, size is 128 bytes
  @Length(96)
  var bnRes3:Array[Byte] = _ ;	// reserved
  } // GLOB_FILEPARAM;	// file parameter, size is 256 bytes long

  // magic string is
  final val GLOB_FILE_MAGIC = "gafislobfile"

  class GLOB_FILEHEAD extends AncientData
  {
    var stFileHead = new GADB_FILEHEADSCHEMA;	// file head

    var stParam = new GLOB_FILEPARAM;	// parameter,
    @Length(1024 - 256 - 64)
    //var bnRes:Byte = _ [1024-sizeof(GLOB_FILEPARAM)-sizeof(GADB_FILEHEADSCHEMA)];
    var bnRes:Array[Byte] = _
  } // GLOB_FILEHEAD;	// size of this structure is 1KB.

  ///////////////////////// structure will appear in memory only
  // single file in memory
  //
  class GLOB_SINGLEFILEINMEM extends AncientData
  {
    var nFileID:Int = _;	// file id
  var nRefCnt:Int = _;	// reference count, if reference count is zero, we can release
  // this object
  var nLastAccessTime:Int = _;	// last access time
  var bCritInited:Byte = _ ;		// whether stCrit has been initialized
  var nDevID:Byte = _ ;				// device id of this file.
  var nState:Byte = _ ;				// file state, LOBFILE_STATE_XXX, defined in gdfidmap.h
  @Length(1)
  var bnRes:Array[Byte] = _ ;			// reserved

    // to here is 16 bytes long
    var pstFile_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstFile_Data:Array[GFILEOBJECT] = _ // for pstFile pointer ,struct:GFILEOBJECT;	// pointer to file
  var pstHead_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstHead_Data:Array[GLOB_FILEHEAD] = _ // for pstHead pointer ,struct:GLOB_FILEHEAD;	// file head
    var pstTable_Ptr:Int = _
    @IgnoreTransfer
  var  pstTable_Data:Array[GADB_TABLEOBJECT] = _
    @Length(4*3)
    var bnRes_Pointer:Array[Byte] = _ ;
    // to here is 40 bytes
    var nNeedChkCnt:Int = _;	// need check count
  var nRes:Int = _;
    var nUsedSize:Long = _;		// used data size.
  @Length(84+20)
  var bnRes3:Array[Byte] = _ ;		//
  // ensure critical section is 96 bytes long
  var stCrit = new GAFIS_CRITSECT;
    @Length(96 - 8)
    var bnRes_Crit:Array[Byte] = _ ;	//
  } // GLOB_SFINMEM;	// one file in memory, size is 256 bytes long
  type GLOB_SFINMEM = GLOB_SINGLEFILEINMEM

  /// file lob manager
  class GLOB_SFILEMGR extends AncientData
  {
    var nFileCnt:Int = _;	// open file count, at the same time, the os does not allowed us
  // open too many blob files.
  var nFileBufCnt:Int = _;	// size of ppstFile array,

    var ppstFile_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var ppstFile_Data:Array[GLOB_SFINMEM] = _ // for ppstFile pointer ,struct:GLOB_SFINMEM;	// pointer to file array
    var pstTable_Ptr:Int = _
    var pstTable_Data:Array[GADB_TABLEOBJECT] = _
    @Length(4*2)
    var bnRes_Pointer:Array[Byte] = _ ;

    // to here is 24 bytes long
    var bCritInited:Byte = _ ;	// whether crit has been initialized
  @Length(3)
  var bnRes:Array[Byte] = _ ;
    var nLastFileIndex:Int = _;	// last file index.
  var stCrit = new GAFIS_CRITSECT;
    @Length(96 - 8)
    var bnRes_Crit:Array[Byte] = _

    // nov. 1, 2005. when loading lob, we does not using the file opened previously, we can only open
    // a new file(because we ensure the same pt can not be modified at the same time).
    // here we defined some structure to manage those files opened only for reading data.
    // because open and then close will cost a lot of time. on my note book cost about 1 millisecond
    //
    var ppstReadFile_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var ppstReadFile_Data:Array[GLOB_SFINMEM] = _ // for ppstReadFile pointer ,struct:GLOB_SFINMEM;
  @Length(4)
  var bnRes_PtReadFile:Array[Byte] = _ ;
    var nReadFileBufCnt:Int = _;
    var nReadFileCnt:Int = _;
    @Length(16)
    var bnRes_Read:Array[Byte] = _ ;
    var stReadFileCrit = new GBASE_CRIT;	// 96 bytes long.
  } // GLOB_SFILEMGR;	// manage all opened lob files, 128 +128 bytes long

  // block in memory
  class GLOB_BKINMEM extends AncientData
  {
    var stBkPt = new GLOB_BKPT;		// block pointer, 8 bit size
  var nDataBufSize:Int = _;	// size of buffer
  var nDataSize:Int = _;		// data size
  var pData_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pData_Data:Array[Byte] = _ // for pData pointer ,struct:UCHAR;	// data
  @Length(4)
  var bnRes_Pointer:Array[Byte] = _ ;
  } // GLOB_BKINMEM;	// block in memory, size is 24 bytes long

  class GLOB_PTDIRHEAD extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure
  var nMajorVer:Byte = _ ;
    var nMinorVer:Byte = _ ;
    var nBkPtCnt:Short = _ ;	// block pointer count
  @Length(8)
  var bnRes2:Array[Byte] = _ ;		// reserved
  } // GLOB_PTDIRHEAD;	// block array head, size is 16 bytes long

  class GLOB_PTDIRBLOCK extends AncientData
  {
    var stBkHead = new GLOB_BKHEAD;	// size is 32 bytes long
  var stDirHead = new GLOB_PTDIRHEAD;	// block array head, size is 16 bytes long
  @Length(1)
  var stPt:Array[GLOB_BKPT] = _;	// pointer array
  } // GLOB_PTDIRBLOCK;	// block pointer. virtual block.

  class GLOB_PTDIRPAGE extends AncientData
  {
    var stDirHead = new GLOB_PTDIRHEAD;	// block array head, size is 16 bytes long
  @Length(1)
  var stPt:Array[GLOB_BKPT] = _;	// pointer array
  } // GLOB_PTDIRPAGE;	// used for data dir.

  class GLOB_KEYBKPTUNION extends AncientData
  {
    @Length(32)
    var bnRes:Array[Byte] = _
    /*
    GLOB_BKPT	stPt;		// pointer to key block, we ensure that key is less then 1kb, so
    // it's always held by one block.
    char		szKey[32];	// key of this record
    */
  } //GLOB_KEYBKPTUNION;	// size is 32 bytes long

  class GLOB_COLDIRHEAD extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure
  var nMajorVer:Byte = _ ;
    var nMinorVer:Byte = _ ;
    var nColCount:Short = _ ;	// column count
  // to here is 8 bytes long
  @Length(SID_SIZE)
  var nSID:String = _ ;	// row sid
  var nKeyLen:Short = _ ;		// length of key, if nKeyLen>32 then store key in another block
  // else store it in stKey.szKey
  @Length(16)
  var bnRes2:Array[Byte] = _ ;		// reserved
  var stKey = new GLOB_KEYBKPTUNION;	// key
  } // GLOB_COLDIRHEAD;	// head for column dir list, size is 64 bytes long

  // column blob directory
  class GLOB_COLDIRENT extends AncientData
  {
    @Length(16)
    var bnColUUID:Array[Byte] = _ ;	// column uuid, we using column uuid to identify columns
  var bHasDirBk:Byte = _ ;		// whether has dir blocks
  var bnRes:Byte = _ ;			// reserved
  var nBkPtCnt:Short = _ ;	// # of useful pointers used by data.
  var nDataLen:Int = _ ;	// length of blob data
  // to here is 24 bytes long
  @Length(5+8)
  var stDatBkPt:Array[GLOB_BKPT] = _;	// data starting bkpt. if the data using less than 13 blocks
    // we store the block pointer in this array, else if bHasDirBk true
    // then we store the directory dir first block pt in stDatBkPt[0]
    // data first block pt in stDatBkPt[1]. if bHasDirBk false we
    // store data first block pt in stDatBkPt[0], other entries are not
    // used.
  } // GLOB_COLDIRENT;	// size is 32+32+64=128 bytes long

  class GLOB_COLDIRPAGE extends AncientData
  {
    var stHead = new GLOB_COLDIRHEAD;
    @Length(1)
    var stEnt:Array[GLOB_COLDIRENT] = _;
  } // GLOB_COLDIRPAGE;	// directory page.

  class GLOB_COLDIRBKHEAD extends AncientData
  {
    var stBkHead = new GLOB_BKHEAD;
    var stDirHead = new GLOB_COLDIRHEAD;
  } // GLOB_COLDIRBKHEAD;	// dir block, 96 bytes long.

  // column data structures.
  class GLOB_COLDATA extends AncientData
  {
    var stProp = new GADB_COLSIMPPROPSTRUCT;	// property
  @Length(16)
  var bnUUID:Array[Byte] = _ ;				// uuid of the column
  @Length(16)
  var bnRes:Array[Byte] = _ ;				// reserved
  } // GLOB_COLDATA;	// size is 64 bytes long

  class GLOB_KEYDATA extends AncientData
  {
    var nKeyLen:Int = _;	// length of key
  var nRes:Int = _;		// reserved
  var pbnKey_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pbnKey_Data:Array[Byte] = _ // for pbnKey pointer ,struct:char;	// pointer to key
  @Length(4)
  var bnRes_Pointer:Array[Byte] = _ ;
    @Length(48)
    var szBuf:String = _ ;	// temp key buf
  } // GLOB_KEYDATA;	// key data, size is 64 bytes long

  class GLOB_PTHEAD extends AncientData
  {
    var pstPtDirPt_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstPtDirPt_Data:Array[GLOB_BKPT] = _ // for pstPtDirPt pointer ,struct:GLOB_BKPT;
  var pstDatBkPt_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstDatBkPt_Data:Array[GLOB_BKPT] = _ // for pstDatBkPt pointer ,struct:GLOB_BKPT;
  @Length(2*4)
  var bnRes_Pointer:Array[Byte] = _ ;
    var nPtDirPtCnt:Int = _;
    var nDatBkPtCnt:Int = _;
    @Length(8)
    var bnRes:Array[Byte] = _ ;		// reserved
  } // GLOB_PTHEAD;	// size is 32 bytes long

  class GLOB_PTHEADPAIR extends AncientData
  {
    var pstOldHead_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstOldHead_Data:Array[GLOB_PTHEAD] = _ // for pstOldHead pointer ,struct:GLOB_PTHEAD;
  var pstNewHead_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstNewHead_Data:Array[GLOB_PTHEAD] = _ // for pstNewHead pointer ,struct:GLOB_PTHEAD;
  @Length(2*4)
  var bnRes_Pointer:Array[Byte] = _ ;
    var nOldHeadCnt:Int = _;
    var nNewHeadCnt:Int = _;
    @Length(8)
    var bnRes:Array[Byte] = _ ;	//
  } // GLOB_PTHEADPAIR;	// size is 32 bytes long.

  final val GLOB_OPT_KEEPZEROLENDATA = 0x1	// keep zero length data, default is not
  final val GLOB_OPT_ISUPDATE = 0x2	// whether is update or new add
  final val GLOB_OPT_NEEDLOCK = 0x4	// need lock of the file .
  final val GLOB_OPT_NEEDPTDIR = 0x8	// need pointer directory page
  final val GLOB_OPT_NOXACT = 0x10	// no transaction.


  ////////////////// the following structure is used to check lob file////////////
  // the following structure is actually
  class GLOB_CHK_SUBBKINFO extends AncientData
  {
    var nCntType:Byte = _ ;	// content type
  var nStatus:Byte = _ ;	// GLOB_CHK_STATUS_XXX
  @Length(6)
  var bnRes:Array[Byte] = _ ;	// reserved.
  // for link item used, in GLOB_BKHEAD we have next block pt, and this pt
  // must match that read from next block head.
  var nNextBKID:Int = _;	//
  var nNextSubBKID:Short = _;
    var nNextCntType:Byte = _ ;
    var nNextSizeType:Byte = _ ;	// next size type
  } // GLOB_CHK_SUBBKINFO;	// subblock info. 16 bytes long

  final val GLOB_CHK_STATUS_ISLINKHEAD = 0x1	// it's link head.
  final val GLOB_CHK_STATUS_CHECKED = 0x2	// the block has been determined belong to one col

  class GLOB_CHK_BKINFO extends AncientData
  {
    var pstSubBk_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstSubBk_Data:Array[GLOB_CHK_SUBBKINFO] = _ // for pstSubBk pointer ,struct:GLOB_CHK_SUBBKINFO;
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;
    var nSubBkCnt:Short = _;	// count of subblock
  var nSizeType:Byte = _ ;	// size type.
  @Length(1+4)
  var bnRes:Array[Byte] = _ ;
    var stBuf = new GLOB_CHK_SUBBKINFO;	// buffer, if nSubBkCnt is one we does not alloc memory, 8 bytes long
  } // GLOB_CHK_BKINFO;	// block info, size is 32 bytes long

  // GLOB_BKINT2INFO and GLOB_BKINT4INFO are used for
  // statistics. and GLOB_BKINT2INFO for one columns
  // GLOB_BKINT4INFO for one file and one row.
  class GLOB_BKINT2INFO extends AncientData
  {
    @Length(GLOB_BLOCK_SIZE_LEVEL)
    var nCnt:Array[Short]= _ ;
  } // GLOB_BKINT2INFO;	// 16 bytes long.

  class GLOB_BKINT4INFO extends AncientData
  {
    @Length(GLOB_BLOCK_SIZE_LEVEL)
    var nCnt:Array[Int]= _ ;
  } // GLOB_BKINT4INFO;	// 32 bytes long.

  class GLOB_COLBKINFO extends AncientData
  {
    var nDataLen:Int = _ ;	// length of data.
  var nCntType:Byte = _ ;		// content type.GLOB_BKCNTTYPE
  @Length(3)
  var bnRes:Array[Byte] = _ ;		// reserved.
  var stCnt = new GLOB_BKINT2INFO;
  } // GLOB_COLBKINFO;	// column used block info. 24 bytes long.

  // storage info for one row, used for statistics.
  class GLOB_COLDIR_BKINFO extends AncientData
  {
    var nColCnt:Short = _ ;		// # of columns in this row. this is not true lob count.
  // but # of pstBkInfo.
  @Length(SID_SIZE)
  var nSID:String = _ ;	// copy sid.
  var nKeyLen:Short = _ ;
    @Length(14)
    var bnRes:Array[Byte] = _ ;		// reserved 14 bytes long.
  var pstBkInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstBkInfo_Data:Array[GLOB_COLBKINFO] = _ // for pstBkInfo pointer ,struct:GLOB_COLBKINFO;	// block info.	pstBkInfo[0] is for the dir it self
  // pstBkInfo[1:nColCnt] is for column lob data.
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;
    var stKey = new GAKEYSTRUCT;	// key. 32 bytes long.
  } // GLOB_COLDIR_BKINFO;	// size is 64 bytes long.

  class GLOB_FILEBKINFO extends AncientData
  {
    var stFreeBkCnt = new GLOB_BKINT4INFO;	// free block count.
  // to here 32 bytes long.
  var nColDirCnt:Int = _ ;	//
  @Length(4)
  var bnRes0:Array[Byte] = _ ;
    var nFileSize:Long = _ ;		// file size. it should be free space+used space+headsize.
  var pstDirInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstDirInfo_Data:Array[GLOB_COLDIR_BKINFO] = _ // for pstDirInfo pointer ,struct:GLOB_COLDIR_BKINFO;
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;
    @Length(8)
    var bnRes2:Array[Byte] = _ ;	// reserved.
  } // GLOB_FILEBKINFO;	// file block info. 64 bytes long.

  class GLOB_LOBSTA extends AncientData
  {
    var nLobDataLen:Long = _;	// length of data.
  @Length(GLOB_BKCNTTYPE_COUNT)
  var nTypeCnt:Array[Long] = _;	// count for each content type.

    /*
  @Length(GLOB_BKCNTTYPE_COUNT)
  var nBkCnt:Array[ga_uint8] = _[GLOB_BLOCK_SIZE_LEVEL];	// distribution of block.
  */
    @Length(8 * GLOB_BLOCK_SIZE_LEVEL *GLOB_BKCNTTYPE_COUNT)
    var nBkCnt:Array[Byte] = _

  } // GLOB_LOBSTA;

  // also for GLOB_SF_Reset.
  final val GLOB_CHKOPT_NOERRORCHK = 0x1	// no error checking while building some info.
  final val GLOB_CHKOPT_FREEBADBLOCK = 0x2	// if block head is error then free it.
  final val GLOB_CHKOPT_CHKCNT = 0x4
  final val GLOB_CHKOPT_CHKSID = 0x8	// checking whether block pt stored in isa is the
  // same as current one.

  class GLOB_SIDBKPT extends AncientData
  {
    var stPt = new GLOB_BKPT;
    @Length(SID_SIZE)
    var nSID:String = _ ;
    var nDevID:Byte = _ ;
    @Length(1)
    var bnRes:Array[Byte] = _ ;	// reserved.
  } // GLOB_SIDBKPT;	// used when packing file. 16 bytes long.

  class GLOB_SETISABKPT extends AncientData
  {
    var nFileID:Int = _ ;	// lob file id.
  var nDevID:Byte = _ ;
    @Length(3)
    var bnRes:Array[Byte] = _ ;
    @Length(32)
    var szDBName:String = _ ;
    @Length(32)
    var szTableName:String = _ ;
  } // GLOB_SETISABKPT;	// size is 64+8=72 bytes long.

  //////////////
  //inorder to update key we need the following routine to control the whole process.
  class GLOB_OLDKEYDATA extends AncientData
  {
    var stHeadPt = new GADB_LOBPT;	// head pointer.
  var stOldKeyBkPt = new GADB_LOBPT;	// if store key in separate block then this hold that block pointer.
  var stNewKeyBkPt = new GADB_LOBPT;	// if store key in separate block then this hold that block pointer.
  var stDirHead = new GLOB_COLDIRBKHEAD;		// old head.
  // to here is 128 + 16 bytes long.
  var pbnOldKey_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pbnOldKey_Data:Array[Byte] = _ // for pbnOldKey pointer ,struct:UCHAR;	// maximum key length
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;
    var nOldKeyLen:Int = _;			// length of old key.
  var nNewKeyLen:Int = _;
    var bDirHeadHasData:Byte = _ ;
    var bOldKeyHasData:Byte = _ ;
    @Length(30)
    var bnRes:Array[Byte] = _ ;			// reserved.
  } // GLOB_OLDKEYDATA;	// used when recovering. 128+64=192 bytes long.



  /// option for GLOB_SF_DIR_Load.
  final val FLOB_DIRLOADOPT_CURCOLCNT = 0x1	// if dir page length calculated from nColCnt is different
  // from actual length, then reset nColCnt to correct one.
  final val FLOB_DIRLOADOPT_IGNORELEN = 0x2


  final val GLOB_DELALLOPT_XACTCALLED = 0x1


  class GLOB_BKIDOFFSET extends AncientData
  {
    var nBKID:Int = _;
    var nOffsetInBlock:Int = _;	// offset in block.
  var nLen:Int = _;
    var nRes:Int = _;
  } // GLOB_BKIDOFFSET;	// 16 bytes long.

  final val GLOB_BUFBLOCK_HEADSIZE = 16
  // mark the dirty flag only when using write cache.
  class GLOB_BUFBLOCK extends AncientData
  {
    var bIsDirty:Int = _;	// whether data has been dirty.
  var nBlockSize:Int = _;
    var nDevID:Byte = _ ;		// device id.
  @Length(7)
  var bnRes:Array[Byte] = _ ;	// reserved.
  // to here must be GLOB_BUFBLOCK_HEADSIZE size.
  @Length(16)
  var bnData:Array[Byte] = _ ;	// may be many data.
  } // GLOB_BUFBLOCK;	// add to cache block

  class GLOB_BKBUFITEM extends AncientData
  {
    var bIsCached:Int = _;
    var nBKID:Int = _;
    var pData_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pData_Data:Array[GLOB_BUFBLOCK] = _ // for pData pointer ,struct:GLOB_BUFBLOCK;
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;
    var nID:Long = _;
  } // GLOB_BKBUFITEM;	// 24 bytes long.

}

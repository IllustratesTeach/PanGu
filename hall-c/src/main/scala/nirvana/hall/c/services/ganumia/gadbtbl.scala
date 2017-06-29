package nirvana.hall.c.services.ganumia

import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.ganumia.gadbcol.{GADB_COLUMNSCHEMA, GADB_MEMCOLSTATISTIC, GADB_SIMPCOLSTATISTIC}
import nirvana.hall.c.services.ganumia.gadbcommon.GADB_FILEHEADSCHEMA
import nirvana.hall.c.services.ganumia.gadbrec.GADB_ROWMODCACHE
import nirvana.hall.c.services.ganumia.gaflobmg.GLOB_SFILEMGR
import nirvana.hall.c.services.ganumia.ganmuser.NMUSER_OBJPRIV
import nirvana.hall.c.services.ganumia.gdfidmap.GADB_DFIDMAPINMEM
import nirvana.hall.c.services.gbaselib.gathrdop.GBASE_CRIT
import nirvana.hall.c.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-23
 */
object gadbtbl {

  final val GADB_TBL_FILEMAGICSTR = "AFIS-TABLE-DATA"

  class GADB_OPTIMELOG extends AncientData
  {
    var nTimeUsed:Long  = _;
    var nOpCount:Long = _;	// # of operation times.
  var nMaxOpTime:Long = _;	// max time cost for one operation
  var nLastOpTime:Long = _;	// operation time of last op.
  } // GADB_OPTIMELOG;	// 32 bytes long.

  // operation time statistics for one table used.
  class GADB_OPTIMESTA extends AncientData
  {
    // add record.
    var stRecAddByKey = new GADB_OPTIMELOG;
    var stRecAddRawRow = new GADB_OPTIMELOG;
    var stRecAddBySID = new GADB_OPTIMELOG;
    // del record.
    var stRecDelByKey = new GADB_OPTIMELOG;
    var stRecDelBySID = new GADB_OPTIMELOG;
    // update record.
    var stRecUpdateByKey = new GADB_OPTIMELOG;
    var stRecUpdateRawRow = new GADB_OPTIMELOG;
    var stRecUpdateBySID = new GADB_OPTIMELOG;
    // get record.
    var stRecGetByKey = new GADB_OPTIMELOG;
    var stRecGetByRawRow = new GADB_OPTIMELOG;
    var stRecGetBySID = new GADB_OPTIMELOG;

    // misc operation counting.
    var stModLogAdd = new GADB_OPTIMELOG;
    var stKeyExist = new GADB_OPTIMELOG;
    var stKey2SID = new GADB_OPTIMELOG;
    var stSID2Key = new GADB_OPTIMELOG;
    var stIndexAdd = new GADB_OPTIMELOG;
    var stIndexDel = new GADB_OPTIMELOG;
    var stIndexOneAdd = new GADB_OPTIMELOG;
    var stIndexOneDel = new GADB_OPTIMELOG;
    var stLobDel = new GADB_OPTIMELOG;
    var stLobUpdate = new GADB_OPTIMELOG;
    var stLobSave = new GADB_OPTIMELOG;
    var stLobRead = new GADB_OPTIMELOG;

    var stCTSave = new GADB_OPTIMELOG;
    var stCTLoad = new GADB_OPTIMELOG;
    var stCTUpdate = new GADB_OPTIMELOG;
    var stSlotReserve = new GADB_OPTIMELOG;	// reserved slot
  var stSlotUnReserve = new GADB_OPTIMELOG;	// unserve slot.
  var stIsaSave = new GADB_OPTIMELOG;		// save isa
  var stIsaRead = new GADB_OPTIMELOG;		// read isa
  var stIsaReserve = new GADB_OPTIMELOG;
    var stIsaUnReserve = new GADB_OPTIMELOG;
    var stSelect = new GADB_OPTIMELOG;
    var stKeySelWild = new GADB_OPTIMELOG;
    var stKeyRetr = new GADB_OPTIMELOG;

    var stFlobSelLobDF = new GADB_OPTIMELOG;		// GADB_TABLE_SelectLOBDF
  var stFlobSelDFID = new GADB_OPTIMELOG;		// GDFIDMAP_UTIL_SelectDFID
  var stFlobGetSF = new GADB_OPTIMELOG;		// GLOB_MGR_GetSF
  var stRowLobNewAdd = new GADB_OPTIMELOG;		// GLOB_SF_ROWLOB_NewAdd
  var stRowLobUpdate = new GADB_OPTIMELOG;		// GLOB_SF_ROWLOB_Update
  var stRowLobLoadAll = new GADB_OPTIMELOG;	// GLOB_SF_ROWLOB_LoadAll
  var stRowLobDelAll = new GADB_OPTIMELOG;		// GLOB_SF_ROWLOB_DelAll

    var stMColProp2Data = new GADB_OPTIMELOG;		// GLOB_SF_ROWLOB_DelAll
  var stRand = new GADB_OPTIMELOG;				// testing for bottle neck.
  var stFlobExpand = new GADB_OPTIMELOG;

    // the following seven entries are used to testing system bottle neck.
    var stTestA = new GADB_OPTIMELOG;
    var stTestB = new GADB_OPTIMELOG;
    var stTestC = new GADB_OPTIMELOG;
    var stTestD = new GADB_OPTIMELOG;
    var stTestE = new GADB_OPTIMELOG;
    var stTestF = new GADB_OPTIMELOG;
    var stTestG = new GADB_OPTIMELOG;

    var stSelResSaveByKeySID = new GADB_OPTIMELOG;
    var stMData2ColProp = new GADB_OPTIMELOG;		// GADB_GLOB_UTIL_MultiData2ColProp
  var stGetModLogSIDArray = new GADB_OPTIMELOG;
    @Length(5)
    var stRes:Array[GADB_OPTIMELOG] = _;
    var stCrit = new GBASE_CRIT;	// critical section. 96 bytes long.
  var nTickPerSecond:Long = _;
    @Length(6)
    var nCnt:Array[Int] = _;		// special data for counter. used for counter
    // for expample, if expand file, then the size will
    // be specified.
  } // GADB_OPTIMESTA;	// operation time used.	size is 1024+512 +512 Byte

  final val GADB_OPTIMESTA_IDX_LOBEXPAND = 0
  final val GADB_OPTIMESTA_IDX_SAVEBYKS = 1	// save by key or sid.
  final val GADB_OPTIMESTA_IDX_ROWLOBADD = 2	// row lob new add.
  final val GADB_OPTIMESTA_IDX_MDATA2COLPROP = 3	// row lob new add.


  // following structure is used to do statistics on table.
  class GADB_TABLESTATISTICS extends AncientData
  {
    var cbSize:Int = _ ;
    var nMajorVer:Byte = _ ;
    var nMinorVer:Byte = _ ;
    var nColCnt:Short = _ ;		// column count.
  // to here is 8 bytes long.
  var nRowCount:Long = _ ;	// real row count,
  var nSIDCount:Long = _ ;	// sid count.
  var nDiskUsed:Long = _ ;	// disk size used in byte.

    var nRawDataLen:Long = _ ;	// net data length.
  var nBlockCount:Int = _ ;
    var nFileCount:Int = _ ;

    var nFreeDiskSize:Long = _ ;
    var nTotalDiskSize:Long = _ ;

    @Length(64)
    var bnRes2:Array[Byte] = _ ;	// reserved.
  } // GADB_TABLESTATISTICS;	//	size is 128 bytes long.

  //
  // we found GADB_TABLE_GetColByUUID is time consuming, so we
  // build an uuid to column pointer mapper array to accelerate the process.
  class GADB_COLUUIDENTRY extends AncientData
  {
    @Length(16)
    var bnUUID:Array[Byte] = _ ;	// uuid of the column
  var pstCol_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstCol_Data:Array[GADB_COLUMNSCHEMA] = _ // for pstCol pointer ,struct:GADB_COLUMNSCHEMA;	// column pointer.
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;
  } // GADB_COLUUIDENTRY;	// size is 24 bytes long.

  class GADB_COLUUIDMAP extends AncientData
  {
    var pstEnt_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstEnt_Data:Array[GADB_COLUUIDENTRY] = _ // for pstEnt pointer ,struct:GADB_COLUUIDENTRY;
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;
    var nEntCnt:Int = _;
    var nEntBufCnt:Int = _;
  } // GADB_COLUUIDMAP;	// size is 16 bytes long.

  class GADB_CTIDCOLCNT extends AncientData
  {
    var nCTID:Short = _ ;
    var nColCnt:Short = _ ;
  } // GADB_CTIDCOLCNT;	// 4 bytes long.

  // table statistics file header.
  class GADB_COLSTAFILEHEAD extends AncientData
  {
    var stFileHead = new GADB_FILEHEADSCHEMA;	// 64 bytes long.
  var nLastChangeTime:Int = _ ;
    @Length(4)
    var bnRes:Array[Byte] = _ ;
    var nCTCnt:Short = _ ;	// child column count.
  var bTableHasError:Byte = _ ;
    var bTableRowChanged:Byte = _ ;
    var bStaIsValid:Byte = _ ;
    @Length(3)
    var bnRes_2:Array[Byte] = _ ;
    // to here 64+16 bytes long.
    var nTotalRowCount:Long = _ ;	// total row count.
  @Length(40)
  var bnRes_3:Array[Byte] = _ ;
  } // GADB_COLSTAFILEHEAD;	// 128 bytes long.

  class GADB_COLSTAFILEMAP extends AncientData
  {
    var stHead = new GADB_COLSTAFILEHEAD;
    @Length(1)
    var stCTID:Array[GADB_CTIDCOLCNT] = _;	// may be many
  @Length(1)
  var stCol:Array[GADB_SIMPCOLSTATISTIC] = _;	// may be many columns.
  } // GADB_COLSTAFILEMAP;	// size is undetermined.

  class GADB_CTCOLSTA extends AncientData
  {
    var nCTID:Short = _;
    var nColCnt:Short = _;	// column count.
  @Length(4)
  var bnRes:Array[Byte] = _ ;
    var pstColSta_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstColSta_Data:Array[GADB_MEMCOLSTATISTIC] = _ // for pstColSta pointer ,struct:GADB_MEMCOLSTATISTIC;	// column statistics.
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;
  } // GADB_CTCOLSTA;	// child table column statistics. 16 bytes long.

  class GADB_TABLECOLSTA extends AncientData
  {
    var nCTCnt:Int = _;
    var nBlockCnt:Int = _;		// block count.
  var nTotalRowCnt:Long = _;	// total row count.
  var pstCT_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstCT_Data:Array[GADB_CTCOLSTA] = _ // for pstCT pointer ,struct:GADB_CTCOLSTA;
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;
    var nLastChangeTime:Int = _;
    var bInited:Byte = _ ;
    var bIsDirty:Byte = _ ;	// has dirty.
  @Length(2)
  var bnRes:Array[Byte] = _ ;
  } // GADB_TABLECOLSTA;	// 32 bytes long.

  // column statistics.
  // we write column statistics is not two much quickly.
  // at first, we loading column statistics from disk and pstCurSta and pstSnapShotSta
  // are the same. if one row has been deleted or one row has been modified
  // we change pstCurSta and not change pstSnapShotSta. we can copy
  // pstSnapShotSta when there is no active rows and current time > last change time.
  class GADB_COLSTAMGR extends AncientData
  {
    var stCrit = new GBASE_CRIT;
    var nLastSaveTime:Int = _;	// last saving time.	// set by save routine.
  var bInited:Byte = _ ;		// whether has been initialized.
  var bStaIsValid:Byte = _ ;	// whether current statistic is valid.
  var bTableHasError:Byte = _ ;	// whether table has been changed., must be same as file.
  // can be set by update routine.
  var bTableRowChanged:Byte = _ ;	// must be the same as file set by update routine.
  // clear by save routine.
  var nActiveRowCnt:Int = _;	// # of active row(add, delete and update).
  var nLastOpTime:Int = _;	// last add delete and update time. set by update routine.
  @Length(8)
  var bnRes1:Array[Byte] = _ ;
    var pstTable_Ptr:Int = _
    @IgnoreTransfer
    var pstTable_Data:Array[GADB_TABLEOBJECT] = _
    @Length(4)
    var bnRes_Pt:Array[Byte] = _ ;
    // to here is 128 bytes long.
    var stSta = new GADB_TABLECOLSTA;
    @Length(96)
    var bnRes2:Array[Byte] = _ ;
  } // GADB_COLSTAMGR;	// 256 bytes long.

  class GADB_BKREFCNT extends AncientData
  {
    var nBkSID:Int = _;		// block sid.
  var nRefCount:Int = _;	// reference count.
  } // GADB_BKREFCNT;

  class GADB_UPDBKLIST_FILEMAP extends AncientData
  {
    var stFileHead = new GADB_FILEHEADSCHEMA;
    var nBkSID:Int= _ ;
  } // GADB_UPDBKLIST_FILEMAP;	// file map.	// size is undetermined.

  // save all block sid to disk for all blocks' nRefCount>0.
  // the following structure is obsolete.
  class GADB_UPDBKLIST extends AncientData
  {
    var stCrit = new GBASE_CRIT;		// 96 bytes long.
  var nLastSaveTime:Int = _;
    var bIsDirty:Int = _;
    var nLastChangeTime:Int = _;
    var nUsedCnt:Int = _;
    var nBufCnt:Int = _;	// how many blocks can be saved in this list.
  var nUsedHighWaterMark:Int = _;		// reserved.
  var nMapBufLen:Int = _;	// in bytes.
  var nSaveBkCnt:Int = _;		// reserved.
  // to here is 128 bytes long.
  var pstBk_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstBk_Data:Array[GADB_BKREFCNT] = _ // for pstBk pointer ,struct:GADB_BKREFCNT;	// isa table block sid. and reference count.when
  // inserting, updating and deleteing rows we log the changed
  // block id in this array and save it on disk.
  var pstMap_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstMap_Data:Array[GADB_UPDBKLIST_FILEMAP] = _ // for pstMap pointer ,struct:GADB_UPDBKLIST_FILEMAP;	// file map.
  @Length(4*2)
  var bnRes_Pt:Array[Byte] = _ ;
    var bFileIsOK:Int = _;	// indicateds whether pstMap is ok and correctly saved on disk.
  var nRes:Int = _;
    var nMapAllocCnt:Long = _;
  } // GADB_UPDBKLIST;	// update block list. 128+32 bytes long.

  class GADB_TABLEINMEMDATA extends AncientData
  {
    var stMc = new GADB_ROWMODCACHE;	// mod cache
  // added on Nov.18, 2003
  var stLobMgr = new GLOB_SFILEMGR;	// lob manager.
  var stLobDF = new GADB_DFIDMAPINMEM;	// lob data file
  var stRowDF = new GADB_DFIDMAPINMEM;	// row data file
  var stOpTimeSta = new GADB_OPTIMESTA;	// operation time statistics.
  var stUserPriv = new NMUSER_OBJPRIV;
    var stColUuidMap = new GADB_COLUUIDMAP;
    var stColStaMgr = new GADB_COLSTAMGR;	// column statistics manager.
  } // GADB_TABLEINMEMDATA;	// table data

  // table definitions, the following structure is used only in memory
  // isa table's table id is 1. other child tables id is larger than or
  // euqal to 2
  class GADB_TABLEOBJECT extends AncientData
  {
    /*
    var stSkm = new GADB_SKMFILEOBJECT;			// skm file of the database, 1024 bytes
  var stIsa = new GADB_ISAFILEINMEM;			// isa data file, not true value for the row., 2.5 kb
  var stLobFileBkBuf = new GADB_MMTREEFORID;		// lob file block buffer.
  //
  @Length(192)
  var bnRes_oldbuf:Array[Byte] = _ ;
    var stSID = new GADB_SIDFILEMEMSTRUCT;			// sid file, 512 bytes
  var stModLog = new GADB_MODLOGINMEM;		// mod log, 512+256 bytes
  ///////////////////////to here is 4096+1024+128=5k+128byte=5120+128=5248 bytes///////
  // in the following range we can use 512+512-224=800 bytes///////
  var pstCT_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstCT_Data:Array[GADB_CHILDTABLEOBJECT] = _ // for pstCT pointer ,struct:GADB_CHILDTABLEOBJECT;			// child table structure
  var pstIdx_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstIdx_Data:Array[GADB_INDEXINMEM] = _ // for pstIdx pointer ,struct:GADB_INDEXINMEM;		// pointer to index, each index a file
  var pstCursor_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstCursor_Data:Array[GADB_INNER_CURSOR] = _ // for pstCursor pointer ,struct:GADB_INNER_CURSOR;		// pointer to cursor
  var pstLock_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstLock_Data:Array[GADB_RECORDLOCKSTRUCT] = _ // for pstLock pointer ,struct:GADB_RECORDLOCKSTRUCT;		// pointer to record lock
  var pstXAct_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstXAct_Data:Array[GADB_XACTADMINSTRUCT] = _ // for pstXAct pointer ,struct:GADB_XACTADMINSTRUCT;		// transaction administration
  //GADB_FLOBMGRSTRUCT		*pstLm;		// lob mgr, used when TABLETYPE==LOG and nBlobStgType==PACKED
  var tagGADB_DBOBJECT = new struct	*pstParentDB;	// parent database
  var ppstModLog_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var ppstModLog_Data:Array[GADB_MODLOGINMEM] = _ // for ppstModLog pointer ,struct:GADB_MODLOGINMEM;	// named mod log
  var pstInMemData_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstInMemData_Data:Array[GADB_TABLEINMEMDATA] = _ // for pstInMemData pointer ,struct:GADB_TABLEINMEMDATA;	// in memory data, added on Aug.21, 2003
  @Length(4*8)
  var bnRes_AllPointer:Array[Byte] = _ ;
    @Length(16)
    var bnRes_Pt:Array[Byte] = _ ;
    var nCTCount:Int = _;		// effective elements in pstCT
  var nCTBufCount:Int = _;	// buf size in pstCT
  var nIdxCount:Int = _;
    var nIdxBufCount:Int = _;
    var nEstimateRowSize:Int = _;
    var nPreLoadStartSID:Int = _;
    var nPreLoadEndSID:Int = _;
    var bCTCanBeFreed:Byte = _ ;
    var bIdxCanBeFreed:Byte = _ ;
    var bCritInited:Byte = _ ;
    var nMode:Byte = _ ;			// nMode, TBLMODE_XXX
  var nCurRowCount = new ga_uint8;	// current row count, does not include deleted row count
  var nMaxRowCountAllowed = new ga_uint8;	// maximum row count can be stored, may be license reason
  var nSkmRefCnt:Int = _;
    var nSkmChangeRefCnt:Int = _;
    var bSkmCritInited:Byte = _ ;
    var bSimpOpCirtInited:Byte = _ ;
    var nppstModLogBufSize:Byte = _ ;	// ppst mod log buf size
  var bppstCanBeFreed:Byte = _ ;	// whether ppstModLog can be freed
  var bHasLob:Byte = _ ;			// whether table has lob.
  var bInMemDataInited:Byte = _ ;	// added on Dec. 21, 2003
  var nStorageType:Byte = _ ;		// GADB_STGTYPE_xxx
  var nBackupFlag:Byte = _ ;		// table backup flag. TBLBKPFLAG_XX
  var nDisableReadCnt:Int = _;
    var nDisableWriteCnt:Int = _;
    @Length(110-20-18)
    var bnRes_1:Array[Byte] = _ ;	// reserved
  @Length(160)
  var bnRes_2:Array[Byte] = _ ;
    var stFQCrit = new GBASE_CRIT;	// fifo que critical section.
  var stSkmCrit = new GAFIS_CRITSECT;
    var bnRes_SkmCrit:Byte = _ [96-sizeof(GAFIS_CRITSECT)];
    var stSimpOpCrit = new GAFIS_CRITSECT;
    var bnRes_SimpOpCrit:Byte = _ [96-sizeof(GAFIS_CRITSECT)];
    /////////////after this threre's 128+64+32=224 bytes, do not add member below
    var stCrit = new GAFIS_CRITSECT;			// critical section to lock the table operations
  // lock table is it's long time operations
  var bnRes2:Byte = _ [96-sizeof(GAFIS_CRITSECT)];	// keep crit 96 bytes
  var stUserData = new GADB_USERDATASTRUCT;		// 128 bytes
  */
    @Length(6144)
    var bnRes:Array[Byte] = _
  } // GADB_TABLEOBJECT;	// 6KB=1024*6=6144 bytes

  final val TBLMODE_NORMAL = 0x0
  final val TBLMODE_DISABLED = 0x1
  final val TBLMODE_LOADING = 0x2	// in loading process.
  //#define	TBLMODE_DISABLEWRITE	0x2	// disable write
  //#define	TBLMODE_DISABLEREAD		0x4	// disable read.

  /////////////////////////////////////////////////////////
  final val CHKBLOB_OPT_NOCHANGE = 0x1

  final val TABLE_CHKOPT_ERRCONTINUE = 0x1	// if error occurred, continue.
  final val TABLE_CHKOPT_CHKLOBBEFORELOAD = 0x2	// checking lob before load. default is not.

  // GADB_TABLEOBJECT::nBackupFlag
  final val TBLBKPFLAG_NOBACKUP = 0x1	// no backup.

  // function declared in gafuncth.h
  //

}

package nirvana.hall.c.services.ganumia

import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services._
import nirvana.hall.c.services.ganumia.gadbcol.GADB_COLUMNSCHEMA
import nirvana.hall.c.services.ganumia.gadbtbl.GADB_TABLEOBJECT
import nirvana.hall.c.services.ganumia.gaitemop.{GADB_COLARRAYSTRUCT, GADB_COLPROPSTRUCT}
import nirvana.hall.c.services.ganumia.isafile.GADB_ISAROWSCHEMA
import nirvana.hall.c.services.gbaselib.gathrdop.GAFIS_CRITSECT
import nirvana.hall.c.services.gbaselib.gbasedef.GATIMERANGE
import nirvana.hall.c.services.ghpcbase.ghpcdef.AFISDateTime
import nirvana.hall.c.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-23
 */
object gadbrec {




  final val GADB_ROWADDOPT_KEEPOLD = 0x1	// update row

  final val ROWGETOPT_LEAVEFILEONDISK = 0x1	// leave large blob data on file
  final val ROWGETOPT_NOTLOADBLOB = 0x2	// do not blob data

  // the following structure only exists in memory it will not be
  // transmitted on network, to add data to database.
  // using this structure we can convert any structure to an array
  // of GADB_C2NMAPPER's.
  class GADB_C2NMAPPER extends AncientData
  {
    @Length(32)
    var szColName:String = _ ;
    var pBuf_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pBuf_Data:Array[Byte] = _ // for pBuf pointer ,struct:UCHAR;			// pointer to buffer
  @Length(4*1)
  var bnRes_Pointer:Array[Byte] = _ ;
    var bIsNULL:Byte = _ ;
    var bCanBorrowPointer:Byte = _ ;	// whether can borrow pointer, blob used only
  var nColID:Short = _ ;			// column id
  var nBufLen:Int = _ ;	// length of buf
  var nSaveDataFlag:Byte = _ ;	// GADB_C2NSDFLAG_XXX
  var nOutFlag:Byte = _ ;		// GADB_C2NOUTFLAG_XXX
  @Length(2)
  var bnRes:Array[Byte] = _ ;
    var nOffset:Int = _ ;		// update data used.
  @Length(8)
  var bnRes2:Array[Byte] = _ ;
  } // GADB_C2NMAPPER;	// size is 64 bytes

  /*
  typedef	union	tagGADB_PTUNION
  {
    UCHAR	**pDataPt;
    UCHAR	*pData;
    UCHAR	bnRes[8];
  } GADB_PTUNION;	// size of this structure is 8
  */
  class GADB_PTUNION extends AncientData
  {
    @Length(8)
    var bnRes:Array[Byte] = _
  } ;	// size of this structure is 8

  // we use the following structure to got data from database
  // when get data from database, if *pBuf is not null, we judge
  // whether its space is enough(its length is nBufLen), if not
  // enough(whether we can alloc memory, if not , then error,
  // else we alloc enough memory to hold data.
  // on case for this is for text conversion, it's structure has
  // a limited buffer, if a text item is very long, then it can
  // not hold by *pBuf, so we need alloc memory
  class GADB_N2CMAPPER extends AncientData
  {
    @Length(32)
    var szColName:String = _ ;	// [in/out]
  var stPt = new GADB_PTUNION;		// [in/out]pointer to buffer, this is second level address, because we need alloc memory
  var pRetLen_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pRetLen_Data:Array[Byte] = _ // for pRetLen pointer ,struct:UCHAR;	// [in]returned length, if pRetLen is null, then we do not set length
  @Length(4*1)
  var bnRes_Pointer:Array[Byte] = _ ;
    var nPtType:Byte = _ ;		// [in]GADB_N2CPTTYPE_XXX
  var bBufCanBeFree:Byte = _ ;	// [in/out]
  var bIsNULL:Byte = _ ;		// [out]
  var bNuminaAlloced:Byte = _ ;	// [out]whether *pBuf has memory alloced by numina system
  var nRetLenSize:Byte = _ ;	// [in]can be 1 2 4 8
  var nGetDataFlag:Byte = _ ;	// GADB_N2CGDFLAG_XXX
  var nOutFlag:Byte = _ ;		// GADB_N2COUTFLAG_XXX
  @Length(1)
  var bnRes0:Array[Byte] = _ ;		// reserved
  var nColID:Short = _ ;		// [in]column id
  var nTableID:Short = _ ;	// [in/out]child table id
  var nBufLen:Int = _ ;		// [in/out]length of buf, on return set the length of data
  var nOffset:Int = _ ;		// [in/out]we copy data from this to start
  @Length(4)
  var bnRes:Array[Byte] = _ ;		// reserved
  } // GADB_N2CMAPPER;	// size 72 bytes

  // GADB_N2CMAPPER::nGetDataFlag
  final val GADB_N2CGDFLAG_GETGIVENLENDATA = 0x1	// if nBufLen is not enough long, then we got only those part.
  // GADB_N2CMAPPER::nOutFlag
  final val GADB_N2COUTFLAG_DATACUT = 0x1	// data is only part.

  // single case : using stPt.pData, if the final data length is larger then the size of
  // this buffer(it may be zero), then if bBufCanBeFree we free(stPt.pData) and
  // then stPt.pData = malloc(we may also Realloc this part of memory)
  // cfixed : using stPt.pData, no memory alloc
  // cPt    : using stPt.pDataPt, if *stPt.pDataPt==NULL or it's size is not enough
  //          we alloc other memory
  // cfixedpt: using stPt.pData, if buf size is not enough we alloc memory and set it
  //           to stPt.pDataPt., in fact, stPt.pDataPt = &stPt.pData;
  final val GADB_N2CPTTYPE_SINGLE = 0	// stPt's value is not any c structure member
  final val GADB_N2CPTTYPE_CFIXED = 1
  final val GADB_N2CPTTYPE_CPT = 2
  final val GADB_N2CPTTYPE_CFIXEDPT = 3

  // the following structure will exist both in memory and on disk.
  // if the structure is stored on disk, only the first byte is
  // used, that it is only bIsUsed item is used on disk.
  // if the structure is mapped into memory, all values must
  // have correct values. and SID <-->(FildID, OID, BKID)
  // can convert mutually.
  // though the size of the structure is 16 bytes long and if a table has
  // 10 child table it will have 160 extra bytes wasted(for 50M records,
  // it will be 50M*160B=8GB wasted). but it worthy it.
  // 2003.5.17 : normally, a table can 1-5 ct tables
  class GADB_ROWHEADSCHEMA extends AncientData
  {
    var bIsUsed:Byte = _ ;	// whether the record is used
  @Length(1)
  var bnRes0:Array[Byte] = _ ;
    var nFileID:Short = _ ;	// file id
  var nBKID:Int = _ ;	// block id
  @Length(SID_SIZE)
  var nSID:String = _ ;	// sequence id
  var nOID:Short = _ ;	// offset in a block
  } // GADB_ROWHEADSCHEMA;	// size of this structure is 16 bytes

  // numina5.0 format of rowhead.
  class GADB_ROWHEADSCHEMA_50 extends AncientData
  {
    var bIsUsed:Byte = _ ;	// whether the record is used
  @Length(1)
  var bnRes0:Array[Byte] = _ ;
    var nFileID:Short = _ ;	// file id
  var nBKID:Int = _ ;	// block id
  var nOID:Short = _ ;	// offset in a block
  @Length(2)
  var bnRes1:Array[Byte] = _ ;	// reserved
  var nSID:Int = _ ;	// sequence id
  } // GADB_ROWHEADSCHEMA_50;	// size of this structure is 16 bytes

  // in order to allocate a row we must know it's size
  // and cast the memory to GADB_ROWSCHEMA type.
  class GADB_ROWSCHEMA extends AncientData
  {
    var stHead = new GADB_ROWHEADSCHEMA;	// 16 bytes
  @Length(8)
  var bnData:Array[Byte] = _ ;	// 8 bytes reserved
  } // GADB_ROWSCHEMA;	//

  // row buffer structure store's GADB_ROWSCHEMA structure
  // vertical mode, one table, it's also used to cache data

  // the following structure has 11 effective items
  class GADB_CTROWBUF extends AncientData
  {
    var pData_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pData_Data:Array[Byte] = _ // for pData pointer ,struct:char;		// data pointer to row data, pointer may be anything, proceed by nItemSize
  @Length(4)
  var bnRes_Pointer:Array[Byte] = _ ;
    var nItemSize:Int = _ ;		// size of each item
  var nRowCountInBuf:Int = _ ;	// how many buffer can be stored
  var nCurRowCount:Int = _ ;	// current valid row count
  var nTableID:Short = _ ;		// table id(child table id)
  var bDataCanBeFreed:Byte = _ ;	// data can be freed
  var bBlockMode:Byte = _ ;			// if it's true, then pBlock points to whole child table block
  // and bDataCanBeFreed must be false, bBlockCanBeFreed must be
  // true, pBlock can not be null, nRowCountInBuf==nCurRowCount=
  // MaxRowPerPage.
  var bInited:Byte = _ ;			//
  @Length(7)
  var bnRes0:Array[Byte] = _ ;			// reserved
  //////////////////// the following items are used by cache only, to here is 32 bytes///////////
  var bIsCached:Byte = _ ;
    var bBlockCanBeFreed:Byte = _ ;
    var nFileID:Short = _ ;	// file id
  var nBKID:Int = _ ;	// block id
  var pBlock_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pBlock_Data:Array[Byte] = _ // for pBlock pointer ,struct:char;			// pointer to block a pointer to GADB_BLOCKSTRUCT structure
  @Length(4)
  var bnRes_Block:Array[Byte] = _ ;
    var nBlockSize:Int = _ ;		// size of block
  var nBkSID:Int = _ ;			// sequential block id.
  @Length(SID_SIZE)
  var nSID:String = _ ;		// sid.
  @Length(8-SID_SIZE)
  var bnRes3:Array[Byte] = _ ;	// true reserved.
  } // GADB_CTROWBUF;	// size of this structure is 64 bytes
  type GADB_ROWBUFSTRUCT = GADB_CTROWBUF


  // many child tables, vertical mode, we can use the following structure
  // to transfer one table(including it's child table data to any other app).
  // it's also used for caching data
  // if it's used for caching data, each GADB_MULTIROWBUFSTRUCT corresponds to one
  // disk block(table level)
  class GADB_MULTIROWBUFSTRUCT extends AncientData
  {
    var nTableCount:Short = _ ;	// table count
  var bInited:Byte = _ ;		// whether this structure has been initialized, call GADB_MROWBUF_InitByTable
  @Length(1)
  var bnRes:Array[Byte] = _ ;		// reserved
  var nRowCount:Int = _ ;	// number of rows in each pstRowBuf, is the same as pstRowBuf->nCurRowCount.
  var pstRowBuf_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstRowBuf_Data:Array[GADB_ROWBUFSTRUCT] = _ // for pstRowBuf pointer ,struct:GADB_ROWBUFSTRUCT;
  @Length(4)
  var bnRes_pstRowBuf:Array[Byte] = _ ;
    /////////////// the following is used by cache only////////////
    @Length(2)
    var bnRes_1:Array[Byte] = _ ;
    var nFileID:Short = _ ;	// file id
  var nBKID:Int = _ ;	// block id
  var pstBlob_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstBlob_Data:Array[GADB_COLARRAYSTRUCT] = _ // for pstBlob pointer ,struct:GADB_COLARRAYSTRUCT;	// pstBlob[i] points to i'th row's blob
  @Length(4)
  var bnRes_pstBlob:Array[Byte] = _ ;
    var nBkSID:Int = _ ;			// block SID
  @Length(28)
  var bnRes_2:Array[Byte] = _ ;
  } // GADB_MULTIROWBUFSTRUCT;	// size of this structure is 64 bytes.

  // the following structure is used only for buffer mgr
  //typedef	struct	tagGADB_MROWBUFCACHEITEM
  //{
  //	GADB_MULTIROWBUFSTRUCT	stRow;	// 64 bytes
  //	int						bCritInited;
  //	int						nRes;
  //	GAFIS_CRITSECT			stCrit;	// critical section
  //	UCHAR					bnRes_Crit[96-sizeof(GAFIS_CRITSECT)];
  //} GADB_MROWBUFCACHEITEM;	// size of this structure is 64+96+8

  class GADB_FULLROWSCHEMA_OLD extends AncientData
  {
    @Length(4)
    var bnRes_0:Array[Byte] = _ ;			// reserved
  var nRowSize:Int = _ ;		// row size
  @Length(SID_SIZE)
  var nSID:String = _ ;			// sid
  @Length(2)
  var bnRes_SID:Array[Byte] = _ ;
    @Length(2)
    var bnRes_1:Array[Byte] = _ ;			// reserved 8 bytes
  var pstRowData_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstRowData_Data:Array[GADB_ROWSCHEMA] = _ // for pstRowData pointer ,struct:GADB_ROWSCHEMA;	// pointer to row data
  @Length(4)
  var bnRes_Pointer:Array[Byte] = _ ;
    var nTableID:Short = _ ;		// table id
  var bRowDataCanBeFreed:Byte = _ ;	// whether row data can be freed
  @Length(5)
  var bnRes_2:Array[Byte] = _ ;			// whether blob can be freed
  } // GADB_FULLROWSCHEMA_OLD;	// size of this structure is 32 bytes
  //#endif

  class GADB_FULLROWSCHEMA extends AncientData
  {
    @Length(1)
    var bnRes_0:Array[Byte] = _ ;			// reserved
  var nColFlagLen:Byte = _ ;		// length of pbnColFlag in byte
  var bRowDataCanBeFreed:Byte = _ ;	// whether row data can be freed
  var bColFlagCanBeFreed:Byte = _ ;	// whether pbnColFlag can be freed.
  var nRowSize:Int = _ ;		// row size
  @Length(6)
  var bnRes_SID:Array[Byte] = _ ;
    var nCTID:Short = _ ;		// table id
  var pstRowData_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstRowData_Data:Array[GADB_ROWSCHEMA] = _ // for pstRowData pointer ,struct:GADB_ROWSCHEMA;	// pointer to row data
  var pbnColFlag_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pbnColFlag_Data:Array[Byte] = _ // for pbnColFlag pointer ,struct:UCHAR;	// column flag, if pbnColFlag is not null, then each bit
  // corresponding to one column in pstRowData to indicate
  // whether pstRowData has valid value.
  @Length(4*2)
  var bnRes_Pointer:Array[Byte] = _ ;
  } // GADB_FULLROWSCHEMA;	// size of this structure is 32 bytes

  // horizontal mode of row , multi table
  class GADB_MULTIFULLROWSCHEMA extends AncientData
  {
    var nTableCount:Short = _ ;	// number of tables
  var bRowCanBeFreed:Byte = _ ;	// whether row can be freed
  var bBlobCanBeFreed:Byte = _ ;	// reserved.
  var nTableBufCount:Short = _ ;	// how many child tables row can be stored
  var nBlobCount:Short = _ ;		// number of blob's included
  var pstRow_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstRow_Data:Array[GADB_FULLROWSCHEMA] = _ // for pstRow pointer ,struct:GADB_FULLROWSCHEMA;
  @Length(4)
  var bnRes_pstRow:Array[Byte] = _ ;
    @Length(SID_SIZE)
    var nSID:String = _ ;		// sequence  id
  @Length(8-SID_SIZE)
  var bnRes_SID:Array[Byte] = _ ;
    var pstBlob_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstBlob_Data:Array[GADB_COLPROPSTRUCT] = _ // for pstBlob pointer ,struct:GADB_COLPROPSTRUCT;
  @Length(4)
  var bnRes_pstBlob:Array[Byte] = _ ;
    var nBlobBufCount:Short = _ ;	// blob buffer count
  var nOID:Short = _ ;		// offset id in block
  @Length(3)
  var bnRes_3:Array[Byte] = _ ;	// reserved
  var bIsaCanBeFreed:Byte = _ ;		//  whether pstIsa can be freed.
 var pstIsa_Ptr:Int = _ ;	// pointer to isa row.
    @IgnoreTransfer
    var pstIsa_Data:Array[GADB_ISAROWSCHEMA] = _ ;	// pointer to isa row.
  @Length(4)
  var bnRes_IsaPt:Array[Byte] = _ ;
  } // GADB_MULTIFULLROWSCHEMA;	// size of this structure is 32+16 bytes
  // GADB_MULTIROWBUFSTRUCT is more efficient than GADB_MULTIFULLROWSCHEMA

  class GADB_BLOBCOL extends AncientData
  {
    var nBlobSize:Int = _ ;	// size of BLOB
  var tGMTime:Int = _ ;		// UTC(GMT)time, modification time.( add, update ( including update to null))
  } // GADB_BLOBCOL;	// size of this structure is 8 bytes long

  class GADB_SETCOLUMNOPTION extends AncientData
  {
    // if we can not borrow pointer we will new a memory to store
    // blob data
    var bCanBorrowPointer:Byte = _ ;	// [in]when set a blob structure whether we can store the pointer
  var bSetToNULL:Byte = _ ;			// [in]set to null?
  var bIsBlob:Byte = _ ;			// temporary use
  var bNotSetBlob:Byte = _ ;		// not set blob
  var nFlag:Byte = _ ;				// SETCOLOPT_FLAG_XXX
  @Length(3)
  var bnRes:Array[Byte] = _ ;
    var stBlobInfo = new GADB_BLOBCOL;	// size is 8
  } // GADB_SETCOLUMNOPTION;	// size is 16 bytes

  final val SETCOLOPT_FLAG_ISCOPY = 0x1
  // when the buffer length in column is small then the data provided, then
  // cut that data.
  final val SETCOLOPT_FLAG_CUTSOURCE = 0x2	// if cell too long, we  cut it.
  final val SETCOLOPT_FLAG_NULLLONGSRC = 0x4	// if cell too long, we set it to null.

  // the following structure must be aligned at 8 bytes boundary
  class GADB_MEMBLOB extends AncientData
  {
    /*
    union	{
      var pData_Ptr:Int = _ //using 4 byte as pointer
      @IgnoreTransfer
      var pData_Data:Array[Byte] = _ // for pData pointer ,struct:UCHAR;			// pointer to blob data, can be NULL
      @Length(8)
      var bnRes:Array[Byte] = _ ;
      var nPos:Int = _ ;		// position result form is FLATMEMORY
    } // u;
    */
    @IgnoreTransfer
    var pData_Data:Array[Byte] = _
    var stLobInfo:GADB_BLOBCOL = _	;	// size is 8 bytes long
    @Length(8)
    var bnRes:Array[Byte] = _
  } //GADB_MEMBLOB;	// size of this structure is 16 bytes long

  // blob pointer.
  class GADB_BLOBPT extends AncientData
  {
    var pPt_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pPt_Data:Array[Byte] = _ // for pPt pointer ,struct:UCHAR;
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;
  } // GADB_BLOBPT;	// blob pt. 8 bytes long.

  class GADB_GETCOLUMNOPTION extends AncientData
  {
    var bIsNULL:Byte = _ ;	// [out]not exist
  var bGetBlob:Byte = _ ;	// [in]get blob or not
  var bIsBlob:Byte = _ ;	// [out]return value
  var bDataCanBeFreed:Byte = _ ;	// [out]data can be freed
  var bNewMemory:Byte = _ ;			// [out]get data new memory, do not use memory in rowschema, the caller must free it
  var bLeaveFileOnDisk:Byte = _ ;	// [in]whether leave file on disk
  var bLeavedFileOnDisk:Byte = _ ;	// [out]has leave the file on disk
  var nFlag:Byte = _ ;		// flag, added on July 20, 2003,, GETCOLOPT_FLAG_xxx, [in]
  var tMinModTime:Int = _ ;	// minimum modification time of blob, added on July 20, 2003, [in]
  var nOutFlag:Byte = _ ;		// GETCOLOPT_OUTFLAG_XXX, [out], added on July 20, 2003
  @Length(3)
  var bnRes:Array[Byte] = _ ;
    // the following items are added on July 22, 2003
    var stBlobInfo = new GADB_BLOBCOL;	// [out], blob info, size is 8 bytes.
  @Length(8)
  var bnRes3:Array[Byte] = _ ;
  } // GADB_GETCOLUMNOPTION;	// 32 bytes

  // GADB_GETCOLUMNOPTION::nFlag
  final val GETCOLOPT_FLAG_USEMINMODTIME = 0x1
  final val GETCOLOPT_FLAG_GETINMEMBLOB = 0x2	// get in memory blob, does not read from disk
  final val GETCOLOPT_FLAG_CUTSOURCE = 0x4	// if target databuf is not enough to hold whole data
  // then cut data.

  final val GETCOLOPT_FLAG_LOADLOB_LOCKROW = 0x8	// lock row while loading lob.
  // GADB_GETCOLUMNOPTION::nOutFlag
  final val GETCOLOPT_OUTFLAG_NOTGOT = 0x1	// if this flag set then it must be blob
  final val GETCOLOPT_OUTFLAG_SOURCECUT = 0x2	// source data has been cut.

  // option for getbykey and getbysid. ensure comply withROWLOBOPT_XXX
  final val GADB_GETROWOPT_CUTSOURCE = 0x1	// cutdata source.
  final val GADB_GETROWOPT_NOLOCK = 0x2

  class GADB_COLOFFSETSTRUCT extends AncientData
  {
    var nNullByteOffset:Int = _;	// null byte offset
  var nColOffset:Int = _;			// column offset
  var nColSize:Int = _;			// column size
  var nNullMask:Byte = _ ;			// null bit mask
  var bIsBlob:Byte = _ ;			// whether is blob or not
  var nDataType:Byte = _ ;
    var bIsISACol:Byte = _ ;			// reserved
  } // GADB_COLOFFSETSTRUCT;		// size is 16 bytes

  // select items, for pointers we need some memory to store it's length
  class GADB_SELRESITEM extends AncientData
  {
    @Length(32)
    var szItemName:String = _ ;		// name of the item
  var nDataOffset:Int = _ ;		// offset of the item from record start
  var nDataLen:Int = _ ;		// maximum length of data can be hold
  var nLengthOffset:Int = _ ;	// offset of the length part, if -1 then no length part
  // if nLengthByte==0 then no length part
  var nLengthByte:Byte = _ ;		// length byte 1 2 4 8
  var nDataType:Byte = _ ;			// added on July.2, 2003, GADBCS_DATATYPE_XXX
  var nFormFlag:Byte = _ ;			// SELRESITEM_FFLAG_XXX, judge by ==.
  var nFlag:Byte = _ ;				// SELRESITEM_FLAG_XXX, judge by or
  } // GADB_SELRESITEM;	// size is 48 bytes

  // value for nFormFlag, when using this flag we pstItem->nFormFlag==SELRESITEM_FFLAG_ISMEMBLOB
  // then width is 16. and nDataLen contains 16.
  // the length of the blob is contains in GADB_MEMBLOB format.
  // if we get data from database, we always treats blob as memblob and non-blob as normal
  // but if user wants to save data to blob, we can store anything in memblob.
  final val SELRESITEM_FFLAG_NORMAL:Byte = 0x0	//
  final val SELRESITEM_FFLAG_ISMEMBLOB :Byte= 0x1	//
  final val SELRESITEM_FFLAG_LOB:Byte = 0x2	// normal lob.

  // GADB_SELRESITEM::nFlag
  final val SELRESITEM_FLAG_MIXLOBPT = 0x1	// mix lob pt.
  final val SELRESITEM_FLAG_PARTIALLOB = 0x2	// read only partial lob, if nDataLen is zero, then
  // read blob info only. else read that length lob.
  // only used if column is lob.

  // used to store data got by free form
  class GADB_MEMFORMROW extends AncientData
  {
    var stHead = new GADB_ROWHEADSCHEMA;
    @Length(8)
    var bnData:Array[Byte] = _ ;
  } // GADB_MEMFORMROW;	// size is not fixed, 24

  // the following structure is an helper structure
  class GADB_DATAINRES extends AncientData
  {
    var pData_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pData_Data:Array[Byte] = _ // for pData pointer ,struct:UCHAR;
  @Length(4)
  var bnRes_Pointer:Array[Byte] = _ ;
    var nDataLen:Int = _;	// data length
  var bColIsNULL:Byte = _ ;	// whether column is null, high priority
  var bColHasBlobInfo:Byte = _ ;	// whether column has blob info
  var bColHasData:Byte = _ ;		// whether column contain data.(data may be null or not null);
  var bDataCanBeFree:Byte = _ ;		// whether data can be free, usually not.
  var stBlobInfo = new GADB_BLOBCOL;	// blob info.
  var nDataType:Byte = _ ;			// data type.
  @Length(7)
  var bnRes2:Array[Byte] = _ ;
  } // GADB_DATAINRES;	// size of this structure is 32 bytes

  // get data options
  class GADB_GETDATAOPT extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure
  var nMaxToGot:Int = _ ;	// at most this # of records got
  @Length(64-8)
  var bnRes:Array[Byte] = _ ;	// reserved
  } // GADB_GETDATAOPT;	// size of this structure is 64 bytes long

  // when we call select we store the result in the following structure
  class GADB_SELRESULT extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure
  @Length(4)
  var bnRes0:Array[Byte] = _ ;	// reserved
  var pDataBuf_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pDataBuf_Data:Array[Byte] = _ // for pDataBuf pointer ,struct:UCHAR;	// data buffer	can be null, if null then no data.
    @IgnoreTransfer
  var pDataBuf_Result:Array[GADB_MEMBLOB] = _
  var pstItem_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstItem_Data:Array[GADB_SELRESITEM] = _ // for pstItem pointer ,struct:GADB_SELRESITEM;
  @Length(2*4)
  var bnRes_Pointer:Array[Byte] = _ ;
    var nFlag:Byte = _ ;				// SELRES_FLAG_XXX
  var nFreeOption:Byte = _ ;		// SELRES_CANBEFREE_XX
  @Length(2)
  var bnRes1:Array[Byte] = _ ;			// reserved
  var nSegSize:Int = _ ;		// size of each segment, row size
  var nRecGot:Int = _ ;			// how many records have been got, row count
  var nResItemCount:Int = _ ;	// how many result items got, # of pstItem
  var nRecBufCount:Int = _ ;	// row count in pDataBuf(max can hold)
  // the # of actually hold is nRecGot.
  var nFormFlag:Byte = _ ;			// form flag	SELRES_FORMFLAG_ISFREE, judge by ==
  var nItemFlag:Byte = _ ;			// SELRES_ITEM_XXXX
  var nReadFlagRowSize:Short = _ ;	// read row flag size
  var pReadFlag_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pReadFlag_Data:Array[Byte] = _ // for pReadFlag pointer ,struct:UCHAR;				// read flag, see description below
  @Length(4)
  var bnRes_PtReadFlag:Array[Byte] = _ ;
    // the following two items used only in flat form case
    var nDataBufSize:Int = _ ;		// memory alloced for pDataBuf. nRecBufCnt * nSegSize
  // may less than nDataBufSize.
  var nNextAvailBlobPos:Int = _ ;	// used in flag format.
  } // GADB_SELRESULT;	// size of this structure is 64 bytes

  // GADB_SELRESULT::pReadFlag
  // this flag is used only when GADB_READROWOPT::nFlag's READROW_FLAG_USEMINMODTIME
  // flag set. if not set all columns will be read, so no need to have flag to indicate
  // which column has read and which column has not read. so when reading data, only
  // lob columns may be set not read. other columns will be set to read all.

  // GADB_SELRESULT::nFlag
  final val SELRES_FLAG_FLATMEMORY : Byte = 0x1	// if nFormFlag is SELRES_FORMFLAG_ISFREE,
  // and if there is blobs, then memory used by
  // blob is borrowed in pDataBuf and not allocated
  // individually.
  final val SELRES_FLAG_BLOBISPT : Byte = 0x2	// used only when SELRES_FLAG_FLATMEMORY is set.
  // if this flag set. then memblob using pointer not
  // offset. i.e., GADB_MEMBLOB::pData is used (called
  // format pt). else GADB_MEMBLOB::nPos is used. called
  // format offset.
  final val SELRES_FLAG_ROWHEADOK : Byte = 0x4	// only usefully in freerow format, each row head
  // is GADB_ROWHEADSCHEMA, and this flag specify
  // that sid in that can be used.

  final val SELRES_FLAG_BLOBHASDATA : Byte = 0x4	// some columns has pointer.
  // the following flag is used in flat form.
  // on some cases, we need change some blob pointer in lob, and for flat form
  // even it's pt, the pt is in the range
  // pstRes->pDataBuf and pstRes->pDataBuf+nDataBufSize.
  // so if this flag set, we also check lob pointer.
  final val SELRES_FLAG_MIXBLOBPT : Byte = 0x10

  // GADB_SELRESULT::nFormFlag, judge by nFormFlag==SELRES_FORMFLAG_xx
  final val SELRES_FORMFLAG_NORMAL : Byte = 0x0	// normal

  // if is free form, we can cast each row to GADB_MEMFORMROW, which has an header. and null flag.
  // so we can judge whether a row is used or not.
  final val SELRES_FORMFLAG_ISFREE : Byte = 0x1	// free form

  // GADB_SELRESULT::nItemFlag, can be or'ed. indicates given pointer can be used or not.
  final val SELRES_ITEM_DATABUF : Byte = 0x1
  final val SELRES_ITEM_RESITEM : Byte = 0x2
  final val SELRES_ITEM_READFLAG : Byte = 0x4

  // GADB_SELSTATEMENT::nResColFmt, GADB_READROWOPT::nResColFmt
  final val RESCOLFMT_PREPARED : Byte = 0x0	// format has been prepared
  final val RESCOLFMT_GETALL : Byte = 0x1	// read all column
  final val RESCOLFMT_NAMEGIVEN : Byte = 0x2	// read only those columns that name given


  // GADB_SELRESULT::nFreeOption
  final val SELRES_CANBEFREE_RESITEM : Byte = 0x1
  final val SELRES_CANBEFREE_DATABUF : Byte = 0x2
  final val SELRES_CANBEFREE_READFLAG : Byte = 0x4
  final val SELRES_CANBEFREE_LOB : Byte = 0x8

  class GBDB_INTERNAL_TIMESEL extends AncientData
  {
    var stCrtTime = new GATIMERANGE;	// create time, 16 bytes
  var stUpdTime = new GATIMERANGE;	// update time, 16 bytes
  var bUseCreateTimeRange:Byte = _ ;
    var bUseUpdateTimeRange:Byte = _ ;
    var bIsOr:Byte = _ ;	// true is one item is satisfied.
  @Length(29)
  var bnRes0:Array[Byte] = _ ;
  } // GADB_INTERNAL_TIMESEL;	// size is 64 bytes long

  // for a well designed schema, a column has pm properties is enough.
  class GADB_READROWOPT extends AncientData
  {
    @Length(4)
    var bnRes0:Array[Byte] = _ ;		// reserved.
  var nGetFlag:Byte = _ ;		// get column according colunm type. READROW_GETFLAG_XX
  var nFlag:Byte = _ ;			// READROW_FLAG_XXX
  var nMaxMemInMB:Short = _ ;	// maximum memory can be used to read data. can read 64GB data max
  var tMinModTime:Int = _ ;	// min modification time. used for getting blob. if the modification
  // time logged in blob is early then this time, then that blob will
  // not be got.READROW_FLAG_USEMINMODTIME must be set.
  var nPosBy:Byte = _ ;			// READROW_POSBY_XXX
  var nResColFmt:Byte = _ ;		// colformat in RESCOLFMT_XXX
  var nViewAsBlobLen:Short = _ ;	// if one column width(not lob) exceed this vlaue, we can store
  // it in memblob form. ( flag must be set) in bytes. this value
  // will be used if its value is larger then 0
  @Length(SID_SIZE)
  var nStartSID:String = _ ;	// if nPosBy is READROW_POSBY_SIDRANGE, then using these
  // two items.
  @Length(SID_SIZE)
  var nEndSID:String = _ ;		// 12 bytes used,
  var nFlagEx:Byte = _ ;			// READROW_FLAGEX_XXX
  @Length(3)
  var bnRes2:Array[Byte] = _ ;		// reserved
  } // GADB_READROWOPT;	// size is 32 bytes

  // GADB_READROWOPT::nGetFlag
  final val READROW_GETFLAG_GETPMCOL : Byte = 0x1	// get parallel search columns
  final val READROW_GETFLAG_GETCLOB : Byte = 0x2	// get lob text
  final val READROW_GETFLAG_GETBLOB : Byte = 0x4	// get binary lob.

  final val READROW_GETFLAG_GETLOB = (READROW_GETFLAG_GETBLOB|READROW_GETFLAG_GETCLOB)

  class GADB_SELSTATEMENT extends AncientData
  {
    @Length(2048)
    var szStatement:String = _ ;	// a statement can not exceed this size
  @Length(SID_SIZE)
  var nStartSID:String = _ ;		// start sid
  @Length(8-SID_SIZE)
  var bnRes_StartSID:Array[Byte] = _ ;
    @Length(SID_SIZE)
    var nEndSID:String = _ ;			// ending sid
  @Length(8-SID_SIZE)
  var bnRes_EndSID:Array[Byte] = _ ;
    // the following items are added on jun.19, 2003
    //var stTimeSel = new GADB_INTERNAL_TIMESEL;	// 64 bytes long
    @Length(64)
    var stTimeSel:Array[Byte] = _;	// 64 bytes long
  var nMaxToGet:Int = _ ;		// at most this number of records get
  var bDirIsBackward:Byte = _ ;		// cursor direction is backward
  var nFlag:Byte = _ ;				// GADB_STMTFLAG_XXX
  @Length(2+8)
  var bnRes0:Array[Byte] = _ ;		// reserved
  var stReadOpt = new GADB_READROWOPT;	// read row options
  // use nMaxMemInMB, tMinModTime, nResColFmt
  @Length(512-128)
  var bnRes:Array[Byte] = _ ;		// reserved
  } // GADB_SELSTATEMENT;	// size is 2048 +512 bytes

  final val GADB_STMTFLAG_NULLASTRUE : Byte = 0x1	// default is false.
  // GADB_READROWOPT::nFlag
  final val READROW_FLAG_USEMINMODTIME : Byte = 0x1	// using tMinModTime, for blobs, if the modification
  // time if before then we will not got that blob
  final val READROW_FLAG_ISFREEFORMROW : Byte = 0x2
  final val READROW_FLAG_GETBLOBINFOONLY : Byte = 0x4	// only get blob info, not blob it self

  // if one column length is larger then given length we prefer it's to be saved in
  // memblob format. this is useful when there is a lot of null values for that column
  final val READROW_FLAG_PREFERMEMBLOB : Byte = 0x8

  // used for non-selection case. if one row is deleted, then does not add one row to the
  // result. if specified the reader will have the responsibility to position given
  // key or sid to specific row in result.
  final val READROW_FLAG_NOTGETDELROW : Byte = 0x10

  // when getting each row, there may be error generated, if
  // the following flag is set, then error will omitted. else will fail.
  final val READROW_FLAG_CONTINUEONERROR : Byte = 0x20

  // drop not exist column
  final val READROW_FLAG_DROPNONEXISTCOL : Byte = 0x40

  // reading partial lob only.
  final val READROW_FLAG_PARTIALLOB = 0x80	// lower priority then READROW_FLAG_GETBLOBINFOONLY.
  // if READROW_FLAG_GETBLOBINFOONLY is specified,
  // then this flag will be omitted.

  // GADB_READROWOPT::nFlagEx
  final val READROW_FLAGEX_USERBUF : Byte = 0x1	// when getting data, the user has supplied buffer.
  final val READROW_FLAGEX_COLFIXEDORDER : Byte = 0x2	// column has fixed order.

  // this flag has effect on memory blob only. if set, we store blob in pDataBuf(first
  // allocated enough memory) and pointer in memory blob can not freed. this is useful
  // when transmitting one network.
  // NOTE : not implemented.
  //#define	READROW_FLAG_FLATMEMORY			0x20

  // GADB_READROWOPT::nPosBy
  final val READROW_POSBY_KEY : Byte = 0x0	// read row by key
  final val READROW_POSBY_SID : Byte = 0x1	// read row by sid
  final val READROW_POSBY_SELECT : Byte = 0x2	// read row by select
  final val READROW_POSBY_SIDRANGE : Byte = 0x3	// read row by sid range.

  class GADB_SAVEROWOPT extends AncientData
  {
    var cbSize:Int = _ ;
    // the following two items used when savemode is SAVEROW_SAVEMODE_UPDATELIB or
    // SAVEROW_SAVEMODE_SYNCUPDATE or SAVEROW_SAVEMODE_ASYNCUPDATE to reserve deleted
    // records.
    var nDestDBID:Short = _ ;
    var nDestTID:Short = _ ;
    var nOldRowExistOption:Byte = _ ;		// SAVEROW_OREOPT_xxxx
  var nColNotExistOption:Byte = _ ;		// SAVEROW_CNEOPT_xxx
  var nOldRowNotExistOption:Byte = _ ;	// SAVEROW_ORNOPT_XXX
  var nPosBy:Byte = _ ;					// SAVEROW_POSBY_XXX
  var nPosFlag:Byte = _ ;				// SAVEROW_POSFLAG_XX
  var nFlag:Byte = _ ;					// SAVEROW_FLAG_XXX
  var nSaveMode:Byte = _ ;				// SAVEROW_SAVEMODE_xx, highest priority
  var nOption:Byte = _ ;				// SAVEROW_OPT_XX
  @Length(SID_SIZE)
  var nStartSID:String = _ ;
    @Length(SID_SIZE)
    var nEndSID:String = _ ;
    var nOldRowExistSameOption:Byte = _ ;	// SAVEROW_OREOPT_SAME_XXX
  var nOldRowExistNotSameOption:Byte = _ ;	// SAVEROW_OREOPT_NOTSAME_XXX
  var nBkpDelRowType:Byte = _ ;			// SAVEROW_BKPDEL_XXX
  var nCellFlag:Byte = _ ;				// SAVEROW_CELLFLAG_XXX
  } // GADB_SAVEROWOPT;	// size is 32 bytes

  class GADB_COPYROWOPT extends AncientData
  {
    var cbSize:Int = _ ;
    @Length(4)
    var bnRes0:Array[Byte] = _ ;	// reserved
  var bDelOldRow:Byte = _ ;	// delete old row if copied
  var bDelOnlyAllFinished:Byte = _ ;	//
  @Length(22)
  var bnRes2:Array[Byte] = _ ;
  } // GADB_COPYROWOPT;	// size is 32 bytes long

  // GADB_SAVEROWOPT::nOldRowExistOption
  final val SAVEROW_OREOPT_FAIL : Byte = 0x0
  final val SAVEROW_OREOPT_DROP : Byte = 0x1
  final val SAVEROW_OREOPT_REPLACE : Byte = 0x2
  final val SAVEROW_OREOPT_UPDATE : Byte = 0x3
  final val SAVEROW_OREOPT_CHECKTYPE : Byte = 0x4	// whether is same or not same.

  // GADB_SAVEROWOPT::nOldRowExistSameOption is used when not using SID as position parameter
  // GADB_SAVEROWOPT::nOldRowExistSameOption
  // if old and new are same record(by uuid and/or key)
  final val SAVEROW_OREOPT_SAME_FAIL : Byte = 0x0
  final val SAVEROW_OREOPT_SAME_REPLACE : Byte = 0x1	// replace old record
  final val SAVEROW_OREOPT_SAME_UPDATE : Byte = 0x2	// update old record
  final val SAVEROW_OREOPT_SAME_DROP : Byte = 0x3	// drop new record

  // different row at same position
  // GADB_SAVEROWOPT::nOldRowExistNotSameOption is used when not using SID as position parameter
  final val SAVEROW_OREOPT_NOTSAME_FAIL : Byte = 0x0	// update old record
  final val SAVEROW_OREOPT_NOTSAME_REPLACE : Byte = 0x1	// update old record
  final val SAVEROW_OREOPT_NOTSAME_DROP : Byte = 0x2	// drop new record.

  // GADB_SAVEROWOPT::nColNotExistOption
  final val SAVEROW_CNEOPT_FAIL : Byte = 0x0	// if column does not exist, fail
  final val SAVEROW_CNEOPT_DROP : Byte = 0x1	// drop non-exist column

  //GADB_SAVEROWOPT:nPosBy
  final val SAVEROW_POSBY_NORMAL : Byte = 0x1	// if key exists, using key, if key does not exist
  // then add it at an unused slot, if table is log
  // mode, add it at table tail
  final val SAVEROW_POSBY_SID : Byte = 0x2	// position by SID, even key exists. the sid can be supplied
  // by function parameters or as one column in results.
  final val SAVEROW_POSBY_KEY : Byte = 0x3

  // GADB_SAVEROWOPT::nPosFlag
  final val SAVEROW_POSFLAG_USESIDINHEAD : Byte = 0x1	// if sid are supplied both through column name
  // and row head, then using sid in row head.

  // GADB_SAVEROWOPT::nOldRowNotExistOption
  final val SAVEROW_ORNOPT_NEW : Byte = 0x0	// new record, if old not exist(key or pos).
  final val SAVEROW_ORNOPT_FAIL : Byte = 0x1	// if old record not exist, fail.
  final val SAVEROW_ORNOPT_DROP : Byte = 0x2	// drop new record.	// so it's update

  // GADB_SAVEROWOPT::nFlag
  final val SAVEROW_FLAG_NOTUSEINDEX : Byte = 0x1	// not use index(do not change).
  final val SAVEROW_FLAG_USEOLDPOS : Byte = 0x2	// if savebysid and old record exist(not at same sid)
  // then do not using sid specified.
  final val SAVEROW_FLAG_ERRCONTINUE : Byte = 0x4	// if meet error continue.
  // default is break.
  final val SAVEROW_FLAG_CHKSAMEROWKEY : Byte = 0x8	// if notuseindex specified, and old and new row is
  // in same position, check key match or not.
  final val SAVEROW_FLAG_UUIDIDENTIFY : Byte = 0x10	// using uuid to identify whether is same row( if
  // has key using key first)
  final val SAVEROW_FLAG_FAILCONTINUE : Byte = 0x20	// ERRCONTINUE is some error occurred, while FAILCONTINUE
  // means one option is fail for some case.

  /*
  // GADB_SAVEROWOPT::nNoKeyOldNewMatchOption
  final val SAVEROW_ONMOPT_VIEWASSAME : Byte = 0x0
  final val SAVEROW_ONMOPT_VIEWASNOTSAME : Byte = 0x1
  final val SAVEROW_ONMOPT_BYUUID : Byte = 0x2
  */

  // GADB_SAVEROWOPT::nCellFlag
  final val SAVEROW_CELLFLAG_CUTSOURCE : Byte = 0x2	// SETCOLOPT_FLAG_CUTSOURCE
  final val SAVEROW_CELLFLAG_NULLLONGSRC : Byte = 0x4	// SETCOLOPT_FLAG_NULLLONGSRC

  // GADB_SAVEROWOPT::nSaveMode, 0 is not ok.
  final val SAVEROW_SAVEMODE_NORMAL : Byte = 0x1
  final val SAVEROW_SAVEMODE_CREATELIB : Byte = 0x2	// batch mode.
  final val SAVEROW_SAVEMODE_UPDATELIB : Byte = 0x3	// batch mode.
  final val SAVEROW_SAVEMODE_ASYNCCREATE : Byte = 0x4
  final val SAVEROW_SAVEMODE_ASYNCUPDATE : Byte = 0x5

  // sync : means row position is same for source and target.
  // async : position is unimportant.
  final val SAVEROW_SAVEMODE_SYNCCREATE : Byte = 0x6
  final val SAVEROW_SAVEMODE_SYNCUPDATE : Byte = 0x7

  // GADB_SAVEROWOPT::nOption
  final val SAVEROW_OPT_NOTSAVEDELROW : Byte = 0x1	//
  final val SAVEROW_OPT_COPYMODE : Byte = 0x2	// in this mode, we reserve almost all inner columns
  final val SAVEROW_OPT_NEWCREATIONTIME : Byte = 0x4	// if SAVEROW_OPT_COPYMODE flag set, the creation time
  // will be reserved, but this indicates not reserve it.
  final val SAVEROW_OPT_NEWUPDATETIME : Byte = 0x8	// not keep old update time.
  final val SAVEROW_OPT_NEWLOBUPDATETIME : Byte = 0x10	// when save selres, set new lob update time.
  final val SAVEROW_OPT_ASYNCBKPKEYUUID : Byte = 0x20	// if do async backup(save mode), using key and uuid to identify
  // each row, default is not using uuid.(to be compatible with some
  // application that does not copy uuid).
  final val SAVEROW_OPT_UPDNOINTERTIMECHANGE : Byte = 0x40	// when update, does not change update user and update time.
  final val SAVEROW_OPT_COPYISAROW = 0x80	// when update, copy isa row.

  // GADB_SAVEROWOPT::nBkpDelRowType
  // the following options used for backup rows to be deleted when updating data.
  final val SAVEROW_BKPDEL_NOBKP : Byte = 0x0	// does not backup deleted row. sync used only.
  final val SAVEROW_BKPDEL_BKPOLDEST : Byte = 0x1	// backup one copy, backup the oldest.
  final val SAVEROW_BKPDEL_BKPLATEST : Byte = 0x2	// backup one copy, backup the latest.
  final val SAVEROW_BKPDEL_BKPMULTI : Byte = 0x3	// backup more than one copy

  // we submit some simple condition to retrieve
  class GADB_ROWRETRSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;		// size of this structure
  @Length(44)
  var szKeyWild:String = _ ;	// wild key, 44 bytes
  var tLastEditTime = new AFISDateTime;	// last edit time
  var tEndLastEditTime = new AFISDateTime;
    var tAddTime = new AFISDateTime;		// add time
  var tEndAddTime = new AFISDateTime;
    var nMaxToGet:Int = _ ;	// at most at this values of records
  var nKeyWidth:Int = _ ;		// key result width
  var nItemFlag:Byte = _ ;		// indicate which item to use, GADB_ROWRETR_ITEMFLAG_XXX
  var nOption:Byte = _ ;		// option	GADB_ROWRETR_OPT_XXX
  var nKeyDataType:Byte = _ ;	// key data type.(out).
  @Length(25)
  var bnRes:Array[Byte] = _ ;		// bytes reserved
  @Length(SID_SIZE)
  var nStartSID:String = _ ;
    @Length(SID_SIZE)
    var nEndSID:String = _ ;
    @Length(32)
    var szCreatorColName:String = _ ;	// creator col name
  @Length(32)
  var szModifierColName:String = _ ;	// column name of modifier
  @Length(32)
  var szCNameWild:String = _ ;	// create user
  @Length(32)
  var szMNameWild:String = _ ;	// modifier name wild
  } // GADB_ROWRETRSTRUCT;	// size is 256 bytes

  // the following structures are added on jun.23, 2003
  final val GADB_SERIAL_ITEMTYPE_UNKONW : Byte = 0x0
  final val GADB_SERIAL_ITEMTYPE_CT : Byte = 0x1
  final val GADB_SERIAL_ITEMTYPE_ISA : Byte = 0x2
  final val GADB_SERIAL_ITEMTYPE_BLOB : Byte = 0x3

  final val GADB_SERIAL_ALIGNAT = 16

  class GADB_SERIALITEMHEAD extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure
  var nItemType:Byte = _ ;	// GADB_SERIAL_ITEMTYPE_XXX
  @Length(1)
  var bnRes:Array[Byte] = _ ;	// reserved
  var nCTID:Short = _ ;	//
  var nOffset:Int = _ ;	// count from start of data, and must be multiple of 16
  var nLen:Int = _ ;	// to here is 16 bytes long
  @Length(16)
  var bnRes2:Array[Byte] = _ ;	// reserved
  } // GADB_SERIALITEMHEAD;	// size of this structure is 32 bytes long

  class GADB_SERIALROWHEAD extends AncientData
  {
    var cbSize:Int = _ ;			// size of this structure
  var nMajorVer:Byte = _ ;			// value is 1
  var nMinorVer:Byte = _ ;			// value is 0
  var nItemCnt:Short = _ ;		// item count
  var nSerialRowLen:Int = _ ;	// length of data
  @Length(20)
  var bnRes:Array[Byte] = _ ;			// reserved
  } // GADB_SERIALROWHEAD;	// size 32 bytes long

  class GADB_SERIALROWSTRUCT extends AncientData
  {
    var stHead = new GADB_SERIALROWHEAD;		//  32 bytes long
  @Length(1)
  var stItem:Array[GADB_SERIALITEMHEAD] = _;	// 32 bytes long
  @Length(8)
  var bnData:Array[Byte] = _ ;
  } // GADB_SERIALROWSTRUCT;	// size of this structure is 32*2+8

  // format of GADB_SERIALROW
  //  +------------------------+
  //  | GADB_SERIALROWHEAD     |
  //  +------------------------+
  //  | GADB_SERIALITEMHEAD    | // repeat nItemCnt times
  //  +------------------------+
  //  |     data               |
  //  +------------------------+
  ///////////////////end of new added structures
  //////////////////
  final val GADB_ROWRETR_ITEMFLAG_KEYWILD : Byte = 0x1
  final val GADB_ROWRETR_ITEMFLAG_ADDTIME : Byte = 0x2
  final val GADB_ROWRETR_ITEMFLAG_LASTEDITTIME : Byte = 0x4
  final val GADB_ROWRETR_ITEMFLAG_MAXTOGET : Byte = 0x8
  final val GADB_ROWRETR_ITEMFLAG_CNAMEWILD : Byte = 0x10
  final val GADB_ROWRETR_ITEMFLAG_MNAMEWILD : Byte = 0x20
  final val GADB_ROWRETR_ITEMFLAG_SIDRANGE : Byte = 0x40

  // values for GADB_ROWRETRSTRUCT::nOption
  final val GADB_ROWRETR_OPT_GETSID : Byte = 0x1	// does not get key but get sid
  final val GADB_ROWRETR_OPT_NOTGETDEL : Byte = 0x2	// does not get delete row


  final val COLCOPY_OPT_UPDATE : Byte = 0x1
  final val COLCOPY_OPT_CREATENEW : Byte = 0x2
  final val COLCOPY_OPT_NOCONTIGUOUS : Byte = 0x4	// does not need records that status is unused

  /////////////////////////////////////////////////////////////

  def SETSELRESITEM_FIXED[T<:AncientData](p:GADB_SELRESITEM,data:T , szname:String, item:String): Unit = {
    p.szItemName = szname
    val (offset,len) = data.findFieldOffsetAndLength(item)
    p.nDataOffset = offset
    p.nDataLen = len
  }
  def GfPub_ItemExistInColumnSchema(szItemName:String, pstSchema:Array[GADB_COLUMNSCHEMA]):Boolean={
    if(szItemName==null || szItemName.isEmpty)
      return false

    if(pstSchema == null)
      return true
    pstSchema.exists(_.szName == szItemName)
  }
  def SETSELRESITEM_FIXED_EXIST[T<:AncientData](p:GADB_SELRESITEM,data:T , szname:String, item:String,pstSchema:Array[GADB_COLUMNSCHEMA]): Unit = {
    if(GfPub_ItemExistInColumnSchema(szname,pstSchema)){
      SETSELRESITEM_FIXED(p,data,szname,item)
    }
  }


  final val TABLE_ADDOPT_APPEND : Byte = 0x1	// append mode

  // AllocBlock Option
  final val ALLOCBLOCK_OPT_NOTADD2SIDFILE : Byte = 0x1
  final val ALLOCBLOCK_OPT_NOLOCK : Byte = 0x2

  // we using the following routine to specify a number of records/rows
  class GADB_ROWPOSSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure
  @Length(4)
  var bnRes:Array[Byte] = _ ;	// reserved
  var nPosBy:Byte = _ ;		// refer format	0 valid, READROW_POSBY_XXX
  @Length(7)
  var bnRes1:Array[Byte] = _ ;		// reserved
    /*
  var tagGADB_RECREFE = new unionR
    {
      var stSid = new GADB_SIDARRAY;
      var stKey = new GADB_KEYARRAY;
      var stMt = new GADB_SELSTATEMENT;
    } // stRef;	// refer
    */
    @Length(2048 + 512)
    var bnRes2:Array[Byte] = _

  } //GADB_ROWPOSSTRUCT;	// row refer struct size is 2048+512+16

  ////////////////// the following added on Aug.21, 2003
  class GADB_ROWMODCACHENODE extends AncientData
  {
    var nTime:Int = _;	// the time that node was modified
  var nCnt:Int = _;	// # of rows modified in this time
  var nNextNode:Int = _;	// next node
  var nRes:Int = _;
  } // GADB_ROWMODCACHENODE;	// mod node cache, size is 16 bytes long


  class GADB_ROWMODCACHE_OLD extends AncientData
  {
    var stCrit = new GAFIS_CRITSECT;	// critical section
    @Length(96 - 8)
  var bnRes_Crit:Array[Byte] = _ //[96-sizeof(GAFIS_CRITSECT)];
    // to here is 96 bytes long
    var nFirst:Int = _;		// first node
  var nLast:Int = _;		// minimum time
  var nNodeBufCnt:Int = _;	// # of nodes
  var nNodeCnt:Int = _;
    var pstNode_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstNode_Data:Array[GADB_ROWMODCACHENODE] = _ // for pstNode pointer ,struct:GADB_ROWMODCACHENODE;	// pointer to node array
  @Length(4)
  var bnRes_Pointer:Array[Byte] = _ ;	//
  var bCritInited:Byte = _ ;
    @Length(3)
    var bnRes0:Array[Byte] = _ ;	// reserved
  @Length(4)
  var bnRes2:Array[Byte] = _ ;	// reserved
  } // GADB_ROWMODCACHE_OLD;	// size of this structure is 128 bytes long

  class GADB_ROWMODCACHE extends AncientData
  {
    var stCrit = new GAFIS_CRITSECT;	// critical section
    @Length(96 - 8)
    var bnRes_Crit:Array[Byte] = _ //[96-sizeof(GAFIS_CRITSECT)];
    // to here is 96 bytes long
    var nMaxTime:Int = _;
    var bCritInited:Byte = _ ;
    @Length(3)
    var bnRes0:Array[Byte] = _ ;		// reserved
  var nNodeBufCnt:Int = _;	// # of nodes
  var nNodeCnt:Int = _;
    var pstNode_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstNode_Data:Array[GADB_ROWMODCACHENODE] = _ // for pstNode pointer ,struct:GADB_ROWMODCACHENODE;	// pointer to node array
  var pstTable_Ptr:Int = _
    var pstTable_Data:Array[GADB_TABLEOBJECT] = _;	// table object.
  @Length(4*2)
  var bnRes_Pointer:Array[Byte] = _ ;	//
  } // GADB_ROWMODCACHE;	// size of this structure is 128 bytes long


  final val MROWOPT_NOTADDZEROLENBLOB : Byte = 0x1	//
  final val MROWOPT_OMMITNONEXISTCOL : Byte = 0x2	// omit non exist column
  final val MROWOPT_CUTSOURCE : Byte = 0x4	// if source data length is longer then target buffer
  // then cut source data. using for setcolumn
  final val MROWOPT_ISNEWADD : Byte = 0x8

  // the following structure is used to reference a column in GADB_SELRESULT
  // structure.
  class GADB_COLPT extends AncientData
  {
    var pPt_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pPt_Data:Array[Byte] = _ // for pPt pointer ,struct:UCHAR;	// pointer to pt.
  var nColWidth:Int = _;
    var nRowWidth:Int = _;
  } // GADB_COLPT;

  // if we call SaveSelRes, we need to know each row result.
  class GADB_RETVAL extends AncientData
  {
    @Length(SID_SIZE)
    var nSID:String = _ ;
    var nRetVal:Byte = _ ;	// -1 error, 1 success.
  var nFlag:Byte = _ ;		// GADB_RETVALFLAG_XXX
  } // GADB_RETVAL;	// size is 8 bytes long.

  final val GADB_RETVALFLAG_HASOP : Byte = 0x1	//
  final val GADB_RETVALFLAG_FAILED : Byte = 0x2

  // read write.
  class GADB_NORMALRES extends AncientData
  {
    var stRes = new GADB_SELRESULT;		// 64 bytes long
    @Length(128 - 64)
    var bnRes:Array[Byte] = _
    /*
  var tagGADB_USR = new union{
      var stSaveOpt = new GADB_SAVEROWOPT;	// 32 bytes long
      var stReadOpt = new GADB_READROWOPT;	// 32 bytes long
    } // usr;	// union of read or write.
    // to here is 96 bytes long.
    int		nItemBufCnt;
    int		bIsRead;
    UCHAR	nOption;	// selres option. GADB_NORMALRESOPT_XXX
    UCHAR	bnRes[23];
    */
  } //GADB_NORMALRES;	// 128 bytes long.

  // GADB_NORMALRES::nOption
  final val GADB_NORMALRESOPT_FIXEDROWSIZE : Byte = 0x1


  class GADB_COLDATA extends AncientData
  {
    var pData_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pData_Data:Array[Byte] = _ // for pData pointer ,struct:UCHAR;
  var pstLobInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstLobInfo_Data:Array[GADB_BLOBCOL] = _ // for pstLobInfo pointer ,struct:GADB_BLOBCOL;
  var pstProp_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstProp_Data:Array[GADB_COLPROPSTRUCT] = _ // for pstProp pointer ,struct:GADB_COLPROPSTRUCT;	// is lob, does not using pData, using this item.
  @Length(4*3)
  var bnRes_Pt:Array[Byte] = _ ;
    // to here is 24 bytes long.
    var bIsNULL:Byte = _ ;
    var bIsLob:Byte = _ ;
    var bIsIsaCol:Byte = _ ;	// whether is reserved column
  var bChanged:Byte = _ ;	// return value
  var nDataLen:Int = _;
    // the fllowing items used for lob only. nChangePartOffset
    // markes the starting position has been changed.
    // nChangePartLen markes length has been changed. if nChangePartLen is
    // zero then all data has been changed.
    var nChangePartOffset:Int = _;
    var nChangePartLen:Int = _;
    var nCTID:Short = _;
    @Length(22)
    var bnRes:Array[Byte] = _ ;
  } // GADB_COLDATA;	// column data. 64 bytes long

  // change row data info.
  class GADB_CRD_DATAINFO extends AncientData
  {
    var nCurSID = new SID_TYPE;
    var nMaxSID = new SID_TYPE;
    @Length(16)
    var bnRes:Array[Byte] = _ ;
  } // GADB_CRD_DATAINFO;	// 32 bytes long.


  // GADB_SELRES_BuildNullFlag::nOption
  final val GADB_BNFLAG_BLANKSTRASNULL : Byte = 0x1	// blank string as null.
  final val GADB_BNFLAG_ZEROLENLOBASNULL : Byte = 0x2	// if lob lengh is zero then view it as null.

}



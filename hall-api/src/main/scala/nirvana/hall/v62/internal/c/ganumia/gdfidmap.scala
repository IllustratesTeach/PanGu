package nirvana.hall.v62.internal.c.ganumia

import nirvana.hall.v62.annotations.{IgnoreTransfer, Length}
import nirvana.hall.v62.internal.c.ganumia.gadbcommon.GADB_FILEHEADSCHEMA
import nirvana.hall.v62.internal.c.gbaselib.gathrdop.GAFIS_CRITSECT
import nirvana.hall.v62.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-23
 */
object gdfidmap {

  /*
   * we keep a record for log for a given file id, which device it belong to.
   * for two type files : row data file and lob file.
   */

  class GADB_DFIDENTRY extends AncientData
  {
    var nDFID:Int = _ ;	// data file id
  var nDevID:Byte = _ ;		// device id
  @Length(3)
  var bnRes:Array[Byte] = _ ;	// reserved
  } // GADB_DFIDENTRY;		// size is 8 bytes long

  class GADB_DFIDPARAM extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure
  @Length(4)
  var bnRes0:Array[Byte] = _ ;	// reserved.
  var nDFCnt:Int = _ ;	// # of file id used.
  var nFileType:Byte = _ ;	// file type, DFID_FTYPE_XXX
  @Length(3)
  var bnRes1:Array[Byte] = _ ;	// reserved
  @Length(48)
  var bnRes:Array[Byte] = _ ;
  } // GADB_DFIDPARAM;	// size of this structure is 64 bytes long

  final val DFID_FTYPE_ROWFILE = 1	// row data file - device mapping
  final val DFID_FTYPE_LOBFILE = 2	// lob data file - device mapping

  // dfid file head
  class GADB_DFIDFILEHEAD extends AncientData
  {
    var stHead = new GADB_FILEHEADSCHEMA;	// head, 64 bytes long
  var stParam = new GADB_DFIDPARAM;
    @Length(256-128)
    var bnRes:Array[Byte] = _ ;	// reserved
  } // GADB_DFIDFILEHEAD;	// file head 256 bytes long

  class GADB_DFIDFILEMAP extends AncientData
  {
    var stHead = new GADB_DFIDFILEHEAD;
    @Length(8)
    var stEnt:Array[GADB_DFIDENTRY] = _;
  } // GADB_DFIDFILEMAP;	// size of this structure is multiple 512 Bytes.

  final val DFIDTYPE_BOUND = 512	// bound

  class GADB_DFIDFILEINFO extends AncientData
  {
    var nFileSize:Long = _;	// size of File
  var nUsedSize:Long = _;	// used file size(may be larger then nFileSize)
  var nDevID:Byte = _ ;		// device id
  var bIsFileClosed:Byte = _ ;	// whether file is closed.
  var nState:Byte = _ ;			// file state.	LOBFILE_STATE_XXX
  @Length(1)
  var bnRes:Array[Byte] = _ ;
    var nDFID:Int = _;		// data file id
  @Length(8)
  var bnRes2:Array[Byte] = _ ;	//
  } // GADB_DFIDFILEINFO;	// file info for data file, size is 32 bytes long.

  // lob file state
  final val LOBFILE_STATE_READONLY = 0x1	// readonly.
  final val LOBFILE_STATE_HASERROR = 0x2	// lob file has error.

  final val LOBFILE_STATE_COUNT = 2	// two states

  class GADB_DFIDMAPINMEM extends AncientData
  {
    var pstMap_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstMap_Data:Array[GADB_DFIDFILEMAP] = _ // for pstMap pointer ,struct:GADB_DFIDFILEMAP;	// map in memory
  var pstDFInfo_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstDFInfo_Data:Array[GADB_DFIDFILEINFO] = _ // for pstDFInfo pointer ,struct:GADB_DFIDFILEINFO;	// data file info, only lob data file has this member used.
  // for row data file, we use sid file to track file usage
  // status.
  @Length(4*2)
  var bnRes_Pointer:Array[Byte] = _ ;
    // to here is 16 bytes long
    var nSize:Int = _;	// map buffer size
  var nDFCnt:Int = _;	// # of data files
  var nDFInfoBufCnt:Int = _;	//
  var bCritInited:Int = _;
    // to here is 32 bytes long
    var stCrit = new GAFIS_CRITSECT;	// critical section
    @Length(96 - 8)
  var bnRes_Crit:Array[Byte] = _ //[96-sizeof(GAFIS_CRITSECT)];
    // to here is 128 bytes long
    var nMaxAllowedFileSize:Long = _ //new ga_int8;
    @Length(120)
    var szMainPath:String = _ ;	// main path of the file.
  } // GADB_DFIDMAPINMEM;	// dfid in memory, size is 128+128 bytes long.


  // we have some data need to add to one lob file, here is the procedure that we
  // choose one.
  // if data file size < table's required max lob file size
  //      if used file size+nsize need<table's max lob file size
  //             ok we found one.
  //      endif
  // endif
  // need create one new data file(return 0, not found)
  // return -1 on error, return 1 if found
  final val GADB_SELDFIDOPT_NOCALL = 0x1	// does not call pfnCalcFSize to calculate file info, instead using cached info.

}

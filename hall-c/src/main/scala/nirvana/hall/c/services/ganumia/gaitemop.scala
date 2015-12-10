package nirvana.hall.c.services.ganumia

import nirvana.hall.c.annotations.{Length, IgnoreTransfer}
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services._

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-23
 */
object gaitemop {

  // common column properties in memory
  // zero length data does not mean NULL data.
  class GADB_COLSIMPPROPSTRUCT extends AncientData
  {
    var nItemSize:Int = _ ;		// item size, size of pbnData
  var nStartOffset:Int = _ ;	// start offset, only for blob
  var nEndOffset:Int = _ ;		// end offset, only for blob
  var bIsNULL:Byte = _ ;			// used on update, if it's true then delete it from file
  var bDataCanBeFreed:Byte = _ ;	// whether data can be freed
  var nFileMode:Byte = _ ;			// file mode, 0--unknown, 1--not file, 2 is file
  var bLeaveFileOnDisk:Byte = _ ;	// [in]if its true, it must be in file mode(2).pbnData is not used, to here is 48 bytes
  // if its tree when load do not read it in memory, when write we do not write the data
  var pbnData_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pbnData_Data:Array[Byte] = _ // for pbnData pointer ,struct:char;			// pointer to data
  @Length(4)
  var bnRes_pbnData:Array[Byte] = _ ;
    var bIsDeleted:Byte = _ ;			// [out]for package use only, is unused
  var bIsFile:Byte = _ ;			// [out]item is file or not
  var bLeavedFileOnDisk:Byte = _ ;	// [out]
  var nFlag:Byte = _ ;				// reserved	COLSIMP_FLAG_XXX
  var DataBufSize:Int = _ ;		// the actual buf size in pbnData.
  } // GADB_COLSIMPPROPSTRUCT;		// 32 bytes

  final val COLSIMP_FLAG_EFFECTIVE = 0x1	// used for parallel match
  final val COLSIMP_FLAG_CHANGENAME = 0x2	// used only for update, when loading data, we do not
  // load data with this flag set to true.

  // the following structure can hold a blob or non blob data.
  // horizontal mode
  class GADB_COLPROPSTRUCT extends AncientData
  {
    @Length(32)
    var szItemName:String = _ ;		// column name
  var stProp = new GADB_COLSIMPPROPSTRUCT;				// column properties
  } // GADB_COLPROPSTRUCT;	// size of this structure is 64 bytes

  // column array
  class GADB_COLARRAYSTRUCT extends AncientData
  {
    var nColCount:Short = _ ;	// number of columns
  var nColBufCount:Short = _ ;	// how many items can be stored
  var bColCanBeFreed:Byte = _ ;	// whether col can be freed
  @Length(3)
  var bnRes0:Array[Byte] = _ ;
    var pstCol_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstCol_Data:Array[GADB_COLPROPSTRUCT] = _ // for pstCol pointer ,struct:GADB_COLPROPSTRUCT;	// points to each item
  @Length(4)
  var bnRes_pstCol:Array[Byte] = _ ;
    @Length(SID_SIZE)
    var nSID:String = _ ;	// sid
  @Length(2)
  var bnRes_SID:Array[Byte] = _ ;
    @Length(2)
    var bnRes2:Array[Byte] = _ ;	// reserved
  } // GADB_COLARRAYSTRUCT;	// many columns, size is 24 bytes

  // the following structure is used only for one column, vertical mode
  class GADB_COLVERTPROPSTRUCT extends AncientData
  {
    @Length(32)
    var szItemName:String = _ ;	// column name
  var pstProp_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstProp_Data:Array[GADB_COLSIMPPROPSTRUCT] = _ // for pstProp pointer ,struct:GADB_COLSIMPPROPSTRUCT;		// properties, many rows
  @Length(4)
  var bnRes_Pointer:Array[Byte] = _ ;
    var nItemCount:Int = _ ;	// how many rows stored in this structure
  var nItemBufCount:Int = _ ;
    var bPropCanBeFreed:Byte = _ ;
    @Length(15)
    var bnRes:Array[Byte] = _ ;	// reserved
  } // GADB_COLVERTPROPSTRUCT;	// size is 64 bytes, one column

  class GADB_COLVERTARRAYSTRUCT extends AncientData
  {
    var nColCount:Short = _ ;	// number of columns
  var bColCanBeFreed:Byte = _ ;	// whether col can be freed
  @Length(1)
  var bnRes0:Array[Byte] = _ ;
    var nColBufCount:Short = _ ;	// how many items can be stored
  @Length(2)
  var bnRes1:Array[Byte] = _ ;		// reserved
  var pstCol_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstCol_Data:Array[GADB_COLVERTPROPSTRUCT] = _ // for pstCol pointer ,struct:GADB_COLVERTPROPSTRUCT;	// points to each item, many rows
  @Length(4)
  var bnRes_pstCol:Array[Byte] = _ ;
  } // GADB_COLVERTARRAYSTRUCT;	// many columns, size is 16 bytes


  //////////////////////////////////

}





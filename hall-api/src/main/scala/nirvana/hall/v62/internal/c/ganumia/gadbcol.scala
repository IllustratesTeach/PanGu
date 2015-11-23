package nirvana.hall.v62.internal.c.ganumia

import nirvana.hall.v62.annotations.{IgnoreTransfer, Length}
import nirvana.hall.v62.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-23
 */
object gadbcol {

  // modification history:
  // 2003.6.6, add bnUUID and bnRes2 field to the following structure.
  // szName and bnUUID must be all unique.
  class GADB_COLUMNSCHEMA extends AncientData
  {
    var cbSize:Int = _ ;		// size of this structure
  @Length(32)
  var szName:String = _ ;		// internal name
  var nLength:Int = _ ;		// max length of this column, (to here is 40 bytes included)
  var nDataType:Byte = _ ;		// data type, GADBCS_DATATYPE_XXX
  var bIsKey:Byte = _ ;			// whether is key or not
  var bIsUniq:Byte = _ ;		// whether is unique
  var bHasDefault:Byte = _ ;	// whether has default
  var bCanBeNull:Byte = _ ;		// can be null?
  var bIsIndexed:Byte = _ ;		// indexed?
  var nColumnID:Short = _ ;	// column id(to here is 48 bytes, included)(starts from 0)
  var nOffset:Int = _ ;		// offset from the origin of record
  var nDefValueSPID:Int = _ ;	// default value stored procedure id
  var nCheckValueSPID:Int = _ ;	// check value spid
  var nSortOrder:Byte = _ ;			// sort order, not used, reserve.
  var bUseParallelmatch:Byte = _ ;	// parallel match, if false, then changing of data in this column
  // will not be logged in modlog file
  var nModMask:Byte = _ ;			// column modification log mask
  var nFlag:Byte = _ ;				// some other option, GADBCS_FLAG_XXX
  var nLatCollID:Byte = _ ;			// collid for latin
  var nMBCollID:Byte = _ ;			// collid for mb or unicode char
  @Length(12)
  var bnRes:Array[Byte] = _ ;			// 12 bytes reserved(to here is 78 bytes)
  var nTableID:Short = _ ;		// child table id, to here is 80 bytes long
  @Length(48)
  var szDispName:String = _ ;		// display name
  @Length(16)
  var bnUUID:Array[Byte] = _ ;			// uuid, treat as binary
  var nUserAccMode:Byte = _ ;		// user access mode, GADB_ACCMODE_XX
  var nGrpAccMode:Byte = _ ;		// group access mode, GADB_ACCMODE_XX
  var nOtherAccMode:Byte = _ ;		// group access mode., GADB_ACCMODE_XX
  var nFlagEx:Byte = _ ;			// reserved.
  var nOwnerID:Short = _ ;		// ownerid.
  var nGroupID:Short = _ ;		// group id.
  @Length(8)
  var bnRes4:Array[Byte] = _ ;			// tempory used field, do not change it.
  @Length(32)
  var szForeignKeyTable:String = _ ;	// foreign key table name.
  @Length(16)
  var nTemp:String = _ ;
    @Length(48)
    var bnRes2:Array[Byte] = _ ;		// reserved.
  } // GADB_COLUMNSCHEMA;	// size of this structure is 256 bytes

  // After the creation of the table, user can modify the following column properties
  // added on Apr.30, 2004.
  final val GADBCS_ITEM_MODMASK = 0x1		// GADB_COLUMNSCHEMA::nModMask will take effect
  final val GADBCS_ITEM_FLAG = 0x2		// GADB_COLUMNSCHEMA::nFlag will take effect
  final val GADBCS_ITEM_CANBENULL = 0x4		// GADB_COLUMNSCHEMA::bCanBeNull will take effect
  final val GADBCS_ITEM_INDEXED = 0x8		// GADB_COLUMNSCHEMA::bIsIndexed will take effect
  final val GADBCS_ITEM_PMSEARCH = 0x10	// GADB_COLUMNSCHEMA::bUseParallelmatch will take effect
  final val GADBCS_ITEM_USRACCMODE = 0x20	// GADB_COLUMNSCHEMA::nUserAccMode will take effect
  final val GADBCS_ITEM_GRPACCMODE = 0x40	// GADB_COLUMNSCHEMA::nGrpAccMode will take effect
  final val GADBCS_ITEM_OTHACCMODE = 0x80	// GADB_COLUMNSCHEMA::nOtherAccMode will take effect

  final val GADBCS_ITEM_DISPNAME = 0x100	// GADB_COLUMNSCHEMA::szDispName will take effect
  final val GADBCS_ITEM_ISKEY = 0x200	// GADB_COLUMNSCHEMA::bIsKey will take effect
  final val GADBCS_ITEM_ISUNIQ = 0x400	// GADB_COLUMNSCHEMA::bIsUniq will take effect
  final val GADBCS_ITEM_HASDEFAULT = 0x800	// GADB_COLUMNSCHEMA::bHasDefault will take effect
  final val GADBCS_ITEM_LATCOLID = 0x1000	// GADB_COLUMNSCHEMA::nLatCollID will take effect
  final val GADBCS_ITEM_MBCOLID = 0x2000	// GADB_COLUMNSCHEMA::nMBCollID will take effect

  // action to be taken.
  final val GADBCS_ACT_SET = 0x1
  final val GADBCS_ACT_OR = 0x2
  final val GADBCS_ACT_AND = 0x3
  final val GADBCS_ACT_XOR = 0x4

  final val GADBCS_OPT_FAILIFNOTEXIST = 0x1

  // GADB_COLUMNSCHEMA::nDataType
  final val GADBCS_DATATYPE_UNKNOWN = 0x0
  final val GADBCS_DATATYPE_INT1 = 0x1	// integer, 1
  final val GADBCS_DATATYPE_INT2 = 0x2	// integer, 2
  final val GADBCS_DATATYPE_INT4 = 0x3	// integer, 4
  final val GADBCS_DATATYPE_INT8 = 0x4	// integer, 8
  final val GADBCS_DATATYPE_BLOB = 0x5	// blob, BLOB
  final val GADBCS_DATATYPE_TEXT = 0x6	// text, CLOB
  final val GADBCS_DATATYPE_BIN = 0x7	// store in row, binary
  final val GADBCS_DATATYPE_STRING = 0x8	// string
  final val GADBCS_DATATYPE_DATE = 0x9
  final val GADBCS_DATATYPE_TIME = 0xA		//
  final val GADBCS_DATATYPE_DATETIME = 0xB	// date time
  final val GADBCS_DATATYPE_FLOAT = 0xC	// single
  final val GADBCS_DATATYPE_DOUBLE = 0xD	// double float
  final val GADBCS_DATATYPE_INT6 = 0xE	// 6 bytes int

  final val GADBCS_DATATYPE_LOBINFO = 0x10	// lob info.

  final val GADBCS_DATATYPE_LOCINT2 = 0x20	// local byte order int.
  final val GADBCS_DATATYPE_LOCINT4 = 0x21
  final val GADBCS_DATATYPE_LOCINT8 = 0x22
  final val GADBCS_DATATYPE_LOCFLOAT = 0x23
  final val GADBCS_DATATYPE_LOCDOUBLE = 0x24

  // values for nFlag
  final val GADBCS_FLAG_NOTIME_CHANGE = 0x1	// if marked then changing of this column will not
  // result in changing of internal last modify time
  final val GADBCS_FLAG_NO_BACKUP = 0x2	// no backup flag, used for any column, but only has
  // meaning for blob/text column, does not backup those
  // columns(may store temporary data)
  final val GADBCS_FLAG_BACKUP_IF_NOT_NULL = 0x4	// for one table, if some columns marked as this flag
  // so only if at least one columns is not null the row
  // can be backuped.
  final val GADBCS_FLAG_BACKUP_USED = 0x8		// the column is copied from other table
  final val GADBCS_FLAG_NLS = 0x10	// the column is an national language(for string used
  // only).
  final val GADBCS_FLAG_FOREIGN_KEY = 0x20	// the column is a foreign key


  class GADB_COL_DATA extends AncientData
  {
    var pData_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pData_Data:Array[Byte] = _ // for pData pointer ,struct:UCHAR;
  @Length(4)
  var bnRes_Pointer:Array[Byte] = _ ;
    var nDataType:Byte = _ ;
    @Length(3)
    var bnRes:Array[Byte] = _ ;
    var nLen:Int = _;
  } // GADB_COL_DATA;	// size is 16 bytes long

  /*
  enum	tagGADB_DATATYPE_CLASS
  {
    GADB_DTCLASS_BAD,	// bad datatype class
    GADB_DTCLASS_INT,
    GADB_DTCLASS_DATETIME,	// date time class
    GADB_DTCLASS_BIN,
    GADB_DTCLASS_FLOAT
  } GADB_DATATYPE_CLASS;
  */

  // the following parameter is used for GADB_COL_CalcOffsetAndRowSizeEx routine
  final val COL_CALCOPT_EXTBLOB = 0x1

  class GADB_COLNAME extends AncientData
  {
    @Length(32)
    var szName:String = _ ;
  } // GADB_COLNAME;	// column name , size is 32 bytes long

  // column name array
  class GADB_COLNAMEARRAY extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure
  @Length(4)
  var bnRes:Array[Byte] = _ ;	// reserved
  var nColCnt:Short = _ ;	// # of columns
  var nColBufCnt:Short = _ ;	// columns buffer count
  @Length(4)
  var bnRes1:Array[Byte] = _ ;		// reserved
    @Length(32)
    var pszColName:String = _ //name
  @Length(4)
  var bnRes_Pointer:Array[Byte] = _ ;
    @Length(8)
    var bnRes2:Array[Byte] = _ ;
  } // GADB_COLNAMEARRAY;	// size is 32 bytes long

  /*
  typedef	enum	tagGADB_COLISSAMEBY
  {
    COLISSAME_BYNAME,
    COLISSAME_BYUUID,
    COLISSAME_BYNAMEANDUUID,
    COLISSAME_BYNAMEORUUID
  } GADB_COLISSAMEBY;
  */

  // in memory col statistics
  class GADB_SIMPCOLSTATISTIC extends AncientData
  {
    var nDataLen:Long = _ ;	// length of data.
  var nCount:Long = _ ;		// count, non null values.
  } // GADB_SIMPCOLSTATISTIC;	// 16 bytes long.

  class GADB_MEMCOLSTATISTIC extends AncientData
  {
    var nDataLen:Long = _;	// length of data.
  var nCount:Long = _;		// count, non null values.
  } // GADB_MEMCOLSTATISTIC;	// 16 bytes long.

  class GADB_COLSTATISTIC extends AncientData
  {
    @Length(32)
    var szColName:String = _ ;	// column name.
  var stA = new GADB_SIMPCOLSTATISTIC;
    @Length(16)
    var bnRes:Array[Byte] = _ ;
  } // GADB_COLSTATISTIC;	// 64 bytes long.



}

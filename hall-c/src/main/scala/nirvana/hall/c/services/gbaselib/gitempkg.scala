package nirvana.hall.c.services.gbaselib

import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-09
 */
object gitempkg {
  val GBASE_ITEMPKG_MAGICSTRING = "<GAFIS50NetPackage>"
  val GBASE_ITEMPKG_ITEMMAGIC = "Item"
  val GBASE_ITEMPKG_MAXSIZE = 256 * 1024 * 1024


  class GBASE_ITEMPKG_PKGHEADSTRUCT extends AncientData
  {
    var cbSize:Int = 64 ;		// size of head, must be 64
  @Length(24)
  var szMagic:String = GBASE_ITEMPKG_MAGICSTRING;	// magic, must be '<GAFIS50NetPackage>'
  var nDataLen:Int = _ ;	// length of the whole package
  var nMajorVer:Short = 5 ;	// must be 5
  var nMinorVer:Short = _ ;	// must be 0
  var nPkgType:Int = _ ;	// type of package, used to identify type of package
  @Length(16)
  var szPkgTypeStr:String = _ ;	// pkg type string, used to identify type package
  var nBufSize:Int = _ ;	// buffer size. used only in memory operations.
  @Length(4)
  var bnRes:Array[Byte] = _ ;		// reserved
  } // GBASE_ITEMPKG_HEADSTRUCT;	// size is 64 bytes
  type GBASE_ITEMPKG_HEADSTRUCT = GBASE_ITEMPKG_PKGHEADSTRUCT

  class GBASE_ITEMPKG_ITEMHEADSTRUCT extends AncientData
  {
    var cbSize:Int = 48 ;			// size of item head, must be 48
  @Length(32)
  var szItemName:String = _ ;	// item name
  var nItemLen:Int = _ ;	// length of item, >=0
  var nItemType:Int = _ ;	// item type
    @Length(4)
    var szMagic:String = GBASE_ITEMPKG_ITEMMAGIC ;		// must 'Item'
  } // GBASE_ITEMPKG_ITEMHEADSTRUCT;	// size is 48 bytes

  // format of package item
  // [ GBASE_ITEMPKG_ITEMHEADSTRUCT]
  // [    ... item data ...        ]

  // format of package
  // [GBASE_ITEMPKG_PKGHEADSTRUCT ]
  // [  ... item1...              ]
  // [  ... itemn...              ]

  class GBASE_ITEMPKG_ITEMSTRUCT extends AncientData
  {
    var stHead = new GBASE_ITEMPKG_ITEMHEADSTRUCT;
    @Length(16)
    var bnRes:Array[Byte] = _ ;
  } // GBASE_ITEMPKG_ITEMSTRUCT;	// size >=48, must be multiple of 4

  class GBASE_ITEMPKG_OPSTRUCT extends AncientData
  {
    var pbnPkg_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pbnPkg_Data:Array[Byte] = _ // for pbnPkg pointer ,struct:UCHAR;	// pointer to package, whole format of package
  @Length(4)
  var bnRes_Pointer:Array[Byte] = _ ;
    var nPkgLen:Int = _; //记录当前数据的总长度
    var nPkgBufLen:Int = _; //记录了当前buffer的长度
  } // GBASE_ITEMPKG_OPSTRUCT;	// size is 16 bytes
}

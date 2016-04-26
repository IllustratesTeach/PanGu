package nirvana.hall.c.services.gbaselib

import java.nio.charset.Charset

import nirvana.hall.c.annotations.{LengthRef, IgnoreTransfer, Length}
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.AncientData.{StreamReader, StreamWriter}

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
  var nMinorVer:Short = 0 ;	// must be 0
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

  /**

  包结构说明:

         __                                            __
       / /  [ GBASE_ITEMPKG_HEADSTRUCT ]               \ \
      | |   [GBASE_ITEMPKG_ITEMHEADSTRUCT   0]         | |
     < <    [ITEM具体数据 0]                             > >
      | |   [GBASE_ITEMPKG_ITEMHEADSTRUCT   N]         | |
       \_\  [ITEM具体数据 N]                            /_/

    注意：
    1.head中的nDataLen是整个包的大小
    2.ITEM的头和数据部分采取4字节对齐模式(已经内置处理)
    3.Item中头中nItemLen的大小是实际数据类型大小，有可能实际的字节大于这个值，因为上面字节对齐问题，需要补充字节


 */

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
    @IgnoreTransfer
    def bnResLength:Int = {
       //采取对其的方式
      val nlen =stHead.getDataSize + stHead.nItemLen
      val nitemlen = gbasedef.GBASE_UTIL_ALIGN(nlen, 4);
      nitemlen - stHead.getDataSize
    }
    @LengthRef("bnResLength")
    var bnRes:Array[Byte] = _ ;
  } // GBASE_ITEMPKG_ITEMSTRUCT;	// size >=48, must be multiple of 4

  /*
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
  */

  class GBASE_ITEMPKG_OPSTRUCT extends AncientData{
    var head = new GBASE_ITEMPKG_HEADSTRUCT()
    private var items:List[GBASE_ITEMPKG_ITEMSTRUCT] = Nil

    /**
      * calculate data size and return.
      *
      * @return data size
      */
    override def getDataSize: Int = {
      head.nDataLen
    }


    /**
      * serialize to channel buffer
      *
      * @param stream netty channel buffer
      */
    override def writeToStreamWriter[T](stream: T, encoding: Charset)(implicit converter: (T) => StreamWriter): T = {
      head.writeToStreamWriter(stream,encoding)
      items.foreach(_.writeToStreamWriter(stream,encoding))

      stream
    }


    /**
      * convert channel buffer data as object
      *
      * @param dataSource netty channel buffer
      */
    override def fromStreamReader(dataSource: StreamReader, encoding: Charset): GBASE_ITEMPKG_OPSTRUCT.this.type = {
      head.fromStreamReader(dataSource,encoding)
      var remainLength = head.nDataLen - head.getDataSize
      val itemHeadSize = new GBASE_ITEMPKG_ITEMHEADSTRUCT().getDataSize
      if(remainLength > itemHeadSize){
        val item = new GBASE_ITEMPKG_ITEMSTRUCT
        item.fromStreamReader(dataSource,encoding)
        addItem(item,updateDataLength = false)
        remainLength -= item.getDataSize
      }

      this
    }

    def findItemByName(name:String)={
      items.find(_.stHead.szItemName == name)
    }
    def addItem(item:GBASE_ITEMPKG_ITEMSTRUCT,updateDataLength:Boolean=true): Unit ={
      items = items :+ item
      if(updateDataLength)
        head.nDataLen += item.getDataSize
    }
    def deleteItemByName(name:String): Unit ={
      //先更新头文件里面的数据长度
      items.takeWhile(_.stHead.szItemName == name).foreach(head.nDataLen -= _.getDataSize)
      items = items.dropWhile(_.stHead.szItemName==name)
    }
  }
}

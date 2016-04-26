package nirvana.hall.v62.internal.c.gbaselib

import nirvana.hall.c.services.gbaselib.gitempkg
import nirvana.hall.c.services.gbaselib.gitempkg.{GBASE_ITEMPKG_ITEMHEADSTRUCT, GBASE_ITEMPKG_ITEMSTRUCT, GBASE_ITEMPKG_OPSTRUCT}

/**
  * gitem package
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-26
  */
trait gitempkg {
//  #define	GBASE_ITEMPKG_MAGICSTRING	"<GAFIS50NetPackage>"
//  #define	GBASE_ITEMPKG_ITEMMAGIC		"Item"
  final val GBASE_ITEMPKG_MAXSIZE		= 256 * 1024 * 1024

  def GBASE_ITEMPKG_New(nEstimateSize:Int):GBASE_ITEMPKG_OPSTRUCT=
  {
    val result = new GBASE_ITEMPKG_OPSTRUCT
    result.head.nDataLen = result.head.getDataSize
    result
  }
  def GBASE_ITEMHEAD_Init(itemHead:GBASE_ITEMPKG_ITEMHEADSTRUCT, pszName:String, nItemType:Int)
  {

    itemHead.szItemName = pszName
    itemHead.nItemType = nItemType
  }


  def GBASE_ITEMPKG_IsValidHead(pstPkg:GBASE_ITEMPKG_OPSTRUCT )
  {
    val ph = pstPkg.head
    if ( ph.szMagic!=gitempkg.GBASE_ITEMPKG_MAGICSTRING ||
      ph.nMajorVer!=5 || ph.nMinorVer != 0)
      throw new IllegalArgumentException("Item package is invalid")
  }
  def GBASE_ITEMPKG_IsValidItem(pstItem:GBASE_ITEMPKG_ITEMHEADSTRUCT)
  {
    if ( pstItem.szMagic!=gitempkg.GBASE_ITEMPKG_ITEMMAGIC )
      throw new IllegalArgumentException("Item package is invalid")
  }
  def GBASE_ITEMPKG_AddItemEx(pstPkg:GBASE_ITEMPKG_OPSTRUCT,
    pszName:String,
    nItemType:Int,
    pData:Array[Byte])
  {
    val item = new GBASE_ITEMPKG_ITEMSTRUCT
    GBASE_ITEMHEAD_Init(item.stHead, pszName, nItemType);
    val nDataLen = pData.length
    item.stHead.nItemLen = pData.length
    item.bnRes = pData
    pstPkg.addItem(item)
  }
  def GBASE_ITEMPKG_AddItem(pstPkg:GBASE_ITEMPKG_OPSTRUCT,
                            item:GBASE_ITEMPKG_ITEMSTRUCT)
  {
    pstPkg.addItem(item)
  }
  def GBASE_ITEMPKG_GetItem(pstPkg:GBASE_ITEMPKG_OPSTRUCT,pszItemName:String):Option[GBASE_ITEMPKG_ITEMSTRUCT]=
  {
    pstPkg.findItemByName(pszItemName)
  }
  // delete one item from package
  def GBASE_ITEMPKG_DelItem(pstPkg:GBASE_ITEMPKG_OPSTRUCT, pszItemName:String)
  {
    pstPkg.deleteItemByName(pszItemName)
  }
  def GBASE_ITEMPKG_UpdateItem(pstPkg:GBASE_ITEMPKG_OPSTRUCT, pstItem:GBASE_ITEMPKG_ITEMSTRUCT)
  {
    GBASE_ITEMPKG_DelItem(pstPkg, pstItem.stHead.szItemName)
    GBASE_ITEMPKG_AddItem(pstPkg, pstItem)
  }
}

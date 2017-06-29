package nirvana.hall.c.services.gbaselib

import nirvana.hall.c.services.gbaselib.gitempkg.{GBASE_ITEMPKG_ITEMSTRUCT, GBASE_ITEMPKG_OPSTRUCT}
import nirvana.hall.c.services.gloclib.glocndef.GNETREQUESTHEADOBJECT
import org.junit.{Assert, Test}

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-26
  */
class gitempkgTest {
  @Test
  def test_length: Unit ={
    val item = new GBASE_ITEMPKG_ITEMSTRUCT
    item.stHead.nItemLen = 100
    val itemLength  = item.stHead.getDataSize+100
    Assert.assertEquals(item.toByteArray().length,itemLength)

    val pkg = new GBASE_ITEMPKG_OPSTRUCT
    pkg.head.nDataLen = pkg.head.getDataSize
    pkg.addItem(item)

    Assert.assertEquals(itemLength+pkg.head.getDataSize,pkg.head.nDataLen)
  }
  @Test
  def test_parse: Unit ={
    var pkg = new GBASE_ITEMPKG_OPSTRUCT
    pkg.fromStreamReader(getClass.getResourceAsStream("/pkg.dat"))
    println("itemName:",pkg.items.head.stHead.szItemName,"type:",pkg.items.head.stHead.nItemType)
    val requestItem = pkg.findItemByName("Request").get
    val request = new GNETREQUESTHEADOBJECT().fromByteArray(requestItem.bnRes)

    pkg = new GBASE_ITEMPKG_OPSTRUCT
    pkg.fromStreamReader(getClass.getResourceAsStream("/pkg2.dat"))
    println("itemName:",pkg.items.head.stHead.szItemName,"type:",pkg.items.head.stHead.nItemType)


    pkg = new GBASE_ITEMPKG_OPSTRUCT
    pkg.fromStreamReader(getClass.getResourceAsStream("/pkg3.dat"))
    println("itemName:",pkg.items.head.stHead.szItemName,"type:",pkg.items.head.stHead.nItemType)

    pkg = new GBASE_ITEMPKG_OPSTRUCT
    pkg.fromStreamReader(getClass.getResourceAsStream("/pkg4.dat"))
    println("itemName:",pkg.items.head.stHead.szItemName,"type:",pkg.items.head.stHead.nItemType)

    pkg = new GBASE_ITEMPKG_OPSTRUCT
    pkg.fromStreamReader(getClass.getResourceAsStream("/pkg5.dat"))
    println("itemName:",pkg.items.head.stHead.szItemName,"type:",pkg.items.head.stHead.nItemType)
    Assert.assertEquals(3,pkg.items.size)
    Assert.assertEquals("650000",new String(pkg.findItemByName("UnitCode").get.bnRes).trim)
  }
}

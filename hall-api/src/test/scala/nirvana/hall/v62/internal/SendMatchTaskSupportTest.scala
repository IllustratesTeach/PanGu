package nirvana.hall.v62.internal

import monad.support.services.LoggerSupport
import nirvana.hall.v62.internal.c.ganumia.gadbdef.GADB_KEYARRAY
import nirvana.hall.v62.internal.c.gbaselib.gitempkg.{GBASE_ITEMPKG_ITEMHEADSTRUCT, GBASE_ITEMPKG_PKGHEADSTRUCT}
import nirvana.hall.v62.internal.c.gloclib.gadbprop.GADBIDSTRUCT
import nirvana.hall.v62.internal.c.gloclib.{glocdef, gaqryque}
import nirvana.hall.v62.internal.c.gloclib.gaqryque.GAQUERYSTRUCT
import nirvana.hall.v62.internal.c.gloclib.glocdef.GAFISMICSTRUCT
import nirvana.hall.v62.internal.c.gloclib.gqrycond.GAFIS_QRYPARAM
import nirvana.hall.v62.services.AncientEnum.MatchType
import nirvana.hall.v62.services._
import org.apache.commons.io.IOUtils
import org.jboss.netty.buffer.ChannelBuffers
import org.junit.{Assert, Test}

import scala.language.postfixOps

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-02
 */
class SendMatchTaskSupportTest extends LoggerSupport{

  private val address = V62ServerAddress("10.1.6.119",6898,10,30,"afisadmin")
  @Test
  def test_query_match_result: Unit ={
    val sender = createSender()
    sender.queryMatchResult(address,7)
  }
  @Test
  def test_add: Unit ={
    val srcDb = DatabaseTable(1,2)
    val destDb = DatabaseTable(1,2)
    val options = new MatchOptions
    options.matchType = MatchType.TT
    //options.positions = Array[Int](1,2,3,4,5,6,7,8,9,10)
    options.srcDb = srcDb
    options.destDb = destDb

    val task = SelfMatchTask("3702022014000002",options)

    val sender = createSender()

    val queryStruct = new GAQUERYSTRUCT
    //fill simple data
    queryStruct.stSimpQry.nQueryType = task.options.matchType.ordinal().asInstanceOf[Byte]
    queryStruct.stSimpQry.nPriority = 1
    queryStruct.stSimpQry.nFlag = (gaqryque.GAQRY_FLAG_USEFINGER | gaqryque.GAQRY_FLAG_USETEXT).asInstanceOf[Byte]

    queryStruct.stSimpQry.stSrcDB.nDBID = task.options.srcDb.dbId.asInstanceOf[Short]
    queryStruct.stSimpQry.stSrcDB.nTableID= task.options.srcDb.tableId.asInstanceOf[Short]

    queryStruct.stSimpQry.stDestDB = Array(new GADBIDSTRUCT)
    queryStruct.stSimpQry.nDestDBCount = 1
    queryStruct.stSimpQry.stDestDB.apply(0).nDBID = task.options.destDb.dbId.asInstanceOf[Short]
    queryStruct.stSimpQry.stDestDB.apply(0).nTableID= task.options.destDb.tableId.asInstanceOf[Short]
    //queryStruct.stSimpQry.tSubmitTime.tDate.tYear = 115
    //queryStruct.stSimpQry.tSubmitTime.tDate.tMonth = 9
    //queryStruct.stSimpQry.tSubmitTime.tDate.tDay = 30

    val mic = new GAFISMICSTRUCT
    queryStruct.pstMIC_Data = Array(mic)
    queryStruct.nMICCount = queryStruct.pstMIC_Data.length

    mic.pstMnt_Data = IOUtils.toByteArray(getClass.getResourceAsStream("/t.mnt"))
    mic.nMntLen = mic.pstMnt_Data.length
    mic.nItemFlag = glocdef.GAMIC_ITEMFLAG_MNT.asInstanceOf[Byte]
    mic.nItemData = 1
    mic.nItemType = glocdef.GAMIC_ITEMTYPE_FINGER.asInstanceOf[Byte]



    //设置比对参数
    val item = new GAFIS_QRYPARAM
    item.stXgw.nMntMatchType = 4
    val itemDataLength = item.getDataSize
    val itemHead = new GBASE_ITEMPKG_ITEMHEADSTRUCT
    itemHead.szItemName = sender.GAFIS_QRYPARAM_GetName
    itemHead.nItemLen = itemDataLength

    val xmlData = "<?xml version=\"1.0\" encoding=\"gb2312\" ?>\n<GAFISTEXTSQL version=\"1.0\" caseidcanbenull=\"FALSE\">\n    <TABLESQL TID=\"2\" >\n        <![CDATA[ \n            ((CardID LIKE '11') AND (CreateUserName LIKE '22') AND ((Name LIKE '3'))) \n        ]]>\n    </TABLESQL>\n</GAFISTEXTSQL>\0".getBytes
    val itemHead2 = new GBASE_ITEMPKG_ITEMHEADSTRUCT
    itemHead2.szItemName = sender.GAFIS_TEXTSQL_GetName
    itemHead2.nItemLen = xmlData.length



    val itemPackage = new GBASE_ITEMPKG_PKGHEADSTRUCT
    itemPackage.nDataLen = itemPackage.getDataSize + itemHead.getDataSize + itemHead.nItemLen + itemHead2.getDataSize + itemHead2.nItemLen
    itemPackage.nBufSize = itemPackage.nDataLen

    val buffer = ChannelBuffers.buffer(itemPackage.nDataLen)
    itemPackage.writeToStreamWriter(buffer)

    itemHead.writeToStreamWriter(buffer)
    item.writeToStreamWriter(buffer)

    itemHead2.writeToStreamWriter(buffer)
    buffer.writeBytes(xmlData)


    val bytes = buffer.array()




    queryStruct.pstQryCond_Data = bytes
    queryStruct.nQryCondLen = queryStruct.pstQryCond_Data.length
    queryStruct.nItemFlagA = gaqryque.GAIFA_FLAG.asInstanceOf[Byte]

    queryStruct.stSimpQry.szKeyID="3702022014000002"




    val ret = sender.NET_GAFIS_QUERY_Add(address,20,2,queryStruct)


    Assert.assertTrue(ret> 0)
    println("sid:::",ret)
  }
  @Test
  def test_send2: Unit ={
    val srcDb = DatabaseTable(1,2)
    val destDb = DatabaseTable(1,2)
    val options = new MatchOptions
    options.matchType = MatchType.TT
    options.positions = Array[Int](1,2,3,4,5,6,7,8,9,10)
    options.srcDb = srcDb
    options.destDb = destDb

    val task = SelfMatchTask("3702022014000002",options)

    val sender = createSender()

    val idx= 1 to 10 map(x=>x.asInstanceOf[Byte]) toArray

    val key = "3702022014000002\0".getBytes
    val pstKey = new GADB_KEYARRAY
    pstKey.nKeyCount = 1
    pstKey.nKeySize = key.size.asInstanceOf[Short]
    pstKey.pKey_Data = key

    val queryStruct = new GAQUERYSTRUCT
    //fill simple data
    queryStruct.stSimpQry.nQueryType = task.options.matchType.ordinal().asInstanceOf[Byte]
    queryStruct.stSimpQry.nPriority = 1
    queryStruct.stSimpQry.nFlag = (gaqryque.GAQRY_FLAG_USEFINGER | gaqryque.GAQRY_FLAG_USETEXT).asInstanceOf[Byte]

    queryStruct.stSimpQry.stSrcDB.nDBID = task.options.srcDb.dbId.asInstanceOf[Short]
    queryStruct.stSimpQry.stSrcDB.nTableID= task.options.srcDb.tableId.asInstanceOf[Short]

    queryStruct.stSimpQry.stDestDB = Array(new GADBIDSTRUCT)
    queryStruct.stSimpQry.nDestDBCount = 1
    queryStruct.stSimpQry.stDestDB.apply(0).nDBID = task.options.destDb.dbId.asInstanceOf[Short]
    queryStruct.stSimpQry.stDestDB.apply(0).nTableID= task.options.destDb.tableId.asInstanceOf[Short]
    //queryStruct.stSimpQry.tSubmitTime.tDate.tYear = 115
    //queryStruct.stSimpQry.tSubmitTime.tDate.tMonth = 9
    //queryStruct.stSimpQry.tSubmitTime.tDate.tDay = 30


    //设置比对参数
    val item = new GAFIS_QRYPARAM
    item.stXgw.nMntMatchType = 4
    val itemDataLength = item.getDataSize
    val itemHead = new GBASE_ITEMPKG_ITEMHEADSTRUCT
    itemHead.szItemName = sender.GAFIS_QRYPARAM_GetName
    itemHead.nItemLen = itemDataLength

    val xmlData = "<?xml version=\"1.0\" encoding=\"gb2312\" ?>\n<GAFISTEXTSQL version=\"1.0\" caseidcanbenull=\"FALSE\">\n    <TABLESQL TID=\"2\" >\n        <![CDATA[ \n            ((CardID LIKE '11') AND (CreateUserName LIKE '22') AND ((Name LIKE '3'))) \n        ]]>\n    </TABLESQL>\n</GAFISTEXTSQL>\0".getBytes
    val itemHead2 = new GBASE_ITEMPKG_ITEMHEADSTRUCT
    itemHead2.szItemName = sender.GAFIS_TEXTSQL_GetName
    itemHead2.nItemLen = xmlData.length



    val itemPackage = new GBASE_ITEMPKG_PKGHEADSTRUCT
    itemPackage.nDataLen = itemPackage.getDataSize + itemHead.getDataSize + itemHead.nItemLen + itemHead2.getDataSize + itemHead2.nItemLen
    itemPackage.nBufSize = itemPackage.nDataLen

    val buffer = ChannelBuffers.buffer(itemPackage.nDataLen)
    itemPackage.writeToStreamWriter(buffer)

    itemHead.writeToStreamWriter(buffer)
    item.writeToStreamWriter(buffer)

    itemHead2.writeToStreamWriter(buffer)
    buffer.writeBytes(xmlData)


    val bytes = buffer.array()




    queryStruct.pstQryCond_Data = bytes
    queryStruct.nQryCondLen = queryStruct.pstQryCond_Data.length


    queryStruct.nItemFlagA = gaqryque.GAIFA_FLAG.asInstanceOf[Byte]


    val ret = sender.NET_GAFIS_QUERY_Submit(address,20,2,pstKey,queryStruct,idx)


    Assert.assertTrue(ret.size > 0)
    Assert.assertEquals(1,ret.head.nRetVal)
  }
  @Test
  def test_send: Unit ={
    val srcDb = DatabaseTable(1,2)
    val destDb = DatabaseTable(1,2)
    val options = new MatchOptions
    options.matchType = MatchType.TT
    options.positions = Array[Int](1,2,3,4,5,6,7,8,9,10)
    options.srcDb = srcDb
    options.destDb = destDb

    val task = SelfMatchTask("3702022014000002",options)

    val sender = createSender()


    val sid = sender.sendMatchTask(address,task)
    debug("sid :{}",sid)
    Assert.assertTrue(sid> 0)
  }
  private def createSender():SendMatchTaskSupport={
    new SendMatchTaskSupport with AncientClientSupport with LoggerSupport{

      override def serverAddress: V62ServerAddress = address
    }
  }
}

package nirvana.hall.v62.internal.c.gnetlib

import nirvana.hall.c.services.gbaselib.gitempkg.{GBASE_ITEMPKG_ITEMHEADSTRUCT, GBASE_ITEMPKG_PKGHEADSTRUCT}
import nirvana.hall.c.services.gloclib.gadbprop.GADBIDSTRUCT
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYSTRUCT
import nirvana.hall.c.services.gloclib.glocdef.GAFISMICSTRUCT
import nirvana.hall.c.services.gloclib.gqrycond.GAFIS_QRYPARAM
import nirvana.hall.c.services.gloclib.{gaqryque, glocdef}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gloclib.gaqryqueConverter
import nirvana.hall.v62.services.AncientEnum.MatchType
import nirvana.hall.v62.services.{DatabaseTable, MatchOptions, SelfMatchTask}
import org.apache.commons.io.IOUtils
import org.jboss.netty.buffer.ChannelBuffers
import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-10
 */
class ganetqryTest {

  @Test
  def test_add: Unit ={
    val config = new HallV62Config
    config.appServer.host = "192.168.1.17"
    config.appServer.port = 6898
    config.appServer.user = "afisadmin"

    val facade = new V62Facade(config)

    val srcDb = DatabaseTable(1,2)
    val destDb = DatabaseTable(1,2)
    val options = new MatchOptions
    options.matchType = MatchType.TT
    //options.positions = Array[Int](1,2,3,4,5,6,7,8,9,10)
    options.srcDb = srcDb
    options.destDb = destDb

    val task = SelfMatchTask("3702022014000002",options)


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
    itemHead.szItemName = gaqryqueConverter.GAFIS_QRYPARAM_GetName
    itemHead.nItemLen = itemDataLength

    val xmlData = "<?xml version=\"1.0\" encoding=\"gb2312\" ?>\n<GAFISTEXTSQL version=\"1.0\" caseidcanbenull=\"FALSE\">\n    <TABLESQL TID=\"2\" >\n        <![CDATA[ \n            ((CardID LIKE '11') AND (CreateUserName LIKE '22') AND ((Name LIKE '3'))) \n        ]]>\n    </TABLESQL>\n</GAFISTEXTSQL>\u0000".getBytes
    val itemHead2 = new GBASE_ITEMPKG_ITEMHEADSTRUCT
    itemHead2.szItemName = gaqryqueConverter.GAFIS_TEXTSQL_GetName
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




    val ret = facade.NET_GAFIS_QUERY_Add(20,2,queryStruct)


    Assert.assertTrue(ret> 0)
    println("sid:::",ret)

  }

  @Test
  def test_NET_GAFIS_QUERY_Get: Unit ={
    val config = new HallV62Config
    config.appServer.host = "127.0.0.1"
    config.appServer.port = 6898
    config.appServer.user = "afisadmin"

    val facade = new V62Facade(config)
    val pstQry = gaqryqueConverter.convertQueryId2GAQUERYSTRUCT(1)
    val gaQry = facade.NET_GAFIS_QUERY_Get(20,2, pstQry)

    println(gaQry.stSimpQry.szKeyID)

  }
}

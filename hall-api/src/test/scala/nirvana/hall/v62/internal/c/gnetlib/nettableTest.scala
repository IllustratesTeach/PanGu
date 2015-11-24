package nirvana.hall.v62.internal.c.gnetlib

import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.ganumia.gadbrec
import nirvana.hall.v62.internal.c.ganumia.gadbrec.{GADB_SELRESULT, GADB_SELRESITEM, GADB_SELSTATEMENT}
import org.junit.Test

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-24
 */
class nettableTest {
  @Test
  def test_select: Unit ={

    val config = new HallV62Config
    config.host = "127.0.0.1"
    config.port = 6798
    config.user = "afisadmin"
    config.password="helloafis"

    val facade = new V62Facade(config)

    val result = new GADB_SELRESULT
    val field1 = new GADB_SELRESITEM
    field1.szItemName = "CardId"

    result.pstItem_Data = Array(field1)
    result.nResItemCount = 1
    result.nItemFlag = gadbrec.SELRES_ITEM_RESITEM
    result.nFreeOption = gadbrec.SELRES_CANBEFREE_RESITEM

    result.nSegSize = 0

    val stmt: GADB_SELSTATEMENT = new GADB_SELSTATEMENT
    //stmt.szStatement="cardid = 12"
    stmt.stReadOpt.nFlag = gadbrec.READROW_FLAG_GETBLOBINFOONLY
    stmt.stReadOpt.nResColFmt = gadbrec.RESCOLFMT_NAMEGIVEN
    stmt.stReadOpt.nPosBy = gadbrec.READROW_POSBY_SELECT

    facade.NET_GAFIS_TABLE_Select(1,2,result,stmt)

    val bufResult = result.pDataBuf_Result
  }
}

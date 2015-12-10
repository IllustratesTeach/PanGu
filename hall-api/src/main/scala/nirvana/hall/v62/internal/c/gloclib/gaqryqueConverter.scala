package nirvana.hall.v62.internal.c.gloclib

import nirvana.hall.c.services.gbaselib.gitempkg.{GBASE_ITEMPKG_ITEMHEADSTRUCT, GBASE_ITEMPKG_PKGHEADSTRUCT}
import nirvana.hall.c.services.gloclib.gadbprop.GADBIDSTRUCT
import nirvana.hall.c.services.gloclib.gaqryque
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYSTRUCT
import nirvana.hall.c.services.gloclib.gqrycond.GAFIS_QRYPARAM
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.v62.config.HallV62Config
import org.jboss.netty.buffer.ChannelBuffers

/**
 * Created by songpeng on 15/12/9.
 */
object gaqryqueConverter {
  final val GAFIS_KEYLIST_GetName = "KeyList"
  final val GAFIS_QRYPARAM_GetName = "QryParam"
  final val GAFIS_QRYFILTER_GetName = "QryFilter"
  final val GAFIS_CANDKEYFILTER_GetName = "CandKeyFilter"
  final val GAFIS_TEXTSQL_GetName = "TextSql"

  def convertProtoBuf2GAQUERYSTRUCT(matchTask: MatchTask)(implicit v62Config: HallV62Config): GAQUERYSTRUCT = {
    val queryStruct = new GAQUERYSTRUCT
    queryStruct.stSimpQry.nQueryType = matchTask.getMatchType.ordinal().asInstanceOf[Byte]
    queryStruct.stSimpQry.nPriority = matchTask.getPriority.toByte
    queryStruct.stSimpQry.nFlag = (gaqryque.GAQRY_FLAG_USEFINGER).asInstanceOf[Byte]

    matchTask.getMatchType match {
      case MatchType.FINGER_TT =>
        queryStruct.stSimpQry.stSrcDB.nDBID = v62Config.templateTable.dbId.asInstanceOf[Short]
        queryStruct.stSimpQry.stSrcDB.nTableID= v62Config.templateTable.tableId.asInstanceOf[Short]
        queryStruct.stSimpQry.stDestDB = Array(new GADBIDSTRUCT)
        queryStruct.stSimpQry.nDestDBCount = 1
        queryStruct.stSimpQry.stDestDB.apply(0).nDBID = v62Config.templateTable.dbId.asInstanceOf[Short]
        queryStruct.stSimpQry.stDestDB.apply(0).nTableID= v62Config.templateTable.tableId.asInstanceOf[Short]
      case MatchType.FINGER_TL =>
        queryStruct.stSimpQry.stSrcDB.nDBID = v62Config.templateTable.dbId.asInstanceOf[Short]
        queryStruct.stSimpQry.stSrcDB.nTableID= v62Config.templateTable.tableId.asInstanceOf[Short]
        queryStruct.stSimpQry.stDestDB = Array(new GADBIDSTRUCT)
        queryStruct.stSimpQry.nDestDBCount = 1
        queryStruct.stSimpQry.stDestDB.apply(0).nDBID = v62Config.latentTable.dbId.asInstanceOf[Short]
        queryStruct.stSimpQry.stDestDB.apply(0).nTableID= v62Config.latentTable.tableId.asInstanceOf[Short]
      case MatchType.FINGER_LT =>
        queryStruct.stSimpQry.stSrcDB.nDBID = v62Config.latentTable.dbId.asInstanceOf[Short]
        queryStruct.stSimpQry.stSrcDB.nTableID= v62Config.latentTable.tableId.asInstanceOf[Short]
        queryStruct.stSimpQry.stDestDB = Array(new GADBIDSTRUCT)
        queryStruct.stSimpQry.nDestDBCount = 1
        queryStruct.stSimpQry.stDestDB.apply(0).nDBID = v62Config.templateTable.dbId.asInstanceOf[Short]
        queryStruct.stSimpQry.stDestDB.apply(0).nTableID= v62Config.templateTable.tableId.asInstanceOf[Short]
      case MatchType.FINGER_LL =>
        queryStruct.stSimpQry.stSrcDB.nDBID = v62Config.latentTable.dbId.asInstanceOf[Short]
        queryStruct.stSimpQry.stSrcDB.nTableID= v62Config.latentTable.tableId.asInstanceOf[Short]
        queryStruct.stSimpQry.stDestDB = Array(new GADBIDSTRUCT)
        queryStruct.stSimpQry.nDestDBCount = 1
        queryStruct.stSimpQry.stDestDB.apply(0).nDBID = v62Config.latentTable.dbId.asInstanceOf[Short]
        queryStruct.stSimpQry.stDestDB.apply(0).nTableID= v62Config.latentTable.tableId.asInstanceOf[Short]
      case other =>
    }

    //设置比对参数
    val item = new GAFIS_QRYPARAM
    item.stXgw.bFullMatchOn = matchTask.getConfig.getFullMatchOn.asInstanceOf[Byte]

    val itemDataLength = item.getDataSize
    val itemHead = new GBASE_ITEMPKG_ITEMHEADSTRUCT
    itemHead.szItemName = GAFIS_TEXTSQL_GetName
    itemHead.nItemLen = itemDataLength

    val itemPackage = new GBASE_ITEMPKG_PKGHEADSTRUCT
    itemPackage.nDataLen = itemPackage.getDataSize + itemHead.getDataSize + itemHead.nItemLen
    itemPackage.nBufSize = itemPackage.nDataLen

    val buffer = ChannelBuffers.buffer(itemPackage.nDataLen)
    itemPackage.writeToStreamWriter(buffer)

    itemHead.writeToStreamWriter(buffer)
    item.writeToStreamWriter(buffer)

    val bytes = buffer.array()
    queryStruct.pstQryCond_Data = bytes
    queryStruct.nQryCondLen = queryStruct.pstQryCond_Data.length

    queryStruct.nItemFlagA = gaqryque.GAIFA_FLAG.asInstanceOf[Byte]

    queryStruct
  }

}

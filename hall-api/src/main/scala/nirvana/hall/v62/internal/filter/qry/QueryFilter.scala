package nirvana.hall.v62.internal.filter.qry

import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.c.services.ganumia.gadbdef.GADB_KEYARRAY
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse}
import nirvana.hall.protocol.v62.qry.QueryProto.{QuerySendResponse, QueryGetRequest, QueryGetResponse, QuerySendRequest}
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v62.internal.c.gloclib.gaqryqueConverter

/**
 * Created by songpeng on 15/12/9.
 */
class QueryFilter(facade: V62Facade, v62Config: HallV62Config) extends ProtobufRequestFilter {

  override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
    if (protobufRequest.hasExtension(QuerySendRequest.cmd)) {
      val request = protobufRequest.getExtension(QuerySendRequest.cmd)
      val response = QuerySendResponse.newBuilder()
      val matchTask = request.getMatchTaskList.get(0)
      val key = matchTask.getMatchId.getBytes()
      val pstKey = new GADB_KEYARRAY
      pstKey.nKeyCount = 1
      pstKey.nKeySize = key.size.asInstanceOf[Short]
      pstKey.pKey_Data = key

      val idx = 1 to 10 map (x => x.asInstanceOf[Byte]) toArray

      val queryStruct = gaqryqueConverter.convertProtoBuf2GAQUERYSTRUCT(matchTask)(v62Config)
      val retvals = facade.NET_GAFIS_QUERY_Submit(v62Config.queryTable.dbId.toShort, v62Config.queryTable.tableId.toShort, pstKey, queryStruct, idx)
      retvals.foreach{retval =>
        val queryId = gaqryqueConverter.convertSixByteArrayToLong(retval.nSID)
        response.setOraSid(queryId)
      }
      responseBuilder.setExtension(QuerySendResponse.cmd, response.build())

      true
    } else if (protobufRequest.hasExtension(QueryGetRequest.cmd)) {
      val request = protobufRequest.getExtension(QueryGetRequest.cmd)
      val response = QueryGetResponse.newBuilder()
      val pstQry = gaqryqueConverter.convertQueryId2GAQUERYSTRUCT(request.getOraSid)
      val gaQueryStruct = facade.NET_GAFIS_QUERY_Get(v62Config.queryTable.dbId.toShort, v62Config.queryTable.tableId.toShort, pstQry)

      val matchResult = gaqryqueConverter.convertGAQUERYSTRUCT2ProtoBuf(gaQueryStruct)
      response.setMatchResult(matchResult)

      responseBuilder.setExtension(QueryGetResponse.cmd, response.build())
      true
    } else {
      handler.handle(protobufRequest, responseBuilder)
    }
  }

}

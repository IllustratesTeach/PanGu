package nirvana.hall.v62.internal.filter.qry

import nirvana.hall.api.services.{ProtobufRequestFilter, ProtobufRequestHandler}
import nirvana.hall.protocol.sys.CommonProto.{BaseRequest, BaseResponse}
import nirvana.hall.protocol.v62.qry.QueryProto.QuerySendRequest
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.c.services.ganumia.gadbdef.GADB_KEYARRAY
import nirvana.hall.v62.internal.c.gloclib.gaqryqueConverter

/**
  * Created by songpeng on 15/12/9.
  */
class QueryFilter(facade: V62Facade, v62Config: HallV62Config) extends ProtobufRequestFilter{
 override def handle(protobufRequest: BaseRequest, responseBuilder: BaseResponse.Builder, handler: ProtobufRequestHandler): Boolean = {
   if(protobufRequest.hasExtension(QuerySendRequest.cmd)){
     val request = protobufRequest.getExtension(QuerySendRequest.cmd)
     val matchTask = request.getMatchTaskList.get(0)
     val key = matchTask.getMatchId.getBytes()
     val pstKey = new GADB_KEYARRAY
     pstKey.nKeyCount = 1
     pstKey.nKeySize = key.size.asInstanceOf[Short]
     pstKey.pKey_Data = key

     val idx= 1 to 10 map(x=>x.asInstanceOf[Byte]) toArray

     val queryStruct = gaqryqueConverter.convertProtoBuf2GAQUERYSTRUCT(matchTask)(v62Config)
     facade.NET_GAFIS_QUERY_Submit(20, 2, pstKey, queryStruct, idx)

     true
   }else{
     handler.handle(protobufRequest, responseBuilder)
   }
 }
}

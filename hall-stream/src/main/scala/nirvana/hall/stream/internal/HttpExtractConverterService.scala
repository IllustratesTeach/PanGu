package nirvana.hall.stream.internal

import java.io.InputStream

import com.google.protobuf.ByteString
import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.protocol.extract.OldConverterNewProto.{OldConverterNewResponse, OldConverterNewRequest}
import nirvana.hall.stream.config.NirvanaHallStreamConfig
import nirvana.hall.stream.services.{FeatureConverterService}
import nirvana.hall.support.services.RpcHttpClient
import org.apache.commons.io.IOUtils

/**
 * Created by wangjue on 2016/5/24.
 */
class HttpExtractConverterService(url: String, rpcHttpClient: RpcHttpClient,config:NirvanaHallStreamConfig) extends FeatureConverterService{

  override def converter(oldMnt : InputStream): Option[ByteString] = {
    val request = OldConverterNewRequest.newBuilder()
    request.setOldMnt(ByteString.copyFrom(IOUtils.toByteArray(oldMnt)))
    val baseResponse = rpcHttpClient.call(url, OldConverterNewRequest.cmd, request.build())
    baseResponse.getStatus  match{
      case CommandStatus.OK =>
        if(baseResponse.hasExtension(OldConverterNewResponse.cmd)) {
          val response = baseResponse.getExtension(OldConverterNewResponse.cmd)
          Some(response.getNewMnt)
        }else{
          throw new IllegalAccessException("response haven't OldConverterNewResponse")
        }
      case CommandStatus.FAIL =>
        throw new IllegalAccessException("fail to converter,server message:%s".format(baseResponse.getMsg))
    }
  }

}

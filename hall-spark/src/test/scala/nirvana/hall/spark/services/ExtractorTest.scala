package nirvana.hall.spark.services

import java.io.File

import com.google.protobuf.ByteString
import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.extract.ExtractProto
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.{ExtractRequest, ExtractResponse, FingerPosition}
import org.apache.commons.io.FileUtils
import org.junit.Test

/**
  * Created by wangjue on 2016/12/21.
  */
class ExtractorTest {

  @Test
  def rpcExtractor()  : Unit = {
    val img = FileUtils.readFileToByteArray(new File("C:\\Users\\wangjue\\Desktop\\tjFPT\\crash\\2897_R_01_crash.img"))

    val rpcHttpClient = SparkFunctions.httpClient
    val request = ExtractRequest.newBuilder()
    request.setImgData(ByteString.copyFrom(new GAFISIMAGESTRUCT().fromByteArray(img).toByteArray()))
    request.setFeatureTry(ExtractProto.NewFeatureTry.V1)
    request.setMntType(FeatureType.FingerTemplate)
    request.setPosition(FingerPosition.FINGER_R_THUMB)
    val baseResponse = rpcHttpClient.call("http://10.1.7.144:9002/extractor", ExtractRequest.cmd, request.build())
    baseResponse.getStatus match {
      case CommandStatus.OK =>
        if (baseResponse.hasExtension(ExtractResponse.cmd)) {
          val response = baseResponse.getExtension(ExtractResponse.cmd)
          val mntData = response.getMntData
          //val binData = response.getBinData
          FileUtils.writeByteArrayToFile(new File("D:\\mnt.mnt"),mntData.toByteArray)

        } else {
          throw new IllegalAccessException("response haven't ExtractResponse")
        }
      case CommandStatus.FAIL =>
        throw new IllegalAccessException("fail to extractor,server message:%s".format(baseResponse.getMsg))
    }
  }

}

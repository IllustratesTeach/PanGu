package nirvana.hall.spark.services

import java.io.File

import com.google.protobuf.ByteString
import monad.rpc.protocol.CommandProto.CommandStatus
import nirvana.hall.protocol.extract.FeatureDisplayProto.{FeatureDisplayRequest, FeatureDisplayResponse}
import nirvana.hall.support.services.WebHttpClientUtils
import org.apache.commons.io.{FileUtils, IOUtils}
import org.junit.{Assert, Test}

/**
  * Created by wangjue on 2017/1/22.
  */
class FeatureDisplayTest {
  private lazy val imgData = getClass.getResourceAsStream("/display.img")
  private lazy val mntData  = getClass.getResourceAsStream("/display.mnt")

  @Test
  def test_rpc_display : Unit = {
    val rpcHttpClient = SparkFunctions.httpClient
    val url = "http://127.0.0.1:9001/extractor"
    val img = IOUtils.toByteArray(imgData)
    val mnt = IOUtils.toByteArray(mntData)
    val request = FeatureDisplayRequest.newBuilder()
    request.setDecompressImageData(ByteString.copyFrom(img))
    request.setMntDispData(ByteString.copyFrom(mnt))
    val baseResponse = rpcHttpClient.call(url,FeatureDisplayRequest.cmd,request.build())
    baseResponse.getStatus match {
      case CommandStatus.OK =>
        if (baseResponse.hasExtension(FeatureDisplayResponse.cmd)) {
          val response = baseResponse.getExtension(FeatureDisplayResponse.cmd)
          Assert.assertNotNull(response.getDisplayData.toByteArray)
          FileUtils.writeByteArrayToFile(new File("display_img_mnt_rpc.bmp"),response.getDisplayData.toByteArray)
        } else {
          throw new IllegalAccessException("response haven't FeatureDisplayResponse")
        }
      case CommandStatus.FAIL =>
        throw new IllegalAccessException("draw feature fail!,server message:%s".format(baseResponse.getMsg))
    }
  }
}

package nirvana.hall.stream.internal

import com.google.protobuf.{ByteString, ExtensionRegistry}
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.c.services.kernel.mnt_def.FINGERMNTSTRUCT
import nirvana.hall.protocol.extract.ExtractProto
import nirvana.hall.protocol.extract.ExtractProto.ExtractRequest.FeatureType
import nirvana.hall.protocol.extract.ExtractProto.FingerPosition
import nirvana.hall.protocol.image.FirmImageDecompressProto
import nirvana.hall.protocol.image.FirmImageDecompressProto.FirmImageDecompressRequest
import nirvana.hall.stream.config.NirvanaHallStreamConfig
import nirvana.hall.support.internal.RpcHttpClientImpl
import org.apache.commons.io.IOUtils
import org.apache.spark.{SparkConf, SparkContext}

/**
 * test spark framework
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-30
 */
object SparkTest {
  //@Test
  //def test_spark: Unit ={
  def main(args:Array[String]):Unit={
    val conf = new SparkConf().setAppName("mytest").setMaster("spark://jcai-macbook.local:7077")
    val sc = new SparkContext(conf)

    sc.textFile("/Users/jcai/workspace/finger/nirvana-hall/*.xml")
      .map(_.length).reduce((a,b)=>a+b)
      /*
      .map(SparkFunctions.requestRemoteFile)
      .flatMap(SparkFunctions.requestDecompress)
      .flatMap(SparkFunctions.requestExtract)
      .foreach(x=>println("feature number"+x.cm.toInt))
      //.saveAsObjectFile("target/result.obj")
      */
  }
}
object SparkFunctions{
  def requestRemoteFile(url:String): GAFISIMAGESTRUCT ={
    println("processing "+url)
    val is = getClass.getResourceAsStream("/wsq.data")
    val bnData = IOUtils.toByteArray(is)
    IOUtils.closeQuietly(is)

    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.bIsCompressed = 1
    gafisImg.stHead.nCompressMethod = glocdef.GAIMG_CPRMETHOD_WSQ.toByte
    gafisImg.stHead.nImageType = glocdef.GAIMG_IMAGETYPE_FINGER.toByte
    gafisImg.stHead.nWidth = 640
    gafisImg.stHead.nBits= 8
    gafisImg.stHead.nResolution = 500
    gafisImg.stHead.nHeight= 640

    gafisImg.bnData = bnData
    gafisImg.stHead.nImgSize = bnData.length

    gafisImg
  }
  def requestDecompress(compressedImg: GAFISIMAGESTRUCT):Option[GAFISIMAGESTRUCT]={
    val registry = ExtensionRegistry.newInstance()
    FirmImageDecompressProto.registerAllExtensions(registry)
    val httpClient = new RpcHttpClientImpl(registry)

    val service = new HttpDecompressService("http://127.0.0.1:9001/image",httpClient)
    val request = FirmImageDecompressRequest.newBuilder()
    request.setCprData(ByteString.copyFrom(compressedImg.toByteArray))
    val response = service.decompress(request.build())
    response
  }

  def requestExtract(originalImg:GAFISIMAGESTRUCT):Option[FINGERMNTSTRUCT]={
    val registry = ExtensionRegistry.newInstance()
    ExtractProto.registerAllExtensions(registry)
    val httpClient = new RpcHttpClientImpl(registry)
    val config = new NirvanaHallStreamConfig
    config.stream.isNewFeature= false
    val service = new HttpExtractService("http://127.0.0.1:9002/extractor", httpClient,config)

    val mnt = service.extract(originalImg, FingerPosition.FINGER_L_THUMB, FeatureType.FingerTemplate)
    mnt.map(x=>new FINGERMNTSTRUCT().fromByteArray(x.toByteArray))
  }
}

package nirvana.hall.stream.internal

import java.io.{File, FileInputStream}
import java.util.concurrent.{CountDownLatch, Executors}

import com.google.protobuf.{ByteString, ExtensionRegistry}
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.protocol.image.FirmImageDecompressProto
import nirvana.hall.protocol.image.FirmImageDecompressProto.FirmImageDecompressRequest
import nirvana.hall.support.internal.RpcHttpClientImpl
import org.apache.commons.io.{FileUtils, IOUtils}
import org.junit.{Assert, Test}

/**
 * Created by songpeng on 15/12/15.
 */
class HttpDecompressServiceTest {
  @Test
  def test_daiku: Unit ={
    //LocalRpcModule.buildProtobufRegistroy(config)
    val registry = ExtensionRegistry.newInstance()
    FirmImageDecompressProto.registerAllExtensions(registry)
    val httpClient = new RpcHttpClientImpl(registry)

    val service = new HttpDecompressService("http://10.1.7.98:9001/image",httpClient)
    val executor = Executors.newFixedThreadPool(10)

    val files = FileUtils.listFiles(new File("/Users/jcai/Downloads/cpr-1"),Array("cpr"),true)
    val it = files.iterator()
    val countDownLatch = new CountDownLatch(files.size())
    while(it.hasNext){
      val file = it.next()
      executor.execute(new Runnable {
        override def run(): Unit = {
          var gafisImg:GAFISIMAGESTRUCT = null
          try {
            println("begin to decompress " + file)
            val request = FirmImageDecompressRequest.newBuilder()
            val is = new FileInputStream(file)
            val data = IOUtils.toByteArray(is)

            gafisImg = new GAFISIMAGESTRUCT
            gafisImg.fromByteArray(data)
            /*
          gafisImg.stHead.bIsCompressed = 1
          gafisImg.stHead.nCompressMethod = glocdef.GAIMG_CPRMETHOD_WSQ.toByte
          gafisImg.bnData = bnData
          gafisImg.stHead.nImgSize = bnData.length
          */

            request.setCprData(ByteString.copyFrom(data))
            val result = service.decompress(request.build())
            Assert.assertTrue(result.isDefined)
            println("finish to decompress " + file)
          }catch{
            case e:Throwable=>
              println("fail to decompress ....",e.toString)
          }finally{
            countDownLatch.countDown()
          }
        }
      })
    }
    countDownLatch.await()
    val request = FirmImageDecompressRequest.newBuilder()
    val is = getClass.getResourceAsStream("/wsq.data")
    val bnData = IOUtils.toByteArray(is)

    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.bIsCompressed = 1
    gafisImg.stHead.nCompressMethod = glocdef.GAIMG_CPRMETHOD_WSQ.toByte
    gafisImg.bnData = bnData
    gafisImg.stHead.nImgSize = bnData.length

    request.setCprData(ByteString.copyFrom(gafisImg.toByteArray))
    val result = service.decompress(request.build())
    Assert.assertTrue(result.isDefined)
    Assert.assertEquals(409600,result.get.bnData.length)

  }

  @Test
  def test_decompress: Unit ={
    //LocalRpcModule.buildProtobufRegistroy(config)
    val registry = ExtensionRegistry.newInstance()
    FirmImageDecompressProto.registerAllExtensions(registry)
    val httpClient = new RpcHttpClientImpl(registry)

    val service = new HttpDecompressService("http://127.0.0.1:9001/image",httpClient)
    val request = FirmImageDecompressRequest.newBuilder()
    val is = getClass.getResourceAsStream("/wsq.data")
    val bnData = IOUtils.toByteArray(is)

    val gafisImg = new GAFISIMAGESTRUCT
    gafisImg.stHead.bIsCompressed = 1
    gafisImg.stHead.nCompressMethod = glocdef.GAIMG_CPRMETHOD_WSQ.toByte
    gafisImg.bnData = bnData
    gafisImg.stHead.nImgSize = bnData.length

    request.setCprData(ByteString.copyFrom(gafisImg.toByteArray))
    val result = service.decompress(request.build())
    Assert.assertTrue(result.isDefined)
    Assert.assertEquals(409600,result.get.bnData.length)
  }
}

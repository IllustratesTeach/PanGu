package nirvana.hall.stream.internal

import java.io.{File, FileInputStream}
import java.util.concurrent.{CountDownLatch, Executors}

import com.google.protobuf.{ByteString, ExtensionRegistry}
import nirvana.hall.c.services.AncientData.AncientDataException
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.c.services.gfpt4lib.FPTFile.FPTParseException
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
  def test_performance: Unit ={
    //LocalRpcModule.buildProtobufRegistroy(config)
    val registry = ExtensionRegistry.newInstance()
    FirmImageDecompressProto.registerAllExtensions(registry)
    val httpClient = new RpcHttpClientImpl(registry)

    val service = new HttpDecompressService("http://127.0.0.1:9999/image",httpClient)
    val executor = Executors.newFixedThreadPool(5)



    val files = FileUtils.listFiles(new File("/Users/jcai/Downloads/fpt-files/2/error"),Array[String]("fpt","FPT","fptt"),true)
    val it = files.iterator()
    while(it.hasNext){
      val file = it.next()
      //      println("processing "+file.getAbsolutePath)
      try {
        val is = new FileInputStream(file)
        val result = FPTFile.parseFromInputStream(is)
        IOUtils.closeQuietly(is)

        /*
    gafisImg.stHead.bIsCompressed = 1
    gafisImg.stHead.nCompressMethod = glocdef.GAIMG_CPRMETHOD_WSQ.toByte
    gafisImg.bnData = bnData
    gafisImg.stHead.nImgSize = bnData.length
    */

        //val result = service.decompress(request.build())

        var method:String = null
        result match{
          case Right(fpt4)=>
            assert(fpt4.fileLength.toInt == fpt4.getDataSize)
            val tpCount = fpt4.tpCount.toInt
            if(tpCount > 0){
              assert(tpCount == fpt4.logic02Recs.size)
              fpt4.logic02Recs.foreach{tp=>
                val fingerCount = tp.sendFingerCount.toInt
                assert(fingerCount == tp.fingers.size)
                tp.fingers.foreach{tData=>
                  method = tData.imgCompressMethod
                  //println("compress method "+tData.imgCompressMethod)
                  if(method == "1700"){
                    val gafisImg = new GAFISIMAGESTRUCT
                    gafisImg.stHead.nWidth = tData.imgHorizontalLength.toShort
                    gafisImg.stHead.nHeight = tData.imgVerticalLength.toShort
                    gafisImg.stHead.bIsCompressed = 1
                    gafisImg.stHead.nCompressMethod= glocdef.GAIMG_CPRMETHOD_COGENT
                    gafisImg.stHead.nImgSize = tData.imgDataLength.toInt
                    gafisImg.stHead.nBits = 8

                    gafisImg.bnData = tData.imgData

                    if(gafisImg.stHead.nImgSize == 0) {
                      println("image is zero")
                    }else {
                      executor.execute{new Runnable {
                        override def run(): Unit = {
                          print(method + " " + file.getAbsolutePath+" ")
                          val request = FirmImageDecompressRequest.newBuilder()
                          request.setCprData(ByteString.copyFrom(gafisImg.toByteArray))
                          service.decompress(request.build())
                          println("ok")
                        }
                      }}
                    }

                  }
                }
              }
            }
          case Left(fpt3) =>
            val tpCount = fpt3.tpCount.toInt
            if(tpCount >0) {
              var method:String = null
              assert(fpt3.tpCount.toInt == fpt3.logic3Recs.size)
              fpt3.logic3Recs.foreach{tp=>
                val fingerCount = tp.sendFingerCount.toInt
                assert(fingerCount == tp.fingers.size)
                tp.fingers.foreach{tData=>
                  method = tData.imgCompressMethod
                  //println("compress method "+tData.imgCompressMethod)
                  if(method == "1700"){
                    val gafisImg = new GAFISIMAGESTRUCT
                    gafisImg.stHead.bIsCompressed = 1
                    gafisImg.stHead.nCompressMethod= glocdef.GAIMG_CPRMETHOD_COGENT
                    gafisImg.stHead.nImgSize = tData.imgDataLength.toInt
                    gafisImg.stHead.nBits = 8

                    gafisImg.bnData = tData.imgData

                    println(method + " " + file.getAbsolutePath)
                    executor.execute(new Runnable {
                      override def run(): Unit = {
                        print(method + " " + file.getAbsolutePath+" ")
                        val request = FirmImageDecompressRequest.newBuilder()
                        request.setCprData(ByteString.copyFrom(gafisImg.toByteArray))
                        service.decompress(request.build())
                      }
                    })

                  }
                }
              }
            }
        }

      }catch{
        case e:FPTParseException =>
        case e:AncientDataException =>
          //do nothing
        case e:Throwable=>
          e.printStackTrace()
        //          println(file.getAbsolutePath+" \n\te:"+e.toString)
      }
    }
  }
  @Test
  def test_daiku: Unit ={
    //LocalRpcModule.buildProtobufRegistroy(config)
    val registry = ExtensionRegistry.newInstance()
    FirmImageDecompressProto.registerAllExtensions(registry)
    val httpClient = new RpcHttpClientImpl(registry)

    val service = new HttpDecompressService("http://10.1.7.98:9001/image",httpClient)
    val executor = Executors.newFixedThreadPool(5)

    val files = FileUtils.listFiles(new File("/Users/jcai/Downloads/cpr-1"),Array("cpr"),true)
    val it = files.iterator()
    val loop = 100
    val countDownLatch = new CountDownLatch(files.size() * loop)
    while(it.hasNext){
      val file = it.next()
      executor.execute(new Runnable {
        override def run(): Unit = {
          0.until(loop).foreach { i =>
            var gafisImg: GAFISIMAGESTRUCT = null
            try {
              //            println("begin to decompress " + file)
              val request = FirmImageDecompressRequest.newBuilder()
              val is = new FileInputStream(file)
              val data = IOUtils.toByteArray(is)
              IOUtils.closeQuietly(is)

              gafisImg = new GAFISIMAGESTRUCT
              gafisImg.fromByteArray(data)
              println(gafisImg.stHead.nCompressMethod)
              /*
          gafisImg.stHead.bIsCompressed = 1
          gafisImg.stHead.nCompressMethod = glocdef.GAIMG_CPRMETHOD_WSQ.toByte
          gafisImg.bnData = bnData
          gafisImg.stHead.nImgSize = bnData.length
          */

              request.setCprData(ByteString.copyFrom(data))
              val result = service.decompress(request.build())
              Assert.assertTrue(result.isDefined)
              //            println("finish to decompress " + file)
            } catch {
              case e: Throwable =>
                println("fail to decompress ....", e.toString)
            } finally {
              countDownLatch.countDown()
            }
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

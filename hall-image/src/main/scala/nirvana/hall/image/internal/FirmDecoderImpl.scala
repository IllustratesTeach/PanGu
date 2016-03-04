package nirvana.hall.image.internal

import java.io.File
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReentrantLock

import monad.core.MonadCoreSymbols
import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gloclib.glocdef
import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT
import nirvana.hall.image.config.ImageConfigSupport
import nirvana.hall.image.jni.NativeImageConverter
import nirvana.hall.image.services.FirmDecoder
import org.apache.commons.io.FileUtils
import org.apache.commons.io.filefilter.AbstractFileFilter
import org.apache.tapestry5.ioc.annotations.Symbol
import org.jnbis.WsqDecoder

import scala.collection.JavaConversions._
import scala.util.control.NonFatal

/**
 * firm decoder
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
class FirmDecoderImpl(@Symbol(MonadCoreSymbols.SERVER_HOME) serverHome:String,imageConfigSupport: ImageConfigSupport) extends FirmDecoder{
  private val dlls = new ConcurrentHashMap[String,Dll]()
  private case class Dll(Handle:Long,lockOpt:Option[ReentrantLock]){
    @volatile
    var isOk = true
  }
  private val lock = new ReentrantLock()
  private val wsqDecoder = new WsqDecoder

  /**
   * decode compressed data using firm's algorithm
   * @return original image data
   */
  def decode(gafisImg:GAFISIMAGESTRUCT): GAFISIMAGESTRUCT={
    if(gafisImg.stHead.bIsCompressed == 0)
      return gafisImg

    val cprMethod = gafisImg.stHead.nCompressMethod.toInt
    val firmCode = getCodeFromGAFISImage(gafisImg)

    val destImg = new GAFISIMAGESTRUCT
    destImg.stHead.fromByteArray(gafisImg.stHead.toByteArray)
    destImg.stHead.bIsCompressed = 0
    destImg.stHead.nCompressMethod = 0
    destImg.stHead.nBits = 8

    firmCode match{
      case fpt4code.GAIMG_CPRMETHOD_WSQ_CODE=>
        /*

        val img = wsqDecoder.decode(gafisImg.bnData)
        destImg.stHead.nWidth = img.getWidth.toShort
        destImg.stHead.nHeight = img.getHeight.toShort

        destImg.stHead.nImgSize = img.getPixels.length
        destImg.bnData = imgixels
        */
        val img = NativeImageConverter.decodeByWSQ(gafisImg.bnData)
        destImg.stHead.nImgSize = img.getData.length
        destImg.stHead.nWidth = img.getWidth.toShort
        destImg.stHead.nHeight = img.getHeight.toShort
        destImg.bnData = img.getData

        if(img.getPpi > 0)
          destImg.stHead.nResolution = img.getPpi.toShort
        else
          destImg.stHead.nResolution = 500 //default ppi
      case fpt4code.GAIMG_CPRMETHOD_EGFS_CODE =>

        val destImgSize = gafisImg.stHead.nWidth * gafisImg.stHead.nHeight
        val destImgBin = new Array[Byte](64 + destImgSize)

        NativeImageConverter.decodeByGFS(gafisImg.bnData,destImgBin)
        destImg.fromByteArray(destImgBin)

        destImg.stHead.bIsCompressed = 0
        destImg.stHead.nCompressMethod = 0
        if(destImg.stHead.nBits <=0)
          destImg.stHead.nBits = 8

        destImg.stHead.nResolution = gafisImg.stHead.nResolution
        if(destImg.stHead.nResolution == 0)
          destImg.stHead.nResolution = 500 //default ppi

      case other=>

        /**
         * 大库测试时候，发现虽然宽度和高度是500,但是实际输出还是640X640,
         * 所以此处最小buffer作为640X640
         */
        var width = gafisImg.stHead.nWidth
        if(width < 640) width = 640
        var height = gafisImg.stHead.nHeight
        if(height < 640) height = 640

        destImg.stHead.nWidth = width
        destImg.stHead.nHeight = height

        val dll = findDllHandle(firmCode,cprMethod)

        val bits = Helper_GetBitsPerPixel(gafisImg.stHead.nBits)
        val destImgSize = Helper_GetImageSize(width,height,bits)

        val cprData = gafisImg.bnData
        if(destImgSize == 0)
          throw new IllegalStateException("width or height is zero!")
        if(cprData == null ||cprData.length == 0)
          throw new IllegalStateException("compressed data is zero!")
        try {
          val originalData = dll.lockOpt match {
            case Some(locker) =>
              try {
                locker.lock()
                if(dll.isOk) {
//                  System.err.println("preparing decompress %s cprMethod:%s cprDataLength:%s destImgSize:%s width:%s height:%s".format(firmCode,cprMethod,cprData.length,destImgSize,width,height))
                  NativeImageConverter.decodeByManufactory(dll.Handle, firmCode, cprMethod, cprData, destImgSize)
                }else{
                  throw new IllegalAccessException("%s dll is unloaded".format(firmCode))
                }
              }catch{
                case e:ArithmeticException =>
                  if(e.toString.indexOf("-100")>0){//unload dll
                    dlls.remove(firmCode)
                    dll.isOk = false
                    NativeImageConverter.freeLibrary(dll.Handle)
                  }
                  throw e
              } finally {
                locker.unlock()
              }
            case None =>
              NativeImageConverter.decodeByManufactory(dll.Handle, firmCode, cprMethod, cprData, destImgSize)
          }
          destImg.bnData = originalData.getData
          destImg.stHead.nImgSize = destImg.bnData.length
          if (originalData.getWidth > 0)
            destImg.stHead.nWidth = originalData.getWidth.toShort
          if (originalData.getHeight > 0)
            destImg.stHead.nHeight = originalData.getHeight.toShort
          if (originalData.getPpi > 0)
            destImg.stHead.nResolution = originalData.getPpi.toShort
          if(destImg.stHead.nImgSize != destImg.stHead.nHeight * destImg.stHead.nHeight)
            System.err.println("decompress %s success imgSize:%s width:%s,height:%s".format(firmCode,destImg.stHead.nImgSize,destImg.stHead.nWidth,destImg.stHead.nHeight))
        }catch{
          case NonFatal(e)=>
            throw new IllegalAccessException("code:"+gafisImg.stHead.nCompressMethod +" e:"+e.toString)
        }
    }

    destImg.stHead.bIsCompressed = 0
    destImg.stHead.nCompressMethod = 0
    destImg.stHead.nCaptureMethod = glocdef.GA_IMGCAPTYPE_CPRGEN
    destImg.stHead.nImgSize = destImg.bnData.length

    /*
    if(gafisImg.stHead.nFlag & glocdef.GAIMG_FLAG_WHITERIDGE == glocdef.GAIMG_FLAG_WHITERIDGE){
      val bnData = destImg.bnData
      val len = bnData.length
      0 until len foreach{i=>
        bnData(i) = ~bnData(i)
      }
    }
    */


    destImg
  }
  private def Helper_GetBitsPerPixel(nBitsPerPixel:Int):Int={
    if ( nBitsPerPixel < 1 ) 8  else nBitsPerPixel
  }

  private def Helper_GetImageSize(nWidth:Int, nHeight:Int, nBitsPerPixel:Int):Int={
    (nWidth * nBitsPerPixel + 7) / 8 * nHeight
  }

  private def getCodeFromGAFISImage(gafisImg: GAFISIMAGESTRUCT): String ={
    val codeFromImage =  gafisImg.stHead.nCompressMethod.toInt
    codeFromImage match{
      case glocdef.GAIMG_CPRMETHOD_DEFAULT => 	// by xgw supplied method
        throw new UnsupportedOperationException("%s compress not supported".format(codeFromImage))
      case glocdef.GAIMG_CPRMETHOD_XGW => 	// by xgw supplied method.
        throw new UnsupportedOperationException("%s compress not supported".format(codeFromImage))
      case glocdef.GAIMG_CPRMETHOD_XGW_EZW => 	// 许公望的EZW压缩方法：适合低倍率高保真的压缩.
        throw new UnsupportedOperationException("%s compress not supported".format(codeFromImage))
      case glocdef.GAIMG_CPRMETHOD_GA10 => 	// 公安部10倍压缩方法
        fpt4code.GAIMG_CPRMETHOD_GA10_CODE
      case glocdef.GAIMG_CPRMETHOD_GFS => 	// golden
        fpt4code.GAIMG_CPRMETHOD_EGFS_CODE
      case glocdef.GAIMG_CPRMETHOD_PKU => 	// call pku's compress method
        fpt4code.GAIMG_CPRMETHOD_PKU_CODE
      case glocdef.GAIMG_CPRMETHOD_COGENT => 	// cogent compress method
        fpt4code.GAIMG_CPRMETHOD_COGENT_CODE
      case glocdef.GAIMG_CPRMETHOD_WSQ => 	// was method
        fpt4code.GAIMG_CPRMETHOD_WSQ_CODE
      case glocdef.GAIMG_CPRMETHOD_NEC => 	// nec compress method
        fpt4code.GAIMG_CPRMETHOD_NEC_CODE
      case glocdef.GAIMG_CPRMETHOD_TSINGHUA => 	// tsing hua
        fpt4code.GAIMG_CPRMETHOD_TSINGHUA_CODE
      case glocdef.GAIMG_CPRMETHOD_BUPT => 	// beijing university of posts and telecommunications
        fpt4code.GAIMG_CPRMETHOD_BUPT_CODE
      case glocdef.GAIMG_CPRMETHOD_MORPHO =>
        fpt4code.GAIMG_CPRMETHOD_MORPHO_CODE
      case glocdef.GAIMG_CPRMETHOD_HLXT =>
        fpt4code.GAIMG_CPRMETHOD_HLXT_CODE
      case glocdef.GAIMG_CPRMETHOD_RMTZIP => 	// compressmethod provide by communication server(GAFIS)
        throw new UnsupportedOperationException("%s compress not supported".format(codeFromImage))
      case glocdef.GAIMG_CPRMETHOD_LCW => 	// compress method provide by lucas wang.
        throw new UnsupportedOperationException("%s compress not supported".format(codeFromImage))
      case glocdef.GAIMG_CPRMETHOD_JPG => 	// jpeg method.
        throw new UnsupportedOperationException("%s compress not supported".format(codeFromImage))
      case glocdef.GAIMG_CPRMETHOD_MORPHO => 	//!< 广东测试时提供的压缩算法，MORPHO(SAGEM)
        throw new UnsupportedOperationException("%s compress not supported".format(codeFromImage))
      case glocdef.GAIMG_CPRMETHOD_MAXVALUE =>
        throw new UnsupportedOperationException("%s compress not supported".format(codeFromImage))
      case other=>
        throw new UnsupportedOperationException("%s compress not supported".format(codeFromImage))
    }
  }
  /**
   * find dll handle by code
   * @param code "1300"
   * @return
   */
  private def findDllHandle(code:String,cprMethod:Int):Dll = {
    var dll = dlls.get(code)
    if(dll == null) {
      try {
        lock.lock()
        dll = dlls.get(code)
        if (dll == null) {
          loadSingleDll(code, cprMethod)
          dll = dlls.get(code)
        }
      }catch{
        case e:Throwable=>
          throw new IllegalAccessException("cprMethod:"+cprMethod+" "+e.getMessage)
      }finally{
        lock.unlock()
      }
    }
    dll
  }
  private def loadSingleDll(code:String,cprMethod:Int): Unit ={
    if (code.length != 4) {
      throw new IllegalArgumentException("code length must be 4")
    }
    val prefix = code.take(2)
    val dllName = "FPT_DC%s.dll".format(prefix)
    val functionName = {
      if(cprMethod == glocdef.GAIMG_CPRMETHOD_GA10)
        "FPT_Decompress"
        else
        "FPT_DC%s".format(prefix)
    }
    val files = FileUtils.listFiles(new File(serverHome + "/dll"), new AbstractFileFilter {
      override def accept(dir: File, name: String): Boolean = name == dllName
    }, new AbstractFileFilter {
      override def accept(file: File): Boolean = true
    })
    if (files.size == 0) {
      throw new IllegalArgumentException("dll [%s] not found".format(dllName))
    } else if (files.size > 1) {
      throw new IllegalStateException("duplicate dll [%s]".format(dllName))
    }
    val dllPropertyOpt = imageConfigSupport.image.dllConcurrent.find(_.name == dllName)
    val isConcurrent =
    dllPropertyOpt match {
      case Some(dllProperty) =>
        dllProperty.isConcurrent
      case None =>
        true
    }
    System.err.println("prepare loading %s,isConcurrent:%s".format(dllName,isConcurrent))
    val handle = NativeImageConverter.loadLibrary(files.head.getAbsolutePath,functionName,cprMethod,0)
    System.err.println("%s loaded,isConcurrent:%s".format(dllName,isConcurrent))
    dlls.put(code, Dll(handle,if(isConcurrent) None else Some(new ReentrantLock())))
  }
}

/*
  #define UTIL_FPT4LIB_AFISSYSTEM_CODE		"1900"

  #define	GAIMG_CPRMETHOD_NOCPR_CODE			"0000"			// 不压缩
  #define	GAIMG_CPRMETHOD_GA10_CODE			"0010"			// 公安部10压缩
  #define	GAIMG_CPRMETHOD_EGFS_CODE			"1900"			// 东方金指
  #define GAIMG_CPRMETHOD_EGFS_WSQ_CODE  "0131"      //东方金指WSQ3.1压缩
  #define	GAIMG_CPRMETHOD_PKU_CODE			"1300"			// 北大高科
  #define GAIMG_CPRMETHOD_WSQ_CODE        "1400"         //WSQ压缩方法
  #define	GAIMG_CPRMETHOD_COGENT_CODE			"1700"			// 北京海鑫
  #define	GAIMG_CPRMETHOD_NEC_CODE			"1800"			// 小日本NEC
  #define	GAIMG_CPRMETHOD_BUPT_CODE			"1200"			// 北京邮电大学
  #define	GAIMG_CPRMETHOD_TSINGHUA_CODE		"1100"			// 北京刑科所
  #define	GAIMG_CPRMETHOD_MORPHO_CODE			"2201"			// 广东测试提供的数据，SAGEM MORPHO
*/



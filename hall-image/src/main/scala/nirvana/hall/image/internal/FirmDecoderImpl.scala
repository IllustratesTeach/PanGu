package nirvana.hall.image.internal

import java.io.File
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.ReentrantLock

import monad.core.MonadCoreSymbols
import nirvana.hall.image.jni.{OriginalImage, NativeImageConverter}
import nirvana.hall.image.services.FirmDecoder
import org.apache.commons.io.FileUtils
import org.apache.commons.io.filefilter.AbstractFileFilter
import org.apache.tapestry5.ioc.annotations.Symbol

import scala.collection.JavaConversions._

/**
 * firm decoder
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-10
 */
class FirmDecoderImpl(@Symbol(MonadCoreSymbols.SERVER_HOME) serverHome:String) extends FirmDecoder{
  private val dlls = new ConcurrentHashMap[String,Dll]()
  private case class Dll(fileName:String,functionName:String,Handle:Long)
  private val lock = new ReentrantLock()
  private val WSQ = "1400"

  /**
   * decode compressed data using firm's algorithm
   * @param code firm code,such as "1300"
   * @param cpr_data compressed data
   * @param width image width
   * @param height image height
   * @return original image data
   */
  def decode(code:String,cpr_data:Array[Byte],width:Int,height:Int,dpi:Int): OriginalImage={
    code match{
      case WSQ =>
        try{
          lock.lock()
          NativeImageConverter.decodeByWSQ(cpr_data)
        }finally{
          lock.unlock()
        }
      case other=>
        val dll = findDllHandle(code)
        val destImgSize = width * height
        val originalData = NativeImageConverter.decodeByManufactory(dll.Handle,dll.functionName,code,cpr_data,destImgSize);
        val img = new OriginalImage
        img.setWidth(width)
        img.setHeight(height)
        img.setData(originalData)
        img
    }
  }
  /**
   * find dll handle by code
   * @param code "1300"
   * @return
   */
  private def findDllHandle(code:String):Dll = {
    var dll = dlls.get(code)
    if(dll == null) {
      try {
        lock.lock()
        dll = dlls.get(code)
        if(dll == null){
          loadSingleDll(code)
          dll = dlls.get(code)
        }
      }finally{
        lock.unlock()
      }
    }
    dll
  }
  private def loadSingleDll(code:String): Unit ={
    if (code.length != 4) {
      throw new IllegalArgumentException("code length must be 4")
    }
    val prefix = code.take(2)
    val dllName = "FPT_DC%s.dll".format(prefix)
    val functionName = "FPT_DC%s".format(prefix)
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
    val handle = NativeImageConverter.loadLibrary(files.head.getAbsolutePath, 0)
    dlls.put(code, Dll(dllName,functionName,handle))
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



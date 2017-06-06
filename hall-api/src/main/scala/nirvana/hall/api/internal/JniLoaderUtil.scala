package nirvana.hall.api.internal

import java.util.concurrent.atomic.AtomicBoolean

import nirvana.hall.extractor.jni.JniLoader

/**
  * 加载jni工具类
  */
object JniLoaderUtil {

  private lazy val imageInit = new AtomicBoolean(false)
  @volatile
  private var imageDllLoaded = false
  def loadImageJNI():Unit ={
    if(imageInit.compareAndSet(false,true)) {
      nirvana.hall.image.jni.JniLoader.loadJniLibrary(".",null)
      imageDllLoaded = true
    }
    if(!imageDllLoaded)
      loadImageJNI()
  }
  private lazy val extractorInit = new AtomicBoolean(false)
  @volatile
  private var extractorDllLoaded = false
  def loadExtractorJNI():Unit ={
    if(extractorInit.compareAndSet(false,true)) {
      JniLoader.loadJniLibrary(".",null)
      extractorDllLoaded = true
    }
    if(!extractorDllLoaded)
      loadExtractorJNI()

  }
}

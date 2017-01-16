package nirvana.hall.api.internal

import java.io.File

import org.junit.Before

/**
  * 加载jni
  */
trait BaseJniLoader {
  @Before
  def loadJni() {
    val file = new File("support")
    if (file.exists()){
      nirvana.hall.extractor.jni.JniLoader.loadJniLibrary("support", "stderr")
      nirvana.hall.image.jni.JniLoader.loadJniLibrary("support", "stderr")
    }
    else{
      nirvana.hall.extractor.jni.JniLoader.loadJniLibrary("../support", "stderr")
      nirvana.hall.image.jni.JniLoader.loadJniLibrary("../support", "stderr")
    }
  }
}

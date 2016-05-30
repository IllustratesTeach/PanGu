// Copyright 2014,2015 Jun Tsai. All rights reserved.
// site: http://www.ganshane.com
package nirvana.spark.services

import java.io.File

import nirvana.hall.extractor.jni.JniLoader
import org.junit.Before

/**
 * Created by jcai on 14-7-25.
 */
class BaseJniTest {
  @Before
  def loadJni() {
    val file = new File("support")
    if (file.exists())
      JniLoader.loadJniLibrary("support", "stderr")
    else
      JniLoader.loadJniLibrary("../support", "stderr")
  }
}

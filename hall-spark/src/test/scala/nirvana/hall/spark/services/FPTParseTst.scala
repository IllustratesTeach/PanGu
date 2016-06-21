package nirvana.hall.spark.services

import java.io.File

import nirvana.hall.c.services.gfpt4lib.FPTFile
import org.apache.commons.io.FileUtils
import org.junit.Test

/**
  * Created by wangjue on 2016/6/15.
  */
class FPTParseTst {
  @Test
  def fpt (){
    val files = FileUtils.listFiles(new File("C:\\Users\\wangjue\\Desktop\\forinputstring\\10len"),Array[String]("fpt","FPT"),true)
    val itt = files.iterator()
    while (itt.hasNext) {
      val fptFile = itt.next()
      println(fptFile.getName)
      val in = FileUtils.openInputStream(fptFile)
      val fpt= FPTFile.parseFromInputStream(in)
      println("left:"+fpt.isLeft+"|right:"+fpt.isRight)
    }

  }
}

package nirvana.hall.spark.services

import java.io.File

import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.spark.internal.GafisPartitionRecordsSaver
import org.apache.commons.io.FileUtils
import org.junit.Test

/**
  * Created by wangjue on 2016/6/15.
  */
class FPTParseTst {
  @Test
  def fpt (){
    val files = FileUtils.listFiles(new File("C:\\Users\\wangjue\\Desktop\\fail_FPT\\20161201"),Array[String]("fpt","FPT"),true)
    val itt = files.iterator()
    while (itt.hasNext) {
      val fptFile = itt.next()
      println(fptFile.getName)
      val in = FileUtils.openInputStream(fptFile)
      val fpt= FPTFile.parseFromInputStream(in)
      println("left:"+fpt.isLeft+"|right:"+fpt.isRight)
    }
  }

  @Test
  def testScala(): Unit ={
    var list : List[Array[Int]] = List()
    for (i <- 0 to 9) {
      val array = Array(i,i+10)
      list = array :: Nil ::: list
    }

    list.foreach{ a=>
      println(a.toList)
      if (a.toList equals Array(0,10).toList)
        println(a(0) + " " + a(1))
    }
  }

  @Test
  def testScala2(): Unit ={
    var map : Map[Int,Any] = Map()
    for (i <- 0 to 9) {
      val list = i :: i+10 :: Nil
      map += (i -> list)
    }
    println(map)
    map.foreach { a =>
      if (!(a._2 equals Array(0,10).toList))
        println(a._2 + " ")
    }
  }


}

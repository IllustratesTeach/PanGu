package nirvana.hall.spark.services

import java.io.File

import nirvana.hall.c.services.gfpt4lib.{FPTFile}
import nirvana.hall.c.services.kernel.FPTLDataToMNTDISP
import nirvana.hall.c.services.kernel.mnt_checker_def.MNTDISPSTRUCT
import nirvana.hall.extractor.internal.FPTLatentConverter
import nirvana.hall.image.internal.FirmDecoderImpl
import org.apache.commons.io.FileUtils
import org.junit.Test

/**
  * Created by wangjue on 2016/6/15.
  */
class FPTParseTst {
  @Test
  def fpt (){
    val decoder = new FirmDecoderImpl(".",null)
    val files = FileUtils.listFiles(new File("C:\\Users\\wangjue\\Desktop\\fail_FPT\\20161227"),Array[String]("fpt","FPT"),true)
    val itt = files.iterator()
    while (itt.hasNext) {
      val fptFile = itt.next()
      println(fptFile.getName)
      val in = FileUtils.openInputStream(fptFile)
      val fpt= FPTFile.parseFromInputStream(in)
      fpt match {
        case Left(fpt3) =>
          //assert(fpt3.fileLength.toInt == fpt3.getDataSize,"fpt3 fileLength != dataSize")
          val tpCounts = fpt3.tpCount
          var tpCount = 0
          if (tpCounts!=null && !"".equals(tpCounts))
            tpCount = tpCounts.toInt
          val lpCounts = fpt3.lpCount
          var lpCount = 0
          if (lpCounts!=null && !"".equals(lpCounts))
            lpCount = lpCounts.toInt
          if (tpCount > 0) {
            assert(tpCount == fpt3.logic3Recs.length)
            fpt3.logic3Recs.foreach { tp =>
              val fingerCount = tp.sendFingerCount.toInt
              assert(fingerCount == tp.fingers.length)
              tp.fingers.foreach { tData =>
                println(tData.imgData.length)
              }
            }
          }
          if (lpCount > 0) { //process latent FPT
            fpt3.logic2Recs.foreach { lp =>
              val caseId = lp.caseId
              assert(caseId != null && !"".equals(caseId), "case id is null")
              val latentCase = FptPropertiesConverter.fpt3ToLatentCaseConvert(lp)

              lp.fingers.foreach{ lData=>
                val disp = FPTLDataToMNTDISP.convertFPT03ToMNTDISP(lData)

              }
            }
          }
        case Right(fpt4) =>
          val tpCounts = fpt4.tpCount
          var tpCount = 0
          if (tpCounts!=null && !"".equals(tpCounts))
            tpCount = tpCounts.toInt
          val lpCounts = fpt4.lpCount
          var lpCount = 0
          if (lpCounts!=null && !"".equals(lpCounts))
            lpCount = lpCounts.toInt
          if (tpCount > 0) {
            assert(tpCount == fpt4.logic02Recs.length)
            fpt4.logic02Recs.foreach { tp =>
              val fingerCount = tp.sendFingerCount.toInt
              assert(fingerCount == tp.fingers.length)
              tp.fingers.foreach { tData =>
                println(tData.imgData.length)
              }
            }
          }
          if (lpCount > 0) { //process latent FPT
            fpt4.logic03Recs.foreach { lp =>
              val caseId = lp.caseId
              assert(caseId != null && !"".equals(caseId), "case id is null")
              lp.fingers.foreach{ lData=>
                val disp = FPTLDataToMNTDISP.convertFPT03ToMNTDISP(lData)
                val feature = createImageLatentEvent(disp)
                //val result = decoder.decode(lData.imgData)
              }
            }
          }
      }
    }
  }

  def createImageLatentEvent(disp: MNTDISPSTRUCT): Array[Byte] = {
    SparkFunctions.loadExtractorJNI()
    val latentFeature = FPTLatentConverter.convert(disp)
    latentFeature.toByteArray()
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

  @Test
  def testHeh() : Unit = {
    val file = FileUtils.readLines(new File("C:\\Users\\wangjue\\Desktop\\fail_FPT\\tj\\heh.txt"))
    var sum = 0.0
    for (i <- 0 to file.size()-1) {
      val d = file.get(i).toDouble
      println(d)
      sum += d
    }
    println("sum : " + sum)
  }

}

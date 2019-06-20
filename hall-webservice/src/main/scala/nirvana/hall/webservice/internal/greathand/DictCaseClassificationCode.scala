package nirvana.hall.webservice.internal.greathand

import org.apache.commons.io.IOUtils

import scala.collection.{JavaConversions, mutable}


object DictCaseClassificationCode {

  var caseClassificationCodeMap:mutable.HashMap[String,String] = _

  def loadAdministrativeCode: mutable.HashMap[String,String] ={

    if(Option(caseClassificationCodeMap).isEmpty){
      caseClassificationCodeMap = new mutable.HashMap[String,String]()
      //administrativeCodeSeq = Some(IOUtils.readLines(getClass.getResourceAsStream("/nirvana/hall/greathand/caseclass.data")))
      val list = IOUtils.readLines(getClass.getResourceAsStream("/nirvana/hall/greathand/caseclass.data"))
      val scalaBuffer = JavaConversions.asScalaBuffer(list)
      scalaBuffer.foreach{
        line =>
          val linelist = line.split("\t")
          caseClassificationCodeMap.put(linelist(0),linelist(1))
      }
      println(caseClassificationCodeMap.size)
    }
    caseClassificationCodeMap
  }
}

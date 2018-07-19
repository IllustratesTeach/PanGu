package nirvana.hall.webservice.services.survey

import org.apache.commons.io.IOUtils

/**
  * Created by yuchen on 2018/7/19.
  */
object DictAdministrativeCode {

  var administrativeCodeSeq:Option[java.util.List[String]] = _

  def loadAdministrativeCode: Option[java.util.List[String]] ={

    if(Option(administrativeCodeSeq).isEmpty){
      administrativeCodeSeq = Some(IOUtils.readLines(getClass.getResourceAsStream("/nirvana/hall/webservice/xzqh_code.data")))
    }
    administrativeCodeSeq
  }
}

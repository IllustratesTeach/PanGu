package nirvana.hall.spark.services

import nirvana.hall.spark.services.FptPropertiesConverter.PersonConvert

/**
  * Created by yuchen on 2018/4/19.
  */
object WSQPropertiesConverter {

  def wsqInfoToPersonConvert(wsqFilePath:String): PersonConvert ={
    val personId = wsqFilePath.split("_")(0)
    val personInfo = new PersonConvert
    personInfo.personId = personId
    personInfo.cardId = personId
    personInfo.fptPath = wsqFilePath
    personInfo
  }
}

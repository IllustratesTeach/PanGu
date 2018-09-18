package nirvana.hall.v70.internal.adapter.gz.services.versionfpt5

import nirvana.hall.api.services.SurveyFnoKnoConnService
import nirvana.hall.v70.internal.adapter.gz.jpa.SurveyFnoKnoConn

/**
  * Created by mengxin on 2018/7/3.
  */
class SurveyFnoKnoConnServiceImpl() extends SurveyFnoKnoConnService{
  /**
    * 新增关联信息
    *
    * @return
    */
  override def addFnoKnoConn(fno: String , kno :String): Unit ={
    val fnoKnoConn = new SurveyFnoKnoConn()
    fnoKnoConn.physicalEvidenceNo = fno
    fnoKnoConn.sceneSurveyId = kno
    fnoKnoConn.save()
  }
}

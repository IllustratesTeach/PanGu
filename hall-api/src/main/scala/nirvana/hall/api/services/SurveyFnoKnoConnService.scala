package nirvana.hall.api.services

import org.springframework.transaction.annotation.Transactional

/**
  * 现勘号、现场物证编号关联service
  */
trait SurveyFnoKnoConnService {

  /**
    * 新增关联信息
    * @return
    */
  @Transactional
  def addFnoKnoConn(fno: String , kno :String): Unit
}
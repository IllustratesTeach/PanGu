package nirvana.hall.api.services

import nirvana.hall.c.services.gfpt4lib.FPT4File.{Logic02Rec, Logic03Rec}

/**
  * Created by songpeng on 2017/1/23.
  * fpt处理service
  */
trait FPTService {

  def getLogic02Rec(cardId: String, dbId: Option[String] = None): Logic02Rec

  def getLogic03Rec(cardId: String, dbId: Option[String] = None): Logic03Rec
}

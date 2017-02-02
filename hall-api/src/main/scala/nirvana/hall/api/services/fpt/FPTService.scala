package nirvana.hall.api.services.fpt

import nirvana.hall.c.services.gfpt4lib.FPT4File.{Logic02Rec, Logic03Rec}

/**
  * Created by songpeng on 2017/1/23.
  * fpt处理service
  */
trait FPTService {

  def getLogic02Rec(cardId: String, dbId: Option[String] = None): Logic02Rec

  def getLogic03Rec(cardId: String, dbId: Option[String] = None): Logic03Rec

  def addLogic02Res(logic02Rec: Logic02Rec)

  def addLogic03Res(logic03Rec: Logic03Rec)
}

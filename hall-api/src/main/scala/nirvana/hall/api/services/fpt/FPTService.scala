package nirvana.hall.api.services.fpt

import nirvana.hall.c.services.gfpt4lib.FPT4File._

/**
  * Created by songpeng on 2017/1/23.
  * fpt处理service
  */
trait FPTService {

  def getLogic02Rec(cardId: String, dbId: Option[String] = None): Logic02Rec

  def getLogic03Rec(cardId: String, dbId: Option[String] = None): Logic03Rec

  def getLogic04Rec(pkid: String,num:Int, dbId: Option[String] = None): Logic04Rec

  def getLogic05Rec(pkid: String,num:Int, dbId: Option[String] = None): Logic05Rec

  def getLogic06Rec(pkid: String,num:Int, dbId: Option[String] = None): Logic06Rec

  def addLogic02Res(logic02Rec: Logic02Rec)

  def addLogic03Res(logic03Rec: Logic03Rec)

}

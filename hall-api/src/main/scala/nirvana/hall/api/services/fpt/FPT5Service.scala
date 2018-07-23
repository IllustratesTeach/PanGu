package nirvana.hall.api.services.fpt

import nirvana.hall.c.services.gfpt5lib.{cancelLatentPackage, LtHitResultPackage, TlResultPackage, TtResultPackage, _}

/**
  * Created by songpeng on 2017/11/3.
  */
trait FPT5Service {

  /**
    * 获取捺印信息
    * @param cardId 捺印卡号
    * @return
    */
  def getFingerprintPackage(cardId: String): FingerprintPackage

  /**
    * 获取现场信息
    * @param caseId 案件编号
    * @return
    */
  def getLatentPackage(caseId: String): LatentPackage

  /**
    * 捺印指纹导入
    * @param fingerprintPackage
    * @param dbId
    */
  def addFingerprintPackage(fingerprintPackage:FingerprintPackage,dbId: Option[String] = None):Unit

  /**
    * 一体化采集捺印指纹导入(不提取特征)
    * @param fingerprintPackage
    * @param dbId
    */
  def addQualityFingerprintPackage(fingerprintPackage:FingerprintPackage,dbId: Option[String] = None):Unit

  /**
    * 现场指纹导入
    * @param latentPackage
    */
  def addLatentPackage(latentPackage:LatentPackage,dbId: Option[String] = None):Unit


  def getLatentTaskPackage(taskId:String):LatenttaskPackage

  def addLatentTaskPackage(latenttaskPackage:LatenttaskPackage):Unit

  //--task---//
  def getPrintTaskPackage(taskId:String):PrinttaskPackage

  def addPrintTaskPackage(printtaskPackage: PrinttaskPackage):Unit

  //--candlist--//
  def getLTResultPackage(taskId:String):LtResultPackage

  def addLTResultPackage(ltResultPackage:LtResultPackage):Unit

  def getTlResultPackage(taskId:String):TlResultPackage

  def addTlResultPackage(tlResultPackage:TlResultPackage):Unit

  def getTTResultPackage(taskId:String):TtResultPackage

  def addTTResultPackage(ttResultPackage:TtResultPackage):Unit

  def getLLResultPackage(taskId:String):LlResultPackage

  def addLLResultPackage(llResultPackage:LlResultPackage):Unit

  //--hitResult--//
  def getLTHitResultPackage(oraSid:String):LtHitResultPackage

  def addLTHitResultPackage(ltHitResultPackage:LtHitResultPackage):Unit

  def getTtHitResultPackage(oraSid:String):TtHitResultPackage

  def addTtHitResultPackage(ttHitResultPackage:TtHitResultPackage):Unit

  def getLlHitResultPackage(oraSid:String):LlHitResultPackage

  def addLlHitResultPackage(llHitResultPackage:LlHitResultPackage):Unit

  //--cancel--//
  def getCancelLatentPackage(originSystemCaseId:String):cancelLatentPackage

  def addCancelLatentPackage(cancelLatentPackage:cancelLatentPackage):Unit


}

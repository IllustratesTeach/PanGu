package nirvana.hall.api.services.fpt

import nirvana.hall.c.services.gfpt5lib.{FingerprintPackage, LatentPackage}

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
    * @param cardId 现场卡号
    * @return
    */
  def getLatentPackage(cardId: String): LatentPackage

}

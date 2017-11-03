package nirvana.hall.api.services.fpt

import nirvana.hall.c.services.gfpt5lib.{FingerprintPackage, LatentPackage}

/**
  * Created by songpeng on 2017/11/3.
  */
trait FPT5Service {

  /**
    * 获取捺印信息
    * @param personId 人员编号
    * @return
    */
  def getFingerprintPackage(personId: String): FingerprintPackage

  /**
    * 获取现场信息
    * @param caseId 案件编号
    * @return
    */
  def getLatentPackage(caseId: String): LatentPackage

}

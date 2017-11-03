package nirvana.hall.api.internal.fpt

import nirvana.hall.api.services.{CaseInfoService, LPCardService, TPCardService}
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.c.services.gfpt5lib.{FingerprintPackage, LatentPackage}

/**
  * Created by songpeng on 2017/11/3.
  */
class FPT5ServiceImpl(tPCardService: TPCardService, caseInfoService: CaseInfoService, lPCardService: LPCardService) extends FPT5Service{
  /**
    * 获取捺印信息
    *
    * @param personId 人员编号
    * @return
    */
  override def getFingerprintPackage(personId: String): FingerprintPackage = ???

  /**
    * 获取现场信息
    *
    * @param caseId 案件编号
    * @return
    */
  override def getLatentPackage(caseId: String): LatentPackage = ???
}

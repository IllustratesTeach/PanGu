package nirvana.hall.api.internal.fpt

import nirvana.hall.api.services.{CaseInfoService, LPCardService, TPCardService}
import nirvana.hall.api.services.fpt.FPT5Service
import nirvana.hall.c.services.gfpt5lib.{FingerprintPackage, LatentPackage}
import scala.collection.JavaConversions._

/**
  * Created by songpeng on 2017/11/3.
  */
class FPT5ServiceImpl(tPCardService: TPCardService, caseInfoService: CaseInfoService, lPCardService: LPCardService) extends FPT5Service{
  /**
    * 获取捺印信息
    * @param personId 人员编号
    * @return
    */
  override def getFingerprintPackage(personId: String): FingerprintPackage = {
    val tpCard = tPCardService.getTPCard(personId)
    FPT5Converter.convertTPCard2FingerprintPackage(tpCard)
  }

  /**
    * 获取现场信息
    * @param caseId 案件编号
    * @return
    */
  override def getLatentPackage(caseId: String): LatentPackage = {
    val caseInfo = caseInfoService.getCaseInfo(caseId)
    val lpCardList = caseInfo.getStrFingerIDList.map{fingerId=>
      lPCardService.getLPCard(fingerId)
    }
    FPT5Converter.convertCaseInfoAndLPCard2LatentPackage(caseInfo, lpCardList)
  }
}

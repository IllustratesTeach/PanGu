package nirvana.hall.api.internal.fpt

import nirvana.hall.c.services.gfpt5lib.{FingerprintPackage, LatentPackage}
import nirvana.hall.protocol.api.FPTProto.{Case, LPCard, TPCard}

/**
  * FPT5数据转换类
  * Created by songpeng on 2017/11/3.
  */
object FPT5Converter {

  /**
    * 将捺印proto信息转为FingerprintPackage
    * @param tpCard 捺印信息
    * @return
    */
  def convertTPCard2FingerprintPackage(tpCard: TPCard): FingerprintPackage ={
    val fingerprintPackage = new FingerprintPackage
    fingerprintPackage.descriptMsg.fingerPalmCardId = tpCard.getStrPersonID
    //TODO 其他属性赋值
    fingerprintPackage
  }

  /**
    * 将现场proto信息转为LatentPackage
    * @param caseInfo 案件信息
    * @param lpCards 现场指纹信息
    * @return
    */
  def convertCaseInfoAndLPCard2LatentPackage(caseInfo: Case, lpCards: Seq[LPCard]): LatentPackage = {
    val latentPackage = new LatentPackage
    latentPackage.caseMsg.caseId =caseInfo.getStrCaseID
    //TODO 其他属性赋值

    latentPackage
  }
}

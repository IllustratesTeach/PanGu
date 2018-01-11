package nirvana.hall.v62.internal

import nirvana.hall.api.internal.fpt.vo.{FingerPrintTaskInfo, LatentTaskInfo}
import nirvana.hall.api.services.fpt.exchange.FPTExchangeService
import nirvana.hall.c.services.gfpt5lib.{LlResultPackage, LtResultPackage, _}
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult

/**
  * Created by T430 on 1/11/2018.
  */
class FPTExchangeServiceImpl extends  FPTExchangeService{

  override def getLatentTaskInfo(taskId: String): LatentTaskInfo = ???

  override def getLTResultPackage(taskId: String): (LtResultPackage, Option[MatchResult]) = ???

  override def addFingerPrintTaskInfo(fingerPrintTaskInfo: FingerPrintTaskInfo): Unit = ???

  override def addLlHitResultPackage(llHitResultPackage: LlHitResultPackage): Unit = ???

  override def addLTResultPackage(ltResultPackage: LtResultPackage): Unit = ???

  override def getTtHitResultPackage(oraSid: String): TtHitResultPackage = ???

  override def getTlResultPackage(taskId: String): (TlResultPackage, Option[MatchResult]) = ???

  override def getLTHitResultPackage(oraSid: String): LtHitResultPackage = ???

  override def getTTResultPackage(taskId: String): (TtResultPackage, Option[MatchResult]) = ???

  override def addLatentTaskInfo(latentTaskInfo: LatentTaskInfo): Unit = ???

  override def getLlHitResultPackage(oraSid: String): LlHitResultPackage = ???

  override def getLLResultPackage(taskId: String): (LlResultPackage, Option[MatchResult]) = ???

  override def addTTResultPackage(ttResultPackage: TtResultPackage): Unit = ???

  override def addTlResultPackage(tlResultPackage: TlResultPackage): Unit = ???

  override def getFingerPrintTaskInfo(taskId: String): FingerPrintTaskInfo = ???

  override def addTtHitResultPackage(ttHitResultPackage: TtHitResultPackage): Unit = ???

  override def addLTHitResultPackage(ltHitResultPackage: LtHitResultPackage): Unit = ???

  override def addLLResultPackage(llResultPackage: LlResultPackage): Unit = ???
}

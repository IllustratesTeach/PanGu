package nirvana.hall.api.services.fpt.exchange

import nirvana.hall.api.internal.fpt.vo.{FingerPrintTaskInfo, LatentTaskInfo}
import nirvana.hall.c.services.gfpt5lib._
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult

/**
  * Created by yuchen on 2017/12/9.
  */
trait FPTExchangeService {

    def getLatentTaskInfo(taskId:String):LatentTaskInfo

    def addLatentTaskInfo(latentTaskInfo:LatentTaskInfo):Unit

    def getFingerPrintTaskInfo(taskId:String):FingerPrintTaskInfo

    def addFingerPrintTaskInfo(fingerPrintTaskInfo:FingerPrintTaskInfo):Unit

    def getLTHitResultPackage(oraSid:String):LtHitResultPackage

    def addLTHitResultPackage(ltHitResultPackage:LtHitResultPackage):Unit

    def getTtHitResultPackage(oraSid:String):TtHitResultPackage

    def addTtHitResultPackage(ttHitResultPackage:TtHitResultPackage):Unit

    def getLlHitResultPackage(oraSid: String):LlHitResultPackage

    def addLlHitResultPackage(llHitResultPackage: LlHitResultPackage):Unit

    def getLTResultPackage(taskId: String):(LtResultPackage,Option[MatchResult])

    def addLTResultPackage(ltResultPackage: LtResultPackage):Unit

    def getTlResultPackage(taskId: String):(TlResultPackage,Option[MatchResult])

    def addTlResultPackage(tlResultPackage:TlResultPackage):Unit

    def getTTResultPackage(taskId:String):(TtResultPackage,Option[MatchResult])

    def addTTResultPackage(ttResultPackage:TtResultPackage):Unit

    def getLLResultPackage(taskId:String):(LlResultPackage,Option[MatchResult])

    def addLLResultPackage(llResultPackage:LlResultPackage):Unit


}

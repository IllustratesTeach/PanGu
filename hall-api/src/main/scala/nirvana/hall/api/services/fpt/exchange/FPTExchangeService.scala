package nirvana.hall.api.services.fpt.exchange

import nirvana.hall.api.internal.fpt.vo.{FingerPrintTaskInfo, LatentTaskInfo}
import nirvana.hall.c.services.gfpt5lib.{LlHitResultPackage, LtHitResultPackage, TtHitResultPackage}

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


}

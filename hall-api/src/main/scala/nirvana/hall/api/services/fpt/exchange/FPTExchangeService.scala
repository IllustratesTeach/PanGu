package nirvana.hall.api.services.fpt.exchange

import nirvana.hall.api.internal.fpt.vo.{FingerPrintTaskInfo, LatentTaskInfo}

/**
  * Created by yuchen on 2017/12/9.
  */
trait FPTExchangeService {

    def getLatentTaskInfo(taskId:String):LatentTaskInfo

    def addLatentTaskInfo(latentTaskInfo:LatentTaskInfo):Unit

    def getFingerPrintTaskInfo(taskId:String):FingerPrintTaskInfo

    def addFingerPrintTaskInfo(fingerPrintTaskInfo:FingerPrintTaskInfo):Unit


}

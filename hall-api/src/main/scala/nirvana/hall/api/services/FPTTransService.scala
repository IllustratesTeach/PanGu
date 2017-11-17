package nirvana.hall.api.services

import nirvana.hall.protocol.api.FPTTrans.ExportType

/**
  * Created by yuchen on 2017/11/9.
  */
trait FPTTransService {
    def fPTImport(data:Array[Byte]):Unit
    def fPTExport(cardId:String,exportType:ExportType):String
}

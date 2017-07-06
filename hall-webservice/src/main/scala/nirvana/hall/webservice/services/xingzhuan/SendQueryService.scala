package nirvana.hall.webservice.services.xingzhuan

import nirvana.hall.c.services.gfpt4lib.FPT4File.FPT4File

/**
  * Created by win-20161010 on 2017/5/31.
  */
trait SendQueryService {
    def sendQuery(fPTFile: FPT4File,id:String,custom1:String):Unit
}

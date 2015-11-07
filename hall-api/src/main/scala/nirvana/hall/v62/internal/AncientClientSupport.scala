package nirvana.hall.v62.internal

import nirvana.hall.v62.internal.c.gbaselib.gafiserr.GAFISERRDATSTRUCT
import nirvana.hall.v62.internal.c.gloclib.glocndef.GNETANSWERHEADOBJECT
import nirvana.hall.v62.services.{AncientData, ChannelOperator, AncientClient}

/**
 * provide AncientClient instance
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-02
 */
trait AncientClientSupport {
  /**
   * obtain AncientClient instance
   * @return AncientClient instance
   */
  def createAncientClient(host:String,port:Int):AncientClient


  /**
   * validate the response from server
   * @param response response object
   * @param channel server channel
   */
  protected def validateResponse(response: GNETANSWERHEADOBJECT,channel:ChannelOperator): Unit ={
    if(response.nReturnValue == -1) {
      val gafisError = channel.receive[GAFISERRDATSTRUCT]()
      println(gafisError.bnAFISErrData)
      throw new IllegalAccessException("fail to send data,num:%s,file:%s,line:%s".format(gafisError.nAFISErrno,gafisError.szFileName,gafisError.nLineNum));
    }
  }
}
class NoneResponse extends AncientData{

}

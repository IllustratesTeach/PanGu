package nirvana.hall.v62.internal

import nirvana.hall.v62.services.{ChannelOperator, AncientClient}

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
  protected def validateResponse(response: ResponseHeader,channel:ChannelOperator): Unit ={
    if(response.nReturnValue == -1) {
      val gafisError = channel.receive[GafisError]()
      println(gafisError.bnAFISErrData)
      throw new IllegalAccessException("fail to send data,num:%s,file:%s,line:%s".format(gafisError.nAFISErrno,gafisError.szFileName,gafisError.nLineNum));
    }
  }
}

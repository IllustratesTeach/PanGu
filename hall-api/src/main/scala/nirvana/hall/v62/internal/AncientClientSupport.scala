package nirvana.hall.v62.internal

import nirvana.hall.v62.internal.c.gbaselib.gafiserr.GAFISERRDATSTRUCT
import nirvana.hall.v62.internal.c.gloclib.glocdef.GAFISMICSTRUCT
import nirvana.hall.v62.internal.c.gloclib.glocndef.{GNETREQUESTHEADOBJECT, GNETANSWERHEADOBJECT}
import nirvana.hall.v62.services.{V62ServerAddress, AncientData, ChannelOperator}

/**
 * provide AncientClient instance
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-02
 */
trait AncientClientSupport {
  /**
   * execute in channel
   */
  def executeInChannel[T](channelOperator: ChannelOperator=>T):T={
    new XSocketAncientClient(serverAddress.host,serverAddress.port).executeInChannel(channelOperator)
  }
  def serverAddress:V62ServerAddress
  def createRequestHeader:GNETREQUESTHEADOBJECT={
    val header = new GNETREQUESTHEADOBJECT
    header.szUserName=serverAddress.user
    serverAddress.password.foreach(header.szUserPass = _)
    header.cbSize = 192
    header.nMajorVer = 6
    header.nMinorVer = 1

    header
  }


  /**
   * validate the response from server
   * @param response response object
   * @param channel server channel
   */
  protected def validateResponse(channel:ChannelOperator,response: GNETANSWERHEADOBJECT): Unit ={
    //if(response.nReturnValue == -1) {
    if(response.nReturnValue <0) {
      val gafisError = channel.receive[GAFISERRDATSTRUCT]()
      println(gafisError.bnAFISErrData)
      throw new IllegalAccessException("fail to send data,num:%s,file:%s,line:%s".format(gafisError.nAFISErrno,gafisError.szFileName,gafisError.nLineNum));
    }
  }
  protected def GAFIS_NETSCR_SendMICStruct(channel:ChannelOperator,mic:GAFISMICSTRUCT): Unit ={
    if(mic.nMntLen > 0) channel.writeByteArray[NoneResponse](mic.pstMnt_Data)
    if(mic.nImgLen > 0) channel.writeByteArray[NoneResponse](mic.pstImg_Data)
    if(mic.nCprLen > 0) channel.writeByteArray[NoneResponse](mic.pstCpr_Data)
    if(mic.nBinLen > 0) channel.writeByteArray[NoneResponse](mic.pstBin_Data)
  }
  protected def GAFIS_NETSCR_RecvMICStruct(channel:ChannelOperator,mic:GAFISMICSTRUCT):Unit={
    // we assume we know the exact size and the memory has been allocated
    if ( mic.nMntLen >0 ) mic.pstMnt_Data = channel.receiveByteArray(mic.nMntLen).array()
    if ( mic.nImgLen >0 ) mic.pstImg_Data = channel.receiveByteArray(mic.nImgLen).array()
    if ( mic.nCprLen >0 ) mic.pstCpr_Data = channel.receiveByteArray(mic.nCprLen).array()
    if ( mic.nBinLen >0 ) mic.pstBin_Data = channel.receiveByteArray(mic.nBinLen).array()
  }
}
class NoneResponse extends AncientData{
}

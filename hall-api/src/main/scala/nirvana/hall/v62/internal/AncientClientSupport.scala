package nirvana.hall.v62.internal

import nirvana.hall.v62.internal.c.gbaselib.gafiserr.GAFISERRDATSTRUCT
import nirvana.hall.v62.internal.c.gloclib.glocdef.GAFISMICSTRUCT
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
  protected def GAFIS_NETSCR_SendMICStruct(mic:GAFISMICSTRUCT,channel:ChannelOperator): Unit ={
    if(mic.nMntLen > 0)
      channel.writeByteArray[NoneResponse](mic.pstMnt_Data)
    if(mic.nImgLen > 0)
      channel.writeByteArray[NoneResponse](mic.pstImg_Data)
    if(mic.nCprLen > 0)
      channel.writeByteArray[NoneResponse](mic.pstCpr_Data)
    if(mic.nBinLen > 0)
      channel.writeByteArray[NoneResponse](mic.pstBin_Data)
  }
}
class NoneResponse extends AncientData{

}

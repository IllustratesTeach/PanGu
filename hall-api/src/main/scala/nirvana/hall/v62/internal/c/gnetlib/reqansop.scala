package nirvana.hall.v62.internal.c.gnetlib

import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.gbaselib.gafiserr.GAFISERRDATSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.{GNETANSWERHEADOBJECT, GNETREQUESTHEADOBJECT}
import nirvana.hall.v62.internal.NoneResponse
import nirvana.hall.v62.services.{GafisException, ChannelOperator}
import org.jboss.netty.buffer.ChannelBuffer

/**
  * request and answer operation
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-28
  */
trait reqansop {
  def NETREQ_GetOpClass(pstReq:GNETREQUESTHEADOBJECT)= {
    pstReq.nOpClass
  }

  def NETREQ_SetOpClass(pstReq:GNETREQUESTHEADOBJECT,nOpclass:Int) {
    pstReq.nOpClass= nOpclass.toShort
  }

  def NETREQ_GetOpCode(pstReq:GNETREQUESTHEADOBJECT)= {
    pstReq.nOpCode
  }

  def NETREQ_SetOpCode(pstReq:GNETREQUESTHEADOBJECT,nOpcode:Int) {
    pstReq.nOpCode = nOpcode.toShort
  }

  def NETREQ_GetOption(pstReq:GNETREQUESTHEADOBJECT)={
    pstReq.nOption
  }

  def NETREQ_SetOption(pstReq:GNETREQUESTHEADOBJECT,nOption:Int) {
    pstReq.nOption = nOption
  }

  def NETANS_SetRetVal(pstAns:GNETANSWERHEADOBJECT, nRetval:Int) {
    pstAns.nReturnValue = nRetval
  }

  def NETANS_GetRetVal(pstAns:GNETANSWERHEADOBJECT) = {
    pstAns.nReturnValue
  }

  def NETANS_SetLongRetVal(pstAns:GNETANSWERHEADOBJECT, nRetval:Long)
  {
    pstAns.nReturnValue = nRetval.toInt
  }

  def NETANS_GetLongRetVal(pstAns:GNETANSWERHEADOBJECT)= {
    pstAns.nReturnValue
  }

  def NETREQ_SetRetVal(pstReq:GNETREQUESTHEADOBJECT,nRetVal:Int) {
    pstReq.nRetVal = nRetVal
  }

  def NETREQ_GetRetVal(pstReq:GNETREQUESTHEADOBJECT)={
    pstReq.nRetVal
  }

  def NETREQ_SetLongRetVal(pstReq:GNETREQUESTHEADOBJECT,nRetVal:Long)
  {
    pstReq.nRetVal = nRetVal
  }

  def NETREQ_GetLongRetVal(pstReq:GNETREQUESTHEADOBJECT)= {
    pstReq.nRetVal
  }

  def NETANS_Init(pstAns:GNETANSWERHEADOBJECT)
  {
    //INIT 的时候nMinorVer是1 or 0 ?
    /*
    memset(pstAns, 0, sizeof(*pstAns));
    uint4ToChar4(sizeof(*pstAns), pstAns->cbSize);
    uint2ToChar2(6, pstAns->nMajorVer);
    uint2ToChar2(1, pstAns->nMinorVer);
    strcpy(pstAns->szMagicStr, GNRHO_MAGICSTR);
    */
  }

  def NETANS_Send(pstCon:ChannelOperator, pstAns:GNETANSWERHEADOBJECT) {
    pstCon.writeMessage[NoneResponse](pstAns)
  }

  def NETANS_Recv(pstCon:ChannelOperator, pstAns:GNETANSWERHEADOBJECT) {
    pstCon.receive(pstAns)
  }


  def NETREQ_Send(pstCon:ChannelOperator, pstReq:GNETREQUESTHEADOBJECT) {
    pstCon.writeMessage(pstReq)
  }

  def NETREQ_Recv(pstCon:ChannelOperator, pstReq:GNETREQUESTHEADOBJECT): Unit =
  {
    pstCon.receive(pstReq)
  }

  def NETREQ_GetDBID(pstReq:GNETREQUESTHEADOBJECT):Short={
    pstReq.nDBID
  }

  def NETREQ_SetDBID(pstReq:GNETREQUESTHEADOBJECT,nDBID:Short)
  {
    pstReq.nDBID = nDBID
  }

  def NETREQ_SetTableID(pstReq:GNETREQUESTHEADOBJECT,nTableID:Short)
  {
    pstReq.nTableID = nTableID
  }

  def NETREQ_GetTableID(pstReq:GNETREQUESTHEADOBJECT):Short=
  {
    pstReq.nTableID
  }


  protected def  NETOP_SENDREQ(channel:ChannelOperator,data:GNETREQUESTHEADOBJECT): Unit ={
    channel.writeMessage[NoneResponse](data)
  }
  protected def  NETOP_SENDDATA[R <: AncientData](channel:ChannelOperator,data:R): Unit ={
    channel.writeMessage[NoneResponse](data)
  }
  protected def  NETOP_SENDDATA(channel:ChannelOperator,data:Array[Byte]): Unit ={
    channel.writeByteArray[NoneResponse](data)
  }
  protected def NETOP_RECVANS(channel:ChannelOperator, pAns:GNETANSWERHEADOBJECT): Unit ={
    channel.receive(pAns)
  }
  protected def NETOP_RECVDATA[R <: AncientData](channel:ChannelOperator,target:R): R={
    channel.receive[R](target)
  }
  protected def NETOP_RECVDATA(channel:ChannelOperator,length:Int): ChannelBuffer ={
    channel.receiveByteArray(length)
  }
  protected def NETOP_RETVAL_LT_FIN(channel:ChannelOperator,pAns:GNETANSWERHEADOBJECT):Unit={
    validateResponse(channel,pAns)
  }
  /**
    * validate the response from server
    *
    * @param response response object
    * @param channel server channel
    */
  protected def validateResponse(channel:ChannelOperator,response: GNETANSWERHEADOBJECT): Unit ={
    //if(response.nReturnValue == -1) {
    if(response.nReturnValue <0) {
      val gafisError = channel.receive[GAFISERRDATSTRUCT]()
      throw new GafisException(gafisError)
    }
  }
}

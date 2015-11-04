package nirvana.hall.v62.internal

import monad.support.services.LoggerSupport
import nirvana.hall.protocol.v62.FPTProto.{Case, LPCard, TPCard}
import nirvana.hall.v62.AncientConstants
import nirvana.hall.v62.services.{ChannelOperator, DatabaseTable, V62ServerAddress}

/**
 * provide data sync function
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-03
 */
trait DataSyncSupport {
  this:LoggerSupport with AncientClientSupport =>

  type HeaderDataModifier = RequestHeader => Unit
  type DataWriter = ChannelOperator => Unit
  private case class V62OperateOptions(opClass:Int,opCode:Int,func:DataWriter)
  /**
   * send case data to v6.2 system
   * @param databaseTable database define
   * @param protoCase case data based on protobuf
   */
  def sendCaseData(serverAddress:V62ServerAddress,databaseTable: DatabaseTable,protoCase:Case): Unit ={
    sendData(serverAddress,databaseTable,
      V62OperateOptions(
        AncientConstants.OP_CLASS_CASE,
        AncientConstants.OP_CASE_ADD,
        syncCase(protoCase)))
  }
  def updateCaseData(serverAddress:V62ServerAddress,databaseTable: DatabaseTable,protoCase:Case): Unit ={
    sendData(serverAddress,databaseTable,
      V62OperateOptions(
        AncientConstants.OP_CLASS_CASE,
        AncientConstants.OP_CASE_UPDATE,
        syncCase(protoCase)))
  }
  def deleteCaseData(serverAddress:V62ServerAddress,databaseTable: DatabaseTable,caseId:String): Unit ={
    sendData(serverAddress,databaseTable,
      V62OperateOptions(
        AncientConstants.OP_CLASS_CASE,
        AncientConstants.OP_CASE_DEL,
      NoneOperator),DeleteDataHeader(caseId))
  }

  def updateTemplateData(address:V62ServerAddress,databaseTable: DatabaseTable,card: TPCard): Unit ={
    sendData(address,databaseTable,
      V62OperateOptions(
        AncientConstants.OP_CLASS_TPLIB,
        AncientConstants.OP_TPLIB_UPDATE,
        syncTemplateData(card)))
  }
  def sendTemplateData(address:V62ServerAddress,databaseTable: DatabaseTable,card: TPCard): Unit ={
    sendData(address,databaseTable,
      V62OperateOptions(
        AncientConstants.OP_CLASS_TPLIB,
        AncientConstants.OP_TPLIB_ADD,
        syncTemplateData(card)))
  }
  def deleteTemplateData(address:V62ServerAddress,databaseTable: DatabaseTable,key:String): Unit ={
    sendData(address,databaseTable,
      V62OperateOptions(
        AncientConstants.OP_CLASS_TPLIB,
        AncientConstants.OP_TPLIB_DEL,
        NoneOperator),
      DeleteDataHeader(key))
  }
  def updateLatentData(address:V62ServerAddress,databaseTable: DatabaseTable,card:LPCard): Unit ={
    sendData(address,databaseTable,
      V62OperateOptions(
        AncientConstants.OP_CLASS_LPLIB,
        AncientConstants.OP_LPLIB_UPDATE,
        syncLatentData(card)))
  }
  def sendLatentData(address:V62ServerAddress,databaseTable: DatabaseTable,card:LPCard): Unit ={
    sendData(address,databaseTable,
      V62OperateOptions(
        AncientConstants.OP_CLASS_LPLIB,
        AncientConstants.OP_LPLIB_ADD,
        syncLatentData(card)))
  }
  def deleteLatentData(address:V62ServerAddress,databaseTable: DatabaseTable,key:String): Unit ={
    sendData(address,databaseTable,
      V62OperateOptions(
        AncientConstants.OP_CLASS_LPLIB,
        AncientConstants.OP_LPLIB_DEL,
        NoneOperator),
      DeleteDataHeader(key))
  }


  val NoneOperator = (channel:ChannelOperator) =>{

  }
  val AddDataHeader = (header:RequestHeader)=>{
    header.bnData = Array[Byte](1)
  }
  def DeleteDataHeader(key:String)(header:RequestHeader): Unit ={
    header.bnData = key.getBytes(AncientConstants.GBK_ENCODING)
  }
  private def sendData(address:V62ServerAddress,databaseTable: DatabaseTable,options:V62OperateOptions,headerDataModifier: HeaderDataModifier=AddDataHeader): Unit ={
    createAncientClient(address.host,address.port).executeInChannel{channel=>
      val header = new RequestHeader
      header.szUserName=address.user
      address.password.foreach(header.szUserPass = _)

      header.nDBID = databaseTable.dbId.asInstanceOf[Short]
      header.nTableID = databaseTable.tableId.asInstanceOf[Short]

      headerDataModifier(header)

      //set operation data
      header.nOpClass = options.opClass.asInstanceOf[Short]
      header.nOpCode= options.opCode.asInstanceOf[Short]
      channel.writeMessage[NoneResponse](header)

      options.func(channel)

      //finally receive server response
      val response  = channel.receive[ResponseHeader]()
      validateResponse(response,channel)

    }
  }
  private def syncCase(protoCase:Case)(channel:ChannelOperator):Unit = {
    //building data
    val data  = CaseStruct.convertProtobuf2Case(protoCase)

    val nfing = data.nFingerCount
    val npalm = data.nPalmCount
    val ntext = data.nTextItemCount
    var nextrainfolen = data.nExtraInfoLen
    // no other info to be sent, quit
    if ( nfing<=0 && npalm<=0 && ntext<=0 && nextrainfolen<=0 ) {
      throw new IllegalArgumentException
    }
    var response = channel.writeMessage[ResponseHeader](data)
    validateResponse(response,channel)

    var bExtraInfoFirst = 0
    val bnData = new String(response.bnData)
    if ( bnData.indexOf("$version=002$")>=0 ) {
      bExtraInfoFirst = 1
    }
    else if ( bnData.indexOf("$version=001$")>=0 ) {
      bExtraInfoFirst = 0
    } else {
      nextrainfolen = 0
    }


    if ( nfing>0 && data.pstFingerIdData != null ) {
      data.pstFingerIdData.foreach(channel.writeMessage[NoneResponse](_))
    }
    if ( npalm>0 && data.pstPalmIdData != null ) {
      data.pstPalmIdData.foreach(channel.writeMessage[NoneResponse](_))
    }
    if ( ntext>0 && data.pstTextData!= null ) {
      data.pstTextData.foreach(channel.writeMessage[NoneResponse](_))
    }

    if ( bExtraInfoFirst> 0 && ( nextrainfolen > 0 ) ) {
      channel.writeMessage[NoneResponse](data.pstExtraInfoData)
      if(data.pstExtraInfoData.nItemSize>0){
        response = channel.receive[ResponseHeader]()
        validateResponse(response,channel)
        channel.writeMessage(data.pstExtraInfoData.pstItemEntryData)
      }
    }

    if(ntext > 0 && data.pstTextData != null)
      data.pstTextData.filter(_.bIsPointer == 1).foreach{x=>
        channel.writeByteArray[NoneResponse](x.textContent)
      }

  }
  private def syncTemplateData(card:TPCard)(channel:ChannelOperator):Unit={
    val data = DataSyncStruct.convertProtoBuf2TPCard(card)

    var response = channel.writeMessage[ResponseHeader](data)
    validateResponse(response,channel)


    if(data.pstMICData != null)
      data.pstMICData.foreach(channel.writeMessage[NoneResponse](_))
    if(data.pstTextData!= null)
      data.pstTextData.foreach(channel.writeMessage[NoneResponse](_))

    response = channel.receive[ResponseHeader]()
    validateResponse(response,channel)

    if(data.pstMICData != null)
      data.pstMICData.foreach(sendGAFISMICSTRUCT(_,channel))

    if(data.pstTextData != null)
      data.pstTextData.filterNot(_.textContent == null).foreach(x=>channel.writeByteArray[NoneResponse](x.textContent))

  }
  private def syncLatentData(card:LPCard)(channel:ChannelOperator):Unit={
    val data = DataSyncStruct.convertProtoBuf2LPCard(card)

    var response = channel.writeMessage[ResponseHeader](data)
    validateResponse(response,channel)

    val bnData = new String(response.bnData)
    var extraInfo = 0
    if(bnData.indexOf("$version=001$") >= 0){
      extraInfo = data.nExtraInfoLen
    }

    if(data.pstMICData != null)
      channel.writeMessage[NoneResponse](data.pstMICData)

    if(data.pstTextData != null)
    data.pstTextData.foreach(channel.writeMessage[NoneResponse](_))

    response = channel.receive[ResponseHeader]()
    validateResponse(response,channel)

    if(data.pstMICData != null)
      sendGAFISMICSTRUCT(data.pstMICData,channel)

    if(data.pstTextData != null)
      data.pstTextData.filterNot(_.textContent == null).foreach(x=>channel.writeByteArray[NoneResponse](x.textContent))

    if(extraInfo > 0)
      channel.writeMessage[NoneResponse](data.pstExtraInfoData)

  }

  private def sendGAFISMICSTRUCT(mic:tagGAFISMICSTRUCT,channel:ChannelOperator): Unit ={
    if(mic.nMntLen > 0)
      channel.writeByteArray[NoneResponse](mic.pstMntData)
    if(mic.nImgLen > 0)
      channel.writeByteArray[NoneResponse](mic.pstImgData)
    if(mic.nCprLen > 0)
      channel.writeByteArray[NoneResponse](mic.pstCprData)
    if(mic.nBinLen > 0)
      channel.writeByteArray[NoneResponse](mic.pstBinData)
  }
}

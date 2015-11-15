package nirvana.hall.v62.internal

import monad.support.services.LoggerSupport
import nirvana.hall.protocol.v62.FPTProto.{Case, LPCard, TPCard}
import nirvana.hall.v62.AncientConstants
import nirvana.hall.v62.internal.c.ghpcbase.gnopcode._
import nirvana.hall.v62.internal.c.gloclib.glocdef.GAFISMICSTRUCT
import nirvana.hall.v62.internal.c.gloclib.glocndef.{GNETANSWERHEADOBJECT, GNETREQUESTHEADOBJECT}
import nirvana.hall.v62.services.{ChannelOperator, DatabaseTable, V62ServerAddress}

/**
 * provide data sync function
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-03
 */
@deprecated(message = "use gnetflib method instead of")
trait DataSyncSupport {
  this:LoggerSupport with AncientClientSupport =>

  //change header data
  private type HeaderDataModifier = GNETREQUESTHEADOBJECT => Unit
  private type DataWriter = ChannelOperator => Unit
  //nothing write
  private val NoneOperator:DataWriter = channel =>{
  }
  private val AddDataHeader:HeaderDataModifier = header =>{
    header.bnData = Array[Byte](1)
  }
  //copy key to header
  private def DeleteDataHeader(key:String)(header:GNETREQUESTHEADOBJECT): Unit ={
    header.bnData = key.getBytes(AncientConstants.GBK_ENCODING)
  }
  private case class V62OperateOptions(opClass:Int,opCode:Int,func:DataWriter)
  /**
   * send case data to v6.2 system
   * @param databaseTable database define
   * @param protoCase case data based on protobuf
   */
  def addCaseData(serverAddress:V62ServerAddress,databaseTable: DatabaseTable,protoCase:Case): Unit ={
    sendData(serverAddress,databaseTable,
      V62OperateOptions(
        OP_CLASS_CASE,
        OP_CASE_ADD,
        syncCase(protoCase)))
  }

  /**
   * update case data
   * @param serverAddress v6.2 server address
   * @param databaseTable database table of case
   * @param protoCase case object
   */
  def updateCaseData(serverAddress:V62ServerAddress,databaseTable: DatabaseTable,protoCase:Case): Unit ={
    sendData(serverAddress,databaseTable,
      V62OperateOptions(
        OP_CLASS_CASE,
        OP_CASE_UPDATE,
        syncCase(protoCase)))
  }

  /**
   * delete case by caseId
   * @param serverAddress server address of v6.2
   * @param databaseTable database table of case
   * @param caseId case id
   */
  def deleteCaseData(serverAddress:V62ServerAddress,databaseTable: DatabaseTable,caseId:String): Unit ={
    sendData(serverAddress,databaseTable,
      V62OperateOptions(
        OP_CLASS_CASE,
        OP_CASE_DEL,
      NoneOperator),DeleteDataHeader(caseId))
  }

  /**
    * update template data
    * @param address server address
    * @param databaseTable database table
    * @param card template data
    */
  def updateTemplateData(address:V62ServerAddress,databaseTable: DatabaseTable,card: TPCard): Unit ={
    sendData(address,databaseTable,
      V62OperateOptions(
        OP_CLASS_TPLIB,
        OP_TPLIB_UPDATE,
        syncTemplateData(card)))
  }

  /**
   * add template data to 6.2
   * @param address server address
   * @param databaseTable database table
   * @param card template data
   */
  def addTemplateData(address:V62ServerAddress,databaseTable: DatabaseTable,card: TPCard): Unit ={
    sendData(address,databaseTable,
      V62OperateOptions(
        OP_CLASS_TPLIB,
        OP_TPLIB_ADD,
        syncTemplateData(card)))
  }

  /**
   * delete template data
   * @param address server address
   * @param databaseTable database table of template
   * @param key card id number
   */
  def deleteTemplateData(address:V62ServerAddress,databaseTable: DatabaseTable,key:String): Unit ={
    sendData(address,databaseTable,
      V62OperateOptions(
        OP_CLASS_TPLIB,
        OP_TPLIB_DEL,
        NoneOperator),
      DeleteDataHeader(key))
  }

  /**
   * update latenet data
   * @param address server address
   * @param databaseTable database table
   * @param card latent card 
   */
  def updateLatentData(address:V62ServerAddress,databaseTable: DatabaseTable,card:LPCard): Unit ={
    sendData(address,databaseTable,
      V62OperateOptions(
        OP_CLASS_LPLIB,
        OP_LPLIB_UPDATE,
        syncLatentData(card)))
  }

  /**
   * add latent data to 6.2
   * @param address server address
   * @param databaseTable database table
   * @param card latent card
   */
  def addLatentData(address:V62ServerAddress,databaseTable: DatabaseTable,card:LPCard): Unit ={
    sendData(address,databaseTable,
      V62OperateOptions(
        OP_CLASS_LPLIB,
        OP_LPLIB_ADD,
        syncLatentData(card)))
  }

  /**
   * delete latent
   * @param address server address
   * @param databaseTable database table
   * @param key key of latent data
   */
  def deleteLatentData(address:V62ServerAddress,databaseTable: DatabaseTable,key:String): Unit ={
    sendData(address,databaseTable,
      V62OperateOptions(
        OP_CLASS_LPLIB,
        OP_LPLIB_DEL,
        NoneOperator),
      DeleteDataHeader(key))
  }


  private def sendData(address:V62ServerAddress,databaseTable: DatabaseTable,options:V62OperateOptions,headerDataModifier: HeaderDataModifier=AddDataHeader): Unit ={
    executeInChannel{channel=>
      val header = new GNETREQUESTHEADOBJECT
      header.szUserName=address.user
      address.password.foreach(header.szUserPass = _)

      header.nDBID = databaseTable.dbId.asInstanceOf[Short]
      header.nTableID = databaseTable.tableId.asInstanceOf[Short]

      headerDataModifier(header)

      //set operation data
      header.nOpClass = options.opClass.asInstanceOf[Short]
      header.nOpCode= options.opCode.asInstanceOf[Short]
      //send commond request header
      channel.writeMessage[NoneResponse](header)

      //transfer data
      options.func(channel)

      //Finally receive server response
      val response  = channel.receive[GNETANSWERHEADOBJECT]()
      validateResponse(channel,response)

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
    var response = channel.writeMessage[GNETANSWERHEADOBJECT](data)
    validateResponse(channel,response)

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


    if ( nfing>0 && data.pstFingerID_Data != null ) {
      data.pstFingerID_Data.foreach(channel.writeMessage[NoneResponse](_))
    }
    if ( npalm>0 && data.pstPalmID_Data!= null ) {
      data.pstPalmID_Data.foreach(channel.writeMessage[NoneResponse](_))
    }
    if ( ntext>0 && data.pstText_Data!= null ) {
      data.pstText_Data.foreach(channel.writeMessage[NoneResponse](_))
    }

    if ( bExtraInfoFirst> 0 && ( nextrainfolen > 0 ) ) {
      channel.writeMessage[NoneResponse](data.pstExtraInfo_Data)
      if(data.pstExtraInfo_Data != null ){
        response = channel.receive[GNETANSWERHEADOBJECT]()

        validateResponse(channel,response)
        val head = data.pstExtraInfo_Data
        channel.writeMessage[NoneResponse](head.pstItemEntry_Data)
      }
    }

    if(ntext > 0 && data.pstText_Data != null)
      data.pstText_Data.filter(_.bIsPointer == 1).foreach{x=>
        channel.writeByteArray[NoneResponse](x.stData.textContent)
      }

  }
  private def syncTemplateData(card:TPCard)(channel:ChannelOperator):Unit={
    val data = FeatureStruct.convertProtoBuf2TPCard(card)

    var response = channel.writeMessage[GNETANSWERHEADOBJECT](data)
    validateResponse(channel,response)


    if(data.pstMIC_Data != null)
      data.pstMIC_Data.foreach(channel.writeMessage[NoneResponse](_))
    if(data.pstText_Data!= null)
      data.pstText_Data.foreach(channel.writeMessage[NoneResponse](_))

    response = channel.receive[GNETANSWERHEADOBJECT]()
    validateResponse(channel,response)

    if(data.pstMIC_Data != null)
      data.pstMIC_Data.foreach(sendGAFISMICSTRUCT(_,channel))

    if(data.pstText_Data != null)
      data.pstText_Data.filterNot(_.stData.textContent == null).foreach(x=>channel.writeByteArray[NoneResponse](x.stData.textContent))

  }
  private def syncLatentData(card:LPCard)(channel:ChannelOperator):Unit={
    val data = FeatureStruct.convertProtoBuf2LPCard(card)

    var response = channel.writeMessage[GNETANSWERHEADOBJECT](data)
    validateResponse(channel,response)

    val bnData = new String(response.bnData)
    var extraInfo = 0
    if(bnData.indexOf("$version=001$") >= 0){
      extraInfo = data.nExtraInfoLen
    }

    if(data.pstMIC_Data != null)
      channel.writeMessage[NoneResponse](data.pstMIC_Data)

    if(data.pstText_Data != null)
    data.pstText_Data.foreach(channel.writeMessage[NoneResponse](_))

    response = channel.receive[GNETANSWERHEADOBJECT]()
    validateResponse(channel,response)

    if(data.pstMIC_Data != null)
      data.pstMIC_Data.foreach(sendGAFISMICSTRUCT(_,channel))

    if(data.pstText_Data != null)
      data.pstText_Data.filterNot(_.stData.textContent == null)
        .foreach(x=>channel.writeByteArray[NoneResponse](x.stData.textContent))

    if(extraInfo > 0)
      channel.writeMessage[NoneResponse](data.pstExtraInfo_Data)

  }

  private def sendGAFISMICSTRUCT(mic:GAFISMICSTRUCT,channel:ChannelOperator): Unit ={
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

package nirvana.hall.v62.internal

import monad.support.services.LoggerSupport
import nirvana.hall.protocol.v62.FPTProto.Case
import nirvana.hall.v62.AncientConstants
import nirvana.hall.v62.services.{V62ServerAddress, ChannelOperator, DatabaseTable}
import org.apache.commons.io.IOUtils

/**
 * provide data sync function
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-03
 */
trait DataSyncSupport {
  this:LoggerSupport with AncientClientSupport =>
  /**
   * send case data to v6.2 system
   * @param databaseTable database define
   * @param protoCase case data based on protobuf
   */
  def sendCaseData(serverAddress:V62ServerAddress,databaseTable: DatabaseTable,protoCase:Case): Unit ={
    createAncientClient(serverAddress.host,serverAddress.port)
      .executeInChannel{channel=>
      val header = new RequestHeader
      header.szUserName=serverAddress.user
      serverAddress.password.foreach(header.szUserPass= _)

      header.nDBID = databaseTable.dbId.asInstanceOf[Short]
      header.nTableID = databaseTable.tableId.asInstanceOf[Short]

      header.bnData1=1
      header.bnData2 = 0
      header.bnData3 = 0

      header.nOpClass = AncientConstants.OP_CLASS_CASE
      header.nOpCode= AncientConstants.OP_CASE_ADD
      channel.writeMessage[NoneResponse](header)


      syncCase(channel,protoCase)


      //finally receive server response
      val response  = channel.receive[ResponseHeader]()
      validateResponse(response,channel)

    }
  }
  private def syncCase(channel:ChannelOperator,protoCase:Case):Unit = {
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

    var bExtraInfoFirst = 0;
    val bnData = new String(response.bnData)
    if ( bnData.indexOf("$version=002$")>=0 ) {
      bExtraInfoFirst = 1;
    }
    else if ( bnData.indexOf("$version=001$")>=0 ) {
      bExtraInfoFirst = 0;
    } else {
      nextrainfolen = 0;
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

    if(ntext > 0)
      data.pstTextData.filter(_.bIsPointer == 1).foreach{x=>
        channel.writeByteArray[NoneResponse](x.textContent)
      }

  }
  def sendData(address:V62ServerAddress,databaseTable: DatabaseTable): Unit ={
    createAncientClient(address.host,address.port).executeInChannel{channel=>
      val header = new RequestHeader
      header.szUserName=address.host
      address.password.foreach(header.szUserPass = _)

      header.nDBID = databaseTable.dbId.asInstanceOf[Short]
      header.nTableID = databaseTable.tableId.asInstanceOf[Short]

      header.bnData1=1
      header.bnData2 = 0
      header.bnData3 = 0


      /// sync latent data
      /*
      header.nOpClass = AncientConstants.OP_CLASS_LPLIB
      header.nOpCode= AncientConstants.OP_LPLIB_ADD
      channel.writeMessage[NoneResponse](header)
      syncLatentData(channel)
      */

      //sync template data
      header.nOpClass = AncientConstants.OP_CLASS_TPLIB
      header.nOpCode= AncientConstants.OP_TPLIB_ADD
      channel.writeMessage[NoneResponse](header)
      syncTemplateData(channel)


      //finally receive server response
      val response  = channel.receive[ResponseHeader]()
      validateResponse(response,channel)

    }
  }
  private def syncTemplateData(channel:ChannelOperator):Unit={
    //construct data model
    val data = new tagGTPCARDINFOSTRUCT()
    data.szCardID=System.currentTimeMillis().toString

    val mics = Array(new tagGAFISMICSTRUCT())
    mics.foreach{mic=>
      mic.pstMntData =  IOUtils.toByteArray(getClass.getResourceAsStream("/t.mnt"))
      mic.nMntLen = mic.pstMntData.length

      mic.pstCprData=  IOUtils.toByteArray(getClass.getResourceAsStream("/t.cpr"))
      mic.nCprLen= mic.pstCprData.length
      mic.nItemData = 1 //指位信息

      mic.nItemFlag = 5 //传送的特征类型 ,特征+图像 , 1 2 4 8
      mic.nItemType = 1 //指纹数据
      mic.bIsLatent = 0 //是否位现场数据
    }
    data.nMicItemCount = mics.length.toByte

    val texts = Array(new tagGATEXTITEMSTRUCT())
    texts.foreach{t=>
      t.szItemName="name"
      t.bIsPointer = 1
      t.textContent = "我们".getBytes("GBK")
      t.nItemLen = t.textContent.length
    }
    data.nTextItemCount = texts.length.toShort




    var response = channel.writeMessage[ResponseHeader](data)
    validateResponse(response,channel)

    /*
    val bnData = new String(response.bnData)
    var extraInfo = 0
    if(bnData.indexOf("$version=001$") >= 0){
      extraInfo = data.nExtraInfoLen
    }
    */

    mics.foreach(channel.writeMessage[NoneResponse](_))
    texts.foreach(channel.writeMessage[NoneResponse](_))

    response = channel.receive[ResponseHeader]()
    validateResponse(response,channel)

    mics.foreach(sendGAFISMICSTRUCT(_,channel))

    texts.filterNot(_.textContent == null).foreach(x=>channel.writeByteArray[NoneResponse](x.textContent))

    /*
    if(extraInfo > 0)
      channel.writeMessage[NoneResponse](data.pstExtraInfoData)
      */

  }
  private def syncLatentData(channel:ChannelOperator):Unit={

    //construct data model
    val data = new tagGLPCARDINFOSTRUCT()
    data.szCardID=System.currentTimeMillis().toString

    val mics = Array(new tagGAFISMICSTRUCT())
    mics.foreach{mic=>
      mic.pstMntData =  IOUtils.toByteArray(getClass.getResourceAsStream("/lf.mnt"))
      mic.nMntLen = mic.pstMntData.length

      mic.pstImgData =  IOUtils.toByteArray(getClass.getResourceAsStream("/lf.img"))
      mic.nImgLen = mic.pstImgData.length

      mic.nItemFlag = 3 //传送的特征类型 ,特征+图像
      mic.nItemType = 1 //指纹数据
      mic.bIsLatent = 1 //是否位现场数据
    }
    data.nMicItemCount = mics.length.toByte

    val texts = Array(new tagGATEXTITEMSTRUCT())
    texts.foreach{t=>
      t.szItemName="SeqNo"
      t.bIsPointer = 1
      t.textContent = "01".getBytes
      t.nItemLen = t.textContent.length
    }
    data.nTextItemCount = texts.length.toShort




    var response = channel.writeMessage[ResponseHeader](data)
    validateResponse(response,channel)

    val bnData = new String(response.bnData)
    var extraInfo = 0
    if(bnData.indexOf("$version=001$") >= 0){
      extraInfo = data.nExtraInfoLen
    }

    mics.foreach(channel.writeMessage[NoneResponse](_))
    texts.foreach(channel.writeMessage[NoneResponse](_))

    response = channel.receive[ResponseHeader]()
    validateResponse(response,channel)

    mics.foreach(sendGAFISMICSTRUCT(_,channel))

    texts.filterNot(_.textContent == null).foreach(x=>channel.writeByteArray[NoneResponse](x.textContent))

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

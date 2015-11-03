package nirvana.hall.v62.internal

import monad.support.services.LoggerSupport
import nirvana.hall.v62.AncientConstants
import nirvana.hall.v62.services.{ChannelOperator, DatabaseTable}
import org.apache.commons.io.IOUtils

/**
 * provide data sync function
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-03
 */
trait DataSyncSupport {
  this:LoggerSupport with AncientClientSupport =>
  def sendData(databaseTable: DatabaseTable): Unit ={
    createAncientClient.executeInChannel{channel=>
      val header = new RequestHeader
      header.szUserName="afisadmin"
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

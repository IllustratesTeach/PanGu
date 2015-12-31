package nirvana.hall.v62.internal

import java.nio.ByteBuffer

import monad.support.services.LoggerSupport
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.ganumia.gadbdef.GADB_KEYARRAY
import nirvana.hall.c.services.ghpcbase.gnopcode._
import nirvana.hall.c.services.GADB_RETVAL
import nirvana.hall.c.services.gloclib.gaqryque.{GAQUERYCANDSTRUCT, GAQUERYCANDHEADSTRUCT, GAQUERYSTRUCT}
import nirvana.hall.c.services.gloclib.glocdef.GAFISMICSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.{GNETANSWERHEADOBJECT, GNETREQUESTHEADOBJECT}
import nirvana.hall.v62.services.{ChannelOperator, SelfMatchTask, V62ServerAddress}

/**
 * send match task support
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-02
 */
@deprecated(message = "use ganetqry method instead of")
trait SendMatchTaskSupport {
  this:AncientClientSupport with LoggerSupport =>

  def queryMatchResult(address:V62ServerAddress,sid:Long):Unit ={
    executeInChannel{channel=>
      val header = new GNETREQUESTHEADOBJECT
      header.szUserName=address.user
      address.password.foreach(header.szUserPass = _)
      header.nOpClass = OP_CLASS_QUERY.asInstanceOf[Short]
      header.nOpCode= OP_QUERY_GET.asInstanceOf[Short]
      header.nDBID = 20
      header.nTableID = 2

      /*
      header.bnData1=1
      header.bnData2 = 0
      header.bnData3 = 0
      */

      channel.writeMessage[NoneResponse](header)


      val queryStruct = new GAQUERYSTRUCT
      //fill simple data
      queryStruct.stSimpQry.nQueryType = 0
      queryStruct.stSimpQry.nPriority = 1
      queryStruct.stSimpQry.nFlag = 1
      queryStruct.stSimpQry.stSrcDB.nDBID = 0
      queryStruct.stSimpQry.stSrcDB.nTableID= 0
      queryStruct.stSimpQry.stDestDB.apply(0).nDBID = 0
      queryStruct.stSimpQry.stDestDB.apply(0).nTableID= 0
      queryStruct.stSimpQry.tSubmitTime.tDate.tYear = 115
      queryStruct.stSimpQry.tSubmitTime.tDate.tMonth = 9
      queryStruct.stSimpQry.tSubmitTime.tDate.tDay = 30
      queryStruct.stSimpQry.nDestDBCount = 1
      queryStruct.stSimpQry.nQueryID = convertLongAsSixByteArray(sid)


      queryStruct.nItemFlagA = 64


      val response = channel.writeMessage[GNETANSWERHEADOBJECT](queryStruct)
      debug("query struct sent,then return code:{},ndatalen:{}",response.nReturnValue,response.nDataLen)
      validateResponse(channel,response)
      //response = channel.receive[GNETANSWERHEADOBJECT]()

      response.nReturnValue = 1
      channel.writeMessage[NoneResponse](response)

      val matchResult = channel.receive[GAQUERYSTRUCT]()
      val count = matchResult.nMICCount
      debug("match result is {}",count)

      val headers = 0 until count map(i=>channel.receive[GAFISMICSTRUCT]())

      headers.foreach{header=>
        if(header.nMntLen > 0)
          channel.receiveByteArray(header.nMntLen)
        if(header.nImgLen > 0)
          channel.receiveByteArray(header.nImgLen)
        if(header.nCprLen > 0)
          channel.receiveByteArray(header.nCprLen)
        if(header.nBinLen > 0)
          channel.receiveByteArray(header.nBinLen)
      }

      //receive server list
      if(matchResult.nSvrListLen > 0 )
        channel.receiveByteArray(matchResult.nSvrListLen)
      var candHead:GAQUERYCANDHEADSTRUCT = null
      if(matchResult.nCandHeadLen >0) {
        val buffer = channel.receiveByteArray(matchResult.nCandHeadLen) //
        val bytes = buffer.toByteBuffer().array()

        candHead = new GAQUERYCANDHEADSTRUCT
        candHead.fromStreamReader(buffer)
      }
      if(matchResult.nCandLen > 0) {
        val buffer = channel.receiveByteArray(matchResult.nCandLen)
        val num = candHead.nCandidateNum & 0x0000ffff
        debug("cand num:{}",num)
        val result = 0 until num map{i=>
          val cand = new GAQUERYCANDSTRUCT
          cand.fromStreamReader(buffer)
          debug("sid:{} pos:{} score:{}",convertSixByteArrayToLong(cand.nSID),cand.nIndex,cand.nScore)
          cand
        }
      }

      if(matchResult.nQryCondLen>0)
        channel.receiveByteArray(matchResult.nQryCondLen)
      if(matchResult.nMISCondLen>0)
        channel.receiveByteArray(matchResult.nMISCondLen)
      if(matchResult.nTextSqlLen>0)
        channel.receiveByteArray(matchResult.nTextSqlLen)
      if(matchResult.nCommentLen> 0)
        channel.receiveByteArray(matchResult.nCommentLen)
      if(matchResult.nQryInfoLen> 0)
        channel.receiveByteArray(matchResult.nQryInfoLen)
    }
  }
  def NET_GAFIS_QUERY_Add(address:V62ServerAddress,
                          nQryDBID:Short,
                          nQryTID:Short,
                          pstQry:GAQUERYSTRUCT,
                          nOption:Int = 0
                          ):Long= executeInChannel{channel=>

    val request = new GNETREQUESTHEADOBJECT
    request.cbSize = 192
    request.nMajorVer = 6
    request.nMinorVer = 1
    request.szUserName=address.user
    address.password.foreach(request.szUserPass = _ )
    request.nOption = nOption
    request.nDBID = nQryDBID
    request.nTableID = nQryTID
    request.nOpClass = OP_CLASS_QUERY.asInstanceOf[Short]
    request.nOpCode = OP_QUERY_ADD.asInstanceOf[Short]

    channel.writeMessage[NoneResponse](request)


    GAFIS_NETSCR_SendQueryInfo(channel,pstQry)

    val response = channel.receive[GNETANSWERHEADOBJECT]()//.writeMessage[GNETANSWERHEADOBJECT](pstQry)
    validateResponse(channel,response)

    response.nReturnValue
  }
  def NET_GAFIS_QUERY_Submit(address:V62ServerAddress,nQryDBID:Short,nQryTID:Short,
                             pstKey:GADB_KEYARRAY,
                             pstQry:GAQUERYSTRUCT,
                             pnIdx:Array[Byte],
                             nOption:Int = 0
                              ):Array[GADB_RETVAL]= executeInChannel{channel=>

    val request = new GNETREQUESTHEADOBJECT
    request.cbSize = 192
    request.nMajorVer = 6
    request.nMinorVer = 1
    request.szUserName=address.user
    address.password.foreach(request.szUserPass = _ )
    request.nOption = nOption
    request.nDBID = nQryDBID
    request.nTableID = nQryTID
    request.nOpClass = OP_CLASS_QUERY.asInstanceOf[Short]
    request.nOpCode = OP_QUERY_SUBMIT.asInstanceOf[Short]

    val byteBuffer = ByteBuffer.allocate(7)
      .putInt(pstKey.nKeyCount)
      .putShort(pstKey.nKeySize)
      .put(pnIdx.length.asInstanceOf[Byte])

    request.bnData = byteBuffer.array()
    var response = channel.writeMessage[GNETANSWERHEADOBJECT](request)
    validateResponse(channel,response)


    channel.writeByteArray[NoneResponse](pstKey.pKey_Data) //send key
    if(pnIdx.length>0) channel.writeByteArray[NoneResponse](pnIdx)

    GAFIS_NETSCR_SendQueryInfo(channel,pstQry)

    response = channel.receive[GNETANSWERHEADOBJECT]()//.writeMessage[GNETANSWERHEADOBJECT](pstQry)
    validateResponse(channel,response)
    0.until(pstKey.nKeyCount).map{ i=>
      channel.receive[GADB_RETVAL]()
    }.toArray
  }
  private def GAFIS_NETSCR_SendQueryInfo(channel:ChannelOperator, pstQry:GAQUERYSTRUCT)
  {


    val ncandhead = pstQry.nCandHeadLen
    val ncand = pstQry.nCandLen
    val nqrycond = pstQry.nQryCondLen
    val nMicCount = pstQry.nMICCount;
    val nSvrListLen = pstQry.nSvrListLen;
    val nMisCondLen = pstQry.nMISCondLen;
    val nTxtSqlLen = pstQry.nTextSqlLen;
    val nCommentLen = pstQry.nCommentLen;	// comment length.
  val nqryinfolen = pstQry.nQryInfoLen;

    if ( ncand<=0 && ncandhead<=0 && nqrycond<=0 && nMicCount<=0 && nSvrListLen<=0 &&
      nMisCondLen<=0 && nTxtSqlLen<=0 && nCommentLen<=0 && nqryinfolen<=0 ) {
      //throw new IllegalArgumentException("data is null");
    }
    val response = channel.writeMessage[GNETANSWERHEADOBJECT](pstQry)
    validateResponse(channel,response)

    if( nMicCount > 0)
    {
      pstQry.pstMIC_Data.foreach(channel.writeMessage[NoneResponse](_))
      pstQry.pstMIC_Data.foreach(GAFIS_NETSCR_SendMICStruct(channel,_))
    }
    if( nSvrListLen > 0 )
      channel.writeByteArray(pstQry.pstSvrList_Data,0,nSvrListLen);

    if ( ncandhead >0)
      channel.writeMessage[NoneResponse](pstQry.pstCandHead_Data)
    if ( ncand >0 )
      pstQry.pstCand_Data.foreach(channel.writeMessage[NoneResponse](_))

    if ( nqrycond>0 ) {
      channel.writeByteArray[NoneResponse](pstQry.pstQryCond_Data,0,nqrycond)
    }
    if ( nMisCondLen>0 ) {
      channel.writeByteArray[NoneResponse]( pstQry.pstMISCond_Data, 0,nMisCondLen)
    }
    if ( nTxtSqlLen>0 ) channel.writeByteArray[NoneResponse]( pstQry.pstTextSql_Data, 0,nTxtSqlLen);
    if ( nCommentLen>0 ) channel.writeByteArray[NoneResponse]( pstQry.pszComment_Data, 0,nCommentLen);
  }

  def sendMatchTask(address:V62ServerAddress,task:SelfMatchTask): Long ={
    executeInChannel{channel=>
      //because gafis use strcpy to get key,so append 0
      val keyBytes = task.cardId.getBytes(AncientConstants.UTF8_ENCODING)
      val buffer = ByteBuffer.allocate(keyBytes.length + 1)
      buffer.put(keyBytes)
      buffer.put(0.asInstanceOf[Byte])

      val key = buffer.array()
      val header = new GNETREQUESTHEADOBJECT
      header.szUserName=address.user
      address.password.foreach(header.szUserPass = _ )
      header.nOpClass = OP_CLASS_QUERY.asInstanceOf[Short]
      header.nOpCode= OP_QUERY_SUBMIT.asInstanceOf[Short]

      header.nDBID = 20 //查询结果的保存位置
      header.nTableID = 2

      //send 7 byte: 1) key count 2) key length 3) finger position count
      val bytes = ByteBuffer.allocate(7)
        .putInt(1)
        .putShort(key.length.asInstanceOf[Short])
        .put(task.options.positions.length.asInstanceOf[Byte]).array()
      header.bnData = bytes

      var response = channel.writeMessage[GNETANSWERHEADOBJECT](header)
      validateResponse(channel,response)

      channel.writeByteArray[NoneResponse](key)

      val pos = task.options.positions.map(_.asInstanceOf[Byte])
      channel.writeByteArray[NoneResponse](pos.toArray)

      val queryStruct = new GAQUERYSTRUCT
      //fill simple data
      queryStruct.stSimpQry.nQueryType = task.options.matchType.ordinal().asInstanceOf[Byte]
      queryStruct.stSimpQry.nPriority = 1
      queryStruct.stSimpQry.nFlag = 1
      queryStruct.stSimpQry.stSrcDB.nDBID = task.options.srcDb.dbId.asInstanceOf[Short]
      queryStruct.stSimpQry.stSrcDB.nTableID= task.options.srcDb.tableId.asInstanceOf[Short]
      queryStruct.stSimpQry.stDestDB.apply(0).nDBID = task.options.destDb.dbId.asInstanceOf[Short]
      queryStruct.stSimpQry.stDestDB.apply(0).nTableID= task.options.destDb.tableId.asInstanceOf[Short]
      //queryStruct.stSimpQry.tSubmitTime.tDate.tYear = 115
      //queryStruct.stSimpQry.tSubmitTime.tDate.tMonth = 9
      //queryStruct.stSimpQry.tSubmitTime.tDate.tDay = 30
      queryStruct.stSimpQry.nDestDBCount = 1


      queryStruct.nItemFlagA = 64

      response = channel.writeMessage[GNETANSWERHEADOBJECT](queryStruct)
      validateResponse(channel,response)
      val ret = channel.receive[GADB_RETVAL]()
      convertSixByteArrayToLong(ret.nSID)
    }
  }
  /**
   * 转换6个字节成long
   * @param bytes 待转换的六个字节
   * @return 转换后的long数字
   */
  def convertSixByteArrayToLong(bytes: Array[Byte]): Long = {
    /*
    val byteBuffer = ByteBuffer.allocate(8).put(Array[Byte](0,0)).put(bytes)
    byteBuffer.rewind()
    byteBuffer.getLong
    */
    var l = 0L
    l |= (0xff & bytes(0)) << 16
    l |= (0xff & bytes(1)) << 8
    l |= (0xff & bytes(2))
    l <<= 24

    l |= (0xff & bytes(3)) << 16
    l |= (0xff & bytes(4)) << 8
    l |= (0xff & bytes(5))

    l
  }
  def convertLongAsSixByteArray(sid: Long): Array[Byte]=
    ByteBuffer.allocate(8).putLong(sid).array().slice(2,8)

  final val GAFIS_KEYLIST_GetName = "KeyList"
  final val GAFIS_QRYPARAM_GetName = "QryParam"
  final val GAFIS_QRYFILTER_GetName = "QryFilter"
  final val GAFIS_CANDKEYFILTER_GetName = "CandKeyFilter"
  final val GAFIS_TEXTSQL_GetName = "TextSql"

}

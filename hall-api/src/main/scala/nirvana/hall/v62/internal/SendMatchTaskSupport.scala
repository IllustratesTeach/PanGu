package nirvana.hall.v62.internal

import java.nio.ByteBuffer

import monad.support.services.LoggerSupport
import nirvana.hall.v62.AncientConstants
import nirvana.hall.v62.internal.c.ghpcbase.gnopcode._
import nirvana.hall.v62.internal.c.GADB_RETVAL
import nirvana.hall.v62.internal.c.gloclib.gaqryque.{GAQUERYCANDSTRUCT, GAQUERYCANDHEADSTRUCT, GAQUERYSTRUCT}
import nirvana.hall.v62.internal.c.gloclib.glocdef.GAFISMICSTRUCT
import nirvana.hall.v62.internal.c.gloclib.glocndef.GNETREQUESTHEADOBJECT
import nirvana.hall.v62.services.{SelfMatchTask, V62ServerAddress}

/**
 * send match task support
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-02
 */
trait SendMatchTaskSupport {
  this:AncientClientSupport with LoggerSupport =>

  def queryMatchResult(address:V62ServerAddress,sid:Long):Unit ={
    createAncientClient(address.host,address.port).executeInChannel{channel=>
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


      val response = channel.writeMessage[ResponseHeader](queryStruct)
      debug("query struct sent,then return code:{},ndatalen:{}",response.nReturnValue,response.nDataLen)
      validateResponse(response,channel)
      //response = channel.receive[ResponseHeader]()

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
        candHead.fromChannelBuffer(buffer)
      }
      if(matchResult.nCandLen > 0) {
        val buffer = channel.receiveByteArray(matchResult.nCandLen)
        val num = candHead.nCandidateNum & 0x0000ffff
        debug("cand num:{}",num)
        val result = 0 until num map{i=>
          val cand = new GAQUERYCANDSTRUCT
          cand.fromChannelBuffer(buffer)
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
  def sendMatchTask(address:V62ServerAddress,task:SelfMatchTask): Long ={
    createAncientClient(address.host,address.port).executeInChannel{channel=>
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

      var response = channel.writeMessage[ResponseHeader](header)
      validateResponse(response,channel)

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

      response = channel.writeMessage[ResponseHeader](queryStruct)
      validateResponse(response,channel)
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
}

package nirvana.hall.v62.internal

import java.nio.ByteBuffer

import monad.support.services.LoggerSupport
import nirvana.hall.v62.services.SelfMatchTask
import org.jboss.netty.buffer.ChannelBuffers

import scala.collection.mutable

/**
 * send match task support
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-02
 */
trait SendMatchTaskSupport {
  this:AncientClientSupport with LoggerSupport =>

  def queryMatchResult(sid:Long):Unit ={
    createAncientClient.executeInChannel{channel=>
      val header = new RequestHeader
      header.szUserName="afisadmin"
      header.nOpClass = 105
      header.nOpCode= 455
      header.nDBID = 20
      header.nTableID = 2

      header.bnData1=1
      header.bnData2 = 0
      header.bnData3 = 0

      channel.writeMessage[NoneResponse](header)


      val queryStruct = new QueryStruct
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


      var response = channel.writeMessage[ResponseHeader](queryStruct)
      debug("query struct sent,then return code:{},ndatalen",response.nReturnValue,response.nDataLen)
      if(response.nReturnValue == -1){
        val gafisError = channel.receive[GafisError]()
        throw new IllegalAccessException("fail to send query struct,num:%s".format(gafisError.nAFISErrno))
      }else{
        //response = channel.receive[ResponseHeader]()

        response.nReturnValue = 1
        channel.writeMessage[NoneResponse](response)

        val matchResult = channel.receive[QueryStruct]()
        val count = matchResult.nMICCount
        debug("match result is {}",count)

        val headers = 0 until count map(i=>channel.receive[tagGAFISMICSTRUCT]())

        headers.foreach{header=>
          if(header.nMntLen > 0){
            channel.receiveByteArray(header.nMntLen)
          }else{
            warn("mnt len is zero")
          }
        }

        //receive server list
        if(matchResult.nSvrListLen > 0 )
          channel.receiveByteArray(matchResult.nSvrListLen)
        var candHead:tagGAQUERYCANDHEADSTRUCT = null
        if(matchResult.nCandHeadLen >0) {
          val bytes = channel.receiveByteArray(matchResult.nCandHeadLen) //
          candHead = new tagGAQUERYCANDHEADSTRUCT
          candHead.fromChannelBuffer(ChannelBuffers.wrappedBuffer(bytes))
        }
        if(matchResult.nCandLen > 0) {
          val bytes = channel.receiveByteArray(matchResult.nCandLen)
          val buffer = ChannelBuffers.wrappedBuffer(bytes)
          val num = candHead.nCandidateNum
          val result = 0 until num map{i=>
            val cand = new tagGAQUERYCANDSTRUCT
            cand.fromChannelBuffer(buffer)
            debug("sid:{} score:",convertSixByteArrayToLong(cand.nSID),cand.nScore)
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
  }
  def sendMatchTask(task:SelfMatchTask): Seq[Long] ={
    createAncientClient.executeInChannel{channel=>
      val buffer = mutable.Buffer[Byte]()
      buffer.appendAll(task.cardId.getBytes)
      buffer.append(0)
      val key = buffer.toArray
      val header = new RequestHeader
      header.szUserName="afisadmin"
      header.nOpClass = 105
      header.nOpCode= 476
      header.nDBID = 20
      header.nTableID = 2

      header.bnData1=1
      header.bnData2 = key.length.asInstanceOf[Short]
      header.bnData3 = task.options.positions.length.asInstanceOf[Byte]

      var response = channel.writeMessage[ResponseHeader](header)
      println("header sent,then return code:{}",response.nReturnValue)

      channel.writeByteArray[NoneResponse](key)

      val pos = task.options.positions.map(_.asInstanceOf[Byte])
      channel.writeByteArray[NoneResponse](pos.toArray)

      val queryStruct = new QueryStruct
      //fill simple data
      queryStruct.stSimpQry.nQueryType = task.options.matchType.ordinal().asInstanceOf[Byte]
      queryStruct.stSimpQry.nPriority = 1
      queryStruct.stSimpQry.nFlag = 1
      queryStruct.stSimpQry.stSrcDB.nDBID = task.options.srcDb.dbId.asInstanceOf[Short]
      queryStruct.stSimpQry.stSrcDB.nTableID= task.options.srcDb.tableId.asInstanceOf[Short]
      queryStruct.stSimpQry.stDestDB.apply(0).nDBID = task.options.destDb.dbId.asInstanceOf[Short]
      queryStruct.stSimpQry.stDestDB.apply(0).nTableID= task.options.destDb.tableId.asInstanceOf[Short]
      queryStruct.stSimpQry.tSubmitTime.tDate.tYear = 115
      queryStruct.stSimpQry.tSubmitTime.tDate.tMonth = 9
      queryStruct.stSimpQry.tSubmitTime.tDate.tDay = 30
      queryStruct.stSimpQry.nDestDBCount = 1


      queryStruct.nItemFlagA = 64


      response = channel.writeMessage[ResponseHeader](queryStruct)
      debug("query struct sent,then return code:{},ndatalen",response.nReturnValue,response.nDataLen)
      if(response.nReturnValue == -1){
        val gafisError = channel.receive[GafisError]()
        throw new IllegalAccessException("fail to send query struct,num:%s".format(gafisError.nAFISErrno))
      }else{
        val ret = channel.receive[GADB_RETVAL]()
        Seq[Long](convertSixByteArrayToLong(ret.nSID))
      }
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

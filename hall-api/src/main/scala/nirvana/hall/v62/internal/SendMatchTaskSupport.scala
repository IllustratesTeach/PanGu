package nirvana.hall.v62.internal

import nirvana.hall.v62.services.SelfMatchTask

/**
 * send match task support
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-02
 */
trait SendMatchTaskSupport {
  this:AncientClientSupport =>

  def sendMatchTask(task:SelfMatchTask): Seq[Long] ={
    createAncientClient.executeInChannel{channel=>
      val key = task.cardId.getBytes
      val header = new RequestHeader
      header.szUserName="afisadmin"
      header.nOpClass = 105
      header.nOpCode= 476
      header.nDBID = 20
      header.nTableID = 2

      header.bnData1=1
      header.bnData2 = key.length.asInstanceOf[Short]
      header.bnData3 = task.options.positions.length.asInstanceOf[Byte]

      val response = channel.writeMessage[ResponseHeader](header)
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
      queryStruct.stSimpQry.stDestDB.apply(0).nTableID= task.options.destDb.dbId.asInstanceOf[Short]
      queryStruct.stSimpQry.tSubmitTime.tDate.tYear = 115
      queryStruct.stSimpQry.tSubmitTime.tDate.tMonth = 9
      queryStruct.stSimpQry.tSubmitTime.tDate.tDay = 30
      queryStruct.stSimpQry.nDestDBCount = 1


      queryStruct.nItemFlagA = 64


      val response2 = channel.writeMessage[ResponseHeader](queryStruct)
      println("query struct sent,then return code:{}",response2.nReturnValue)
    }

    Seq[Long]()
  }
}

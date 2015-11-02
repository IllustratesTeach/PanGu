package nirvana.hall.v62.internal

/**
 * send match task support
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-02
 */
trait SendMatchTaskSupport {
  this:AncientClientSupport =>
  /*
  def sendMatchTask: Unit ={
    val client = createClient("10.1.6.119",6898,1)
    client.executeInChannel{channel=>
      val header = new RequestHeader
      header.szUserName="afisadmin"
      header.nOpClass = 105
      header.nOpCode= 476
      header.nDBID = 20
      header.nTableID = 2

      header.bnData1=1
      header.bnData2 = 48
      header.bnData3 = 10

      val response = channel.writeMessage[ResponseHeader](header)
      println("header sent,then return code:{}",response.nReturnValue)

      channel.writeByteArray[NoneResponse]("3702022014000002".getBytes(),0,header.bnData2)

      val pos = 0 until 10 map(x=>(x+1).asInstanceOf[Byte])
      channel.writeByteArray[NoneResponse](pos.toArray)

      val queryStruct = new QueryStruct
      //fill simple data
      queryStruct.stSimpQry.nQueryType = AncientConstants.TTQUERY.asInstanceOf[Byte]
      queryStruct.stSimpQry.nPriority = 1
      queryStruct.stSimpQry.nFlag = 1
      queryStruct.stSimpQry.stSrcDB.nDBID = 1
      queryStruct.stSimpQry.stSrcDB.nTableID= 2
      queryStruct.stSimpQry.stDestDB.apply(0).nDBID = 1
      queryStruct.stSimpQry.stDestDB.apply(0).nTableID= 2
      queryStruct.stSimpQry.tSubmitTime.tDate.tYear = 115
      queryStruct.stSimpQry.tSubmitTime.tDate.tMonth = 9
      queryStruct.stSimpQry.tSubmitTime.tDate.tDay = 30
      queryStruct.stSimpQry.nDestDBCount = 1


      queryStruct.nItemFlagA = 64


      val response2 = channel.writeMessage[ResponseHeader](queryStruct)
      println("query struct sent,then return code:{}",response2.nReturnValue)
    }
  }
  */
}

package nirvana.hall.v62.internal.sync

import java.io.ByteArrayOutputStream
import javax.sql.DataSource

import nirvana.hall.api.config.QueryQue
import nirvana.hall.api.jpa.HallFetchConfig
import nirvana.hall.api.services.TPCardService
import nirvana.hall.api.services.sync.FetchQueryService
import nirvana.hall.c.services.ghpcbase.ghpcdef.AFISDateTime
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYCANDSTRUCT
import nirvana.hall.protocol.api.HallMatchRelationProto.MatchStatus
import nirvana.hall.protocol.matcher.MatchResultProto.MatchResult
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade
import nirvana.hall.v70.internal.query.QueryConstants
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa.GafisNormalqueryQueryque

import scala.collection.mutable.ArrayBuffer

/**
  * Created by songpeng on 16/8/31.
  */
class FetchQueryServiceImpl(facade: V62Facade, config:HallV62Config, tPCardService: TPCardService, implicit val dataSource: DataSource) extends FetchQueryService {
  /**
    * 获取比对任务, 使用ora_uuid字段及不在7.0抓取6.2记录表中，确保不被重复抓取
    *
    * @param size
    * @param dbId
    * @return
    */
  override def fetchMatchTask(size: Int, dbId: Option[String]): Seq[MatchTask] = {
    val matchTaskList = new ArrayBuffer[MatchTask]
   /* val sql = ("select ora_sid,keyid,minscore,querytype,priority,maxcandnum,mic from"
              +"(select ora_sid,keyid,minscore,querytype,priority,maxcandnum,mic from"
              +"( select t.ora_uuid,t.ora_sid,t.keyid,t.minscore,t.querytype,t.priority,t.maxcandnum,t.mic from NORMALQUERY_QUERYQUE  t where  t.status<=2"
              +"and not exists (select h.ora_uuid from Hall_Fetch_Record_7to6 h where h.ora_uuid=t.ora_uuid) ) t1 )"
              +"where  rownum <=?") //取消seq作为查询条件*/
   val sql = "select * from (select t.username,t.computerip,t.userunitcode,t.ora_createtime,t.ora_sid, t.keyid, t.querytype, t.maxcandnum, t.minscore, t.priority, t.mic, t.qrycondition, t.textsql, t.flag from NORMALQUERY_QUERYQUE t where  t.status=0 ) tt where rownum <=?" //取消seq作为查询条件 第二阶段

    JdbcDatabase.queryWithPsSetter(sql) {ps =>
      //ps.setLong(1, seq)
      //ps.setLong(2, seq + size)
      ps.setLong(1, size)
    } { rs =>
      val gaQuery = new GafisNormalqueryQueryque()
      gaQuery.oraSid = rs.getLong("ora_sid") //使用seq，查询任务被删除后oraSid会被重新使用
      gaQuery.keyid = rs.getString("keyid")
      gaQuery.minscore = rs.getInt("minscore")
      gaQuery.querytype = rs.getShort("querytype")
      gaQuery.priority = rs.getShort("priority")
      gaQuery.maxcandnum = rs.getInt("maxcandnum")
      gaQuery.flag = rs.getShort("flag") //测试6.2flag字段
      gaQuery.mic = rs.getBytes("mic")
      gaQuery.createtime = rs.getTimestamp("ora_createtime") //仅限第一阶段

      gaQuery.username = rs.getString("username")
      gaQuery.computerip = rs.getString("computerip")
      gaQuery.userunitcode = rs.getString("userunitcode")
      matchTaskList += ProtobufConverter.convertGafisNormalqueryQueryque2MatchTask(gaQuery)
    }

    matchTaskList.toSeq
  }

  ///**
  //  * 更新比对状态
  //  * @param oraSid
  //  * @param status
  // */
  /* private def updateMatchStatus(oraSid: Long, status: Int): Unit ={
    val sql = "update NORMALQUERY_QUERYQUE t set t.status =? where t.seq=?"
    JdbcDatabase.update(sql){ps=>
      ps.setInt(1, status)
      ps.setLong(2, oraSid)
    }
  }*/

  /**
    * 根据远程查询queryid获取查询结果信息
    * @param queryId
    * @param pkId
    * @param typ
    * @param dbId
    * @return
    */
  override def getMatchResultByQueryid(queryId: Long, pkId: String, typ: Short, dbId: Option[String]): Option[MatchResult] = {
    throw new UnsupportedOperationException
  }

    /**
    * 保存候选信息
    * @param matchResult
    */
  override def saveMatchResult(matchResult: MatchResult, fetchConfig: HallFetchConfig, candDBIDMap: Map[String, Short] = Map(), configMap : scala.collection.mutable.HashMap[String, String]) = {
    val oraSid = matchResult.getMatchId       //oraSid
    val candNum = matchResult.getCandidateNum //candidateNum
    val maxScore = matchResult.getMaxScore   //hitpossibility
    val maxcandnum = matchResult.getMaxcandnum //maxcandnum
    val queryQue = getQueryQue(oraSid.toInt)
    val candHead = getCandHead(matchResult, queryQue)
    var candList:Array[Byte] = null
    var isChecked:Boolean = false
    var isHaveCandidate:Boolean = false
    if(candNum > 0){
      val resultMap = convertMatchResult2CandList(matchResult, queryQue.queryType, candDBIDMap, configMap)
      candList = resultMap("candList").asInstanceOf[Array[Byte]]
      isChecked = resultMap("isChecked").asInstanceOf[Boolean]
      isHaveCandidate = resultMap("isHaveCandidate").asInstanceOf[Boolean]
    }

    if(queryQue.queryType == QueryConstants.QUERY_TYPE_TT){
      var userunitcode = "" //单位代码
      val sql = "select userunitcode, querytype from normalquery_queryque where ora_sid = ? and rownum = 1"
      JdbcDatabase.queryWithPsSetter(sql){ps=>
        ps.setLong(1,oraSid.toLong)
      }{rs=>
        userunitcode = rs.getString("userunitcode")
      }
      //需要自动认证, 有比中候选
      if(isChecked){
        val sql : String = "update normalquery_queryque t " +
          "set t.ora_updatetime = sysdate, " +
          "t.ora_updator        = '@match+server@'," +
          "t.finishtime         = sysdate, " +
          "t.checktime          = sysdate, " +
          "t.curcandnum         = ?, " +   //matchresult.getcandidatenum
          "t.checkusername      = '$autocheck$', " +
          "t.verifyresult       = 2," +
          "t.hitpossibility     = ?, " +   //matchresult.setmaxscore
          "t.status             = '7', " +
          "t.checkerunitcode    = ?, " +
          "t.candhead           = ?, " +
          "t.candlist           = ?, " +
          "t.maxcandnum         = ?, " +
          "t.flagc              = '8', " +
          "t.flagg              = '1' " +
          "where t.ora_sid          = ? "

        JdbcDatabase.update(sql) { ps =>
          ps.setInt(1, candNum)
          ps.setInt(2, maxScore)
          ps.setString(3, userunitcode)
          ps.setBytes(4, candHead)
          ps.setBytes(5, candList)
          ps.setLong(6, maxcandnum)
          ps.setLong(7,oraSid.toLong)
        }
        //自动手动无候选未比中
      } else if(!isHaveCandidate){
        val sql : String = "update normalquery_queryque t " +
          "set t.ora_updatetime = sysdate, " +
          "t.ora_updator        = '@match+server@', " +
          "t.finishtime         = sysdate, " +
          "t.checktime          = sysdate, " +
          "t.checkusername      = '$autocheck$', " +
          "t.verifyresult       = '1'," +
          "t.status             = '7'," +
          "t.checkerunitcode    = ?, " +
          "t.candhead           = ?, " +
          "t.candlist           = ?, " +
          "t.curcandnum         = ?, " +
          "t.hitpossibility     = ?, " +
          "t.maxcandnum         = ?, " +
          "t.flagg              = '96' " +
          "where t.ora_sid          = ? "

        JdbcDatabase.update(sql) { ps =>
          ps.setString(1,userunitcode)
          ps.setBytes(2, candHead)
          ps.setBytes(3, candList)
          ps.setInt(4, candNum)
          ps.setInt(5, maxScore)
          ps.setLong(6, maxcandnum)
          ps.setLong(7,oraSid.toLong)
        }
      } else {
        val sql = "update NORMALQUERY_QUERYQUE t set t.status=2, t.curcandnum=?, t.candhead=?, t.candlist=?, t.hitpossibility=?, t.FINISHTIME=sysdate where t.ora_sid =?"
        JdbcDatabase.update(sql) { ps =>
          ps.setInt(1, candNum)
          ps.setBytes(2, candHead)
          ps.setBytes(3, candList)
          ps.setInt(4, maxScore)
          ps.setLong(5, oraSid.toLong)
        }
      }
    } else {
      val sql = "update NORMALQUERY_QUERYQUE t set t.status=2, t.curcandnum=?, t.candhead=?, t.candlist=?, t.hitpossibility=?, t.FINISHTIME=sysdate where t.ora_sid =?"
      JdbcDatabase.update(sql) { ps =>
        ps.setInt(1, candNum)
        ps.setBytes(2, candHead)
        ps.setBytes(3, candList)
        ps.setInt(4, maxScore)
        ps.setLong(5, oraSid.toLong)
      }
    }
  }

  //TODO dbid要遍历数据库查找
  private def convertMatchResult2CandList(matchResult: MatchResult, queryType: Int, candDBIDMap: Map[String, Short] = Map(), configMap : scala.collection.mutable.HashMap[String, String]): scala.collection.mutable.HashMap[String, Any] ={
    val resultMap = new scala.collection.mutable.HashMap[String,Any]
    var isChecked = false
    val result = new ByteArrayOutputStream()
    val candIter = matchResult.getCandidateResultList.iterator()
    var isHaveCandidate = false
    if(matchResult.getCandidateResultList.size() > 0){
      isHaveCandidate = true
    }
    var index = 0 //比对排名
    while (candIter.hasNext) {
      index += 1
      val cand = candIter.next()
      val gCand = new GAQUERYCANDSTRUCT
      gCand.nScore = cand.getScore
      gCand.szKey = cand.getObjectId
      //根据候选卡号获取dbid，如果没有使用默认库dbid
      if(candDBIDMap.nonEmpty && candDBIDMap.get(cand.getObjectId).nonEmpty){
        gCand.nDBID = candDBIDMap.get(cand.getObjectId).get
      }else{
        gCand.nDBID = if (queryType == QueryConstants.QUERY_TYPE_TT || queryType == QueryConstants.QUERY_TYPE_TL) 1 else 2
      }
      gCand.nTableID = 2
      gCand.nIndex = cand.getPos.toByte
      gCand.tFinishTime = new AFISDateTime
      gCand.nStepOneRank = index
      val gCandArray: Array[Byte] = gCand.toByteArray()
      val TTSearchQryAutoCheckLocal = configMap("TTSearchQryAutoCheckLocal")      //自动认定
      val TTAutoMergeCandScore = configMap("TTAutoMergeCandScore")                //大于此值自动比中
      if(null != TTSearchQryAutoCheckLocal
                && TTSearchQryAutoCheckLocal == "1"
                && queryType == QueryConstants.QUERY_TYPE_TT){
        if(null != TTAutoMergeCandScore && gCand.nScore >= TTAutoMergeCandScore.toInt){
          //复核状态
          gCandArray(48) = 1
          gCandArray(57) = 6
          isChecked = true
        }
      }
      result.write(gCandArray)
    }
    resultMap("candList") = result.toByteArray
    resultMap("isChecked") = isChecked
    resultMap("isHaveCandidate") = isHaveCandidate
    return resultMap
  }



  /**
    * 获取查询信息
    *
    * @param orasid
    * @return
    */
  override def getQueryQue(orasid: Int): QueryQue = {
    // val sql = "select t.keyid, t.querytype, t.flag from NORMALQUERY_QUERYQUE t where t.seq =?"
    val sql = "select t.keyid, t.querytype, t.flag from NORMALQUERY_QUERYQUE t where t.ora_sid =?"
    JdbcDatabase.queryFirst(sql) { ps =>
      //ps.setInt(1, seq)
      ps.setInt(1, orasid)
    } { rs =>
      val keyId = rs.getString("keyid")
      val queryType = rs.getInt("querytype")
      val flag = rs.getInt("flag")
      //new QueryQue(keyId, seq, queryType, if(flag == 2 || flag == 22) true else false)
      new QueryQue(keyId, orasid, queryType, if (flag == 2 || flag == 22) true else false)
    }.get
  }

  /**
    * 根据卡号查找
    *
    * @param matchResult
    * @param queryType
    */
  private def getCardIdDbidMap(matchResult: MatchResult, queryType: Int): Unit = {

  }

  /**
    * 根据queryid获取比对状态
    * @param queryId
    * @param pkId
    * @param typ
    * @return
    */
  override def getMatchStatusByQueryid(queryId: Long, pkId: String, typ: Short): MatchStatus = {
    throw new UnsupportedOperationException
  }

  /**
    * 获取比对状态正在比对任务SID
    *
    * @param size
    * @return
    */
  override def getSidByStatusMatching(size: Int, dbId: Option[String]): Seq[Long] = {
    val sidArr = ArrayBuffer[Long]()
    //val sql = "select t.seq from NORMALQUERY_QUERYQUE t where t.status=1 and rownum <=?"
    val sql = "select t.ora_sid from NORMALQUERY_QUERYQUE t where t.status=1 and rownum <=?"
    JdbcDatabase.queryWithPsSetter(sql) { ps =>
      ps.setInt(1, size)
    } { rs =>
      //sidArr += rs.getLong("seq")
      sidArr += rs.getLong("ora_sid")
    }
    sidArr
  }

  /**
    * 更新Status比对状态
    *
    * @param oraSid
    * @param status
    */
  override def updateMatchStatus(oraSid: Long, status: Int): Unit = {
    //val sql = "update NORMALQUERY_QUERYQUE t set t.status =? where t.seq=?"
    val sql = "update NORMALQUERY_QUERYQUE t set t.status =? where t.ora_sid=?" //更新ora_sid对应的比对任务的状态
    JdbcDatabase.update(sql) { ps =>
      ps.setInt(1, status)
      ps.setLong(2, oraSid)
    }
  }

  /**
    *根据orasid获取对应任务的捺印卡号 keyId
    * @param oraSid
    * @return
    */
  override def getKeyIdArrByOraSid(oraSid: Long): Seq[String] = {
    val keyIdArr = ArrayBuffer[String]()
    val sql = "select t.keyid from NORMALQUERY_QUERYQUE t where t.ora_sid=?"
    JdbcDatabase.queryWithPsSetter(sql) { ps =>
      ps.setLong(1, oraSid)
    } { rs =>
      keyIdArr += rs.getString("keyid")
    }
    keyIdArr
  }

  /**
    *根据orasid获取对应任务的查询类型 queryType
    * @param oraSid
    * @return
    */
  override def getQueryTypeArrByOraSid(oraSid: Long): Seq[String] = {
    val queryTypeArr = ArrayBuffer[String]()
    val sql = "select t.querytype from NORMALQUERY_QUERYQUE t where t.ora_sid=?"
    JdbcDatabase.queryWithPsSetter(sql) { ps =>
      ps.setLong(1, oraSid)
    } { rs =>
      queryTypeArr += rs.getShort("querytype").toString
    }
    queryTypeArr
  }
  
   /**
    * 获得配置信息
    */
  override def getAfisinitConfig() : scala.collection.mutable.HashMap[String, String] = {
    val configMap = new scala.collection.mutable.HashMap[String, String]
    val sql = "select * from ADMIN_AFISINIT where key in (?,?) "
    JdbcDatabase.queryWithPsSetter(sql){ps=>
      ps.setString(1,"TTSearchQryAutoCheckLocal")
      ps.setString(2,"TTAutoMergeCandScore")
    }{rs=>
      val key = rs.getString("key")
      val value = rs.getString("value")
      configMap(key) = value
    }
    return configMap
  }

  /**
    *获得Ora_UUID 比对任务对应的唯一标识
    * @param oraSid
    * @return
    */
  def getOraUUID(oraSid: Long): Seq[String]={
    val OraUUIDArr = ArrayBuffer[String]()
    val sql = "select t.ora_uuid from NORMALQUERY_QUERYQUE t where t.ora_sid=?"
    JdbcDatabase.queryWithPsSetter(sql) { ps =>
      ps.setLong(1, oraSid)
    } { rs =>
      OraUUIDArr += rs.getString("ora_uuid")
    }
    OraUUIDArr
  }

  /**
    * 保存抓取记录
    * @param ORA_UUID
    */
  def saveFetchRecord(ORA_UUID: String)={
    val sql ="insert into HALL_FETCH_RECORD_7TO6 ORA_UUID  values(?)"
    JdbcDatabase.update(sql) { ps =>
      ps.setString(1, ORA_UUID)
    }
  }

}


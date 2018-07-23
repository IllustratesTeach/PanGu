package nirvana.hall.webservice.internal.haixin.v70

import java.util
import java.util.UUID
import javax.activation.DataHandler
import javax.sql.DataSource

import nirvana.hall.api.internal.ExceptionUtil
import nirvana.hall.c.services.gfpt4lib.FPT4File.FPT4File
import nirvana.hall.c.services.gfpt4lib.FPTFile
import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.webservice.internal.haixin.{CommonUtils, IAConstant}
import nirvana.hall.webservice.internal.haixin.exception.CustomException._
import nirvana.hall.webservice.services.haixin.StrategyService
import org.apache.commons.io.IOUtils
import org.apache.commons.lang.StringUtils
import org.springframework.transaction.annotation.Transactional

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Created by yuchen on 2017/7/25.
  */
class StrategyServiceImpl(implicit dataSource: DataSource) extends StrategyService{

  /**
    * 检验传入的必填的入参是否全部传入
    */
  override def inputParamIsNullOrEmpty(paramMap: mutable.HashMap[String, Any]): Unit = {

    paramMap.foreach(
      t =>
        if(t._2.isInstanceOf[String]){
          if(StringUtils.isEmpty(t._2.asInstanceOf[String]) ||StringUtils.isBlank(t._2.asInstanceOf[String])){
            throw new InputParamIsNullOrEmptyException("入参" + t._1 + "为空或空串;当前传入为:" + t._2)
          }
        }else{
          if(null == t._2){
            throw new InputParamIsNullOrEmptyException("入参" + t._1 + "为空或空串;当前传入为:" + t._2)
          }
        }
    )
  }

  /**
    * 判断用户是否合法
    *
    * @param userId
    */
override def checkUserIsVaild(userId: String, unitCode: String): Unit = {
    val sql = s"SELECT count(1) num " +
      s"FROM HALL_GAFIS_IA_USER t " +
      s"WHERE t.userid = ? AND t.Unitcode = ? AND t.deleteflag = 1"
    JdbcDatabase.queryWithPsSetter2(sql){ps=>
      ps.setString(1,userId)
      ps.setString(2,unitCode)
    }{rs=>
      while (rs.next()){
        if(rs.getString("num").toInt <= 0){
          throw new UserIsNotVaildException("传入的用户名或单位代码无效,当前传入为:" + userId + "|" + unitCode)
        }
      }
    }
  }

  /**
    * 检查当前传入的捺印指纹是否存在
    *
    * @param personId
    * @param bussType
    * @return
    */
override def checkFingerCardIsExist(personId: String, bussType: Int): Unit = {
    var count = 0
    val sql = s"SELECT  count(1) num  " +
              s"FROM  HALL_GAFIS_IA_FINGER  t " +
              s"WHERE t.response_status = 1 AND t.OPER_TYPE = 1 " +
              s"AND   t.personid = ?"
    JdbcDatabase.queryWithPsSetter2(sql){ps=>
      ps.setString(1,personId)
    }{rs=>
      while (rs.next()){
        count = rs.getString("num").toInt
      }

    }

    bussType match {
      case IAConstant.SET_FINGER =>
        if(count > 0){
          throw new FingerExistException("人员编号为" + personId + "的卡已经存在,不能再次添加")
        }
      case IAConstant.SET_FINGER_AGAIN |
           IAConstant.GET_FINGER_STATUS |
           IAConstant.GET_FINGER_MATCH_DATA =>
        if(count <= 0){
          throw new FingerNotExistException("人员编号为" + personId + "的卡在本系统中不存在")
        }
    }
  }

  /**
    * 检验来源是否合法
    */
  override def checkCollectSrcIsVaild(collectSrc: String): Unit = {
    val sql = s"SELECT t.id  " +
      s"FROM HALL_GAFIS_IA_COLLECTSRC t " +
      s"WHERE t.parentid = 1700 AND t.id = 1701 AND deleteflag = 1 "
    JdbcDatabase.queryWithPsSetter2(sql){ps=>
    }{rs=>
      while (rs.next()){
        if(!rs.getString("id").equals(collectSrc)){
          throw new CollectSrcIsNotVaildException("来源不合法:"+collectSrc)
        }
      }
    }
  }

  /**
    * 检验FPT是否合法
    *
    * @param personId
    * @param dataHandler
    * @return
    */
  override def checkFptIsVaild(personId: String, dataHandler: DataHandler): FPT4File = {

    val fpt = FPTFile.parseFromInputStream(dataHandler.getInputStream)
    fpt match {
      case Left(fpt3) => throw new FPTNot4Exception("Only Support FPT-V4.0")
      case Right(fpt4) =>
        fpt4.logic02Recs.foreach{
          t =>
            if(!t.personId.equals(personId)){
              throw new IndoorPersonIdNotEqFPTPersonIdException("传入参数personId与FPT中的personid不一致,当前传入personid为:" + personId)
            }
        }
    }
    fpt.right.get
  }

  /**
    * 处理业务完成的通用方法(指纹)
    */
  @Transactional
  override def fingerBusinessFinishedHandler(uuid:String,collectsrc:String,userid:String
                                             ,unitcode:String,response_status:Int
                                             ,oper_type:Int,personid:String,queryid:String,dataHandler: DataHandler,throwAble:Option[Throwable]): Unit = {

    var indoorReturnValue = IAConstant.SUCCESS
    throwAble match{
      case Some(t) => indoorReturnValue = getIndoorReturnValue(t)
      case _ =>
    }


    val sql_t1 = s"INSERT INTO HALL_GAFIS_IA_FINGER ( UUID" +
                                                s",COLLECTSRC" +
                                                s",USERID" +
                                                s",UNITCODE" +
                                                s",INDOOR_STATUS" +
                                                s",RESPONSE_STATUS" +
                                                s",OPER_TYPE" +
                                                s",PERSONID" +
                                                s",QUERYID" +
                                                s",CREATETIME" +
                                                s",MATCHSTATUS )" +
              s" VALUES(?,?,?,?,?,?,?,?,?,sysdate,?)"
              JdbcDatabase.update(sql_t1){ps=>
                ps.setString(1, uuid)
                ps.setString(2,collectsrc)
                ps.setString(3,userid)
                ps.setString(4,unitcode)
                ps.setInt(5,indoorReturnValue)
                ps.setInt(6,response_status)
                ps.setInt(7,oper_type)
                ps.setString(8,personid)
                ps.setString(9,queryid)
                ps.setInt(10,0)
              }

    if(indoorReturnValue == IAConstant.SUCCESS){
      if(null != dataHandler){
        val sql_t2 = s"INSERT INTO HALL_GAFIS_IA_FINGER_FPT(UUID" +
                                                          s",IA_FINGER_PKID" +
                                                          s",PERSONID" +
                                                          s",FPT) VALUES(?,?,?,?)"
        JdbcDatabase.update(sql_t2){ps=>
          ps.setString(1, UUID.randomUUID().toString.replace("-",""))
          ps.setString(2,uuid)
          ps.setString(3,personid)
          ps.setBytes(4, IOUtils.toByteArray(dataHandler.getInputStream))
        }
      }
    }else{
      val sql_t3 = s"INSERT INTO HALL_GAFIS_IA_FINGER_EXCEPTION(UUID,IA_FINGER_PKID,EXCEPTIONINFO) VALUES(?,?,?)"
      JdbcDatabase.update(sql_t3){ps=>
        ps.setString(1, UUID.randomUUID().toString.replace("-",""))
        ps.setString(2,uuid)
        ps.setString(3,ExceptionUtil.getStackTraceInfo(throwAble.get)
          + "&&" + throwAble.get.fillInStackTrace
          + "&&" + throwAble.get.getLocalizedMessage)
      }
    }
  }

  /**
    * 记录自动发查重任务后本地系统生成的任务号orasid和远程任务号queryid
    *
    * @param pkId
    * @param oraSid
    * @param queryId
    */
  override def recordAutoSendQueryOraSidAndQueryIdWithTT(pkId: String, oraSid: Long, queryId: String,queryType:Int): Unit = {
    val sql = s"INSERT INTO HALL_GAFIS_IA_FINGER_QUERY (UUID,IA_FINGER_PKID,ORASID,QUERYID,QUERYTYPE,CREATETIME) " +
                s"VALUES (?,?,?,?,?,sysdate)"
    JdbcDatabase.update(sql){ps=>
      ps.setString(1, UUID.randomUUID().toString.replace("-",""))
      ps.setString(2,pkId)
      ps.setLong(3,oraSid)
      ps.setString(4, queryId)
      ps.setInt(5,queryType)
    }
  }

  /**
    * 通过personid获得该卡号所对应的任务状态7.0
    *
    * @param personId
    * @return
    */
  override def getResponseStatusAndOrasidByPersonId(personId: String): mutable.HashMap[String,Any] = {

    var resultMap = new scala.collection.mutable.HashMap[String,Any]
    val sql = s"SELECT t.response_status" +
      s",k.orasid " +
      s"FROM HALL_GAFIS_IA_FINGER t " +
      s"LEFT JOIN HALL_GAFIS_IA_FINGER_QUERY k " +
      s"ON t.uuid = k.ia_finger_pkid " +
      s"WHERE t.personid = ? AND t.response_status = 1 AND t.OPER_TYPE = 1 "
    JdbcDatabase.queryWithPsSetter2(sql){ps=>
      ps.setString(1, personId)
    }{rs=>
      while(rs.next){
        resultMap += ("response_status" -> rs.getInt("response_status"))
        resultMap += ("orasid" -> rs.getLong("orasid"))
      }
    }
    resultMap
  }

  /**
    * 根据6.2返回的查询状态获得应该返回给调用客户端的状态
    *
    * @param status
    * @return
    */
  override def getResponseStatusByGafisStatus(status: Long): Int = {

    val result = status match {

      case IAConstant.WAITING_MATCH =>

        IAConstant.CREATE_STORE_SUCCESS

      case IAConstant.MATCHING
           | IAConstant.WAITING_CHECK
           | IAConstant.CHECKING
           | IAConstant.CHECKED
           | IAConstant.WAITING_RECHECK
           | IAConstant.RECHECKING =>

        IAConstant.HANDLE_ING

      case IAConstant.RECHECKED =>
        IAConstant.HITED

      case other =>
        IAConstant.CREATE_STORE_SUCCESS
    }
    result
  }


  /**
    * 根据6.2返回的查询状态获得应该返回给调用客户端的状态(TT)
    *
    * @param status
    * @return
    */
  override def getResponseStatusByGafisStatus_TT(status: Long): Int = {
    val result = status match {

      case IAConstant.WAITING_MATCH =>

        IAConstant.CREATE_STORE_SUCCESS

      case IAConstant.MATCHING
           | IAConstant.WAITING_CHECK
           | IAConstant.CHECKING =>

        IAConstant.HANDLE_ING

      case IAConstant.CHECKED =>
        IAConstant.HITED

      case other =>
        IAConstant.CREATE_STORE_SUCCESS
    }
    result
  }

  /**
    * 获得卡号，用于自动发查询 TT
    *
    * @return
    */
  override def getPersonIdUseBySendQuery(batchSize:Int): Option[ListBuffer[mutable.HashMap[String,Any]]] = {

    val listBuffer = new mutable.ListBuffer[mutable.HashMap[String,Any]]
    val sql = s"SELECT t.uuid,t.personid,t.queryid " +
              s"FROM HALL_GAFIS_IA_FINGER t " +
              s"WHERE t.response_status = 1 AND t.OPER_TYPE = 1 AND t.matchstatus = 0 AND  ROWNUM < ?"
    JdbcDatabase.queryWithPsSetter2(sql){ps=>
      ps.setInt(1,batchSize)
    }{rs=>
      while (rs.next()){
        val mapBuffer = new scala.collection.mutable.HashMap[String,Any]
        mapBuffer += ("uuid" -> rs.getString("uuid"))
        mapBuffer += ("personid" -> rs.getString("personid"))
        mapBuffer += ("queryid" -> rs.getLong("queryid"))
        listBuffer.append(mapBuffer)
      }
    }
    Some(listBuffer)
  }

  /**
    * 记录发查询是出现的异常信息
    *
    * @param pkid
    * @param exceptionInfo
    */
  override def sendQueryExceptionHandler(pkid: String, exceptionInfo: String): Unit = {
    val sql = s"INSERT INTO HALL_GAFIS_IA_FINGER_QUE_EXC (UUID,IA_FINGER_PKID,EXCEPTIONINFO,CREATETIME) " +
      s"VALUES (?,?,?,sysdate)"
    JdbcDatabase.update(sql){ps=>
      ps.setString(1, UUID.randomUUID().toString.replace("-",""))
      ps.setString(2,pkid)
      ps.setString(3,exceptionInfo)
    }
  }

  /**
    * 发查询后，将FINGER_IA表中的matchstatus置位
    *
    * @param uuid
    * @param status
    */
  override def setSendQueryStatus(uuid: String, status: Int): Unit = {
    val sql = s"UPDATE HALL_GAFIS_IA_FINGER t SET t.matchstatus = ? WHERE t.uuid = ?"
    JdbcDatabase.update(sql){ps=>
      ps.setInt(1, status)
      ps.setString(2,uuid)
    }
  }

  /**
    * 获得比中列表 7.0
    *
    * @param timefrom 开始时间(可选)
    * @param timeto   结束时间(可选)
    * @param startnum 起始序号(默认1)
    * @param endnum   结束序号(默认10)
    */
  override def getHitList(timefrom: String, timeto: String, startnum: Int = 1, endnum: Int = 10): Option[util.ArrayList[String]] = {

    val listArray = new util.ArrayList[String]
    val sql = new StringBuilder

    sql ++= s"SELECT personid FROM "+
                 s" ( SELECT ROWNUM AS rowno,w.personid FROM"+
                     s" GAFIS_CHECKIN_INFO i ,"+
                     s" (SELECT p.personid AS cardid,m.personid"+
                         s" FROM GAFIS_PERSON p,HALL_GAFIS_IA_FINGER m"+
                         s" WHERE p.personid = m.personid AND m.matchstatus = '1' AND m.OPER_TYPE = '1' ) w ,"+
                     s" GAFIS_NORMALQUERY_QUERYQUE q"+
                     s" WHERE q.keyid = w.cardid AND q.pk_id = i.query_uuid AND i.confirm_status = 98 AND (i.querytype=0 OR i.querytype = 1) AND q.deletag = '1'"

    if(!CommonUtils.isNullOrEmpty(timefrom) && !CommonUtils.isNullOrEmpty(timeto)){
      sql ++= s" AND i.confirm_time >= to_date('" + timefrom + "', 'yyyy-MM-dd hh24:mi:ss') " +
        s"AND i.confirm_time <= to_date('" + timeto + "',  'yyyy-MM-dd hh24:mi:ss') "
    }

    sql ++= s" ) WHERE ROWNO >= ?  AND   ROWNO <= ?  "

    JdbcDatabase.queryWithPsSetter2(sql.toString){ps=>
      ps.setInt(1,startnum)
      ps.setInt(2,endnum)
    }{rs=>
      while (rs.next()){
        listArray.add(rs.getString("personid"))
      }
    }

    Some(listArray)
  }

  /**
    * 获得比中数量 7.0
    *
    * @param timefrom 开始时间(可选)
    * @param timeto   结束时间(可选)
    */
  override def getHitCount(timefrom: String, timeto: String): Int = {
    var count = 0
    val sql = new StringBuilder

    sql ++= s"SELECT COUNT(1) NUM FROM " +
                s" GAFIS_CHECKIN_INFO i " +
                s",(SELECT p.personid AS cardid,m.personid " +
                      s"FROM GAFIS_PERSON p,HALL_GAFIS_IA_FINGER m " +
                      s"WHERE p.personid = m.personid AND m.matchstatus = '1' AND m.OPER_TYPE = '1') w " +
                s",GAFIS_NORMALQUERY_QUERYQUE q "+
            s" WHERE q.keyid = w.cardid  AND q.pk_id = i.query_uuid AND i.confirm_status = 98 AND (i.querytype=0 OR i.querytype = 1) AND q.deletag = '1' "

    if(!CommonUtils.isNullOrEmpty(timefrom) && !CommonUtils.isNullOrEmpty(timeto)){
      sql ++= s" AND i.confirm_time >= to_date('" + timefrom + "', 'yyyy-MM-dd hh24:mi:ss') " +
        s"AND i.confirm_time <= to_date('" + timeto + "',  'yyyy-MM-dd hh24:mi:ss') "
    }

    JdbcDatabase.queryWithPsSetter2(sql.toString){ps=>
    }{rs=>
      while (rs.next()){
        count = rs.getString("NUM").toInt
      }
    }

    count
  }

  /**
    * 通过personid获得综采对接系统中已经发过查询的Orasid和queryId
    *
    * @param personId
    * @return
    */
  override def getOraSidAndQueryIdByPersonId(personId: String): Option[ListBuffer[mutable.HashMap[String,Any]]] = {

    val listBuffer = new mutable.ListBuffer[mutable.HashMap[String,Any]]

    val sql = s"SELECT t.ora_sid,t.pk_id pkid " +
              s"FROM GAFIS_NORMALQUERY_QUERYQUE t " +
              s"WHERE t.keyid = ?"

    JdbcDatabase.queryWithPsSetter2(sql){ps=>
      ps.setString(1,personId)
    }{rs=>
      while (rs.next()){
        val mapBuffer = new scala.collection.mutable.HashMap[String,Any]
        mapBuffer += ("orasid" -> rs.getString("ora_sid"))
        mapBuffer += ("queryid" -> rs.getString("pkid"))
        listBuffer.append(mapBuffer)
      }
    }

    Some(listBuffer)
  }


  private def getIndoorReturnValue(throwAble:Throwable): Int ={

      var returnValue = IAConstant.UNKNOWN_ERROR
      if(throwAble.isInstanceOf[InputParamIsNullOrEmptyException]){
        returnValue = IAConstant.INPUT_PARAM_IS_EMPTY
      }else if(throwAble.isInstanceOf[CollectSrcIsNotVaildException ]){
        returnValue = IAConstant.COLLECTSRC_NOT_VAILD
      }else if(throwAble.isInstanceOf[UserIsNotVaildException]){
        returnValue = IAConstant.USERID_NOT_VAILD
      }else if(throwAble.isInstanceOf[FingerExistException]){
        returnValue = IAConstant.FINGER_EXISTS
      }else if(throwAble.isInstanceOf[FingerNotExistException]){
        returnValue = IAConstant.FINGER_NOT_EXISTS
      }else if(throwAble.isInstanceOf[FPTNot4Exception]){
        returnValue = IAConstant.FPT_NOT_FOUR_VERSION
      }else if(throwAble.isInstanceOf[IndoorPersonIdNotEqFPTPersonIdException]){
        returnValue = IAConstant.INDOOR_NOT_EQ_OUTDOOR
      }else if(throwAble.isInstanceOf[Exception]){
        returnValue = IAConstant.UNKNOWN_ERROR
      }
    returnValue
  }

  /**
    * 检查当前传入的捺印掌纹是否存在
    *
    * @param palmid
    * @param palmtype
    * @param bussType
    * @return
    */
  override def checkPalmIsExist(palmid: String, palmtype: Int, bussType: Int): Unit = {
    var count = 0
    val sql = s"SELECT  count(1) num  " +
      s"FROM  HALL_GAFIS_IA_PALM  t " +
      s"WHERE t.response_status = 1 AND t.OPER_TYPE = 8 " +
      s"AND   t.palmid = ?   AND  t.palmfgp = ?"
    JdbcDatabase.queryWithPsSetter2(sql){ps=>
      ps.setString(1,palmid)
      ps.setInt(2,palmtype)
    }{rs=>
      while (rs.next()){
        count = rs.getString("num").toInt
      }
    }
    bussType match {
      case IAConstant.SET_PALM =>
        if(count > 0){
          throw new FingerExistException("掌纹编号为" + palmid + "的卡已经存在,不能再次添加")
        }
      case IAConstant.GET_PALM_STATUS |
           IAConstant.SET_PALM_AGAIN =>
        if(count <= 0){
          throw new FingerNotExistException("掌纹编号为" + palmid + "的卡在本系统中不存在")
        }
    }
  }

  /**
    * 处理业务完成的通用方法(掌纹)
    *
    * @param uuid            全球唯一编码，程序自动生成；
    * @param collectsrc      请求方来源；
    * @param userid          用户id
    * @param unitcode        用户id所属的单位代码
    * @param response_status 返回给客户端的程序执行状态
    * @param oper_type       操作类型，区分是添加还是修改还是删除还是查询
    * @param palmid          请求方掌纹编号
    * @param palmtype        公安部掌纹部位代码
    * @param personid        公安部标准的23位唯一码，人员编号
    * @param dataHandler     承载FPT的一种数据类型
    * @param throwAble       异常对象
    */
  override def palmBusinessFinishedHandler(uuid: String, collectsrc: String, userid: String, unitcode: String, response_status: Int, oper_type: Int, palmid: String, palmtype: Int, personid: String, dataHandler: Array[Byte], throwAble: Option[Throwable]): Unit = {
    var indoorReturnValue = IAConstant.SUCCESS
    throwAble match{
      case Some(t) => indoorReturnValue = getIndoorReturnValue(t)
      case _ =>
    }

    val sql_t1 = s"INSERT INTO HALL_GAFIS_IA_PALM ( UUID" +
      s",COLLECTSRC" +
      s",USERID" +
      s",UNITCODE" +
      s",INDOOR_STATUS" +
      s",RESPONSE_STATUS" +
      s",OPER_TYPE" +
      s",PERSONID" +
      s",PALMID" +
      s",PALMFGP" +
      s",CREATETIME" +
      s",MATCHSTATUS )" +
      s" VALUES(?,?,?,?,?,?,?,?,?,?,sysdate,?)"
    JdbcDatabase.update(sql_t1){ps=>
      ps.setString(1, uuid)
      ps.setString(2,collectsrc)
      ps.setString(3,userid)
      ps.setString(4,unitcode)
      ps.setInt(5,indoorReturnValue)
      ps.setInt(6,response_status)
      ps.setInt(7,oper_type)
      ps.setString(8,personid)
      ps.setString(9,palmid)
      ps.setInt(10,palmtype)
      ps.setInt(11,0)
    }

    if(indoorReturnValue == 9){
      if(null != dataHandler){
        val sql_t2 = s"INSERT INTO HALL_GAFIS_IA_PALM_WSQ(UUID" +
          s",IA_PALM_PKID" +
          s",PALMID" +
          s",PERSONID" +
          s",PALMFGP" +
          s",WSQ) VALUES(?,?,?,?,?,?)"
        JdbcDatabase.update(sql_t2){ps=>
          ps.setString(1, UUID.randomUUID().toString.replace("-",""))
          ps.setString(2,uuid)
          ps.setString(3,palmid)
          ps.setString(4,personid)
          ps.setInt(5,palmtype)
          ps.setBytes(6, dataHandler)
        }
      }
    }else{
      val sql_t3 = s"INSERT INTO HALL_GAFIS_IA_PALM_EXCEPTION(UUID,IA_PALM_PKID,EXCEPTIONINFO) VALUES(?,?,?)"
      JdbcDatabase.update(sql_t3){ps=>
        ps.setString(1, UUID.randomUUID().toString.replace("-",""))
        ps.setString(2,uuid)
        ps.setString(3,throwAble.get.getMessage)
      }
    }
  }

  override def getResponseStatusAndPlam(palmid: String, palmtype: Int): Int =  {
    var count = 0
    val sql = s"SELECT count(1)  num  " +
      s"FROM HALL_GAFIS_IA_PALM t " +
      s"WHERE t.palmid = ? AND t.palmfgp = ? AND t.response_status = 1 AND t.OPER_TYPE = 8 "
    JdbcDatabase.queryWithPsSetter2(sql){ps=>
      ps.setString(1, palmid)
      ps.setInt(2, palmtype)
    }{rs=>
      while (rs.next()){
        count = rs.getString("num").toInt
      }
    }
    count
  }

  /**
    * 通过personid获得该卡号所对应的任务状态
    * -针对模式:各地市向省厅下载采集的捺印卡信息,查询的是远程的查询的状态
    *
    * @param personId
    * @return
    */
  override def getRemoteResponseStatusAndOrasidByPersonId(personId: String): Option[mutable.HashMap[String, Any]] = ???

  /**
    * 获取错误信息集合
    *
    * @param personid  公安部标准的23位唯一码，人员编号
    * @param oper_type 操作类型
    * @return
    */
  override def getErrorInfoList(userid:String,unitcode:String,personid:String,oper_type : Int)  = ???

  /**
    * 获取人员信息
    *
    * @param idcard 身份证号
    * @return
    */
  override def getPersonInfo(idcard: String) = ???
}

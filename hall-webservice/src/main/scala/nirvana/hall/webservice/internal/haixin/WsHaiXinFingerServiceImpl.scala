package nirvana.hall.webservice.internal.haixin


import java.text.SimpleDateFormat
import java.util
import java.util.{Date, UUID}
import javax.activation.DataHandler

import monad.support.services.{LoggerSupport, XmlLoader}
import nirvana.hall.api.services.{ExceptRelationService, QueryService}
import nirvana.hall.api.services.fpt.FPTService
import nirvana.hall.webservice.internal.haixin.vo.{HitConfig,ListItem}
import nirvana.hall.webservice.services.haixin.{StrategyService, WsHaiXinFingerService}
import org.apache.axiom.attachments.ByteArrayDataSource

import scala.collection.mutable

/**
  * Created by yuchen on 2017/7/24.
  */
class WsHaiXinFingerServiceImpl(strategyService:StrategyService
                                ,fptService: FPTService
                                ,queryService: QueryService
                                ,exceptRelationService:ExceptRelationService) extends WsHaiXinFingerService with LoggerSupport{
  /**
    * 接口01:捺印指纹信息录入
    *
    * @param collectsrc 来源
    * @param userid     用户id
    * @param unitcode   单位代码
    * @param personid   公安部标准的23位唯一码，人员编号
    * @param dh         FPT载体
    * @return 1: 成功加入队列 0: 失败
    */
  override def setFinger(collectsrc: String, userid: String, unitcode: String, personid: String, dh: DataHandler): Int = {

    var result = IAConstant.ADD_QUEUE_FAIL
    val uuid = UUID.randomUUID().toString.replace("-",IAConstant.EMPTY)
    var queryId :Option[String]= None
    try{
      val paramMap = new scala.collection.mutable.HashMap[String,Any]
      paramMap.put("collectsrc",collectsrc)
      paramMap.put("userid",userid)
      paramMap.put("unitcode",unitcode)
      paramMap.put("personid",personid)
      paramMap.put("dh",dh)

      strategyService.inputParamIsNullOrEmpty(paramMap)
      strategyService.checkCollectSrcIsVaild(collectsrc)
      strategyService.checkUserIsVaild(userid,unitcode)
      strategyService.checkFingerCardIsExist(personid,IAConstant.SET_FINGER)
      val fpt = strategyService.checkFptIsVaild(personid,dh)
      queryId = Some(fpt.sid)
      fpt.logic02Recs.foreach{
        t => fptService.addLogic02Res(t)
      }
      strategyService.fingerBusinessFinishedHandler(uuid,collectsrc,userid,unitcode
        ,IAConstant.ADD_QUEUE_SUCCESS
        ,IAConstant.SET_FINGER,personid,queryId.getOrElse(IAConstant.EMPTY_ORASID.toString),dh,None)

      result = IAConstant.ADD_QUEUE_SUCCESS

    }catch{
      case ex:Throwable =>
        strategyService.fingerBusinessFinishedHandler(uuid,collectsrc,userid,unitcode
          ,IAConstant.ADD_QUEUE_FAIL
          ,IAConstant.SET_FINGER,personid,queryId.getOrElse(IAConstant.EMPTY_ORASID.toString),dh,Some(ex))
        result = IAConstant.ADD_QUEUE_FAIL
    }
    result
  }

  /**
    * 接口02:查询捺印指纹在指纹系统中的状态
    *
    * @param userid   用户id
    * @param unitcode 单位代码
    * @param personid 公安部标准的23位唯一码，人员编号
    * @return 4: 建库失败 3: 处理中 2: 已比中 1: 成功建库 0: 查询失败
    */
  override def getFingerStatus(userid: String, unitcode: String, personid: String): Int = {
    /**
      *指纹业务表与发查询表左联查，取出response_status，orasid
      * if(response_status == 0){
      *    =>4: 建库失败
      * }
      * else if(response_status = 1 && orasid != null){
      *   用orasid调用hall6.2接口，获得状态
      *   3: 处理中
          2: 已比中
      * }else{
      *   =>成功建库
      * }
      * 抛异常时，=>查询失败
      */
    var result = IAConstant.QUERY_FAIL
    val uuid = UUID.randomUUID().toString.replace("-","")
    try{
      val paramMap = new scala.collection.mutable.HashMap[String,Any]
      paramMap.put("userid",userid)
      paramMap.put("unitcode",unitcode)
      paramMap.put("personid",personid)
      strategyService.inputParamIsNullOrEmpty(paramMap)
      strategyService.checkUserIsVaild(userid,unitcode)
      strategyService.checkFingerCardIsExist(personid,IAConstant.GET_FINGER_STATUS)
      val responseStatusAndOraSidMap = strategyService.getResponseStatusAndOrasidByPersonId(personid)
      if(!responseStatusAndOraSidMap.nonEmpty){
        result = IAConstant.CREATE_STORE_FAIL
      }else{
        if(!responseStatusAndOraSidMap.get("orasid").get.toString.equals(IAConstant.EMPTY_ORASID)){
          val status = queryService.getStatusBySid(responseStatusAndOraSidMap.get("orasid").get.asInstanceOf[Long])
          result = strategyService.getResponseStatusByGafisStatus(status)
        }else{
          result = IAConstant.CREATE_STORE_SUCCESS
        }
      }
      strategyService.fingerBusinessFinishedHandler(uuid,IAConstant.EMPTY,userid,unitcode
        ,result,IAConstant.GET_FINGER_STATUS,personid,IAConstant.EMPTY_ORASID.toString,null,None)
    }catch{
      case ex:Throwable =>
        strategyService.fingerBusinessFinishedHandler(uuid,IAConstant.EMPTY,userid,unitcode
          ,IAConstant.QUERY_FAIL
          ,IAConstant.GET_FINGER_STATUS,personid,IAConstant.EMPTY_ORASID.toString,null,Some(ex))
        result = IAConstant.QUERY_FAIL
    }
    result
  }

  /**
    * 接口03:捺印指纹信息更新
    *
    * @param collectsrc 来源
    * @param userid     用户id
    * @param unitcode   单位代码
    * @param personid   公安部标准的23位唯一码，人员编号
    * @param dh         FPT载体
    * @return 1: 成功加入队列 0: 失败
    */
  override def setFingerAgain(collectsrc: String, userid: String, unitcode: String, personid: String, dh: DataHandler): Int = {
    var result = IAConstant.ADD_QUEUE_FAIL
    val uuid = UUID.randomUUID().toString.replace("-","")
    try{
      val paramMap = new scala.collection.mutable.HashMap[String,Any]
      paramMap.put("collectsrc",collectsrc)
      paramMap.put("userid",userid)
      paramMap.put("unitcode",unitcode)
      paramMap.put("personid",personid)
      paramMap.put("dh",dh)

      strategyService.inputParamIsNullOrEmpty(paramMap)
      strategyService.checkCollectSrcIsVaild(collectsrc)
      strategyService.checkUserIsVaild(userid,unitcode)
      strategyService.checkFingerCardIsExist(personid,IAConstant.SET_FINGER_AGAIN)
      val fpt = strategyService.checkFptIsVaild(personid,dh)
      fpt.logic02Recs.foreach{
        t => fptService.updateLogic02Res(t)
      }
      strategyService.fingerBusinessFinishedHandler(uuid,collectsrc,userid,unitcode
        ,IAConstant.ADD_QUEUE_SUCCESS
        ,IAConstant.SET_FINGER_AGAIN,personid,IAConstant.EMPTY_ORASID.toString,dh,None)
      result = IAConstant.ADD_QUEUE_SUCCESS

    }catch{

      case ex:Throwable =>
        strategyService.fingerBusinessFinishedHandler(uuid,collectsrc,userid,unitcode
          ,IAConstant.ADD_QUEUE_FAIL
          ,IAConstant.SET_FINGER_AGAIN,personid,IAConstant.EMPTY_ORASID.toString,dh,Some(ex))
        result = IAConstant.ADD_QUEUE_FAIL
    }
    result
  }

  /**
    * 接口04:获取捺印指纹的比中列表
    *
    * @param userid   用户id
    * @param unitcode 单位代码
    * @param timefrom 起始时间(YYYY/MM/DD HH24:MI:SS) 选填
    * @param timeto   结束时间(YYYY/MM/DD HH24:MI:SS) 选填
    * @param Startnum 记录起始位置编号(默认为1)
    * @param endnum   记录结束位置编号(默认为10）
    * @return XML文件 无信息或异常返回空
    */
  override def getFingerMatchList(userid: String, unitcode: String, timefrom: String, timeto: String, Startnum: Int, endnum: Int): DataHandler = {

    var dataHandler:DataHandler = null

    val uuid = UUID.randomUUID().toString.replace("-","")
    try{
      val paramMap = new scala.collection.mutable.HashMap[String,Any]
      paramMap.put("userid",userid)
      paramMap.put("unitcode",unitcode)

      strategyService.inputParamIsNullOrEmpty(paramMap)
      strategyService.checkUserIsVaild(userid,unitcode)

      val listBuffer = strategyService.getHitList(timefrom,timeto,Startnum,endnum)

      listBuffer match {
        case Some(m) =>

          dataHandler = getXMLFile(m)

          strategyService.fingerBusinessFinishedHandler(uuid,IAConstant.EMPTY
            ,userid,unitcode
            ,IAConstant.SEARCH_SUCCESS
            ,IAConstant.GET_FINGER_MATCH_LIST
            ,IAConstant.EMPTY,IAConstant.EMPTY_ORASID.toString,null,None)

        case _ =>
      }

    }catch{
      case ex:Throwable =>
        strategyService.fingerBusinessFinishedHandler(uuid,IAConstant.EMPTY,userid,unitcode
          ,IAConstant.SEARCH_FAIL,IAConstant.GET_FINGER_MATCH_LIST,IAConstant.EMPTY,IAConstant.EMPTY_ORASID.toString,null,Some(ex))
    }
    dataHandler
  }


  /**
    * 接口05:获取捺印指纹的比中数量
    *
    * @param userid   用户id
    * @param unitcode 单位代码
    * @param timefrom 起始时间(YYYY/MM/DD HH24:MI:SS) 选填
    * @param timeto   结束时间(YYYY/MM/DD HH24:MI:SS) 选填
    * @return n（n>=0）:已录入数据比中个数；（n<0）: 参数或接口异常返回。
    */
  override def getFingerMatchCount(userid: String, unitcode: String, timefrom: String, timeto: String): Int = {

    var count = 0
    val uuid = UUID.randomUUID().toString.replace("-","")
    try{
      val paramMap = new scala.collection.mutable.HashMap[String,Any]
      paramMap.put("userid",userid)
      paramMap.put("unitcode",unitcode)

      strategyService.inputParamIsNullOrEmpty(paramMap)
      strategyService.checkUserIsVaild(userid,unitcode)

      count = strategyService.getHitCount(timefrom,timeto)

      strategyService.fingerBusinessFinishedHandler(uuid,IAConstant.EMPTY
        ,userid,unitcode,IAConstant.SEARCH_SUCCESS
        ,IAConstant.GET_FINGER_MATCH_COUNT
        ,IAConstant.EMPTY,IAConstant.EMPTY_ORASID.toString,null,None)

    }catch{

      case ex:Throwable =>
        strategyService.fingerBusinessFinishedHandler(uuid,IAConstant.EMPTY,userid,unitcode
          ,IAConstant.SEARCH_FAIL,IAConstant.GET_FINGER_MATCH_COUNT,IAConstant.EMPTY,IAConstant.EMPTY_ORASID.toString,null,Some(ex))
    }
    count
  }

  /**
    * 接口06:根据请求方指纹编号(人员编号)获取捺印指纹的比中信息
    *
    * @param userid   用户id
    * @param unitcode 单位代码
    * @param personid 公安部标准的23位唯一码，人员编号
    * @return DataHandler 比中关系的FPT文件
    */
  override def getFingerMatchData(userid: String, unitcode: String, personid: String): mutable.ListBuffer[DataHandler] = {

    val listDataHandler = new mutable.ListBuffer[DataHandler]

    val uuid = UUID.randomUUID().toString.replace("-","")
    try{
      val paramMap = new scala.collection.mutable.HashMap[String,Any]
      paramMap.put("userid",userid)
      paramMap.put("unitcode",unitcode)
      paramMap.put("personid",personid)

      strategyService.inputParamIsNullOrEmpty(paramMap)
      strategyService.checkUserIsVaild(userid,unitcode)
      strategyService.checkFingerCardIsExist(personid,IAConstant.GET_FINGER_MATCH_DATA)

      val listMapBuffer = strategyService.getOraSidAndQueryIdByPersonId(personid)

      listMapBuffer match {
        case Some(m) => m.foreach{ t =>
          listDataHandler.+=(exceptRelationService.exportMatchRelation(t.get("queryid").get.toString
            ,t.get("orasid").get.toString))
        }

        case _ =>
      }


      strategyService.fingerBusinessFinishedHandler(uuid,IAConstant.EMPTY
        ,userid,unitcode,IAConstant.SEARCH_SUCCESS
        ,IAConstant.GET_FINGER_MATCH_DATA
        ,IAConstant.EMPTY,IAConstant.EMPTY_ORASID.toString,null,None)


    }catch{

      case ex:Throwable =>

      strategyService.fingerBusinessFinishedHandler(uuid,IAConstant.EMPTY,userid,unitcode
        ,IAConstant.SEARCH_FAIL,IAConstant.GET_FINGER_MATCH_DATA,IAConstant.EMPTY,IAConstant.EMPTY_ORASID.toString,null,Some(ex))
    }
    listDataHandler
  }

  /**
    * 接口07:获取指纹系统服务器时间
    *
    * @return String 指纹系统服务器时间 (YYYY/MM/DD HH24:MI:SS),接口异常返回空。
    */
  override def getSysTime(): String = {
    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date)
  }


  private def getXMLFile(listItem:util.ArrayList[String]): DataHandler ={

    val list = new ListItem
    list.Item = listItem
    list.itemCnt = listItem.size
    val hitConfig = new HitConfig
    hitConfig.List = list

    new DataHandler(new ByteArrayDataSource(XmlLoader.toXml(hitConfig).getBytes))
  }


}

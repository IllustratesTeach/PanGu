package nirvana.hall.webservice.services.survey.gz

import java.sql.Timestamp

import nirvana.hall.webservice.internal.survey.gz.vo.TimeConfig

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Created by ssj on 2017/11/16.
  */
trait SurveyRecordService {

  /**
    * 根据时间判断是否应该休眠
    * @return
    */
  def isSleep(haiXinServerTime:Long ,startTime: Long ,increments : String):(Boolean,TimeConfig)

  /**
    * 获取开始时间，时间间隔，行政区划代码列表
    */
  def getSurveyConfig() : ListBuffer[mutable.HashMap[String,Any]]

  /**
    * 更新现勘时间配置表 开始时间字段
    *
    * @param endTime
    * @return
    */
  def updateSurveyConfig(endTime : Timestamp ,unitcode :String): Unit

  /**
    * 判断现场物证编号再现勘记录表中是否存在
    * @param xcwzbh
    * @return
    */
  def isSurvey(xcwzbh:String): Boolean

  /**
    * 存入现勘现场数据入记录表操作
    * @param casename
    * @param kno
    * @param xcwzbh
    */
  def saveSurveyRecord(casename:String,kno:String,xcwzbh:String): Unit

  /**
    * 存储调用接口后的请求和返回入库操作
    * @param interfacetype
    * @param kno
    * @param xcwzbh
    * @param requestmsg
    * @param responsemsg
    * @param error
    */
  def saveSurveyLogRecord(interfacetype: String,kno: String,xcwzbh: String,requestmsg: String,responsemsg: String,error: String): Unit

  /**
    * 根据状态，获取不同的现勘号业务
    * @param state
    * @return
    */
  def getSurveyRecordbyState(state: Int,batchSize:Int) : ListBuffer[String]

  /**
    * 根据现勘号查询对应的现勘现场数据
    * @param kno
    */
  def getWZBHbyKno(kno: String):  ListBuffer[mutable.HashMap[String,Any]]

  /**
    * 更新现勘现场数据状态，根据uuid
    * @param state
    * @param xcwzbh
    */
  def updateRecordStateByXCWZBH(state: Int, xcwzbh : String): Unit

  /**
    * 更新现勘现场数据状态，根据kno
    * @param state
    * @param kno
    */
  def updateSurveyRecordStateByKno(state: Int, kno : String): Unit

  /**
    * 更新现勘现场数据接警编号状态，根据kno
    * @param receptionno_state
    * @param kno
    */
  def updateRecordStateByKno(receptionno_state: Int,kno : String): Unit

  /**
    * 增加palm路径到记录表中
    * @param kno
    * @param sno
    * @param path
    */
  def savePalmpath(kno: String,sno: String,path: String): Unit

  /**
    * 增加接警编号到case表 中
    */
  def updateCasePeception(receptionid: String,kno: String): Unit

  /**
    * 获取现勘比中数据列表
    *
    * @return
    */
  def getSurveyHit(batchSize: Int): ListBuffer[mutable.HashMap[String,Any]]

  /**
    * 更新比中关系列表状态，根据orasid
    */
  def updateSurveyHitState(state: String, uuid : String): Unit
}

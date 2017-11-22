package nirvana.hall.webservice.services.survey.gz

import java.sql.Timestamp
import nirvana.hall.webservice.internal.survey.gz.vo.{ TimeConfig}

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
  def isSleep(haiXinServerTime:Long):(Boolean,TimeConfig)

  /**
    * 更新现勘时间配置表 开始时间字段
    *
    * @param endTime
    * @return
    */
  def updateSurveyConfig(endTime : Timestamp): Unit

  /**
    * 存入现勘现场数据入记录表操作
    * @param kno
    * @param sno
    * @param cardtype
    * @param casename
    */
  def saveSurveySnoRecord(kno:String,sno:String,cardtype:String,casename:String): Unit

  /**
    * 判断现勘号再现勘记录表中是否存在
    * @param kno
    * @return
    */
  def isKno(kno:String): Boolean

  /**
    * 存储现勘号记录到现勘记录表中
    * @param kno
    */
  def saveSurveyKnoRecord(kno:String): Unit

  /**
    * 存储调用接口后的请求和返回入库操作
    * @param interfacetype
    * @param kno
    * @param Sno
    * @param requestmsg
    * @param responsemsg
    * @param error
    */
  def saveSurveyLogRecord(interfacetype: String,kno: String,Sno: String,requestmsg: String,responsemsg: String,error: String): Unit

  /**
    * 根据状态，获取不同的现勘号业务
    * @param state
    * @return
    */
  def getXkcodebyState(state: Int,batchSize:Int) : ListBuffer[String]

  /**
    * 更新现勘表状态根据kno
    * @param state
    * @param kno
    */
  def updateXkcodeState(state: Int,kno : String): Unit

  /**
    * 根据现勘号查询对应的现勘现场数据
    * @param kno
    */
  def getSurveySnoRecord(kno: String):  ListBuffer[mutable.HashMap[String,Any]]

  /**
    * 更新现勘现场数据状态，根据sno
    * @param state
    * @param sno
    */
  def updateSnoState(state: Int,sno : String): Unit



  /**
    * 增加接警编号到case表 中
    */
  def updateCasePeception(peception: String,kno: String): Unit
}

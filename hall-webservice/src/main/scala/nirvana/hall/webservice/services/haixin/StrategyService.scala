package nirvana.hall.webservice.services.haixin


import java.util
import javax.activation.DataHandler

import nirvana.hall.c.services.gfpt4lib.FPT4File.FPT4File

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Created by yuchen on 2017/7/25.
  */
trait StrategyService {

  /**
    * 检验传入的必填的入参是否全部传入
    */
  def inputParamIsNullOrEmpty(paramMap:scala.collection.mutable.HashMap[String,Any]): Unit

  /**
    * 检验来源是否合法
    */
  def checkCollectSrcIsVaild(collectSrc:String): Unit

  /**
    * 判断用户是否合法
    * @param userId
    */
  def checkUserIsVaild(userId:String,unitCode:String): Unit

  /**
    * 检查当前传入的捺印指纹是否存在
    * @param personId
    * @param bussType
    * @return
    */
  def checkFingerCardIsExist(personId:String,bussType:Int): Unit

  /**
    * 检验FPT是否合法
    * @param personId
    * @param dataHandler
    * @return
    */
  def checkFptIsVaild(personId:String,dataHandler: DataHandler): FPT4File

  /**
    * 处理业务完成的通用方法(指纹)
    * @param uuid 全球唯一编码，程序自动生成；
    * @param collectsrc 请求方来源；
    * @param userid 用户id
    * @param unitcode 用户id所属的单位代码
    * @param response_status 返回给客户端的程序执行状态
    * @param oper_type 操作类型，区分是添加还是修改还是删除还是查询
    * @param personid 公安部标准生成的23位的唯一的人员编号
    * @param queryid 远程任务号
    * @param dataHandler 承载FPT的一种数据类型
    * @param throwAble   异常对象
    */
  def fingerBusinessFinishedHandler( uuid:String,collectsrc:String,userid:String
                                     ,unitcode:String,response_status:Int
                                     ,oper_type:Int,personid:String,queryid:String,dataHandler: DataHandler,throwAble:Option[Throwable]):Unit


  /**
    * 记录发查询是出现的异常信息
    * @param pkid
    * @param exceptionInfo
    */
  def sendQueryExceptionHandler(pkid:String,exceptionInfo:String):Unit


  /**
    * 获得卡号，用于自动发查询 TT
    * @return
    */
  def getPersonIdUseBySendQuery(batchSize:Int): Option[ListBuffer[mutable.HashMap[String,Any]]]

  /**
    * 记录自动发查重任务后本地系统生成的任务号orasid和远程任务号queryid
    * @param pkId
    * @param oraSid
    * @param queryId
    */
  def recordAutoSendQueryOraSidAndQueryIdWithTT(pkId:String,oraSid:Long,queryId:String,queryType:Int):Unit

  /**
    * 发查询后，将FINGER_IA表中的matchstatus置位
    * @param uuid
    * @param status
    */
  def setSendQueryStatus(uuid:String,status:Int):Unit

  /**
    * 通过personid获得该卡号所对应的任务状态
    * @param personId
    * @return
    */
  def getResponseStatusAndOrasidByPersonId(personId:String):mutable.HashMap[String,Any]

  /**
    * 根据6.2返回的查询状态获得应该返回给调用客户端的状态
    * @param status
    * @return
    */
  def getResponseStatusByGafisStatus(status:Long):Int

  /**
    * 获得比中列表
    * @param timefrom 开始时间(可选)
    * @param timeto 结束时间(可选)
    * @param startnum 起始序号(默认1)
    * @param endnum 结束序号(默认10)
    */
  def getHitList(timefrom:String,timeto:String,startnum:Int = 1,endnum:Int = 10):Option[util.ArrayList[String]]


  /**
    * 获得比中数量
    * @param timefrom 开始时间(可选)
    * @param timeto 结束时间(可选)
    */
  def getHitCount(timefrom:String,timeto:String):Int


  /**
    * 通过personid获得综采对接系统中已经发过查询的Orasid和queryId
    * @param personId
    * @return
    */
  def getOraSidAndQueryIdByPersonId(personId:String): Option[ListBuffer[mutable.HashMap[String,Any]]]

  /**
    * 检查当前传入的捺印掌纹是否存在
    * @param palmid
    * @param palmtype
    * @param bussType
    * @return
    */
  def checkPalmIsExist(palmid:String,palmtype:Int,bussType:Int): Unit

  /**
    * 处理业务完成的通用方法(掌纹)
    * @param uuid 全球唯一编码，程序自动生成；
    * @param collectsrc 请求方来源；
    * @param userid 用户id
    * @param unitcode 用户id所属的单位代码
    * @param response_status 返回给客户端的程序执行状态
    * @param oper_type 操作类型，区分是添加还是修改还是删除还是查询
    * @param palmid 请求方掌纹编号
    * @param palmtype 公安部掌纹部位代码
    * @param personid 公安部标准的23位唯一码，人员编号
    * @param dataHandler 承载FPT的一种数据类型
    * @param throwAble   异常对象
    */
  def palmBusinessFinishedHandler( uuid:String,collectsrc:String,userid:String
                                     ,unitcode:String,response_status:Int
                                     ,oper_type:Int,palmid:String,palmtype:Int,personid:String,dataHandler: Array[Byte],throwAble:Option[Throwable]):Unit

  def getResponseStatusAndPlam(palmid:String,palmtype:Int): Int
}

package nirvana.hall.webservice.services.haixin

import java.util
import javax.activation.DataHandler
import javax.jws.{WebMethod, WebParam, WebService}


/**
  * Created by yuchen on 2017/7/24.
  */
@WebService(serviceName = "WsHaiXinFingerService", targetNamespace = "http://www.gafis.com")
trait WsHaiXinFingerService {

  /**
    * 接口01:捺印指纹信息录入
    * @param collectsrc 来源
    * @param userid 用户id
    * @param unitcode 单位代码
    * @param personid 公安部标准的23位唯一码，人员编号
    * @param dh FPT载体
    * @return 1: 成功加入队列 0: 失败
    */
  @WebMethod def setFinger(  @WebParam(name="collectsrc") collectsrc:String
                            ,@WebParam(name="userid") userid:String
                            ,@WebParam(name="unitcode") unitcode:String
                            ,@WebParam(name="personid") personid:String
                            ,@WebParam(name="dh") dh:DataHandler):Int

  /**
    * 接口02:查询捺印指纹在指纹系统中的状态
    * @param userid 用户id
    * @param unitcode 单位代码
    * @param personid 公安部标准的23位唯一码，人员编号
    * @return 4: 建库失败 3: 处理中 2: 已比中 1: 成功建库 0: 查询失败
    */
  @WebMethod def getFingerStatus( @WebParam(name="userid") userid:String
                                 ,@WebParam(name="unitcode") unitcode:String
                                 ,@WebParam(name="personid") personid:String):Int

  /**
    * 接口03:捺印指纹信息更新
    * @param collectsrc 来源
    * @param userid 用户id
    * @param unitcode 单位代码
    * @param personid 公安部标准的23位唯一码，人员编号
    * @param dh FPT载体
    * @return 1: 成功加入队列 0: 失败
    */
  @WebMethod def setFingerAgain(@WebParam(name="collectsrc") collectsrc:String
                                ,@WebParam(name="userid") userid:String
                                ,@WebParam(name="unitcode") unitcode:String
                                ,@WebParam(name="personid") personid:String
                                ,@WebParam(name="dh") dh:DataHandler):Int

  /**
    * 接口04:获取捺印指纹的比中列表
    * @param userid 用户id
    * @param unitcode 单位代码
    * @param timefrom 起始时间(YYYY/MM/DD HH24:MI:SS) 选填
    * @param timeto 结束时间(YYYY/MM/DD HH24:MI:SS) 选填
    * @param Startnum 记录起始位置编号(默认为1)
    * @param endnum 记录结束位置编号(默认为10）
    * @return XML文件 无信息或异常返回空
    */
  @WebMethod def getFingerMatchList( @WebParam(name="userid") userid:String
                                    ,@WebParam(name="unitcode") unitcode:String
                                    ,@WebParam(name="timefrom") timefrom:String
                                    ,@WebParam(name="timeto") timeto:String
                                    ,@WebParam(name="Startnum") Startnum:Int
                                    ,@WebParam(name="endnum") endnum:Int) :DataHandler


  /**
    * 接口05:获取捺印指纹的比中数量
    * @param userid 用户id
    * @param unitcode 单位代码
    * @param timefrom 起始时间(YYYY/MM/DD HH24:MI:SS) 选填
    * @param timeto 结束时间(YYYY/MM/DD HH24:MI:SS) 选填
    * @return n（n>=0）:已录入数据比中个数；（n<0）: 参数或接口异常返回。
    */
  @WebMethod def getFingerMatchCount(@WebParam(name="userid") userid:String
                                     ,@WebParam(name="unitcode") unitcode:String
                                     ,@WebParam(name="timefrom") timefrom:String
                                     ,@WebParam(name="timeto") timeto:String) :Int


  /**
    * 接口06:根据请求方指纹编号(人员编号)获取捺印指纹的比中信息
    * @param userid 用户id
    * @param unitcode 单位代码
    * @param personid 公安部标准的23位唯一码，人员编号
    * @return DataHandler 比中关系的FPT文件
    */
  @WebMethod def getFingerMatchData(@WebParam(name="userid") userid:String
                                    ,@WebParam(name="unitcode") unitcode:String
                                    ,@WebParam(name="personid") personid:String):util.ArrayList[DataHandler]

  /**
    * 接口07:获取指纹系统服务器时间
    * @return String 指纹系统服务器时间 (YYYY/MM/DD HH24:MI:SS),接口异常返回空。
    */
  @WebMethod def getSysTime():String


  /**
    * 接口08:捺印掌纹信息录入
    * @param collectsrc 来源
    * @param userid 用户id
    * @param unitcode 单位代码
    * @param palmid 请求方掌纹编号
    * @param palmtype 公安部掌纹部位代码
    * @param personid 公安部标准的23位唯一码，人员编号
    * @param dh WSQ文件
    * @return 1: 成功加入队列 0: 失败
    */
  @WebMethod def setPalm(@WebParam(name="collectsrc") collectsrc:String
                         ,@WebParam(name="userid") userid:String
                         ,@WebParam(name="unitcode") unitcode:String
                         ,@WebParam(name="palmid") palmid:String
                         ,@WebParam(name="palmtype") palmtype:Int
                         ,@WebParam(name="personid") personid:String
                         ,@WebParam(name="dh") dh: Array[Byte]):Int

  /**
    * 接口09:捺印掌纹信息状态查询
    * @param userid 用户id
    * @param unitcode 单位代码
    * @param palmid 请求方掌纹编号
    * @param palmtype 公安部掌纹部位代码
    * @return 4: 建库失败 3: 处理中 1: 成功建库 0: 查询失败
    */
  @WebMethod def getPalmStatus(@WebParam(name="userid") userid:String
                               ,@WebParam(name="unitcode") unitcode:String
                               ,@WebParam(name="palmid") palmid:String
                               ,@WebParam(name="palmtype") palmtype:Int):Int


  /**
    * 接口10:捺印掌纹信息更新
    * @param collectsrc 来源
    * @param userid 用户id
    * @param unitcode 单位代码
    * @param palmid 请求方掌纹编号
    * @param palmtype 公安部掌纹部位代码
    * @param personid 公安部标准的23位唯一码，人员编号
    * @param dh WSQ文件
    * @return 1: 成功加入队列 0: 失败
    */
  @WebMethod def setPalmAgain(@WebParam(name="collectsrc") collectsrc:String
                              ,@WebParam(name="userid") userid:String
                              ,@WebParam(name="unitcode") unitcode:String
                              ,@WebParam(name="palmid") palmid:String
                              ,@WebParam(name="palmtype") palmtype:Int
                              ,@WebParam(name="personid") personid:String
                              ,@WebParam(name="dh") dh: Array[Byte]):Int

  /**
    * 接口11:获取错误详细信息
    * @param userid 用户id
    * @param unitcode 单位代码
    * @param funType 接口方法类型
    * @param personid 公安部标准的23位唯一码，人员编号
    * @return
    */
  @WebMethod def getErrorInfo(@WebParam(name="userid") userid:String
                              ,@WebParam(name="unitcode") unitcode:String
                              ,@WebParam(name="funType") funType:Int
                              ,@WebParam(name="personid") personid:String):DataHandler
}

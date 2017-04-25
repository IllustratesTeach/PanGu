package nirvana.hall.api.webservice.services.haixin

import javax.activation.DataHandler
import javax.jws.{WebMethod, WebParam, WebService}

/**
  * Created by yuchen on 2017/4/14.
  * 为海鑫公司提供的webservice接口,主要对接海鑫的综采系统.
  */
@WebService(serviceName = "collectionSysPorts", targetNamespace = "http://www.egfit.com/")
trait haixinCollectionSysService {

  /**
    * 1 接口名称：捺印指纹信息录入
    * 接口说明：捺印指纹信息录入到指纹系统（包含文字信息）
    * @param collectSrc
    * @param userId
    * @param unitCode
    * @param fingerId
    * @param dataHandler
    * @return 1: 成功加入队列 0: 失败
    */
  @WebMethod
  def setFinger(@WebParam(name="collectsrc")collectSrc:String
                ,@WebParam(name="userid")userId:String
                ,@WebParam(name="unitcode")unitCode:String
                ,@WebParam(name="fingerid")fingerId:String
                ,@WebParam(name="datahandler")dataHandler:DataHandler):Int

  /**
    * 2 接口名称：捺印指纹信息状态查询
    * 接口说明：查询捺印指纹在指纹系统中的状态。
    * @param userId
    * @param unitCode
    * @param fingerId
    * @return 4: 建库失败 3: 处理中 2: 已比中 1: 成功建库 0: 查询失败
    */
  @WebMethod
  def getFingerStatus(@WebParam(name="userid")userId:String
                      ,@WebParam(name="unitcode")unitCode:String
                      ,@WebParam(name="fingerid")fingerId:String):Int


  /**
    * 3 接口名称：捺印指纹信息更新
    * 接口说明：更新需要重新进行录入的捺印指纹信息。
    * @param collectSrc
    * @param userId
    * @param unitCode
    * @param fingerId
    * @param dataHandler
    * @return 1: 成功加入队列 0: 失败
    */
  @WebMethod
  def setFingerAgain(@WebParam(name="collectsrc")collectSrc:String
                     ,@WebParam(name="userid")userId:String
                     ,@WebParam(name="unitcode")unitCode:String
                     ,@WebParam(name="fingerid")fingerId:String
                     ,@WebParam(name="datahandler")dataHandler:DataHandler):Int


  /**
    * 4 接口名称：获取比中列表
    * 接口说明：获取捺印指纹的比中列表。
    * @param userId
    * @param unitCode
    * @param timeForm
    * @param timeTo
    * @param startNum
    * @param endNum
    * @return 需用soap协议的附件形式，见附录2，无信息或异常返回空。xml-file
    */
  @WebMethod
  def getFingerMatchList(@WebParam(name="userid")userId:String
                         ,@WebParam(name="unitcode")unitCode:String
                         ,@WebParam(name="timeform")timeForm:String
                         ,@WebParam(name="timeto")timeTo:String
                         ,@WebParam(name="startnum")startNum:Int
                         ,@WebParam(name="endnum")endNum:Int):DataHandler

  /**
    * 5 接口说明：获取捺印指纹的比中数量。
    * @param userid
    * @param unitcode
    * @param timecode
    * @param timeto
    * @return n（n>=0）:已录入数据比中个数；（n<0）: 参数或接口异常返回。
    */
  @WebMethod
  def getFingerMatchCount(@WebParam(name="userid")userid:String
                          ,@WebParam(name="unitcode") unitcode:String
                          ,@WebParam(name="timeform")timecode:String
                          ,@WebParam(name="timeto")timeto:String):Int

  /**
    * 6 接口说明：根据请求方指纹编号(人员编号)获取捺印指纹的比中信息。
    * @param userid
    * @param unitcode
    * @param fingerid
    * @return datahandle 需用soap协议的附件形式，无信息或异常返回空。
    */
  def getFingerMatchData(@WebParam(name="userid")userid:String
                        ,@WebParam(name="unitcode")unitcode:String
                        ,@WebParam(name="fingerid")fingerid:String):DataHandler


  /**
    * 7 接口说明：获取指纹系统服务器时间
    * @return 指纹系统当前时间(YYYY/MM/DD HH24:MI:SS),接口异常返回空。
    */
  def getSysTime():String

  /**
    * 8接口名称：捺印掌纹信息录入
    * @param collectSrc
    * @param userId
    * @param unitCode
    * @param fingerId
    * @param dataHandler
    * @return 1: 成功加入队列 0: 失败
    */
  def setPalm(@WebParam(name="collectsrc")collectSrc:String
              ,@WebParam(name="userid")userId:String
              ,@WebParam(name="unitcode")unitCode:String
              ,@WebParam(name="fingerid")fingerId:String
              ,@WebParam(name="datahandler")dataHandler:DataHandler):Int


}

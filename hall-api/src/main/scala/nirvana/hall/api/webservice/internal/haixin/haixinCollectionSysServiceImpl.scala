package nirvana.hall.api.webservice.internal.haixin

import java.text.SimpleDateFormat
import java.util.{Date, UUID}
import javax.activation.DataHandler

import monad.support.services.LoggerSupport
import nirvana.hall.api.services.{QueryService, TPCardService}
import nirvana.hall.api.webservice.internal.haixin.util.Constant
import nirvana.hall.api.webservice.services.haixin.haixinCollectionSysService
import nirvana.hall.api.webservice.util.FPTConvertToProtoBuffer
import nirvana.hall.c.services.gfpt4lib.FPTFile

/**
  * Created by yuchen on 2017/4/14.
  */
class haixinCollectionSysServiceImpl(tpCardService: TPCardService, queryService:QueryService) extends haixinCollectionSysService with LoggerSupport{
  /**
    * 1 接口名称：捺印指纹信息录入
    * 接口说明：捺印指纹信息录入到指纹系统（包含文字信息）
    *
    * @param collectSrc
    * @param userId
    * @param unitCode
    * @param fingerId
    * @param dataHandler
    * @return 1: 成功加入队列 0: 失败
    */
  override def setFinger(collectSrc: String
                         , userId: String
                         , unitCode: String
                         , fingerId: String
                         , dataHandler: DataHandler): Int = {
    val uuid = UUID.randomUUID
    info("setFinger called-start:uuid:{};collectsrc:{};userid:{};unitcode:{};fingerid:{};time:{}"
         ,uuid,collectSrc,userId,unitCode,fingerId,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
    var result = Constant.CREATE_TASK_FAIL
    try{
      if(null != dataHandler){
        val taskFpt = FPTFile.parseFromInputStream(dataHandler.getInputStream)
        taskFpt match {
          case Left(fpt3) => throw new Exception("Not Support FPT-V3.0")
          case Right(fpt4) =>
            if(fpt4.logic02Recs.length>0){
              fpt4.logic02Recs.foreach{ sLogic02Rec =>
                val tpCard = FPTConvertToProtoBuffer.TPFPT2ProtoBuffer(sLogic02Rec, "imageDecompressUrl")
                tpCardService.addTPCard(tpCard)
              }
              val matchTask = FPTConvertToProtoBuffer.FPT2MatchTaskProtoBuffer(fpt4)
              queryService.sendQuery(matchTask)
            }else{
              throw new Exception("logictype is error,only support finger")
            }
        }
      }else{
        throw new Exception("dataHandler is null,fingerId is" + fingerId)
      }
      result = Constant.CREATE_TASK_SUCCESS
      info("setFinger called-success:uuid:{};result:{};time:{}"
        ,uuid,result,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
    }catch{
      case ex:Exception => error("setFinger-error:uuid:{};message:{};stackInfo:{};fingerId:{};time:{}"
        ,uuid,ex.getMessage,ex.getStackTrace,fingerId,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
    }
    result
  }

  /**
    * 2 接口名称：捺印指纹信息状态查询
    * 接口说明：查询捺印指纹在指纹系统中的状态。
    * @param userId
    * @param unitCode
    * @param taskId
    * @return 4: 建库失败 3: 处理中 2: 已比中 1: 成功建库 0: 查询失败
    */
override def getFingerStatus(userId: String, unitCode: String, taskId: String): Int = {
  val uuid = UUID.randomUUID
  info("getFingerStatus called-start:uuid:{};userid:{};unitcode:{};taskId:{};time:{}",uuid,userId,unitCode,taskId
        ,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
  var result = Constant.QUERY_FAIL
  try{
    result = Constant.queryStatusConvert(queryService.getStatusBySid(taskId.toLong))
    info("getFingerStatus called-success:uuid:{};userid:{};unitcode:{};taskId:{};time:{}",uuid,userId,unitCode,taskId
      ,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
  }catch{
    case ex:Exception => error("getFingerStatus-error:uuid:{};message:{};stackInfo:{};taskId:{};time:{}"
      ,uuid,ex.getMessage,ex.getStackTrace,taskId,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
  }
  result
}

  /**
    * 3 接口名称：捺印指纹信息更新
    * 接口说明：更新需要重新进行录入的捺印指纹信息。
    *
    * @param collectSrc
    * @param userId
    * @param unitCode
    * @param fingerId
    * @param dataHandler
    * @return 1: 成功加入队列 0: 失败
    */
  override def setFingerAgain(collectSrc: String, userId: String, unitCode: String, fingerId: String, dataHandler: DataHandler): Int = {
    val uuid = UUID.randomUUID
    info("setFingerAgain called-start:uuid:{};collectsrc:{};userid:{};unitcode:{};fingerid:{};time:{}"
      ,uuid,collectSrc,userId,unitCode,fingerId,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
    var result = Constant.CREATE_TASK_FAIL
    try{
      if(null != dataHandler){
        val taskFpt = FPTFile.parseFromInputStream(dataHandler.getInputStream)
        taskFpt match {
          case Left(fpt3) => throw new Exception("Not Support FPT-V3.0")
          case Right(fpt4) =>
            if(fpt4.logic02Recs.length>0){
              fpt4.logic02Recs.foreach{ sLogic02Rec =>
                val tpCard = FPTConvertToProtoBuffer.TPFPT2ProtoBuffer(sLogic02Rec, "imageDecompressUrl")
                if(tpCardService.isExist(tpCard.getStrCardID)){
                  tpCardService.updateTPCard(tpCard)
                }else{
                  throw new Exception("cardid:" + tpCard.getStrCardID + "not exist,please call setFinger fun")
                }
              }
              val matchTask = FPTConvertToProtoBuffer.FPT2MatchTaskProtoBuffer(fpt4)
              queryService.sendQuery(matchTask)
            }else{
              throw new Exception("logictype is error,only support finger")
            }
        }
      }else{
        throw new Exception("dataHandler is null,fingerId is" + fingerId)
      }
      result = Constant.CREATE_TASK_SUCCESS
      info("setFingerAgain called-success:uuid:{};collectsrc:{};userid:{};unitcode:{};fingerid:{};"
        ,uuid,collectSrc,userId,unitCode,fingerId)
    }catch{
      case ex:Exception => error("setFingerAgain-error:uuid:{};message:{};stackInfo:{};fingerId:{};time:{}"
        ,uuid,ex.getMessage,ex.getStackTrace,fingerId,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))
    }
    result
  }

  /**
    * 4 接口名称：获取比中列表
    * 接口说明：获取捺印指纹的比中列表。
    *
    * @param userId
    * @param unitCode
    * @param timeForm
    * @param timeTo
    * @param startNum
    * @param endNum
    * @return 需用soap协议的附件形式，见附录2，无信息或异常返回空。xml-file
    */
  override def getFingerMatchList(userId: String, unitCode: String, timeForm: String, timeTo: String, startNum: Int, endNum: Int): DataHandler = ???

  /**
    * 5 接口说明：获取捺印指纹的比中数量。
    *
    * @param userid
    * @param unitcode
    * @param timecode
    * @param timeto
    * @return n（n>=0）:已录入数据比中个数；（n<0）: 参数或接口异常返回。
    */
  override def getFingerMatchCount(userid: String, unitcode: String, timecode: String, timeto: String): Int = ???

  /**
    * 6 接口说明：根据请求方指纹编号(人员编号)获取捺印指纹的比中信息。
    *
    * @param userid
    * @param unitcode
    * @param fingerid
    * @return datahandle 需用soap协议的附件形式，无信息或异常返回空。
    */
  override def getFingerMatchData(userid: String, unitcode: String, fingerid: String): DataHandler = ???

  /**
    * 7 接口说明：获取指纹系统服务器时间
    *
    * @return 指纹系统当前时间(YYYY/MM/DD HH24:MI:SS),接口异常返回空。
    */
  override def getSysTime(): String = {
    new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date())
  }

  /**
    * 8接口名称：捺印掌纹信息录入
    *
    * @param collectSrc
    * @param userId
    * @param unitCode
    * @param fingerId
    * @param dataHandler
    * @return 1: 成功加入队列 0: 失败
    */
  override def setPalm(collectSrc: String, userId: String, unitCode: String, fingerId: String, dataHandler: DataHandler): Int = ???
}

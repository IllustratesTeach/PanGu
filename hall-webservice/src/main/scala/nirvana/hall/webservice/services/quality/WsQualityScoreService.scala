package nirvana.hall.webservice.services.quality

import javax.activation.DataHandler
import javax.jws.{WebMethod, WebParam, WebService}


/**
  * Created by mengxin on 2018/7/13.
  */
@WebService(serviceName = "sendFingerPrint", targetNamespace = "http://www.gafis.com")
trait WsQualityScoreService {

  /**
    * 接口01:捺印指纹信息录入
    * @param userID 请求方id
    * @param password 请求方密码
    * @param 	NYZZWdh 捺印指掌纹信息交换文件
    * @return xml结果文件
    */
  @WebMethod def sendFingerPrint(  @WebParam(name="userID") userID:String
                            ,@WebParam(name="password") password:String
                            ,@WebParam(name="NYZZWdh") NYZZWdh:DataHandler):  Array[Byte]


}

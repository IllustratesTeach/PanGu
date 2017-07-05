package nirvana.hall.webservice.services.xingzhuan

import javax.activation.DataHandler
import javax.jws.{WebMethod, WebParam, WebService}

/**
  * Created by yuchen on 2017/7/2.
  * 刑专协查获得任务后，可以调用此webservice接口发送查询
  */
@WebService(serviceName = "WsSendQueryService", targetNamespace = "http://www.assertcheck.com/")
trait WsSendQueryService {
  @WebMethod def sendQuery(@WebParam(name="userid") userId: String,
                           @WebParam(name="password") password: String,
                           @WebParam(name="dataHandler")dataHandler:DataHandler): Long
}

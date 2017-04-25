package nirvana.hall.webservice.util

import javax.xml.namespace.QName

import org.apache.axis2.addressing.EndpointReference
import org.apache.axis2.client.Options
import org.apache.axis2.rpc.client.RPCServiceClient

/**
  * Created by sjr on 2017/4/24.
  */
object WebServicesClient_AssistCheck {

   def callHallWebService(opAddEntryArgs: Array[AnyRef], methodName:String): Int ={
     val classes: Array[java.lang.Class[_]] = Array[java.lang.Class[_]](classOf[Int])
    val serviceClientAndSetTaskStatus = createClient("http://127.0.0.1:8081" + "/ws/collectionSysPorts?wsdl"
      ,"http://www.egfit.com/",methodName)
    val flag = serviceClientAndSetTaskStatus.get._1.invokeBlocking(serviceClientAndSetTaskStatus.get._2, opAddEntryArgs, classes)(0).asInstanceOf[Int]
    flag
  }

  private def createClient(url:String,targetNamespace:String,functionName:String): Option[(RPCServiceClient,QName)] ={
    val serviceClient: RPCServiceClient = new RPCServiceClient
    val options: Options = serviceClient.getOptions
    val targetEPR: EndpointReference = new EndpointReference(url)
    options.setTo(targetEPR)
    val opAddEntry: QName = new QName(targetNamespace, functionName)
    Some(serviceClient,opAddEntry)
  }

}

package nirvana.hall.webservice

import java.io.File
import javax.activation.{DataHandler, FileDataSource}
import javax.xml.namespace.QName

import org.apache.axis2.addressing.EndpointReference
import org.apache.axis2.client.Options
import org.apache.axis2.rpc.client.RPCServiceClient
import org.junit.Test

/**
  * Created by yuchen on 2017/4/8.
  */
class webservicesClient {

  @Test
  def  test(): Unit ={
    val dataHandler = new DataHandler(new FileDataSource(new File("/Users/yuchen/fpt/test.FPT")))
    val opAddEntryArgs: Array[AnyRef] = new Array[AnyRef](5)
    opAddEntryArgs(0) = "1700"
    opAddEntryArgs(1) = "1700"
    opAddEntryArgs(2) = "1700"
    opAddEntryArgs(3) = "1234567890"
    opAddEntryArgs(4) = dataHandler
    val result = callHallWebServiceWithDataHandle(opAddEntryArgs,"setFinger")
    println(result)
  }

  @Test
  def test_xingzhuan(): Unit = {
    try{
      val opAddEntryArgs: Array[AnyRef] = new Array[AnyRef](3)
      val dataHandler = new DataHandler(new FileDataSource(new File("/Users/yuchen/fpt/A1200000000002006060707.fpt")))
      opAddEntryArgs(0) = "1700"
      opAddEntryArgs(1) = "1700"
      opAddEntryArgs(2) = dataHandler
      val result = callHallWebServiceWithLong(opAddEntryArgs,"sendQuery")
      assert(null != result ,result)
    }catch{
      case e:Exception => println("ERROR:" + e.getMessage)
    }
  }


  /**
    *  返回DataHandle
    * @param opAddEntryArgs
    * @param methodName
    * @return
    */
  private def callHallWebServiceWithDataHandle(opAddEntryArgs: Array[AnyRef], methodName:String): DataHandler ={
    val classes: Array[Class[_]] = Array[Class[_]](classOf[DataHandler])
    val serviceClientAndSetTaskStatus = createClient("http://localhost:8080" + "/ws/WsSendQueryService?wsdl"
      ,"http://www.egfit.com/",methodName)
    val dataHandle = serviceClientAndSetTaskStatus.get._1.invokeBlocking(serviceClientAndSetTaskStatus.get._2
                                                                  , opAddEntryArgs
                                                                  , classes)(0).asInstanceOf[DataHandler]
    dataHandle
  }

  /**
    * 返回Long
    * @param opAddEntryArgs
    * @param methodName
    * @return
    */
  private def callHallWebServiceWithLong(opAddEntryArgs: Array[AnyRef], methodName:String): Long ={
    val classes: Array[Class[_]] = Array[Class[_]](classOf[Long])
    val serviceClientAndSetTaskStatus = createClient("http://localhost:8080" + "/ws/WsSendQueryService?wsdl"
      ,"http://www.egfit.com/",methodName)
    val flag = serviceClientAndSetTaskStatus.get._1.invokeBlocking(serviceClientAndSetTaskStatus.get._2
      , opAddEntryArgs
      , classes)(0).asInstanceOf[Long]
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

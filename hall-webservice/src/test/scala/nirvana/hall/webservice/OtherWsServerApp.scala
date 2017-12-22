package nirvana.hall.webservice

import javax.servlet.ServletContext

import nirvana.hall.webservice.services.xingzhuan.FingerPalmAppServer
import nirvana.hall.webservice.xingzhuan.FingerPalmAppServerImpl
import org.apache.tapestry5.TapestryFilter
import org.apache.tapestry5.ioc.ServiceBinder
import stark.utils.services.JettyServerCreator

/**
  * 模拟启动第三方webservice服务
  * Created by songpeng on 2017/12/6.
  */
object OtherWsServerApp {

  def main(args: Array[String]): Unit = {
    val server = JettyServerCreator.createTapestryWebapp("localhost", 12345, "nirvana.hall.webservice", "ws", new WsFilter)
    server.start()
    server.join()
  }

  class WsFilter extends TapestryFilter {
    protected override def provideExtraModuleClasses(context: ServletContext): Array[Class[_]] = {
      Array[Class[_]](Class.forName("nirvana.hall.webservice.TestOtherWsModule"))
    }
  }
}

/**
  * 绑定service
  */
object TestOtherWsModule{
  def bind(binder: ServiceBinder): Unit = {
    binder.bind(classOf[FingerPalmAppServer],classOf[FingerPalmAppServerImpl])
  }
}

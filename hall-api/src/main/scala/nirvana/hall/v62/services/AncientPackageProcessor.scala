package nirvana.hall.v62.services

import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.GNETREQUESTHEADOBJECT
import org.apache.tapestry5.ioc.annotations.UsesMappedConfiguration
import org.jboss.netty.channel.Channel

@UsesMappedConfiguration(classOf[AncientPackageProcessor])
trait AncientPackageProcessorSource{
  def process(pkg:GBASE_ITEMPKG_OPSTRUCT,channel:Channel)
}
/**
  * 针对不同数据包类型的处理
 *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-27
  */
trait AncientPackageProcessor {
  /**
    * 处理不同的数据
    *
    * @param request 请求
    * @param pkg 数据包
    * @param channel 连接通道
    */
  def process(request: GNETREQUESTHEADOBJECT,pkg:GBASE_ITEMPKG_OPSTRUCT,channel:Channel)
}

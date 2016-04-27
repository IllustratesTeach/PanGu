package nirvana.hall.v62.services

import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import org.jboss.netty.channel.Channel

/**
  * 针对不同数据包类型的处理
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-27
  */
trait AncientPackageProcessor {
  def process(pkg:GBASE_ITEMPKG_OPSTRUCT,channel:Channel)
}

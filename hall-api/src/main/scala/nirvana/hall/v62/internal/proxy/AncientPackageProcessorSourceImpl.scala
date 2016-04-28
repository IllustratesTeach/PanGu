package nirvana.hall.v62.internal.proxy

import java.util

import monad.support.services.{LoggerSupport, MonadException}
import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.v62.internal.c.gbaselib.gitempkg
import nirvana.hall.v62.internal.c.grmtlib.grmtpkg
import nirvana.hall.v62.services.{AncientPackageProcessorSource, AncientPackageProcessor}
import nirvana.hall.v62.services.HallV62ExceptionCode.FAIL_TO_FIND_PROCESSOR
import org.jboss.netty.channel.Channel

/**
  * implements ancient package processor source
 *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-28
  */
object AncientPackageProcessorSource{
  //DSL Context using DynamicVariable method
  private [proxy] val channelContext = new scala.util.DynamicVariable[Channel](null)
}
class AncientPackageProcessorSourceImpl(configuration: util.Map[Short, AncientPackageProcessor])
  extends AncientPackageProcessorSource
  with grmtpkg
  with gitempkg
  with LoggerSupport{
  override def process(pkg: GBASE_ITEMPKG_OPSTRUCT, channel: Channel): Unit = {
    if(pkg.head.nDataLen != pkg.getDataSize){
      throw new MonadException("invalidate package len(r):%s!=len(h)%s".format(pkg.getDataSize,pkg.head.nDataLen),FAIL_TO_FIND_PROCESSOR)
    }
    val request = GAFIS_PKG_GetRmtRequest(pkg).getOrElse(throw new IllegalStateException("Missing Request Item"))
    val opClass = request.nOpClass
    val opCode = request.nOpCode
    info("username:{} opClass {} opCode:{} ",request.szUserName,opClass,opCode)

    val processor = configuration.get(opClass)

    if(processor == null){
      throw new MonadException("fail to find processor by opClass "+opClass,FAIL_TO_FIND_PROCESSOR)
    }
    AncientPackageProcessorSource.channelContext.withValue(channel){
      processor.process(request,pkg,channel)
    }
  }
}

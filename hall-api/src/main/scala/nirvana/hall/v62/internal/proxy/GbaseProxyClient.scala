package nirvana.hall.v62.internal.proxy

import monad.support.services.LoggerSupport
import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.v62.internal.AncientClientSupport
import nirvana.hall.v62.internal.c.gbaselib.gitempkg
import nirvana.hall.v62.internal.c.gnetlib.{gnetcsr, reqansop}
import nirvana.hall.v62.internal.c.grmtlib.{grmtpkg, grmtcsr}
import nirvana.hall.v62.services.V62ServerAddress

/**
 * Created by songpeng on 16/5/3.
 */
@deprecated
class GbaseProxyClient extends AncientClientSupport
  with grmtcsr
  with gnetcsr
  with gitempkg
  with grmtpkg
  with reqansop
  with LoggerSupport{
  //TODO 可配置
  override def serverAddress: V62ServerAddress = V62ServerAddress("10.1.6.181",6811,10,30,"afisadmin",Some("helloafis"))
  def sendAndReceiveFromBackend(pkg:GBASE_ITEMPKG_OPSTRUCT): GBASE_ITEMPKG_OPSTRUCT={
    executeInChannel{channelOperator=>
      GAFIS_RMTLIB_SendPkgInClient(channelOperator,pkg)
      GAFIS_RMTLIB_RecvPkg(channelOperator)
    }
  }
  def sendFromBackend(pkg:GBASE_ITEMPKG_OPSTRUCT): Unit = {
    executeInChannel{channelOperator=>
      GAFIS_RMTLIB_SendPkgInClient(channelOperator,pkg)
    }
  }
}

package nirvana.hall.v62.internal.proxy

import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.v62.internal.AncientClientSupport
import nirvana.hall.v62.internal.c.gnetlib.{reqansop, gnetcsr}
import nirvana.hall.v62.internal.c.grmtlib.grmtcsr
import nirvana.hall.v62.services.V62ServerAddress

/**
 * Created by songpeng on 16/5/3.
 */
class GbaseProxyClient extends AncientClientSupport with grmtcsr with gnetcsr with reqansop {
  override def serverAddress: V62ServerAddress = V62ServerAddress("10.1.6.247",6811,10,30,"afisadmin",Some("helloafis"))
  def sendAndReceiveFromBackend(pkg:GBASE_ITEMPKG_OPSTRUCT): GBASE_ITEMPKG_OPSTRUCT={
    executeInChannel{channelOperator=>
      GAFIS_RMTLIB_SendPkgInClient(channelOperator,pkg)
      GAFIS_RMTLIB_RecvPkg(channelOperator)
    }
  }
}

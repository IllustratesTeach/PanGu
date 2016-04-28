package nirvana.hall.v62.internal.c.grmtlib

import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.v62.internal.AncientClientSupport
import nirvana.hall.v62.internal.c.gnetlib.{gnetcsr, reqansop}

/**
  * 远程通信服务器的客户端程序
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-27
  */
trait grmtcsr {
  this:AncientClientSupport with gnetcsr with reqansop =>
  def GAFIS_RMTLIB_SendPkg(pstPkg:GBASE_ITEMPKG_OPSTRUCT){
    executeInChannel{channelOperator=>
      /*
      val dataLen = pstPkg.head.nDataLen
      val bytes = ByteBuffer.allocate(4).putInt(dataLen).array()
      NETOP_SENDDATA(channelOperator,bytes)
      val bufferReceived = NETOP_RECVDATA(channelOperator,4)
      if(bufferReceived.readInt() <0){
        throw new IllegalStateException("server return data length less than zero")
      }
      */
      NETOP_SENDDATA(channelOperator,pstPkg)
    }
  }
}

package nirvana.hall.v62.internal.c.grmtlib

import java.nio.ByteBuffer

import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.v62.internal.AncientClientSupport
import nirvana.hall.v62.internal.c.gnetlib.{gnetcsr, reqansop}

/**
  * 远程通信服务器的客户端程序
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-27
  */
trait grmtcsr {
  this:AncientClientSupport with gnetcsr with reqansop =>
  /**
    * 此方法主要用来作为服务器端方向，向请求来的客户端发送数据
    *
    * @param pstPkg 待发送的包
    */
  def GAFIS_RMTLIB_SendPkgInServer(pstPkg:GBASE_ITEMPKG_OPSTRUCT){
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

  /**
    * 此方法作为客户端向服务器端发送数据
    *
    * @param pstPkg 包
    */
  def GAFIS_RMTLIB_SendPkgInClient(pstPkg:GBASE_ITEMPKG_OPSTRUCT){
    executeInChannel{channelOperator=>
      val dataLen = pstPkg.head.nDataLen
      val bytes = ByteBuffer.allocate(4).putInt(dataLen).array()
      NETOP_SENDDATA(channelOperator,bytes)
      val bufferReceived = NETOP_RECVDATA(channelOperator,4)
      if(bufferReceived.readInt() <0){
        throw new IllegalStateException("server return data length less than zero")
      }
      NETOP_SENDDATA(channelOperator,pstPkg)
    }
  }

  /**
    * 作为客户端从服务器接收包
    */
  def GAFIS_RMTLIB_RecvPkg():GBASE_ITEMPKG_OPSTRUCT={
    executeInChannel { channelOperator =>

      val channelBuffer = NETOP_RECVDATA(channelOperator, 4)
      channelBuffer.markReaderIndex()
      val bytes = channelBuffer.readBytes(4).array()
      channelBuffer.resetReaderIndex()
      val dataLength = channelBuffer.readInt()

      /*
    //for normal net function, only for OP_CLASS_FILE and OP_CLASS_HOTUPDATE
    if(Char4To_uint4(nDataLen) == sizeof(GNETREQUESTHEADOBJECT))	//normal net function
    {
      NETOP_RECVDATA(pstCon, (UCHAR*)&stReq + sizeof(stReq.cbSize),sizeof(stReq)-sizeof(stReq.cbSize));
      CHAR4COPY(stReq.cbSize,nDataLen);
      if( GBASE_ITEMPKG_New(pstPkg,1024) < 0 ) ERRFAILFINISHEXIT();
      if( GAFIS_PKG_AddRmtRequest(pstPkg,&stReq) < 0 ) ERRFAILFINISHEXIT();
      retval = pstPkg->nPkgLen;
      if( pbIsProxyReq )	*pbIsProxyReq = 1;
      goto Finish_Exit;
    }
    */
      val pstPkg = new GBASE_ITEMPKG_OPSTRUCT
      pstPkg.head.nDataLen = dataLength

      NETOP_SENDDATA(channelOperator, bytes)
      NETOP_RECVDATA(channelOperator, pstPkg)
    }
  }
}

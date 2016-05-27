package nirvana.hall.v62.internal.c.grmtlib

import java.nio.ByteBuffer

import monad.support.services.LoggerSupport
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gbaselib.gafiserr
import nirvana.hall.c.services.gbaselib.gafiserr.{FILENLSTRUCT, GAFISERRDATSTRUCT}
import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.{GNETANSWERHEADOBJECT, GNETREQUESTHEADOBJECT}
import nirvana.hall.v62.internal.AncientClientSupport
import nirvana.hall.v62.internal.c.gbaselib.gitempkg
import nirvana.hall.v62.internal.c.gnetlib.{gnetcsr, reqansop}
import nirvana.hall.v62.services.ChannelOperator
import org.jboss.netty.buffer.ChannelBuffers

/**
  * 远程通信服务器的客户端程序
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-27
  */
trait grmtcsr {
  this:AncientClientSupport with gitempkg with grmtpkg with gnetcsr with reqansop with LoggerSupport =>
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
  def GAFIS_RMTLIB_SendPkgInClient(channelOperator: ChannelOperator,pstPkg:GBASE_ITEMPKG_OPSTRUCT){
      val dataLen = pstPkg.head.nDataLen
      val bytes = ByteBuffer.allocate(4).putInt(dataLen).array()
//    debug("{} sending backend package length ...",channelOperator.getServerInfo)
    NETOP_SENDDATA(channelOperator,bytes)
//    debug("{} receiving backend package length ...",channelOperator.getServerInfo)
    val bufferReceived = NETOP_RECVDATA(channelOperator,4)
    if(bufferReceived.readInt() <0){
      throw new IllegalStateException("server return data length less than zero")
    }
//    debug("{} sending package to backend ",channelOperator.getServerInfo)
    NETOP_SENDDATA(channelOperator,pstPkg)
//    debug("{} finish send to backend ",channelOperator.getServerInfo)
  }

  /**
    * 作为客户端从服务器接收包
    */
  def GAFIS_RMTLIB_RecvPkg(channelOperator: ChannelOperator):GBASE_ITEMPKG_OPSTRUCT= {
    val channelBuffer = NETOP_RECVDATA(channelOperator, 4)
    channelBuffer.markReaderIndex()
    val bytes = channelBuffer.readBytes(4).array()
    channelBuffer.resetReaderIndex()
    val dataLength = channelBuffer.readInt()

    //for normal net function, only for OP_CLASS_FILE and OP_CLASS_HOTUPDATE
    val pReq = new GNETREQUESTHEADOBJECT
    debug("{} receiving pkg length:"+pReq.getDataSize,channelOperator.getServerInfo)
    if(dataLength == pReq.getDataSize)	//normal net function
    {
      //TODO 优化此处代码
      val remainBuffer = channelOperator.receiveByteArray(dataLength - 4)
      val buffer = ChannelBuffers.buffer(dataLength)
      buffer.writeInt(dataLength)
      buffer.writeBytes(remainBuffer)
      pReq.fromStreamReader(buffer)

      val pstPkg = GBASE_ITEMPKG_New
      GAFIS_PKG_AddRmtRequest(pstPkg,pReq)
      pstPkg
    }else {
      val pstPkg = new GBASE_ITEMPKG_OPSTRUCT
      pstPkg.head.nDataLen = dataLength

      NETOP_SENDDATA(channelOperator, bytes)
      NETOP_RECVDATA(channelOperator, pstPkg)
    }
  }
  def GAFIS_NETCSR_SendRemoteErrStruct(e:Throwable)
  {
    var stacks = e.getStackTrace
    val errdata = new GAFISERRDATSTRUCT
    errdata.bnAFISErrData = e.getMessage.getBytes(AncientConstants.GBK_ENCODING)
    errdata.nErrDataLen = errdata.bnAFISErrData.length.toByte

    errdata.szFileName = stacks.head.getFileName
    errdata.nAFISErrno = gafiserr.GAFISERR_SYS_UNKNOWN
    errdata.nLineNum = stacks.head.getLineNumber.toShort
    if(stacks.length > 14)
      stacks = stacks.take(14)

    errdata.stFileList = stacks.map{s=>
      val f = new FILENLSTRUCT()
      f.nLineNum = s.getLineNumber.toShort
      f.sFileName = s.getFileName
      if(f.sFileName != null && f.sFileName.length>18)
        f.sFileName = f.sFileName.take(18)
      f
    }
    errdata.nFileCount = errdata.stFileList.length.toByte

    val stAns = new GNETANSWERHEADOBJECT
    NETANS_SetRetVal(stAns,-1)
    val stSendPkg = GBASE_ITEMPKG_New
    GAFIS_PKG_AddRmtAnswer(stSendPkg,stAns)
    GAFIS_PKG_AddOtherData(stSendPkg,errdata.toByteArray(AncientConstants.GBK_ENCODING))
    GAFIS_RMTLIB_SendPkgInServer(stSendPkg)
  }
}

/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package nirvana.hall.v62.internal.proxy

import java.nio.channels.ClosedChannelException

import monad.support.services.LoggerSupport
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.GNETREQUESTHEADOBJECT
import nirvana.hall.v62.internal.c.gbaselib.gitempkg
import nirvana.hall.v62.internal.c.grmtlib.grmtpkg
import org.jboss.netty.bootstrap.ClientBootstrap
import org.jboss.netty.buffer.ChannelBuffer
import org.jboss.netty.buffer.ChannelBuffers
import org.jboss.netty.channel.Channel
import org.jboss.netty.channel.ChannelFuture
import org.jboss.netty.channel.ChannelFutureListener
import org.jboss.netty.channel.ChannelHandlerContext
import org.jboss.netty.channel.ChannelStateEvent
import org.jboss.netty.channel.ExceptionEvent
import org.jboss.netty.channel.MessageEvent
import org.jboss.netty.channel.SimpleChannelUpstreamHandler
import org.jboss.netty.channel.socket.ClientSocketChannelFactory
import java.net.InetSocketAddress

import org.jboss.netty.handler.codec.frame.FrameDecoder
import org.jboss.netty.handler.codec.oneone.{OneToOneEncoder, OneToOneDecoder}

import scala.concurrent.{Promise, ExecutionContext}

object TxProxyInboundHandler {
  /**
    * Closes the specified channel after all queued write requests are flushed.
    */
  private[proxy] def closeOnFlush(ch: Channel) {
    if (ch.isConnected) {
      ch.write(ChannelBuffers.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE)
    }
  }
}
case object RequestHandled
case object RequestNotHandled

class TxProxyInboundHandler(cf: ClientSocketChannelFactory) extends SimpleChannelUpstreamHandler {
  private[proxy] final val trafficLock: AnyRef = new AnyRef
  @volatile
  private var outboundChannel: Channel = null
  @volatile
  private var working = false
  @volatile
  private var firstCall = true

  override def channelOpen(ctx: ChannelHandlerContext, e: ChannelStateEvent) {
    val inboundChannel: Channel = e.getChannel
    inboundChannel.setReadable(false)
    val cb: ClientBootstrap = new ClientBootstrap(cf)
    cb.getPipeline.addLast("handler", new OutboundHandler(e.getChannel))
    val f: ChannelFuture = cb.connect(new InetSocketAddress("10.1.6.182", 6811))
    outboundChannel = f.getChannel
    f.addListener(new ChannelFutureListener() {
      def operationComplete(future: ChannelFuture) {
        if (future.isSuccess) {
          inboundChannel.setReadable(true)
        }
        else {
          inboundChannel.close
        }
      }
    })
    inboundChannel.setAttachment(outboundChannel)
  }

  override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent) {
    val msg: ChannelBuffer = e.getMessage.asInstanceOf[ChannelBuffer]
    trafficLock synchronized {
      if(working || firstCall)
        ctx.sendUpstream(e) //向上发送数据
      else {
        outboundChannel.write(msg)
      }
      if (!outboundChannel.isWritable) {
        e.getChannel.setReadable(false)
      }
    }
  }

  override def channelInterestChanged(ctx: ChannelHandlerContext, e: ChannelStateEvent) {
    trafficLock synchronized {
      if (e.getChannel.isWritable) {
        if (outboundChannel != null) {
          outboundChannel.setReadable(true)
        }
      }
    }
  }

  override def channelClosed(ctx: ChannelHandlerContext, e: ChannelStateEvent) {
    if (outboundChannel != null) {
      TxProxyInboundHandler.closeOnFlush(outboundChannel)
    }
  }

  override def exceptionCaught(ctx: ChannelHandlerContext, e: ExceptionEvent) {
    outputException(e)
    TxProxyInboundHandler.closeOnFlush(e.getChannel)
  }

  private class OutboundHandler(inboundChannel: Channel) extends SimpleChannelUpstreamHandler {
    override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent) {
      val msg: ChannelBuffer = e.getMessage.asInstanceOf[ChannelBuffer]
      trafficLock synchronized {
          inboundChannel.write(msg)
        if (!inboundChannel.isWritable) {
          e.getChannel.setReadable(false)
        }
      }
    }

    override def channelInterestChanged(ctx: ChannelHandlerContext, e: ChannelStateEvent) {
      trafficLock synchronized {
        if (e.getChannel.isWritable) {
          inboundChannel.setReadable(true)
        }
      }
    }

    override def channelClosed(ctx: ChannelHandlerContext, e: ChannelStateEvent) {
      TxProxyInboundHandler.closeOnFlush(inboundChannel)
    }

    override def exceptionCaught(ctx: ChannelHandlerContext, e: ExceptionEvent) {
      outputException(e)
      TxProxyInboundHandler.closeOnFlush(e.getChannel)
    }
  }
  private def outputException(e:ExceptionEvent): Unit ={
    e.getCause match {
      case cce:ClosedChannelException=>
//        System.err.println("channel closed!")
      case ioe:java.io.IOException =>
        if(ioe.getMessage== null || ioe.getMessage != "Connection reset by peer")
          ioe.printStackTrace(System.err)

      case other=>
        other.printStackTrace(System.err)
    }
  }


  class GbasePkgFrameDecoder extends FrameDecoder with LoggerSupport with gitempkg with grmtpkg{
    private val DIRECT_REQUEST_LENGTH=192
    private implicit val executionContext = ExecutionContext.global
    private var lengthOpt:Option[Int] = None
    private var nextPromiseOpt:Option[Promise[ChannelBuffer]] = None
    @throws(classOf[Exception])
    protected def decode(ctx: ChannelHandlerContext, channel: Channel, buffer: ChannelBuffer): AnyRef = {
      lengthOpt match{
        case Some(dataLength)=>
          //        debug("readable length:{} dataLength:{}",buffer.readableBytes(),dataLength)
          if(buffer.readableBytes() >= dataLength){
            val tmpBuffer = buffer.readBytes(dataLength)
            nextPromiseOpt match{
              case Some(promise)=>
                promise.success(tmpBuffer)
                nextPromiseOpt = None
                return null
              case None =>
                return tmpBuffer
            }
          }
        case None if buffer.readableBytes() >= 4=> //第一次读取
          buffer.markReaderIndex()
          val dataLength = buffer.readInt()
          if(dataLength <= 0)
            throw new IllegalArgumentException("data length is zero!")
          lengthOpt = Some(dataLength)
          if(dataLength == DIRECT_REQUEST_LENGTH) {
            buffer.resetReaderIndex()
          }
          else {
            //向通讯服务器发送数据
            val dataLengthBuffer = buffer.factory().getBuffer(4)
            dataLengthBuffer.writeInt(dataLength)
            outboundChannel.write(dataLengthBuffer)
          }

          if(buffer.readableBytes() >= dataLength){
            lengthOpt = None //清空之前的操作数据
            return buffer.readBytes(dataLength)
          }
        case other=>
      }

      null
    }
    class GbasePkgDecoder extends OneToOneDecoder{
      override def decode(ctx: ChannelHandlerContext, channel: Channel, msg: scala.Any): AnyRef = {
        firstCall = false
        msg match{
          case buffer:ChannelBuffer =>
            working = false
            if(buffer.readableBytes() == DIRECT_REQUEST_LENGTH) {
              val pstPkg = GBASE_ITEMPKG_New
              val pReq = new GNETREQUESTHEADOBJECT().fromStreamReader(buffer)
              GAFIS_PKG_AddRmtRequest(pstPkg,pReq)
              //192的忽略
              error("===========> direct data for opClass:"+pReq.nOpClass+" opCode:"+pReq.nOpCode)
              pstPkg
            }else{
              working = false
              new GBASE_ITEMPKG_OPSTRUCT().fromStreamReader(buffer)
            }
          case other=>
            other.asInstanceOf[AnyRef]
        }
      }
    }
    class GbasePkgEncoder extends OneToOneEncoder{
      override def encode(ctx: ChannelHandlerContext, channel: Channel, msg: scala.Any): AnyRef = {
        msg match{
          case pkg:GBASE_ITEMPKG_OPSTRUCT =>
            //先发四个长度
            val buffer = channel.getConfig.getBufferFactory.getBuffer(4)
            buffer.writeInt(pkg.getDataSize)

            //读取四个长度的promise
            val promise = Promise[ChannelBuffer]()
            promise.future.foreach{intBuffer=>
              val pkgBuffer = channel.getConfig.getBufferFactory.getBuffer(pkg.getDataSize)
              pkg.writeToStreamWriter(pkgBuffer)
              channel.write(pkgBuffer)
            }
            lengthOpt = Some(4)
            nextPromiseOpt = Some(promise)

            buffer
          case ancientData:AncientData =>
            val buffer = channel.getConfig.getBufferFactory.getBuffer(ancientData.getDataSize)
            ancientData.writeToStreamWriter(buffer)

            buffer

          case (len:Int,promise:Promise[ChannelBuffer])=>
            lengthOpt = Some(len)
            nextPromiseOpt = Some(promise)
            null
          case RequestHandled=>
            working = true
            null
          case RequestNotHandled =>
            working = false
            null
          case other=>
            other.asInstanceOf[AnyRef]
        }
      }
    }
  }

}
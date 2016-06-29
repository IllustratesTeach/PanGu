// Copyright 2014,2015 Jun Tsai. All rights reserved.
// site: http://www.ganshane.com
package nirvana.hall.support.internal

import java.io.Closeable

import com.google.protobuf.ExtensionRegistry
import com.google.protobuf.GeneratedMessage.GeneratedExtension
import monad.rpc.protocol.CommandProto.{BaseCommand, CommandStatus}
import nirvana.hall.support.HallSupportConstants
import nirvana.hall.support.services.RpcHttpClient
import org.apache.commons.io.IOUtils
import org.apache.http.HttpEntity
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.{CloseableHttpResponse, HttpGet, HttpPost}
import org.apache.http.entity.ByteArrayEntity
import org.apache.http.impl.client.{CloseableHttpClient, HttpClientBuilder}
import org.apache.http.util.EntityUtils

import scala.util.control.NonFatal

/**
 * WebApp的客户端类，用来处理web
 * @author jcai
 */
class RpcHttpClientImpl(extensionRegistry: ExtensionRegistry) extends RpcHttpClient{
  def download(url:String): Array[Byte] ={
    val httpClient: CloseableHttpClient = createHttpClient
    try {
      val get = new HttpGet(url)
      val response: CloseableHttpResponse = httpClient.execute(get)
      try {
        if (response.getStatusLine.getStatusCode == 200) {
          val entity = response.getEntity
          try {
            IOUtils.toByteArray(entity.getContent)
          } finally {
            EntityUtils.consume(entity)
          }
        }
        else throw new CallRpcException(response.getStatusLine.toString)
      }
      finally {
        close(response)
      }
    }
    catch {
      case e:CallRpcException =>
        throw e
      case NonFatal(e)=>
        throw new CallRpcException(e.getMessage)

    }
    finally {
      close(httpClient)
    }
  }
  /**
   * 通过protobuf的数据来调用远程的url
   * @param url web application url
   */
  def call[T](url: String,extension: GeneratedExtension[BaseCommand, T], value: T, headerMap: Map[String, String] = Map()):BaseCommand={
    val httpClient: CloseableHttpClient = createHttpClient
    try {
      val post: HttpPost = new HttpPost(url)
      //添加header头信息
      post.setHeader(HallSupportConstants.HTTP_PROTOBUF_HEADER,HallSupportConstants.HTTP_PROTOBUF_HEADER_VALUE)
      headerMap.foreach(header=> post.setHeader(header._1, header._2))
      val request = BaseCommand.newBuilder()
      request.setExtension(extension,value)
      request.setTaskId(1L)

      val reqEntity: ByteArrayEntity = new ByteArrayEntity(request.build().toByteArray)
      post.setEntity(reqEntity)
      val response: CloseableHttpResponse = httpClient.execute(post)
      try {
        if (response.getStatusLine.getStatusCode == 200) {
          val entity: HttpEntity = response.getEntity
          try {
            //val bytes = IOUtils.toyteArray(entity.getContent)
            //responseBuilder.mergeFrom(entity.getContent)
            val baseCommand = BaseCommand.getDefaultInstance.getParserForType.parseFrom(entity.getContent,extensionRegistry)
            if(baseCommand.getStatus == CommandStatus.FAIL){
              throw new RuntimeException(baseCommand.getMsg)
            }
            baseCommand
          } finally {
            EntityUtils.consume(entity)
          }
        }
        else throw new CallRpcException(response.getStatusLine.toString)
      }
      finally {
        close(response)
      }
    }
    catch {
      case e:CallRpcException =>
        throw e
      case NonFatal(e)=>
        throw new CallRpcException(e.getMessage)

    }
    finally {
      close(httpClient)
    }
  }

  private def close(e: Closeable) {
    try {
      e.close()
    }
    catch {
      case NonFatal(e) =>

    }
  }

  private def createHttpClient: CloseableHttpClient = {
    val defaultConfig: RequestConfig = RequestConfig.custom.
      setConnectTimeout(10 * 1000). //连接超时设置
      setSocketTimeout(30 * 1000). //读取时间设置
      build
    HttpClientBuilder.create.setDefaultRequestConfig(defaultConfig).setUserAgent("nirvana-hall-stream/1.0").build
  }
}
class CallRpcException(message:String) extends RuntimeException(message)


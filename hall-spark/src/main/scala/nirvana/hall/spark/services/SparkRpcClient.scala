// Copyright 2014,2015 Jun Tsai. All rights reserved.
// site: http://www.ganshane.com
package nirvana.hall.spark.services

import java.io.Closeable

import com.google.protobuf.ExtensionRegistry
import com.google.protobuf.GeneratedMessage.GeneratedExtension
import monad.rpc.protocol.CommandProto.BaseCommand
import nirvana.hall.protocol.extract.{ExtractProto, FeatureDisplayProto}
import nirvana.hall.protocol.image.FirmImageDecompressProto
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
object SparkRpcClient extends RpcHttpClient{
  private lazy val extensionRegistry = createExtensionRegistry
  private def createExtensionRegistry= {
    val registry = ExtensionRegistry.newInstance()
    FirmImageDecompressProto.registerAllExtensions(registry)
    ExtractProto.registerAllExtensions(registry)
    FeatureDisplayProto.registerAllExtensions(registry)
    registry
  }
  private lazy val httpClient: CloseableHttpClient = createHttpClient
  def download(url:String): Array[Byte] ={
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
  }
  /**
   * 通过protobuf的数据来调用远程的url
   * @param url web application url
   */
  def call[T](url: String,extension: GeneratedExtension[BaseCommand, T], value: T, headerMap: Map[String, String] = Map()):BaseCommand={
    try {
      val post: HttpPost = new HttpPost(url)
      post.setHeader(HallSupportConstants.HTTP_PROTOBUF_HEADER,HallSupportConstants.HTTP_PROTOBUF_HEADER_VALUE)
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
            BaseCommand.getDefaultInstance.getParserForType.parseFrom(entity.getContent,extensionRegistry)
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
      setConnectTimeout(5 * 1000). //连接超时设置
      setSocketTimeout(10 * 1000). //读取时间设置
      build
    val httpClient = HttpClientBuilder.create
      .setDefaultRequestConfig(defaultConfig)
      .setMaxConnPerRoute(50)
      .setMaxConnTotal(200)
      .setUserAgent("nirvana-hall-stream/1.0").build
    sys.addShutdownHook{
      close(httpClient)
    }

    httpClient
  }
}
class CallRpcException(message:String) extends RuntimeException(message) {
  override def getMessage: String = "fetch fpt file error ,"
}


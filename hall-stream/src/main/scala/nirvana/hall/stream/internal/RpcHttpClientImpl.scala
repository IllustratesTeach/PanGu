// Copyright 2014,2015 Jun Tsai. All rights reserved.
// site: http://www.ganshane.com
package nirvana.hall.stream.internal

import java.io.{Closeable, IOException}

import com.google.protobuf.ExtensionRegistry
import com.google.protobuf.GeneratedMessage.GeneratedExtension
import monad.rpc.protocol.CommandProto.BaseCommand
import nirvana.hall.stream.HallStreamConstants
import nirvana.hall.stream.services.RpcHttpClient
import org.apache.http.HttpEntity
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.{CloseableHttpResponse, HttpPost}
import org.apache.http.entity.ByteArrayEntity
import org.apache.http.impl.client.{CloseableHttpClient, HttpClientBuilder}
import org.apache.http.util.EntityUtils

import scala.util.control.NonFatal

/**
 * WebApp的客户端类，用来处理web
 * @author jcai
 */
class RpcHttpClientImpl(extensionRegistry: ExtensionRegistry) extends RpcHttpClient{
  /**
   * 通过protobuf的数据来调用远程的url
   * @param url web application url
   */
  def call[T](url: String,extension: GeneratedExtension[BaseCommand, T], value: T):BaseCommand={
    val httpClient: CloseableHttpClient = createHttpClient
    try {
      val post: HttpPost = new HttpPost(url)
      post.setHeader(HallStreamConstants.PROTOBUF_HEADER,HallStreamConstants.PROTOBUF_HEADER_VALUE)
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
        else throw new RuntimeException(response.getStatusLine.toString)
      }
      finally {
        close(response)
      }
    }
    catch {
      case e: IOException =>
        throw new RuntimeException(e)

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


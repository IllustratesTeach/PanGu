// Copyright 2014,2015 Jun Tsai. All rights reserved.
// site: http://www.ganshane.com
package nirvana.hall.api.internal

import java.io.{Closeable, IOException}

import com.google.protobuf.GeneratedMessage.GeneratedExtension
import com.google.protobuf.{GeneratedMessage, Message}
import nirvana.hall.protocol.sys.CommonProto.BaseRequest
import nirvana.hall.support.HallSupportConstants
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
object WebHttpClientUtils {

  /**
   * 通过protobuf的数据来调用远程的url
   * @param url web application url
   * @param request  调用请求类
   * @param responseBuilder 处理结果类
   */
  def call(url: String, request: GeneratedMessage, responseBuilder: Message.Builder) {
    val httpClient: CloseableHttpClient = createHttpClient
    try {
      val post: HttpPost = new HttpPost(url)
      post.setHeader("X-Hall-Request","ok");
      val reqEntity: ByteArrayEntity = new ByteArrayEntity(request.toByteArray)
      post.setEntity(reqEntity)
      val response: CloseableHttpResponse = httpClient.execute(post)
      try {
        if (response.getStatusLine.getStatusCode == 200) {
          val entity: HttpEntity = response.getEntity
          try {
            responseBuilder.mergeFrom(entity.getContent)
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

  def call[T](url: String,extension: GeneratedExtension[BaseRequest, T], request: T,  responseBuilder: Message.Builder) {
    val httpClient: CloseableHttpClient = createHttpClient
    try {
      val post: HttpPost = new HttpPost(url)
      post.setHeader(HallSupportConstants.HTTP_PROTOBUF_HEADER, HallSupportConstants.HTTP_PROTOBUF_HEADER_VALUE)
      val baseRequest= BaseRequest.newBuilder().setExtension(extension, request).setToken("1").setVersion(1).build()

      val reqEntity: ByteArrayEntity = new ByteArrayEntity(baseRequest.toByteArray)
      post.setEntity(reqEntity)
      val response: CloseableHttpResponse = httpClient.execute(post)
      try {
        if (response.getStatusLine.getStatusCode == 200) {
          val entity: HttpEntity = response.getEntity
          try {
            responseBuilder.mergeFrom(entity.getContent)
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
      setConnectTimeout(30 * 1000). //连接超时设置
      setSocketTimeout(30 * 1000). //读取时间设置
      build
    HttpClientBuilder.create.setDefaultRequestConfig(defaultConfig).setUserAgent("nirvana-hall/1.0").build
  }
}


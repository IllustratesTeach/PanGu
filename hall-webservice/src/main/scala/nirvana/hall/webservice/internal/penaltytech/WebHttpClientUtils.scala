package nirvana.hall.webservice.internal.penaltytech

import java.io.{Closeable, IOException}
import java.util

import monad.support.services.LoggerSupport
import org.apache.http.{HttpEntity, NameValuePair}
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.{CloseableHttpResponse, HttpPost}
import org.apache.http.impl.client.{CloseableHttpClient, HttpClientBuilder}
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils

import scala.util.control.NonFatal

/**
  * Created by liukai on 2018/12/11.
  */
object WebHttpClientUtils extends LoggerSupport {


  def call(url: String, json: String): String = {
    val httpClient: CloseableHttpClient = createHttpClient
    var entityStr = ""
    try {
      val post: HttpPost = new HttpPost(url)
      val nameValuePairs = new util.ArrayList[NameValuePair]()
      nameValuePairs.add(new BasicNameValuePair("content", json))
      post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"))
      val response: CloseableHttpResponse = httpClient.execute(post)
      try {
        if (response.getStatusLine.getStatusCode == 200) {
          val entity: HttpEntity = response.getEntity
          try {
            if (entity != null) {
              entityStr = EntityUtils.toString(entity, "utf-8")
            }
          } finally {
            EntityUtils.consume(entity)
          }
        }
        else throw new RuntimeException(response.getStatusLine.toString)
      }
      finally {
        close(response)
      }

    } catch {
      case e: IOException =>
        throw new RuntimeException(e)
    }
    finally {
      close(httpClient)
    }
    entityStr
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
      setConnectTimeout(300 * 1000). //连接超时设置
      setSocketTimeout(300 * 1000). //读取时间设置
      build
    HttpClientBuilder.create().setDefaultRequestConfig(defaultConfig).build()
  }

}

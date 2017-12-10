// Copyright 2014,2015,2016 the original author or authors. All rights reserved.
// site: http://www.ganshane.com
package nirvana.hall.support.services

import java.io._
import java.net.URL
import javax.xml.XMLConstants
import javax.xml.bind.util.ValidationEventCollector
import javax.xml.bind.{JAXBContext, Marshaller}
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory

import com.sun.org.apache.xerces.internal.dom.DOMInputImpl
import monad.support.MonadSupportConstants
import monad.support.services.{MonadException, MonadSupportErrorCode, SymbolExpander}
import org.w3c.dom.ls.{LSInput, LSResourceResolver}

import scala.collection.mutable
import scala.io.Source
import scala.util.control.NonFatal

/**
  * 使用Jaxb方式解析XML配置文件
  *
  * @author jcai
  * @version 0.1
  */
object XmlLoader {
  def loadConfig[T <: Object](clazz: Class[T], filePath: String): T = {
    loadConfig[T](filePath)(Manifest.classType(clazz))
  }

  /** 加载某一个配置 **/
  def loadConfig[T <: Object](filePath: String,
                              symbols: Map[String, String] = Map[String, String](),
                              encoding: String = MonadSupportConstants.UTF8_ENCODING)(implicit m: Manifest[T]): T = {
    val content = Source.fromFile(filePath, encoding).mkString
    parseXML(content, symbols, encoding)
  }

  def parseXML[T <: Object](content: String,
                            symbols: Map[String, String] = Map[String, String](),
                            encoding: String = MonadSupportConstants.UTF8_ENCODING,
                            xsd: Option[InputStream] = None, basePath: String = null)(implicit m: Manifest[T]): T = {
    parseXML(new ByteArrayInputStream(SymbolExpander.expand(content, symbols).getBytes(encoding)), xsd, basePath)
  }

  def parseXML[T <: Object](is: InputStream, xsd: Option[InputStream], basePath: String)(implicit m: Manifest[T]): T = {
    val vec = new ValidationEventCollector()
    try {
      //obtain type parameter
      val clazz = m.runtimeClass.asInstanceOf[Class[T]]
      //create io reader
      val reader = new InputStreamReader(is, MonadSupportConstants.UTF8_ENCODING)
      val context = JAXBContext.newInstance(clazz)
      //unmarshal xml
      val unmarshaller = context.createUnmarshaller()
      //.unmarshal(reader).asInstanceOf[T]
      if (xsd.isDefined) {
        val systemIdMap = mutable.Map[String, String]() //map存放加载的systemId
        val sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
        sf.setResourceResolver(new LSResourceResolver {
          override def resolveResource(`type`: String, namespaceURI: String, publicId: String, systemId: String, baseURI: String): LSInput = {
            val input = new DOMInputImpl()
            var inputStream = getClass.getResourceAsStream(systemId)
            if(systemIdMap.get(systemId).isEmpty){//同一个systemId，只加载一次
              systemIdMap.put(systemId, "")
              if(inputStream == null){
                if (systemId.endsWith("monad.xsd")) {//monad.xsd
                  inputStream = getClass.getResourceAsStream("/monad.xsd")
                }else if(systemId.startsWith("file:")){
                  if(systemId.startsWith("file:")){
                    inputStream = new FileInputStream(new URL(systemId).getPath)
                  }
                }else{
                  inputStream = getClass.getResourceAsStream(basePath+systemId)
                }
              }
            }
            input.setByteStream(inputStream)

            input
          }
        })
        val schemaSource = new StreamSource(xsd.get)
        val schema = sf.newSchema(schemaSource)
        unmarshaller.setSchema(schema)
        unmarshaller.setEventHandler(vec)
      }
      unmarshaller.unmarshal(reader).asInstanceOf[T]
    } catch {
      case NonFatal(e) =>
        throw MonadException.wrap(e, MonadSupportErrorCode.FAIL_PARSE_XML)
    } finally {
      close(is)
      if (xsd.isDefined)
        close(xsd.get)
      if (vec.hasEvents) {
        val veOption = vec.getEvents.headOption
        if (veOption.isDefined) {
          val ve = veOption.get
          val vel = ve.getLocator
          throw new MonadException(
            "line %s column %s :%s".format(vel.getLineNumber, vel.getColumnNumber, ve.getMessage),
            MonadSupportErrorCode.FAIL_PARSE_XML)
        }
      }
    }
  }

  private def close(io: Closeable) {
    try {
      io.close()
    } catch {
      case NonFatal(e) =>
    }
  }

  /**
    * 把对象转化为XML文件
    */
  def toXml[T](obj: T, encoding: String = MonadSupportConstants.UTF8_ENCODING): String = {
    val context = JAXBContext.newInstance(obj.getClass)
    val marshaller = context.createMarshaller()
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
    marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding)
    val out = new ByteArrayOutputStream
    marshaller.marshal(obj, out)
    new String(out.toByteArray, encoding)
  }
}

package nirvana.hall.webservice.internal.survey

import java.io.{Closeable, InputStream, InputStreamReader}
import javax.xml.bind.JAXBContext
import javax.xml.bind.util.ValidationEventCollector

import monad.support.MonadSupportConstants
import monad.support.services.{MonadException, MonadSupportErrorCode}

import scala.util.control.NonFatal

/**
  * Created by ssj on 2017/11/16.
  */
object XmlToObject {
  def parseXML[T <: Object](is: InputStream)(implicit m: Manifest[T]): T = {
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
      unmarshaller.unmarshal(reader).asInstanceOf[T]
    } catch {
      case NonFatal(e) =>
        throw MonadException.wrap(e, MonadSupportErrorCode.FAIL_PARSE_XML)
    } finally {
      close(is)
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
}

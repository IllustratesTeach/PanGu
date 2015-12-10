package nirvana.hall.v62.services

import java.util
import javax.xml.bind.annotation._

import monad.support.services.XmlLoader
import nirvana.hall.c.services.gbaselib.gafiserr.GAFISERRDATSTRUCT

import scala.collection.JavaConversions._
import scala.io.Source
import GafisException._

/**
 * exception from v62 system
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-07
 */
object GafisException{
  val UNKNOW_ERROR="UNKNOWN"
  private val resource:GAFIS_RESOURCE = loadResource
  def findMessageByCode(code:Int):Option[String] = {
    resource.items.find(_.id == code).map(_.value)
  }
  private def loadResource={
    val content = Source.fromInputStream(getClass.getResourceAsStream("/nirvana/hall/v62/gbaselibrc_zh.xml")).mkString
    XmlLoader.parseXML[GAFIS_RESOURCE](content)
  }
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name = "GAFIS_RESOURCE")
  @XmlRootElement(name = "GAFIS_RESOURCE")
  class GAFIS_RESOURCE {
    @XmlElementWrapper(name="STRINGTABLE")
    @XmlElement(name = "ITEM")
    var items = new util.ArrayList[ResourceItem]()
  }
  @XmlRootElement(name = "ITEM")
  class ResourceItem{
    @XmlAttribute(name = "id")
    var id:Int = _
    @XmlAttribute(name = "value")
    var value:String = _
  }
}
class GafisException(gafisError:GAFISERRDATSTRUCT) extends RuntimeException{
  /**
   * get error code
   * @return error code
   */
  def code:Int =  gafisError.nAFISErrno

  def getSimpleMessage: String = findMessageByCode(code).getOrElse(UNKNOW_ERROR) +"(%s)".format(code)
  /**
   * retrieve message
   * @return error message
   */
  override def getMessage: String = {
    val message = getSimpleMessage
    if(gafisError.szFileName != null)
      message + "\n" +"\tat %s:%s".format(gafisError.szFileName,gafisError.nLineNum)
    else
      message
  }
}

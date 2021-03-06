package nirvana.hall.v62.services

import java.util
import javax.xml.bind.annotation._

import monad.support.services.XmlLoader
import nirvana.hall.c.AncientConstants
import nirvana.hall.c.services.gbaselib.gafiserr.GAFISERRDATSTRUCT
import nirvana.hall.v62.services.GafisException._

import scala.collection.JavaConversions._
import scala.io.Source

/**
 * exception from v62 system
 *
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
 *
   * @return error code
   */
  def code:Int =  gafisError.nAFISErrno

  def getSimpleMessage: String = findMessageByCode(code).getOrElse(UNKNOW_ERROR) +"(%s)".format(code)
  /**
   * retrieve message
 *
   * @return error message
   */
  override def getMessage: String = {
    val message = getSimpleMessage
    val sb = new StringBuilder
    sb.append(message)

    if(gafisError.bnAFISErrData != null)
      sb.append("\n \t ErrorData: ").append(new String(gafisError.bnAFISErrData, AncientConstants.GBK_ENCODING).trim)

    if(gafisError.szFileName != null)
      sb.append( "\n" +"\tat %s:%s".format(gafisError.szFileName,gafisError.nLineNum))

    if(gafisError.szSysErrStr != null){
      sb.append( "\n" +"\tat %s:%s".format(gafisError.szSysErrStr,gafisError.nLineNum))
    }

    if(gafisError.stFileList != null && gafisError.stFileList.size > 0){
      gafisError.stFileList.map{
         m =>
           sb.append( "\n" +"\tat %s:%s".format(m.sFileName,m.nLineNum))
      }
      //sb.append( "\n" +"\tat %s:%s".format(gafisError.szSysErrStr,gafisError.nLineNum))
    }

    sb.toString()
  }
  def getFullMessage: String = {
    val message = getSimpleMessage
    val sb = new StringBuilder
    sb.append(message)
    if(gafisError.bnAFISErrData != null)
      sb.append("\n \t ErrorData: ").append(new String(gafisError.bnAFISErrData, AncientConstants.GBK_ENCODING).trim)

    if(gafisError.szFileName != null)
      sb.append( "\n" +"\tat %s:%s".format(gafisError.szFileName,gafisError.nLineNum))

    sb.toString()
  }
}

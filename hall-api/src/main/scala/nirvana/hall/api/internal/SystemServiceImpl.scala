package nirvana.hall.api.internal

import nirvana.hall.api.services.{ProtobufRequestGlobal, SystemService}
import org.apache.tapestry5.ioc.services.ClassNameLocator

import scala.collection.mutable.ListBuffer

/**
 * implements SystemService
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-06
 */
class SystemServiceImpl(protobufRequestGlobal: ProtobufRequestGlobal, classNameLocator: ClassNameLocator) extends SystemService {

  /*
  @Transactional
  @RequiresUser
  override def deleteEntity(entity: String, id: Int): Unit = {
    val userId = protobufRequestGlobal.userId
    SQL("delete from " + entity + " where id=? and user_id=?").bind(id, userId).executeUpdate().apply()
  }
  */


  override def findAllProtocol(): Seq[String] = {
    val packages = Seq("nirvana.hall.protocol.sys", "nirvana.hall.protocol.api")
    //val contextClassLoader = Thread.currentThread().getContextClassLoader
    val buff = new ListBuffer[String]()
    packages.foreach { packageName =>
      val it = classNameLocator.locateClassNames(packageName).iterator();
      while (it.hasNext) {
        buff += it.next()
      }
    }

    buff.filterNot(_.endsWith("CommonProto")).filterNot(_.endsWith("ModelProto")).toSeq
  }
}

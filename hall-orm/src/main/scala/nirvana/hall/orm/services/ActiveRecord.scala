package nirvana.hall.orm.services

import javax.persistence.Transient

import org.apache.tapestry5.ioc.ObjectLocator

import scala.reflect._

/**
 * active record
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-03
 */
object ActiveRecord {
  var objectLocator:ObjectLocator= _
  def save[T](record:T):T={
    getService[EntityService].save(record)
  }
  def delete[T](record:T): Unit ={
    getService[EntityService].delete(record)
  }
  protected def getService[T:ClassTag]:T={
    if(objectLocator == null)
      throw new IllegalStateException("object locator is null")
    objectLocator.getService(classTag[T].runtimeClass.asInstanceOf[Class[T]])
  }
}
trait ActiveRecord {
  @Transient
  def save():this.type = {
    ActiveRecord.save(this)
  }
  def delete(): Unit ={
    ActiveRecord.delete(this)
  }
}

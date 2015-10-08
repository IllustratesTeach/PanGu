package nirvana.hall.api.internal

import scala.reflect.{ ClassTag, classTag }

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-05-26
 */
object ScalaUtils {
  def currentTimeInSeconds = (System.currentTimeMillis() / 1000).toInt

  /**
   * Returns the [[java.lang.Class]] corresponding to the given class tag.
   *
   * @param tag the class tag to convert
   * @tparam T the tag's bound type
   * @return the runtime class of the tag
   */
  def tagToClass[T](tag: ClassTag[T]): Class[T] = {
    tag.runtimeClass.asInstanceOf[Class[T]]
  }

  /**
   * Returns the [[java.lang.Class]] corresponding to the given type.
   *
   * @tparam T the bound type to convert
   * @return the runtime class of the given type
   */
  def typeToClass[T: ClassTag]: Class[T] = {
    tagToClass(classTag[T])
  }
}

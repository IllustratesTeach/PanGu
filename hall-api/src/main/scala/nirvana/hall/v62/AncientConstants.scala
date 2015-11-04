package nirvana.hall.v62

import java.nio.charset.Charset

/**
 * constants for ancient system
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-30
 */
object AncientConstants {
  final val UTF8_ENCODING=Charset.forName("GBK")
  final val GBK_ENCODING=Charset.forName("GBK")
  final val OP_CLASS_TPLIB = 101.toShort
  final val OP_TPLIB_ADD = 50.toShort
  final val OP_CLASS_LPLIB = 102.toShort
  final val OP_LPLIB_ADD = 100.toShort
  final val OP_CLASS_CASE = 114.toShort
  final val OP_CASE_ADD = 250.toShort
}

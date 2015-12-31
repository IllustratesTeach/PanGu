package nirvana.hall.support

/**
 * hall support constants
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-18
 */
object HallSupportConstants {
  final val HALL_TEXT_LOGO = """ #
   __ _____   __   __
  / // / _ | / /  / /
 / _  / __ |/ /__/ /__ module : |@@|red %s|@#
/_//_/_/ |_/____/____/ version: |@@|yellow %s|@
                             """.replaceAll("#", "@|green ")
  val HTTP_PROTOBUF_HEADER = "X-Hall-Request"
  val HTTP_PROTOBUF_HEADER_VALUE = "ok"
}

package nirvana.hall.api

/**
 * constant for api moudle
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-06-02
 */
object HallApiConstants {
  final val PROTOBUF_CONTEXT = "application/protobuf"
  //http://bigtext.org/?font=smslant&text=HALL
  final val HALL_TEXT_LOGO = """ #
   __ _____   __   __
  / // / _ | / /  / /
 / _  / __ |/ /__/ /__ module : |@@|red %s|@#
/_//_/_/ |_/____/____/ version: |@@|yellow %s|@
                               """.replaceAll("#", "@|green ")
}

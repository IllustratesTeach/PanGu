package nirvana.hall.c.services.gloclib

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-19
  */
object galocpkg {
  //package type
  final val PKG_TYPE_DEFAULT = 0x0000
  final val PKG_TYPE_TPCARD = 0x0001
  final val PKG_TYPE_LPCARD = 0x0002
  final val PKG_TYPE_CASE = 0x0003
  final val PKG_TYPE_QUERY = 0x0004
  final val PKG_TYPE_MSG = 0x0005

  final val PKG_TYPE_FPTFILE = 0x00FE

  final val PKG_TYPE_RMTSERVER = 0x0100
  final val PKG_TYPE_RMTAUTOTRANSQUEUE = 0x0101
  final val PKG_TYPE_RMTGETKEYLISTQUEUE = 0x0102
  final val PKG_TYPE_RMTAUTOSENDQUERYCFG = 0x0103

}

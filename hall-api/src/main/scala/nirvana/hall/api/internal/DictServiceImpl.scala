package nirvana.hall.api.internal

import nirvana.hall.api.entities._
import nirvana.hall.api.services.DictService
import nirvana.hall.protocol.sys.DictListProto.DictListRequest.DictType

/**
 * Created by songpeng on 15/10/28.
 */
class DictServiceImpl extends DictService{
  override def findDictList(dictType: DictType): Seq[(String, String)] = {
    dictType match {
      case DictType.AJLB =>
        CodeAjlb.findAll().map(f => (f.code, f.name.toString))
      case DictType.XB =>
        CodeXb.findAll().map(f => (f.code, f.name.toString))
      case DictType.MZ =>
        CodeMz.findAll().map(f => (f.code, f.name.toString))
      case DictType.GJ =>
        CodeGj.findAll().map(f => (f.code, f.name.toString))
      case DictType.ZZMM =>
        CodeZzmm.findAll().map(f => (f.code, f.name.toString))
      case DictType.WHCD =>
        CodeWhcd.findAll().map(f => (f.code, f.name.toString))
      case DictType.ZJXY =>
        CodeZjxy.findAll().map(f => (f.code, f.name.toString))
      case DictType.MARRY =>
        CodeMarry.findAll().map(f => (f.code, f.name.toString))
      case DictType.TSRQ =>
        CodeTsrq.findAll().map(f => (f.code, f.name.toString))
      case DictType.JJLY =>
        CodeJjly.findAll().map(f => (f.code, f.name.toString))
      case DictType.TSSF =>
        CodeTssf.findAll().map(f => (f.code, f.name.toString))
      case DictType.ZC =>
        CodeZc.findAll().map(f => (f.code, f.name.toString))
      case _ =>
        Seq()

    }
  }
}

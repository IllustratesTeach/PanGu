package nirvana.hall.v70.gz.sys

import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.internal.ScalaUtils
import nirvana.hall.protocol.sys.DictProto._
import nirvana.hall.v70.jpa._

/**
 * Created by songpeng on 15/11/4.
 * 字典服务
 */
class DictServiceImpl(config: HallApiConfig) extends DictService{

  /**
   * 将scala对象转换为SyncDict类型
   * @param o
   * @return
   */
  def convert2CodeData(o: Any): DictData ={
    val data = CodeData.newBuilder()
    ScalaUtils.convertScalaToProtobuf(o, data)
    DictData.newBuilder().setExtension(CodeData.data, data.build()).build()
  }
  /**
   * 根据字典类型获取全部字典数据
   * @param dictType 字典类型
   * @return
   */
  override def findAllDict(dictType: DictType): Seq[DictData] = {
    dictType match {
      case DictType.CODE_AJLB =>
        CodeAjlb.all.map { f =>
          val data = CodeData.newBuilder()
          ScalaUtils.convertScalaToProtobuf(f, data)
          data.setOther(f.firstLetter)
          DictData.newBuilder().setExtension(CodeData.data, data.build()).build()
        }.toSeq
      case DictType.CODE_CJLB =>
        CodeCjlb.all.map(convert2CodeData).toSeq
      case DictType.CODE_GJ =>
        CodeGj.all.map(convert2CodeData).toSeq
      case DictType.CODE_JJLY =>
        CodeJjly.all.map(convert2CodeData).toSeq
      case DictType.CODE_LY =>
        CodeLy.all.map(convert2CodeData).toSeq
      case DictType.CODE_MARRY =>
        CodeMarry.all.map(convert2CodeData).toSeq
      case DictType.CODE_MZ =>
        CodeMz.all.map(convert2CodeData).toSeq
      case DictType.CODE_RYLX =>
        CodeRylx.all.map{ f =>
          val data = CodeData.newBuilder()
          ScalaUtils.convertScalaToProtobuf(f, data)
          data.setOther(f.strtype)
          DictData.newBuilder().setExtension(CodeData.data, data.build()).build()
        }.toSeq
      case DictType.CODE_TSRQ =>
        CodeTsrq.all.map(convert2CodeData).toSeq
      case DictType.CODE_TSSF =>
        CodeTssf.all.map(convert2CodeData).toSeq
      case DictType.CODE_WHCD =>
        CodeWhcd.all.map(convert2CodeData).toSeq
      case DictType.CODE_XB =>
        CodeXb.all.map(convert2CodeData).toSeq
      case DictType.CODE_ZC =>
        CodeZc.all.map(convert2CodeData).toSeq
      case DictType.CODE_ZJXY =>
        CodeZjxy.all.map(convert2CodeData).toSeq
      case DictType.CODE_ZZMM =>
        CodeZzmm.all.map(convert2CodeData).toSeq
      case DictType.GAFIS_GATHER_NODE =>
        GafisGatherNode.all.map{ f =>
          val data = GatherNode.newBuilder()
          ScalaUtils.convertScalaToProtobuf(f, data)
          DictData.newBuilder().setExtension(GatherNode.data, data.build()).build()
        }.toSeq
      case DictType.GAFIS_GATHER_TYPE =>
        GafisGatherType.all.map{ f =>
          val data = GatherType.newBuilder()
          ScalaUtils.convertScalaToProtobuf(f, data)
          DictData.newBuilder().setExtension(GatherType.data, data.build()).build()
        }.toSeq
      case DictType.GAFIS_GATHER_TYPE_NODE_FIELD =>
        GafisGatherTypeNodeField.all.map{ f =>
          val data = GatherTypeNodeField.newBuilder()
          ScalaUtils.convertScalaToProtobuf(f, data)
          DictData.newBuilder().setExtension(GatherTypeNodeField.data, data.build()).build()
        }.toSeq
      case DictType.SYS_DEPART =>
        SysDepart.all.map{ f =>
          val data = Depart.newBuilder()
          ScalaUtils.convertScalaToProtobuf(f, data)
          DictData.newBuilder().setExtension(Depart.data, data.build()).build()
        }.toSeq
      case _ =>
        Nil
    }
  }

  /**
   * 查询字典列表
   * @param dictType 字典类型
   * @return
   */
  override def findDictList(dictType: DictType, code: Option[String], name: Option[String], from: Int, size: Int): Seq[(String, String)] = {
    dictType match {
      case DictType.CODE_XB =>
        CodeXb.all.map(f => (f.code, f.name.toString))
      case DictType.CODE_MZ =>
        CodeMz.all.map(f => (f.code, f.name.toString))
      case DictType.CODE_GJ =>
        CodeGj.all.map(f => (f.code, f.name.toString))
      case DictType.CODE_ZZMM =>
        CodeZzmm.all.map(f => (f.code, f.name.toString))
      case DictType.CODE_WHCD =>
        CodeWhcd.all.map(f => (f.code, f.name.toString))
      case DictType.CODE_ZJXY =>
        CodeZjxy.all.map(f => (f.code, f.name.toString))
      case DictType.CODE_MARRY =>
        CodeMarry.all.map(f => (f.code, f.name.toString))
      case DictType.CODE_TSRQ =>
        CodeTsrq.all.map(f => (f.code, f.name.toString))
      case DictType.CODE_JJLY =>
        CodeJjly.all.map(f => (f.code, f.name.toString))
      case DictType.CODE_TSSF =>
        CodeTssf.all.map(f => (f.code, f.name.toString))
      case DictType.CODE_ZC =>
        CodeZc.all.map(f => (f.code, f.name.toString))
      case _ => Nil
    }
  }
}

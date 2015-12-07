package nirvana.hall.api.internal

import nirvana.hall.api.config.HallApiConfig
import nirvana.hall.api.entities._
import nirvana.hall.api.services.DictService
import nirvana.hall.protocol.sys.DictProto._

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
    //TODO 代码优化
    dictType match {
      case DictType.CODE_AJLB =>
        CodeAjlb.findAll().map { f =>
          val data = CodeData.newBuilder()
          ScalaUtils.convertScalaToProtobuf(f, data)
          f.firstLetter.foreach(data.setOther)
          DictData.newBuilder().setExtension(CodeData.data, data.build()).build()
        }.toSeq
      case DictType.CODE_CJLB =>
        CodeCjlb.findAll().map(convert2CodeData).toSeq
      case DictType.CODE_GJ =>
        CodeGj.findAll().map(convert2CodeData).toSeq
      case DictType.CODE_JJLY =>
        CodeJjly.findAll().map(convert2CodeData).toSeq
      case DictType.CODE_LY =>
        CodeLy.findAll().map(convert2CodeData).toSeq
      case DictType.CODE_MARRY =>
        CodeMarry.findAll().map(convert2CodeData).toSeq
      case DictType.CODE_MZ =>
        CodeMz.findAll().map(convert2CodeData).toSeq
      case DictType.CODE_RYLX =>
        CodeRylx.findAll().map{ f =>
          val data = CodeData.newBuilder()
          ScalaUtils.convertScalaToProtobuf(f, data)
          f.strtype.foreach(data.setOther)
          DictData.newBuilder().setExtension(CodeData.data, data.build()).build()
        }.toSeq
      case DictType.CODE_TSRQ =>
        CodeTsrq.findAll().map(convert2CodeData).toSeq
      case DictType.CODE_TSSF =>
        CodeTssf.findAll().map(convert2CodeData).toSeq
      case DictType.CODE_WHCD =>
        CodeWhcd.findAll().map(convert2CodeData).toSeq
      case DictType.CODE_XB =>
        CodeXb.findAll().map(convert2CodeData).toSeq
      case DictType.CODE_ZC =>
        CodeZc.findAll().map(convert2CodeData).toSeq
      case DictType.CODE_ZJXY =>
        CodeZjxy.findAll().map(convert2CodeData).toSeq
      case DictType.CODE_ZZMM =>
        CodeZzmm.findAll().map(convert2CodeData).toSeq
      case DictType.GAFIS_GATHER_NODE =>
        GafisGatherNode.findAll().map{ f =>
          val data = GatherNode.newBuilder()
          ScalaUtils.convertScalaToProtobuf(f, data)
          DictData.newBuilder().setExtension(GatherNode.data, data.build()).build()
        }.toSeq
      case DictType.GAFIS_GATHER_TYPE =>
        GafisGatherType.findAll().map{ f =>
          val data = GatherType.newBuilder()
          ScalaUtils.convertScalaToProtobuf(f, data)
          DictData.newBuilder().setExtension(GatherType.data, data.build()).build()
        }.toSeq
      case DictType.GAFIS_GATHER_TYPE_NODE_FIELD =>
        GafisGatherTypeNodeField.findAll().map{ f =>
          val data = GatherTypeNodeField.newBuilder()
          ScalaUtils.convertScalaToProtobuf(f, data)
          DictData.newBuilder().setExtension(GatherTypeNodeField.data, data.build()).build()
        }.toSeq
      case DictType.SYS_DEPART =>
        SysDepart.findAll().map{ f =>
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
    // TODO 实现根据编号和名称检索以及分页，代码优化
    dictType match {
      case DictType.CODE_XB =>
        CodeXb.findAll().map(f => (f.code, f.name.toString))
      case DictType.CODE_MZ =>
        CodeMz.findAll().map(f => (f.code, f.name.toString))
      case DictType.CODE_GJ =>
        CodeGj.findAll().map(f => (f.code, f.name.toString))
      case DictType.CODE_ZZMM =>
        CodeZzmm.findAll().map(f => (f.code, f.name.toString))
      case DictType.CODE_WHCD =>
        CodeWhcd.findAll().map(f => (f.code, f.name.toString))
      case DictType.CODE_ZJXY =>
        CodeZjxy.findAll().map(f => (f.code, f.name.toString))
      case DictType.CODE_MARRY =>
        CodeMarry.findAll().map(f => (f.code, f.name.toString))
      case DictType.CODE_TSRQ =>
        CodeTsrq.findAll().map(f => (f.code, f.name.toString))
      case DictType.CODE_JJLY =>
        CodeJjly.findAll().map(f => (f.code, f.name.toString))
      case DictType.CODE_TSSF =>
        CodeTssf.findAll().map(f => (f.code, f.name.toString))
      case DictType.CODE_ZC =>
        CodeZc.findAll().map(f => (f.code, f.name.toString))
      case _ => Nil
    }
  }
}

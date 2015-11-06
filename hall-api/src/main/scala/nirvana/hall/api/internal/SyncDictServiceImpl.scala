package nirvana.hall.api.internal

import nirvana.hall.api.entities._
import nirvana.hall.api.services.SyncDictService
import nirvana.hall.protocol.sys.SyncDictProto.SyncDictResponse._
import nirvana.hall.protocol.sys.SyncDictProto.{SyncDictRequest, DictType, SyncDictResponse}
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.springframework.transaction.annotation.Transactional
import scalikejdbc._
import scala.collection.JavaConversions._

/**
 * Created by songpeng on 15/11/4.
 * 字典同步服务
 */
class SyncDictServiceImpl extends SyncDictService{
  private val REMOTE_SERVICE_URL = "http://127.0.0.1:9081";

  /**
   * 访问远程服务器，同步字典
   */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    periodicExecutor.addJob(new CronSchedule("0 0 0 * * ? *"), "sync-remote-dict", new Runnable {
      override def run(): Unit = {
        val request = SyncDictRequest.newBuilder()
        val response = SyncDictResponse.newBuilder()
        DictType.values().foreach{ f =>
          request.setDictType(f)
          WebAppClientUtils.call(REMOTE_SERVICE_URL, request.build(), response)
          syncDict(f, response.getSyncDataList.toSeq)
        }
      }
    })
  }

  /**
   * 将scala对象转换为SyncDict类型
   * @param o
   * @return
   */
  def convert2CodeData(o: Any): SyncData ={
    val data = CodeData.newBuilder()
    ScalaUtils.convertScalaToProtobuf(o, data)
    SyncData.newBuilder().setExtension(CodeData.data, data.build()).build()
  }
  /**
   * 根据字典类型获取全部字典数据
   * @param dictType 字典类型
   * @return
   */
  override def findAllDict(dictType: DictType): Seq[SyncDictResponse.SyncData] = {
    dictType match {
      case DictType.CODE_AJLB =>
        CodeAjlb.findAll().map { f =>
          val data = CodeData.newBuilder()
          ScalaUtils.convertScalaToProtobuf(f, data)
          f.firstLetter.foreach(data.setOther)
          SyncData.newBuilder().setExtension(CodeData.data, data.build()).build()
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
          SyncData.newBuilder().setExtension(CodeData.data, data.build()).build()
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
          SyncData.newBuilder().setExtension(GatherNode.data, data.build()).build()
        }.toSeq
      case DictType.GAFIS_GATHER_TYPE =>
        GafisGatherType.findAll().map{ f =>
          val data = GatherType.newBuilder()
          ScalaUtils.convertScalaToProtobuf(f, data)
          SyncData.newBuilder().setExtension(GatherType.data, data.build()).build()
        }.toSeq
      case DictType.GAFIS_GATHER_TYPE_NODE_FIELD =>
        GafisGatherTypeNodeField.findAll().map{ f =>
          val data = GatherTypeNodeField.newBuilder()
          ScalaUtils.convertScalaToProtobuf(f, data)
          SyncData.newBuilder().setExtension(GatherTypeNodeField.data, data.build()).build()
        }.toSeq
      case DictType.SYS_DEPART =>
        SysDepart.findAll().map{ f =>
          val data = Depart.newBuilder()
          ScalaUtils.convertScalaToProtobuf(f, data)
          SyncData.newBuilder().setExtension(Depart.data, data.build()).build()
        }.toSeq
      case _ =>
        Nil
    }
  }

  /**
   * 同步字典
   * @param dictType 字典类型
   * @param syncDataList 要同步的字典数据
   * @param session
   * @return
   */
  @Transactional
  override def syncDict(dictType: DictType, syncDataList: Seq[SyncData])(implicit session: DBSession): Unit = {
    //删除原有的数据
    deleteDictData(dictType)
    syncDataList.foreach{ f =>
      if(f.hasExtension(CodeData.data)){
        val data = f.getExtension(CodeData.data)
        dictType match {
          case DictType.CODE_AJLB =>
            val code = ScalaUtils.convertProtobufToScala[CodeAjlb](data)
            CodeAjlb.create(code.code,code.name,code.deleteFlag,code.ord,code.remark,Option(data.getOther()))
          case DictType.CODE_CJLB =>
            val code = ScalaUtils.convertProtobufToScala[CodeCjlb](data)
            CodeCjlb.create(code.code,code.name,code.deleteFlag,code.ord,code.remark)
          case DictType.CODE_GJ =>
            val code = ScalaUtils.convertProtobufToScala[CodeGj](data)
            CodeGj.create(code.code,code.name,code.deleteFlag,code.ord,code.remark)
          case DictType.CODE_JJLY =>
            val code = ScalaUtils.convertProtobufToScala[CodeJjly](data)
            CodeJjly.create(code.code,code.name,code.deleteFlag,code.ord,code.remark)
          case DictType.CODE_LY =>
            val code = ScalaUtils.convertProtobufToScala[CodeLy](data)
            CodeLy.create(code.code,code.name,code.deleteFlag,code.ord,code.remark)
          case DictType.CODE_MARRY =>
            val code = ScalaUtils.convertProtobufToScala[CodeMarry](data)
            CodeMarry.create(code.code,code.name,code.deleteFlag,code.ord,code.remark)
          case DictType.CODE_MZ =>
            val code = ScalaUtils.convertProtobufToScala[CodeMz](data)
            CodeMz.create(code.code,code.name,code.deleteFlag,code.ord,code.remark)
          case DictType.CODE_RYLX =>
            val code = ScalaUtils.convertProtobufToScala[CodeRylx](data)
            CodeRylx.create(code.code,code.name,code.deleteFlag,code.ord,code.remark)
          case DictType.CODE_TSRQ =>
            val code = ScalaUtils.convertProtobufToScala[CodeTsrq](data)
            CodeTsrq.create(code.code,code.name,code.deleteFlag,code.ord,code.remark)
          case DictType.CODE_TSSF =>
            val code = ScalaUtils.convertProtobufToScala[CodeTssf](data)
            CodeTssf.create(code.code,code.name,code.deleteFlag,code.ord,code.remark)
          case DictType.CODE_WHCD =>
            val code = ScalaUtils.convertProtobufToScala[CodeWhcd](data)
            CodeWhcd.create(code.code,code.name,code.deleteFlag,code.ord,code.remark)
          case DictType.CODE_XB =>
            val code = ScalaUtils.convertProtobufToScala[CodeXb](data)
            CodeXb.create(code.code,code.name,code.deleteFlag,code.ord,code.remark)
          case DictType.CODE_ZC =>
            val code = ScalaUtils.convertProtobufToScala[CodeZc](data)
            CodeZc.create(code.code,code.name,code.deleteFlag,code.ord,code.remark)
          case DictType.CODE_ZJXY =>
            val code = ScalaUtils.convertProtobufToScala[CodeZjxy](data)
            CodeZjxy.create(code.code,code.name,code.deleteFlag,code.ord,code.remark)
          case DictType.CODE_ZZMM =>
            val code = ScalaUtils.convertProtobufToScala[CodeZzmm](data)
            CodeZzmm.create(code.code,code.name,code.deleteFlag,code.ord,code.remark)
          case _ =>
            Nil
        }
      }
      else if(f.hasExtension(GatherType.data)){
        val data = f.getExtension(GatherType.data)
        val o = ScalaUtils.convertProtobufToScala[GafisGatherType](data)
        GafisGatherType.create(o.pkId,o.typeName,o.deleteFlag,o.createUserId,o.createDatetime,o.updateUserId,o.updateDatetime,o.menuId,o.personCategory,o.gatherCategory,o.parentId,o.ischildren,o.ruleId)
      }
      else if(f.hasExtension(GatherNode.data)){
        val data = f.getExtension(GatherNode.data)
        val o = ScalaUtils.convertProtobufToScala[GafisGatherNode](data)
        GafisGatherNode.create(o.pkId,o.nodeCode,o.nodeName,o.nodeRequest,o.deleteFlag,o.createUserId,o.createDatetime,o.updateUserId,o.updateDatetime,o.nodeImg)
      }
      else if(f.hasExtension(GatherTypeNodeField.data)){
        val data = f.getExtension(GatherTypeNodeField.data)
        val o = ScalaUtils.convertProtobufToScala[GafisGatherTypeNodeField](data)
        GafisGatherTypeNodeField.create(o.pkId,o.typeId,o.nodeId,o.fieldId,o.required,o.departId)
      }
      else if(f.hasExtension(Depart.data)){
        val data = f.getExtension(Depart.data)
        val o = ScalaUtils.convertProtobufToScala[SysDepart](data)
        SysDepart.create(o.code,o.name,o.leader,o.remark,o.deleteFlag,o.isLeaf,o.parentId,o.deptLevel,o.longitude,o.latitude,o.phone,o.longName,o.isHaveChamber,o.chamberType,o.isSpecial,o.integrationType)
      }
    }

  }

  /**
   * 根据字典类型删除字典数据
   * @param dictType 字典类型
   * @param session
   * @return
   */
  def deleteDictData(dictType: DictType)(implicit session: DBSession): Unit ={
    val tableName = dictType.toString
    if(!tableName.isEmpty)
      SQL("delete from "+ tableName).update().apply()
  }

}

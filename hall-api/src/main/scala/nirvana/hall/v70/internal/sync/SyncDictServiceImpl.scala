package nirvana.hall.v70.internal.sync

import nirvana.hall.api.internal.{ScalaUtils, WebHttpClientUtils}
import nirvana.hall.v70.services.sync.SyncDictService
import nirvana.hall.protocol.sys.DictProto._
import nirvana.hall.protocol.sys.SyncDictProto.{SyncDictRequest, SyncDictResponse}
import nirvana.hall.v70.config.HallV70Config
import nirvana.hall.v70.jpa._
import org.apache.tapestry5.ioc.annotations.PostInjection
import org.apache.tapestry5.ioc.services.cron.{CronSchedule, PeriodicExecutor}
import org.springframework.transaction.annotation.Transactional

import scala.collection.JavaConversions._

/**
 * Created by songpeng on 15/12/7.
 */
class SyncDictServiceImpl(config: HallV70Config) extends SyncDictService{
  /**
   * 访问远程服务器，同步字典
   */
  @PostInjection
  def startUp(periodicExecutor: PeriodicExecutor): Unit = {
    if(config.syncDict != null){
      periodicExecutor.addJob(new CronSchedule(config.syncDict.cron), "sync-remote-dict", new Runnable {
        override def run(): Unit = {
          val request = SyncDictRequest.newBuilder()
          val response = SyncDictResponse.newBuilder()
          DictType.values().foreach{ f =>
            request.setDictType(f)
            WebHttpClientUtils.call(config.syncDict.url, request.build(), response)
            syncDict(f, response.getDictDataList.toSeq)
          }
        }
      })
    }
  }

  /**
   * 同步字典
   * @param dictType 字典类型
   * @param dictDataList 要同步的字典数据
   * @return
   */
  @Transactional
  def syncDict(dictType: DictType, dictDataList: Seq[DictData]): Unit = {
    //删除原有的数据
    deleteDictData(dictType)
    dictDataList.foreach{ f =>
      if(f.hasExtension(CodeData.data)){
        val data = f.getExtension(CodeData.data)
        //TODO 代码优化
        dictType match {
          case DictType.CODE_AJLB =>
            val code = ScalaUtils.convertProtobufToScala[CodeAjlb](data)
            new CodeAjlb(code.code,code.name,code.deleteFlag,code.ord,code.remark,data.getOther()).save()
          case DictType.CODE_CJLB =>
            val code = ScalaUtils.convertProtobufToScala[CodeCjlb](data)
            new CodeCjlb(code.code,code.name,code.deleteFlag,code.ord,code.remark).save()
          case DictType.CODE_GJ =>
            val code = ScalaUtils.convertProtobufToScala[CodeGj](data)
            new CodeGj(code.code,code.name,code.deleteFlag,code.ord,code.remark).save()
          case DictType.CODE_JJLY =>
            val code = ScalaUtils.convertProtobufToScala[CodeJjly](data)
            new CodeJjly(code.code,code.name,code.deleteFlag,code.ord,code.remark).save()
          case DictType.CODE_LY =>
            val code = ScalaUtils.convertProtobufToScala[CodeLy](data)
            new CodeLy(code.code,code.name,code.deleteFlag,code.ord,code.remark).save()
          case DictType.CODE_MARRY =>
            val code = ScalaUtils.convertProtobufToScala[CodeMarry](data)
            new CodeMarry(code.code,code.name,code.deleteFlag,code.ord,code.remark).save()
          case DictType.CODE_MZ =>
            val code = ScalaUtils.convertProtobufToScala[CodeMz](data)
            new CodeMz(code.code,code.name,code.deleteFlag,code.ord,code.remark).save()
          case DictType.CODE_RYLX =>
            val code = ScalaUtils.convertProtobufToScala[CodeRylx](data)
            new CodeRylx(code.code,code.name,code.deleteFlag,code.ord,code.remark).save()
          case DictType.CODE_TSRQ =>
            val code = ScalaUtils.convertProtobufToScala[CodeTsrq](data)
            new CodeTsrq(code.code,code.name,code.deleteFlag,code.ord,code.remark).save()
          case DictType.CODE_TSSF =>
            val code = ScalaUtils.convertProtobufToScala[CodeTssf](data)
            new CodeTssf(code.code,code.name,code.deleteFlag,code.ord,code.remark).save()
          case DictType.CODE_WHCD =>
            val code = ScalaUtils.convertProtobufToScala[CodeWhcd](data)
            new CodeWhcd(code.code,code.name,code.deleteFlag,code.ord,code.remark).save()
          case DictType.CODE_XB =>
            val code = ScalaUtils.convertProtobufToScala[CodeXb](data)
            new CodeXb(code.code,code.name,code.deleteFlag,code.ord,code.remark).save()
          case DictType.CODE_ZC =>
            val code = ScalaUtils.convertProtobufToScala[CodeZc](data)
            new CodeZc(code.code,code.name,code.deleteFlag,code.ord,code.remark).save()
          case DictType.CODE_ZJXY =>
            val code = ScalaUtils.convertProtobufToScala[CodeZjxy](data)
            new CodeZjxy(code.code,code.name,code.deleteFlag,code.ord,code.remark).save()
          case DictType.CODE_ZZMM =>
            val code = ScalaUtils.convertProtobufToScala[CodeZzmm](data)
            new CodeZzmm(code.code,code.name,code.deleteFlag,code.ord,code.remark).save()
          case _ =>
            Nil
        }
      }
      else if(f.hasExtension(GatherType.data)){
        val data = f.getExtension(GatherType.data)
        val o = ScalaUtils.convertProtobufToScala[GafisGatherType](data)
        o.save()
        //new GafisGatherType(o.pkId,o.typeName,o.deleteFlag,o.createUserId,o.createDatetime,o.updateUserId,o.updateDatetime,o.menuId,o.personCategory,o.gatherCategory,o.parentId,o.ischildren,o.ruleId).save()
      }
      else if(f.hasExtension(GatherNode.data)){
        val data = f.getExtension(GatherNode.data)
        val o = ScalaUtils.convertProtobufToScala[GafisGatherNode](data)
        o.save()
        //new GafisGatherNode(o.pkId,o.nodeCode,o.nodeName,o.nodeRequest,o.deleteFlag,o.createUserId,o.createDatetime,o.updateUserId,o.updateDatetime,o.nodeImg).save()
      }
      else if(f.hasExtension(GatherTypeNodeField.data)){
        val data = f.getExtension(GatherTypeNodeField.data)
        val o = ScalaUtils.convertProtobufToScala[GafisGatherTypeNodeField](data)
        o.save()
        //new GafisGatherTypeNodeField(o.pkId,o.typeId,o.nodeId,o.fieldId,o.required,o.departId).save()
      }
      else if(f.hasExtension(Depart.data)){
        val data = f.getExtension(Depart.data)
        val o = ScalaUtils.convertProtobufToScala[SysDepart](data)
        o.save()
        //new SysDepart(o.code,o.name,o.leader,o.remark,o.deleteFlag,o.isLeaf,o.parentId,o.deptLevel,o.longitude,o.latitude,o.phone,o.longName,o.isHaveChamber,o.chamberType,o.isSpecial,o.integrationType).save()
      }
    }

  }

  /**
   * 根据字典类型删除字典数据
   * @param dictType 字典类型
   * @return
   */
  def deleteDictData(dictType: DictType): Unit ={
    //TODO
    throw new UnsupportedOperationException
/*    val tableName = dictType.toString
    if(!tableName.isEmpty)
      SQL("delete from "+ tableName).update().apply()*/
  }

}

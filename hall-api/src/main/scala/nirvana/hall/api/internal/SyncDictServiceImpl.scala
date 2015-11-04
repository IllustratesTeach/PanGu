package nirvana.hall.api.internal

import nirvana.hall.api.entities._
import nirvana.hall.api.services.SyncDictService
import nirvana.hall.protocol.sys.SyncDictProto.SyncDictResponse._
import nirvana.hall.protocol.sys.SyncDictProto.{SyncDictResponse, DictType}

/**
 * Created by songpeng on 15/11/4.
 * 字典同步服务
 */
class SyncDictServiceImpl extends SyncDictService{

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
          data.setCode(f.code)
          f.name.foreach(data.setName)
          f.deleteFlag.foreach(data.setDeleteFlag)
          f.remark.foreach(data.setRemark)
          f.ord.foreach(data.setOrd)
          f.firstLetter.foreach(data.setOther)
          SyncData.newBuilder().setExtension(CodeData.data, data.build()).build()
        }.toSeq
      case DictType.CODE_CJLB =>
        CodeCjlb.findAll().map{ f =>
          val data = CodeData.newBuilder()
          data.setCode(f.code)
          f.name.foreach(data.setName)
          f.deleteFlag.foreach(data.setDeleteFlag)
          f.remark.foreach(data.setRemark)
          f.ord.foreach(data.setOrd)
          SyncData.newBuilder().setExtension(CodeData.data, data.build()).build()
        }.toSeq
      case DictType.CODE_GJ =>
        CodeGj.findAll().map{ f =>
          val data = CodeData.newBuilder()
          data.setCode(f.code)
          f.name.foreach(data.setName)
          f.deleteFlag.foreach(data.setDeleteFlag)
          f.remark.foreach(data.setRemark)
          f.ord.foreach(data.setOrd)
          SyncData.newBuilder().setExtension(CodeData.data, data.build()).build()
        }.toSeq
      case DictType.CODE_JJLY =>
        CodeJjly.findAll().map{ f =>
          val data = CodeData.newBuilder()
          data.setCode(f.code)
          f.name.foreach(data.setName)
          f.deleteFlag.foreach(data.setDeleteFlag)
          f.remark.foreach(data.setRemark)
          f.ord.foreach(data.setOrd)
          SyncData.newBuilder().setExtension(CodeData.data, data.build()).build()
        }.toSeq
      case DictType.CODE_LY =>
        CodeLy.findAll().map{ f =>
          val data = CodeData.newBuilder()
          data.setCode(f.code)
          f.name.foreach(data.setName)
          f.deleteFlag.foreach(data.setDeleteFlag)
          f.remark.foreach(data.setRemark)
          f.ord.foreach(data.setOrd)
          SyncData.newBuilder().setExtension(CodeData.data, data.build()).build()
        }.toSeq
      case DictType.CODE_MARRY =>
        CodeMarry.findAll().map{ f =>
          val data = CodeData.newBuilder()
          data.setCode(f.code)
          f.name.foreach(data.setName)
          f.deleteFlag.foreach(data.setDeleteFlag)
          f.remark.foreach(data.setRemark)
          f.ord.foreach(data.setOrd)
          SyncData.newBuilder().setExtension(CodeData.data, data.build()).build()
        }.toSeq
      case DictType.CODE_MZ =>
        CodeMz.findAll().map{ f =>
          val data = CodeData.newBuilder()
          data.setCode(f.code)
          f.name.foreach(data.setName)
          f.deleteFlag.foreach(data.setDeleteFlag)
          f.remark.foreach(data.setRemark)
          f.ord.foreach(data.setOrd)
          SyncData.newBuilder().setExtension(CodeData.data, data.build()).build()
        }.toSeq
      case DictType.CODE_RYLX =>
        CodeRylx.findAll().map{ f =>
          val data = CodeData.newBuilder()
          data.setCode(f.code)
          f.name.foreach(data.setName)
          f.deleteFlag.foreach(data.setDeleteFlag)
          f.remark.foreach(data.setRemark)
          f.ord.foreach(data.setOrd)
          f.strtype.foreach(data.setOther)
          data.build()
          SyncData.newBuilder().setExtension(CodeData.data, data.build()).build()
        }.toSeq
      case DictType.CODE_TSRQ =>
        CodeTsrq.findAll().map{ f =>
          val data = CodeData.newBuilder()
          data.setCode(f.code)
          f.name.foreach(data.setName)
          f.deleteFlag.foreach(data.setDeleteFlag)
          f.remark.foreach(data.setRemark)
          f.ord.foreach(data.setOrd)
          SyncData.newBuilder().setExtension(CodeData.data, data.build()).build()
        }.toSeq
      case DictType.CODE_TSSF =>
        CodeTssf.findAll().map{ f =>
          val data = CodeData.newBuilder()
          data.setCode(f.code)
          f.name.foreach(data.setName)
          f.deleteFlag.foreach(data.setDeleteFlag)
          f.remark.foreach(data.setRemark)
          f.ord.foreach(data.setOrd)
          SyncData.newBuilder().setExtension(CodeData.data, data.build()).build()
        }.toSeq
      case DictType.CODE_WHCD =>
        CodeWhcd.findAll().map{ f =>
          val data = CodeData.newBuilder()
          data.setCode(f.code)
          f.name.foreach(data.setName)
          f.deleteFlag.foreach(data.setDeleteFlag)
          f.remark.foreach(data.setRemark)
          f.ord.foreach(data.setOrd)
          SyncData.newBuilder().setExtension(CodeData.data, data.build()).build()
        }.toSeq
      case DictType.CODE_XB =>
        CodeXb.findAll().map{ f =>
          val data = CodeData.newBuilder()
          data.setCode(f.code)
          f.name.foreach(data.setName)
          f.deleteFlag.foreach(data.setDeleteFlag)
          f.remark.foreach(data.setRemark)
          f.ord.foreach(data.setOrd)
          SyncData.newBuilder().setExtension(CodeData.data, data.build()).build()
        }.toSeq
      case DictType.CODE_ZC =>
        CodeZc.findAll().map{ f =>
          val data = CodeData.newBuilder()
          data.setCode(f.code)
          f.name.foreach(data.setName)
          f.deleteFlag.foreach(data.setDeleteFlag)
          f.remark.foreach(data.setRemark)
          f.ord.foreach(data.setOrd)
          SyncData.newBuilder().setExtension(CodeData.data, data.build()).build()
        }.toSeq
      case DictType.CODE_ZJXY =>
        CodeZjxy.findAll().map{ f =>
          val data = CodeData.newBuilder()
          data.setCode(f.code)
          f.name.foreach(data.setName)
          f.deleteFlag.foreach(data.setDeleteFlag)
          f.remark.foreach(data.setRemark)
          f.ord.foreach(data.setOrd)
          SyncData.newBuilder().setExtension(CodeData.data, data.build()).build()
        }.toSeq
      case DictType.CODE_ZZMM =>
        CodeZzmm.findAll().map{ f =>
          val data = CodeData.newBuilder()
          data.setCode(f.code)
          f.name.foreach(data.setName)
          f.deleteFlag.foreach(data.setDeleteFlag)
          f.remark.foreach(data.setRemark)
          f.ord.foreach(data.setOrd)
          SyncData.newBuilder().setExtension(CodeData.data, data.build()).build()
        }.toSeq
      case DictType.GAFIS_GATHER_NODE =>
        GafisGatherNode.findAll().map(gafisGafisNode2SyncData).toSeq
      case DictType.GAFIS_GATHER_TYPE =>
        GafisGatherType.findAll().map(gafisGatherType2SyncData).toSeq
      case DictType.GAFIS_GATHER_TYPE_NODE_FIELD =>
        GafisGatherTypeNodeField.findAll().map(gafisGatherTypeNodeField2SyncData).toSeq
      case DictType.SYS_DEPART =>
        SysDepart.findAll().map(sysDepart2SyncData).toSeq
      case _ =>
        Nil
    }
  }

  /**
   * 采集节点，数据类型转换为proto格式
   * @param gafisGatherNode 采集节点
   * @return
   */
  def gafisGafisNode2SyncData(gafisGatherNode: GafisGatherNode): SyncData = {
    val data = GatherNode.newBuilder()
    data.setPkId(gafisGatherNode.pkId)
    data.setNodeRequest(gafisGatherNode.nodeRequest)
    data.setCreateUserId(gafisGatherNode.createUserId)
    data.setCreateDatetime(gafisGatherNode.createDatetime.getMillis)
    gafisGatherNode.nodeCode.foreach(data.setNodeCode)
    gafisGatherNode.nodeName.foreach(data.setNodeName)
    gafisGatherNode.nodeImg.foreach(data.setNodeImg)
    gafisGatherNode.updateUserId.foreach(data.setUpdateUserId)
    gafisGatherNode.updateDatetime.foreach(t => data.setUpdateDatetime(t.getMillis))
    gafisGatherNode.deleteFlag.foreach(t => data.setDeleteFlag(t.toString))
    SyncData.newBuilder().setExtension(GatherNode.data, data.build()).build()
  }

  /**
   * 采集类型，数据类型转换为proto格式
   * @param gafisGatherType 采集类型
   * @return
   */
  def gafisGatherType2SyncData(gafisGatherType: GafisGatherType): SyncData = {
    val data = GatherType.newBuilder()
    data.setPkId(gafisGatherType.pkId)
    data.setTypeName(gafisGatherType.typeName)
    data.setCreateUserId(gafisGatherType.createUserId)
    data.setCreateDatetime(gafisGatherType.createDatetime.getMillis)
    gafisGatherType.updateUserId.foreach(data.setUpdateUserId)
    gafisGatherType.updateDatetime.foreach(t => data.setUpdateDatetime(t.getMillis))
    gafisGatherType.menuId.foreach(data.setMenuId)
    gafisGatherType.personCategory.foreach(data.setPersonCategory)
    gafisGatherType.gatherCategory.foreach(data.setGatherCategory)
    gafisGatherType.parentId.foreach(data.setParentId)
    gafisGatherType.ischildren.foreach(data.setIsChildren)
    gafisGatherType.ruleId.foreach(data.setRuleId)
    gafisGatherType.deleteFlag.foreach(t => data.setDeleteFlag(t.toString))
    SyncData.newBuilder().setExtension(GatherType.data, data.build()).build()
  }

  /**
   * 采集字段，转换为proto格式
   * @param gafisGatherTypeNodeField 采集字段
   * @return
   */
  def gafisGatherTypeNodeField2SyncData(gafisGatherTypeNodeField: GafisGatherTypeNodeField): SyncData = {
    val data = GatherTypeNodeField.newBuilder()
    data.setPkId(gafisGatherTypeNodeField.pkId)
    gafisGatherTypeNodeField.typeId.foreach(data.setTypeId)
    gafisGatherTypeNodeField.nodeId.foreach(data.setNodeId)
    gafisGatherTypeNodeField.fieldId.foreach(data.setFieldId)
    gafisGatherTypeNodeField.required.foreach(data.setRequired(_))
    gafisGatherTypeNodeField.departId.foreach(data.setDepartId)
    SyncData.newBuilder().setExtension(GatherTypeNodeField.data, data.build()).build()
  }

  /**
   * 单位，转换为proto格式
   * @param sysDepart 单位
   * @return
   */
  def sysDepart2SyncData(sysDepart: SysDepart): SyncData = {
    val data = Depart.newBuilder()
    data.setCode(sysDepart.code)
    sysDepart.name.foreach(data.setName)
    sysDepart.leader.foreach(data.setLeader)
    sysDepart.remark.foreach(data.setRemark)
    sysDepart.isLeaf.foreach(data.setIsLeaf)
    sysDepart.parentId.foreach(data.setParentId)
    sysDepart.deptLevel.foreach(data.setDeptLevel)
    sysDepart.longitude.foreach(data.setLongitude)
    sysDepart.latitude.foreach(data.setLatitude)
    sysDepart.phone.foreach(data.setPhone)
    sysDepart.longName.foreach(data.setLongName)
    sysDepart.isHaveChamber.foreach(data.setIsHaveChamber)
    sysDepart.chamberType.foreach(data.setChamberType(_))
    sysDepart.isSpecial.foreach(data.setIsSpecial(_))
    sysDepart.integrationType.foreach(data.setIntegrationType)
    sysDepart.deleteFlag.foreach(data.setDeleteFlag)
    SyncData.newBuilder().setExtension(Depart.data, data.build()).build()
  }

}

package nirvana.hall.api.internal.stamp

import java.util
import java.util.Date
import javax.sql.rowset.serial.SerialBlob

import nirvana.hall.api.internal.AnalysisData
import nirvana.hall.api.jpa.{GafisGatherFinger, GafisGatherPalm}
import nirvana.hall.api.services.stamp.GatherFingerPalmService
import nirvana.hall.orm.services.Relation

/**
 * Created by wangjue on 2015/10/27.
 */
class GatherFingerPalmServiceImpl extends GatherFingerPalmService{

  /**
   * 查找人员全部指纹信息
   * @param personId
   * @param session
   * @return
   */
  override def queryFingerInfoByPersonId(personId : String): Relation[GafisGatherFinger] = {
    GafisGatherFinger.find_by_personId(personId)
  }

  /**
   * 查询指纹图像数据
   * @param personId
   * @param session
   * @return
   */
  override def queryFingerDataByPersonId(personId: String): Relation[GafisGatherFinger] = {
    GafisGatherFinger.find_by_personId_and_lobtype(personId,1).asc("fgp")
  }

  /**
   * 查询指纹信息
   * @param personId
   * @param groupId
   */
  override def queryFingerInfoBy(personId: String, groupId: Short)  : Relation[GafisGatherFinger] = {
    GafisGatherFinger.find_by_personId_and_groupId(personId,groupId)
    //GafisGatherFinger.findAllBy(sqls.eq(GafisGatherFinger.column.personId,personId).and.eq(GafisGatherFinger.column.groupId,groupId))
  }

  /**
   * 查询掌纹信息
   * @param personId
   * @param groupId
   */
  override def queryPalmInfoBy(personId: String, groupId: Short)  : Relation[GafisGatherPalm] = {
    GafisGatherPalm.find_by_personId_and_groupId(personId,groupId)
    //GafisGatherPalm.findAllBy(sqls.eq(GafisGatherPalm.column.personId,personId).and.eq(GafisGatherPalm.column.groupId,groupId))
  }

  /**
   * 指掌纹添加
   * @param fingerPalmData
   */
  override def addFingerPalmData(fingerPalmData: String,personId: String) : String = {
    val listData : util.List[util.HashMap[_, _]] = AnalysisData.analysisFinger(fingerPalmData);
    deleteFingerPalmData(personId);//采集指纹数据前删除旧数据
    for (v <- 0 to listData.size()-1) {
      val map : util.HashMap[_, _] = listData.get(v)
      val pkId = map.get("pkId").toString
      val fgp = map.get("fgp").toString
      val fgpCase = map.get("fgpCase").toString
      val groupId = map.get("groupId").toString
      val lobType = map.get("lobType").toString
      val gatherData : SerialBlob = map.get("gatherData").asInstanceOf[SerialBlob]
      val finger = new GafisGatherFinger()
        finger.pkId = pkId
        finger.fgp = fgp.toShort
        finger.lobtype = lobType.toShort
        finger.inputtime = new Date()
      finger.fgpCase = fgpCase
      finger.groupId = groupId.toShort

      //pkId,fgp.toShort,lobType.toShort,new Date(),new Date())

      //gatherData 的二进制blob需要进行处理
      if(2 >1)
        throw new UnsupportedOperationException

        finger

        /*
      new GafisGatherFinger(pkId,Some(personId),fgp.toShort,Some(groupId.toShort),lobType.toShort,None,
            DateTime.now(),None,Some(DateTime.now()),Some(fgpCase),None,None,None,None,gatherData,None,None,None)
            */
    }

    val info = queryFingerInfoByPersonId(personId)

    "success"
  }

  /**
   * 删除指掌纹
   * @param person
   */
  override def deleteFingerPalmData(person: String)  : Boolean = {
    try {
      deleteFingerData(person)
      true
    } catch {
      case exception : Exception => false
    }
  }

  def deleteFingerData(personId: String)  = {
    GafisGatherFinger.find_by_personId(personId).delete()
    //withSQL { delete.from(GafisGatherFinger).where.eq(GafisGatherFinger.column.personId, personId) }.update.apply()
  }

  def deletePalmData(personId: String)  = {
    GafisGatherPalm.find_by_personId(personId).delete()
    //withSQL { delete.from(GafisGatherPalm).where.eq(GafisGatherPalm.column.personId, personId) }.update.apply()
  }


}

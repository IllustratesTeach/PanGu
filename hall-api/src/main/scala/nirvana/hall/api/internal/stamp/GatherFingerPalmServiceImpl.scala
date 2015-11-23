package nirvana.hall.api.internal.stamp

import java.util
import javax.sql.rowset.serial.SerialBlob

import nirvana.hall.api.entities.{GafisGatherPalm, GafisGatherFinger}
import nirvana.hall.api.internal.AnalysisData
import nirvana.hall.api.services.stamp.GatherFingerPalmService
import org.joda.time.DateTime
import scalikejdbc._

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
  override def queryFingerInfoByPersonId(personId : String)(implicit session: DBSession = GafisGatherFinger.autoSession) : List[GafisGatherFinger] = {
    GafisGatherFinger.findAllBy(sqls.eq(GafisGatherFinger.column.personId,personId))
  }

  /**
   * 查询指纹图像数据
   * @param personId
   * @param session
   * @return
   */
  override def queryFingerDataByPersonId(personId: String)(implicit session: DBSession): List[GafisGatherFinger] = {
    GafisGatherFinger.findAllBy(sqls.eq(GafisGatherFinger.column.personId,personId).and.eq(GafisGatherFinger.column.lobtype,1).orderBy(GafisGatherFinger.column.fgp))
  }

  /**
   * 查询指纹信息
   * @param personId
   * @param groupId
   */
  override def queryFingerInfoBy(personId: String, groupId: Short) (implicit session: DBSession = GafisGatherFinger.autoSession) : List[GafisGatherFinger] = {
    GafisGatherFinger.findAllBy(sqls.eq(GafisGatherFinger.column.personId,personId).and.eq(GafisGatherFinger.column.groupId,groupId))
  }

  /**
   * 查询掌纹信息
   * @param personId
   * @param groupId
   */
  override def queryPalmInfoBy(personId: String, groupId: Short) (implicit session: DBSession = GafisGatherPalm.autoSession) : List[GafisGatherPalm] = {
    GafisGatherPalm.findAllBy(sqls.eq(GafisGatherPalm.column.personId,personId).and.eq(GafisGatherPalm.column.groupId,groupId))
  }

  /**
   * 指掌纹添加
   * @param fingerPalmData
   */
  override def addFingerPalmData(fingerPalmData: String,personId: String) (implicit session: DBSession) : String = {
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
      GafisGatherFinger.create(pkId,Some(personId),fgp.toShort,Some(groupId.toShort),lobType.toShort,None,
            DateTime.now(),None,Some(DateTime.now()),Some(fgpCase),None,None,None,None,gatherData,None,None,None)
    }

    val info : List[GafisGatherFinger] = queryFingerInfoByPersonId(personId)

    "success"
  }

  /**
   * 删除指掌纹
   * @param person
   */
  override def deleteFingerPalmData(person: String) (implicit session: DBSession) : Boolean = {
    try {
      deleteFingerData(person)
      true
    } catch {
      case exception : Exception => false
    }
  }

  def deleteFingerData(personId: String) (implicit session: DBSession) = {
    withSQL { delete.from(GafisGatherFinger).where.eq(GafisGatherFinger.column.personId, personId) }.update.apply()
  }

  def deletePalmData(personId: String) (implicit session: DBSession) = {
    withSQL { delete.from(GafisGatherPalm).where.eq(GafisGatherPalm.column.personId, personId) }.update.apply()
  }


}

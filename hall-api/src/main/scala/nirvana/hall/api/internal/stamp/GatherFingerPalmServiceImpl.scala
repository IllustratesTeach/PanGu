package nirvana.hall.api.internal.stamp

import nirvana.hall.api.entities.{GafisGatherPalm, GafisGatherFinger, SysUser}
import nirvana.hall.api.services.stamp.GatherFingerPalmService
import scalikejdbc._

/**
 * Created by wangjue on 2015/10/27.
 */
class GatherFingerPalmServiceImpl extends GatherFingerPalmService{
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
   * @param login
   */
  override def addFingerPalmData(fingerPalmData: String, login: SysUser) (implicit session: DBSession) : Boolean = ???

  /**
   * 删除指掌纹
   * @param person
   */
  override def deleteFingerPalmData(person: String) (implicit session: DBSession) : Boolean = {
    try {
      deleteFingerData(person)
      deletePalmData(person)
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

package nirvana.hall.api.services.stamp

import nirvana.hall.api.entities.{GafisGatherPalm, GafisGatherFinger, SysUser}
import nirvana.hall.api.services.AutoSpringDataSourceSession
import scalikejdbc.DBSession

/**
 * 指掌纹
 * Created by wangjue on 2015/10/26.
 */
trait GatherFingerPalmService {

  /**
   * 查询指纹信息
   * @param personId
   * @param groupId
   */
  def queryFingerInfoBy(personId : String,groupId : Short)  (implicit session: DBSession = AutoSpringDataSourceSession.apply()) : List[GafisGatherFinger]


  /**
   * 查询掌纹信息
   * @param personId
   * @param groupId
   */
  def queryPalmInfoBy(personId : String,groupId : Short) (implicit session: DBSession = AutoSpringDataSourceSession.apply()) : List[GafisGatherPalm]


  /**
   * 指掌纹添加
   * @param fingerPalmData
   * @param login
   */
  def addFingerPalmData(fingerPalmData : String,login : SysUser)  (implicit session: DBSession = AutoSpringDataSourceSession.apply()) : Boolean


  /**
   * 删除指掌纹
   * @param person
   */
  def deleteFingerPalmData(person : String)  (implicit session: DBSession = AutoSpringDataSourceSession.apply()) : Boolean




}

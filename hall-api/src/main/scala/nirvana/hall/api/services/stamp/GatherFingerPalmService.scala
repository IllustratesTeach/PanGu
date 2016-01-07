package nirvana.hall.api.services.stamp

import nirvana.hall.api.jpa.{GafisGatherFinger, GafisGatherPalm}
import nirvana.hall.api.services.AutoSpringDataSourceSession
import nirvana.hall.orm.services.Relation
import org.springframework.transaction.annotation.Transactional
import scalikejdbc.DBSession

/**
 * 指掌纹
 * Created by wangjue on 2015/10/26.
 */
trait GatherFingerPalmService {

  /**
   * 通过人员ID查询
   * @param personId
   * @param session
   * @return
   */
  def queryFingerInfoByPersonId(personId : String) : Relation[GafisGatherFinger]

  /**
   * 查询指纹图像数据
   * @param personId
   * @param session
   * @return
   */
  def queryFingerDataByPersonId(personId : String)  : Relation[GafisGatherFinger]


  /**
   * 查询指纹信息
   * @param personId
   * @param groupId
   */
  def queryFingerInfoBy(personId : String,groupId : Short)   : Relation[GafisGatherFinger]


  /**
   * 查询掌纹信息
   * @param personId
   * @param groupId
   */
  def queryPalmInfoBy(personId : String,groupId : Short)  : Relation[GafisGatherPalm]


  /**
   * 指掌纹添加
   * @param fingerPalmData
   */
  @Transactional
  def addFingerPalmData(fingerPalmData : String,personId: String)   : String


  /**
   * 删除指掌纹
   * @param person
   */
  @Transactional
  def deleteFingerPalmData(person : String)   : Boolean




}

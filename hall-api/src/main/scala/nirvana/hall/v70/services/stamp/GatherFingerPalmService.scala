package nirvana.hall.v70.services.stamp

import nirvana.hall.v70.jpa.{GafisGatherFinger, GafisGatherPalm}
import org.springframework.transaction.annotation.Transactional

/**
 * 指掌纹
 * Created by wangjue on 2015/10/26.
 */
trait GatherFingerPalmService {

  /**
   * 通过人员ID查询
   * @param personId
   * @return
   */
  def queryFingerInfoByPersonId(personId : String) : Seq[GafisGatherFinger]

  /**
   * 查询指纹图像数据
   * @param personId
   * @return
   */
  def queryFingerDataByPersonId(personId : String)  : Seq[GafisGatherFinger]


  /**
   * 查询指纹信息
   * @param personId
   * @param groupId
   */
  def queryFingerInfoBy(personId : String,groupId : Short)   : Seq[GafisGatherFinger]


  /**
   * 查询掌纹信息
   * @param personId
   * @param groupId
   */
  def queryPalmInfoBy(personId : String,groupId : Short)  : Seq[GafisGatherPalm]


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

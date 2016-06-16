package nirvana.hall.v70.internal

import java.util.Date

import nirvana.hall.api.services.TPCardService
import nirvana.hall.protocol.api.FPTProto.TPCard
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa.{GafisGatherFinger, GafisGatherPortrait, GafisPerson}
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/1/26.
 */
class TPCardServiceImpl extends TPCardService{
  /**
   * 获取捺印卡信息
   * @param personId
   * @return
   */
  override def getTPCard(personId: String): TPCard = {
    val person = GafisPerson.find(personId)
    val photoList = GafisGatherPortrait.find_by_personid(personId).toSeq
    val fingerList = GafisGatherFinger.find_by_personId(personId).toSeq

    ProtobufConverter.convertGafisPerson2TPCard(person, photoList, fingerList, null)
  }

  /**
   * 新增捺印卡片
   * @param tpCard
   * @return
   */
  @Transactional
  override def addTPCard(tpCard: TPCard): Unit = {
    //验证卡号是否已经存在
    if(isExist(tpCard.getStrCardID)){
      throw new RuntimeException("记录已存在")
    }else{
      val person = ProtobufConverter.convertTPCard2GafisPerson(tpCard)
      val fingerList = ProtobufConverter.convertTPCard2GafisGatherFinger(tpCard)
      person.inputtime = new Date()
      person.inputpsn = Gafis70Constants.INPUTPSN
      person.save()
      fingerList.foreach{finger =>
        finger.pkId = CommonUtils.getUUID()
        finger.inputtime = new Date()
        finger.inputpsn = Gafis70Constants.INPUTPSN
        finger.save()
      }
    }
  }

  /**
   * 删除捺印卡片
   * @param cardId
   * @return
   */
  @Transactional
  override def delTPCard(cardId: String): Unit = {
    //删除指纹
    GafisGatherFinger.find_by_personId(cardId).foreach(f=> f.delete())
    //删除人员信息
    GafisPerson.find(cardId).delete()
  }

  /**
   * 更新捺印卡片
   * @param tpCard
   * @return
   */
  @Transactional
  override def updateTPCard(tpCard: TPCard): Unit = {
    val person = ProtobufConverter.convertTPCard2GafisPerson(tpCard)
    val fingerList = ProtobufConverter.convertTPCard2GafisGatherFinger(tpCard)

    person.modifiedpsn = Gafis70Constants.INPUTPSN
    person.modifiedtime = new Date()
    person.deletag = Gafis70Constants.DELETAG_USE

    //删除指纹
    GafisGatherFinger.find_by_personId(person.personid).foreach(f=> f.delete())

    fingerList.foreach{finger =>
      finger.pkId = CommonUtils.getUUID()
      finger.inputtime = new Date()
      finger.inputpsn = Gafis70Constants.INPUTPSN
      finger.save()
    }

  }

  /**
   * 验证卡号是否已存在
   * @param cardId
   * @return
   */
  override def isExist(cardId: String): Boolean = {
    GafisPerson.findOption(cardId).nonEmpty
  }
}

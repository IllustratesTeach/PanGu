package nirvana.hall.v70.internal

import java.util.Date

import nirvana.hall.api.services.TPCardService
import nirvana.hall.protocol.v62.tp.TPCardProto._
import nirvana.hall.v70.internal.sync.ProtobufConverter
import nirvana.hall.v70.jpa.{GafisGatherFinger, GafisPerson}
import org.springframework.transaction.annotation.Transactional

/**
 * Created by songpeng on 16/1/26.
 */
class TPCardServiceImpl extends TPCardService{
  /**
   * 新增捺印卡片
   * @param tPCardAddRequest
   * @return
   */
  @Transactional
  override def addTPCard(tPCardAddRequest: TPCardAddRequest): TPCardAddResponse = {
    val tpCard = tPCardAddRequest.getCard
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

    TPCardAddResponse.newBuilder().build()
  }

  /**
   * 删除捺印卡片
   * @param tPCardDelRequest
   * @return
   */
  @Transactional
  override def delTPCard(tPCardDelRequest: TPCardDelRequest): TPCardDelResponse = {
    val personId = tPCardDelRequest.getCardId
    //删除指纹
    GafisGatherFinger.find_by_personId(personId).foreach(f=> f.delete())
    //删除人员信息
    GafisPerson.find(personId).delete()
    TPCardDelResponse.newBuilder().build()
  }

  /**
   * 获取捺印卡片
   * @param tPCardGetRequest
   * @return
   */
  override def getTPCard(tPCardGetRequest: TPCardGetRequest): TPCardGetResponse = {
    val personId = tPCardGetRequest.getCardId
    val person = GafisPerson.find(personId)
    val fingerList = GafisGatherFinger.find_by_personId(personId)

    val tpCard = ProtobufConverter.convertGafisPerson2TPCard(person, fingerList)

    TPCardGetResponse.newBuilder().setCard(tpCard).build()
  }

  /**
   * 更新捺印卡片
   * @param tPCardUpdateRequest
   * @return
   */
  @Transactional
  override def updateTPCard(tPCardUpdateRequest: TPCardUpdateRequest): TPCardUpdateResponse = {
    val tpCard = tPCardUpdateRequest.getCard
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

    TPCardUpdateResponse.newBuilder().build()
  }
}

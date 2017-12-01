package nirvana.hall.v70.gz.services.versionfpt5

import java.text.SimpleDateFormat
import java.util.{Date, UUID}
import javax.persistence.EntityManager

import com.google.protobuf.ByteString
import nirvana.hall.api.services.LPCardService
import nirvana.hall.protocol.api.FPTProto.{FingerFgp, ImageType, LPCard, PatternType}
import nirvana.hall.v70.gz.Constant
import nirvana.hall.v70.gz.jpa.{GafisCaseFinger, GafisCaseFingerMnt, SysUser}
import nirvana.hall.v70.gz.sync.ProtobufConverter
import nirvana.hall.v70.gz.sys.UserService
import nirvana.hall.v70.internal.Gafis70Constants

/**
  * Created by songpeng on 2017/6/29.
  */
class LPCardServiceImpl(entityManager: EntityManager, userService: UserService) extends LPCardService{
  /**
    * 新增现场卡片
    *
    * @param lpCard
    * @return
    */
  override def addLPCard(lpCard: LPCard, dbId: Option[String]): Unit = {
    val caseFinger = ProtobufConverter.convertLPCard2GafisCaseFinger(lpCard)
    val caseFingerMnt = ProtobufConverter.convertLPCard2GafisCaseFingerMnt(lpCard)
    val nativeQuery = entityManager.createNativeQuery("select gafis_case_sid_seq.nextval from dual")
    val sid = java.lang.Long.parseLong(nativeQuery.getResultList.get(0).toString)
    caseFinger.sid = sid
    //将用户名转为用户id
    var user = userService.findSysUserByLoginName(caseFinger.inputpsn)
    if (user.isEmpty){//找不到对应的用户，使用管理员用户
      user = Option(SysUser.find(Gafis70Constants.INPUTPSN))
    }
    caseFinger.inputpsn = user.get.pkId
    caseFinger.inputtime = new Date
    caseFinger.creatorUnitCode = user.get.departCode
    val modUser = userService.findSysUserByLoginName(caseFinger.modifiedpsn)
    if(modUser.nonEmpty){
      caseFinger.modifiedpsn = modUser.get.pkId
    }else{
      caseFinger.modifiedpsn = ""
    }
    caseFinger.deletag = Gafis70Constants.DELETAG_USE
    caseFinger.save()

    caseFingerMnt.pkId = UUID.randomUUID().toString.replace("-",Constant.EMPTY)
    caseFingerMnt.inputpsn = user.get.pkId
    caseFingerMnt.deletag = Gafis70Constants.DELETAG_USE
    caseFingerMnt.save()
  }

  /**
    * 删除现场卡片
    *
    * @param cardId
    * @return
    */
override def delLPCard(cardId: String, dbId: Option[String]): Unit = ???

  /**
    * 更新现场卡片
    *
    * @param lpCard
    * @return
    */
  override def updateLPCard(lpCard: LPCard, dbId: Option[String]): Unit = {
    val caseFinger = GafisCaseFinger.find(lpCard.getStrCardID)
    ProtobufConverter.convertLPCard2GafisCaseFinger(lpCard, caseFinger)
    //将用户名转为用户id
    var user = userService.findSysUserByLoginName(caseFinger.inputpsn)
    if (user.isEmpty){//找不到对应的用户，使用管理员用户
      user = Option(SysUser.find(Gafis70Constants.INPUTPSN))
    }
    caseFinger.inputpsn = user.get.pkId
    caseFinger.inputtime = new Date
    caseFinger.creatorUnitCode= user.get.departCode
    val modUser = userService.findSysUserByLoginName(caseFinger.modifiedpsn)
    if(modUser.nonEmpty){
      caseFinger.modifiedpsn = modUser.get.pkId
    }else{
      caseFinger.modifiedpsn = ""
    }
    caseFinger.deletag = Gafis70Constants.DELETAG_USE
    caseFinger.save()

    val caseFingerMnt = ProtobufConverter.convertLPCard2GafisCaseFingerMnt(lpCard)
    //先删除，后插入
    GafisCaseFingerMnt.delete.where(GafisCaseFingerMnt.fingerId === caseFinger.fingerId).execute
    caseFingerMnt.pkId = UUID.randomUUID().toString.replace("-",Constant.EMPTY)
    caseFingerMnt.inputpsn = user.get.pkId
    caseFingerMnt.deletag = Gafis70Constants.DELETAG_USE
    caseFingerMnt.save()
  }

  /**
    * 获取现场卡片
    *
    * @param fingerId
    * @return
    */
  override def getLPCard(fingerId: String, dbId: Option[String]): LPCard = {
    if(isExist(fingerId, dbId)){
      val caseFinger = GafisCaseFinger.find(fingerId)
      //处理特征不存在的情况
      if(caseFinger.fingerImg != null){
        val caseFingerMntOp = GafisCaseFingerMnt.where(GafisCaseFingerMnt.fingerId === fingerId).and(GafisCaseFingerMnt.isMainMnt === "1").headOption
        convertGafisCaseFinger2LPCard(caseFinger, caseFingerMntOp.getOrElse(new GafisCaseFingerMnt()))
      }else{
        null
      }
    }else{
      null
    }
  }

  /**
    * 验证现场卡片是否存在
    *
    * @param cardId
    * @return
    */
  override def isExist(cardId: String, dbId: Option[String]): Boolean = {
    GafisCaseFinger.findOption(cardId).nonEmpty
  }

  implicit def string2Int(string: String): Int ={
    if(isNonBlank(string))
      Integer.parseInt(string)
    else
      0
  }
  implicit def date2String(date: Date): String = {
    if (date != null)
      new SimpleDateFormat("yyyyMMdd").format(date)
    else ""
  }
  implicit def string2Date(date: String): Date= {
    if (date != null && date.length == 8)
      new SimpleDateFormat("yyyyMMdd").parse(date)
    else null
  }
  def isNonBlank(string: String):Boolean = string != null && string.length >0
  def magicSet(value:String,fun:String=>Any){
    if(isNonBlank(value)){ fun(value)}
  }
  def convertGafisCaseFinger2LPCard(caseFinger: GafisCaseFinger, caseFingerMnt: GafisCaseFingerMnt): LPCard = {
    val lpCard = LPCard.newBuilder()
    lpCard.setStrCardID(caseFinger.fingerId)
    val textBuilder = lpCard.getTextBuilder
    magicSet(caseFinger.caseId, textBuilder.setStrCaseId)
    magicSet(caseFinger.seqNo, textBuilder.setStrSeq)
    if ("1".equals(caseFinger.isCorpse))
      textBuilder.setBDeadBody(true)
    magicSet(caseFinger.corpseNo, textBuilder.setStrDeadPersonNo)
    magicSet(caseFinger.remainPlace, textBuilder.setStrRemainPlace)
    magicSet(caseFinger.ridgeColor, textBuilder.setStrRidgeColor)
    magicSet(caseFinger.mittensBegNo, textBuilder.setStrStart)
    magicSet(caseFinger.mittensEndNo, textBuilder.setStrEnd)
    textBuilder.setNXieChaState(caseFinger.isAssist)
    textBuilder.setNBiDuiState(caseFinger.matchStatus)

    val blobBuilder = lpCard.getBlobBuilder
    blobBuilder.setType(ImageType.IMAGETYPE_FINGER)
    //    magicSet(caseFinger.developMethod, blobBuilder.setStrMntExtractMethod)
    blobBuilder.setStImageBytes(ByteString.copyFrom(caseFinger.fingerImg))
    //特征
    if(caseFingerMnt.fingerMnt != null)
      blobBuilder.setStMntBytes(ByteString.copyFrom(caseFingerMnt.fingerMnt))
    magicSet(caseFingerMnt.captureMethod, blobBuilder.setStrMntExtractMethod)
    //指位
    if (isNonBlank(caseFinger.fgp))
      0.until(caseFinger.fgp.length)
        .filter("1" == caseFinger.fgp.charAt(_))
        .foreach(i => blobBuilder.addFgp(FingerFgp.valueOf(i + 1)))
    //纹型
    if (isNonBlank(caseFinger.pattern))
      caseFinger.pattern.split(",").foreach(f => blobBuilder.addRp(PatternType.valueOf(f)))

    lpCard.build()
  }
}

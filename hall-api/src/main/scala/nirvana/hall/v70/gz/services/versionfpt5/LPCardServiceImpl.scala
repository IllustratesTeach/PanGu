package nirvana.hall.v70.gz.services.versionfpt5

import java.text.SimpleDateFormat
import java.util.{Date, UUID}
import javax.persistence.EntityManager
import javax.sql.DataSource

import com.google.protobuf.ByteString
import nirvana.hall.api.internal.DateConverter
import nirvana.hall.api.services.LPCardService
import nirvana.hall.protocol.api.FPTProto.{FingerFgp, ImageType, LPCard, PatternType}
import nirvana.hall.support.services.JdbcDatabase
import nirvana.hall.v70.config.HallV70Config
import nirvana.hall.v70.gz.Constant
import nirvana.hall.v70.gz.jpa.{GafisCaseFinger, GafisCaseFingerMnt, SysUser}
import nirvana.hall.v70.gz.sync.ProtobufConverter
import nirvana.hall.v70.gz.sys.UserService
import nirvana.hall.v70.internal.Gafis70Constants

import scala.collection.mutable

/**
  * Created by songpeng on 2017/6/29.
  */
class LPCardServiceImpl(hallV70Config: HallV70Config,entityManager: EntityManager, userService: UserService ,implicit val dataSource: DataSource) extends LPCardService{
  /**
    * 新增现场卡片
    *
    * @param lpCard
    * @return
    */
  override def addLPCard(lpCard: LPCard, dbId: Option[String]): Unit = {
    val caseFinger = convertLPCard2GafisCaseFinger(lpCard)
    val caseFingerMnt = ProtobufConverter.convertLPCard2GafisCaseFingerMnt(lpCard)
    val nativeQuery = entityManager.createNativeQuery("select gafis_case_sid_seq.nextval from dual")
    val sid = java.lang.Long.parseLong(nativeQuery.getResultList.get(0).toString)
    caseFinger.sid = sid
    var seqNo = getCardSeq(caseFinger.caseId)
    if(Integer.parseInt(seqNo) >= Integer.parseInt(caseFinger.seqNo)){
      seqNo = (Integer.parseInt(seqNo)+1).toString
      if((Integer.parseInt(seqNo)+1).toString.length == 1){  //如果seqNo<10 前面补0
        seqNo = "0" + (Integer.parseInt(seqNo)+1)
      }
      caseFinger.seqNo = seqNo
      caseFinger.fingerId = caseFinger.caseId + seqNo
      caseFingerMnt.fingerId = caseFinger.caseId + seqNo
    }
    //将用户名转为用户id
    var user = userService.findSysUserByLoginName(hallV70Config.server.users)
    if (user.isEmpty){//找不到对应的用户，使用管理员用户
      user = Option(SysUser.find(hallV70Config.server.users))
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
    val fingerId = caseFinger.fingerId
    val seqNo = caseFinger.seqNo
    convertLPCard2GafisCaseFinger(lpCard, caseFinger)
    caseFinger.fingerId = fingerId
    caseFinger.seqNo = seqNo
    //将用户名转为用户id
    var user = userService.findSysUserByLoginName(caseFinger.inputpsn)
    if (user.isEmpty){//找不到对应的用户，使用管理员用户
      user = Option(SysUser.find(hallV70Config.server.users))
    }
    caseFinger.inputpsn = user.get.pkId
    caseFinger.inputtime = new Date
    caseFinger.creatorUnitCode= user.get.departCode
    val modUser = userService.findSysUserByLoginName(hallV70Config.server.users)
    if(modUser.nonEmpty){
      caseFinger.modifiedpsn = modUser.get.pkId
    }else{
      caseFinger.modifiedpsn = ""
    }
    caseFinger.deletag = Gafis70Constants.DELETAG_USE
    caseFinger.save()

    val caseFingerMnt = ProtobufConverter.convertLPCard2GafisCaseFingerMnt(lpCard)
    caseFingerMnt.fingerId = fingerId
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
    //5.0新加
    lpCard.setStrPhysicalId(caseFinger.physicalEvidenceNo)

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
    textBuilder.setStrFeatureGroupIdentifier(caseFingerMnt.mntCombinationCode)
    textBuilder.setStrFeatureGroupDscriptInfo(caseFingerMnt.mntCombinationMessage)
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

  def convertLPCard2GafisCaseFinger(lpCard: LPCard, caseFinger: GafisCaseFinger = new GafisCaseFinger()): GafisCaseFinger = {
    caseFinger.fingerId = lpCard.getStrCardID
    //5.0新加
    caseFinger.physicalEvidenceNo = lpCard.getStrPhysicalId

    val text = lpCard.getText
    //caseFinger.ltOperator = "8a8187175138054e0151381149490003"
    caseFinger.seqNo = text.getStrSeq
    caseFinger.remainPlace = text.getStrRemainPlace
    caseFinger.ridgeColor = text.getStrRidgeColor
    caseFinger.isCorpse = if(text.getBDeadBody) "1" else "0"
    caseFinger.corpseNo = text.getStrDeadPersonNo
    caseFinger.isAssist = text.getNXieChaState.toString
    caseFinger.matchStatus = text.getNBiDuiState.toString
    caseFinger.mittensBegNo = text.getStrStart
    caseFinger.mittensEndNo = text.getStrEnd
    caseFinger.caseId = text.getStrCaseId
    caseFinger.developMethod = text.getStrCaptureMethod
    caseFinger.remark = text.getStrComment

    //操作信息
    val admData = lpCard.getAdmData
    if(admData != null){
      caseFinger.inputpsn = admData.getCreator
      caseFinger.modifiedpsn = admData.getUpdator
      caseFinger.creatorUnitCode = admData.getCreateUnitCode
      if(admData.getCreateDatetime != null && admData.getCreateDatetime.length == 14){
        caseFinger.inputtime = DateConverter.convertString2Date(admData.getCreateDatetime, "yyyyMMddHHmmss")
      }
      if(admData.getUpdateDatetime != null && admData.getUpdateDatetime.length == 14){
        caseFinger.modifiedtime = DateConverter.convertString2Date(admData.getUpdateDatetime, "yyyyMMddHHmmss")
      }
    }


    val blob = lpCard.getBlob
    caseFinger.fingerImg = blob.getStImageBytes.toByteArray
    for(i <- 0 until blob.getFgpCount){
      blob.getFgp(i).getNumber.toString
    }
    caseFinger
  }

  def getCardSeq(cardId:String): String={
    val sql = s"select seq_no from "+
                s" (select seq_no from Gafis_Case_Finger f where f.case_id = ?"+
                s" union"+
                s" select seq_no from gafis_case_palm p where p.case_id = ?)"+
              s" order by seq_no desc"
    val resultList = new mutable.ListBuffer[String]
    JdbcDatabase.queryWithPsSetter2(sql){ps=>
      ps.setString(1,cardId)
      ps.setString(2,cardId)
    } {rs=>
      while (rs.next()) {
        resultList.append(rs.getString("seq_no"))
      }
    }
    if(resultList.size>0){
      resultList.head
    }else{
      "00"
    }
  }
}

package nirvana.hall.v62.internal


import monad.support.services.{LoggerSupport, XmlLoader}
import nirvana.hall.api.internal.DateConverter
import nirvana.hall.api.services._
import nirvana.hall.c.services.gbaselib.gbasedef.GAKEYSTRUCT
import nirvana.hall.c.services.gfpt4lib.FPT4File.{Logic04Rec, Logic05Rec, Logic06Rec}
import nirvana.hall.c.services.gfpt4lib.fpt4code
import nirvana.hall.c.services.gfpt5lib.{LlHitResultPackage, LtHitResultPackage, TtHitResultPackage, fpt5util}
import nirvana.hall.c.services.gloclib.gafisusr.GAFIS_USERSTRUCT
import nirvana.hall.c.services.gloclib.galoclog.GAFIS_VERIFYLOGSTRUCT
import nirvana.hall.c.services.gloclib.galoclp.GAFIS_LPGROUPSTRUCT
import nirvana.hall.c.services.gloclib.galoctp._
import nirvana.hall.c.services.gloclib.gaqryque
import nirvana.hall.protocol.api.FPTProto.{FingerFgp, MatchRelationInfo}
import nirvana.hall.protocol.api.HallMatchRelationProto.{MatchRelationGetRequest, MatchRelationGetResponse, MatchStatus}
import nirvana.hall.protocol.fpt.MatchRelationProto._
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.c.gloclib.gcolnames._
import nirvana.hall.v62.internal.c.gloclib.{BreakInfos, ganetlogverifyConverter}
import nirvana.hall.v70.internal.query.QueryConstants
import org.apache.tapestry5.ioc.internal.util.InternalUtils

import scala.collection.mutable.ArrayBuffer

/**
 * Created by songpeng on 16/6/21.
 */
class MatchRelationServiceImpl(v62Config: HallV62Config
                               , facade: V62Facade
                               , lPCardService: LPCardService
                               , queryService: QueryService
                              ,tPCardService:TPCardService) extends MatchRelationService with LoggerSupport{
  /**
    * 获取比对关系
    * 先根据查询队列表，读取到第一条查询的任务状态，如果状态是已复核，根据查询类型查询比中关系
    * @param request
    * @return
    */
  override def getMatchRelation(request: MatchRelationGetRequest): MatchRelationGetResponse = {
    val matchType = request.getMatchType
    val response = MatchRelationGetResponse.newBuilder()
    response.setMatchType(matchType)
    val cardId = request.getCardId
    //获取比对状态
    val status = queryService.findFirstQueryStatusByCardIdAndMatchType(cardId, matchType)
    response.setMatchStatus(status)
    matchType match {
      case MatchType.FINGER_TL =>
        if(status == MatchStatus.RECHECKED){//倒查复核后生成比对关系
        //倒查直接查询比中关系表
        val statement = Option("(SrcKey='%s')".format(cardId))
          val matchInfo = queryMatchInfo(statement, 1)
          matchInfo.foreach{ verifyLog: GAFIS_VERIFYLOGSTRUCT=>
            val matchRelationTL = MatchRelationTLAndLT.newBuilder()
            matchRelationTL.setPersonId(verifyLog.szSrcKey)
            matchRelationTL.setFpg(FingerFgp.valueOf(verifyLog.nFg))
            matchRelationTL.setCapture(verifyLog.bIsCrimeCaptured > 0)

            //根据现场卡号查询案件卡号，seqno信息
            val fingerId = verifyLog.szDestKey
            val lpCard = lPCardService.getLPCard(fingerId)
            val caseId = lpCard.getText.getStrCaseId
            matchRelationTL.setCaseId(caseId)
            matchRelationTL.setSeqNo(lpCard.getText.getStrSeq)

            val matchSysInfo = MatchSysInfo.newBuilder()
            matchSysInfo.setMatchUnitCode(verifyLog.szSubmitUserUnitCode)
            matchSysInfo.setMatchUnitName("")
            matchSysInfo.setMatcher(verifyLog.szSubmitUserName)
            matchSysInfo.setMatchDate(DateConverter.convertAFISDateTime2String(verifyLog.tSubmitDateTime).substring(0, 8))
            matchSysInfo.setInputer(verifyLog.szBreakUserName)
            matchSysInfo.setInputUnitCode(verifyLog.szBreakUserUnitCode)
            matchSysInfo.setInputDate(DateConverter.convertAFISDateTime2String(verifyLog.tBreakDateTime).substring(0, 8))
            matchSysInfo.setApprover(verifyLog.szReCheckUserName)
            matchSysInfo.setApproveDate(DateConverter.convertAFISDateTime2String(verifyLog.tReCheckDateTime).substring(0, 8))

            val matchRelation = MatchRelation.newBuilder()
            matchRelation.setMatchSysInfo(matchSysInfo)
            matchRelation.setExtension(MatchRelationTLAndLT.data, matchRelationTL.build())

            response.addMatchRelation(matchRelation.build())
          }
        }
      case MatchType.FINGER_TT =>
        //查重核查完毕后生成比对关系
        if(status.getNumber == MatchStatus.CHECKED.getNumber){
          //重卡信息先从捺印表查到重卡组号，然后根据重卡组号查询重卡信息
          val personId = getPersonid(cardId)//重卡组号
          //如果没有重卡组号不获取重卡信息
          if (InternalUtils.isNonBlank(personId)){
            val m_stPersonInfo = new GPERSONINFOSTRUCT
            m_stPersonInfo.szPersonID = personId
            m_stPersonInfo.nItemFlag = (GPIS_ITEMFLAG_CARDCOUNT | GPIS_ITEMFLAG_CARDID | GPIS_ITEMFLAG_TEXT).toByte
            m_stPersonInfo.nItemFlag2 = (GPIS_ITEMFLAG2_LPGROUPDBID | GPIS_ITEMFLAG2_LPGROUPTID | GPIS_ITEMFLAG2_FLAG).toByte
            val dbId: Short = 1
            val tableId: Short = 3
            facade.NET_GAFIS_PERSON_Get(dbId, tableId, m_stPersonInfo)
            m_stPersonInfo.pstID_Data.foreach{personInfo =>
              if(!cardId.equals(personInfo.szCardID)){//排除自己
                val tt = MatchRelationTT.newBuilder()
                tt.setPersonId1(cardId)
                tt.setPersonId2(personInfo.szCardID)
                //TT没有单位信息
                val matchSysInfo = MatchSysInfo.newBuilder()
                matchSysInfo.setMatchUnitCode("")
                matchSysInfo.setMatchUnitName("")
                matchSysInfo.setMatcher(personInfo.szUserName)
                matchSysInfo.setMatchDate(DateConverter.convertAFISDateTime2String(personInfo.tCheckTime).substring(0, 8))
                val matchRelation = MatchRelation.newBuilder()
                matchRelation.setMatchSysInfo(matchSysInfo)
                matchRelation.setExtension(MatchRelationTT.data, tt.build())

                response.addMatchRelation(matchRelation.build())
              }
            }
          }
        }
      case other =>
        warn("unsupport matchType:{}", matchType)
    }

    response.build()
  }
  /**
   * 查询比中关系
   *
   * @param statementOpt 查询语句
   * @param limit 抓取多少条
   * @return 查询结构
   */
  private def queryMatchInfo(statementOpt:Option[String],limit:Int): List[GAFIS_VERIFYLOGSTRUCT]={
    val mapper = Map(
      g_stCN.stBc.pszBreakID->"szBreakID",
      g_stCN.stBc.pszSrcKey->"szSrcKey",
      g_stCN.stBc.pszDestKey->"szDestKey",
      g_stCN.stBc.pszScore->"nScore",
      g_stCN.stBc.pszFirstRankScore->"nFirstRankScore",
      g_stCN.stBc.pszRank->"nRank",
      g_stCN.stBc.pszFg->"nFg",
      g_stCN.stBc.pszHitPoss->"nHitPoss",
      g_stCN.stBc.pszIsRemoteSearched->"bIsRmtSearched",
      g_stCN.stBc.pszIsCrimeCaptured->"bIsCrimeCaptured",
      g_stCN.stBc.pszTotoalMatchedCnt->"nTotalMatchedCnt",
      g_stCN.stBc.pszQueryType->"nQueryType",
      g_stCN.stBc.pszSrcDBID->"nSrcDBID",
      g_stCN.stBc.pszDestDBID->"nDestDBID",
      g_stCN.stBc.pszSrcPersonCaseID->"szSrcPersonCaseID",
      g_stCN.stBc.pszDestPersonCaseID->"szDestPersonCaseID",
      g_stCN.stBc.pszCaseClassCode1->"szCaseClassCode1",
      g_stCN.stBc.pszCaseClassCode2->"szCaseClassCode2",
      g_stCN.stBc.pszCaseClassCode3->"szCaseClassCode3",
      g_stCN.stBc.pszSubmitUserName->"szSubmitUserName",
      g_stCN.stBc.pszSubmitUserUnitCode->"szSubmitUserUnitCode",
      g_stCN.stBc.pszSubmitDateTime->"tSubmitDateTime",
      g_stCN.stBc.pszBreakUserName->"szBreakUserName",
      g_stCN.stBc.pszBreakUserUnitCode->"szBreakUserUnitCode",
      g_stCN.stBc.pszBreakDateTime->"tBreakDateTime",
      g_stCN.stBc.pszReCheckUserName->"szReCheckUserName",
      g_stCN.stBc.pszReCheckerUnitCode->"szReCheckerUnitCode",
      g_stCN.stBc.pszReCheckDate->"tReCheckDateTime",
      g_stCN.stNuminaCol.pszSID->"nSID"
    )

    val dbDef = facade.NET_GAFIS_MISC_GetDefDBID()
    facade.queryV62Table[GAFIS_VERIFYLOGSTRUCT](
      dbDef.nAdminDefDBID,
      g_stCN.stSysAdm.nTIDBreakCaseTable,
      mapper,
      statementOpt,
      limit)
  }

  /**
    * 增加比中关系
    *
    * @param matchRelationInfo
    * @param dbId
    */
  override def addMatchRelation(matchRelationInfo: MatchRelationInfo, dbId: Option[String]): Unit = {
    val gafis_VERIFYLOGSTRUCT = ganetlogverifyConverter.convertProtoBuf2GAFIS_VERIFYLOGSTRUCT(matchRelationInfo)
    facade.NET_GAFIS_VERIFYLOG_Add(gafis_VERIFYLOGSTRUCT)
  }

  /**
    * 判断是否存在该breakid的比中关系
    *
    * @param szBreakID
    * @param dbId
    * @return
    */
  override def isExist(szBreakID: String, dbId: Option[String]): Boolean = {
    val statement = Option("(BREAKID='%s')".format(szBreakID))
    queryMatchInfo(statement, 1).nonEmpty
  }

  /**
    * 更新比中关系
    *
    * @param matchRelationInfo
    * @param dbId
    */
  override def updateMatchRelation(matchRelationInfo: MatchRelationInfo, dbId: Option[String]): Unit = {
    val gafis_VERIFYLOGSTRUCT = ganetlogverifyConverter.convertProtoBuf2GAFIS_VERIFYLOGSTRUCT(matchRelationInfo)
    facade.NET_GAFIS_VERIFYLOG_Update(gafis_VERIFYLOGSTRUCT)
  }

  /**
    * 获取比对关系
    *
    * @param breakId
    * @return
    */
  override def getMatchRelation(breakId: String): MatchRelationInfo = ???

  /**
    * 获取重卡比中关系
    * 先获取重卡组号，根据重卡组号读取重卡组列表编号
    * @param cardId
    * @return
    */
  override def getTtHitResultPackage(cardId: String): Seq[TtHitResultPackage] = {
    val ttHitList = new ArrayBuffer[TtHitResultPackage]()
    //首先获取重卡组号
    val personId = getPersonid(cardId)
    if(personId.nonEmpty){
      val m_stPersonInfo = getGPERSONINFOSTRUCT(personId)
      m_stPersonInfo.pstID_Data.foreach{personInfo =>
        if(!cardId.equals(personInfo.szCardID)){//排除自己
          val ttHit = new TtHitResultPackage
          ttHit.cardId = personId
          ttHit.resultCardId = personInfo.szCardID
          ttHit.memo = personInfo.szComment
          ttHitList += ttHit
        }
      }

    }

    ttHitList
  }

  /**
    * 获取重卡比中关系
    *
    * @param oraSid 任务号
    * @return
    */
  override def getTtHitResultPackageByOraSid(oraSid: String): Seq[TtHitResultPackage] = {
    val queryStruct = queryService.getGAQUERYSTRUCT(oraSid.toLong)
    val simpQry = queryStruct.stSimpQry
    val sourceTpCard = tPCardService.getTPCard(simpQry.szKeyID)
    val candList = queryStruct.pstCand_Data
    val ttHitList = new ArrayBuffer[TtHitResultPackage]()
    candList.foreach{
      t =>
        if(t.nCheckState == gaqryque.GAQRYCAND_CHKSTATE_MATCH.toByte
              && t.nStatus == gaqryque.GAQRYCAND_CHKSTATE_UNCHECKED.toByte
              && t.nFlag == (gaqryque.GAQRYCAND_FLAG_RECHECKED.toByte | gaqryque.GAQRYCAND_FLAG_BROKEN.toByte).toByte){
          val destTpCard = tPCardService.getTPCard(t.szKey)
          val ttHit = new TtHitResultPackage
          ttHit.taskId = oraSid
          ttHit.comparisonSystemTypeDescript = fpt4code.GAIMG_CPRMETHOD_EGFS_CODE
          ttHit.ttHitTypeCode = fpt5util.TT_MATCHED_NORMAL
          ttHit.originalPersonId = sourceTpCard.getStrMisPersonID
          ttHit.jingZongPersonId = sourceTpCard.getStrJingZongPersonId
          ttHit.personId = sourceTpCard.getStrCasePersonID
          ttHit.cardId = sourceTpCard.getStrCardID
          ttHit.whetherFingerJudgmentMark = fpt5util.FINGER.toString
          ttHit.resultOriginalSystemPersonId = destTpCard.getStrMisPersonID
          ttHit.resultjingZongPersonId = destTpCard.getStrJingZongPersonId
          ttHit.resultPersonId = destTpCard.getStrCasePersonID
          ttHit.resultCardId = destTpCard.getStrCardID
          ttHit.hitUnitCode = queryStruct.pstInfo_Data.szCheckerUnitCode
          //获取用户信息
          val userStruct = new GAFIS_USERSTRUCT()
          userStruct.stInfo.szName = queryStruct.stSimpQry.szCheckUserName
          facade.NET_GAFIS_USER_GetUserInfo(V62Facade.DBID_ADMIN_DEFAULT, V62Facade.TID_USER, userStruct)

          val hitUserInfo = userStruct.stInfo
          ttHit.hitUnitName = "" //TODO:
          ttHit.hitPersonName = hitUserInfo.szFullName
          ttHit.hitPersonIdCard = hitUserInfo.szMail //C组最终决定将身份证号存mail属性里
          ttHit.hitPersonTel = hitUserInfo.szPhone
          ttHit.hitDateTime = DateConverter.convertAFISDateTime2String(queryStruct.stSimpQry.tCheckTime)
          ttHit.checkUnitCode = queryStruct.pstInfo_Data.szReCheckerUnitCode
          ttHit.checkUnitName = "" //TODO:

          userStruct.stInfo.szName = queryStruct.stSimpQry.szReCheckUserName
          facade.NET_GAFIS_USER_GetUserInfo(V62Facade.DBID_ADMIN_DEFAULT, V62Facade.TID_USER, userStruct)

          val reCheckUserInfo = userStruct.stInfo
          ttHit.checkPersonName =  reCheckUserInfo.szFullName
          ttHit.checkPersonIdCard =  reCheckUserInfo.szMail //C组最终决定将身份证号存mail属性里
          ttHit.checkPersonTel =  reCheckUserInfo.szPhone
          ttHit.checkDateTime = DateConverter.convertAFISDateTime2String(queryStruct.stSimpQry.tReCheckDate)
          ttHit.memo = ""
          ttHitList += ttHit
        }
    }
    ttHitList
  }

  /**
    * 获取正查或倒查比中关系
    * 通过解析hithistory字段获取比中信息
    * @param cardId
    * @return
    */
  override def getLtHitResultPackage(cardId: String, isLatent: Boolean): Seq[LtHitResultPackage] = {
    val ltHitList = new ArrayBuffer[LtHitResultPackage]()
    val hitHistory = getHitHistory(cardId, isLatent)
    if(hitHistory.nonEmpty){
      val breakInfos = XmlLoader.parseXML[BreakInfos](hitHistory)
      breakInfos.breakRecords.foreach{record=>
        val ltHit = new LtHitResultPackage
        ltHit.fingerPrintCardId = cardId
        ltHit.latentFingerCardId = record.latentID
        //TODO 补全其他信息
        ltHitList += ltHit
      }
    }

    ltHitList
  }


  /**
    * 获取串查比中关系
    * 先获取现场关联组号，根据现场关联组号，获取现场关联信息
    * @param cardId
    * @return
    */
  override def getLlHitResultPackage(cardId: String): Seq[LlHitResultPackage] = {
    val llHitList = new ArrayBuffer[LlHitResultPackage]()
    val groupName = getGroupName(cardId)
    if(groupName.nonEmpty){
      val lpGroup = getGAFIS_LPGROUPSTRUCT(groupName)
      lpGroup.pstKeyList_Data.foreach{key=>
        if(!cardId.equals(key.szKey)){//排除自己
          val llHit = new LlHitResultPackage
          llHit.cardId = cardId
          llHit.resultCardId = key.szKey
          //TODO 补全其他信息
          llHitList += llHit
        }
      }
    }

    llHitList
  }

  /**
    * 获取正查或倒查比中关系
    * @param cardId   现场指纹编号
    * @param isLatent 是否现场
    * @return
    */
  override def getLogic04Rec(cardId: String, isLatent: Boolean): Seq[Logic04Rec] = {
    val logic04RecList = new ArrayBuffer[Logic04Rec]()
    val hitHistory = getHitHistory(cardId, isLatent)
    if(hitHistory.nonEmpty){
      val breakInfos = XmlLoader.parseXML[BreakInfos](hitHistory)
      breakInfos.breakRecords.foreach{record=>
        val logic04Rec = new Logic04Rec
        logic04Rec.caseId = record.latentID.substring(0, record.latentID.length - 2)//案件编号可能为空，使用现场卡号截取
        logic04Rec.seqNo = record.latentID.substring(record.latentID.length-2)//现场序号截取现场卡号后2位
        logic04Rec.personId = record.tprCardID //捺印卡号，由于人员编号可能为空，这里使用捺印卡号
        logic04Rec.fgp = record.nFgIndex.toString
        logic04Rec.matchMethod = getMatchMethod(record.queryType)
        logic04Rec.matchUnitCode = record.breakUnitCode
        logic04Rec.matcher = record.breakUserName
        logic04Rec.matchDate = record.breakDateTime
        logic04Rec.recheckUnitCode = record.reCheckUnitCode
        logic04Rec.rechecker = record.reCheckUserName
        logic04Rec.recheckDate = record.reCheckDate

        logic04RecList += logic04Rec
      }
    }

    logic04RecList
  }

  /**
    * 获取重卡比中关系
    * @param cardId
    * @return
    */
  override def getLogic05Rec(cardId: String): Seq[Logic05Rec] = {
    val logic05RecList = new ArrayBuffer[Logic05Rec]()
    //首先获取重卡组号
    val personId = getPersonid(cardId)
    if(personId.nonEmpty){
      val m_stPersonInfo = getGPERSONINFOSTRUCT(personId)
      m_stPersonInfo.pstID_Data.foreach{personInfo =>
        if(!cardId.equals(personInfo.szCardID)){//排除自己
          val logic05Rec = new Logic05Rec
          logic05Rec.personId1 = cardId
          logic05Rec.personId2 = personInfo.szCardID
          logic05Rec.matcher = personInfo.szUserName
          logic05Rec.matchDate = DateConverter.convertAFISDateTime2String(personInfo.tCheckTime)

          logic05RecList += logic05Rec
        }
      }
    }

    logic05RecList
  }


  /**
    * 获取串查比中关系
    * @param cardId 现场卡号
    * @return
    */
  override def getLogic06Rec(cardId: String): Seq[Logic06Rec] = {
    val logic06RecList = new ArrayBuffer[Logic06Rec]()
    val groupName = getGroupName(cardId)
    if(groupName.nonEmpty){
      val lpGroup = getGAFIS_LPGROUPSTRUCT(groupName)
      lpGroup.pstKeyList_Data.foreach{key=>
        if(!cardId.equals(key.szKey)){//排除自己
          val logic06Rec = new Logic06Rec
          logic06Rec.caseId1 = cardId.substring(0, cardId.length - 2)//现场卡号去掉2位表示案件编号
          logic06Rec.seqNo1 = cardId.substring(cardId.length - 2)
          logic06Rec.caseId2 = key.szKey.substring(0, key.szKey.length - 2)
          logic06Rec.seqNo2 = key.szKey.substring(key.szKey.length - 2)
          logic06Rec.matcher = key.szUserName
          logic06Rec.matchUnitCode = key.szUnitCode
          logic06Rec.matchDate = DateConverter.convertAFISDateTime2String(key.tDateTime)

          logic06RecList += logic06Rec
        }
      }
    }

    logic06RecList
  }

  /**
    * 获取现场关联信息
    * @param groupName 串卡组号
    * @return
    */
  private def getGAFIS_LPGROUPSTRUCT(groupName: String): GAFIS_LPGROUPSTRUCT ={
    val lpGroup = new GAFIS_LPGROUPSTRUCT
    lpGroup.szGroupID = groupName
    facade.NET_GAFIS_LPGROUP_Get(v62Config.latentTable.dbId.toShort, V62Facade.TID_LPGROUP, lpGroup)

    lpGroup
  }

  /**
    * 获取重卡组信息
    * @param personid 重卡组号
    */
  private def getGPERSONINFOSTRUCT(personid: String): GPERSONINFOSTRUCT ={
    val m_stPersonInfo = new GPERSONINFOSTRUCT
    m_stPersonInfo.szPersonID = personid
    m_stPersonInfo.nItemFlag = (GPIS_ITEMFLAG_CARDCOUNT | GPIS_ITEMFLAG_CARDID | GPIS_ITEMFLAG_TEXT).toByte
    m_stPersonInfo.nItemFlag2 = (GPIS_ITEMFLAG2_LPGROUPDBID | GPIS_ITEMFLAG2_LPGROUPTID | GPIS_ITEMFLAG2_FLAG).toByte
    //获取dbid，tableid
//    val dbDef = facade.NET_GAFIS_MISC_GetDefDBID()
    //根据重卡组号读取重卡组信息
    facade.NET_GAFIS_PERSON_Get(V62Facade.DBID_TP_DEFAULT, V62Facade.TID_PERSONINFO, m_stPersonInfo)

    m_stPersonInfo
  }

  /**
    * 获取重卡组号
    * @param cardId 卡号
    * @return
    */
  private def getPersonid(cardId: String): String = {
    val mapper = Map(
      g_stCN.stTAdm.pszPersonID -> "szKey"
    )
    val statementOpt = Option("(CardID='%s')".format(cardId))
    val keyList = facade.queryV62Table[GAKEYSTRUCT](v62Config.templateTable.dbId.toShort, v62Config.templateTable.tableId.toShort, mapper, statementOpt, 1)

    if(keyList.nonEmpty){
      keyList.head.szKey
    }else{
      ""
    }
  }
  /**
    * 获取捺印比中信息
    * @param cardId 卡号
    * @return
    */
  private def getHitHistory(cardId: String, isLatent: Boolean): String = {
    var db = v62Config.templateTable
    if(isLatent){
      db = v62Config.latentTable
    }
    val data = facade.NET_GAFIS_COL_GetByKey(db.dbId.toShort, db.tableId.toShort, cardId, g_stCN.stTCardText.pszHitHistory)
    if(data != null && data.length > 0){
      new String(data)
    }else{
      ""
    }
  }

  /**
    * 获取现场关联
    * @param cardId
    */
  private def getGroupName(cardId: String): String ={
    val data = facade.NET_GAFIS_COL_GetByKey(v62Config.latentTable.dbId.toShort, v62Config.latentTable.tableId.toShort, cardId, g_stCN.stLAdm.pszGroupName)
    if(data != null && data.length > 0){
      new String(data)
    }else{
      ""
    }
  }

  /**
    * 比对方法 1:正查/2:倒查/9:其它
    * @param queryType
    * @return
    */
  private def getMatchMethod(queryType: Int): String ={
    queryType match {
      case QueryConstants.QUERY_TYPE_LT => "1"
      case QueryConstants.QUERY_TYPE_TL => "2"
      case other => "9"
    }
  }

  /**
    * 获取正查或倒查比中关系
    *
    * @param oraSid 任务号
    * @return
    */
  override def getLtHitResultPackageByOraSid(oraSid: String): Seq[LtHitResultPackage] = ???

  override def getLlHitResultPackageByOraSid(oraSid: String): Seq[LlHitResultPackage] = ???
}

package nirvana.hall.v62.internal

import nirvana.hall.api.services.TPCardService
import nirvana.hall.c.services.gfpt4lib.FPT4File
import nirvana.hall.c.services.gfpt4lib.FPT4File.Logic02Rec
import nirvana.hall.c.services.gloclib.galoctp.GTPCARDINFOSTRUCT
import nirvana.hall.c.services.gloclib.gatplpassociate.GAFIS_TPLP_ASSOCIATE
import nirvana.hall.protocol.api.FPTProto.TPCard
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.c.V62SqlHelper
import nirvana.hall.v62.internal.c.gloclib.galoctpConverter
import nirvana.hall.v62.internal.c.gloclib.gcolnames._

/**
 * Created by songpeng on 16/1/26.
 */
class TPCardServiceImpl(facade:V62Facade,config:HallV62Config) extends TPCardService{
  /**
   * 新增捺印卡片
   * @param tPCard
   * @return
   */
  override def addTPCard(tPCard: TPCard, dbId: Option[String]): Unit = {
    val tpCard = galoctpConverter.convertProtoBuf2GTPCARDINFOSTRUCT(tPCard)
    facade.NET_GAFIS_FLIB_Add(getDBID(dbId),
      V62Facade.TID_TPCARDINFO,
      tPCard.getStrCardID,tpCard)
  }

  /**
   * 删除捺印卡片
   * @param cardId
   * @return
   */
  def delTPCard(cardId: String, dbId: Option[String]): Unit ={
    facade.NET_GAFIS_FLIB_Del(config.templateTable.dbId.toShort, V62Facade.TID_TPCARDINFO, cardId)
  }

  /**
   * 更新捺印卡片
   * @param tPCard
   * @return
   */
  override def updateTPCard(tPCard: TPCard, dbId: Option[String]): Unit = {
    val tpCard = galoctpConverter.convertProtoBuf2GTPCARDINFOSTRUCT(tPCard)
    facade.NET_GAFIS_FLIB_Update(config.templateTable.dbId.toShort, V62Facade.TID_TPCARDINFO,
      tPCard.getStrCardID, tpCard)
  }

  /**
   * 验证卡号是否已存在
   * @param cardId
   * @return
   */
  override def isExist(cardId: String, dbId: Option[String]): Boolean = {
    facade.NET_GAFIS_FLIB_Exist(getDBID(dbId), V62Facade.TID_TPCARDINFO, cardId)
  }

  /**
   * 获取捺印卡信息
   * @param cardId
   * @return
   */
  override def getTPCard(cardId: String, dbid: Option[String]): TPCard = {
    val tdbId = if(dbid == None){
      config.templateTable.dbId.toShort
    }else{
      dbid.get.toShort
    }
    val tp = new GTPCARDINFOSTRUCT
    facade.NET_GAFIS_FLIB_Get(tdbId, V62Facade.TID_TPCARDINFO,
      cardId, tp, null, 3)

    val mapper = Map(
      g_stCN.stNuminaCol.pszSID -> "nSID",
      g_stCN.stTPLPAssociate.pszLPGroupID -> "szLPGroupID"
    )
    var statement = "(1=1)"
    statement += V62SqlHelper.andSQL("TPPERSONID",tp.stAdmData.szPersonID)
    val tpLpAssocaites = facade.queryV62Table[GAFIS_TPLP_ASSOCIATE](21, 340, mapper, Option(statement), 1)
    val groupId =
      if(null!=tpLpAssocaites && tpLpAssocaites.size>0)
        tpLpAssocaites.head.szLPGroupID
      else ""

    galoctpConverter.convertGTPCARDINFOSTRUCT2ProtoBuf(tp,groupId)
  }

  /**
    * 查询捺印卡编号列表
    *
    * @param ryno        人员编号
    * @param xm          姓名
    * @param xb          性别
    * @param idno        身份证号码
    * @param zjlb        证件类别
    * @param zjhm        证件号码
    * @param hjddm       户籍地代码
    * @param xzzdm       现住址代码
    * @param rylb        人员类别
    * @param ajlb        案件类别
    * @param qkbs        前科标识
    * @param xcjb        协查级别
    * @param nydwdm      捺印单位代码
    * @param startnydate 开始时间（检索捺印时间，时间格式YYYYMMDDHHMM）
    * @param endnydate   结束时间（检索捺印时间，时间格式YYYYMMDDHHMM）
    * @return Logic02Rec(fpt4捺印文本信息)
    */
  override def getFPT4Logic02RecList(ryno: String, xm: String, xb: String, idno: String, zjlb: String, zjhm: String, hjddm: String, xzzdm: String, rylb: String, ajlb: String, qkbs: String, xcjb: String, nydwdm: String, startnydate: String, endnydate: String): Seq[Logic02Rec] = {
    val TCardText = g_stCN.stTCardText
    val mapper = Map(
      //MISPERSONID	MIS人员信息编号
      g_stCN.stTPnID.pszName -> "personId",
      //CARDID	卡号
      g_stCN.stTcID.pszName -> "cardId",
      //NAME	姓名
      TCardText.pszName -> "personName",
      //ALIAS 别名
      TCardText.pszAlias -> "alias",
      //SEXCODE	性别
      TCardText.pszSexCode -> "gender",
      //BIRTHDATE	出生日期
      TCardText.pszBirthDate -> "birthday",
      //NATIONALITY 国籍
      TCardText.pszNationality -> "nativeplace",
      //RACECODE	民族
      TCardText.pszRaceCode -> "nation",
      //SHENFENID	身份证号码
      TCardText.pszShenFenID -> "idCardNo",
      //CERTIFICATETYPE 证件类型
      TCardText.pszCertificateType -> "certificateType",
      //CERTIFICATECODE 证件号码
      TCardText.pszCertificateCode -> "certificateNo",
      //HUKOUPLACECODE	户口所在地代码
      TCardText.pszHuKouPlaceCode -> "door",
      //HUKOUPLACETAIL	户口所在地
      TCardText.pszHuKouPlaceTail -> "doorDetail",
      //ADDRESSCODE	现住址代码
      TCardText.pszAddressCode -> "address",
      //ADDRESSTAIL	现住址
      TCardText.pszAddressTail -> "addressDetail",
      //PERSONCLASSCODE 人员类别代码
      TCardText.pszPersonClassCode -> "category",
      //CASECLASS1CODE	案件类别1
      TCardText.pszCaseClass1Code -> "caseClass1Code",
      //CASECLASS2CODE	案件类别2
      TCardText.pszCaseClass2Code -> "caseClass2Code",
      //CASECLASS3CODE	案件类别3
      TCardText.pszCaseClass3Code -> "caseClass3Code",
      //ISCRIMINALRECORD	前科标识 否48 是49
      TCardText.pszIsCriminalRecord -> "isCriminal",
      //CRIMINALRECORDDESC	前科情况
      TCardText.pszCriminalRecordDesc -> "criminalInfo",
      //PRINTERUNITCODE	捺印人单位代码
      TCardText.pszPrinterUnitCode -> "gatherUnitCode",
      //PRINTERUNITNAMETAIL	捺印人单位
      TCardText.pszPrinterUnitNameTail -> "gatherUnitName",
      //PRINTERNAME	捺印人
      TCardText.pszPrinterName -> "gatherName",
      //PRINTDATE	捺印日期
      TCardText.pszPrintDate -> "gatherDate",
      //ORACOMMENT 备注   CLOB
      TCardText.pszComment -> "remark"
      //   PRINTERUNITCODE
      // 捺印单位代码
      //      TCardText.pszUnitNameCode -> "gatherUnitCode"
    )
    var statement = "(1=1)"
    statement += V62SqlHelper.likeSQL(g_stCN.stTcID.pszName, ryno)
    statement += V62SqlHelper.likeSQL(TCardText.pszName, xm)
    statement += V62SqlHelper.andSQL(TCardText.pszSexCode, xb)
    statement += V62SqlHelper.andSQL(TCardText.pszShenFenID, idno)
    statement += V62SqlHelper.andSQL(TCardText.pszCertificateType, zjlb)
    statement += V62SqlHelper.andSQL(TCardText.pszCertificateCode, zjhm)
    statement += V62SqlHelper.andSQL(TCardText.pszHuKouPlaceCode, hjddm)
    statement += V62SqlHelper.andSQL(TCardText.pszAddressCode, xzzdm)
    statement += V62SqlHelper.andSQL(TCardText.pszPersonClassCode, rylb)
    statement += V62SqlHelper.andSQL(TCardText.pszIsCriminalRecord, qkbs)
    statement += V62SqlHelper.andSQL(TCardText.pszPrinterUnitCode, nydwdm)
    //无协查级别 xcjb
    if(V62SqlHelper.isNonBlank(ajlb)){
      statement += " AND (%s = '%s' OR %s = '%s' OR %s = '%s')"
        .format(TCardText.pszCaseClass1Code,ajlb,TCardText.pszCaseClass2Code,ajlb, TCardText.pszCaseClass3Code, ajlb)
    }
    if(V62SqlHelper.isNonBlank(startnydate)){
      statement += " AND (%s >= %s)".format(TCardText.pszPrintDate, startnydate.substring(0,8))
    }
    if(V62SqlHelper.isNonBlank(endnydate)){
      statement += " AND (%s <= %s)".format(TCardText.pszPrintDate, endnydate.substring(0,8))
    }

    val logic02RecList = facade.queryV62Table[Logic02Rec](V62Facade.DBID_TP_DEFAULT, V62Facade.TID_TPCARDINFO, mapper, Option(statement), 256)//最大返回256
    //初始数据丢失，重新赋值
    logic02RecList.foreach(_.head.dataType = FPT4File.LOGIC02REC_DATATYPE)

    logic02RecList
  }

  /**
   * 获取DBID
    *
    * @param dbId
   */
  private def getDBID(dbId: Option[String]): Short ={
    if(dbId == None){
      config.templateTable.dbId.toShort
    }else{
      dbId.get.toShort
    }
  }

  /**
    * 针对海鑫综采对接使用
    * 6.2不实现该方法
    * @param tpCard
    * @param dbId
    */
  override def addTPCardHXZC(tpCard: TPCard, dbId: Option[String]): Unit = {}
}

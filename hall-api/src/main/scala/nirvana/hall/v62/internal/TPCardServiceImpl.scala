package nirvana.hall.v62.internal

import nirvana.hall.api.services.TPCardService
import nirvana.hall.c.services.gfpt4lib.FPT4File.Logic02Rec
import nirvana.hall.c.services.gloclib.galoctp.GTPCARDINFOSTRUCT
import nirvana.hall.protocol.api.FPTProto.TPCard
import nirvana.hall.v62.config.HallV62Config
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

    galoctpConverter.convertGTPCARDINFOSTRUCT2ProtoBuf(tp)
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
    //TODO 补全查询的文本信息，解决中文乱码
    val mapper = Map(
      g_stCN.stTcID.pszName -> "cardId",
      TCardText.pszName -> "personName",
      TCardText.pszSexCode -> "gender",
      TCardText.pszShenFenID -> "idCardNo",
      TCardText.pszCertificateCode -> "certificateNo",
      TCardText.pszCertificateType -> "certificateType",
      TCardText.pszHuKouPlaceCode -> "door",
      TCardText.pszAddressCode -> "address",
//      TCardText.pszPersonClassCode -> "",
      TCardText.pszCaseClass1Code -> "caseClass1Code",
//      TCardText.pszIsCriminalRecord -> "",
      TCardText.pszUnitNameCode -> "gatherUnitCode"
    )
    var statement = "(1=1)"
    statement += likeSQL(g_stCN.stTcID.pszName, ryno)
    statement += likeSQL(TCardText.pszName, xm)
    statement += andSQL(TCardText.pszSexCode, xb)
    statement += andSQL(TCardText.pszShenFenID, idno)
    statement += andSQL(TCardText.pszCertificateCode, zjhm)
    statement += andSQL(TCardText.pszCertificateType, zjlb)
    statement += andSQL(TCardText.pszHuKouPlaceCode, hjddm)
    statement += andSQL(TCardText.pszAddressCode, xzzdm)
    statement += andSQL(TCardText.pszPersonClassCode, rylb)
    statement += andSQL(TCardText.pszIsCriminalRecord, qkbs)
    statement += andSQL(TCardText.pszUnitNameCode, nydwdm)
    //xcj
    if(isNonBlank(ajlb)){
      statement += " AND (%s = '%s' OR %s = '%s' OR %s = '%s')"
        .format(TCardText.pszCaseClass1Code,ajlb,TCardText.pszCaseClass2Code,ajlb, TCardText.pszCaseClass3Code, ajlb)
    }
    if(isNonBlank(startnydate)){
      statement += " AND (%s >= %s)".format(TCardText.pszPrintDate, startnydate)
    }
    if(isNonBlank(endnydate)){
      statement += " AND (%s >= %s)".format(TCardText.pszPrintDate, endnydate)
    }

    facade.queryV62Table[Logic02Rec](V62Facade.DBID_TP_DEFAULT, V62Facade.TID_TPCARDINFO, mapper, Option(statement), 256)//最大返回256
  }
  def isNonBlank(string: String):Boolean = string != null && string.length >0
  def likeSQL(column: String, value: String): String ={
    if(isNonBlank(value)){
      " AND (%s LIKE '%s%%')".format(column, value)
    }else{
      ""
    }
  }
  def andSQL(column: String, value: String): String ={
    if(isNonBlank(value)){
      " AND (%s = '%s')".format(column, value)
    }else{
      ""
    }
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
}

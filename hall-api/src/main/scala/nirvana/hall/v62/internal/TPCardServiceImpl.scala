package nirvana.hall.v62.internal

import java.io.{BufferedReader, Reader}

import nirvana.hall.api.services.TPCardService
import nirvana.hall.c.services.gbaselib.gbasedef.GAKEYSTRUCT
import nirvana.hall.c.services.gloclib.galoctp.GTPCARDINFOSTRUCT
import nirvana.hall.protocol.api.FPTProto.TPCard
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.c.gloclib.galoctpConverter
import nirvana.hall.v62.internal.c.gloclib.gcolnames._
import nirvana.hall.support.services.JdbcDatabase
import javax.sql.DataSource

import scala.collection.mutable

/**
 * Created by songpeng on 16/1/26.
 */
class TPCardServiceImpl(facade:V62Facade,config:HallV62Config,implicit val dataSource: DataSource) extends TPCardService{
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
    * @return
    */
  override def getCardIdList(ryno: String, xm: String, xb: String, idno: String, zjlb: String, zjhm: String, hjddm: String, xzzdm: String, rylb: String, ajlb: String, qkbs: String, xcjb: String, nydwdm: String, startnydate: String, endnydate: String): Seq[String] = {
    val mapper = Map(
      g_stCN.stTcID.pszName -> "szKey"
    )
    var statement = "(1=1)"
    //TODO 补全所有查询条件，并支持like
    if(isNonBlank(ryno)){
      //mispersonid
      statement += " AND (cardid like '%s')".format("%"+ryno+"%")
    }
    if(isNonBlank(xm)){
      statement += " AND (name like '%s')".format("%"+xm+"%")
    }
    if(isNonBlank(xb)){
      statement += " AND (sexcode = '%s')".format(xb)
    }
    if(isNonBlank(idno)){
      statement += " AND (shenfenid like '%s')".format("%"+idno+"%")
    }
    //证件类别
    if(isNonBlank(zjlb)){
      statement += " AND (certificatetype = '%s')".format(zjlb)
    }
    //证件号码
    if(isNonBlank(zjhm)){
      statement += " AND (certificatecode like '%s')".format("%"+zjhm+"%")
    }
    //户籍地代码
    if(isNonBlank(hjddm)){
      statement += " AND (hukouplacetail like '%s')".format("%"+hjddm+"%")
    }
    //现住址代码
    if(isNonBlank(xzzdm)){
      statement += " AND (addresscode like '%s')".format("%"+xzzdm+"%")
    }
    //人员类别
    if(isNonBlank(rylb)){
      statement += " AND (personclasscode = '%s')".format(rylb)
    }
    //案件类别
    if(isNonBlank(ajlb)){
      statement += " AND (caseclass1code = '%s' or caseclass2code = '%s' or caseclass3code = '%s')".format(ajlb,ajlb,ajlb)
    }
    //前科标识 否48 是49
    if(isNonBlank(qkbs)){
      statement += " AND (iscriminalrecord='%s')".format(qkbs)
    }
    //协查级别 (未找到)
    if(isNonBlank(xcjb)){
      // statement += " AND (caseclass1code='%s')".format(xcjb)
    }
    //开始时间  时间格式YYYYMMDDHHMM
    if(isNonBlank(startnydate)){
      statement += " AND (printdate >= '%s')".format(startnydate.substring(0,8))
    }
    //结束时间  时间格式YYYYMMDDHHMM
    if(isNonBlank(endnydate)){
      statement += " AND (printdate <= '%s')".format(endnydate.substring(0,8))
    }

    val cardIdList = facade.queryV62Table[GAKEYSTRUCT](V62Facade.DBID_TP_DEFAULT, V62Facade.TID_TPCARDINFO, mapper, Option(statement), 256)//最大返回256
    cardIdList.map{ key =>
      key.szKey
    }
  }

  /**
    * 根据查询捺印卡id列表查询捺印数据
    * @param cardIdList
    * @return
    */
  def getTpCardList(cardIdList: Seq[String]): mutable.ListBuffer[mutable.HashMap[String,Any]] = {
    var and = ""
    var cardids = ""
    cardIdList.foreach { e =>
      cardids += and + "?"
      and = ","
    }
    val sql = "select " +
      "t.mispersonid mispersonid, " +
      "t.cardid cardid, " +
      "t.name name, " +
      "t.alias alias, " +
      "t.sexcode sexcode, " +
      "t.birthdate birthdate, " +
      "t.nationality nationality, " +
      "t.racecode racecode, " +
      "t.shenfenid shenfenid, " +
      "t.certificatetype certificatetype, " +
      "t.certificatecode certificatecode, " +
      "t.hukouplacecode hukouplacecode, " +
      "t.hukouplacetail hukouplacetail, " +
      "t.addresstail addresstail, " +
      "t.addresscode addresscode, " +
      "t.personclasscode personclasscode, " +
      "t.caseclass1code caseclass1code, " +
      "t.caseclass2code caseclass2code, " +
      "t.caseclass3code caseclass3code, " +
      "t.iscriminalrecord iscriminalrecord, " +
      "t.criminalrecorddesc criminalrecorddesc, " +
      "t.printerunitcode printerunitcode, " +
      "t.printerunitnametail printerunitnametail, " +
      "t.printername printername, " +
      "t.printdate printdate " +
      "from normaltp_tpcardinfo t " +
      "where t.cardid in ("+cardids+") "
    var resultList = new mutable.ListBuffer[mutable.HashMap[String,Any]]
    JdbcDatabase.queryWithPsSetter(sql) { ps =>
      for(i <- 1 to cardIdList.length){
        ps.setString(i, cardIdList(i-1))
      }
    } { rs =>
      var map = new scala.collection.mutable.HashMap[String,Any]
      //MISPERSONID	MIS人员信息编号  VARCHAR2(32)
      map += ("mispersonid" -> rs.getString("mispersonid"))
      //CARDID	卡号 VARCHAR2(32)
      map += ("cardid" -> rs.getString("cardid"))
      //NAME	姓名 VARCHAR2(40)
      map += ("name" -> rs.getString("name"))
      //ALIAS	别名 VARCHAR2(40)
      map += ("alias" -> rs.getString("alias"))
      //SEXCODE	性别 VARCHAR2(6)
      map += ("sexcode" -> rs.getString("sexcode"))
      //BIRTHDATE	出生日期 VARCHAR2(9)
      map += ("birthdate" -> rs.getString("birthdate"))
      //NATIONALITY 国籍  VARCHAR2(6)
      map += ("nationality" -> rs.getString("nationality"))
      //RACECODE	民族 VARCHAR2(8)
      map += ("racecode" -> rs.getString("racecode"))
      //SHENFENID	身份证号码 VARCHAR2(32)
      map += ("shenfenid" -> rs.getString("shenfenid"))
      //CERTIFICATETYPE 证件类型 VARCHAR2(6)
      map += ("certificatetype" -> rs.getString("certificatetype"))
      //CERTIFICATECODE 证件号码 VARCHAR2(32)
      map += ("certificatecode" -> rs.getString("certificatecode"))
      //HUKOUPLACECODE	户口所在地代码 VARCHAR2(16)
      map += ("hukouplacecode" -> rs.getString("hukouplacecode"))
      // HUKOUPLACETAIL	户口所在地 VARCHAR2(70)
      map += ("hukouplacetail" -> rs.getString("hukouplacetail"))
      //ADDRESSTAIL	现住址 VARCHAR2(70)
      map += ("addresstail" -> rs.getString("addresstail"))
      //ADDRESSCODE	现住址代码 VARCHAR2(16)
      map += ("addresscode" -> rs.getString("addresscode"))
      //PERSONCLASSCODE 人员类别代码 VARCHAR2(16)
      map += ("personclasscode" -> rs.getString("personclasscode"))
      //CASECLASS1CODE	案件类别1 VARCHAR2(16)
      map += ("caseclass1code" -> rs.getString("caseclass1code"))
      //CASECLASS2CODE	案件类别2 VARCHAR2(16)
      map += ("caseclass2code" -> rs.getString("caseclass2code"))
      //CASECLASS3CODE	案件类别3 VARCHAR2(16)
      map += ("caseclass3code" -> rs.getString("caseclass3code"))
      //ISCRIMINALRECORD 前科标识 否48 是49 NUMBER(3)
      val iscriminalrecord = rs.getInt("iscriminalrecord")
      map += ("iscriminalrecord" -> iscriminalrecord)
      //CRIMINALRECORDDESC 前科情况 CLOB
      val clob : java.sql.Clob  = rs.getClob("criminalrecorddesc")
      var criminalrecorddesc = ""
      if(clob != null){
        criminalrecorddesc = clob2String(clob)
      }
      map += ("criminalrecorddesc" -> criminalrecorddesc)
      //PRINTERUNITCODE	捺印人单位代码 VARCHAR2(16)
      map += ("printerunitcode" -> rs.getString("printerunitcode"))
      //PRINTERUNITNAMETAIL	捺印人单位 VARCHAR2(70)
      map += ("printerunitnametail" -> rs.getString("printerunitnametail"))
      //PRINTERNAME	捺印人 VARCHAR2(40)
      map += ("printername" -> rs.getString("printername"))
      //PRINTDATE	捺印日期 VARCHAR2(9)
      map += ("printdate" -> rs.getString("printdate"))
      resultList.append(map)
    }
    resultList
  }

  def isNonBlank(string: String):Boolean = string != null && string.length >0

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
    * Clob转String
    * @param clob
    */
  private def clob2String(clob : java.sql.Clob): String ={
    var reader : Reader = null
    var br : BufferedReader = null
    try{
      reader = clob.getCharacterStream
      br  = new BufferedReader(reader)
      var criminalrecorddesc: String = ""
      var content: String = ""
      while((content = br.readLine()) != null && content != null){
        criminalrecorddesc += content
      }
      criminalrecorddesc
    } catch {
      case e: Exception => {
        throw e
      }
    } finally {
      if(null != br){
        try{
          br.close()
        } catch {
          case e: Exception => {
          }
        }
      }
      if(null != reader){
        try{
          reader.close()
        } catch {
          case e: Exception => {
          }
        }
      }
    }
  }

}

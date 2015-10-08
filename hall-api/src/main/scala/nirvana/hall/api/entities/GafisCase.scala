package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisCase(
  caseId: String,
  cardId: Option[String] = None,
  caseClassCode: Option[String] = None,
  caseOccurDate: Option[DateTime] = None,
  caseOccurPlaceCode: Option[String] = None,
  caseOccurPlaceDetail: Option[String] = None,
  caseBriefDetail: Option[String] = None,
  isMurder: Option[String] = None,
  amount: Option[String] = None,
  extractUnitCode: Option[String] = None,
  extractUnitName: Option[String] = None,
  extractDate: Option[DateTime] = None,
  extractor: Option[String] = None,
  suspiciousAreaCode: Option[String] = None,
  caseState: Option[String] = None,
  caseNature: Option[String] = None,
  remark: Option[String] = None,
  inputpsn: Option[String] = None,
  inputtime: Option[DateTime] = None,
  modifiedpsn: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  deletag: Option[String] = None,
  brokenStatus: Option[Short] = None,
  caseSource: Option[String] = None,
  createUnitCode: Option[String] = None,
  assistLevel: Option[String] = None,
  caseNatureOld: Option[String] = None,
  isChecked: Option[String] = None,
  fptExtractUnitCode: Option[String] = None,
  fptExtractUnitName: Option[String] = None,
  sid: Option[Long] = None,
  assistBonus: Option[String] = None,
  assistDeptCode: Option[String] = None,
  assistDeptName: Option[String] = None,
  assistDate: Option[String] = None,
  assistSign: Option[String] = None,
  assistRevokeSign: Option[String] = None) {

  def save()(implicit session: DBSession = GafisCase.autoSession): GafisCase = GafisCase.save(this)(session)

  def destroy()(implicit session: DBSession = GafisCase.autoSession): Unit = GafisCase.destroy(this)(session)

}


object GafisCase extends SQLSyntaxSupport[GafisCase] {

  override val tableName = "GAFIS_CASE"

  override val columns = Seq("CASE_ID", "CARD_ID", "CASE_CLASS_CODE", "CASE_OCCUR_DATE", "CASE_OCCUR_PLACE_CODE", "CASE_OCCUR_PLACE_DETAIL", "CASE_BRIEF_DETAIL", "IS_MURDER", "AMOUNT", "EXTRACT_UNIT_CODE", "EXTRACT_UNIT_NAME", "EXTRACT_DATE", "EXTRACTOR", "SUSPICIOUS_AREA_CODE", "CASE_STATE", "CASE_NATURE", "REMARK", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "DELETAG", "BROKEN_STATUS", "CASE_SOURCE", "CREATE_UNIT_CODE", "ASSIST_LEVEL", "CASE_NATURE_OLD", "IS_CHECKED", "FPT_EXTRACT_UNIT_CODE", "FPT_EXTRACT_UNIT_NAME", "SID", "ASSIST_BONUS", "ASSIST_DEPT_CODE", "ASSIST_DEPT_NAME", "ASSIST_DATE", "ASSIST_SIGN", "ASSIST_REVOKE_SIGN")

  def apply(gc: SyntaxProvider[GafisCase])(rs: WrappedResultSet): GafisCase = apply(gc.resultName)(rs)
  def apply(gc: ResultName[GafisCase])(rs: WrappedResultSet): GafisCase = new GafisCase(
    caseId = rs.get(gc.caseId),
    cardId = rs.get(gc.cardId),
    caseClassCode = rs.get(gc.caseClassCode),
    caseOccurDate = rs.get(gc.caseOccurDate),
    caseOccurPlaceCode = rs.get(gc.caseOccurPlaceCode),
    caseOccurPlaceDetail = rs.get(gc.caseOccurPlaceDetail),
    caseBriefDetail = rs.get(gc.caseBriefDetail),
    isMurder = rs.get(gc.isMurder),
    amount = rs.get(gc.amount),
    extractUnitCode = rs.get(gc.extractUnitCode),
    extractUnitName = rs.get(gc.extractUnitName),
    extractDate = rs.get(gc.extractDate),
    extractor = rs.get(gc.extractor),
    suspiciousAreaCode = rs.get(gc.suspiciousAreaCode),
    caseState = rs.get(gc.caseState),
    caseNature = rs.get(gc.caseNature),
    remark = rs.get(gc.remark),
    inputpsn = rs.get(gc.inputpsn),
    inputtime = rs.get(gc.inputtime),
    modifiedpsn = rs.get(gc.modifiedpsn),
    modifiedtime = rs.get(gc.modifiedtime),
    deletag = rs.get(gc.deletag),
    brokenStatus = rs.get(gc.brokenStatus),
    caseSource = rs.get(gc.caseSource),
    createUnitCode = rs.get(gc.createUnitCode),
    assistLevel = rs.get(gc.assistLevel),
    caseNatureOld = rs.get(gc.caseNatureOld),
    isChecked = rs.get(gc.isChecked),
    fptExtractUnitCode = rs.get(gc.fptExtractUnitCode),
    fptExtractUnitName = rs.get(gc.fptExtractUnitName),
    sid = rs.get(gc.sid),
    assistBonus = rs.get(gc.assistBonus),
    assistDeptCode = rs.get(gc.assistDeptCode),
    assistDeptName = rs.get(gc.assistDeptName),
    assistDate = rs.get(gc.assistDate),
    assistSign = rs.get(gc.assistSign),
    assistRevokeSign = rs.get(gc.assistRevokeSign)
  )

  val gc = GafisCase.syntax("gc")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(caseId: String)(implicit session: DBSession = autoSession): Option[GafisCase] = {
    withSQL {
      select.from(GafisCase as gc).where.eq(gc.caseId, caseId)
    }.map(GafisCase(gc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisCase] = {
    withSQL(select.from(GafisCase as gc)).map(GafisCase(gc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisCase as gc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisCase] = {
    withSQL {
      select.from(GafisCase as gc).where.append(where)
    }.map(GafisCase(gc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisCase] = {
    withSQL {
      select.from(GafisCase as gc).where.append(where)
    }.map(GafisCase(gc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisCase as gc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    caseId: String,
    cardId: Option[String] = None,
    caseClassCode: Option[String] = None,
    caseOccurDate: Option[DateTime] = None,
    caseOccurPlaceCode: Option[String] = None,
    caseOccurPlaceDetail: Option[String] = None,
    caseBriefDetail: Option[String] = None,
    isMurder: Option[String] = None,
    amount: Option[String] = None,
    extractUnitCode: Option[String] = None,
    extractUnitName: Option[String] = None,
    extractDate: Option[DateTime] = None,
    extractor: Option[String] = None,
    suspiciousAreaCode: Option[String] = None,
    caseState: Option[String] = None,
    caseNature: Option[String] = None,
    remark: Option[String] = None,
    inputpsn: Option[String] = None,
    inputtime: Option[DateTime] = None,
    modifiedpsn: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    deletag: Option[String] = None,
    brokenStatus: Option[Short] = None,
    caseSource: Option[String] = None,
    createUnitCode: Option[String] = None,
    assistLevel: Option[String] = None,
    caseNatureOld: Option[String] = None,
    isChecked: Option[String] = None,
    fptExtractUnitCode: Option[String] = None,
    fptExtractUnitName: Option[String] = None,
    sid: Option[Long] = None,
    assistBonus: Option[String] = None,
    assistDeptCode: Option[String] = None,
    assistDeptName: Option[String] = None,
    assistDate: Option[String] = None,
    assistSign: Option[String] = None,
    assistRevokeSign: Option[String] = None)(implicit session: DBSession = autoSession): GafisCase = {
    withSQL {
      insert.into(GafisCase).columns(
        column.caseId,
        column.cardId,
        column.caseClassCode,
        column.caseOccurDate,
        column.caseOccurPlaceCode,
        column.caseOccurPlaceDetail,
        column.caseBriefDetail,
        column.isMurder,
        column.amount,
        column.extractUnitCode,
        column.extractUnitName,
        column.extractDate,
        column.extractor,
        column.suspiciousAreaCode,
        column.caseState,
        column.caseNature,
        column.remark,
        column.inputpsn,
        column.inputtime,
        column.modifiedpsn,
        column.modifiedtime,
        column.deletag,
        column.brokenStatus,
        column.caseSource,
        column.createUnitCode,
        column.assistLevel,
        column.caseNatureOld,
        column.isChecked,
        column.fptExtractUnitCode,
        column.fptExtractUnitName,
        column.sid,
        column.assistBonus,
        column.assistDeptCode,
        column.assistDeptName,
        column.assistDate,
        column.assistSign,
        column.assistRevokeSign
      ).values(
        caseId,
        cardId,
        caseClassCode,
        caseOccurDate,
        caseOccurPlaceCode,
        caseOccurPlaceDetail,
        caseBriefDetail,
        isMurder,
        amount,
        extractUnitCode,
        extractUnitName,
        extractDate,
        extractor,
        suspiciousAreaCode,
        caseState,
        caseNature,
        remark,
        inputpsn,
        inputtime,
        modifiedpsn,
        modifiedtime,
        deletag,
        brokenStatus,
        caseSource,
        createUnitCode,
        assistLevel,
        caseNatureOld,
        isChecked,
        fptExtractUnitCode,
        fptExtractUnitName,
        sid,
        assistBonus,
        assistDeptCode,
        assistDeptName,
        assistDate,
        assistSign,
        assistRevokeSign
      )
    }.update.apply()

    new GafisCase(
      caseId = caseId,
      cardId = cardId,
      caseClassCode = caseClassCode,
      caseOccurDate = caseOccurDate,
      caseOccurPlaceCode = caseOccurPlaceCode,
      caseOccurPlaceDetail = caseOccurPlaceDetail,
      caseBriefDetail = caseBriefDetail,
      isMurder = isMurder,
      amount = amount,
      extractUnitCode = extractUnitCode,
      extractUnitName = extractUnitName,
      extractDate = extractDate,
      extractor = extractor,
      suspiciousAreaCode = suspiciousAreaCode,
      caseState = caseState,
      caseNature = caseNature,
      remark = remark,
      inputpsn = inputpsn,
      inputtime = inputtime,
      modifiedpsn = modifiedpsn,
      modifiedtime = modifiedtime,
      deletag = deletag,
      brokenStatus = brokenStatus,
      caseSource = caseSource,
      createUnitCode = createUnitCode,
      assistLevel = assistLevel,
      caseNatureOld = caseNatureOld,
      isChecked = isChecked,
      fptExtractUnitCode = fptExtractUnitCode,
      fptExtractUnitName = fptExtractUnitName,
      sid = sid,
      assistBonus = assistBonus,
      assistDeptCode = assistDeptCode,
      assistDeptName = assistDeptName,
      assistDate = assistDate,
      assistSign = assistSign,
      assistRevokeSign = assistRevokeSign)
  }

  def save(entity: GafisCase)(implicit session: DBSession = autoSession): GafisCase = {
    withSQL {
      update(GafisCase).set(
        column.caseId -> entity.caseId,
        column.cardId -> entity.cardId,
        column.caseClassCode -> entity.caseClassCode,
        column.caseOccurDate -> entity.caseOccurDate,
        column.caseOccurPlaceCode -> entity.caseOccurPlaceCode,
        column.caseOccurPlaceDetail -> entity.caseOccurPlaceDetail,
        column.caseBriefDetail -> entity.caseBriefDetail,
        column.isMurder -> entity.isMurder,
        column.amount -> entity.amount,
        column.extractUnitCode -> entity.extractUnitCode,
        column.extractUnitName -> entity.extractUnitName,
        column.extractDate -> entity.extractDate,
        column.extractor -> entity.extractor,
        column.suspiciousAreaCode -> entity.suspiciousAreaCode,
        column.caseState -> entity.caseState,
        column.caseNature -> entity.caseNature,
        column.remark -> entity.remark,
        column.inputpsn -> entity.inputpsn,
        column.inputtime -> entity.inputtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.modifiedtime -> entity.modifiedtime,
        column.deletag -> entity.deletag,
        column.brokenStatus -> entity.brokenStatus,
        column.caseSource -> entity.caseSource,
        column.createUnitCode -> entity.createUnitCode,
        column.assistLevel -> entity.assistLevel,
        column.caseNatureOld -> entity.caseNatureOld,
        column.isChecked -> entity.isChecked,
        column.fptExtractUnitCode -> entity.fptExtractUnitCode,
        column.fptExtractUnitName -> entity.fptExtractUnitName,
        column.sid -> entity.sid,
        column.assistBonus -> entity.assistBonus,
        column.assistDeptCode -> entity.assistDeptCode,
        column.assistDeptName -> entity.assistDeptName,
        column.assistDate -> entity.assistDate,
        column.assistSign -> entity.assistSign,
        column.assistRevokeSign -> entity.assistRevokeSign
      ).where.eq(column.caseId, entity.caseId)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisCase)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisCase).where.eq(column.caseId, entity.caseId) }.update.apply()
  }

}

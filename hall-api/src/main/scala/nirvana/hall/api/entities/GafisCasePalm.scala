package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}
import java.sql.{Blob}

case class GafisCasePalm(
  seqNo: Option[String] = None,
  palmId: String,
  caseId: Option[String] = None,
  isCorpse: Option[String] = None,
  corpseNo: Option[String] = None,
  remainPlace: Option[String] = None,
  fgp: Option[String] = None,
  pattern: Option[String] = None,
  ridgeColor: Option[String] = None,
  thanStatus: Option[String] = None,
  isAssist: Option[String] = None,
  palmImg: Option[Blob] = None,
  palmCpr: Option[Blob] = None,
  ltCount: Option[Long] = None,
  llCount: Option[Long] = None,
  ltCountModMnt: Option[Long] = None,
  llCountModMnt: Option[Long] = None,
  editCount: Option[Long] = None,
  ltDate: Option[DateTime] = None,
  llDate: Option[DateTime] = None,
  ltOperator: Option[String] = None,
  llOperator: Option[String] = None,
  creatorUnitCode: Option[String] = None,
  updatorUnitCode: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  modifiedpsn: Option[String] = None,
  inputtime: Option[DateTime] = None,
  inputpsn: Option[String] = None,
  deletag: Option[String] = None,
  remark: Option[String] = None,
  ltStatus: Option[String] = None,
  llStatus: Option[String] = None,
  sid: Option[Long] = None,
  matchStatus: Option[String] = None,
  developMethod: Option[String] = None,
  palmImgNosqlId: Option[String] = None,
  palmCprNosqlId: Option[String] = None,
  seq: Option[Long] = None) {

  def save()(implicit session: DBSession = GafisCasePalm.autoSession): GafisCasePalm = GafisCasePalm.save(this)(session)

  def destroy()(implicit session: DBSession = GafisCasePalm.autoSession): Unit = GafisCasePalm.destroy(this)(session)

}


object GafisCasePalm extends SQLSyntaxSupport[GafisCasePalm] {

  override val tableName = "GAFIS_CASE_PALM"

  override val columns = Seq("SEQ_NO", "PALM_ID", "CASE_ID", "IS_CORPSE", "CORPSE_NO", "REMAIN_PLACE", "FGP", "PATTERN", "RIDGE_COLOR", "THAN_STATUS", "IS_ASSIST", "PALM_IMG", "PALM_CPR", "LT_COUNT", "LL_COUNT", "LT_COUNT_MOD_MNT", "LL_COUNT_MOD_MNT", "EDIT_COUNT", "LT_DATE", "LL_DATE", "LT_OPERATOR", "LL_OPERATOR", "CREATOR_UNIT_CODE", "UPDATOR_UNIT_CODE", "MODIFIEDTIME", "MODIFIEDPSN", "INPUTTIME", "INPUTPSN", "DELETAG", "REMARK", "LT_STATUS", "LL_STATUS", "SID", "MATCH_STATUS", "DEVELOP_METHOD", "PALM_IMG_NOSQL_ID", "PALM_CPR_NOSQL_ID", "SEQ")

  def apply(gcp: SyntaxProvider[GafisCasePalm])(rs: WrappedResultSet): GafisCasePalm = apply(gcp.resultName)(rs)
  def apply(gcp: ResultName[GafisCasePalm])(rs: WrappedResultSet): GafisCasePalm = new GafisCasePalm(
    seqNo = rs.get(gcp.seqNo),
    palmId = rs.get(gcp.palmId),
    caseId = rs.get(gcp.caseId),
    isCorpse = rs.get(gcp.isCorpse),
    corpseNo = rs.get(gcp.corpseNo),
    remainPlace = rs.get(gcp.remainPlace),
    fgp = rs.get(gcp.fgp),
    pattern = rs.get(gcp.pattern),
    ridgeColor = rs.get(gcp.ridgeColor),
    thanStatus = rs.get(gcp.thanStatus),
    isAssist = rs.get(gcp.isAssist),
    palmImg = rs.get(gcp.palmImg),
    palmCpr = rs.get(gcp.palmCpr),
    ltCount = rs.get(gcp.ltCount),
    llCount = rs.get(gcp.llCount),
    ltCountModMnt = rs.get(gcp.ltCountModMnt),
    llCountModMnt = rs.get(gcp.llCountModMnt),
    editCount = rs.get(gcp.editCount),
    ltDate = rs.get(gcp.ltDate),
    llDate = rs.get(gcp.llDate),
    ltOperator = rs.get(gcp.ltOperator),
    llOperator = rs.get(gcp.llOperator),
    creatorUnitCode = rs.get(gcp.creatorUnitCode),
    updatorUnitCode = rs.get(gcp.updatorUnitCode),
    modifiedtime = rs.get(gcp.modifiedtime),
    modifiedpsn = rs.get(gcp.modifiedpsn),
    inputtime = rs.get(gcp.inputtime),
    inputpsn = rs.get(gcp.inputpsn),
    deletag = rs.get(gcp.deletag),
    remark = rs.get(gcp.remark),
    ltStatus = rs.get(gcp.ltStatus),
    llStatus = rs.get(gcp.llStatus),
    sid = rs.get(gcp.sid),
    matchStatus = rs.get(gcp.matchStatus),
    developMethod = rs.get(gcp.developMethod),
    palmImgNosqlId = rs.get(gcp.palmImgNosqlId),
    palmCprNosqlId = rs.get(gcp.palmCprNosqlId),
    seq = rs.get(gcp.seq)
  )

  val gcp = GafisCasePalm.syntax("gcp")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(palmId: String)(implicit session: DBSession = autoSession): Option[GafisCasePalm] = {
    withSQL {
      select.from(GafisCasePalm as gcp).where.eq(gcp.palmId, palmId)
    }.map(GafisCasePalm(gcp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisCasePalm] = {
    withSQL(select.from(GafisCasePalm as gcp)).map(GafisCasePalm(gcp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisCasePalm as gcp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisCasePalm] = {
    withSQL {
      select.from(GafisCasePalm as gcp).where.append(where)
    }.map(GafisCasePalm(gcp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisCasePalm] = {
    withSQL {
      select.from(GafisCasePalm as gcp).where.append(where)
    }.map(GafisCasePalm(gcp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisCasePalm as gcp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    seqNo: Option[String] = None,
    palmId: String,
    caseId: Option[String] = None,
    isCorpse: Option[String] = None,
    corpseNo: Option[String] = None,
    remainPlace: Option[String] = None,
    fgp: Option[String] = None,
    pattern: Option[String] = None,
    ridgeColor: Option[String] = None,
    thanStatus: Option[String] = None,
    isAssist: Option[String] = None,
    palmImg: Option[Blob] = None,
    palmCpr: Option[Blob] = None,
    ltCount: Option[Long] = None,
    llCount: Option[Long] = None,
    ltCountModMnt: Option[Long] = None,
    llCountModMnt: Option[Long] = None,
    editCount: Option[Long] = None,
    ltDate: Option[DateTime] = None,
    llDate: Option[DateTime] = None,
    ltOperator: Option[String] = None,
    llOperator: Option[String] = None,
    creatorUnitCode: Option[String] = None,
    updatorUnitCode: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    modifiedpsn: Option[String] = None,
    inputtime: Option[DateTime] = None,
    inputpsn: Option[String] = None,
    deletag: Option[String] = None,
    remark: Option[String] = None,
    ltStatus: Option[String] = None,
    llStatus: Option[String] = None,
    sid: Option[Long] = None,
    matchStatus: Option[String] = None,
    developMethod: Option[String] = None,
    palmImgNosqlId: Option[String] = None,
    palmCprNosqlId: Option[String] = None,
    seq: Option[Long] = None)(implicit session: DBSession = autoSession): GafisCasePalm = {
    withSQL {
      insert.into(GafisCasePalm).columns(
        column.seqNo,
        column.palmId,
        column.caseId,
        column.isCorpse,
        column.corpseNo,
        column.remainPlace,
        column.fgp,
        column.pattern,
        column.ridgeColor,
        column.thanStatus,
        column.isAssist,
        column.palmImg,
        column.palmCpr,
        column.ltCount,
        column.llCount,
        column.ltCountModMnt,
        column.llCountModMnt,
        column.editCount,
        column.ltDate,
        column.llDate,
        column.ltOperator,
        column.llOperator,
        column.creatorUnitCode,
        column.updatorUnitCode,
        column.modifiedtime,
        column.modifiedpsn,
        column.inputtime,
        column.inputpsn,
        column.deletag,
        column.remark,
        column.ltStatus,
        column.llStatus,
        column.sid,
        column.matchStatus,
        column.developMethod,
        column.palmImgNosqlId,
        column.palmCprNosqlId,
        column.seq
      ).values(
        seqNo,
        palmId,
        caseId,
        isCorpse,
        corpseNo,
        remainPlace,
        fgp,
        pattern,
        ridgeColor,
        thanStatus,
        isAssist,
        palmImg,
        palmCpr,
        ltCount,
        llCount,
        ltCountModMnt,
        llCountModMnt,
        editCount,
        ltDate,
        llDate,
        ltOperator,
        llOperator,
        creatorUnitCode,
        updatorUnitCode,
        modifiedtime,
        modifiedpsn,
        inputtime,
        inputpsn,
        deletag,
        remark,
        ltStatus,
        llStatus,
        sid,
        matchStatus,
        developMethod,
        palmImgNosqlId,
        palmCprNosqlId,
        seq
      )
    }.update.apply()

    new GafisCasePalm(
      seqNo = seqNo,
      palmId = palmId,
      caseId = caseId,
      isCorpse = isCorpse,
      corpseNo = corpseNo,
      remainPlace = remainPlace,
      fgp = fgp,
      pattern = pattern,
      ridgeColor = ridgeColor,
      thanStatus = thanStatus,
      isAssist = isAssist,
      palmImg = palmImg,
      palmCpr = palmCpr,
      ltCount = ltCount,
      llCount = llCount,
      ltCountModMnt = ltCountModMnt,
      llCountModMnt = llCountModMnt,
      editCount = editCount,
      ltDate = ltDate,
      llDate = llDate,
      ltOperator = ltOperator,
      llOperator = llOperator,
      creatorUnitCode = creatorUnitCode,
      updatorUnitCode = updatorUnitCode,
      modifiedtime = modifiedtime,
      modifiedpsn = modifiedpsn,
      inputtime = inputtime,
      inputpsn = inputpsn,
      deletag = deletag,
      remark = remark,
      ltStatus = ltStatus,
      llStatus = llStatus,
      sid = sid,
      matchStatus = matchStatus,
      developMethod = developMethod,
      palmImgNosqlId = palmImgNosqlId,
      palmCprNosqlId = palmCprNosqlId,
      seq = seq)
  }

  def save(entity: GafisCasePalm)(implicit session: DBSession = autoSession): GafisCasePalm = {
    withSQL {
      update(GafisCasePalm).set(
        column.seqNo -> entity.seqNo,
        column.palmId -> entity.palmId,
        column.caseId -> entity.caseId,
        column.isCorpse -> entity.isCorpse,
        column.corpseNo -> entity.corpseNo,
        column.remainPlace -> entity.remainPlace,
        column.fgp -> entity.fgp,
        column.pattern -> entity.pattern,
        column.ridgeColor -> entity.ridgeColor,
        column.thanStatus -> entity.thanStatus,
        column.isAssist -> entity.isAssist,
        column.palmImg -> entity.palmImg,
        column.palmCpr -> entity.palmCpr,
        column.ltCount -> entity.ltCount,
        column.llCount -> entity.llCount,
        column.ltCountModMnt -> entity.ltCountModMnt,
        column.llCountModMnt -> entity.llCountModMnt,
        column.editCount -> entity.editCount,
        column.ltDate -> entity.ltDate,
        column.llDate -> entity.llDate,
        column.ltOperator -> entity.ltOperator,
        column.llOperator -> entity.llOperator,
        column.creatorUnitCode -> entity.creatorUnitCode,
        column.updatorUnitCode -> entity.updatorUnitCode,
        column.modifiedtime -> entity.modifiedtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.inputtime -> entity.inputtime,
        column.inputpsn -> entity.inputpsn,
        column.deletag -> entity.deletag,
        column.remark -> entity.remark,
        column.ltStatus -> entity.ltStatus,
        column.llStatus -> entity.llStatus,
        column.sid -> entity.sid,
        column.matchStatus -> entity.matchStatus,
        column.developMethod -> entity.developMethod,
        column.palmImgNosqlId -> entity.palmImgNosqlId,
        column.palmCprNosqlId -> entity.palmCprNosqlId,
        column.seq -> entity.seq
      ).where.eq(column.palmId, entity.palmId)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisCasePalm)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisCasePalm).where.eq(column.palmId, entity.palmId) }.update.apply()
  }

}

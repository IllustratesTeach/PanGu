package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}
import java.sql.{Blob}

case class GafisCaseFinger(
  seqNo: Option[String] = None,
  fingerId: String,
  caseId: Option[String] = None,
  isCorpse: Option[String] = None,
  corpseNo: Option[String] = None,
  remainPlace: Option[String] = None,
  fgpGroup: Option[Short] = None,
  fgp: Option[String] = None,
  ridgeColor: Option[String] = None,
  pattern: Option[String] = None,
  mittensBegNo: Option[String] = None,
  mittensEndNo: Option[String] = None,
  thanStatus: Option[String] = None,
  isAssist: Option[String] = None,
  fingerImg: Option[Blob] = None,
  fingerCpr: Option[Blob] = None,
  ltCount: Option[Long] = None,
  llCount: Option[Long] = None,
  ltCountModMnt: Option[Long] = None,
  llCountModMnt: Option[Long] = None,
  editCount: Option[Long] = None,
  ltDate: Option[DateTime] = None,
  llDate: Option[DateTime] = None,
  ltOperator: Option[String] = None,
  llOperator: Option[String] = None,
  ltStatus: Option[String] = None,
  llStatus: Option[String] = None,
  creatorUnitCode: Option[String] = None,
  updatorUnitCode: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  modifiedpsn: Option[String] = None,
  inputtime: Option[DateTime] = None,
  inputpsn: Option[String] = None,
  deletag: Option[String] = None,
  remark: Option[String] = None,
  sid: Option[Long] = None,
  matchStatus: Option[String] = None,
  developMethod: Option[String] = None,
  fingerCprNosqlId: Option[String] = None,
  fingerImgNosqlId: Option[String] = None,
  seq: Option[Long] = None) {

  def save()(implicit session: DBSession = GafisCaseFinger.autoSession): GafisCaseFinger = GafisCaseFinger.save(this)(session)

  def destroy()(implicit session: DBSession = GafisCaseFinger.autoSession): Unit = GafisCaseFinger.destroy(this)(session)

}


object GafisCaseFinger extends SQLSyntaxSupport[GafisCaseFinger] {

  override val tableName = "GAFIS_CASE_FINGER"

  override val columns = Seq("SEQ_NO", "FINGER_ID", "CASE_ID", "IS_CORPSE", "CORPSE_NO", "REMAIN_PLACE", "FGP_GROUP", "FGP", "RIDGE_COLOR", "PATTERN", "MITTENS_BEG_NO", "MITTENS_END_NO", "THAN_STATUS", "IS_ASSIST", "FINGER_IMG", "FINGER_CPR", "LT_COUNT", "LL_COUNT", "LT_COUNT_MOD_MNT", "LL_COUNT_MOD_MNT", "EDIT_COUNT", "LT_DATE", "LL_DATE", "LT_OPERATOR", "LL_OPERATOR", "LT_STATUS", "LL_STATUS", "CREATOR_UNIT_CODE", "UPDATOR_UNIT_CODE", "MODIFIEDTIME", "MODIFIEDPSN", "INPUTTIME", "INPUTPSN", "DELETAG", "REMARK", "SID", "MATCH_STATUS", "DEVELOP_METHOD", "FINGER_CPR_NOSQL_ID", "FINGER_IMG_NOSQL_ID", "SEQ")

  def apply(gcf: SyntaxProvider[GafisCaseFinger])(rs: WrappedResultSet): GafisCaseFinger = apply(gcf.resultName)(rs)
  def apply(gcf: ResultName[GafisCaseFinger])(rs: WrappedResultSet): GafisCaseFinger = new GafisCaseFinger(
    seqNo = rs.get(gcf.seqNo),
    fingerId = rs.get(gcf.fingerId),
    caseId = rs.get(gcf.caseId),
    isCorpse = rs.get(gcf.isCorpse),
    corpseNo = rs.get(gcf.corpseNo),
    remainPlace = rs.get(gcf.remainPlace),
    fgpGroup = rs.get(gcf.fgpGroup),
    fgp = rs.get(gcf.fgp),
    ridgeColor = rs.get(gcf.ridgeColor),
    pattern = rs.get(gcf.pattern),
    mittensBegNo = rs.get(gcf.mittensBegNo),
    mittensEndNo = rs.get(gcf.mittensEndNo),
    thanStatus = rs.get(gcf.thanStatus),
    isAssist = rs.get(gcf.isAssist),
    fingerImg = rs.get(gcf.fingerImg),
    fingerCpr = rs.get(gcf.fingerCpr),
    ltCount = rs.get(gcf.ltCount),
    llCount = rs.get(gcf.llCount),
    ltCountModMnt = rs.get(gcf.ltCountModMnt),
    llCountModMnt = rs.get(gcf.llCountModMnt),
    editCount = rs.get(gcf.editCount),
    ltDate = rs.get(gcf.ltDate),
    llDate = rs.get(gcf.llDate),
    ltOperator = rs.get(gcf.ltOperator),
    llOperator = rs.get(gcf.llOperator),
    ltStatus = rs.get(gcf.ltStatus),
    llStatus = rs.get(gcf.llStatus),
    creatorUnitCode = rs.get(gcf.creatorUnitCode),
    updatorUnitCode = rs.get(gcf.updatorUnitCode),
    modifiedtime = rs.get(gcf.modifiedtime),
    modifiedpsn = rs.get(gcf.modifiedpsn),
    inputtime = rs.get(gcf.inputtime),
    inputpsn = rs.get(gcf.inputpsn),
    deletag = rs.get(gcf.deletag),
    remark = rs.get(gcf.remark),
    sid = rs.get(gcf.sid),
    matchStatus = rs.get(gcf.matchStatus),
    developMethod = rs.get(gcf.developMethod),
    fingerCprNosqlId = rs.get(gcf.fingerCprNosqlId),
    fingerImgNosqlId = rs.get(gcf.fingerImgNosqlId),
    seq = rs.get(gcf.seq)
  )

  val gcf = GafisCaseFinger.syntax("gcf")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(fingerId: String)(implicit session: DBSession = autoSession): Option[GafisCaseFinger] = {
    withSQL {
      select.from(GafisCaseFinger as gcf).where.eq(gcf.fingerId, fingerId)
    }.map(GafisCaseFinger(gcf.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisCaseFinger] = {
    withSQL(select.from(GafisCaseFinger as gcf)).map(GafisCaseFinger(gcf.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisCaseFinger as gcf)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisCaseFinger] = {
    withSQL {
      select.from(GafisCaseFinger as gcf).where.append(where)
    }.map(GafisCaseFinger(gcf.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisCaseFinger] = {
    withSQL {
      select.from(GafisCaseFinger as gcf).where.append(where)
    }.map(GafisCaseFinger(gcf.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisCaseFinger as gcf).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    seqNo: Option[String] = None,
    fingerId: String,
    caseId: Option[String] = None,
    isCorpse: Option[String] = None,
    corpseNo: Option[String] = None,
    remainPlace: Option[String] = None,
    fgpGroup: Option[Short] = None,
    fgp: Option[String] = None,
    ridgeColor: Option[String] = None,
    pattern: Option[String] = None,
    mittensBegNo: Option[String] = None,
    mittensEndNo: Option[String] = None,
    thanStatus: Option[String] = None,
    isAssist: Option[String] = None,
    fingerImg: Option[Blob] = None,
    fingerCpr: Option[Blob] = None,
    ltCount: Option[Long] = None,
    llCount: Option[Long] = None,
    ltCountModMnt: Option[Long] = None,
    llCountModMnt: Option[Long] = None,
    editCount: Option[Long] = None,
    ltDate: Option[DateTime] = None,
    llDate: Option[DateTime] = None,
    ltOperator: Option[String] = None,
    llOperator: Option[String] = None,
    ltStatus: Option[String] = None,
    llStatus: Option[String] = None,
    creatorUnitCode: Option[String] = None,
    updatorUnitCode: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    modifiedpsn: Option[String] = None,
    inputtime: Option[DateTime] = None,
    inputpsn: Option[String] = None,
    deletag: Option[String] = None,
    remark: Option[String] = None,
    sid: Option[Long] = None,
    matchStatus: Option[String] = None,
    developMethod: Option[String] = None,
    fingerCprNosqlId: Option[String] = None,
    fingerImgNosqlId: Option[String] = None,
    seq: Option[Long] = None)(implicit session: DBSession = autoSession): GafisCaseFinger = {
    withSQL {
      insert.into(GafisCaseFinger).columns(
        column.seqNo,
        column.fingerId,
        column.caseId,
        column.isCorpse,
        column.corpseNo,
        column.remainPlace,
        column.fgpGroup,
        column.fgp,
        column.ridgeColor,
        column.pattern,
        column.mittensBegNo,
        column.mittensEndNo,
        column.thanStatus,
        column.isAssist,
        column.fingerImg,
        column.fingerCpr,
        column.ltCount,
        column.llCount,
        column.ltCountModMnt,
        column.llCountModMnt,
        column.editCount,
        column.ltDate,
        column.llDate,
        column.ltOperator,
        column.llOperator,
        column.ltStatus,
        column.llStatus,
        column.creatorUnitCode,
        column.updatorUnitCode,
        column.modifiedtime,
        column.modifiedpsn,
        column.inputtime,
        column.inputpsn,
        column.deletag,
        column.remark,
        column.sid,
        column.matchStatus,
        column.developMethod,
        column.fingerCprNosqlId,
        column.fingerImgNosqlId,
        column.seq
      ).values(
        seqNo,
        fingerId,
        caseId,
        isCorpse,
        corpseNo,
        remainPlace,
        fgpGroup,
        fgp,
        ridgeColor,
        pattern,
        mittensBegNo,
        mittensEndNo,
        thanStatus,
        isAssist,
        fingerImg,
        fingerCpr,
        ltCount,
        llCount,
        ltCountModMnt,
        llCountModMnt,
        editCount,
        ltDate,
        llDate,
        ltOperator,
        llOperator,
        ltStatus,
        llStatus,
        creatorUnitCode,
        updatorUnitCode,
        modifiedtime,
        modifiedpsn,
        inputtime,
        inputpsn,
        deletag,
        remark,
        sid,
        matchStatus,
        developMethod,
        fingerCprNosqlId,
        fingerImgNosqlId,
        seq
      )
    }.update.apply()

    new GafisCaseFinger(
      seqNo = seqNo,
      fingerId = fingerId,
      caseId = caseId,
      isCorpse = isCorpse,
      corpseNo = corpseNo,
      remainPlace = remainPlace,
      fgpGroup = fgpGroup,
      fgp = fgp,
      ridgeColor = ridgeColor,
      pattern = pattern,
      mittensBegNo = mittensBegNo,
      mittensEndNo = mittensEndNo,
      thanStatus = thanStatus,
      isAssist = isAssist,
      fingerImg = fingerImg,
      fingerCpr = fingerCpr,
      ltCount = ltCount,
      llCount = llCount,
      ltCountModMnt = ltCountModMnt,
      llCountModMnt = llCountModMnt,
      editCount = editCount,
      ltDate = ltDate,
      llDate = llDate,
      ltOperator = ltOperator,
      llOperator = llOperator,
      ltStatus = ltStatus,
      llStatus = llStatus,
      creatorUnitCode = creatorUnitCode,
      updatorUnitCode = updatorUnitCode,
      modifiedtime = modifiedtime,
      modifiedpsn = modifiedpsn,
      inputtime = inputtime,
      inputpsn = inputpsn,
      deletag = deletag,
      remark = remark,
      sid = sid,
      matchStatus = matchStatus,
      developMethod = developMethod,
      fingerCprNosqlId = fingerCprNosqlId,
      fingerImgNosqlId = fingerImgNosqlId,
      seq = seq)
  }

  def save(entity: GafisCaseFinger)(implicit session: DBSession = autoSession): GafisCaseFinger = {
    withSQL {
      update(GafisCaseFinger).set(
        column.seqNo -> entity.seqNo,
        column.fingerId -> entity.fingerId,
        column.caseId -> entity.caseId,
        column.isCorpse -> entity.isCorpse,
        column.corpseNo -> entity.corpseNo,
        column.remainPlace -> entity.remainPlace,
        column.fgpGroup -> entity.fgpGroup,
        column.fgp -> entity.fgp,
        column.ridgeColor -> entity.ridgeColor,
        column.pattern -> entity.pattern,
        column.mittensBegNo -> entity.mittensBegNo,
        column.mittensEndNo -> entity.mittensEndNo,
        column.thanStatus -> entity.thanStatus,
        column.isAssist -> entity.isAssist,
        column.fingerImg -> entity.fingerImg,
        column.fingerCpr -> entity.fingerCpr,
        column.ltCount -> entity.ltCount,
        column.llCount -> entity.llCount,
        column.ltCountModMnt -> entity.ltCountModMnt,
        column.llCountModMnt -> entity.llCountModMnt,
        column.editCount -> entity.editCount,
        column.ltDate -> entity.ltDate,
        column.llDate -> entity.llDate,
        column.ltOperator -> entity.ltOperator,
        column.llOperator -> entity.llOperator,
        column.ltStatus -> entity.ltStatus,
        column.llStatus -> entity.llStatus,
        column.creatorUnitCode -> entity.creatorUnitCode,
        column.updatorUnitCode -> entity.updatorUnitCode,
        column.modifiedtime -> entity.modifiedtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.inputtime -> entity.inputtime,
        column.inputpsn -> entity.inputpsn,
        column.deletag -> entity.deletag,
        column.remark -> entity.remark,
        column.sid -> entity.sid,
        column.matchStatus -> entity.matchStatus,
        column.developMethod -> entity.developMethod,
        column.fingerCprNosqlId -> entity.fingerCprNosqlId,
        column.fingerImgNosqlId -> entity.fingerImgNosqlId,
        column.seq -> entity.seq
      ).where.eq(column.fingerId, entity.fingerId)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisCaseFinger)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisCaseFinger).where.eq(column.fingerId, entity.fingerId) }.update.apply()
  }

}

package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}
import java.sql.{Blob}

case class GafisCaseFingerXingzong(
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

  def save()(implicit session: DBSession = GafisCaseFingerXingzong.autoSession): GafisCaseFingerXingzong = GafisCaseFingerXingzong.save(this)(session)

  def destroy()(implicit session: DBSession = GafisCaseFingerXingzong.autoSession): Unit = GafisCaseFingerXingzong.destroy(this)(session)

}


object GafisCaseFingerXingzong extends SQLSyntaxSupport[GafisCaseFingerXingzong] {

  override val tableName = "GAFIS_CASE_FINGER_XINGZONG"

  override val columns = Seq("SEQ_NO", "FINGER_ID", "CASE_ID", "IS_CORPSE", "CORPSE_NO", "REMAIN_PLACE", "FGP_GROUP", "FGP", "RIDGE_COLOR", "PATTERN", "MITTENS_BEG_NO", "MITTENS_END_NO", "THAN_STATUS", "IS_ASSIST", "FINGER_IMG", "FINGER_CPR", "LT_COUNT", "LL_COUNT", "LT_COUNT_MOD_MNT", "LL_COUNT_MOD_MNT", "EDIT_COUNT", "LT_DATE", "LL_DATE", "LT_OPERATOR", "LL_OPERATOR", "LT_STATUS", "LL_STATUS", "CREATOR_UNIT_CODE", "UPDATOR_UNIT_CODE", "MODIFIEDTIME", "MODIFIEDPSN", "INPUTTIME", "INPUTPSN", "DELETAG", "REMARK", "SID", "MATCH_STATUS", "DEVELOP_METHOD", "FINGER_CPR_NOSQL_ID", "FINGER_IMG_NOSQL_ID", "SEQ")

  def apply(gcfx: SyntaxProvider[GafisCaseFingerXingzong])(rs: WrappedResultSet): GafisCaseFingerXingzong = apply(gcfx.resultName)(rs)
  def apply(gcfx: ResultName[GafisCaseFingerXingzong])(rs: WrappedResultSet): GafisCaseFingerXingzong = new GafisCaseFingerXingzong(
    seqNo = rs.get(gcfx.seqNo),
    fingerId = rs.get(gcfx.fingerId),
    caseId = rs.get(gcfx.caseId),
    isCorpse = rs.get(gcfx.isCorpse),
    corpseNo = rs.get(gcfx.corpseNo),
    remainPlace = rs.get(gcfx.remainPlace),
    fgpGroup = rs.get(gcfx.fgpGroup),
    fgp = rs.get(gcfx.fgp),
    ridgeColor = rs.get(gcfx.ridgeColor),
    pattern = rs.get(gcfx.pattern),
    mittensBegNo = rs.get(gcfx.mittensBegNo),
    mittensEndNo = rs.get(gcfx.mittensEndNo),
    thanStatus = rs.get(gcfx.thanStatus),
    isAssist = rs.get(gcfx.isAssist),
    fingerImg = rs.get(gcfx.fingerImg),
    fingerCpr = rs.get(gcfx.fingerCpr),
    ltCount = rs.get(gcfx.ltCount),
    llCount = rs.get(gcfx.llCount),
    ltCountModMnt = rs.get(gcfx.ltCountModMnt),
    llCountModMnt = rs.get(gcfx.llCountModMnt),
    editCount = rs.get(gcfx.editCount),
    ltDate = rs.get(gcfx.ltDate),
    llDate = rs.get(gcfx.llDate),
    ltOperator = rs.get(gcfx.ltOperator),
    llOperator = rs.get(gcfx.llOperator),
    ltStatus = rs.get(gcfx.ltStatus),
    llStatus = rs.get(gcfx.llStatus),
    creatorUnitCode = rs.get(gcfx.creatorUnitCode),
    updatorUnitCode = rs.get(gcfx.updatorUnitCode),
    modifiedtime = rs.get(gcfx.modifiedtime),
    modifiedpsn = rs.get(gcfx.modifiedpsn),
    inputtime = rs.get(gcfx.inputtime),
    inputpsn = rs.get(gcfx.inputpsn),
    deletag = rs.get(gcfx.deletag),
    remark = rs.get(gcfx.remark),
    sid = rs.get(gcfx.sid),
    matchStatus = rs.get(gcfx.matchStatus),
    developMethod = rs.get(gcfx.developMethod),
    fingerCprNosqlId = rs.get(gcfx.fingerCprNosqlId),
    fingerImgNosqlId = rs.get(gcfx.fingerImgNosqlId),
    seq = rs.get(gcfx.seq)
  )

  val gcfx = GafisCaseFingerXingzong.syntax("gcfx")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(seqNo: Option[String], fingerId: String, caseId: Option[String], isCorpse: Option[String], corpseNo: Option[String], remainPlace: Option[String], fgpGroup: Option[Short], fgp: Option[String], ridgeColor: Option[String], pattern: Option[String], mittensBegNo: Option[String], mittensEndNo: Option[String], thanStatus: Option[String], isAssist: Option[String], fingerImg: Option[Blob], fingerCpr: Option[Blob], ltCount: Option[Long], llCount: Option[Long], ltCountModMnt: Option[Long], llCountModMnt: Option[Long], editCount: Option[Long], ltDate: Option[DateTime], llDate: Option[DateTime], ltOperator: Option[String], llOperator: Option[String], ltStatus: Option[String], llStatus: Option[String], creatorUnitCode: Option[String], updatorUnitCode: Option[String], modifiedtime: Option[DateTime], modifiedpsn: Option[String], inputtime: Option[DateTime], inputpsn: Option[String], deletag: Option[String], remark: Option[String], sid: Option[Long], matchStatus: Option[String], developMethod: Option[String], fingerCprNosqlId: Option[String], fingerImgNosqlId: Option[String], seq: Option[Long])(implicit session: DBSession = autoSession): Option[GafisCaseFingerXingzong] = {
    withSQL {
      select.from(GafisCaseFingerXingzong as gcfx).where.eq(gcfx.seqNo, seqNo).and.eq(gcfx.fingerId, fingerId).and.eq(gcfx.caseId, caseId).and.eq(gcfx.isCorpse, isCorpse).and.eq(gcfx.corpseNo, corpseNo).and.eq(gcfx.remainPlace, remainPlace).and.eq(gcfx.fgpGroup, fgpGroup).and.eq(gcfx.fgp, fgp).and.eq(gcfx.ridgeColor, ridgeColor).and.eq(gcfx.pattern, pattern).and.eq(gcfx.mittensBegNo, mittensBegNo).and.eq(gcfx.mittensEndNo, mittensEndNo).and.eq(gcfx.thanStatus, thanStatus).and.eq(gcfx.isAssist, isAssist).and.eq(gcfx.fingerImg, fingerImg).and.eq(gcfx.fingerCpr, fingerCpr).and.eq(gcfx.ltCount, ltCount).and.eq(gcfx.llCount, llCount).and.eq(gcfx.ltCountModMnt, ltCountModMnt).and.eq(gcfx.llCountModMnt, llCountModMnt).and.eq(gcfx.editCount, editCount).and.eq(gcfx.ltDate, ltDate).and.eq(gcfx.llDate, llDate).and.eq(gcfx.ltOperator, ltOperator).and.eq(gcfx.llOperator, llOperator).and.eq(gcfx.ltStatus, ltStatus).and.eq(gcfx.llStatus, llStatus).and.eq(gcfx.creatorUnitCode, creatorUnitCode).and.eq(gcfx.updatorUnitCode, updatorUnitCode).and.eq(gcfx.modifiedtime, modifiedtime).and.eq(gcfx.modifiedpsn, modifiedpsn).and.eq(gcfx.inputtime, inputtime).and.eq(gcfx.inputpsn, inputpsn).and.eq(gcfx.deletag, deletag).and.eq(gcfx.remark, remark).and.eq(gcfx.sid, sid).and.eq(gcfx.matchStatus, matchStatus).and.eq(gcfx.developMethod, developMethod).and.eq(gcfx.fingerCprNosqlId, fingerCprNosqlId).and.eq(gcfx.fingerImgNosqlId, fingerImgNosqlId).and.eq(gcfx.seq, seq)
    }.map(GafisCaseFingerXingzong(gcfx.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisCaseFingerXingzong] = {
    withSQL(select.from(GafisCaseFingerXingzong as gcfx)).map(GafisCaseFingerXingzong(gcfx.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisCaseFingerXingzong as gcfx)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisCaseFingerXingzong] = {
    withSQL {
      select.from(GafisCaseFingerXingzong as gcfx).where.append(where)
    }.map(GafisCaseFingerXingzong(gcfx.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisCaseFingerXingzong] = {
    withSQL {
      select.from(GafisCaseFingerXingzong as gcfx).where.append(where)
    }.map(GafisCaseFingerXingzong(gcfx.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisCaseFingerXingzong as gcfx).where.append(where)
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
    seq: Option[Long] = None)(implicit session: DBSession = autoSession): GafisCaseFingerXingzong = {
    withSQL {
      insert.into(GafisCaseFingerXingzong).columns(
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

    new GafisCaseFingerXingzong(
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

  def save(entity: GafisCaseFingerXingzong)(implicit session: DBSession = autoSession): GafisCaseFingerXingzong = {
    withSQL {
      update(GafisCaseFingerXingzong).set(
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
      ).where.eq(column.seqNo, entity.seqNo).and.eq(column.fingerId, entity.fingerId).and.eq(column.caseId, entity.caseId).and.eq(column.isCorpse, entity.isCorpse).and.eq(column.corpseNo, entity.corpseNo).and.eq(column.remainPlace, entity.remainPlace).and.eq(column.fgpGroup, entity.fgpGroup).and.eq(column.fgp, entity.fgp).and.eq(column.ridgeColor, entity.ridgeColor).and.eq(column.pattern, entity.pattern).and.eq(column.mittensBegNo, entity.mittensBegNo).and.eq(column.mittensEndNo, entity.mittensEndNo).and.eq(column.thanStatus, entity.thanStatus).and.eq(column.isAssist, entity.isAssist).and.eq(column.fingerImg, entity.fingerImg).and.eq(column.fingerCpr, entity.fingerCpr).and.eq(column.ltCount, entity.ltCount).and.eq(column.llCount, entity.llCount).and.eq(column.ltCountModMnt, entity.ltCountModMnt).and.eq(column.llCountModMnt, entity.llCountModMnt).and.eq(column.editCount, entity.editCount).and.eq(column.ltDate, entity.ltDate).and.eq(column.llDate, entity.llDate).and.eq(column.ltOperator, entity.ltOperator).and.eq(column.llOperator, entity.llOperator).and.eq(column.ltStatus, entity.ltStatus).and.eq(column.llStatus, entity.llStatus).and.eq(column.creatorUnitCode, entity.creatorUnitCode).and.eq(column.updatorUnitCode, entity.updatorUnitCode).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.deletag, entity.deletag).and.eq(column.remark, entity.remark).and.eq(column.sid, entity.sid).and.eq(column.matchStatus, entity.matchStatus).and.eq(column.developMethod, entity.developMethod).and.eq(column.fingerCprNosqlId, entity.fingerCprNosqlId).and.eq(column.fingerImgNosqlId, entity.fingerImgNosqlId).and.eq(column.seq, entity.seq)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisCaseFingerXingzong)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisCaseFingerXingzong).where.eq(column.seqNo, entity.seqNo).and.eq(column.fingerId, entity.fingerId).and.eq(column.caseId, entity.caseId).and.eq(column.isCorpse, entity.isCorpse).and.eq(column.corpseNo, entity.corpseNo).and.eq(column.remainPlace, entity.remainPlace).and.eq(column.fgpGroup, entity.fgpGroup).and.eq(column.fgp, entity.fgp).and.eq(column.ridgeColor, entity.ridgeColor).and.eq(column.pattern, entity.pattern).and.eq(column.mittensBegNo, entity.mittensBegNo).and.eq(column.mittensEndNo, entity.mittensEndNo).and.eq(column.thanStatus, entity.thanStatus).and.eq(column.isAssist, entity.isAssist).and.eq(column.fingerImg, entity.fingerImg).and.eq(column.fingerCpr, entity.fingerCpr).and.eq(column.ltCount, entity.ltCount).and.eq(column.llCount, entity.llCount).and.eq(column.ltCountModMnt, entity.ltCountModMnt).and.eq(column.llCountModMnt, entity.llCountModMnt).and.eq(column.editCount, entity.editCount).and.eq(column.ltDate, entity.ltDate).and.eq(column.llDate, entity.llDate).and.eq(column.ltOperator, entity.ltOperator).and.eq(column.llOperator, entity.llOperator).and.eq(column.ltStatus, entity.ltStatus).and.eq(column.llStatus, entity.llStatus).and.eq(column.creatorUnitCode, entity.creatorUnitCode).and.eq(column.updatorUnitCode, entity.updatorUnitCode).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.deletag, entity.deletag).and.eq(column.remark, entity.remark).and.eq(column.sid, entity.sid).and.eq(column.matchStatus, entity.matchStatus).and.eq(column.developMethod, entity.developMethod).and.eq(column.fingerCprNosqlId, entity.fingerCprNosqlId).and.eq(column.fingerImgNosqlId, entity.fingerImgNosqlId).and.eq(column.seq, entity.seq) }.update.apply()
  }

}

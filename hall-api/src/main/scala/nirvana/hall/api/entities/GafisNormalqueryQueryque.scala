package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}
import java.sql.{Blob, Clob}

case class GafisNormalqueryQueryque(
  pkId: String,
  oraSid: Option[Long] = None,
  keyid: Option[String] = None,
  querytype: Option[Short] = None,
  status: Option[Short] = None,
  priority: Option[Short] = None,
  hitpossibility: Option[Short] = None,
  verifyresult: Option[Short] = None,
  flag: Option[Short] = None,
  flagc: Option[Short] = None,
  flagd: Option[Short] = None,
  flage: Option[Short] = None,
  flagg: Option[Short] = None,
  qrycondition: Option[Blob] = None,
  mic: Option[Blob] = None,
  textsql: Option[Clob] = None,
  candhead: Option[Blob] = None,
  candlist: Option[Blob] = None,
  timeused: Option[Int] = None,
  maxcandnum: Option[Int] = None,
  curcandnum: Option[Int] = None,
  minscore: Option[Int] = None,
  startkey1: Option[String] = None,
  endkey1: Option[String] = None,
  startkey2: Option[String] = None,
  endkey2: Option[String] = None,
  username: Option[String] = None,
  userunitcode: Option[String] = None,
  finishtime: Option[DateTime] = None,
  computerip: Option[String] = None,
  computername: Option[String] = None,
  oracomment: Option[Clob] = None,
  maxscore: Option[Long] = None,
  createtime: Option[DateTime] = None,
  begintime: Option[DateTime] = None,
  deletag: Option[String] = None,
  userid: Option[String] = None,
  submittsystem: Option[String] = None,
  prioritynew: Option[Short] = None,
  handleresult: Option[Short] = None,
  qryconditionNosqlId: Option[String] = None,
  micNosqlId: Option[String] = None,
  candheadNosqlId: Option[String] = None,
  candlistNosqlId: Option[String] = None,
  timeElapsed: Option[Long] = None,
  recordNumMatched: Option[Long] = None,
  matchProgress: Option[Short] = None,
  messagetag: Option[Short] = None,
  overtimetag: Option[Short] = None) {

  def save()(implicit session: DBSession = GafisNormalqueryQueryque.autoSession): GafisNormalqueryQueryque = GafisNormalqueryQueryque.save(this)(session)

  def destroy()(implicit session: DBSession = GafisNormalqueryQueryque.autoSession): Unit = GafisNormalqueryQueryque.destroy(this)(session)

}


object GafisNormalqueryQueryque extends SQLSyntaxSupport[GafisNormalqueryQueryque] {

  override val tableName = "GAFIS_NORMALQUERY_QUERYQUE"

  override val columns = Seq("PK_ID", "ORA_SID", "KEYID", "QUERYTYPE", "STATUS", "PRIORITY", "HITPOSSIBILITY", "VERIFYRESULT", "FLAG", "FLAGC", "FLAGD", "FLAGE", "FLAGG", "QRYCONDITION", "MIC", "TEXTSQL", "CANDHEAD", "CANDLIST", "TIMEUSED", "MAXCANDNUM", "CURCANDNUM", "MINSCORE", "STARTKEY1", "ENDKEY1", "STARTKEY2", "ENDKEY2", "USERNAME", "USERUNITCODE", "FINISHTIME", "COMPUTERIP", "COMPUTERNAME", "ORACOMMENT", "MAXSCORE", "CREATETIME", "BEGINTIME", "DELETAG", "USERID", "SUBMITTSYSTEM", "PRIORITYNEW", "HANDLERESULT", "QRYCONDITION_NOSQL_ID", "MIC_NOSQL_ID", "CANDHEAD_NOSQL_ID", "CANDLIST_NOSQL_ID", "TIME_ELAPSED", "RECORD_NUM_MATCHED", "MATCH_PROGRESS", "MESSAGETAG", "OVERTIMETAG")

  def apply(gnq: SyntaxProvider[GafisNormalqueryQueryque])(rs: WrappedResultSet): GafisNormalqueryQueryque = apply(gnq.resultName)(rs)
  def apply(gnq: ResultName[GafisNormalqueryQueryque])(rs: WrappedResultSet): GafisNormalqueryQueryque = new GafisNormalqueryQueryque(
    pkId = rs.get(gnq.pkId),
    oraSid = rs.get(gnq.oraSid),
    keyid = rs.get(gnq.keyid),
    querytype = rs.get(gnq.querytype),
    status = rs.get(gnq.status),
    priority = rs.get(gnq.priority),
    hitpossibility = rs.get(gnq.hitpossibility),
    verifyresult = rs.get(gnq.verifyresult),
    flag = rs.get(gnq.flag),
    flagc = rs.get(gnq.flagc),
    flagd = rs.get(gnq.flagd),
    flage = rs.get(gnq.flage),
    flagg = rs.get(gnq.flagg),
    qrycondition = rs.get(gnq.qrycondition),
    mic = rs.get(gnq.mic),
    textsql = rs.get(gnq.textsql),
    candhead = rs.get(gnq.candhead),
    candlist = rs.get(gnq.candlist),
    timeused = rs.get(gnq.timeused),
    maxcandnum = rs.get(gnq.maxcandnum),
    curcandnum = rs.get(gnq.curcandnum),
    minscore = rs.get(gnq.minscore),
    startkey1 = rs.get(gnq.startkey1),
    endkey1 = rs.get(gnq.endkey1),
    startkey2 = rs.get(gnq.startkey2),
    endkey2 = rs.get(gnq.endkey2),
    username = rs.get(gnq.username),
    userunitcode = rs.get(gnq.userunitcode),
    finishtime = rs.get(gnq.finishtime),
    computerip = rs.get(gnq.computerip),
    computername = rs.get(gnq.computername),
    oracomment = rs.get(gnq.oracomment),
    maxscore = rs.get(gnq.maxscore),
    createtime = rs.get(gnq.createtime),
    begintime = rs.get(gnq.begintime),
    deletag = rs.get(gnq.deletag),
    userid = rs.get(gnq.userid),
    submittsystem = rs.get(gnq.submittsystem),
    prioritynew = rs.get(gnq.prioritynew),
    handleresult = rs.get(gnq.handleresult),
    qryconditionNosqlId = rs.get(gnq.qryconditionNosqlId),
    micNosqlId = rs.get(gnq.micNosqlId),
    candheadNosqlId = rs.get(gnq.candheadNosqlId),
    candlistNosqlId = rs.get(gnq.candlistNosqlId),
    timeElapsed = rs.get(gnq.timeElapsed),
    recordNumMatched = rs.get(gnq.recordNumMatched),
    matchProgress = rs.get(gnq.matchProgress),
    messagetag = rs.get(gnq.messagetag),
    overtimetag = rs.get(gnq.overtimetag)
  )

  val gnq = GafisNormalqueryQueryque.syntax("gnq")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String)(implicit session: DBSession = autoSession): Option[GafisNormalqueryQueryque] = {
    withSQL {
      select.from(GafisNormalqueryQueryque as gnq).where.eq(gnq.pkId, pkId)
    }.map(GafisNormalqueryQueryque(gnq.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisNormalqueryQueryque] = {
    withSQL(select.from(GafisNormalqueryQueryque as gnq)).map(GafisNormalqueryQueryque(gnq.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisNormalqueryQueryque as gnq)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisNormalqueryQueryque] = {
    withSQL {
      select.from(GafisNormalqueryQueryque as gnq).where.append(where)
    }.map(GafisNormalqueryQueryque(gnq.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisNormalqueryQueryque] = {
    withSQL {
      select.from(GafisNormalqueryQueryque as gnq).where.append(where)
    }.map(GafisNormalqueryQueryque(gnq.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisNormalqueryQueryque as gnq).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    oraSid: Option[Long] = None,
    keyid: Option[String] = None,
    querytype: Option[Short] = None,
    status: Option[Short] = None,
    priority: Option[Short] = None,
    hitpossibility: Option[Short] = None,
    verifyresult: Option[Short] = None,
    flag: Option[Short] = None,
    flagc: Option[Short] = None,
    flagd: Option[Short] = None,
    flage: Option[Short] = None,
    flagg: Option[Short] = None,
    qrycondition: Option[Blob] = None,
    mic: Option[Blob] = None,
    textsql: Option[Clob] = None,
    candhead: Option[Blob] = None,
    candlist: Option[Blob] = None,
    timeused: Option[Int] = None,
    maxcandnum: Option[Int] = None,
    curcandnum: Option[Int] = None,
    minscore: Option[Int] = None,
    startkey1: Option[String] = None,
    endkey1: Option[String] = None,
    startkey2: Option[String] = None,
    endkey2: Option[String] = None,
    username: Option[String] = None,
    userunitcode: Option[String] = None,
    finishtime: Option[DateTime] = None,
    computerip: Option[String] = None,
    computername: Option[String] = None,
    oracomment: Option[Clob] = None,
    maxscore: Option[Long] = None,
    createtime: Option[DateTime] = None,
    begintime: Option[DateTime] = None,
    deletag: Option[String] = None,
    userid: Option[String] = None,
    submittsystem: Option[String] = None,
    prioritynew: Option[Short] = None,
    handleresult: Option[Short] = None,
    qryconditionNosqlId: Option[String] = None,
    micNosqlId: Option[String] = None,
    candheadNosqlId: Option[String] = None,
    candlistNosqlId: Option[String] = None,
    timeElapsed: Option[Long] = None,
    recordNumMatched: Option[Long] = None,
    matchProgress: Option[Short] = None,
    messagetag: Option[Short] = None,
    overtimetag: Option[Short] = None)(implicit session: DBSession = autoSession): GafisNormalqueryQueryque = {
    withSQL {
      insert.into(GafisNormalqueryQueryque).columns(
        column.pkId,
        column.oraSid,
        column.keyid,
        column.querytype,
        column.status,
        column.priority,
        column.hitpossibility,
        column.verifyresult,
        column.flag,
        column.flagc,
        column.flagd,
        column.flage,
        column.flagg,
        column.qrycondition,
        column.mic,
        column.textsql,
        column.candhead,
        column.candlist,
        column.timeused,
        column.maxcandnum,
        column.curcandnum,
        column.minscore,
        column.startkey1,
        column.endkey1,
        column.startkey2,
        column.endkey2,
        column.username,
        column.userunitcode,
        column.finishtime,
        column.computerip,
        column.computername,
        column.oracomment,
        column.maxscore,
        column.createtime,
        column.begintime,
        column.deletag,
        column.userid,
        column.submittsystem,
        column.prioritynew,
        column.handleresult,
        column.qryconditionNosqlId,
        column.micNosqlId,
        column.candheadNosqlId,
        column.candlistNosqlId,
        column.timeElapsed,
        column.recordNumMatched,
        column.matchProgress,
        column.messagetag,
        column.overtimetag
      ).values(
        pkId,
        oraSid,
        keyid,
        querytype,
        status,
        priority,
        hitpossibility,
        verifyresult,
        flag,
        flagc,
        flagd,
        flage,
        flagg,
        qrycondition,
        mic,
        textsql,
        candhead,
        candlist,
        timeused,
        maxcandnum,
        curcandnum,
        minscore,
        startkey1,
        endkey1,
        startkey2,
        endkey2,
        username,
        userunitcode,
        finishtime,
        computerip,
        computername,
        oracomment,
        maxscore,
        createtime,
        begintime,
        deletag,
        userid,
        submittsystem,
        prioritynew,
        handleresult,
        qryconditionNosqlId,
        micNosqlId,
        candheadNosqlId,
        candlistNosqlId,
        timeElapsed,
        recordNumMatched,
        matchProgress,
        messagetag,
        overtimetag
      )
    }.update.apply()

    new GafisNormalqueryQueryque(
      pkId = pkId,
      oraSid = oraSid,
      keyid = keyid,
      querytype = querytype,
      status = status,
      priority = priority,
      hitpossibility = hitpossibility,
      verifyresult = verifyresult,
      flag = flag,
      flagc = flagc,
      flagd = flagd,
      flage = flage,
      flagg = flagg,
      qrycondition = qrycondition,
      mic = mic,
      textsql = textsql,
      candhead = candhead,
      candlist = candlist,
      timeused = timeused,
      maxcandnum = maxcandnum,
      curcandnum = curcandnum,
      minscore = minscore,
      startkey1 = startkey1,
      endkey1 = endkey1,
      startkey2 = startkey2,
      endkey2 = endkey2,
      username = username,
      userunitcode = userunitcode,
      finishtime = finishtime,
      computerip = computerip,
      computername = computername,
      oracomment = oracomment,
      maxscore = maxscore,
      createtime = createtime,
      begintime = begintime,
      deletag = deletag,
      userid = userid,
      submittsystem = submittsystem,
      prioritynew = prioritynew,
      handleresult = handleresult,
      qryconditionNosqlId = qryconditionNosqlId,
      micNosqlId = micNosqlId,
      candheadNosqlId = candheadNosqlId,
      candlistNosqlId = candlistNosqlId,
      timeElapsed = timeElapsed,
      recordNumMatched = recordNumMatched,
      matchProgress = matchProgress,
      messagetag = messagetag,
      overtimetag = overtimetag)
  }

  def save(entity: GafisNormalqueryQueryque)(implicit session: DBSession = autoSession): GafisNormalqueryQueryque = {
    withSQL {
      update(GafisNormalqueryQueryque).set(
        column.pkId -> entity.pkId,
        column.oraSid -> entity.oraSid,
        column.keyid -> entity.keyid,
        column.querytype -> entity.querytype,
        column.status -> entity.status,
        column.priority -> entity.priority,
        column.hitpossibility -> entity.hitpossibility,
        column.verifyresult -> entity.verifyresult,
        column.flag -> entity.flag,
        column.flagc -> entity.flagc,
        column.flagd -> entity.flagd,
        column.flage -> entity.flage,
        column.flagg -> entity.flagg,
        column.qrycondition -> entity.qrycondition,
        column.mic -> entity.mic,
        column.textsql -> entity.textsql,
        column.candhead -> entity.candhead,
        column.candlist -> entity.candlist,
        column.timeused -> entity.timeused,
        column.maxcandnum -> entity.maxcandnum,
        column.curcandnum -> entity.curcandnum,
        column.minscore -> entity.minscore,
        column.startkey1 -> entity.startkey1,
        column.endkey1 -> entity.endkey1,
        column.startkey2 -> entity.startkey2,
        column.endkey2 -> entity.endkey2,
        column.username -> entity.username,
        column.userunitcode -> entity.userunitcode,
        column.finishtime -> entity.finishtime,
        column.computerip -> entity.computerip,
        column.computername -> entity.computername,
        column.oracomment -> entity.oracomment,
        column.maxscore -> entity.maxscore,
        column.createtime -> entity.createtime,
        column.begintime -> entity.begintime,
        column.deletag -> entity.deletag,
        column.userid -> entity.userid,
        column.submittsystem -> entity.submittsystem,
        column.prioritynew -> entity.prioritynew,
        column.handleresult -> entity.handleresult,
        column.qryconditionNosqlId -> entity.qryconditionNosqlId,
        column.micNosqlId -> entity.micNosqlId,
        column.candheadNosqlId -> entity.candheadNosqlId,
        column.candlistNosqlId -> entity.candlistNosqlId,
        column.timeElapsed -> entity.timeElapsed,
        column.recordNumMatched -> entity.recordNumMatched,
        column.matchProgress -> entity.matchProgress,
        column.messagetag -> entity.messagetag,
        column.overtimetag -> entity.overtimetag
      ).where.eq(column.pkId, entity.pkId)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisNormalqueryQueryque)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisNormalqueryQueryque).where.eq(column.pkId, entity.pkId) }.update.apply()
  }

}

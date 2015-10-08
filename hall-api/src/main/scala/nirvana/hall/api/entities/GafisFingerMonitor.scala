package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisFingerMonitor(
  pkId: String,
  personId: String,
  storageSta: Option[Long] = None,
  storageMeg: Option[String] = None,
  sendttSta: Option[Long] = None,
  sendttMeg: Option[String] = None,
  ttSta: Option[Long] = None,
  ttMeg: Option[String] = None,
  sendtlSta: Option[Long] = None,
  sendtlMeg: Option[String] = None,
  tlSta: Option[Long] = None,
  tlMeg: Option[String] = None,
  ttHandle: Option[Long] = None,
  tlHandle: Option[Long] = None,
  thanNum: Option[Long] = None,
  thanTime: Option[DateTime] = None,
  isUpload: Option[Long] = None,
  inputtime: Option[DateTime] = None,
  modifiedtime: Option[DateTime] = None) {

  def save()(implicit session: DBSession = GafisFingerMonitor.autoSession): GafisFingerMonitor = GafisFingerMonitor.save(this)(session)

  def destroy()(implicit session: DBSession = GafisFingerMonitor.autoSession): Unit = GafisFingerMonitor.destroy(this)(session)

}


object GafisFingerMonitor extends SQLSyntaxSupport[GafisFingerMonitor] {

  override val tableName = "GAFIS_FINGER_MONITOR"

  override val columns = Seq("PK_ID", "PERSON_ID", "STORAGE_STA", "STORAGE_MEG", "SENDTT_STA", "SENDTT_MEG", "TT_STA", "TT_MEG", "SENDTL_STA", "SENDTL_MEG", "TL_STA", "TL_MEG", "TT_HANDLE", "TL_HANDLE", "THAN_NUM", "THAN_TIME", "IS_UPLOAD", "INPUTTIME", "MODIFIEDTIME")

  def apply(gfm: SyntaxProvider[GafisFingerMonitor])(rs: WrappedResultSet): GafisFingerMonitor = apply(gfm.resultName)(rs)
  def apply(gfm: ResultName[GafisFingerMonitor])(rs: WrappedResultSet): GafisFingerMonitor = new GafisFingerMonitor(
    pkId = rs.get(gfm.pkId),
    personId = rs.get(gfm.personId),
    storageSta = rs.get(gfm.storageSta),
    storageMeg = rs.get(gfm.storageMeg),
    sendttSta = rs.get(gfm.sendttSta),
    sendttMeg = rs.get(gfm.sendttMeg),
    ttSta = rs.get(gfm.ttSta),
    ttMeg = rs.get(gfm.ttMeg),
    sendtlSta = rs.get(gfm.sendtlSta),
    sendtlMeg = rs.get(gfm.sendtlMeg),
    tlSta = rs.get(gfm.tlSta),
    tlMeg = rs.get(gfm.tlMeg),
    ttHandle = rs.get(gfm.ttHandle),
    tlHandle = rs.get(gfm.tlHandle),
    thanNum = rs.get(gfm.thanNum),
    thanTime = rs.get(gfm.thanTime),
    isUpload = rs.get(gfm.isUpload),
    inputtime = rs.get(gfm.inputtime),
    modifiedtime = rs.get(gfm.modifiedtime)
  )

  val gfm = GafisFingerMonitor.syntax("gfm")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personId: String, storageSta: Option[Long], storageMeg: Option[String], sendttSta: Option[Long], sendttMeg: Option[String], ttSta: Option[Long], ttMeg: Option[String], sendtlSta: Option[Long], sendtlMeg: Option[String], tlSta: Option[Long], tlMeg: Option[String], ttHandle: Option[Long], tlHandle: Option[Long], thanNum: Option[Long], thanTime: Option[DateTime], isUpload: Option[Long], inputtime: Option[DateTime], modifiedtime: Option[DateTime])(implicit session: DBSession = autoSession): Option[GafisFingerMonitor] = {
    withSQL {
      select.from(GafisFingerMonitor as gfm).where.eq(gfm.pkId, pkId).and.eq(gfm.personId, personId).and.eq(gfm.storageSta, storageSta).and.eq(gfm.storageMeg, storageMeg).and.eq(gfm.sendttSta, sendttSta).and.eq(gfm.sendttMeg, sendttMeg).and.eq(gfm.ttSta, ttSta).and.eq(gfm.ttMeg, ttMeg).and.eq(gfm.sendtlSta, sendtlSta).and.eq(gfm.sendtlMeg, sendtlMeg).and.eq(gfm.tlSta, tlSta).and.eq(gfm.tlMeg, tlMeg).and.eq(gfm.ttHandle, ttHandle).and.eq(gfm.tlHandle, tlHandle).and.eq(gfm.thanNum, thanNum).and.eq(gfm.thanTime, thanTime).and.eq(gfm.isUpload, isUpload).and.eq(gfm.inputtime, inputtime).and.eq(gfm.modifiedtime, modifiedtime)
    }.map(GafisFingerMonitor(gfm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisFingerMonitor] = {
    withSQL(select.from(GafisFingerMonitor as gfm)).map(GafisFingerMonitor(gfm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisFingerMonitor as gfm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisFingerMonitor] = {
    withSQL {
      select.from(GafisFingerMonitor as gfm).where.append(where)
    }.map(GafisFingerMonitor(gfm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisFingerMonitor] = {
    withSQL {
      select.from(GafisFingerMonitor as gfm).where.append(where)
    }.map(GafisFingerMonitor(gfm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisFingerMonitor as gfm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personId: String,
    storageSta: Option[Long] = None,
    storageMeg: Option[String] = None,
    sendttSta: Option[Long] = None,
    sendttMeg: Option[String] = None,
    ttSta: Option[Long] = None,
    ttMeg: Option[String] = None,
    sendtlSta: Option[Long] = None,
    sendtlMeg: Option[String] = None,
    tlSta: Option[Long] = None,
    tlMeg: Option[String] = None,
    ttHandle: Option[Long] = None,
    tlHandle: Option[Long] = None,
    thanNum: Option[Long] = None,
    thanTime: Option[DateTime] = None,
    isUpload: Option[Long] = None,
    inputtime: Option[DateTime] = None,
    modifiedtime: Option[DateTime] = None)(implicit session: DBSession = autoSession): GafisFingerMonitor = {
    withSQL {
      insert.into(GafisFingerMonitor).columns(
        column.pkId,
        column.personId,
        column.storageSta,
        column.storageMeg,
        column.sendttSta,
        column.sendttMeg,
        column.ttSta,
        column.ttMeg,
        column.sendtlSta,
        column.sendtlMeg,
        column.tlSta,
        column.tlMeg,
        column.ttHandle,
        column.tlHandle,
        column.thanNum,
        column.thanTime,
        column.isUpload,
        column.inputtime,
        column.modifiedtime
      ).values(
        pkId,
        personId,
        storageSta,
        storageMeg,
        sendttSta,
        sendttMeg,
        ttSta,
        ttMeg,
        sendtlSta,
        sendtlMeg,
        tlSta,
        tlMeg,
        ttHandle,
        tlHandle,
        thanNum,
        thanTime,
        isUpload,
        inputtime,
        modifiedtime
      )
    }.update.apply()

    GafisFingerMonitor(
      pkId = pkId,
      personId = personId,
      storageSta = storageSta,
      storageMeg = storageMeg,
      sendttSta = sendttSta,
      sendttMeg = sendttMeg,
      ttSta = ttSta,
      ttMeg = ttMeg,
      sendtlSta = sendtlSta,
      sendtlMeg = sendtlMeg,
      tlSta = tlSta,
      tlMeg = tlMeg,
      ttHandle = ttHandle,
      tlHandle = tlHandle,
      thanNum = thanNum,
      thanTime = thanTime,
      isUpload = isUpload,
      inputtime = inputtime,
      modifiedtime = modifiedtime)
  }

  def save(entity: GafisFingerMonitor)(implicit session: DBSession = autoSession): GafisFingerMonitor = {
    withSQL {
      update(GafisFingerMonitor).set(
        column.pkId -> entity.pkId,
        column.personId -> entity.personId,
        column.storageSta -> entity.storageSta,
        column.storageMeg -> entity.storageMeg,
        column.sendttSta -> entity.sendttSta,
        column.sendttMeg -> entity.sendttMeg,
        column.ttSta -> entity.ttSta,
        column.ttMeg -> entity.ttMeg,
        column.sendtlSta -> entity.sendtlSta,
        column.sendtlMeg -> entity.sendtlMeg,
        column.tlSta -> entity.tlSta,
        column.tlMeg -> entity.tlMeg,
        column.ttHandle -> entity.ttHandle,
        column.tlHandle -> entity.tlHandle,
        column.thanNum -> entity.thanNum,
        column.thanTime -> entity.thanTime,
        column.isUpload -> entity.isUpload,
        column.inputtime -> entity.inputtime,
        column.modifiedtime -> entity.modifiedtime
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.storageSta, entity.storageSta).and.eq(column.storageMeg, entity.storageMeg).and.eq(column.sendttSta, entity.sendttSta).and.eq(column.sendttMeg, entity.sendttMeg).and.eq(column.ttSta, entity.ttSta).and.eq(column.ttMeg, entity.ttMeg).and.eq(column.sendtlSta, entity.sendtlSta).and.eq(column.sendtlMeg, entity.sendtlMeg).and.eq(column.tlSta, entity.tlSta).and.eq(column.tlMeg, entity.tlMeg).and.eq(column.ttHandle, entity.ttHandle).and.eq(column.tlHandle, entity.tlHandle).and.eq(column.thanNum, entity.thanNum).and.eq(column.thanTime, entity.thanTime).and.eq(column.isUpload, entity.isUpload).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedtime, entity.modifiedtime)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisFingerMonitor)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisFingerMonitor).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.storageSta, entity.storageSta).and.eq(column.storageMeg, entity.storageMeg).and.eq(column.sendttSta, entity.sendttSta).and.eq(column.sendttMeg, entity.sendttMeg).and.eq(column.ttSta, entity.ttSta).and.eq(column.ttMeg, entity.ttMeg).and.eq(column.sendtlSta, entity.sendtlSta).and.eq(column.sendtlMeg, entity.sendtlMeg).and.eq(column.tlSta, entity.tlSta).and.eq(column.tlMeg, entity.tlMeg).and.eq(column.ttHandle, entity.ttHandle).and.eq(column.tlHandle, entity.tlHandle).and.eq(column.thanNum, entity.thanNum).and.eq(column.thanTime, entity.thanTime).and.eq(column.isUpload, entity.isUpload).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedtime, entity.modifiedtime) }.update.apply()
  }

}

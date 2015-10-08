package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisFingerMonitorTemp(
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

  def save()(implicit session: DBSession = GafisFingerMonitorTemp.autoSession): GafisFingerMonitorTemp = GafisFingerMonitorTemp.save(this)(session)

  def destroy()(implicit session: DBSession = GafisFingerMonitorTemp.autoSession): Unit = GafisFingerMonitorTemp.destroy(this)(session)

}


object GafisFingerMonitorTemp extends SQLSyntaxSupport[GafisFingerMonitorTemp] {

  override val tableName = "GAFIS_FINGER_MONITOR_TEMP"

  override val columns = Seq("PK_ID", "PERSON_ID", "STORAGE_STA", "STORAGE_MEG", "SENDTT_STA", "SENDTT_MEG", "TT_STA", "TT_MEG", "SENDTL_STA", "SENDTL_MEG", "TL_STA", "TL_MEG", "TT_HANDLE", "TL_HANDLE", "THAN_NUM", "THAN_TIME", "IS_UPLOAD", "INPUTTIME", "MODIFIEDTIME")

  def apply(gfmt: SyntaxProvider[GafisFingerMonitorTemp])(rs: WrappedResultSet): GafisFingerMonitorTemp = apply(gfmt.resultName)(rs)
  def apply(gfmt: ResultName[GafisFingerMonitorTemp])(rs: WrappedResultSet): GafisFingerMonitorTemp = new GafisFingerMonitorTemp(
    pkId = rs.get(gfmt.pkId),
    personId = rs.get(gfmt.personId),
    storageSta = rs.get(gfmt.storageSta),
    storageMeg = rs.get(gfmt.storageMeg),
    sendttSta = rs.get(gfmt.sendttSta),
    sendttMeg = rs.get(gfmt.sendttMeg),
    ttSta = rs.get(gfmt.ttSta),
    ttMeg = rs.get(gfmt.ttMeg),
    sendtlSta = rs.get(gfmt.sendtlSta),
    sendtlMeg = rs.get(gfmt.sendtlMeg),
    tlSta = rs.get(gfmt.tlSta),
    tlMeg = rs.get(gfmt.tlMeg),
    ttHandle = rs.get(gfmt.ttHandle),
    tlHandle = rs.get(gfmt.tlHandle),
    thanNum = rs.get(gfmt.thanNum),
    thanTime = rs.get(gfmt.thanTime),
    isUpload = rs.get(gfmt.isUpload),
    inputtime = rs.get(gfmt.inputtime),
    modifiedtime = rs.get(gfmt.modifiedtime)
  )

  val gfmt = GafisFingerMonitorTemp.syntax("gfmt")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personId: String, storageSta: Option[Long], storageMeg: Option[String], sendttSta: Option[Long], sendttMeg: Option[String], ttSta: Option[Long], ttMeg: Option[String], sendtlSta: Option[Long], sendtlMeg: Option[String], tlSta: Option[Long], tlMeg: Option[String], ttHandle: Option[Long], tlHandle: Option[Long], thanNum: Option[Long], thanTime: Option[DateTime], isUpload: Option[Long], inputtime: Option[DateTime], modifiedtime: Option[DateTime])(implicit session: DBSession = autoSession): Option[GafisFingerMonitorTemp] = {
    withSQL {
      select.from(GafisFingerMonitorTemp as gfmt).where.eq(gfmt.pkId, pkId).and.eq(gfmt.personId, personId).and.eq(gfmt.storageSta, storageSta).and.eq(gfmt.storageMeg, storageMeg).and.eq(gfmt.sendttSta, sendttSta).and.eq(gfmt.sendttMeg, sendttMeg).and.eq(gfmt.ttSta, ttSta).and.eq(gfmt.ttMeg, ttMeg).and.eq(gfmt.sendtlSta, sendtlSta).and.eq(gfmt.sendtlMeg, sendtlMeg).and.eq(gfmt.tlSta, tlSta).and.eq(gfmt.tlMeg, tlMeg).and.eq(gfmt.ttHandle, ttHandle).and.eq(gfmt.tlHandle, tlHandle).and.eq(gfmt.thanNum, thanNum).and.eq(gfmt.thanTime, thanTime).and.eq(gfmt.isUpload, isUpload).and.eq(gfmt.inputtime, inputtime).and.eq(gfmt.modifiedtime, modifiedtime)
    }.map(GafisFingerMonitorTemp(gfmt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisFingerMonitorTemp] = {
    withSQL(select.from(GafisFingerMonitorTemp as gfmt)).map(GafisFingerMonitorTemp(gfmt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisFingerMonitorTemp as gfmt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisFingerMonitorTemp] = {
    withSQL {
      select.from(GafisFingerMonitorTemp as gfmt).where.append(where)
    }.map(GafisFingerMonitorTemp(gfmt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisFingerMonitorTemp] = {
    withSQL {
      select.from(GafisFingerMonitorTemp as gfmt).where.append(where)
    }.map(GafisFingerMonitorTemp(gfmt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisFingerMonitorTemp as gfmt).where.append(where)
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
    modifiedtime: Option[DateTime] = None)(implicit session: DBSession = autoSession): GafisFingerMonitorTemp = {
    withSQL {
      insert.into(GafisFingerMonitorTemp).columns(
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

    GafisFingerMonitorTemp(
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

  def save(entity: GafisFingerMonitorTemp)(implicit session: DBSession = autoSession): GafisFingerMonitorTemp = {
    withSQL {
      update(GafisFingerMonitorTemp).set(
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

  def destroy(entity: GafisFingerMonitorTemp)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisFingerMonitorTemp).where.eq(column.pkId, entity.pkId).and.eq(column.personId, entity.personId).and.eq(column.storageSta, entity.storageSta).and.eq(column.storageMeg, entity.storageMeg).and.eq(column.sendttSta, entity.sendttSta).and.eq(column.sendttMeg, entity.sendttMeg).and.eq(column.ttSta, entity.ttSta).and.eq(column.ttMeg, entity.ttMeg).and.eq(column.sendtlSta, entity.sendtlSta).and.eq(column.sendtlMeg, entity.sendtlMeg).and.eq(column.tlSta, entity.tlSta).and.eq(column.tlMeg, entity.tlMeg).and.eq(column.ttHandle, entity.ttHandle).and.eq(column.tlHandle, entity.tlHandle).and.eq(column.thanNum, entity.thanNum).and.eq(column.thanTime, entity.thanTime).and.eq(column.isUpload, entity.isUpload).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedtime, entity.modifiedtime) }.update.apply()
  }

}

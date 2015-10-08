package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherVirtual(
  pkId: String,
  personid: Option[String] = None,
  virtualTypeCode: Option[String] = None,
  virtualNumber: Option[String] = None,
  nickname: Option[String] = None,
  inputpsn: Option[String] = None,
  inputtime: Option[DateTime] = None,
  modifiedpsn: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  deletag: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherVirtual.autoSession): GafisGatherVirtual = GafisGatherVirtual.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherVirtual.autoSession): Unit = GafisGatherVirtual.destroy(this)(session)

}


object GafisGatherVirtual extends SQLSyntaxSupport[GafisGatherVirtual] {

  override val tableName = "GAFIS_GATHER_VIRTUAL"

  override val columns = Seq("PK_ID", "PERSONID", "VIRTUAL_TYPE_CODE", "VIRTUAL_NUMBER", "NICKNAME", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "DELETAG")

  def apply(ggv: SyntaxProvider[GafisGatherVirtual])(rs: WrappedResultSet): GafisGatherVirtual = apply(ggv.resultName)(rs)
  def apply(ggv: ResultName[GafisGatherVirtual])(rs: WrappedResultSet): GafisGatherVirtual = new GafisGatherVirtual(
    pkId = rs.get(ggv.pkId),
    personid = rs.get(ggv.personid),
    virtualTypeCode = rs.get(ggv.virtualTypeCode),
    virtualNumber = rs.get(ggv.virtualNumber),
    nickname = rs.get(ggv.nickname),
    inputpsn = rs.get(ggv.inputpsn),
    inputtime = rs.get(ggv.inputtime),
    modifiedpsn = rs.get(ggv.modifiedpsn),
    modifiedtime = rs.get(ggv.modifiedtime),
    deletag = rs.get(ggv.deletag)
  )

  val ggv = GafisGatherVirtual.syntax("ggv")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personid: Option[String], virtualTypeCode: Option[String], virtualNumber: Option[String], nickname: Option[String], inputpsn: Option[String], inputtime: Option[DateTime], modifiedpsn: Option[String], modifiedtime: Option[DateTime], deletag: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherVirtual] = {
    withSQL {
      select.from(GafisGatherVirtual as ggv).where.eq(ggv.pkId, pkId).and.eq(ggv.personid, personid).and.eq(ggv.virtualTypeCode, virtualTypeCode).and.eq(ggv.virtualNumber, virtualNumber).and.eq(ggv.nickname, nickname).and.eq(ggv.inputpsn, inputpsn).and.eq(ggv.inputtime, inputtime).and.eq(ggv.modifiedpsn, modifiedpsn).and.eq(ggv.modifiedtime, modifiedtime).and.eq(ggv.deletag, deletag)
    }.map(GafisGatherVirtual(ggv.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherVirtual] = {
    withSQL(select.from(GafisGatherVirtual as ggv)).map(GafisGatherVirtual(ggv.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherVirtual as ggv)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherVirtual] = {
    withSQL {
      select.from(GafisGatherVirtual as ggv).where.append(where)
    }.map(GafisGatherVirtual(ggv.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherVirtual] = {
    withSQL {
      select.from(GafisGatherVirtual as ggv).where.append(where)
    }.map(GafisGatherVirtual(ggv.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherVirtual as ggv).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personid: Option[String] = None,
    virtualTypeCode: Option[String] = None,
    virtualNumber: Option[String] = None,
    nickname: Option[String] = None,
    inputpsn: Option[String] = None,
    inputtime: Option[DateTime] = None,
    modifiedpsn: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    deletag: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherVirtual = {
    withSQL {
      insert.into(GafisGatherVirtual).columns(
        column.pkId,
        column.personid,
        column.virtualTypeCode,
        column.virtualNumber,
        column.nickname,
        column.inputpsn,
        column.inputtime,
        column.modifiedpsn,
        column.modifiedtime,
        column.deletag
      ).values(
        pkId,
        personid,
        virtualTypeCode,
        virtualNumber,
        nickname,
        inputpsn,
        inputtime,
        modifiedpsn,
        modifiedtime,
        deletag
      )
    }.update.apply()

    GafisGatherVirtual(
      pkId = pkId,
      personid = personid,
      virtualTypeCode = virtualTypeCode,
      virtualNumber = virtualNumber,
      nickname = nickname,
      inputpsn = inputpsn,
      inputtime = inputtime,
      modifiedpsn = modifiedpsn,
      modifiedtime = modifiedtime,
      deletag = deletag)
  }

  def save(entity: GafisGatherVirtual)(implicit session: DBSession = autoSession): GafisGatherVirtual = {
    withSQL {
      update(GafisGatherVirtual).set(
        column.pkId -> entity.pkId,
        column.personid -> entity.personid,
        column.virtualTypeCode -> entity.virtualTypeCode,
        column.virtualNumber -> entity.virtualNumber,
        column.nickname -> entity.nickname,
        column.inputpsn -> entity.inputpsn,
        column.inputtime -> entity.inputtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.modifiedtime -> entity.modifiedtime,
        column.deletag -> entity.deletag
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.virtualTypeCode, entity.virtualTypeCode).and.eq(column.virtualNumber, entity.virtualNumber).and.eq(column.nickname, entity.nickname).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherVirtual)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherVirtual).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.virtualTypeCode, entity.virtualTypeCode).and.eq(column.virtualNumber, entity.virtualNumber).and.eq(column.nickname, entity.nickname).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag) }.update.apply()
  }

}

package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class SysDepartEquipment(
  pkId: String,
  depart: String,
  ip: Option[String] = None,
  mac: Option[String] = None,
  inputpsn: String,
  inputtime: Option[DateTime] = None,
  modifiedpsn: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  deletag: Option[String] = None) {

  def save()(implicit session: DBSession = SysDepartEquipment.autoSession): SysDepartEquipment = SysDepartEquipment.save(this)(session)

  def destroy()(implicit session: DBSession = SysDepartEquipment.autoSession): Unit = SysDepartEquipment.destroy(this)(session)

}


object SysDepartEquipment extends SQLSyntaxSupport[SysDepartEquipment] {

  override val tableName = "SYS_DEPART_EQUIPMENT"

  override val columns = Seq("PK_ID", "DEPART", "IP", "MAC", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "DELETAG")

  def apply(sde: SyntaxProvider[SysDepartEquipment])(rs: WrappedResultSet): SysDepartEquipment = apply(sde.resultName)(rs)
  def apply(sde: ResultName[SysDepartEquipment])(rs: WrappedResultSet): SysDepartEquipment = new SysDepartEquipment(
    pkId = rs.get(sde.pkId),
    depart = rs.get(sde.depart),
    ip = rs.get(sde.ip),
    mac = rs.get(sde.mac),
    inputpsn = rs.get(sde.inputpsn),
    inputtime = rs.get(sde.inputtime),
    modifiedpsn = rs.get(sde.modifiedpsn),
    modifiedtime = rs.get(sde.modifiedtime),
    deletag = rs.get(sde.deletag)
  )

  val sde = SysDepartEquipment.syntax("sde")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, depart: String, ip: Option[String], mac: Option[String], inputpsn: String, inputtime: Option[DateTime], modifiedpsn: Option[String], modifiedtime: Option[DateTime], deletag: Option[String])(implicit session: DBSession = autoSession): Option[SysDepartEquipment] = {
    withSQL {
      select.from(SysDepartEquipment as sde).where.eq(sde.pkId, pkId).and.eq(sde.depart, depart).and.eq(sde.ip, ip).and.eq(sde.mac, mac).and.eq(sde.inputpsn, inputpsn).and.eq(sde.inputtime, inputtime).and.eq(sde.modifiedpsn, modifiedpsn).and.eq(sde.modifiedtime, modifiedtime).and.eq(sde.deletag, deletag)
    }.map(SysDepartEquipment(sde.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysDepartEquipment] = {
    withSQL(select.from(SysDepartEquipment as sde)).map(SysDepartEquipment(sde.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysDepartEquipment as sde)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysDepartEquipment] = {
    withSQL {
      select.from(SysDepartEquipment as sde).where.append(where)
    }.map(SysDepartEquipment(sde.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysDepartEquipment] = {
    withSQL {
      select.from(SysDepartEquipment as sde).where.append(where)
    }.map(SysDepartEquipment(sde.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysDepartEquipment as sde).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    depart: String,
    ip: Option[String] = None,
    mac: Option[String] = None,
    inputpsn: String,
    inputtime: Option[DateTime] = None,
    modifiedpsn: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    deletag: Option[String] = None)(implicit session: DBSession = autoSession): SysDepartEquipment = {
    withSQL {
      insert.into(SysDepartEquipment).columns(
        column.pkId,
        column.depart,
        column.ip,
        column.mac,
        column.inputpsn,
        column.inputtime,
        column.modifiedpsn,
        column.modifiedtime,
        column.deletag
      ).values(
        pkId,
        depart,
        ip,
        mac,
        inputpsn,
        inputtime,
        modifiedpsn,
        modifiedtime,
        deletag
      )
    }.update.apply()

    SysDepartEquipment(
      pkId = pkId,
      depart = depart,
      ip = ip,
      mac = mac,
      inputpsn = inputpsn,
      inputtime = inputtime,
      modifiedpsn = modifiedpsn,
      modifiedtime = modifiedtime,
      deletag = deletag)
  }

  def save(entity: SysDepartEquipment)(implicit session: DBSession = autoSession): SysDepartEquipment = {
    withSQL {
      update(SysDepartEquipment).set(
        column.pkId -> entity.pkId,
        column.depart -> entity.depart,
        column.ip -> entity.ip,
        column.mac -> entity.mac,
        column.inputpsn -> entity.inputpsn,
        column.inputtime -> entity.inputtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.modifiedtime -> entity.modifiedtime,
        column.deletag -> entity.deletag
      ).where.eq(column.pkId, entity.pkId).and.eq(column.depart, entity.depart).and.eq(column.ip, entity.ip).and.eq(column.mac, entity.mac).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag)
    }.update.apply()
    entity
  }

  def destroy(entity: SysDepartEquipment)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysDepartEquipment).where.eq(column.pkId, entity.pkId).and.eq(column.depart, entity.depart).and.eq(column.ip, entity.ip).and.eq(column.mac, entity.mac).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag) }.update.apply()
  }

}

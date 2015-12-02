package nirvana.hall.api.entities

import scalikejdbc._

case class SysDepart(
  code: String,
  name: Option[String] = None,
  leader: Option[String] = None,
  remark: Option[String] = None,
  deleteFlag: Option[String] = None,
  isLeaf: Option[String] = None,
  parentId: Option[String] = None,
  deptLevel: Option[String] = None,
  longitude: Option[Int] = None,
  latitude: Option[Int] = None,
  phone: Option[String] = None,
  longName: Option[String] = None,
  isHaveChamber: Option[String] = None,
  chamberType: Option[Short] = None,
  isSpecial: Option[Short] = None,
  integrationType: Option[String] = None) {

  def save()(implicit session: DBSession = SysDepart.autoSession): SysDepart = SysDepart.save(this)(session)

  def destroy()(implicit session: DBSession = SysDepart.autoSession): Unit = SysDepart.destroy(this)(session)

}


object SysDepart extends SQLSyntaxSupport[SysDepart] {

  override val tableName = "SYS_DEPART"

  override val columns = Seq("CODE", "NAME", "LEADER", "REMARK", "DELETE_FLAG", "IS_LEAF", "PARENT_ID", "DEPT_LEVEL", "LONGITUDE", "LATITUDE", "PHONE", "LONG_NAME", "IS_HAVE_CHAMBER", "CHAMBER_TYPE", "IS_SPECIAL", "INTEGRATION_TYPE")

  def apply(sd: SyntaxProvider[SysDepart])(rs: WrappedResultSet): SysDepart = apply(sd.resultName)(rs)
  def apply(sd: ResultName[SysDepart])(rs: WrappedResultSet): SysDepart = new SysDepart(
    code = rs.get(sd.code),
    name = rs.get(sd.name),
    leader = rs.get(sd.leader),
    remark = rs.get(sd.remark),
    deleteFlag = rs.get(sd.deleteFlag),
    isLeaf = rs.get(sd.isLeaf),
    parentId = rs.get(sd.parentId),
    deptLevel = rs.get(sd.deptLevel),
    longitude = rs.get(sd.longitude),
    latitude = rs.get(sd.latitude),
    phone = rs.get(sd.phone),
    longName = rs.get(sd.longName),
    isHaveChamber = rs.get(sd.isHaveChamber),
    chamberType = rs.get(sd.chamberType),
    isSpecial = rs.get(sd.isSpecial),
    integrationType = rs.get(sd.integrationType)
  )

  val sd = SysDepart.syntax("sd")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(code: String)(implicit session: DBSession = autoSession): Option[SysDepart] = {
    withSQL {
      select.from(SysDepart as sd).where.eq(sd.code, code)
    }.map(SysDepart(sd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysDepart] = {
    withSQL(select.from(SysDepart as sd)).map(SysDepart(sd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysDepart as sd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysDepart] = {
    withSQL {
      select.from(SysDepart as sd).where.append(where)
    }.map(SysDepart(sd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysDepart] = {
    withSQL {
      select.from(SysDepart as sd).where.append(where)
    }.map(SysDepart(sd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysDepart as sd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    code: String,
    name: Option[String] = None,
    leader: Option[String] = None,
    remark: Option[String] = None,
    deleteFlag: Option[String] = None,
    isLeaf: Option[String] = None,
    parentId: Option[String] = None,
    deptLevel: Option[String] = None,
    longitude: Option[Int] = None,
    latitude: Option[Int] = None,
    phone: Option[String] = None,
    longName: Option[String] = None,
    isHaveChamber: Option[String] = None,
    chamberType: Option[Short] = None,
    isSpecial: Option[Short] = None,
    integrationType: Option[String] = None)(implicit session: DBSession = autoSession): SysDepart = {
    withSQL {
      insert.into(SysDepart).columns(
        column.code,
        column.name,
        column.leader,
        column.remark,
        column.deleteFlag,
        column.isLeaf,
        column.parentId,
        column.deptLevel,
        column.longitude,
        column.latitude,
        column.phone,
        column.longName,
        column.isHaveChamber,
        column.chamberType,
        column.isSpecial,
        column.integrationType
      ).values(
        code,
        name,
        leader,
        remark,
        deleteFlag,
        isLeaf,
        parentId,
        deptLevel,
        longitude,
        latitude,
        phone,
        longName,
        isHaveChamber,
        chamberType,
        isSpecial,
        integrationType
      )
    }.update.apply()

    SysDepart(
      code = code,
      name = name,
      leader = leader,
      remark = remark,
      deleteFlag = deleteFlag,
      isLeaf = isLeaf,
      parentId = parentId,
      deptLevel = deptLevel,
      longitude = longitude,
      latitude = latitude,
      phone = phone,
      longName = longName,
      isHaveChamber = isHaveChamber,
      chamberType = chamberType,
      isSpecial = isSpecial,
      integrationType = integrationType)
  }

  def save(entity: SysDepart)(implicit session: DBSession = autoSession): SysDepart = {
    withSQL {
      update(SysDepart).set(
        column.code -> entity.code,
        column.name -> entity.name,
        column.leader -> entity.leader,
        column.remark -> entity.remark,
        column.deleteFlag -> entity.deleteFlag,
        column.isLeaf -> entity.isLeaf,
        column.parentId -> entity.parentId,
        column.deptLevel -> entity.deptLevel,
        column.longitude -> entity.longitude,
        column.latitude -> entity.latitude,
        column.phone -> entity.phone,
        column.longName -> entity.longName,
        column.isHaveChamber -> entity.isHaveChamber,
        column.chamberType -> entity.chamberType,
        column.isSpecial -> entity.isSpecial,
        column.integrationType -> entity.integrationType
      ).where.eq(column.code, entity.code)
    }.update.apply()
    entity
  }

  def destroy(entity: SysDepart)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysDepart).where.eq(column.code, entity.code) }.update.apply()
  }

}

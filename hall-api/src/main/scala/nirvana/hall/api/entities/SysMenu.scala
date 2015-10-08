package nirvana.hall.api.entities

import scalikejdbc._

case class SysMenu(
  menuCode: String,
  sysCode: Option[String] = None,
  menuName: Option[String] = None,
  menuAction: Option[String] = None,
  deleteFlag: Option[Long] = None,
  icon: Option[String] = None,
  parentId: Option[String] = None,
  levelNo: Option[Long] = None,
  ord: Option[Long] = None,
  isLeaf: Option[Long] = None,
  sofa: Option[String] = None,
  bench: Option[String] = None,
  winPanel: Option[String] = None) {

  def save()(implicit session: DBSession = SysMenu.autoSession): SysMenu = SysMenu.save(this)(session)

  def destroy()(implicit session: DBSession = SysMenu.autoSession): Unit = SysMenu.destroy(this)(session)

}


object SysMenu extends SQLSyntaxSupport[SysMenu] {

  override val tableName = "SYS_MENU"

  override val columns = Seq("MENU_CODE", "SYS_CODE", "MENU_NAME", "MENU_ACTION", "DELETE_FLAG", "ICON", "PARENT_ID", "LEVEL_NO", "ORD", "IS_LEAF", "SOFA", "BENCH", "WIN_PANEL")

  def apply(sm: SyntaxProvider[SysMenu])(rs: WrappedResultSet): SysMenu = apply(sm.resultName)(rs)
  def apply(sm: ResultName[SysMenu])(rs: WrappedResultSet): SysMenu = new SysMenu(
    menuCode = rs.get(sm.menuCode),
    sysCode = rs.get(sm.sysCode),
    menuName = rs.get(sm.menuName),
    menuAction = rs.get(sm.menuAction),
    deleteFlag = rs.get(sm.deleteFlag),
    icon = rs.get(sm.icon),
    parentId = rs.get(sm.parentId),
    levelNo = rs.get(sm.levelNo),
    ord = rs.get(sm.ord),
    isLeaf = rs.get(sm.isLeaf),
    sofa = rs.get(sm.sofa),
    bench = rs.get(sm.bench),
    winPanel = rs.get(sm.winPanel)
  )

  val sm = SysMenu.syntax("sm")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(menuCode: String)(implicit session: DBSession = autoSession): Option[SysMenu] = {
    withSQL {
      select.from(SysMenu as sm).where.eq(sm.menuCode, menuCode)
    }.map(SysMenu(sm.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[SysMenu] = {
    withSQL(select.from(SysMenu as sm)).map(SysMenu(sm.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(SysMenu as sm)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[SysMenu] = {
    withSQL {
      select.from(SysMenu as sm).where.append(where)
    }.map(SysMenu(sm.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SysMenu] = {
    withSQL {
      select.from(SysMenu as sm).where.append(where)
    }.map(SysMenu(sm.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(SysMenu as sm).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    menuCode: String,
    sysCode: Option[String] = None,
    menuName: Option[String] = None,
    menuAction: Option[String] = None,
    deleteFlag: Option[Long] = None,
    icon: Option[String] = None,
    parentId: Option[String] = None,
    levelNo: Option[Long] = None,
    ord: Option[Long] = None,
    isLeaf: Option[Long] = None,
    sofa: Option[String] = None,
    bench: Option[String] = None,
    winPanel: Option[String] = None)(implicit session: DBSession = autoSession): SysMenu = {
    withSQL {
      insert.into(SysMenu).columns(
        column.menuCode,
        column.sysCode,
        column.menuName,
        column.menuAction,
        column.deleteFlag,
        column.icon,
        column.parentId,
        column.levelNo,
        column.ord,
        column.isLeaf,
        column.sofa,
        column.bench,
        column.winPanel
      ).values(
        menuCode,
        sysCode,
        menuName,
        menuAction,
        deleteFlag,
        icon,
        parentId,
        levelNo,
        ord,
        isLeaf,
        sofa,
        bench,
        winPanel
      )
    }.update.apply()

    SysMenu(
      menuCode = menuCode,
      sysCode = sysCode,
      menuName = menuName,
      menuAction = menuAction,
      deleteFlag = deleteFlag,
      icon = icon,
      parentId = parentId,
      levelNo = levelNo,
      ord = ord,
      isLeaf = isLeaf,
      sofa = sofa,
      bench = bench,
      winPanel = winPanel)
  }

  def save(entity: SysMenu)(implicit session: DBSession = autoSession): SysMenu = {
    withSQL {
      update(SysMenu).set(
        column.menuCode -> entity.menuCode,
        column.sysCode -> entity.sysCode,
        column.menuName -> entity.menuName,
        column.menuAction -> entity.menuAction,
        column.deleteFlag -> entity.deleteFlag,
        column.icon -> entity.icon,
        column.parentId -> entity.parentId,
        column.levelNo -> entity.levelNo,
        column.ord -> entity.ord,
        column.isLeaf -> entity.isLeaf,
        column.sofa -> entity.sofa,
        column.bench -> entity.bench,
        column.winPanel -> entity.winPanel
      ).where.eq(column.menuCode, entity.menuCode)
    }.update.apply()
    entity
  }

  def destroy(entity: SysMenu)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(SysMenu).where.eq(column.menuCode, entity.menuCode) }.update.apply()
  }

}

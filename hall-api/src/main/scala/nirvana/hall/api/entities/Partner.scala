package nirvana.hall.api.entities

import nirvana.hall.api.services.AutoSpringDataSourceSession
import scalikejdbc._

case class Partner(
  id: Int,
  userId: Int,
  createdTime: Option[Int] = None,
  updatedTime: Option[Int] = None,
  relationId: Option[Int] = None,
  corporateName: String,
  boss: Option[String] = None,
  address: Option[String] = None,
  mobile: String,
  linkMan: String) {

  def save()(implicit session: DBSession = Partner.autoSession): Partner = Partner.save(this)(session)

  def destroy()(implicit session: DBSession = Partner.autoSession): Unit = Partner.destroy(this)(session)

}


object Partner extends SQLSyntaxSupport[Partner] {

  override val tableName = "PARTNER"

  override val columns = Seq("ID", "USER_ID", "CREATED_TIME", "UPDATED_TIME", "RELATION_ID", "CORPORATE_NAME", "BOSS", "ADDRESS", "MOBILE", "LINK_MAN")

  def apply(p: SyntaxProvider[Partner])(rs: WrappedResultSet): Partner = apply(p.resultName)(rs)
  def apply(p: ResultName[Partner])(rs: WrappedResultSet): Partner = new Partner(
    id = rs.get(p.id),
    userId = rs.get(p.userId),
    createdTime = rs.get(p.createdTime),
    updatedTime = rs.get(p.updatedTime),
    relationId = rs.get(p.relationId),
    corporateName = rs.get(p.corporateName),
    boss = rs.get(p.boss),
    address = rs.get(p.address),
    mobile = rs.get(p.mobile),
    linkMan = rs.get(p.linkMan)
  )

  val p = Partner.syntax("p")

 override def autoSession = AutoSpringDataSourceSession()

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Partner] = {
    withSQL {
      select.from(Partner as p).where.eq(p.id, id)
    }.map(Partner(p.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Partner] = {
    withSQL(select.from(Partner as p)).map(Partner(p.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Partner as p)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Partner] = {
    withSQL {
      select.from(Partner as p).where.append(where)
    }.map(Partner(p.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Partner] = {
    withSQL {
      select.from(Partner as p).where.append(where)
    }.map(Partner(p.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Partner as p).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    userId: Int,
    createdTime: Option[Int] = None,
    updatedTime: Option[Int] = None,
    relationId: Option[Int] = None,
    corporateName: String,
    boss: Option[String] = None,
    address: Option[String] = None,
    mobile: String,
    linkMan: String)(implicit session: DBSession = autoSession): Partner = {
    val generatedKey = withSQL {
      insert.into(Partner).columns(
        column.userId,
        column.createdTime,
        column.updatedTime,
        column.relationId,
        column.corporateName,
        column.boss,
        column.address,
        column.mobile,
        column.linkMan
      ).values(
        userId,
        createdTime,
        updatedTime,
        relationId,
        corporateName,
        boss,
        address,
        mobile,
        linkMan
      )
    }.updateAndReturnGeneratedKey.apply()

    Partner(
      id = generatedKey.toInt,
      userId = userId,
      createdTime = createdTime,
      updatedTime = updatedTime,
      relationId = relationId,
      corporateName = corporateName,
      boss = boss,
      address = address,
      mobile = mobile,
      linkMan = linkMan)
  }

  def save(entity: Partner)(implicit session: DBSession = autoSession): Partner = {
    withSQL {
      update(Partner).set(
        column.id -> entity.id,
        column.userId -> entity.userId,
        column.createdTime -> entity.createdTime,
        column.updatedTime -> entity.updatedTime,
        column.relationId -> entity.relationId,
        column.corporateName -> entity.corporateName,
        column.boss -> entity.boss,
        column.address -> entity.address,
        column.mobile -> entity.mobile,
        column.linkMan -> entity.linkMan
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Partner)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(Partner).where.eq(column.id, entity.id) }.update.apply()
  }

}

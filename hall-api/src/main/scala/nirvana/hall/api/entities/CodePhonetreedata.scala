package nirvana.hall.api.entities

import scalikejdbc._

case class CodePhonetreedata(
  id: Option[Long] = None,
  text: Option[String] = None,
  parentId: Option[Long] = None,
  treeurl: Option[String] = None,
  accounttype: Option[String] = None,
  ischild: Option[String] = None) {

  def save()(implicit session: DBSession = CodePhonetreedata.autoSession): CodePhonetreedata = CodePhonetreedata.save(this)(session)

  def destroy()(implicit session: DBSession = CodePhonetreedata.autoSession): Unit = CodePhonetreedata.destroy(this)(session)

}


object CodePhonetreedata extends SQLSyntaxSupport[CodePhonetreedata] {

  override val tableName = "CODE_PHONETREEDATA"

  override val columns = Seq("ID", "TEXT", "PARENT_ID", "TREEURL", "ACCOUNTTYPE", "ISCHILD")

  def apply(cp: SyntaxProvider[CodePhonetreedata])(rs: WrappedResultSet): CodePhonetreedata = apply(cp.resultName)(rs)
  def apply(cp: ResultName[CodePhonetreedata])(rs: WrappedResultSet): CodePhonetreedata = new CodePhonetreedata(
    id = rs.get(cp.id),
    text = rs.get(cp.text),
    parentId = rs.get(cp.parentId),
    treeurl = rs.get(cp.treeurl),
    accounttype = rs.get(cp.accounttype),
    ischild = rs.get(cp.ischild)
  )

  val cp = CodePhonetreedata.syntax("cp")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(id: Option[Long], text: Option[String], parentId: Option[Long], treeurl: Option[String], accounttype: Option[String], ischild: Option[String])(implicit session: DBSession = autoSession): Option[CodePhonetreedata] = {
    withSQL {
      select.from(CodePhonetreedata as cp).where.eq(cp.id, id).and.eq(cp.text, text).and.eq(cp.parentId, parentId).and.eq(cp.treeurl, treeurl).and.eq(cp.accounttype, accounttype).and.eq(cp.ischild, ischild)
    }.map(CodePhonetreedata(cp.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CodePhonetreedata] = {
    withSQL(select.from(CodePhonetreedata as cp)).map(CodePhonetreedata(cp.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CodePhonetreedata as cp)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CodePhonetreedata] = {
    withSQL {
      select.from(CodePhonetreedata as cp).where.append(where)
    }.map(CodePhonetreedata(cp.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CodePhonetreedata] = {
    withSQL {
      select.from(CodePhonetreedata as cp).where.append(where)
    }.map(CodePhonetreedata(cp.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CodePhonetreedata as cp).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    id: Option[Long] = None,
    text: Option[String] = None,
    parentId: Option[Long] = None,
    treeurl: Option[String] = None,
    accounttype: Option[String] = None,
    ischild: Option[String] = None)(implicit session: DBSession = autoSession): CodePhonetreedata = {
    withSQL {
      insert.into(CodePhonetreedata).columns(
        column.id,
        column.text,
        column.parentId,
        column.treeurl,
        column.accounttype,
        column.ischild
      ).values(
        id,
        text,
        parentId,
        treeurl,
        accounttype,
        ischild
      )
    }.update.apply()

    CodePhonetreedata(
      id = id,
      text = text,
      parentId = parentId,
      treeurl = treeurl,
      accounttype = accounttype,
      ischild = ischild)
  }

  def save(entity: CodePhonetreedata)(implicit session: DBSession = autoSession): CodePhonetreedata = {
    withSQL {
      update(CodePhonetreedata).set(
        column.id -> entity.id,
        column.text -> entity.text,
        column.parentId -> entity.parentId,
        column.treeurl -> entity.treeurl,
        column.accounttype -> entity.accounttype,
        column.ischild -> entity.ischild
      ).where.eq(column.id, entity.id).and.eq(column.text, entity.text).and.eq(column.parentId, entity.parentId).and.eq(column.treeurl, entity.treeurl).and.eq(column.accounttype, entity.accounttype).and.eq(column.ischild, entity.ischild)
    }.update.apply()
    entity
  }

  def destroy(entity: CodePhonetreedata)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CodePhonetreedata).where.eq(column.id, entity.id).and.eq(column.text, entity.text).and.eq(column.parentId, entity.parentId).and.eq(column.treeurl, entity.treeurl).and.eq(column.accounttype, entity.accounttype).and.eq(column.ischild, entity.ischild) }.update.apply()
  }

}

package nirvana.hall.api.entities

import scalikejdbc._

case class GafisGatherJoint(
  pkId: String,
  personid: Option[String] = None,
  ifsuspicion: Option[String] = None,
  ifdeliver: Option[String] = None,
  ifhavegoods: Option[String] = None,
  ifescapepsn: Option[String] = None,
  escapepsnid: Option[String] = None,
  ifdrugpsn: Option[String] = None,
  drugpsnid: Option[String] = None,
  ifillegalpsn: Option[String] = None,
  illegalpsnid: Option[String] = None,
  ifcatchpsn: Option[String] = None,
  catchpsnid: Option[String] = None,
  ifimportancepsn: Option[String] = None,
  importancepsnid: Option[String] = None,
  ifdispsn: Option[String] = None,
  dispsnid: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherJoint.autoSession): GafisGatherJoint = GafisGatherJoint.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherJoint.autoSession): Unit = GafisGatherJoint.destroy(this)(session)

}


object GafisGatherJoint extends SQLSyntaxSupport[GafisGatherJoint] {

  override val tableName = "GAFIS_GATHER_JOINT"

  override val columns = Seq("PK_ID", "PERSONID", "IFSUSPICION", "IFDELIVER", "IFHAVEGOODS", "IFESCAPEPSN", "ESCAPEPSNID", "IFDRUGPSN", "DRUGPSNID", "IFILLEGALPSN", "ILLEGALPSNID", "IFCATCHPSN", "CATCHPSNID", "IFIMPORTANCEPSN", "IMPORTANCEPSNID", "IFDISPSN", "DISPSNID")

  def apply(ggj: SyntaxProvider[GafisGatherJoint])(rs: WrappedResultSet): GafisGatherJoint = apply(ggj.resultName)(rs)
  def apply(ggj: ResultName[GafisGatherJoint])(rs: WrappedResultSet): GafisGatherJoint = new GafisGatherJoint(
    pkId = rs.get(ggj.pkId),
    personid = rs.get(ggj.personid),
    ifsuspicion = rs.get(ggj.ifsuspicion),
    ifdeliver = rs.get(ggj.ifdeliver),
    ifhavegoods = rs.get(ggj.ifhavegoods),
    ifescapepsn = rs.get(ggj.ifescapepsn),
    escapepsnid = rs.get(ggj.escapepsnid),
    ifdrugpsn = rs.get(ggj.ifdrugpsn),
    drugpsnid = rs.get(ggj.drugpsnid),
    ifillegalpsn = rs.get(ggj.ifillegalpsn),
    illegalpsnid = rs.get(ggj.illegalpsnid),
    ifcatchpsn = rs.get(ggj.ifcatchpsn),
    catchpsnid = rs.get(ggj.catchpsnid),
    ifimportancepsn = rs.get(ggj.ifimportancepsn),
    importancepsnid = rs.get(ggj.importancepsnid),
    ifdispsn = rs.get(ggj.ifdispsn),
    dispsnid = rs.get(ggj.dispsnid)
  )

  val ggj = GafisGatherJoint.syntax("ggj")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personid: Option[String], ifsuspicion: Option[String], ifdeliver: Option[String], ifhavegoods: Option[String], ifescapepsn: Option[String], escapepsnid: Option[String], ifdrugpsn: Option[String], drugpsnid: Option[String], ifillegalpsn: Option[String], illegalpsnid: Option[String], ifcatchpsn: Option[String], catchpsnid: Option[String], ifimportancepsn: Option[String], importancepsnid: Option[String], ifdispsn: Option[String], dispsnid: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherJoint] = {
    withSQL {
      select.from(GafisGatherJoint as ggj).where.eq(ggj.pkId, pkId).and.eq(ggj.personid, personid).and.eq(ggj.ifsuspicion, ifsuspicion).and.eq(ggj.ifdeliver, ifdeliver).and.eq(ggj.ifhavegoods, ifhavegoods).and.eq(ggj.ifescapepsn, ifescapepsn).and.eq(ggj.escapepsnid, escapepsnid).and.eq(ggj.ifdrugpsn, ifdrugpsn).and.eq(ggj.drugpsnid, drugpsnid).and.eq(ggj.ifillegalpsn, ifillegalpsn).and.eq(ggj.illegalpsnid, illegalpsnid).and.eq(ggj.ifcatchpsn, ifcatchpsn).and.eq(ggj.catchpsnid, catchpsnid).and.eq(ggj.ifimportancepsn, ifimportancepsn).and.eq(ggj.importancepsnid, importancepsnid).and.eq(ggj.ifdispsn, ifdispsn).and.eq(ggj.dispsnid, dispsnid)
    }.map(GafisGatherJoint(ggj.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherJoint] = {
    withSQL(select.from(GafisGatherJoint as ggj)).map(GafisGatherJoint(ggj.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherJoint as ggj)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherJoint] = {
    withSQL {
      select.from(GafisGatherJoint as ggj).where.append(where)
    }.map(GafisGatherJoint(ggj.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherJoint] = {
    withSQL {
      select.from(GafisGatherJoint as ggj).where.append(where)
    }.map(GafisGatherJoint(ggj.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherJoint as ggj).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personid: Option[String] = None,
    ifsuspicion: Option[String] = None,
    ifdeliver: Option[String] = None,
    ifhavegoods: Option[String] = None,
    ifescapepsn: Option[String] = None,
    escapepsnid: Option[String] = None,
    ifdrugpsn: Option[String] = None,
    drugpsnid: Option[String] = None,
    ifillegalpsn: Option[String] = None,
    illegalpsnid: Option[String] = None,
    ifcatchpsn: Option[String] = None,
    catchpsnid: Option[String] = None,
    ifimportancepsn: Option[String] = None,
    importancepsnid: Option[String] = None,
    ifdispsn: Option[String] = None,
    dispsnid: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherJoint = {
    withSQL {
      insert.into(GafisGatherJoint).columns(
        column.pkId,
        column.personid,
        column.ifsuspicion,
        column.ifdeliver,
        column.ifhavegoods,
        column.ifescapepsn,
        column.escapepsnid,
        column.ifdrugpsn,
        column.drugpsnid,
        column.ifillegalpsn,
        column.illegalpsnid,
        column.ifcatchpsn,
        column.catchpsnid,
        column.ifimportancepsn,
        column.importancepsnid,
        column.ifdispsn,
        column.dispsnid
      ).values(
        pkId,
        personid,
        ifsuspicion,
        ifdeliver,
        ifhavegoods,
        ifescapepsn,
        escapepsnid,
        ifdrugpsn,
        drugpsnid,
        ifillegalpsn,
        illegalpsnid,
        ifcatchpsn,
        catchpsnid,
        ifimportancepsn,
        importancepsnid,
        ifdispsn,
        dispsnid
      )
    }.update.apply()

    GafisGatherJoint(
      pkId = pkId,
      personid = personid,
      ifsuspicion = ifsuspicion,
      ifdeliver = ifdeliver,
      ifhavegoods = ifhavegoods,
      ifescapepsn = ifescapepsn,
      escapepsnid = escapepsnid,
      ifdrugpsn = ifdrugpsn,
      drugpsnid = drugpsnid,
      ifillegalpsn = ifillegalpsn,
      illegalpsnid = illegalpsnid,
      ifcatchpsn = ifcatchpsn,
      catchpsnid = catchpsnid,
      ifimportancepsn = ifimportancepsn,
      importancepsnid = importancepsnid,
      ifdispsn = ifdispsn,
      dispsnid = dispsnid)
  }

  def save(entity: GafisGatherJoint)(implicit session: DBSession = autoSession): GafisGatherJoint = {
    withSQL {
      update(GafisGatherJoint).set(
        column.pkId -> entity.pkId,
        column.personid -> entity.personid,
        column.ifsuspicion -> entity.ifsuspicion,
        column.ifdeliver -> entity.ifdeliver,
        column.ifhavegoods -> entity.ifhavegoods,
        column.ifescapepsn -> entity.ifescapepsn,
        column.escapepsnid -> entity.escapepsnid,
        column.ifdrugpsn -> entity.ifdrugpsn,
        column.drugpsnid -> entity.drugpsnid,
        column.ifillegalpsn -> entity.ifillegalpsn,
        column.illegalpsnid -> entity.illegalpsnid,
        column.ifcatchpsn -> entity.ifcatchpsn,
        column.catchpsnid -> entity.catchpsnid,
        column.ifimportancepsn -> entity.ifimportancepsn,
        column.importancepsnid -> entity.importancepsnid,
        column.ifdispsn -> entity.ifdispsn,
        column.dispsnid -> entity.dispsnid
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.ifsuspicion, entity.ifsuspicion).and.eq(column.ifdeliver, entity.ifdeliver).and.eq(column.ifhavegoods, entity.ifhavegoods).and.eq(column.ifescapepsn, entity.ifescapepsn).and.eq(column.escapepsnid, entity.escapepsnid).and.eq(column.ifdrugpsn, entity.ifdrugpsn).and.eq(column.drugpsnid, entity.drugpsnid).and.eq(column.ifillegalpsn, entity.ifillegalpsn).and.eq(column.illegalpsnid, entity.illegalpsnid).and.eq(column.ifcatchpsn, entity.ifcatchpsn).and.eq(column.catchpsnid, entity.catchpsnid).and.eq(column.ifimportancepsn, entity.ifimportancepsn).and.eq(column.importancepsnid, entity.importancepsnid).and.eq(column.ifdispsn, entity.ifdispsn).and.eq(column.dispsnid, entity.dispsnid)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherJoint)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherJoint).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.ifsuspicion, entity.ifsuspicion).and.eq(column.ifdeliver, entity.ifdeliver).and.eq(column.ifhavegoods, entity.ifhavegoods).and.eq(column.ifescapepsn, entity.ifescapepsn).and.eq(column.escapepsnid, entity.escapepsnid).and.eq(column.ifdrugpsn, entity.ifdrugpsn).and.eq(column.drugpsnid, entity.drugpsnid).and.eq(column.ifillegalpsn, entity.ifillegalpsn).and.eq(column.illegalpsnid, entity.illegalpsnid).and.eq(column.ifcatchpsn, entity.ifcatchpsn).and.eq(column.catchpsnid, entity.catchpsnid).and.eq(column.ifimportancepsn, entity.ifimportancepsn).and.eq(column.importancepsnid, entity.importancepsnid).and.eq(column.ifdispsn, entity.ifdispsn).and.eq(column.dispsnid, entity.dispsnid) }.update.apply()
  }

}

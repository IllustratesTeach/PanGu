package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherDna(
  pkId: String,
  issampled: Option[String] = None,
  dnaid: Option[String] = None,
  locaNum: Option[Short] = None,
  personid: Option[String] = None,
  inputpsn: Option[String] = None,
  inputtime: Option[DateTime] = None,
  modifiedpsn: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  deletag: Option[String] = None,
  sampleType: Option[String] = None,
  samplePg: Option[String] = None,
  sampleDes: Option[String] = None,
  caseProperty: Option[String] = None,
  identificationUnit: Option[String] = None,
  appraiser: Option[String] = None,
  identificationtime: Option[DateTime] = None,
  result: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherDna.autoSession): GafisGatherDna = GafisGatherDna.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherDna.autoSession): Unit = GafisGatherDna.destroy(this)(session)

}


object GafisGatherDna extends SQLSyntaxSupport[GafisGatherDna] {

  override val tableName = "GAFIS_GATHER_DNA"

  override val columns = Seq("PK_ID", "ISSAMPLED", "DNAID", "LOCA_NUM", "PERSONID", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "DELETAG", "SAMPLE_TYPE", "SAMPLE_PG", "SAMPLE_DES", "CASE_PROPERTY", "IDENTIFICATION_UNIT", "APPRAISER", "IDENTIFICATIONTIME", "RESULT")

  def apply(ggd: SyntaxProvider[GafisGatherDna])(rs: WrappedResultSet): GafisGatherDna = apply(ggd.resultName)(rs)
  def apply(ggd: ResultName[GafisGatherDna])(rs: WrappedResultSet): GafisGatherDna = new GafisGatherDna(
    pkId = rs.get(ggd.pkId),
    issampled = rs.get(ggd.issampled),
    dnaid = rs.get(ggd.dnaid),
    locaNum = rs.get(ggd.locaNum),
    personid = rs.get(ggd.personid),
    inputpsn = rs.get(ggd.inputpsn),
    inputtime = rs.get(ggd.inputtime),
    modifiedpsn = rs.get(ggd.modifiedpsn),
    modifiedtime = rs.get(ggd.modifiedtime),
    deletag = rs.get(ggd.deletag),
    sampleType = rs.get(ggd.sampleType),
    samplePg = rs.get(ggd.samplePg),
    sampleDes = rs.get(ggd.sampleDes),
    caseProperty = rs.get(ggd.caseProperty),
    identificationUnit = rs.get(ggd.identificationUnit),
    appraiser = rs.get(ggd.appraiser),
    identificationtime = rs.get(ggd.identificationtime),
    result = rs.get(ggd.result)
  )

  val ggd = GafisGatherDna.syntax("ggd")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, issampled: Option[String], dnaid: Option[String], locaNum: Option[Short], personid: Option[String], inputpsn: Option[String], inputtime: Option[DateTime], modifiedpsn: Option[String], modifiedtime: Option[DateTime], deletag: Option[String], sampleType: Option[String], samplePg: Option[String], sampleDes: Option[String], caseProperty: Option[String], identificationUnit: Option[String], appraiser: Option[String], identificationtime: Option[DateTime], result: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherDna] = {
    withSQL {
      select.from(GafisGatherDna as ggd).where.eq(ggd.pkId, pkId).and.eq(ggd.issampled, issampled).and.eq(ggd.dnaid, dnaid).and.eq(ggd.locaNum, locaNum).and.eq(ggd.personid, personid).and.eq(ggd.inputpsn, inputpsn).and.eq(ggd.inputtime, inputtime).and.eq(ggd.modifiedpsn, modifiedpsn).and.eq(ggd.modifiedtime, modifiedtime).and.eq(ggd.deletag, deletag).and.eq(ggd.sampleType, sampleType).and.eq(ggd.samplePg, samplePg).and.eq(ggd.sampleDes, sampleDes).and.eq(ggd.caseProperty, caseProperty).and.eq(ggd.identificationUnit, identificationUnit).and.eq(ggd.appraiser, appraiser).and.eq(ggd.identificationtime, identificationtime).and.eq(ggd.selectDynamic("result"), result)
    }.map(GafisGatherDna(ggd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherDna] = {
    withSQL(select.from(GafisGatherDna as ggd)).map(GafisGatherDna(ggd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherDna as ggd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherDna] = {
    withSQL {
      select.from(GafisGatherDna as ggd).where.append(where)
    }.map(GafisGatherDna(ggd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherDna] = {
    withSQL {
      select.from(GafisGatherDna as ggd).where.append(where)
    }.map(GafisGatherDna(ggd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherDna as ggd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    issampled: Option[String] = None,
    dnaid: Option[String] = None,
    locaNum: Option[Short] = None,
    personid: Option[String] = None,
    inputpsn: Option[String] = None,
    inputtime: Option[DateTime] = None,
    modifiedpsn: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    deletag: Option[String] = None,
    sampleType: Option[String] = None,
    samplePg: Option[String] = None,
    sampleDes: Option[String] = None,
    caseProperty: Option[String] = None,
    identificationUnit: Option[String] = None,
    appraiser: Option[String] = None,
    identificationtime: Option[DateTime] = None,
    result: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherDna = {
    withSQL {
      insert.into(GafisGatherDna).columns(
        column.pkId,
        column.issampled,
        column.dnaid,
        column.locaNum,
        column.personid,
        column.inputpsn,
        column.inputtime,
        column.modifiedpsn,
        column.modifiedtime,
        column.deletag,
        column.sampleType,
        column.samplePg,
        column.sampleDes,
        column.caseProperty,
        column.identificationUnit,
        column.appraiser,
        column.identificationtime,
        column.result
      ).values(
        pkId,
        issampled,
        dnaid,
        locaNum,
        personid,
        inputpsn,
        inputtime,
        modifiedpsn,
        modifiedtime,
        deletag,
        sampleType,
        samplePg,
        sampleDes,
        caseProperty,
        identificationUnit,
        appraiser,
        identificationtime,
        result
      )
    }.update.apply()

    GafisGatherDna(
      pkId = pkId,
      issampled = issampled,
      dnaid = dnaid,
      locaNum = locaNum,
      personid = personid,
      inputpsn = inputpsn,
      inputtime = inputtime,
      modifiedpsn = modifiedpsn,
      modifiedtime = modifiedtime,
      deletag = deletag,
      sampleType = sampleType,
      samplePg = samplePg,
      sampleDes = sampleDes,
      caseProperty = caseProperty,
      identificationUnit = identificationUnit,
      appraiser = appraiser,
      identificationtime = identificationtime,
      result = result)
  }

  def save(entity: GafisGatherDna)(implicit session: DBSession = autoSession): GafisGatherDna = {
    withSQL {
      update(GafisGatherDna).set(
        column.pkId -> entity.pkId,
        column.issampled -> entity.issampled,
        column.dnaid -> entity.dnaid,
        column.locaNum -> entity.locaNum,
        column.personid -> entity.personid,
        column.inputpsn -> entity.inputpsn,
        column.inputtime -> entity.inputtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.modifiedtime -> entity.modifiedtime,
        column.deletag -> entity.deletag,
        column.sampleType -> entity.sampleType,
        column.samplePg -> entity.samplePg,
        column.sampleDes -> entity.sampleDes,
        column.caseProperty -> entity.caseProperty,
        column.identificationUnit -> entity.identificationUnit,
        column.appraiser -> entity.appraiser,
        column.identificationtime -> entity.identificationtime,
        column.result -> entity.result
      ).where.eq(column.pkId, entity.pkId).and.eq(column.issampled, entity.issampled).and.eq(column.dnaid, entity.dnaid).and.eq(column.locaNum, entity.locaNum).and.eq(column.personid, entity.personid).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag).and.eq(column.sampleType, entity.sampleType).and.eq(column.samplePg, entity.samplePg).and.eq(column.sampleDes, entity.sampleDes).and.eq(column.caseProperty, entity.caseProperty).and.eq(column.identificationUnit, entity.identificationUnit).and.eq(column.appraiser, entity.appraiser).and.eq(column.identificationtime, entity.identificationtime).and.eq(column.result, entity.result)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherDna)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherDna).where.eq(column.pkId, entity.pkId).and.eq(column.issampled, entity.issampled).and.eq(column.dnaid, entity.dnaid).and.eq(column.locaNum, entity.locaNum).and.eq(column.personid, entity.personid).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag).and.eq(column.sampleType, entity.sampleType).and.eq(column.samplePg, entity.samplePg).and.eq(column.sampleDes, entity.sampleDes).and.eq(column.caseProperty, entity.caseProperty).and.eq(column.identificationUnit, entity.identificationUnit).and.eq(column.appraiser, entity.appraiser).and.eq(column.identificationtime, entity.identificationtime).and.eq(column.result, entity.result) }.update.apply()
  }

}

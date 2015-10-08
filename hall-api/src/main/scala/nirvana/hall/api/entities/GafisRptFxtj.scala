package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisRptFxtj(
  orgcode: Option[String] = None,
  gatherdate: Option[DateTime] = None,
  total: Option[Int] = None,
  idcardrate: Option[Int] = None,
  namerate: Option[Int] = None,
  sexrate: Option[Int] = None,
  nationrate: Option[Int] = None,
  nativeplacerate: Option[Int] = None,
  birthdaystrate: Option[Int] = None,
  phonerate: Option[Int] = None,
  virtualrate: Option[Int] = None,
  resumerate: Option[Int] = None,
  certificatesrate: Option[Int] = None,
  specialtyrate: Option[Int] = None,
  dnagatherrate: Option[Int] = None,
  fingerrate: Option[Int] = None,
  portraitrate: Option[Int] = None,
  partyrate: Option[Int] = None,
  physicalrate: Option[Int] = None,
  handwritingrate: Option[Int] = None,
  simrate: Option[Int] = None,
  goodsrate: Option[Int] = None,
  urine: Option[Int] = None,
  gatherTypeId: Option[String] = None) {

  def save()(implicit session: DBSession = GafisRptFxtj.autoSession): GafisRptFxtj = GafisRptFxtj.save(this)(session)

  def destroy()(implicit session: DBSession = GafisRptFxtj.autoSession): Unit = GafisRptFxtj.destroy(this)(session)

}


object GafisRptFxtj extends SQLSyntaxSupport[GafisRptFxtj] {

  override val tableName = "GAFIS_RPT_FXTJ"

  override val columns = Seq("ORGCODE", "GATHERDATE", "TOTAL", "IDCARDRATE", "NAMERATE", "SEXRATE", "NATIONRATE", "NATIVEPLACERATE", "BIRTHDAYSTRATE", "PHONERATE", "VIRTUALRATE", "RESUMERATE", "CERTIFICATESRATE", "SPECIALTYRATE", "DNAGATHERRATE", "FINGERRATE", "PORTRAITRATE", "PARTYRATE", "PHYSICALRATE", "HANDWRITINGRATE", "SIMRATE", "GOODSRATE", "URINE", "GATHER_TYPE_ID")

  def apply(grf: SyntaxProvider[GafisRptFxtj])(rs: WrappedResultSet): GafisRptFxtj = apply(grf.resultName)(rs)
  def apply(grf: ResultName[GafisRptFxtj])(rs: WrappedResultSet): GafisRptFxtj = new GafisRptFxtj(
    orgcode = rs.get(grf.orgcode),
    gatherdate = rs.get(grf.gatherdate),
    total = rs.get(grf.total),
    idcardrate = rs.get(grf.idcardrate),
    namerate = rs.get(grf.namerate),
    sexrate = rs.get(grf.sexrate),
    nationrate = rs.get(grf.nationrate),
    nativeplacerate = rs.get(grf.nativeplacerate),
    birthdaystrate = rs.get(grf.birthdaystrate),
    phonerate = rs.get(grf.phonerate),
    virtualrate = rs.get(grf.virtualrate),
    resumerate = rs.get(grf.resumerate),
    certificatesrate = rs.get(grf.certificatesrate),
    specialtyrate = rs.get(grf.specialtyrate),
    dnagatherrate = rs.get(grf.dnagatherrate),
    fingerrate = rs.get(grf.fingerrate),
    portraitrate = rs.get(grf.portraitrate),
    partyrate = rs.get(grf.partyrate),
    physicalrate = rs.get(grf.physicalrate),
    handwritingrate = rs.get(grf.handwritingrate),
    simrate = rs.get(grf.simrate),
    goodsrate = rs.get(grf.goodsrate),
    urine = rs.get(grf.urine),
    gatherTypeId = rs.get(grf.gatherTypeId)
  )

  val grf = GafisRptFxtj.syntax("grf")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(orgcode: Option[String], gatherdate: Option[DateTime], total: Option[Int], idcardrate: Option[Int], namerate: Option[Int], sexrate: Option[Int], nationrate: Option[Int], nativeplacerate: Option[Int], birthdaystrate: Option[Int], phonerate: Option[Int], virtualrate: Option[Int], resumerate: Option[Int], certificatesrate: Option[Int], specialtyrate: Option[Int], dnagatherrate: Option[Int], fingerrate: Option[Int], portraitrate: Option[Int], partyrate: Option[Int], physicalrate: Option[Int], handwritingrate: Option[Int], simrate: Option[Int], goodsrate: Option[Int], urine: Option[Int], gatherTypeId: Option[String])(implicit session: DBSession = autoSession): Option[GafisRptFxtj] = {
    withSQL {
      select.from(GafisRptFxtj as grf).where.eq(grf.orgcode, orgcode).and.eq(grf.gatherdate, gatherdate).and.eq(grf.total, total).and.eq(grf.idcardrate, idcardrate).and.eq(grf.namerate, namerate).and.eq(grf.sexrate, sexrate).and.eq(grf.nationrate, nationrate).and.eq(grf.nativeplacerate, nativeplacerate).and.eq(grf.birthdaystrate, birthdaystrate).and.eq(grf.phonerate, phonerate).and.eq(grf.virtualrate, virtualrate).and.eq(grf.resumerate, resumerate).and.eq(grf.certificatesrate, certificatesrate).and.eq(grf.specialtyrate, specialtyrate).and.eq(grf.dnagatherrate, dnagatherrate).and.eq(grf.fingerrate, fingerrate).and.eq(grf.portraitrate, portraitrate).and.eq(grf.partyrate, partyrate).and.eq(grf.physicalrate, physicalrate).and.eq(grf.handwritingrate, handwritingrate).and.eq(grf.simrate, simrate).and.eq(grf.goodsrate, goodsrate).and.eq(grf.urine, urine).and.eq(grf.gatherTypeId, gatherTypeId)
    }.map(GafisRptFxtj(grf.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisRptFxtj] = {
    withSQL(select.from(GafisRptFxtj as grf)).map(GafisRptFxtj(grf.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisRptFxtj as grf)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisRptFxtj] = {
    withSQL {
      select.from(GafisRptFxtj as grf).where.append(where)
    }.map(GafisRptFxtj(grf.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisRptFxtj] = {
    withSQL {
      select.from(GafisRptFxtj as grf).where.append(where)
    }.map(GafisRptFxtj(grf.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisRptFxtj as grf).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    orgcode: Option[String] = None,
    gatherdate: Option[DateTime] = None,
    total: Option[Int] = None,
    idcardrate: Option[Int] = None,
    namerate: Option[Int] = None,
    sexrate: Option[Int] = None,
    nationrate: Option[Int] = None,
    nativeplacerate: Option[Int] = None,
    birthdaystrate: Option[Int] = None,
    phonerate: Option[Int] = None,
    virtualrate: Option[Int] = None,
    resumerate: Option[Int] = None,
    certificatesrate: Option[Int] = None,
    specialtyrate: Option[Int] = None,
    dnagatherrate: Option[Int] = None,
    fingerrate: Option[Int] = None,
    portraitrate: Option[Int] = None,
    partyrate: Option[Int] = None,
    physicalrate: Option[Int] = None,
    handwritingrate: Option[Int] = None,
    simrate: Option[Int] = None,
    goodsrate: Option[Int] = None,
    urine: Option[Int] = None,
    gatherTypeId: Option[String] = None)(implicit session: DBSession = autoSession): GafisRptFxtj = {
    withSQL {
      insert.into(GafisRptFxtj).columns(
        column.orgcode,
        column.gatherdate,
        column.total,
        column.idcardrate,
        column.namerate,
        column.sexrate,
        column.nationrate,
        column.nativeplacerate,
        column.birthdaystrate,
        column.phonerate,
        column.virtualrate,
        column.resumerate,
        column.certificatesrate,
        column.specialtyrate,
        column.dnagatherrate,
        column.fingerrate,
        column.portraitrate,
        column.partyrate,
        column.physicalrate,
        column.handwritingrate,
        column.simrate,
        column.goodsrate,
        column.urine,
        column.gatherTypeId
      ).values(
        orgcode,
        gatherdate,
        total,
        idcardrate,
        namerate,
        sexrate,
        nationrate,
        nativeplacerate,
        birthdaystrate,
        phonerate,
        virtualrate,
        resumerate,
        certificatesrate,
        specialtyrate,
        dnagatherrate,
        fingerrate,
        portraitrate,
        partyrate,
        physicalrate,
        handwritingrate,
        simrate,
        goodsrate,
        urine,
        gatherTypeId
      )
    }.update.apply()

    new GafisRptFxtj(
      orgcode = orgcode,
      gatherdate = gatherdate,
      total = total,
      idcardrate = idcardrate,
      namerate = namerate,
      sexrate = sexrate,
      nationrate = nationrate,
      nativeplacerate = nativeplacerate,
      birthdaystrate = birthdaystrate,
      phonerate = phonerate,
      virtualrate = virtualrate,
      resumerate = resumerate,
      certificatesrate = certificatesrate,
      specialtyrate = specialtyrate,
      dnagatherrate = dnagatherrate,
      fingerrate = fingerrate,
      portraitrate = portraitrate,
      partyrate = partyrate,
      physicalrate = physicalrate,
      handwritingrate = handwritingrate,
      simrate = simrate,
      goodsrate = goodsrate,
      urine = urine,
      gatherTypeId = gatherTypeId)
  }

  def save(entity: GafisRptFxtj)(implicit session: DBSession = autoSession): GafisRptFxtj = {
    withSQL {
      update(GafisRptFxtj).set(
        column.orgcode -> entity.orgcode,
        column.gatherdate -> entity.gatherdate,
        column.total -> entity.total,
        column.idcardrate -> entity.idcardrate,
        column.namerate -> entity.namerate,
        column.sexrate -> entity.sexrate,
        column.nationrate -> entity.nationrate,
        column.nativeplacerate -> entity.nativeplacerate,
        column.birthdaystrate -> entity.birthdaystrate,
        column.phonerate -> entity.phonerate,
        column.virtualrate -> entity.virtualrate,
        column.resumerate -> entity.resumerate,
        column.certificatesrate -> entity.certificatesrate,
        column.specialtyrate -> entity.specialtyrate,
        column.dnagatherrate -> entity.dnagatherrate,
        column.fingerrate -> entity.fingerrate,
        column.portraitrate -> entity.portraitrate,
        column.partyrate -> entity.partyrate,
        column.physicalrate -> entity.physicalrate,
        column.handwritingrate -> entity.handwritingrate,
        column.simrate -> entity.simrate,
        column.goodsrate -> entity.goodsrate,
        column.urine -> entity.urine,
        column.gatherTypeId -> entity.gatherTypeId
      ).where.eq(column.orgcode, entity.orgcode).and.eq(column.gatherdate, entity.gatherdate).and.eq(column.total, entity.total).and.eq(column.idcardrate, entity.idcardrate).and.eq(column.namerate, entity.namerate).and.eq(column.sexrate, entity.sexrate).and.eq(column.nationrate, entity.nationrate).and.eq(column.nativeplacerate, entity.nativeplacerate).and.eq(column.birthdaystrate, entity.birthdaystrate).and.eq(column.phonerate, entity.phonerate).and.eq(column.virtualrate, entity.virtualrate).and.eq(column.resumerate, entity.resumerate).and.eq(column.certificatesrate, entity.certificatesrate).and.eq(column.specialtyrate, entity.specialtyrate).and.eq(column.dnagatherrate, entity.dnagatherrate).and.eq(column.fingerrate, entity.fingerrate).and.eq(column.portraitrate, entity.portraitrate).and.eq(column.partyrate, entity.partyrate).and.eq(column.physicalrate, entity.physicalrate).and.eq(column.handwritingrate, entity.handwritingrate).and.eq(column.simrate, entity.simrate).and.eq(column.goodsrate, entity.goodsrate).and.eq(column.urine, entity.urine).and.eq(column.gatherTypeId, entity.gatherTypeId)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisRptFxtj)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisRptFxtj).where.eq(column.orgcode, entity.orgcode).and.eq(column.gatherdate, entity.gatherdate).and.eq(column.total, entity.total).and.eq(column.idcardrate, entity.idcardrate).and.eq(column.namerate, entity.namerate).and.eq(column.sexrate, entity.sexrate).and.eq(column.nationrate, entity.nationrate).and.eq(column.nativeplacerate, entity.nativeplacerate).and.eq(column.birthdaystrate, entity.birthdaystrate).and.eq(column.phonerate, entity.phonerate).and.eq(column.virtualrate, entity.virtualrate).and.eq(column.resumerate, entity.resumerate).and.eq(column.certificatesrate, entity.certificatesrate).and.eq(column.specialtyrate, entity.specialtyrate).and.eq(column.dnagatherrate, entity.dnagatherrate).and.eq(column.fingerrate, entity.fingerrate).and.eq(column.portraitrate, entity.portraitrate).and.eq(column.partyrate, entity.partyrate).and.eq(column.physicalrate, entity.physicalrate).and.eq(column.handwritingrate, entity.handwritingrate).and.eq(column.simrate, entity.simrate).and.eq(column.goodsrate, entity.goodsrate).and.eq(column.urine, entity.urine).and.eq(column.gatherTypeId, entity.gatherTypeId) }.update.apply()
  }

}

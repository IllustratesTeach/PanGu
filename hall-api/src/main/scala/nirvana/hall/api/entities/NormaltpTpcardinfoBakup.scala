package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}
import java.sql.{Clob}

case class NormaltpTpcardinfoBakup(
  cardid: Option[String] = None,
  scancardconfigid: Option[String] = None,
  hithistory: Option[Clob] = None,
  oracomment: Option[Clob] = None,
  personstate: Option[Short] = None,
  digitizedtime: Option[DateTime] = None,
  orgscanner: Option[String] = None,
  orgscanunitcode: Option[String] = None,
  orgafistype: Option[String] = None,
  rolldigitizemethod: Option[Short] = None,
  tplaindigitizemethod: Option[Short] = None,
  palmdigitizemethod: Option[Short] = None) {

  def save()(implicit session: DBSession = NormaltpTpcardinfoBakup.autoSession): NormaltpTpcardinfoBakup = NormaltpTpcardinfoBakup.save(this)(session)

  def destroy()(implicit session: DBSession = NormaltpTpcardinfoBakup.autoSession): Unit = NormaltpTpcardinfoBakup.destroy(this)(session)

}


object NormaltpTpcardinfoBakup extends SQLSyntaxSupport[NormaltpTpcardinfoBakup] {

  override val tableName = "NORMALTP_TPCARDINFO_BAKUP"

  override val columns = Seq("CARDID", "SCANCARDCONFIGID", "HITHISTORY", "ORACOMMENT", "PERSONSTATE", "DIGITIZEDTIME", "ORGSCANNER", "ORGSCANUNITCODE", "ORGAFISTYPE", "ROLLDIGITIZEMETHOD", "TPLAINDIGITIZEMETHOD", "PALMDIGITIZEMETHOD")

  def apply(ntb: SyntaxProvider[NormaltpTpcardinfoBakup])(rs: WrappedResultSet): NormaltpTpcardinfoBakup = apply(ntb.resultName)(rs)
  def apply(ntb: ResultName[NormaltpTpcardinfoBakup])(rs: WrappedResultSet): NormaltpTpcardinfoBakup = new NormaltpTpcardinfoBakup(
    cardid = rs.get(ntb.cardid),
    scancardconfigid = rs.get(ntb.scancardconfigid),
    hithistory = rs.get(ntb.hithistory),
    oracomment = rs.get(ntb.oracomment),
    personstate = rs.get(ntb.personstate),
    digitizedtime = rs.get(ntb.digitizedtime),
    orgscanner = rs.get(ntb.orgscanner),
    orgscanunitcode = rs.get(ntb.orgscanunitcode),
    orgafistype = rs.get(ntb.orgafistype),
    rolldigitizemethod = rs.get(ntb.rolldigitizemethod),
    tplaindigitizemethod = rs.get(ntb.tplaindigitizemethod),
    palmdigitizemethod = rs.get(ntb.palmdigitizemethod)
  )

  val ntb = NormaltpTpcardinfoBakup.syntax("ntb")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(cardid: Option[String], scancardconfigid: Option[String], hithistory: Option[Clob], oracomment: Option[Clob], personstate: Option[Short], digitizedtime: Option[DateTime], orgscanner: Option[String], orgscanunitcode: Option[String], orgafistype: Option[String], rolldigitizemethod: Option[Short], tplaindigitizemethod: Option[Short], palmdigitizemethod: Option[Short])(implicit session: DBSession = autoSession): Option[NormaltpTpcardinfoBakup] = {
    withSQL {
      select.from(NormaltpTpcardinfoBakup as ntb).where.eq(ntb.cardid, cardid).and.eq(ntb.scancardconfigid, scancardconfigid).and.eq(ntb.hithistory, hithistory).and.eq(ntb.oracomment, oracomment).and.eq(ntb.personstate, personstate).and.eq(ntb.digitizedtime, digitizedtime).and.eq(ntb.orgscanner, orgscanner).and.eq(ntb.orgscanunitcode, orgscanunitcode).and.eq(ntb.orgafistype, orgafistype).and.eq(ntb.rolldigitizemethod, rolldigitizemethod).and.eq(ntb.tplaindigitizemethod, tplaindigitizemethod).and.eq(ntb.palmdigitizemethod, palmdigitizemethod)
    }.map(NormaltpTpcardinfoBakup(ntb.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[NormaltpTpcardinfoBakup] = {
    withSQL(select.from(NormaltpTpcardinfoBakup as ntb)).map(NormaltpTpcardinfoBakup(ntb.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(NormaltpTpcardinfoBakup as ntb)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[NormaltpTpcardinfoBakup] = {
    withSQL {
      select.from(NormaltpTpcardinfoBakup as ntb).where.append(where)
    }.map(NormaltpTpcardinfoBakup(ntb.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[NormaltpTpcardinfoBakup] = {
    withSQL {
      select.from(NormaltpTpcardinfoBakup as ntb).where.append(where)
    }.map(NormaltpTpcardinfoBakup(ntb.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(NormaltpTpcardinfoBakup as ntb).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    cardid: Option[String] = None,
    scancardconfigid: Option[String] = None,
    hithistory: Option[Clob] = None,
    oracomment: Option[Clob] = None,
    personstate: Option[Short] = None,
    digitizedtime: Option[DateTime] = None,
    orgscanner: Option[String] = None,
    orgscanunitcode: Option[String] = None,
    orgafistype: Option[String] = None,
    rolldigitizemethod: Option[Short] = None,
    tplaindigitizemethod: Option[Short] = None,
    palmdigitizemethod: Option[Short] = None)(implicit session: DBSession = autoSession): NormaltpTpcardinfoBakup = {
    withSQL {
      insert.into(NormaltpTpcardinfoBakup).columns(
        column.cardid,
        column.scancardconfigid,
        column.hithistory,
        column.oracomment,
        column.personstate,
        column.digitizedtime,
        column.orgscanner,
        column.orgscanunitcode,
        column.orgafistype,
        column.rolldigitizemethod,
        column.tplaindigitizemethod,
        column.palmdigitizemethod
      ).values(
        cardid,
        scancardconfigid,
        hithistory,
        oracomment,
        personstate,
        digitizedtime,
        orgscanner,
        orgscanunitcode,
        orgafistype,
        rolldigitizemethod,
        tplaindigitizemethod,
        palmdigitizemethod
      )
    }.update.apply()

    NormaltpTpcardinfoBakup(
      cardid = cardid,
      scancardconfigid = scancardconfigid,
      hithistory = hithistory,
      oracomment = oracomment,
      personstate = personstate,
      digitizedtime = digitizedtime,
      orgscanner = orgscanner,
      orgscanunitcode = orgscanunitcode,
      orgafistype = orgafistype,
      rolldigitizemethod = rolldigitizemethod,
      tplaindigitizemethod = tplaindigitizemethod,
      palmdigitizemethod = palmdigitizemethod)
  }

  def save(entity: NormaltpTpcardinfoBakup)(implicit session: DBSession = autoSession): NormaltpTpcardinfoBakup = {
    withSQL {
      update(NormaltpTpcardinfoBakup).set(
        column.cardid -> entity.cardid,
        column.scancardconfigid -> entity.scancardconfigid,
        column.hithistory -> entity.hithistory,
        column.oracomment -> entity.oracomment,
        column.personstate -> entity.personstate,
        column.digitizedtime -> entity.digitizedtime,
        column.orgscanner -> entity.orgscanner,
        column.orgscanunitcode -> entity.orgscanunitcode,
        column.orgafistype -> entity.orgafistype,
        column.rolldigitizemethod -> entity.rolldigitizemethod,
        column.tplaindigitizemethod -> entity.tplaindigitizemethod,
        column.palmdigitizemethod -> entity.palmdigitizemethod
      ).where.eq(column.cardid, entity.cardid).and.eq(column.scancardconfigid, entity.scancardconfigid).and.eq(column.hithistory, entity.hithistory).and.eq(column.oracomment, entity.oracomment).and.eq(column.personstate, entity.personstate).and.eq(column.digitizedtime, entity.digitizedtime).and.eq(column.orgscanner, entity.orgscanner).and.eq(column.orgscanunitcode, entity.orgscanunitcode).and.eq(column.orgafistype, entity.orgafistype).and.eq(column.rolldigitizemethod, entity.rolldigitizemethod).and.eq(column.tplaindigitizemethod, entity.tplaindigitizemethod).and.eq(column.palmdigitizemethod, entity.palmdigitizemethod)
    }.update.apply()
    entity
  }

  def destroy(entity: NormaltpTpcardinfoBakup)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(NormaltpTpcardinfoBakup).where.eq(column.cardid, entity.cardid).and.eq(column.scancardconfigid, entity.scancardconfigid).and.eq(column.hithistory, entity.hithistory).and.eq(column.oracomment, entity.oracomment).and.eq(column.personstate, entity.personstate).and.eq(column.digitizedtime, entity.digitizedtime).and.eq(column.orgscanner, entity.orgscanner).and.eq(column.orgscanunitcode, entity.orgscanunitcode).and.eq(column.orgafistype, entity.orgafistype).and.eq(column.rolldigitizemethod, entity.rolldigitizemethod).and.eq(column.tplaindigitizemethod, entity.tplaindigitizemethod).and.eq(column.palmdigitizemethod, entity.palmdigitizemethod) }.update.apply()
  }

}

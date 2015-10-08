package nirvana.hall.api.entities

import scalikejdbc._
import org.joda.time.{DateTime}

case class GafisGatherCase(
  pkId: String,
  personid: Option[String] = None,
  caseNo: Option[String] = None,
  caseType: Option[String] = None,
  caseName: Option[String] = None,
  caseClasses: Option[String] = None,
  nature: Option[String] = None,
  reason: Option[String] = None,
  outline: Option[String] = None,
  causescrimeCode: Option[String] = None,
  gangCode: Option[String] = None,
  gangDivide: Option[String] = None,
  gangPeopleNumber: Option[String] = None,
  gangAge: Option[String] = None,
  gangMeet: Option[String] = None,
  placeReason: Option[String] = None,
  objReason: Option[String] = None,
  timeDescribe: Option[String] = None,
  timeReason: Option[String] = None,
  mentalityBefore: Option[String] = None,
  mentalityDoing: Option[String] = None,
  mentalityAfter: Option[String] = None,
  frequency: Option[String] = None,
  escapeWay: Option[String] = None,
  hurtReason: Option[String] = None,
  hideCode: Option[String] = None,
  hideStreet: Option[String] = None,
  hideAddress: Option[String] = None,
  bribesAddress: Option[String] = None,
  disposalway: Option[String] = None,
  disposalareaCode: Option[String] = None,
  disposalindustryCode: Option[String] = None,
  bribesDisposeTime: Option[String] = None,
  bribesBuyer: Option[String] = None,
  bribesWhere: Option[String] = None,
  notsoldapproachCode: Option[String] = None,
  iscrimevehicle: Option[String] = None,
  isnolicense: Option[String] = None,
  vehiclecrimeCode: Option[String] = None,
  sourcestransportCode: Option[String] = None,
  escapewayCode: Option[String] = None,
  psoperationCode: Option[String] = None,
  pabusinesscase: Option[String] = None,
  inputpsn: Option[String] = None,
  inputtime: Option[DateTime] = None,
  modifiedpsn: Option[String] = None,
  modifiedtime: Option[DateTime] = None,
  deletag: Option[String] = None) {

  def save()(implicit session: DBSession = GafisGatherCase.autoSession): GafisGatherCase = GafisGatherCase.save(this)(session)

  def destroy()(implicit session: DBSession = GafisGatherCase.autoSession): Unit = GafisGatherCase.destroy(this)(session)

}


object GafisGatherCase extends SQLSyntaxSupport[GafisGatherCase] {

  override val tableName = "GAFIS_GATHER_CASE"

  override val columns = Seq("PK_ID", "PERSONID", "CASE_NO", "CASE_TYPE", "CASE_NAME", "CASE_CLASSES", "NATURE", "REASON", "OUTLINE", "CAUSESCRIME_CODE", "GANG_CODE", "GANG_DIVIDE", "GANG_PEOPLE_NUMBER", "GANG_AGE", "GANG_MEET", "PLACE_REASON", "OBJ_REASON", "TIME_DESCRIBE", "TIME_REASON", "MENTALITY_BEFORE", "MENTALITY_DOING", "MENTALITY_AFTER", "FREQUENCY", "ESCAPE_WAY", "HURT_REASON", "HIDE_CODE", "HIDE_STREET", "HIDE_ADDRESS", "BRIBES_ADDRESS", "DISPOSALWAY", "DISPOSALAREA_CODE", "DISPOSALINDUSTRY_CODE", "BRIBES_DISPOSE_TIME", "BRIBES_BUYER", "BRIBES_WHERE", "NOTSOLDAPPROACH_CODE", "ISCRIMEVEHICLE", "ISNOLICENSE", "VEHICLECRIME_CODE", "SOURCESTRANSPORT_CODE", "ESCAPEWAY_CODE", "PSOPERATION_CODE", "PABUSINESSCASE", "INPUTPSN", "INPUTTIME", "MODIFIEDPSN", "MODIFIEDTIME", "DELETAG")

  def apply(ggc: SyntaxProvider[GafisGatherCase])(rs: WrappedResultSet): GafisGatherCase = apply(ggc.resultName)(rs)
  def apply(ggc: ResultName[GafisGatherCase])(rs: WrappedResultSet): GafisGatherCase = new GafisGatherCase(
    pkId = rs.get(ggc.pkId),
    personid = rs.get(ggc.personid),
    caseNo = rs.get(ggc.caseNo),
    caseType = rs.get(ggc.caseType),
    caseName = rs.get(ggc.caseName),
    caseClasses = rs.get(ggc.caseClasses),
    nature = rs.get(ggc.nature),
    reason = rs.get(ggc.reason),
    outline = rs.get(ggc.outline),
    causescrimeCode = rs.get(ggc.causescrimeCode),
    gangCode = rs.get(ggc.gangCode),
    gangDivide = rs.get(ggc.gangDivide),
    gangPeopleNumber = rs.get(ggc.gangPeopleNumber),
    gangAge = rs.get(ggc.gangAge),
    gangMeet = rs.get(ggc.gangMeet),
    placeReason = rs.get(ggc.placeReason),
    objReason = rs.get(ggc.objReason),
    timeDescribe = rs.get(ggc.timeDescribe),
    timeReason = rs.get(ggc.timeReason),
    mentalityBefore = rs.get(ggc.mentalityBefore),
    mentalityDoing = rs.get(ggc.mentalityDoing),
    mentalityAfter = rs.get(ggc.mentalityAfter),
    frequency = rs.get(ggc.frequency),
    escapeWay = rs.get(ggc.escapeWay),
    hurtReason = rs.get(ggc.hurtReason),
    hideCode = rs.get(ggc.hideCode),
    hideStreet = rs.get(ggc.hideStreet),
    hideAddress = rs.get(ggc.hideAddress),
    bribesAddress = rs.get(ggc.bribesAddress),
    disposalway = rs.get(ggc.disposalway),
    disposalareaCode = rs.get(ggc.disposalareaCode),
    disposalindustryCode = rs.get(ggc.disposalindustryCode),
    bribesDisposeTime = rs.get(ggc.bribesDisposeTime),
    bribesBuyer = rs.get(ggc.bribesBuyer),
    bribesWhere = rs.get(ggc.bribesWhere),
    notsoldapproachCode = rs.get(ggc.notsoldapproachCode),
    iscrimevehicle = rs.get(ggc.iscrimevehicle),
    isnolicense = rs.get(ggc.isnolicense),
    vehiclecrimeCode = rs.get(ggc.vehiclecrimeCode),
    sourcestransportCode = rs.get(ggc.sourcestransportCode),
    escapewayCode = rs.get(ggc.escapewayCode),
    psoperationCode = rs.get(ggc.psoperationCode),
    pabusinesscase = rs.get(ggc.pabusinesscase),
    inputpsn = rs.get(ggc.inputpsn),
    inputtime = rs.get(ggc.inputtime),
    modifiedpsn = rs.get(ggc.modifiedpsn),
    modifiedtime = rs.get(ggc.modifiedtime),
    deletag = rs.get(ggc.deletag)
  )

  val ggc = GafisGatherCase.syntax("ggc")

 override def autoSession = nirvana.hall.api.services.AutoSpringDataSourceSession()

  def find(pkId: String, personid: Option[String], caseNo: Option[String], caseType: Option[String], caseName: Option[String], caseClasses: Option[String], nature: Option[String], reason: Option[String], outline: Option[String], causescrimeCode: Option[String], gangCode: Option[String], gangDivide: Option[String], gangPeopleNumber: Option[String], gangAge: Option[String], gangMeet: Option[String], placeReason: Option[String], objReason: Option[String], timeDescribe: Option[String], timeReason: Option[String], mentalityBefore: Option[String], mentalityDoing: Option[String], mentalityAfter: Option[String], frequency: Option[String], escapeWay: Option[String], hurtReason: Option[String], hideCode: Option[String], hideStreet: Option[String], hideAddress: Option[String], bribesAddress: Option[String], disposalway: Option[String], disposalareaCode: Option[String], disposalindustryCode: Option[String], bribesDisposeTime: Option[String], bribesBuyer: Option[String], bribesWhere: Option[String], notsoldapproachCode: Option[String], iscrimevehicle: Option[String], isnolicense: Option[String], vehiclecrimeCode: Option[String], sourcestransportCode: Option[String], escapewayCode: Option[String], psoperationCode: Option[String], pabusinesscase: Option[String], inputpsn: Option[String], inputtime: Option[DateTime], modifiedpsn: Option[String], modifiedtime: Option[DateTime], deletag: Option[String])(implicit session: DBSession = autoSession): Option[GafisGatherCase] = {
    withSQL {
      select.from(GafisGatherCase as ggc).where.eq(ggc.pkId, pkId).and.eq(ggc.personid, personid).and.eq(ggc.caseNo, caseNo).and.eq(ggc.caseType, caseType).and.eq(ggc.caseName, caseName).and.eq(ggc.caseClasses, caseClasses).and.eq(ggc.nature, nature).and.eq(ggc.reason, reason).and.eq(ggc.outline, outline).and.eq(ggc.causescrimeCode, causescrimeCode).and.eq(ggc.gangCode, gangCode).and.eq(ggc.gangDivide, gangDivide).and.eq(ggc.gangPeopleNumber, gangPeopleNumber).and.eq(ggc.gangAge, gangAge).and.eq(ggc.gangMeet, gangMeet).and.eq(ggc.placeReason, placeReason).and.eq(ggc.objReason, objReason).and.eq(ggc.timeDescribe, timeDescribe).and.eq(ggc.timeReason, timeReason).and.eq(ggc.mentalityBefore, mentalityBefore).and.eq(ggc.mentalityDoing, mentalityDoing).and.eq(ggc.mentalityAfter, mentalityAfter).and.eq(ggc.frequency, frequency).and.eq(ggc.escapeWay, escapeWay).and.eq(ggc.hurtReason, hurtReason).and.eq(ggc.hideCode, hideCode).and.eq(ggc.hideStreet, hideStreet).and.eq(ggc.hideAddress, hideAddress).and.eq(ggc.bribesAddress, bribesAddress).and.eq(ggc.disposalway, disposalway).and.eq(ggc.disposalareaCode, disposalareaCode).and.eq(ggc.disposalindustryCode, disposalindustryCode).and.eq(ggc.bribesDisposeTime, bribesDisposeTime).and.eq(ggc.bribesBuyer, bribesBuyer).and.eq(ggc.bribesWhere, bribesWhere).and.eq(ggc.notsoldapproachCode, notsoldapproachCode).and.eq(ggc.iscrimevehicle, iscrimevehicle).and.eq(ggc.isnolicense, isnolicense).and.eq(ggc.vehiclecrimeCode, vehiclecrimeCode).and.eq(ggc.sourcestransportCode, sourcestransportCode).and.eq(ggc.escapewayCode, escapewayCode).and.eq(ggc.psoperationCode, psoperationCode).and.eq(ggc.pabusinesscase, pabusinesscase).and.eq(ggc.inputpsn, inputpsn).and.eq(ggc.inputtime, inputtime).and.eq(ggc.modifiedpsn, modifiedpsn).and.eq(ggc.modifiedtime, modifiedtime).and.eq(ggc.deletag, deletag)
    }.map(GafisGatherCase(ggc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[GafisGatherCase] = {
    withSQL(select.from(GafisGatherCase as ggc)).map(GafisGatherCase(ggc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(GafisGatherCase as ggc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[GafisGatherCase] = {
    withSQL {
      select.from(GafisGatherCase as ggc).where.append(where)
    }.map(GafisGatherCase(ggc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[GafisGatherCase] = {
    withSQL {
      select.from(GafisGatherCase as ggc).where.append(where)
    }.map(GafisGatherCase(ggc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(GafisGatherCase as ggc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    pkId: String,
    personid: Option[String] = None,
    caseNo: Option[String] = None,
    caseType: Option[String] = None,
    caseName: Option[String] = None,
    caseClasses: Option[String] = None,
    nature: Option[String] = None,
    reason: Option[String] = None,
    outline: Option[String] = None,
    causescrimeCode: Option[String] = None,
    gangCode: Option[String] = None,
    gangDivide: Option[String] = None,
    gangPeopleNumber: Option[String] = None,
    gangAge: Option[String] = None,
    gangMeet: Option[String] = None,
    placeReason: Option[String] = None,
    objReason: Option[String] = None,
    timeDescribe: Option[String] = None,
    timeReason: Option[String] = None,
    mentalityBefore: Option[String] = None,
    mentalityDoing: Option[String] = None,
    mentalityAfter: Option[String] = None,
    frequency: Option[String] = None,
    escapeWay: Option[String] = None,
    hurtReason: Option[String] = None,
    hideCode: Option[String] = None,
    hideStreet: Option[String] = None,
    hideAddress: Option[String] = None,
    bribesAddress: Option[String] = None,
    disposalway: Option[String] = None,
    disposalareaCode: Option[String] = None,
    disposalindustryCode: Option[String] = None,
    bribesDisposeTime: Option[String] = None,
    bribesBuyer: Option[String] = None,
    bribesWhere: Option[String] = None,
    notsoldapproachCode: Option[String] = None,
    iscrimevehicle: Option[String] = None,
    isnolicense: Option[String] = None,
    vehiclecrimeCode: Option[String] = None,
    sourcestransportCode: Option[String] = None,
    escapewayCode: Option[String] = None,
    psoperationCode: Option[String] = None,
    pabusinesscase: Option[String] = None,
    inputpsn: Option[String] = None,
    inputtime: Option[DateTime] = None,
    modifiedpsn: Option[String] = None,
    modifiedtime: Option[DateTime] = None,
    deletag: Option[String] = None)(implicit session: DBSession = autoSession): GafisGatherCase = {
    withSQL {
      insert.into(GafisGatherCase).columns(
        column.pkId,
        column.personid,
        column.caseNo,
        column.caseType,
        column.caseName,
        column.caseClasses,
        column.nature,
        column.reason,
        column.outline,
        column.causescrimeCode,
        column.gangCode,
        column.gangDivide,
        column.gangPeopleNumber,
        column.gangAge,
        column.gangMeet,
        column.placeReason,
        column.objReason,
        column.timeDescribe,
        column.timeReason,
        column.mentalityBefore,
        column.mentalityDoing,
        column.mentalityAfter,
        column.frequency,
        column.escapeWay,
        column.hurtReason,
        column.hideCode,
        column.hideStreet,
        column.hideAddress,
        column.bribesAddress,
        column.disposalway,
        column.disposalareaCode,
        column.disposalindustryCode,
        column.bribesDisposeTime,
        column.bribesBuyer,
        column.bribesWhere,
        column.notsoldapproachCode,
        column.iscrimevehicle,
        column.isnolicense,
        column.vehiclecrimeCode,
        column.sourcestransportCode,
        column.escapewayCode,
        column.psoperationCode,
        column.pabusinesscase,
        column.inputpsn,
        column.inputtime,
        column.modifiedpsn,
        column.modifiedtime,
        column.deletag
      ).values(
        pkId,
        personid,
        caseNo,
        caseType,
        caseName,
        caseClasses,
        nature,
        reason,
        outline,
        causescrimeCode,
        gangCode,
        gangDivide,
        gangPeopleNumber,
        gangAge,
        gangMeet,
        placeReason,
        objReason,
        timeDescribe,
        timeReason,
        mentalityBefore,
        mentalityDoing,
        mentalityAfter,
        frequency,
        escapeWay,
        hurtReason,
        hideCode,
        hideStreet,
        hideAddress,
        bribesAddress,
        disposalway,
        disposalareaCode,
        disposalindustryCode,
        bribesDisposeTime,
        bribesBuyer,
        bribesWhere,
        notsoldapproachCode,
        iscrimevehicle,
        isnolicense,
        vehiclecrimeCode,
        sourcestransportCode,
        escapewayCode,
        psoperationCode,
        pabusinesscase,
        inputpsn,
        inputtime,
        modifiedpsn,
        modifiedtime,
        deletag
      )
    }.update.apply()

    new GafisGatherCase(
      pkId = pkId,
      personid = personid,
      caseNo = caseNo,
      caseType = caseType,
      caseName = caseName,
      caseClasses = caseClasses,
      nature = nature,
      reason = reason,
      outline = outline,
      causescrimeCode = causescrimeCode,
      gangCode = gangCode,
      gangDivide = gangDivide,
      gangPeopleNumber = gangPeopleNumber,
      gangAge = gangAge,
      gangMeet = gangMeet,
      placeReason = placeReason,
      objReason = objReason,
      timeDescribe = timeDescribe,
      timeReason = timeReason,
      mentalityBefore = mentalityBefore,
      mentalityDoing = mentalityDoing,
      mentalityAfter = mentalityAfter,
      frequency = frequency,
      escapeWay = escapeWay,
      hurtReason = hurtReason,
      hideCode = hideCode,
      hideStreet = hideStreet,
      hideAddress = hideAddress,
      bribesAddress = bribesAddress,
      disposalway = disposalway,
      disposalareaCode = disposalareaCode,
      disposalindustryCode = disposalindustryCode,
      bribesDisposeTime = bribesDisposeTime,
      bribesBuyer = bribesBuyer,
      bribesWhere = bribesWhere,
      notsoldapproachCode = notsoldapproachCode,
      iscrimevehicle = iscrimevehicle,
      isnolicense = isnolicense,
      vehiclecrimeCode = vehiclecrimeCode,
      sourcestransportCode = sourcestransportCode,
      escapewayCode = escapewayCode,
      psoperationCode = psoperationCode,
      pabusinesscase = pabusinesscase,
      inputpsn = inputpsn,
      inputtime = inputtime,
      modifiedpsn = modifiedpsn,
      modifiedtime = modifiedtime,
      deletag = deletag)
  }

  def save(entity: GafisGatherCase)(implicit session: DBSession = autoSession): GafisGatherCase = {
    withSQL {
      update(GafisGatherCase).set(
        column.pkId -> entity.pkId,
        column.personid -> entity.personid,
        column.caseNo -> entity.caseNo,
        column.caseType -> entity.caseType,
        column.caseName -> entity.caseName,
        column.caseClasses -> entity.caseClasses,
        column.nature -> entity.nature,
        column.reason -> entity.reason,
        column.outline -> entity.outline,
        column.causescrimeCode -> entity.causescrimeCode,
        column.gangCode -> entity.gangCode,
        column.gangDivide -> entity.gangDivide,
        column.gangPeopleNumber -> entity.gangPeopleNumber,
        column.gangAge -> entity.gangAge,
        column.gangMeet -> entity.gangMeet,
        column.placeReason -> entity.placeReason,
        column.objReason -> entity.objReason,
        column.timeDescribe -> entity.timeDescribe,
        column.timeReason -> entity.timeReason,
        column.mentalityBefore -> entity.mentalityBefore,
        column.mentalityDoing -> entity.mentalityDoing,
        column.mentalityAfter -> entity.mentalityAfter,
        column.frequency -> entity.frequency,
        column.escapeWay -> entity.escapeWay,
        column.hurtReason -> entity.hurtReason,
        column.hideCode -> entity.hideCode,
        column.hideStreet -> entity.hideStreet,
        column.hideAddress -> entity.hideAddress,
        column.bribesAddress -> entity.bribesAddress,
        column.disposalway -> entity.disposalway,
        column.disposalareaCode -> entity.disposalareaCode,
        column.disposalindustryCode -> entity.disposalindustryCode,
        column.bribesDisposeTime -> entity.bribesDisposeTime,
        column.bribesBuyer -> entity.bribesBuyer,
        column.bribesWhere -> entity.bribesWhere,
        column.notsoldapproachCode -> entity.notsoldapproachCode,
        column.iscrimevehicle -> entity.iscrimevehicle,
        column.isnolicense -> entity.isnolicense,
        column.vehiclecrimeCode -> entity.vehiclecrimeCode,
        column.sourcestransportCode -> entity.sourcestransportCode,
        column.escapewayCode -> entity.escapewayCode,
        column.psoperationCode -> entity.psoperationCode,
        column.pabusinesscase -> entity.pabusinesscase,
        column.inputpsn -> entity.inputpsn,
        column.inputtime -> entity.inputtime,
        column.modifiedpsn -> entity.modifiedpsn,
        column.modifiedtime -> entity.modifiedtime,
        column.deletag -> entity.deletag
      ).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.caseNo, entity.caseNo).and.eq(column.caseType, entity.caseType).and.eq(column.caseName, entity.caseName).and.eq(column.caseClasses, entity.caseClasses).and.eq(column.nature, entity.nature).and.eq(column.reason, entity.reason).and.eq(column.outline, entity.outline).and.eq(column.causescrimeCode, entity.causescrimeCode).and.eq(column.gangCode, entity.gangCode).and.eq(column.gangDivide, entity.gangDivide).and.eq(column.gangPeopleNumber, entity.gangPeopleNumber).and.eq(column.gangAge, entity.gangAge).and.eq(column.gangMeet, entity.gangMeet).and.eq(column.placeReason, entity.placeReason).and.eq(column.objReason, entity.objReason).and.eq(column.timeDescribe, entity.timeDescribe).and.eq(column.timeReason, entity.timeReason).and.eq(column.mentalityBefore, entity.mentalityBefore).and.eq(column.mentalityDoing, entity.mentalityDoing).and.eq(column.mentalityAfter, entity.mentalityAfter).and.eq(column.frequency, entity.frequency).and.eq(column.escapeWay, entity.escapeWay).and.eq(column.hurtReason, entity.hurtReason).and.eq(column.hideCode, entity.hideCode).and.eq(column.hideStreet, entity.hideStreet).and.eq(column.hideAddress, entity.hideAddress).and.eq(column.bribesAddress, entity.bribesAddress).and.eq(column.disposalway, entity.disposalway).and.eq(column.disposalareaCode, entity.disposalareaCode).and.eq(column.disposalindustryCode, entity.disposalindustryCode).and.eq(column.bribesDisposeTime, entity.bribesDisposeTime).and.eq(column.bribesBuyer, entity.bribesBuyer).and.eq(column.bribesWhere, entity.bribesWhere).and.eq(column.notsoldapproachCode, entity.notsoldapproachCode).and.eq(column.iscrimevehicle, entity.iscrimevehicle).and.eq(column.isnolicense, entity.isnolicense).and.eq(column.vehiclecrimeCode, entity.vehiclecrimeCode).and.eq(column.sourcestransportCode, entity.sourcestransportCode).and.eq(column.escapewayCode, entity.escapewayCode).and.eq(column.psoperationCode, entity.psoperationCode).and.eq(column.pabusinesscase, entity.pabusinesscase).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag)
    }.update.apply()
    entity
  }

  def destroy(entity: GafisGatherCase)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(GafisGatherCase).where.eq(column.pkId, entity.pkId).and.eq(column.personid, entity.personid).and.eq(column.caseNo, entity.caseNo).and.eq(column.caseType, entity.caseType).and.eq(column.caseName, entity.caseName).and.eq(column.caseClasses, entity.caseClasses).and.eq(column.nature, entity.nature).and.eq(column.reason, entity.reason).and.eq(column.outline, entity.outline).and.eq(column.causescrimeCode, entity.causescrimeCode).and.eq(column.gangCode, entity.gangCode).and.eq(column.gangDivide, entity.gangDivide).and.eq(column.gangPeopleNumber, entity.gangPeopleNumber).and.eq(column.gangAge, entity.gangAge).and.eq(column.gangMeet, entity.gangMeet).and.eq(column.placeReason, entity.placeReason).and.eq(column.objReason, entity.objReason).and.eq(column.timeDescribe, entity.timeDescribe).and.eq(column.timeReason, entity.timeReason).and.eq(column.mentalityBefore, entity.mentalityBefore).and.eq(column.mentalityDoing, entity.mentalityDoing).and.eq(column.mentalityAfter, entity.mentalityAfter).and.eq(column.frequency, entity.frequency).and.eq(column.escapeWay, entity.escapeWay).and.eq(column.hurtReason, entity.hurtReason).and.eq(column.hideCode, entity.hideCode).and.eq(column.hideStreet, entity.hideStreet).and.eq(column.hideAddress, entity.hideAddress).and.eq(column.bribesAddress, entity.bribesAddress).and.eq(column.disposalway, entity.disposalway).and.eq(column.disposalareaCode, entity.disposalareaCode).and.eq(column.disposalindustryCode, entity.disposalindustryCode).and.eq(column.bribesDisposeTime, entity.bribesDisposeTime).and.eq(column.bribesBuyer, entity.bribesBuyer).and.eq(column.bribesWhere, entity.bribesWhere).and.eq(column.notsoldapproachCode, entity.notsoldapproachCode).and.eq(column.iscrimevehicle, entity.iscrimevehicle).and.eq(column.isnolicense, entity.isnolicense).and.eq(column.vehiclecrimeCode, entity.vehiclecrimeCode).and.eq(column.sourcestransportCode, entity.sourcestransportCode).and.eq(column.escapewayCode, entity.escapewayCode).and.eq(column.psoperationCode, entity.psoperationCode).and.eq(column.pabusinesscase, entity.pabusinesscase).and.eq(column.inputpsn, entity.inputpsn).and.eq(column.inputtime, entity.inputtime).and.eq(column.modifiedpsn, entity.modifiedpsn).and.eq(column.modifiedtime, entity.modifiedtime).and.eq(column.deletag, entity.deletag) }.update.apply()
  }

}

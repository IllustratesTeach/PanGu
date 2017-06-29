package nirvana.hall.v70.internal.stamp

import nirvana.hall.v70.services.stamp.GatherPersonService
import nirvana.hall.v70.jpa.{GafisPerson, GafisGatherTypeNodeField, GafisGatherType}

import scala.reflect.runtime.universe._

/**
 * Created by wangjue on 2015/10/27.
 */
class GatherPersonServiceImpl extends GatherPersonService{
  /**
   * 捺印人员列表
   * @param start:查询开始条数
   * @param limit:查询条数
   */
  override def queryGatherPersonList(start: Integer, limit: Integer)  : Seq[GafisPerson] = {
//    GafisPerson.all.limit(limit).offset(start).map(_).toSeq
    null
  }

  /**
   * 上报
   * @param personid
   * @param uplaodStatus(0:等待上报;1:正在上报;2:完成上报)
   */
  override def uploadGatherPerson(personid: String, uplaodStatus: String) : Boolean = {
    GafisPerson.update.set(status=uplaodStatus).where(GafisPerson.personid === personid).execute
    true
  }

  /**
   * 捺印人员查询
   * @param gatherDateStart 采集开始时间
   * @param gatherDateEnd 采集结束时间
   * @param name  姓名
   * @param idCard  身份证号
   * @param start:查询开始条数
   * @param limit:查询条数
   */
  override def queryGatherPersonBy(gatherDateStart: String, gatherDateEnd: String, name: String, idCard: String,start: Integer, limit: Integer) : Seq[GafisPerson] = {
    null
  }

  /**
   * 捺印人员高级查询
   */
  override def queryGatherPersonSeniorBy() : Seq[GafisPerson] = ???


  /**
   * 人员采集类型查询
   * @return
   */
  def queryGatherType() : Seq[GafisGatherType] = {
//    GafisGatherType.all.toList.toSeq
    null
  }



  /**
   * 通过人员类型获取不同的采集字段和必填项
   * @param gatherTypeId
   * @return
   */
  def queryGatherTypeNodeFieldBy(gatherTypeId : String)  : Seq[GafisGatherTypeNodeField] = {
    GafisGatherTypeNodeField.find_by_typeId(gatherTypeId).toSeq
  }


  /**
   * 查询人员基本信息
   * @param personId
   * @return
   */
  def queryBasePersonInfo(personId : String) : Option[GafisPerson] = {
    GafisPerson.find_by_personid(personId).headOption
  }

  /**
   * 新增捺印信息
   * @param personInfo
   * @return
   */
  def saveGatherPerson(personInfo : String) : GafisPerson = {
      throw new UnsupportedOperationException
    /*
    //构造函数
    val Constructorparams = defineConstructorParameter(personInfo)
    val index = getPropertyIndex("inputtime")
    Constructorparams(getPropertyIndex("inputtime")) = Some(DateTime.now())
    val ga : GafisPerson = reflectObject[GafisPerson](Constructorparams)

    //println(ga.personid+"---"+ga.name.get)
    val person = createPerson(ga)
    */

    /*updateGatherPerson(personInfo)
    val params = defineConstructorParameter(personInfo)
    val ga1 : GafisPerson = reflectObject[GafisPerson](params)
    val person = GafisPerson.save(ga1)*/

    /*val p = GafisPerson.find("CS520201511050001").get
    println("采集日期："+parseDateTimeToString(p.gatherDate.get))
    println("创建日期："+parseDateTimeToString(p.inputtime.get))
    println(p.personid+"---------"+p.name.get+"-----"+p.dataSources.get+"-----"+p.gatherFingerNum.get)*/

  }


  /**
   * 修改人员信息
   * @param personInfo
   * @return
   */
  def updateGatherPerson(personInfo : String) : GafisPerson = {
    throw new UnsupportedOperationException
    /*
    val params = defineConstructorParameter(personInfo)
    val personid = params(0).toString
    val p = GafisPerson.find(personid)
    params(getPropertyIndex("inputtime")) = p.inputtime
    params(getPropertyIndex("modifiedtime")) = Some(DateTime.now())
    val ga1 : GafisPerson = reflectObject[GafisPerson](params)
    val person = GafisPerson.save(ga1)
    person
    */
  }
  /*




  //构建构造函数参数数组
  def defineConstructorParameter(personInfo : String) : Array[Any] = {
    val pis : Array[String] = personInfo.split("&")
    var map = new HashMap[String,String]()
    //val pi = pis(1).split("=")


    for (p <- pis) {
      val pi = p.split("=")
      if (pi.length == 1)
        map += (pi(0) -> "")
      else
      map += (pi(0) -> pi(1))
    }
    //var shortArr = Array("staturest","avoirdupois","footsize","shoelength","shoesize","dataSources","fingershowStatus")
    //val longArr = Array("gatherFingerNum","isSendTl","sid","seq")

    val Constructorparams = new Array[Any](GafisPerson.columns.size)

    for (c <- 0 to GafisPerson.columns.size-1) {
      val t = GafisPerson.columns(c)
      var field = ""
      var hasValue = false
      if (t.indexOf("_")>0) {
        field = t.replace("_","").toLowerCase
      } else {
        field = t.toLowerCase
      }
      map.foreach(e => {
        val (k,v) = e
        if (field.equals(k.toLowerCase)) {
          if (field == "personid") {
            if (v.isEmpty)
              Constructorparams(c) = UUID.randomUUID().toString.replace("-","").substring(0,22)
            else
              Constructorparams(c) = v
          } else if (field == "birthdayst"){
            Constructorparams(c) = Some(null)
          }
          else {
            Constructorparams(c) = Some(v)
          }
          hasValue = true
        }
      })
      if (!hasValue) {
        if (field == "personid")
          Constructorparams(c) = ""
        else
          Constructorparams(c) = Some(null)
      }
    }
    Constructorparams
  }



  //获取属性位置
  def getPropertyIndex(property : String) : Integer = {
    var index = -1
    for (c <- 0 to GafisPerson.columns.size-1) {
      val t = GafisPerson.columns(c)
      var field = ""
      if (t.indexOf("_")>0) {
        field = t.replace("_","").toLowerCase
      } else {
        field = t.toLowerCase
      }
      if (field.equals(property.toLowerCase))
        index = c
    }
    index
  }

  //格式化时间
  def parseDateTime(time : String) : DateTime = {
    if (time == None || time.equals("") || time.isEmpty)
      null
    else
      DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(time)
  }

  def parseDateTimeToString(time : DateTime) : String = {
    if (time == null)
      ""
    else
      time.toString("yyyy-MM-dd HH:mm:ss")
  }


  def createPerson(person : GafisPerson)  : GafisPerson = {
    val column = GafisPerson.column
    withSQL {
      insert.into(GafisPerson).columns(
        column.personid,
        column.idcardno,
        column.name,
        column.spellname,
        column.usedname,
        column.usedspell,
        column.aliasname,
        column.aliasspell,
        column.sexCode,
        column.nativeplaceCode,
        column.nationCode,
        column.ifmarryCode,
        column.toneCode,
        column.tone,
        column.birthdayst,
        column.birthdayed,
        column.birthCode,
        column.birthStreet,
        column.birthdetail,
        column.door,
        column.doorStreet,
        column.doordetail,
        column.address,
        column.addressStreet,
        column.addressdetail,
        column.cultureCode,
        column.sourceincomeCode,
        column.faithCode,
        column.haveemployment,
        column.jobCode,
        column.headship,
        column.employunit,
        column.employaddress,
        column.otherspecialty,
        column.specialidentityCode,
        column.politicsCode,
        column.istransientpop,
        column.istempregist,
        column.havepermit,
        column.haveresidence,
        column.isservice,
        column.specialgroupCode,
        column.haveseparation,
        column.ismigrantworker,
        column.nameofschool,
        column.istraining,
        column.havecertificate,
        column.staturest,
        column.avoirdupois,
        column.footsize,
        column.shoelength,
        column.bodilyformCode,
        column.faceformCode,
        column.iseyeglass,
        column.shoesize,
        column.bloodtypeCode,
        column.gatherOrgCode,
        column.ipaddress,
        column.gathererId,
        column.gatherDate,
        column.gatherTypeId,
        column.status,
        column.isfingerrepeat,
        column.fingerrepeatno,
        column.taskSource,
        column.receiveTime,
        column.isreturn,
        column.returnTime,
        column.annex,
        column.inputpsn,
        column.inputtime,
        column.modifiedpsn,
        column.modifiedtime,
        column.deletag,
        column.schedule,
        column.approval,
        column.dnaCode,
        column.gatherCategory,
        column.personCategory,
        column.auditor,
        column.auditedtime,
        column.isregather,
        column.gatherFingerMode,
        column.caseName,
        column.caseClasses,
        column.reason,
        column.gatherFingerNum,
        column.fingerRemark,
        column.deprtmac,
        column.gatherdepartcode,
        column.gatheruserid,
        column.gatherFingerTime,
        column.isSendTl,
        column.caseBriefContents,
        column.pushStatus,
        column.pushDate,
        column.remark,
        column.dataSources,
        column.fingershowStatus,
        column.cityCode,
        column.delayDeadline,
        column.fptGatherDepartCode,
        column.fptGatherDepartName,
        column.sid,
        column.blowCode,
        column.blowStreet,
        column.blowDetail,
        column.blowLongitude,
        column.blowLatitude,
        column.blowEastwest,
        column.blowNorthsouth,
        column.seq,
        column.cardid,
        column.recordmark,
        column.recordsituation,
        column.validDate,
        column.arriveLocalDate,
        column.leaveLocalDate,
        column.dbSource,
        column.dbSourceDis,
        column.jobDes,
        column.isXjssmz,
        column.passportNum,
        column.countryCode,
        column.foreignName,
        column.passportValidDate,
        column.visaPlace,
        column.passportType,
        column.visaDate,
        column.assistLevel,
        column.assistBonus,
        column.assistPurpose,
        column.assistRefPerson,
        column.assistRefCase,
        column.assistValidDate,
        column.assistExplain,
        column.assistDeptCode,
        column.assistDeptName,
        column.assistDate,
        column.assistContacts,
        column.assistNumber,
        column.assistApproval,
        column.assistSign,
        column.gatherdepartname,
        column.gatherusername,
        column.contrcaptureCode
      ).values(
        person.personid,
        person.idcardno,
        person.name,
        person.spellname,
        person.usedname,
        person.usedspell,
        person.aliasname,
        person.aliasspell,
        person.sexCode,
        person.nativeplaceCode,
        person.nationCode,
        person.ifmarryCode,
        person.toneCode,
        person.tone,
        person.birthdayst,
        person.birthdayed,
        person.birthCode,
        person.birthStreet,
        person.birthdetail,
        person.door,
        person.doorStreet,
        person.doordetail,
        person.address,
        person.addressStreet,
        person.addressdetail,
        person.cultureCode,
        person.sourceincomeCode,
        person.faithCode,
        person.haveemployment,
        person.jobCode,
        person.headship,
        person.employunit,
        person.employaddress,
        person.otherspecialty,
        person.specialidentityCode,
        person.politicsCode,
        person.istransientpop,
        person.istempregist,
        person.havepermit,
        person.haveresidence,
        person.isservice,
        person.specialgroupCode,
        person.haveseparation,
        person.ismigrantworker,
        person.nameofschool,
        person.istraining,
        person.havecertificate,
        person.staturest,
        person.avoirdupois,
        person.footsize,
        person.shoelength,
        person.bodilyformCode,
        person.faceformCode,
        person.iseyeglass,
        person.shoesize,
        person.bloodtypeCode,
        person.gatherOrgCode,
        person.ipaddress,
        person.gathererId,
        person.gatherDate,
        person.gatherTypeId,
        person.status,
        person.isfingerrepeat,
        person.fingerrepeatno,
        person.taskSource,
        person.receiveTime,
        person.isreturn,
        person.returnTime,
        person.annex,
        person.inputpsn,
        person.inputtime,
        person.modifiedpsn,
        person.modifiedtime,
        person.deletag,
        person.schedule,
        person.approval,
        person.dnaCode,
        person.gatherCategory,
        person.personCategory,
        person.auditor,
        person.auditedtime,
        person.isregather,
        person.gatherFingerMode,
        person.caseName,
        person.caseClasses,
        person.reason,
        person.gatherFingerNum,
        person.fingerRemark,
        person.deprtmac,
        person.gatherdepartcode,
        person.gatheruserid,
        person.gatherFingerTime,
        person.isSendTl,
        person.caseBriefContents,
        person.pushStatus,
        person.pushDate,
        person.remark,
        person.dataSources,
        person.fingershowStatus,
        person.cityCode,
        person.delayDeadline,
        person.fptGatherDepartCode,
        person.fptGatherDepartName,
        person.sid,
        person.blowCode,
        person.blowStreet,
        person.blowDetail,
        person.blowLongitude,
        person.blowLatitude,
        person.blowEastwest,
        person.blowNorthsouth,
        person.seq,
        person.cardid,
        person.recordmark,
        person.recordsituation,
        person.validDate,
        person.arriveLocalDate,
        person.leaveLocalDate,
        person.dbSource,
        person.dbSourceDis,
        person.jobDes,
        person.isXjssmz,
        person.passportNum,
        person.countryCode,
        person.foreignName,
        person.passportValidDate,
        person.visaPlace,
        person.passportType,
        person.visaDate,
        person.assistLevel,
        person.assistBonus,
        person.assistPurpose,
        person.assistRefPerson,
        person.assistRefCase,
        person.assistValidDate,
        person.assistExplain,
        person.assistDeptCode,
        person.assistDeptName,
        person.assistDate,
        person.assistContacts,
        person.assistNumber,
        person.assistApproval,
        person.assistSign,
        person.gatherdepartname,
        person.gatherusername,
        person.contrcaptureCode
      )
    }.update.apply()
    new GafisPerson(
      personid = person.personid,
      idcardno = person.idcardno,
      name = person.name,
      spellname = person.spellname,
      usedname = person.usedname,
      usedspell = person.usedspell,
      aliasname = person.aliasname,
      aliasspell = person.aliasspell,
      sexCode = person.sexCode,
      nativeplaceCode = person.nativeplaceCode,
      nationCode = person.nationCode,
      ifmarryCode = person.ifmarryCode,
      toneCode = person.toneCode,
      tone = person.tone,
      birthdayst = person.birthdayst,
      birthdayed = person.birthdayed,
      birthCode = person.birthCode,
      birthStreet = person.birthStreet,
      birthdetail = person.birthdetail,
      door = person.door,
      doorStreet = person.doorStreet,
      doordetail = person.doordetail,
      address = person.address,
      addressStreet = person.addressStreet,
      addressdetail = person.addressdetail,
      cultureCode = person.cultureCode,
      sourceincomeCode = person.sourceincomeCode,
      faithCode = person.faithCode,
      haveemployment = person.haveemployment,
      jobCode = person.jobCode,
      headship = person.headship,
      employunit = person.employunit,
      employaddress = person.employaddress,
      otherspecialty = person.otherspecialty,
      specialidentityCode = person.specialidentityCode,
      politicsCode = person.politicsCode,
      istransientpop = person.istransientpop,
      istempregist = person.istempregist,
      havepermit = person.havepermit,
      haveresidence = person.haveresidence,
      isservice = person.isservice,
      specialgroupCode = person.specialgroupCode,
      haveseparation = person.haveseparation,
      ismigrantworker = person.ismigrantworker,
      nameofschool = person.nameofschool,
      istraining = person.istraining,
      havecertificate = person.havecertificate,
      staturest = person.staturest,
      avoirdupois = person.avoirdupois,
      footsize = person.footsize,
      shoelength = person.shoelength,
      bodilyformCode = person.bodilyformCode,
      faceformCode = person.faceformCode,
      iseyeglass = person.iseyeglass,
      shoesize = person.shoesize,
      bloodtypeCode = person.bloodtypeCode,
      gatherOrgCode = person.gatherOrgCode,
      ipaddress = person.ipaddress,
      gathererId = person.gathererId,
      gatherDate = person.gatherDate,
      gatherTypeId = person.gatherTypeId,
      status = person.status,
      isfingerrepeat = person.isfingerrepeat,
      fingerrepeatno = person.fingerrepeatno,
      taskSource = person.taskSource,
      receiveTime = person.receiveTime,
      isreturn = person.isreturn,
      returnTime = person.returnTime,
      annex = person.annex,
      inputpsn = person.inputpsn,
      inputtime = person.inputtime,
      modifiedpsn = person.modifiedpsn,
      modifiedtime = person.modifiedtime,
      deletag = person.deletag,
      schedule = person.schedule,
      approval = person.approval,
      dnaCode = person.dnaCode,
      gatherCategory = person.gatherCategory,
      personCategory = person.personCategory,
      auditor = person.auditor,
      auditedtime = person.auditedtime,
      isregather = person.isregather,
      gatherFingerMode = person.gatherFingerMode,
      caseName = person.caseName,
      caseClasses = person.caseClasses,
      reason = person.reason,
      gatherFingerNum = person.gatherFingerNum,
      fingerRemark = person.fingerRemark,
      deprtmac = person.deprtmac,
      gatherdepartcode = person.gatherdepartcode,
      gatheruserid = person.gatheruserid,
      gatherFingerTime = person.gatherFingerTime,
      isSendTl = person.isSendTl,
      caseBriefContents = person.caseBriefContents,
      pushStatus = person.pushStatus,
      pushDate = person.pushDate,
      remark = person.remark,
      dataSources = person.dataSources,
      fingershowStatus = person.fingershowStatus,
      cityCode = person.cityCode,
      delayDeadline = person.delayDeadline,
      fptGatherDepartCode = person.fptGatherDepartCode,
      fptGatherDepartName = person.fptGatherDepartName,
      sid = person.sid,
      blowCode = person.blowCode,
      blowStreet = person.blowStreet,
      blowDetail = person.blowDetail,
      blowLongitude = person.blowLongitude,
      blowLatitude = person.blowLatitude,
      blowEastwest = person.blowEastwest,
      blowNorthsouth = person.blowNorthsouth,
      seq = person.seq,
      cardid = person.cardid,
      recordmark = person.recordmark,
      recordsituation = person.recordsituation,
      validDate = person.validDate,
      arriveLocalDate = person.arriveLocalDate,
      leaveLocalDate = person.leaveLocalDate,
      dbSource = person.dbSource,
      dbSourceDis = person.dbSourceDis,
      jobDes = person.jobDes,
      isXjssmz = person.isXjssmz,
      passportNum = person.passportNum,
      countryCode = person.countryCode,
      foreignName = person.foreignName,
      passportValidDate = person.passportValidDate,
      visaPlace = person.visaPlace,
      passportType = person.passportType,
      visaDate = person.visaDate,
      assistLevel = person.assistLevel,
      assistBonus = person.assistBonus,
      assistPurpose = person.assistPurpose,
      assistRefPerson = person.assistRefPerson,
      assistRefCase = person.assistRefCase,
      assistValidDate = person.assistValidDate,
      assistExplain = person.assistExplain,
      assistDeptCode = person.assistDeptCode,
      assistDeptName = person.assistDeptName,
      assistDate = person.assistDate,
      assistContacts = person.assistContacts,
      assistNumber = person.assistNumber,
      assistApproval = person.assistApproval,
      assistSign = person.assistSign,
      gatherdepartname = person.gatherdepartname,
      gatherusername = person.gatherusername,
      contrcaptureCode = person.contrcaptureCode)

  }
  */


  //反射构造函数赋值
  def reflectObject[T](args : Seq[Any])(implicit typeTag: TypeTag[T]):T = {
    val ru = scala.reflect.runtime.universe
    val m = ru.runtimeMirror(getClass.getClassLoader)
    val clazzSymbol = ru.typeOf[T].typeSymbol
    val clazzMirror = clazzSymbol.asClass
    val c = clazzMirror.primaryConstructor.asMethod
    val constructor =  m.reflectClass(clazzMirror).reflectConstructor(c)
    constructor.apply(args:_*).asInstanceOf[T]
  }

}

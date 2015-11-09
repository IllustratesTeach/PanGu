package nirvana.hall.api.internal.stamp

import java.util.UUID
import nirvana.hall.api.entities.{GafisGatherTypeNodeField, GafisGatherType, GafisPerson}
import nirvana.hall.api.services.stamp.GatherPersonService
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import scalikejdbc._

import scala.collection.immutable.HashMap

import scala.reflect.runtime.universe._

/**
 * Created by wangjue on 2015/10/27.
 */
class GatherPersonServiceImpl extends GatherPersonService{
  val gp = GafisPerson.syntax("gp")
  /**
   * 捺印人员列表
   * @param start:查询开始条数
   * @param limit:查询条数
   */
  override def queryGatherPersonList(start: Integer, limit: Integer) (implicit session: DBSession = GafisPerson.autoSession) : List[GafisPerson] = {
    withSQL {
      select.from(GafisPerson as gp).limit(limit).offset(start)
    }.map(GafisPerson(gp)).list.apply()
  }

  /**
   * 上报
   * @param personid
   * @param uplaodStatus(0:等待上报;1:正在上报;2:完成上报)
   */
  override def uploadGatherPerson(personid: String, uplaodStatus: Integer) (implicit session: DBSession = GafisPerson.autoSession): Boolean = {
    try {
      withSQL {update(GafisPerson).set(GafisPerson.column.status -> uplaodStatus).
        where.eq(GafisPerson.column.personid,personid)}.
        update.apply()
      true
    } catch {
      case exception: Exception => false
    }
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
  override def queryGatherPersonBy(gatherDateStart: String, gatherDateEnd: String, name: String, idCard: String,start: Integer, limit: Integer) (implicit session: DBSession = GafisPerson.autoSession): List[GafisPerson] = {
    withSQL {
      select.from(GafisPerson as gp).where.eq(GafisPerson.column.name,name).and.
        eq(GafisPerson.column.idcardno,idCard).and.between(GafisPerson.column.gatherDate,gatherDateStart,gatherDateEnd).
        orderBy(GafisPerson.column.gatherDate).desc.
        limit(limit).
        offset(start)
    }.map(GafisPerson(gp.resultName)).list.apply()

  }

  /**
   * 捺印人员高级查询
   */
  override def queryGatherPersonSeniorBy() (implicit session: DBSession = GafisPerson.autoSession): List[GafisPerson] = ???


  /**
   * 人员采集类型查询
   * @return
   */
  def queryGatherType() (implicit session: DBSession = GafisGatherType.autoSession) : List[GafisGatherType] = {
    GafisGatherType.findAll()
  }



  /**
   * 通过人员类型获取不同的采集字段和必填项
   * @param gatherTypeId
   * @return
   */
  def queryGatherTypeNodeFieldBy(gatherTypeId : String) (implicit session: DBSession = GafisGatherTypeNodeField.autoSession) : List[GafisGatherTypeNodeField] = {
    GafisGatherTypeNodeField.findAllBy(sqls.eq(GafisGatherTypeNodeField.column.typeId,gatherTypeId))
  }


  /**
   * 新增捺印信息
   * @param personInfo
   * @return
   */
  def saveGatherPerson(personInfo : String) : Integer = {
    //构造函数
    val Constructorparams = defineConstructorParameter(personInfo)
    val ga : GafisPerson = reflectObject[GafisPerson](Constructorparams)
    //println(ga.personid+"---"+ga.name.get)
    val result = createPerson(ga)
    //

    /*val params = defineNameParameter(personInfo)
    val ga1 : GafisPerson = reflectObject[GafisPerson](params)
    val person = GafisPerson.save(ga1)

    val p = GafisPerson.find("CS520201511050001").get
    println("采集日期："+parseDateTimeToString(p.gatherDate.get))
    println(p.personid+"---------"+p.name.get+"-----"+p.dataSources.get+"-----"+p.gatherFingerNum.get)*/

    result
  }


  /**
   * 修改人员信息
   * @param personInfo
   * @return
   */
  def updateGatherPerson(personInfo : String) : GafisPerson = {
    //命名参数赋值
    val params = defineNameParameter(personInfo)
    val ga1 : GafisPerson = reflectObject[GafisPerson](params)
    GafisPerson.save(ga1)
  }




  //构建构造函数参数数组
  def defineConstructorParameter(personInfo : String) : Array[Any] = {
    val pis : Array[String] = personInfo.split("&")
    var map = new HashMap[String,String]()
    for (p <- pis) {
      val pi = p.split("=")
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
              Constructorparams(c) = UUID.randomUUID().toString.replace("-","")
            else
              Constructorparams(c) = v
          }
          else {
            Constructorparams(c) = Some(v)
          }
          hasValue = true
        }
      })
      if (!hasValue) {
        if (field == "personid")
          Constructorparams(c) = None
        else
          Constructorparams(c) = Some(null)
      }
    }
    Constructorparams
  }

  //构建命名参数数组
  def defineNameParameter(personInfo : String) : Array[Any] = {
    //val personInfo = "personid=CS520201511050001&name=anmiyy&idcardno=123&gatherDate=2015-11-9 10:30:30&dataSources=1&gatherFingerNum=10"
    val pis : Array[String] = personInfo.split("&")
    var map = new HashMap[String,String]()
    for (p <- pis) {
      val pi = p.split("=")
      map += (pi(0) -> pi(1))
    }
    val params = new Array[Any](GafisPerson.columns.size)

    for (c <- 0 to GafisPerson.columns.size-1) {
      val t = GafisPerson.columns(c)
      var field = ""
      if (t.indexOf("_")>0) {
        field = t.replace("_","").toLowerCase
      } else {
        field = t.toLowerCase
      }
      map.foreach(e => {
        val (k,v) = e
        if (field.equals(k.toLowerCase)) {
          if (field == "personid") {
            params(c) = k + "=" + v
          } else if (field == "gatherDate") {
            params(c) = Some(k + "=" + parseDateTime(v))
          }
          else {
            params(c) = Some(k + "=" + v)
          }
        }
      })
    }
    params
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


  def createPerson(person : GafisPerson) (implicit session: DBSession = GafisPerson.autoSession) : Integer = {
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

  }


  //对象序列化数组
  //def ObjectToArray(obj : Any) : Seq[Any] = {

  //}

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

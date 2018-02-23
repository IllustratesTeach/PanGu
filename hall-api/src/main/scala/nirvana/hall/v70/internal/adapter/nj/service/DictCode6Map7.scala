package nirvana.hall.v70.internal.adapter.nj.service

import java.util
import javax.xml.bind.annotation._

import monad.support.services.XmlLoader

import scala.collection.Map
import scala.io.Source
import scala.collection.JavaConversions._
/**
 * Created by shishijie on 18/2/23.
 */
object DictCode6Map7 {
  //案件类别
  var caseClasses: Map[String,String] = _
  //区划代码
  var areaClasses: Map[String,String] = _
  //证件类型
  var certificatetype: Map[String,String]= _
  //协查级别
  var assistLevel: Map[String,String]= _
  //人员类别
  var personType: Map[String,String] = _
  //民族
  var nation: Map[String,String]= _
  //前科标识
  var criminalRecord: Map[String,String]= _

  loadResource

  private def loadResource={
    val content = Source.fromInputStream(getClass.getResourceAsStream("/nirvana/hall/v70/nj_dict_code_mapper.xml")).mkString
    val codeMapper = XmlLoader.parseXML[DICT_CODE_MAPPER](content)
    caseClasses = codeMapper.caseClassesCodeList.map(mapper=> mapper.code6 -> mapper.code7).toMap
    areaClasses =  codeMapper.areaCodeList.map(mapper=> mapper.code6 -> mapper.code7).toMap
    certificatetype = codeMapper.certificatetypeCodeList.map(mapper=> mapper.code6 -> mapper.code7).toMap
    assistLevel = codeMapper.assistLevelCodeList.map(mapper=> mapper.code6 -> mapper.code7).toMap
    personType = codeMapper.personTypeCodeList.map(mapper=> mapper.code6 -> mapper.code7).toMap
    nation = codeMapper.nationCodeList.map(mapper=> mapper.code6 -> mapper.code7).toMap
    criminalRecord = codeMapper.criminalRecordCodeList.map(mapper=> mapper.code6 -> mapper.code7).toMap
  }
}
object DictCode7Map6 {
  //案件类别
  var caseClasses: Map[String,String] = _
  //区划代码
  var areaClasses: Map[String,String] = _
  //证件类型
  var certificatetype: Map[String,String]= _
  //协查级别
  var assistLevel: Map[String,String]= _
  //人员类别
  var personType: Map[String,String] = _
  //民族
  var nation: Map[String,String]= _
  //前科标识
  var criminalRecord: Map[String,String]= _

  loadResource

  private def loadResource={
    val content = Source.fromInputStream(getClass.getResourceAsStream("/nirvana/hall/v70/nj_dict_code_mapper.xml")).mkString
    val codeMapper = XmlLoader.parseXML[DICT_CODE_MAPPER](content)
    caseClasses = codeMapper.caseClassesCodeList.map(mapper=> mapper.code7 -> mapper.code6).toMap
    areaClasses =  codeMapper.areaCodeList.map(mapper=> mapper.code7 -> mapper.code6).toMap
    certificatetype = codeMapper.certificatetypeCodeList.map(mapper=> mapper.code7 -> mapper.code6).toMap
    assistLevel = codeMapper.assistLevelCodeList.map(mapper=> mapper.code7 -> mapper.code6).toMap
    personType = codeMapper.personTypeCodeList.map(mapper=> mapper.code7 -> mapper.code6).toMap
    nation = codeMapper.nationCodeList.map(mapper=> mapper.code7 -> mapper.code6).toMap
    criminalRecord = codeMapper.criminalRecordCodeList.map(mapper=> mapper.code7 -> mapper.code6).toMap
  }
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dict")
@XmlRootElement(name = "dict")
class DICT_CODE_MAPPER{
  @XmlElementWrapper(name="caseClassesCode")
  @XmlElement(name = "mapper")
  var caseClassesCodeList= new util.ArrayList[Mapper]
  @XmlElementWrapper(name="areaCode")
  @XmlElement(name = "mapper")
  var areaCodeList= new util.ArrayList[Mapper]
  @XmlElementWrapper(name="certificatetypeCode")
  @XmlElement(name = "mapper")
  var certificatetypeCodeList= new util.ArrayList[Mapper]
  @XmlElementWrapper(name="assistLevelCode")
  @XmlElement(name = "mapper")
  var assistLevelCodeList= new util.ArrayList[Mapper]
  @XmlElementWrapper(name="personTypeCode")
  @XmlElement(name = "mapper")
  var personTypeCodeList= new util.ArrayList[Mapper]
  @XmlElementWrapper(name="nationCode")
  @XmlElement(name = "mapper")
  var nationCodeList= new util.ArrayList[Mapper]
  @XmlElementWrapper(name="criminalRecordCode")
  @XmlElement(name = "mapper")
  var criminalRecordCodeList= new util.ArrayList[Mapper]
}
@XmlRootElement(name="mapper")
class Mapper{
  @XmlAttribute(name = "code6")
  var code6:String = _
  @XmlAttribute(name = "code7")
  var code7:String = _
}

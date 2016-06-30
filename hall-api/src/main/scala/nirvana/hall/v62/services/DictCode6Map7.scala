package nirvana.hall.v62.services

import java.util
import javax.xml.bind.annotation._

import monad.support.services.XmlLoader

import scala.collection.Map
import scala.io.Source
import scala.collection.JavaConversions._
/**
 * Created by songpeng on 16/6/21.
 */
object DictCode6Map7 {
  //案件类别
  var caseClasses: Map[String,String] = _
  //人员类别
  var personCategory: Map[String,String] = _
  //国籍
  var nativeplace: Map[String,String]= _
  //民族
  var nation: Map[String,String]= _
  //证件类型
  var certificatetype: Map[String,String]= _
  //协查级别
  var assistLevel: Map[String,String]= _
  //协查目的
  var assistPurpose: Map[String,String]= _
  //协查有效期
  var assistValidDate: Map[String,String]= _

  loadResource

  private def loadResource={
    val content = Source.fromInputStream(getClass.getResourceAsStream("/nirvana/hall/v62/dict_code_mapper.xml")).mkString
    val codeMapper = XmlLoader.parseXML[DICT_CODE_MAPPER](content)
    caseClasses = codeMapper.caseClassesList.map(mapper=> mapper.code6 -> mapper.code7).toMap
    personCategory = codeMapper.personCategoryList.map(mapper=> mapper.code6 -> mapper.code7).toMap
    nativeplace = codeMapper.nativeplaceList.map(mapper=> mapper.code6 -> mapper.code7).toMap
    nation = codeMapper.nationList.map(mapper=> mapper.code6 -> mapper.code7).toMap
    certificatetype = codeMapper.certificatetypeList.map(mapper=> mapper.code6 -> mapper.code7).toMap
    assistLevel = codeMapper.assistLevelList.map(mapper=> mapper.code6 -> mapper.code7).toMap
    assistPurpose = codeMapper.assistPurposeList.map(mapper=> mapper.code6 -> mapper.code7).toMap
    assistValidDate = codeMapper.assistValidDateList.map(mapper=> mapper.code6 -> mapper.code7).toMap
  }
}
object DictCode7Map6 {
  //案件类别
  var caseClasses: Map[String,String] = _
  //人员类别
  var personCategory: Map[String,String] = _
  //国籍
  var nativeplace: Map[String,String]= _
  //民族
  var nation: Map[String,String]= _
  //证件类型
  var certificatetype: Map[String,String]= _
  //协查级别
  var assistLevel: Map[String,String]= _
  //协查目的
  var assistPurpose: Map[String,String]= _
  //协查有效期
  var assistValidDate: Map[String,String]= _

  loadResource

  private def loadResource={
    val content = Source.fromInputStream(getClass.getResourceAsStream("/nirvana/hall/v62/dict_code_mapper.xml")).mkString
    val codeMapper = XmlLoader.parseXML[DICT_CODE_MAPPER](content)
    caseClasses = codeMapper.caseClassesList.map(mapper=> mapper.code7 -> mapper.code6).toMap
    personCategory = codeMapper.personCategoryList.map(mapper=> mapper.code7 -> mapper.code6).toMap
    nativeplace = codeMapper.nativeplaceList.map(mapper=> mapper.code7 -> mapper.code6).toMap
    nation = codeMapper.nationList.map(mapper=> mapper.code7 -> mapper.code6).toMap
    certificatetype = codeMapper.certificatetypeList.map(mapper=> mapper.code7 -> mapper.code6).toMap
    assistLevel = codeMapper.assistLevelList.map(mapper=> mapper.code7 -> mapper.code6).toMap
    assistPurpose = codeMapper.assistPurposeList.map(mapper=> mapper.code7 -> mapper.code6).toMap
    assistValidDate = codeMapper.assistValidDateList.map(mapper=> mapper.code7 -> mapper.code6).toMap
  }
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dict")
@XmlRootElement(name = "dict")
class DICT_CODE_MAPPER{
  @XmlElementWrapper(name="case_classes")
  @XmlElement(name = "mapper")
  var caseClassesList= new util.ArrayList[Mapper]
  @XmlElementWrapper(name="person_category")
  @XmlElement(name = "mapper")
  var personCategoryList= new util.ArrayList[Mapper]
  @XmlElementWrapper(name="nativeplace")
  @XmlElement(name = "mapper")
  var nativeplaceList= new util.ArrayList[Mapper]
  @XmlElementWrapper(name="nation")
  @XmlElement(name = "mapper")
  var nationList= new util.ArrayList[Mapper]
  @XmlElementWrapper(name="certificatetype")
  @XmlElement(name = "mapper")
  var certificatetypeList= new util.ArrayList[Mapper]
  @XmlElementWrapper(name="assist_level")
  @XmlElement(name = "mapper")
  var assistLevelList= new util.ArrayList[Mapper]
  @XmlElementWrapper(name="assist_purpose")
  @XmlElement(name = "mapper")
  var assistPurposeList= new util.ArrayList[Mapper]
  @XmlElementWrapper(name="assist_valid_date")
  @XmlElement(name = "mapper")
  var assistValidDateList= new util.ArrayList[Mapper]
}
@XmlRootElement(name="mapper")
class Mapper{
  @XmlAttribute(name = "code6")
  var code6:String = _
  @XmlAttribute(name = "code7")
  var code7:String = _
}

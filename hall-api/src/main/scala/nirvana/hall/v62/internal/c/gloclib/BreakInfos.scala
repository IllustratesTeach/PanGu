package nirvana.hall.v62.internal.c.gloclib

import javax.xml.bind.annotation._

/**
  * normaltp_tpcardinfo表hithistory比中信息
  * Created by songpeng on 2017/11/15.
  */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BreakInfos")
@XmlRootElement(name="BreakInfos")
class BreakInfos {
  @XmlElement(name="RecordCount")
  var recordCount: Int = _
  @XmlElement(name="BreakRecord")
  var breakRecords: Array[BreakRecord] = _
}
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BreakRecord")
class BreakRecord {
  @XmlElement(name="Version")
  var version: Int = _
  @XmlElement(name="BreakID")
  var breakID: String = _
  @XmlElement(name="QueryType")
  var queryType: Int = _
  @XmlElement(name="TprCardID")
  var tprCardID: String = _
  @XmlElement(name="TprPersonID")
  var tprPersonID: String = _
  @XmlElement(name="Name")
  var name: String = _
  @XmlElement(name="SexCode")
  var sexCode: String = _
  @XmlElement(name="Birtchday")
  var birtchday: String = _
  @XmlElement(name="ShenFenID")
  var shenFenID: String = _
  @XmlElement(name="HuKouPlaceCode")
  var huKouPlaceCode: String = _
  @XmlElement(name="HuKouPlaceTail")
  var huKouPlaceTail: String = _
  @XmlElement(name="PrinterUnitCode")
  var printerUnitCode: String = _
  @XmlElement(name="PrintDate")
  var printDate: String = _
  @XmlElement(name="PrintName")
  var printName: String = _
  @XmlElement(name="LatentID")
  var latentID: String = _
  @XmlElement(name="CaseID")
  var caseID: String = _
  @XmlElement(name="NFgIndex")
  var nFgIndex: Int = _
  @XmlElement(name="CaseOccurDate")
  var caseOccurDate: String = _
  @XmlElement(name="CaseOccurPlaceTail")
  var caseOccurPlaceTail: String = _
  @XmlElement(name="CaseClassCode")
  var caseClassCode: String = _
  @XmlElement(name="SuperviseLevel")
  var superviseLevel: String = _
  @XmlElement(name="ExtractUnitCode")
  var extractUnitCode: String = _
  @XmlElement(name="LatentMemo")
  var latentMemo: String = _
  @XmlElement(name="CaseMemo")
  var caseMemo: String = _
  @XmlElement(name="BreakUserName")
  var breakUserName: String = _
  @XmlElement(name="BreakerUnitCode")
  var breakUnitCode: String = _
  @XmlElement(name="BreakDateTime")
  var breakDateTime: String = _
  @XmlElement(name="ReCheckUserName")
  var reCheckUserName: String = _
  @XmlElement(name="ReCheckUnitCode")
  var reCheckUnitCode: String = _
  @XmlElement(name="ReCheckDate")
  var reCheckDate: String = _
}
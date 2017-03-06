package nirvana.hall.v62.internal.c.vo

import nirvana.hall.c.annotations.Length
import nirvana.hall.c.services.AncientData

/**
  * Created by songpeng on 2017/2/14.
  * 6.2 normallp_case实体映射，用于通用查询字段映射
  */
class LPCase extends AncientData{
  @Length(23)
  var caseId: String = _    //案件编号
  @Length(6)
  var caseClass1Code: String = _    //案件类别1
  @Length(6)
  var caseClass2Code: String = _    //案件类别2
  @Length(6)
  var caseClass3Code: String = _    //案件类别3
  @Length(8)
  var occurDate: String = _   //发案日期
  @Length(6)
  var occurPlaceCode: String = _    //发案地点代码
  @Length(70)
  var occurPlace: String = _    //发案地点
  @Length(512)
  var caseBriefDetail: String = _   //简要案情
  @Length(1)
  var isMurder: String = _    //命案标识
  @Length(10)
  var amount: String = _    //涉案金额
  @Length(12)
  var extractUnitCode: String = _   //提取单位代码
  @Length(70)
  var extractUnitName: String = _   //提取单位名称
  @Length(8)
  var extractDate: String = _   //提取日期
  @Length(30)
  var extractor: String = _   //提取人
  @Length(6)
  var suspiciousArea1Code: String = _   //可疑地区线索1
  @Length(6)
  var suspiciousArea2Code: String = _   //可疑地区线索2
  @Length(6)
  var suspiciousArea3Code: String = _   //可疑地区线索3
  @Length(1)
  var assistLevel: String = _   //协查级别
  @Length(6)
  var bonus: String = _   //奖金
  @Length(12)
  var assistUnitCode: String = _    //协查单位代码
  @Length(70)
  var assistUnitName: String = _    //协查单位名称
  @Length(8)
  var assistDate: String = _    //协查日期
  @Length(1)
  var isCaseAssist: String = _    //案件协查标识
  @Length(1)
  var caseStatus: String = _    //案件状态
  var fingerCount: Short = _   //本案现场指纹个数
}

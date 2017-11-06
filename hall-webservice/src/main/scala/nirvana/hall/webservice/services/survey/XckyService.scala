package nirvana.hall.webservice.services.survey

import javax.jws.{WebMethod, WebService}

/**
  * Created by yuchen on 2017/11/3.
  */
@WebService(serviceName = "HandprintService", targetNamespace = "http://service")
trait HandprintService {

  /**
    * 数量获取服务
    * @param userName 接口访问用户名(必填)
    * @param password 接口访问密码(必填)
    * @param unitCode 六位单位代码(必填)
    * @param kNo 关联编号(选填)
    * @param updateTimeForm 起始时间（yyyy-mm-dd HH:MM:SS）(选填)
    * @param updateTimeTo 结束时间（yyyy-mm-dd HH:MM:SS）(选填)
    * @return -1：单位代码必须为6位数字 -2：时间不符合格式：yyyy-mm-dd HH:MM:SS n(n>=0)：根据条件查询结果数量
    */
  @WebMethod  def getOriginalDataCount(userName:String,password:String,unitCode:String
                                       ,kNo:String,updateTimeForm:String,updateTimeTo:String):Int

  /**
    * 获取数据列表
    * @param userName 接口访问用户名(必填)
    * @param password 接口访问密码(必填)
    * @param unitCode 六位单位代码(必填)
    * @param kNo 关联编号(选填)
    * @param updateTimeForm 起始时间（yyyy-mm-dd HH:MM:SS）(选填)
    * @param updateTimeTo 结束时间（yyyy-mm-dd HH:MM:SS）(选填)
    * @param startNum 记录开始位置(选填，若为空则默认1)
    * @param endNum 记录结束位置(选填，若为空则默认10)
    * @return data	byte[]	见附录1
      注：以下情况返回null
      1.UnitCode单位代码不符合6位数字
      2.UpdateTimeFrom或UpdateTimeTo时间不符合格式：yyyy-mm-dd HH:MM:SS则返回null
    */
  @WebMethod def getOriginalDataList(userName:String,password:String,unitCode:String
                                     ,kNo:String,updateTimeForm:String,updateTimeTo:String,startNum:Int,endNum:Int):Array[Byte]

  /**
    * 获取数据
    * @param userName 接口访问用户名(必填)
    * @param password 接口访问密码(必填)
    * @param kNo 关联编号(必填)
    * @param sNo 指纹序号(Cardtype为T时此字段不是必填，否则必填）
    * @param cardType  数据类型(必填)，F:现场指纹P:现场掌纹T:案件文字信息
    * @return
    *    data	byte[]	Cardtype为F或P时返回公安部标准的FPT包，Cardtype为T时返回XML，见附录2
         注：以下情况返回null
         1.Cardtype数据类型不为F或P或T
         2.Cardtype数据类型为F或P时，KNo勘验号或者SNo指纹序号为空
         3.Cardtype数据类型为T时，KNo勘验号为空
    */
  @WebMethod def getOriginalData(userName:String,password:String,kNo:String
                                 ,sNo:String,cardType:String): Array[Byte]

  /**
    * 数据利用情况反馈服务（FBUseCondition）
    * @param userName 接口访问用户名(必填)
    * @param password 接口访问密码(必填)
    * @param kNo 关联编号(必填)
    * @param sNo 指纹序号(Cardtype为T时此字段不是必填，否则必填）
    * @param cardNo 指纹编号(选填)
    * @param cardType 数据类型(必填) F:现场指纹P:现场掌纹T:案件文字信息
    * @param resultType 反馈结果(必填) 1：指掌纹系统建库 8：指掌纹系统反馈无建库价值 9：指掌纹系统反馈数据包有问题
    * @return -1：不存在的勘验号或指纹序号 0：反馈失败 1：反馈成功
    */
    @WebMethod def FBUseCondition(userName:String,password:String,kNo:String
                                  ,sNo:String,cardNo:String,cardType:String,resultType:String):String

  /**
    * 指掌纹比中关系反馈服务（FBMatchCondition）
    * @param userName 接口访问用户名(必填)
    * @param password 接口访问密码(必填)
    * @param data byte[] 比中关系(必填) 目前提供两种数据交换方式，第一种是公安部标准FPT包，第二种是XML，XML结构见附录3 注：由指纹系统方来确定采用哪种方式交换数据
    * @return String 1：反馈成功 0：反馈失败
    */
    @WebMethod def FBMatchCondition(userName:String,password:String,data:Array[Byte]):String


  /**
    * 指掌纹鉴定书数据反馈服务（FBIdentifyResult）
    * @param userName 接口访问用户名(必填)
    * @param password 接口访问密码(必填)
    * @param identification_no 鉴定书编号(必填)
    * @param identification_file 鉴定书文件内容(选填)
    * @param kNo 关联编号(必填)
    * @param sNo 指纹序号(Cardtype为T时此字段不是必填，否则必填）
    * @param cardType string	数据类型 F:现场指纹P:现场掌纹T:案件文字信息
    * @return  String 1：反馈成功 0：反馈失败
    */
    @WebMethod def FBIdentifyResult(userName:String,password:String
                                    ,identification_no:String
                                    ,identification_file:Array[Byte]
                                    ,kNo:String,sNo:String,cardType:String):String

  /**
    * 取消指掌纹比中关系服务（FBMatchCancel）
    * @param userName 接口访问用户名(必填)
    * @param password 接口访问密码(必填)
    * @param data byte[]	取消比中关系数据(必填)，XML结构见附录4
    * @return String	-1：无比中关系 1：反馈成功 0：反馈失败
    */
    @WebMethod def FBMatchCancel(userName:String,password:String,data:Array[Byte]):String

  /**
    * 返回指纹系统数据服务（insertOriginalData）
    * @param userName 接口访问用户名(必填)
    * @param password 接口访问密码(必填)
    * @param data byte[] XML结构见附录5
    * @return String	-1：无此勘验号 1：反馈成功 0：反馈失败
    */
    @WebMethod def insertOriginalData(userName:String,password:String,data:Array[Byte]):String

  /**
    * 获取服务器当前时间（getSystemDateTime）
    * @return String	返回时间格式：yyyy-mm-dd HH:MM:SS
    */
    @WebMethod def getSystemDateTime:String

  /**
    * 根据关联编号查询案件编号（getCaseNo）
    * @param userName 接口访问用户名(必填)
    * @param password 接口访问密码(必填)
    * @param kNo 关联编号(必填)
    * @return String	案件编号
    */
    @WebMethod def getCaseNo(userName:String,password:String,kNo:String):String

  /**
    * 根据关联编号查询接警编号（getReceptionNo）
    * @param userName 接口访问用户名(必填)
    * @param password 接口访问密码(必填)
    * @param kNo 关联编号(必填)
    * @return String 接警编号
    */
    @WebMethod def getReceptionNo(userName:String,password:String,kNo:String):String

  /**
    * 获取掌纹数据（getPalmOriginalData）
    * @param userName 接口访问用户名(必填)
    * @param password 接口访问密码(必填)
    * @param kNo 关联编号(必填)
    * @param sNo 指纹序号(必填）
    * @return byte[] 见附录6
    */
    @WebMethod def getPalmOriginalData(userName:String,password:String,kNo:String,sNo:String):Array[Byte]

  /**
    * 获取单个数据（getOriginalDataSingle）
    * @param userName 接口访问用户名(必填)
    * @param password 接口访问密码(必填)
    * @return byte[] 标准FPT
    */
    @WebMethod def getOriginalDataSingle(userName:String,password:String):Array[Byte]

  /**
    * 接收比中信息FPT数据（FBFPTData）
    * @param userName 接口访问用户名(必填)
    * @param password 接口访问密码(必填)
    * @param no1 现勘号或人员号
    * @param no2 现勘号或人员号
    * @param fpt1 对应No1的FPT数据
    * @param fpt2 对应No2的FPT数据
    * @return String 1：反馈成功;0：反馈失败
    */
    @WebMethod def FBFPTData(userName:String,password:String,no1:String,no2:String,fpt1:Array[Byte],fpt2:Array[Byte]):String

  /**
    * 获取指掌纹原始图片（getOriginalPicture）
    * @param userName 接口访问用户名(必填)
    * @param password 接口访问密码(必填)
    * @param kNo 关联编号(必填)
    * @param sNo 序号(必填）
    * @return byte[]
    * <?xml version=”1.0” encoding=”utf-8”?>
      <Root>
        <format>图片格式</format>
        <image>图片base64字符串</image>
        <width>图片宽度</width>
        <height>图片高度<height>
      </Root>
    */
    @WebMethod def getOriginalPicture(userName:String,password:String,kNo:String,sNo:String):Array[Byte]

  /**
    * 获取指掌纹预览图片（getPreviewPicture）
    * @param userName 接口访问用户名(必填)
    * @param password 接口访问密码(必填)
    * @param kNo 关联编号(必填)
    * @param sNo 序号(必填）
    * @return byte[]
    * <?xml version=”1.0” encoding=”utf-8”?>
      <Root>
        <format>图片格式</format>
        <image>图片base64字符串</image>
        <width>图片宽度</width>
        <height>图片高度<height>
      </Root>
    */
    @WebMethod def getPreviewPicture(userName:String,password:String,kNo:String,sNo:String):Array[Byte]

}

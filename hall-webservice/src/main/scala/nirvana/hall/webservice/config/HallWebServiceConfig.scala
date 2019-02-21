package nirvana.hall.webservice.config

import javax.xml.bind.annotation.{XmlElement, _}

import monad.core.config.{HeartbeatConfigSupport, LocalStoreConfigSupport, LogFileSupport}
import monad.support.services.WebServerConfigSupport
import nirvana.hall.api.config.HallImageRemoteConfigSupport
import nirvana.hall.v62.config.V62ServerConfig

/**
  * Created by songpeng on 2017/4/24.
  */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HallWebserviceConfig")
@XmlRootElement(name = "hall_webservice")
class HallWebserviceConfig
  extends LogFileSupport
    with HallImageRemoteConfigSupport
    with WebServerConfigSupport
    with LocalStoreConfigSupport
    with HeartbeatConfigSupport{

  @XmlElement(name = "union4pfmip")
  var union4pfmip: Union4pfmipConfig = new Union4pfmipConfig

  @XmlElement(name = "handprintService")
  var handprintService:HandprintServiceConfig = new HandprintServiceConfig
  @XmlType(name = "quickMatchService")
  var quickMatchService:QuickMatchServiceConfig = new QuickMatchServiceConfig

  @XmlElement(name = "xing_zhuan")
  var XingZhuan: XingZhuanConfig = new XingZhuanConfig
  @XmlElement(name = "local_tenprint_path")
  var localTenprintPath:String = _
  @XmlElement(name = "local_latent_path")
  var localLatentPath:String = _
  @XmlElement(name = "local_hit_result_path")
  var localHitResultPath:String = _
  @XmlElement(name = "xc_hit_result_path")
  var xcHitResultPath:String = _
  @XmlElement(name = "is_save_fpt")
  var saveFPTFlag:String = _
  @XmlElement(name = "template_finger_database_id")
  var templateFingerDBId:String = _
  @XmlElement(name = "local_xkFinger_list_path")
  var localXKFingerListPath:String = _

  @XmlElement(name = "swiftExportService")
  var swiftExportService = new SwiftExportService

  @XmlElement(name = "penaltyTechService")
  var penaltyTechService = new PenaltyTechService

  @XmlElement(name = "xjConvertService")
  var xjConvertService = new XjConvertService

  @XmlElement(name = "colligateGatherService")
  val colligateGatherService = new ColligateGatherService
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "quickMatchService")
class QuickMatchServiceConfig{
  @XmlElement(name = "send_package_cron")
  var sendPackageCron: String = _
  @XmlElement(name = "get_hitresult_cron")
  var getHitResultCron: String = _
  @XmlElement(name = "create_user")
  var createUser: String = _
  @XmlElement(name = "create_unit_code")
  var createUnitCode: String = _
  @XmlElement(name = "kb_name")
  var kbName: String = _
}


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "swiftExportService")
class SwiftExportService{
  @XmlElement(name = "cron")
  var cron:String = _
  @XmlElement(name = "cardid_file_dir")
  var cardIdFileDir:String = _
  @XmlElement(name = "local_store_dir")
  var localStoreDir:String = _
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "penaltyTechService")
class PenaltyTechService{
  @XmlElement(name = "cron_tt")
  var cron_tt:String = _
  @XmlElement(name = "cron_ll")
  var cron_ll:String = _
  @XmlElement(name = "cron_lt")
  var cron_lt:String = _
  @XmlElement(name = "cron_tl")
  var cron_tl:String = _
  @XmlElement(name = "cron_tp")
  var cron_tp:String = _
  @XmlElement(name = "cron_lp")
  var cron_lp:String = _
  @XmlElement(name = "data_transport_url")
  var data_transport_url:String = _
  @XmlElement(name = "tp_endtime")
  var tp_endTime:String = _
  @XmlElement(name = "lp_endtime")
  var lp_endTime:String = _
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xjConvertService")
class XjConvertService{
  @XmlElement(name = "cron")
  var cron:String = _
  @XmlElement(name = "excel_file_dir")
  var excelFileDir:String = _
  @XmlElement(name = "fptx_input_dir")
  var fptxInputDir:String = _
  @XmlElement(name = "fptx_output_dir")
  var fptxOutputDir:String = _
}

@XmlAccessorType(XmlAccessType.FIELD)
class ColligateGatherService{
  @XmlElement(name = "cron")
  var cron:String = _
  @XmlElement(name = "ip")
  var ftpIp:String = _
  @XmlElement(name = "port")
  var port:Int = _
  @XmlElement(name = "username")
  var ftpUsername:String = _
  @XmlElement(name = "password")
  var ftpPassword:String = _
  @XmlElement(name = "url")
  var ftpUrl:String = _
}



/**
  * 现勘接口配置
  */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HandprintService")
class HandprintServiceConfig{
  @XmlElement(name = "cron")
  var cron: String = _
  @XmlElement(name = "get_caseno_cron")
  var getCaseNoCron: String = _
  @XmlElement(name = "send_hit_cron")
  var sendHitCron: String = _
  @XmlElement(name = "get_latent_package_cron")
  var getLatentPackageCron: String = _
  @XmlElement(name = "check_haixin_service_cron")
  var checkHaiXinServiceCron: String = _
  @XmlElement(name = "area")
  var area: String = _
  @XmlElement(name = "url")
  var url: String = _
  @XmlElement(name = "target_namespace")
  var targetNamespace: String = _
  @XmlElement(name = "user")
  var user: String = _
  @XmlElement(name = "password")
  var password: String = _
  @XmlElement(name = "local_store_dir")
  var localStoreDir: String = _
  @XmlElement(name = "data_type")
  var dataType: String = _
  @XmlElement(name = "is_check_asjbh")
  var isCheckAsjbh:Boolean = false
  @XmlElement(name = "is_delete_list_zip")
  var isDeleteListZip:Boolean = false
  @XmlElement(name = "is_delete_file_zip")
  var isDeleteFileZip:Boolean = false
  @XmlElement(name = "survey_v62_server")
  var surveyV62ServiceConfig :Array[SurveyV62ServerConfig] = _
  @XmlElement(name = "survey_hit_result_head_info")
  var surveyHitResultHeadPackageInfo = new SurveyHitResultHeadPackageInfo
  @XmlElement(name = "platform_operatorinfo_provider_class")
  var platformOperatorInfoProviderClass: String = _
}

/**
  * 比中关系导出时需要写入FPT5.0标准要求的HeadPackage信息，而这些信息一般均为固定值
  * 所以在此设置配置项
  */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SurveyHitResultHeadPackageInfo")
class SurveyHitResultHeadPackageInfo{
   @XmlElement(name = "send_unit_code")
   var sendUnitCode:String = _
   @XmlElement(name = "send_unit_name")
   var sendUnitName:String = _
   @XmlElement(name = "send_person_name")
   var sendPersonName:String = _
   @XmlElement(name = "send_person_idcard")
   var sendPersonIdCard:String = _
   @XmlElement(name = "send_person_tel")
   var sendPersonTel:String = _
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SurveyV62ServerConfig")
class SurveyV62ServerConfig{
  @XmlElement(name = "app_server")
  var v62ServerConfig = new V62ServerConfig
  @XmlElement(name = "survey_config")
  var surveyConfig: Array[SurveyConfig] = _
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SurveyConfig")
class SurveyConfig{
  @XmlElement(name = "unit_code")
  var unitCode:String = _
  @XmlElement(name = "start_time")
  var startTime:String = _
  @XmlElement(name = "end_time")
  var endTime:String = _
  @XmlElement(name = "config")
  var config:String = _
}


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Union4pfmip")
class Union4pfmipConfig{
  @XmlElement(name = "cron")
  var cron: String = _
  @XmlElement(name = "url")
  var url: String = _
  @XmlElement(name = "target_namespace")
  var targetNamespace: String = _
  @XmlElement(name = "user")
  var user: String = _
  @XmlElement(name = "password")
  var password: String = _
  @XmlElement(name = "dateLimit")
  var dateLimit: String = _
  @XmlElement(name = "TenPrintPrex")
  var TenPrintPrex: String = _
  @XmlElement(name = "LatentPrex")
  var LatentPrex: String = _
  @XmlElement(name = "send_checkin_cron")
  var sendCheckinCron: String = _
  @XmlElement(name = "upload_checkin_cron")
  var uploadCheckinCron: String = _
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XingZhuanConfig")
class XingZhuanConfig{
  @XmlElement(name = "url")
  var url: String = _
  @XmlElement(name = "target_namespace")
  var targetNamespace: String = _
  @XmlElement(name = "user")
  var user: String = _
  @XmlElement(name = "password")
  var password: String = _
}


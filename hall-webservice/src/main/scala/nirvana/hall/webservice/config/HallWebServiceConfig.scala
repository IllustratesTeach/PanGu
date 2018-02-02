package nirvana.hall.webservice.config

import javax.xml.bind.annotation._

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

  @XmlElement(name = "survey_shh")
  var survey_shh: Survey_shhConfig = new Survey_shhConfig

  @XmlElement(name = "handprintService")
  var handprintService:HandprintServiceConfig = new HandprintServiceConfig

  @XmlElement(name = "XingZhuanSetting")
  var XingZhuanSetting: XingZhuanSetting = new XingZhuanSetting
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
}

/**
  * 现勘接口配置
  */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HandprintService")
class HandprintServiceConfig{
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
  @XmlElement(name = "local_store_dir")
  var localStoreDir: String = _
  @XmlElement(name = "is_delete_list_zip")
  var isDeleteListZip:Boolean = false
  @XmlElement(name = "is_delete_file_zip")
  var isDeleteFileZip:Boolean = false
  @XmlElement(name = "survey_v62_server")
  var surveyV62ServiceConfig :Array[SurveyV62ServerConfig] = _
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
@XmlType(name = "Survey_shh")
class Survey_shhConfig{
  @XmlElement(name = "cron")
  var cron: String = _
  @XmlElement(name = "getSendMatchCron")
  var getSendMatchCron: String = _
  @XmlElement(name = "url")
  var url: String = _
  @XmlElement(name = "target_namespace")
  var targetNamespace: String = _
  @XmlElement(name = "user")
  var user: String = _
  @XmlElement(name = "password")
  var password: String = _
}


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XingZhuanSetting")
class XingZhuanSetting{
  @XmlElement(name = "isAutoQuery")
  var isAutoQuery: String = _
  @XmlElement(name = "cron")
  var cron: String = _
}
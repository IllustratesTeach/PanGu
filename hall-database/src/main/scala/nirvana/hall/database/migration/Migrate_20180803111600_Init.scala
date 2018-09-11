package nirvana.hall.database.migration

import stark.migration._

class Migrate_20180803111600_Init extends Migration {

  def up(): Unit = {
    createTable("LOG_GETFINGERCOUNT", Comment("获取指掌纹数量记录--指纹系统调用现勘接口，获取指掌纹数量，无论是否成功，均在下表中记录信息")) { t =>
      t.column("PK_ID", VarcharType, Limit(32), NotNull, Comment("主键唯一主键，可用uuid"), PrimaryKey)
      t.column("USERID", VarcharType, Limit(20), Nullable, Comment("调用用户名我方用户名，默认AFIS_JZ"))
      t.column("ASJFSDD_XZQHDM", IntegerType, Limit(12), Nullable, Comment("案事件发生地点_行政区划代码"))
      t.column("XCKYBH", VarcharType, Limit(23), Nullable, Comment("现勘编号"))
      t.column("ZZHWLX", VarcharType, Limit(3), Nullable, Comment("查询指掌纹类型----’P’代表掌纹，’F’代表指纹，’A’代表全部。必填。"))
      t.column("KSSJ", TimestampType, Nullable, Comment("开始时间----选填，缺省为当月第一天开始时间。"))
      t.column("JSSJ", TimestampType, Nullable, Comment("结束时间----选填，缺省为当前时间。"))
      t.column("CALLTIME", TimestampType, Nullable, Comment("调用接口日期"))
      t.column("RETURNTIME", TimestampType, Nullable, Comment("接口返回日期"))
      t.column("FINGERCOUNT", IntegerType, Nullable, Comment("接口方返回数量----指掌纹数量，由现勘返回"))
      t.column("ERRORMSG", ClobType, Nullable, Comment("异常信息记录----记录exception"))
    }

    createTable("LOG_GETFINGERLIST", Comment("获取指掌纹列表记录----指纹系统调用现勘接口，获取指掌纹列表（案件名，现勘号，物证号），无论是否成功，均在下表中记录信息")) { t =>
      t.column("PK_ID", VarcharType, Limit(32), NotNull, Comment("主键唯一主键，可用uuid"), PrimaryKey)
      t.column("USERID", VarcharType, Limit(20), Nullable, Comment("调用用户名我方用户名，默认AFIS_JZ"))
      t.column("ASJFSDD_XZQHDM", IntegerType, Limit(12), Nullable, Comment("案事件发生地点_行政区划代码"))
      t.column("XCKYBH", VarcharType, Limit(23), Nullable, Comment("现勘编号"))
      t.column("ZZHWLX", VarcharType, Limit(3), Nullable, Comment("查询指掌纹类型----’P’代表掌纹，’F’代表指纹，’A’代表全部。必填。"))
      t.column("KSSJ", TimestampType, Nullable, Comment("开始时间----选填，缺省为当月第一天开始时间。"))
      t.column("JSSJ", TimestampType, Nullable, Comment("结束时间----选填，缺省为当前时间。"))
      t.column("KS", IntegerType, Nullable, Comment("记录开始位置----选填，缺省值为1"))
      t.column("JS", IntegerType, Nullable, Comment("记录结束位置----选填，缺省值为10"))
      t.column("CALLTIME", TimestampType, Nullable, Comment("调用接口日期"))
      t.column("RETURNTIME", TimestampType, Nullable, Comment("接口返回日期"))
      t.column("RETURNXML", ClobType, Nullable, Comment("接口方返回xml"))
      t.column("ERRORMSG", ClobType, Nullable, Comment("异常信息记录----记录exception"))
    }

    createTable("LOG_GETFINGERDETAIL", Comment("获取指掌纹信息----指纹系统调用现勘接口，获取指掌纹详细信息，无论是否成功，均在下表中记录信息")) { t =>
      t.column("PK_ID", VarcharType, Limit(32), NotNull, Comment("主键唯一主键，可用uuid"), PrimaryKey)
      t.column("USERID", VarcharType, Limit(20), Nullable, Comment("调用用户名我方用户名，默认AFIS_JZ"))
      t.column("XCWZBH", VarcharType, Limit(30), Nullable, Comment("现场物证编号必填"))
      t.column("ASJBH", VarcharType, Limit(23), Nullable, Comment("案事件编号"))
      t.column("YSXT_ASJBH", VarcharType, Limit(30), Nullable, Comment("原始系统-案事件编号"))
      t.column("XCKYBH", VarcharType, Limit(30), Nullable, Comment("现勘编号"))
      t.column("ASJFSDD_XZQHDM", IntegerType, Limit(12), Nullable, Comment("案事件发生地点_行政区划代码----2101:沈阳  2102：大连 ……"))
      t.column("CALLTIME", TimestampType, Nullable, Comment("调用接口日期"))
      t.column("RETURNTIME", TimestampType, Nullable, Comment("接口返回日期"))
      t.column("SAVETIME", TimestampType, Nullable, Comment("入库日期"))
      t.column("CHECKSTATUS", VarcharType, Limit(1), Nullable, Comment("校验状态----0：校验未通过  1：校验通过"))
      t.column("CHECKMESSAGE", ClobType, Nullable, Comment("校验详细信息----xsd过滤信息记录"))
      t.column("SAVESTATUS", VarcharType, Limit(1), Nullable, Comment("入库状态---0：入库失败  1：入库成功"))
      t.column("STATUS", VarcharType, Limit(1), Nullable, Comment("当前状态----1：指掌纹系统建库 3：指纹比中 4：指纹未比中 8：指掌纹系统反馈无建库价值 9：指掌纹系统反馈数据包有问题"))
      t.column("FPTPATH", VarcharType, Limit(500), Nullable, Comment("fpt文件位置"))
      t.column("HITRECORD_REPORT_STATUS", VarcharType, Limit(1), Nullable, Comment("比中记录上报情况----0：无数据或未上报  1：已上报 2：上报失败"))
      t.column("ERRORMSG", ClobType, Nullable, Comment("异常信息记录----记录exception"))
    }

    createTable("LOG_PUTFINGERSTATUS", Comment("发送现场指掌纹状态----指纹系统调用现勘系统接口，提交获取的指掌纹状态信息，在下表中记录信息")) { t =>
      t.column("PK_ID", VarcharType, Limit(32), NotNull, Comment("主键唯一主键，可用uuid"), PrimaryKey)
      t.column("USERNAME", VarcharType, Limit(20), Nullable, Comment("调用用户名我方用户名，默认AFIS_JZ"))
      t.column("XCWZBH", VarcharType, Limit(30), Nullable, Comment("现场物证编号必填"))
      t.column("RESULTTYPE", VarcharType, Limit(1), Nullable, Comment("反馈信息----1：指掌纹系统建库 3：指纹比中 4：指纹未比中 8：指掌纹系统反馈无建库价值 9：指掌纹系统反馈数据包有问题"))
      t.column("CALLTIME", TimestampType, Nullable, Comment("反馈时间"))
      t.column("RETURNSTATUS", VarcharType, Limit(2), Nullable, Comment("服务请求应答-----1：现场物证编号不存在 0：反馈失败 1：反馈成功"))
      t.column("RETURNTIME", TimestampType, Nullable, Comment("应答时间"))
      t.column("ERRORMSG", ClobType, Nullable, Comment("异常信息记录----记录exception"))
    }

    createTable("LOG_GET_CASENO", Comment("获取现场案事件编号----指纹系统调用现勘接口，通过现勘号获取案事件编号，无论是否成功，均在下表中记录信息")) { t =>
      t.column("PK_ID", VarcharType, Limit(32), NotNull, Comment("主键唯一主键，可用uuid"), PrimaryKey)
      t.column("USERNAME", VarcharType, Limit(20), Nullable, Comment("调用用户名我方用户名，默认AFIS_JZ"))
      t.column("XCKYBH", VarcharType, Limit(23), Nullable, Comment("现勘编号"))
      t.column("CALLTIME", TimestampType, Nullable, Comment("服务请求时间"))
      t.column("RETURN_ASJBH", VarcharType, Limit(23), Nullable, Comment("反馈案事件编号"))
      t.column("RETURNTIME", TimestampType, Nullable, Comment("应答时间"))
      t.column("ERRORMSG", ClobType, Nullable, Comment("异常信息记录----记录exception"))
    }

    createTable("LOG_GET_RECEPTIONNO", Comment("获取现场接警号----指纹系统调用现勘接口，通过现勘号获取接警号，无论是否成功，均在下表中记录信息")) { t =>
      t.column("PK_ID", VarcharType, Limit(32), NotNull, Comment("主键唯一主键，可用uuid"), PrimaryKey)
      t.column("USERNAME", VarcharType, Limit(20), Nullable, Comment("调用用户名我方用户名，默认AFIS_JZ"))
      t.column("XCKYBH", VarcharType, Limit(23), Nullable, Comment("现勘编号"))
      t.column("CALLTIME", TimestampType, Nullable, Comment("服务请求时间"))
      t.column("RETURN_JJBH", VarcharType, Limit(23), Nullable, Comment("反馈接警号"))
      t.column("RETURNTIME", TimestampType, Nullable, Comment("应答时间"))
      t.column("ERRORMSG", ClobType, Nullable, Comment("异常信息记录----记录exception"))
    }


//    createTable("LOG_PUTHITRESULT", Comment("发送比中信息主表----指纹系统调用现勘接口，上报比中关系fpt文件，无论是否成功，均在下表中记录信息")) { t =>
//      t.column("PK_ID", VarcharType, Limit(32), NotNull, Comment("主键唯一主键，可用uuid"), PrimaryKey)
//      t.column("USERNAME", VarcharType, Limit(20), Nullable, Comment("调用用户名我方用户名，默认AFIS_JZ"))
//      t.column("HITRESULT_TYPE", VarcharType, Limit(2), Nullable, Comment("比中信息类型----上报比中信息类型，ll or lt"))
//      t.column("XCZW_XCKYBH", VarcharType, Limit(23), Nullable, Comment("现场指纹_现场勘验编号"))
//      t.column("XCZW_XCWZBH", VarcharType, Limit(30), Nullable, Comment("现场指纹_现场物证编号"))
//      t.column("NYZW_YSXT_ASJXGRYBH", VarcharType, Limit(23), Nullable, Comment("捺印指纹_原始系统_案事件相关人员编号"))
//      t.column("NYZW_ZZHWDM", VarcharType, Limit(2), Nullable, Comment("捺印指纹_指掌位代码"))
//      t.column("ASJFSDD_XZQHDM", VarcharType, Limit(12), Nullable, Comment("案事件发生地点_行政区划代码----2101:沈阳  2102：大连"))
//      t.column("CREATETIME", TimestampType, Nullable, Comment("创建日期"))
//      t.column("UPDATETIME", TimestampType, Nullable, Comment("更新日期"))
//      t.column("SENDHITRESULTCOUNT", IntegerType, Nullable, Comment("比中上报次数"))
//    }

    createTable("LOG_PUTHITRESULT", Comment("发送比中信息表----指纹系统调用现勘接口，上报比中关系fpt文件，无论是否成功，均在下表中记录信息")) { t =>
      t.column("PK_ID", VarcharType, Limit(32), NotNull, Comment("主键唯一主键，可用uuid"), PrimaryKey)
      //t.column("FID", VarcharType, Limit(32), NotNull, Comment("发送比中信息主表的PK_ID"))
      t.column("USERNAME", VarcharType, Limit(20), Nullable, Comment("调用用户名我方用户名，默认AFIS_JZ"))
      t.column("HITRESULT_TYPE", VarcharType, Limit(2), Nullable, Comment("比中信息类型----上报比中信息类型，ll or lt"))
      t.column("XCZW_ASJBH", VarcharType, Limit(30), Nullable, Comment("现场指纹_案事件编号"))
      t.column("XCZW_YSXT_ASJBH", VarcharType, Limit(23), Nullable, Comment("现场指纹_原始系统_案事件编号"))
      t.column("XCZW_XCKYBH", VarcharType, Limit(23), Nullable, Comment("现场指纹_现场勘验编号"))
      t.column("XCZW_YSXT_XCZZHWBH", VarcharType, Limit(30), Nullable, Comment("现场指纹_原始系统_现场指掌纹编号"))
      t.column("XCZW_XCWZBH", VarcharType, Limit(30), Nullable, Comment("现场指纹_现场物证编号"))
      t.column("XCZW_XCZZHWKBH", VarcharType, Limit(23), Nullable, Comment("现场指纹_现场指掌纹卡编号"))

      t.column("NYZW_YSXT_ASJXGRYBH", VarcharType, Limit(23), Nullable, Comment("捺印指纹_原始系统_案事件相关人员编号"))
      t.column("NYZW_JZRYBH", VarcharType, Limit(23), Nullable, Comment("捺印指纹_警综人员编号"))
      t.column("NYZW_ASJXGRYBH", VarcharType, Limit(23), Nullable, Comment("捺印指纹_案事件相关人员编号"))
      t.column("NYZW_ZZHWKBH", VarcharType, Limit(23), Nullable, Comment("捺印指纹_指掌纹卡编号"))
      t.column("NYZW_ZZHWDM", VarcharType, Limit(2), Nullable, Comment("捺印指纹_指掌位代码"))
      t.column("ASJFSDD_XZQHDM", VarcharType, Limit(12), Nullable, Comment("案事件发生地点_行政区划代码----2101:沈阳  2102：大连"))
      t.column("CALLTIME", TimestampType, Nullable, Comment("发送请求日期"))
      t.column("RETURNTIME", TimestampType, Nullable, Comment("反馈日期"))
      t.column("FPTPATH", VarcharType, Limit(500), Nullable, Comment("fpt文件位置"))
      t.column("RETURNSTATUS", VarcharType, Limit(3), Nullable, Comment("接口方返回标记-----1：现场物证编号不存在 0：反馈失败 1：反馈成功"))
      t.column("ERRORMSG", ClobType, Nullable, Comment("异常信息记录----记录exception"))
    }

    createTable("LOG_EDITRECORD", Comment("修改补全比中信息----在上报比中关系前，系统自动修改比中关系对应字段，将历史数据和新数据记入下表")) { t =>
      t.column("PK_ID", VarcharType, Limit(32), NotNull,Comment("主键唯一主键，可用uuid"),PrimaryKey)
      t.column("OLD_XCZW_ASJBH", VarcharType, Limit(30), Nullable,Comment("原始_现场指纹_案事件编号"))
      t.column("OLD_XCZW_YSXT_ASJBH", VarcharType, Limit(23), Nullable,Comment("原始_现场指纹_原始系统_案事件编号"))
      t.column("OLD_XCZW_XCKYBH", VarcharType, Limit(23), Nullable,Comment("原始_现场指纹_现场勘验编号"))
      t.column("OLD_XCZW_YSXT_XCZZHWBH", VarcharType, Limit(30), Nullable,Comment("原始_现场指纹_原始系统_现场指掌纹编号"))
      t.column("OLD_XCZW_XCWZBH", VarcharType, Limit(30), Nullable,Comment("原始_现场指纹_现场物证编号"))
      t.column("OLD_XCZW_XCZZHWKBH", VarcharType, Limit(23), Nullable,Comment("原始_现场指纹_现场指掌纹卡编号"))
      t.column("OLD_NYZW_YSXT_ASJXGRYBH", VarcharType, Limit(23), Nullable,Comment("原始_捺印指纹_原始系统_案事件相关人员编号"))
      t.column("OLD_NYZW_JZRYBH", VarcharType, Limit(23), Nullable,Comment("原始_捺印指纹_警综人员编号"))
      t.column("OLD_NYZW_ASJXGRYBH", VarcharType, Limit(23), Nullable,Comment("原始_捺印指纹_案事件相关人员编号"))
      t.column("OLD_NYZW_ZZHWKBH", VarcharType, Limit(23), Nullable,Comment("原始_捺印指纹_指掌纹卡编号"))
      t.column("OLD_NYZW_ZZHWDM", VarcharType, Limit(2), Nullable,Comment("原始_捺印指纹_指掌位代码"))
      t.column("MODIFYTIME", TimestampType, Nullable,Comment("修改时间"))
      t.column("NEW_XCZW_ASJBH", VarcharType, Limit(30), Nullable,Comment("修改_现场指纹_案事件编号"))
      t.column("NEW_XCZW_YSXT_ASJBH", VarcharType, Limit(23), Nullable,Comment("修改_现场指纹_原始系统_案事件编号"))
      t.column("NEW_XCZW_XCKYBH", VarcharType, Limit(23), Nullable,Comment("修改_现场指纹_现场勘验编号"))
      t.column("NEW_XCZW_YSXT_XCZZHWBH", VarcharType, Limit(30), Nullable,Comment("修改_现场指纹_原始系统_现场指掌纹编号"))
      t.column("NEW_XCZW_XCWZBH", VarcharType, Limit(30), Nullable,Comment("修改_现场指纹_现场物证编号"))
      t.column("NEW_XCZW_XCZZHWKBH", VarcharType, Limit(23), Nullable,Comment("修改_现场指纹_现场指掌纹卡编号"))
      t.column("NEW_NYZW_YSXT_ASJXGRYBH", VarcharType, Limit(23), Nullable,Comment("修改_捺印指纹_原始系统_案事件相关人员编号"))
      t.column("NEW_NYZW_JZRYBH", VarcharType, Limit(23), Nullable,Comment("修改_捺印指纹_警综人员编号"))
      t.column("NEW_NYZW_ASJXGRYBH", VarcharType, Limit(23), Nullable,Comment("修改_捺印指纹_案事件相关人员编号"))
      t.column("NEW_NYZW_ZZHWKBH", VarcharType, Limit(23), Nullable,Comment("修改_捺印指纹_指掌纹卡编号"))
      t.column("NEW_NYZW_ZZHWDM", VarcharType, Limit(2), Nullable,Comment("修改_捺印指纹_指掌位代码"))
    }

    createTable("LOG_GETMODFINGERDETAIL", Comment("获取修改后指掌纹信息----指纹系统调用现勘接口，获取修改后的指掌纹信息，无论是否成功，均在下表中记录信息")) { t =>
      t.column("PK_ID", VarcharType, Limit(32), NotNull, Comment("主键唯一主键，可用uuid"), PrimaryKey)
      t.column("USERNAME", VarcharType, Limit(20), Nullable, Comment("调用用户名我方用户名，默认AFIS_JZ"))
      t.column("YSXT_ASJBH", VarcharType, Limit(23), Nullable,Comment("原始系统_案事件编号"))
      t.column("XCKYBH", VarcharType, Limit(23), Nullable, Comment("现勘编号"))
      t.column("ASJFSDD_XZQHDM", IntegerType, Limit(12), Nullable, Comment("案事件发生地点_行政区划代码"))
      t.column("CALLTIME", TimestampType, Nullable, Comment("请求日期"))
      t.column("RETURNTIME", TimestampType, Nullable, Comment("获取日期"))
      t.column("SAVETIME", TimestampType, Nullable, Comment("入库日期"))
      t.column("SAVESTATUS", VarcharType, Limit(1), Nullable, Comment("入库状态"))
      t.column("FPTPATH", VarcharType, Nullable,Limit(500), Comment("fpt文件位置"))
      t.column("RETURNSTATUS", VarcharType, Limit(1), Nullable, Comment("接口方返回标记"))
      t.column("ERRORMSG", ClobType, Nullable, Comment("异常信息记录----记录exception"))
    }

    createTable("LOG_INTERFACESTATUS", Comment("金指服务状态----指纹系统各个接口服务状态")) { t =>
      t.column("PK_ID", VarcharType, Limit(32), NotNull, Comment("主键唯一主键，可用uuid"), PrimaryKey)
      t.column("INTERFACENAME", VarcharType, Limit(30), Nullable, Comment("调用接口程序名称"))
      t.column("SCHEDULE", VarcharType, Limit(40), Nullable, Comment("调用程序定时周期"))
      t.column("ASJFSDD_XZQHDM", IntegerType, Limit(12), Nullable, Comment("2101:沈阳  2102：大连 ……"))
      t.column("CALLTIME", TimestampType, Nullable, Comment("请求时间"))
    }

    createTable("LOG_HAIXINSERVICESTATUS", Comment("海鑫服务状态----海鑫各地市服务状态")) { t =>
      t.column("PK_ID", VarcharType, Limit(32), NotNull, Comment("主键唯一主键，可用uuid"), PrimaryKey)
      t.column("INTERFACEADDR", VarcharType, Limit(200), Nullable, Comment("调用接口程序名称"))
      t.column("STATUS", VarcharType, Limit(1), Nullable, Comment("调用程序定时周期"))
      t.column("ASJFSDD_XZQHDM", IntegerType, Limit(12), Nullable, Comment("2101:沈阳  2102：大连 ……"))
      t.column("CALLTIME", TimestampType, Nullable, Comment("最后一次调用服务时间"))
    }

    createTable("LOG_UPDATESTATUS", Comment("海鑫服务状态----海鑫各地市服务状态")) { t =>
      t.column("PK_ID", VarcharType, Limit(32), NotNull, Comment("主键唯一主键，可用uuid"), PrimaryKey)
      t.column("SVRADDR", VarcharType, Limit(20), Nullable, Comment("程序服务地址"))
      t.column("ASJFSDD_XZQHDM", IntegerType, Limit(12), Nullable, Comment("2101:沈阳  2102：大连 ……"))
    }

  }

  def down(): Unit = {}

}

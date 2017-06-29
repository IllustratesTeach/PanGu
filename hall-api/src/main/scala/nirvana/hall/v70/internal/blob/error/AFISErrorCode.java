package nirvana.hall.v70.internal.blob.error;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class AFISErrorCode
{
  public static final int UNKNOWN = 0;
  public static final int IO_OPEN = 1;
  public static final int IO_LOCK = 2;
  public static final int IO_READ = 3;
  public static final int IO_WRITE = 4;
  public static final int IO_SEEK = 5;
  public static final int IO_CLOSE = 6;
  public static final int IO_CREATE = 7;
  public static final int IO_UNLOCK = 8;
  public static final int IO_MAP = 9;
  public static final int IO_STAT = 10;
  public static final int IO_OTHEROP = 11;
  public static final int IO_COPY = 12;
  public static final int IO_CHMOD = 13;
  public static final int IO_RENAME = 14;
  public static final int IO_CHSIZE = 15;
  public static final int IO_NOSPACE = 16;
  public static final int IO_DEL = 17;
  public static final int IO_SIZETOOSHORT = 18;
  public static final int IO_SIZETOOLONG = 19;
  public static final int IO_FILE_NOTEXIST = 23;
  public static final int IO_FILE_EXIST = 24;
  public static final int IO_FILE_BADPOS = 25;
  public static final int IO_FILE_SYNC = 26;
  public static final int IO_FILE_FINDEMPTYPATH = 27;
  public static final int IO_FILE_FINDCOPYNAME = 28;
  public static final int IO_FILE_CHDIR = 29;
  public static final int MEM_ALLOC = 20;
  public static final int MEM_FREE = 21;
  public static final int MEM_OUTOF = 22;
  public static final int BAR_INVALID = 30;
  public static final int BAR_TOOLONG = 31;
  public static final int BAR_TOOSHORT = 32;
  public static final int BAR_EXIST = 33;
  public static final int BAR_LENNOTMATCH = 34;
  public static final int BAR_NOTEXIST = 35;
  public static final int BAR_NEEDMOREBUF = 36;
  public static final int BAR_IDNOTMATCH = 37;
  public static final int TENSTR_INVALID = 40;
  public static final int TENSTR_TOOLONG = 41;
  public static final int TENSTR_TOOSHORT = 42;
  public static final int PARAMETER_INVALID = 50;
  public static final int PARAMETER_ISNULL = 51;
  public static final int PARAMETER_NOENTRY = 52;
  public static final int MISC_NOEFFECT = 60;
  public static final int SOCK_OPEN = 71;
  public static final int SOCK_CLOSE = 72;
  public static final int SOCK_SEND = 73;
  public static final int SOCK_RECV = 74;
  public static final int SOCK_SELECT = 75;
  public static final int SOCK_BIND = 76;
  public static final int SOCK_LISTEN = 77;
  public static final int SOCK_ACCEPT = 78;
  public static final int SOCK_INIT = 79;
  public static final int SOCK_SOCKET = 80;
  public static final int SOCK_GETHOSTBYNAME = 81;
  public static final int SOCK_CONNECT = 82;
  public static final int SOCK_UNKNOWN = 83;
  public static final int SOCK_NAME2IP = 84;
  public static final int SOCK_SENDPARAM = 85;
  public static final int SOCK_RECVPARAM = 86;
  public static final int SOCK_WAITPARAM = 87;
  public static final int SYS_INVALIDFORMAT = 100;
  public static final int SYS_FATAL = 101;
  public static final int SYS_BUSY = 102;
  public static final int SYS_UNKNOWN = 103;
  public static final int SYS_OPCODE = 104;
  public static final int SYS_NOUSER = 105;
  public static final int SYS_MUCHANGED = 106;
  public static final int SYS_NOTSUPPPARAMATCH = 107;
  public static final int SYS_ABORTBYCLIENT = 108;
  public static final int SYS_REACHLIMIT = 109;
  public static final int SYS_LOWRESOURCE = 110;
  public static final int SYS_NOLICENSE = 111;
  public static final int SYS_SVRWILLSTOP = 112;
  public static final int SYS_SIZE = 113;
  public static final int SYS_NOTSUPPORT = 114;
  public static final int SYS_ALREADYRUN = 115;
  public static final int SYS_NOCALLBACK = 117;
  public static final int SYS_NOENTRY = 118;
  public static final int SYS_CLIENTERROR = 119;
  public static final int SYS_NOTEXIST = 120;
  public static final int SYS_UNFINISHED = 121;
  public static final int SYS_BADQUEUE = 122;
  public static final int SYS_NOTAPPLICABLE = 123;
  public static final int SYS_LENGTH = 124;
  public static final int SYS_IMGSIZE = 125;
  public static final int SYS_BADLICENSE = 126;
  public static final int SYS_GETCOMPUTERNAME = 127;
  public static final int SYS_PINYINNOTEXIST = 128;
  public static final int SYS_SVRNOTRUN = 129;
  public static final int SYS_BADSTATE = 130;
  public static final int SYS_EXIST = 131;
  public static final int SYS_NOTIMPLEMENTED = 145;
  public static final int SYS_VALUERANGE = 146;
  public static final int SYS_MAINPATHNOTSET = 147;
  public static final int SYS_MAINPATHNOTEXIST = 148;
  public static final int SYS_MAINPATHISNOTDIR = 149;
  public static final int SYS_READINIFILEERROR = 150;
  public static final int USER_NOACCOUNT = 1100;
  public static final int USER_NORIGHT = 1101;
  public static final int USER_PASSERR = 1102;
  public static final int USER_NOLOGIN = 1103;
  public static final int USER_EXIST = 1104;
  public static final int USER_PASSTOLONG = 1105;
  public static final int USER_NAMETOLONG = 1106;
  public static final int USER_DISABLED = 1107;
  public static final int USER_IPNOTMATCH = 1108;
  public static final int USER_LISTEMPTY = 1109;
  public static final int USER_ISGROUP = 1110;
  public static final int USER_DISABLELOCLOGIN = 1111;
  public static final int USER_DISABLETXLOGIN = 1112;
  public static final int USER_EXCEEDQUOTAS = 1113;
  public static final int USER_ROWFILTERNOTMATCH = 1114;
  public static final int USER_ISRESTRICTED = 1115;
  public static final int EXF_DECOMPRESS = 1500;
  public static final int EXF_COMPRESS = 1501;
  public static final int EXF_MNT_EXTRACT = 1502;
  public static final int EXF_NO_CPR_ENGINE = 1503;
  public static final int EXF_NO_DECPR_ENGINE = 1504;
  public static final int EXF_NO_EXF_ENGINE = 1505;
  public static final int EXF_PARAMETER = 1506;
  public static final int EGFSTD_HEAD_MAGIC = 10001;
  public static final int EGFSTD_HEAD_LENGTH = 10002;
  public static final int EGFSTD_HEAD_VERSION = 10003;
  public static final int EGFSTD_HEAD_BADENCODING = 10004;
  public static final int EGFSTD_NEED_FS = 10005;
  public static final int EGFSTD_FIELD_NAMELEN = 10006;
  public static final int EGFSTD_NEED_TAG = 10007;
  public static final int EGFSTD_ASCII_TOOLONG = 10008;
  public static final int EGFSTD_STR_LENGTH = 10009;
  public static final int EGFSTD_LENGTH_INVALID = 10010;
  public static final int EGFSTD_NAKED_HASZERO = 10011;
  public static final int EGFSTD_STRING_ENCODE = 10012;
  public static final int FPT_HEAD_FLAG = 11001;
  public static final int FPT_VERSION_ERR = 11002;
  public static final int FPT_LR_TYPE_ERR = 11003;
  public static final int FPT_STRING_ENCODING = 11004;
  public static final int FPT_BUFFER_OVER = 11005;
  public static final int FPT_NUMBER_FORMAT = 11006;
  public static final int FPT_NUMBER_PARSE = 11007;
  public static final int FPT_ADD_ERR = 11008;
  public static final int FPT_DELETE_ERR = 11009;
  public static final int FPT_PARAM_INVALID = 11010;
  public static final int FPT_FILE_NO_FOUND = 11011;
  public static final int FPT_PARSE_ERR = 11012;
  public static final int FPT_IO_ERR = 11013;
  public static final int FPT_METHOD_NO_SUPPOR = 11014;
  public static final int FPT_PARSE_DATE = 11015;
  public static final int FPT_FORMAT_INVALID = 11016;
  public static final int SQL_GENERAL_ERROR = 11100;
  public static final int RMT_SERVER_NO_FOUND = 12001;
  public static final int RMT_ROUTER_NO_FOUND = 12002;
  public static final int RMT_SUPPERIOR_SERVER_NO_FOUND = 12003;
  public static final int RMT_QUERY_NO_SERVER = 12004;
  public static final int RMT_STATUS_INVALID = 12005;
  public static final int RMT_DATATYPE_INVALID = 12006;
  public static final int RMT_DATABASE_NO_REMOTE = 12007;
  public static final int RMT_DATABASE_NO_DEFAULT = 12008;
  public static final int RMT_DIRECT_SERVER_INVALID = 12009;
  public static final int RMT_SUPPERIOR_SERVER_INVALID = 12010;
  public static final int NIST_INVALID_FORMAT = 13001;
  public static final int NIST_INVALID_DATATYPE = 13002;
  public static final int NIST_VERSION_NOTSUPPORT = 13003;
  public static final int NIST_INVALID_MANDATORY = 13004;
  public static final int NIST_INVALID_LRTYPE = 13005;
  public static final int NIST_NEED_TRANSINFOR = 13006;
  public static final int NIST_INVALID_IMAGESLC = 13007;
  public static final int NIST_MISS_IMAGELR = 13008;
  public static final int NIST_HASNO_TPCARD = 13009;
  public static final int NIST_HASNO_LPCASE = 13010;
  public static final int NIST_EMPTY_DOCUMNT = 13011;

  public static boolean hasDuplicateCode()
  {
    Class claz = AFISErrorCode.class;
    Field[] fields = claz.getDeclaredFields();
    int[] errorCode = new int[fields.length];
    int k = 0;
    for (Field f : fields)
    {
      if (!Modifier.isStatic(f.getModifiers())) return false; try
      {
        errorCode[(k++)] = f.getInt(null);
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }

    Arrays.sort(errorCode);
    for (k = 0; k < errorCode.length - 1; k++)
    {
      if (errorCode[k] == errorCode[(k + 1)]) return true;
    }
    return false;
  }

  public static void main(String[] args) {
    System.out.println("是否有重复代码:" + hasDuplicateCode());
  }

  public static boolean isNetworkError(int error)
  {
    return (error > 70) && (error < 100);
  }


  public static String toName(int error)
  {
    String name = "未知错误";

    /*switch (error)
    {
    case 1:
      name = "打开文件失败";
      break;
    case 2:
      name = "文件加锁失败";
      break;
    case 3:
      name = "读取文件失败";
      break;
    case 4:
      name = "写入文件失败";
      break;
    case 5:
      name = "定位文件失败";
      break;
    case 6:
      name = "关闭文件失败";
      break;
    case 7:
      name = "创建文件失败";
      break;
    case 8:
      name = "文件解锁失败";
      break;
    case 9:
      name = "文件映射失败";
      break;
    case 10:
      name = "取得文件属性失败";
      break;
    case 11:
      name = "未知操作";
      break;
    case 12:
      name = "拷贝文件失败";
      break;
    case 13:
      name = "修改文件属性失败";
      break;
    case 14:
      name = "改变文件名称失败";
      break;
    case 15:
      name = "改变文件大小失败";
      break;
    case 16:
      name = "文件中没有足够的空间容纳需要写入的内容";
      break;
    case 17:
      name = "删除文件失败";
      break;
    case 18:
      name = "文件长度太短";
      break;
    case 19:
      name = "文件长度太长";
      break;
    case 23:
      name = "文件不存在";
      break;
    case 24:
      name = "文件已经存在";
      break;
    case 25:
      name = "非法的文件偏移";
      break;
    case 26:
      name = "同步文件失败";
      break;
    case 27:
      name = "查找文件，但是给定目录为空";
      break;
    case 28:
      name = "拷贝文件名称参数错误";
      break;
    case 29:
      name = "改变工作目录发生错误";
      break;
    case 20:
      name = "申请内存失败";
      break;
    case 21:
      name = "释放内存失败";
      break;
    case 22:
      name = "内存超过范围";
      break;
    case 30:
      name = "条码格式或长度错误";
      break;
    case 31:
      name = "条码太长";
      break;
    case 32:
      name = "条码太短";
      break;
    case 33:
      name = "条码已经存在";
      break;
    case 34:
      name = "条码长度不匹配";
      break;
    case 35:
      name = "条码不存在";
      break;
    case 36:
      name = "把长整型转换为条码，给定缓冲区太短";
      break;
    case 37:
      name = "条码和ID不匹配";
      break;
    case 40:
      name = "无效的十指串";
      break;
    case 41:
      name = "十指串太长";
      break;
    case 42:
      name = "十指串太短";
      break;
    case 50:
      name = "无效参数";
      break;
    case 51:
      name = "参数不能为空";
      break;
    case 52:
      name = "在初始参数配置文件中没有相应的参数设置";
      break;
    case 60:
      name = "无效或没有作用";
      break;
    case 71:
      name = "打开网络端口失败";
      break;
    case 72:
      name = "关闭网络端口失败";
      break;
    case 73:
      name = "发送数据失败";
      break;
    case 74:
      name = "接收数据失败";
      break;
    case 75:
      name = "选择网络端口失败(Select)";
      break;
    case 76:
      name = "绑定网络端口失败";
      break;
    case 77:
      name = "监听网络端口失败";
      break;
    case 78:
      name = "接收网络端口失败";
      break;
    case 79:
      name = "初始化网络失败";
      break;
    case 80:
      name = "一般网络端口错误";
      break;
    case 81:
      name = "获取IP地址失败";
      break;
    case 82:
      name = "连接网络端口失败";
      break;
    case 83:
      name = "未知网络错误";
      break;
    case 84:
      name = "name2ip失败";
      break;
    case 85:
      name = "设置网络发送参数失败";
      break;
    case 86:
      name = "设置网络接收参数失败";
      break;
    case 87:
      name = "设置网络等待参数十遍";
      break;
    case 100:
      name = "无效格式";
      break;
    case 101:
      name = "未知致命错误";
      break;
    case 102:
      name = "系统忙";
      break;
    case 103:
      name = "未知系统错误";
      break;
    case 104:
      name = "无效操作命令";
      break;
    case 105:
      name = "用户不存在";
      break;
    case 106:
      name = "并行比对器已被改变";
      break;
    case 107:
      name = "不支持并行比对";
      break;
    case 108:
      name = "操作被客户终止";
      break;
    case 109:
      name = "同时登录请求过多";
      break;
    case 110:
      name = "资源不足";
      break;
    case 111:
      name = "没有软件授权许可";
      break;
    case 112:
      name = "GAFIS服务器即将被终止运行";
      break;
    case 113:
      name = "长度不匹配";
      break;
    case 114:
      name = "不被客户端支持";
      break;
    case 115:
      name = "已经运行";
      break;
    case 117:
      name = "不支持回调函数";
      break;
    case 118:
      name = "没有此数据库或对应的项";
      break;
    case 119:
      name = "客户端发生错误";
      break;
    case 120:
      name = "不存在";
      break;
    case 121:
      name = "未完成";
      break;
    case 122:
      name = "错误的队列类型";
      break;
    case 123:
      name = "请求在此情形下不适用";
      break;
    case 124:
      name = "长度过长或过短";
      break;
    case 125:
      name = "错误的未压缩图象长度";
      break;
    case 126:
      name = "错误的授权（软件许可）";
      break;
    case 127:
      name = "取得计算机名称错误";
      break;
    case 128:
      name = "对应汉字的拼音不存在";
      break;
    case 129:
      name = "服务器没有运行";
      break;
    case 130:
      name = "错误的状态类型";
      break;
    case 131:
      name = "已经存在";
      break;
    case 145:
      name = "此功能没有实现";
      break;
    case 146:
      name = "无效的值区间";
      break;
    case 147:
      name = "未设置系统主路径";
      break;
    case 148:
      name = "系统主路径不存在";
      break;
    case 149:
      name = "系统主路径不是目录";
      break;
    case 150:
      name = "读取初始化文件失败";
      break;
    case 1100:
      name = "用户不存在";
      break;
    case 1101:
      name = "用户没有权限";
      break;
    case 1102:
      name = "用户密码错误";
      break;
    case 1103:
      name = "用户未登录";
      break;
    case 1104:
      name = "用户已经存在";
      break;
    case 1105:
      name = "用户密码太长";
      break;
    case 1106:
      name = "用户名称太长";
      break;
    case 1107:
      name = "用户被禁止";
      break;
    case 1108:
      name = "用户IP地址不匹配";
      break;
    case 1109:
      name = "用户列表为空";
      break;
    case 1110:
      name = "用户是个组";
      break;
    case 1111:
      name = "禁止用户本地登录";
      break;
    case 1112:
      name = "禁止用户远程登录";
      break;
    case 1113:
      name = "超出配额限制";
      break;
    case 1114:
      name = "不匹配行过滤条件";
      break;
    case 1115:
      name = "用户登录受限，可能超过登录次数、IP受限或者登录时间受限";
      break;
    case 1500:
      name = "解压缩图像失败";
      break;
    case 1501:
      name = "压缩图像失败";
      break;
    case 1502:
      name = "提取特征失败";
      break;
    case 1503:
      name = "未找到图像压缩引擎";
      break;
    case 1504:
      name = "未找到图像解压缩引擎";
      break;
    case 1505:
      name = "未找到特征提取引擎";
      break;
    case 1506:
      name = "图像处理参数错误";
      break;
    case 10001:
    case 10002:
    case 10003:
    case 10004:
    case 10005:
    case 10006:
    case 10007:
    case 10008:
    case 10009:
    case 10010:
    case 10011:
    case 10012:
      break;
    case 11001:
      name = "FPT文件无效";
      break;
    case 11002:
      name = "FPT文件版本错误";
      break;
    case 11003:
      name = "FPT文件中逻辑记录类型错误";
      break;
    case 11004:
      name = "FPT文件字符串编码错误";
      break;
    case 11005:
      name = "FPT文件缓冲区溢出";
      break;
    case 11006:
      name = "FPT文件中数字格式不正确";
      break;
    case 11007:
      name = "FPT文件中解析数字失败";
      break;
    case 11008:
      name = "FPT文件添加逻辑记录失败";
      break;
    case 11009:
      name = "FPT文件删除逻辑记录事变";
      break;
    case 11010:
      name = "FPT文件参数无效";
      break;
    case 11011:
      name = "FPT文件不存在";
      break;
    case 11012:
      name = "FPT文件解析失败";
      break;
    case 11013:
      name = "FPT文件IO错误";
      break;
    case 11014:
      name = "";
      break;
    case 11015:
      name = "";
      break;
    case 11016:
      name = "";
      break;
    case 11100:
      name = "一般SQL错误";
      break;
    case 12001:
      name = "未找到服务器";
      break;
    case 12002:
      name = "未找到路由表";
      break;
    case 12003:
      name = "未找到直接上级服务器";
      break;
    case 12004:
      name = "远程查询没有配置目的服务器";
      break;
    case 12005:
      name = "无效状态";
      break;
    case 12006:
      name = "无效的数据类型";
      break;
    case 12007:
      name = "没有配置远程数据库";
      break;
    case 12008:
      name = "没有缺省的数据库";
      break;
    case 12009:
      name = "不是可以直接连接的服务器";
      break;
    case 12010:
      name = "直接上级服务器无效";
      break;
    case 13001:
      name = "无效的NIST文件";
      break;
    case 13002:
      name = "无效的NIST数据类型";
      break;
    case 13003:
      name = "不支持的NIST版本号";
      break;
    case 13004:
      name = "NIST缺少必填项或者必填项无效";
      break;
    case 13005:
      name = "无效的NIST逻辑记录类型";
      break;
    case 13011:
      name = "NIST空文档";
      break;
    case 13006:
      name = "NIST文件需要交易信息记录";
      break;
    case 13007:
      name = "NIST无效的图像分辨率";
      break;
    case 13008:
      name = "NIST缺少相应的图像记录";
      break;
    case 13009:
      name = "NIST文件中不存在捺印卡片的相应记录";
      break;
    case 13010:
      name = "NIST文件中不存在现场案件的相应记录";
    }
*/
    return name + "[" + error + "]";
  }

}
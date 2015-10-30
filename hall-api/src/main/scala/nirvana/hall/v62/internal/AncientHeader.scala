package nirvana.hall.v62.internal

import nirvana.hall.v62.annotations.Length

/**
 * request ancient application server
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-29
 */
class RequestHeader extends AncientData{
  var cbSize                = 192  //UCHAR	4	结构体大小  192
  var nMajorVer             = 6.toShort  //UCHAR	2	主版本号    6
  var nMinorVer             = 1.toShort   //UCHAR	2	副版本号    1
  @Length(8)
  var szMagicStr            = "G@xucg$"  //char	8	必须是      G@xucg$
  @Length(16)
  var szUserName:String     = _  //char	16	用户账号
  @Length(16)
  var szUserPass:String     = _  //char	16	用户密码
  var tLoginTime            = 0  //UCHAR	4	登陆时间
  var nOpClass              = 0.toShort   //UCHAR	2	操作类型
  var nOpCode               = 0.toShort  //UCHAR	2	操作码 482
  var nLoginID              = 0  //UCHAR	4	登陆id
  var nSeqNo                = 0  //UCHAR	4	序号
  @Length(16)
  var nIP:String            = _  //UCHAR	16	IP
  var bIsRmtUser:Byte       = 0   //UCHAR	1	是否远程用户
  var bIsIPV6:Byte          = 0   //UCHAR	1	是否IPV6
  var bnRes                 = 0.toShort   //UCHAR	2	保留
  var nDataLen              = 0   //UCHAR	4	请求头后跟的数据长度
  var nOption               = 0  //UCHAR	4	选项
  var nDBID                 = 0.toShort //UCHAR	2	dbid
  var nTableID              = 0.toShort   //UCHAR	2	tableid
  var nRetVal               = 0L  //UCHAR	8	客户端返回值
  @Length(24)
  var bnRes2:Array[Byte]   = _ //UCHAR	24	保留
  @Length(64)
  var bnData:Array[Byte]   = _  //UCHAR	64	附加数据
}
class ResponseHeader extends AncientData{
  var cbSize                = 96   //UCHAR	4	结构体大小 96
  var nMajorVer             = 6.toShort  //UCHAR	2	主版本号   6
  var nMinorVer             = 1.toShort  //UCHAR	2	副版本号   1
  @Length(8)
  var szMagicStr            = "G@xucg$"  //char	8	必须是      G@xucg$
  var nDataLen              = 0  //UCHAR	4	后面的数据长度
  var nReturnValue          = 0  //UCHAR	8	服务端返回值 1  成功 -1  失败,服务器仅仅用了前面4个字节
  var nReturnValue_suffix   = 0 //reserved ,because server only use 4 byte
  var bnRes                 = 0  //UCHAR	4	Reserved
  @Length(64)
  var bnData                = new Array[Byte](64)  //UCHAR	64	附加数据
}

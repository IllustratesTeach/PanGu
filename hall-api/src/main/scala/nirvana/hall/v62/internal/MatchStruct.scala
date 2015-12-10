package nirvana.hall.v62.internal

import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask
import nirvana.hall.protocol.matcher.MatchTaskQueryProto.MatchTask.MatchConfig
import nirvana.hall.protocol.matcher.NirvanaTypeDefinition.MatchType
import nirvana.hall.v62.config.DatabaseTable
import nirvana.hall.c.services.gbaselib.gitempkg.{GBASE_ITEMPKG_PKGHEADSTRUCT, GBASE_ITEMPKG_ITEMHEADSTRUCT}
import nirvana.hall.c.services.gloclib.gadbprop.GADBIDSTRUCT
import nirvana.hall.c.services.gloclib.gqrycond.{GAFIS_XGWQRYCOND, GAFIS_QRYPARAM}
import nirvana.hall.c.services.gloclib.{glocdef, gaqryque}
import nirvana.hall.c.services.gloclib.gaqryque.GAQUERYSTRUCT
import nirvana.hall.c.services.gloclib.glocdef.GAFISMICSTRUCT
import nirvana.hall.v62.services.AncientEnum
import org.jboss.netty.buffer.ChannelBuffers
import scala.collection.JavaConversions._

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-09
 */
object MatchStruct {
  final val GAFIS_KEYLIST_GetName = "KeyList"
  final val GAFIS_QRYPARAM_GetName = "QryParam"
  final val GAFIS_QRYFILTER_GetName = "QryFilter"
  final val GAFIS_CANDKEYFILTER_GetName = "CandKeyFilter"
  final val GAFIS_TEXTSQL_GetName = "TextSql"

  /**
   * convert protobuf MatchTask to v62's GAQUERYSTRUCT
   * @param task match task
   * @param destDb dest database
   * @return V62's GAQUERYSTRUCT
   */
  def convertProtoToGAQUERYSTRUCT(task:MatchTask,destDb:DatabaseTable): GAQUERYSTRUCT ={
    val queryStruct = new GAQUERYSTRUCT
    //fill simple data
      val matchType = task.getMatchType match{
      case MatchType.FINGER_TT | MatchType.PALM_TT =>
        AncientEnum.MatchType.TT
      case MatchType.FINGER_TL | MatchType.PALM_TL =>
        AncientEnum.MatchType.TL
      case MatchType.FINGER_LT | MatchType.PALM_LT =>
        AncientEnum.MatchType.LT
      case MatchType.FINGER_LL | MatchType.PALM_LL =>
        AncientEnum.MatchType.LL
    }
    val isPalm = task.getMatchType.name().startsWith("PALM")

    queryStruct.stSimpQry.nQueryType =matchType.asInstanceOf[Byte]
    queryStruct.stSimpQry.nPriority = task.getPriority.asInstanceOf[Byte]
    //queryStruct.stSimpQry.nFlag = (gaqryque.GAQRY_FLAG_USEFINGER | gaqryque.GAQRY_FLAG_USETEXT).asInstanceOf[Byte]
    if(isPalm)
      queryStruct.stSimpQry.nFlag = (gaqryque.GAQRY_FLAG_USEPALM).asInstanceOf[Byte]
    else
      queryStruct.stSimpQry.nFlag = (gaqryque.GAQRY_FLAG_USEFINGER ).asInstanceOf[Byte]

    //queryStruct.stSimpQry.stSrcDB.nDBID = task.options.srcDb.dbId.asInstanceOf[Short]
    //queryStruct.stSimpQry.stSrcDB.nTableID= task.options.srcDb.tableId.asInstanceOf[Short]

    queryStruct.stSimpQry.stDestDB = Array(new GADBIDSTRUCT)
    queryStruct.stSimpQry.nDestDBCount = 1
    queryStruct.stSimpQry.stDestDB.apply(0).nDBID = destDb.dbId.asInstanceOf[Short]
    queryStruct.stSimpQry.stDestDB.apply(0).nTableID= destDb.tableId.asInstanceOf[Short]

    if(task.hasTData){
      val mics = task.getTData.getMinutiaDataList.map{minutiaData=>
        val mic = new GAFISMICSTRUCT
        mic.pstMnt_Data = minutiaData.getMinutia.toByteArray
        mic.nMntLen = mic.pstMnt_Data.length
        mic.nItemFlag = glocdef.GAMIC_ITEMFLAG_MNT.asInstanceOf[Byte]
        mic.nItemData = autoFixPos(minutiaData.getPos).asInstanceOf[Byte]
        mic.nItemType = glocdef.GAMIC_ITEMTYPE_FINGER.asInstanceOf[Byte]

        mic
      }
      queryStruct.pstMIC_Data = mics.toArray
      queryStruct.nMICCount = queryStruct.pstMIC_Data.length
    }
    if(task.hasLData){
      val minutiaData = task.getLData
      val mic = new GAFISMICSTRUCT
      mic.pstMnt_Data = minutiaData.getMinutia.toByteArray
      mic.nMntLen = mic.pstMnt_Data.length
      mic.nItemFlag = glocdef.GAMIC_ITEMFLAG_MNT.asInstanceOf[Byte]
      //TODO 确认现场是否发送意
      //mic.nItemData = autoFixPos(minutiaData.).asInstanceOf[Byte]
      mic.nItemType = glocdef.GAMIC_ITEMTYPE_FINGER.asInstanceOf[Byte]
      queryStruct.pstMIC_Data = Array(mic)
      queryStruct.nMICCount = queryStruct.pstMIC_Data.length
    }

    //设置比对参数
    val item = new GAFIS_QRYPARAM
    convertProtoConfigToC(item.stXgw,task.getConfig)
    val itemDataLength = item.getDataSize
    val itemHead = new GBASE_ITEMPKG_ITEMHEADSTRUCT
    itemHead.szItemName = GAFIS_QRYPARAM_GetName
    itemHead.nItemLen = itemDataLength

    val itemPackage = new GBASE_ITEMPKG_PKGHEADSTRUCT
    itemPackage.nDataLen = itemPackage.getDataSize  + itemHead.getDataSize + itemHead.nItemLen
    itemPackage.nBufSize = itemPackage.nDataLen

    val buffer = ChannelBuffers.buffer(itemPackage.nDataLen)
    itemPackage.writeToStreamWriter(buffer)
    itemHead.writeToStreamWriter(buffer)
    item.writeToStreamWriter(buffer)

    queryStruct.pstQryCond_Data = buffer.array()
    queryStruct.nQryCondLen = queryStruct.pstQryCond_Data.length
    queryStruct.nItemFlagA = gaqryque.GAIFA_FLAG.asInstanceOf[Byte]
    queryStruct.stSimpQry.szKeyID=task.getMatchId

    queryStruct

  }
  private def convertProtoConfigToC(matchOptions: GAFIS_XGWQRYCOND, matchConfig: MatchConfig) {
    if (matchConfig.hasMinutia)
      matchOptions.nMntMatchType = matchConfig.getMinutia.asInstanceOf[Byte]
    if (matchConfig.hasDistore)
      matchOptions.nDistore = matchConfig.getDistore.asInstanceOf[Byte]
    if (matchConfig.hasLocStructure)
      matchOptions.nLocStructureMatchType = matchConfig.getLocStructure.asInstanceOf[Byte]
    if (matchConfig.hasMaskEnhFeat)
      matchOptions.bDisableEnhFeat = matchConfig.getMaskEnhFeat.asInstanceOf[Byte]
    if (matchConfig.hasMorphAccuUse)
      matchOptions.bMorphAccuUse = matchConfig.getMorphAccuUse.asInstanceOf[Byte]
    if (matchConfig.hasFullMatchOn)
      matchOptions.bFullMatchOn = matchConfig.getFullMatchOn.asInstanceOf[Byte]
    if (matchConfig.hasScale0)
      matchOptions.nScale0 = matchConfig.getScale0.asInstanceOf[Byte]
    if (matchConfig.hasScale1)
      matchOptions.nScale1 = matchConfig.getScale1.asInstanceOf[Byte]
  }



  /**
   * 自动修正指纹的位置信息
   * 右手拇指是 1 << 0 ,左手小指是 1 << 9
   * @param pos 包含了位置信息的整数
   * @return 指位信息,返回情况是 1-22
   */
  def autoFixPos(pos: Int): Int = {
    var tmpPos = pos
    if (tmpPos <= 0 || tmpPos > maxPos)
      throw new IllegalArgumentException("pos " + Integer.toBinaryString(pos) + " is invalid")

    val isFlatPos = tmpPos > flatFingerPos //加入是平面指纹的指位信息
    if (isFlatPos)
      tmpPos >>= 10

    var index = 0
    index = BIT_LIST(tmpPos & 0x000000ff)
    if (index == 0)
      index = BIT_LIST((tmpPos >>> 8) & 0x000000ff) + 8

    if (index > 12) //索引位置不应该 > 12 ，这样支持pos的范围是 1-22
      throw new IllegalArgumentException("pos " + Integer.toBinaryString(pos) + " is invalid")

    if (isFlatPos)
      index += 10

    index
  }
  /**
   * 记录每个数出现1的位置信息
   * 譬如：5，出现1的位是第一位和第三位，那么对应的就是 0x31
   */
  private final val BIT_LIST = Array(
    0x0, 0x1, 0x2, 0x21, 0x3, 0x31, 0x32, 0x321, 0x4, 0x41, 0x42, 0x421, 0x43,
    0x431, 0x432, 0x4321, 0x5, 0x51, 0x52, 0x521, 0x53, 0x531, 0x532, 0x5321,
    0x54, 0x541, 0x542, 0x5421, 0x543, 0x5431, 0x5432, 0x54321, 0x6, 0x61, 0x62,
    0x621, 0x63, 0x631, 0x632, 0x6321, 0x64, 0x641, 0x642, 0x6421, 0x643, 0x6431,
    0x6432, 0x64321, 0x65, 0x651, 0x652, 0x6521, 0x653, 0x6531, 0x6532, 0x65321,
    0x654, 0x6541, 0x6542, 0x65421, 0x6543, 0x65431, 0x65432, 0x654321, 0x7,
    0x71, 0x72, 0x721, 0x73, 0x731, 0x732, 0x7321, 0x74, 0x741, 0x742, 0x7421,
    0x743, 0x7431, 0x7432, 0x74321, 0x75, 0x751, 0x752, 0x7521, 0x753, 0x7531,
    0x7532, 0x75321, 0x754, 0x7541, 0x7542, 0x75421, 0x7543, 0x75431, 0x75432,
    0x754321, 0x76, 0x761, 0x762, 0x7621, 0x763, 0x7631, 0x7632, 0x76321, 0x764,
    0x7641, 0x7642, 0x76421, 0x7643, 0x76431, 0x76432, 0x764321, 0x765, 0x7651,
    0x7652, 0x76521, 0x7653, 0x76531, 0x76532, 0x765321, 0x7654, 0x76541, 0x76542,
    0x765421, 0x76543, 0x765431, 0x765432, 0x7654321, 0x8, 0x81, 0x82, 0x821,
    0x83, 0x831, 0x832, 0x8321, 0x84, 0x841, 0x842, 0x8421, 0x843, 0x8431, 0x8432,
    0x84321, 0x85, 0x851, 0x852, 0x8521, 0x853, 0x8531, 0x8532, 0x85321, 0x854,
    0x8541, 0x8542, 0x85421, 0x8543, 0x85431, 0x85432, 0x854321, 0x86, 0x861,
    0x862, 0x8621, 0x863, 0x8631, 0x8632, 0x86321, 0x864, 0x8641, 0x8642, 0x86421,
    0x8643, 0x86431, 0x86432, 0x864321, 0x865, 0x8651, 0x8652, 0x86521, 0x8653,
    0x86531, 0x86532, 0x865321, 0x8654, 0x86541, 0x86542, 0x865421, 0x86543,
    0x865431, 0x865432, 0x8654321, 0x87, 0x871, 0x872, 0x8721, 0x873, 0x8731,
    0x8732, 0x87321, 0x874, 0x8741, 0x8742, 0x87421, 0x8743, 0x87431, 0x87432,
    0x874321, 0x875, 0x8751, 0x8752, 0x87521, 0x8753, 0x87531, 0x87532, 0x875321,
    0x8754, 0x87541, 0x87542, 0x875421, 0x87543, 0x875431, 0x875432, 0x8754321,
    0x876, 0x8761, 0x8762, 0x87621, 0x8763, 0x87631, 0x87632, 0x876321, 0x8764,
    0x87641, 0x87642, 0x876421, 0x87643, 0x876431, 0x876432, 0x8764321, 0x8765,
    0x87651, 0x87652, 0x876521, 0x87653, 0x876531, 0x876532, 0x8765321, 0x87654,
    0x876541, 0x876542, 0x8765421, 0x876543, 0x8765431, 0x8765432, 0x87654321
  )
  private val flatFingerPos: Int = (1 << 9) + 1
  private val maxPos: Int = (1 << 21) + 1
}

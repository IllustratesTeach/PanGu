package nirvana.hall.v62.internal

import com.google.protobuf.ProtocolStringList
import nirvana.hall.protocol.v62.FPTProto.Case
import nirvana.hall.v62.AncientConstants
import nirvana.hall.v62.annotations.{IgnoreTransfer, Length}
import nirvana.hall.v62.services.AncientData
import scala.collection.JavaConversions._
import scala.collection.mutable

/**
 * case struct
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-03
 */
object CaseStruct {
  //convert protocol string list as gafis GAKEYSTRUCT
  private def convertAsKeyArray(stringList:ProtocolStringList): Array[GAKEYSTRUCT] ={
    stringList.map{id=>
      val key = new GAKEYSTRUCT
      key.key = id
      key
    }.toArray
  }
  private[internal] def appendTextStruct(buffer:mutable.Buffer[tagGATEXTITEMSTRUCT],name:String,value:String):Unit = {
    if(value != null && value.length > 0) {
      val textStruct = new tagGATEXTITEMSTRUCT()
      textStruct.bIsPointer = 1
      textStruct.szItemName = name
      //convert as GBK encoding,because 6.2 need gbk encoding
      textStruct.textContent = value.getBytes(AncientConstants.GBK_ENCODING)
      textStruct.nItemLen = textStruct.textContent.length

      buffer += textStruct
    }

  }
  def convertProtobuf2Case(protoCase:Case):tagGCASEINFOSTRUCT = {
    val gafisCase = new tagGCASEINFOSTRUCT
    gafisCase.nItemFlag = (1 + 4 + 16).asInstanceOf[Byte]
    //GAFIS里面没有'A',这里去掉前缀
    gafisCase.szCaseID = protoCase.getStrCaseID
    if(gafisCase.szCaseID.charAt(0) == 'A')
      gafisCase.szCaseID = gafisCase.szCaseID.substring(1)

    gafisCase.pstFingerIdData = convertAsKeyArray(protoCase.getStrFingerIDList)
    gafisCase.nFingerCount = gafisCase.pstFingerIdData.length.asInstanceOf[Short]

    gafisCase.pstPalmIdData = convertAsKeyArray(protoCase.getStrPalmIDList)
    gafisCase.nPalmCount = gafisCase.pstPalmIdData.length.asInstanceOf[Short]


    if (protoCase.hasText) {
      val text = protoCase.getText
      val buffer = mutable.Buffer[tagGATEXTITEMSTRUCT]()

      appendTextStruct(buffer, "CaseClass1Code", text.getStrCaseType1)
      appendTextStruct(buffer, "CaseClass2Code", text.getStrCaseType2)
      appendTextStruct(buffer, "CaseClass3Code", text.getStrCaseType3)
      appendTextStruct(buffer, "SuspiciousArea1Code", text.getStrSuspArea1Code)
      appendTextStruct(buffer, "SuspiciousArea2Code", text.getStrSuspArea2Code)
      appendTextStruct(buffer, "SuspiciousArea3Code", text.getStrSuspArea3Code)
      appendTextStruct(buffer, "CaseOccurDate", text.getStrCaseOccurDate)
      appendTextStruct(buffer, "CaseOccurPlaceCode", text.getStrCaseOccurPlaceCode)
      appendTextStruct(buffer, "CaseOccurPlaceTail", text.getStrCaseOccurPlace)

      if(text.hasNSuperviseLevel)
        appendTextStruct(buffer, "SuperviseLevel", text.getNSuperviseLevel.toString)

      appendTextStruct(buffer, "ExtractUnitCode", text.getStrExtractUnitCode)
      appendTextStruct(buffer, "ExtractUnitNameTail", text.getStrExtractUnitName)
      appendTextStruct(buffer, "Extractor1", text.getStrExtractor)
      appendTextStruct(buffer, "ExtractDate", text.getStrExtractDate)
      appendTextStruct(buffer, "IllicitMoney", text.getStrMoneyLost)
      appendTextStruct(buffer, "Premium", text.getStrPremium)
      if(text.hasBPersonKilled)
        appendTextStruct(buffer, "HasPersonKilled", if (text.getBPersonKilled()) "1" else "0")
      appendTextStruct(buffer, "Comment", text.getStrComment)
      if(text.hasNCaseState)
        appendTextStruct(buffer, "CaseState", text.getNCaseState.toString)

      if(text.hasNXieChaState)
        appendTextStruct(buffer, "XieChaFlag", text.getNCaseState.toString)
      if(text.hasNCancelFlag)
        appendTextStruct(buffer, "CancelFlag", text.getNCancelFlag.toString)

      appendTextStruct(buffer, "XieChaDate", text.getStrXieChaDate)
      appendTextStruct(buffer, "XieChaRequestUnitName", text.getStrXieChaRequestUnitName)
      appendTextStruct(buffer, "XieChaRequestUnitCode", text.getStrXieChaRequestUnitCode)

      gafisCase.pstTextData = buffer.toArray
      gafisCase.nTextItemCount = gafisCase.pstTextData.length.asInstanceOf[Short]
    }
    gafisCase
  }
}
// case info structure
class tagGCASEINFOSTRUCT  extends AncientData {
  var cbSize:Int = _ ;		// size of this structure
  var nGroupID:Int = _ ;	// group id
  @Length(32)
  var szCaseID:String = _	// case id
  var nFingerCount:Short = _ ;	// finger count in this case
  var nPalmCount:Short = _ ;		// palm count in this case
  var nTextItemCount:Short = _ ;	// text item count bytes
  var nItemFlag:Byte = _			// which item in this structure is used, GCIS_ITEMFLAG_XXX
  var nItemFlagEx:Byte = _		// GCIS_ITEMFLAGEX_XXX
  // to here is 48 bytes
  var pstFingerID:Long = _;	// pointer to finger id
  var pstPalmID:Long = _ ;		// pointer to palm id
  var pstText:Long = _;	// pointer to text
  @IgnoreTransfer
  var pstFingerIdData:Array[GAKEYSTRUCT] = _
  @IgnoreTransfer
  var pstPalmIdData:Array[GAKEYSTRUCT] = _
  @IgnoreTransfer
  var pstTextData:Array[tagGATEXTITEMSTRUCT]= _;	// pointer to text

  var	bFingerIDCanBeFreed:Byte = _
  var	bPalmIDCanBeFreed:Byte = _
  var	bTextCanBeFreed:Byte = _
  var	bIsBroken:Byte = _			// whether the case has been broken.
  var	bHasPersonKilled:Byte = _
  var	nPersonKilledCnt:Byte = _	// how many people killed.[0 255]
  var	bIsLTBroken:Byte = _
  var	bExtraInfoCanBeFreed:Byte = _	// 2006.08.04
  @Length(16)
  var	bnUUID:String = _		// UUID, readonly
  @Length(32)
  var	szMISCaseID:String = _
  var	nFingerIDLen:Int = _ 	// temp use
  var	nPalmIDLen:Int = _ 		// temp use
  @Length(6)
  var	nSID:Array[Byte] = _ 		// sid, readonly
  @Length(2)
  var bnRes4:Array[Byte] = _ ;			// ensure sid occupy 8 byte.
  //	AFISDateTime	tBrokenDate;
  var pstExtraInfo:Long = _
  @IgnoreTransfer
  var pstExtraInfoData:tagGAFIS_protoCaseEXTRAINFO =  _
  @Length(16)
  var szBrokenUser:String = _		// broken user. store actual user name.
  @Length(16)
  var szReChecker:String = _
  @Length(16)
  var szBrokenUnitCode:String = _	// broken unit code.
  var szGroupCode:Long = _;			// group code.
  @Length(9)
  var szBrokenDate:String = _
  var 	nItemFlag3:Byte= _;				// GCIS_ITEMFLAG3_XXX
  var 	nExtraInfoLen:Short = _ 		// length of extra info.
  @Length(4)
  var 	bnRes7:Array[Byte] = _
  var tCreateDateTime:GafisDateTime = _
  var tUpdateDateTime:GafisDateTime = _
  @Length(16)
  var bnRes3:Array[Byte] = _
} // size of this structure is 256 bytes

class GAKEYSTRUCT extends AncientData {
  @Length(32)
  var key:String = _
}
// the data object for latent is simpler than tenprint
// there are only finger and palm data's

// for store face, voice and etc list.
class tagGAFIS_CASEITEMENTRY  extends AncientData {
  var	cbSize:Int = _
  var	bnRes:Int = _
  @Length(32)
  var	szKey:String = _
  @Length(16)
  var	szUserName:String = _
  @Length(16)
  var	szUnitCode:String = _
  var tDateTime:GafisDateTime = _
  // to here is 80 bytes long.
  var	nKeyType:Byte = _ 	// LPCARDTYPE_xxxx
  @Length(47)
  var	bnRes2:Array[Byte] = _
} ;	// 128 bytes long.

// the GCASEINFOSTRUCT can not hold enough data, we need expand
// it's size, but for compatibility reason, we add an extra
// structure to hold other info. 2006.08.04
class tagGAFIS_protoCaseEXTRAINFO  extends AncientData {
  var	cbSize:Int = _
  var	bnRes:Int = _
  @Length(32)
  var	szCaseGroupID:String = _	// case group id.
  @Length(8)
  var	bnRes1:Array[Byte]= _
  // to here is 48 bytes long.
  var pstItemEntry:Long = _ 	// for store item key list(except lp finger and palm).
  @IgnoreTransfer
  var pstItemEntryData:tagGAFIS_CASEITEMENTRY= _
  @Length(24)
  var	bnRes2:Array[Byte] = _
  // to here is 48+32=80 bytes long. above buffer should be used for store pointer.
  @Length(16)
  var	szOrgScanner:String = _
  @Length(16)
  var	szOrgScanUnitCode:String = _
  var	szOrgAFISType:Int = _ 		// fill in AFIS type code.
  var	nItemFlag:Byte = _;				// protoCaseEXTRAINFO_ITEMFLAG_XXX
  @Length(3)
  var	bnRes3:Array[Byte] = _
  var	nItemSize:Int = _ 		// length of pstItemEntry(count*sizeof(GAFIS_CASEITEMENTRY))
  // ease for loading and saving.
  @Length(4)
  var	bnRes4:Array[Byte] = _;
  // to here is 128 bytes long.
  var stFpx = new tagGAFIS_FPX_STATUS()	;	// 128 bytes long.
  ///
  //var	szMISConnectCaseID[32];	// mis connect id. has another szMISCaseID
  @Length(256)
  var	bnResx:Array[Byte]= _
} ;	// size is 512 bytes long.



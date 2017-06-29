package nirvana.hall.v62.internal.c.gloclib

import monad.support.services.LoggerSupport
import nirvana.hall.api.internal.DateConverter
import nirvana.hall.c.services.gloclib.galoclog.GAFIS_VERIFYLOGSTRUCT
import nirvana.hall.protocol.api.FPTProto.MatchRelationInfo

/**
  * Created by yuchen on 2017/6/21.
  */
object ganetlogverifyConverter extends LoggerSupport{


  def convertProtoBuf2GAFIS_VERIFYLOGSTRUCT(matchRelationInfo: MatchRelationInfo): GAFIS_VERIFYLOGSTRUCT= {

    val gAFIS_VERIFYLOGSTRUCT = new GAFIS_VERIFYLOGSTRUCT
    gAFIS_VERIFYLOGSTRUCT.szBreakID = matchRelationInfo.getBreakid
    gAFIS_VERIFYLOGSTRUCT.szQueryTaskID = matchRelationInfo.getQueryTaskId
    gAFIS_VERIFYLOGSTRUCT.szSrcKey = matchRelationInfo.getSrckey
    gAFIS_VERIFYLOGSTRUCT.szDestKey = matchRelationInfo.getDestkey
    gAFIS_VERIFYLOGSTRUCT.nScore = matchRelationInfo.getScore.toShort
    gAFIS_VERIFYLOGSTRUCT.nFirstRankScore = matchRelationInfo.getFirstRankScore.toShort
    gAFIS_VERIFYLOGSTRUCT.nRank = matchRelationInfo.getRank.toShort
    gAFIS_VERIFYLOGSTRUCT.nFg = matchRelationInfo.getFg.getNumber.asInstanceOf[Byte]
    gAFIS_VERIFYLOGSTRUCT.nHitPoss = matchRelationInfo.getHitposs.asInstanceOf[Byte]
    gAFIS_VERIFYLOGSTRUCT.bIsRmtSearched = matchRelationInfo.getIsCrimeCaptured.asInstanceOf[Byte]
    gAFIS_VERIFYLOGSTRUCT.bIsCrimeCaptured = matchRelationInfo.getIsCrimeCaptured.asInstanceOf[Byte]
    gAFIS_VERIFYLOGSTRUCT.nTotalMatchedCnt = matchRelationInfo.getTotalMatchedCnt.asInstanceOf[Byte]
    gAFIS_VERIFYLOGSTRUCT.nQueryType = matchRelationInfo.getQuerytype.asInstanceOf[Byte]
    gAFIS_VERIFYLOGSTRUCT.szSrcPersonCaseID = matchRelationInfo.getSrcPersonCaseID
    gAFIS_VERIFYLOGSTRUCT.szDestPersonCaseID = matchRelationInfo.getDestPersonCaseID
    gAFIS_VERIFYLOGSTRUCT.szCaseClassCode1 = matchRelationInfo.getCaseClassCode1
    gAFIS_VERIFYLOGSTRUCT.szCaseClassCode2 = matchRelationInfo.getCaseClassCode2
    gAFIS_VERIFYLOGSTRUCT.szCaseClassCode3 = matchRelationInfo.getCaseClassCode3
    gAFIS_VERIFYLOGSTRUCT.szSearchingUnitCode = matchRelationInfo.getSearchingUnitCode
    gAFIS_VERIFYLOGSTRUCT.szSubmitUserName = matchRelationInfo.getInputer
    gAFIS_VERIFYLOGSTRUCT.szSubmitUserUnitCode = matchRelationInfo.getInputUnitCode
    gAFIS_VERIFYLOGSTRUCT.tSubmitDateTime = DateConverter.convertString2AFISDateTime(matchRelationInfo.getInputDate)
    gAFIS_VERIFYLOGSTRUCT.szBreakUserName = matchRelationInfo.getBreakUsername
    gAFIS_VERIFYLOGSTRUCT.szBreakUserUnitCode = matchRelationInfo.getBreakUserUnitCode
    gAFIS_VERIFYLOGSTRUCT.tBreakDateTime = DateConverter.convertString2AFISDateTime(matchRelationInfo.getBreakDateTime)
    gAFIS_VERIFYLOGSTRUCT.szReCheckUserName = matchRelationInfo.getRechecker
    gAFIS_VERIFYLOGSTRUCT.szReCheckerUnitCode = matchRelationInfo.getRecheckUnitCode
    gAFIS_VERIFYLOGSTRUCT.tReCheckDateTime = DateConverter.convertString2AFISDateTime(matchRelationInfo.getRecheckDate)
    gAFIS_VERIFYLOGSTRUCT
  }
}

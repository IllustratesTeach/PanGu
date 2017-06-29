package nirvana.hall.v62.internal.c.gnetlib


import nirvana.hall.c.services.gloclib.galoclog.GAFIS_VERIFYLOGSTRUCT
import nirvana.hall.v62.config.HallV62Config
import nirvana.hall.v62.internal.V62Facade

/**
  * Created by yuchen on 2017/6/20.
  */
class ganetverifyTest {

  val config = new HallV62Config
  config.appServer.host = "192.168.1.125"
  config.appServer.port = 6898
  config.appServer.user = "afisadmin"

  val facade = new V62Facade(config)

  val verifyLogStruct = new GAFIS_VERIFYLOGSTRUCT

  verifyLogStruct.szBreakID = "900891234567891234123456"
  verifyLogStruct.szQueryTaskID = "19"
  verifyLogStruct.szSrcKey = "430111501999201206001703"
  verifyLogStruct.szDestKey = "1234567890"
  verifyLogStruct.nScore = 600
  verifyLogStruct.nFirstRankScore = 600
  verifyLogStruct.nRank = 0
  verifyLogStruct.nFg = 6
  verifyLogStruct.nHitPoss = 59
  verifyLogStruct.bIsRmtSearched = 0
  verifyLogStruct.bIsCrimeCaptured = 0
  verifyLogStruct.nFgType = 0
  verifyLogStruct.nTotalMatchedCnt = 1
  verifyLogStruct.nQueryType = 1
  verifyLogStruct.nSrcDBID = 1
  verifyLogStruct.nDestDBID = 2
  verifyLogStruct.szSrcPersonCaseID = "1111111"
  verifyLogStruct.szDestPersonCaseID = "1111111"
  verifyLogStruct.szCaseClassCode1 = "010300"
  verifyLogStruct.szCaseClassCode2 = "010301"
  verifyLogStruct.szCaseClassCode3 = "010302"
  verifyLogStruct.szSubmitUserName = "860103"
  verifyLogStruct.szSubmitUserUnitCode = "3100037"
  verifyLogStruct.szBreakUserName = "111"
  verifyLogStruct.szBreakUserUnitCode = "3700101"
  verifyLogStruct.szReCheckUserName = "111"
  verifyLogStruct.szReCheckerUnitCode = "3818231"



 /* @Test
  def test_add: Unit ={
    try{
      facade.NET_GAFIS_VERIFY_Add(21,4,verifyLogStruct)
    }catch{
      case e:Exception => e.printStackTrace
    }
  }

  @Test
  def test_del(): Unit ={
    try{
      verifyLogStruct.nSID = "0"
      facade.NET_GAFIS_VERIFY_DEL(21,4,verifyLogStruct)
    }catch{
      case e:Exception => e.printStackTrace
    }
  }


  @Test
  def test_update: Unit ={
    try{
      verifyLogStruct.nSID = "0"
      facade.NET_GAFIS_VERIFY_Update(21,4,verifyLogStruct)
    }catch{
      case e:Exception => e.printStackTrace
    }
  }*/

}

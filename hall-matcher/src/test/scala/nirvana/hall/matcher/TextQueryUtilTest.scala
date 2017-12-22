package nirvana.hall.matcher

import net.sf.json.JSONObject
import nirvana.hall.matcher.internal.TextQueryUtil
import org.junit.Test

/**
  * Created by songpeng on 2017/8/30.
  */
class TextQueryUtilTest {

  @Test
  def test_personidGroupQuery: Unit ={
    val testSql = "{'personIdST1':'5201*','personIdED1':'5203*','personIdST2':'3701','personIdED2':'3703'}"
    val jsonObject = JSONObject.fromObject(testSql)
    val query = TextQueryUtil.getPersonidGroupQueryByJSONObject(jsonObject)
    println(query)
  }
  @Test
  def test_caseidGroupQuery: Unit ={
    val testSql = "{'caseIdST1':'5201*','caseIdED1':'5203','caseIdST2':'3701*','caseIdED2':'3703'}"
    val jsonObject = JSONObject.fromObject(testSql)
    val query = TextQueryUtil.getCaseidGroupQueryByJSONObject(jsonObject)
    println(query)
  }

  @Test
  def test_textSql62TextQueryData: Unit ={
    val sql = "((CardID LIKE '123456')" +
      " AND ((CardID >= '12') AND (CardID < '34'))" +
      " AND (CreateUserName LIKE 'creater')" +
      " AND (UpdateUserName LIKE 'updater')" +
      " AND (CreateTime >= TO_DATE('20171130000000', 'YYYYMMDDHH24MISS')" +
      " AND CreateTime < TO_DATE('20171130235959', 'YYYYMMDDHH24MISS'))" +
      " AND (UpdateTime >= TO_DATE('20171130000000', 'YYYYMMDDHH24MISS')" +
      " AND UpdateTime < TO_DATE('20171130235959', 'YYYYMMDDHH24MISS'))" +
      " AND (MISPersonID LIKE 'cardid') AND (PersonID LIKE 'personid')" +
      " AND (MISConnectPersonID LIKE 'mispersonid')" +
      " AND ((Name LIKE 'name'))" +
      " AND (Alias LIKE 'bieming')" +
      " AND ((BirthDate >= '20171130') AND (BirthDate <= '20171130'))" +
      " AND (AddressTail LIKE 'addrTail')" +
      " AND (HuKouPlaceTail LIKE 'hukouTail')" +
      " AND (((CaseClass1Code LIKE '010000') OR (CaseClass2Code LIKE '010000') OR (CaseClass3Code LIKE '010000')) OR((CaseClass1Code LIKE '010100') OR (CaseClass2Code LIKE '010100') OR (CaseClass3Code LIKE '010100')))" +
      " AND ((PrintDate >= '20171130') AND (PrintDate <= '20171130'))" +
      " AND (PrinterUnitNameTail LIKE 'printUnitName')" +
      " AND (PrinterUnitCode LIKE '110000')" +
      " AND (PrinterName LIKE 'printer')" +
      " AND ((SexCode = '0') OR (SexCode = '1'))" +
      " AND (ShenFenID LIKE 'idcardno')" +
      " AND (CreatorUnitCode LIKE 'createunitcode')" +
      " AND (UpdatorUnitCode LIKE 'updateunitcode')" +
      " AND (AddressCode LIKE 'addrcode')" +
      " AND (HuKouPlaceCode LIKE 'hujidicode')" +
      " AND ((PersonState != 2) OR (PersonState IS NULL))" +
      " AND (MicbUpdatorUserName LIKE 'mntupdater')" +
      " AND (MicbUpdatorUnitCode LIKE 'mntupdatercode')" +
      " AND ((RaceCode LIKE '01') OR (RaceCode LIKE '03')))"

    val colDatas = TextQueryUtil.convertTextSql2TextQueryData(sql)
//    println(colDatas)
  }
  @Test
  def test_textSql62TextQueryDataTID2: Unit ={
    val sql = "((FingerID LIKE 'casefingerid') AND ((FingerID >= '12') AND (FingerID < '34'))" +
      " AND (CaseID LIKE 'caseid')" +
      " AND (CreateUserName LIKE 'creator')" +
      " AND (UpdateUserName LIKE 'updator')" +
      " AND (CreateTime >= TO_DATE('20171205000000', 'YYYYMMDDHH24MISS') AND CreateTime < TO_DATE('20171205235959', 'YYYYMMDDHH24MISS'))" +
      " AND (UpdateTime >= TO_DATE('20171205000000', 'YYYYMMDDHH24MISS') AND UpdateTime < TO_DATE('20171205235959', 'YYYYMMDDHH24MISS'))" +
      " AND (RemainPlace LIKE 'remainpalce')" +
      " AND (CaptureMethod LIKE '2')" +
      " AND (CreatorUnitCode LIKE 'createunitcode')" +
      " AND (UpdatorUnitCode LIKE 'updateunitcode')" +
      " AND (BrokenUser LIKE 'matcher')" +
      " AND ((BrokenDate >= '20171205') AND (BrokenDate <= '20171205'))" +
      " AND (BrokenUnitCode LIKE '110102')" +
      " AND ((IsBroken > 0) AND (IsLTBroken > 0))" +
      " AND ((IsBroken > 0)" +
      " AND (HitPersonState != 2))" +
      " AND (MicbUpdatorUserName LIKE 'mntmodify')" +
      " AND (MicbUpdatorUnitCode LIKE 'mntupdatorcode')" +
      " AND (PersonID LIKE 'ttgroup')" +
      " AND (GroupName LIKE 'llgroup'))"

  }
  @Test
  def test_textSql62TextQueryDataTID4: Unit ={
    val sql = " ((CaseID LIKE 'caseid') AND ((CaseID >= '12') AND (CaseID < '34'))" +
      " AND (MISConnectCaseID LIKE 'hu lian hao')" +
      " AND ($$CreateTime >= TO_DATE('20171205000000', 'YYYYMMDDHH24MISS')" +
      " AND $$CreateTime < TO_DATE('20171205235959', 'YYYYMMDDHH24MISS'))" +
      " AND ($$UpdateTime >= TO_DATE('20171205000000', 'YYYYMMDDHH24MISS')" +
      " AND $$UpdateTime < TO_DATE('20171205235959', 'YYYYMMDDHH24MISS'))" +
      " AND ((CaseOccurDate >= '20171205') AND (CaseOccurDate <= '20171205'))" +
      " AND (CaseOccurPlaceTail LIKE 'occur place')" +
      " AND ((CaseClass1Code LIKE '010000') OR (CaseClass2Code LIKE '010000'))" +
      " AND (CaseClass3Code LIKE '01')" +
      " AND ((SuspiciousArea1Code LIKE '110000') OR (SuspiciousArea2Code LIKE '110000') OR (SuspiciousArea3Code LIKE '110000'))" +
      " AND (ExtractUnitNameTail LIKE 'extractunitname')" +
      " AND (ExtractUnitCode LIKE 'extractunitcode')" +
      " AND (Extractor1 LIKE 'extractor')" +
      " AND ((SuperviseLevel = '1') OR (SuperviseLevel = '4'))" +
      " AND (CaseOccurPlaceCode LIKE '110000')" +
      " AND (BrokenUser LIKE 'matcher')" +
      " AND ((BrokenDate >= '20171205') AND (BrokenDate <= '20171205'))" +
      " AND (BrokenUnitCode LIKE '110101')" +
      " AND (IsBroken > 0)" +
      " AND ((AJID IS NULL) AND (JQID IS NULL) AND (XKID IS NULL))" +
      " AND (AJID LIKE 'caseid')" +
      " AND (XKID LIKE 'kancha')" +
      " AND (JQID LIKE 'jingqing'))"


  }
}

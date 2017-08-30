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
}

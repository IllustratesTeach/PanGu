package nirvana.hall.api.internal

import java.util.Date

import org.junit.{Assert, Test}

/**
  * Created by songpeng on 2017/3/17.
  */
class DateConverterTest {

  @Test
  def test_convertAFISDateTime2String: Unit ={
    val date = new Date()
    val dateStr = DateConverter.convertDate2String(date, "yyyyMMddHHmmss")
    val afisDateTime = DateConverter.convertString2AFISDateTime(dateStr)
    val afisDateTime2 = DateConverter.convertDate2AFISDateTime(date)
    val afisDateTimeStr = DateConverter.convertAFISDateTime2String(afisDateTime)
    val afisDateTime2Str = DateConverter.convertAFISDateTime2String(afisDateTime2)
    Assert.assertEquals(dateStr, afisDateTimeStr)
    Assert.assertEquals(dateStr, afisDateTime2Str)
  }
}

package nirvana.hall.matcher.internal

import net.sourceforge.pinyin4j.PinyinHelper
import net.sourceforge.pinyin4j.format.{HanyuPinyinCaseType, HanyuPinyinOutputFormat, HanyuPinyinToneType}

/**
  * Created by songpeng on 2017/5/31.
  */
object PinyinConverter {

  /**
    * 将汉字转换为拼音，空格分割，对不能转换的字符忽略
    * @param str
    * @return
    */
  def convert2Pinyin(str: String): String ={
    if(str == null || str.length == 0)
      return str
    val format = new HanyuPinyinOutputFormat
    format.setCaseType(HanyuPinyinCaseType.LOWERCASE)
    format.setToneType(HanyuPinyinToneType.WITHOUT_TONE)
    //这里str追加一个. 来保证最后的拼音是分开的
    PinyinHelper.toHanYuPinyinString(str+"", format, "", true)
  }
}

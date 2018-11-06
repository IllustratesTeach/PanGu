package nirvana.hall.v70.internal.adapter.common.service

import net.sourceforge.pinyin4j.PinyinHelper
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination
import net.sourceforge.pinyin4j.format.{HanyuPinyinCaseType, HanyuPinyinOutputFormat, HanyuPinyinToneType, HanyuPinyinVCharType}

/**
  * Created by Administrator on 2017/6/1.
  */
object TransSpellName {
  /**
    * 捺印人员姓名转拼音
    *
    * @param ChineseLanguage
    */
  @throws[BadHanyuPinyinOutputFormatCombination]
  def toHanyuPinyin(ChineseLanguage: String): String = {
    val cl_chars: Array[Char] = ChineseLanguage.trim.toCharArray
    var hanyupinyin: String = ""
    val defaultFormat: HanyuPinyinOutputFormat = new HanyuPinyinOutputFormat
    defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE) // 输出拼音全部小写
    defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE) // 不带声调
    defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE)
    var i: Int = 0
    while (i < cl_chars.length) {
      {
        if (String.valueOf(cl_chars(i)).matches("[\u4e00-\u9fa5]+")) {
          // 如果字符是中文,则将中文转为汉语拼音
          val pinYin: Array[String] = PinyinHelper.toHanyuPinyinStringArray(cl_chars(i), defaultFormat)
          for (p <- pinYin) {
            hanyupinyin += p + " "
          }
        }
        else {
          // 如果字符不是中文,则不转换
          hanyupinyin += cl_chars(i)
        }
      }
      {
        i += 1; i - 1
      }
    }
    hanyupinyin
  }
}

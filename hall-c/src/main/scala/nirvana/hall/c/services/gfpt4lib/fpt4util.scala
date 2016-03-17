package nirvana.hall.c.services.gfpt4lib

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-03-17
  */
object fpt4util {

  /**
    * 目的特征数据的表示方式
    */
  final val UTIL_FPT4MNT_TOUNKNOWN = 0
  final val UTIL_FPT4MNT_TOMNTDISP = 1

  /**
    * 纹型类型
    */
  final val UTIL_FPT4_PATTERNTYPE_ARCH = 1		// 弓型纹
  final val UTIL_FPT4_PATTERNTYPE_LEFTLOOP = 2		// 左箕型纹
  final val UTIL_FPT4_PATTERNTYPE_RIGHTLOOP = 3		// 右箕型纹
  final val UTIL_FPT4_PATTERNTYPE_WHORL = 4		// 斗型纹
  final val UTIL_FPT4_PATTERNTYPE_NOTEXIST = 5		// 缺指
  final val UTIL_FPT4_PATTERNTYPE_UNKNOWN = 6		// 未知
  final val UTIL_FPT4_PATTERNTYPE_OTHER = 9		// 其它



  /**
    * 中心点、三角
    */
  final val UTIL_COREDELTA_TYPE_UPCORE = 1
  final val UTIL_COREDELTA_TYPE_VICECORE = 2
  final val UTIL_COREDELTA_TYPE_LDELTA = 3
  final val UTIL_COREDELTA_TYPE_RDELTA = 4
  final val UTIL_COREDELTA_TYPE_MINUTIA = 5



  /**
    * 去掉字符串左侧（或）和右侧的空格
    */
  final val UTIL_FPT4LIB_STRTRIM_STYLE_LEFT = 0x1
  final val UTIL_FPT4LIB_STRTRIM_STYLE_RIGHT = 0x2
  final val UTIL_FPT4LIB_STRTRIM_STYLE_TWOSIDE = 0x3


  /**
    * GAFIS6.2系统中的协查级别使用的是FPT标准3（Version3）标准，其代码为：0 - 未知；1 - A级；2 - B级；3 - C级；4 - B级紧急
    * 而最新协查级别为公安部FTP标准4（Version4），其代码：1 - A级；2 - B级紧急；3 - B级；4 - C级；9 - 其他；
    * 为了与最新的标准一致，在fpt导入导出时，对协查级别进行转换，转换原则为：
    *	1、导出时，协查级别转换成Version4标准，并且在fpt文件的第一类逻辑记录的备注里面增
    *		加标记"<FPX_Level Version = "4" />"，除非m_bReserveFPX_Level = 1
    * 2、导入时，如果导入的是非东方金指生成的fpt文件，则其中的协查级别自动被认为是Version4，
    *		把其转换成Version3标准，如果是东方金指生成的fpt文件，则检查第一类逻辑记录的备注中有
    *		没有FPX_Level Version标记，如果有并且其值为4，则转换成Version3标准，如果没有或者其值
    *		为3，则认为就是Version3标准，不用转换
    *	3、不管是导入还是导出，如果m_bReserveFPX_Level=1，则不对协查级别进行转换
    */
}

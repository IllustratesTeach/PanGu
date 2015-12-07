package nirvana.hall.api.internal

import org.joda.time.DateTime

/**
 * some implicit converter
 * Created by songpeng on 15/12/7.
 */
package object sync {
  implicit def string2Int(string: String): Int ={
    Integer.parseInt(string)
  }
  implicit def dateTime2String(date: DateTime): String = {
    date.toString("yyyyMMdd")
  }
}

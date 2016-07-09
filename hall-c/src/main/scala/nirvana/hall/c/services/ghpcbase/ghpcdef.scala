package nirvana.hall.c.services.ghpcbase

import java.util.Calendar

import nirvana.hall.c.annotations.Length
import nirvana.hall.c.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-06
 */
object ghpcdef {

  class GAFIS_UUIDStruct extends AncientData {
    var Data1: Int = _;
    // generation time
    var Data2: Short = _;
    // a random number
    var Data3: Short = _;
    // value returned by clock()
    var Data4: Short = _;
    // second random num, srand by value returned by clock
    @Length(6)
    var Data5: String = _; // using malloc memory to get another random char
  }

  // GAFIS_UUIDStruct;	// size is 16 bytes


  // date time format, t[7:6]-year, t[5]-mon, t[4]-day, t[3]-hour, t[2]-minute, t[1:0]-millisec
  class GAFIS_TIME extends AncientData {
    private val calendar = Calendar.getInstance()
    var tMilliSec: Short = {
      val sec = calendar.get(Calendar.SECOND)
      (((sec & 0xff) << 8) | (sec >>> 8)).toShort
    }
    // millisecond.	[0, 999]
    var tMin: Byte = calendar.get(Calendar.MINUTE).toByte;
    // minute. [0, 59]
    var tHour: Byte = calendar.get(Calendar.HOUR_OF_DAY).toByte; // hour,   [0, 23]
  }

  // GAFIS_TIME;	// size is 4 bytes long

  class GAFIS_DATE extends AncientData {
    private val calendar = Calendar.getInstance()
    var tDay: Byte = calendar.get(Calendar.DAY_OF_MONTH).toByte;
    // day, [1, 31]
    var tMonth: Byte = calendar.get(Calendar.MONTH).toByte;
    // month, [0, 11]
    var tYear: Short = converAsGafisYear()
    def converAsGafisYear(): Short ={
      val year = calendar.get(Calendar.YEAR)
      (((year & 0xff) << 8) | (year >>> 8)).toShort
    }
  }

  // GAFIS_DATE;	// size is 4 bytes long.

  class GAFIS_DATETIME extends AncientData {
    var tTime = new GAFIS_TIME;
    var tDate = new GAFIS_DATE; // date
  }

  // GAFIS_DATETIME;	// gafis data time. size is 8 bytes long

  type AFISDateTime = GAFIS_DATETIME
  // can map to GAFIS_DATETIME structure
  type AFISDate = GAFIS_DATE
  // can map to GAFIS_DATE structure
  type AFISTime = GAFIS_TIME // can map to GAFIS_TIME structure
  /*
  typedef UCHAR	AFISDateTime[8];
  typedef	UCHAR	AFISDate[4];
  typedef	UCHAR	AFISTime[4];


  final val GBASE_UTIL_ALIGN(a, = at)	((((a)+(at)-1)/(at))*(at))
  */


}

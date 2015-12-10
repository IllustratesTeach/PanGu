package nirvana.hall.c.services.ghpcbase

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
    var tMilliSec: Short = _;
    // millisecond.	[0, 999]
    var tMin: Byte = _;
    // minute. [0, 59]
    var tHour: Byte = _; // hour,   [0, 23]
  }

  // GAFIS_TIME;	// size is 4 bytes long

  class GAFIS_DATE extends AncientData {
    var tDay: Byte = _;
    // day, [1, 31]
    var tMonth: Byte = _;
    // month, [0, 11]
    var tYear: Short = _; // short int year.
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

package nirvana.hall.c.services.ghpcbase

import nirvana.hall.c.annotations.Length
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.gbaselib.gbasedef

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
  @deprecated
  class GAFIS_TIME extends gbasedef.GAFIS_TIME{
  }

  // GAFIS_TIME;	// size is 4 bytes long
  @deprecated
  class GAFIS_DATE extends gbasedef.GAFIS_DATE{
  }

  // GAFIS_DATE;	// size is 4 bytes long.

  @deprecated
  class GAFIS_DATETIME extends gbasedef.GAFIS_DATE{
  }

  // GAFIS_DATETIME;	// gafis data time. size is 8 bytes long

  @deprecated
  type AFISDateTime = gbasedef.AFISDateTime
  // can map to GAFIS_DATETIME structure
  @deprecated
  type AFISDate = gbasedef.AFISDate
  // can map to GAFIS_DATE structure
  @deprecated
  type AFISTime = gbasedef.AFISTime// can map to GAFIS_TIME structure
  /*
  typedef UCHAR	AFISDateTime[8];
  typedef	UCHAR	AFISDate[4];
  typedef	UCHAR	AFISTime[4];


  final val GBASE_UTIL_ALIGN(a, = at)	((((a)+(at)-1)/(at))*(at))
  */


}

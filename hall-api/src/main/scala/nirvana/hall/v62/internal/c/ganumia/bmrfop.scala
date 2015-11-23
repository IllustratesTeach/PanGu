package nirvana.hall.v62.internal.c.ganumia

import nirvana.hall.v62.annotations.Length
import nirvana.hall.v62.internal.c.ganumia.bmfop.GADB_BMFILEHEAD
import nirvana.hall.v62.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-23
 */
object bmrfop {



  class GADB_BMRPROPSTRUCT extends AncientData
  {
    var nRowSize:Int = _ ;
    @Length(60)
    var bnRes:Array[Byte] = _ ;
  } // GADB_BMRPROPSTRUCT;	// size is 64 bytes

  final val BMRFILEHEAD_MAGICSTR= "$bmrfilebyxcg$"

  class GADB_BMRFILEHEAD extends AncientData
  {
    var stBMFh = new GADB_BMFILEHEAD;	// size is 128 bytes
  var stBmrProp = new GADB_BMRPROPSTRUCT;	// size is 64 bytes
  } // GADB_BMRFILEHEAD;	// size of this structure is 192 bytes

  // in the following structure nID count from 0, and it can not exceed the
  // maximum # of record in one file
  //typedef	struct	tagGADB_BMRID
  //{
  //	uint4	nBKID;		// block id
  //	uint2	nOID;		// offset in one block, so in 1 block can have maximum 65535 records
  //	uint2	nFileID;	// file id, so a table can have at most 65535 files
  //} GADB_SRID;	// size is 8 bytes

}

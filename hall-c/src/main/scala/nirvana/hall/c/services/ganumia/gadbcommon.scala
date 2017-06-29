package nirvana.hall.c.services.ganumia

import nirvana.hall.c.annotations.Length
import nirvana.hall.c.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-23
 */
object gadbcommon {

  class GADB_FILEHEADSCHEMA extends AncientData
  {
    var cbSize:Int = _ ;			// size of this structure, it must be 64
  @Length(16)
  var szMagicStr:String = _ ;		// magic string, have different values in diff files
  var nMajorVer:Short = _ ;		// major ver, 2 bytes int
  var nMinorVer:Short = _ ;		// minor ver, 2 bytes int
  // nHeadSize is not the size of this record. In numina system, all files have a head
  // and followed by data records. here nHeadSize refers to that head size. this size
  // includes this structure size.
  var nHeadSize:Int = _ ;		// head size, 4 bytes int,
  var nWriteRefCount:Int = _ ;	// when changing a file, we increment this flag, when finished
  // changing, we decrement this flag. when opening a file, we
  // check this flag. if this flag is 0, then the file was closed
  // ok and no error occur while writing, else something must
  // be error(server killed, power down, disk failure, etc...)
  var nFileSize:Long = _ ;		// file size, 8 bytes int
  @Length(12)
  var bnRes:Array[Byte] = _ ;			// reserved
  @Length(12)
  var szOwnMagic:String = _ ;		// must be 'numinabyxcg'
  } // GADB_FILEHEADSCHEMA;			// size of this structure is 64 bytes

  final val FILEHEAD_OWNMGIC = "numinabyxcg"

}

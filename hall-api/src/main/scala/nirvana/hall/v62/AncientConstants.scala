package nirvana.hall.v62

import java.nio.charset.Charset

/**
 * constants for ancient system
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-30
 */
object AncientConstants {
  final val UTF8_ENCODING=Charset.forName("GBK")
  final val GBK_ENCODING=Charset.forName("GBK")
  final val OP_CLASS_TPLIB = 101.toShort
  final val OP_TPLIB_ADD = 50.toShort
  final val OP_CLASS_LPLIB = 102.toShort
  final val OP_LPLIB_ADD = 100.toShort
  final val OP_CLASS_CASE = 114.toShort
  final val OP_CASE_ADD = 250.toShort


  // can be or'ed
  final val GAMIC_ITEMFLAG_MNT      =0x1
  final val GAMIC_ITEMFLAG_IMG      =0x2
  final val GAMIC_ITEMFLAG_CPR      =0x4
  final val GAMIC_ITEMFLAG_BIN      =0x8

  // cann't be or'ed
  final val GAMIC_ITEMTYPE_FINGER   =0x1 // rolled finger.
  final val GAMIC_ITEMTYPE_PALM     =0x2 // palm part.
  final val GAMIC_ITEMTYPE_PLAINFINGER  =0x3 // plain finger using for information store.
  final val GAMIC_ITEMTYPE_FACE     =0x4 // ITEMDATA:1-FRONT, 2 noseleftSIDE, 3 noserightSIDE, GTPIO_ITEMINDEX_XXX
  final val GAMIC_ITEMTYPE_DATA     =0x5 // card bin data.
  // =0x6 is not used, it's reserved for text, refer to GTPIO_ITEMTYPE
  final val GAMIC_ITEMTYPE_SIGNATURE  =0x7
  final val GAMIC_ITEMTYPE_TPLAIN   =0x8 // plain finger, PLAINFINGER is used to store data.
  // and TPLAIN is used to do search
  final val GAMIC_ITEMTYPE_EXTRAFINGER  =0x9 // six finger
  final val GAMIC_ITEMTYPE_HANDPART   =0xa // hand part except palm
  final val GAMIC_ITEMTYPE_VOICE    =0xb
}

package nirvana.hall.v62.internal.c

import nirvana.hall.c.services.ghpcbase.glocdef
import nirvana.hall.protocol.api.FPTProto.PalmFgp

/**
  * Created by songpeng on 2017/8/22.
  * 指掌位转换工具类
  */
object FgpConverter {

  def convertPalmFgp2GTPIO_ITEMINDEX(palmFgp: PalmFgp): Byte={
    (palmFgp match {
      case PalmFgp.PALM_RIGHT => glocdef.GTPIO_ITEMINDEX_PALM_RIGHT
      case PalmFgp.PALM_LEFT => glocdef.GTPIO_ITEMINDEX_PALM_LEFT
      case PalmFgp.PALM_FINGER_R => glocdef.GTPIO_ITEMINDEX_PALM_RFINGER
      case PalmFgp.PALM_FINGER_L => glocdef.GTPIO_ITEMINDEX_PALM_LFINGER
      case PalmFgp.PALM_THUMB_R_LOW => glocdef.GTPIO_ITEMINDEX_PALM_RTHUMBLOW
      case PalmFgp.PALM_THUMB_R_UP => glocdef.GTPIO_ITEMINDEX_PALM_RTHUMBUP
      case PalmFgp.PALM_THUMB_L_LOW => glocdef.GTPIO_ITEMINDEX_PALM_LTHUMBLOW
      case PalmFgp.PALM_THUMB_L_UP => glocdef.GTPIO_ITEMINDEX_PALM_LTHUMBUP
    }).asInstanceOf[Byte]
  }
  def convertGTPIO_ITEMINDEX2PalmFgp(index: Byte): PalmFgp={
    index match {
      case glocdef.GTPIO_ITEMINDEX_PALM_RIGHT => PalmFgp.PALM_RIGHT
      case glocdef.GTPIO_ITEMINDEX_PALM_LEFT => PalmFgp.PALM_LEFT
      case glocdef.GTPIO_ITEMINDEX_PALM_RFINGER => PalmFgp.PALM_FINGER_R
      case glocdef.GTPIO_ITEMINDEX_PALM_LFINGER => PalmFgp.PALM_FINGER_L
      case glocdef.GTPIO_ITEMINDEX_PALM_RTHUMBLOW => PalmFgp.PALM_THUMB_R_LOW
      case glocdef.GTPIO_ITEMINDEX_PALM_RTHUMBUP=> PalmFgp.PALM_THUMB_R_UP
      case glocdef.GTPIO_ITEMINDEX_PALM_LTHUMBLOW => PalmFgp.PALM_THUMB_L_LOW
      case glocdef.GTPIO_ITEMINDEX_PALM_LTHUMBUP=> PalmFgp.PALM_THUMB_L_UP
      case other => PalmFgp.PALM_UNKNOWN
    }

  }
}

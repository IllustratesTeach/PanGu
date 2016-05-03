package nirvana.hall.image.internal

import nirvana.hall.c.services.gloclib.glocdef.GAFISIMAGESTRUCT

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-03
  */
object GfsImageUtils {
  def FPT_datatranform(gafisImage: GAFISIMAGESTRUCT):Unit={
    if (gafisImage == null) throw new NullPointerException
    if (gafisImage.stHead.nCompressMethod >= 10) throw new IllegalStateException("invalid image compress method")
    val bnData = gafisImage.bnData
    val imgSize = gafisImage.stHead.nImgSize

    if (imgSize == 0) return

    if (imgSize < 100 || bnData == NULL) new IllegalStateException("wrong image data")

    0 until 100 foreach{case i=>
      bnData(i) ^= (i * i + 9 * i + 11) % 256;
    }
  }
}

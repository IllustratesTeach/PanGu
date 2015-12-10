package nirvana.hall.v62.internal

import nirvana.hall.protocol.v62.FPTProto.{LPCard, TPCard}
import nirvana.hall.c.services.gloclib.galoclp.GLPCARDINFOSTRUCT
import nirvana.hall.c.services.gloclib.galoctp.GTPCARDINFOSTRUCT
import nirvana.hall.v62.internal.c.gloclib.{galoclpConverter, galoctpConverter}

/**
 * struct for feature data,such as template,latent and image.
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-03
 */
object FeatureStruct {
  /**
   * convert protobuf object to gafis' TPCard
   * @param card protobuf object
   * @return gafis TPCard
   * @see FPTBatchUpdater.cpp #812
   */
  @deprecated
  def convertProtoBuf2TPCard(card: TPCard): GTPCARDINFOSTRUCT={
    galoctpConverter.convertProtoBuf2GTPCARDINFOSTRUCT(card)
  }

  /**
   * convert protobuf object to latent card object
   * @param card protobuf object
   * @return gafis latent card object
   */
  @deprecated
  def convertProtoBuf2LPCard(card: LPCard): GLPCARDINFOSTRUCT= {
    galoclpConverter.convertProtoBuf2GLPCARDINFOSTRUCT(card)
  }
}


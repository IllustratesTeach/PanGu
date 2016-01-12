package nirvana.hall.v62.internal.c.gloclib

import nirvana.hall.c.services.gloclib.galoctp.GADB_MICSTREAMNAMESTRUCT
import nirvana.hall.c.services.gloclib.glocdef.GAFISMICSTRUCT
import org.jboss.netty.buffer.ChannelBuffer

import scala.collection.mutable

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-12
 */
trait galoctp {
  val streamParameter = new GADB_MICSTREAMNAMESTRUCT
  def GAFIS_MIC_GetDataFromStream(buffer:ChannelBuffer): Seq[GAFISMICSTRUCT]={

    val result= mutable.Buffer[GAFISMICSTRUCT]()
    var dataLength = 0
    while(buffer.readableBytes() >= 360) {
      GADB_COL_CmpName(buffer.readBytes(36),streamParameter.pszMICName_Data)
      val pmic = new GAFISMICSTRUCT

      try {
        buffer.markReaderIndex()
        GAFIS_MIC_GetItemDataFromStream(buffer, streamParameter.pszItemIndex_Data)
        pmic.nIndex = buffer.readByte()
        buffer.skipBytes(UTIL_TO4ALIGN(dataLength) - dataLength)
      } catch {
        case e: Throwable => //
          buffer.resetReaderIndex()
      }
      dataLength = GAFIS_MIC_GetItemDataFromStream(buffer, streamParameter.pszItemFlag_Data)
      pmic.nItemFlag = buffer.readByte()
      buffer.skipBytes(UTIL_TO4ALIGN(dataLength) - dataLength)
      dataLength = GAFIS_MIC_GetItemDataFromStream(buffer, streamParameter.pszItemType_Data)
      pmic.nItemType = buffer.readByte()
      buffer.skipBytes(UTIL_TO4ALIGN(dataLength) - dataLength)
      dataLength = GAFIS_MIC_GetItemDataFromStream(buffer, streamParameter.pszItemData_Data)
      pmic.nItemData = buffer.readByte()
      buffer.skipBytes(UTIL_TO4ALIGN(dataLength) - dataLength)
      dataLength = GAFIS_MIC_GetItemDataFromStream(buffer, streamParameter.pszIsLatent_Data)
      pmic.bIsLatent = buffer.readByte()
      buffer.skipBytes(UTIL_TO4ALIGN(dataLength) - dataLength)

      dataLength = GAFIS_MIC_GetItemDataFromStream(buffer, streamParameter.pszMntName_Data)
      if(dataLength > 0) {
        pmic.pstMnt_Data = buffer.readBytes(dataLength).array()
        pmic.nMntLen = dataLength
        buffer.skipBytes(UTIL_TO4ALIGN(dataLength) - dataLength)
      }
      dataLength = GAFIS_MIC_GetItemDataFromStream(buffer, streamParameter.pszImgName_Data)
      if(dataLength >0) {
        pmic.pstImg_Data = buffer.readBytes(dataLength).array()
        pmic.nImgLen = dataLength
        buffer.skipBytes(UTIL_TO4ALIGN(dataLength) - dataLength)
      }
      dataLength = GAFIS_MIC_GetItemDataFromStream(buffer, streamParameter.pszCprName_Data)
      if(dataLength >0) {
        pmic.pstCpr_Data = buffer.readBytes(dataLength).array()
        pmic.nCprLen = dataLength
        buffer.skipBytes(UTIL_TO4ALIGN(dataLength) - dataLength)
      }
      dataLength = GAFIS_MIC_GetItemDataFromStream(buffer, streamParameter.pszBinName_Data)
      if(dataLength >0) {
        pmic.pstBin_Data = buffer.readBytes(dataLength).array()
        pmic.nBinLen = dataLength
        buffer.skipBytes(UTIL_TO4ALIGN(dataLength) - dataLength)
      }
      result += pmic
    }

    result.toSeq
  }
  def GAFIS_MIC_GetItemDataFromStream(buffer:ChannelBuffer,pszExpectedName:String): Int={
    GADB_COL_CmpName(buffer.readBytes(32),pszExpectedName)
    val nLen = buffer.readInt()
    if(nLen > buffer.readableBytes()){
      throw new IllegalAccessException("nLen=%d > readable =%d".format(nLen,buffer.readableBytes()))
    }
    nLen
  }
  private def UTIL_TO4ALIGN(x:Int)=	((x + 3) / 4) * 4


  def GADB_COL_CmpName(bytes:ChannelBuffer,expecteName:String) {
    val currentName= new String(bytes.array()).trim
    if(!currentName.startsWith(expecteName))
      throw new IllegalAccessException("expectName=%s actual =%s".format(expecteName,currentName))
  }
}

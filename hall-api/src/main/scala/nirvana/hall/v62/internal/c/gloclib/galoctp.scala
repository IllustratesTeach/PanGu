package nirvana.hall.v62.internal.c.gloclib

import java.nio.ByteBuffer

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
  val pn = streamParameter

  def GAFIS_MIC_GetDataFromStream(buffer: ChannelBuffer): Seq[GAFISMICSTRUCT] = {

    val result = mutable.Buffer[GAFISMICSTRUCT]()
    var dataLength = 0
    while (buffer.readableBytes() >= 360) {
      GADB_COL_CmpName(buffer.readBytes(36), streamParameter.pszMICName_Data)
      val pmic = new GAFISMICSTRUCT

      try {
        buffer.markReaderIndex()
        pmic.nIndex = GAFIS_MIC_GetItemDataFromStreamSingleByte(buffer, streamParameter.pszItemIndex_Data)
      } catch {
        case e: Throwable => //
          buffer.resetReaderIndex()
      }
      pmic.nItemFlag = GAFIS_MIC_GetItemDataFromStreamSingleByte(buffer, streamParameter.pszItemFlag_Data)
      pmic.nItemType = GAFIS_MIC_GetItemDataFromStreamSingleByte(buffer, streamParameter.pszItemType_Data)
      pmic.nItemData = GAFIS_MIC_GetItemDataFromStreamSingleByte(buffer, streamParameter.pszItemData_Data)
      pmic.bIsLatent = GAFIS_MIC_GetItemDataFromStreamSingleByte(buffer, streamParameter.pszIsLatent_Data)

      pmic.pstMnt_Data = GAFIS_MIC_GetItemDataFromStreamByteArray(buffer, streamParameter.pszMntName_Data)
      pmic.nMntLen = pmic.pstMnt_Data.length

      pmic.pstImg_Data = GAFIS_MIC_GetItemDataFromStreamByteArray(buffer, streamParameter.pszImgName_Data)
      pmic.nImgLen = pmic.pstImg_Data.length

      pmic.pstCpr_Data = GAFIS_MIC_GetItemDataFromStreamByteArray(buffer, streamParameter.pszCprName_Data)
      pmic.nCprLen = pmic.pstCpr_Data.length

      pmic.pstBin_Data = GAFIS_MIC_GetItemDataFromStreamByteArray(buffer, streamParameter.pszBinName_Data)
      pmic.nBinLen = pmic.pstBin_Data.length

      result += pmic
    }

    result.toSeq
  }

  private def GAFIS_MIC_GetItemDataFromStream(buffer: ChannelBuffer, pszExpectedName: String): Int = {
    GADB_COL_CmpName(buffer.readBytes(32), pszExpectedName)
    val nLen = buffer.readInt()
    if (nLen > buffer.readableBytes()) {
      throw new IllegalAccessException("nLen=%d > readable =%d".format(nLen, buffer.readableBytes()))
    }
    nLen
  }

  private def GAFIS_MIC_GetItemDataFromStreamSingleByte(buffer: ChannelBuffer, pszExpectedName: String): Byte = {
    val dataLength = GAFIS_MIC_GetItemDataFromStream(buffer, pszExpectedName)
    val byte = buffer.readByte()
    buffer.skipBytes(UTIL_TO4ALIGN(dataLength) - dataLength)
    byte
  }

  private def GAFIS_MIC_GetItemDataFromStreamByteArray(buffer: ChannelBuffer, pszExpectedName: String): Array[Byte] = {
    val dataLength = GAFIS_MIC_GetItemDataFromStream(buffer, pszExpectedName)
    val bytes = buffer.readBytes(dataLength).array()
    buffer.skipBytes(UTIL_TO4ALIGN(dataLength) - dataLength)
    bytes
  }

  private def UTIL_TO4ALIGN(x: Int) = ((x + 3) / 4) * 4


  private def GADB_COL_CmpName(bytes: ChannelBuffer, expectedName: String) {
    val currentName = new String(bytes.array()).trim
    if (!currentName.startsWith(expectedName))
      throw new IllegalAccessException("expectName=%s actual =%s".format(expectedName, currentName))
  }
  def GAFIS_MIC_HasMICData(buffer: ChannelBuffer) {
    buffer.markReaderIndex()
    val length = streamParameter.pszMICName_Data.length
    GADB_COL_CmpName(buffer.readBytes(length), streamParameter.pszMICName_Data)
    buffer.resetReaderIndex()
  }
  // return the # of mic got
  def GAFIS_MIC_GetMicArrayFromStream(buffer: ChannelBuffer): Array[GAFISMICSTRUCT] = {
    val result = mutable.Buffer[GAFISMICSTRUCT]()
    while (buffer.readableBytes() > 360) {
      // even has many larger buffer, these may not be mic stream(other application used).
      GAFIS_MIC_HasMICData(buffer)
      result ++= GAFIS_MIC_GetDataFromStream(buffer)
    }

    result.toArray
  }
  def GAFIS_MIC_AddDataToStream(pstream:ChannelBuffer,pszName:String,pData:Array[Byte]) //, int nLen, void *pData)
  {
    val bytes = pszName.getBytes()
    pstream.writeBytes(bytes)
    //不足32位补0
    pstream.writeZero(32 - bytes.length)

    val nLen = if(pData == null) 0 else pData.length
    pstream.writeInt(nLen)
    if(pData != null){
      pstream.writeBytes(pData)
      pstream.writeZero(UTIL_TO4ALIGN(nLen) - nLen)
    }
  }
  // return 1 on success else return -1
  def GAFIS_MIC_Mic2Stream(pmic:GAFISMICSTRUCT, pstream:ChannelBuffer)
  {
    GAFIS_MIC_AddDataToStream(pstream, pn.pszMICName_Data,null);
    if ( pmic.nIndex >0 ) {
      GAFIS_MIC_AddDataToStream(pstream,  pn.pszItemIndex_Data, ByteBuffer.allocate(4).put(pmic.nIndex).array())
    }
    GAFIS_MIC_AddDataToStream(pstream,  pn.pszItemFlag_Data , Array(pmic.nItemFlag))
    GAFIS_MIC_AddDataToStream(pstream,  pn.pszItemType_Data, Array(pmic.nItemType))
    GAFIS_MIC_AddDataToStream(pstream,  pn.pszItemData_Data, Array(pmic.nItemData))
    GAFIS_MIC_AddDataToStream(pstream,  pn.pszIsLatent_Data, Array(pmic.bIsLatent))
    GAFIS_MIC_AddDataToStream(pstream,  pn.pszMntName_Data, pmic.pstMnt_Data);
    GAFIS_MIC_AddDataToStream(pstream,  pn.pszImgName_Data,  pmic.pstImg_Data);
    GAFIS_MIC_AddDataToStream(pstream,  pn.pszCprName_Data,  pmic.pstCpr_Data);
    GAFIS_MIC_AddDataToStream(pstream,  pn.pszBinName_Data,  pmic.pstBin_Data);
  }
  // return length of stream
  def GAFIS_MIC_MicArray2Stream(pmic:Array[GAFISMICSTRUCT], buffer:ChannelBuffer)
  {
    pmic.foreach(GAFIS_MIC_Mic2Stream(_,buffer))
  }
}


package nirvana.hall.v62.internal.c.grmtlib

import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services.gbaselib.gitempkg.GBASE_ITEMPKG_OPSTRUCT
import nirvana.hall.c.services.gloclib.glocndef.{GNETANSWERHEADOBJECT, GNETREQUESTHEADOBJECT}
import nirvana.hall.c.services.grmtlib.grmtdef
import nirvana.hall.c.services.grmtlib.grmtpara.RMTFLIBADDSTRUCT
import nirvana.hall.c.services.grmtlib.grmtpkg._
import nirvana.hall.v62.internal.c.gbaselib.gitempkg

import scala.reflect.{ClassTag, classTag}

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-04-26
  */
trait grmtpkg {
  this: gitempkg =>
  private val pkg_pszThis = "This"
  private val pkg_pszRequest = "Request"
  private val pkg_pszAnswer = "Answer"
  private val pkg_pszUnitCodeName = "UnitCode"
  private val pkg_pszOther = "Other"
  private val pkg_pszFlibAdd = "rmtpkg_FlibAdd"

  private def addRmtItem[T <: AncientData](pstPkg: GBASE_ITEMPKG_OPSTRUCT, obj: T, itemType: Int, itemName: String) {
    GBASE_ITEMPKG_AddItemEx(pstPkg, itemName, itemType, obj.toByteArray())
  }

  private def findRmtItemData(pstPkg: GBASE_ITEMPKG_OPSTRUCT, name: String): Option[Array[Byte]] = {
    GBASE_ITEMPKG_GetItem(pstPkg, name).map(_.bnRes)
  }

  private def findRmtItem[T <: AncientData : ClassTag](pstPkg: GBASE_ITEMPKG_OPSTRUCT, name: String): Option[T] = {
    findRmtItemData(pstPkg, name).map { data =>
      val instance = classTag[T].runtimeClass.newInstance().asInstanceOf[T]
      if (instance.getDataSize != data.length)
        throw new IllegalStateException("data length is not equals!")
      instance.fromByteArray(data)
    }
  }

  def GAFIS_PKG_AddRmtRequest(pstPkg: GBASE_ITEMPKG_OPSTRUCT, pstReq: GNETREQUESTHEADOBJECT) {
    addRmtItem(pstPkg, pstReq, RMTPKG_ITEMTYPE_REQUEST, pkg_pszRequest)
  }

  def GAFIS_PKG_GetRmtRequest(pstPkg: GBASE_ITEMPKG_OPSTRUCT): Option[GNETREQUESTHEADOBJECT] = {
    findRmtItem[GNETREQUESTHEADOBJECT](pstPkg, pkg_pszRequest)
  }

  def GAFIS_PKG_AddRmtAnswer(pstPkg: GBASE_ITEMPKG_OPSTRUCT, pstAns: GNETANSWERHEADOBJECT) {
    addRmtItem(pstPkg, pstAns, RMTPKG_ITEMTYPE_ANSWER, pkg_pszAnswer)
  }

  def GAFIS_PKG_GetRmtAnswer(pstPkg: GBASE_ITEMPKG_OPSTRUCT): Option[GNETANSWERHEADOBJECT] = {
    findRmtItem[GNETANSWERHEADOBJECT](pstPkg, pkg_pszRequest)
  }

  def GAFIS_PKG_AddOtherData(pstPkg: GBASE_ITEMPKG_OPSTRUCT, pData: Array[Byte]): Unit = {
    GBASE_ITEMPKG_AddItemEx(pstPkg,  pkg_pszOther,RMTPKG_ITEMTYPE_OTHER, pData)
  }

  def GAFIS_PKG_GetOtherData(pstPkg: GBASE_ITEMPKG_OPSTRUCT): Option[Array[Byte]] = {
    findRmtItemData(pstPkg, pkg_pszOther)
  }

  def GAFIS_PKG_AddUnitCode(pstPkg: GBASE_ITEMPKG_OPSTRUCT, pszUnitCode: String): Unit = {
    val bytes = new Array[Byte](grmtdef.RMTUNITCODELEN)
    val unitBytes = pszUnitCode.getBytes()
    System.arraycopy(unitBytes, 0, bytes, 0, unitBytes.length)
    GBASE_ITEMPKG_AddItemEx(pstPkg, pkg_pszUnitCodeName,RMTPKG_ITEMTYPE_UNITCODE, bytes)
  }

  def GAFIS_PKG_GetUnitCode(pstPkg: GBASE_ITEMPKG_OPSTRUCT): Option[String] = {
    findRmtItemData(pstPkg, pkg_pszUnitCodeName).map(new String(_).trim)
  }

  // add by xxf, compatible with gafis 5.0 [11/22/2004]
  def GAFIS_PKG_AddItemData(pstPkg: GBASE_ITEMPKG_OPSTRUCT, pData: Array[Byte], pszItemName: String, nItemType: Int): Unit = {
    GBASE_ITEMPKG_AddItemEx(pstPkg,  pszItemName,nItemType, pData)
  }

  def GAFIS_PKG_AddThisData(pstPkg: GBASE_ITEMPKG_OPSTRUCT, pData: Array[Byte]) {
    GAFIS_PKG_AddItemData(pstPkg, pData, pkg_pszThis, RMTPKG_ITEMTYPE_THIS);
  }

  def GAFIS_PKG_GetItemData(pstPkg: GBASE_ITEMPKG_OPSTRUCT, pszItemName: String): Option[Array[Byte]] = {
    findRmtItemData(pstPkg, pszItemName)
  }

  def GAFIS_RMTPKG_AddFlibAddStruct(pstPkg: GBASE_ITEMPKG_OPSTRUCT, pstAdd: RMTFLIBADDSTRUCT) {
    addRmtItem(pstPkg, pstAdd,  RMTPKG_ITEMTYPE_FLIBADD,pkg_pszFlibAdd);
  }

  def GAFIS_RMTPKG_GetFlibAddStruct(pstPkg: GBASE_ITEMPKG_OPSTRUCT): Option[RMTFLIBADDSTRUCT] = {
    findRmtItem(pstPkg, pkg_pszFlibAdd)
  }
}

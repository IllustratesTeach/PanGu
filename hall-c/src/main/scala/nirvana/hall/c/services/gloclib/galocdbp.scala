package nirvana.hall.c.services.gloclib

import nirvana.hall.c.annotations.IgnoreTransfer
import nirvana.hall.c.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-12
 */
object galocdbp {


  class GAFIS_DEFDBIDSTRUCT extends AncientData
  {
    var nTPDefDBID:Short = _ ;
    var nLPDefDBID:Short = _ ;
    var nQryDefDBID:Short = _ ;
    var nAdminDefDBID:Short = _ ;
  } // GAFIS_DEFDBIDSTRUCT;	// default database id

  final val GAFIS_DROPDBOPT_WILLDELETE = 0x1


  class GAFIS_DBNAMEIDMAP extends AncientData
  {
    var pszName_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pszName_Data:Array[Byte] = _ // for pszName pointer ,struct:char;
  var nDBID:Int = _;
    var nType:Int = _;
  } // GAFIS_DBNAMEIDMAP;


  ////////////////////////////////////////////////////////
  //extern	GANMDBPROPSTRUCT *		GAFIS_DB_NewEmptyDB(char *pszPathName, char *pszDBName);
  //extern	GADBPROPSTRUCT *		GAFIS_DB_GetFirst(void **pHandle);



  // nOption will be GAFIS_DROPDBOPT_WILLDELETE
  //extern	GADBPROPSTRUCT	* GAFIS_SYS_GetDBByUUID(UCHAR *guuid);


}




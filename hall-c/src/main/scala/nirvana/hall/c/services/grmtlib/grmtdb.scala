package nirvana.hall.c.services.grmtlib

import nirvana.hall.c.annotations.Length
import nirvana.hall.c.services.AncientData
import nirvana.hall.c.services._
import nirvana.hall.c.services.gloclib.galocdbp.GAFIS_DEFDBIDSTRUCT

/**
  *
  * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
  * @since 2016-05-18
  */
object grmtdb {

  class RMTUSERDBIDSTRUCT //extends AncientData
  {
    var cbSize:Int = _ ;			//size of struct
  @Length(8-SID_SIZE)
  var bnSID:Array[Byte] = _ ;
    @Length(SID_SIZE)
    var nID:String = _ ;		//record id

    @Length(16)
    var szUserName:String = _ ;		//user name
  @Length(16)
  var szUnitCode:String = _ ;		//unit code

    var nMatchTPDBID = Array.ofDim[Short](4,2)	//match tenprint db id
  var nMatchLPDBID = Array.ofDim[Short](4,2)	//match latent db id

    var nDownloadTPDBID= Array.ofDim[Short](4,2)	//download from which tp db
  var nDownloadLPDBID=Array.ofDim[Short](4,2)	//download from which lp db

    var nReportTPDBID:Short = _ ;		//report which tp db
  var nReportLPDBID:Short = _ ;		//reprot which lp db

    var nQryDBID:Short = _ ;			//send query to which db

    var nFlag:Byte = _ ;					//falg, not used
  var nFlagEx:Byte = _ ;				//flagex, not used
  // is group or belong to which group [1/15/2007]
  var bIsGroup:Byte = _ ;		//is group ?
  var nGroupID:Int = _ ;	//group id
  @Length(39)
  var bnRes0:Array[Byte] = _ ;				//to here is 128

    @Length(128)
    var bnResData:Array[Byte] = _ ;			//for extend
  } //RMTUSERDBIDSTRUCT;	//size is 256, saved in user db table

  class RMTLOCALDBIDSTRUCT extends AncientData
  {
    var stDefDBID = new GAFIS_DEFDBIDSTRUCT;	//gafis_def, size is 8

    var nRmtQryTPDefDBID:Short = _ ;	//101
  var nRmtQryLPDefDBID:Short = _ ;	//102

    var nRmtTransTPDefDBID:Short = _ ;	//103
  var nRmtTransLPDefDBID:Short = _ ;	//104

    var nStatTPDBID = Array.ofDim[Short](4,2);	//stat tp lib
  var nStatLPDBID =Array.ofDim[Short](4,2);	//stat lp lib

    @Length(128 - 32)
    var bnRes:Array[Byte] = _ ;		//reserved
  } //RMTLOCALDBIDSTRUCT;	//size is 128, saved in txserver.dat

  class RMTCACHEDBIDTID extends AncientData
  {
    var nDBID:Short = _ ;
    var nExfQueTID:Short = _ ;
    var nEditQueTID:Short = _ ;
    var nTTSchQueTID:Short = _ ;
    var nTLSchQueTID:Short = _ ;
    var nTTChkQueTID:Short = _ ;
    var nTLChkQueTID:Short = _ ;
    var nLiveScanQualCheckEditQue:Short = _ ;
    @Length(16)
    var bnRes:Array[Byte] = _ ;
  } //RMTCACHEDBIDTID;




}

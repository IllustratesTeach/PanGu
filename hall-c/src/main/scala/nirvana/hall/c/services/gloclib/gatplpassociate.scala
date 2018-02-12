package nirvana.hall.c.services.gloclib

import nirvana.hall.c.annotations.Length
import nirvana.hall.c.services.{AncientData, SID_SIZE}
import nirvana.hall.c.services.gbaselib.gbasedef.AFISDateTime

/**
  * Created by yuchen on 2018/2/5.
  */
object gatplpassociate {

    class GAFIS_TPLP_ASSOCIATE extends AncientData{

      var cbSize:Int = _
      var bnRes2:Int = _
      @Length(32)
      var szTPPersonID:String = _
      @Length(32)
      var szLPGroupID:String = _

      var stTPDtid:GADBIDSTRUCT = _
      var stLPDtid:GADBIDSTRUCT = _
      @Length(16)
      var szCreateUserName:Array[Byte] = _
      @Length(16)
      var szCreateUnitCode:Array[Byte] = _
      @Length(16)
      var szUpdateUserName:Array[Byte] = _
      @Length(16)
      var szUpdateUnitCode:Array[Byte] = _
      @Length(16)
      var szIdentifyUserName:Array[Byte] = _
      @Length(16)
      var szIdentifyUnitCode:Array[Byte] = _

      var tCreateDateTime:AFISDateTime = _
      var tUpdateDateTime:AFISDateTime = _
      var tIdentifyDateTime:AFISDateTime = _
      // second part size is 96+24=120 bytes long.
      @Length(SID_SIZE)
      var nSID:Array[Byte] = _
      @Length(8-SID_SIZE)
      var bnRes_SID:Array[Byte] = _
      // to here is 128+80 bytes long.
      @Length(128-80)
      var bnRes3:Array[Byte] = _

    }//GAFIS_TPLP_ASSOCIATE;	// 256 bytes long.

  class GADBIDSTRUCT extends AncientData{
    var nDBID:Short = _
    var nTableID:Short = _
  } //GADBIDSTRUCT;	// size is 4 bytes
}

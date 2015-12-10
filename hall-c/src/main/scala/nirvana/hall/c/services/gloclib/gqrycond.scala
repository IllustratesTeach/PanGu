package nirvana.hall.c.services.gloclib

import nirvana.hall.c.annotations.{Length, IgnoreTransfer}
import nirvana.hall.c.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-09
 */
object gqrycond {

  // the following structure is related with xgw
  class GAFIS_XGWQRYCOND extends AncientData
  {
    var cbSize:Int = 64 ;	// size of this structure.
  var nMajorVer:Byte = 1 ;	// version, must be 1
  var nMinorVer:Byte = 0 ;	// version, must be 0.
  var nMntFormat:Byte = gadbprop.GAFIS_MNTFORMAT_XGW.asInstanceOf[Byte] ;	// mntformat. GAFIS_MNTFORMAT_XGW
  @Length(1)
  var bnRes0:Array[Byte] = _ ;	// to here is 8 bytes long.
  var nMntMatchType:Byte = _ ;	// [0, 5], higher # means need match more mnt
  // MATCHDATASTRUCT::MaMth_Minutia
  var nDistore:Byte = _ ;		// [0, 5], higher # means larger distort, describe the latent finger/palm
  // only(for lt describe the source data, for tl describe the target).
  // MATCHDATASTRUCT::MaMth_Distore
  var nLocStructureMatchType:Byte = _ ;	// [0, 5] higher # means more exact match.
  // MATCHDATASTRUCT::MaMth_LocStructure
  var bDisableEnhFeat:Byte = _ ;		// whether disable enhance feature.
  // MATCHDATASTRUCT::MaMth_MaskEnhFeat
  var nAngleVar:Short = _ ;			// direction variation of finger or palm.
  // MATCHDATASTRUCT::AngleVar
  var nScale0:Byte = _ ;				// MATCHDATASTRUCT::Scale0
  var nScale1:Byte = _ ;		// to here is 16 bytes long MATCHDATASTRUCT::Scale1
  // nScale/100.0 is the actual scale. so it's fixed pt float.
  // for one float, f, nScale=f*100;
  var bFullMatchOn:Byte = _ ;	// added on Nov. 19, 2004. MATCHDATASTRUCT::FullMatchOn
  var bMorphAccuUse:Byte = _ ;	// MATCHDATASTRUCT::MaMth_MorphAccuUse.
  @Length(46)
  var bnRes:Array[Byte] = _ ;			// reserved.
  } // GAFIS_XGWQRYCOND;	// size is 64 bytes long.

  class GAFIS_LCWQRYCOND extends AncientData
  {
    var cbSize:Int = 64 ;	// size of this structure.
  var nMajorVer:Byte = 0 ;	// version, must be 0
  var nMinorVer:Byte = 0 ;	// version, must be 0.
  var nMntFormat:Byte = _ ;	// mntformat. GAFIS_MNTFORMAT_LCW
  @Length(1)
  var bnRes0:Array[Byte] = _ ;	// to here is 8 bytes long.
  @Length(56)
  var bnRes:Array[Byte] = _ ;			// reserved.
  } // GAFIS_LCWQRYCOND;	// size is 64 bytes long.

  class GAFIS_KEYITEM extends AncientData
  {
    @Length(32)
    var szKey:String = _ ;
    var nDBID:Short = _ ;
    var nTID:Short = _ ;
    @Length(4)
    var bnRes:Array[Byte] = _ ;
  } // GAFIS_KEYITEM;	// size is 40 bytes long

  class GAFIS_KEYLISTSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure.
  @Length(2)
  var bnRes:Array[Byte] = _ ;
    var nKeyCount:Short = _ ;	// # of keys.
  @Length(1)
  var stKey:Array[GAFIS_KEYITEM] = _;	// store key self.
  } // GAFIS_KEYLISTSTRUCT;	// size is 8+sizeof(GAFIS_KEYITEM)*nKeyCount.

  class GAFIS_QRYPARAM extends AncientData
  {
    val stXgw:GAFIS_XGWQRYCOND	 = new GAFIS_XGWQRYCOND;
    //GAFIS_LCWQRYCOND	stLcw;
  } //GAFIS_QRYPARAM;

  class GAFIS_ITEMLISTSTRUCT extends AncientData
  {
    var cbSize:Int = _ ;		// size of this structure. = nitemsize*nitemcnt+12.
  var nItemSize:Short = _ ;	// size of each item
  var nItemCnt:Short = _ ;	// # of items.
  var bIsWild:Byte = _ ;
    var bIsExcept:Byte = _ ;		// not include those in this list.
  // to here is 10 bytes long.
  var bCanBeFree:Byte = _ ;
    @Length(1)
    var bnRes:Array[Byte] = _ ;
    @Length(4)
    var bnData:Array[Byte] = _ ;
  } // GAFIS_ITEMLISTSTRUCT;	// this structure is at least 16 bytes long.

  // new added on sep. 8, 2004.
  class GAFIS_NONMNTFILTER extends AncientData
  {
    var cbSize:Int = _ ;	// size of this structure.
  var nVersion:Byte = _ ;	// version.
  var nFlag:Byte = _ ;		// currently not used. GAFIS_NMF_FLAG_XXX
  var nItemFlag:Byte = _ ;	// not used.
  var nSearchBrokenFlag:Byte = _ ;	// GAFIS_NMF_SCHFLAG_xx
  // to here is 8 bytes long.
  @Length(8)
  var bnRes:Array[Byte] = _ ;
    var pstGroupID_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstGroupID_Data:Array[GAFIS_ITEMLISTSTRUCT] = _ // for pstGroupID pointer ,struct:GAFIS_ITEMLISTSTRUCT;	// item size is 4.
  var pstGroupName_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstGroupName_Data:Array[GAFIS_ITEMLISTSTRUCT] = _ // for pstGroupName pointer ,struct:GAFIS_ITEMLISTSTRUCT;
  var pstGroupCode_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstGroupCode_Data:Array[GAFIS_ITEMLISTSTRUCT] = _ // for pstGroupCode pointer ,struct:GAFIS_ITEMLISTSTRUCT;
  var pstFingerType_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstFingerType_Data:Array[GAFIS_ITEMLISTSTRUCT] = _ // for pstFingerType pointer ,struct:GAFIS_ITEMLISTSTRUCT;	// person type or finger type.
  var pstCaseID_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstCaseID_Data:Array[GAFIS_ITEMLISTSTRUCT] = _ // for pstCaseID pointer ,struct:GAFIS_ITEMLISTSTRUCT;
  var pbnRes_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pbnRes_Data:Array[Byte] = _ // for pbnRes pointer ,struct:void;
  @Length(4*14)
  var bnRes_Pt:Array[Byte] = _ ;
  } // GAFIS_NONMNTFILTER;	// non mnt filter. 128 bytes long.

  // GAFIS_NONMNTFILTER::nSearchBrokenFlag
  final val GAFIS_NMF_SCHFLAG_NOTUSED = 0
  final val GAFIS_NMF_SCHFLAG_BROKENONLY = 1	// include broken.
  final val GAFIS_NMF_SCHFLAG_UNBROKENONLY = 2	// include unbroken.

  //GAFIS_NONMNTFILTER::nFlag
  final val GAFIS_NMF_FLAG_MATCHSINGLE = 0x1	// match only one item is enough.


}

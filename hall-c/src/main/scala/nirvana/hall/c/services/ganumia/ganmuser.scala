package nirvana.hall.c.services.ganumia

import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.gbaselib.gathrdop.GBASE_CRIT
import nirvana.hall.c.services.gbaselib.gbasedef.GAFIS_UUIDStruct
import nirvana.hall.c.services.gbaselib.paramadm.GBASE_PARAM_MEMITEM
import nirvana.hall.c.services.AncientData

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-23
 */
object ganmuser {

  // same # of NMUSER_PRIVTYPE
  class NMUSER_OBJPRIVITEM extends AncientData
  {
    var nAdd:Byte = _ ;
    var nDel:Byte = _ ;
    var nMod:Byte = _ ;
    var nSelect:Byte = _ ;
  } // NMUSER_OBJPRIVITEM;

  // all privilege in the following structure are object privilege. NMUSER_PRIVVAL type.
  class NMUSER_PRIV_TABLE extends AncientData
  {
    // table.
    var stTable = new NMUSER_OBJPRIVITEM;

    // column
    var stCol = new NMUSER_OBJPRIVITEM;

    // data.
    var stRow = new NMUSER_OBJPRIVITEM;

    // user.
    var stUser = new NMUSER_OBJPRIVITEM;

    @Length(8+16)
    var bnRes2:Array[Byte] = _ ;	// reserved.
  var nDiskQuotaInMB:Int = _ ;		// can not exceed this limit.
  var nRowCountQuotaInK:Int = _ ;	// row quota.
  } // NMUSER_PRIV_TABLE;	// size is 48 bytes long.

  class NMUSER_PRIV_DB extends AncientData
  {
    // db.
    var stDB = new NMUSER_OBJPRIVITEM;
    // user op
    var stUser = new NMUSER_OBJPRIVITEM;
    @Length(8+16)
    var bnRes2:Array[Byte] = _ ;		// reserved.
  } // NMUSER_PRIV_DB;	// size is 32 bytes long.

  // system privilege
  final val NMPRIV_SYS_ISADMINISTRATOR = 0x1	// is administrator.
  final val NMPRIV_SYS_CREATEDB = 0x2	// can create db.


  // the privilege in the following structure is global structure. and stored in useraccount
  // table.
  class NMUSER_PRIV_SYS extends AncientData
  {
    var nSysPriv:Byte = _ ;		// NMPRIV_SYS_
  @Length(3)
  var bnRes:Array[Byte] = _ ;

    var stSysUser = new NMUSER_OBJPRIVITEM;
    // to here is 8 bytes long.

    // for user's object using stDB's user property.
    // database object. no db create, that's system privilege.
    var stDB = new NMUSER_PRIV_DB;		// 32 bytes long.

    // table object.
    var stTable = new NMUSER_PRIV_TABLE;	// 48 bytes long.

    var nUID:Short = _ ;		// user id.
  var nGrpCnt:Byte = _ ;
    var nRoleCnt:Byte = _ ;
    @Length(4)
    var nGroupID:Short = _ ;	// max 4 groups.
  @Length(6)
  var nRoleID:Short= _ ;	// max 6 roles.
  // to here is 96+16 bytes long.
  var stUserUUID = new GAFIS_UUIDStruct;		// uuid of the user.
  @Length(64)
  var bnRes2:Array[Byte] = _ ;
  } // NMUSER_PRIV_SYS;	// size is 128 +64 bytes long.

  // info for gadb.
  class NMUSER_INFOSTRUCT extends AncientData
  {
    @Length(16)
    var szUserName:String = _ ;	// user name.
  // to here 16 bytes long.
  var stPriv = new NMUSER_PRIV_SYS;			// some other info.
  @Length(48)
  var bnRes:Array[Byte] = _ ;
  } // NMUSER_INFOSTRUCT;	// 256 bytes long.

  class NMUSER_DBPRIV_ENTRY extends AncientData
  {
    var cbSize:Int = _ ;	// size of this entry.
  var nUID:Short = _ ;	// user id.
  @Length(2+8)
  var bnRes:Array[Byte] = _ ;
    @Length(16)
    var bnRes2:Array[Byte] = _ ;
    // to here is 32 bytes long.
    var stUserUUID = new GAFIS_UUIDStruct;	// user uuid. if we copy one db to another place
  // then the user
  var stDB = new NMUSER_PRIV_DB;
    // to here is 80 bytes long.
    var stTable = new NMUSER_PRIV_TABLE;
  } // NMUSER_DBPRIV_ENTRY;	// 128 bytes long.

  class NMUSER_TABLEPRIV_ENTRY extends AncientData
  {
    var cbSize:Int = _ ;	// size of this entry.
  var nUID:Short = _ ;	// user id.
  @Length(8+2)
  var bnRes:Array[Byte] = _ ;
    var stUserUUID = new GAFIS_UUIDStruct;
    var stTable = new NMUSER_PRIV_TABLE;
    @Length(48)
    var bnRes2:Array[Byte] = _ ;
  } // NMUSER_TABLEPRIV_ENTRY;	// 128 bytes long.

  class NMUSER_OBJPRIV extends AncientData
  {
    var stCrit = new GBASE_CRIT;
    @Length(120)
    var stGa:Array[Byte] = _ //new GARRAY_STRUCT;
    var bIsTable:Int = _;
    @Length(28)
    var bnRes:Array[Byte] = _ ;
  } // NMUSER_OBJPRIV;	// 128 +32 bytes long.

  class NMUSER_ADM extends AncientData
  {
    var stCrit = new GBASE_CRIT;
    var pstUser_Ptr:Int = _ //using 4 byte as pointer
  @IgnoreTransfer
  var pstUser_Data:Array[NMUSER_INFOSTRUCT] = _ // for pstUser pointer ,struct:NMUSER_INFOSTRUCT;
  @Length(4)
  var bnRes_Pt:Array[Byte] = _ ;
    var nUserCnt:Int = _;
    var nUserBufCnt:Int = _;
  } // NMUSER_ADM;

  class NMUSER_VAR extends AncientData
  {
    var bEnableInnerUser:Int = _;	// enable inner user.
  var bNotCheckUserPriv:Int = _;	// in some mode, we does not check user privilege(eg, startup).
  var bNotSetPrivAsEnable:Int = _;	// if priv not set, then default is disable.
  var stCrit = new GBASE_CRIT;

    @Length(8)
    var stMemItem:Array[GBASE_PARAM_MEMITEM] = _;	// parameters for user.

    var stAdm = new NMUSER_ADM;	// user administration.
  } // NMUSER_VAR;




  //form ganmuser.c
  def NMUSER_COL_GetUserName():String=
  {
    "UserName";
  }

  def NMUSER_COL_GetPrivName():String=
  {
    "NMUserPriv";
  }

}

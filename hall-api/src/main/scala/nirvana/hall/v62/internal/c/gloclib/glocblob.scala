package nirvana.hall.v62.internal.c.gloclib

import nirvana.hall.c.annotations.{IgnoreTransfer, Length}
import nirvana.hall.c.services.AncientData

/**
  * Created by songpeng on 2017/11/13.
  */
object glocblob {
  /*
   * glocblob.h : tenprint operations
   * Creation Date : July.1, 2002
   * Copyright(c) 1996-2002
   */
  // when geting we need set
  // cbSize, nItemCount, szColName
  // on return pnOffset, pData, pnItemLen and pnRetVal will be set,
  // 0 means failure to get data on that key, >=0 success, 0 means that key for that column
  // is null
  // when updating, we need set
  // cbSize, nItemCount, szColName, pData, pnOffset, pnItemLen
  // to set and item to null, set pnItemLen to 0
  // on return pnRetVal will be set
  class GAFIS_MULTICOLDATASTRUCT extends AncientData {
    var cbSize: Int = _;
    // size of this structure
    var nItemCount: Int = _;
    // how many items stored in this structure
    @Length(32)
    var szColName: String = _;
    // column name
    var pnOffset: Int = _;
    // offset, count is nItemCount+1, last value
    // if length of pData
    var pData_Ptr: Int = _
    //using 4 byte as pointer
    @IgnoreTransfer
    var pData_Data: Array[Byte] = _
    // for pData pointer ,struct:UCHAR;			// pointer to data
    var pnItemLen: Int = _;
    // pointer to item length flag
    // if pnItemLen[i] is 0, then item is NULL
    var pnRetVal: Int = _;
    // return values, get or update success or failure
    @Length(4 * 4)
    var bnRes_Pointer: Array[Byte] = _;
    var nDataLen: Int = _;
    // data length, used for transmitting on network
    var bOffsetCanBeFree: Byte = _;
    var bItemLenCanBeFree: Byte = _;
    var bDataCanBeFree: Byte = _;
    var bRetValCanBeFree: Byte = _;
    @Length(48)
    var bnRes: Array[Byte] = _; // reserved
  } // GAFIS_MULTICOLDATASTRUCT;	// size of this structure is 128
}

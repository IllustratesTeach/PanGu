package nirvana.hall.v62.internal

import nirvana.hall.v62.annotations.Length
import nirvana.hall.v62.services.AncientData

/**
 * 640 byte
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-11-02
 */
class GafisError extends AncientData{
    var	cbSize:Int = _ 			// size of this structure
    var	nAFISErrno:Int = _ 		// AFIS internal errno
    var	nSYSErrno:Int = _ 		// operation system errno
    var	nLineNum:Short= _ ;		// file line no, 2 bytes int
    var	nFileCount:Byte = _;			// how many files followed
    var	nErrDataLen:Byte = _;		// err data len
    @Length(32)
    var szFileName:String = _		// file name when this error was generated, to here is 48 bytes
    var	bIsOSError:Byte = _;			// whether is os error or not
    var	bnRes:Byte = _			// reserved,
    var	nSubErrno:Short = _;		// sub error code.
    var	nWinErr:Int = _ 			// to here is 56 bytes
    @Length(96)
    var	bnAFISErrData:String = _ 	// length is 96 bytes, to here 152 bytes
    @Length(60)
    var	szSysErrStr:String = _ 	// 60 bytes error string

    @Length(14)
    var stFileList:Array[tagFILENLSTRUCT] = _ 	// total size is 280 bytes
    @Length(8)
    var bnErrOccureTime:String = _ 	// error occur date time. format is AFISDateTime.
    @Length(12)
    var bnRes2:String = _ 			// reserved.
    @Length(16)
    var szHostName:String = _ 		// computer name.
    @Length(64)
    var szEnvInfo:String = _
    @Length(48)
    var bnAFISErrData2:String = _ 	// 2006.01.06 we found 96 bytes to store error specific data is
    // not enough, so we expand error data size.
}
class tagFILENLSTRUCT extends AncientData{
    var	nLineNum:Short = _ 	// can not exceed 65536 lines
    @Length(18)
    var	sFileName:String = _ 	// maximum 18 characters
} 	// size is 20 bytes


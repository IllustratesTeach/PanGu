#ifndef MANUFACTORY_WIN32_H_
#define MANUFACTORY_WIN32_H_
#ifdef WIN32
#include <Windows.h>

#define GFP_FPT_DECOMPRESS_DLL				"FPT_DC%s.DLL"
#define GFP_FPT_DC_PROCNAME				"FPT_DC%s"

#define GFP_FPT_GA10_DCDLLNAME				"FPT_DC00.dll"
#define GFP_FPT_GA10_DCPROCNAME			"FPT_Decompress"

#define GFP_FPT_GA10_CPDLLNAME				"FPT_CP00.dll"
#define GFP_FPT_GA10_CPPROCNAME			"FPT_Compress"


//define function type
typedef int (PASCAL *GA_FPT_DCXX)(
unsigned char	code[4],
unsigned char	*cp_data,
int			length,
unsigned char	*img,
unsigned char	buf[256]
);
typedef int (*GFP_FPT_BUPT_DCXX)(
    unsigned char	code[4],
    unsigned char	*cp_data,
    int			length,
    unsigned char	*img,
    unsigned char	buf[256]
);
typedef int	(PASCAL	*GFP_FPT_GA10_DECOMPRESS)(
unsigned char code[4],
unsigned char *pCompressedImg,
int  nCompressedImgLength,
unsigned char *pFingerImg,
int *pnRow,
int *pnCol,
int *pnResolution,
unsigned char strBuf[256]
);



#define	GAIMG_CPRMETHOD_DEFAULT		0	// by xgw supplied method
#define	GAIMG_CPRMETHOD_XGW			1	// by xgw supplied method.
#define GAIMG_CPRMETHOD_XGW_EZW		2	// 许公望的EZW压缩方法：适合低倍率高保真的压缩.
#define GAIMG_CPRMETHOD_GA10		10	// 公安部10倍压缩方法
#define	GAIMG_CPRMETHOD_GFS			19	// golden
#define	GAIMG_CPRMETHOD_PKU			13	// call pku's compress method
#define	GAIMG_CPRMETHOD_COGENT		101	// cogent compress method
#define	GAIMG_CPRMETHOD_WSQ			102	// was method
#define	GAIMG_CPRMETHOD_NEC			103	// nec compress method
#define	GAIMG_CPRMETHOD_TSINGHUA	104	// tsing hua
#define	GAIMG_CPRMETHOD_BUPT		105	// beijing university of posts and telecommunications
#define	GAIMG_CPRMETHOD_RMTZIP		106	// compressmethod provide by communication server(GAFIS)
#define	GAIMG_CPRMETHOD_LCW			107	// compress method provide by lucas wang.
#define	GAIMG_CPRMETHOD_JPG			108	// jpeg method.
#define GAIMG_CPRMETHOD_MORPHO		109	//!< 广东测试时提供的压缩算法，MORPHO(SAGEM)
#define GAIMG_CPRMETHOD_MAXVALUE	109


#endif

#endif //MANUFACTORY_WIN32_H_

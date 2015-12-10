/**
 * @file	gfimglib.h
 * @brief	封装了wsq、jpeg、tiff等图像格式的编码和解码
 * @author	北京东方金指科技有限公司
 * @date	2010/08/20 15:16:27
 * @note	
 * @copyright	版权所有(@) 1996-2010
 *				北京东方金指科技有限公司
 */


#ifndef _INC_GFIMGLIB_H_20100820151656_
#define _INC_GFIMGLIB_H_20100820151656_

#include "gbaselib/osdepend.h"
#include <stdlib.h>
#include <stdio.h>
#include <stdarg.h>
#include <malloc.h>

#ifdef	__cplusplus
extern	"C"	{
#endif

/**
 * 返回的错误值
 */
#define GFIMGLIB_ERROR_SUCCESS		1		//!< 调用成功
#define GFIMGLIB_ERROR_PARAMETER	-1		//!< 参数错误
#define GFIMGLIB_ERROR_MEMORY		-2		//!< 内存错误

#define GFIMGLIB_ERROR_WSQSTART		-1000	//!< WSQ解压缩算法返回的错误值的起始值，返回值减去该值就是内部WSQ返回的错误代码
#define GFIMGLIB_ERROR_JPGSTART		-2000	//!< JPEG解压缩算法返回的错误值的起始值，返回值减去该值就是内部JPEG返回的错误代码

/**
 * WSQ 压缩、解压缩
 */
extern	int
gfimglib_wsq_encode(unsigned char *input_buffer,		//!< input buffer (1-d, stream format)
					int width, int height,				//!< the pixel width and height of the image
					int nddi, /*int ndepth,*/			//!< 分辨率(nddi)、位深度(ndepth)
					int nRatio,							//!< compression ratio，压缩赔率，范围为[2, 50]
					unsigned char **compress_buffer,	//!< pointer to a compressed data buffer
					int *compressed_size,				//!< number of bytes in the compressed buffer
					float *ratio_achieved				//!< pointer to the compression ratio achieved, can be NULL
					);



extern	int
gfimglib_wsq_decode(unsigned char *compress_buffer,		//!< compressed data buffer (1-d stream)
					int compressed_size,				//!< number of bytes in the compressed buffer
					int *width, int *height, int *ppi,	//!< 宽度、高度和分辨率
					unsigned char **output_buffer,		//!< pointer to decompressed image buffer
					int *output_size					//!< number of bytes in output buffer, can be null
					);

extern	int
gfimglib_wsq_decodedinfo(unsigned char *compress_buffer,	//!< compressed data buffer (1-d stream)
						 int compressed_size,				//!< number of bytes in the compressed buffer
						 int *width, int *height, int *ppi	//!< 宽度、高度和分辨率
						);


/**
 * JPEG有损压缩、解压缩
 */
extern	int
gfimglib_jpeg_encode(unsigned char *input_buffer,		//!< input buffer (1-d, stream format)
					 int width, int height,				//!< the pixel width and height of the image
					 int nddi, int ndepth,				//!< 分辨率(nddi)、位深度(ndepth，值为8或24)
					 int nQuality,						//!< quantization table [1 -- 100], 缺省为75
					 unsigned char **compress_buffer,	//!< pointer to a compressed data buffer
					 int *compressed_size				//!< number of bytes in the compressed buffer
					);


extern	int
gfimglib_jpeg_decode(unsigned char *compress_buffer,	//!< compressed data buffer (1-d stream)
					 int compressed_size,				//!< number of bytes in the compressed buffer
					 int *width, int *height,			//!< 宽度、高度
					 int *depth, int *ppi,				//!< 位深度(8，24)、分辨率
					 unsigned char **output_buffer		//!< pointer to decompressed image buffer
					);


extern	int
gfimglib_jpeg_decodedinfo(unsigned char *compress_buffer,	//!< compressed data buffer (1-d stream)
						 int compressed_size,				//!< number of bytes in the compressed buffer
						 int *width, int *height,			//!< 宽度、高度
						 int *depth, int *ppi				//!< 位深度(8，24)、分辨率
						 );

/**
 * 释放动态库申请的内存
 */
extern	void
gfimglib_free(void** buffer);

#ifdef	__cplusplus
}
#endif


#endif	// _INC_GFIMGLIB_H_20100820151656_

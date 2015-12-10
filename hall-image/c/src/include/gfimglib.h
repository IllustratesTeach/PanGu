/**
 * @file	gfimglib.h
 * @brief	��װ��wsq��jpeg��tiff��ͼ���ʽ�ı���ͽ���
 * @author	����������ָ�Ƽ����޹�˾
 * @date	2010/08/20 15:16:27
 * @note	
 * @copyright	��Ȩ����(@) 1996-2010
 *				����������ָ�Ƽ����޹�˾
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
 * ���صĴ���ֵ
 */
#define GFIMGLIB_ERROR_SUCCESS		1		//!< ���óɹ�
#define GFIMGLIB_ERROR_PARAMETER	-1		//!< ��������
#define GFIMGLIB_ERROR_MEMORY		-2		//!< �ڴ����

#define GFIMGLIB_ERROR_WSQSTART		-1000	//!< WSQ��ѹ���㷨���صĴ���ֵ����ʼֵ������ֵ��ȥ��ֵ�����ڲ�WSQ���صĴ������
#define GFIMGLIB_ERROR_JPGSTART		-2000	//!< JPEG��ѹ���㷨���صĴ���ֵ����ʼֵ������ֵ��ȥ��ֵ�����ڲ�JPEG���صĴ������

/**
 * WSQ ѹ������ѹ��
 */
extern	int
gfimglib_wsq_encode(unsigned char *input_buffer,		//!< input buffer (1-d, stream format)
					int width, int height,				//!< the pixel width and height of the image
					int nddi, /*int ndepth,*/			//!< �ֱ���(nddi)��λ���(ndepth)
					int nRatio,							//!< compression ratio��ѹ�����ʣ���ΧΪ[2, 50]
					unsigned char **compress_buffer,	//!< pointer to a compressed data buffer
					int *compressed_size,				//!< number of bytes in the compressed buffer
					float *ratio_achieved				//!< pointer to the compression ratio achieved, can be NULL
					);



extern	int
gfimglib_wsq_decode(unsigned char *compress_buffer,		//!< compressed data buffer (1-d stream)
					int compressed_size,				//!< number of bytes in the compressed buffer
					int *width, int *height, int *ppi,	//!< ��ȡ��߶Ⱥͷֱ���
					unsigned char **output_buffer,		//!< pointer to decompressed image buffer
					int *output_size					//!< number of bytes in output buffer, can be null
					);

extern	int
gfimglib_wsq_decodedinfo(unsigned char *compress_buffer,	//!< compressed data buffer (1-d stream)
						 int compressed_size,				//!< number of bytes in the compressed buffer
						 int *width, int *height, int *ppi	//!< ��ȡ��߶Ⱥͷֱ���
						);


/**
 * JPEG����ѹ������ѹ��
 */
extern	int
gfimglib_jpeg_encode(unsigned char *input_buffer,		//!< input buffer (1-d, stream format)
					 int width, int height,				//!< the pixel width and height of the image
					 int nddi, int ndepth,				//!< �ֱ���(nddi)��λ���(ndepth��ֵΪ8��24)
					 int nQuality,						//!< quantization table [1 -- 100], ȱʡΪ75
					 unsigned char **compress_buffer,	//!< pointer to a compressed data buffer
					 int *compressed_size				//!< number of bytes in the compressed buffer
					);


extern	int
gfimglib_jpeg_decode(unsigned char *compress_buffer,	//!< compressed data buffer (1-d stream)
					 int compressed_size,				//!< number of bytes in the compressed buffer
					 int *width, int *height,			//!< ��ȡ��߶�
					 int *depth, int *ppi,				//!< λ���(8��24)���ֱ���
					 unsigned char **output_buffer		//!< pointer to decompressed image buffer
					);


extern	int
gfimglib_jpeg_decodedinfo(unsigned char *compress_buffer,	//!< compressed data buffer (1-d stream)
						 int compressed_size,				//!< number of bytes in the compressed buffer
						 int *width, int *height,			//!< ��ȡ��߶�
						 int *depth, int *ppi				//!< λ���(8��24)���ֱ���
						 );

/**
 * �ͷŶ�̬��������ڴ�
 */
extern	void
gfimglib_free(void** buffer);

#ifdef	__cplusplus
}
#endif


#endif	// _INC_GFIMGLIB_H_20100820151656_

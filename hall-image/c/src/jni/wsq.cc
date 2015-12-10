#include "nirvana_hall_image_jni_NativeImageConverter.h"
#include <jni.h>
#include <stdlib.h>
#include <string.h>

#include "../include/wsq.h"
#include "jni_helper.h"

float	UTIL_GetWsqEncodeBitRate(int nRatio)
{
	/**
	 * 下面的转换是根据NIST提供的WSQ算法的相关描述产生的
	 * Suggested settings:
	 *	bitrate = 2.25 yields around 5:1 compression
	 *	bitrate = 0.75 yields around 15:1 compression
	 */

	float fratio;

	if ( nRatio < 2 ) nRatio = 2;
	else if ( nRatio > 50 ) nRatio = 50;

	fratio = 15.0f / nRatio * 0.75f;

	return fratio;
}


/*
int
gfimglib_wsq_decode(unsigned char *compress_buffer,		//!< compressed data buffer (1-d stream)
					int compressed_size,				//!< number of bytes in the compressed buffer
					int *width, int *height, int *ppi,	//!< 宽度、高度和分辨率
					unsigned char **output_buffer,		//!< pointer to decompressed image buffer
					int *output_size					//!< number of bytes in output buffer, can be null
					)
*/
JNIEXPORT jbyteArray JNICALL Java_nirvana_hall_image_jni_NativeImageConverter_decodeByWSQ
  (JNIEnv *jenv, jclass, jbyteArray compressed_img, jint width, jint height, jint ppi)
{
	int		retval, ndepth;

  int compressed_size = jenv->GetArrayLength(compressed_img);
  UCHAR* compressed_img_bin = (UCHAR*)jenv->GetByteArrayElements(compressed_img, NULL);

  int dest_img_size = width * height;
  jbyteArray dest_img = jenv->NewByteArray(dest_img_size);
  UCHAR* dest_img_bin = (UCHAR*)jenv->GetByteArrayElements(dest_img, NULL);
	ndepth = 8;
	retval = wsq_decode_mem(&dest_img_bin, (int*)&width, (int*)&height, &ndepth, (int*)&ppi, NULL, compressed_img_bin, compressed_size);
	jenv->ReleaseByteArrayElements(compressed_img,(jbyte*)compressed_img_bin,JNI_ABORT);
	//force commit data
	jenv->ReleaseByteArrayElements(dest_img,(jbyte*)dest_img_bin,JNI_COMMIT);
	if(retval != 0) {
		SWIG_JavaThrowExceptionByCode(jenv, SWIG_JavaArithmeticException, retval);
	}
	return dest_img;

}

JNIEXPORT jbyteArray JNICALL Java_nirvana_hall_image_jni_NativeImageConverter_encodeByWSQ
		(JNIEnv *jenv, jclass, jbyteArray input_buffer, jint width, jint height, jint ppi,jint ratio){
	int		retval, ndepth;
	float	fRatio;


	ndepth = 8;

	UCHAR* original_img_bin = (UCHAR*)jenv->GetByteArrayElements(input_buffer,NULL);

	int compressed_img_size=0;
	//TODO size too large?
	UCHAR * compressed_img_bin = (UCHAR*)malloc(width * height);


	fRatio = UTIL_GetWsqEncodeBitRate((int)ratio);
	retval = wsq_encode_mem(&compressed_img_bin, &compressed_img_size, fRatio,
													original_img_bin, width, height, ndepth, ppi, NULL);

	if ( retval != 0 )
	{
		free(compressed_img_bin);
		SWIG_JavaThrowExceptionByCode(jenv,SWIG_JavaArithmeticException,retval);
	}
	jbyteArray compressed_img = jenv->NewByteArray(compressed_img_size);
	jenv->SetByteArrayRegion(compressed_img,0,compressed_img_size,(jbyte*)compressed_img_bin);
	free(compressed_img_bin);

	return compressed_img;
}




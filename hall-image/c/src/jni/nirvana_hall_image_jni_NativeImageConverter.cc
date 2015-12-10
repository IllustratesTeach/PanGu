#include "nirvana_hall_image_jni_NativeImageConverter.h"
#include <jni.h>
#include <stdlib.h>
#include <string.h>

#include "../include/wsq.h"
#include "jni_helper.h"


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
	jenv->ReleaseByteArrayElements(dest_img,(jbyte*)dest_img_bin,JNI_ABORT);
	if(retval != 0)
		SWIG_JavaThrowExceptionByCode(jenv,SWIG_JavaArithmeticException,retval);
	  return dest_img;

}



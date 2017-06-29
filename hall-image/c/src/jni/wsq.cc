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
 * Class:     nirvana_hall_image_jni_NativeImageConverter
 * Method:    decodeByWSQ
 * Signature: ([B)Lnirvana/hall/image/jni/OriginalImage;
 */
JNIEXPORT jobject JNICALL Java_nirvana_hall_image_jni_NativeImageConverter_decodeByWSQ
    (JNIEnv * jenv, jclass, jbyteArray compressed_img) {
  
  if(compressed_img == NULL){
    SWIG_JavaThrowException(jenv,SWIG_JavaIllegalArgumentException,"compressed image is null");
    return NULL;
  }

	int		retval, ndepth;
  int   width,height,ppi;

  //find original image info
  int compressed_size = jenv->GetArrayLength(compressed_img);
  UCHAR* compressed_img_bin = (UCHAR*)jenv->GetByteArrayElements(compressed_img, JNI_FALSE);
  retval = wsq_decodedinfo(&width, &height, &ppi, compressed_img_bin, compressed_size);
  if(retval !=0){
    jenv->ReleaseByteArrayElements(compressed_img, (jbyte *) compressed_img_bin, JNI_ABORT);
    SWIG_JavaThrowExceptionByCode(jenv, SWIG_JavaArithmeticException, retval);
    return NULL;
  }else {
    //construct original image buffer
    int dest_img_size = width * height;
    UCHAR *dest_img_bin = NULL;
    ndepth = 8;
    retval = wsq_decode_mem(&dest_img_bin, &width, &height, &ndepth, &ppi, NULL,
                            compressed_img_bin, compressed_size);
    //release compressed image
    jenv->ReleaseByteArrayElements(compressed_img, (jbyte *) compressed_img_bin, JNI_ABORT);
    if (retval != 0) {
      SWIG_JavaThrowExceptionByCode(jenv, SWIG_JavaArithmeticException, retval);
      return NULL;
    }
    jbyteArray dest_img = jenv->NewByteArray(dest_img_size);
    //copy original image data to dest image
    if(dest_img_bin != NULL) {
      jenv->SetByteArrayRegion(dest_img, 0, dest_img_size, (jbyte *) dest_img_bin);
      free(dest_img_bin);
    }

    //build OriginalImage object
    jclass originalImageClass = jenv->FindClass("nirvana/hall/image/jni/OriginalImage");
    jmethodID constructorId = jenv->GetMethodID(originalImageClass,"<init>","()V");
    jobject originalImage = jenv->NewObject(originalImageClass,constructorId);

    jmethodID widthMethod = jenv->GetMethodID(originalImageClass,"setWidth","(I)V");
    jenv->CallVoidMethod(originalImage,widthMethod,width);

    jmethodID heightMethod = jenv->GetMethodID(originalImageClass,"setHeight","(I)V");
    jenv->CallVoidMethod(originalImage,heightMethod,height);

    jmethodID dataMethod = jenv->GetMethodID(originalImageClass,"setData","([B)V");
    jenv->CallVoidMethod(originalImage,dataMethod,dest_img);

    jmethodID ppiMethod = jenv->GetMethodID(originalImageClass,"setPpi","(I)V");
    jenv->CallVoidMethod(originalImage,ppiMethod,ppi);

    return originalImage;
  }

}

JNIEXPORT jbyteArray JNICALL Java_nirvana_hall_image_jni_NativeImageConverter_encodeByWSQ
		(JNIEnv *jenv, jclass, jbyteArray input_buffer, jint width, jint height, jint ppi,jint ratio){
	int		retval, ndepth;
	float	fRatio;


	ndepth = 8;

	UCHAR* original_img_bin = (UCHAR*)jenv->GetByteArrayElements(input_buffer,NULL);
	int compressed_img_size=0;
	UCHAR * compressed_img_bin = NULL;


	fRatio = UTIL_GetWsqEncodeBitRate((int)ratio);
	retval = wsq_encode_mem(&compressed_img_bin, &compressed_img_size, fRatio,
													original_img_bin, width, height, ndepth, ppi, NULL);

  //release input buffer
  jenv->ReleaseByteArrayElements(input_buffer,(jbyte*)original_img_bin,JNI_ABORT);
	if ( retval != 0 ){
		SWIG_JavaThrowExceptionByCode(jenv,SWIG_JavaArithmeticException,retval);
    return NULL;
	}else{
    if(compressed_img_bin != NULL) {
      jbyteArray compressed_img = jenv->NewByteArray(compressed_img_size);
      jenv->SetByteArrayRegion(compressed_img, 0, compressed_img_size, (jbyte *) compressed_img_bin);
      free(compressed_img_bin);
      return compressed_img;
    }
  }
  return NULL;
}




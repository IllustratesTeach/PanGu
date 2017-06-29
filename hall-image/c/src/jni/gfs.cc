#include "nirvana_hall_image_jni_NativeImageConverter.h"

#include "nirvana/kernel.h"
#include "jni_helper.h"

#include <jni.h>
#ifndef DISABLE_GFS
/*
 * Class:     nirvana_hall_image_jni_NativeImageConverter
 * Method:    GAFIS_CompressIMG
 * Signature: ([B[BII)I
 */
JNIEXPORT jint JNICALL Java_nirvana_hall_image_jni_NativeImageConverter_GAFIS_1CompressIMG
  (JNIEnv *jenv, jclass, jbyteArray original_img, jbyteArray compressed_img, jint cprmethod, jint compress_ratio){

    UCHAR* original_img_bin = (UCHAR*)jenv->GetByteArrayElements(original_img, JNI_FALSE);
    GAFISIMAGESTRUCT* original =(GAFISIMAGESTRUCT*)(original_img_bin);

    UCHAR* compressed_img_bin = (UCHAR*)jenv->GetByteArrayElements(compressed_img, JNI_FALSE);
    GAFISIMAGESTRUCT* cpr =(GAFISIMAGESTRUCT*)(compressed_img_bin);

    int ret = GAFIS_CompressIMG(cpr,original,cprmethod,compress_ratio);

    //release compressed image
    jenv->ReleaseByteArrayElements(original_img, (jbyte *) original_img_bin, JNI_ABORT);
    jenv->ReleaseByteArrayElements(compressed_img, (jbyte *) compressed_img_bin, 0);
    if(ret != 1)
      SWIG_JavaThrowExceptionByCode(jenv, SWIG_JavaArithmeticException, ret);
    return ret;
}

/*
 * Class:     nirvana_hall_image_jni_NativeImageConverter
 * Method:    GAFIS_DecompressIMG
 * Signature: ([B[B)I
 */
JNIEXPORT jint JNICALL Java_nirvana_hall_image_jni_NativeImageConverter_GAFIS_1DecompressIMG
  (JNIEnv *jenv, jclass, jbyteArray compressed_img, jbyteArray original_data){
    UCHAR* compressed_img_bin = (UCHAR*)jenv->GetByteArrayElements(compressed_img, JNI_FALSE);
    GAFISIMAGESTRUCT* cpr =(GAFISIMAGESTRUCT*)(compressed_img_bin);

    UCHAR* original_img_bin = (UCHAR*)jenv->GetByteArrayElements(original_data, JNI_FALSE);
    GAFISIMAGESTRUCT* original_img =(GAFISIMAGESTRUCT*)(original_img_bin);

    int ret = GAFIS_DecompressIMG(cpr,original_img);

    //release compressed image
    jenv->ReleaseByteArrayElements(compressed_img, (jbyte *) compressed_img_bin, JNI_ABORT);
    jenv->ReleaseByteArrayElements(original_data, (jbyte *) original_img_bin, 0);
    if(ret != 1)
      SWIG_JavaThrowExceptionByCode(jenv, SWIG_JavaArithmeticException, ret);

      return ret;
}

#endif

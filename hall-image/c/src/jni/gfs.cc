#include "nirvana_hall_image_jni_NativeImageConverter.h"

#include "nirvana/kernel.h"
#include "jni_helper.h"

#include <jni.h>
#ifdef DISABLE_GFS
JNIEXPORT void JNICALL Java_nirvana_hall_image_jni_NativeImageConverter_decodeByGFS
   (JNIEnv *jenv, jclass, jbyteArray compressed_img, jbyteArray original_data){

  UCHAR* compressed_img_bin = (UCHAR*)jenv->GetByteArrayElements(compressed_img, JNI_FALSE);
  GAFISIMAGESTRUCT* cpr =(GAFISIMAGESTRUCT*)(compressed_img_bin);
  FPT_datatranform(cpr);

  UCHAR* original_img_bin = (UCHAR*)jenv->GetByteArrayElements(original_data, JNI_FALSE);
  GAFISIMAGESTRUCT* original_img =(GAFISIMAGESTRUCT*)(original_img_bin);

  int ret = GAFIS_DecompressIMG(cpr,original_img);

  //release compressed image
  jenv->ReleaseByteArrayElements(compressed_img, (jbyte *) compressed_img_bin, JNI_ABORT);
  jenv->ReleaseByteArrayElements(original_data, (jbyte *) original_img_bin, 0);
  if(ret != 1)
    SWIG_JavaThrowExceptionByCode(jenv, SWIG_JavaArithmeticException, ret);
}
#endif
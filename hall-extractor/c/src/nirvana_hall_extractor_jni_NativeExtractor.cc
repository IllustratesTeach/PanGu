#include <string.h>
#include "nirvana_hall_extractor_jni_NativeExtractor.h"

#include "../../../hall-image/c/src/jni/jni_helper.h"

/*
 * Class:     nirvana_hall_extractor_jni_NativeExtractor
 * Method:    ExtractMNT_All
 * Signature: ([BBB)[B
 */
JNIEXPORT void JNICALL Java_nirvana_hall_extractor_jni_NativeExtractor_ExtractMNT_1All
  (JNIEnv * jenv, jclass, jbyteArray img_bytes,jbyteArray mnt_bytes,jbyte finger_pos,jbyte ExtractMode, jbyte belatent) {
  if (img_bytes == NULL) {
    SWIG_JavaThrowException(jenv, SWIG_JavaIllegalArgumentException, "image data is null");
    return;
  }
  if (mnt_bytes == NULL) {
    SWIG_JavaThrowException(jenv, SWIG_JavaIllegalArgumentException, "feature output is null");
    return;
  }

  UCHAR *img_data = (UCHAR *) jenv->GetByteArrayElements(img_bytes, JNI_FALSE);
  GAFISIMAGESTRUCT *img = (GAFISIMAGESTRUCT *) img_data;
  img->stHead.nFingerIndex = (UCHAR) finger_pos;

  UCHAR *mnt_data = (UCHAR *) jenv->GetByteArrayElements(mnt_bytes, JNI_FALSE);
  GAFISIMAGESTRUCT *mnt = (GAFISIMAGESTRUCT *) mnt_data;
  //mnt->stHead.nFingerIndex = (UCHAR) finger_pos;

  XGWMNTEXTRACTSTR mntExtStr = {0};

  mntExtStr.pImage = img;
  mntExtStr.pMnt = mnt->bnData;
  mntExtStr.ExtractMode = (unsigned char) ExtractMode;
  mntExtStr.belatent = (unsigned char) belatent;
  int ret = GAFIS_ExtractMNT_All(&mntExtStr);
  jenv->ReleaseByteArrayElements(img_bytes, (jbyte *) img_data, JNI_ABORT);
  jenv->ReleaseByteArrayElements(mnt_bytes, (jbyte *) mnt_data, 0);
  if (ret != 1) {//success
    SWIG_JavaThrowExceptionByCode(jenv, SWIG_JavaArithmeticException, ret);
  }
}
#ifdef LINUX


/*
 * Class:     nirvana_hall_extractor_jni_NativeExtractor
 * Method:    ExtractMNT_AllWithNewFeature
 * Signature: ([B[BBBB)V
 */
JNIEXPORT void JNICALL Java_nirvana_hall_extractor_jni_NativeExtractor_ExtractMNT_1AllWithNewFeature
    (JNIEnv * jenv, jclass, jbyteArray img_bytes,jbyteArray mnt_bytes,jbyte finger_pos,jbyte ExtractMode, jbyte belatent){
  if(img_bytes == NULL){
    SWIG_JavaThrowException(jenv,SWIG_JavaIllegalArgumentException,"image data is null");
    return;
  }
  if(mnt_bytes == NULL){
    SWIG_JavaThrowException(jenv,SWIG_JavaIllegalArgumentException,"feature output is null");
    return;
  }

  UCHAR* img_data = (UCHAR*)jenv->GetByteArrayElements(img_bytes,JNI_FALSE);
  GAFISIMAGESTRUCT* img= (GAFISIMAGESTRUCT*)img_data;
  img->stHead.nFingerIndex = (UCHAR) finger_pos;

  UCHAR* mnt_data = (UCHAR*)jenv->GetByteArrayElements(mnt_bytes,JNI_FALSE);
  GAFISIMAGESTRUCT* mnt = (GAFISIMAGESTRUCT*)mnt_data;
  //mnt->stHead.nFingerIndex = (UCHAR) finger_pos;

  XGWMNTEXTRACTSTR mntExtStr={0};

  mntExtStr.pImage      = img;
  mntExtStr.pMnt        = mnt->bnData;
  mntExtStr.ExtractMode	= (unsigned char) ExtractMode;
  mntExtStr.belatent    = (unsigned char) belatent;
  int ret = GAFIS_ExtractMNT_All_NewTTFea(&mntExtStr);
  jenv->ReleaseByteArrayElements(img_bytes,(jbyte*)img_data,JNI_ABORT);
  jenv->ReleaseByteArrayElements(mnt_bytes,(jbyte*)mnt_data,0);
  if(ret != 1){//success
    SWIG_JavaThrowExceptionByCode(jenv,SWIG_JavaArithmeticException,ret);
  }
}
#endif


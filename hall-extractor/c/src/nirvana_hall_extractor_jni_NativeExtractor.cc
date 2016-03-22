#include <string.h>
#include <stddef.h>

#include "nirvana_hall_extractor_jni_NativeExtractor.h"

#include "nirvana/kernel.h"

#include "../../../hall-image/c/src/jni/jni_helper.h"

extern "C" void GAFIS_MntDispToMntStd(MNTDISPSTRUCT *pmnt, void *mnt);

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
JNIEXPORT void JNICALL Java_nirvana_hall_extractor_jni_NativeExtractor_ConvertFPTLatentMNT2Std
  (JNIEnv *jenv, jclass, jbyteArray dispMnt, jbyteArray stdMnt){

  int length = jenv->GetArrayLength(dispMnt);
  if(length != sizeof(MNTDISPSTRUCT))
    SWIG_JavaThrowExceptionByCode(jenv, SWIG_JavaArithmeticException, -100);
   else{

    UCHAR* disp_mnt_bin= (UCHAR*)jenv->GetByteArrayElements(dispMnt,JNI_FALSE);
    UCHAR* mnt_data = (UCHAR*)jenv->GetByteArrayElements(stdMnt,JNI_FALSE);
    /*
    int n1=offsetof(MNTDISPSTRUCT,stPm);
    int n2=offsetof(MNTDISPSTRUCT,stFg);
    int n3=offsetof(MNTDISPSTRUCT,stCm);
    int n4=offsetof(MNTDISPSTRUCT,stdReserve);
    int n5=offsetof(MNTDISPSTRUCT,stInnerData);
    printf("disp mnt length:%d %d %d %d %d %d\n",jenv->GetArrayLength(dispMnt),n1,n2,n3,n4,n5);
    fflush(stdout);
    */
    MNTDISPSTRUCT * disp_mnt_structure;
    disp_mnt_structure=(MNTDISPSTRUCT*)disp_mnt_bin;
    /*
    uint2 width = disp_mnt_structure->nWidth;
    uint2 height = disp_mnt_structure->nHeight;
    printf("w:%"PRIu16 " h:%u \n",width,height);
    printf("size:%u  \n",disp_mnt_structure->nSize);
    fflush(stdout);
    */
    GAFIS_MntDispToMntStd(disp_mnt_structure,mnt_data);
    jenv->ReleaseByteArrayElements(dispMnt,(jbyte*)disp_mnt_bin,JNI_ABORT);
    jenv->ReleaseByteArrayElements(stdMnt,(jbyte*)mnt_data,0);
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


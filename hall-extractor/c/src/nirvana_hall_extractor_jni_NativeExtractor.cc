#include <string.h>
#include <stddef.h>

#include "nirvana_hall_extractor_jni_NativeExtractor.h"

#include "nirvana/kernel.h"

#include "../../../hall-image/c/src/jni/jni_helper.h"

extern "C" void GAFIS_MntDispToMntStd(MNTDISPSTRUCT *pmnt, void *mnt);
extern "C" void GAFIS_MntStdToMntDisp(void *mnt, MNTDISPSTRUCT *pmnt, int MinutiaON);



/*
 * Class:     nirvana_hall_extractor_jni_NativeExtractor
 * Method:    ExtractMNT_All
 * Signature: ([BBB)[B
 */
JNIEXPORT void JNICALL Java_nirvana_hall_extractor_jni_NativeExtractor_ExtractMNT_1All
  (JNIEnv * jenv, jclass, jbyteArray img_bytes,jbyteArray mnt_bytes,jbyteArray bin_bytes,jbyte finger_pos,jbyte ExtractMode, jbyte belatent) {
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

  GAFISIMAGESTRUCT *bin = NULL;
  UCHAR* bin_data = NULL;
  if(bin_bytes != NULL){
    bin_data = (UCHAR *) jenv->GetByteArrayElements(bin_bytes, JNI_FALSE);
    bin = (GAFISIMAGESTRUCT *) bin_data;
  }

  UCHAR *mnt_data = (UCHAR *) jenv->GetByteArrayElements(mnt_bytes, JNI_FALSE);
  GAFISIMAGESTRUCT *mnt = (GAFISIMAGESTRUCT *) mnt_data;
  //mnt->stHead.nFingerIndex = (UCHAR) finger_pos;

  XGWMNTEXTRACTSTR mntExtStr = {0};

  mntExtStr.pImage = img;
  mntExtStr.pMnt = mnt->bnData;
  mntExtStr.pBin = bin;
  mntExtStr.ExtractMode = (unsigned char) ExtractMode;
  mntExtStr.belatent = (unsigned char) belatent;
  int ret = GAFIS_ExtractMNT_All(&mntExtStr);
  jenv->ReleaseByteArrayElements(img_bytes, (jbyte *) img_data, JNI_ABORT);
  jenv->ReleaseByteArrayElements(mnt_bytes, (jbyte *) mnt_data, 0);
  if(bin_bytes)
    jenv->ReleaseByteArrayElements(bin_bytes, (jbyte *) bin_data, 0);

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
/*
 * Class:     nirvana_hall_extractor_jni_NativeExtractor
 * Method:    GAFIS_MntDispToMntStd
 * Signature: ([B[B)V
 */
JNIEXPORT void JNICALL Java_nirvana_hall_extractor_jni_NativeExtractor_GAFIS_1MntDispToMntStd
    (JNIEnv *jenv, jclass, jbyteArray dispMnt, jbyteArray stdMnt){
  int length = jenv->GetArrayLength(dispMnt);
  if(length != sizeof(MNTDISPSTRUCT)){
    /*
    char msg[100];
    snprintf(msg,sizeof(msg),"dispMnt length %d != struct size %lu",length,sizeof(MNTDISPSTRUCT));
    */
    SWIG_JavaThrowExceptionByCode(jenv, SWIG_JavaArithmeticException, -100);
  }else{

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

/*
 * Class:     nirvana_hall_extractor_jni_NativeExtractor
 * Method:    GAFIS_MntStdToMntDisp
 * Signature: ([B[BI)V
 */
JNIEXPORT void JNICALL Java_nirvana_hall_extractor_jni_NativeExtractor_GAFIS_1MntStdToMntDisp
    (JNIEnv * jenv, jclass, jbyteArray stdMnt, jbyteArray dispMnt, jint MinutiaON) {
  int length = jenv->GetArrayLength(dispMnt);
  if (length != sizeof(MNTDISPSTRUCT))
    SWIG_JavaThrowExceptionByCode(jenv, SWIG_JavaArithmeticException, -100);
  else {

    UCHAR *disp_mnt_bin = (UCHAR *) jenv->GetByteArrayElements(dispMnt, JNI_FALSE);
    UCHAR *mnt_data = (UCHAR *) jenv->GetByteArrayElements(stdMnt, JNI_FALSE);

/*
    FINGERMNTSTRUCT* std_mnt=(FINGERMNTSTRUCT*)mnt_data;
    printf("std mnt count:%d \n",std_mnt->cm);
    fflush(stdout);
*/

    /*
    int n1=offsetof(MNTDISPSTRUCT,stPm);
    int n2=offsetof(MNTDISPSTRUCT,stFg);
    int n3=offsetof(MNTDISPSTRUCT,stCm);
    int n4=offsetof(MNTDISPSTRUCT,stdReserve);
    int n5=offsetof(MNTDISPSTRUCT,stInnerData);
    printf("disp mnt length:%d %d %d %d %d %d\n",jenv->GetArrayLength(dispMnt),n1,n2,n3,n4,n5);
    fflush(stdout);
    */
    MNTDISPSTRUCT *disp_mnt_structure;
    disp_mnt_structure = (MNTDISPSTRUCT *) disp_mnt_bin;
    /*
    uint2 width = disp_mnt_structure->nWidth;
    uint2 height = disp_mnt_structure->nHeight;
    printf("w:%"PRIu16 " h:%u \n",width,height);
    printf("size:%u  \n",disp_mnt_structure->nSize);
    fflush(stdout);
    */
    GAFIS_MntStdToMntDisp(mnt_data,disp_mnt_structure,MinutiaON);
    //printf("disp mnt count:%d \n",disp_mnt_structure->stCm.nMntCnt);
    jenv->ReleaseByteArrayElements(dispMnt, (jbyte *) disp_mnt_bin, 0);
    jenv->ReleaseByteArrayElements(stdMnt, (jbyte *) mnt_data, JNI_ABORT);
  }
}


#ifdef LINUX
//Linux下支持新的算法

#define MAXTTFEASIZE 3320
typedef struct {
   FINGERMNTSTRUCT MNT;
   unsigned char pTTFea[MAXTTFEASIZE];
}FINGERMNTSTRUCT_NEWTT;
extern "C" int GAFIS_Generate_MntNewTTFea( FINGERMNTSTRUCT* pMnt, FINGERMNTSTRUCT_NEWTT* pMntTTFea );
extern "C" int GAFIS_ExtractMNT_All_NewTTFea(XGWMNTEXTRACTSTR *MntExtStr);

/*
 * Class:     nirvana_hall_extractor_jni_NativeExtractor
 * Method:    ExtractMNT_AllWithNewFeature
 * Signature: ([B[BBBB)V
 */
JNIEXPORT void JNICALL Java_nirvana_hall_extractor_jni_NativeExtractor_ExtractMNT_1AllWithNewFeature
    (JNIEnv * jenv, jclass, jbyteArray img_bytes,jbyteArray mnt_bytes,jbyteArray bin_bytes,jbyte finger_pos,jbyte ExtractMode, jbyte belatent){
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

  GAFISIMAGESTRUCT *bin = NULL;
  UCHAR* bin_data = NULL;
  if(bin_bytes != NULL){
    bin_data = (UCHAR *) jenv->GetByteArrayElements(bin_bytes, JNI_FALSE);
    bin = (GAFISIMAGESTRUCT *) bin_data;
  }

  UCHAR* mnt_data = (UCHAR*)jenv->GetByteArrayElements(mnt_bytes,JNI_FALSE);
  GAFISIMAGESTRUCT* mnt = (GAFISIMAGESTRUCT*)mnt_data;
  //mnt->stHead.nFingerIndex = (UCHAR) finger_pos;

  XGWMNTEXTRACTSTR mntExtStr={0};

  mntExtStr.pImage      = img;
  mntExtStr.pMnt        = mnt->bnData;
  mntExtStr.pBin = bin;
  mntExtStr.ExtractMode	= (unsigned char) ExtractMode;
  mntExtStr.belatent    = (unsigned char) belatent;
  int ret = GAFIS_ExtractMNT_All_NewTTFea(&mntExtStr);
  jenv->ReleaseByteArrayElements(img_bytes,(jbyte*)img_data,JNI_ABORT);
  jenv->ReleaseByteArrayElements(mnt_bytes,(jbyte*)mnt_data,0);
  if(bin_bytes)
    jenv->ReleaseByteArrayElements(bin_bytes, (jbyte *) bin_data, 0);
  if(ret != 1){//success
    SWIG_JavaThrowExceptionByCode(jenv,SWIG_JavaArithmeticException,ret);
  }
}
JNIEXPORT void JNICALL Java_nirvana_hall_extractor_jni_NativeExtractor_ConvertMntOldToNew
  (JNIEnv *jenv, jclass, jbyteArray old_mnt_bytes, jbyteArray new_mnt_bytes){
    if(old_mnt_bytes == NULL){
      SWIG_JavaThrowException(jenv,SWIG_JavaIllegalArgumentException,"old mn bytes is null");
      return;
    }
    if(new_mnt_bytes == NULL){
      SWIG_JavaThrowException(jenv,SWIG_JavaIllegalArgumentException,"new mnt bytes is null");
      return;
    }
     UCHAR* old_mnt_data = (UCHAR*)jenv->GetByteArrayElements(old_mnt_bytes,JNI_FALSE);
     FINGERMNTSTRUCT* old_mnt = (FINGERMNTSTRUCT*)old_mnt_data;

     UCHAR* new_mnt_data = (UCHAR*)jenv->GetByteArrayElements(new_mnt_bytes,JNI_FALSE);
     FINGERMNTSTRUCT_NEWTT* new_mnt = (FINGERMNTSTRUCT_NEWTT*)new_mnt_data;

     int ret = GAFIS_Generate_MntNewTTFea(old_mnt,new_mnt);
    jenv->ReleaseByteArrayElements(old_mnt_bytes, (jbyte *) old_mnt_data, JNI_ABORT);
    jenv->ReleaseByteArrayElements(new_mnt_bytes, (jbyte *) new_mnt_data, 0);
  if(ret != 1){//success
    SWIG_JavaThrowExceptionByCode(jenv,SWIG_JavaArithmeticException,ret);
  }
}

#endif


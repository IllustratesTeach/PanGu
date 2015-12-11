#ifdef WIN32

#include "nirvana_hall_image_jni_NativeImageConverter.h"

#include "jni_helper.h"

//define function type
typedef int (*GFP_FPT_DCXX)(
    unsigned char	code[4],
    unsigned char	*cp_data,
    int			length,
    unsigned char	*img,
    unsigned char	buf[256]
    );

/*
 * Class:     nirvana_hall_image_jni_NativeImageConverter
 * Method:    loadLibrary
 * Signature: (Ljava/lang/String;I)J
 */
JNIEXPORT jlong JNICALL Java_nirvana_hall_image_jni_NativeImageConverter_loadLibrary
  (JNIEnv *jenv, jclass, jstring pszFileName, jint nOption){
	  char* dll_path;
	  HMODULE hHandle;
	  dll_path = (char *)jenv->GetStringUTFChars(pszFileName, 0);
	  if(dll_path == NULL){
		  SWIG_JavaThrowException(jenv, SWIG_JavaIllegalArgumentException, "dll path is null");
		  return 0;
	  }
	  hHandle = LoadLibraryEx(dll_path, NULL, nOption);
	  if(hHandle == NULL)
		  SWIG_JavaThrowException(jenv, SWIG_JavaNullPointerException, "fail to load dll");
	  return (jlong)hHandle;
}

/*
 * Class:     nirvana_hall_image_jni_NativeImageConverter
 * Method:    freeLibrary
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_nirvana_hall_image_jni_NativeImageConverter_freeLibrary
  (JNIEnv *jenv, jclass, jlong handle){
	  if(FreeLibrary((HMODULE)handle) != TRUE){
		  SWIG_JavaThrowException(jenv, SWIG_JavaRuntimeException, "fail to free library");
	  }
}

/*
 * Class:     nirvana_hall_image_jni_NativeImageConverter
 * Method:    decodeByManufactory
 * Signature: (J[B)[B
 */
JNIEXPORT jbyteArray JNICALL Java_nirvana_hall_image_jni_NativeImageConverter_decodeByManufactory
  (JNIEnv *jenv, jclass, jlong hHandle, jstring fun_name,jstring code,jbyteArray cpr_data,jint dest_img_size){
	  char* fun= (char *)jenv->GetStringUTFChars(fun_name, 0);
	  if(fun == NULL){
		  SWIG_JavaThrowException(jenv, SWIG_JavaIllegalArgumentException, "function name is null");
		  return 0;
	  }
	  GFP_FPT_DCXX p = (GFP_FPT_DCXX)GetProcAddress((HMODULE)hHandle, fun);

	  size_t cpr_data_length = jenv->GetArrayLength(cpr_data);
	  UCHAR* cpr_data_bin = (UCHAR*) jenv->GetByteArrayElements(cpr_data, 0);
	  UCHAR szResult[260] = {0};
	  UCHAR *code_str = (UCHAR*)jenv->GetStringUTFChars(code,0);
	  jbyteArray dest_img = jenv->NewByteArray(dest_img_size);
	  UCHAR* dest_img_bin = (UCHAR*)jenv->GetByteArrayElements(dest_img, NULL);
	  int ret = p(code_str,cpr_data_bin,cpr_data_length,dest_img_bin,szResult);
	  //free byte array elements
	  jenv->ReleaseByteArrayElements(cpr_data,(jbyte*)cpr_data_bin,JNI_ABORT);
	  jenv->ReleaseByteArrayElements(dest_img,(jbyte*)dest_img_bin,0);

	  ThrowExceptionByFPTCode(jenv,ret);
	  return dest_img;
}
#endif



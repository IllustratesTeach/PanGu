#ifdef WIN32

#include "nirvana_hall_image_jni_NativeImageConverter.h"

#include "jni_helper.h"

//define function type
typedef int (*GA_FPT_DCXX)(
    unsigned char	code[4],
    unsigned char	*cp_data,
    int			length,
    unsigned char	*img,
    unsigned char	buf[256]
    );
    
static void ThrowExceptionByFPTCode(JNIEnv* jenv,int nCode)
{
	if(nCode != 1){
		char string[25]={0};
		_itoa_s(nCode,string,10);
		SWIG_JavaThrowException(jenv, SWIG_JavaArithmeticException, string);
	}
}
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
		  jenv->ReleaseStringUTFChars(pszFileName,dll_path);
		  SWIG_JavaThrowException(jenv, SWIG_JavaIllegalArgumentException, "dll path is null");
		  return 0;
	  }
	  hHandle = LoadLibraryEx(dll_path, NULL, nOption);
	  //relase string
	  jenv->ReleaseStringUTFChars(pszFileName,dll_path);
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
		  jenv->ReleaseStringUTFChars(fun_name,fun);
		  SWIG_JavaThrowException(jenv, SWIG_JavaIllegalArgumentException, "function name is null");
		  return 0;
	  }
	  GA_FPT_DCXX p = (GA_FPT_DCXX)GetProcAddress((HMODULE)hHandle, fun);

	  UCHAR *code_str = (UCHAR*)jenv->GetStringUTFChars(code,JNI_FALSE);

	  size_t cpr_data_length = jenv->GetArrayLength(cpr_data);
	  UCHAR* cpr_data_bin = (UCHAR*) jenv->GetByteArrayElements(cpr_data, JNI_FALSE);

	  jbyteArray dest_img = jenv->NewByteArray(dest_img_size);
	  UCHAR* dest_img_bin = (UCHAR*)jenv->GetByteArrayElements(dest_img, JNI_FALSE);

	  UCHAR szResult[260] = {0};
	  //call dll function to decompress data
	  int ret = p(code_str,cpr_data_bin,cpr_data_length,dest_img_bin,szResult);

	  //free byte array elements
	  jenv->ReleaseByteArrayElements(cpr_data,(jbyte*)cpr_data_bin,JNI_ABORT);
	  jenv->ReleaseByteArrayElements(dest_img,(jbyte*)dest_img_bin,0);

	  //release string
	  jenv->ReleaseStringUTFChars(fun_name,fun);
	  jenv->ReleaseStringUTFChars(code,(char *)code_str);

	  ThrowExceptionByFPTCode(jenv,ret);
	  return dest_img;
}
#endif



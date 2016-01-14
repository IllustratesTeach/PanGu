#ifdef WIN32

#include "manufactory_win32.h"

#include "nirvana_hall_image_jni_NativeImageConverter.h"

#include "jni_helper.h"



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
  (JNIEnv *jenv, jclass, jstring pszFileName, jstring pszFunName,jint nFirmCode,jint nOption){
    if(pszFileName == NULL){
      SWIG_JavaThrowException(jenv,SWIG_JavaIllegalArgumentException,"file path is null");
      return NULL;
    }
	char* dll_path = (char *)jenv->GetStringUTFChars(pszFileName, JNI_FALSE);
	if(dll_path == NULL){
		jenv->ReleaseStringUTFChars(pszFileName,dll_path);
		SWIG_JavaThrowException(jenv, SWIG_JavaIllegalArgumentException, "dll path is null");
		return 0;
	}
	HMODULE hHandle = LoadLibraryEx(dll_path, NULL, nOption);
	//release string
	jenv->ReleaseStringUTFChars(pszFileName,dll_path);
	if(hHandle == NULL)
		SWIG_JavaThrowException(jenv, SWIG_JavaNullPointerException, "fail to load dll");
	char* fun_name = (char*)jenv->GetStringUTFChars(pszFunName,JNI_FALSE);

	bool bGetProc = false;
	jlong result = NULL;
	
	if(GAIMG_CPRMETHOD_BUPT == nFirmCode)
	{
		GFP_FPT_BUPT_DCXX p = (GFP_FPT_BUPT_DCXX)GetProcAddress((HMODULE)hHandle, fun_name);
		result =(jlong)p;
	}
	else
	{
		GA_FPT_DCXX p = (GA_FPT_DCXX)GetProcAddress((HMODULE)hHandle, fun_name);
		result =(jlong)p;
	}

	jenv->ReleaseStringUTFChars(pszFunName,fun_name);

	if(result == NULL){
	  FreeLibrary((HMODULE)hHandle);
	}
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
  (JNIEnv *jenv, jclass, jlong hHandle, jstring code,jint nFirmCode,jbyteArray cpr_data,jint dest_img_size){
    if(hHandle == NULL){
      SWIG_JavaThrowException(jenv,SWIG_JavaIllegalArgumentException,"hHandle is null");
      return NULL;
    }
    if(code == NULL){
      SWIG_JavaThrowException(jenv,SWIG_JavaIllegalArgumentException,"firm code is null");
      return NULL;
    }
    int code_len = jenv->GetStringUTFLength(code);
    if(code_len != 4){
      SWIG_JavaThrowException(jenv,SWIG_JavaIllegalArgumentException,"firm code's length must be 4");
      return NULL;
    }
	UCHAR *code_str = (UCHAR*)jenv->GetStringUTFChars(code,JNI_FALSE);

	size_t cpr_data_length = jenv->GetArrayLength(cpr_data);
	UCHAR* cpr_data_bin = (UCHAR*) jenv->GetByteArrayElements(cpr_data, JNI_FALSE);

	jbyteArray dest_img = jenv->NewByteArray(dest_img_size);
	UCHAR* dest_img_bin = (UCHAR*)jenv->GetByteArrayElements(dest_img, JNI_FALSE);

	UCHAR szResult[260] = {0};

	int ret = 0;
	if(nFirmCode == GAIMG_CPRMETHOD_BUPT){
		//call dll function to decompress data
		GFP_FPT_BUPT_DCXX p =(GFP_FPT_BUPT_DCXX)hHandle;
		ret = p(code_str,cpr_data_bin,cpr_data_length,dest_img_bin,szResult);
	}else{
		//call dll function to decompress data
		GA_FPT_DCXX p = (GA_FPT_DCXX)hHandle;
		ret = p(code_str,cpr_data_bin,cpr_data_length,dest_img_bin,szResult);
	}

	//free byte array elements
	jenv->ReleaseByteArrayElements(cpr_data,(jbyte*)cpr_data_bin,JNI_ABORT);
	jenv->ReleaseByteArrayElements(dest_img,(jbyte*)dest_img_bin,0);

	//release string
	jenv->ReleaseStringUTFChars(code,(char *)code_str);

	ThrowExceptionByFPTCode(jenv,ret);
	return dest_img;
}
#endif



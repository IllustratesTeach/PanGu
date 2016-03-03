#ifdef WIN32

#include <jni.h>
#include <crtdbg.h>

#include "manufactory_win32.h"

#include "nirvana_hall_image_jni_NativeImageConverter.h"

#include "jni_helper.h"


LONG	WINAPI	DBG_DumpExcept(LPEXCEPTION_POINTERS exceptInfo)
{
 return 1;
}

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
	}else if(GAIMG_CPRMETHOD_GA10 == nFirmCode){
		GFP_FPT_GA10_DECOMPRESS p=(GFP_FPT_GA10_DECOMPRESS)GetProcAddress((HMODULE)hHandle, fun_name);
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
//  SetUnhandledExceptionFilter(DBG_DumpExcept);
	return result;
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
JNIEXPORT jobject JNICALL Java_nirvana_hall_image_jni_NativeImageConverter_decodeByManufactory
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


	int width = 0;
	int height =0;
	int ppi = 0;
	int ret = 0;
	__try{

    if(nFirmCode == GAIMG_CPRMETHOD_BUPT){
      //call dll function to decompress data
      GFP_FPT_BUPT_DCXX p =(GFP_FPT_BUPT_DCXX)hHandle;
      ret = p(code_str,cpr_data_bin,cpr_data_length,dest_img_bin,szResult);
    }else if(GAIMG_CPRMETHOD_GA10 == nFirmCode){
      GFP_FPT_GA10_DECOMPRESS p=(GFP_FPT_GA10_DECOMPRESS)hHandle;
      ret=p(code_str,cpr_data_bin,cpr_data_length,dest_img_bin,&height,&width,&ppi,szResult);
    }else{
      //call dll function to decompress data
      GA_FPT_DCXX p = (GA_FPT_DCXX)hHandle;
      ret = p(code_str,cpr_data_bin,cpr_data_length,dest_img_bin,szResult);
    }
	}
	__except(EXCEPTION_EXECUTE_HANDLER){
		ret = -100;
	}



	//free byte array elements
	jenv->ReleaseByteArrayElements(cpr_data,(jbyte*)cpr_data_bin,JNI_ABORT);
	jenv->ReleaseByteArrayElements(dest_img,(jbyte*)dest_img_bin,0);

  if(ret != 1){
  	printf("unable to decompress,ret:%d firmCode:%s msg:%s",ret,code_str,szResult);
  }
	//release string
	jenv->ReleaseStringUTFChars(code,(char *)code_str);

	if(ret == 1){
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
	}else{
		ThrowExceptionByFPTCode(jenv,ret);
		return NULL;
	}
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *jvm, void *reserved){
  printf("begin init ...\n");
  fflush(stdout);

  _CrtSetReportMode( _CRT_ERROR, _CRTDBG_MODE_FILE );
  _CrtSetReportFile( _CRT_ERROR, _CRTDBG_FILE_STDERR );

  _CrtSetReportMode( _CRT_WARN, _CRTDBG_MODE_FILE );
  _CrtSetReportFile( _CRT_WARN, _CRTDBG_FILE_STDERR );

  _CrtSetReportMode( _CRT_ASSERT, _CRTDBG_MODE_FILE );
  _CrtSetReportFile( _CRT_ASSERT, _CRTDBG_FILE_STDERR );
  return JNI_VERSION_1_6;
}
#else
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *jvm, void *reserved){
  return JNI_VERSION_1_6;
}

#endif



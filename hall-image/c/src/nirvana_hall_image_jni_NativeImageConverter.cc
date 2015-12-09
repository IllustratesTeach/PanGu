#include "nirvana_hall_image_jni_NativeImageConverter.h"
#include <windows.h>
#include <jni.h>
#include <stdlib.h>
#include <string.h>

typedef int (*GFP_FPT_DCXX)(
							     unsigned char	code[4],
								 unsigned char	*cp_data,
								 int			length,
								 unsigned char	*img,
								 unsigned char	buf[256]
								);

/* Support for throwing Java exceptions */
typedef enum {
  SWIG_JavaOutOfMemoryError = 1,
  SWIG_JavaIOException = 2,
  SWIG_JavaRuntimeException = 3,
  SWIG_JavaIndexOutOfBoundsException = 4,
  SWIG_JavaArithmeticException = 5,
  SWIG_JavaIllegalArgumentException = 6,
  SWIG_JavaNullPointerException = 7,
  SWIG_JavaDirectorPureVirtual = 8,
  SWIG_JavaUnknownError = 9
} SWIG_JavaExceptionCodes;

typedef struct {
  SWIG_JavaExceptionCodes code;
  const char *java_exception;
} SWIG_JavaExceptions_t;

static void SWIG_JavaThrowException(JNIEnv *jenv, SWIG_JavaExceptionCodes code, const char *msg) {
  jclass excep;
  static const SWIG_JavaExceptions_t java_exceptions[] = {
    { SWIG_JavaOutOfMemoryError, "java/lang/OutOfMemoryError" },
    { SWIG_JavaIOException, "java/io/IOException" },
    { SWIG_JavaRuntimeException, "java/lang/RuntimeException" },
    { SWIG_JavaIndexOutOfBoundsException, "java/lang/IndexOutOfBoundsException" },
    { SWIG_JavaArithmeticException, "java/lang/ArithmeticException" },
    { SWIG_JavaIllegalArgumentException, "java/lang/IllegalArgumentException" },
    { SWIG_JavaNullPointerException, "java/lang/NullPointerException" },
    { SWIG_JavaDirectorPureVirtual, "java/lang/RuntimeException" },
    { SWIG_JavaUnknownError,  "java/lang/UnknownError" },
    { (SWIG_JavaExceptionCodes)0,  "java/lang/UnknownError" }
  };
  const SWIG_JavaExceptions_t *except_ptr = java_exceptions;

  while (except_ptr->code != code && except_ptr->code)
    except_ptr++;

  jenv->ExceptionClear();
  excep = jenv->FindClass(except_ptr->java_exception);
  if (excep)
    jenv->ThrowNew(excep, msg);
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
  (JNIEnv *jenv, jobject, jstring pszFileName, jint nOption){
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
  (JNIEnv *jenv, jobject, jlong handle){
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
  (JNIEnv *jenv, jobject jobject, jlong hHandle, jstring fun_name,jstring code,jbyteArray cpr_data,jint dest_img_size){
	  char* fun;
	  GFP_FPT_DCXX p;
	  size_t cpr_data_length;
	  UCHAR* cpr_data_bin;
	  fun= (char *)jenv->GetStringUTFChars(fun_name, 0);
	  if(fun == NULL){
		  SWIG_JavaThrowException(jenv, SWIG_JavaIllegalArgumentException, "function name is null");
		  return 0;
	  }
	  p = (GFP_FPT_DCXX)GetProcAddress((HMODULE)hHandle, fun);

	  cpr_data_length = jenv->GetArrayLength(cpr_data);
	  cpr_data_bin = (UCHAR*) jenv->GetByteArrayElements(cpr_data, 0);
	  UCHAR szResult[260] = {0};
	  UCHAR *code_str;
	  code_str = (UCHAR*)jenv->GetStringUTFChars(code,0);
	  jbyteArray dest_img = jenv->NewByteArray(dest_img_size);
	  UCHAR* dest_img_bin = (UCHAR*)jenv->GetByteArrayElements(dest_img, NULL);
	  int ret = p(code_str,cpr_data_bin,cpr_data_length,dest_img_bin,szResult);
	  //free byte array elements
	  jenv->ReleaseByteArrayElements(cpr_data,(jbyte*)cpr_data_bin,JNI_ABORT);
	  jenv->ReleaseByteArrayElements(dest_img,(jbyte*)dest_img_bin,JNI_ABORT);

	  ThrowExceptionByFPTCode(jenv,ret);

	  return dest_img;
}




#include "jni_helper.h"

void SWIG_JavaThrowException(JNIEnv *jenv, SWIG_JavaExceptionCodes code, const char *msg) {
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
void SWIG_JavaThrowExceptionByCode(JNIEnv *jenv, SWIG_JavaExceptionCodes code, const int ret) {
  char msg[20]={0};
  sprintf(msg,"code:%d",ret);
  SWIG_JavaThrowException(jenv,code,msg);
}

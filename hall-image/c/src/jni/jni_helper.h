#ifndef JNI_HELPER_H_

#include "jni.h"

#ifdef WIN32
#include <Windows.h>
#else
typedef unsigned char UCHAR;
#endif
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

void SWIG_JavaThrowException(JNIEnv *jenv, SWIG_JavaExceptionCodes code, const char *msg);
void SWIG_JavaThrowExceptionByCode(JNIEnv *jenv, SWIG_JavaExceptionCodes code, const int ret);

#endif //JNI_HELPER_H_

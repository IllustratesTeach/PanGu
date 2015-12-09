package nirvana.hall.image.jni;

/**
 * image converter
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-09
 */
public class NativeImageConverter {
    /**
     * load dll to memory
     * hHandle = LoadLibraryEx(pszFileName, NULL, nOption);
     * @param dll_path dll path
     * @return dll handler
     */
    public native long loadLibrary(String dll_path,int option);

    /**
     * free library
     * FreeLibrary(hHandle)
     * @param handle dll handle
     */
    public native void freeLibrary(long handle);

    /**
     * decode compressed data by manufactory
     * @param handle dll handler
     * @param cpr_data compressed data
     * @return source data
     */
    public native byte[] decodeByManufactory(long handle,byte[] cpr_data);
}

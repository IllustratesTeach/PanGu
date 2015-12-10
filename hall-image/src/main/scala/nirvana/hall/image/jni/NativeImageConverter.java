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
    public static native long loadLibrary(String dll_path,int option);

    /**
     * free library
     * FreeLibrary(hHandle)
     * @param handle dll handle
     */
    public static native void freeLibrary(long handle);

    /**
     * decode compressed data by manufactory
     * @param handle dll handler
     * @param cpr_data compressed data
     * @return source data
     */
    public static native byte[] decodeByManufactory(long handle,String function,String code,byte[] cpr_data,int dest_img_size);

    /**
     * decode compressed data by WSQ
     * @param cpr_data compressed data
     * @return original data
     */
    public static native OriginalImage decodeByWSQ(byte[] cpr_data);
    public static native byte[] encodeByWSQ(byte[] origin_data,int width,int height,int ppi,int ratio);
}

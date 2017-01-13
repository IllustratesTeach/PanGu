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
    public static native long loadLibrary(String dll_path,String funName,int cprMethod,int option);

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
    public static native OriginalImage decodeByManufactory(long handle,String code,int cprMethod,byte[] cpr_data,int dest_img_size);

	/**
   * decode fpt image data
   * @param cprData compressed image
   * @param originData original data
   */
  public static void decodeByGFS(byte[] cprData,byte[] originData){
      decodeByGFS(cprData,originData,true);
    }

  /**
   * decode compressed image
   * @param cprData compressed image data
   * @param originData original image data
   * @param isFptImg if true ,is fpt image
	 */
    public static native void decodeByGFS(byte[] cprData,byte[] originData,boolean isFptImg);

    /**
     * decode compressed data by WSQ
     * @param cpr_data compressed data
     * @return original data
     */
    public static native OriginalImage decodeByWSQ(byte[] cpr_data);
    public static native byte[] encodeByWSQ(byte[] origin_data,int width,int height,int ppi,int ratio);
  /**
   * 压缩图像数据
   * @param pImage GAFISIMAGESTRUCT 	图象指针（单枚）
   * @param cpr GAFISIMAGESTRUCT 			返回的压缩图象指针（单枚）
   * @param cprmethod,		压缩方法，目前固定值为0, 1为EZW压缩方法适合低倍率高保真的压缩
   * @param compress_ratio 	压缩倍率2...30
   * @return 1	......	成功, <0	......	失败,错误代码为KERROR_XXX
   */
//  public static native int GAFIS_CompressIMG(byte[] pImage,byte[] cpr,int cprmethod,int compress_ratio);
  /**
   * 解压缩图像数据
   * @param cpr GAFISIMAGESTRUCT 返回的压缩图象指针（单枚）
   * @param pImage GAFISIMAGESTRUCT 图象指针（单枚）
   * @return 1	......	成功, <0	......	失败,错误代码为KERROR_XXX
   */
//  public static native int GAFIS_DecompressIMG(byte[] cpr,byte[] pImage);
}

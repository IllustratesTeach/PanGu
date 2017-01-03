package nirvana.hall.extractor.jni;



/**
 * extractor
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-09
 */
public class NativeExtractor{
    /**
     * extract feature
     * @param img GAFISIMAGEHEADSTRUCT + image data
     * @param mnt GAFISIMAGEHEADSTRUCT + mnt output
     * @param bin 纹线数据
     * @param finger_pos finger position
     * @param ExtractMode extract mode. see afiskernel.EXETRACTMODE_XXX
     * @param belatent 0 -> latent,1->template
     */
    public static native void ExtractMNT_All(byte[] img,byte[] mnt,byte[] bin,
                                             byte finger_pos,
                                             byte ExtractMode,
                                             byte belatent);
    public static native void ExtractMNT_AllWithNewFeature(byte[] img,byte[] mnt,byte[] bin,
                                             byte finger_pos,
                                             byte ExtractMode,
                                             byte belatent);

	/**
   * @deprecated use {@link #GAFIS_MntDispToMntStd}
   * @param dispMnt
   * @param stdMnt
   */
  @Deprecated
    public static native void ConvertFPTLatentMNT2Std(byte[] dispMnt, byte[] stdMnt);

  /**
   * 显示特征转换为标准的特征
   * @param dispMnt 显示特征
   * @param stdMnt 标准特征
   */
    public static native void GAFIS_MntDispToMntStd(byte[] dispMnt, byte[] stdMnt);
	/**
   *
   * 标准特征转换为显示特征
   * @param stdMnt  标准特征
   * @param dispMnt 显示特征
   * @param MinutiaON 细节点是否需要开启
   */
    public static native void GAFIS_MntStdToMntDisp(byte[] stdMnt, byte[] dispMnt, int MinutiaON);

    /**
     * 转换特征
     * @param oldMnt 老的特征,不带头，结构为:FINGERMNTSTRUCT
     * @param newMnt 新的特征，不带头，结构为:FINGERMNTSTRUCT_NEWTT
     */
    public static native void ConvertMntOldToNew(byte[] oldMnt,byte[] newMnt);
}

package nirvana.hall.api.jni;


/**
 * extractor
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-09
 */
public class NativeExtractor {
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
    public static native void ConvertFPTLatentMNT2Std(byte[] dispMnt, byte[] stdMnt);

    /**
     * 转换特征
     * @param oldMnt 老的特征,不带头，结构为:FINGERMNTSTRUCT
     * @param newMnt 新的特征，不带头，结构为:FINGERMNTSTRUCT_NEWTT
     */
    public static native void ConvertMntOldToNew(byte[] oldMnt,byte[] newMnt);
}

package nirvana.hall.extractor.jni;

/**
 * extractor
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-09
 */
public class NativeExtractor{
    /**
     * extract mnt
     */
    public static native void ExtractMNT_All(byte[] img,byte[] mnt,byte finger_pos,byte ExtractMode,byte belatent);
}

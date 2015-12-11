package nirvana.hall.extractor.services;

import nirvana.hall.c.services.afiskernel;

/**
 * finger position
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-12-11
 */
public interface ExtractorModel {
    enum FeatureType{
        Template,Latent;
    }
    enum ExtractMode {
        NEW,UPDATE,CHECK;
    }
    enum FingerPosition {
        /**
         * <code>FINGER_UNDET = 0;</code>
         * <p/>
         * <pre>
         * 不确定
         * </pre>
         */
        FINGER_UNDET(0),
        /**
         * <code>FINGER_R_THUMB = 1;</code>
         * <p/>
         * <pre>
         * 右拇
         * </pre>
         */
        FINGER_R_THUMB(1),
        /**
         * <code>FINGER_R_INDEX = 2;</code>
         * <p/>
         * <pre>
         * 右食
         * </pre>
         */
        FINGER_R_INDEX(2),
        /**
         * <code>FINGER_R_MIDDLE = 3;</code>
         * <p/>
         * <pre>
         * 右中
         * </pre>
         */
        FINGER_R_MIDDLE(3),
        /**
         * <code>FINGER_R_RING = 4;</code>
         * <p/>
         * <pre>
         * 右环
         * </pre>
         */
        FINGER_R_RING(4),
        /**
         * <code>FINGER_R_LITTLE = 5;</code>
         * <p/>
         * <pre>
         * 右小
         * </pre>
         */
        FINGER_R_LITTLE(5),
        /**
         * <code>FINGER_L_THUMB = 6;</code>
         * <p/>
         * <pre>
         * 左姆
         * </pre>
         */
        FINGER_L_THUMB(6),
        /**
         * <code>FINGER_L_INDEX = 7;</code>
         * <p/>
         * <pre>
         * 左食
         * </pre>
         */
        FINGER_L_INDEX(7),
        /**
         * <code>FINGER_L_MIDDLE = 8;</code>
         * <p/>
         * <pre>
         * 左中
         * </pre>
         */
        FINGER_L_MIDDLE(8),
        /**
         * <code>FINGER_L_RING = 9;</code>
         * <p/>
         * <pre>
         * 左环
         * </pre>
         */
        FINGER_L_RING(9),
        /**
         * <code>FINGER_L_LITTLE = 10;</code>
         * <p/>
         * <pre>
         * 左小
         * </pre>
         */
        FINGER_L_LITTLE(10);

        private int value;

        FingerPosition(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}

package nirvana.hall.webservice.penaltytech;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 加密工具类
 * @author zqLuo
 */
public class GafisEncryptUtil {

    /**
     * 压缩
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] gzip(byte[] data) throws Exception{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(bos);
        gzip.write(data);
        gzip.finish();
        gzip.close();
        byte[] ret = bos.toByteArray();
        bos.close();
        return ret;
    }

    /**
     * 解压缩
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] ungzip(byte[] data) throws Exception{
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        GZIPInputStream gzip = new GZIPInputStream(bis);
        byte[] buf = new byte[1024];
        int num = -1;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((num = gzip.read(buf, 0 , buf.length)) != -1 ){
            bos.write(buf, 0, num);
        }
        gzip.close();
        bis.close();
        byte[] ret = bos.toByteArray();
        bos.flush();
        bos.close();
        return ret;
    }

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认的加密算法

    /**
     * AES 加密操作
     * @param content 待加密内容
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content) throws Exception{
        if(content != null && !content.trim().equals("")){
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());// 初始化为加密模式的密码器
            byte[] result = cipher.doFinal(byteContent);// 加密
            return encodeBase64(gzip(result));//通过Base64转码返回
        }
        return null;
    }

    /**
     * AES 解密操作
     * @param content 待解密内容
     * @return
     */
    public static String decrypt(String content) throws Exception{
        if(content != null && !content.trim().equals("")){
            //实例化
            Cipher cipher  = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            //使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
            //执行操作
            byte[] result = cipher.doFinal(ungzip(decodeBase64(content)));
            return new String(result, "utf-8");
        }
        return null;
    }

    private static SecretKeySpec getSecretKey(){
        return getSecretKeyHolder.secretKeySpec;
    }

    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例没有绑定关系，而且只有被调用到才会装载，从而实现了延迟加载
     */
    private static class getSecretKeyHolder{
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static SecretKeySpec secretKeySpec = getSecretKey(salt);
        /**
         * 生成加密秘钥
         *
         * @return
         */
        private static SecretKeySpec getSecretKey(final String salt){
            try {
                //返回生成指定算法密钥生成器的 KeyGenerator 对象
                KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);;
                SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
                secureRandom.setSeed(salt.getBytes());
                //AES 要求密钥长度为 128
                kg.init(128, secureRandom);
                //生成一个密钥
                SecretKey secretKey = kg.generateKey();
                return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }


    private static char[] base64EncodeChars = new char[]
            { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                    'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                    'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
                    '6', '7', '8', '9', '+', '/' };
    private static byte[] base64DecodeChars = new byte[]
            { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53,
                    54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
                    12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29,
                    30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1,
                    -1, -1, -1 };

    public static String encodeBase64(byte[] data)
    {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;
        while (i < len)
        {
            b1 = data[i++] & 0xff;
            if (i == len)
            {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len)
            {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }

    public static byte[] decodeBase64(String str)
    {
        try
        {
            return decodePrivate(str);
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return new byte[]
                {};
    }

    private static byte[] decodePrivate(String str) throws UnsupportedEncodingException
    {
        StringBuffer sb = new StringBuffer();
        byte[] data = null;
        data = str.getBytes("US-ASCII");
        int len = data.length;
        int i = 0;
        int b1, b2, b3, b4;
        while (i < len)
        {

            do
            {
                b1 = base64DecodeChars[data[i++]];
            } while (i < len && b1 == -1);
            if (b1 == -1)
                break;

            do
            {
                b2 = base64DecodeChars[data[i++]];
            } while (i < len && b2 == -1);
            if (b2 == -1)
                break;
            sb.append((char) ((b1 << 2) | ((b2 & 0x30) >>> 4)));

            do
            {
                b3 = data[i++];
                if (b3 == 61)
                    return sb.toString().getBytes("iso8859-1");
                b3 = base64DecodeChars[b3];
            } while (i < len && b3 == -1);
            if (b3 == -1)
                break;
            sb.append((char) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));

            do
            {
                b4 = data[i++];
                if (b4 == 61)
                    return sb.toString().getBytes("iso8859-1");
                b4 = base64DecodeChars[b4];
            } while (i < len && b4 == -1);
            if (b4 == -1)
                break;
            sb.append((char) (((b3 & 0x03) << 6) | b4));
        }
        return sb.toString().getBytes("iso8859-1");
    }
    private static final String salt = "gafishall";


    public static void main(String[] args) {
        String str = "[{\\\"verifyDate\\\":\\\"\\\",\\\"inputTime\\\":\\\"20181210152500\\\",\\\"hitUser\\\":\\\"\\\",\\\"inputUnit\\\":\\\"\\\",\\\"hitDate\\\":\\\"\\\",\\\"inputUser\\\":\\\"afisadmin\\\",\\\"fingerPalmType\\\":1,\\\"verifyStatus\\\":2,\\\"destTemplateFingerId\\\":\\\"A520524600000201712000701\\\",\\\"sourceTemplateFingerId\\\":\\\"A360102000999201711281601\\\"}]";
        try {
            String jia = new GafisEncryptUtil().encrypt(str);
            System.out.println(jia);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

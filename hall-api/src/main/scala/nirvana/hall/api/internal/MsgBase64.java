package nirvana.hall.api.internal;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class MsgBase64 {

	public static String toBase64(byte[] data) {
		if (data == null)
			return null;
		if (data.length == 0)
			return "";
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}

	public static boolean isValidBase64Char(char c) {
		if ((c >= 'A') && (c <= 'Z'))
			return true;
		if ((c >= 'a') && (c <= 'z'))
			return true;
		if ((c >= '0') && (c <= '9'))
			return true;
		return (c == '/') || (c == '+') || (c == '=');
	}

	public static byte[] fromBase64(String s) {
		if ((s == null) || (s.length() == 0))
			return null;
		StringBuilder sb = new StringBuilder();
		sb.ensureCapacity(s.length());
		for (int i = 0; i < s.length(); i++) {
			if (!isValidBase64Char(s.charAt(i)))
				continue;
			sb.append(s.charAt(i));
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			return decoder.decodeBuffer(sb.toString()); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] fromByte64(byte[] src, int begin, int count){
		if (count-begin < 0)
			return null;
		byte[] bs = new byte[count-begin];  
		 for (int i=begin; i<count; i++){
			 bs[i-begin] = src[i];   
		 }
		 return bs;
	}

}
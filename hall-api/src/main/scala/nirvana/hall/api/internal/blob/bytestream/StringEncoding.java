package nirvana.hall.api.internal.blob.bytestream;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StringEncoding {
	public static final int ENCODING_UNKNOWN = 0;
	public static final int ENCODING_ASCII = 1;
	public static final int ENCODING_GB18030 = 2;
	public static final int ENCODING_UTF_8 = 3;
	public static final int ENCODING_UTF16_BE = 4;
	public static final int ENCODING_UTF16_LE = 5;
	public static final int ENCODING_UTF16 = 6;
	public static final HashMap<String, Integer> map = new HashMap();

	static {
		map.put("US-ASCII", Integer.valueOf(1));
		map.put("GB18030", Integer.valueOf(2));
		map.put("UTF-8", Integer.valueOf(3));
		map.put("UTF-16BE", Integer.valueOf(4));
		map.put("UTF-16LE", Integer.valueOf(5));
		map.put("UTF-16", Integer.valueOf(6));
	}

	public static int codeFromName(String encodingName) {
		Integer v = (Integer) map.get(encodingName);
		if (v == null)
			return 0;
		return v.intValue();
	}

	public static String nameFromCode(int code) {
		Set<Map.Entry<String, Integer>> set = map.entrySet();
		for (Map.Entry<String, Integer> v : set) {
			if (v.getValue() == code)
				return v.getKey();
		}
		return null;
	}

	public static Charset charsetFromCode(int code) {
		String name = nameFromCode(code);
		if (name == null)
			return null;
		return Charset.forName(name);
	}

	public static int codeFromCharset(Charset charset) {
		return codeFromName(charset.name());
	}
}
package nirvana.hall.v70.internal.blob.coder;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class TextEntityObject implements Serializable {
	private static final long serialVersionUID = 1L;
	public Map<Integer, Object> itemList;
	public Map<String, Object> strItemList;

	public TextEntityObject() {
		this.itemList = new HashMap();
	}

	public static String vgetString(Object v) {
		return (String) v;
	}

	public static byte vgetByte(Object v) {
		return ((Integer) v).byteValue();
	}

	public static Date vgetDate(Object v) {
		return (Date) v;
	}

	public static byte[] vgetBinary(Object v) {
		return (byte[]) v;
	}

	public static boolean vgetBool(Object v) {
		return ((Boolean) v).booleanValue();
	}

	public static final class FieldID {
		public static final int FID_INTITEMLIST = 1;
		public static final int FID_STRITEMLIST = 2;
	}
}
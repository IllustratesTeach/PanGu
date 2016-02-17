package nirvana.hall.v70.internal.blob.bytestream;

import java.nio.charset.Charset;

public class StringStreamImpl implements IStringStream {
	public String fromBytes(byte[] data, int encoding) {
		Charset cs = StringEncoding.charsetFromCode(encoding);
		if (cs == null)
			return null;
		return new String(data, cs);
	}

	public byte[] toBytes(String str, int encoding) {
		Charset cs = StringEncoding.charsetFromCode(encoding);
		if (cs == null)
			return null;
		return str.getBytes(cs);
	}

	public String fromBytes(byte[] data, int offset, int length, int encoding) {
		Charset cs = StringEncoding.charsetFromCode(encoding);
		if (cs == null)
			return null;
		return new String(data, offset, length, cs);
	}
}
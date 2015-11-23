package nirvana.hall.api.internal.blob.bytestream;

import java.sql.Date;
import java.sql.Timestamp;

import nirvana.hall.api.internal.blob.error.AFISError;

public class EgfStdField {
	public int fieldType;
	public int fieldId;
	public Object value;
	public int nUserType;
	public int nEncoding;

	public EgfStdField() {
		this.fieldType = -1;
		this.fieldId = -1;
	}

	public EgfStdField(int fieldType, int fieldId) {
		this.fieldType = fieldType;
		this.fieldId = fieldId;
	}

	private long getLongValue(long minValue, long maxValue) {
		if (this.value == null)
			throw new AFISError();
		long n;
		switch (this.fieldType) {
		case 2:
			boolean b = ((Boolean) this.value).booleanValue();
			n = b ? 1 : 0;
			break;
		case 1:
			n = ((Byte) this.value).byteValue();
			break;
		case 4:
		case 21:
			n = ((Short) this.value).shortValue();
			if ((n <= maxValue) && (n >= minValue))
				break;
			throw new AFISError();
		case 5:
		case 22:
			n = ((Integer) this.value).intValue();
			if ((n <= maxValue) && (n >= minValue))
				break;
			throw new AFISError();
		case 6:
		case 23:
			long t = ((Long) this.value).longValue();
			if ((t > maxValue) || (t < minValue))
				throw new AFISError();
			n = t;
			break;
		default:
			throw new AFISError();
		}
		return n;
	}

	public Boolean getBool() {
		return (Boolean) this.value;
	}

	public Byte getTiny() {
		long n = getLongValue(-128L, 127L);

		return Byte.valueOf((byte) (int) n);
	}

	public Byte getByte() {
		return getTiny();
	}

	public Short getShort() {
		long n = getLongValue(-32768L, 32767L);

		return Short.valueOf((short) (int) n);
	}

	public Integer getInt() {
		long n = getLongValue(-2147483648L, 2147483647L);
		return Integer.valueOf((int) n);
	}

	public Long getLong() {
		long n = getLongValue(-9223372036854775808L, 9223372036854775807L);
		return Long.valueOf(n);
	}

	public Short getUtiny() {
		long n = getLongValue(0L, 255L);
		return Short.valueOf((short) (int) n);
	}

	public Integer getUshort() {
		long n = getLongValue(0L, 65535L);
		return Integer.valueOf((int) n);
	}

	public Long getUint() {
		long n = getLongValue(0L, 4294967295L);
		return Long.valueOf(n);
	}

	public String getString() {
		return (String) this.value;
	}

	public Date getDate() {
		if ((this.value instanceof Long)) {
			return new Date(((Long) this.value).longValue());
		}
		return (Date) this.value;
	}

	public Timestamp getTimestamp() {
		if ((this.value instanceof Long)) {
			return new Timestamp(((Long) this.value).longValue());
		}
		if ((this.value instanceof AFISLongTime)) {
			AFISLongTime lt = (AFISLongTime) this.value;
			return new Timestamp(lt.getDateTime());
		}
		return (Timestamp) this.value;
	}

	public Float getFloat() {
		return (Float) this.value;
	}

	public Double getDouble() {
		return (Double) this.value;
	}

	public byte[] getBinary() {
		return (byte[]) this.value;
	}
}
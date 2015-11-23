package nirvana.hall.api.internal.blob.bytestream;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nirvana.hall.api.internal.blob.util.AFISDate;
import nirvana.hall.api.internal.blob.util.AFISDateTime;
import nirvana.hall.api.internal.blob.util.AFISHelper;
import nirvana.hall.api.internal.blob.util.AFISTime;
import nirvana.hall.api.internal.blob.util.DataConv;

public class EgfStdStreamImpl implements IEgfStdStream {
	/**
	 * StringStreamImpl
	 */
	private IStringStream strStm;
	/**
	 * MemInputStream
	 */
	private IBufferIn inBuf;
	private IBufferOut outBuf;
	private EgfStdDocHead docHead = new EgfStdDocHead();

	public EgfStdStreamImpl(IBufferIn inBuf, IStringStream strStm) {
		this.inBuf = inBuf;
		this.strStm = strStm;
	}

	public EgfStdStreamImpl(IBufferOut outBuf, IStringStream strStm) {
		this.outBuf = outBuf;
		this.strStm = strStm;
	}

	public EgfStdStreamImpl(IBufferIn inBuf) {
		this.inBuf = inBuf;
		this.strStm = new StringStreamImpl();
	}

	public EgfStdStreamImpl(IBufferOut outBuf) {
		this.outBuf = outBuf;
		this.strStm = new StringStreamImpl();
	}

	public int decodeField(EgfStdField field, EgfStdCoder coder) {
		int n;
		IEgfStdCoder decoder;
		EgfStdField sf;
		switch (field.fieldType) {
		case 2:
			field.value = Boolean.valueOf(readBool());
			break;
		case 1:
			field.value = Byte.valueOf(readTiny());
			break;
		case 4:
			field.value = Short.valueOf(readShort());
			break;
		case 5:
			field.value = Integer.valueOf(readInt());
			break;
		case 6:
			field.value = Long.valueOf(readLong());
			break;
		case 21:
			field.value = Short.valueOf(readUtiny());
			break;
		case 22:
			field.value = Integer.valueOf(readUshort());
			break;
		case 23:
			field.value = Long.valueOf(readUint());
			break;
		case 14:
			field.value = Float.valueOf(readFloat());
			break;
		case 15:
			field.value = Double.valueOf(readDouble());
			break;
		case 12:
			field.value = readDateTime();
			break;
		case 10:
			field.value = readDate();
			break;
		case 11:
			field.value = readTime();
			break;
		case 24:
			field.value = readLongTime();
			break;
		case 16:
		case 17:

			if (field.fieldType == 16) {
				n = 4;
			} else {
				n = readMInt();
			}
			field.nEncoding = n;
			field.value = readString(n);
			break;
		case 32:
			n = readMInt();
			field.value = readBinary(n);
			break;
		case 25:
			n = readInt();
			if (n <= 0)
				break;
			short[] sv = new short[n];
			for (int i = 0; i < n; i++) {
				sv[i] = readShort();
			}
			field.value = sv;

			break;
		case 26:
			n = readInt();
			if (n <= 0)
				break;
			int[] iv = new int[n];
			for (int i = 0; i < n; i++) {
				iv[i] = readInt();
			}
			field.value = iv;

			break;
		case 27:
			n = readInt();
			if (n <= 0)
				break;
			long[] lv = new long[n];
			for (int i = 0; i < n; i++) {
				lv[i] = readLong();
			}
			field.value = lv;

			break;
		case 33:
			readTag();

			List fl = new ArrayList();
			do {
				sf = new EgfStdField();
				readNextField(sf, coder);
				if (sf.fieldType == 63)
					break;
				fl.add(sf);
			} while (sf.fieldType != 63);
			field.value = fl;
			break;
		case 40:
			field.nUserType = readMInt();
			decoder = coder.getCoder(field.nUserType);
			if (decoder == null) {
				System.out.println("no coder.user type=" + field.nUserType);
			} else {
				readTag();
				decoder.decode(this, field);
			}
			break;
		case 31:
			field.nUserType = readMInt();
			decoder = coder.getCoder(field.nUserType);
			sf = new EgfStdField();
			sf.fieldId = 0;
			sf.fieldType = 40;
			sf.nUserType = field.nUserType;
			List uList = new ArrayList();
			readTag();
			do {
				n = readTag();
				if (n != 52)
					break;
				decoder.decode(this, sf);
				uList.add(sf.value);
			} while (sf.fieldType != 55);
			field.value = uList;
		case 3:
		case 7:
		case 8:
		case 9:
		case 13:
		case 18:
		case 19:
		case 20:
		case 28:
		case 29:
		case 30:
		case 34:
		case 35:
		case 36:
		case 37:
		case 38:
		case 39:
		}
		return 0;
	}

	public int encodeField(EgfStdField field, EgfStdCoder coder) {
		writeTag(field.fieldType);
		writeFieldId(field.fieldId);
		switch (field.fieldType) {
		case 2:
			writeBool(((Boolean) field.value).booleanValue());
			break;
		case 1:
			writeTiny(((Byte) field.value).byteValue());
			break;
		case 4:
			writeShort(((Short) field.value).shortValue());
			break;
		case 5:
			writeInt(((Integer) field.value).intValue());
			break;
		case 6:
			writeLong(((Long) field.value).longValue());
			break;
		case 21:
			writeUtiny(((Short) field.value).shortValue());
			break;
		case 22:
			writeUshort(((Integer) field.value).intValue());
			break;
		case 23:
			writeUint(((Long) field.value).longValue());
			break;
		case 14:
			writeFloat(((Float) field.value).floatValue());
			break;
		case 15:
			writeDouble(((Double) field.value).doubleValue());
			break;
		case 12:
			writeDateTime((AFISDateTime) field.value);
			break;
		case 10:
			writeDate((AFISDate) field.value);
			break;
		case 11:
			writeTime((AFISTime) field.value);
			break;
		case 16:
			writeString((String) field.value, 4);
			break;
		case 17:
			writeString((String) field.value, field.nEncoding);
			break;
		case 32:
			byte[] b = (byte[]) field.value;
			writeMInt(b.length);
			writeBinary(b);
			break;
		case 25:
			short[] sv = (short[]) field.value;
			writeInt(sv.length);
			for (int n = 0; n < sv.length; n++) {
				writeShort(sv[n]);
			}
			break;
		case 26:
			int[] iv = (int[]) field.value;
			writeInt(iv.length);
			for (int n = 0; n < iv.length; n++) {
				writeInt(iv[n]);
			}
			break;
		case 27:
			long[] lv = (long[]) field.value;
			writeInt(lv.length);
			for (int n = 0; n < lv.length; n++) {
				writeLong(lv[n]);
			}
			break;
		case 33:
			writeTag(62);
			List<EgfStdField> temp = (List<EgfStdField>) field.value;
			for (EgfStdField f : temp) {
				encodeField(f, coder);
			}
			writeTag(63);
			break;
		case 40:
			IEgfStdCoder encoder = coder.getCoder(field.nUserType);

			writeMInt(field.nUserType);
			writeTag(52);
			encoder.encode(this, field);
			writeTag(53);
			break;
		case 31:
			encoder = coder.getCoder(field.nUserType);
			EgfStdField uf = new EgfStdField();
			uf.fieldType = 40;
			uf.nUserType = field.nUserType;
			List uList = (List) field.value;
			writeMInt(field.nUserType);
			writeTag(54);
			Iterator it = uList.iterator();
			while (it.hasNext()) {
				Object v = it.next();
				writeTag(52);
				uf.value = v;
				encoder.encode(this, uf);
				writeTag(53);
			}
			writeTag(55);
		case 3:
		case 7:
		case 8:
		case 9:
		case 13:
		case 18:
		case 19:
		case 20:
		case 24:
		case 28:
		case 29:
		case 30:
		case 34:
		case 35:
		case 36:
		case 37:
		case 38:
		case 39:
		}
		return 0;
	}

	public int readTag() {
		return this.inBuf.readByte();
	}

	public int writeTag(int n) {
		this.outBuf.writeByte((byte) n);
		return 1;
	}

	public int readFieldId() {
		return readMInt();
	}

	public int writeFieldId(int n) {
		return writeMInt(n);
	}

	public int readMInt() {
		byte[] b = new byte[4];
		b[0] = this.inBuf.readByte();
		int n = DataConv.mcharByteCnt(b[0]);
		if (n > 1) {
			this.inBuf.readBinary(b, 1, n - 1);
		}
		return DataConv.mcharToInt4(b, 0);
	}

	public int readNextField(EgfStdField field, EgfStdCoder coder) {
		field.fieldType = readTag();

		if ((field.fieldType == 53) || (field.fieldType == 52)
				|| (field.fieldType == 62) || (field.fieldType == 63)
				|| (field.fieldType == 70) || (field.fieldType == 55)
				|| (field.fieldType == 54)) {
			return 1;
		}

		field.fieldId = readMInt();
		field.nUserType = (field.nEncoding = -1);
		field.value = null;
		return decodeField(field, coder);
	}

	public int writeBinaryField(int fieldId, byte[] data) {
		if (data == null)
			return 0;

		writeTag(32);
		writeFieldId(fieldId);
		writeMInt(data.length);
		return writeBinary(data);
	}

	public int writeBool(boolean n) {
		this.outBuf.writeBool(n);
		return 1;
	}

	public int writeDateField(int fieldId, AFISDate n) {
		writeTag(10);
		writeFieldId(fieldId);
		return writeDate(n);
	}

	public int writeDateTimeField(int fieldId, AFISDateTime n) {
		writeTag(12);
		writeFieldId(fieldId);
		return writeDateTime(n);
	}

	public int writeDouble(double n) {
		return writeBinary(DataConv.doubleToChar8(n));
	}

	public int writeDoubleField(int fieldId, double n) {
		writeTag(15);
		writeFieldId(fieldId);
		return writeDouble(n);
	}

	public int writeFloat(float n) {
		return writeBinary(DataConv.floatToChar4(n));
	}

	public int writeFloatField(int fieldId, float n) {
		writeTag(14);
		writeFieldId(fieldId);
		return writeFloat(n);
	}

	public int writeInt(int n) {
		this.outBuf.writeInt(n);
		return 1;
	}

	public int writeIntField(int fieldId, int n) {
		writeTag(5);
		writeFieldId(fieldId);
		return writeInt(n);
	}

	public int writeLong(long n) {
		return writeBinary(DataConv.int8ToChar8(n));
	}

	public int writeLongField(int fieldId, long n) {
		writeTag(6);
		writeFieldId(fieldId);
		return writeLong(n);
	}

	public int writeLongTimeField(int fieldId, Timestamp n) {
		if (n == null)
			return 0;

		writeTag(24);
		writeFieldId(fieldId);
		return writeLongTime(n);
	}

	public int writeMInt(int n) {
		byte[] nc = DataConv.int4ToMChar(n, null, 0);
		return writeBinary(nc);
	}

	public int writeShort(short n) {
		writeBinary(DataConv.int2ToChar2(n));
		return 1;
	}

	public int writeShortField(int fieldId, short n) {
		writeTag(4);
		writeFieldId(fieldId);
		return writeShort(n);
	}

	public int writeString(String n, int encoding) {
		if ((n == null) || (n.length() == 0))
			return 0;

		byte[] data = this.strStm.toBytes(n, encoding);
		writeMInt(data.length);
		return writeBinary(data);
	}

	public int writeStringField(int fieldId, String str, int encoding) {
		if ((str == null) || (str.length() == 0))
			return 0;

		if (encoding == 4) {
			writeTag(16);
			writeFieldId(fieldId);
		} else {
			writeTag(17);
			writeFieldId(fieldId);
			writeMInt(encoding);
		}
		return writeString(str, encoding);
	}

	public int writeTimeField(int fieldId, AFISTime n) {
		writeTag(11);
		writeFieldId(fieldId);
		return writeTime(n);
	}

	public int writeTiny(byte n) {
		this.outBuf.writeByte(n);
		return 1;
	}

	public int writeTinyField(int fieldId, byte n) {
		writeTag(1);
		writeFieldId(fieldId);
		return writeTiny(n);
	}

	public int writeUintField(int fieldId, long n) {
		writeTag(23);
		writeFieldId(fieldId);
		return writeUint(n);
	}

	public int writeUshortField(int fieldId, int n) {
		writeTag(22);
		writeFieldId(fieldId);
		return writeUshort(n);
	}

	public int writeUtinyField(int fieldId, short n) {
		writeTag(21);
		writeFieldId(fieldId);
		return writeUtiny(n);
	}

	public boolean readBool() {
		return this.inBuf.readBool();
	}

	public AFISDate readDate() {
		return new AFISDate(this.inBuf.readBinary(4));
	}

	public AFISDateTime readDateTime() {
		return new AFISDateTime(this.inBuf.readBinary(8));
	}

	public double readDouble() {
		byte[] f = this.inBuf.readBinary(8);
		return DataConv.char8ToDouble(f);
	}

	public float readFloat() {
		byte[] f = this.inBuf.readBinary(4);
		return DataConv.char4ToFloat(f);
	}

	public int readInt() {
		return this.inBuf.readInt();
	}

	public long readLong() {
		return this.inBuf.readLong();
	}

	public AFISLongTime readLongTime() {
		return new AFISLongTime(this.inBuf.readBinary(8));
	}

	public short readShort() {
		return this.inBuf.readShort();
	}

	public String readString(int encoding) {
		int length = readMInt();
		byte[] data = this.inBuf.readBinary(length);
		return this.strStm.fromBytes(data, encoding);
	}

	public AFISTime readTime() {
		return new AFISTime(this.inBuf.readBinary(4));
	}

	public byte readTiny() {
		return this.inBuf.readByte();
	}

	public long readUint() {
		return this.inBuf.readUint();
	}

	public int readUshort() {
		return this.inBuf.readUshort();
	}

	public short readUtiny() {
		return this.inBuf.readUbyte();
	}

	public int writeDate(AFISDate date) {
		writeBinary(date.toBinary());
		return 0;
	}

	public int writeDateTime(AFISDateTime dateTime) {
		writeBinary(dateTime.toBinary());
		return 0;
	}

	public int writeLongTime(Timestamp longTime) {
		writeBinary(DataConv.int8ToChar8(longTime.getTime()));
		return 0;
	}

	public int writeTime(AFISTime time) {
		writeBinary(time.toBinary());
		return 0;
	}

	public int writeUint(long n) {
		this.outBuf.writeUint(n);
		return 0;
	}

	public int writeUshort(int n) {
		this.outBuf.writeUshort(n);
		return 0;
	}

	public int writeUtiny(short n) {
		this.outBuf.writeUbyte(n);
		return 0;
	}

	public int writeBinary(byte[] data) {
		this.outBuf.writeByteArray(data);
		return 1;
	}

	public byte[] readBinary(int length) {
		return this.inBuf.readBinary(length);
	}

	public int writeUDF(int fieldId, int userType, Object userData,
			IEgfStdCoder coder) {
		if (userData == null)
			return 0;

		EgfStdField field = new EgfStdField(40, fieldId);
		field.nUserType = userType;
		field.value = userData;
		writeTag(40);
		writeFieldId(fieldId);
		writeMInt(userType);
		writeTag(52);
		coder.encode(this, field);
		writeTag(53);
		return 0;
	}

	public int writeString(String n) {
		if (n == null)
			return 0;

		return writeString(n, 4);
	}

	public int writeStringField(int fieldId, String str) {
		if (str == null)
			return 0;

		return writeStringField(fieldId, str, 4);
	}

	public int writeByteField(int fieldId, byte n) {
		return writeTinyField(fieldId, n);
	}

	public int writeUDList(int fieldId, int userType, List userData,
			IEgfStdCoder coder) {
		if (AFISHelper.isEmpty(userData))
			return 0;

		EgfStdField uf = new EgfStdField();
		uf.fieldId = fieldId;
		uf.fieldType = 31;
		uf.nUserType = userType;
		uf.value = userData;

		EgfStdCoder encoder = new EgfStdCoder();
		encoder.addCoder(userType, coder);

		return encodeField(uf, encoder);
	}

	public EgfStdDocHead getDocHead() {
		return this.docHead;
	}

	public int startReadDoc() {
		int n = this.inBuf.readInt();
		if ((!EgfStdDocHead.isValidHeadSize(n)) || (!this.inBuf.hasMore(n - 4)))
			return -1;
		byte[] data = new byte[n];
		DataConv.int4ToChar4(n, data);

		this.inBuf.readBinary(data, 4, n - 4);

		if (this.docHead.fromBinary(data) < 0)
			return -1;
		return 1;
	}

	public int startWriteDoc() {
		this.outBuf.writeByteArray(this.docHead.toBinary());
		return 1;
	}

	public int endWriteDoc() {
		writeTag(70);
		return 1;
	}

	public int parseDoc(List<EgfStdField> doc, EgfStdCoder coder) {
		EgfStdField uf;
		do {
			uf = new EgfStdField();
			readNextField(uf, coder);
			if (uf.fieldType == 70)
				break;
			doc.add(uf);
		} while (uf.fieldType != 70);

		if (this.inBuf.hasMore())
			return -1;
		return 1;
	}

	public int writeDoc(List<EgfStdField> doc, EgfStdCoder coder) {
		for (EgfStdField uf : doc) {
			encodeField(uf, coder);
		}
		return 1;
	}

	public int parseDoc(EgfStdField uf, EgfStdCoder coder) {
		readNextField(uf, coder);
		if (uf.fieldType == 70)
			return 0;
		return 1;
	}

	public int writeDoc(EgfStdField uf, EgfStdCoder coder) {
		return encodeField(uf, coder);
	}

	public int writeBoolField(int fieldId, boolean n) {
		writeTag(2);
		writeFieldId(fieldId);
		return writeBool(n);
	}

	public int writeField(int fieldId, boolean n) {
		return writeBoolField(fieldId, n);
	}

	public int writeField(int fieldId, byte n) {
		return writeByteField(fieldId, n);
	}

	public int writeField(int fieldId, short n) {
		return writeShortField(fieldId, n);
	}

	public int writeField(int fieldId, int n) {
		return writeIntField(fieldId, n);
	}

	public int writeField(int fieldId, long n) {
		return writeLongField(fieldId, n);
	}

	public int writeField(int fieldId, String n) {
		return writeStringField(fieldId, n);
	}

	public int writeField(int fieldId, Timestamp n) {
		return writeLongTimeField(fieldId, n);
	}

	public int writeField(int fieldId, float n) {
		return writeFloatField(fieldId, n);
	}

	public int writeField(int fieldId, double n) {
		return writeDoubleField(fieldId, n);
	}

	public int writeField(int fieldId, byte[] n) {
		return writeBinaryField(fieldId, n);
	}

	public int writeField(int fieldId, short[] n) {
		if (n == null)
			return 0;
		EgfStdField f = new EgfStdField();
		f.fieldId = fieldId;
		f.fieldType = 25;
		f.value = n;
		return encodeField(f, null);
	}

	public int writeField(int fieldId, int[] n) {
		if (n == null)
			return 0;
		EgfStdField f = new EgfStdField();
		f.fieldId = fieldId;
		f.fieldType = 26;
		f.value = n;
		return encodeField(f, null);
	}

	public int writeField(int fieldId, long[] n) {
		if (n == null)
			return 0;
		EgfStdField f = new EgfStdField();
		f.fieldId = fieldId;
		f.fieldType = 27;
		f.value = n;
		return encodeField(f, null);
	}

	public int writeField(int fieldId, Object obj) {
		if ((obj instanceof Byte)) {
			writeByteField(fieldId, ((Byte) obj).byteValue());
		} else if ((obj instanceof Short)) {
			writeShortField(fieldId, ((Short) obj).shortValue());
		} else if ((obj instanceof String)) {
			writeStringField(fieldId, (String) obj);
		} else if ((obj instanceof Boolean)) {
			writeBoolField(fieldId, ((Boolean) obj).booleanValue());
		} else if ((obj instanceof Integer)) {
			writeIntField(fieldId, ((Integer) obj).intValue());
		} else if ((obj instanceof Long)) {
			writeLongField(fieldId, ((Long) obj).longValue());
		} else if ((obj instanceof Timestamp)) {
			writeLongTimeField(fieldId, (Timestamp) obj);
		} else if ((obj instanceof Float)) {
			writeFloatField(fieldId, ((Float) obj).floatValue());
		} else if ((obj instanceof Double)) {
			writeDoubleField(fieldId, ((Double) obj).doubleValue());
		} else if ((obj instanceof byte[])) {
			writeField(fieldId, (byte[]) obj);
		} else if ((obj instanceof short[])) {
			writeField(fieldId, (short[]) obj);
		} else if ((obj instanceof int[])) {
			writeField(fieldId, (int[]) obj);
		} else if ((obj instanceof long[])) {
			writeField(fieldId, (long[]) obj);
		} else {
			return 0;
		}

		return 1;
	}
}
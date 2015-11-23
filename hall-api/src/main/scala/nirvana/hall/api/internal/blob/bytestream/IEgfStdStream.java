package nirvana.hall.api.internal.blob.bytestream;

import java.sql.Timestamp;
import java.util.List;

import nirvana.hall.api.internal.blob.util.AFISDate;
import nirvana.hall.api.internal.blob.util.AFISDateTime;
import nirvana.hall.api.internal.blob.util.AFISTime;

public abstract interface IEgfStdStream {

	public abstract int readTag();

	public abstract int writeTag(int paramInt);

	public abstract int readFieldId();

	public abstract int writeFieldId(int paramInt);

	public abstract int writeMInt(int paramInt);

	public abstract int readMInt();

	public abstract int encodeField(EgfStdField paramEgfStdField,
									EgfStdCoder paramEgfStdCoder);

	public abstract int decodeField(EgfStdField paramEgfStdField,
									EgfStdCoder paramEgfStdCoder);

	public abstract int readNextField(EgfStdField paramEgfStdField,
									  EgfStdCoder paramEgfStdCoder);

	public abstract int writeBool(boolean paramBoolean);

	public abstract int writeTiny(byte paramByte);

	public abstract int writeShort(short paramShort);

	public abstract int writeInt(int paramInt);

	public abstract int writeLong(long paramLong);

	public abstract int writeString(String paramString, int paramInt);

	public abstract int writeString(String paramString);

	public abstract int writeFloat(float paramFloat);

	public abstract int writeDouble(double paramDouble);

	public abstract int writeUtiny(short paramShort);

	public abstract int writeUshort(int paramInt);

	public abstract int writeUint(long paramLong);

	public abstract int writeDateTime(AFISDateTime paramAFISDateTime);

	public abstract int writeDate(AFISDate paramAFISDate);

	public abstract int writeTime(AFISTime paramAFISTime);

	public abstract int writeLongTime(Timestamp paramTimestamp);

	public abstract int writeBinary(byte[] paramArrayOfByte);

	public abstract int writeBoolField(int paramInt, boolean paramBoolean);

	public abstract int writeTinyField(int paramInt, byte paramByte);

	public abstract int writeByteField(int paramInt, byte paramByte);

	public abstract int writeShortField(int paramInt, short paramShort);

	public abstract int writeIntField(int paramInt1, int paramInt2);

	public abstract int writeLongField(int paramInt, long paramLong);

	public abstract int writeUtinyField(int paramInt, short paramShort);

	public abstract int writeUshortField(int paramInt1, int paramInt2);

	public abstract int writeUintField(int paramInt, long paramLong);

	public abstract int writeStringField(int paramInt1, String paramString,
										 int paramInt2);

	public abstract int writeStringField(int paramInt, String paramString);

	public abstract int writeDateTimeField(int paramInt,
										   AFISDateTime paramAFISDateTime);

	public abstract int writeDateField(int paramInt, AFISDate paramAFISDate);

	public abstract int writeTimeField(int paramInt, AFISTime paramAFISTime);

	public abstract int writeLongTimeField(int paramInt,
										   Timestamp paramTimestamp);

	public abstract int writeFloatField(int paramInt, float paramFloat);

	public abstract int writeDoubleField(int paramInt, double paramDouble);

	public abstract int writeBinaryField(int paramInt, byte[] paramArrayOfByte);

	public abstract int writeField(int paramInt, boolean paramBoolean);

	public abstract int writeField(int paramInt, byte paramByte);

	public abstract int writeField(int paramInt, short paramShort);

	public abstract int writeField(int paramInt1, int paramInt2);

	public abstract int writeField(int paramInt, long paramLong);

	public abstract int writeField(int paramInt, String paramString);

	public abstract int writeField(int paramInt, Timestamp paramTimestamp);

	public abstract int writeField(int paramInt, float paramFloat);

	public abstract int writeField(int paramInt, double paramDouble);

	public abstract int writeField(int paramInt, byte[] paramArrayOfByte);

	public abstract int writeField(int paramInt, short[] paramArrayOfShort);

	public abstract int writeField(int paramInt, int[] paramArrayOfInt);

	public abstract int writeField(int paramInt, long[] paramArrayOfLong);

	public abstract int writeField(int paramInt, Object paramObject);

	public abstract int writeUDF(int paramInt1, int paramInt2,
								 Object paramObject, IEgfStdCoder paramIEgfStdCoder);

	public abstract int writeUDList(int paramInt1, int paramInt2,
									List paramList, IEgfStdCoder paramIEgfStdCoder);

	public abstract boolean readBool();

	public abstract byte readTiny();

	public abstract short readShort();

	public abstract int readInt();

	public abstract long readLong();

	public abstract String readString(int paramInt);

	public abstract float readFloat();

	public abstract double readDouble();

	public abstract short readUtiny();

	public abstract int readUshort();

	public abstract long readUint();

	public abstract byte[] readBinary(int paramInt);

	public abstract AFISDateTime readDateTime();

	public abstract AFISDate readDate();

	public abstract AFISTime readTime();

	public abstract AFISLongTime readLongTime();

	public abstract int startReadDoc();

	public abstract int startWriteDoc();

	public abstract int endWriteDoc();

	public abstract int parseDoc(List<EgfStdField> paramList,
								 EgfStdCoder paramEgfStdCoder);

	public abstract int writeDoc(List<EgfStdField> paramList,
								 EgfStdCoder paramEgfStdCoder);

	public abstract int parseDoc(EgfStdField paramEgfStdField,
								 EgfStdCoder paramEgfStdCoder);

	public abstract int writeDoc(EgfStdField paramEgfStdField,
								 EgfStdCoder paramEgfStdCoder);

	public abstract EgfStdDocHead getDocHead();
}
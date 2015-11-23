package nirvana.hall.api.internal.blob.bytestream;

public abstract interface IBufferOut {
	public abstract void writeByte(byte paramByte);

	public abstract void writeBytes(byte[] paramArrayOfByte);

	public abstract void writeByteArray(byte[] paramArrayOfByte);

	public abstract void writeByteArray(byte[] paramArrayOfByte, int paramInt1,
										int paramInt2);

	public abstract void writeInt(int paramInt);

	public abstract void writeShort(short paramShort);

	public abstract void writeUint(long paramLong);

	public abstract void writeUshort(int paramInt);

	public abstract void writeAscii(String paramString, int paramInt);

	public abstract void writeUbyte(short paramShort);

	public abstract void writeBool(boolean paramBoolean);

	public abstract int length();

	public abstract void skip(int paramInt);

	public abstract void moveTo(int paramInt);

	public abstract byte[] toBytes();

	public abstract byte[] getBuffer();

	public abstract int getCurPos();

	public abstract void moveEnd();

	public abstract void writeLong(long paramLong);
}
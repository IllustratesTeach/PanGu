package nirvana.hall.api.internal.blob.bytestream;

public abstract interface IBufferIn
{
  public abstract byte readByte();

  public abstract short readUbyte();

  public abstract int readUshort();

  public abstract short readShort();

  public abstract int readInt();

  public abstract long readUint();

  public abstract long readLong();

  public abstract void skip(int paramInt);

  public abstract byte[] readBinary(int paramInt);

  public abstract void readBinary(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  public abstract String readAscii(int paramInt);

  public abstract boolean readBool();

  public abstract boolean hasMore();

  public abstract boolean hasMore(int paramInt);

  public abstract int moveTo(int paramInt);

  public abstract int getCurPos();

  public abstract byte peekByte();

  public abstract short peekUbyte();

  public abstract int peekInt();

  public abstract short peekShort();

  public abstract void peekBinary(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  public abstract BytePointer getPointer(int paramInt);

  public abstract byte[] getBuffer();
}
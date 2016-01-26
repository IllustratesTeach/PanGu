package nirvana.hall.v70.internal.blob.bytestream;

public abstract interface IStringStream
{
  public abstract byte[] toBytes(String paramString, int paramInt);

  public abstract String fromBytes(byte[] paramArrayOfByte, int paramInt);

  public abstract String fromBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3);
}
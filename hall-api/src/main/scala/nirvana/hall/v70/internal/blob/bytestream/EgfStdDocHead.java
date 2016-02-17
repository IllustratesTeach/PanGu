package nirvana.hall.v70.internal.blob.bytestream;

import nirvana.hall.v70.internal.blob.util.DataConv;

public class EgfStdDocHead
{
  public int majorVer = 1;
  public int minorVer = 0;
  public static final byte[] MAGIC = { 69, 71, 70, 83, 84, 68, 67, 78 };

  public byte[] toBinary()
  {
    byte[] data = new byte[64];

    DataConv.int4ToChar4(64, data);
    data[4] = 1;

    System.arraycopy(MAGIC, 0, data, 8, 8);
    return data;
  }

  public int fromBinary(byte[] data)
  {
    int n = DataConv.char4ToInt4(data);
    if (!isValidHeadSize(n)) return -1;
    this.majorVer = data[4];
    this.minorVer = data[5];
    for (int i = 0; i < 8; i++)
    {
      if (data[(8 + i)] != MAGIC[i]) return -1;
    }
    return 1;
  }

  public static boolean isValidHeadSize(int n)
  {
    return (n >= 64) && (n <= 512);
  }
}
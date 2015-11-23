package nirvana.hall.api.internal.blob.bytestream;

import nirvana.hall.api.internal.blob.util.DataConv;

public class MemInputStream implements IBufferIn {
  /**
   * 整个二进制
   */
  private byte[] buffer;
  
  private int curPos;
  private int lowPos;
  
  /**
   * 整个二进制的长度
   */
  private int hiPos;
  private byte[] tempBuf = new byte[64];

  public MemInputStream(byte[] buf)
  {
    this(buf, 0, buf.length);
  }

  public MemInputStream(byte[] buf, int offset, int length)
  {
    this.buffer = buf;
    this.curPos = offset;
    this.lowPos = offset;
    this.hiPos = (offset + length);
  }

  public boolean hasMore()
  {
    return hasMore(1);
  }

  public boolean hasMore(int left)
  {
    return this.curPos + left <= this.hiPos;
  }

  private void ensureHasMore(int n)
  {
    if (!hasMore(n))
    {
      throw new RuntimeException();
    }
  }

  public String readAscii(int length)
  {
    ensureHasMore(length);
    String temp = DataConv.asciiByteToString(this.buffer, this.curPos, length);
    this.curPos += length;
    return temp;
  }

  public byte[] readBinary(int length)
  {
    byte[] temp = new byte[length];
    readBinary(temp, 0, length);
    return temp;
  }

  public void readBinary(byte[] buf, int offset, int length)
  {
    ensureHasMore(length);
    if (buf != null)
    {
      System.arraycopy(this.buffer, this.curPos, buf, offset, length);
    }
    this.curPos += length;
  }

  public boolean readBool()
  {
    readBinary(this.tempBuf, 0, 1);
    return this.tempBuf[0] != 0;
  }

  public byte readByte()
  {
    readBinary(this.tempBuf, 0, 1);
    return this.tempBuf[0];
  }

  public int readInt()
  {
    readBinary(this.tempBuf, 0, 4);
    return DataConv.char4ToInt4(this.tempBuf);
  }

  public long readLong()
  {
    readBinary(this.tempBuf, 0, 8);
    return DataConv.char8ToInt8(this.tempBuf);
  }

  public short readShort()
  {
    readBinary(this.tempBuf, 0, 2);
    return DataConv.char2ToInt2(this.tempBuf);
  }

  public short readUbyte()
  {
    readBinary(this.tempBuf, 0, 1);
    return DataConv.ubyteToShort(this.tempBuf[0]);
  }

  public long readUint()
  {
    readBinary(this.tempBuf, 0, 4);
    return DataConv.uchar4ToInt8(this.tempBuf);
  }

  public int readUshort()
  {
    readBinary(this.tempBuf, 0, 2);
    return DataConv.uchar2ToInt4(this.tempBuf);
  }

  public void skip(int n)
  {
    readBinary(null, 0, n);
  }

  public int moveTo(int newPos)
  {
    if ((newPos < this.lowPos) || (newPos > this.hiPos)) return -1;
    this.curPos = newPos;
    return this.curPos;
  }

  public int getCurPos()
  {
    return this.curPos;
  }

  public byte peekByte()
  {
    peekBinary(this.tempBuf, 0, 1);
    return this.tempBuf[0];
  }

  public int peekInt()
  {
    peekBinary(this.tempBuf, 0, 4);
    return DataConv.char4ToInt4(this.tempBuf);
  }

  public short peekShort()
  {
    peekBinary(this.tempBuf, 0, 2);
    return DataConv.char2ToInt2(this.tempBuf);
  }

  public short peekUbyte()
  {
    peekBinary(this.tempBuf, 0, 1);
    return DataConv.ubyteToShort(this.tempBuf[0]);
  }

  public void peekBinary(byte[] buf, int offset, int length)
  {
    ensureHasMore(length);
    if (buf != null)
    {
      System.arraycopy(this.buffer, this.curPos, buf, offset, length);
    }
  }

  public BytePointer getPointer(int length)
  {
    ensureHasMore(length);

    BytePointer bp = new BytePointer();
    bp.data = this.buffer;
    bp.offset = this.curPos;
    bp.length = length;
    this.curPos += length;
    return bp;
  }

  public byte[] getBuffer()
  {
    return this.buffer;
  }
}
package nirvana.hall.v70.internal.blob.util;

import java.math.BigInteger;
import java.nio.charset.Charset;

import nirvana.hall.v70.internal.blob.error.AFISError;


public class DataConv
{
  public static final short UBYTE_MAX = 255;
  public static final int USHORT_MAX = 65535;
  public static final long UINT_MAX = 4294967295L;
  public static final Charset asciiCharSet = Charset.forName("US-ASCII");

  private static int[] nValue = { 128, 16384, 2097152, 268435456 };
  private static int[] nMask = { 0, 128, 192, 224, 240 };

  public static short char2ToInt2(byte[] b)
  {
    return (short)(b[0] << 8 | ubyteToShort(b[1]));
  }

  public static short char2ToInt2(byte[] b, int startidx)
  {
    return (short)(b[startidx] << 8 | ubyteToShort(b[(startidx + 1)]));
  }

  public static int uchar2ToInt4(byte[] b)
  {
    short n = char2ToInt2(b);
    return ushortToInt(n);
  }

  public static int uchar2ToInt4(byte[] b, int offset)
  {
    short n = char2ToInt2(b, offset);
    return ushortToInt(n);
  }

  public static void int2ToChar2(short a, byte[] b)
  {
    b[0] = (byte)(a >> 8 & 0xFF);
    b[1] = (byte)(a & 0xFF);
  }

  public static void int2ToChar2(short a, byte[] b, int startidx)
  {
    b[startidx] = (byte)(a >> 8 & 0xFF);
    b[(startidx + 1)] = (byte)(a & 0xFF);
  }

  public static byte[] int2ToChar2(short a)
  {
    byte[] b = new byte[2];
    int2ToChar2(a, b);
    return b;
  }

  public static byte int2ToUbyte(short a)
    throws AFISError
  {
    if ((a < 0) || (a > 255)) throw new AFISError(146);
    return (byte)a;
  }

  public static byte int4ToUbyte(int a) throws AFISError
  {
    if ((a < 0) || (a > 255)) throw new AFISError(146);
    return (byte)a;
  }

  public static int char4ToInt4(byte[] b)
  {
    return ubyteToShort(b[0]) << 24 | ubyteToShort(b[1]) << 16 | 
      ubyteToShort(b[2]) << 8 | ubyteToShort(b[3]);
  }

  public static int char4ToInt4(byte[] b, int startidx)
  {
    return ubyteToShort(b[startidx]) << 24 | ubyteToShort(b[(startidx + 1)]) << 16 | 
      ubyteToShort(b[(startidx + 2)]) << 8 | ubyteToShort(b[(startidx + 3)]);
  }

  public static long uchar4ToInt8(byte[] b)
  {
    int n = char4ToInt4(b);
    return uintToLong(n);
  }

  public static BigInteger uchar8ToInt8(byte[] b)
  {
    return new BigInteger(1, b);
  }

  public static void int4ToChar4(int a, byte[] b)
  {
    b[0] = (byte)(a >> 24 & 0xFF);
    b[1] = (byte)(a >> 16 & 0xFF);
    b[2] = (byte)(a >> 8 & 0xFF);
    b[3] = (byte)(a & 0xFF);
  }

  public static void int4ToChar4(int a, byte[] b, int startidx)
  {
    b[startidx] = (byte)(a >> 24 & 0xFF);
    b[(startidx + 1)] = (byte)(a >> 16 & 0xFF);
    b[(startidx + 2)] = (byte)(a >> 8 & 0xFF);
    b[(startidx + 3)] = (byte)(a & 0xFF);
  }

  public static byte[] int4ToChar4(int a)
  {
    byte[] b = new byte[4];
    int4ToChar4(a, b);
    return b;
  }

  public static void int4ToUchar2(int a, byte[] b)
    throws AFISError
  {
    if ((a > 65535) || (a < 0)) {
      throw new AFISError(146);
    }
    int2ToChar2((short)a, b);
  }

  public static void int4ToUchar2(int a, byte[] b, int startidx) throws AFISError
  {
    if ((a > 65535) || (a < 0)) {
      throw new AFISError(146);
    }
    int2ToChar2((short)a, b, startidx);
  }

  public static long char8ToInt8(byte[] b)
  {
    long a = b[0];
    for (int i = 1; i < 8; i++)
    {
      a = a << 8 | ubyteToShort(b[i]);
    }
    return a;
  }

  public static long char8ToInt8(byte[] b, int startidx)
  {
    long a = b[startidx];
    for (int i = 1; i < 8; i++)
    {
      a = a << 8 | ubyteToShort(b[(startidx + i)]);
    }
    return a;
  }

  public static void int8ToChar8(long a, byte[] b)
  {
    b[7] = (byte)(int)(a & 0xFF); a >>= 8;
    b[6] = (byte)(int)(a & 0xFF); a >>= 8;
    b[5] = (byte)(int)(a & 0xFF); a >>= 8;
    b[4] = (byte)(int)(a & 0xFF); a >>= 8;
    b[3] = (byte)(int)(a & 0xFF); a >>= 8;
    b[2] = (byte)(int)(a & 0xFF); a >>= 8;
    b[1] = (byte)(int)(a & 0xFF); a >>= 8;
    b[0] = (byte)(int)(a & 0xFF);
  }

  public static void int8ToChar8(long a, byte[] b, int startidx)
  {
    b[(startidx + 7)] = (byte)(int)(a & 0xFF); a >>= 8;
    b[(startidx + 6)] = (byte)(int)(a & 0xFF); a >>= 8;
    b[(startidx + 5)] = (byte)(int)(a & 0xFF); a >>= 8;
    b[(startidx + 4)] = (byte)(int)(a & 0xFF); a >>= 8;
    b[(startidx + 3)] = (byte)(int)(a & 0xFF); a >>= 8;
    b[(startidx + 2)] = (byte)(int)(a & 0xFF); a >>= 8;
    b[(startidx + 1)] = (byte)(int)(a & 0xFF); a >>= 8;
    b[startidx] = (byte)(int)(a & 0xFF);
  }

  public static byte[] int8ToChar8(long a)
  {
    byte[] b = new byte[8];
    int8ToChar8(a, b);
    return b;
  }

  public static void int8ToUchar4(long a, byte[] b)
    throws AFISError
  {
    if ((a < 0L) || (a > 4294967295L)) throw new AFISError(146);
    int4ToChar4((int)a, b);
  }

  public static void int8ToUchar4(long a, byte[] b, int startidx) throws AFISError
  {
    if ((a < 0L) || (a > 4294967295L)) throw new AFISError(146);
    int4ToChar4((int)a, b, startidx);
  }

  public static void floatToChar4(float f, byte[] b)
  {
    int4ToChar4(Float.floatToRawIntBits(f), b);
  }

  public static void floatToChar4(float f, byte[] b, int startidx)
  {
    int4ToChar4(Float.floatToRawIntBits(f), b, startidx);
  }

  public static byte[] floatToChar4(float f)
  {
    byte[] b = new byte[4];
    floatToChar4(f, b);
    return b;
  }

  public static float char4ToFloat(byte[] b)
  {
    return Float.intBitsToFloat(char4ToInt4(b));
  }

  public static float char4ToFloat(byte[] b, int startidx)
  {
    return Float.intBitsToFloat(char4ToInt4(b, startidx));
  }

  public static void doubleToChar8(double f, byte[] b)
  {
    int8ToChar8(Double.doubleToRawLongBits(f), b);
  }

  public static void doubleToChar8(double f, byte[] b, int startidx)
  {
    int8ToChar8(Double.doubleToRawLongBits(f), b, startidx);
  }

  public static byte[] doubleToChar8(double f)
  {
    byte[] b = new byte[8];
    doubleToChar8(f, b);
    return b;
  }

  public static double char8ToDouble(byte[] b)
  {
    return Double.longBitsToDouble(char8ToInt8(b));
  }

  public static double char8ToDouble(byte[] b, int startidx)
  {
    return Double.longBitsToDouble(char8ToInt8(b, startidx));
  }

  public static short ubyteToShort(byte n)
  {
    return (short)(n & 0xFF);
  }

  public static int ushortToInt(short n)
  {
    return n & 0xFFFF;
  }

  public static long uintToLong(int n)
  {
    long l = n;
    return l & 0xFFFFFFFF;
  }

  public static byte[] toAscii(String value)
  {
    return value.getBytes(asciiCharSet);
  }

  public static String asciiByteToString(byte[] data, int offset, int length)
  {
    if (offset + length > data.length) return null;

    int n;
    for (n = offset; n < offset + length; n++)
    {
      if (data[n] == 0) break;
    }
    length = n - offset;
    return new String(data, offset, length, asciiCharSet);
  }

  public static byte[] int4ToMChar(int n, byte[] data, int offset)
  {
    if (n < 0) return null;

    int i;
    for (i = 0; i < 4; i++)
    {
      if (n < nValue[i]) break;
    }
    if (i == 4) return null;

    i++;

    if (data == null)
    {
      data = new byte[i];
      offset = 0;
    }

    byte[] nc = new byte[4];

    int4ToChar4(n, nc);
    for (n = 0; n < i; n++)
    {
      data[(n + offset)] = nc[(4 - i + n)];
    }
    int tmp92_91 = (0 + offset);
    byte[] tmp92_88 = data; tmp92_88[tmp92_91] = (byte)(tmp92_88[tmp92_91] | nMask[(i - 1)]);

    return data;
  }

  public static int mcharToInt4(byte[] data, int offset)
  {
    int i = mcharByteCnt(data[(0 + offset)]);
    if (i < 0) return -1;

    int n = data[(0 + offset)] & (nMask[i] ^ 0xFFFFFFFF) & 0xFF;

    for (int k = 1; k < i; k++)
    {
      n = n << 8 | data[(k + offset)] & 0xFF;
    }
    return n;
  }

  public static int mcharByteCnt(byte firstByte)
  {
    for (int i = 1; i < 5; i++)
    {
      if ((firstByte & nMask[i]) == nMask[(i - 1)]) return i;
    }
    return -1;
  }

  public static long[] longBytesToLong(byte[] data)
  {
    if (data == null) return null;
    int n = data.length / 8;
    if (n == 0) return null;
    long[] v = new long[n];

    for (int i = 0; i < n; i++)
    {
      v[i] = char8ToInt8(data, i * 8);
    }
    return v;
  }

  public static byte[] longToLongBytes(long[] data)
  {
    if (data == null) return null;
    byte[] v = new byte[data.length * 8];

    for (int i = 0; i < data.length; i++)
    {
      int8ToChar8(data[i], v, i * 8);
    }
    return v;
  }
}
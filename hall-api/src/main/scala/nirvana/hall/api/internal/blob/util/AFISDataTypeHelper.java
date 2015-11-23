package nirvana.hall.api.internal.blob.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import nirvana.hall.api.internal.blob.error.AFISError;


public class AFISDataTypeHelper
{
  private static final SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");
  private static final SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");

  public static Object convert(Class<?> cla, Object src)
  {
    if (src == null) return null;

    String name = cla.getName();

    if (name.equals("java.lang.String")) return toString(src);
    if (name.equals("long")) return toLong(src);
    if (name.equals("int")) return toInt(src);
    if (name.equals("short")) return toShort(src);
    if (name.equals("byte")) return toByte(src);
    if (name.equals("char")) return toChar(src);
    if (name.equals("double")) return toDouble(src);
    if (name.equals("boolean")) return toBoolean(src);
    if (name.equals("java.lang.Character")) return toChar(src);
    if (name.equals("java.lang.Double")) return toDouble(src);
    if (name.equals("java.lang.Long")) return toLong(src);
    if (name.equals("java.lang.Integer")) return toInt(src);
    if (name.equals("java.lang.Short")) return toShort(src);
    if (name.equals("java.lang.Byte")) return toByte(src);
    if (name.equals("java.lang.Boolean")) return toBoolean(src);
    if (name.equals("java.sql.Timestamp")) return toTimestamp(src);
    if (name.equals("java.sql.Date")) return toJavaSqlDate(src);
    if (name.equals("java.util.Date")) return toJavaUtilDate(src);

    return src;
  }

  public static String toString(Object src)
  {
    if (src == null) return null;

    if ((src instanceof Timestamp)) return df2.format(src);
    if ((src instanceof java.util.Date)) return df2.format(src);
    if ((src instanceof java.sql.Date)) return df1.format(src);
    return src.toString();
  }

  public static Character toChar(Object src)
  {
    String str = toString(src);

    if (AFISHelper.isEmpty(str)) return null;

    return Character.valueOf(str.charAt(0));
  }

  public static Double toDouble(Object src)
  {
    if (src == null) return null;
    String str = src.toString();
    if (AFISHelper.isEmpty(str)) return null;
    return Double.valueOf(Double.parseDouble(str));
  }

  public static Long toLong(Object src)
  {
    if (src == null) return null;
    if ((src instanceof Number)) return Long.valueOf(((Number)src).longValue());
    String str = src.toString();
    if (AFISHelper.isEmpty(str)) return null;
    return Long.valueOf(Long.parseLong(str));
  }

  public static Integer toInt(Object src)
  {
    Long val = toLong(src);

    return val == null ? null : Integer.valueOf((int)val.longValue());
  }

  public static Short toShort(Object src)
  {
    Long val = toLong(src);

    return val == null ? null : Short.valueOf((short)(int)val.longValue());
  }

  public static Byte toByte(Object src)
  {
    Long val = toLong(src);

    return val == null ? null : Byte.valueOf((byte)(int)val.longValue());
  }

  public static Boolean toBoolean(Object src)
  {
    if (src == null) return Boolean.valueOf(false);

    Long val = toLong(src);

    return val == null ? null : Boolean.valueOf(val.longValue() != 0L);
  }

  public static Timestamp toTimestamp(Object src)
  {
    if (AFISHelper.isEmpty(src)) return null;

    if ((src instanceof java.util.Date))
    {
      java.util.Date dt = (java.util.Date)src;
      return new Timestamp(dt.getTime());
    }

    try
    {
      String str = src.toString();
      SimpleDateFormat df = str.length() == 8 ? df1 : df2;
      return new Timestamp(df.parse(str).getTime());
    }
    catch (ParseException e) {
    	throw new AFISError(e);
    }
    
  }

  public static void main(String[] args)
  {
    String str = "20101212";
    Timestamp stamp = toTimestamp(str);
  }

  public static java.sql.Date toJavaSqlDate(Object src)
  {
    Timestamp ts = toTimestamp(src);

    if (ts == null) return null;

    return new java.sql.Date(ts.getTime());
  }

  public static java.util.Date toJavaUtilDate(Object src)
  {
    Timestamp ts = toTimestamp(src);

    if (ts == null) return null;

    return new java.util.Date(ts.getTime());
  }
}
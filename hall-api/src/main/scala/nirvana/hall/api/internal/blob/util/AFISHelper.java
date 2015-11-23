package nirvana.hall.api.internal.blob.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;

import nirvana.hall.api.internal.blob.error.AFISError;


public class AFISHelper
{
  public static boolean isEmpty(Collection<?> list)
  {
    return (list == null) || (list.isEmpty());
  }

  public static boolean isEmpty(Object obj)
  {
    return (obj == null) || (obj.toString().isEmpty());
  }

  public static <K, V> boolean isEmpty(Map<K, V> map)
  {
    return (map == null) || (map.isEmpty());
  }

  public static <T> boolean isEmpty(T[] array)
  {
    return (array == null) || (array.length == 0);
  }

  public static boolean isEmpty(long[] array)
  {
    return (array == null) || (array.length == 0);
  }

  public static boolean isEmpty(byte[] array)
  {
    return (array == null) || (array.length == 0);
  }

  public static boolean isEmpty(String str)
  {
    return (str == null) || (str.isEmpty());
  }

  public static boolean isDigit(String str)
  {
    if (isEmpty(str)) return false;

    for (char c : str.toCharArray())
    {
      if (!Character.isDigit(c)) return false;
    }

    return true;
  }

  public static boolean isAllChars(String str, char c)
  {
    if (isEmpty(str)) return false;

    for (char c1 : str.toCharArray())
    {
      if (c1 != c) return false;
    }
    return true;
  }

  public static String[] split(String str, String split)
  {
    if ((isEmpty(str)) || (isEmpty(split))) return null;

    return str.split(split);
  }

  public static String[] split(String str, char split)
  {
    return split(str, String.valueOf(split));
  }

  public static <T extends Number> List<T> split(String str, String split, Class<T> c)
  {
    String[] items = split(str, split);
    if ((items == null) || (items.length < 1)) return null;

    List list = new ArrayList();
    for (String item : items)
    {
      item = item.trim();
      Number num = null;
      try
      {
        num = (Number)c.getConstructor(new Class[] { String.class }).newInstance(new Object[] { item });
      }
      catch (Exception e)
      {
        throw new AFISError(e);
      }
      list.add(num);
    }
    return list;
  }

  public static String createString(char c, int length)
  {
    if (length < 1) return new String();

    char[] chars = new char[length];

    Arrays.fill(chars, c);

    return new String(chars);
  }

  public static String createZeroString(int length)
  {
    return createString('0', length);
  }

  public static String createEmptyString(int length)
  {
    return createString('\000', length);
  }

  public static String toRegex(String wildString)
  {
    if (isEmpty(wildString)) return null;

    StringBuffer sb = new StringBuffer();

    if (wildString.charAt(0) != '*') sb.append("^");

    wildString = wildString.replaceAll("\\.", "\\\\.");
    wildString = wildString.replaceAll("\\+", "\\\\+");
    wildString = wildString.replaceAll("\\*+", "\\\\w*");
    wildString = wildString.replaceAll("\\?", "\\\\w");
    sb.append(wildString);

    if (wildString.charAt(wildString.length() - 1) != '*') sb.append("$");

    return sb.toString();
  }

  public static List<String> toRegex(Collection<String> wildStringList)
  {
    if (wildStringList == null) return null;

    List regexList = new ArrayList();

    for (String wildString : wildStringList)
    {
      regexList.add(toRegex(wildString));
    }

    return regexList;
  }

  public static List<String> toRegex(String[] wildStringList)
  {
    if (wildStringList == null) return null;

    List regexList = new ArrayList();

    String[] arrayOfString = wildStringList; int j = wildStringList.length; for (int i = 0; i < j; i++) { String wildString = arrayOfString[i];

      if (wildString != null) {
        regexList.add(toRegex(wildString));
      }
    }
    return regexList;
  }

  public static boolean matches(Collection<String> regexList, String str)
  {
    if (isEmpty(regexList)) return false;

    for (String regex : regexList)
    {
      if (str.matches(regex)) return true;
    }

    return false;
  }

  public static String getExceptionStackTrace(Exception e)
  {
    StringWriter sw = new StringWriter();
    e.printStackTrace(new PrintWriter(sw));
    return sw.toString();
  }

  public static String toString(int number, int length)
  {
    String format = String.format("%%0%dd", new Object[] { Integer.valueOf(length) });

    return String.format(format, new Object[] { Integer.valueOf(number) });
  }

  public static String toString(Object obj)
  {
    if (obj == null) return null;
    return obj.toString();
  }

  public static Byte toByte(Object obj)
  {
    if (isEmpty(obj)) return null;
    return Byte.valueOf(obj.toString());
  }

  public static Short toShort(Object obj)
  {
    if (isEmpty(obj)) return null;
    return Short.valueOf(obj.toString());
  }

  public static Integer toInteger(Object obj)
  {
    if (isEmpty(obj)) return null;
    return Integer.valueOf(obj.toString());
  }

  public static Long toLong(Object obj)
  {
    if (isEmpty(obj)) return null;
    return Long.valueOf(obj.toString());
  }

  public static Boolean toBoolean(Object obj)
  {
    if (isEmpty(obj)) return null;
    return Boolean.valueOf(obj.toString());
  }

  public static Boolean toBooleanByInt(Object obj)
  {
    if (isEmpty(obj)) return null;
    int value = Integer.parseInt(obj.toString());
    if (value == 1) return Boolean.valueOf(true);
    if (value == 0) return Boolean.valueOf(false);
    return null;
  }

  public static BigDecimal toBigDecimal(Object obj)
  {
    if (isEmpty(obj)) return null;
    BigDecimal big = new BigDecimal(obj.toString());
    return big;
  }

  public static byte toByteValue(Object obj)
  {
    if (isEmpty(obj)) return 0;
    return Byte.valueOf(obj.toString()).byteValue();
  }

  public static short toShortValue(Object obj)
  {
    if (isEmpty(obj)) return 0;
    return Short.valueOf(obj.toString()).shortValue();
  }

  public static int toIntegerValue(Object obj)
  {
    if (isEmpty(obj)) return 0;
    return Integer.valueOf(obj.toString()).intValue();
  }

  public static long toLongValue(Object obj)
  {
    if (isEmpty(obj)) return 0L;
    return Long.valueOf(obj.toString()).longValue();
  }

  public static boolean toBooleanValue(Object obj)
  {
    if (isEmpty(obj)) return false;
    return Boolean.valueOf(obj.toString()).booleanValue();
  }

  public static Boolean toBooleanValueByInt(Object obj)
  {
    if (isEmpty(obj)) return Boolean.valueOf(false);
    int value = Integer.parseInt(obj.toString());
    if (value == 1) return Boolean.valueOf(true);
    return Boolean.valueOf(false);
  }

  public static long toNumber(String str, int beginIndex, int endIndex, long def)
  {
    try
    {
      String s = str.substring(beginIndex, endIndex);
      return Long.parseLong(s.trim());
    }
    catch (Exception e) {
    }
    return def;
  }

  public static long toNumber(String str, long def)
  {
    try
    {
      return Long.parseLong(str.trim());
    }
    catch (Exception e) {
    }
    return def;
  }

  public static void saveDataToFile(String fileName, byte[] data)
  {
    FileOutputStream os = null;
    try
    {
      os = new FileOutputStream(fileName);
      os.write(data);
    }
    catch (Exception e)
    {
      e.printStackTrace();

      if (os != null)
      {
        try
        {
          os.close();
        }
        catch (IOException localIOException)
        {
        }
      }
    }
    finally
    {
      if (os != null)
      {
        try
        {
          os.close();
        }
        catch (IOException localIOException1)
        {
        }
      }
    }
  }

  public static boolean isNeedTransformImage(byte compressMethod)
  {
    switch (compressMethod)
    {
    case 1:
    case 101:
    case 102:
      return true;
    }

    return false;
  }

  public static String combine(Collection<?> list, String split)
  {
    if (isEmpty(list)) return null;
    StringBuffer sb = new StringBuffer();
    for (Iterator localIterator = list.iterator(); localIterator.hasNext(); ) { Object item = localIterator.next();

      sb.append(item.toString());
      sb.append(split);
    }
    return sb.substring(0, sb.length() - 1);
  }

  public static boolean contains(Collection<?> list, Object value)
  {
    if (isEmpty(list)) return false;
    for (Iterator localIterator = list.iterator(); localIterator.hasNext(); ) { Object item = localIterator.next();

      if (CompHelper.equals(item, value)) return true;
    }
    return false;
  }

  public static <T extends Number> boolean contains(Collection<T> list, T t)
  {
    if (isEmpty(list)) return false;
    for (Number item : list)
    {
      if (item.longValue() == t.longValue()) return true;
    }
    return false;
  }

  public static boolean wildMatch(String wild, String str)
  {
    wild = toRegex(wild);

    return str.matches(wild);
  }

  public static boolean isAll(String src, char c)
  {
    if (isEmpty(src)) return false;

    for (int i = 0; i < src.length(); i++)
      if (src.charAt(i) != c) return false;
    return true;
  }

  public static byte[] read(InputStream is)
    throws IOException
  {
    return read(is, 0);
  }

  public static byte[] read(InputStream is, int size)
    throws IOException
  {
    ByteArrayOutputStream os = new ByteArrayOutputStream(size);

    write(os, is, size);

    return os.toByteArray();
  }

  public static boolean hasOption(long option, long mask)
  {
    return (option & mask) != 0L;
  }

  public static int createPidMask(int usedMask)
  {
    int pidMask = 1;
    for (int i = 0; i < 32; pidMask <<= 1)
    {
      if (!hasOption(usedMask, pidMask)) return pidMask;
      i++;
    }

    return pidMask;
  }

  public static String format(String fmtStr, Object[] obj)
  {
    if (isEmpty(fmtStr))
    {
      return null;
    }
    if (isEmpty(obj))
    {
      return fmtStr;
    }
    String rex = "\\{[0-9]+\\}";
    Pattern p = Pattern.compile(rex);
    Matcher matcher = p.matcher(fmtStr);
    String result = fmtStr;
    while (matcher.find())
    {
      int start = matcher.start();
      int end = matcher.end();
      String fmtCell = fmtStr.substring(start, end);
      int index = Integer.parseInt(fmtCell.substring(1, fmtCell.length() - 1));
      if (index < obj.length) {
        String value = obj[index] != null ? obj[index].toString() : "null";
        result = result.replaceAll("\\{" + index + "\\}", value);
      }
    }
    return result;
  }

  public static String format(String fmtStr, List<Object> objList)
  {
    if (isEmpty(objList))
    {
      return fmtStr;
    }
    return format(fmtStr, objList.toArray());
  }

  public static int write(OutputStream os, InputStream is)
    throws IOException
  {
    byte[] buffer = new byte[4096];

    int length = 0;
    int bytesRead;
    while ((bytesRead = is.read(buffer)) != -1)
    {
      os.write(buffer, 0, bytesRead);

      length += bytesRead;
    }

    return length;
  }

  public static int write(OutputStream os, InputStream is, int size)
    throws IOException
  {
    if (size < 1) return write(os, is); 
byte[] buffer = new byte[4096];

    int length = 0;
    int bytesRead;
    do {
      bytesRead = Math.min(size - length, buffer.length);
      bytesRead = is.read(buffer, 0, bytesRead);
      if (bytesRead <= 0) {
        continue;
      }
      os.write(buffer, 0, bytesRead);
      length += bytesRead;
    }

    while ((bytesRead >= 0) && (length < size));

    return length;
  }

  public static <T> void convertArrayToCollection(T[] array, Collection<T> list)
  {
    if ((isEmpty(array)) || (list == null)) return;

    Object[] arrayOfObject = array; 
    int j = array.length; 
    for (int i = 0; i < j; i++) { 
    	Object item = arrayOfObject[i];
    	list.add((T) item);
    }
  }

  public static <T> List<T> convertArrayToList(T[] array)
  {
    if (isEmpty(array)) return null;

    List list = new ArrayList();
    convertArrayToCollection(array, list);
    return list;
  }

  public static long[] createLongArray(Collection<? extends Number> list)
  {
    if (isEmpty(list)) return null;

    int i = 0;
    long[] array = new long[list.size()];
    for (Number n : list)
    {
      array[(i++)] = n.longValue();
    }

    return array;
  }

  public static boolean sleepNoException(long milliSeconds)
  {
    try
    {
      Thread.sleep(milliSeconds);
      return true;
    }
    catch (InterruptedException e) {
    }
    return false;
  }

  public static byte[] read(DataHandler dh)
    throws IOException
  {
    return dh != null ? read(dh.getInputStream()) : null;
  }

  public static long limit(long minValue, long maxValue, long curValue)
  {
    if (curValue < minValue) return minValue;
    if (curValue > maxValue) return maxValue;
    return curValue;
  }

  public static boolean deleteFile(String fileName)
  {
    return new File(fileName).delete();
  }

  public static String replaceBlank(String str)
  {
    String dest = "";
    if (str != null)
    {
      Pattern p = Pattern.compile("\t|\r|\n");
      Matcher m = p.matcher(str);
      dest = m.replaceAll("");
    }
    return dest;
  }

  public static int length(byte[] data)
  {
    return data == null ? 0 : data.length;
  }

  public static String urlDecode(Object obj)
  {
    if (isEmpty(obj)) return null;
    try
    {
      return URLDecoder.decode(obj.toString(), "UTF-8");
    }
    catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }
    return null;
  }
}
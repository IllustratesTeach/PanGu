package nirvana.hall.v70.internal.blob.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MsgDigest
{
  public static byte[] md5(byte[] data)
  {
    try
    {
      MessageDigest digest = MessageDigest.getInstance("MD5");
      digest.update(data);
      return digest.digest();
    }
    catch (NoSuchAlgorithmException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  public static byte[] sha(byte[] data)
  {
    try
    {
      MessageDigest digest = MessageDigest.getInstance("SHA");
      digest.update(data);
      return digest.digest();
    }
    catch (NoSuchAlgorithmException e)
    {
      e.printStackTrace();
    }
    return null;
  }
}
package nirvana.hall.api.internal.blob.util;

import java.sql.Timestamp;

public class CompHelper
{
  public static boolean equals(Byte arg1, Byte arg2)
  {
    if (arg1 == arg2) return true;
    if ((arg1 == null) || (arg2 == null)) return false;
    return arg1.equals(arg2);
  }

  public static boolean equals(Short arg1, Short arg2)
  {
    if (arg1 == arg2) return true;
    if ((arg1 == null) || (arg2 == null)) return false;
    return arg1.equals(arg2);
  }

  public static boolean equals(Integer arg1, Integer arg2)
  {
    if (arg1 == arg2) return true;
    if ((arg1 == null) || (arg2 == null)) return false;
    return arg1.equals(arg2);
  }

  public static boolean equals(Long arg1, Long arg2)
  {
    if (arg1 == arg2) return true;
    if ((arg1 == null) || (arg2 == null)) return false;
    return arg1.equals(arg2);
  }

  public static boolean equals(Boolean arg1, Boolean arg2)
  {
    if (arg1 == arg2) return true;
    if ((arg1 == null) || (arg2 == null)) return false;
    return arg1.equals(arg2);
  }

  public static boolean equals(Float arg1, Float arg2)
  {
    if (arg1 == arg2) return true;
    if ((arg1 == null) || (arg2 == null)) return false;
    return arg1.equals(arg2);
  }

  public static boolean equals(Double arg1, Double arg2)
  {
    if (arg1 == arg2) return true;
    if ((arg1 == null) || (arg2 == null)) return false;
    return arg1.equals(arg2);
  }

  public static boolean equals(String arg1, String arg2)
  {
    if (arg1 == arg2) return true;
    if ((arg1 == null) || (arg2 == null)) return false;
    return arg1.equals(arg2);
  }

  public static boolean equalsIgnoreCase(String arg1, String arg2)
  {
    if (arg1 == arg2) return true;
    if ((arg1 == null) || (arg2 == null)) return false;
    return arg1.equalsIgnoreCase(arg2);
  }

  public static boolean equals(Object arg1, Object arg2)
  {
    if (arg1 == arg2) return true;
    if ((arg1 == null) || (arg2 == null)) return false;
    return arg1.equals(arg2);
  }

  public static boolean equals(Timestamp arg1, Timestamp arg2)
  {
    if (arg1 == arg2) return true;
    if ((arg1 == null) || (arg2 == null)) return false;
    return arg1.equals(arg2);
  }

  public static boolean equals(java.sql.Date arg1, java.sql.Date arg2)
  {
    if (arg1 == arg2) return true;
    if ((arg1 == null) || (arg2 == null)) return false;
    return arg1.equals(arg2);
  }

  public static boolean equals(java.util.Date arg1, java.util.Date arg2)
  {
    if (arg1 == arg2) return true;
    if ((arg1 == null) || (arg2 == null)) return false;
    return arg1.equals(arg2);
  }

  public static int compareTo(Byte arg1, Byte arg2)
  {
    if (arg1 == arg2) return 0;
    if (arg1 == null) return -1;
    if (arg2 == null) return 1;
    return arg1.compareTo(arg2);
  }

  public static int compareTo(Short arg1, Short arg2)
  {
    if (arg1 == arg2) return 0;
    if (arg1 == null) return -1;
    if (arg2 == null) return 1;
    return arg1.compareTo(arg2);
  }

  public static int compareTo(Integer arg1, Integer arg2)
  {
    if (arg1 == arg2) return 0;
    if (arg1 == null) return -1;
    if (arg2 == null) return 1;
    return arg1.compareTo(arg2);
  }

  public static int compareTo(Long arg1, Long arg2)
  {
    if (arg1 == arg2) return 0;
    if (arg1 == null) return -1;
    if (arg2 == null) return 1;
    return arg1.compareTo(arg2);
  }

  public static int compareTo(Boolean arg1, Boolean arg2)
  {
    if (arg1 == arg2) return 0;
    if (arg1 == null) return -1;
    if (arg2 == null) return 1;
    return arg1.compareTo(arg2);
  }

  public static int compareTo(Float arg1, Float arg2)
  {
    if (arg1 == arg2) return 0;
    if (arg1 == null) return -1;
    if (arg2 == null) return 1;
    return arg1.compareTo(arg2);
  }

  public static int compareTo(Double arg1, Double arg2)
  {
    if (arg1 == arg2) return 0;
    if (arg1 == null) return -1;
    if (arg2 == null) return 1;
    return arg1.compareTo(arg2);
  }

  public static int compareTo(String arg1, String arg2)
  {
    if (arg1 == arg2) return 0;
    if (arg1 == null) return -1;
    if (arg2 == null) return 1;
    return arg1.compareTo(arg2);
  }

  public static int compareTo(java.util.Date arg1, java.util.Date arg2)
  {
    if (arg1 == arg2) return 0;
    if (arg1 == null) return -1;
    if (arg2 == null) return 1;
    return arg1.compareTo(arg2);
  }

  public static int compareTo(java.sql.Date arg1, java.sql.Date arg2)
  {
    if (arg1 == arg2) return 0;
    if (arg1 == null) return -1;
    if (arg2 == null) return 1;
    return arg1.compareTo(arg2);
  }

  public static int compareTo(Timestamp arg1, Timestamp arg2)
  {
    if (arg1 == arg2) return 0;
    if (arg1 == null) return -1;
    if (arg2 == null) return 1;
    return arg1.compareTo(arg2);
  }
}
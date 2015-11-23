package nirvana.hall.api.internal.blob.coder;

import java.io.Serializable;
import java.sql.Date;

public class TPFpxDataObject
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public long id;
  public TextEntityObject text;
  public Date fpxDateTime;
  public int premium;

  public static final class FieldID
  {
    public static final int FID_SID = 1;
    public static final int FID_TEXT = 2;
    public static final int FID_DATETIME = 3;
    public static final int FID_PREMIUM = 4;
  }
}
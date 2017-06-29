package nirvana.hall.v70.internal.blob.coder;

import java.io.Serializable;

public class TPExtraInfoObject
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public long id;
  public String misConnectId;
  public String mobid;
  public Long rcn;
  public TextEntityObject text;

  public static final class FieldID
  {
    public static final int FID_SID = 1;
    public static final int FID_RCN = 2;
    public static final int FID_CID = 3;
    public static final int FID_MID = 4;
    public static final int FID_TEXT = 5;
  }
}
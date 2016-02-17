package nirvana.hall.v70.internal.blob.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlobItemEx extends BlobItem
{
  public short dbid;
  public byte bty;
  public byte lobFmtId;
  public byte srcIndex;
  public byte lobType;
  public boolean isLatent;

  public static Map<Byte, List<BlobItemEx>> splitByBty(List<BlobItemEx> lobList)
  {
    Map btyLob = new HashMap();

    for (BlobItemEx bi : lobList)
    {
      List tempBiList = (List)btyLob.get(Byte.valueOf(bi.bty));
      if (tempBiList == null)
      {
        tempBiList = new ArrayList();
        btyLob.put(Byte.valueOf(bi.bty), tempBiList);
      }
      tempBiList.add(bi);
    }
    return btyLob;
  }
}
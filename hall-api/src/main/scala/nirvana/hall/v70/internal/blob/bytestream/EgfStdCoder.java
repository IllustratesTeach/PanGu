package nirvana.hall.v70.internal.blob.bytestream;

import java.util.HashMap;
import java.util.Map;

public class EgfStdCoder
{
  public Map<Integer, IEgfStdCoder> coderMap = new HashMap<Integer, IEgfStdCoder>();

  public IEgfStdCoder getCoder(int n)
  {
    return (IEgfStdCoder)this.coderMap.get(Integer.valueOf(n));
  }

  public void addCoder(int n, IEgfStdCoder coder)
  {
    this.coderMap.put(Integer.valueOf(n), coder);
  }
}
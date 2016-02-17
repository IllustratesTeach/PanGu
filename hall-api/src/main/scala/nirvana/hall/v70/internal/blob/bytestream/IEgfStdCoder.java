package nirvana.hall.v70.internal.blob.bytestream;

public abstract interface IEgfStdCoder
{
  public abstract int decode(IEgfStdStream paramIEgfStdStream, EgfStdField paramEgfStdField);

  public abstract int encode(IEgfStdStream paramIEgfStdStream, EgfStdField paramEgfStdField);
}
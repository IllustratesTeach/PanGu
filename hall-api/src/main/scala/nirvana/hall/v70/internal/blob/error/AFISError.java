package nirvana.hall.v70.internal.blob.error;

public class AFISError extends RuntimeException
{
  private static final long serialVersionUID = 2694532845419228187L;
  private int errorNo;

  public AFISError()
  {
    this(101);
  }

  public AFISError(Exception e)
  {
    super(e);
  }

  public AFISError(int errorNo)
  {
    super(AFISErrorCode.toName(errorNo));
    this.errorNo = errorNo;
  }

  public AFISError(int errorNo, String message)
  {
    super(message);
    this.errorNo = errorNo;
  }

  public AFISError(String message)
  {
    super(message);
    this.errorNo = 0;
  }

  public AFISError(int errorNo, String message, Throwable cause)
  {
    super(message, cause);
    this.errorNo = errorNo;
  }

  public AFISError(int errorNo, Throwable cause)
  {
    super(AFISErrorCode.toName(errorNo), cause);
    this.errorNo = errorNo;
  }

  public AFISError(String message, Throwable cause)
  {
    this(0, message, cause);
  }

  public void setErrorNo(int errorNo) {
    this.errorNo = errorNo;
  }

  public int getErrorNo() {
    return this.errorNo;
  }
}
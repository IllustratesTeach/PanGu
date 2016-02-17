package nirvana.hall.v70.internal.blob.bytestream;

import java.sql.Date;

import nirvana.hall.v70.internal.blob.util.DataConv;

public class AFISLongTime
{
  public long dateTime;

  public AFISLongTime(long dateTime)
  {
    this.dateTime = dateTime;
  }

  public AFISLongTime(Date date)
  {
    this.dateTime = date.getTime();
  }

  public AFISLongTime(byte[] data, int offset)
  {
    this.dateTime = DataConv.char8ToInt8(data, offset);
  }

  public AFISLongTime(byte[] data)
  {
    this(data, 0);
  }

  public long getDateTime() {
    return this.dateTime;
  }

  public void setDateTime(long dateTime) {
    this.dateTime = dateTime;
  }

  public byte[] toBinary(byte[] data, int offset)
  {
    DataConv.int8ToChar8(this.dateTime, data, offset);
    return data;
  }

  public byte[] toBinary(byte[] data)
  {
    return toBinary(data, 0);
  }

  public byte[] toBinary()
  {
    return toBinary(new byte[8], 0);
  }
}
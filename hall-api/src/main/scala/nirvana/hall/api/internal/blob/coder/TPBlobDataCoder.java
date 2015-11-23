package nirvana.hall.api.internal.blob.coder;

import nirvana.hall.api.internal.blob.bytestream.EgfStdCoder;
import nirvana.hall.api.internal.blob.bytestream.EgfStdField;
import nirvana.hall.api.internal.blob.bytestream.IEgfStdCoder;
import nirvana.hall.api.internal.blob.bytestream.IEgfStdStream;
import nirvana.hall.api.internal.blob.entity.TPBlobData;
import nirvana.hall.api.internal.blob.entity.TPBlobUniqId;

public class TPBlobDataCoder implements IEgfStdCoder
{
  public int decode(IEgfStdStream strm, EgfStdField field)
  {
    TPBlobData val = new TPBlobData();

    EgfStdField uf = new EgfStdField();
    EgfStdCoder coder = new EgfStdCoder();
    coder.addCoder(1, new UserModInfoCoder());
    coder.addCoder(2, new TPBlobUniqIdCoder());
    do
    {
      strm.readNextField(uf, coder);
      if (uf.fieldType == 53) break;
      UserModInfo ui;
      switch (uf.fieldId)
      {
      case 1:
        val.setId(uf.getLong().longValue());
        break;
      case 2:
        val.setRcn(uf.getLong());
        break;
      case 3:
        val.setBlobUniqId((TPBlobUniqId)uf.value);
        break;
      case 4:
        ui = (UserModInfo)uf.value;
        val.setCreateUser(ui.userName);
        val.setCreaterUnitCode(ui.unitCode);
        val.setCreateTime(ui.dateTime);
        break;
      case 5:
        ui = (UserModInfo)uf.value;
        val.setUpdateUser(ui.userName);
        val.setUpdaterUnitCode(ui.unitCode);
        val.setUpdateTime(ui.dateTime);
        break;
      case 13:
        val.setSignature(uf.getBinary());
        break;
      case 14:
        val.setSigalg(uf.getByte());
        break;
      case 15:
        val.setData(uf.getBinary());
        break;
      case 16:
        val.setLobFormat(uf.getByte());
        break;
      case 17:
        val.setDbid(uf.getShort().shortValue());
        break;
      case 20:
        val.setExData1(uf.getBinary());
        break;
      case 22:
        val.setExData1Fmt(uf.getByte());
        break;
      case 24:
        val.setExData1Hash(uf.getBinary());
        break;
      case 21:
        val.setExData2(uf.getBinary());
        break;
      case 23:
        val.setExData2Fmt(uf.getByte());
        break;
      case 25:
        val.setExData2Hash(uf.getBinary());
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 18:
      case 19: }  } while (uf.fieldType != 
      53);
    field.value = val;
    return 1;
  }

  public int encode(IEgfStdStream strm, EgfStdField field)
  {
    TPBlobData val = (TPBlobData)field.value;

    strm.writeLongField(1, val.getId());

    if (val.getRcn() != null) strm.writeLongField(2, val.getRcn().longValue());
    strm.writeUDF(3, 2, val.getBlobUniqId(), new TPBlobUniqIdCoder());

    UserModInfo ui = new UserModInfo();
    UserModInfoCoder uiCoder = new UserModInfoCoder();

    ui.userName = val.getCreateUser();
    ui.unitCode = val.getCreaterUnitCode();
    ui.dateTime = val.getCreateTime();
    if (!ui.isAllNull())
    {
      strm.writeUDF(4, 1, ui, uiCoder);
    }

    ui.userName = val.getUpdateUser();
    ui.unitCode = val.getUpdaterUnitCode();
    ui.dateTime = val.getUpdateTime();

    if (!ui.isAllNull())
    {
      strm.writeUDF(5, 1, ui, uiCoder);
    }

    if (val.getSignature() != null) strm.writeBinaryField(13, val.getSignature());
    if (val.getSigalg() != null) strm.writeByteField(14, val.getSigalg().byteValue());
    if (val.getData() != null) strm.writeBinaryField(15, val.getData());
    if (val.getLobFormat() != null) strm.writeByteField(16, val.getLobFormat().byteValue());

    strm.writeField(17, val.getDbid());
    strm.writeField(22, val.getExData1Fmt());
    strm.writeField(23, val.getExData2Fmt());
    if (val.getExData1() != null) strm.writeField(20, val.getExData1());
    if (val.getExData2() != null) strm.writeField(21, val.getExData2());
    if (val.getExData1Hash() != null) strm.writeField(24, val.getExData1Hash());
    if (val.getExData2Hash() != null) strm.writeField(25, val.getExData2Hash());

    return 1;
  }

  public static class FieldType
  {
    public static final int UDT_USERINFO = 1;
    public static final int UDT_UNIQID = 2;
  }
}
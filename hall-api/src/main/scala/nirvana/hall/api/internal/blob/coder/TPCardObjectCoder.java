package nirvana.hall.api.internal.blob.coder;

import java.util.List;

import nirvana.hall.api.internal.blob.bytestream.EgfStdCoder;
import nirvana.hall.api.internal.blob.bytestream.EgfStdField;
import nirvana.hall.api.internal.blob.bytestream.IEgfStdCoder;
import nirvana.hall.api.internal.blob.bytestream.IEgfStdStream;
import nirvana.hall.api.internal.blob.entity.TPCardAdmin;
import nirvana.hall.api.internal.blob.entity.TextObject;

public class TPCardObjectCoder implements IEgfStdCoder
{
  public int decode(IEgfStdStream strm, EgfStdField field)
  {
    TPCardObject val = new TPCardObject();

    EgfStdCoder coder = new EgfStdCoder();
    coder.addCoder(101, new TPCardAdminCoder());
    coder.addCoder(104, new TPBlobDataCoder());
    coder.addCoder(102, new TPCardTextObjectCoder());

    EgfStdField uf = new EgfStdField();
    do
    {
      strm.readNextField(uf, coder);
      if (uf.fieldType == 53) break;
      switch (uf.fieldId)
      {
      case 1:
        val.setAdmin((TPCardAdmin)uf.value);
        break;
      case 4:
        val.setBlobList((List)uf.value);
        break;
      case 2:
        val.setText((TextObject)uf.value);
      case 3:
      }
    }
    while (uf.fieldType != 
      53);

    field.value = val;
    return 1;
  }

  public int encode(IEgfStdStream strm, EgfStdField field)
  {
    TPCardObject val = (TPCardObject)field.value;

    if (val.getAdmin() != null)
    {
      strm.writeUDF(1, 
        101, 
        val.getAdmin(), 
        new TPCardAdminCoder());
    }
    if (val.getBlobList() != null)
    {
      strm.writeUDList(4, 
        104, 
        val.getBlobList(), 
        new TPBlobDataCoder());
    }
    if (val.getText() != null)
    {
      strm.writeUDF(2, 
        102, 
        val.getText(), 
        new TPCardTextObjectCoder());
    }
    return 0;
  }

  public static class FieldType
  {
    public static final int UDT_TP_ADMIN = 101;
    public static final int UDT_TP_TEXT = 102;
    public static final int UDT_TP_BLOB = 104;
  }
}
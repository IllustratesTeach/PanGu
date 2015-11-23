package nirvana.hall.api.internal.blob.coder;

import nirvana.hall.api.internal.blob.bytestream.EgfStdCoder;
import nirvana.hall.api.internal.blob.bytestream.EgfStdField;
import nirvana.hall.api.internal.blob.bytestream.IEgfStdCoder;
import nirvana.hall.api.internal.blob.bytestream.IEgfStdStream;

public class TPCardTextCoder implements IEgfStdCoder {
	public int decode(IEgfStdStream strm, EgfStdField field) {
		EgfStdField uf = new EgfStdField();

		EgfStdCoder coder = new EgfStdCoder();
		coder.addCoder(1, new TextEntityObjectCoder());

		TPCardTextObject val = new TPCardTextObject();
		do {
			strm.readNextField(uf, coder);
			if (uf.fieldType == 53)
				break;
			switch (uf.fieldId) {
			case 1:
				val.id = uf.getLong().longValue();
				break;
			case 3:
				val.dbId = uf.getShort();
				break;
			case 4:
				val.updateTime = uf.getTimestamp();
				break;
			case 5:
				val.updateUser = uf.getString();
				break;
			case 2:
				val.text = ((TextEntityObject) uf.value);
			}
		}

		while (uf.fieldType != 53);

		field.value = val;
		return 1;
	}

	public int encode(IEgfStdStream strm, EgfStdField field) {
		TPCardTextObject val = (TPCardTextObject) field.value;
		strm.writeLongField(1, val.id);
		if (val.dbId != null)
			strm.writeShortField(3, val.dbId.shortValue());
		if (val.updateTime != null)
			strm.writeLongTimeField(4, val.updateTime);
		if (val.updateUser != null)
			strm.writeStringField(5, val.updateUser);
		if (val.text != null) {
			strm.writeUDF(2, 1, val.text, new TextEntityObjectCoder());
		}
		return 0;
	}

	public static class FieldType {
		public static final int UDT_TEXTINFO = 1;
	}
}
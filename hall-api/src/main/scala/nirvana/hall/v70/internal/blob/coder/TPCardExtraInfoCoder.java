package nirvana.hall.v70.internal.blob.coder;

import nirvana.hall.v70.internal.blob.bytestream.EgfStdCoder;
import nirvana.hall.v70.internal.blob.bytestream.EgfStdField;
import nirvana.hall.v70.internal.blob.bytestream.IEgfStdCoder;
import nirvana.hall.v70.internal.blob.bytestream.IEgfStdStream;

public class TPCardExtraInfoCoder implements IEgfStdCoder {
	public int decode(IEgfStdStream strm, EgfStdField field) {
		EgfStdField uf = new EgfStdField();

		EgfStdCoder coder = new EgfStdCoder();
		coder.addCoder(52, new TextEntityObjectCoder());

		TPExtraInfoObject val = new TPExtraInfoObject();
		do {
			strm.readNextField(uf, coder);
			if (uf.fieldType == 53)
				break;
			switch (uf.fieldId) {
			case 1:
				val.id = uf.getLong().longValue();
				break;
			case 3:
				val.misConnectId = uf.getString();
				break;
			case 4:
				val.mobid = uf.getString();
				break;
			case 2:
				val.rcn = uf.getLong();
				break;
			case 5:
				val.text = ((TextEntityObject) uf.value);
			}
		}

		while (uf.fieldType != 53);

		field.value = val;
		return 1;
	}

	public int encode(IEgfStdStream strm, EgfStdField field) {
		TPExtraInfoObject val = (TPExtraInfoObject) field.value;

		strm.writeLongField(1, val.id);
		if (val.rcn != null)
			strm.writeLongField(2, val.rcn.longValue());
		if (val.misConnectId != null)
			strm.writeStringField(3, val.misConnectId);
		if (val.mobid != null)
			strm.writeStringField(4, val.mobid);
		if (val.text != null) {
			strm.writeUDF(5, 52, val.text, new TextEntityObjectCoder());
		}
		return 0;
	}
}
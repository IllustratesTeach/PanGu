package nirvana.hall.api.internal.blob.coder;

import nirvana.hall.api.internal.blob.bytestream.EgfStdCoder;
import nirvana.hall.api.internal.blob.bytestream.EgfStdField;
import nirvana.hall.api.internal.blob.bytestream.IEgfStdCoder;
import nirvana.hall.api.internal.blob.bytestream.IEgfStdStream;

public class TPFpxDataCoder implements IEgfStdCoder {
	public int decode(IEgfStdStream strm, EgfStdField field) {
		EgfStdField uf = new EgfStdField();

		EgfStdCoder coder = new EgfStdCoder();
		coder.addCoder(52, new TextEntityObjectCoder());

		TPFpxDataObject val = new TPFpxDataObject();
		do {
			strm.readNextField(uf, coder);
			if (uf.fieldType == 53)
				break;
			switch (uf.fieldId) {
			case 1:
				val.id = uf.getLong().longValue();
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
		TPFpxDataObject val = (TPFpxDataObject) field.value;

		strm.writeLongField(1, val.id);
		strm.writeUDF(2, 52, val.text, new TextEntityObjectCoder());
		return 1;
	}
}
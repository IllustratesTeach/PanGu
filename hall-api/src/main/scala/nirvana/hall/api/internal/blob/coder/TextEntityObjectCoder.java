package nirvana.hall.api.internal.blob.coder;

import java.util.Map;

import nirvana.hall.api.internal.blob.bytestream.EgfStdCoder;
import nirvana.hall.api.internal.blob.bytestream.EgfStdField;
import nirvana.hall.api.internal.blob.bytestream.IEgfStdCoder;
import nirvana.hall.api.internal.blob.bytestream.IEgfStdStream;
import nirvana.hall.api.internal.blob.util.AFISHelper;

public class TextEntityObjectCoder implements IEgfStdCoder {
	public int decode(IEgfStdStream strm, EgfStdField field) {
		EgfStdField uf = new EgfStdField();

		EgfStdCoder coder = new EgfStdCoder();
		MapCoder mapCoder = new MapCoder();
		coder.addCoder(1, mapCoder);
		coder.addCoder(2, mapCoder);

		TextEntityObject val = new TextEntityObject();
		do {
			strm.readNextField(uf, coder);
			if (uf.fieldType == 53) {
				break;
			}
			switch (uf.fieldId) {
			case 1:
				val.itemList = ((Map) uf.value);
				break;
			case 2:
				val.strItemList = ((Map) uf.value);
			}
		}

		while (uf.fieldType != 53);

		field.value = val;
		return 1;
	}

	public int encode(IEgfStdStream strm, EgfStdField field) {
		TextEntityObject val = (TextEntityObject) field.value;

		MapCoder mapCoder = new MapCoder();

		if (!AFISHelper.isEmpty(val.itemList)) {
			strm.writeUDF(1, 1, val.itemList, mapCoder);
		}

		if (!AFISHelper.isEmpty(val.strItemList)) {
			strm.writeUDF(2, 2, val.strItemList, mapCoder);
		}

		return 0;
	}

	public static class FieldType {
		public static final int UDT_INTITEMLIST = 1;
		public static final int UDT_STRITEMLIST = 2;
	}
}
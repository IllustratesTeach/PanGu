package nirvana.hall.api.internal.blob.coder;

import nirvana.hall.api.internal.blob.bytestream.EgfStdField;
import nirvana.hall.api.internal.blob.bytestream.IEgfStdCoder;
import nirvana.hall.api.internal.blob.bytestream.IEgfStdStream;



public class AFISPairCoder implements IEgfStdCoder {
	public int decode(IEgfStdStream strm, EgfStdField field) {
		AFISPair pair = new AFISPair();
		EgfStdField uf = new EgfStdField();
		do {
			strm.readNextField(uf, null);

			if (uf.fieldType == 53)
				break;
			if (uf.fieldType == 24)
				uf.value = uf.getTimestamp();
			switch (uf.fieldId) {
			case 1:
				pair.setFirst(uf.value);
				break;
			case 2:
				pair.setSecond(uf.value);
			}
		} while (uf.fieldType != 53);

		field.value = pair;
		return 1;
	}

	public int encode(IEgfStdStream strm, EgfStdField field) {
		AFISPair pair = (AFISPair) field.value;
		if (pair.getFirst() != null)
			strm.writeField(1, pair.getFirst());
		if (pair.getSecond() != null)
			strm.writeField(2, pair.getSecond());
		return 1;
	}
}
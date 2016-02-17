package nirvana.hall.v70.internal.blob.coder;

import java.util.HashMap;
import java.util.Map;

import nirvana.hall.v70.internal.blob.bytestream.EgfStdCoder;
import nirvana.hall.v70.internal.blob.bytestream.EgfStdField;
import nirvana.hall.v70.internal.blob.bytestream.IEgfStdCoder;
import nirvana.hall.v70.internal.blob.bytestream.IEgfStdStream;

public class MapCoder implements IEgfStdCoder {
	public int decode(IEgfStdStream strm, EgfStdField field) {
		Map map = new HashMap();

		EgfStdCoder coder = new EgfStdCoder();
		coder.addCoder(1, new AFISPairCoder());

		EgfStdField uf = new EgfStdField();
		do {
			strm.readNextField(uf, coder);

			if (uf.fieldType == 53) {
				break;
			}
			AFISPair pair = (AFISPair) uf.value;
			map.put(pair.getFirst(), pair.getSecond());
		} while (uf.fieldType != 53);

		field.value = map;
		return 1;
	}

	public int encode(IEgfStdStream strm, EgfStdField field) {
		Map<Object, Object> map = (Map<Object, Object>) field.value;

		if ((map == null) || (map.isEmpty())) {
			return 0;
		}
		AFISPairCoder coder = new AFISPairCoder();
		AFISPair pair = new AFISPair();
		int fieldId = 1;
		for (Map.Entry<Object, Object> entry : map.entrySet()) {
			pair.setFirst(entry.getKey());
			pair.setSecond(entry.getValue());
			strm.writeUDF(fieldId++, 1, pair, coder);
		}

		return 1;
	}

	public static final class FieldType {
		public static final int UDT_AFISPAIR = 1;
	}
}
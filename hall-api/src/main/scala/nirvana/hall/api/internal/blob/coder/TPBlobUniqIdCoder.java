package nirvana.hall.api.internal.blob.coder;

import nirvana.hall.api.internal.blob.bytestream.EgfStdField;
import nirvana.hall.api.internal.blob.bytestream.IEgfStdCoder;
import nirvana.hall.api.internal.blob.bytestream.IEgfStdStream;
import nirvana.hall.api.internal.blob.entity.TPBlobUniqId;

public class TPBlobUniqIdCoder implements IEgfStdCoder {
	public int decode(IEgfStdStream strm, EgfStdField field) {
		TPBlobUniqId id = new TPBlobUniqId();

		EgfStdField uf = new EgfStdField();
		do {
			strm.readNextField(uf, null);
			if (uf.fieldType == 53)
				break;
			switch (uf.fieldId) {
			case 2:
				id.setBty(uf.getTiny());
				break;
			case 1:
				id.setTpCardId(uf.getLong().longValue());
				break;
			case 3:
				id.setFgp(uf.getShort().shortValue());
				break;
			case 5:
				id.setGroupId(uf.getByte().byteValue());
				break;
			case 6:
				id.setLobType(uf.getByte().byteValue());
				break;
			case 4:
				id.setViewId(uf.getByte().byteValue());
			}

		}

		while (uf.fieldType != 53);

		field.value = id;

		return 1;
	}

	public int encode(IEgfStdStream strm, EgfStdField field) {
		TPBlobUniqId id = (TPBlobUniqId) field.value;

		strm.writeLongField(1, id.getTpCardId().longValue());
		strm.writeTinyField(2, id.getBty().byteValue());
		strm.writeShortField(3, id.getFgp().shortValue());
		strm.writeTinyField(6, id.getLobType().byteValue());
		strm.writeTinyField(5, id.getGroupId().byteValue());
		strm.writeTinyField(4, id.getViewId().byteValue());
		return 1;
	}
}
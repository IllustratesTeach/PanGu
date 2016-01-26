package nirvana.hall.v70.internal.blob.coder;

import nirvana.hall.v70.internal.blob.bytestream.EgfStdField;
import nirvana.hall.v70.internal.blob.bytestream.IEgfStdCoder;
import nirvana.hall.v70.internal.blob.bytestream.IEgfStdStream;

public class UserModInfoCoder implements IEgfStdCoder {
	public int encode(IEgfStdStream stm, EgfStdField field) {
		UserModInfo ui = (UserModInfo) field.value;

		if (ui.userName != null)
			stm.writeStringField(1, ui.userName);
		if (ui.unitCode != null)
			stm.writeStringField(2, ui.unitCode);
		if (ui.dateTime != null)
			stm.writeLongTimeField(3, ui.dateTime);
		return 1;
	}

	public int decode(IEgfStdStream stm, EgfStdField field) {
		UserModInfo ui = new UserModInfo();
		EgfStdField uf;
		do {
			uf = new EgfStdField();

			stm.readNextField(uf, null);
			if (uf.fieldType == 53)
				break;
			switch (uf.fieldId) {
			case 1:
				ui.userName = uf.getString();
				break;
			case 2:
				ui.unitCode = uf.getString();
				break;
			case 3:
				ui.dateTime = uf.getTimestamp();
			}
		}

		while (uf.fieldType != 53);

		field.value = ui;
		return 1;
	}
}
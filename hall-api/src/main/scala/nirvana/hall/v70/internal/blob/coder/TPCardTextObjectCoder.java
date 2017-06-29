package nirvana.hall.v70.internal.blob.coder;

import java.util.Iterator;

import nirvana.hall.v70.internal.blob.bytestream.EgfStdCoder;
import nirvana.hall.v70.internal.blob.bytestream.EgfStdField;
import nirvana.hall.v70.internal.blob.bytestream.IEgfStdCoder;
import nirvana.hall.v70.internal.blob.bytestream.IEgfStdStream;
import nirvana.hall.v70.internal.blob.entity.CNTPCardText;
import nirvana.hall.v70.internal.blob.entity.TPCardExtraInfo;
import nirvana.hall.v70.internal.blob.entity.TPFPXData;
import nirvana.hall.v70.internal.blob.entity.TextObject;

public class TPCardTextObjectCoder implements IEgfStdCoder {
	public int decode(IEgfStdStream strm, EgfStdField field) {
		EgfStdField uf = new EgfStdField();

		EgfStdCoder coder = new EgfStdCoder();
		coder.addCoder(1, new TPCardTextCoder());
		coder.addCoder(2, new TPFpxDataCoder());
		coder.addCoder(3, new TPCardExtraInfoCoder());

		TextObject val = new TextObject();
		do {
			strm.readNextField(uf, coder);
			if (uf.fieldType == 53)
				break;
			val.getEntityList().add(uf.value);
		} while (uf.fieldType != 53);

		field.value = val;
		return 1;
	}

	public int encode(IEgfStdStream strm, EgfStdField field) {
		TextObject txtObj = (TextObject) field.value;

		for (Iterator localIterator = txtObj.getEntityList().iterator(); localIterator
				.hasNext();) {
			Object obj = localIterator.next();

			if (((obj instanceof CNTPCardText))
					|| ((obj instanceof TPCardTextObject))) {
				TPCardTextObject txt;
				if ((obj instanceof CNTPCardText)) {
					txt = CNTPCardTextCnv.toObject((CNTPCardText) obj);
				} else {
					txt = (TPCardTextObject) obj;
				}
				strm.writeUDF(1, 1, txt, new TPCardTextCoder());
			} else if (((obj instanceof TPFPXData))
					|| ((obj instanceof TPFpxDataObject))) {
				TPFpxDataObject fpx;
				if ((obj instanceof TPFPXData)) {
					fpx = TPFpxDataCnv.toObject((TPFPXData) obj);
				} else {
					fpx = (TPFpxDataObject) obj;
				}
				strm.writeUDF(2, 2, fpx, new TPFpxDataCoder());
			} else {
				if ((!(obj instanceof TPCardExtraInfo))
						&& (!(obj instanceof TPExtraInfoObject)))
					continue;
				TPExtraInfoObject extra;
				if ((obj instanceof TPCardExtraInfo)) {
					extra = TPExtraInfoCnv.toObject((TPCardExtraInfo) obj);
				} else {
					extra = (TPExtraInfoObject) obj;
				}
				strm.writeUDF(3, 3, extra, new TPCardExtraInfoCoder());
			}
		}

		return 1;
	}

	public static final class FieldID {
		public static final int FID_CNTEXT = 1;
		public static final int FID_FPXDATA = 2;
		public static final int FID_EXTRAINFO = 3;
	}

	public static final class FieldType {
		public static final int UDT_TP_CNTEXTINFO = 1;
		public static final int UDT_TP_FPX = 2;
		public static final int UDT_TP_EXTRA = 3;
	}
}
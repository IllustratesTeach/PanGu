package nirvana.hall.api.internal.blob.coder;

import nirvana.hall.api.internal.blob.bytestream.EgfStdCoder;
import nirvana.hall.api.internal.blob.bytestream.EgfStdField;
import nirvana.hall.api.internal.blob.bytestream.IEgfStdCoder;
import nirvana.hall.api.internal.blob.bytestream.IEgfStdStream;
import nirvana.hall.api.internal.blob.entity.TPCardAdmin;

public class TPCardAdminCoder implements IEgfStdCoder {
	public int decode(IEgfStdStream stm, EgfStdField field) {
		TPCardAdmin tp = new TPCardAdmin();

		EgfStdCoder coder = new EgfStdCoder();

		coder.addCoder(1, new UserModInfoCoder());
		EgfStdField uf = new EgfStdField();
		do {
			stm.readNextField(uf, coder);
			if (uf.fieldType == 53)
				break;
			UserModInfo ui;
			switch (uf.fieldId) {
			case 11:
				tp.setCardNum(uf.getString());
				break;
			case 4:
				tp.setDbid(uf.getShort().shortValue());
				break;
			case 5:
				tp.setTid(uf.getShort().shortValue());
				break;
			case 20:
				ui = (UserModInfo) uf.value;
				tp.setCreateUser(ui.userName);
				tp.setCreateUnitCode(ui.unitCode);
				tp.setCreateTime(ui.dateTime);
				break;
			case 21:
				ui = (UserModInfo) uf.value;
				tp.setUpdateUser(ui.userName);
				tp.setUpdateUnitCode(ui.unitCode);
				tp.setUpdateTime(ui.dateTime);
				break;
			case 22:
				ui = (UserModInfo) uf.value;
				tp.setOrgScanner(ui.userName);
				tp.setOrgScannerUnitCode(ui.unitCode);
				tp.setOrgScanTime(ui.dateTime);
				break;
			case 23:
				tp.setUuid(uf.getString());
				break;
			case 12:
				tp.setPersonNo(uf.getString());
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 13:
			case 14:
			case 15:
			case 16:
			case 17:
			case 18:
			case 19:
			}
		} while (uf.fieldType != 53);

		field.value = tp;
		return 1;
	}

	public int encode(IEgfStdStream stm, EgfStdField field) {
		TPCardAdmin tp = (TPCardAdmin) field.value;

		stm.writeLongField(3, tp.getId());
		if (tp.getRcn() != null)
			stm.writeLongField(6, tp.getRcn().longValue());
		stm.writeShortField(4, tp.getDbid());
		stm.writeShortField(5, tp.getTid());
		if (tp.getCardNum() != null)
			stm.writeStringField(11, tp.getCardNum());
		if (tp.getOrgAFISVendorId() != null)
			stm.writeShortField(10, tp.getOrgAFISVendorId().shortValue());

		UserModInfoCoder encoder = new UserModInfoCoder();

		UserModInfo ui = new UserModInfo();
		ui.userName = tp.getCreateUser();
		ui.unitCode = tp.getCreateUnitCode();
		ui.dateTime = tp.getCreateTime();

		if (!ui.isAllNull()) {
			stm.writeUDF(20, 1, ui, encoder);
		}

		ui.userName = tp.getUpdateUser();
		ui.unitCode = tp.getUpdateUnitCode();
		ui.dateTime = tp.getUpdateTime();

		if (!ui.isAllNull()) {
			stm.writeUDF(21, 1, ui, encoder);
		}

		ui.userName = tp.getOrgScanner();
		ui.unitCode = tp.getOrgScannerUnitCode();
		ui.dateTime = tp.getOrgScanTime();

		if (!ui.isAllNull()) {
			stm.writeUDF(22, 1, ui, encoder);
		}

		stm.writeField(23, tp.getUuid());
		stm.writeField(12, tp.getPersonNo());

		return 1;
	}

	public static class FieldType {
		public static final int UDT_USERINFO = 1;
	}
}
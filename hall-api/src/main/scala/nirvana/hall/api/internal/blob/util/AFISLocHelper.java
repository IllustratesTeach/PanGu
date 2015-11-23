package nirvana.hall.api.internal.blob.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import nirvana.hall.api.internal.blob.entity.LPBlobData;
import nirvana.hall.api.internal.blob.entity.LPBlobUniqId;
import nirvana.hall.api.internal.blob.entity.TPBlobData;
import nirvana.hall.api.internal.blob.entity.TPBlobUniqId;

public class AFISLocHelper {
	public static Object clone(Serializable src) throws IOException,
			ClassNotFoundException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(1048576);
		ByteArrayInputStream bis = null;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		try {
			oos = new ObjectOutputStream(bos);
			oos.writeObject(src);
			oos.close();
			oos = null;

			bis = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bis);
			Object localObject2 = ois.readObject();
			return localObject2;
		} finally {
			if (oos != null)
				oos.close();
			if (ois != null)
				ois.close();
			if (bis != null)
				bis.close();
		}
	}

	public static TPBlobData createTPBlobDataWithoutBLobHead(byte[] data,
			int bty, int fgp, int viewId, int groupId, int lobType,
			int lobFormat) {
		if (AFISHelper.isEmpty(data))
			return null;

		TPBlobData blob = new TPBlobData();

		TPBlobUniqId uid = new TPBlobUniqId();
		uid.setBty(Byte.valueOf((byte) bty));
		uid.setFgp((short) fgp);
		uid.setGroupId((byte) groupId);
		uid.setViewId((byte) viewId);
		uid.setLobType((byte) lobType);

		blob.setBlobUniqId(uid);
		blob.setLobFormat(Byte.valueOf((byte) lobFormat));
		blob.setData(data);
		blob.setSigalg((byte) 1);
		blob.setSignature(MsgDigest.md5(data));

		return blob;
	}

	public static TPBlobData createTPBlobUseJpeg(byte[] data, int bty, int fgp,
			int viewId) {
		return createTPBlobDataWithoutBLobHead(data, bty, fgp, viewId, 1, 1, 10);
	}

	public static TPBlobData createTPBlobUseJpeg(byte[] data, int bty, int fgp) {
		return createTPBlobUseJpeg(data, bty, fgp, 0);
	}

	public static TPBlobData createTPBlobIDPhoto(byte[] data) {
		return createTPBlobUseJpeg(data, 4, 1, 1);
	}

	public static TPBlobData createTPBlobSignature(byte[] data) {
		return createTPBlobUseJpeg(data, 9, 1);
	}

	public static TPBlobData createTPBlobHandWriting(byte[] data) {
		return createTPBlobUseJpeg(data, 21, 1);
	}

	public static TPBlobData createTPBlobSound(byte[] data) {
		return createTPBlobDataWithoutBLobHead(data, 3, 1, 0, 0, 1, 20);
	}

	public static TPBlobData createTPBlobShoes(byte[] data, byte fgp) {
		return createTPBlobUseJpeg(data, 15, fgp);
	}

	public static LPBlobData createLPBlobDataWithoutBLobHead(byte[] data,
			short bty, Short fgp, short groupId, short lobType, short lobFormat) {
		if (AFISHelper.isEmpty(data))
			return null;

		LPBlobData blob = new LPBlobData();

		LPBlobUniqId uid = new LPBlobUniqId();
		uid.setGroupId((byte) groupId);
		uid.setLobType((byte) lobType);

		blob.setBlobUniqId(uid);
		blob.setBty(Byte.valueOf((byte) bty));
		blob.setFgp(fgp);
		blob.setLobFormat(Byte.valueOf((byte) lobFormat));
		blob.setData(data);
		blob.setSigalg((byte) 1);
		blob.setSignature(MsgDigest.md5(data));

		return blob;
	}

	public static LPBlobData createLPBlobUseJpeg(byte[] data, short bty,
			Short fgp) {
		return createLPBlobDataWithoutBLobHead(data, bty, fgp, (short) 0,
				(short) 1, (short) 10);
	}

	public static LPBlobData createLPBlobShoes(byte[] data, Short fgp) {
		return createLPBlobUseJpeg(data, (short) 15, fgp);
	}

	public static String rebuildPersonNum(String personNum) {
		return personNum != null ? personNum.replaceFirst("^[Rr]*", "R") : null;
	}

	public static String rebuildCaseNum(String caseNum) {
		return caseNum != null ? caseNum.replaceFirst("^[AaKkJj]*", "A") : null;
	}

	public static String rebuildTPCardNum(String cardNum) {
		return cardNum != null ? cardNum.replaceFirst("^[Rr]*", "") : null;
	}

	public static String rebuildLPCardNum(String cardNum) {
		return cardNum != null ? cardNum.replaceFirst("^[KkAaJj]*", "A") : null;
	}

	public static String rebuildLPCaseKNo(String caseNum) {
		return caseNum != null ? caseNum.replaceFirst("^[AaKkJj]*", "K") : null;
	}

	public static String rebuildLPCardKNo(String cardNum) {
		return cardNum != null ? cardNum.replaceFirst("^[KkAaJj]*", "K") : null;
	}
}
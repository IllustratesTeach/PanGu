package nirvana.hall.api.internal.blob.entity;

import java.io.Serializable;

public class LPBlobUniqId implements Serializable {
	private static final long serialVersionUID = 1L;

	private long cardId;

	private byte lobType;

	private byte groupId;

	public long getCardId() {
		return this.cardId;
	}

	public void setCardId(long cardId) {
		this.cardId = cardId;
	}

	public byte getLobType() {
		return this.lobType;
	}

	public void setLobType(byte lobType) {
		this.lobType = lobType;
	}

	public byte getGroupId() {
		return this.groupId;
	}

	public void setGroupId(byte groupId) {
		this.groupId = groupId;
	}

	public void setUniqId(LPBlobUniqId uid) {
		this.cardId = uid.cardId;
		this.groupId = uid.groupId;
		this.lobType = uid.lobType;
	}

	public static class FieldID {
		public static final int FID_CARDID = 1;
		public static final int FID_GROUPID = 5;
		public static final int FID_LOBTYPE = 6;
	}
}
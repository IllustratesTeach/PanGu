package nirvana.hall.v70.internal.blob.entity;

import java.io.Serializable;

public class TPBlobUniqId implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	private Long tpCardId;

	private Byte bty;

	private Short fgp;

	private Byte viewId;

	private Byte groupId;

	private Byte lobType;

	public Long getTpCardId() {
		return this.tpCardId;
	}

	public void setTpCardId(long tpCardId) {
		this.tpCardId = Long.valueOf(tpCardId);
	}

	public Short getFgp() {
		return this.fgp;
	}

	public void setFgp(short fgp) {
		this.fgp = Short.valueOf(fgp);
	}

	public Byte getViewId() {
		return this.viewId;
	}

	public void setViewId(byte viewId) {
		this.viewId = Byte.valueOf(viewId);
	}

	public Byte getGroupId() {
		return this.groupId;
	}

	public void setGroupId(byte groupId) {
		this.groupId = Byte.valueOf(groupId);
	}

	public Byte getLobType() {
		return this.lobType;
	}

	public void setLobType(byte lobType) {
		this.lobType = Byte.valueOf(lobType);
	}

	public void setBty(Byte bty) {
		this.bty = bty;
	}

	public Byte getBty() {
		return this.bty;
	}

	public boolean equals(Object arg0) {
		if ((arg0 instanceof TPBlobId)) {
			TPBlobId ti = (TPBlobId) arg0;
			if ((ti.bty == this.bty.byteValue())
					&& (ti.fgp == this.fgp.shortValue())
					&& (ti.lobType == this.lobType.byteValue())
					&& (ti.viewId == this.viewId.byteValue())
					&& (ti.groupId == this.groupId.byteValue()))
				return true;
		} else if ((arg0 instanceof TPBlobUniqId)) {
			TPBlobUniqId unid = (TPBlobUniqId) arg0;
			if ((this.bty.byteValue() == unid.bty.byteValue())
					&& (this.viewId.byteValue() == unid.viewId.byteValue())
					&& (this.groupId.byteValue() == unid.groupId.byteValue())
					&& (this.fgp.byteValue() == unid.fgp.byteValue())
					&& (this.lobType.byteValue() == unid.lobType.byteValue())) {
				return true;
			}
		}
		return false;
	}

	public Object clone() throws CloneNotSupportedException {
		TPBlobUniqId uniqid = new TPBlobUniqId();
		uniqid.bty = Byte.valueOf(this.bty.byteValue());
		uniqid.fgp = Short.valueOf(this.fgp.shortValue());
		uniqid.viewId = Byte.valueOf(this.viewId.byteValue());
		uniqid.groupId = Byte.valueOf(this.groupId.byteValue());
		uniqid.lobType = Byte.valueOf(this.lobType.byteValue());
		return uniqid;
	}

	public void setUniqId(TPBlobUniqId uid) {
		this.bty = uid.bty;
		this.fgp = uid.fgp;
		this.viewId = uid.viewId;
		this.lobType = uid.lobType;
		this.groupId = uid.groupId;
		this.tpCardId = uid.tpCardId;
	}

	public String toString() {
		return "TPBlobUniqId [bty=" + this.bty + ", fgp=" + this.fgp
				+ ", groupId=" + this.groupId + ", lobType=" + this.lobType
				+ ", cardId=" + this.tpCardId + ", viewId=" + this.viewId + "]";
	}

	public static final class FieldID {
		public static final int FID_CARDID = 1;
		public static final int FID_BTY = 2;
		public static final int FID_FGP = 3;
		public static final int FID_VIEWID = 4;
		public static final int FID_GROUPID = 5;
		public static final int FID_LOBTYPE = 6;
	}
}
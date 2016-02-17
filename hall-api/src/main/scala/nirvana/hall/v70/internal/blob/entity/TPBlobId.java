package nirvana.hall.v70.internal.blob.entity;

import java.io.Serializable;

public class TPBlobId implements Comparable<TPBlobId>, Serializable {
	private static final long serialVersionUID = 1L;
	public byte bty;
	public byte fgp;
	public byte lobType;
	public byte viewId;
	public byte groupId;

	public boolean equals(Object arg0) {
		if (!(arg0 instanceof TPBlobId))
			return false;
		TPBlobId ti = (TPBlobId) arg0;

		return (ti.bty == this.bty) && (ti.fgp == this.fgp)
				&& (ti.lobType == this.lobType) && (ti.viewId == this.viewId)
				&& (ti.groupId == this.groupId);
	}

	public int hashCode() {
		return this.bty | this.fgp << 4 | this.lobType << 9 | this.viewId << 14
				| this.groupId << 17;
	}

	public int compareTo(TPBlobId arg0) {
		if (this.bty != arg0.bty)
			return this.bty < arg0.bty ? -1 : 1;
		if (this.fgp != arg0.fgp)
			return this.fgp < arg0.fgp ? -1 : 1;
		if (this.lobType != arg0.lobType)
			return this.lobType < arg0.lobType ? -1 : 1;
		if (this.viewId != arg0.viewId)
			return this.viewId < arg0.viewId ? -1 : 1;
		if (this.groupId != arg0.groupId)
			return this.groupId < arg0.groupId ? -1 : 1;
		return 0;
	}
}
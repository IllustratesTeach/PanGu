package nirvana.hall.api.internal.blob.entity;

public class BlobItem {
	public long cardId;
	public long lobId;
	public long cardGroupId;
	public byte fgp;
	public byte viewId;
	public byte mntLevel;
	public byte groupId;
	public byte[] data;

	public boolean equals(Object arg0) {
		if (!(arg0 instanceof BlobItem))
			return false;
		BlobItem bi = (BlobItem) arg0;
		return this.cardId == bi.cardId;
	}

	public int hashCode() {
		return (int) this.lobId;
	}

	public void setBlobItem(BlobItem bi) {
		this.cardGroupId = bi.cardGroupId;
		this.cardId = bi.cardId;
		this.lobId = bi.lobId;
		this.fgp = bi.fgp;
		this.viewId = bi.viewId;
		this.mntLevel = bi.mntLevel;
		this.groupId = bi.groupId;

		this.data = bi.data;
	}
}
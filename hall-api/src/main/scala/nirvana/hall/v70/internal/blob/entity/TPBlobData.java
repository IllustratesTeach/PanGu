package nirvana.hall.v70.internal.blob.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class TPBlobData implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;

	private TPBlobUniqId blobUniqId;

	private short dbid;

	private byte[] data;

	private byte[] signature;

	private Byte sigalg;

	private String createUser;

	private Timestamp createTime;

	private String createrUnitCode;

	private String updateUser;

	private Timestamp updateTime;

	private String updaterUnitCode;

	private Long rcn;

	private Byte lobFormat;

	private Byte quality;

	private byte[] exData1;

	private byte[] exData2;

	private Byte exData1Fmt;

	private Byte exData2Fmt;

	private byte[] exData1Hash;

	private byte[] exData2Hash;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte[] getData() {
		return this.data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public byte[] getSignature() {
		return this.signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	public Byte getSigalg() {
		return this.sigalg;
	}

	public void setSigalg(Byte sigalg) {
		this.sigalg = sigalg;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreaterUnitCode() {
		return this.createrUnitCode;
	}

	public void setCreaterUnitCode(String createrUnitCode) {
		this.createrUnitCode = createrUnitCode;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdaterUnitCode() {
		return this.updaterUnitCode;
	}

	public void setUpdaterUnitCode(String updaterUnitCode) {
		this.updaterUnitCode = updaterUnitCode;
	}

	public Long getRcn() {
		return this.rcn;
	}

	public void setRcn(Long rcn) {
		this.rcn = rcn;
	}

	public TPBlobUniqId getBlobUniqId() {
		return this.blobUniqId;
	}

	public void setBlobUniqId(TPBlobUniqId blobUniqId) {
		this.blobUniqId = blobUniqId;
	}

	public void setLobFormat(Byte lobFormat) {
		this.lobFormat = lobFormat;
	}

	public Byte getLobFormat() {
		return this.lobFormat;
	}

	public BlobItemEx toBlobItemEx() {
		BlobItemEx bi = new BlobItemEx();
		bi.cardId = this.blobUniqId.getTpCardId().longValue();
		bi.lobId = this.id;
		bi.data = this.data;
		bi.bty = this.blobUniqId.getBty().byteValue();
		bi.fgp = this.blobUniqId.getFgp().byteValue();
		bi.viewId = this.blobUniqId.getViewId().byteValue();
		bi.groupId = this.blobUniqId.getGroupId().byteValue();
		bi.lobFmtId = this.lobFormat.byteValue();
		bi.lobType = this.blobUniqId.getLobType().byteValue();
		bi.srcIndex = 0;
		bi.mntLevel = -1;
		bi.cardGroupId = (-bi.cardId);

		return bi;
	}

	public void setQuality(Byte quality) {
		this.quality = quality;
	}

	public Byte getQuality() {
		return this.quality;
	}

	public void setDbid(short dbid) {
		this.dbid = dbid;
	}

	public short getDbid() {
		return this.dbid;
	}

	public void setCreateInfo(OpInfo opInfo) {
		if (opInfo == null)
			return;
		this.createUser = opInfo.curUser;
		this.createrUnitCode = opInfo.curUnitCode;
		this.createTime = opInfo.curTime;
	}

	public void setUpdateInfo(OpInfo opInfo) {
		if (opInfo == null)
			return;
		this.updateUser = opInfo.curUser;
		this.updaterUnitCode = opInfo.curUnitCode;
		this.updateTime = opInfo.curTime;
	}

	public byte[] getExData1() {
		return this.exData1;
	}

	public void setExData1(byte[] exData1) {
		this.exData1 = exData1;
	}

	public byte[] getExData2() {
		return this.exData2;
	}

	public void setExData2(byte[] exData2) {
		this.exData2 = exData2;
	}

	public Byte getExData1Fmt() {
		return this.exData1Fmt;
	}

	public void setExData1Fmt(Byte exData1Fmt) {
		this.exData1Fmt = exData1Fmt;
	}

	public Byte getExData2Fmt() {
		return this.exData2Fmt;
	}

	public void setExData2Fmt(Byte exData2Fmt) {
		this.exData2Fmt = exData2Fmt;
	}

	public byte[] getExData1Hash() {
		return this.exData1Hash;
	}

	public void setExData1Hash(byte[] exData1Hash) {
		this.exData1Hash = exData1Hash;
	}

	public byte[] getExData2Hash() {
		return this.exData2Hash;
	}

	public void setExData2Hash(byte[] exData2Hash) {
		this.exData2Hash = exData2Hash;
	}

	public String toString() {
		return "TPBlobData [" + this.blobUniqId + ", dbid=" + this.dbid
				+ ", id=" + this.id + ", lobFormat=" + this.lobFormat + "]";
	}

	public static final class FieldID {
		public static final int FID_SID = 1;
		public static final int FID_RCN = 2;
		public static final int FID_ID = 3;
		public static final int FID_CRTINFO = 4;
		public static final int FID_UPDINFO = 5;
		public static final int FID_SIGNATURE = 13;
		public static final int FID_SIGALG = 14;
		public static final int FID_LOBDATA = 15;
		public static final int FID_LOBFMT = 16;
		public static final int FID_DBID = 17;
		public static final int FID_EXDATA1 = 20;
		public static final int FID_EXDATA2 = 21;
		public static final int FID_EXDATA1FMT = 22;
		public static final int FID_EXDATA2FMT = 23;
		public static final int FID_EXDATA1HASH = 24;
		public static final int FID_EXDATA2HASH = 25;
	}
}
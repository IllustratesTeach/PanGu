package nirvana.hall.api.internal.blob.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class LPBlobData implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private LPBlobUniqId blobUniqId;

	private Short fgp;

	private byte[] data;

	private Byte bty;

	private String createUser;

	private Timestamp createTime;

	private String createrUnitCode;

	private String updateUser;

	private Timestamp updateTime;

	private String updaterUnitCode;

	private Long rcn;

	private Byte sigalg;

	private byte[] signature;

	private String comments;

	private Byte lobFormat;

	private Short dbId;

	private byte[] exData1;

	private byte[] exData2;

	private Byte exData1Fmt;

	private Byte exData2Fmt;

	private byte[] exData1Hash;

	private byte[] exData2Hash;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Short getFgp() {
		return this.fgp;
	}

	public void setFgp(Short fgp) {
		this.fgp = fgp;
	}

	public byte[] getData() {
		return this.data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Byte getBty() {
		return this.bty;
	}

	public void setBty(Byte bty) {
		this.bty = bty;
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

	public Byte getSigalg() {
		return this.sigalg;
	}

	public void setSigalg(Byte sigalg) {
		this.sigalg = sigalg;
	}

	public byte[] getSignature() {
		return this.signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public LPBlobUniqId getBlobUniqId() {
		return this.blobUniqId;
	}

	public void setBlobUniqId(LPBlobUniqId blobUniqId) {
		this.blobUniqId = blobUniqId;
	}

	public void setLobFormat(Byte lobFormat) {
		this.lobFormat = lobFormat;
	}

	public Byte getLobFormat() {
		return this.lobFormat;
	}

	public Short getDbId() {
		return this.dbId;
	}

	public void setDbId(Short dbId) {
		this.dbId = dbId;
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

	public BlobItemEx toBlobItemEx() {
		BlobItemEx bi = new BlobItemEx();
		bi.cardId = this.blobUniqId.getCardId();
		bi.lobId = this.id.longValue();
		bi.data = this.data;
		bi.bty = this.bty.byteValue();
		bi.groupId = this.blobUniqId.getGroupId();
		bi.lobFmtId = this.lobFormat.byteValue();
		bi.lobType = this.blobUniqId.getLobType();
		bi.srcIndex = 0;
		bi.mntLevel = -1;
		bi.cardGroupId = (-bi.cardId);

		bi.fgp = 0;
		bi.viewId = 0;
		return bi;
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

	public static class FieldID {
		public static final int FID_SID = 1;
		public static final int FID_RCN = 2;
		public static final int FID_UNIQID = 3;
		public static final int FID_CRTINFO = 4;
		public static final int FID_UPDINFO = 5;
		public static final int FID_BTY = 6;
		public static final int FID_FGP = 7;
		public static final int FID_COMMENTS = 8;
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
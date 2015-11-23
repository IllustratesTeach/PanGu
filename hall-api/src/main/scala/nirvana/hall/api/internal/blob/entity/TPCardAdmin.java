package nirvana.hall.api.internal.blob.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class TPCardAdmin implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;

	private short dbid;

	private short tid;

	private String cardNum;

	private String personNo;

	private Byte vpid;

	private String createUser;

	private Timestamp createTime;

	private String createUnitCode;

	private String updateUser;

	private Timestamp updateTime;

	private String updateUnitCode;

	private String orgScanner;

	private String orgScannerUnitCode;

	private Timestamp orgScanTime;

	private Short orgAFISVendorId;

	private Long rcn;

	private String objectType;

	private long tpPersonId;

	private String uuid;

	private int btyFlag;

	private byte status;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public short getDbid() {
		return this.dbid;
	}

	public void setDbid(short dbid) {
		this.dbid = dbid;
	}

	public short getTid() {
		return this.tid;
	}

	public void setTid(short tid) {
		this.tid = tid;
	}

	public String getCardNum() {
		return this.cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
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

	public String getCreateUnitCode() {
		return this.createUnitCode;
	}

	public void setCreateUnitCode(String createUnitCode) {
		this.createUnitCode = createUnitCode;
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

	public String getUpdateUnitCode() {
		return this.updateUnitCode;
	}

	public void setUpdateUnitCode(String updateUnitCode) {
		this.updateUnitCode = updateUnitCode;
	}

	public String getOrgScanner() {
		return this.orgScanner;
	}

	public void setOrgScanner(String orgScanner) {
		this.orgScanner = orgScanner;
	}

	public String getOrgScannerUnitCode() {
		return this.orgScannerUnitCode;
	}

	public void setOrgScannerUnitCode(String orgScannerUnitCode) {
		this.orgScannerUnitCode = orgScannerUnitCode;
	}

	public Timestamp getOrgScanTime() {
		return this.orgScanTime;
	}

	public void setOrgScanTime(Timestamp orgScanTime) {
		this.orgScanTime = orgScanTime;
	}

	public Short getOrgAFISVendorId() {
		return this.orgAFISVendorId;
	}

	public void setOrgAFISVendorId(short orgAFISVendorId) {
		this.orgAFISVendorId = Short.valueOf(orgAFISVendorId);
	}

	public Long getRcn() {
		return this.rcn;
	}

	public void setRcn(Long rcn) {
		this.rcn = rcn;
	}

	public Byte getVpid() {
		return this.vpid;
	}

	public void setVpid(Byte vpid) {
		this.vpid = vpid;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getObjectType() {
		return this.objectType;
	}

	public void setCreateInfo(OpInfo opInfo) {
		if (opInfo == null)
			return;
		this.createUser = opInfo.curUser;
		this.createUnitCode = opInfo.curUnitCode;
		this.createTime = opInfo.curTime;
	}

	public void setUpdateInfo(OpInfo opInfo) {
		if (opInfo == null)
			return;
		this.updateUser = opInfo.curUser;
		this.updateUnitCode = opInfo.curUnitCode;
		this.updateTime = opInfo.curTime;
	}

	public void setTPPersonId(long tpPersonId) {
		this.tpPersonId = tpPersonId;
	}

	public long getTPPersonId() {
		return this.tpPersonId;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getBtyFlag() {
		return this.btyFlag;
	}

	public void setBtyFlag(int btyFlag) {
		this.btyFlag = btyFlag;
	}

	public String getPersonNo() {
		return this.personNo;
	}

	public void setPersonNo(String personNo) {
		this.personNo = personNo;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public static final class FieldID {
		public static final int MAJOR_VER = 1;
		public static final int MINOR_VER = 2;
		public static final int SID = 3;
		public static final int DBID = 4;
		public static final int TID = 5;
		public static final int RCN = 6;
		public static final int ORGAFISVENDORID = 10;
		public static final int CARDNUM = 11;
		public static final int PERSONNO = 12;
		public static final int CREATEINFO = 20;
		public static final int UPDATEINFO = 21;
		public static final int ORGINFO = 22;
		public static final int UUID = 23;
	}
}
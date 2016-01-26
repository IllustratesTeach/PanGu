package nirvana.hall.v70.internal.blob.coder;

import java.io.Serializable;
import java.sql.Timestamp;

import nirvana.hall.v70.internal.blob.entity.OpInfo;

public class TPCardUpdateInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private long tpCardId;

	private Short dbId;

	private String updateUser;

	private Timestamp updateTime;

	private String updateUnitCode;

	public long getTpCardId() {
		return this.tpCardId;
	}

	public void setTpCardId(long tpCardId) {
		this.tpCardId = tpCardId;
	}

	public Short getDbId() {
		return this.dbId;
	}

	public void setDbId(Short dbId) {
		this.dbId = dbId;
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

	public void setUpdateInfo(OpInfo opInfo) {
		if (opInfo == null)
			return;
		this.updateUser = opInfo.curUser;
		this.updateUnitCode = opInfo.curUnitCode;
		this.updateTime = opInfo.curTime;
	}

	public static final class FieldID {
		public static final int FID_TPCARD_ID = 1;
		public static final int FID_DB_ID = 2;
		public static final int FID_UPDATE_USER = 3;
		public static final int FID_UPDATE_TIME = 4;
		public static final int FID_UPDATE_UNIT = 5;
	}
}
package nirvana.hall.api.internal.blob.entity;

import java.io.Serializable;
import java.sql.Date;

public class TPFPXData implements Serializable {
	private static final long serialVersionUID = 2L;

	private long id;

	private String fpxPriority;

	private int premium;

	private String fpxPurpose;

	private String assoPersonId;

	private String assoCaseId;

	private String fpxTimeLimit;

	private String fpxUnitCode;

	private String fpxUnitName;

	private Date fpxDateTime;

	private String linkMan;

	private String linkPhone;

	private String approvedby;

	private String fpxFlag;

	private long rcn;

	private String comments;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFpxPriority() {
		return this.fpxPriority;
	}

	public void setFpxPriority(String fpxPriority) {
		this.fpxPriority = fpxPriority;
	}

	public int getPremium() {
		return this.premium;
	}

	public void setPremium(int premium) {
		this.premium = premium;
	}

	public String getFpxPurpose() {
		return this.fpxPurpose;
	}

	public void setFpxPurpose(String fpxPurpose) {
		this.fpxPurpose = fpxPurpose;
	}

	public String getAssoPersonId() {
		return this.assoPersonId;
	}

	public void setAssoPersonId(String assoPersonId) {
		this.assoPersonId = assoPersonId;
	}

	public String getAssoCaseId() {
		return this.assoCaseId;
	}

	public void setAssoCaseId(String assoCaseId) {
		this.assoCaseId = assoCaseId;
	}

	public String getFpxTimeLimit() {
		return this.fpxTimeLimit;
	}

	public void setFpxTimeLimit(String fpxTimeLimit) {
		this.fpxTimeLimit = fpxTimeLimit;
	}

	public String getFpxUnitCode() {
		return this.fpxUnitCode;
	}

	public void setFpxUnitCode(String fpxUnitCode) {
		this.fpxUnitCode = fpxUnitCode;
	}

	public String getFpxUnitName() {
		return this.fpxUnitName;
	}

	public void setFpxUnitName(String fpxUnitName) {
		this.fpxUnitName = fpxUnitName;
	}

	public Date getFpxDateTime() {
		return this.fpxDateTime;
	}

	public void setFpxDateTime(Date fpxDateTime) {
		this.fpxDateTime = fpxDateTime;
	}

	public String getLinkMan() {
		return this.linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getLinkPhone() {
		return this.linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getApprovedby() {
		return this.approvedby;
	}

	public void setApprovedby(String approvedby) {
		this.approvedby = approvedby;
	}

	public String getFpxFlag() {
		return this.fpxFlag;
	}

	public void setFpxFlag(String fpxFlag) {
		this.fpxFlag = fpxFlag;
	}

	public long getRcn() {
		return this.rcn;
	}

	public void setRcn(long rcn) {
		this.rcn = rcn;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public static final class FieldID {
		public static final int FID_SID = 1;
		public static final int FID_TEXT = 2;
	}
}
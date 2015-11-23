package nirvana.hall.api.internal.blob.entity;

import java.io.Serializable;

public class TPCardExtraInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;

	private String misConnectId;

	private String mobid;

	private Long rcn;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMisConnectId() {
		return this.misConnectId;
	}

	public void setMisConnectId(String misConnectId) {
		this.misConnectId = misConnectId;
	}

	public String getMobid() {
		return this.mobid;
	}

	public void setMobid(String mobid) {
		this.mobid = mobid;
	}

	public void setRcn(Long rcn) {
		this.rcn = rcn;
	}

	public Long getRcn() {
		return this.rcn;
	}
}
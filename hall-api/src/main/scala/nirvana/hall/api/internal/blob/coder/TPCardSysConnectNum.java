package nirvana.hall.api.internal.blob.coder;

import java.io.Serializable;

public class TPCardSysConnectNum implements Serializable {
	private static final long serialVersionUID = 1L;

	private long tpCardId;

	private int numDefId;

	private String connectNum;

	public String getConnectNum() {
		return this.connectNum;
	}

	public void setConnectNum(String connectNum) {
		this.connectNum = connectNum;
	}

	public long getTpCardId() {
		return this.tpCardId;
	}

	public void setTpCardId(long tpCardId) {
		this.tpCardId = tpCardId;
	}

	public int getNumDefId() {
		return this.numDefId;
	}

	public void setNumDefId(int numDefId) {
		this.numDefId = numDefId;
	}

	public static final class FieldID {
		public static final int FID_TPCARD_ID = 1;
		public static final int FID_NUM_DEF_ID = 2;
		public static final int FID_CONNECT_NUM = 3;
	}
}
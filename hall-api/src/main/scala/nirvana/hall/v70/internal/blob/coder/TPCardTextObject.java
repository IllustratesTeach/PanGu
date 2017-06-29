package nirvana.hall.v70.internal.blob.coder;

import java.io.Serializable;
import java.sql.Timestamp;

public class TPCardTextObject implements Serializable {
	private static final long serialVersionUID = 1L;
	public long id;
	public Short dbId;
	public String updateUser;
	public Timestamp updateTime;
	public TextEntityObject text;

	public static final class FieldID {
		public static final int FID_SID = 1;
		public static final int FID_TEXT = 2;
		public static final int FID_DBID = 3;
		public static final int FID_UPDATETIME = 4;
		public static final int FID_UPDATEUSER = 5;
	}
}
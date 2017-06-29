package nirvana.hall.v70.internal.blob.coder;

import java.sql.Timestamp;

public class UserModInfo {
	public String userName;
	public String unitCode;
	public Timestamp dateTime;

	public boolean isAllNull() {
		return (this.userName == null) && (this.unitCode == null)
				&& (this.dateTime == null);
	}

	public static final class FieldID {
		public static final int USERNAME = 1;
		public static final int UNITCODE = 2;
		public static final int DATETIME = 3;
	}
}
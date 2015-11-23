package nirvana.hall.api.internal.blob.coder;

public class AFISPair {
	private Object first;
	private Object second;

	public AFISPair() {
	}

	public AFISPair(Object first, Object second) {
		this.first = first;
		this.second = second;
	}

	public Object getFirst() {
		return this.first;
	}

	public void setFirst(Object first) {
		this.first = first;
	}

	public Object getSecond() {
		return this.second;
	}

	public void setSecond(Object second) {
		this.second = second;
	}

	public static final class FieldID {
		public static final int FID_FIRST = 1;
		public static final int FID_SECOND = 2;
	}
}
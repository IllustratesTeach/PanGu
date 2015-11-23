package nirvana.hall.api.internal.blob.util;

public class AFISDateTime {
	public AFISDate date;
	public AFISTime time;

	public AFISDateTime() {
		this.date = new AFISDate();
		this.time = new AFISTime();
	}

	public AFISDateTime(byte[] data) {
		this.date = new AFISDate(data, 4);
		this.time = new AFISTime(data);
	}

	public byte[] toBinary() {
		byte[] data = new byte[8];
		this.time.toBinary(data);
		this.date.toBinary(data, 4);
		return data;
	}

	public void fromBinary(byte[] data, int offset) {
		this.time.fromBinary(data, offset);
		this.date.fromBinary(data, offset + 4);
	}
}
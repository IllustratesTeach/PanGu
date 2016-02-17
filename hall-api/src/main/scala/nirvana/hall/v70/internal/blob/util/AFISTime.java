package nirvana.hall.v70.internal.blob.util;

public class AFISTime {
	private byte hour;
	private byte minute;
	private byte second;
	private short milliSecond;

	public AFISTime() {
		this.hour = (this.minute = this.second = 0);
		this.milliSecond = 0;
	}

	public AFISTime(byte hour, byte minute, byte second, short milliSecond) {
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		this.milliSecond = milliSecond;
	}

	public AFISTime(byte[] data, int offset) {
		fromBinary(data, offset);
	}

	public void fromBinary(byte[] data, int offset) {
		this.hour = data[offset];
		this.minute = data[(offset + 1)];
		this.second = data[(offset + 2)];

		this.milliSecond = DataConv.char2ToInt2(data, offset + 4);
	}

	public AFISTime(byte[] data) {
		this(data, 0);
	}

	public byte getHour() {
		return this.hour;
	}

	public void setHour(byte hour) {
		this.hour = hour;
	}

	public byte getMinute() {
		return this.minute;
	}

	public void setMinute(byte minute) {
		this.minute = minute;
	}

	public byte getSecond() {
		return this.second;
	}

	public void setSecond(byte second) {
		this.second = second;
	}

	public short getMilliSecond() {
		return this.milliSecond;
	}

	public void setMilliSecond(short milliSecond) {
		this.milliSecond = milliSecond;
	}

	public byte[] toBinary(byte[] data, int offset) {
		data[offset] = this.hour;
		data[(offset + 1)] = this.minute;
		data[(offset + 2)] = this.second;
		DataConv.int2ToChar2(this.milliSecond, data, offset + 4);
		return data;
	}

	public byte[] toBinary(byte[] data) {
		return toBinary(data, 0);
	}

	public byte[] toBinary() {
		return toBinary(new byte[6], 0);
	}
}
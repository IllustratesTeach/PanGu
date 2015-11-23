package nirvana.hall.api.internal.blob.util;

public class AFISDate {
	private short year;
	private byte month;
	private byte day;

	public AFISDate() {
		this.year = 0;
		this.month = 0;
		this.day = 0;
	}

	public AFISDate(short year, byte month, byte day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	public AFISDate(byte[] data) {
		fromBinary(data, 0);
	}

	public AFISDate(byte[] data, int offset) {
		fromBinary(data, offset);
	}

	public void fromBinary(byte[] data, int offset) {
		this.year = DataConv.char2ToInt2(data, offset);
		this.month = data[(offset + 2)];
		this.day = data[(offset + 3)];
	}

	public short getYear() {
		return this.year;
	}

	public void setYear(short year) {
		this.year = year;
	}

	public byte getMonth() {
		return this.month;
	}

	public void setMonth(byte month) {
		this.month = month;
	}

	public byte getDay() {
		return this.day;
	}

	public void setDay(byte day) {
		this.day = day;
	}

	public byte[] toBinary(byte[] data, int offset) {
		DataConv.int2ToChar2(this.year, data, offset);
		data[(offset + 2)] = this.month;
		data[(offset + 3)] = this.day;
		return data;
	}

	public byte[] toBinary() {
		return toBinary(new byte[4], 0);
	}

	public byte[] toBinary(byte[] data) {
		return toBinary(data, 0);
	}
}
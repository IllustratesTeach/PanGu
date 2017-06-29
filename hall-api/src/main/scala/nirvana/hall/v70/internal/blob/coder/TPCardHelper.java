package nirvana.hall.v70.internal.blob.coder;

import nirvana.hall.v70.internal.blob.bytestream.EgfStdCoder;
import nirvana.hall.v70.internal.blob.bytestream.EgfStdField;
import nirvana.hall.v70.internal.blob.bytestream.EgfStdStreamImpl;
import nirvana.hall.v70.internal.blob.bytestream.MemInputStream;
import nirvana.hall.v70.internal.blob.bytestream.StringStreamImpl;
import nirvana.hall.v70.internal.blob.error.AFISError;
import nirvana.hall.v70.internal.blob.util.AFISHelper;

public class TPCardHelper {
	private static TPCardHelper instance = new TPCardHelper();

	public static TPCardHelper getInstance() {
		return instance;
	}

	/**
	 * 人像、指纹
	 * 
	 * @param paramArrayOfByte
	 * @return
	 * @throws AFISError
	 */
	public TPCardObject convertTPCard(byte[] paramArrayOfByte) throws AFISError {
		if (AFISHelper.isEmpty(paramArrayOfByte))
			return null;
		try {
			MemInputStream localMemInputStream = new MemInputStream(paramArrayOfByte, 0, paramArrayOfByte.length);
			StringStreamImpl localStringStreamImpl = new StringStreamImpl();
			EgfStdStreamImpl localEgfStdStreamImpl = new EgfStdStreamImpl(localMemInputStream, localStringStreamImpl);
			localEgfStdStreamImpl.startReadDoc();// 验证头部信息
			EgfStdField localEgfStdField = new EgfStdField();
			EgfStdCoder localEgfStdCoder = new EgfStdCoder();
			localEgfStdCoder.addCoder(100, new TPCardObjectCoder());
			localEgfStdStreamImpl.parseDoc(localEgfStdField, localEgfStdCoder);
			return (TPCardObject) localEgfStdField.value;
		} catch (Exception localException) {
		}
		throw new AFISError();
	}
}
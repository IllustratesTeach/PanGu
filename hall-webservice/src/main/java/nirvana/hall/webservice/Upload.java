package nirvana.hall.webservice;

import java.io.Serializable;
/**
 * 上报
 * @author shijiarui
 * @date 2017年5月9日  13:53:01
 * @version V1.0
 */

public class Upload {

	/* 本地十指 */
	public static final byte UPLOAD_SERVICE_TYPE_LOCAL_TP = 1;
	/* 本地现场 */
	public static final byte UPLOAD_SERVICE_TYPE_LOCAL_LP = 2;
	/* 本地TT */
	public static final byte UPLOAD_SERVICE_TYPE_LOCAL_TT = 3;
	/* 本地TL */
	public static final byte UPLOAD_SERVICE_TYPE_LOCAL_TL = 4;
	/* 本地LT */
	public static final byte UPLOAD_SERVICE_TYPE_LOCAL_LT = 5;
	/* 本地LL */
	public static final byte UPLOAD_SERVICE_TYPE_LOCAL_LL = 6;
	/*协查案件 */
	public static final byte UPLOAD_SERVICE_TYPE_ASSIST_CASE = 7;
	/* 追逃 */
	public static final byte UPLOAD_SERVICE_TYPE_ESCAPE = 8;
	
	/* 已接收 */
	public static final byte UPLOAD_STATUS_RECEIVED = 0;
	/* fpt已生成 */
	public static final byte UPLOAD_STATUS_CREATED = 1;
	/* fpt已发送 */
	public static final byte UPLOAD_STATUS_SENT = 2;
	/* 上报成功 */
	public static final byte UPLOAD_STATUS_SUCCESS = 3;
	/* 上报重复 */
	public static final byte UPLOAD_STATUS_REPEAT = 4;
	/* 本地撤销成功 */
	public static final byte UPLOAD_STATUS_LOCAL_UNDO_SUCCESS = 5;
	/* 外省撤销 */
	public static final byte UPLOAD_STATUS_REMOTE_CANCEL = 6;
	
	/* id */
	private  String id;   			    
	/* 任务id */
	private  String taskId;
	/* 业务id */
	private  String serviceId;
	/* 业务类型 */
	private  int serviceType; 
	/* fpt文件 */
	private byte[] fpt;
	
}

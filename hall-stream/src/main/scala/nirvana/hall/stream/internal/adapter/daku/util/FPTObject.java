package nirvana.hall.stream.internal.adapter.daku.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sp
 * FPT文件工具类用于解析FPT文件，支持3和4的版本
 */
public final class FPTObject {
    private String version;//版本信息
    private FPTHead head = new FPTHead();//头部描述信息
    private List<FPTtpData> tpDataList = new ArrayList<FPTtpData>();//发送捺印指纹数据
    private List<FPTlpData> lpDataList = new ArrayList<FPTlpData>();//发送现场指纹数据
    private List<FPTlt_tlMatchData> lt_tlMatchDataList = new ArrayList<FPTObject.FPTlt_tlMatchData>();//指纹正查和倒查比中信息
    private List<FPTttMatchData> ttMatchDataList = new ArrayList<FPTObject.FPTttMatchData>();//指纹查重比中信息
    private List<FPTllMatchData> llMatchDataList = new ArrayList<FPTObject.FPTllMatchData>();//指纹串查比中信息
    private List<FPTlpMatchRequestData> lpMatchRequestDataList = new ArrayList<FPTObject.FPTlpMatchRequestData>();//现场指纹查询请求信息
    private List<FPTtpMatchRequestData> tpMatchRequestDataList = new ArrayList<FPTObject.FPTtpMatchRequestData>();//十指指纹查询请求信息
    private List<FPTltMatchCandidateData> ltMatchCandidateDataList = new ArrayList<FPTObject.FPTltMatchCandidateData>();//正查比对结果候选信息
    private List<FPTtlMatchCandidateData> tlMatchCandidateDataList = new ArrayList<FPTObject.FPTtlMatchCandidateData>();//倒查比对结果候选信息
    private List<FPTttMatchCandidateData> ttMatchCandidateDataList = new ArrayList<FPTObject.FPTttMatchCandidateData>();//倒查比对结果候选信息
    private List<FPTllMatchCandidateData> llMatchCandidateDataList = new ArrayList<FPTObject.FPTllMatchCandidateData>();//倒查比对结果候选信息
    private List<FPTCustomData> customDataList = new ArrayList<FPTCustomData>();//自定义逻辑记录
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public FPTHead getHead() {
        return head;
    }
    public void setHead(FPTHead head) {
        this.head = head;
    }
    public List<FPTtpData> getTpDataList() {
        return tpDataList;
    }
    public void setTpDataList(List<FPTtpData> tpDataList) {
        this.tpDataList = tpDataList;
    }
    public List<FPTlpData> getLpDataList() {
        return lpDataList;
    }
    public void setLpDataList(List<FPTlpData> lpDataList) {
        this.lpDataList = lpDataList;
    }
    public List<FPTlt_tlMatchData> getLt_tlMatchDataList() {
        return lt_tlMatchDataList;
    }
    public void setLt_tlMatchDataList(List<FPTlt_tlMatchData> lt_tlMatchDataList) {
        this.lt_tlMatchDataList = lt_tlMatchDataList;
    }
    public List<FPTttMatchData> getTtMatchDataList() {
        return ttMatchDataList;
    }
    public void setTtMatchDataList(List<FPTttMatchData> ttMatchDataList) {
        this.ttMatchDataList = ttMatchDataList;
    }
    public List<FPTllMatchData> getLlMatchDataList() {
        return llMatchDataList;
    }
    public void setLlMatchDataList(List<FPTllMatchData> llMatchDataList) {
        this.llMatchDataList = llMatchDataList;
    }
    public List<FPTlpMatchRequestData> getLpMatchRequestDataList() {
        return lpMatchRequestDataList;
    }
    public void setLpMatchRequestDataList(
            List<FPTlpMatchRequestData> lpMatchRequestDataList) {
        this.lpMatchRequestDataList = lpMatchRequestDataList;
    }
    public List<FPTtpMatchRequestData> getTpMatchRequestDataList() {
        return tpMatchRequestDataList;
    }
    public void setTpMatchRequestDataList(
            List<FPTtpMatchRequestData> tpMatchRequestDataList) {
        this.tpMatchRequestDataList = tpMatchRequestDataList;
    }
    public List<FPTltMatchCandidateData> getLtMatchCandidateDataList() {
        return ltMatchCandidateDataList;
    }
    public void setLtMatchCandidateDataList(
            List<FPTltMatchCandidateData> ltMatchCandidateDataList) {
        this.ltMatchCandidateDataList = ltMatchCandidateDataList;
    }
    public List<FPTtlMatchCandidateData> getTlMatchCandidateDataList() {
        return tlMatchCandidateDataList;
    }
    public void setTlMatchCandidateDataList(
            List<FPTtlMatchCandidateData> tlMatchCandidateDataList) {
        this.tlMatchCandidateDataList = tlMatchCandidateDataList;
    }
    public List<FPTttMatchCandidateData> getTtMatchCandidateDataList() {
        return ttMatchCandidateDataList;
    }
    public void setTtMatchCandidateDataList(
            List<FPTttMatchCandidateData> ttMatchCandidateDataList) {
        this.ttMatchCandidateDataList = ttMatchCandidateDataList;
    }
    public List<FPTllMatchCandidateData> getLlMatchCandidateDataList() {
        return llMatchCandidateDataList;
    }
    public void setLlMatchCandidateDataList(
            List<FPTllMatchCandidateData> llMatchCandidateDataList) {
        this.llMatchCandidateDataList = llMatchCandidateDataList;
    }
    public List<FPTCustomData> getCustomDataList() {
        return customDataList;
    }
    public void setCustomDataList(List<FPTCustomData> customDataList) {
        this.customDataList = customDataList;
    }
    /**
     * 解析fpt文件输出流
     * @param in
     * @return
     * @throws Exception
     */
    public static FPTObject parseOfInputStream(InputStream in) throws Exception{
        FPTObject fptObject = new FPTObject();
        byte[] fptFlag = new byte[3];
        in.read(fptFlag);
        if("FPT".equals(new String(fptFlag))){
            byte[] version = new byte[4];
            byte[] length = new byte[8];
            byte[] dataType = new byte[1];
            in.read(version);
            fptObject.version = bytes2String(version);
            if(fptObject.version.startsWith("03")){
                fptObject.head = FPTHead.parseFromInputStream3(in);
            }else if(fptObject.version.startsWith("04")){
                fptObject.head = FPTHead.parseFromInputStream4(in);
                dataType = new byte[2];//FPT4逻辑类型长度是2
            }
            while(in.read(length) != -1){
                int length_ = bytes2String2Int(length);//逻辑记录长度
                in.read(dataType);
                String dataType_ = bytes2String(dataType);//逻辑数据类型 ：3版本为1,2,3; 4版本为01,01,03,04,05,06,07,08,09,10,11,12,99
                if("2".equals(dataType_)){
                    fptObject.lpDataList.add(FPTlpData.parseFromInputStream3(in));
                }else if("3".equals(dataType_)){
                    fptObject.tpDataList.add(FPTtpData.parseFromInputStream3(in));
                }else if("02".equals(dataType_)){
                    fptObject.tpDataList.add(FPTtpData.parseFromInputStream4(in));
                }else if("03".equals(dataType_)){
                    fptObject.lpDataList.add(FPTlpData.parseFromInputStream4(in));
                }else if("04".equals(dataType_)){
                    fptObject.lt_tlMatchDataList.add(FPTlt_tlMatchData.parseFromInputStream(in));
                }else if("05".equals(dataType_)){
                    fptObject.ttMatchDataList.add(FPTttMatchData.parseFromInputStream(in));
                }else if("06".equals(dataType_)){
                    fptObject.llMatchDataList.add(FPTllMatchData.parseFromInputStream(in));
                }else if("07".equals(dataType_)){
                    fptObject.lpMatchRequestDataList.add(FPTlpMatchRequestData.parseFromInputStream(in));
                }else if("08".equals(dataType_)){
                    fptObject.tpMatchRequestDataList.add(FPTtpMatchRequestData.parseFromInputStream(in));
                }else if("09".equals(dataType_)){
                    fptObject.ltMatchCandidateDataList.add(FPTltMatchCandidateData.parseFromInputStream(in));
                }else if("10".equals(dataType_)){
                    fptObject.tlMatchCandidateDataList.add(FPTtlMatchCandidateData.parseFromInputStream(in));
                }else if("11".equals(dataType_)){
                    fptObject.ttMatchCandidateDataList.add(FPTttMatchCandidateData.parseFromInputStream(in));
                }else if("12".equals(dataType_)){
                    fptObject.llMatchCandidateDataList.add(FPTllMatchCandidateData.parseFromInputStream(in));
                }else if("99".equals(dataType_)){
                    fptObject.customDataList.add(FPTCustomData.parseFromInputStream(in, length_));
                }
            }
        }else{
            throw new RuntimeException("FPT文件格式错误");
        }
        in.close();
        return fptObject;
    }
    /**
     * 解析fpt文件
     * @param file
     * @return
     * @throws Exception
     */
    public static FPTObject parseOfFile(File file) throws Exception{
        return parseOfInputStream(new FileInputStream(file));
    }
    /**
     * 将对象转换为fpt文件byte[]
     * @throws Exception
     */
    public byte[] toByteArray() throws Exception{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        this.version = "0400";//默认版本号
        this.head.sendUnitSystemType = "1900";//东方金指
        //重新设定数量
        this.head.setTpCount(this.tpDataList.size());
        this.head.setLpCount(this.lpDataList.size());
        this.head.setTl_ltCount(this.lt_tlMatchDataList.size());
        this.head.setTtCount(this.ttMatchDataList.size());
        this.head.setLlCount(this.llMatchDataList.size());
        this.head.setLpRequestCount(this.lpMatchRequestDataList.size());
        this.head.setTpRequestCount(this.tpMatchRequestDataList.size());
        this.head.setLtCandidateCount(this.ltMatchCandidateDataList.size());
        this.head.setTlCandidateCount(this.tlMatchCandidateDataList.size());
        this.head.setTtCandidateCount(this.ttMatchCandidateDataList.size());
        this.head.setLlCandidateCount(this.llMatchCandidateDataList.size());
        this.head.setCustomCandidateCount(0);
        //FPT标记+版本号
        out.write(("FPT"+this.version).getBytes());
        //01 任务描述类记录数据
        byte[] head = this.head.toByteArray();
        out.write(head);
        //02 十指指纹信息记录数据
        for (int i = 0; i < this.tpDataList.size(); i++) {
            FPTtpData tpData = this.tpDataList.get(i);
            tpData.index = i+1;
            out.write(tpData.toByteArray());
        }
        //03 现场指纹信息记录数据
        for (int i = 0; i < this.lpDataList.size(); i++) {
            FPTlpData lpData = this.lpDataList.get(i);
            lpData.index = i+1;
            out.write(lpData.toByteArray());
        }
        //04指纹正查和倒查比中信息
        for (int i = 0; i < this.lt_tlMatchDataList.size(); i++) {
            FPTlt_tlMatchData matchData = this.lt_tlMatchDataList.get(i);
            matchData.index = i+1;
            out.write(matchData.toByteArray());
        }
        //05指纹查重比中信息
        for (int i = 0; i < this.ttMatchDataList.size(); i++) {
            FPTttMatchData matchData = this.ttMatchDataList.get(i);
            matchData.index = i+1;
            out.write(matchData.toByteArray());
        }
        //06指纹串查比中信息
        for (int i = 0; i < this.llMatchDataList.size(); i++) {
            FPTllMatchData matchData = this.llMatchDataList.get(i);
            matchData.index = i+1;
            out.write(matchData.toByteArray());
        }
        byte[] result = out.toByteArray();
        byte[] fileLength = String.valueOf(result.length).getBytes();
        System.arraycopy(fileLength, 0, result, 7, fileLength.length);
        return result;
    }

    /**
     * 任务描述类记录数据
     */
    public static class FPTHead{
        //		private String version;//版本编号
        private int fileLength;//文件长度
        private int lpCount;//现场指纹信息记录数量
        private int tpCount;//十指指纹信息记录数量
        private String sendTime;//发送时间yyyyMMddhhmmss
        private String receiveUnitCode;//接收单位代码
        private String sendUnitCode;//发送单位代码
        private String sendUnitName;//发送单位名称
        private String sender;//发送人
        /**
         * 发送单位系统类型
         * 1900 东方金指
         * 1300 北大高科
         * 1700 北京海鑫
         * 1800 小日本NEC
         * 1200 北京邮电大学
         * 1100 北京刑科所
         */
        private String sendUnitSystemType;
        private String sid;//任务控制号
        private String remark;//备注
        /*
		 * 一下是FPT4版本才会有的数据
		 * */
        private int tl_ltCount;//指纹正查及倒查比中信息记录数量
        private int ttCount;//指纹查重比中信息记录数量
        private int llCount;//指纹串查比中信息记录数量
        private int lpRequestCount;//现场指纹查询请求信息记录数量
        private int tpRequestCount;//十指指纹查询请求信息记录数量
        private int ltCandidateCount;//正查比对结果候选信息记录数量
        private int tlCandidateCount;//倒查比对结果候选信息记录数量
        private int ttCandidateCount;//查重比对结果候选信息记录数量
        private int llCandidateCount;//串查比对结果候选信息记录数量
        private int customCandidateCount;//自定义逻辑记录数量

        public int getFileLength() {
            return fileLength;
        }
        public void setFileLength(int fileLength) {
            this.fileLength = fileLength;
        }
        public int getLpCount() {
            return lpCount;
        }
        public void setLpCount(int lpCount) {
            this.lpCount = lpCount;
        }
        public int getTpCount() {
            return tpCount;
        }
        public void setTpCount(int tpCount) {
            this.tpCount = tpCount;
        }
        public String getSendTime() {
            return sendTime;
        }
        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }
        public String getReceiveUnitCode() {
            return receiveUnitCode;
        }
        public void setReceiveUnitCode(String receiveUnitCode) {
            this.receiveUnitCode = receiveUnitCode;
        }
        public String getSendUnitCode() {
            return sendUnitCode;
        }
        public void setSendUnitCode(String sendUnitCode) {
            this.sendUnitCode = sendUnitCode;
        }
        public String getSendUnitName() {
            return sendUnitName;
        }
        public void setSendUnitName(String sendUnitName) {
            this.sendUnitName = sendUnitName;
        }
        public String getSender() {
            return sender;
        }
        public void setSender(String sender) {
            this.sender = sender;
        }
        public String getSendUnitSystemType() {
            return sendUnitSystemType;
        }
        public void setSendUnitSystemType(String sendUnitSystemType) {
            this.sendUnitSystemType = sendUnitSystemType;
        }
        public String getSid() {
            return sid;
        }
        public void setSid(String sid) {
            this.sid = sid;
        }
        public String getRemark() {
            return remark;
        }
        public void setRemark(String remark) {
            this.remark = remark;
        }
        public int getTl_ltCount() {
            return tl_ltCount;
        }
        public void setTl_ltCount(int tl_ltCount) {
            this.tl_ltCount = tl_ltCount;
        }
        public int getTtCount() {
            return ttCount;
        }
        public void setTtCount(int ttCount) {
            this.ttCount = ttCount;
        }
        public int getLlCount() {
            return llCount;
        }
        public void setLlCount(int llCount) {
            this.llCount = llCount;
        }
        public int getLpRequestCount() {
            return lpRequestCount;
        }
        public void setLpRequestCount(int lpRequestCount) {
            this.lpRequestCount = lpRequestCount;
        }
        public int getTpRequestCount() {
            return tpRequestCount;
        }
        public void setTpRequestCount(int tpRequestCount) {
            this.tpRequestCount = tpRequestCount;
        }
        public int getLtCandidateCount() {
            return ltCandidateCount;
        }
        public void setLtCandidateCount(int ltCandidateCount) {
            this.ltCandidateCount = ltCandidateCount;
        }
        public int getTlCandidateCount() {
            return tlCandidateCount;
        }
        public void setTlCandidateCount(int tlCandidateCount) {
            this.tlCandidateCount = tlCandidateCount;
        }
        public int getTtCandidateCount() {
            return ttCandidateCount;
        }
        public void setTtCandidateCount(int ttCandidateCount) {
            this.ttCandidateCount = ttCandidateCount;
        }
        public int getLlCandidateCount() {
            return llCandidateCount;
        }
        public void setLlCandidateCount(int llCandidateCount) {
            this.llCandidateCount = llCandidateCount;
        }
        public int getCustomCandidateCount() {
            return customCandidateCount;
        }
        public void setCustomCandidateCount(int customCandidateCount) {
            this.customCandidateCount = customCandidateCount;
        }
        /**
         * 解析版本是3的文件头信息
         * @param in
         * @return
         * @throws IOException
         */
        private static FPTHead parseFromInputStream3(InputStream in) throws IOException {
            FPTHead head = new FPTHead();
            byte[] fileLength = new byte[12];
            byte[] dataType = new byte[1];
            byte[] lpCount = new byte[6];
            byte[] tpCount = new byte[6];
            byte[] sendTime = new byte[14];
            byte[] receiveUnitCode = new byte[12];
            byte[] sendUnitCode = new byte[12];
            byte[] sendUnitName = new byte[70];
            byte[] sender = new byte[30];
            byte[] sendUnitSystemType = new byte[4];
            byte[] sid = new byte[10];
            byte[] remark = new byte[512];
            in.read(fileLength);
            head.fileLength = bytes2String2Int(fileLength);
            in.read(dataType);//FPT3版本的逻辑记录类型1,这里可不做验证
            in.read(lpCount);
            head.lpCount = bytes2String2Int(lpCount);
            in.read(tpCount);
            head.tpCount = bytes2String2Int(tpCount);
            in.read(sendTime);
            head.sendTime = bytes2String(sendTime);
            in.read(receiveUnitCode);
            head.receiveUnitCode = bytes2String(receiveUnitCode);
            in.read(sendUnitCode);
            head.sendUnitCode = bytes2String(sendUnitCode);
            in.read(sendUnitName);
            head.sendUnitName = bytes2String(sendUnitName);
            in.read(sender);
            head.sender = bytes2String(sender);
            in.read(sendUnitSystemType);
            head.sendUnitSystemType = bytes2String(sendUnitSystemType);
            in.read(sid);
            head.sid = bytes2String(sid);
            in.read(remark);
            head.remark = bytes2String(remark);
            in.read();//结束分隔符
            return head;
        }
        private static FPTHead parseFromInputStream4(InputStream in) throws IOException {
            FPTHead head = new FPTHead();
            byte[] fileLength = new byte[12];
            byte[] dataType = new byte[2];//fpt4版本的逻辑类型长度2
            byte[] lpCount = new byte[6];
            byte[] tpCount = new byte[6];
            byte[] tl_ltCount = new byte[6];
            byte[] ttCount = new byte[6];
            byte[] llCount = new byte[6];
            byte[] lpRequestCount = new byte[6];
            byte[] tpRequestCount = new byte[6];
            byte[] ltCandidateCount = new byte[6];
            byte[] tlCandidateCount = new byte[6];
            byte[] ttCandidateCount = new byte[6];
            byte[] llCandidateCount = new byte[6];
            byte[] customCandidateCount = new byte[6];
            byte[] sendTime = new byte[14];
            byte[] receiveUnitCode = new byte[12];
            byte[] sendUnitCode = new byte[12];
            byte[] sendUnitName = new byte[70];
            byte[] sender = new byte[30];
            byte[] sendUnitSystemType = new byte[4];
            byte[] sid = new byte[10];
            byte[] remark = new byte[512];
            in.read(fileLength);
            head.fileLength = bytes2String2Int(fileLength);
            in.read(dataType);//逻辑记录类型01
            in.read(lpCount);
            head.lpCount = bytes2String2Int(lpCount);
            in.read(tpCount);
            head.tpCount = bytes2String2Int(tpCount);
            in.read(tl_ltCount);
            head.tl_ltCount = bytes2String2Int(tl_ltCount);
            in.read(ttCount);
            head.ttCount = bytes2String2Int(ttCount);
            in.read(llCount);
            head.llCount = bytes2String2Int(llCount);
            in.read(lpRequestCount);
            head.lpRequestCount = bytes2String2Int(lpRequestCount);
            in.read(tpRequestCount);
            head.tpRequestCount = bytes2String2Int(tpRequestCount);
            in.read(ltCandidateCount);
            head.ltCandidateCount = bytes2String2Int(ltCandidateCount);
            in.read(tlCandidateCount);
            head.tlCandidateCount = bytes2String2Int(tlCandidateCount);
            in.read(ttCandidateCount);
            head.ttCandidateCount = bytes2String2Int(ttCandidateCount);
            in.read(llCandidateCount);
            head.llCandidateCount = bytes2String2Int(llCandidateCount);
            in.read(customCandidateCount);
            head.customCandidateCount = bytes2String2Int(customCandidateCount);
            in.read(sendTime);
            head.sendTime = bytes2String(sendTime);
            in.read(receiveUnitCode);
            head.receiveUnitCode = bytes2String(receiveUnitCode);
            in.read(sendUnitCode);
            head.sendUnitCode = bytes2String(sendUnitCode);
            in.read(sendUnitName);
            head.sendUnitName = bytes2String(sendUnitName);
            in.read(sender);
            head.sender = bytes2String(sender);
            in.read(sendUnitSystemType);
            head.sendUnitSystemType = bytes2String(sendUnitSystemType);
            in.read(sid);
            head.sid = bytes2String(sid);
            in.read(remark);
            head.remark = bytes2String(remark);
            in.read();//结束分隔符
            return head;
        }
        /**
         * 将对象转换为byte[]
         * @throws Exception
         */
        private byte[] toByteArray() throws Exception{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] fileLength = new byte[12];
            out.write(fileLength);
            out.write(string2Bytes("01", 2));//fpt4版本的逻辑类型长度2
            out.write(int2String2Bytes(this.tpCount, 6));
            out.write(int2String2Bytes(this.lpCount, 6));
            out.write(int2String2Bytes(this.tl_ltCount, 6));
            out.write(int2String2Bytes(this.ttCount, 6));
            out.write(int2String2Bytes(this.llCount, 6));
            out.write(int2String2Bytes(this.lpRequestCount, 6));
            out.write(int2String2Bytes(this.tpRequestCount, 6));
            out.write(int2String2Bytes(this.ltCandidateCount, 6));
            out.write(int2String2Bytes(this.tlCandidateCount, 6));
            out.write(int2String2Bytes(this.ttCandidateCount, 6));
            out.write(int2String2Bytes(this.llCandidateCount, 6));
            out.write(int2String2Bytes(this.customCandidateCount, 6));
            out.write(string2Bytes(this.sendTime, 14));
            out.write(string2Bytes(this.receiveUnitCode, 12));
            out.write(string2Bytes(this.sendUnitCode, 12));
            out.write(string2Bytes(this.sendUnitName, 70));
            out.write(string2Bytes(this.sender, 30));
            out.write(string2Bytes(this.sendUnitSystemType, 4));
            out.write(string2Bytes(this.sid, 10));
            out.write(string2Bytes(this.remark, 512));
            out.write((byte)28);
            return out.toByteArray();
        }
    }
    /**
     * 十指指纹捺印数据
     */
    public static class FPTtpData {
        private int length;//本逻辑记录长度
        private String dataType;//记录类型
        private int index;//序号
        private String systemType;//系统类型
        private String personId;//人员编号
        private String cardId;//卡号
        private String personName;//被捺印人姓名
        private String alias;//别名
        private String gender;//性别
        private String birthday;//出生日期
        private String idCardNo;//身份证
        private String door;//户籍地行政区划
        private String doorDetail;//现住址
        private String address;//现住址行政区划
        private String addressDetail;//现住址category
        private String category;//人员类别
        private String caseClass1Code;//案件类别1
        private String caseClass2Code;//案件类别2
        private String caseClass3Code;//案件类别3
        private String gatherUnitCode;//捺印单位代码
        private String gatherUnitName;//捺印单位名称
        private String gatherName;//捺印人姓名
        private String gatherDate;//捺印日期
        private String remark;//备注
        private int sendFingerCount;//发送指纹个数
        /* 以下为4版本数据项 */
        private String nativeplace;//国籍
        private String nation;//民族
        private String certificateType;//证件类型
        private String certificateNo;//证件号码
        private String isCriminal;//前科标识
        private String criminalInfo;//前科情况
        private String assistLevel;//协查级别
        private String bonus;//奖金
        private String assistPurpose;//协查目的
        private String relatedPersonId;//相关人员编号
        private String relatedCaseId;//相关案件编号
        private String assistTimeLimit;//协查有效时限
        private String assistAskingInfo;//协查请求说明
        private String assistUnitCode;//协查单位代码
        private String assistUnitName;//协查单位名称
        private String assistDate;//协查日期
        private String contact;//联系人
        private String contactPhone;//联系人电话
        private String approver;//审批人
        private String isAssist;//协查标识
        private int portraitDataLength;//人像图像数据长度
        private byte[] portraitData;//人像图像数据

        public int getLength() {
            return length;
        }
        public void setLength(int length) {
            this.length = length;
        }
        public String getDataType() {
            return dataType;
        }
        public void setDataType(String dataType) {
            this.dataType = dataType;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
        public String getSystemType() {
            return systemType;
        }
        public void setSystemType(String systemType) {
            this.systemType = systemType;
        }
        public String getPersonId() {
            return personId;
        }
        public void setPersonId(String personId) {
            this.personId = personId;
        }
        public String getCardId() {
            return cardId;
        }
        public void setCardId(String cardId) {
            this.cardId = cardId;
        }
        public String getPersonName() {
            return personName;
        }
        public void setPersonName(String personName) {
            this.personName = personName;
        }
        public String getAlias() {
            return alias;
        }
        public void setAlias(String alias) {
            this.alias = alias;
        }
        public String getGender() {
            return gender;
        }
        public void setGender(String gender) {
            this.gender = gender;
        }
        public String getBirthday() {
            return birthday;
        }
        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }
        public String getIdCardNo() {
            return idCardNo;
        }
        public void setIdCardNo(String idCardNo) {
            this.idCardNo = idCardNo;
        }
        public String getDoor() {
            return door;
        }
        public void setDoor(String door) {
            this.door = door;
        }
        public String getDoorDetail() {
            return doorDetail;
        }
        public void setDoorDetail(String doorDetail) {
            this.doorDetail = doorDetail;
        }
        public String getAddress() {
            return address;
        }
        public void setAddress(String address) {
            this.address = address;
        }
        public String getAddressDetail() {
            return addressDetail;
        }
        public void setAddressDetail(String addressDetail) {
            this.addressDetail = addressDetail;
        }
        public String getCategory() {
            return category;
        }
        public void setCategory(String category) {
            this.category = category;
        }
        public String getCaseClass1Code() {
            return caseClass1Code;
        }
        public void setCaseClass1Code(String caseClass1Code) {
            this.caseClass1Code = caseClass1Code;
        }
        public String getCaseClass2Code() {
            return caseClass2Code;
        }
        public void setCaseClass2Code(String caseClass2Code) {
            this.caseClass2Code = caseClass2Code;
        }
        public String getCaseClass3Code() {
            return caseClass3Code;
        }
        public void setCaseClass3Code(String caseClass3Code) {
            this.caseClass3Code = caseClass3Code;
        }
        public String getGatherUnitCode() {
            return gatherUnitCode;
        }
        public void setGatherUnitCode(String gatherUnitCode) {
            this.gatherUnitCode = gatherUnitCode;
        }
        public String getGatherUnitName() {
            return gatherUnitName;
        }
        public void setGatherUnitName(String gatherUnitName) {
            this.gatherUnitName = gatherUnitName;
        }
        public String getGatherName() {
            return gatherName;
        }
        public void setGatherName(String gatherName) {
            this.gatherName = gatherName;
        }
        public String getGatherDate() {
            return gatherDate;
        }
        public void setGatherDate(String gatherDate) {
            this.gatherDate = gatherDate;
        }
        public String getRemark() {
            return remark;
        }
        public void setRemark(String remark) {
            this.remark = remark;
        }
        public int getSendFingerCount() {
            return sendFingerCount;
        }
        public void setSendFingerCount(int sendFingerCount) {
            this.sendFingerCount = sendFingerCount;
        }
        public String getNativeplace() {
            return nativeplace;
        }
        public void setNativeplace(String nativeplace) {
            this.nativeplace = nativeplace;
        }
        public String getNation() {
            return nation;
        }
        public void setNation(String nation) {
            this.nation = nation;
        }
        public String getCertificateType() {
            return certificateType;
        }
        public void setCertificateType(String certificateType) {
            this.certificateType = certificateType;
        }
        public String getCertificateNo() {
            return certificateNo;
        }
        public void setCertificateNo(String certificateNo) {
            this.certificateNo = certificateNo;
        }
        public String getIsCriminal() {
            return isCriminal;
        }
        public void setIsCriminal(String isCriminal) {
            this.isCriminal = isCriminal;
        }
        public String getCriminalInfo() {
            return criminalInfo;
        }
        public void setCriminalInfo(String criminalInfo) {
            this.criminalInfo = criminalInfo;
        }
        public String getAssistLevel() {
            return assistLevel;
        }
        public void setAssistLevel(String assistLevel) {
            this.assistLevel = assistLevel;
        }
        public String getBonus() {
            return bonus;
        }
        public void setBonus(String bonus) {
            this.bonus = bonus;
        }
        public String getAssistPurpose() {
            return assistPurpose;
        }
        public void setAssistPurpose(String assistPurpose) {
            this.assistPurpose = assistPurpose;
        }
        public String getRelatedPersonId() {
            return relatedPersonId;
        }
        public void setRelatedPersonId(String relatedPersonId) {
            this.relatedPersonId = relatedPersonId;
        }
        public String getRelatedCaseId() {
            return relatedCaseId;
        }
        public void setRelatedCaseId(String relatedCaseId) {
            this.relatedCaseId = relatedCaseId;
        }
        public String getAssistTimeLimit() {
            return assistTimeLimit;
        }
        public void setAssistTimeLimit(String assistTimeLimit) {
            this.assistTimeLimit = assistTimeLimit;
        }
        public String getAssistAskingInfo() {
            return assistAskingInfo;
        }
        public void setAssistAskingInfo(String assistAskingInfo) {
            this.assistAskingInfo = assistAskingInfo;
        }
        public String getAssistUnitCode() {
            return assistUnitCode;
        }
        public void setAssistUnitCode(String assistUnitCode) {
            this.assistUnitCode = assistUnitCode;
        }
        public String getAssistUnitName() {
            return assistUnitName;
        }
        public void setAssistUnitName(String assistUnitName) {
            this.assistUnitName = assistUnitName;
        }
        public String getAssistDate() {
            return assistDate;
        }
        public void setAssistDate(String assistDate) {
            this.assistDate = assistDate;
        }
        public String getContact() {
            return contact;
        }
        public void setContact(String contact) {
            this.contact = contact;
        }
        public String getContactPhone() {
            return contactPhone;
        }
        public void setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
        }
        public String getApprover() {
            return approver;
        }
        public void setApprover(String approver) {
            this.approver = approver;
        }
        public String getIsAssist() {
            return isAssist;
        }
        public void setIsAssist(String isAssist) {
            this.isAssist = isAssist;
        }
        public int getPortraitDataLength() {
            return portraitDataLength;
        }
        public void setPortraitDataLength(int portraitDataLength) {
            this.portraitDataLength = portraitDataLength;
        }
        public byte[] getPortraitData() {
            return portraitData;
        }
        public void setPortraitData(byte[] portraitData) {
            this.portraitData = portraitData;
        }
        public List<FingerData> getFingerDataList() {
            return fingerDataList;
        }
        public void setFingerDataList(List<FingerData> fingerDataList) {
            this.fingerDataList = fingerDataList;
        }
        private List<FingerData> fingerDataList = new ArrayList<FPTObject.FPTtpData.FingerData>();//指纹数据列表

        private static FPTtpData parseFromInputStream3(InputStream in) throws IOException{
            FPTtpData data = new FPTtpData();
            byte[] index = new byte[6];
            byte[] systemType = new byte[4];
            byte[] personId = new byte[23];
            byte[] cardId = new byte[20];
            byte[] personName = new byte[30];
            byte[] alias = new byte[30];
            byte[] gender = new byte[1];
            byte[] birthday = new byte[8];
            byte[] idCardNo = new byte[18];
            byte[] door = new byte[6];
            byte[] doorDetail = new byte[70];
            byte[] address = new byte[6];
            byte[] addressDetail = new byte[70];
            byte[] category = new byte[20];
            byte[] caseClass1Code = new byte[6];
            byte[] caseClass2Code = new byte[6];
            byte[] caseClass3Code = new byte[6];
            byte[] gatherUnitCode = new byte[12];
            byte[] gatherUnitName = new byte[70];
            byte[] gatherName = new byte[30];
            byte[] gatherDate = new byte[8];
            byte[] remark = new byte[512];
            byte[] sendFingerCount = new byte[2];
            byte[] dataLength = new byte[6];
            byte[] sendNo = new byte[2];
            byte[] fgp = new byte[2];
            byte[] extractMethod = new byte[1];
            byte[] pattern1 = new byte[1];
            byte[] pattern2 = new byte[1];
            byte[] fingerDirection = new byte[5];
            byte[] centerPoint = new byte[14];
            byte[] subCenterPoint = new byte[14];
            byte[] leftTriangle = new byte[14];
            byte[] rightTriangle = new byte[14];
            byte[] featureCount = new byte[3];
            byte[] feature = new byte[1800];
            byte[] customInfoLength = new byte[4];
//			byte[] customInfo;
            byte[] imgHorizontalLength = new byte[3];
            byte[] imgVerticalLength = new byte[3];
            byte[] dpi = new byte[3];
            byte[] imgCompressMethod = new byte[4];
            byte[] imgDataLenght = new byte[6];
//			byte[] imgData;
            byte[] end = new byte[1];
            in.read(index);
            data.index = bytes2String2Int(index);
            in.read(systemType);
            data.systemType = bytes2String(systemType);
            in.read(personId);
            data.personId = bytes2String(personId);
            in.read(cardId);
            data.cardId = bytes2String(cardId);
            in.read(personName);
            data.personName = bytes2String(personName);
            in.read(alias);
            data.alias = bytes2String(alias);
            in.read(gender);
            data.gender = bytes2String(gender);
            in.read(birthday);
            data.birthday = bytes2String(birthday);
            in.read(idCardNo);
            data.idCardNo = bytes2String(idCardNo);
            in.read(door);
            data.door = bytes2String(door);
            in.read(doorDetail);
            data.doorDetail = bytes2String(doorDetail);
            in.read(address);
            data.address = bytes2String(address);
            in.read(addressDetail);
            data.addressDetail = bytes2String(addressDetail);
            in.read(category);
            data.category = bytes2String(category);
            in.read(caseClass1Code);
            data.caseClass1Code = bytes2String(caseClass1Code);
            in.read(caseClass2Code);
            data.caseClass2Code = bytes2String(caseClass2Code);
            in.read(caseClass3Code);
            data.caseClass3Code = bytes2String(caseClass3Code);
            in.read(gatherUnitCode);
            data.gatherUnitCode = bytes2String(gatherUnitCode);
            in.read(gatherUnitName);
            data.gatherUnitName = bytes2String(gatherUnitName);
            in.read(gatherName);
            data.gatherName = bytes2String(gatherName);
            in.read(gatherDate);
            data.gatherDate = bytes2String(gatherDate);
            in.read(remark);
            data.remark = bytes2String(remark);
            in.read(sendFingerCount);
            data.sendFingerCount = bytes2String2Int(sendFingerCount);
            do{
                if(data.sendFingerCount <= 0){//如果没有指纹数据
                    in.read(end);//读取文件分隔符FS
                    break;
                }
                FingerData fingerData = new FingerData();
                in.read(dataLength);
                fingerData.dataLength = bytes2String2Int(dataLength);
                in.read(sendNo);
                fingerData.sendNo = bytes2String(sendNo);
                in.read(fgp);
                fingerData.fgp = bytes2String(fgp);
                in.read(extractMethod);
                fingerData.extractMethod = bytes2String(extractMethod);
                in.read(pattern1);
                fingerData.pattern1 = bytes2String(pattern1);
                in.read(pattern2);
                fingerData.pattern2 = bytes2String(pattern2);
                in.read(fingerDirection);
                fingerData.fingerDirection = bytes2String(fingerDirection);
                in.read(centerPoint);
                fingerData.centerPoint = bytes2String(centerPoint);
                in.read(subCenterPoint);
                fingerData.subCenterPoint = bytes2String(subCenterPoint);
                in.read(leftTriangle);
                fingerData.leftTriangle = bytes2String(leftTriangle);
                in.read(rightTriangle);
                fingerData.rightTriangle = bytes2String(rightTriangle);
                in.read(featureCount);
                fingerData.featureCount = bytes2String2Int(featureCount);
                in.read(feature);
                fingerData.feature = bytes2String(feature).substring(0, fingerData.featureCount*9);
                in.read(customInfoLength);
                fingerData.customInfoLength = bytes2String2Int(customInfoLength);;
                fingerData.customInfo = new byte[fingerData.customInfoLength];
                in.read(fingerData.customInfo);
                in.read(imgHorizontalLength);
                fingerData.imgHorizontalLength = bytes2String2Int(imgHorizontalLength);
                in.read(imgVerticalLength);
                fingerData.imgVerticalLength = bytes2String2Int(imgVerticalLength);
                in.read(dpi);
                fingerData.dpi = bytes2String2Int(dpi);
                in.read(imgCompressMethod);
                fingerData.imgCompressMethod = bytes2String(imgCompressMethod);
                in.read(imgDataLenght);
                fingerData.imgDataLenght = bytes2String2Int(imgDataLenght);
                fingerData.imgData = new byte[fingerData.imgDataLenght];
                in.read(fingerData.imgData);
                data.fingerDataList.add(fingerData);
//			}while(in.read(end) != -1 ? "1d".equals(ByteUtil.bytes2HexString(end)) : false);//如果是GS组分隔符 继续读取
            }while(in.read(end) != -1 ? 29 == end[0] : false);//1d==29
            return data;
        }
        private static FPTtpData parseFromInputStream4(InputStream in) throws IOException{
            FPTtpData data = new FPTtpData();
            byte[] index = new byte[6];
            byte[] systemType = new byte[4];
            byte[] personId = new byte[23];
            byte[] cardId = new byte[23];
            byte[] personName = new byte[30];
            byte[] alias = new byte[30];
            byte[] gender = new byte[1];
            byte[] birthday = new byte[8];
            byte[] nativeplace = new byte[3];
            byte[] nation = new byte[2];
            byte[] idCardNo = new byte[18];
            byte[] certificateType = new byte[3];
            byte[] certificateNo = new byte[20];
            byte[] door = new byte[6];
            byte[] doorDetail = new byte[70];
            byte[] address = new byte[6];
            byte[] addressDetail = new byte[70];
            byte[] category = new byte[2];
            byte[] caseClass1Code = new byte[6];
            byte[] caseClass2Code = new byte[6];
            byte[] caseClass3Code = new byte[6];
            byte[] isCriminal = new byte[1];
            byte[] criminalInfo = new byte[1024];
            byte[] gatherUnitCode = new byte[12];
            byte[] gatherUnitName = new byte[70];
            byte[] gatherName = new byte[30];
            byte[] gatherDate = new byte[8];
            byte[] assistLevel = new byte[1];
            byte[] bonus = new byte[6];
            byte[] assistPurpose = new byte[5];
            byte[] relatedPersonId = new byte[23];
            byte[] relatedCaseId = new byte[23];
            byte[] assistTimeLimit = new byte[1];
            byte[] assistAskingInfo = new byte[512];
            byte[] assistUnitCode = new byte[12];
            byte[] assistUnitName = new byte[70];
            byte[] assistDate = new byte[8];
            byte[] contact = new byte[30];
            byte[] contactPhone = new byte[40];
            byte[] approver = new byte[30];
            byte[] remark = new byte[512];
            byte[] isAssist = new byte[1];
            byte[] portraitDataLength = new byte[6];
//			byte[] portraitData;
            byte[] sendFingerCount = new byte[2];
            byte[] dataLength = new byte[7];
            byte[] sendNo = new byte[2];
            byte[] fgp = new byte[2];
            byte[] extractMethod = new byte[1];
            byte[] pattern1 = new byte[1];
            byte[] pattern2 = new byte[1];
            byte[] fingerDirection = new byte[5];
            byte[] centerPoint = new byte[14];
            byte[] subCenterPoint = new byte[14];
            byte[] leftTriangle = new byte[14];
            byte[] rightTriangle = new byte[14];
            byte[] featureCount = new byte[3];
            byte[] feature = new byte[1800];
            byte[] customInfoLength = new byte[6];
//			byte[] customInfo;
            byte[] imgHorizontalLength = new byte[3];
            byte[] imgVerticalLength = new byte[3];
            byte[] dpi = new byte[3];
            byte[] imgCompressMethod = new byte[4];
            byte[] imgDataLenght = new byte[6];
//			byte[] imgData;
            byte[] end = new byte[1];
            in.read(index);
            data.index = bytes2String2Int(index);
            in.read(systemType);
            data.systemType = bytes2String(systemType);
            in.read(personId);
            data.personId = bytes2String(personId);
            in.read(cardId);
            data.cardId = bytes2String(cardId);
            in.read(personName);
            data.personName = bytes2String(personName);
            in.read(alias);
            data.alias = bytes2String(alias);
            in.read(gender);
            data.gender = bytes2String(gender);
            in.read(birthday);
            data.birthday = bytes2String(birthday);
            in.read(nativeplace);
            data.nativeplace = bytes2String(nativeplace);
            in.read(nation);
            data.nation = bytes2String(nation);
            in.read(idCardNo);
            data.idCardNo = bytes2String(idCardNo);
            in.read(certificateType);
            data.certificateType = bytes2String(certificateType);
            in.read(certificateNo);
            data.certificateNo = bytes2String(certificateNo);
            in.read(door);
            data.door = bytes2String(door);
            in.read(doorDetail);
            data.doorDetail = bytes2String(doorDetail);
            in.read(address);
            data.address = bytes2String(address);
            in.read(addressDetail);
            data.addressDetail = bytes2String(addressDetail);
            in.read(category);
            data.category = bytes2String(category);
            in.read(caseClass1Code);
            data.caseClass1Code = bytes2String(caseClass1Code);
            in.read(caseClass2Code);
            data.caseClass2Code = bytes2String(caseClass2Code);
            in.read(caseClass3Code);
            data.caseClass3Code = bytes2String(caseClass3Code);
            in.read(isCriminal);
            data.isCriminal = bytes2String(isCriminal);
            in.read(criminalInfo);
            data.criminalInfo = bytes2String(criminalInfo);
            in.read(gatherUnitCode);
            data.gatherUnitCode = bytes2String(gatherUnitCode);
            in.read(gatherUnitName);
            data.gatherUnitName = bytes2String(gatherUnitName);
            in.read(gatherName);
            data.gatherName = bytes2String(gatherName);
            in.read(gatherDate);
            data.gatherDate = bytes2String(gatherDate);
            in.read(assistLevel);
            data.assistLevel = bytes2String(assistLevel);
            in.read(bonus);
            data.bonus = bytes2String(bonus);
            in.read(assistPurpose);
            data.assistPurpose = bytes2String(assistPurpose);
            in.read(relatedPersonId);
            data.relatedPersonId = bytes2String(relatedPersonId);
            in.read(relatedCaseId);
            data.relatedCaseId = bytes2String(relatedCaseId);
            in.read(assistTimeLimit);
            data.assistTimeLimit = bytes2String(assistTimeLimit);
            in.read(assistAskingInfo);
            data.assistAskingInfo = bytes2String(assistAskingInfo);
            in.read(assistUnitCode);
            data.assistUnitCode = bytes2String(assistUnitCode);
            in.read(assistUnitName);
            data.assistUnitName = bytes2String(assistUnitName);
            in.read(assistDate);
            data.assistDate = bytes2String(assistDate);
            in.read(contact);
            data.contact = bytes2String(contact);
            in.read(contactPhone);
            data.contactPhone = bytes2String(contactPhone);
            in.read(approver);
            data.approver = bytes2String(approver);
            in.read(remark);
            data.remark = bytes2String(remark);
            in.read(isAssist);
            data.isAssist = bytes2String(isAssist);
            in.read(portraitDataLength);
            data.portraitDataLength = bytes2String2Int(portraitDataLength);
            data.portraitData = new byte[data.portraitDataLength];
            in.read(data.portraitData);
            in.read(sendFingerCount);
            data.sendFingerCount = bytes2String2Int(sendFingerCount);
            do{
                if(data.sendFingerCount <= 0){//如果没有指纹数据
                    in.read(end);//读取文件分隔符FS
                    break;
                }
                FingerData fingerData = new FingerData();
                in.read(dataLength);
                fingerData.dataLength = bytes2String2Int(dataLength);
                in.read(sendNo);
                fingerData.sendNo = bytes2String(sendNo);
                in.read(fgp);
                fingerData.fgp = bytes2String(fgp);
                in.read(extractMethod);
                fingerData.extractMethod = bytes2String(extractMethod);
                in.read(pattern1);
                fingerData.pattern1 = bytes2String(pattern1);
                in.read(pattern2);
                fingerData.pattern2 = bytes2String(pattern2);
                in.read(fingerDirection);
                fingerData.fingerDirection = bytes2String(fingerDirection);
                in.read(centerPoint);
                fingerData.centerPoint = bytes2String(centerPoint);
                in.read(subCenterPoint);
                fingerData.subCenterPoint = bytes2String(subCenterPoint);
                in.read(leftTriangle);
                fingerData.leftTriangle = bytes2String(leftTriangle);
                in.read(rightTriangle);
                fingerData.rightTriangle = bytes2String(rightTriangle);
                in.read(featureCount);
                fingerData.featureCount = bytes2String2Int(featureCount);
                in.read(feature);
                fingerData.feature = bytes2String(feature).substring(0, fingerData.featureCount*9);
                in.read(customInfoLength);
                fingerData.customInfoLength = bytes2String2Int(customInfoLength);;
                fingerData.customInfo = new byte[fingerData.customInfoLength];
                in.read(fingerData.customInfo);
                in.read(imgHorizontalLength);
                fingerData.imgHorizontalLength = bytes2String2Int(imgHorizontalLength);
                in.read(imgVerticalLength);
                fingerData.imgVerticalLength = bytes2String2Int(imgVerticalLength);
                in.read(dpi);
                fingerData.dpi = bytes2String2Int(dpi);
                in.read(imgCompressMethod);
                fingerData.imgCompressMethod = bytes2String(imgCompressMethod);
                in.read(imgDataLenght);
                fingerData.imgDataLenght = bytes2String2Int(imgDataLenght);
                fingerData.imgData = new byte[fingerData.imgDataLenght];
                in.read(fingerData.imgData);
                data.fingerDataList.add(fingerData);
//			}while(in.read(end) != -1 ? "1d".equals(ByteUtil.bytes2HexString(end)) : false);//如果是GS组分隔符 继续读取
            }while(in.read(end) != -1 ? 29 == end[0] : false);//1d==29
            return data;
        }
        /**
         * 转为byte[]
         */
        private byte[] toByteArray() throws Exception{
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            this.systemType = "1900";//系统类型1900
            this.dataType = "02";
            out.write(string2Bytes(this.dataType, 2));
            out.write(int2String2Bytes(this.index, 6));
            out.write(string2Bytes(this.systemType, 4));
            out.write(string2Bytes(this.personId, 23));
            out.write(string2Bytes(this.cardId, 23));
            out.write(string2Bytes(this.personName, 30));
            out.write(string2Bytes(this.alias, 30));
            out.write(string2Bytes(this.gender, 1));
            out.write(string2Bytes(this.birthday, 8));
            out.write(string2Bytes(this.nativeplace, 3));
            out.write(string2Bytes(this.nation, 2));
            out.write(string2Bytes(this.idCardNo, 18));
            out.write(string2Bytes(this.certificateType, 3));
            out.write(string2Bytes(this.certificateNo, 20));
            out.write(string2Bytes(this.door, 6));
            out.write(string2Bytes(this.doorDetail, 70));
            out.write(string2Bytes(this.address, 6));
            out.write(string2Bytes(this.addressDetail, 70));
            out.write(string2Bytes(this.category, 2));
            out.write(string2Bytes(this.caseClass1Code, 6));
            out.write(string2Bytes(this.caseClass2Code, 6));
            out.write(string2Bytes(this.caseClass3Code, 6));
            out.write(string2Bytes(this.isCriminal, 1));
            out.write(string2Bytes(this.criminalInfo, 1024));
            out.write(string2Bytes(this.gatherUnitCode, 12));
            out.write(string2Bytes(this.gatherUnitName, 70));
            out.write(string2Bytes(this.gatherName, 30));
            out.write(string2Bytes(this.gatherDate, 8));
            out.write(string2Bytes(this.assistLevel, 1));
            out.write(string2Bytes(this.bonus, 6));
            out.write(string2Bytes(this.assistPurpose, 5));
            out.write(string2Bytes(this.relatedPersonId, 23));
            out.write(string2Bytes(this.relatedCaseId, 23));
            out.write(string2Bytes(this.assistTimeLimit, 1));
            out.write(string2Bytes(this.assistAskingInfo, 512));
            out.write(string2Bytes(this.assistUnitCode, 12));
            out.write(string2Bytes(this.assistUnitName, 70));
            out.write(string2Bytes(this.assistDate, 8));
            out.write(string2Bytes(this.contact, 30));
            out.write(string2Bytes(this.contactPhone, 40));
            out.write(string2Bytes(this.approver, 30));
            out.write(string2Bytes(this.remark, 512));
            out.write(string2Bytes(this.isAssist, 1));
            this.portraitDataLength = this.portraitData != null ? this.portraitData.length : 0;
            out.write(int2String2Bytes(this.portraitDataLength, 6));
            if(this.portraitDataLength > 0){
                out.write(this.portraitData);
            }
            this.sendFingerCount = this.fingerDataList.size();
            out.write(int2String2Bytes(this.sendFingerCount, 2));
            if(this.sendFingerCount > 0){//如果有发送指纹信息，循环写入数据，否则写入文件分隔符FS
                for (int i = 0; i < this.fingerDataList.size(); i++) {
                    ByteArrayOutputStream out_ = new ByteArrayOutputStream();
                    FPTtpData.FingerData fingerData = this.fingerDataList.get(i);
                    out_.write(string2Bytes(fingerData.sendNo, 2));
                    out_.write(string2Bytes(fingerData.fgp, 2));
                    out_.write(string2Bytes(fingerData.extractMethod, 1));
                    out_.write(string2Bytes(fingerData.pattern1, 1));
                    out_.write(string2Bytes(fingerData.pattern2, 1));
                    out_.write(string2BytesAndNullIsSP(fingerData.fingerDirection, 5));
                    out_.write(string2BytesAndNullIsSP(fingerData.centerPoint, 14));
                    out_.write(string2BytesAndNullIsSP(fingerData.subCenterPoint, 14));
                    out_.write(string2BytesAndNullIsSP(fingerData.leftTriangle, 14));
                    out_.write(string2BytesAndNullIsSP(fingerData.rightTriangle, 14));
                    out_.write(int2String2Bytes(fingerData.feature != null ? fingerData.feature.length()/9 : 0, 3));
                    out_.write(string2BytesAndNullIsSP(fingerData.feature, 1800));
                    out_.write(int2String2Bytes(0, 6));//自定义信息，无
                    out_.write(int2String2Bytes(fingerData.imgHorizontalLength, 3));
                    out_.write(int2String2Bytes(fingerData.imgVerticalLength, 3));
                    out_.write(int2String2Bytes(fingerData.dpi, 3));
                    out_.write(string2Bytes(fingerData.imgCompressMethod, 4));
                    if(fingerData.imgData != null){//判断指纹数据是否为空
                        fingerData.imgDataLenght = fingerData.imgData.length;
                        out_.write(int2String2Bytes(fingerData.imgDataLenght, 6));
                        out_.write(fingerData.imgData);
                    }else {
                        out_.write(int2String2Bytes(0, 6));
                    }
                    //计算本指纹信息总长度
                    byte[] data = out_.toByteArray();
                    out.write(int2String2Bytes(data.length+7, 7));
                    out.write(data);
                    //若本记录还有其他信息，用字段分隔符GS，跳回到201数据项继续发送下一枚指纹信息；若本记录结束，用文件分隔符FS，采用GB/T 1988-1998
                    if(i == this.fingerDataList.size()-1){
                        out.write((byte)28);
                    }else{
                        out.write((byte)29);
                    }
                }
            }else{
                out.write((byte)28);
            }
            //计算本逻辑记录长度
            byte[] data = out.toByteArray();
            result.write(int2String2Bytes(data.length+8, 8));
            result.write(data);
            return result.toByteArray();
        }
        /**
         * 指纹数据
         */
        public static class FingerData{
            private int dataLength;//指纹信息长度
            private String sendNo;//发送序号
            private String fgp;//指位
            private String extractMethod;//提取特征方法
            private String pattern1;//指纹纹型分类1
            private String pattern2;//指纹纹型分类1
            private String fingerDirection;//指纹方向
            private String centerPoint;//中心点
            private String subCenterPoint;//副中心
            private String leftTriangle;//左三角
            private String rightTriangle;//右三角
            private int featureCount;//特征个数
            private String feature;//特征点
            private int customInfoLength;//自定义信息长度
            private byte[] customInfo;// 自定义信息
            private int imgHorizontalLength;//图像水平方向长度
            private int imgVerticalLength;//图像垂直方向长度
            private int dpi;//图像分辨率
            private String imgCompressMethod;//图像压缩方法
            private int imgDataLenght;//图像长度
            private byte[] imgData;//图像数据

            public int getDataLength() {
                return dataLength;
            }
            public void setDataLength(int dataLength) {
                this.dataLength = dataLength;
            }
            public String getSendNo() {
                return sendNo;
            }
            public void setSendNo(String sendNo) {
                this.sendNo = sendNo;
            }
            public String getFgp() {
                return fgp;
            }
            public void setFgp(String fgp) {
                this.fgp = fgp;
            }
            public String getExtractMethod() {
                return extractMethod;
            }
            public void setExtractMethod(String extractMethod) {
                this.extractMethod = extractMethod;
            }
            public String getPattern1() {
                return pattern1;
            }
            public void setPattern1(String pattern1) {
                this.pattern1 = pattern1;
            }
            public String getPattern2() {
                return pattern2;
            }
            public void setPattern2(String pattern2) {
                this.pattern2 = pattern2;
            }
            public String getFingerDirection() {
                return fingerDirection;
            }
            public void setFingerDirection(String fingerDirection) {
                this.fingerDirection = fingerDirection;
            }
            public String getCenterPoint() {
                return centerPoint;
            }
            public void setCenterPoint(String centerPoint) {
                this.centerPoint = centerPoint;
            }
            public String getSubCenterPoint() {
                return subCenterPoint;
            }
            public void setSubCenterPoint(String subCenterPoint) {
                this.subCenterPoint = subCenterPoint;
            }
            public String getLeftTriangle() {
                return leftTriangle;
            }
            public void setLeftTriangle(String leftTriangle) {
                this.leftTriangle = leftTriangle;
            }
            public String getRightTriangle() {
                return rightTriangle;
            }
            public void setRightTriangle(String rightTriangle) {
                this.rightTriangle = rightTriangle;
            }
            public int getFeatureCount() {
                return featureCount;
            }
            public void setFeatureCount(int featureCount) {
                this.featureCount = featureCount;
            }
            public String getFeature() {
                return feature;
            }
            public void setFeature(String feature) {
                this.feature = feature;
            }
            public int getCustomInfoLength() {
                return customInfoLength;
            }
            public void setCustomInfoLength(int customInfoLength) {
                this.customInfoLength = customInfoLength;
            }
            public byte[] getCustomInfo() {
                return customInfo;
            }
            public void setCustomInfo(byte[] customInfo) {
                this.customInfo = customInfo;
            }
            public int getImgHorizontalLength() {
                return imgHorizontalLength;
            }
            public void setImgHorizontalLength(int imgHorizontalLength) {
                this.imgHorizontalLength = imgHorizontalLength;
            }
            public int getImgVerticalLength() {
                return imgVerticalLength;
            }
            public void setImgVerticalLength(int imgVerticalLength) {
                this.imgVerticalLength = imgVerticalLength;
            }
            public int getDpi() {
                return dpi;
            }
            public void setDpi(int dpi) {
                this.dpi = dpi;
            }
            public String getImgCompressMethod() {
                return imgCompressMethod;
            }
            public void setImgCompressMethod(String imgCompressMethod) {
                this.imgCompressMethod = imgCompressMethod;
            }
            public int getImgDataLenght() {
                return imgDataLenght;
            }
            public void setImgDataLenght(int imgDataLenght) {
                this.imgDataLenght = imgDataLenght;
            }
            public byte[] getImgData() {
                return imgData;
            }
            public void setImgData(byte[] imgData) {
                this.imgData = imgData;
            }
        }
    }
    /**
     * FPT发送现场指纹数据
     */
    public static class FPTlpData {
        private int length;//本逻辑记录长度
        private String dataType;//记录类型
        private int index;//序号
        private String systemType;//系统类型
        private String caseId;//案件编号
        private String cardId;//现场卡号
        private String caseClass1Code;//案件类别1
        private String caseClass2Code;//案件类别2
        private String caseClass3Code;//案件类别3
        private String occurDate;//发案日期
        private String assistLevel;//协查级别
        private String occurPlace;//发案地点
        private String extractUnitCode;//提取单位代码
        private String extractUnitName;//提取单位名称
        private String extractor;//提取人
        private String suspiciousArea1Code;//可疑地区1
        private String suspiciousArea2Code;//可疑地区2
        private String suspiciousArea3Code;//可疑地区3
        private String amount;//涉案金额
        private String remark;//备注fpt3
        private int fingerCount;//本案现场指纹个数
        private int sendFingerCount;//发送现场指纹个数
        /* 以下为4的数据*/
        private String caseBriefDetail;//简要案情fpt4
        private String occurPlaceCode;//发案代码
        private String isMurder;//命案标识
        private String bonus;//奖金
        private String extractDate;//提取日期
        private String assistUnitCode;//协查单位代码
        private String assistUnitName;//协查单位名称
        private String assistDate;//协查日期
        private String isAssist;//案件协查标识
        private String isRevoke;//部撤销标识
        private String caseStatus;//案件状态
        private List<FingerData> fingerDataList = new ArrayList<FPTObject.FPTlpData.FingerData>();//指纹数据

        public int getLength() {
            return length;
        }
        public void setLength(int length) {
            this.length = length;
        }
        public String getDataType() {
            return dataType;
        }
        public void setDataType(String dataType) {
            this.dataType = dataType;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
        public String getSystemType() {
            return systemType;
        }
        public void setSystemType(String systemType) {
            this.systemType = systemType;
        }
        public String getCaseId() {
            return caseId;
        }
        public void setCaseId(String caseId) {
            this.caseId = caseId;
        }
        public String getCardId() {
            return cardId;
        }
        public void setCardId(String cardId) {
            this.cardId = cardId;
        }
        public String getCaseClass1Code() {
            return caseClass1Code;
        }
        public void setCaseClass1Code(String caseClass1Code) {
            this.caseClass1Code = caseClass1Code;
        }
        public String getCaseClass2Code() {
            return caseClass2Code;
        }
        public void setCaseClass2Code(String caseClass2Code) {
            this.caseClass2Code = caseClass2Code;
        }
        public String getCaseClass3Code() {
            return caseClass3Code;
        }
        public void setCaseClass3Code(String caseClass3Code) {
            this.caseClass3Code = caseClass3Code;
        }
        public String getOccurDate() {
            return occurDate;
        }
        public void setOccurDate(String occurDate) {
            this.occurDate = occurDate;
        }
        public String getAssistLevel() {
            return assistLevel;
        }
        public void setAssistLevel(String assistLevel) {
            this.assistLevel = assistLevel;
        }
        public String getOccurPlace() {
            return occurPlace;
        }
        public void setOccurPlace(String occurPlace) {
            this.occurPlace = occurPlace;
        }
        public String getExtractUnitCode() {
            return extractUnitCode;
        }
        public void setExtractUnitCode(String extractUnitCode) {
            this.extractUnitCode = extractUnitCode;
        }
        public String getExtractUnitName() {
            return extractUnitName;
        }
        public void setExtractUnitName(String extractUnitName) {
            this.extractUnitName = extractUnitName;
        }
        public String getExtractor() {
            return extractor;
        }
        public void setExtractor(String extractor) {
            this.extractor = extractor;
        }
        public String getSuspiciousArea1Code() {
            return suspiciousArea1Code;
        }
        public void setSuspiciousArea1Code(String suspiciousArea1Code) {
            this.suspiciousArea1Code = suspiciousArea1Code;
        }
        public String getSuspiciousArea2Code() {
            return suspiciousArea2Code;
        }
        public void setSuspiciousArea2Code(String suspiciousArea2Code) {
            this.suspiciousArea2Code = suspiciousArea2Code;
        }
        public String getSuspiciousArea3Code() {
            return suspiciousArea3Code;
        }
        public void setSuspiciousArea3Code(String suspiciousArea3Code) {
            this.suspiciousArea3Code = suspiciousArea3Code;
        }
        public String getAmount() {
            return amount;
        }
        public void setAmount(String amount) {
            this.amount = amount;
        }
        public String getOccurPlaceCode() {
            return occurPlaceCode;
        }
        public void setOccurPlaceCode(String occurPlaceCode) {
            this.occurPlaceCode = occurPlaceCode;
        }
        public String getIsMurder() {
            return isMurder;
        }
        public void setIsMurder(String isMurder) {
            this.isMurder = isMurder;
        }
        public String getBonus() {
            return bonus;
        }
        public void setBonus(String bonus) {
            this.bonus = bonus;
        }
        public String getExtractDate() {
            return extractDate;
        }
        public void setExtractDate(String extractDate) {
            this.extractDate = extractDate;
        }
        public String getAssistUnitCode() {
            return assistUnitCode;
        }
        public void setAssistUnitCode(String assistUnitCode) {
            this.assistUnitCode = assistUnitCode;
        }
        public String getAssistUnitName() {
            return assistUnitName;
        }
        public void setAssistUnitName(String assistUnitName) {
            this.assistUnitName = assistUnitName;
        }
        public String getAssistDate() {
            return assistDate;
        }
        public void setAssistDate(String assistDate) {
            this.assistDate = assistDate;
        }
        public String getIsAssist() {
            return isAssist;
        }
        public void setIsAssist(String isAssist) {
            this.isAssist = isAssist;
        }
        public String getIsRevoke() {
            return isRevoke;
        }
        public void setIsRevoke(String isRevoke) {
            this.isRevoke = isRevoke;
        }
        public String getCaseStatus() {
            return caseStatus;
        }
        public void setCaseStatus(String caseStatus) {
            this.caseStatus = caseStatus;
        }
        public String getRemark() {
            return remark;
        }
        public void setRemark(String remark) {
            this.remark = remark;
        }
        public int getFingerCount() {
            return fingerCount;
        }
        public void setFingerCount(int fingerCount) {
            this.fingerCount = fingerCount;
        }
        public int getSendFingerCount() {
            return sendFingerCount;
        }
        public void setSendFingerCount(int sendFingerCount) {
            this.sendFingerCount = sendFingerCount;
        }
        public List<FingerData> getFingerDataList() {
            return fingerDataList;
        }
        public void setFingerDataList(List<FingerData> fingerDataList) {
            this.fingerDataList = fingerDataList;
        }
        public String getCaseBriefDetail() {
            return caseBriefDetail;
        }
        public void setCaseBriefDetail(String caseBriefDetail) {
            this.caseBriefDetail = caseBriefDetail;
        }
        private static FPTlpData parseFromInputStream3(InputStream in) throws IOException{
            FPTlpData data = new FPTlpData();
            byte[] index = new byte[6];
            byte[] systemType = new byte[4];
            byte[] caseId = new byte[23];
            byte[] cardId = new byte[20];
            byte[] caseClass1Code = new byte[6];
            byte[] caseClass2Code = new byte[6];
            byte[] caseClass3Code = new byte[6];
            byte[] occurDate = new byte[8];
            byte[] assistLevel = new byte[1];
            byte[] occurPlace = new byte[70];
            byte[] extractUnitCode = new byte[12];
            byte[] extractUnitName = new byte[70];
            byte[] extractor = new byte[30];
            byte[] suspiciousArea1Code = new byte[6];
            byte[] suspiciousArea2Code = new byte[6];
            byte[] suspiciousArea3Code = new byte[6];
            byte[] amount = new byte[10];
            byte[] remark = new byte[512];
            byte[] fingerCount = new byte[2];
            byte[] sendFingerCount = new byte[2];

            byte[] dataLength = new byte[6];
            byte[] sendNo = new byte[2];
            byte[] fingerNo = new byte[2];
            byte[] fingerId = new byte[20];
            byte[] remainPlace = new byte[30];
            byte[] fgp = new byte[10];
            byte[] ridgeColor = new byte[1];
            byte[] mittensBegNo = new byte[2];
            byte[] mittensEndNo = new byte[2];
            byte[] extractMethod = new byte[1];
            byte[] pattern = new byte[7];
            byte[] fingerDirection = new byte[5];
            byte[] centerPoint = new byte[14];
            byte[] subCenterPoint = new byte[14];
            byte[] leftTriangle = new byte[14];
            byte[] rightTriangle = new byte[14];
            byte[] featureCount = new byte[3];
            byte[] feature = new byte[1800];
            byte[] customInfoLength = new byte[4];
//			byte[] customInfo;
            byte[] imgHorizontalLength = new byte[3];
            byte[] imgVerticalLength = new byte[3];
            byte[] dpi = new byte[3];
            byte[] imgCompressMethod = new byte[4];
            byte[] imgDataLenght = new byte[6];
//			byte[] imgData;
            byte[] end = new byte[1];

            in.read(index);
            data.index = bytes2String2Int(index);
            in.read(systemType);
            data.systemType = bytes2String(systemType);
            in.read(caseId);
            data.caseId = bytes2String(caseId);
            in.read(cardId);
            data.cardId = bytes2String(cardId);
            in.read(caseClass1Code);
            data.caseClass1Code = bytes2String(caseClass1Code);
            in.read(caseClass2Code);
            data.caseClass2Code = bytes2String(caseClass2Code);
            in.read(caseClass3Code);
            data.caseClass3Code = bytes2String(caseClass3Code);

            in.read(occurDate);
            data.occurDate = bytes2String(occurDate);
            in.read(assistLevel);
            data.assistLevel = bytes2String(assistLevel);
            in.read(occurPlace);
            data.occurPlace = bytes2String(occurPlace);
            in.read(extractUnitCode);
            data.extractUnitCode = bytes2String(extractUnitCode);
            in.read(extractUnitName);
            data.extractUnitName = bytes2String(extractUnitName);
            in.read(extractor);
            data.extractor = bytes2String(extractor);
            in.read(suspiciousArea1Code);
            data.suspiciousArea1Code = bytes2String(suspiciousArea1Code);
            in.read(suspiciousArea2Code);
            data.suspiciousArea2Code = bytes2String(suspiciousArea2Code);
            in.read(suspiciousArea3Code);
            data.suspiciousArea3Code = bytes2String(suspiciousArea3Code);
            in.read(amount);
            data.amount = bytes2String(amount);
            in.read(remark);
            data.remark = bytes2String(remark);
            in.read(fingerCount);
            data.fingerCount = bytes2String2Int(fingerCount);
            in.read(sendFingerCount);
            data.sendFingerCount = bytes2String2Int(sendFingerCount);
            do{
                if(data.sendFingerCount <= 0){//如果没有指纹数据
                    in.read(end);//读取文件分隔符FS
                    break;
                }
                FingerData fingerData = new FingerData();
                in.read(dataLength);
                fingerData.dataLength = bytes2String2Int(dataLength);
                in.read(sendNo);
                fingerData.sendNo = bytes2String(sendNo);
                in.read(fingerNo);
                fingerData.fingerNo = bytes2String(fingerNo);
                in.read(fingerId);
                fingerData.fingerId = bytes2String(fingerId);
                in.read(remainPlace);
                fingerData.remainPlace = bytes2String(remainPlace);
                in.read(fgp);
                fingerData.fgp = bytes2String(fgp);
                in.read(ridgeColor);
                fingerData.ridgeColor = bytes2String(ridgeColor);
                in.read(mittensBegNo);
                fingerData.mittensBegNo = bytes2String(mittensBegNo);
                in.read(mittensEndNo);
                fingerData.mittensEndNo = bytes2String(mittensEndNo);
                in.read(extractMethod);
                fingerData.extractMethod = bytes2String(extractMethod);
                in.read(pattern);
                fingerData.pattern = bytes2String(pattern);
                in.read(fingerDirection);
                fingerData.fingerDirection = bytes2String(fingerDirection);
                in.read(centerPoint);
                fingerData.centerPoint = bytes2String(centerPoint);
                in.read(subCenterPoint);
                fingerData.subCenterPoint = bytes2String(subCenterPoint);
                in.read(leftTriangle);
                fingerData.leftTriangle = bytes2String(leftTriangle);
                in.read(rightTriangle);
                fingerData.rightTriangle = bytes2String(rightTriangle);
                in.read(featureCount);
                fingerData.featureCount = bytes2String2Int(featureCount);
                in.read(feature);
                fingerData.feature = bytes2String(feature).substring(0, fingerData.featureCount*9);
                in.read(customInfoLength);
                customInfoLength.equals(new byte[4]);
                fingerData.customInfoLength = bytes2String2Int(customInfoLength);;
                fingerData.customInfo = new byte[fingerData.customInfoLength];
                in.read(fingerData.customInfo);
                in.read(imgHorizontalLength);
                fingerData.imgHorizontalLength = bytes2String2Int(imgHorizontalLength);
                in.read(imgVerticalLength);
                fingerData.imgVerticalLength = bytes2String2Int(imgVerticalLength);
                in.read(dpi);
                fingerData.dpi = bytes2String2Int(dpi);
                in.read(imgCompressMethod);
                fingerData.imgCompressMethod = bytes2String(imgCompressMethod);
                in.read(imgDataLenght);
                fingerData.imgDataLenght = bytes2String2Int(imgDataLenght);
                fingerData.imgData = new byte[fingerData.imgDataLenght];
                in.read(fingerData.imgData);

                data.fingerDataList.add(fingerData);
//			}while(in.read(end) != -1 ? "1d".equals(ByteUtil.bytes2HexString(end))  : false);//如果是GS组分隔符 继续读取
            }while(in.read(end) != -1 ? 29 == end[0] : false);//1d==29
            return data;
        }
        private static FPTlpData parseFromInputStream4(InputStream in) throws IOException{
            FPTlpData data = new FPTlpData();
            byte[] index = new byte[6];
            byte[] systemType = new byte[4];
            byte[] caseId = new byte[23];
            byte[] cardId = new byte[23];
            byte[] caseClass1Code = new byte[6];
            byte[] caseClass2Code = new byte[6];
            byte[] caseClass3Code = new byte[6];
            byte[] occurDate = new byte[8];
            byte[] occurPlaceCode = new byte[6];
            byte[] occurPlace = new byte[70];
            byte[] caseBriefDetail = new byte[512];
            byte[] isMurder = new byte[1];
            byte[] amount = new byte[10];
            byte[] extractUnitCode = new byte[12];
            byte[] extractUnitName = new byte[70];
            byte[] extractDate = new byte[8];
            byte[] extractor = new byte[30];
            byte[] suspiciousArea1Code = new byte[6];
            byte[] suspiciousArea2Code = new byte[6];
            byte[] suspiciousArea3Code = new byte[6];
            byte[] assistLevel = new byte[1];
            byte[] bonus = new byte[6];
            byte[] assistUnitCode = new byte[12];
            byte[] assistUnitName = new byte[70];
            byte[] assistDate = new byte[8];
            byte[] isCaseAssist = new byte[1];
            byte[] isRevoke = new byte[1];
            byte[] caseStatus = new byte[1];
            byte[] fingerCount = new byte[2];
            byte[] sendFingerCount = new byte[2];

            byte[] dataLength = new byte[7];
            byte[] sendNo = new byte[2];
            byte[] fingerNo = new byte[2];
            byte[] fingerId = new byte[25];
            byte[] isCorpse = new byte[1];
            byte[] corpseNo = new byte[23];
            byte[] remainPlace = new byte[30];
            byte[] fgp = new byte[10];
            byte[] ridgeColor = new byte[1];
            byte[] mittensBegNo = new byte[2];
            byte[] mittensEndNo = new byte[2];
            byte[] isFingerAssist = new byte[1];
            byte[] matchStatus = new byte[1];
            byte[] extractMethod = new byte[1];
            byte[] pattern = new byte[7];
            byte[] fingerDirection = new byte[5];
            byte[] centerPoint = new byte[14];
            byte[] subCenterPoint = new byte[14];
            byte[] leftTriangle = new byte[14];
            byte[] rightTriangle = new byte[14];
            byte[] featureCount = new byte[3];
            byte[] feature = new byte[1800];
            byte[] customInfoLength = new byte[6];
//			byte[] customInfo;
            byte[] imgHorizontalLength = new byte[3];
            byte[] imgVerticalLength = new byte[3];
            byte[] dpi = new byte[3];
            byte[] imgCompressMethod = new byte[4];
            byte[] imgDataLenght = new byte[6];
//			byte[] imgData;
            byte[] end = new byte[1];

            in.read(index);
            data.index = bytes2String2Int(index);
            in.read(systemType);
            data.systemType = bytes2String(systemType);
            in.read(caseId);
            data.caseId = bytes2String(caseId);
            in.read(cardId);
            data.cardId = bytes2String(cardId);
            in.read(caseClass1Code);
            data.caseClass1Code = bytes2String(caseClass1Code);
            in.read(caseClass2Code);
            data.caseClass2Code = bytes2String(caseClass2Code);
            in.read(caseClass3Code);
            data.caseClass3Code = bytes2String(caseClass3Code);
            in.read(occurDate);
            data.occurDate = bytes2String(occurDate);

            in.read(occurPlaceCode);
            data.occurPlaceCode = bytes2String(occurPlaceCode);
            in.read(occurPlace);
            data.occurPlace = bytes2String(occurPlace);
            in.read(caseBriefDetail);
            data.caseBriefDetail = bytes2String(caseBriefDetail);
            in.read(isMurder);
            data.isMurder = bytes2String(isMurder);
            in.read(amount);
            data.amount = bytes2String(amount);
            in.read(extractUnitCode);
            data.extractUnitCode = bytes2String(extractUnitCode);
            in.read(extractUnitName);
            data.extractUnitName = bytes2String(extractUnitName);
            in.read(extractDate);
            data.extractDate = bytes2String(extractDate);
            in.read(extractor);
            data.extractor = bytes2String(extractor);
            in.read(suspiciousArea1Code);
            data.suspiciousArea1Code = bytes2String(suspiciousArea1Code);
            in.read(suspiciousArea2Code);
            data.suspiciousArea2Code = bytes2String(suspiciousArea2Code);
            in.read(suspiciousArea3Code);
            data.suspiciousArea3Code = bytes2String(suspiciousArea3Code);
            in.read(assistLevel);
            data.assistLevel = bytes2String(assistLevel);
            in.read(bonus);
            data.bonus = bytes2String(bonus);

            in.read(assistUnitCode);
            data.assistUnitCode = bytes2String(assistUnitCode);
            in.read(assistUnitName);
            data.assistUnitName = bytes2String(assistUnitName);
            in.read(assistDate);
            data.assistDate = bytes2String(assistDate);
            in.read(isCaseAssist);
            data.isAssist = bytes2String(isCaseAssist);
            in.read(isRevoke);
            data.isRevoke = bytes2String(isRevoke);
            in.read(caseStatus);
            data.caseStatus = bytes2String(caseStatus);
            in.read(fingerCount);
            data.fingerCount = bytes2String2Int(fingerCount);
            in.read(sendFingerCount);
            data.sendFingerCount = bytes2String2Int(sendFingerCount);
            do{
                if(data.sendFingerCount <= 0){//如果没有指纹数据
                    in.read(end);//读取文件分隔符FS
                    break;
                }
                FingerData fingerData = new FingerData();
                in.read(dataLength);
                fingerData.dataLength = bytes2String2Int(dataLength);
                in.read(sendNo);
                fingerData.sendNo = bytes2String(sendNo);
                in.read(fingerNo);
                fingerData.fingerNo = bytes2String(fingerNo);
                in.read(fingerId);
                fingerData.fingerId = bytes2String(fingerId);
                in.read(isCorpse);
                fingerData.isCorpse = bytes2String(isCorpse);
                in.read(corpseNo);
                fingerData.corpseNo = bytes2String(corpseNo);
                in.read(remainPlace);
                fingerData.remainPlace = bytes2String(remainPlace);
                in.read(fgp);
                fingerData.fgp = bytes2String(fgp);
                in.read(ridgeColor);
                fingerData.ridgeColor = bytes2String(ridgeColor);
                in.read(mittensBegNo);
                fingerData.mittensBegNo = bytes2String(mittensBegNo);
                in.read(mittensEndNo);
                fingerData.mittensEndNo = bytes2String(mittensEndNo);
                in.read(isFingerAssist);
                fingerData.isAssist = bytes2String(isFingerAssist);
                in.read(matchStatus);
                fingerData.matchStatus = bytes2String(matchStatus);
                in.read(extractMethod);
                fingerData.extractMethod = bytes2String(extractMethod);
                in.read(pattern);
                fingerData.pattern = bytes2String(pattern);
                in.read(fingerDirection);
                fingerData.fingerDirection = bytes2String(fingerDirection);
                in.read(centerPoint);
                fingerData.centerPoint = bytes2String(centerPoint);
                in.read(subCenterPoint);
                fingerData.subCenterPoint = bytes2String(subCenterPoint);
                in.read(leftTriangle);
                fingerData.leftTriangle = bytes2String(leftTriangle);
                in.read(rightTriangle);
                fingerData.rightTriangle = bytes2String(rightTriangle);
                in.read(featureCount);
                fingerData.featureCount = bytes2String2Int(featureCount);
                in.read(feature);
                fingerData.feature = bytes2String(feature).substring(0, fingerData.featureCount*9);
                in.read(customInfoLength);
                customInfoLength.equals(new byte[4]);
                fingerData.customInfoLength = bytes2String2Int(customInfoLength);;
                fingerData.customInfo = new byte[fingerData.customInfoLength];
                in.read(fingerData.customInfo);
                in.read(imgHorizontalLength);
                fingerData.imgHorizontalLength = bytes2String2Int(imgHorizontalLength);
                in.read(imgVerticalLength);
                fingerData.imgVerticalLength = bytes2String2Int(imgVerticalLength);
                in.read(dpi);
                fingerData.dpi = bytes2String2Int(dpi);
                in.read(imgCompressMethod);
                fingerData.imgCompressMethod = bytes2String(imgCompressMethod);
                in.read(imgDataLenght);
                fingerData.imgDataLenght = bytes2String2Int(imgDataLenght);
                fingerData.imgData = new byte[fingerData.imgDataLenght];
                in.read(fingerData.imgData);

                data.fingerDataList.add(fingerData);
//			}while(in.read(end) != -1 ? "1d".equals(ByteUtil.bytes2HexString(end))  : false);//如果是GS组分隔符 继续读取
            }while(in.read(end) != -1 ? 29 == end[0] : false);//1d==29
            return data;
        }
        /**
         * 转为byte[]
         * @throws Exception
         */
        private byte[] toByteArray() throws Exception{
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            this.systemType = "1900";//系统类型1900
            this.dataType = "03";
            out.write(string2Bytes(this.dataType, 2));
            out.write(string2Bytes(this.index+"", 6));
            out.write(string2Bytes(this.systemType, 4));
            out.write(string2Bytes(this.caseId, 23));
            out.write(string2Bytes(this.cardId, 23));
            out.write(string2Bytes(this.caseClass1Code, 6));
            out.write(string2Bytes(this.caseClass2Code, 6));
            out.write(string2Bytes(this.caseClass3Code, 6));
            out.write(string2Bytes(this.occurDate, 8));
            out.write(string2Bytes(this.occurPlaceCode, 6));
            out.write(string2Bytes(this.occurPlace, 70));
            out.write(string2Bytes(this.caseBriefDetail, 512));
            out.write(string2Bytes(this.isMurder, 1));
            out.write(string2Bytes(this.amount, 10));
            out.write(string2Bytes(this.extractUnitCode, 12));
            out.write(string2Bytes(this.extractUnitName, 70));
            out.write(string2Bytes(this.extractDate, 8));
            out.write(string2Bytes(this.extractor, 30));
            out.write(string2Bytes(this.suspiciousArea1Code, 6));
            out.write(string2Bytes(this.suspiciousArea2Code, 6));
            out.write(string2Bytes(this.suspiciousArea3Code, 6));
            out.write(string2Bytes(this.assistLevel, 1));
            out.write(string2Bytes(this.bonus, 6));
            out.write(string2Bytes(this.assistUnitCode, 12));
            out.write(string2Bytes(this.assistUnitName, 70));
            out.write(string2Bytes(this.assistDate, 8));
            out.write(string2Bytes(this.isAssist, 1));
            out.write(string2Bytes(this.isRevoke, 1));
            out.write(string2Bytes(this.caseStatus, 1));
            this.fingerCount = this.fingerDataList.size();
            out.write(int2String2Bytes(this.fingerCount, 2));
            this.sendFingerCount = this.fingerCount;
            out.write(int2String2Bytes(this.sendFingerCount, 2));
            if(this.sendFingerCount > 0){//如果有发送指纹信息，循环写入指纹数据，否则写入文件分隔符FS
                for (int i = 0; i < this.fingerDataList.size(); i++) {
                    FingerData fingerData = this.fingerDataList.get(i);
                    ByteArrayOutputStream out_ = new ByteArrayOutputStream();
                    out_.write(string2Bytes(fingerData.sendNo, 2));
                    out_.write(string2Bytes(fingerData.fingerNo, 2));
                    out_.write(string2Bytes(fingerData.fingerId, 25));
                    out_.write(string2Bytes(fingerData.isCorpse, 1));
                    out_.write(string2Bytes(fingerData.corpseNo, 23));
                    out_.write(string2Bytes(fingerData.remainPlace, 30));
                    out_.write(string2Bytes(fingerData.fgp, 10));
                    out_.write(string2Bytes(fingerData.ridgeColor, 1));
                    out_.write(string2Bytes(fingerData.mittensBegNo, 2));
                    out_.write(string2Bytes(fingerData.mittensEndNo, 2));
                    out_.write(string2Bytes(fingerData.isAssist, 1));
                    out_.write(string2Bytes(fingerData.matchStatus, 1));
                    out_.write(string2Bytes(fingerData.extractMethod, 1));
                    out_.write(string2Bytes(fingerData.pattern, 7));
                    out_.write(string2BytesAndNullIsSP(fingerData.fingerDirection, 5));
                    out_.write(string2BytesAndNullIsSP(fingerData.centerPoint, 14));
                    out_.write(string2BytesAndNullIsSP(fingerData.subCenterPoint, 14));
                    out_.write(string2BytesAndNullIsSP(fingerData.leftTriangle, 14));
                    out_.write(string2BytesAndNullIsSP(fingerData.rightTriangle, 14));
                    out_.write(int2String2Bytes(fingerData.feature != null ? fingerData.feature.length()/9 : 0, 3));
                    out_.write(string2BytesAndNullIsSP(fingerData.feature, 1800));
                    out_.write(int2String2Bytes(fingerData.customInfoLength, 6));
                    out_.write(int2String2Bytes(fingerData.imgHorizontalLength, 3));
                    out_.write(int2String2Bytes(fingerData.imgVerticalLength, 3));
                    out_.write(int2String2Bytes(fingerData.dpi, 3));
                    out_.write(string2Bytes(fingerData.imgCompressMethod, 4));
                    if(fingerData.imgData != null){//判断指纹数据是否为空
                        fingerData.imgDataLenght = fingerData.imgData.length;
                        out_.write(int2String2Bytes(fingerData.imgDataLenght, 6));
                        out_.write(fingerData.imgData);
                    }else {
                        out_.write(int2String2Bytes(0, 6));
                    }
                    //计算本枚指纹信息总长度
                    byte[] data = out_.toByteArray();
                    out.write(int2String2Bytes(data.length+7, 7));
                    out.write(data);
                    //若本记录还有其他信息，用字段分隔符GS，跳回到201数据项继续发送下一枚指纹信息；若本记录结束，用文件分隔符FS，采用GB/T 1988-1998
                    if(i == this.fingerDataList.size()-1){
                        out.write((byte)28);
                    }else{
                        out.write((byte)29);
                    }
                }
            }else{
                out.write((byte)28);
            }
            //计算本逻辑记录长度
            byte[] data = out.toByteArray();
            result.write(int2String2Bytes(data.length+8, 8));
            result.write(data);
            return result.toByteArray();
        }
        public static class FingerData{
            private int dataLength;//指纹信息长度
            private String sendNo;//发送序号
            private String fingerNo;//现场指纹序号
            private String fingerId;//现场指纹编号
            private String remainPlace;//遗留部位
            private String fgp;//分析指位
            private String ridgeColor;//乳突线颜色
            private String mittensBegNo;//连指开始序号
            private String mittensEndNo;//连指结束序号
            private String extractMethod;//提取特征方法
            private String pattern;//纹型
            private String fingerDirection;//指纹方向
            private String centerPoint;//中心点
            private String subCenterPoint;//副中心
            private String leftTriangle;//左三角
            private String rightTriangle;//右三角
            private int featureCount;//特征个数
            private String feature;//特征点
            private int customInfoLength;//自定义信息长度
            private byte[] customInfo;// 自定义信息
            private int imgHorizontalLength;//图像水平方向长度
            private int imgVerticalLength;//图像水平方向长度
            private int dpi;//图像分辨率
            private String imgCompressMethod;//图像压缩方法
            private int imgDataLenght;//图像长度
            private byte[] imgData;//图像数据
            /* 以下4版本的数据 */
            private String isCorpse;//是否为尸体指纹
            private String corpseNo;//未知名尸体编号
            private String isAssist;//指纹协查标识
            private String matchStatus;//指纹比对状态

            public int getDataLength() {
                return dataLength;
            }
            public void setDataLength(int dataLength) {
                this.dataLength = dataLength;
            }
            public String getSendNo() {
                return sendNo;
            }
            public void setSendNo(String sendNo) {
                this.sendNo = sendNo;
            }
            public String getFingerNo() {
                return fingerNo;
            }
            public void setFingerNo(String fingerNo) {
                this.fingerNo = fingerNo;
            }
            public String getFingerId() {
                return fingerId;
            }
            public void setFingerId(String fingerId) {
                this.fingerId = fingerId;
            }
            public String getRemainPlace() {
                return remainPlace;
            }
            public void setRemainPlace(String remainPlace) {
                this.remainPlace = remainPlace;
            }
            public String getFgp() {
                return fgp;
            }
            public void setFgp(String fgp) {
                this.fgp = fgp;
            }
            public String getRidgeColor() {
                return ridgeColor;
            }
            public void setRidgeColor(String ridgeColor) {
                this.ridgeColor = ridgeColor;
            }
            public String getMittensBegNo() {
                return mittensBegNo;
            }
            public void setMittensBegNo(String mittensBegNo) {
                this.mittensBegNo = mittensBegNo;
            }
            public String getMittensEndNo() {
                return mittensEndNo;
            }
            public void setMittensEndNo(String mittensEndNo) {
                this.mittensEndNo = mittensEndNo;
            }
            public String getExtractMethod() {
                return extractMethod;
            }
            public void setExtractMethod(String extractMethod) {
                this.extractMethod = extractMethod;
            }
            public String getPattern() {
                return pattern;
            }
            public void setPattern(String pattern) {
                this.pattern = pattern;
            }
            public String getFingerDirection() {
                return fingerDirection;
            }
            public void setFingerDirection(String fingerDirection) {
                this.fingerDirection = fingerDirection;
            }
            public String getCenterPoint() {
                return centerPoint;
            }
            public void setCenterPoint(String centerPoint) {
                this.centerPoint = centerPoint;
            }
            public String getSubCenterPoint() {
                return subCenterPoint;
            }
            public void setSubCenterPoint(String subCenterPoint) {
                this.subCenterPoint = subCenterPoint;
            }
            public String getLeftTriangle() {
                return leftTriangle;
            }
            public void setLeftTriangle(String leftTriangle) {
                this.leftTriangle = leftTriangle;
            }
            public String getRightTriangle() {
                return rightTriangle;
            }
            public void setRightTriangle(String rightTriangle) {
                this.rightTriangle = rightTriangle;
            }
            public int getFeatureCount() {
                return featureCount;
            }
            public void setFeatureCount(int featureCount) {
                this.featureCount = featureCount;
            }
            public String getFeature() {
                return feature;
            }
            public void setFeature(String feature) {
                this.feature = feature;
            }
            public int getCustomInfoLength() {
                return customInfoLength;
            }
            public void setCustomInfoLength(int customInfoLength) {
                this.customInfoLength = customInfoLength;
            }
            public byte[] getCustomInfo() {
                return customInfo;
            }
            public void setCustomInfo(byte[] customInfo) {
                this.customInfo = customInfo;
            }
            public int getImgHorizontalLength() {
                return imgHorizontalLength;
            }
            public void setImgHorizontalLength(int imgHorizontalLength) {
                this.imgHorizontalLength = imgHorizontalLength;
            }
            public int getImgVerticalLength() {
                return imgVerticalLength;
            }
            public void setImgVerticalLength(int imgVerticalLength) {
                this.imgVerticalLength = imgVerticalLength;
            }
            public int getDpi() {
                return dpi;
            }
            public void setDpi(int dpi) {
                this.dpi = dpi;
            }
            public String getImgCompressMethod() {
                return imgCompressMethod;
            }
            public void setImgCompressMethod(String imgCompressMethod) {
                this.imgCompressMethod = imgCompressMethod;
            }
            public int getImgDataLenght() {
                return imgDataLenght;
            }
            public void setImgDataLenght(int imgDataLenght) {
                this.imgDataLenght = imgDataLenght;
            }
            public byte[] getImgData() {
                return imgData;
            }
            public void setImgData(byte[] imgData) {
                this.imgData = imgData;
            }
            public String getIsCorpse() {
                return isCorpse;
            }
            public void setIsCorpse(String isCorpse) {
                this.isCorpse = isCorpse;
            }
            public String getCorpseNo() {
                return corpseNo;
            }
            public void setCorpseNo(String corpseNo) {
                this.corpseNo = corpseNo;
            }
            public String getIsAssist() {
                return isAssist;
            }
            public void setIsAssist(String isAssist) {
                this.isAssist = isAssist;
            }
            public String getMatchStatus() {
                return matchStatus;
            }
            public void setMatchStatus(String matchStatus) {
                this.matchStatus = matchStatus;
            }
        }
    }

    /**
     * 指纹正查和倒查比中信息记录数据
     */
    public static class FPTlt_tlMatchData{
        private int length;//本逻辑记录长度
        private String dataType;//记录类型04
        private int index;//序号
        private String systemType;//系统类型
        private String caseId;//案件编号
        private String seqNo;//现场指纹序号
        private String personId;//人员编号
        private String fgp;//指纹指位
        private String capture;//抓获
        private String matchMethod;//比对方法
        private String matchUnitCode;//比对单位代码
        private String matchName;//比对单位名称
        private String matcher;//比对人姓名
        private String matchDate;//比对日期
        private String remark;//备注
        private String inputer;//填报人
        private String inputDate;//填报日期
        private String approver;//审批人
        private String approveDate;//审批日期
        private String inputUnitCode;//填报单位代码
        private String inputUnitName;//填报单位名称
        private String rechecker;//复核人
        private String recheckUnitCode;//复核单位代码
        private String recheckDate;//复核日期
        private static FPTlt_tlMatchData parseFromInputStream(InputStream in) throws IOException{
            FPTlt_tlMatchData data = new FPTlt_tlMatchData();
//			byte[] length = new byte[8];//本逻辑记录长度
//			byte[] dataType = new byte[2];//记录类型04
            byte[] index = new byte[6];//序号
            byte[] systemType = new byte[4];//系统类型
            byte[] caseId = new byte[23];//案件编号
            byte[] seqNo = new byte[2];//现场指纹序号
            byte[] personId = new byte[23];//人员编号
            byte[] fgp = new byte[2];//指纹指位
            byte[] capture = new byte[1];//抓获
            byte[] matchMethod = new byte[1];//比对方法
            byte[] matchUnitCode = new byte[12];//比对单位代码
            byte[] matchName = new byte[70];//比对单位名称
            byte[] matcher = new byte[30];//比对人姓名
            byte[] matchDate = new byte[8];//比对日期
            byte[] remark = new byte[100];//备注
            byte[] inputer = new byte[30];//填报人
            byte[] inputDate = new byte[8];//填报日期
            byte[] approver = new byte[30];//审批人
            byte[] approveDate = new byte[8];//审批日期
            byte[] inputUnitCode = new byte[12];//填报单位代码
            byte[] inputUnitName = new byte[70];//填报单位名称
            byte[] rechecker = new byte[30];//复核人
            byte[] recheckUnitCode = new byte[12];//复核单位代码
            byte[] recheckDate = new byte[8];//复核日期
            in.read(index);
            data.index = bytes2String2Int(index);
            in.read(systemType);
            data.systemType = bytes2String(systemType);
            in.read(caseId);
            data.caseId = bytes2String(caseId);
            in.read(seqNo);
            data.seqNo = bytes2String(seqNo);
            in.read(personId);
            data.personId = bytes2String(personId);
            in.read(fgp);
            data.fgp = bytes2String(fgp);
            in.read(capture);
            data.capture = bytes2String(capture);
            in.read(matchMethod);
            data.matchMethod = bytes2String(matchMethod);
            in.read(matchUnitCode);
            data.matchUnitCode = bytes2String(matchUnitCode);
            in.read(matchName);
            data.matchName = bytes2String(matchName);
            in.read(matcher);
            data.matcher = bytes2String(matcher);
            in.read(matchDate);
            data.matchDate = bytes2String(matchDate);
            in.read(remark);
            data.remark = bytes2String(remark);
            in.read(inputer);
            data.inputer = bytes2String(inputer);
            in.read(inputDate);
            data.inputDate = bytes2String(inputDate);
            in.read(approver);
            data.approver = bytes2String(approver);
            in.read(approveDate);
            data.approveDate = bytes2String(approveDate);
            in.read(inputUnitCode);
            data.inputUnitCode = bytes2String(inputUnitCode);
            in.read(inputUnitName);
            data.inputUnitName = bytes2String(inputUnitName);
            in.read(rechecker);
            data.rechecker = bytes2String(rechecker);
            in.read(recheckUnitCode);
            data.recheckUnitCode = bytes2String(recheckUnitCode);
            in.read(recheckDate);
            data.recheckDate = bytes2String(recheckDate);
            in.read();//结束分隔符
            return data;
        }
        private byte[] toByteArray() throws Exception{
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            this.dataType = "04";
            this.systemType = "1900";
            out.write(string2Bytes(this.dataType, 2));//fpt4版本的逻辑类型长度2
            out.write(string2Bytes(this.index+"", 6));
            out.write(string2Bytes(this.systemType, 4));
            out.write(string2Bytes(this.caseId, 23));
            out.write(string2Bytes(this.seqNo, 2));
            out.write(string2Bytes(this.personId, 23));
            out.write(string2Bytes(this.fgp, 2));
            out.write(string2Bytes(this.capture, 1));
            out.write(string2Bytes(this.matchMethod, 1));
            out.write(string2Bytes(this.matchUnitCode, 12));
            out.write(string2Bytes(this.matchName, 70));
            out.write(string2Bytes(this.matcher, 30));
            out.write(string2Bytes(this.matchDate, 8));
            out.write(string2Bytes(this.remark, 100));
            out.write(string2Bytes(this.inputer, 30));
            out.write(string2Bytes(this.inputDate, 8));
            out.write(string2Bytes(this.approver, 30));
            out.write(string2Bytes(this.approveDate, 8));
            out.write(string2Bytes(this.inputUnitCode, 12));
            out.write(string2Bytes(this.inputUnitName, 70));
            out.write(string2Bytes(this.rechecker, 30));
            out.write(string2Bytes(this.recheckUnitCode, 12));
            out.write(string2Bytes(this.recheckDate, 8));
            out.write((byte)29);
            //计算本逻辑记录长度
            byte[] data = out.toByteArray();
            result.write(int2String2Bytes(data.length+8, 8));
            result.write(data);
            return result.toByteArray();
        }

        public int getLength() {
            return length;
        }
        public void setLength(int length) {
            this.length = length;
        }
        public String getDataType() {
            return dataType;
        }
        public void setDataType(String dataType) {
            this.dataType = dataType;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
        public String getSystemType() {
            return systemType;
        }
        public void setSystemType(String systemType) {
            this.systemType = systemType;
        }
        public String getCaseId() {
            return caseId;
        }
        public void setCaseId(String caseId) {
            this.caseId = caseId;
        }
        public String getSeqNo() {
            return seqNo;
        }
        public void setSeqNo(String seqNo) {
            this.seqNo = seqNo;
        }
        public String getPersonId() {
            return personId;
        }
        public void setPersonId(String personId) {
            this.personId = personId;
        }
        public String getFgp() {
            return fgp;
        }
        public void setFgp(String fgp) {
            this.fgp = fgp;
        }
        public String getCapture() {
            return capture;
        }
        public void setCapture(String capture) {
            this.capture = capture;
        }
        public String getMatchMethod() {
            return matchMethod;
        }
        public void setMatchMethod(String matchMethod) {
            this.matchMethod = matchMethod;
        }
        public String getMatchUnitCode() {
            return matchUnitCode;
        }
        public void setMatchUnitCode(String matchUnitCode) {
            this.matchUnitCode = matchUnitCode;
        }
        public String getMatchName() {
            return matchName;
        }
        public void setMatchName(String matchName) {
            this.matchName = matchName;
        }
        public String getMatcher() {
            return matcher;
        }
        public void setMatcher(String matcher) {
            this.matcher = matcher;
        }
        public String getMatchDate() {
            return matchDate;
        }
        public void setMatchDate(String matchDate) {
            this.matchDate = matchDate;
        }
        public String getRemark() {
            return remark;
        }
        public void setRemark(String remark) {
            this.remark = remark;
        }
        public String getInputer() {
            return inputer;
        }
        public void setInputer(String inputer) {
            this.inputer = inputer;
        }
        public String getInputDate() {
            return inputDate;
        }
        public void setInputDate(String inputDate) {
            this.inputDate = inputDate;
        }
        public String getApprover() {
            return approver;
        }
        public void setApprover(String approver) {
            this.approver = approver;
        }
        public String getApproveDate() {
            return approveDate;
        }
        public void setApproveDate(String approveDate) {
            this.approveDate = approveDate;
        }
        public String getInputUnitCode() {
            return inputUnitCode;
        }
        public void setInputUnitCode(String inputUnitCode) {
            this.inputUnitCode = inputUnitCode;
        }
        public String getInputUnitName() {
            return inputUnitName;
        }
        public void setInputUnitName(String inputUnitName) {
            this.inputUnitName = inputUnitName;
        }
        public String getRechecker() {
            return rechecker;
        }
        public void setRechecker(String rechecker) {
            this.rechecker = rechecker;
        }
        public String getRecheckUnitCode() {
            return recheckUnitCode;
        }
        public void setRecheckUnitCode(String recheckUnitCode) {
            this.recheckUnitCode = recheckUnitCode;
        }
        public String getRecheckDate() {
            return recheckDate;
        }
        public void setRecheckDate(String recheckDate) {
            this.recheckDate = recheckDate;
        }
    }
    /**
     * 指纹查重比中信息
     */
    public static class FPTttMatchData{
        private int length;//本逻辑记录长度
        private String dataType;//记录类型05
        private int index;//序号
        private String systemType;//系统类型
        private String personId1;//人员编号1
        private String personId2;//人员编号2
        private String matchUnitCode;//比对单位代码
        private String matchName;//比对单位名称
        private String matcher;//比对人姓名
        private String matchDate;//比对日期
        private String remark;//备注
        private String inputer;//填报人
        private String inputDate;//填报日期
        private String approver;//审批人
        private String approveDate;//审批日期
        private String inputUnitCode;//填报单位代码
        private String inputUnitName;//填报单位名称
        private String rechecker;//复核人
        private String recheckUnitCode;//复核单位代码
        private String recheckDate;//复核日期
        private static FPTttMatchData parseFromInputStream(InputStream in) throws IOException{
            FPTttMatchData data = new FPTttMatchData();
//			byte[] length = new byte[8];//本逻辑记录长度
//			byte[] dataType = new byte[2];//记录类型04
            byte[] index = new byte[6];//序号
            byte[] systemType = new byte[4];//系统类型
            byte[] personId1 = new byte[23];//人员编号1
            byte[] personId2 = new byte[23];//人员编号2
            byte[] matchUnitCode = new byte[12];//比对单位代码
            byte[] matchName = new byte[70];//比对单位名称
            byte[] matcher = new byte[30];//比对人姓名
            byte[] matchDate = new byte[8];//比对日期
            byte[] remark = new byte[100];//备注
            byte[] inputer = new byte[30];//填报人
            byte[] inputDate = new byte[8];//填报日期
            byte[] approver = new byte[30];//审批人
            byte[] approveDate = new byte[8];//审批日期
            byte[] inputUnitCode = new byte[12];//填报单位代码
            byte[] inputUnitName = new byte[70];//填报单位名称
            byte[] rechecker = new byte[30];//复核人
            byte[] recheckUnitCode = new byte[12];//复核单位代码
            byte[] recheckDate = new byte[8];//复核日期
            in.read(index);
            data.index = bytes2String2Int(index);
            in.read(systemType);
            data.systemType = bytes2String(systemType);
            in.read(personId1);
            data.personId1 = bytes2String(personId1);
            in.read(personId2);
            data.personId2 = bytes2String(personId2);
            in.read(matchUnitCode);
            data.matchUnitCode = bytes2String(matchUnitCode);
            in.read(matchName);
            data.matchName = bytes2String(matchName);
            in.read(matcher);
            data.matcher = bytes2String(matcher);
            in.read(matchDate);
            data.matchDate = bytes2String(matchDate);
            in.read(remark);
            data.remark = bytes2String(remark);
            in.read(inputer);
            data.inputer = bytes2String(inputer);
            in.read(inputDate);
            data.inputDate = bytes2String(inputDate);
            in.read(approver);
            data.approver = bytes2String(approver);
            in.read(approveDate);
            data.approveDate = bytes2String(approveDate);
            in.read(inputUnitCode);
            data.inputUnitCode = bytes2String(inputUnitCode);
            in.read(inputUnitName);
            data.inputUnitName = bytes2String(inputUnitName);
            in.read(rechecker);
            data.rechecker = bytes2String(rechecker);
            in.read(recheckUnitCode);
            data.recheckUnitCode = bytes2String(recheckUnitCode);
            in.read(recheckDate);
            data.recheckDate = bytes2String(recheckDate);
            in.read();//结束分隔符
            return data;
        }
        private byte[] toByteArray() throws Exception{
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            this.dataType = "05";
            this.systemType = "1900";
            out.write(string2Bytes(this.dataType,2));
            out.write(string2Bytes(this.index+"",6));
            out.write(string2Bytes(this.systemType,4));
            out.write(string2Bytes(this.personId1,23));
            out.write(string2Bytes(this.personId2,23));
            out.write(string2Bytes(this.matchUnitCode,12));
            out.write(string2Bytes(this.matchName,70));
            out.write(string2Bytes(this.matcher,30));
            out.write(string2Bytes(this.matchDate,8));
            out.write(string2Bytes(this.remark,100));
            out.write(string2Bytes(this.inputer,30));
            out.write(string2Bytes(this.inputDate,8));
            out.write(string2Bytes(this.approver,30));
            out.write(string2Bytes(this.approveDate,8));
            out.write(string2Bytes(this.inputUnitCode,12));
            out.write(string2Bytes(this.inputUnitName,70));
            out.write(string2Bytes(this.rechecker,30));
            out.write(string2Bytes(this.recheckUnitCode,12));
            out.write(string2Bytes(this.recheckDate,8));
            out.write((byte)29);
            //计算本逻辑记录长度
            byte[] data = out.toByteArray();
            result.write(int2String2Bytes(data.length+8, 8));
            result.write(data);
            return result.toByteArray();
        }
        public int getLength() {
            return length;
        }
        public void setLength(int length) {
            this.length = length;
        }
        public String getDataType() {
            return dataType;
        }
        public void setDataType(String dataType) {
            this.dataType = dataType;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
        public String getSystemType() {
            return systemType;
        }
        public void setSystemType(String systemType) {
            this.systemType = systemType;
        }
        public String getPersonId1() {
            return personId1;
        }
        public void setPersonId1(String personId1) {
            this.personId1 = personId1;
        }
        public String getPersonId2() {
            return personId2;
        }
        public void setPersonId2(String personId2) {
            this.personId2 = personId2;
        }
        public String getMatchUnitCode() {
            return matchUnitCode;
        }
        public void setMatchUnitCode(String matchUnitCode) {
            this.matchUnitCode = matchUnitCode;
        }
        public String getMatchName() {
            return matchName;
        }
        public void setMatchName(String matchName) {
            this.matchName = matchName;
        }
        public String getMatcher() {
            return matcher;
        }
        public void setMatcher(String matcher) {
            this.matcher = matcher;
        }
        public String getMatchDate() {
            return matchDate;
        }
        public void setMatchDate(String matchDate) {
            this.matchDate = matchDate;
        }
        public String getRemark() {
            return remark;
        }
        public void setRemark(String remark) {
            this.remark = remark;
        }
        public String getInputer() {
            return inputer;
        }
        public void setInputer(String inputer) {
            this.inputer = inputer;
        }
        public String getInputDate() {
            return inputDate;
        }
        public void setInputDate(String inputDate) {
            this.inputDate = inputDate;
        }
        public String getApprover() {
            return approver;
        }
        public void setApprover(String approver) {
            this.approver = approver;
        }
        public String getApproveDate() {
            return approveDate;
        }
        public void setApproveDate(String approveDate) {
            this.approveDate = approveDate;
        }
        public String getInputUnitCode() {
            return inputUnitCode;
        }
        public void setInputUnitCode(String inputUnitCode) {
            this.inputUnitCode = inputUnitCode;
        }
        public String getInputUnitName() {
            return inputUnitName;
        }
        public void setInputUnitName(String inputUnitName) {
            this.inputUnitName = inputUnitName;
        }
        public String getRechecker() {
            return rechecker;
        }
        public void setRechecker(String rechecker) {
            this.rechecker = rechecker;
        }
        public String getRecheckUnitCode() {
            return recheckUnitCode;
        }
        public void setRecheckUnitCode(String recheckUnitCode) {
            this.recheckUnitCode = recheckUnitCode;
        }
        public String getRecheckDate() {
            return recheckDate;
        }
        public void setRecheckDate(String recheckDate) {
            this.recheckDate = recheckDate;
        }
    }
    /**
     * 指纹串查比中信息
     */
    public static class FPTllMatchData{
        private int length;//本逻辑记录长度
        private String dataType;//记录类型06
        private int index;//序号
        private String systemType;//系统类型
        private String caseId1;//案件编号1
        private String seqNo1;//现场指纹序号1
        private String caseId2;//案件编号2
        private String seqNo2;//现场指纹序号2
        private String matchUnitCode;//比对单位代码
        private String matchName;//比对单位名称
        private String matcher;//比对人姓名
        private String matchDate;//比对日期
        private String remark;//备注
        private String inputer;//填报人
        private String inputDate;//填报日期
        private String approver;//审批人
        private String approveDate;//审批日期
        private String inputUnitCode;//填报单位代码
        private String inputUnitName;//填报单位名称
        private String rechecker;//复核人
        private String recheckUnitCode;//复核单位代码
        private String recheckDate;//复核日期
        private static FPTllMatchData parseFromInputStream(InputStream in) throws IOException{
            FPTllMatchData data = new FPTllMatchData();
//			byte[] length = new byte[8];//本逻辑记录长度
//			byte[] dataType = new byte[2];//记录类型06
            byte[] index = new byte[6];//序号
            byte[] systemType = new byte[4];//系统类型
            byte[] caseId1 = new byte[23];//案件编号1
            byte[] seqNo1 = new byte[2];//指纹序号1
            byte[] caseId2 = new byte[23];//案件编号2
            byte[] seqNo2 = new byte[2];//指纹序号2
            byte[] matchUnitCode = new byte[12];//比对单位代码
            byte[] matchName = new byte[70];//比对单位名称
            byte[] matcher = new byte[30];//比对人姓名
            byte[] matchDate = new byte[8];//比对日期
            byte[] remark = new byte[100];//备注
            byte[] inputer = new byte[30];//填报人
            byte[] inputDate = new byte[8];//填报日期
            byte[] approver = new byte[30];//审批人
            byte[] approveDate = new byte[8];//审批日期
            byte[] inputUnitCode = new byte[12];//填报单位代码
            byte[] inputUnitName = new byte[70];//填报单位名称
            byte[] rechecker = new byte[30];//复核人
            byte[] recheckUnitCode = new byte[12];//复核单位代码
            byte[] recheckDate = new byte[8];//复核日期
            in.read(index);
            data.index = bytes2String2Int(index);
            in.read(systemType);
            data.systemType = bytes2String(systemType);
            in.read(caseId1);
            data.caseId1 = bytes2String(caseId1);
            in.read(seqNo1);
            data.seqNo1 = bytes2String(seqNo1);
            in.read(caseId2);
            data.caseId2 = bytes2String(caseId2);
            in.read(seqNo2);
            data.seqNo2 = bytes2String(seqNo2);
            in.read(matchUnitCode);
            data.matchUnitCode = bytes2String(matchUnitCode);
            in.read(matchName);
            data.matchName = bytes2String(matchName);
            in.read(matcher);
            data.matcher = bytes2String(matcher);
            in.read(matchDate);
            data.matchDate = bytes2String(matchDate);
            in.read(remark);
            data.remark = bytes2String(remark);
            in.read(inputer);
            data.inputer = bytes2String(inputer);
            in.read(inputDate);
            data.inputDate = bytes2String(inputDate);
            in.read(approver);
            data.approver = bytes2String(approver);
            in.read(approveDate);
            data.approveDate = bytes2String(approveDate);
            in.read(inputUnitCode);
            data.inputUnitCode = bytes2String(inputUnitCode);
            in.read(inputUnitName);
            data.inputUnitName = bytes2String(inputUnitName);
            in.read(rechecker);
            data.rechecker = bytes2String(rechecker);
            in.read(recheckUnitCode);
            data.recheckUnitCode = bytes2String(recheckUnitCode);
            in.read(recheckDate);
            data.recheckDate = bytes2String(recheckDate);
            in.read();//结束分隔符
            return data;
        }
        private byte[] toByteArray()throws Exception{
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            this.dataType = "06";
            this.systemType = "1900";
            out.write(string2Bytes(this.dataType,2));
            out.write(string2Bytes(this.index+"",6));
            out.write(string2Bytes(this.systemType,4));
            out.write(string2Bytes(this.caseId1,23));
            out.write(string2Bytes(this.seqNo1,2));
            out.write(string2Bytes(this.caseId2,23));
            out.write(string2Bytes(this.seqNo2,2));
            out.write(string2Bytes(this.matchUnitCode,12));
            out.write(string2Bytes(this.matchName,70));
            out.write(string2Bytes(this.matcher,30));
            out.write(string2Bytes(this.matchDate,8));
            out.write(string2Bytes(this.remark,100));
            out.write(string2Bytes(this.inputer,30));
            out.write(string2Bytes(this.inputDate,8));
            out.write(string2Bytes(this.approver,30));
            out.write(string2Bytes(this.approveDate,8));
            out.write(string2Bytes(this.inputUnitCode,12));
            out.write(string2Bytes(this.inputUnitName,70));
            out.write(string2Bytes(this.rechecker,30));
            out.write(string2Bytes(this.recheckUnitCode,12));
            out.write(string2Bytes(this.recheckDate,8));
            out.write((byte)29);
            //计算本逻辑记录长度
            byte[] data = out.toByteArray();
            result.write(int2String2Bytes(data.length+8, 8));
            result.write(data);
            return result.toByteArray();
        }
        public int getLength() {
            return length;
        }
        public void setLength(int length) {
            this.length = length;
        }
        public String getDataType() {
            return dataType;
        }
        public void setDataType(String dataType) {
            this.dataType = dataType;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
        public String getSystemType() {
            return systemType;
        }
        public void setSystemType(String systemType) {
            this.systemType = systemType;
        }
        public String getCaseId1() {
            return caseId1;
        }
        public void setCaseId1(String caseId1) {
            this.caseId1 = caseId1;
        }
        public String getSeqNo1() {
            return seqNo1;
        }
        public void setSeqNo1(String seqNo1) {
            this.seqNo1 = seqNo1;
        }
        public String getCaseId2() {
            return caseId2;
        }
        public void setCaseId2(String caseId2) {
            this.caseId2 = caseId2;
        }
        public String getSeqNo2() {
            return seqNo2;
        }
        public void setSeqNo2(String seqNo2) {
            this.seqNo2 = seqNo2;
        }
        public String getMatchUnitCode() {
            return matchUnitCode;
        }
        public void setMatchUnitCode(String matchUnitCode) {
            this.matchUnitCode = matchUnitCode;
        }
        public String getMatchName() {
            return matchName;
        }
        public void setMatchName(String matchName) {
            this.matchName = matchName;
        }
        public String getMatcher() {
            return matcher;
        }
        public void setMatcher(String matcher) {
            this.matcher = matcher;
        }
        public String getMatchDate() {
            return matchDate;
        }
        public void setMatchDate(String matchDate) {
            this.matchDate = matchDate;
        }
        public String getRemark() {
            return remark;
        }
        public void setRemark(String remark) {
            this.remark = remark;
        }
        public String getInputer() {
            return inputer;
        }
        public void setInputer(String inputer) {
            this.inputer = inputer;
        }
        public String getInputDate() {
            return inputDate;
        }
        public void setInputDate(String inputDate) {
            this.inputDate = inputDate;
        }
        public String getApprover() {
            return approver;
        }
        public void setApprover(String approver) {
            this.approver = approver;
        }
        public String getApproveDate() {
            return approveDate;
        }
        public void setApproveDate(String approveDate) {
            this.approveDate = approveDate;
        }
        public String getInputUnitCode() {
            return inputUnitCode;
        }
        public void setInputUnitCode(String inputUnitCode) {
            this.inputUnitCode = inputUnitCode;
        }
        public String getInputUnitName() {
            return inputUnitName;
        }
        public void setInputUnitName(String inputUnitName) {
            this.inputUnitName = inputUnitName;
        }
        public String getRechecker() {
            return rechecker;
        }
        public void setRechecker(String rechecker) {
            this.rechecker = rechecker;
        }
        public String getRecheckUnitCode() {
            return recheckUnitCode;
        }
        public void setRecheckUnitCode(String recheckUnitCode) {
            this.recheckUnitCode = recheckUnitCode;
        }
        public String getRecheckDate() {
            return recheckDate;
        }
        public void setRecheckDate(String recheckDate) {
            this.recheckDate = recheckDate;
        }
    }

    /**
     * 现场指纹查询请求信息记录数据
     */
    public static class FPTlpMatchRequestData{
        private int length;//本逻辑记录长度
        private String dataType;//记录类型07
        private int index;//序号
        private String systemType;//系统类型
        private String caseId;//案件编号
        private String seqNo;//现场指纹序号
        private String matchPurpose;//指纹查询目的1：正查；2：串查；3：查尸源
        private static FPTlpMatchRequestData parseFromInputStream(InputStream in) throws IOException{
            FPTlpMatchRequestData data = new FPTlpMatchRequestData();
//			byte[] length = new byte[8];//本逻辑记录长度
//			byte[] dataType = new byte[2];//记录类型07
            byte[] index = new byte[6];//序号
            byte[] systemType = new byte[4];//系统类型
            byte[] caseId = new byte[23];//案件编号
            byte[] seqNo = new byte[2];//指纹序号
            byte[] matchPurpose = new byte[1];//指纹查询目的
            in.read(index);
            data.index = bytes2String2Int(index);
            in.read(systemType);
            data.systemType = bytes2String(systemType);
            in.read(caseId);
            data.caseId = bytes2String(caseId);
            in.read(seqNo);
            data.seqNo = bytes2String(seqNo);
            in.read(matchPurpose);
            data.matchPurpose = bytes2String(matchPurpose);
            return data;
        }
        public int getLength() {
            return length;
        }
        public void setLength(int length) {
            this.length = length;
        }
        public String getDataType() {
            return dataType;
        }
        public void setDataType(String dataType) {
            this.dataType = dataType;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
        public String getSystemType() {
            return systemType;
        }
        public void setSystemType(String systemType) {
            this.systemType = systemType;
        }
        public String getCaseId() {
            return caseId;
        }
        public void setCaseId(String caseId) {
            this.caseId = caseId;
        }
        public String getSeqNo() {
            return seqNo;
        }
        public void setSeqNo(String seqNo) {
            this.seqNo = seqNo;
        }
        public String getMatchPurpose() {
            return matchPurpose;
        }
        public void setMatchPurpose(String matchPurpose) {
            this.matchPurpose = matchPurpose;
        }
    }

    /**
     * 十指指纹查询请求信息记录数据
     */
    public static class FPTtpMatchRequestData{
        private int length;//本逻辑记录长度
        private String dataType;//记录类型08
        private int index;//序号
        private String systemType;//系统类型
        private String personId;//人员编号
        private String matchPurpose;//指纹查询目的1：倒查；2：查重
        private static FPTtpMatchRequestData parseFromInputStream(InputStream in) throws IOException{
            FPTtpMatchRequestData data = new FPTtpMatchRequestData();
//			byte[] length = new byte[8];//本逻辑记录长度
//			byte[] dataType = new byte[2];//记录类型08
            byte[] index = new byte[6];//序号
            byte[] systemType = new byte[4];//系统类型
            byte[] personId = new byte[23];//人员编号
            byte[] matchPurpose = new byte[1];//指纹查询目的
            in.read(index);
            data.index = bytes2String2Int(index);
            in.read(systemType);
            data.systemType = bytes2String(systemType);
            in.read(personId);
            data.personId = bytes2String(personId);
            in.read(matchPurpose);
            data.matchPurpose = bytes2String(matchPurpose);
            return data;
        }
        public int getLength() {
            return length;
        }
        public void setLength(int length) {
            this.length = length;
        }
        public String getDataType() {
            return dataType;
        }
        public void setDataType(String dataType) {
            this.dataType = dataType;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
        public String getSystemType() {
            return systemType;
        }
        public void setSystemType(String systemType) {
            this.systemType = systemType;
        }
        public String getPersonId() {
            return personId;
        }
        public void setPersonId(String personId) {
            this.personId = personId;
        }
        public String getMatchPurpose() {
            return matchPurpose;
        }
        public void setMatchPurpose(String matchPurpose) {
            this.matchPurpose = matchPurpose;
        }
    }
    /**
     * 正查比对结果候选信息记录数据
     */
    public static class FPTltMatchCandidateData{
        private int length;//本逻辑记录长度
        private String dataType;//记录类型09
        private int index;//序号
        private String matchMethod;//比对方法代码
        private String matchUnitCode;//提交结果单位代码
        private String matchFinshDate;//比对完成时间
        private String authenticator;//认证人姓名
        private String caseId;//送检指纹案件编号
        private String seqNo;//送检指纹序号
        private int tpFingerCount;//实际返回十指指纹数
        private int candidatePlace;//侯选名次
        private int candidateScore;//侯选得分
        private String personId;//人员编号
        private String fgp;//指位
        private static FPTltMatchCandidateData parseFromInputStream(InputStream in) throws IOException{
            FPTltMatchCandidateData data = new FPTltMatchCandidateData();
//			byte[] length = new byte[8];//本逻辑记录长度
//			byte[] dataType = new byte[2];//记录类型09
            byte[] index = new byte[6];//序号
            byte[] matchMethod = new byte[4];
            byte[] matchUnitCode = new byte[12];
            byte[] matchFinshDate = new byte[8];
            byte[] authenticator = new byte[30];
            byte[] caseId = new byte[23];
            byte[] seqNo = new byte[2];
            byte[] tpFingerCount = new byte[3];
            byte[] candidatePlace = new byte[3];
            byte[] candidateScore = new byte[8];
            byte[] personId = new byte[23];
            byte[] fgp = new byte[2];
            in.read(index);
            data.index = bytes2String2Int(index);
            in.read(matchMethod);
            data.matchMethod = bytes2String(matchMethod);
            in.read(matchUnitCode);
            data.matchUnitCode = bytes2String(matchUnitCode);
            in.read(matchFinshDate);
            data.matchFinshDate = bytes2String(matchFinshDate);
            in.read(authenticator);
            data.authenticator = bytes2String(authenticator);
            in.read(caseId);
            data.caseId = bytes2String(caseId);
            in.read(seqNo);
            data.seqNo = bytes2String(seqNo);
            in.read(tpFingerCount);
            data.tpFingerCount = bytes2String2Int(tpFingerCount);
            in.read(candidatePlace);
            data.candidatePlace = bytes2String2Int(candidatePlace);
            in.read(candidateScore);
            data.candidateScore = bytes2String2Int(candidateScore);
            in.read(personId);
            data.personId = bytes2String(personId);
            in.read(fgp);
            data.fgp = bytes2String(fgp);
            return data;
        }
        public int getLength() {
            return length;
        }
        public void setLength(int length) {
            this.length = length;
        }
        public String getDataType() {
            return dataType;
        }
        public void setDataType(String dataType) {
            this.dataType = dataType;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
        public String getMatchMethod() {
            return matchMethod;
        }
        public void setMatchMethod(String matchMethod) {
            this.matchMethod = matchMethod;
        }
        public String getMatchUnitCode() {
            return matchUnitCode;
        }
        public void setMatchUnitCode(String matchUnitCode) {
            this.matchUnitCode = matchUnitCode;
        }
        public String getMatchFinshDate() {
            return matchFinshDate;
        }
        public void setMatchFinshDate(String matchFinshDate) {
            this.matchFinshDate = matchFinshDate;
        }
        public String getAuthenticator() {
            return authenticator;
        }
        public void setAuthenticator(String authenticator) {
            this.authenticator = authenticator;
        }
        public String getCaseId() {
            return caseId;
        }
        public void setCaseId(String caseId) {
            this.caseId = caseId;
        }
        public String getSeqNo() {
            return seqNo;
        }
        public void setSeqNo(String seqNo) {
            this.seqNo = seqNo;
        }
        public int getTpFingerCount() {
            return tpFingerCount;
        }
        public void setTpFingerCount(int tpFingerCount) {
            this.tpFingerCount = tpFingerCount;
        }
        public int getCandidatePlace() {
            return candidatePlace;
        }
        public void setCandidatePlace(int candidatePlace) {
            this.candidatePlace = candidatePlace;
        }
        public int getCandidateScore() {
            return candidateScore;
        }
        public void setCandidateScore(int candidateScore) {
            this.candidateScore = candidateScore;
        }
        public String getPersonId() {
            return personId;
        }
        public void setPersonId(String personId) {
            this.personId = personId;
        }
        public String getFgp() {
            return fgp;
        }
        public void setFgp(String fgp) {
            this.fgp = fgp;
        }
    }
    /**
     * 倒查比对结果候选信息记录数据
     */
    public static class FPTtlMatchCandidateData{
        private int length;//本逻辑记录长度
        private String dataType;//记录类型10
        private int index;//序号
        private String matchMethod;//比对方法代码
        private String matchUnitCode;//比对单位代码
        private String matchFinshDate;//比对完成时间
        private String personId;//送检指纹人员编号
        private int lpFingerCount;//实际返回现场指纹数
        private int candidatePlace;//侯选名次
        private int candidateScore;//侯选得分
        private String fgp;//指位
        private String caseId;//送检指纹案件编号
        private String seqNo;//送检指纹序号
        private static FPTtlMatchCandidateData parseFromInputStream(InputStream in) throws IOException{
            FPTtlMatchCandidateData data = new FPTtlMatchCandidateData();
//			byte[] length = new byte[8];//本逻辑记录长度
//			byte[] dataType = new byte[2];//记录类型10
            byte[] index = new byte[6];//序号
            byte[] matchMethod = new byte[4];
            byte[] matchUnitCode = new byte[12];
            byte[] matchFinshDate = new byte[8];
            byte[] personId = new byte[23];
            byte[] lpFingerCount = new byte[3];
            byte[] candidatePlace = new byte[3];
            byte[] candidateScore = new byte[8];
            byte[] fgp = new byte[2];
            byte[] caseId = new byte[23];
            byte[] seqNo = new byte[2];
            in.read(index);
            data.index = bytes2String2Int(index);
            in.read(matchMethod);
            data.matchMethod = bytes2String(matchMethod);
            in.read(matchUnitCode);
            data.matchUnitCode = bytes2String(matchUnitCode);
            in.read(matchFinshDate);
            data.matchFinshDate = bytes2String(matchFinshDate);
            in.read(personId);
            data.personId = bytes2String(personId);
            in.read(lpFingerCount);
            data.lpFingerCount = bytes2String2Int(lpFingerCount);
            in.read(candidatePlace);
            data.candidatePlace = bytes2String2Int(candidatePlace);
            in.read(candidateScore);
            data.candidateScore = bytes2String2Int(candidateScore);
            in.read(fgp);
            data.fgp = bytes2String(fgp);
            in.read(caseId);
            data.caseId = bytes2String(caseId);
            in.read(seqNo);
            data.seqNo = bytes2String(seqNo);
            return data;
        }
        public int getLength() {
            return length;
        }
        public void setLength(int length) {
            this.length = length;
        }
        public String getDataType() {
            return dataType;
        }
        public void setDataType(String dataType) {
            this.dataType = dataType;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
        public String getMatchMethod() {
            return matchMethod;
        }
        public void setMatchMethod(String matchMethod) {
            this.matchMethod = matchMethod;
        }
        public String getMatchUnitCode() {
            return matchUnitCode;
        }
        public void setMatchUnitCode(String matchUnitCode) {
            this.matchUnitCode = matchUnitCode;
        }
        public String getMatchFinshDate() {
            return matchFinshDate;
        }
        public void setMatchFinshDate(String matchFinshDate) {
            this.matchFinshDate = matchFinshDate;
        }
        public String getPersonId() {
            return personId;
        }
        public void setPersonId(String personId) {
            this.personId = personId;
        }
        public int getLpFingerCount() {
            return lpFingerCount;
        }
        public void setLpFingerCount(int lpFingerCount) {
            this.lpFingerCount = lpFingerCount;
        }
        public int getCandidatePlace() {
            return candidatePlace;
        }
        public void setCandidatePlace(int candidatePlace) {
            this.candidatePlace = candidatePlace;
        }
        public int getCandidateScore() {
            return candidateScore;
        }
        public void setCandidateScore(int candidateScore) {
            this.candidateScore = candidateScore;
        }
        public String getFgp() {
            return fgp;
        }
        public void setFgp(String fgp) {
            this.fgp = fgp;
        }
        public String getCaseId() {
            return caseId;
        }
        public void setCaseId(String caseId) {
            this.caseId = caseId;
        }
        public String getSeqNo() {
            return seqNo;
        }
        public void setSeqNo(String seqNo) {
            this.seqNo = seqNo;
        }
    }
    /**
     * 查重比对结果候选信息记录数据
     */
    public static class FPTttMatchCandidateData{
        private int length;//本逻辑记录长度
        private String dataType;//记录类型11
        private int index;//序号
        private String matchMethod;//比对方法代码
        private String matchUnitCode;//比对单位代码
        private String matchFinshDate;//比对完成时间
        private String submitPersonId;//送检指纹人员编号
        private int personCount;//实际返回人员数
        private int candidatePlace;//侯选名次
        private int candidateScore;//侯选得分
        private String personId;//人员编号
        private static FPTttMatchCandidateData parseFromInputStream(InputStream in) throws IOException{
            FPTttMatchCandidateData data = new FPTttMatchCandidateData();
//			byte[] length = new byte[8];//本逻辑记录长度
//			byte[] dataType = new byte[2];//记录类型11
            byte[] index = new byte[6];//序号
            byte[] matchMethod = new byte[4];
            byte[] matchUnitCode = new byte[12];
            byte[] matchFinshDate = new byte[8];
            byte[] submitPersonId = new byte[23];
            byte[] personCount = new byte[3];
            byte[] candidatePlace = new byte[3];
            byte[] candidateScore = new byte[8];
            byte[] personId = new byte[23];
            in.read(index);
            data.index = bytes2String2Int(index);
            in.read(matchMethod);
            data.matchMethod = bytes2String(matchMethod);
            in.read(matchUnitCode);
            data.matchUnitCode = bytes2String(matchUnitCode);
            in.read(matchFinshDate);
            data.matchFinshDate = bytes2String(matchFinshDate);
            in.read(submitPersonId);
            data.submitPersonId = bytes2String(submitPersonId);
            in.read(personCount);
            data.personCount = bytes2String2Int(personCount);
            in.read(candidatePlace);
            data.candidatePlace = bytes2String2Int(candidatePlace);
            in.read(candidateScore);
            data.candidateScore = bytes2String2Int(candidateScore);
            in.read(personId);
            data.personId = bytes2String(personId);
            return data;
        }
        public int getLength() {
            return length;
        }
        public void setLength(int length) {
            this.length = length;
        }
        public String getDataType() {
            return dataType;
        }
        public void setDataType(String dataType) {
            this.dataType = dataType;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
        public String getMatchMethod() {
            return matchMethod;
        }
        public void setMatchMethod(String matchMethod) {
            this.matchMethod = matchMethod;
        }
        public String getMatchUnitCode() {
            return matchUnitCode;
        }
        public void setMatchUnitCode(String matchUnitCode) {
            this.matchUnitCode = matchUnitCode;
        }
        public String getMatchFinshDate() {
            return matchFinshDate;
        }
        public void setMatchFinshDate(String matchFinshDate) {
            this.matchFinshDate = matchFinshDate;
        }
        public String getSubmitPersonId() {
            return submitPersonId;
        }
        public void setSubmitPersonId(String submitPersonId) {
            this.submitPersonId = submitPersonId;
        }
        public int getPersonCount() {
            return personCount;
        }
        public void setPersonCount(int personCount) {
            this.personCount = personCount;
        }
        public int getCandidatePlace() {
            return candidatePlace;
        }
        public void setCandidatePlace(int candidatePlace) {
            this.candidatePlace = candidatePlace;
        }
        public int getCandidateScore() {
            return candidateScore;
        }
        public void setCandidateScore(int candidateScore) {
            this.candidateScore = candidateScore;
        }
        public String getPersonId() {
            return personId;
        }
        public void setPersonId(String personId) {
            this.personId = personId;
        }
    }
    /**
     * 串查比对结果候选信息记录数据
     */
    public static class FPTllMatchCandidateData{
        private int length;//本逻辑记录长度
        private String dataType;//记录类型12
        private int index;//序号
        private String matchMethod;//比对方法代码
        private String matchUnitCode;//比对单位代码
        private String matchFinshDate;//比对完成时间
        private String submitCaseId;//送检指纹案件编号
        private String submitSeqNo;//送检指纹序号
        private int llFingerCount;//实际返回现场指纹数
        private int candidatePlace;//侯选名次
        private int candidateScore;//侯选得分
        private String caseId;//指纹案件编号
        private String seqNo;//指纹序号
        private static FPTllMatchCandidateData parseFromInputStream(InputStream in) throws IOException{
            FPTllMatchCandidateData data = new FPTllMatchCandidateData();
//			byte[] length = new byte[8];//本逻辑记录长度
//			byte[] dataType = new byte[2];//记录类型12
            byte[] index = new byte[6];//序号
            byte[] matchMethod = new byte[4];
            byte[] matchUnitCode = new byte[12];
            byte[] matchFinshDate = new byte[8];
            byte[] submitCaseId = new byte[23];
            byte[] submitSeqNo = new byte[2];
            byte[] llFingerCount = new byte[3];
            byte[] candidatePlace = new byte[3];
            byte[] candidateScore = new byte[8];
            byte[] caseId = new byte[23];
            byte[] seqNo = new byte[2];
            in.read(index);
            data.index = bytes2String2Int(index);
            in.read(matchMethod);
            data.matchMethod = bytes2String(matchMethod);
            in.read(matchUnitCode);
            data.matchUnitCode = bytes2String(matchUnitCode);
            in.read(matchFinshDate);
            data.matchFinshDate = bytes2String(matchFinshDate);
            in.read(submitCaseId);
            data.submitCaseId = bytes2String(submitCaseId);
            in.read(submitSeqNo);
            data.submitSeqNo = bytes2String(submitSeqNo);
            in.read(llFingerCount);
            data.llFingerCount = bytes2String2Int(llFingerCount);
            in.read(candidatePlace);
            data.candidatePlace = bytes2String2Int(candidatePlace);
            in.read(candidateScore);
            data.candidateScore = bytes2String2Int(candidateScore);
            in.read(caseId);
            data.caseId = bytes2String(caseId);
            in.read(seqNo);
            data.seqNo = bytes2String(seqNo);
            return data;
        }
        public int getLength() {
            return length;
        }
        public void setLength(int length) {
            this.length = length;
        }
        public String getDataType() {
            return dataType;
        }
        public void setDataType(String dataType) {
            this.dataType = dataType;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
        public String getMatchMethod() {
            return matchMethod;
        }
        public void setMatchMethod(String matchMethod) {
            this.matchMethod = matchMethod;
        }
        public String getMatchUnitCode() {
            return matchUnitCode;
        }
        public void setMatchUnitCode(String matchUnitCode) {
            this.matchUnitCode = matchUnitCode;
        }
        public String getMatchFinshDate() {
            return matchFinshDate;
        }
        public void setMatchFinshDate(String matchFinshDate) {
            this.matchFinshDate = matchFinshDate;
        }
        public String getSubmitCaseId() {
            return submitCaseId;
        }
        public void setSubmitCaseId(String submitCaseId) {
            this.submitCaseId = submitCaseId;
        }
        public String getSubmitSeqNo() {
            return submitSeqNo;
        }
        public void setSubmitSeqNo(String submitSeqNo) {
            this.submitSeqNo = submitSeqNo;
        }
        public int getLlFingerCount() {
            return llFingerCount;
        }
        public void setLlFingerCount(int llFingerCount) {
            this.llFingerCount = llFingerCount;
        }
        public int getCandidatePlace() {
            return candidatePlace;
        }
        public void setCandidatePlace(int candidatePlace) {
            this.candidatePlace = candidatePlace;
        }
        public int getCandidateScore() {
            return candidateScore;
        }
        public void setCandidateScore(int candidateScore) {
            this.candidateScore = candidateScore;
        }
        public String getCaseId() {
            return caseId;
        }
        public void setCaseId(String caseId) {
            this.caseId = caseId;
        }
        public String getSeqNo() {
            return seqNo;
        }
        public void setSeqNo(String seqNo) {
            this.seqNo = seqNo;
        }
    }

    /**
     * 自定义逻辑记录数据
     */
    public static class FPTCustomData{
        private int length;//本逻辑记录长度
        private String dataType;//记录类型99
        private int index;//序号
        private String systemType;//系统类型
        private byte[] data;
        private static FPTCustomData parseFromInputStream(InputStream in, int length) throws IOException{
            FPTCustomData data = new FPTCustomData();
            data.length = length;
//			byte[] length = new byte[8];//本逻辑记录长度
//			byte[] dataType = new byte[2];//记录类型04
            byte[] index = new byte[6];//序号
            byte[] systemType = new byte[4];//系统类型
            in.read(index);
            data.index = bytes2String2Int(index);
            in.read(systemType);
            data.systemType = bytes2String(systemType);
            data.data = new byte[length];
            in.read(data.data);
            return data;
        }
        public int getLength() {
            return length;
        }
        public void setLength(int length) {
            this.length = length;
        }
        public String getDataType() {
            return dataType;
        }
        public void setDataType(String dataType) {
            this.dataType = dataType;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
        public String getSystemType() {
            return systemType;
        }
        public void setSystemType(String systemType) {
            this.systemType = systemType;
        }
        public byte[] getData() {
            return data;
        }
        public void setData(byte[] data) {
            this.data = data;
        }
    }
    /**
     * 将byte数组转换为String(GB2312格式编码),去除byte数组空字节
     */
    private static String bytes2String(byte[] bs){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (int i = 0; i < bs.length; i++) {
            if(bs[i] != 0){
                out.write(bs[i]);
            }
        }
        try {
            return out.toString("GB2312");
        } catch (UnsupportedEncodingException e) {
        }
        return out.toString();
    }
    /**
     * 将字符串转换为byte[](GB2312格式编码)
     */
    private static byte[] string2Bytes(String str, int len){
        byte[] result = new byte[len];
        if(str != null){
            try {
                byte[] bs = str.getBytes("GB2312");
                System.arraycopy(bs, 0, result, 0, bs.length> len ? len : bs.length);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * 将字符串转换为byte[](GB2312格式编码)无有效值的数据用ASCII码空格（SP）填写
     * @param str
     * @param len
     * @return
     */
    private static byte[] string2BytesAndNullIsSP(String str, int len){
        byte[] result = new byte[len];
        if(str != null && str.length() > 0){
            try {
                byte[] bs = str.getBytes("GB2312");
                System.arraycopy(bs, 0, result, 0, bs.length);
                for (int i = bs.length; i < len; i++) {
                    result[i] = 32;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * 将byte数组以字符形式转为int类型, 如果为空返回0
     * @param bs
     * @return
     */
    private static int bytes2String2Int(byte[] bs){
        String str = bytes2String(bs).trim();
        return str.length() > 0 ? Integer.parseInt(str) : 0;
    }

    /**
     * 将int转为String再转为byte[]
     * @param num
     * @param len
     * @return
     */
    private static byte[] int2String2Bytes(int num, int len){
        byte[] result = new byte[len];
        try {
            byte[] bs = String.valueOf(num).getBytes("GB2312");
            System.arraycopy(bs, 0, result, 0, bs.length);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}


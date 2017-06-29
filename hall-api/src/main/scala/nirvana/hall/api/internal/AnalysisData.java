package nirvana.hall.api.internal;

import nirvana.hall.v70.internal.blob.coder.TPCardHelper;
import nirvana.hall.v70.internal.blob.coder.TPCardObject;
import nirvana.hall.v70.internal.blob.entity.TPBlobData;

import javax.sql.rowset.serial.SerialBlob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by wangjue on 2015/11/16.
 */
public class AnalysisData {

    //解析人像
    public static List<HashMap> analysisPortrait(String faceData){
        byte[] faceDatas = MsgBase64.fromBase64(faceData); //转换格式
        //数据拆分
        Object dataObject = TPCardHelper.getInstance().convertTPCard(faceDatas);
        List<TPBlobData> dataList = null;
        if ((dataObject != null) && (((TPCardObject) dataObject).getBlobList() != null)) {
            dataList = ((TPCardObject) dataObject).getBlobList();
        }
        List<HashMap> list = new ArrayList();
        HashMap<String,Object> map;
        //截取图片个数添加多条data记录
        try {
            for (TPBlobData data : dataList) {
                map = new HashMap();
                String pkid = UUID.randomUUID().toString().replace("-", "");
                String fgp = data.getBlobUniqId().getFgp() + "";
                byte[] gatherData = data.getData();
                map.put("pkid", pkid);
                map.put("fgp", fgp);
                map.put("gatherData", gatherData);
                list.add(map);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    //解析指纹
    public static List<HashMap> analysisFinger(String fingerData){
        List<HashMap> list = new ArrayList();
        List<byte[]> dataStrList = new ArrayList<>();
        if(fingerData!=null&&!"".equals(fingerData)){
            dataStrList.add(MsgBase64.fromBase64(fingerData));
        }
        //采集指纹map
        HashMap<String, Object> map;
        for(byte[] fingerPalmDatas : dataStrList){
            //数据拆分
            Object dataObject = TPCardHelper.getInstance().convertTPCard(fingerPalmDatas);
            List<TPBlobData> dataList = new ArrayList<TPBlobData>();
            if ((dataObject != null) && (((TPCardObject) dataObject).getBlobList() != null)) {
                dataList = ((TPCardObject) dataObject).getBlobList();
            }
            //截取图片个数添加多条data记录
            try {
                for (TPBlobData data : dataList) {
                    map = new HashMap();
                    String byt = "040_" + data.getBlobUniqId().getBty();//BTY
                    if (byt.equals("040_1")) { //指纹
                        String pkId = UUID.randomUUID().toString().replace("-", "");
                        String fgp = data.getBlobUniqId().getFgp().toString();
                        String fgpCase = data.getBlobUniqId().getViewId() + "";
                        String groupId = data.getBlobUniqId().getGroupId() + "";
                        byte[] gatherData = data.getData();
                        String lobType = data.getBlobUniqId().getLobType() + "";//1:数据，2：特征

                        //如果是特征数据，并且以<BIN>标签结尾，解析并保存纹线
                        if ("2".equals(lobType) && "<BIN>".equals(new String(MsgBase64.fromByte64(gatherData, gatherData.length - 5, gatherData.length)))) {
                            byte[] streakData = MsgBase64.fromByte64(gatherData, 64 + 640 + 5, gatherData.length - 5);
                            HashMap<String, Object> streakMap = new HashMap();//纹线
                            String streakPkId = UUID.randomUUID().toString().replace("-", "");
                            streakMap.put("pkId", streakPkId);
                            streakMap.put("fgp", fgp);
                            streakMap.put("fgpCase", fgpCase);
                            streakMap.put("groupId", "4");
                            streakMap.put("lobType", lobType);
                            streakMap.put("gatherData", new SerialBlob(streakData));
                            list.add(streakMap);//存在纹线保存

                            gatherData = MsgBase64.fromByte64(gatherData, 0, 64 + 640);//指纹特征长度64头+特征640
                        }
                        map.put("pkId", pkId);
                        map.put("fgp", fgp);
                        map.put("fgpCase", fgpCase);
                        map.put("groupId", groupId);
                        map.put("lobType", lobType);
                        map.put("gatherData", new SerialBlob(gatherData));

                        list.add(map);//保存特征或数据
                    } else
                        continue;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return list;
    }
}

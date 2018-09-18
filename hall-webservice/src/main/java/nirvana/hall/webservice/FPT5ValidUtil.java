package nirvana.hall.webservice;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FPT5校验工具类
 * Created by zqLuo
 */
public class FPT5ValidUtil {

    private static boolean hasLoad = false;

    public static int VALIDTYPE_CODE = 1; //代码
    public static int VALIDTYPE_REGEX = 2; //正则表达式
    public static int VALIDTYPE_STRING = 3; //字符串
    /**
     * 编码map
     */
    private static Map<String,List<String>> codeMap = new HashMap<String,List<String>>();

    private static Map<String,String> regexMap = new HashMap<String,String>();

    private static String[] regexXsd = {
            "date.xsd",
            "float.xsd",
            "int.xsd",
            "regex.xsd",
            "sfzh.xsd",
            "time.xsd",
            "regex.xsd"
    };

    /**
     * 加载XSD到内存
     * @param resourceDirPath
     * @throws DocumentException
     */
    public static void loadXsd(String resourceDirPath) throws DocumentException {
        System.out.println("loadXsd");
        if(!hasLoad){
            File xsdDir = new File(resourceDirPath);
            if(xsdDir != null){
                File[] xsdFiles = new File(resourceDirPath).listFiles();
                if(xsdFiles != null && xsdFiles.length > 0){
                    for(File xsd : xsdFiles){
                        String fileName = xsd.getName();
                        if(fileName.endsWith(".xsd")){
                            if(fileName.startsWith("code_")){
                                codeMap.put(fileName.replaceAll(".xsd",""),readXsdCode(xsd.getAbsolutePath()));
                            }else if(inArray(regexXsd,fileName)){
                                //正则校验xsd
                                regexMap.putAll(readRegex(xsd.getAbsolutePath()));
                            }else{

                            }
                        }
                    }
                    hasLoad = true;
                }else{
                    System.out.println("weiqudao");
                }
            }
        }
    }

    /**
     * 正则表达式验证
     * @param str
     * @param match
     * @return
     */
    public static boolean regexValid(String str,String match){
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile(match);
            Matcher matcher = regex.matcher(str);
            flag = matcher.matches();
        } catch (Exception e){
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**
     * 加载 fpt5  code_*.xsd
     * @param filePath
     * @return
     * @throws DocumentException
     */
    public static List<String> readXsdCode(String filePath) throws DocumentException {
        List<String> list = new ArrayList<>();
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(filePath));
        Element node = document.getRootElement();
        Element simpleType = (Element) node.elements().get(0);
        Element restriction = (Element) simpleType.elements().get(0);
        List<Element> enumerations = restriction.elements();
        for(Element enumeration : enumerations){
            Attribute attribute = enumeration.attribute("value");
            list.add(attribute.getText());
        }
        return list;
    }

    /**
     * 加载 fpt5 正则验证xsd
     * @param filePath
     * @return
     * @throws DocumentException
     */
    public static Map<String,String> readRegex(String filePath) throws DocumentException{
        Map<String,String> map = new HashMap<String,String>();
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(filePath));
        Element node = document.getRootElement();
        List<Element> simpleTypes = node.elements();
        for(Element simpleType : simpleTypes){
            Attribute name = simpleType.attribute("name");
            Element restriction = (Element)simpleType.elements().get(0);
            Element patternEle = (Element)restriction.elements().get(0);
            Attribute pattern = patternEle.attribute("value");
            map.put(name.getText(),pattern.getText());
        }
        return map;
    }

    public static boolean inArray(String[] array,String str){
        for(String arr : array){
            if(arr.equals(str)){
                return true;
            }
        }
        return false;
    }

    /**
     * @param fieldId
     * @param validType
     * @param lengths 长度为3(当校验类型为字符串时传如) ，第一位：固定长度,第二位：最小长度，第三位：最大长度，
     * @return
     */
    public static String valid(String name,String fieldId,String value,int validType,Integer...lengths){
        String result = "";
        try {
            if(validType == FPT5ValidUtil.VALIDTYPE_CODE){
                List<String> list = codeMap.get(fieldId);
                if(list != null && list.size() > 0){
                    if(!list.contains(value)){
                        result = "【字典类型(" + name + "):" + fieldId + ",未查询到编码:"+ value +"】";
                    }
                }else{
                    result = "【未查询到字典(" + name + "):" + fieldId + "】";
                }
            }else if(validType == FPT5ValidUtil.VALIDTYPE_REGEX){
                String regex = regexMap.get(fieldId);
                if(StringUtils.isNotEmpty(regex)){
                    if(!regexValid(value,regex)){
                        String v=value==null||value.equals("")?"空值":value;
                        result = "【正则校验失败("+ name +"):" + fieldId + "= " + v + "】";
                    }
                }else{
                    result = "【未查询到正则校验规则("+ name +"):" + fieldId + "】";
                }
            }else if(validType == FPT5ValidUtil.VALIDTYPE_STRING){
                if(lengths[0] != -1){
                    //第一位不为-1，表示为固定长度
                    if(value.length() != lengths[0]){
                        result = "【长度校验失败("+ name +"),预期长度:" + lengths[0] + ",实际长度" + value.length() + "】";
                    }
                }else{
                    int minLength = lengths[1];
                    int maxLength = lengths[2];
                    if(value.length() < minLength || value.length() > maxLength){
                        result = "【长度校验失败(" + name + "),最小长度:" + lengths[1] + ",最大长度:" + maxLength + ",实际长度:" + value.length() + "】";
                    }
                }
            }
        } catch (Exception e){
            result = "【" + name+ "校验失败,失败原因：" + e.getMessage() + "】";
        }
        return result;
    }
}

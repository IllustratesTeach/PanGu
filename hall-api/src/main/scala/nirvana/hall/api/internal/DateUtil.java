package nirvana.hall.api.internal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    /**
     * Date转日期字符串
     * @param date
     * @throws ParseException
     */
    public static String dateToStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = format.format(date);
        return strDate;
    }

    /**
     * 日期字符串转Date
     * @param strDate
     * @throws ParseException
     */
    public static Date strToDate(String strDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(strDate);
        return date;
    }

}

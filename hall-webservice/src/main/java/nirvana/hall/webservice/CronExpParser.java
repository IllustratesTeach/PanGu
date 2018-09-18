package nirvana.hall.webservice;

import org.apache.tapestry5.ioc.internal.services.cron.CronExpression;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by liukai on 8/22/18.
 */
public class CronExpParser {

    public static String translate(String cronExp){
        if (cronExp == null || cronExp.length() < 1)
        {
            return "cron表达式为空";
        }
        CronExpression exp = null;
        // 初始化cron表达式解析器
        try
        {
            exp = new CronExpression(cronExp);
        }
        catch (ParseException e)
        {
            return "corn表达式不正确";
        }
        String[] tmpCorns = cronExp.split(" ");
        String number = "";
        if(tmpCorns.length == 7)
        {
            //解析日
            if(!tmpCorns[3].equals("?"))
            {
                if(!tmpCorns[3].equals("*"))
                {
                    number = String.valueOf(Integer.valueOf(tmpCorns[3]) * 1440);
                }
            }

            //解析时
            if(!tmpCorns[2].equals("*"))
            {
                number = String.valueOf(Integer.valueOf(tmpCorns[2]) * 60);
            }

            //解析分
            if(!tmpCorns[1].equals("*") && !tmpCorns[1].equals("0"))
            {
                String[] minTemp = tmpCorns[1].split("/");
                number = minTemp[1];
            }

            //解析秒
            if(!tmpCorns[0].equals("*") && !tmpCorns[0].equals("0"))
            {
                String[] secTemp = tmpCorns[0].split("/");
                number = String.format("%.2f",Long.valueOf(secTemp[1])/60.0);
            }
        }

        return number;
    }
    public static void main(String[] args){
        String CRON_EXPRESSION = "0/20 0 * * * ? *";
        String abc = translate(CRON_EXPRESSION);
        System.out.println(abc);
    }
}

package nirvana.hall.api.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class ExceptionUtil {

    /**
     * 获取异常信息
     * @param e 异常
     */
    public static String getStackTraceInfo(Exception e) {
        String info = "";
        Writer writer = null;
        PrintWriter printWriter = null;
        try {
            writer = new StringWriter();
            printWriter = new PrintWriter(writer);
            e.printStackTrace(printWriter);
            info = writer.toString();
//            if(info.length() > 4000){
//                info = info.substring(0, 4000);
//            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        return info;
    }

}
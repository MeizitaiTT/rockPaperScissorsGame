import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tanghaojie on 2020/8/29
 */
public class Logger {

    private static final String logFile = "game.log";
    private static final String resultFile = "result.txt";
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static String LS = System.getProperty("line.separator");//获取系统参数，进行换行


    public static void error(String msg){
        info(msg);
    }
    public static void info(String msg){
        String content = nowTime() + " " + msg + LS;
        FileUtil.appendFile(logFile, content);
    }

    public static void logResult(String msg){
        String content = nowTime() + " " + msg + LS;
        FileUtil.appendFile(resultFile, content);
    }

    public static String nowTime(){
        Date now = new Date();
        return sdf.format(now);
    }
}

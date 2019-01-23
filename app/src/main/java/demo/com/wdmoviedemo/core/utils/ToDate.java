package demo.com.wdmoviedemo.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者: Wang on 2019/1/1 16:30
 * 寄语：加油！相信自己可以！！！
 */

public class ToDate {
    public static String timedate(long time) {
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd   HH:mm");
        return format2.format(new Date(time));
    }

    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }
}

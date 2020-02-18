import reflection.TTT;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TestTimeForJava8 {
    public static void main(String[] args) {
        testYYYY();
    }

    /**
     * YYYY和yyyy的区别:YYYY会跨年，yyyy不会
     * YYYY:只好本周跨年，那么本周就算入下一年，只变年份，其他不变
     * yyyy:正正经经的
     * MM:月份
     * mm:分钟
     */
    private static void testYYYY() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.DECEMBER, 31);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat2 = new SimpleDateFormat("YYYY-MM-dd");
        System.out.println(dateFormat.format(calendar.getTime())); //2019-12-31
        System.out.println(dateFormat2.format(calendar.getTime())); //2020-12-31
    }

    /**
     * java8的日期apis
     */
    private static void testJava8() {
        //Instant 时刻
        //获取当前时间
        Instant instant = Instant.now();
        println(instant);

        //获取当前时间的另一种方式
        Instant instant1 = Instant.ofEpochMilli(System.currentTimeMillis());
        println(instant1);

        //和Date的转换
        //Instant传Date
        Date date = new Date(Instant.now().toEpochMilli());
        println("Instant parse to data: " + date);
        //Date传Instant
        Instant instant2 = new Date().toInstant();
        println(instant2);
        Instant instant3 = Instant.ofEpochMilli(new Date().getTime());
        println(instant3);

        println(instant3.atZone(ZoneId.of(TimeZone.getDefault().getID())));

        //ZonedDateTime 直接附加时区信息
        println(ZonedDateTime.now());
    }

    private static void println(Object object) {
        System.out.println(object);
    }
}

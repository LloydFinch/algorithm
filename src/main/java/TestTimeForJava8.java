import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

public class TestTimeForJava8 {
    public static void main(String[] args) {
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

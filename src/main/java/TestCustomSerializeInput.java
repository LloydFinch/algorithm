import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class TestCustomSerializeInput {

    public static void main(String[] args) {

    }


    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Label {
        String value() default "";
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Format {
        String pattern() default "yyyy-MM-dd: HH:mm:ss";

        String timeZone() default "GMT+8";
    }
}

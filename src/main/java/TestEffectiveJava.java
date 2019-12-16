import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TestEffectiveJava {


    public static void main(String[] args) {

        test1();

//        System.out.println(Integer.MAX_VALUE);
//        String a = "199999999";
//        System.out.println(String.valueOf(Integer.parseInt(a)));

        int a = 9744;
        int right = 1 << 13;
        System.out.println(a & right);

        long number = 1 << 13;
        System.out.println(number);

        System.out.println("=====================");


        System.out.println(Long.MAX_VALUE);
        long max = (1L << 63);
        System.out.println(max);

        System.out.println("=====================");

        double row = Math.log(Long.MAX_VALUE) / Math.log(2);
        System.out.println(row);

        testMaxLong();
    }

    public TestEffectiveJava() {
    }

    private static void test1() {
//        boolean b1 = true;
//        boolean b2 = true;
//
//        Boolean B1 = Boolean.valueOf(b1);
//        Boolean B2 = Boolean.valueOf(b2);
//
//        System.out.println(B1 == B2);

        List<User> users = new ArrayList<>();
        int count = 10;
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.name = "user" + i;
            user.address = "address" + i;
            users.add(user);
        }

        List<User> users1 = new ArrayList<>(users.size());
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.name = "user1" + i;
            user.address = "address1" + i;
            users1.add(user);
        }

        Collections.copy(users1, users);

        for (User user : users) {
            user.name = "hello";
        }
        users1.forEach(user -> System.out.println(user.name));

    }


    public static class User {
        public String name;
        public String address;

    }


    private static void testMaxLong() {
//        long sup = Long.MAX_VALUE;
//        int sum = 0;
//        while (sup > 0L) {
//            sup = sup / 2;
//            sum++;
//        }
//
//        System.out.println(sum);

        int number = 100;
        for (int i = 0; i < number; i++) {
            long a = (1L << i);
            System.out.println("1<<" + i + " is " + a);
        }
    }


    public static class Processon extends AbstractProcessor {

        @Override
        public synchronized void init(ProcessingEnvironment processingEnv) {
            super.init(processingEnv);
        }

        @Override
        public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

            return false;


        }
    }

}

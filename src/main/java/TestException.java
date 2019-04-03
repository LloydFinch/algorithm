import java.io.*;
import java.util.List;

public class TestException<T> {

    public static void main(String[] args) {
//        println(testFinally());
        testFinally();
    }


    private static void testFinally() {

        try {
            int a = 5 / 0;
//            return a;
        } finally {
            println("finally");
//            return 2;
            throw new IllegalArgumentException("hello");
        }
    }


    private static void testTryWithResources() throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File("")); FileInputStream fileInputStream = new FileInputStream(new File(""))) {
            fileOutputStream.close();

        }
    }

    private static void println(Object o) {
        System.out.println(o);
    }

    public <E> void test(List<? super T> t) {

    }
}

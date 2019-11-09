public class TestEffectiveJava {


    public static void main(String[] args) {

        test1();
    }

    private static void test1() {
        boolean b1 = true;
        boolean b2 = true;

        Boolean B1 = Boolean.valueOf(b1);
        Boolean B2 = Boolean.valueOf(b2);

        System.out.println(B1 == B2);
    }

}

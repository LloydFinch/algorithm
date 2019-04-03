/**
 * 测试类加载
 */
public class TestClass {

    public static void main(String[] args) {
//        A a = new A();
//        loadClass(A.class);

//        AA aa = new AA();
//        loadClass(AA.class);

        //测试多态
        A a = new AA();
        println(a.getValue()); //拿的是AA的成员方法
        println(a.b); //拿的是A的成员变量
    }

    public static class A {
        int a = 1;
        int b = 2;

        {
            //第二个执行
            b = 10;
            println("A init, a=" + a + ", b=" + b);
        }

        static {
            //第一个执行
            println("A static init");
//            println("static init, a=" + a + ", b=" + b);
        }

        public A() {
            //第三个执行
            a = 5;
            b = 5;
            println("A constructor, a=" + a + ", b=" + b);

        }

        public int getValue() {
            println("A getValue=" + b);
            return b;
        }
    }

    public static class AA extends A {

        int b = 100;
        int c = 3;
        int d = 4;

        {
            //第二个执行
//            b = 11;
            println("AA init, a=" + a + ", b=" + b + ", c=" + c + ", d=" + d);
        }

        static {
            //第一个执行
            println("AA static init");
//            println("static init, a=" + a + ", b=" + b);
        }

        public AA() {
            //第三个执行
            a = 6;
            b = 6;
            println("AA constructor, a=" + a + ", b=" + b + ", c=" + c + ", d=" + d);
        }

        public int getValue() {
            println("AA getValue=" + b);
            return b;
        }
    }

    private static void loadClass(Class claz) {
        try {
            Class.forName(claz.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void println(Object o) {
        System.out.println(o);
    }
}

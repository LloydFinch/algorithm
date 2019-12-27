public class ATest {

    public static void main(String[] args) {

        testPackage();
    }

    /**
     * 因为泛型擦除，这两个方法的参数会被认为是一样的，编译不通过
     */
//    public int tra(List<String> sources) {
//
//        return 0;
//    }
//
//    public String tra(List<Integer> sources) {
//
//        return "";
//    }
    private void testLoop() {
    }

    private static void testPackage() {
        Integer a = 1;
        Integer b = 2;
        Integer c = 127;
        Integer d = 127;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        Integer h = 100;
        Integer i = 221;

        /**
         * 1 个byte之内的属于内定常量，都相同
         * 2 算数运算会自动拆箱，所以值==等价于equals
         * 3 不同类型的equals不相同，比如Long.equals(Integer)，因为有类型判定，可以重写equals方法修改逻辑
         */

        System.out.println(c == d); //true
        System.out.println(e == f); //false
        System.out.println(c == (a + b)); //true
        System.out.println(c.equals(a + b)); //true
        System.out.println(e.equals(f)); //true
        System.out.println(e == (h + i)); //true
        System.out.println(e.equals(h + i)); //true
        System.out.println(g == (a + b)); //true
        System.out.println(g.equals(a + b)); //false

    }
}

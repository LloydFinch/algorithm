public class TestLambda {

    public static void main(String[] args) {
        ((Runnable) () -> {

        }).run();


        ((TLambda) () -> {

        }).call();
    }

    /**
     * 只有一个抽象方法的接口，就是函数式接口，可以使用lambda表示
     */
    @FunctionalInterface //这个注解不是必须的，只是显式的告诉:这是一个函数式接口
    public interface TLambda {
        void call();
    }
}

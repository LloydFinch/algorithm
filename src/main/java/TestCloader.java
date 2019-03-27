import java.io.*;
import java.util.ArrayList;

public class TestCloader {
    public static void main(String[] args) {

        ClassLoader classLoader = Loader.class.getClassLoader();

//        System.out.println(classLoader.getParent().getParent());

        //循环遍历父ClassLoader
        while (classLoader != null) {
            System.out.println(classLoader);
            classLoader = classLoader.getParent();
        }

        System.out.println(String.class.getClassLoader());
        System.out.println(ClassLoader.getSystemClassLoader());

        ClassLoader loader = ClassLoader.getSystemClassLoader();
        try {
            Class<?> aClass = loader.loadClass("java.util.ArrayList");
            ClassLoader classLoader1 = aClass.getClassLoader();
            System.out.println(classLoader1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("=======================================");
        test();
    }

    /**
     * 对比Class.forName和ClassLoader.loadClass的区别
     */
    private static void test() {
        try {
            //通过Class.forName实现，会执行初始化代码
//            Class.forName(Loader.class.getName());

            //通过ClassLoader.loadClass实现，不会执行初始化代码
            ClassLoader.getSystemClassLoader().loadClass(Loader.class.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static class Loader {
        static {
            System.out.println("loader static code block");
        }
    }


    /**
     * 自定义ClassLoader
     */
    public class MyClassLoader extends ClassLoader {
        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            return super.findClass(name);
        }
    }

    /**
     * 这里从文件读取数据写成一个class
     */
    private static Loader getClassFromFile(File file) {
        //file的方法
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
            Object o = objectInputStream.readObject();
            //这个Object可以强制转换成需要的类来使用
        } catch (Exception e) {
            e.printStackTrace();
        }

        //反射的方法
//        Class.forName();

        return null;
    }
}

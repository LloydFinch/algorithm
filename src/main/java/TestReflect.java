import javax.lang.model.element.NestingKind;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class TestReflect {

    public static void main(String args[]) {
//        getName(String[].class);
//        getField();
//        getMethod();
//        testConstructor();
//        testInstance();
//        testClassType();
        test();
    }


    /**
     * 获取Class对象的三种方法
     */
    private static void testClass() {
        //getClass()
        Object o = new Object();
        o.getClass();

        //.class
        Class<Object> aClass = Object.class;
        Class<Integer> integerClass = int.class;
        Class<Void> voidClass = void.class; //void也有class

        //通过Class.forName()获取
        try {
            Class<?> aClass1 = Class.forName("java.util.HashMap");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * getName的四种类型的区别
     */
    private static void getName(Class claz) {
        println(claz.getName());
        println(claz.getSimpleName());
        println(claz.getCanonicalName());
        println(claz.getPackage());
    }

    /**
     * field测试
     */
    private static void getField() {
        List<String> list = Collections.emptyList();
        Class<? extends List> aClass = list.getClass();
        Field[] fields = aClass.getDeclaredFields();//获取所有本类证明的字段，包括非public的
        for (Field field : fields) {
            field.setAccessible(true); //设置非public可访问性
            try {
                println(field.getName() + " " + field.get(aClass));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            println(field.getType());
            int modifiers = field.getModifiers();
            println(Modifier.toString(modifiers));
            println(Modifier.isFinal(modifiers));
            println(Modifier.isPrivate(modifiers));
        }
    }

    /**
     * Method测试
     */
    private static void getMethod() {
        Class<Integer> claz = Integer.class;
        try {
            //获取parseInt()方法
            Method method = claz.getMethod("parseInt", new Class[]{String.class});
            println(method.getName());
            println((Integer) method.invoke(claz, "123") + (Integer) method.invoke(claz, "100"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor测试
     */
    public static void testConstructor() {
        //newInstance
        try {
            Map<String, String> map = HashMap.class.newInstance();
            map.put("hello", "world");
            println(map);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            Constructor<StringBuilder> constructor = StringBuilder.class.getConstructor(new Class[]{int.class});
            StringBuilder stringBuilder = constructor.newInstance(100);
            stringBuilder.append(110);
            println(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试类型转换
     */
    public static void testInstance() {
        ArrayList<String> list = new ArrayList<>();
        println(list instanceof List);
        println((ArrayList<String>) list);
        try {
            Class claz = Class.forName("java.util.ArrayList");
            println(claz.cast(list)); //强制类型转换
            println(claz.isInstance(list)); //list是否是calz类型

            //检测参数能否赋给claz
            println(claz.isAssignableFrom(String.class));
            println(claz.isAssignableFrom(ArrayList.class));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试Class的类型信息
     */
    private static void testClassType() {

        /**
         * 本地类，定义在方法内部的非匿名类
         */
        class LocalClass {

        }
        println("本地类: " + LocalClass.class.isLocalClass());

        //ture
        boolean b = new Runnable() {
            @Override
            public void run() {

            }
        }.getClass().isAnonymousClass();
        //false
//        boolean b = ((Runnable) () -> {
//            //
//        }).getClass().isAnonymousClass();
        println("匿名内部类: " + b);

        println("成员类: " + MemberClass.class.isMemberClass());

        println(int.class.isPrimitive());
        println(int[].class.isArray());
        println(Runnable.class.isInterface());
        println(Week.class.isEnum());
        println(Override.class.isAnnotation());

        //获取父类
        println(HashMap.class.getSuperclass());
        println(Object.class.getSuperclass());
        //获取接口
        println(Thread.class.getInterfaces().getClass().getCanonicalName());

    }

    /**
     * 测试
     */
    private static void test() {
        try {
            Field[] fields = MemberClass.class.getDeclaredFields();
            MemberClass memberClass = MemberClass.class.newInstance();
            for (Field field : fields) {
                if (!field.isAccessible())
                    field.setAccessible(true);
                Class<?> type = field.getType();

                //这里set的时候，值必须和类型一样才行，麻烦之处...
                if (type == String.class) {
                    field.set(memberClass, "hello");
                } else if (type == int.class) {
                    field.set(memberClass, 10);
                }
            }

            println(memberClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void println(Object o) {
        System.out.println(o);
    }

    /**
     * 成员类
     */
    static class MemberClass {
        String name;
        int age;

        @Override
        public String toString() {
            return "MemberClass{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    enum Week {
        Monday,
        Tuesday;
    }
}

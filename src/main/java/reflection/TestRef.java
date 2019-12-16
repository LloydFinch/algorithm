package reflection;

import java.lang.reflect.Constructor;

public class TestRef {

    public static void main(String[] args) {

        try {
            Class<?> aClass = Class.forName(TTT.class.getCanonicalName());
            Constructor<?>[] constructors = aClass.getDeclaredConstructors();
            for (Constructor<?> constructor : constructors) {
                constructor.setAccessible(true);
                System.out.println("set constructor accessible");
            }
            Thread.sleep(1000);
            Object o = aClass.newInstance(); //要有默认构造器
            System.out.println(o.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println(Boolean.class);
        System.out.println(Boolean.TYPE);
    }

}

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestAnnotation {


    @Inherited //使用这玩意修饰，这个注解就可以被实现该注解的类的孩子继承
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Test {

    }

    @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface ViewInit {
        int getID() default 0;
    }

    @Test
    static class Parent {
    }

    static class Child extends Parent {

        //把注解用于成员变量
        @ViewInit(getID = 10)
        int anInt = 10;

        public void test() {

            //把注解用于本地变量
            @ViewInit(getID = 100)
            int test = 10;
        }
    }


    public static void main(String[] args) {
        //Child继承了Parent，Parent实现了注解@TestCollectionAPI，而Test注解被@Inherited修饰，所以可以被继承，Child就有了这个注解
        System.out.println(Child.class.isAnnotationPresent(Test.class));
        Annotation[] annotations = Child.class.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }

        //这里在运行时，获取Child的注解，然后获取这个注解的值
        ViewInit annotation = Child.class.getAnnotation(ViewInit.class);
        if (annotation != null) {
            //获取这个id值，假如现在是android的View，并且这个id是xml里面的id
            //这里就可以findViewById(id)找到，然后再添加一个参数传递view的类型即可
            int id = annotation.getID();
        }

        getAnnotationDemo();
    }

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface QueryParam {
        String name() default "";
    }

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DefaultValue {
        String value() default "";
    }

    /**
     * 获取参数的注解信息
     */
    public static void getAnnotationDemo() {
        try {
            Method method = TTAnotation.class.getMethod("hello", String.class, String.class);
            //参数的注解是二维数组
            Annotation[][] annotations = method.getParameterAnnotations();
            for (int i = 0; i < annotations.length; i++) {
                Annotation[] annotation = annotations[i];
                for (Annotation a : annotation) {
                    if (a instanceof QueryParam) {
                        System.out.println(a + " = " + ((QueryParam) a).name());
                    } else if (a instanceof DefaultValue) {
                        System.out.println(a + " = " + ((DefaultValue) a).value());
                    }
                }
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public class TTAnotation {
        //这个方法用注解修饰的风骚一点
        public void hello(@QueryParam(name = "java") String name, @QueryParam(name = "android") @DefaultValue(value = "kotlin") String world) {

        }
    }
}


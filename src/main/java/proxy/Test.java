package proxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Name: Test
 * Author: lloydfinch
 * Function: //TODO
 * Date: 2020-04-20 18:03
 * Modify: lloydfinch 2020-04-20 18:03
 */
public class Test {


    public static void main(String[] args) {

//        // 根据运行环境获取表名
//        String tableName = getTable();
//        // 获取 Test 上的注解
//        Table annoTable = Test.class.getAnnotation(Table.class);
//
//        if (annoTable == null) {
//            throw new RuntimeException("please add @Table for Test");
//        }
//        // 获取代理处理器
//        InvocationHandler invocationHandler = Proxy.getInvocationHandler(annoTable);
//        // 获取私有 memberValues 属性
//        Field f = invocationHandler.getClass().getDeclaredField("memberValues");
//        f.setAccessible(true);
//        // 获取实例的属性map
//        Map<String, Object> memberValues = (Map<String, Object>) f.get(invocationHandler);
//        // 修改属性值
//        memberValues.put("name", tableName);


        Test test = new Test();
        test.changeAnnotationByProxy();

    }


    private void changeAnnotationByProxy() {

        TestAnnotation annotation = this.getClass().getAnnotation(TestAnnotation.class);
        if (annotation != null) {

        }

        System.out.println(annotation);
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TestAnnotation {

        int value() default 0;
    }

    public static class Table {

        @TestAnnotation(value = 10)
        public void println() {

        }
    }
}

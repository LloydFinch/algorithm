package dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestDynamic {

    public static void main(String[] args) {
        //编译正常，运行时异常
        //int[][][] arr = new int[1][0][-1];

        IHello iHello = (IHello) new DynamicProxy().bind(new Hello());
        iHello.sayHello();
    }


    public interface IHello {
        void sayHello();
    }

    public static class Hello implements IHello {
        @Override
        public void sayHello() {
            System.out.println("hello, world");
        }
    }

    public static class DynamicProxy implements InvocationHandler {

        public Object originObj;

        public Object bind(Object obj) {
            this.originObj = obj;

            return Proxy.newProxyInstance(originObj.getClass().getClassLoader(), originObj.getClass().getInterfaces(), this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("welcome");
            return method.invoke(originObj, args);
        }
    }
}

package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Name: TestProxyInvokeHandler
 * Author: lloydfinch
 * Function: 动态代理
 * Date: 2020-04-23 10:33
 * Modify: lloydfinch 2020-04-23 10:33
 */
public class TestProxyInvokeHandler {
    public static void main(String[] args) {
        TestProxyInvokeHandler test = new TestProxyInvokeHandler();
        test.testInvocationHandler();
    }

    /**
     * 静态代理
     */
    private void test() {
        IService service = new ServiceProxy(new ServiceImpl());
        service.sayHello();
    }

    /**
     * 动态代理
     */
    private void testInvocationHandler() {
        IService service = new ServiceImpl();
        System.out.println("service is " + service);
        IService iService = (IService) Proxy.newProxyInstance(IService.class.getClassLoader(), new Class[]{IService.class}, new TInvocationHandler(service));
        iService.sayHello();
    }


    interface IService {
        void sayHello();
    }

    /**
     * 被代理类
     */
    static class ServiceImpl implements IService {
        @Override
        public void sayHello() {
            System.out.println("sayHello in ServiceImpl");
        }
    }

    /**
     * 静态代理类
     */
    static class ServiceProxy implements IService {
        private IService realService;

        public ServiceProxy(IService realService) {
            this.realService = realService;
        }

        @Override
        public void sayHello() {
            if (realService != null) {
                System.out.println("before sayHello");
                realService.sayHello();
                System.out.println("after sayHello");
            }
        }
    }

    /**
     * 动态代理类
     */
    static class TInvocationHandler implements InvocationHandler {

        private Object object;

        public TInvocationHandler(Object object) {
            this.object = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object obi = proxy;
            int a = 10;
            int b = 20;
            int c = 30;

            Object result = method.invoke(object, args);
            System.out.println(result);
            return result;
        }
    }
}

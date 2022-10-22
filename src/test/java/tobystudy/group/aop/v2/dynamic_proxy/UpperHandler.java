package tobystudy.group.aop.v2.dynamic_proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UpperHandler implements InvocationHandler {

    private Hello hello;

    public UpperHandler(Hello hello) {
        this.hello = hello;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        String data = (String) method.invoke(hello, objects);
        return data.toUpperCase();
    }
}

package tobystudy.group.aop.v2.dynamic_proxy;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class JdkProxyTest {


    @Test
    public void proxyTest() {
        //given
        Hello hello = (Hello) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{Hello.class}, new UpperHandler(new HelloTarget()));
        //when
        //then
        System.out.println("hello.getClass() = " + hello.getClass());
        assertThat(hello.sayHello("hy")).isEqualTo("HELLO HY");
        assertThat(hello.sayHi("hy")).isEqualTo("HI HY");
        assertThat(hello.sayThankYou("hy")).isEqualTo("THANKYOU HY");

    }
}

package tobystudy.group.aop.v2.proxy_factory_bean;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class ProxyBeanTest {

    @Test
    public void proxyFactory() {
        //given
        ProxyFactoryBean pf = new ProxyFactoryBean();
        pf.setTarget(new HelloTarget());
        pf.addAdvice(new UpperHandler());
        //when
        Hello proxy = (Hello) pf.getObject();
        assertThat(proxy.sayHello("hy")).isEqualTo("HELLO HY");
        assertThat(proxy.sayHi("hy")).isEqualTo("HI HY");
        assertThat(proxy.sayThankYou("hy")).isEqualTo("THANKYOU HY");
        //then
    }

    @Test
    public void pointcutTest() {
        //given
        ProxyFactoryBean pf = new ProxyFactoryBean();
        pf.setTarget(new HelloTarget());
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*");
        pf.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UpperHandler()));
        Hello proxy = (Hello) pf.getObject();

        //when
        //then
        assertThat(proxy.sayHello("hy")).isEqualTo("HELLO HY");
        assertThat(proxy.sayHi("hy")).isEqualTo("HI HY");
        assertThat(proxy.sayThankYou("hy")).isEqualTo("ThankYou hy");

    }


    static class UpperHandler implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            String data = (String) invocation.proceed();
            return Objects.requireNonNull(data).toUpperCase();
        }
    }
}

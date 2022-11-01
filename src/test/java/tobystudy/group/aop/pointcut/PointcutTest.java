package tobystudy.group.aop.pointcut;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import tobystudy.group.aop.v2.proxy_factory_bean.Hello;
import tobystudy.group.aop.v2.proxy_factory_bean.HelloTarget;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class PointcutTest {
    @Test
    public void classNamePointcutAdvisor() {
        //given
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut() {
            @Override
            public ClassFilter getClassFilter() {
                return clazz -> clazz.getSimpleName().startsWith("HelloT");
            }
        };

        pointcut.setMappedName("sayH*");
        //when

        checkAdvice(new HelloTarget(), pointcut, true);
        checkAdvice(new HelloWorld(), pointcut, false);
        checkAdvice(new HelloTT(), pointcut, true);
        //then

    }

    private void checkAdvice(Object target, Pointcut pointcut, boolean adviced) {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(target);
        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UpperHandler()));
        Hello proxy = (Hello) pfBean.getObject();
        if (adviced) {
            assertThat(proxy.sayHello("hy")).isEqualTo("HELLO HY");
            assertThat(proxy.sayHi("hy")).isEqualTo("HI HY");
            assertThat(proxy.sayThankYou("hy")).isEqualTo("ThankYou hy");
        } else {
            assertThat(proxy.sayHello("hy")).isEqualTo("Hello hy");
            assertThat(proxy.sayHi("hy")).isEqualTo("Hi hy");
            assertThat(proxy.sayThankYou("hy")).isEqualTo("ThankYou hy");
        }

    }

    static class UpperHandler implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            String data = (String) invocation.proceed();
            return Objects.requireNonNull(data).toUpperCase();
        }
    }

    static class HelloWorld implements Hello {
        @Override
        public String sayHello(String name) {
            return "Hello " + name;
        }

        @Override
        public String sayHi(String name) {
            return "Hi " + name;
        }

        @Override
        public String sayThankYou(String name) {
            return "ThankYou " + name;
        }

    }

    static class HelloTT implements Hello {
        @Override
        public String sayHello(String name) {
            return "Hello " + name;
        }

        @Override
        public String sayHi(String name) {
            return "Hi " + name;
        }

        @Override
        public String sayThankYou(String name) {
            return "ThankYou " + name;
        }

    }
}

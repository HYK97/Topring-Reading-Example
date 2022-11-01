package tobystudy.group.aop.pointcut;


import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.NameMatchMethodPointcut;

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

        //then

    }

    private void checkAdvice(Object target, Pointcut pointcut, boolean adviced) {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(target);


    }
}

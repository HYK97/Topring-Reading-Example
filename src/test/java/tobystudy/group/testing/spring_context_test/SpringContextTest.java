package tobystudy.group.testing.spring_context_test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import tobystudy.group.testing.TestConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = TestConfiguration.class)
public class SpringContextTest {

    static ApplicationContext testObject = null;
    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void test1() {
        //given
        log.info("testObject = {} ", testObject);
        log.info("applicationContext = {} ", applicationContext);
        //when
        assertThat(testObject == null || this.applicationContext == testObject).isTrue();
        testObject = this.applicationContext;
        //then
    }

    @Test
    public void test2() {
        //given
        log.info("testObject = {} ", testObject);
        log.info("applicationContext = {} ", applicationContext);
        //when
        assertThat(testObject == null || this.applicationContext == testObject).isTrue();
        testObject = this.applicationContext;
        //then
    }

    @Test
    public void test3() {
        //given
        log.info("testObject = {} ", testObject);
        log.info("applicationContext = {} ", applicationContext);
        //when
        assertThat(testObject == null || this.applicationContext == testObject).isTrue();
        testObject = this.applicationContext;
        //then
    }
}

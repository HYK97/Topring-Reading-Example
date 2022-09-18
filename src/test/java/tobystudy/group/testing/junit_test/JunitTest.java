package tobystudy.group.testing.junit_test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
public class JunitTest {

    static Set<JunitTest> junitTest = new HashSet<>();
    static Object Holder;

    @Test
    public void test1() {
        //given
        log.info("junitTest = {} ", junitTest);
        log.info("this = {} ", this);
        assertThat(junitTest).doesNotContain(this);
        //when
        //then
        junitTest.add(this);
        Holder = this;
    }

    @Test
    public void test2() {
        //given
        log.info("junitTest = {} ", junitTest);
        log.info("this = {} ", this);
        assertThat(junitTest).doesNotContain(this);
        //when
        //then
        junitTest.add(this);
    }

    @Test
    public void test3() {
        //given
        log.info("junitTest = {} ", junitTest);
        log.info("this = {} ", this);
        assertThat(junitTest).doesNotContain(this);
        //when
        //then
        junitTest.add(this);
    }

    @Test
    public void contains() {
        //given
        junitTest.add(this);
        Holder = this;
        //when
        //then
        assertThat(junitTest).contains(this);
    }

    @Test
    public void isInTest() {
        //given
        Holder = this;
        //when
        //then
        assertThat(junitTest).doesNotContain(this);

    }
}

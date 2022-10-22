package tobystudy.group.aop.v2.reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {

    @Test
    public void reflectionTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //given
        String refl = "String";
        assertThat(refl.length()).isEqualTo(6);
        Method lengthMethod = refl.getClass().getMethod("length");
        int length = (int) lengthMethod.invoke(refl);
        assertThat(length).isEqualTo(6);
        //when

        //then

    }
}

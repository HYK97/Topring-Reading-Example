package tobystudy.group.template.test_calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class CalcSumTest {

    Calculator calculator;
    String path;

    @BeforeEach
    public void setup() {
        this.calculator = new Calculator();
        this.path = this.getClass().getResource("").getPath() + "numbers.txt";
    }

    @Test
    public void sumOfNumbers() throws IOException {
        //given
        //when
        //then
        assertThat(calculator.calcSum(path)).isEqualTo(10);
    }

    @Test
    public void multiplyOfNumbers() throws IOException {
        //given
        //when
        //then
        assertThat(calculator.calcMultiply(path)).isEqualTo(24);

    }

    @Test
    public void Concatenate() throws IOException {
        //given
        //when
        //then
        assertThat(calculator.Concatenate(path)).isEqualTo("1234");

    }
}

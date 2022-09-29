package tobystudy.group.template.test_calculator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public Integer calcSum(String path) {
        return fileReadTemplate(path, (i, j) -> i + Integer.parseInt(j), 0);
    }

    public Integer calcMultiply(String path) {
        return fileReadTemplate(path, (i, j) -> i * Integer.parseInt(j), 1);
    }

    public String Concatenate(String path) {
        return fileReadTemplate(path, (i, j) -> i + j, "");
    }

    public <T> T fileReadTemplate(String path, LineCallback<T> lineCallback, T initVal) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            T sum = initVal;
            String line = null;
            while ((line = br.readLine()) != null) {
                sum = lineCallback.operator(sum, line);
            }
            return sum;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("e = " + e);
                }
            }
        }
    }


}

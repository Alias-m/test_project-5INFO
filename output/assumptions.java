import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;

public class assumptions {
    @Test
    public void assume_true() {
        Assume.assumeTrue((1 == 1), () -> {
            System.out.println("Test");
        });
    }

    @Test
    public void assume_false() {
        Assume.assumeTrue((1 == 1), () -> {
            System.out.println("Test");
        });
    }

    @Test
    public void assume_that() {
        String s = "test";
        Assume.assumingThat(s.equals("test"), () -> {
            System.out.println("Test");
        });
    }

    @Test
    public void assume_not_null() {
        Assume.assumeTrue(() -> {
            Object[] tmp_lambda_array = new Object[5];
            tmp_lambda_array[0] = new String("test");
            tmp_lambda_array[1] = 15;
            tmp_lambda_array[2] = null;
            tmp_lambda_array[3] = "test";
            tmp_lambda_array[4] = new Object();
            for (Object o : tmp_lambda_array) {
                if (o == null)
                    return false;
                
            }
            return true;
        }, () -> {
            System.out.println("Test");
        });
    }
}


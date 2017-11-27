

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import spoon.support.reflect.code.CtLiteralImpl;

@ExtendWith(Theories.class)
public class theories {
    @DataPoint
    public static String GOOD_USERNAME = "optimus";

    @DataPoint
    public static String USERNAME_WITH_SLASH = "optimus/prime";

    @ParameterizedTest
    @CsvSource({ "optimus" , "optimus/prime" })
    public void filenameIncludesUsername(String username) {
    }
}


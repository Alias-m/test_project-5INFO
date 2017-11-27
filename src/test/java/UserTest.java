import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;


@RunWith(Theories.class)
public class UserTest {
    @DataPoint
    public static String GOOD_USERNAME = "optimus";
    @DataPoint
    public static String USERNAME_WITH_SLASH = "optimus/prime";

    @Theory
    @CsvSource({"42","45","48","47","49"})
    public void filenameIncludesUsername(int username) {
        //assumeThat(username, "");
        //assertThat(new User(username).configFileName(), containsString(username));
    }
}
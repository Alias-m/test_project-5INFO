import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;

public class AssertThrow {
    @Test(expected = Exception.class)
    public void shouldRaiseAnException() throws Exception
    {
        System.out.println("suis un test");
    }
}
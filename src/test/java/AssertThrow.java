import org.junit.Test;

public class AssertThrow {
    @Test(expected = Exception.class)
    public void shouldRaiseAnException() throws Exception
    {
        System.out.println("suis un test");
    }
}
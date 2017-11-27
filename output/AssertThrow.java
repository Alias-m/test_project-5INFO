

import org.junit.Test;
import org.junit.jupiter.api.Test;

public class AssertThrow {
    @Test
    public void shouldRaiseAnException() throws Exception {
        assertThrows(Exception.class, () -> {
            System.out.println("suis un test");
        });
    }
}


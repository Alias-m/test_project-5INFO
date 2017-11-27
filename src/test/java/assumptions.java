package part2;

import org.junit.*;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;


public class assumptions {
    @Test
    public void assume_true() {
        assumeTrue(1 == 1);
        System.out.println("Test");
    }
    @Test
    public void assume_false() {
        assumeTrue(1 == 1);
        System.out.println("Test");
    }
    @Test
    public void assume_that() {
        String s = "test";
        assumeThat(s, is("test"));
        System.out.println("Test");
    }
    @Test
    public void assume_not_null() {
        assumeNotNull(new String("test"), 15, null, "test", new Object());
        System.out.println("Test");
    }
}
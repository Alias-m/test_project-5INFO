import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@Ignore
public class annotations {
    @Before
    public void before() {
    }
    @After
    public void after() {
    }
    @BeforeClass
    public void beforeclass() {
    }
    @AfterClass
    public void afterclass() {
    }
    @Ignore
    public void ignore() {
    }
    @Category(Ignore.class)
    public void category() {
    }
}
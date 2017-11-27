

import org.junit.Ignore;
import org.junit.experimental.categories.Category;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;

@Disabled
public class annotations {
    @BeforeEach
    public void before() {
    }

    @AfterEach
    public void after() {
    }

    @BeforeAll
    public void beforeclass() {
    }

    @AfterAll
    public void afterclass() {
    }

    @Disabled
    public void ignore() {
    }

    @Tag(Ignore.class)
    public void category() {
    }
}


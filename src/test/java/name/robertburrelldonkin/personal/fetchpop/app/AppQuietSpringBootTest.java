package name.robertburrelldonkin.personal.fetchpop.app;

import static name.robertburrelldonkin.personal.fetchpop.app.AlphaSequence.nextAlphanumeric;
import static name.robertburrelldonkin.personal.fetchpop.app.StandardOutput.STDERR_MARKER;
import static name.robertburrelldonkin.personal.fetchpop.app.StandardOutput.STDOUT_MARKER;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("quiet")
public class AppQuietSpringBootTest {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    @Rule
    public final SystemErrRule systemErrCapture = new SystemErrRule().enableLog();

    @Rule
    public OutputCapture capture = new OutputCapture();

    private String someMessage;

    @Before
    public void setUp() {
        someMessage = nextAlphanumeric();
    }

    @Test
    public void quietProfileIsQuiet() {
        logger.info(MarkerFactory.getMarker("not.out"), someMessage);
        assertThat(capture.toString(), isEmptyString());
    }

    @Test
    public void quietProfilePrintsMarkedMessages() {
        logger.info(STDOUT_MARKER, someMessage);
        assertThat(capture.toString(), is(someMessage + "\n"));
    }

    @Test
    public void quietProfilePrintsErrMarkedMessages() {
        logger.info(STDERR_MARKER, someMessage);
        assertThat(systemErrCapture.getLog(), is(someMessage + "\n"));
    }
}

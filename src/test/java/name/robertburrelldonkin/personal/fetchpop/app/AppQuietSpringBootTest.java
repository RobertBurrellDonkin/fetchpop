package name.robertburrelldonkin.personal.fetchpop.app;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureRule;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static name.robertburrelldonkin.personal.fetchpop.app.AlphaSequence.nextAlphanumeric;
import static name.robertburrelldonkin.personal.fetchpop.app.StandardOutput.STDERR_MARKER;
import static name.robertburrelldonkin.personal.fetchpop.app.StandardOutput.STDOUT_MARKER;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("quiet")
public class AppQuietSpringBootTest {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    @Rule
    public OutputCaptureRule output = new OutputCaptureRule();

    private String someMessage;

    @Before
    public void setUp() {
        someMessage = nextAlphanumeric();
    }

    @Test
    public void quietProfileIsQuiet() {
        logger.info(MarkerFactory.getMarker("not.out"), someMessage);
        assertThat(output.toString(), is(emptyString()));
    }

    @Test
    public void quietProfilePrintsMarkedMessages() {
        logger.info(STDOUT_MARKER, someMessage);
        assertThat(output.toString(), is(someMessage + "\n"));
    }

    @Test
    public void quietProfilePrintsErrMarkedMessages() {
        logger.info(STDERR_MARKER, someMessage);
        assertThat(output.toString(), is(someMessage + "\n"));
    }
}

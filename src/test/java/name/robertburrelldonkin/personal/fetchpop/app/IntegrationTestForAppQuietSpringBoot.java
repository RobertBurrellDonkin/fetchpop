/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Robert Burrell Donkin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
public class IntegrationTestForAppQuietSpringBoot {

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

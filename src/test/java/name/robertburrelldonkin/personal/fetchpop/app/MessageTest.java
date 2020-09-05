package name.robertburrelldonkin.personal.fetchpop.app;

/*
MIT License

Copyright (c) 2018 Robert Burrell Donkin

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

import org.apache.commons.net.pop3.POP3MessageInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;

import java.io.Reader;
import java.io.StringReader;

import static name.robertburrelldonkin.personal.fetchpop.app.AlphaSequence.nextAlphanumeric;
import static name.robertburrelldonkin.personal.fetchpop.app.NumberSequence.nextInt;
import static name.robertburrelldonkin.personal.fetchpop.app.StandardOutput.STDOUT_MARKER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessageTest {

    private static final String SAMPLE_EMAIL =
            "From: John Doe <jdoe@machine.example>\r\n" +
                    "To: Mary Smith <mary@example.net>\r\n" +
                    "Subject: Saying Hello\n" +
                    "Date: Fri, 21 Nov 1997 09:55:06 -0600\r\n" +
                    "Message-ID: <1234@local.machine.example>\r\n" +
                    "\r\n" +
                    "This is a message just to say hello.\r\n" +
                    "So, \"Hello\".\r\n";

    private static final String FOLDED_EMAIL =
            "From: John Doe <jdoe@machine.example>\r\n" +
                    "To: Mary Smith <mary@example.net>\r\n" +
                    "Subject: This\n" +
                    "            is a test\n" +
                    "Date: Fri, 21 Nov 1997 09:55:06 -0600\r\n" +
                    "Message-ID: <1234@local.machine.example>\r\n" +
                    "\r\n" +
                    "This is a message just to say hello.\r\n" +
                    "So, \"Hello\".\r\n";

    @Mock
    private Reader reader;
    @Mock
    private ISession mockSession;
    @Mock
    private Logger mockLogger;
    private int someMessageNumber;
    private POP3MessageInfo someInfo;

    private Message subject;

    @Before
    public void setUp() {
        this.someMessageNumber = nextInt();
        this.someInfo = new POP3MessageInfo(someMessageNumber, nextAlphanumeric());
        when(this.mockSession.retrieveMessage(someMessageNumber)).thenReturn(reader);

        this.subject = new Message(someInfo, mockSession);
    }

    @Test
    public void readShouldRetrieveMessageById() {
        this.subject.read();

        verify(this.mockSession).retrieveMessage(someMessageNumber);
    }

    @Test
    public void readShouldReadRetrievedMessage() {
        assertThat(this.subject.read()).isSameAs(reader);
    }

    @Test
    public void headerFolding() {
        when(this.mockSession.retrieveMessage(someMessageNumber)).thenReturn(new StringReader(FOLDED_EMAIL));

        assertThat(this.subject.headerLines())
                .containsExactly(
                        "From: John Doe <jdoe@machine.example>",
                        "To: Mary Smith <mary@example.net>",
                        "Subject: This is a test",
                        "Date: Fri, 21 Nov 1997 09:55:06 -0600",
                        "Message-ID: <1234@local.machine.example>"
                );
    }

    @Test
    public void headerLines() {
        when(this.mockSession.retrieveMessage(someMessageNumber)).thenReturn(new StringReader(SAMPLE_EMAIL));

        assertThat(this.subject.headerLines())
                .containsExactly(
                        "From: John Doe <jdoe@machine.example>",
                        "To: Mary Smith <mary@example.net>",
                        "Subject: Saying Hello",
                        "Date: Fri, 21 Nov 1997 09:55:06 -0600",
                        "Message-ID: <1234@local.machine.example>"
                );
    }


    @Test
    public void headerName() {
        assertThat(this.subject.headerName("From: John Doe <jdoe@machine.example>")).isEqualTo("From");
    }

    @Test
    public void info() {
        when(this.mockSession.retrieveMessage(someMessageNumber)).thenReturn(new StringReader(SAMPLE_EMAIL));

        assertThat(this.subject.info())
                .containsExactly(
                        "From: John Doe <jdoe@machine.example>",
                        "Subject: Saying Hello",
                        "Message-ID: <1234@local.machine.example>"
                );
    }

    @Test
    public void logInfoShouldLogOutToStdOut() {
        when(this.mockSession.retrieveMessage(someMessageNumber)).thenReturn(new StringReader(SAMPLE_EMAIL));

        subject.logInfo(mockLogger);

        verify(mockLogger).info(STDOUT_MARKER, "{}", "From: John Doe <jdoe@machine.example> Message-ID: <1234@local.machine.example> Subject: Saying Hello");
    }
}

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

import org.apache.commons.net.ProtocolCommandEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;

import static name.robertburrelldonkin.personal.fetchpop.app.AlphaSequence.nextAlphanumeric;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LoggingProtocolCommandListenerTest {

    @Mock
    private Logger mockLogger;

    private LoggingProtocolCommandListener subject;

    private Object source;
    private String replyCode;
    private String message;

    @Before
    public void setUp() throws Exception {
        this.source = new Object();
        this.replyCode = nextAlphanumeric();
        this.message = nextAlphanumeric();
        this.subject = new LoggingProtocolCommandListener(mockLogger);
    }

    @Test
    public void whenEventSentThenLogMessage() {
        this.subject.protocolCommandSent(new ProtocolCommandEvent(this.source, this.replyCode, this.message));

        verify(this.mockLogger).info("-> {}", this.message);
    }

    @Test
    public void whenEventSentIsNullThenLogNull() {
        this.subject.protocolCommandSent(new ProtocolCommandEvent(this.source, this.replyCode, null));

        verify(this.mockLogger).info("-> {}", (Object) null);
    }

    @Test
    public void whenEventReceivedThenLogMessage() {
        this.subject.protocolReplyReceived(new ProtocolCommandEvent(this.source, this.replyCode, this.message));

        verify(this.mockLogger).info("<- {}", this.message);
    }

    @Test
    public void whenEventReceivedIsNullThenLogNull() {
        this.subject.protocolReplyReceived(new ProtocolCommandEvent(this.source, this.replyCode, null));

        verify(this.mockLogger).info("<- {}", (Object) null);
    }
}

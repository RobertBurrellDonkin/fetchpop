package name.robertburrelldonkin.personal.fetchpop.app;

import static name.robertburrelldonkin.personal.fetchpop.app.NumberSequence.nextInt;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.Reader;

import org.apache.commons.net.pop3.POP3SClient;
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
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AuthorizedSessionTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private POP3SClient mockClient;

    private int someMessageNumber;
    @Mock
    private Reader mockReader;

    private ISession subject;

    @Before
    public void setUp() {
        someMessageNumber = nextInt();
        subject = new AuthorizedSession(mockClient);
    }

    @Test
    public void whenStatusFailsThenThrowException() throws IOException {
        thrown.expect(AuthorizedSession.StatusException.class);

        when(mockClient.status()).thenThrow(new IOException());

        this.subject.currentStatus();
    }

    @Test
    public void whenStatusIsNullThenThrowException() throws IOException {
        thrown.expect(AuthorizedSession.MissingStatusException.class);

        when(mockClient.status()).thenReturn(null);

        this.subject.currentStatus();
    }

    @Test
    public void readShouldRetrieveMessageFromMailbox() throws IOException {
        when(mockClient.retrieveMessage(someMessageNumber)).thenReturn(this.mockReader);

        assertThat(this.subject.readMessage(someMessageNumber), is(this.mockReader));

    }

    @Test
    public void whenRetrieveMessageFailsThenThrowException() throws IOException {
        when(mockClient.retrieveMessage(someMessageNumber)).thenThrow(new IOException());
        thrown.expect(AuthorizedSession.MessageRetrievalException.class);

        this.subject.readMessage(someMessageNumber);

    }
}

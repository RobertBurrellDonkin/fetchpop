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

import org.apache.commons.net.pop3.POP3MessageInfo;
import org.apache.commons.net.pop3.POP3SClient;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.Reader;
import java.net.SocketException;

import static java.util.stream.Collectors.toList;
import static name.robertburrelldonkin.personal.fetchpop.app.AlphaSequence.nextAlphanumeric;
import static name.robertburrelldonkin.personal.fetchpop.app.NumberSequence.nextInt;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientSessionTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private POP3SClient mockClient;

    private POP3MessageInfo pop3MessageInfo;
    private POP3MessageInfo[] messages;

    private int someMessageNumber;
    private String userName;
    private String credentials;
    private String hostName;
    private int hostPort;
    private int messageNumber;

    @Mock
    private Reader mockReader;

    private Client subject;

    @Before
    public void setUp() {
        userName = nextAlphanumeric();
        credentials = nextAlphanumeric();
        hostName = nextAlphanumeric();
        hostPort = nextInt();
        messageNumber = nextInt();

        someMessageNumber = nextInt();

        pop3MessageInfo = new POP3MessageInfo();
        pop3MessageInfo.number = someMessageNumber;
        messages = new POP3MessageInfo[]{pop3MessageInfo};

        subject = new Client(mockClient);
    }

    @Test
    public void whenLoginFailsPerformShouldThrowException() throws IOException {
        when(mockClient.login(userName, credentials)).thenReturn(false);
        thrown.expect(FatalRuntimeException.LoginRejectedException.class);

        this.subject.login(userName, credentials);
    }

    @Test
    public void whenConnectFailsPerformShouldThrowException() throws IOException {
        doThrow(new IOException()).when(mockClient).connect(hostName, hostPort);
        thrown.expect(FatalNestedRuntimeException.ConnectionFailedException.class);

        this.subject.connect(hostName, hostPort);
    }

    @Test
    public void whenLoginThrowsExceptionPerformShouldThrowException() throws IOException {
        when(mockClient.login(userName, credentials)).thenThrow(new IOException());
        thrown.expect(FatalNestedRuntimeException.LoginFailedException.class);

        this.subject.login(userName, credentials);
    }

    @Test
    public void whenStatusFailsThenThrowException() throws IOException {
        thrown.expect(FatalNestedRuntimeException.StatusException.class);

        when(mockClient.status()).thenThrow(new IOException());

        this.subject.status();
    }

    @Test
    public void whenStatusIsNullThenThrowException() throws IOException {
        thrown.expect(FatalRuntimeException.MissingStatusException.class);

        when(mockClient.status()).thenReturn(null);

        this.subject.status();
    }

    @Test
    public void readShouldRetrieveMessageFromMailbox() throws IOException {
        when(mockClient.retrieveMessage(someMessageNumber)).thenReturn(this.mockReader);

        assertThat(this.subject.retrieveMessage(someMessageNumber), is(this.mockReader));

    }

    @Test
    public void whenRetrieveMessageFailsThenThrowException() throws IOException {
        when(mockClient.retrieveMessage(someMessageNumber)).thenThrow(new IOException());
        thrown.expect(FatalNestedRuntimeException.MessageRetrievalException.class);

        this.subject.retrieveMessage(someMessageNumber);

    }

    @Test
    public void messagesShouldListMessages() throws Exception {
        when(mockClient.listMessages()).thenReturn(messages);

        assertThat(this.subject.messages().map(Message::getNumber).collect(toList()), contains(someMessageNumber));
    }

    @Test
    public void whenListMessagesFailsThenThrowException() throws IOException {
        when(mockClient.listMessages()).thenThrow(new IOException());
        thrown.expect(FatalNestedRuntimeException.ListMessagesException.class);

        this.subject.messages();

    }

}

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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static name.robertburrelldonkin.personal.fetchpop.app.AlphaSequence.nextAlphanumeric;
import static name.robertburrelldonkin.personal.fetchpop.app.NumberSequence.nextPositiveInt;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

@RunWith(MockitoJUnitRunner.class)
public class AccountTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private Client mockClient;
    @Mock
    private IOperation mockOperation;

    private String userName;
    private String credentials;
    private String hostName;
    private int hostPort;
    private Account subject;

    @Before
    public void setUp() throws Exception {
        userName = nextAlphanumeric();
        credentials = nextAlphanumeric();
        hostName = nextAlphanumeric();
        hostPort = nextPositiveInt();

        subject = new Account(userName, credentials, hostName, hostPort, true);

        when(mockClient.verify()).thenReturn(mockClient);
    }

    @Test
    public void whenAllSetThenAccountIsNotEmpty() {
        assertThat(new Account(userName, credentials, hostName, hostPort, true).isEmpty(), is(false));
    }

    @Test
    public void whenUserIsEmptyStringThenAccountIsEmpty() {
        assertThat(new Account("", credentials, hostName, hostPort, true).isEmpty(), is(true));
    }

    @Test
    public void whenUserIsNullThenAccountIsEmpty() {
        assertThat(new Account(null, credentials, hostName, hostPort, true).isEmpty(), is(true));
    }

    @Test
    public void whenCredIsEmptyStringThenAccountIsEmpty() {
        assertThat(new Account(userName, "", hostName, hostPort, true).isEmpty(), is(true));
    }

    @Test
    public void whenCredIsNullThenAccountIsEmpty() {
        assertThat(new Account(userName, null, hostName, hostPort, true).isEmpty(), is(true));
    }

    @Test
    public void whenHostNameIsEmptyStringThenAccountIsEmpty() {
        assertThat(new Account(userName, credentials, "", hostPort, true).isEmpty(), is(true));
    }

    @Test
    public void whenHostNameIsNullThenAccountIsEmpty() {
        assertThat(new Account(userName, credentials, null, hostPort, true).isEmpty(), is(true));
    }

    @Test
    public void whenHostPortIsZeroThenAccountIsEmpty() {
        assertThat(new Account(userName, credentials, hostName, 0, true).isEmpty(), is(true));
    }

    @Test
    public void whenHostPortIsNegativeThenAccountIsEmpty() {
        assertThat(new Account(userName, credentials, hostName, -1, true).isEmpty(), is(true));
    }

    @Test
    public void performShouldConnectLoginStatusOperationLogoutDisconnect() throws IOException {

        this.subject.perform(mockClient, mockOperation);

        InOrder inOrder = inOrder(mockClient, mockOperation);
        inOrder.verify(mockClient).connect(hostName, hostPort);
        inOrder.verify(mockClient).login(userName, credentials);
        inOrder.verify(mockClient).verify();
        inOrder.verify(mockOperation).operateOn((ISession) argThat(is(notNullValue())));
        inOrder.verify(mockClient).logout();
        inOrder.verify(mockClient).disconnect();
    }
}

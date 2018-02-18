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
import static name.robertburrelldonkin.personal.fetchpop.app.AlphaSequence.nextAlphanumeric;
import static name.robertburrelldonkin.personal.fetchpop.app.NumberSequence.nextInt;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.SocketException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
        hostPort = nextInt();

        subject = new Account(userName, credentials, hostName, hostPort);

        when(mockClient.verify()).thenReturn(mockClient);
    }

    @Test
    public void performShouldConnectLoginStatusOperationLogoutDisconnect() throws SocketException, IOException {

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

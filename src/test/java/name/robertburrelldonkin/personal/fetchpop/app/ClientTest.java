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

import org.apache.commons.net.pop3.POP3Client;
import org.apache.commons.net.pop3.POP3SClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

@RunWith(MockitoJUnitRunner.class)
public class ClientTest {

    @Mock
    private POP3SClient mockPop3;

    private Client subject;

    @Before
    public void setUp() throws Exception {
        this.subject = new Client(this.mockPop3);
    }

    @Test
    public void listenerAddedOnConstruction() {
        verify(this.mockPop3).addProtocolCommandListener(argThat(is(instanceOf(LoggingProtocolCommandListener.class))));
    }

    @Test
    public void useTLS() {
        assertThat(new Client(true).getPop3Client()).isInstanceOf(POP3SClient.class);
    }

    @Test
    public void usePlain() {
        assertThat(new Client(false).getPop3Client()).isExactlyInstanceOf(POP3Client.class);
    }
}

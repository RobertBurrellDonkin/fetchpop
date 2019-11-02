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
import static name.robertburrelldonkin.personal.fetchpop.app.NumberSequence.nextInt;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.apache.commons.net.pop3.POP3MessageInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StatusTest {

    private int someNumberOfMessages;
    private int someMessageBoxSize;
    private POP3MessageInfo someInfo;

    private Status subject;

    @Before
    public void setUp() {
        this.someMessageBoxSize = nextInt();
        this.someNumberOfMessages = nextInt();
        this.someInfo = new POP3MessageInfo(someNumberOfMessages, someMessageBoxSize);
        this.subject = new Status(someInfo);
    }

    @Test
    public void messageBoxSizeInBytes() {
        assertThat(this.subject.getMessageBoxSizeInBytes(), is(someMessageBoxSize));
    }

    @Test
    public void numberOfMessages() {
        assertThat(this.subject.getNumberOfMessages(), is(someNumberOfMessages));
    }

    @Test
    public void messageBoxDisplaySize() {
        assertThat(new Status(new POP3MessageInfo(someNumberOfMessages, 20001230)).getMessageBoxDisplaySize(),
                is("19 MB"));
    }

    @Test
    public void toStringReturnsMessageBoxDisplaySize() {
        assertThat(new Status(new POP3MessageInfo(someNumberOfMessages, 20001230)).toString(), containsString("19 MB"));
    }

    @Test
    public void toStringReturnsMessageBoxSize() {
        assertThat(new Status(new POP3MessageInfo(someNumberOfMessages, 20001230)).toString(),
                containsString("20001230"));
    }

    @Test
    public void toStringReturnsNumberOfMessages() {
        assertThat(new Status(new POP3MessageInfo(2345, 20001230)).toString(), containsString("2345"));
    }
}

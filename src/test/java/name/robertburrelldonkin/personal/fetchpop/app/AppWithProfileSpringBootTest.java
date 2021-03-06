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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("app-test")
public class AppWithProfileSpringBootTest {

    @Autowired
    Account account;

    @Test
    public void whenProfileSetThenUserConfigured() {
        assertThat(account.getUserName(), is("me@example.org"));
    }

    @Test
    public void whenProfileSetThenHostNameConfigured() {
        assertThat(account.getHostName(), is("pop3.example.org"));
    }

    @Test
    public void whenProfileSetThenHostPortConfigured() {
        assertThat(account.getHostPort(), is(999));
    }

    @Test
    public void whenProfileIsSetThenCredentialsConfigured() {
        assertThat(account.digestCredentialsAsSHA(), is("970093678b182127f60bb51b8af2c94d539eca3a"));
    }
}

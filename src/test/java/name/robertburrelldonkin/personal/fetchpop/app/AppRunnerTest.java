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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.ApplicationArguments;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AppRunnerTest {

    @Mock
    Account mockAccount;
    @Mock
    ApplicationArguments args;
    App subject;

    @Before
    public void setUp() {
        subject = new App(mockAccount);
    }

    @Test
    public void whenNoOperationsThenDoNotCallAccount() {
        when(args.getNonOptionArgs()).thenReturn(emptyList());

        this.subject.run(args);

        verifyNoInteractions(mockAccount);
    }

    @Test
    public void whenBogusOperationThenThrowException() {
        when(args.getNonOptionArgs()).thenReturn(singletonList("bogus"));

        assertThrows(IllegalArgumentException.class, () -> this.subject.run(args));
    }

    @Test
    public void whenStatusOperationThenCallAccount() {
        when(args.getNonOptionArgs()).thenReturn(singletonList("status"));

        this.subject.run(args);

        verify(mockAccount).perform(isA(PrintStatusOperation.class));
    }


    @Test
    public void whenInfoOperationThenCallAccount() {
        when(args.getNonOptionArgs()).thenReturn(singletonList("info"));

        this.subject.run(args);

        verify(mockAccount).perform(isA(PrintMessageInfo.class));
    }

    @Test
    public void whenBogusOperationInListThenThrowException() {
        when(args.getNonOptionArgs()).thenReturn(asList("status", "bogus"));

        assertThrows(IllegalArgumentException.class, () -> this.subject.run(args));
    }
}
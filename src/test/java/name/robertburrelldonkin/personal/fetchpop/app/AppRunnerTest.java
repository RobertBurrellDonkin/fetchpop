package name.robertburrelldonkin.personal.fetchpop.app;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.ApplicationArguments;

import java.util.Arrays;
import java.util.Collections;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.instanceOf;
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
    public void whenBogusOperationInListThenThrowException() {
        when(args.getNonOptionArgs()).thenReturn(asList("status", "bogus"));

        assertThrows(IllegalArgumentException.class, () -> this.subject.run(args));
    }
}
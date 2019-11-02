package name.robertburrelldonkin.personal.fetchpop.app;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.ApplicationArguments;

import static java.util.Collections.emptyList;
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
}
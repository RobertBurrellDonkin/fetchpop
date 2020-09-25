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

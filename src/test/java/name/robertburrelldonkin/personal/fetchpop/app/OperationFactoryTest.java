package name.robertburrelldonkin.personal.fetchpop.app;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

class OperationFactoryTest {

    @Test
    void nameToOperationWhenStatus() {
        assertThat(OperationFactory.nameToOperation("status")).isInstanceOf(PrintStatusOperation.class);
    }

    @Test
    void nameToOperationWhenStatusUppercase() {
        assertThat(OperationFactory.nameToOperation("STATUS")).isInstanceOf(PrintStatusOperation.class);
    }
}
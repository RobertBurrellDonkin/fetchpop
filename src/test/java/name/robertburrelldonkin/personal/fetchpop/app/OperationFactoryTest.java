package name.robertburrelldonkin.personal.fetchpop.app;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OperationFactoryTest {

    @Test
    void nameToOperationWhenStatus() {
        assertThat(OperationFactory.nameToOperation("status")).isInstanceOf(PrintStatusOperation.class);
    }

    @Test
    void nameToOperationWhenStatusUppercase() {
        assertThat(OperationFactory.nameToOperation("STATUS")).isInstanceOf(PrintStatusOperation.class);
    }

    @Test
    void nameToOperationWhenInfo() {
        assertThat(OperationFactory.nameToOperation("info")).isInstanceOf(PrintMessageInfo.class);
    }

    @Test
    void nameToOperationWhenInfoUppercase() {
        assertThat(OperationFactory.nameToOperation("INFO")).isInstanceOf(PrintMessageInfo.class);
    }
}
package name.robertburrelldonkin.personal.fetchpop.app;

import java.io.PrintStream;

class PrintStatusOperation implements IOperation {

    private final PrintStream out;

    public PrintStatusOperation(final PrintStream out) {
        super();
        this.out = out;
    }

    @Override
    public void operateOn(final ISession session) {
        session.status().printTo(out);
    }
}

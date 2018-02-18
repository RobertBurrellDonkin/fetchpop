package name.robertburrelldonkin.personal.fetchpop.app;

/**
 * An operation is a series of actions undertaken within the context of a server
 * session.
 */
interface IOperation {
    void operateOn(ISession session);
}

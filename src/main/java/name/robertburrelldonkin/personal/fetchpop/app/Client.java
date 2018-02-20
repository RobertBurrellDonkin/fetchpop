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

import static java.util.Objects.isNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.pop3.POP3MessageInfo;
import org.apache.commons.net.pop3.POP3SClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Adapts and specialises Apache Commons POP3 client.
 */
class Client implements ISession {

    private final Logger logger = LoggerFactory.getLogger(Client.class);

    private final POP3SClient pop3Client;

    public Client() {
        this(new POP3SClient(true));
    }

    public Client(final POP3SClient client) {
        this.pop3Client = client;
        // TODO: debug
        client.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));
    }

    public void logout() {
        try {
            if (!pop3Client.logout()) {
                logger.warn("Continuing after failing to logout from session.");
            }
        } catch (IOException e) {
            logger.warn("Continuing after failing to logout from session.", e);
        }
    }

    public void disconnect() {
        try {
            logger.info("Disconnecting session");
            pop3Client.disconnect();
        } catch (IOException e) {
            logger.warn("Continuing after failing to disconnect session.", e);
        }
    }

    public void connect(final String hostName, final int hostPort) {
        try {
            logger.info("Connecting to host {} port {}...", hostName, hostPort);
            pop3Client.connect(hostName, hostPort);
        } catch (IOException e) {
            throw new FatalNestedRuntimeException.ConnectionFailedException(e);
        }
    }

    public void login(final String userName, final String credentials) {
        try {
            logger.info("Logging in as '{}'...", userName);
            if (!pop3Client.login(userName, credentials)) {
                throw new FatalRuntimeException.LoginRejectedException();
            }
        } catch (IOException e) {
            throw new FatalNestedRuntimeException.LoginFailedException(e);
        }
    }

    @Override
    public Reader retrieveMessage(int someMessageNumber) {
        try {
            return this.pop3Client.retrieveMessage(someMessageNumber);
        } catch (IOException e) {
            throw new FatalNestedRuntimeException.MessageRetrievalException(e);
        }
    }

    @Override
    public Status status() {
        try {
            final POP3MessageInfo status = pop3Client.status();
            if (isNull(status)) {
                throw new FatalRuntimeException.MissingStatusException();
            }
            return new Status(status);
        } catch (IOException e) {
            throw new FatalNestedRuntimeException.StatusException(e);
        }
    }

    public ISession verify() {
        status();
        return this;
    }
}

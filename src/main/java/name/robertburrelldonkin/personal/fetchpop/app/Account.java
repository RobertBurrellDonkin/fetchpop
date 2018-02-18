package name.robertburrelldonkin.personal.fetchpop.app;

import static name.robertburrelldonkin.personal.fetchpop.app.ExitCode.CONNECTION_FAILED;
import static name.robertburrelldonkin.personal.fetchpop.app.ExitCode.LOGIN_FAILED;
import static name.robertburrelldonkin.personal.fetchpop.app.ExitCode.LOGIN_REJECTED;

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
import java.io.IOException;

import org.apache.commons.net.pop3.POP3SClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class Account {

    final static class LoginRejectedException extends FatalRuntimeException {

        private static final long serialVersionUID = -2487518389439340937L;

        public LoginRejectedException() {
            super(LOGIN_REJECTED);
        }
    }

    final static class ConnectionFailedException extends FatalNestedRuntimeException {
        private static final long serialVersionUID = -2617040595479379258L;

        public ConnectionFailedException(final IOException cause) {
            super(CONNECTION_FAILED, cause);
        }
    }

    final static class LoginFailedException extends FatalNestedRuntimeException {

        private static final long serialVersionUID = 8751903558629536749L;

        public LoginFailedException(final IOException cause) {
            super(LOGIN_FAILED, cause);
        }
    }

    private final Logger logger = LoggerFactory.getLogger(Account.class);

    private final String userName;
    private final String credentials;
    private final String hostName;
    private final int hostPort;

    public Account(final String userName, final String credentials, final String hostName, final int hostPort) {
        super();
        this.userName = userName;
        this.credentials = credentials;
        this.hostName = hostName;
        this.hostPort = hostPort;
    }

    public void perform(final POP3SClient client, final IOperation operation) {
        connect(client);
        try {
            login(client);
            try {
                operation.operateOn(new AuthorizedSession(client).verify());
            } finally {
                logout(client);
            }
        } finally {
            disconnect(client);
        }
    }

    private void logout(final POP3SClient client) {
        try {
            if (!client.logout()) {
                logger.warn("Continuing after failing to logout from session.");
            }
        } catch (IOException e) {
            logger.warn("Continuing after failing to logout from session.", e);
        }
    }

    private void disconnect(final POP3SClient client) {
        try {
            logger.info("Disconnecting session");
            client.disconnect();
        } catch (IOException e) {
            logger.warn("Continuing after failing to disconnect session.", e);
        }
    }

    private void connect(final POP3SClient client) {
        try {
            logger.info("Connecting to host {} port {}...", hostName, hostPort);
            client.connect(hostName, hostPort);
        } catch (IOException e) {
            throw new ConnectionFailedException(e);
        }
    }

    private void login(final POP3SClient client) {
        try {
            logger.info("Logging in as '{}'...", userName);
            if (!client.login(userName, credentials)) {
                throw new LoginRejectedException();
            }
        } catch (IOException e) {
            throw new LoginFailedException(e);
        }
    }

}
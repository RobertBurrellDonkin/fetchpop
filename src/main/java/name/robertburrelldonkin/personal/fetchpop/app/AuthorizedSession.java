package name.robertburrelldonkin.personal.fetchpop.app;

import static java.util.Objects.isNull;
import static name.robertburrelldonkin.personal.fetchpop.app.ExitCode.RETREIVE_MESSAGE_CALL_FAILED;
import static name.robertburrelldonkin.personal.fetchpop.app.ExitCode.STATUS_CALL_FAILED;
import static name.robertburrelldonkin.personal.fetchpop.app.ExitCode.STATUS_MISSING;

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
import java.io.Reader;

import org.apache.commons.net.pop3.POP3MessageInfo;
import org.apache.commons.net.pop3.POP3SClient;

class AuthorizedSession implements ISession {

    final class MessageRetrievalException extends FatalNestedRuntimeException {
        private static final long serialVersionUID = -2617040595479379258L;

        public MessageRetrievalException(final IOException cause) {
            super(RETREIVE_MESSAGE_CALL_FAILED, cause);
        }
    }

    final static class MissingStatusException extends FatalRuntimeException {

        private static final long serialVersionUID = 1074390910212122532L;

        MissingStatusException() {
            super(STATUS_MISSING);
        }

    }

    final static class StatusException extends FatalNestedRuntimeException {
        private static final long serialVersionUID = 5024769660178592258L;

        public StatusException(final IOException cause) {
            super(STATUS_CALL_FAILED, cause);
        }
    }

    private final POP3SClient client;

    AuthorizedSession(final POP3SClient client) {
        this.client = client;
    }

    @Override
    public Reader readMessage(int someMessageNumber) {
        try {
            return this.client.retrieveMessage(someMessageNumber);
        } catch (IOException e) {
            throw new MessageRetrievalException(e);
        }
    }

    Status currentStatus() {
        try {
            final POP3MessageInfo status = client.status();
            if (isNull(status)) {
                throw new MissingStatusException();
            }
            return new Status(status);
        } catch (IOException e) {
            throw new StatusException(e);
        }
    }

    public ISession verify() {
        currentStatus();
        return this;
    }

}

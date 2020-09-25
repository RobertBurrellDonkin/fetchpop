/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Robert Burrell Donkin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package name.robertburrelldonkin.personal.fetchpop.app;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.core.NestedRuntimeException;

import java.io.IOException;

import static name.robertburrelldonkin.personal.fetchpop.app.ExitCode.*;

class FatalNestedRuntimeException extends NestedRuntimeException implements ExitCodeGenerator {
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

    final static class MessageRetrievalException extends FatalNestedRuntimeException {
        private static final long serialVersionUID = -2617040595479379258L;

        public MessageRetrievalException(final IOException cause) {
            super(RETRIEVE_MESSAGE_CALL_FAILED, cause);
        }
    }

    final static class ListMessagesException extends FatalNestedRuntimeException {

        public ListMessagesException(final IOException cause) {
            super(LIST_MESSAGES_CALL_FAILED, cause);
        }
    }


    final static class StatusException extends FatalNestedRuntimeException {
        private static final long serialVersionUID = 5024769660178592258L;

        public StatusException(final IOException cause) {
            super(STATUS_CALL_FAILED, cause);
        }
    }

    private static final long serialVersionUID = 5024769660178592258L;

    private final ExitCode exitCode;

    public FatalNestedRuntimeException(final ExitCode code, final Throwable cause) {
        super(code.getDescription(), cause);
        this.exitCode = code;

    }

    @Override
    public int getExitCode() {
        return exitCode.getCode();
    }

}

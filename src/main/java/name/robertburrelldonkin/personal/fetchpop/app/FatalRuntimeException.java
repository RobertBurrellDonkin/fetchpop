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

import org.springframework.boot.ExitCodeGenerator;

import static name.robertburrelldonkin.personal.fetchpop.app.ExitCode.LOGIN_REJECTED;
import static name.robertburrelldonkin.personal.fetchpop.app.ExitCode.STATUS_MISSING;

class FatalRuntimeException extends RuntimeException implements ExitCodeGenerator {

    final static class LoginRejectedException extends FatalRuntimeException {

        private static final long serialVersionUID = -2487518389439340937L;

        public LoginRejectedException() {
            super(LOGIN_REJECTED);
        }
    }

    final static class MissingStatusException extends FatalRuntimeException {

        private static final long serialVersionUID = 1074390910212122532L;

        MissingStatusException() {
            super(STATUS_MISSING);
        }

    }

    private static final long serialVersionUID = -4972664000872524612L;

    private final ExitCode exitCode;

    public FatalRuntimeException(final ExitCode exitCode) {
        super(exitCode.getDescription());
        this.exitCode = exitCode;
    }

    @Override
    public int getExitCode() {
        return exitCode.getCode();
    }

}

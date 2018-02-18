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
import org.springframework.core.NestedRuntimeException;

class FatalNestedRuntimeException extends NestedRuntimeException implements ExitCodeGenerator {
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

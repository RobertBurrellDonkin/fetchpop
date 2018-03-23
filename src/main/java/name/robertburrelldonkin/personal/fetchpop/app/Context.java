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

import static java.util.Objects.nonNull;

final class Context {

    public static Builder aCommandLineContext(final Arguments args) {
        return new Builder().withBatchSize(args.oneIntegerNamed("batch-size"));
    }

    final static class Builder {
        private Integer batchSize = 5;

        public Context build() {
            return new Context(this);
        }

        public Builder withBatchSize(final Integer batchSize) {
            // TODO: clear method
            if (nonNull(batchSize)) {
                this.batchSize = batchSize;
            }
            return this;
        }
    }

    /** Not negative */
    private final Integer batchSize;

    private Context(final Builder builder) {
        this.batchSize = builder.batchSize;
    }

    public boolean isBatched() {
        return nonNull(batchSize);
    }

    public Integer getBatchSize() {
        return batchSize;
    }

    @Override
    public String toString() {
        return "Context [isBatched()=" + isBatched() + ", getBatchSize()=" + getBatchSize() + "]";
    }

}

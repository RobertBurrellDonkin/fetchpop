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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;

class OperationFactory {

    private static final Logger logger = LoggerFactory.getLogger(OperationFactory.class);

    private static final String STATUS_OP_NAME = "status";
    private static final String INFO_OP_NAME = "info";

    static IOperation nameToOperation(final String name) {
        switch (name.toLowerCase()) {
            case INFO_OP_NAME:
                return new PrintMessageInfo();
            case STATUS_OP_NAME:
                return new PrintStatusOperation(LoggerFactory.getLogger(PrintStatusOperation.class));
            default:
                logger.error("'{}' is not a known operation.", name);
                throw new IllegalArgumentException(format("Unknown operation: %s", name));
        }
    }
}

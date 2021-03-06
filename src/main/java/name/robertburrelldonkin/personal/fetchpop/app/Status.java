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

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.pop3.POP3MessageInfo;

import java.io.PrintStream;

import static org.apache.commons.io.FileUtils.byteCountToDisplaySize;

final class Status {

    private final int messageBoxSizeInBytes;
    private final int numberOfMessages;

    public Status(final POP3MessageInfo info) {
        super();
        this.messageBoxSizeInBytes = info.size;
        this.numberOfMessages = info.number;
    }

    public int getMessageBoxSizeInBytes() {
        return messageBoxSizeInBytes;
    }

    public int getNumberOfMessages() {
        return numberOfMessages;
    }

    public void printTo(PrintStream out) {
        out.println("messages: " + getNumberOfMessages() + ", size: "
                + FileUtils.byteCountToDisplaySize(getMessageBoxSizeInBytes()));
    }

    public String getMessageBoxDisplaySize() {
        return byteCountToDisplaySize(getMessageBoxSizeInBytes());
    }

    @Override
    public String toString() {
        return "Status [getMessageBoxSizeInBytes()=" + getMessageBoxSizeInBytes() + ", getNumberOfMessages()="
                + getNumberOfMessages() + ", getMessageBoxDisplaySize()=" + getMessageBoxDisplaySize() + "]";
    }
}

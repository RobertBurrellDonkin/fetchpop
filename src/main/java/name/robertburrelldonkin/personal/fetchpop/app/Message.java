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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.pop3.POP3MessageInfo;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Character.isSpaceChar;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableCollection;
import static java.util.stream.Collectors.joining;
import static name.robertburrelldonkin.personal.fetchpop.app.StandardOutput.STDOUT_MARKER;
import static org.apache.commons.lang.StringUtils.substringBefore;
import static org.apache.commons.lang.StringUtils.trim;

/**
 * <p>
 * A message stored in a POP3 server.
 * </p>
 */
class Message {

    private final static Collection<String> INFO_HEADERS = unmodifiableCollection(asList("from", "subject", "message-id"));

    private final POP3MessageInfo info;
    private final ISession session;

    Message(final POP3MessageInfo info, final ISession session) {
        super();
        this.info = info;
        this.session = session;
    }

    Reader read() {
        return session.retrieveMessage(info.number);
    }

    @Override
    public String toString() {
        return "Message [" + info + "]";
    }

    int getNumber() {
        return info.number;
    }

    void logInfo(final Logger logger) {
        logger.info(STDOUT_MARKER, "{}", info().sorted().collect(joining(" ")));
    }

    Stream<String> headerLines() {
        final StringBuilder current = new StringBuilder(1024);
        final List<String> accumulator = new ArrayList<>();
        new BufferedReader(read()).lines().takeWhile(StringUtils::isNotBlank).forEach(
                l -> {
                    if (isSpaceChar(l.codePointAt(0))) {
                        // Folded continuation of last header
                        current.append(' '); // normalize white space
                        current.append(trim(l));
                    } else {
                        // New header
                        if (current.length() > 0) {
                            // Accumulate current before moving on
                            accumulator.add(current.toString());
                            current.setLength(0);
                        }
                        current.append(l);
                    }
                }
        );
        // Last time
        if (current.length() > 0) {
            // Accumulate current before moving on
            accumulator.add(current.toString());
        }
        return accumulator.stream();
    }

    String headerName(final String line) {
        return substringBefore(line, ":");
    }

    Stream<String> info() {
        return headerLines().filter(l -> INFO_HEADERS.contains(headerName(l).toLowerCase()));
    }
}

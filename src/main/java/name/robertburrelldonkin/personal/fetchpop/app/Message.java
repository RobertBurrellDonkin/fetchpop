package name.robertburrelldonkin.personal.fetchpop.app;

import static name.robertburrelldonkin.personal.fetchpop.app.ExitCode.DOWNLOAD_FAILURE;
import static name.robertburrelldonkin.personal.fetchpop.app.StandardOutput.STDOUT_MARKER;
import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.commons.io.IOUtils.copyLarge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import java.io.Reader;
import java.util.Locale;

import org.apache.commons.net.pop3.POP3MessageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * A message stored in a POP3 server.
 * </p>
 */
class Message {

    private final Logger logger = LoggerFactory.getLogger(Message.class);

    private final POP3MessageInfo info;
    private final ISession session;

    public Message(final POP3MessageInfo info, final ISession session) {
        super();
        this.info = info;
        this.session = session;
    }

    public Reader read() {
        return session.retrieveMessage(info.number);
    }

    final void logInfo() {
        final BufferedReader reader = new BufferedReader(read());
        String from = "";
        String subject = "";
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String lower = line.toLowerCase(Locale.ENGLISH);
                if (lower.startsWith("from: ")) {
                    from = line.substring(6).trim();
                } else if (lower.startsWith("subject: ")) {
                    subject = line.substring(9).trim();
                }
            }
        } catch (IOException e) {
            throw new FatalNestedRuntimeException.MessageRetrievalException(e);
        }

        this.logger.info(STDOUT_MARKER, "MESSAGE #{} From:{} Subject:{}", Integer.toString(getId()), from, subject);
    }

    private int getId() {
        return info.number;
    }

    @Override
    public String toString() {
        return "Message [" + info + "]";
    }

    void downloadTo(final File file) {
        this.logger.info("Downloading {} into {}", this, file.getAbsolutePath());
        try {
            final FileWriter writer = new FileWriter(file);
            final Reader reader = read();
            try {
                copyLarge(reader, writer);
            } catch (IOException e) {
                // TODO error handling
                throw new FatalNestedRuntimeException(DOWNLOAD_FAILURE, e);
            } finally {
                closeQuietly(reader);
                closeQuietly(writer);
            }
        } catch (IOException e) {
            // TODO error handling
            throw new FatalNestedRuntimeException(DOWNLOAD_FAILURE, e);
        }
    }

}

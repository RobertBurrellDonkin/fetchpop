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

import org.apache.commons.net.ProtocolCommandEvent;
import org.apache.commons.net.ProtocolCommandListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class LoggingProtocolCommandListener implements ProtocolCommandListener {

    private final Logger logger;

    public LoggingProtocolCommandListener() {
        this(LoggerFactory.getLogger("protocol"));
    }

    public LoggingProtocolCommandListener(final Logger logger) {
        super();
        this.logger = logger;
    }

    @Override
    public void protocolCommandSent(final ProtocolCommandEvent event) {
        this.logger.info("-> {}", messageOrNull(event));
    }

    private String messageOrNull(final ProtocolCommandEvent event) {
        return nonNull(event) ? event.getMessage() : null;
    }

    @Override
    public void protocolReplyReceived(ProtocolCommandEvent event) {
        this.logger.info("<- {}", messageOrNull(event));
    }

}

package name.robertburrelldonkin.personal.fetchpop.app;

import java.io.PrintStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.pop3.POP3MessageInfo;

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

    @Override
    public String toString() {
        return "Status [messageBoxSizeInBytes=" + messageBoxSizeInBytes + ", numberOfMessages=" + numberOfMessages
                + "]";
    }

    public void printTo(PrintStream out) {
        out.println("messages: " + getNumberOfMessages() + ", size: "
                + FileUtils.byteCountToDisplaySize(getMessageBoxSizeInBytes()));
    }
}

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

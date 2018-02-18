package name.robertburrelldonkin.personal.fetchpop.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Account {

    private final Logger logger = LoggerFactory.getLogger(Account.class);

    private final String userName;
    private final String credentials;
    private final String hostName;
    private final int hostPort;

    public Account(final String userName, final String credentials, final String hostName, final int hostPort) {
        super();
        this.userName = userName;
        this.credentials = credentials;
        this.hostName = hostName;
        this.hostPort = hostPort;
    }

    public void perform(final Client client, final IOperation operation) {
        logger.info("Opening connection to perform {}", operation);
        client.connect(this.hostName, this.hostPort);
        try {
            client.login(this.userName, this.credentials);
            try {
                operation.operateOn(client.verify());
            } finally {
                client.logout();
            }
        } finally {
            client.disconnect();
        }
        logger.info("Completed {} and terminated session", operation);
    }

}
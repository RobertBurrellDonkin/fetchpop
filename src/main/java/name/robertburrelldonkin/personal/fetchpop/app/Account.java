package name.robertburrelldonkin.personal.fetchpop.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class Account {

    private final Logger logger = LoggerFactory.getLogger(Account.class);

    private final String userName;
    private final String credentials;
    private final String hostName;
    private final int hostPort;

    public Account(@Value("${application.user:}") final String userName,
            @Value("${application.cred:}") final String credentials,
            @Value("${application.host.name:}") final String hostName,
            @Value("${application.host.port:-1}") final int hostPort) {
        super();
        this.userName = userName;
        this.credentials = credentials;
        this.hostName = hostName;
        this.hostPort = hostPort;
        logger.info("Account { user: {}, host: { name: {}, port: {} } }", userName, hostName, hostPort);
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
package name.robertburrelldonkin.personal.fetchpop.app;

import static java.util.Objects.nonNull;
import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;
import static org.apache.commons.lang.ArrayUtils.EMPTY_CHAR_ARRAY;
import static org.apache.commons.lang.StringUtils.isBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class Account {

    private final Logger logger = LoggerFactory.getLogger(Account.class);

    private final String userName;
    /**
     * Minor security gain by reducing risk that password is accidentally
     * printed to String
     */
    private final char[] credentials;
    private final String hostName;
    private final int hostPort;

    public Account(@Value("${application.user:}") final String userName,
            @Value("${application.cred:}") final String credentials,
            @Value("${application.host.name:}") final String hostName,
            @Value("${application.host.port:995}") final int hostPort) {
        super();
        this.userName = userName;
        this.credentials = nonNull(credentials) ? credentials.toCharArray().clone()
                /* cloning prevent concurrent zeroing */ : EMPTY_CHAR_ARRAY;
        this.hostName = hostName;
        this.hostPort = hostPort;
        logger.info("Account { user: {}, host: { name: {}, port: {} } }", userName, hostName, hostPort);
    }

    public String digestCredentialsAsSHA() {
        return sha1Hex(credentialsAsString());
    }

    private String credentialsAsString() {
        return new String(this.credentials);
    }

    public String getUserName() {
        return this.userName;
    }

    public String getHostName() {
        return this.hostName;
    }

    public int getHostPort() {
        return this.hostPort;
    }

    public void perform(final Client client, final IOperation operation) {
        logger.info("Opening connection to perform {}", operation);
        client.connect(this.hostName, this.hostPort);
        try {
            client.login(this.userName, credentialsAsString());
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

    public boolean isEmpty() {
        return isBlank(userName) || isBlank(credentialsAsString()) || isBlank(hostName) || hostPort <= 0;
    }

    @Override
    public String toString() {
        return "Account [userName=" + userName + ", hostName=" + hostName + ", hostPort=" + hostPort + "]";
    }

}
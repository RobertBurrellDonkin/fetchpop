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
import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;
import static org.apache.commons.lang.ArrayUtils.EMPTY_CHAR_ARRAY;
import static org.apache.commons.lang.StringUtils.isBlank;

import org.apache.commons.lang.ArrayUtils;
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
        this.credentials = nonNull(credentials) ? ArrayUtils.clone(credentials.toCharArray())
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
package name.robertburrelldonkin.personal.fetchpop.app;

public enum ExitCode {

    STATUS_CALL_FAILED("STATUS call failed.", 8),
    STATUS_MISSING("Missing STATUS.", 9),
    RETREIVE_MESSAGE_CALL_FAILED("Retrieve message call failed.", 10),
    LOGIN_FAILED("Login failed.", 11),
    LOGIN_REJECTED("Login rejected by server.", 12),
    CONNECTION_FAILED("Could not connect to server.", 13);

    private final String description;
    private final int code;

    private ExitCode(String description, int code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }
}

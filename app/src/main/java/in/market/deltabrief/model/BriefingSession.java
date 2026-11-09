package in.market.deltabrief.model;

public class BriefingSession {
    private String sessionId;
    private String sessionName;
    private long serverVersion;

    public BriefingSession(String sessionId, String sessionName, long serverVersion) {
        this.sessionId = sessionId;
        this.sessionName = sessionName;
        this.serverVersion = serverVersion;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getSessionName() {
        return sessionName;
    }

    public long getServerVersion() {
        return serverVersion;
    }
}

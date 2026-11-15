package in.market.deltabrief.data.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "session_meta")
public class SessionMetaEntity {
    @PrimaryKey
    @NonNull
    private String sessionId;
    private String sessionName;
    private String releaseTime;
    private int briefCount;
    private boolean isPublished;
    private String colorHex;

    public SessionMetaEntity(@NonNull String sessionId, String sessionName, String releaseTime, int briefCount, boolean isPublished, String colorHex) {
        this.sessionId = sessionId;
        this.sessionName = sessionName;
        this.releaseTime = releaseTime;
        this.briefCount = briefCount;
        this.isPublished = isPublished;
        this.colorHex = colorHex;
    }

    @NonNull
    public String getSessionId() { return sessionId; }
    public String getSessionName() { return sessionName; }
    public String getReleaseTime() { return releaseTime; }
    public int getBriefCount() { return briefCount; }
    public boolean isPublished() { return isPublished; }
    public String getColorHex() { return colorHex; }
}

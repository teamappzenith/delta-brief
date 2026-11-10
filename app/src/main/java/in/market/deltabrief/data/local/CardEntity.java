package in.market.deltabrief.data.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cards")
public class CardEntity {

    @PrimaryKey
    @NonNull
    private String cardId;
    private String title;
    private String summaryText;
    private String category;
    private String bias;
    private String fullText;
    private String keyTakeaways;
    private String levels;
    private long timestamp;
    private boolean isBookmarked;

    public CardEntity(@NonNull String cardId, String title, String summaryText, String category, String bias, String fullText, String keyTakeaways, String levels, long timestamp, boolean isBookmarked) {
        this.cardId = cardId;
        this.title = title;
        this.summaryText = summaryText;
        this.category = category;
        this.bias = bias;
        this.fullText = fullText;
        this.keyTakeaways = keyTakeaways;
        this.levels = levels;
        this.timestamp = timestamp;
        this.isBookmarked = isBookmarked;
    }

    @NonNull
    public String getCardId() { return cardId; }
    public void setCardId(@NonNull String cardId) { this.cardId = cardId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSummaryText() { return summaryText; }
    public void setSummaryText(String summaryText) { this.summaryText = summaryText; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getBias() { return bias != null ? bias : "NEUTRAL"; }
    public void setBias(String bias) { this.bias = bias; }

    public String getFullText() { return fullText != null ? fullText : summaryText; }
    public void setFullText(String fullText) { this.fullText = fullText; }

    public String getKeyTakeaways() { return keyTakeaways != null ? keyTakeaways : ""; }
    public void setKeyTakeaways(String keyTakeaways) { this.keyTakeaways = keyTakeaways; }

    public String getLevels() { return levels != null ? levels : ""; }
    public void setLevels(String levels) { this.levels = levels; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public boolean isBookmarked() { return isBookmarked; }
    public void setBookmarked(boolean bookmarked) { isBookmarked = bookmarked; }
}

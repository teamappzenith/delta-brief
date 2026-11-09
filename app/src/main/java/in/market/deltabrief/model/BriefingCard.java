package in.market.deltabrief.model;

public class BriefingCard {
    private String cardId;
    private String title;
    private String summaryText;
    private String category;
    private long timestamp;
    private boolean isBookmarked;

    public BriefingCard(String cardId, String title, String summaryText, String category, long timestamp, boolean isBookmarked) {
        this.cardId = cardId;
        this.title = title;
        this.summaryText = summaryText;
        this.category = category;
        this.timestamp = timestamp;
        this.isBookmarked = isBookmarked;
    }

    public String getCardId() {
        return cardId;
    }

    public String getTitle() {
        return title;
    }

    public String getSummaryText() {
        return summaryText;
    }

    public String getCategory() {
        return category;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }
}

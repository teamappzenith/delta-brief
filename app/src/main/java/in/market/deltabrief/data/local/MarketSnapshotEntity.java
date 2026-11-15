package in.market.deltabrief.data.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "market_snapshots")
public class MarketSnapshotEntity {
    @PrimaryKey
    @NonNull
    private String symbol; // "NIFTY_50", "BANK_NIFTY", "INDIA_VIX", "MARKET_SENTIMENT"
    private String indexName;
    private String currentValue;
    private String subLabel;
    private String supportVal;
    private String resistanceVal;
    private String statusOrBias;
    private int score;

    public MarketSnapshotEntity(@NonNull String symbol, String indexName, String currentValue, String subLabel, String supportVal, String resistanceVal, String statusOrBias, int score) {
        this.symbol = symbol;
        this.indexName = indexName;
        this.currentValue = currentValue;
        this.subLabel = subLabel;
        this.supportVal = supportVal;
        this.resistanceVal = resistanceVal;
        this.statusOrBias = statusOrBias;
        this.score = score;
    }

    @NonNull
    public String getSymbol() { return symbol; }
    public String getIndexName() { return indexName; }
    public String getCurrentValue() { return currentValue; }
    public String getSubLabel() { return subLabel; }
    public String getSupportVal() { return supportVal; }
    public String getResistanceVal() { return resistanceVal; }
    public String getStatusOrBias() { return statusOrBias; }
    public int getScore() { return score; }
}

package in.market.deltabrief.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import in.market.deltabrief.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_CARD_ID = "extra_card_id";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_SUMMARY = "extra_summary";
    public static final String EXTRA_CATEGORY = "extra_category";
    public static final String EXTRA_BIAS = "extra_bias";
    public static final String EXTRA_TIMESTAMP = "extra_timestamp";
    public static final String EXTRA_BOOKMARKED = "extra_bookmarked";
    public static final String EXTRA_FULL_TEXT = "extra_full_text";
    public static final String EXTRA_TAKEAWAYS = "extra_takeaways";
    public static final String EXTRA_LEVELS = "extra_levels";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String title = intent.getStringExtra(EXTRA_TITLE);
        String summary = intent.getStringExtra(EXTRA_SUMMARY);
        String category = intent.getStringExtra(EXTRA_CATEGORY);
        String bias = intent.getStringExtra(EXTRA_BIAS);
        long timestamp = intent.getLongExtra(EXTRA_TIMESTAMP, System.currentTimeMillis());
        String fullText = intent.getStringExtra(EXTRA_FULL_TEXT);
        String takeaways = intent.getStringExtra(EXTRA_TAKEAWAYS);

        ImageButton btnBack = findViewById(R.id.btnBack);
        ImageButton btnShare = findViewById(R.id.btnShare);

        btnBack.setOnClickListener(v -> finish());
        btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareMessage = "DeltaBrief - " + (title != null ? title : "Market Briefing") + "\n\n" + (summary != null ? summary : "") + "\n\nDownload DeltaBrief App!";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Share Briefing"));
        });

        TextView tvDetailToolbarTitle = findViewById(R.id.tvDetailToolbarTitle);
        TextView tvDetailBias = findViewById(R.id.tvDetailBias);
        TextView tvDetailTitle = findViewById(R.id.tvDetailTitle);
        TextView tvDetailTimestamp = findViewById(R.id.tvDetailTimestamp);
        TextView tvDetailSummary = findViewById(R.id.tvDetailSummary);
        TextView tvDetailTakeaways = findViewById(R.id.tvDetailTakeaways);

        Date date = new Date(timestamp);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy", Locale.getDefault());

        String catStr = category != null ? category.toUpperCase(Locale.ROOT) : "PRE-MARKET";
        tvDetailToolbarTitle.setText(catStr + " · " + timeFormat.format(date));

        String biasStr = bias != null ? bias : "Bullish";
        if (!biasStr.isEmpty()) {
            biasStr = biasStr.substring(0, 1).toUpperCase(Locale.ROOT) + biasStr.substring(1).toLowerCase(Locale.ROOT);
        }
        tvDetailBias.setText(biasStr);

        tvDetailTitle.setText(title != null ? title : "Pre-Market Equity Briefing: Nifty & BankNifty Open Strong");
        tvDetailTimestamp.setText(dateFormat.format(date) + " · 2 min read");

        String contentToDisplay = (fullText != null && !fullText.isEmpty()) ? fullText : (summary != null ? summary : "Global markets moved higher as major technology stocks gained, supported by optimism around technology spending and expectations of continued economic growth.");
        tvDetailSummary.setText(contentToDisplay);

        if (takeaways != null && !takeaways.isEmpty()) {
            tvDetailTakeaways.setText(takeaways);
        }
    }
}

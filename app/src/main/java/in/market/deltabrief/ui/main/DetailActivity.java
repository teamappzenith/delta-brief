package in.market.deltabrief.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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

        ImageButton btnBack = findViewById(R.id.btnBack);
        ImageButton btnShare = findViewById(R.id.btnShare);

        TextView tvDetailSession = findViewById(R.id.tvDetailSession);
        TextView tvDetailBias = findViewById(R.id.tvDetailBias);
        TextView tvDetailTimestamp = findViewById(R.id.tvDetailTimestamp);
        TextView tvDetailTitle = findViewById(R.id.tvDetailTitle);
        TextView tvDetailSummary = findViewById(R.id.tvDetailSummary);

        btnBack.setOnClickListener(v -> finish());

        btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareMessage = "DeltaBrief - " + (title != null ? title : "Market Briefing") + "\n\n" + (summary != null ? summary : "") + "\n\nDownload DeltaBrief App!";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Share Briefing"));
        });

        tvDetailSession.setText(category != null ? category.toUpperCase(Locale.ROOT) : "PRE-MARKET");

        String biasText = bias != null ? bias.toUpperCase(Locale.ROOT) : "BULLISH";
        tvDetailBias.setText(biasText);
        if ("BULLISH".equalsIgnoreCase(biasText)) {
            tvDetailBias.setTextColor(ContextCompat.getColor(this, R.color.bullish_text));
        } else if ("BEARISH".equalsIgnoreCase(biasText)) {
            tvDetailBias.setTextColor(ContextCompat.getColor(this, R.color.bearish_text));
        } else {
            tvDetailBias.setTextColor(ContextCompat.getColor(this, R.color.trading_blue));
        }

        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.getDefault());
        tvDetailTimestamp.setText(sdf.format(new Date(timestamp)));

        if (title != null && !title.isEmpty()) {
            tvDetailTitle.setText(title);
        } else {
            tvDetailTitle.setText("Nifty & Bank Nifty Set for a Strong Opening");
        }

        if (summary != null && !summary.isEmpty()) {
            tvDetailSummary.setText(summary);
        } else {
            tvDetailSummary.setText("Positive global cues and institutional buying support expectations of a gap-up opening in domestic indices, led by robust FII inflows and stable global crude oil prices.");
        }
    }
}

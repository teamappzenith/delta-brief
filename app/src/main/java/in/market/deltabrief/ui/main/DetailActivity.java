package in.market.deltabrief.ui.main;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
        String fullText = intent.getStringExtra(EXTRA_FULL_TEXT);
        String takeaways = intent.getStringExtra(EXTRA_TAKEAWAYS);
        String levels = intent.getStringExtra(EXTRA_LEVELS);

        ImageButton btnBack = findViewById(R.id.btnBack);
        ImageButton btnShare = findViewById(R.id.btnShare);

        TextView tvDetailSession = findViewById(R.id.tvDetailSession);
        TextView tvDetailBias = findViewById(R.id.tvDetailBias);
        TextView tvDetailTimestamp = findViewById(R.id.tvDetailTimestamp);
        TextView tvDetailTitle = findViewById(R.id.tvDetailTitle);
        TextView tvDetailFullText = findViewById(R.id.tvDetailFullText);
        LinearLayout layoutTakeawaysContainer = findViewById(R.id.layoutTakeawaysContainer);
        LinearLayout layoutLevelsContainer = findViewById(R.id.layoutLevelsContainer);

        btnBack.setOnClickListener(v -> finish());

        btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareMessage = "DeltaBrief - " + title + "\n\n" + (fullText != null ? fullText : summary) + "\n\nDownload DeltaBrief App!";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Share Briefing"));
        });

        tvDetailSession.setText(category != null ? category.toUpperCase(Locale.ROOT) : "SESSION");
        if ("Pre-Market".equalsIgnoreCase(category)) {
            tvDetailSession.setBackgroundResource(R.drawable.bg_gradient_premarket);
        } else if ("Stabilized".equalsIgnoreCase(category)) {
            tvDetailSession.setBackgroundResource(R.drawable.bg_gradient_stabilized);
        } else if ("Mid-Day".equalsIgnoreCase(category) || "Pre-Close".equalsIgnoreCase(category)) {
            tvDetailSession.setBackgroundResource(R.drawable.bg_gradient_midday);
        } else {
            tvDetailSession.setBackgroundResource(R.drawable.bg_gradient_postmarket);
        }

        String biasText = bias != null ? bias.toUpperCase(Locale.ROOT) : "NEUTRAL";
        tvDetailBias.setText(biasText);
        if ("BULLISH".equalsIgnoreCase(biasText)) {
            tvDetailBias.setTextColor(ContextCompat.getColor(this, R.color.bullish_text));
        } else if ("BEARISH".equalsIgnoreCase(biasText)) {
            tvDetailBias.setTextColor(ContextCompat.getColor(this, R.color.bearish_text));
        } else {
            tvDetailBias.setTextColor(ContextCompat.getColor(this, R.color.trading_blue));
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());
        tvDetailTimestamp.setText(sdf.format(new Date(timestamp)));

        tvDetailTitle.setText(title);
        tvDetailFullText.setText(fullText != null ? fullText : summary);

        populateDistinctRows(layoutTakeawaysContainer, takeaways != null && !takeaways.isEmpty() ? takeaways : "• Stay updated with real-time market trends.\n• Practice disciplined risk management.");
        populateDistinctRows(layoutLevelsContainer, levels != null && !levels.isEmpty() ? levels : "• Key Pivot Support: Nifty 22,200 | Resistance: 22,500");
    }

    private void populateDistinctRows(LinearLayout container, String rawText) {
        container.removeAllViews();
        if (rawText == null || rawText.isEmpty()) {
            return;
        }
        String[] lines = rawText.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;
            if (line.startsWith("•") || line.startsWith("-") || line.startsWith("💡") || line.startsWith("🎯")) {
                line = line.substring(1).trim();
            }
            if (line.isEmpty()) continue;

            TextView tvRow = new TextView(this);
            tvRow.setText("•  " + line);
            tvRow.setTextColor(getResources().getColor(R.color.subtitle_text, null));
            tvRow.setTextSize(14f);
            tvRow.setTypeface(null, Typeface.BOLD);
            tvRow.setPadding(0, 10, 0, 10);
            container.addView(tvRow);

            if (i < lines.length - 1) {
                View divider = new View(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 1);
                params.setMargins(0, 8, 0, 8);
                divider.setLayoutParams(params);
                divider.setBackgroundColor(getResources().getColor(R.color.divider_color, null));
                container.addView(divider);
            }
        }
    }
}

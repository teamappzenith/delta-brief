package in.market.deltabrief.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import in.market.deltabrief.R;
import in.market.deltabrief.ui.viewmodel.BriefingViewModel;

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

    private BriefingViewModel viewModel;
    private String cardId;
    private boolean isBookmarked;
    private ImageButton btnBookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_detail);

        viewModel = new ViewModelProvider(this).get(BriefingViewModel.class);

        Intent intent = getIntent();
        cardId = intent.getStringExtra(EXTRA_CARD_ID);
        String title = intent.getStringExtra(EXTRA_TITLE);
        String summary = intent.getStringExtra(EXTRA_SUMMARY);
        String category = intent.getStringExtra(EXTRA_CATEGORY);
        String bias = intent.getStringExtra(EXTRA_BIAS);
        long timestamp = intent.getLongExtra(EXTRA_TIMESTAMP, System.currentTimeMillis());
        isBookmarked = intent.getBooleanExtra(EXTRA_BOOKMARKED, false);
        String fullText = intent.getStringExtra(EXTRA_FULL_TEXT);
        String takeaways = intent.getStringExtra(EXTRA_TAKEAWAYS);
        String levels = intent.getStringExtra(EXTRA_LEVELS);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBookmark = findViewById(R.id.btnBookmark);
        ImageButton btnShare = findViewById(R.id.btnShare);
        ImageButton btnFeedback = findViewById(R.id.btnFeedback);

        TextView tvDetailSession = findViewById(R.id.tvDetailSession);
        TextView tvDetailBias = findViewById(R.id.tvDetailBias);
        TextView tvDetailTimestamp = findViewById(R.id.tvDetailTimestamp);
        TextView tvDetailTitle = findViewById(R.id.tvDetailTitle);
        TextView tvDetailFullText = findViewById(R.id.tvDetailFullText);
        TextView tvDetailTakeaways = findViewById(R.id.tvDetailTakeaways);
        TextView tvDetailLevels = findViewById(R.id.tvDetailLevels);

        btnBack.setOnClickListener(v -> finish());

        updateBookmarkUI();
        btnBookmark.setOnClickListener(v -> {
            isBookmarked = !isBookmarked;
            updateBookmarkUI();
            if (cardId != null) {
                viewModel.updateBookmarkStatus(cardId, isBookmarked);
            }
            Toast.makeText(this, isBookmarked ? "Card Bookmarked" : "Bookmark Removed", Toast.LENGTH_SHORT).show();
        });

        btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareMessage = "DeltaBrief - " + title + "\n\n" + (fullText != null ? fullText : summary) + "\n\nDownload DeltaBrief App!";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Share Briefing"));
        });

        btnFeedback.setOnClickListener(v -> {
            Toast.makeText(this, "Thank you! Marked as helpful.", Toast.LENGTH_SHORT).show();
        });

        tvDetailSession.setText(category != null ? category.toUpperCase(Locale.ROOT) : "SESSION");
        if ("Pre-Market".equalsIgnoreCase(category)) {
            tvDetailSession.setBackgroundColor(Color.parseColor("#0284C7"));
        } else if ("Stabilized".equalsIgnoreCase(category)) {
            tvDetailSession.setBackgroundColor(Color.parseColor("#16A34A"));
        } else if ("Mid-Day".equalsIgnoreCase(category) || "Pre-Close".equalsIgnoreCase(category)) {
            tvDetailSession.setBackgroundColor(Color.parseColor("#DC2626"));
        } else {
            tvDetailSession.setBackgroundColor(Color.parseColor("#9333EA"));
        }

        String biasText = bias != null ? bias.toUpperCase(Locale.ROOT) : "NEUTRAL";
        tvDetailBias.setText(biasText);
        if ("BULLISH".equalsIgnoreCase(biasText)) {
            tvDetailBias.setBackgroundColor(Color.parseColor("#16A34A"));
        } else if ("BEARISH".equalsIgnoreCase(biasText)) {
            tvDetailBias.setBackgroundColor(Color.parseColor("#DC2626"));
        } else {
            tvDetailBias.setBackgroundColor(Color.parseColor("#64748B"));
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());
        tvDetailTimestamp.setText(sdf.format(new Date(timestamp)));

        tvDetailTitle.setText(title);
        tvDetailFullText.setText(fullText != null ? fullText : summary);
        tvDetailTakeaways.setText(takeaways != null && !takeaways.isEmpty() ? takeaways : "• Stay updated with real-time market trends.\n• Practice disciplined risk management.");
        tvDetailLevels.setText(levels != null && !levels.isEmpty() ? levels : "• Key Pivot Support: Nifty 22,200 | Resistance: 22,500");
    }

    private void updateBookmarkUI() {
        btnBookmark.setImageResource(isBookmarked ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
    }
}

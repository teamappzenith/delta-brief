package in.market.deltabrief.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.market.deltabrief.R;
import in.market.deltabrief.data.local.CardEntity;
import in.market.deltabrief.model.HubItem;
import in.market.deltabrief.ui.adapter.HubAdapter;
import in.market.deltabrief.ui.viewmodel.BriefingViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private HubAdapter hubAdapter;
    private final Map<String, Integer> sessionCounts = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Load and apply saved theme mode on startup
        SharedPreferences prefs = getSharedPreferences("delta_brief_prefs", MODE_PRIVATE);
        int savedTheme = prefs.getInt("pref_theme_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        AppCompatDelegate.setDefaultNightMode(savedTheme);

        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);

        ImageButton btnHeaderSettings = findViewById(R.id.btnHeaderSettings);
        if (btnHeaderSettings != null) {
            btnHeaderSettings.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerViewHub);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 2; // All session hub cards take full width (1 card per row stack)
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);

        hubAdapter = new HubAdapter();
        recyclerView.setAdapter(hubAdapter);

        hubAdapter.setOnHubItemClickListener(item -> {
            if (item.getViewType() == HubItem.TYPE_SESSION_HUB) {
                Intent intent = new Intent(this, SessionFeedActivity.class);
                intent.putExtra(SessionFeedActivity.EXTRA_SESSION_NAME, item.getCategory());
                startActivity(intent);
            }
        });

        BriefingViewModel viewModel = new ViewModelProvider(this).get(BriefingViewModel.class);
        viewModel.getAllCards().observe(this, cards -> {
            if (cards != null) {
                updateSessionCounts(cards);
                loadHubItems();
            }
        });

        loadHubItems();
    }

    private void updateSessionCounts(List<CardEntity> cards) {
        sessionCounts.clear();
        for (CardEntity card : cards) {
            String category = card.getCategory();
            if (category != null) {
                sessionCounts.put(category, sessionCounts.getOrDefault(category, 0) + 1);
            }
        }
    }

    private void loadHubItems() {
        List<HubItem> hubItems = new ArrayList<>();

        // Full-Width Stack Layout: Only the 4 Session Hub Cards
        int preMarketCount = sessionCounts.getOrDefault("Pre-Market", 1);
        int stabilizedCount = sessionCounts.getOrDefault("Stabilized", 1);
        int midDayCount = sessionCounts.getOrDefault("Mid-Day", 1);
        int postMarketCount = sessionCounts.getOrDefault("Post-Market", 1);

        hubItems.add(new HubItem(
                HubItem.TYPE_SESSION_HUB,
                "Pre-Market",
                "8:00 AM Release",
                "Institutional order flow, global cues, SGX Nifty & overnight FII/DII activity.",
                "Pre-Market",
                android.R.drawable.ic_menu_today,
                preMarketCount,
                "#D97706"
        ));

        hubItems.add(new HubItem(
                HubItem.TYPE_SESSION_HUB,
                "Stabilized",
                "11:00 AM Release",
                "Mid-morning IV expansion, straddle adjustments & trend confirmation levels.",
                "Stabilized",
                android.R.drawable.ic_menu_day,
                stabilizedCount,
                "#059669"
        ));

        hubItems.add(new HubItem(
                HubItem.TYPE_SESSION_HUB,
                "Mid-Day",
                "2:00 PM Release",
                "Macroeconomic policy updates, RBI interest rate stance & breakout levels.",
                "Mid-Day",
                android.R.drawable.ic_menu_recent_history,
                midDayCount,
                "#DC2626"
        ));

        hubItems.add(new HubItem(
                HubItem.TYPE_SESSION_HUB,
                "Post-Market",
                "4:00 PM Release",
                "Closing market wrap, IT & Auto momentum and next-day outlook.",
                "Post-Market",
                android.R.drawable.ic_menu_month,
                postMarketCount,
                "#7C3AED"
        ));

        hubAdapter.setItems(hubItems);
    }
}

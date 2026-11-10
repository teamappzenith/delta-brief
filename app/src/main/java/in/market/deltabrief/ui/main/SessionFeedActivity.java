package in.market.deltabrief.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.market.deltabrief.R;
import in.market.deltabrief.data.local.CardEntity;
import in.market.deltabrief.ui.adapter.SessionNewsAdapter;
import in.market.deltabrief.ui.viewmodel.BriefingViewModel;

import java.util.ArrayList;
import java.util.List;

public class SessionFeedActivity extends AppCompatActivity {

    public static final String EXTRA_SESSION_NAME = "extra_session_name";

    private SessionNewsAdapter adapter;
    private String sessionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_session_feed);

        Intent intent = getIntent();
        sessionName = intent.getStringExtra(EXTRA_SESSION_NAME);
        if (sessionName == null) {
            sessionName = "Pre-Market";
        }

        View rootLayout = findViewById(R.id.rootSessionFeedLayout);
        if ("Pre-Market".equalsIgnoreCase(sessionName)) {
            rootLayout.setBackgroundColor(Color.parseColor("#12F59E0B")); // Ambient amber tint
        } else if ("Stabilized".equalsIgnoreCase(sessionName)) {
            rootLayout.setBackgroundColor(Color.parseColor("#120D9488")); // Ambient teal tint
        } else if ("Mid-Day".equalsIgnoreCase(sessionName) || "Pre-Close".equalsIgnoreCase(sessionName)) {
            rootLayout.setBackgroundColor(Color.parseColor("#12E11D48")); // Ambient red tint
        } else {
            rootLayout.setBackgroundColor(Color.parseColor("#127C3AED")); // Ambient purple tint
        }

        ImageButton btnBack = findViewById(R.id.btnBack);
        ImageButton btnShare = findViewById(R.id.btnShare);
        TextView tvSessionFeedTitle = findViewById(R.id.tvSessionFeedTitle);

        tvSessionFeedTitle.setText(sessionName + " Briefs");
        btnBack.setOnClickListener(v -> finish());

        btnShare.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Explore " + sessionName + " market briefings on DeltaBrief!");
            startActivity(Intent.createChooser(shareIntent, "Share Session Briefs"));
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerViewSessionFeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SessionNewsAdapter();
        recyclerView.setAdapter(adapter);

        BriefingViewModel viewModel = new ViewModelProvider(this).get(BriefingViewModel.class);
        viewModel.getAllCards().observe(this, cards -> {
            if (cards != null) {
                List<CardEntity> filteredList = new ArrayList<>();
                for (CardEntity card : cards) {
                    if (sessionName.equalsIgnoreCase(card.getCategory())) {
                        filteredList.add(card);
                    }
                }
                adapter.setCards(filteredList);
            }
        });
    }
}

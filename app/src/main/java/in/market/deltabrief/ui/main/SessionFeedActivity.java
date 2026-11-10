package in.market.deltabrief.ui.main;

import android.content.Intent;
import android.os.Bundle;
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

    private BriefingViewModel viewModel;
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

        ImageButton btnBack = findViewById(R.id.btnBack);
        TextView tvSessionFeedTitle = findViewById(R.id.tvSessionFeedTitle);

        tvSessionFeedTitle.setText(sessionName + " Briefs");
        btnBack.setOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.recyclerViewSessionFeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SessionNewsAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(BriefingViewModel.class);
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

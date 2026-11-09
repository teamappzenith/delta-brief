package in.market.deltabrief.ui.main;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.ChipGroup;
import in.market.deltabrief.R;
import in.market.deltabrief.data.local.CardEntity;
import in.market.deltabrief.ui.adapter.CardSwipeAdapter;
import in.market.deltabrief.ui.viewmodel.BriefingViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BriefingViewModel viewModel;
    private CardSwipeAdapter adapter;
    private List<CardEntity> allCardsList = new ArrayList<>();
    private String currentCategoryFilter = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCards);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        adapter = new CardSwipeAdapter();
        recyclerView.setAdapter(adapter);

        ChipGroup chipGroup = findViewById(R.id.chipGroup);
        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.chipEquity) {
                currentCategoryFilter = "Equity";
            } else if (checkedId == R.id.chipOptions) {
                currentCategoryFilter = "Options";
            } else if (checkedId == R.id.chipMacro) {
                currentCategoryFilter = "Macro";
            } else if (checkedId == R.id.chipPostMarket) {
                currentCategoryFilter = "Post-Market";
            } else {
                currentCategoryFilter = "All";
            }
            filterCards();
        });

        viewModel = new ViewModelProvider(this).get(BriefingViewModel.class);
        viewModel.getAllCards().observe(this, cards -> {
            if (cards != null) {
                allCardsList = cards;
                filterCards();
            }
        });

        adapter.setOnBookmarkClickListener(card -> {
            boolean newBookmarkStatus = !card.isBookmarked();
            viewModel.updateBookmarkStatus(card.getCardId(), newBookmarkStatus);
        });

        // Add swipe gesture support using ItemTouchHelper
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAbsoluteAdapterPosition();
                adapter.notifyItemChanged(position);
            }
        };

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    private void filterCards() {
        if ("All".equals(currentCategoryFilter)) {
            adapter.setCards(allCardsList);
        } else {
            List<CardEntity> filteredList = new ArrayList<>();
            for (CardEntity card : allCardsList) {
                if (currentCategoryFilter.equalsIgnoreCase(card.getCategory())) {
                    filteredList.add(card);
                }
            }
            adapter.setCards(filteredList);
        }
    }
}

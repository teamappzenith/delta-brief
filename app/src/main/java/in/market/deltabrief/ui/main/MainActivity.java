package in.market.deltabrief.ui.main;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.market.deltabrief.R;
import in.market.deltabrief.ui.adapter.CardSwipeAdapter;
import in.market.deltabrief.ui.viewmodel.BriefingViewModel;

public class MainActivity extends AppCompatActivity {

    private BriefingViewModel viewModel;
    private CardSwipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCards);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        adapter = new CardSwipeAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(BriefingViewModel.class);
        viewModel.getAllCards().observe(this, cards -> {
            if (cards != null) {
                adapter.setCards(cards);
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
}

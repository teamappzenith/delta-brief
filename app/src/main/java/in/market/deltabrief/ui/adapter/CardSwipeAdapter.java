package in.market.deltabrief.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import in.market.deltabrief.R;
import in.market.deltabrief.data.local.CardEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CardSwipeAdapter extends RecyclerView.Adapter<CardSwipeAdapter.CardViewHolder> {

    private List<CardEntity> cards = new ArrayList<>();
    private OnBookmarkClickListener bookmarkClickListener;

    public interface OnBookmarkClickListener {
        void onBookmarkClick(CardEntity card);
    }

    public void setOnBookmarkClickListener(OnBookmarkClickListener listener) {
        this.bookmarkClickListener = listener;
    }

    public void setCards(List<CardEntity> cards) {
        this.cards = cards != null ? cards : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_briefing_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardEntity card = cards.get(position);
        holder.tvTitle.setText(card.getTitle());
        holder.tvSummary.setText(card.getSummaryText());
        holder.tvCategory.setText(card.getCategory());
        
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());
        holder.tvTimestamp.setText(sdf.format(new Date(card.getTimestamp())));

        holder.btnBookmark.setImageResource(card.isBookmarked() ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);

        // Color indicator strip based on category
        if ("Equity".equalsIgnoreCase(card.getCategory())) {
            holder.indicatorStrip.setBackgroundColor(android.graphics.Color.parseColor("#16A34A")); // Green
        } else if ("Options".equalsIgnoreCase(card.getCategory())) {
            holder.indicatorStrip.setBackgroundColor(android.graphics.Color.parseColor("#DC2626")); // Red
        } else if ("Macro".equalsIgnoreCase(card.getCategory())) {
            holder.indicatorStrip.setBackgroundColor(android.graphics.Color.parseColor("#0284C7")); // Blue
        } else {
            holder.indicatorStrip.setBackgroundColor(android.graphics.Color.parseColor("#9333EA")); // Purple
        }

        holder.btnBookmark.setOnClickListener(v -> {
            if (bookmarkClickListener != null) {
                bookmarkClickListener.onBookmarkClick(card);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSummary, tvCategory, tvTimestamp;
        ImageButton btnBookmark;
        View indicatorStrip;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSummary = itemView.findViewById(R.id.tvSummary);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            btnBookmark = itemView.findViewById(R.id.btnBookmark);
            indicatorStrip = itemView.findViewById(R.id.indicatorStrip);
        }
    }
}

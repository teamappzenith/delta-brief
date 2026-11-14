package in.market.deltabrief.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import in.market.deltabrief.R;
import in.market.deltabrief.data.local.CardEntity;
import in.market.deltabrief.ui.main.DetailActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SessionNewsAdapter extends RecyclerView.Adapter<SessionNewsAdapter.SessionNewsViewHolder> {

    private List<CardEntity> cards = new ArrayList<>();

    public void setCards(List<CardEntity> cards) {
        this.cards = cards != null ? cards : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SessionNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_session_news_card, parent, false);
        return new SessionNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionNewsViewHolder holder, int position) {
        CardEntity card = cards.get(position);
        Context context = holder.itemView.getContext();

        holder.tvTitle.setText(card.getTitle());
        holder.tvSummary.setText(card.getSummaryText());

        Date date = new Date(card.getTimestamp());
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        holder.tvPanelTime.setText(timeFormat.format(date) + " · 45 sec read");

        String bias = card.getBias() != null ? card.getBias().toUpperCase(Locale.ROOT) : "BULLISH";
        holder.tvBiasTag.setText(bias);

        String category = card.getCategory();
        if (category != null) {
            holder.tvCategoryLabel.setText(category.toUpperCase(Locale.ROOT) + " OUTLOOK");
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_CARD_ID, card.getCardId());
            intent.putExtra(DetailActivity.EXTRA_TITLE, card.getTitle());
            intent.putExtra(DetailActivity.EXTRA_SUMMARY, card.getSummaryText());
            intent.putExtra(DetailActivity.EXTRA_CATEGORY, card.getCategory());
            intent.putExtra(DetailActivity.EXTRA_BIAS, card.getBias());
            intent.putExtra(DetailActivity.EXTRA_TIMESTAMP, card.getTimestamp());
            intent.putExtra(DetailActivity.EXTRA_BOOKMARKED, card.isBookmarked());
            intent.putExtra(DetailActivity.EXTRA_FULL_TEXT, card.getFullText());
            intent.putExtra(DetailActivity.EXTRA_TAKEAWAYS, card.getKeyTakeaways());
            intent.putExtra(DetailActivity.EXTRA_LEVELS, card.getLevels());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    static class SessionNewsViewHolder extends RecyclerView.ViewHolder {
        TextView tvBiasTag, tvPanelTime, tvTitle, tvSummary, tvCategoryLabel;

        public SessionNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBiasTag = itemView.findViewById(R.id.tvBiasTag);
            tvPanelTime = itemView.findViewById(R.id.tvPanelTime);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSummary = itemView.findViewById(R.id.tvSummary);
            tvCategoryLabel = itemView.findViewById(R.id.tvCategoryLabel);
        }
    }
}

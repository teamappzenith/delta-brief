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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CardSwipeAdapter extends RecyclerView.Adapter<CardSwipeAdapter.CardViewHolder> {

    private List<CardEntity> cards = new ArrayList<>();

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
        
        String session = card.getCategory();
        holder.tvSessionBadge.setText(session != null ? session.toUpperCase(Locale.ROOT) : "SESSION");

        String bias = card.getBias() != null ? card.getBias().toUpperCase(Locale.ROOT) : "NEUTRAL";
        holder.tvBiasTag.setText(bias);

        // Indicator strip & Bias tag colors
        if ("BULLISH".equalsIgnoreCase(bias)) {
            holder.indicatorStrip.setBackgroundColor(android.graphics.Color.parseColor("#16A34A")); // Green
            holder.tvBiasTag.setBackgroundColor(android.graphics.Color.parseColor("#16A34A"));
        } else if ("BEARISH".equalsIgnoreCase(bias)) {
            holder.indicatorStrip.setBackgroundColor(android.graphics.Color.parseColor("#DC2626")); // Red
            holder.tvBiasTag.setBackgroundColor(android.graphics.Color.parseColor("#DC2626"));
        } else {
            holder.indicatorStrip.setBackgroundColor(android.graphics.Color.parseColor("#0284C7")); // Blue / Neutral
            holder.tvBiasTag.setBackgroundColor(android.graphics.Color.parseColor("#0284C7"));
        }

        // Session badge color
        if ("Pre-Market".equalsIgnoreCase(session)) {
            holder.tvSessionBadge.setBackgroundColor(android.graphics.Color.parseColor("#0284C7"));
        } else if ("Stabilized".equalsIgnoreCase(session)) {
            holder.tvSessionBadge.setBackgroundColor(android.graphics.Color.parseColor("#16A34A"));
        } else if ("Mid-Day".equalsIgnoreCase(session) || "Pre-Close".equalsIgnoreCase(session)) {
            holder.tvSessionBadge.setBackgroundColor(android.graphics.Color.parseColor("#DC2626"));
        } else {
            holder.tvSessionBadge.setBackgroundColor(android.graphics.Color.parseColor("#9333EA"));
        }

        holder.tvReadTime.setText("⏱️ 45 sec read");

        // Tapping card opens DetailActivity
        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
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

    static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSummary, tvSessionBadge, tvBiasTag, tvReadTime;
        View indicatorStrip;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSummary = itemView.findViewById(R.id.tvSummary);
            tvSessionBadge = itemView.findViewById(R.id.tvSessionBadge);
            tvBiasTag = itemView.findViewById(R.id.tvBiasTag);
            tvReadTime = itemView.findViewById(R.id.tvReadTime);
            indicatorStrip = itemView.findViewById(R.id.indicatorStrip);
        }
    }
}

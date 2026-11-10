package in.market.deltabrief.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());

        holder.tvPanelDate.setText(dateFormat.format(date));
        holder.tvPanelTime.setText(timeFormat.format(date));

        String session = card.getCategory();
        if ("Pre-Market".equalsIgnoreCase(session)) {
            holder.leftSidePanel.setBackgroundColor(Color.parseColor("#F59E0B")); // Amber
        } else if ("Stabilized".equalsIgnoreCase(session)) {
            holder.leftSidePanel.setBackgroundColor(Color.parseColor("#0D9488")); // Teal
        } else if ("Mid-Day".equalsIgnoreCase(session) || "Pre-Close".equalsIgnoreCase(session)) {
            holder.leftSidePanel.setBackgroundColor(Color.parseColor("#E11D48")); // Red
        } else {
            holder.leftSidePanel.setBackgroundColor(Color.parseColor("#7C3AED")); // Purple
        }

        String bias = card.getBias() != null ? card.getBias().toUpperCase(Locale.ROOT) : "NEUTRAL";
        holder.tvBiasTag.setText(bias);
        if ("BULLISH".equalsIgnoreCase(bias)) {
            holder.tvBiasTag.setTextColor(ContextCompat.getColor(context, R.color.bullish_text));
        } else if ("BEARISH".equalsIgnoreCase(bias)) {
            holder.tvBiasTag.setTextColor(ContextCompat.getColor(context, R.color.bearish_text));
        } else {
            holder.tvBiasTag.setTextColor(ContextCompat.getColor(context, R.color.trading_blue));
        }

        holder.tvReadTime.setText("⏱️ 45 sec read");

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
        LinearLayout leftSidePanel;
        TextView tvPanelDate, tvPanelTime, tvTitle, tvSummary, tvBiasTag, tvReadTime;

        public SessionNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            leftSidePanel = itemView.findViewById(R.id.leftSidePanel);
            tvPanelDate = itemView.findViewById(R.id.tvPanelDate);
            tvPanelTime = itemView.findViewById(R.id.tvPanelTime);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSummary = itemView.findViewById(R.id.tvSummary);
            tvBiasTag = itemView.findViewById(R.id.tvBiasTag);
            tvReadTime = itemView.findViewById(R.id.tvReadTime);
        }
    }
}

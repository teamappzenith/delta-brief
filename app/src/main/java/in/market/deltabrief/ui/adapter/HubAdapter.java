package in.market.deltabrief.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import in.market.deltabrief.R;
import in.market.deltabrief.model.HubItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HubAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HubItem> items = new ArrayList<>();
    private OnHubItemClickListener clickListener;

    public interface OnHubItemClickListener {
        void onHubItemClick(HubItem item);
    }

    public void setOnHubItemClickListener(OnHubItemClickListener listener) {
        this.clickListener = listener;
    }

    public void setItems(List<HubItem> items) {
        this.items = items != null ? items : new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getViewType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HubItem.TYPE_SECTION_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hub_section_header, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == HubItem.TYPE_SESSION_HUB) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_session_hub, parent, false);
            return new SessionHubViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hub_card, parent, false);
            return new ToolCardViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HubItem item = items.get(position);

        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).tvSectionHeader.setText(item.getTitle());
        } else if (holder instanceof SessionHubViewHolder) {
            SessionHubViewHolder sessionHolder = (SessionHubViewHolder) holder;
            String category = item.getCategory();

            sessionHolder.tvHubSessionTitle.setText(item.getTitle().toUpperCase(Locale.ROOT));
            sessionHolder.tvHubDescription.setText(item.getDescription());
            sessionHolder.tvHubReleaseTime.setText("⏱️ " + item.getSubtitle());

            int count = item.getBadgeCount();
            sessionHolder.tvHubBriefsCount.setText("● " + count + (count == 1 ? " Brief Available" : " Briefs Available"));

            sessionHolder.tvHubSessionTitle.setBackgroundResource(R.drawable.bg_glossy_pill);
            sessionHolder.tvHubBriefsCount.setBackgroundResource(R.drawable.bg_glossy_pill);

            // Full-Card Gradient Background based on session
            if ("Pre-Market".equalsIgnoreCase(category)) {
                sessionHolder.fullCardContainer.setBackgroundResource(R.drawable.bg_gradient_premarket);
            } else if ("Stabilized".equalsIgnoreCase(category)) {
                sessionHolder.fullCardContainer.setBackgroundResource(R.drawable.bg_gradient_stabilized);
            } else if ("Mid-Day".equalsIgnoreCase(category)) {
                sessionHolder.fullCardContainer.setBackgroundResource(R.drawable.bg_gradient_midday);
            } else if ("Post-Market".equalsIgnoreCase(category)) {
                sessionHolder.fullCardContainer.setBackgroundResource(R.drawable.bg_gradient_postmarket);
            } else {
                sessionHolder.fullCardContainer.setBackgroundColor(Color.parseColor("#121824"));
            }

            sessionHolder.itemView.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.onHubItemClick(item);
                }
            });
        } else if (holder instanceof ToolCardViewHolder) {
            ToolCardViewHolder toolHolder = (ToolCardViewHolder) holder;
            toolHolder.tvHubTitle.setText(item.getTitle());
            toolHolder.tvHubSubtitle.setText(item.getSubtitle());

            if (item.getIconResId() != 0) {
                toolHolder.imgIcon.setImageResource(item.getIconResId());
            }

            if (item.getColorHex() != null && !item.getColorHex().isEmpty()) {
                toolHolder.iconBg.setBackgroundColor(Color.parseColor(item.getColorHex()));
            }

            if (item.getBadgeCount() > 0) {
                toolHolder.tvBadgeCount.setVisibility(View.VISIBLE);
                toolHolder.tvBadgeCount.setText(String.valueOf(item.getBadgeCount()));
            } else {
                toolHolder.tvBadgeCount.setVisibility(View.GONE);
            }

            toolHolder.itemView.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.onHubItemClick(item);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tvSectionHeader;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSectionHeader = itemView.findViewById(R.id.tvSectionHeader);
        }
    }

    static class SessionHubViewHolder extends RecyclerView.ViewHolder {
        LinearLayout fullCardContainer;
        TextView tvHubSessionTitle, tvHubDescription, tvHubReleaseTime, tvHubBriefsCount;

        public SessionHubViewHolder(@NonNull View itemView) {
            super(itemView);
            fullCardContainer = itemView.findViewById(R.id.fullCardContainer);
            tvHubSessionTitle = itemView.findViewById(R.id.tvHubSessionTitle);
            tvHubDescription = itemView.findViewById(R.id.tvHubDescription);
            tvHubReleaseTime = itemView.findViewById(R.id.tvHubReleaseTime);
            tvHubBriefsCount = itemView.findViewById(R.id.tvHubBriefsCount);
        }
    }

    static class ToolCardViewHolder extends RecyclerView.ViewHolder {
        View iconBg;
        ImageView imgIcon;
        TextView tvBadgeCount, tvHubTitle, tvHubSubtitle;

        public ToolCardViewHolder(@NonNull View itemView) {
            super(itemView);
            iconBg = itemView.findViewById(R.id.iconBg);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            tvBadgeCount = itemView.findViewById(R.id.tvBadgeCount);
            tvHubTitle = itemView.findViewById(R.id.tvHubTitle);
            tvHubSubtitle = itemView.findViewById(R.id.tvHubSubtitle);
        }
    }
}

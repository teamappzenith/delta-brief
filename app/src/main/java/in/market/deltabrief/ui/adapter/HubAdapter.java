package in.market.deltabrief.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_session_hub, parent, false);
            return new SessionHubViewHolder(view);
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
            sessionHolder.tvHubReleaseTime.setText(item.getSubtitle());

            if ("Post-Market".equalsIgnoreCase(category)) {
                sessionHolder.tvHubStatusBadge.setText("Upcoming");
                sessionHolder.tvHubStatusBadge.setTextColor(Color.parseColor("#C2410C")); // Orange
            } else {
                sessionHolder.tvHubStatusBadge.setText("Published");
                sessionHolder.tvHubStatusBadge.setTextColor(Color.parseColor("#16A34A")); // Green
            }

            sessionHolder.itemView.setOnClickListener(v -> {
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
        TextView tvHubSessionTitle, tvHubStatusBadge, tvHubReleaseTime, tvHubDescription;

        public SessionHubViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHubSessionTitle = itemView.findViewById(R.id.tvHubSessionTitle);
            tvHubStatusBadge = itemView.findViewById(R.id.tvHubStatusBadge);
            tvHubReleaseTime = itemView.findViewById(R.id.tvHubReleaseTime);
            tvHubDescription = itemView.findViewById(R.id.tvHubDescription);
        }
    }
}

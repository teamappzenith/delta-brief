package in.market.deltabrief.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import in.market.deltabrief.R;

import java.util.List;

public class CalendarAdapter extends BaseAdapter {
    private final Context context;
    private List<CalendarDay> days;
    private final LayoutInflater inflater;

    public CalendarAdapter(Context context, List<CalendarDay> days) {
        this.context = context;
        this.days = days;
        this.inflater = LayoutInflater.from(context);
    }

    public void updateData(List<CalendarDay> days) {
        this.days = days;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Object getItem(int position) {
        return days.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        CalendarDay day = days.get(position);
        return day != null && day.getDayNumber() > 0 && !day.isDisabled();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_calendar_day, parent, false);
        } else {
            view = convertView;
        }

        TextView tvDayNumber = view.findViewById(R.id.tvDayNumber);
        CalendarDay calendarDay = days.get(position);

        if (calendarDay == null || calendarDay.getDayNumber() == 0) {
            tvDayNumber.setText("");
            tvDayNumber.setBackgroundColor(Color.TRANSPARENT);
            view.setEnabled(false);
            view.setClickable(false);
        } else {
            tvDayNumber.setText(String.valueOf(calendarDay.getDayNumber()));
            if (calendarDay.isSelected()) {
                tvDayNumber.setBackgroundResource(R.drawable.bg_date_selected_circle);
                tvDayNumber.setTextColor(Color.WHITE);
                tvDayNumber.setAlpha(1f);
            } else if (calendarDay.isDisabled()) {
                tvDayNumber.setBackgroundColor(Color.TRANSPARENT);
                tvDayNumber.setTextColor(Color.parseColor("#94A3B8")); // Muted grey for current & future dates
                tvDayNumber.setAlpha(0.4f);
            } else {
                tvDayNumber.setBackgroundColor(Color.TRANSPARENT);
                tvDayNumber.setTextColor(ContextCompat.getColor(context, R.color.text_primary));
                tvDayNumber.setAlpha(1f);
            }
            view.setEnabled(!calendarDay.isDisabled());
            view.setClickable(calendarDay.isDisabled());
        }
        return view;
    }

    public static class CalendarDay {
        private final int dayNumber;
        private final boolean isSelected;
        private final boolean isDisabled;

        public CalendarDay(int dayNumber, boolean isSelected, boolean isDisabled) {
            this.dayNumber = dayNumber;
            this.isSelected = isSelected;
            this.isDisabled = isDisabled;
        }

        public int getDayNumber() { return dayNumber; }
        public boolean isSelected() { return isSelected; }
        public boolean isDisabled() { return isDisabled; }
    }
}

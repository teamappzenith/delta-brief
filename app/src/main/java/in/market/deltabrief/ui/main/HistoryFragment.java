package in.market.deltabrief.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import in.market.deltabrief.R;
import in.market.deltabrief.ui.adapter.CalendarAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HistoryFragment extends Fragment {

    private TextView tvMonthYear, tvSelectedDateHeader;
    private GridView calendarGridView;
    private View sessionsContainer;
    private Calendar currentCalendar;
    private Calendar selectedCalendar;
    private CalendarAdapter calendarAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        tvMonthYear = view.findViewById(R.id.tvMonthYear);
        tvSelectedDateHeader = view.findViewById(R.id.tvSelectedDateHeader);
        calendarGridView = view.findViewById(R.id.calendarGridView);
        sessionsContainer = view.findViewById(R.id.sessionsContainer);

        currentCalendar = Calendar.getInstance();
        currentCalendar.set(2026, Calendar.NOVEMBER, 1);

        selectedCalendar = Calendar.getInstance();
        selectedCalendar.set(2026, Calendar.NOVEMBER, 10); // Default to a past selectable date

        setupCalendar();

        view.findViewById(R.id.btnPrevMonth).setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, -1);
            setupCalendar();
        });

        view.findViewById(R.id.btnNextMonth).setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, 1);
            setupCalendar();
        });

        view.findViewById(R.id.rowPreMarket).setOnClickListener(v -> openSessionFeed("Pre-Market"));
        view.findViewById(R.id.rowStabilized).setOnClickListener(v -> openSessionFeed("Stabilized"));
        view.findViewById(R.id.rowMidDay).setOnClickListener(v -> openSessionFeed("Mid-Day"));
        view.findViewById(R.id.rowPostMarket).setOnClickListener(v -> openSessionFeed("Post-Market"));

        return view;
    }

    private void setupCalendar() {
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        tvMonthYear.setText(monthFormat.format(currentCalendar.getTime()));

        List<CalendarAdapter.CalendarDay> days = generateDaysForMonth(currentCalendar);
        if (calendarAdapter == null) {
            calendarAdapter = new CalendarAdapter(requireContext(), days);
            calendarGridView.setAdapter(calendarAdapter);
            calendarGridView.setOnItemClickListener((parent, view, position, id) -> {
                CalendarAdapter.CalendarDay dayObj = (CalendarAdapter.CalendarDay) calendarAdapter.getItem(position);
                if (dayObj != null && dayObj.getDayNumber() > 0 && !dayObj.isDisabled()) {
                    selectedCalendar.set(currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), dayObj.getDayNumber());
                    setupCalendar();

                    if (sessionsContainer != null) {
                        sessionsContainer.animate().alpha(0f).setDuration(100).withEndAction(() -> {
                            updateSelectedDateHeader();
                            sessionsContainer.animate().alpha(1f).setDuration(150).start();
                        }).start();
                    } else {
                        updateSelectedDateHeader();
                    }
                }
            });
        } else {
            calendarAdapter.updateData(days);
        }

        updateSelectedDateHeader();
    }

    private List<CalendarAdapter.CalendarDay> generateDaysForMonth(Calendar cal) {
        List<CalendarAdapter.CalendarDay> days = new ArrayList<>();
        Calendar c = (Calendar) cal.clone();
        c.set(Calendar.DAY_OF_MONTH, 1);

        int firstDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int shift = (firstDayOfWeek + 5) % 7;

        for (int i = 0; i < shift; i++) {
            days.add(null);
        }

        // Reference today for disabling current and future dates (e.g., Nov 14, 2026)
        Calendar today = Calendar.getInstance();
        today.set(2026, Calendar.NOVEMBER, 14);

        int maxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= maxDays; i++) {
            Calendar checkCal = (Calendar) c.clone();
            checkCal.set(Calendar.DAY_OF_MONTH, i);

            boolean isSelected = (checkCal.get(Calendar.YEAR) == selectedCalendar.get(Calendar.YEAR) &&
                    checkCal.get(Calendar.MONTH) == selectedCalendar.get(Calendar.MONTH) &&
                    checkCal.get(Calendar.DAY_OF_MONTH) == selectedCalendar.get(Calendar.DAY_OF_MONTH));

            // Disable current date and future dates (checkCal >= today)
            boolean isDisabled = !checkCal.before(today);

            days.add(new CalendarAdapter.CalendarDay(i, isSelected, isDisabled));
        }

        return days;
    }

    private void updateSelectedDateHeader() {
        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy", Locale.getDefault());
        tvSelectedDateHeader.setText(sdf.format(selectedCalendar.getTime()));
    }

    private void openSessionFeed(String sessionName) {
        Intent intent = new Intent(requireContext(), SessionFeedActivity.class);
        intent.putExtra(SessionFeedActivity.EXTRA_SESSION_NAME, sessionName);
        startActivity(intent);
    }
}

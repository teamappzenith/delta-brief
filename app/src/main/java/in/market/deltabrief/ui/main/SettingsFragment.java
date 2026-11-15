package in.market.deltabrief.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import in.market.deltabrief.R;

public class SettingsFragment extends Fragment {

    private static final String PREF_THEME = "pref_theme_mode";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        view.findViewById(R.id.optionTheme).setOnClickListener(v -> {
            String[] themes = {"Light Theme", "Dark Theme", "System Default"};
            SharedPreferences prefs = requireContext().getSharedPreferences("delta_brief_prefs", Context.MODE_PRIVATE);
            int currentChoice = prefs.getInt(PREF_THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

            int checkedItem = 2;
            if (currentChoice == AppCompatDelegate.MODE_NIGHT_NO) checkedItem = 0;
            else if (currentChoice == AppCompatDelegate.MODE_NIGHT_YES) checkedItem = 1;

            new AlertDialog.Builder(requireContext())
                    .setTitle("Select App Theme")
                    .setSingleChoiceItems(themes, checkedItem, (dialog, which) -> {
                        int mode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
                        if (which == 0) mode = AppCompatDelegate.MODE_NIGHT_NO;
                        else if (which == 1) mode = AppCompatDelegate.MODE_NIGHT_YES;

                        prefs.edit().putInt(PREF_THEME, mode).apply();
                        AppCompatDelegate.setDefaultNightMode(mode);
                        dialog.dismiss();
                        requireActivity().recreate();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        view.findViewById(R.id.optionNotifications).setOnClickListener(v ->
                Toast.makeText(requireContext(), "Notifications settings clicked", Toast.LENGTH_SHORT).show());
        view.findViewById(R.id.optionSchedule).setOnClickListener(v ->
                Toast.makeText(requireContext(), "Briefing Schedule clicked", Toast.LENGTH_SHORT).show());
        view.findViewById(R.id.optionLanguage).setOnClickListener(v ->
                Toast.makeText(requireContext(), "Language settings clicked", Toast.LENGTH_SHORT).show());
        view.findViewById(R.id.optionProfile).setOnClickListener(v ->
                Toast.makeText(requireContext(), "Profile clicked", Toast.LENGTH_SHORT).show());
        view.findViewById(R.id.optionPrivacy).setOnClickListener(v ->
                Toast.makeText(requireContext(), "Privacy Policy clicked", Toast.LENGTH_SHORT).show());
        view.findViewById(R.id.optionTerms).setOnClickListener(v ->
                Toast.makeText(requireContext(), "Terms and Conditions clicked", Toast.LENGTH_SHORT).show());

        return view;
    }
}

package in.market.deltabrief.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import in.market.deltabrief.R;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREF_THEME = "pref_theme_mode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_settings);

        ImageButton btnBack = findViewById(R.id.btnBack);
        TextView optionRateUs = findViewById(R.id.optionRateUs);
        TextView optionShareApp = findViewById(R.id.optionShareApp);
        TextView optionFeedback = findViewById(R.id.optionFeedback);
        TextView optionTheme = findViewById(R.id.optionTheme);
        TextView optionAbout = findViewById(R.id.optionAbout);

        btnBack.setOnClickListener(v -> finish());

        optionRateUs.setOnClickListener(v -> {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
            }
        });

        optionShareApp.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Download DeltaBrief for fast, snackable trading session briefings: https://play.google.com/store/apps/details?id=" + getPackageName());
            startActivity(Intent.createChooser(shareIntent, "Share DeltaBrief"));
        });

        optionFeedback.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:support@deltabrief.in"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "DeltaBrief App Feedback");
            try {
                startActivity(Intent.createChooser(emailIntent, "Send Feedback"));
            } catch (Exception ignored) {}
        });

        optionTheme.setOnClickListener(v -> {
            String[] themes = {"Light Theme", "Dark Theme", "System Default"};
            SharedPreferences prefs = getSharedPreferences("delta_brief_prefs", Context.MODE_PRIVATE);
            int currentChoice = prefs.getInt(PREF_THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

            int checkedItem = 2;
            if (currentChoice == AppCompatDelegate.MODE_NIGHT_NO) checkedItem = 0;
            else if (currentChoice == AppCompatDelegate.MODE_NIGHT_YES) checkedItem = 1;

            new AlertDialog.Builder(this)
                    .setTitle("Select App Theme")
                    .setSingleChoiceItems(themes, checkedItem, (dialog, which) -> {
                        int mode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
                        if (which == 0) mode = AppCompatDelegate.MODE_NIGHT_NO;
                        else if (which == 1) mode = AppCompatDelegate.MODE_NIGHT_YES;

                        prefs.edit().putInt(PREF_THEME, mode).apply();
                        AppCompatDelegate.setDefaultNightMode(mode);
                        dialog.dismiss();
                        recreate();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        optionAbout.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("About DeltaBrief (v1.0.0)")
                    .setMessage("DeltaBrief delivers actionable, bite-sized market intelligence across 4 daily trading sessions:\n\n1. Pre-Market (8:00 AM)\n2. Stabilized (11:00 AM)\n3. Mid-Day (2:00 PM)\n4. Post-Market (4:00 PM)\n\nBuilt for modern traders and investors.")
                    .setPositiveButton("Close", null)
                    .show();
        });
    }
}

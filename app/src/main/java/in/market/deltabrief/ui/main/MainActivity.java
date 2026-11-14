package in.market.deltabrief.ui.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import in.market.deltabrief.R;

public class MainActivity extends AppCompatActivity {

    private boolean isLevelsExpanded = true;
    private boolean isConditionsExpanded = true;
    private boolean isBriefingsExpanded = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences("delta_brief_prefs", MODE_PRIVATE);
        int savedTheme = prefs.getInt("pref_theme_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        AppCompatDelegate.setDefaultNightMode(savedTheme);

        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);

        ImageButton btnHeaderSettings = findViewById(R.id.btnHeaderSettings);
        if (btnHeaderSettings != null) {
            btnHeaderSettings.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
        }

        // Market Levels Collapse/Expand
        LinearLayout layoutMarketLevelsHeader = findViewById(R.id.layoutMarketLevelsHeader);
        LinearLayout layoutMarketLevelsContent = findViewById(R.id.layoutMarketLevelsContent);
        ImageView imgArrowLevels = findViewById(R.id.imgArrowLevels);

        layoutMarketLevelsHeader.setOnClickListener(v -> toggleSection(layoutMarketLevelsContent, imgArrowLevels));

        // Market Conditions Collapse/Expand
        LinearLayout layoutMarketConditionsHeader = findViewById(R.id.layoutMarketConditionsHeader);
        LinearLayout layoutMarketConditionsContent = findViewById(R.id.layoutMarketConditionsContent);
        ImageView imgArrowConditions = findViewById(R.id.imgArrowConditions);

        layoutMarketConditionsHeader.setOnClickListener(v -> toggleSection(layoutMarketConditionsContent, imgArrowConditions));

        // Today's Briefings Collapse/Expand
        LinearLayout layoutBriefingsHeader = findViewById(R.id.layoutBriefingsHeader);
        LinearLayout layoutBriefingsContent = findViewById(R.id.layoutBriefingsContent);
        ImageView imgArrowBriefings = findViewById(R.id.imgArrowBriefings);

        layoutBriefingsHeader.setOnClickListener(v -> toggleSection(layoutBriefingsContent, imgArrowBriefings));

        // Session Clicks
        findViewById(R.id.rowPreMarket).setOnClickListener(v -> openSessionFeed("Pre-Market"));
        findViewById(R.id.rowStabilized).setOnClickListener(v -> openSessionFeed("Stabilized"));
        findViewById(R.id.rowMidDay).setOnClickListener(v -> openSessionFeed("Mid-Day"));
        findViewById(R.id.rowPostMarket).setOnClickListener(v -> openSessionFeed("Post-Market"));
    }

    private void toggleSection(final LinearLayout contentLayout, final ImageView arrowView) {
        boolean isExpanded = contentLayout.getVisibility() == View.VISIBLE;
        if (isExpanded) {
            // Collapse Animation
            int initialHeight = contentLayout.getMeasuredHeight();
            ValueAnimator anim = ValueAnimator.ofInt(initialHeight, 0);
            anim.addUpdateListener(animation -> {
                int val = (Integer) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = contentLayout.getLayoutParams();
                layoutParams.height = val;
                contentLayout.setLayoutParams(layoutParams);
            });
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    contentLayout.setVisibility(View.GONE);
                    ViewGroup.LayoutParams layoutParams = contentLayout.getLayoutParams();
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    contentLayout.setLayoutParams(layoutParams);
                }
            });
            anim.setDuration(250);
            anim.start();
            arrowView.setImageResource(R.drawable.ic_chevron_down);
        } else {
            // Expand Animation
            contentLayout.setVisibility(View.VISIBLE);
            contentLayout.measure(
                    View.MeasureSpec.makeMeasureSpec(((View) contentLayout.getParent()).getWidth(), View.MeasureSpec.AT_MOST),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            );
            int targetHeight = contentLayout.getMeasuredHeight();

            ViewGroup.LayoutParams layoutParams = contentLayout.getLayoutParams();
            layoutParams.height = 0;
            contentLayout.setLayoutParams(layoutParams);

            ValueAnimator anim = ValueAnimator.ofInt(0, targetHeight);
            anim.addUpdateListener(animation -> {
                int val = (Integer) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = contentLayout.getLayoutParams();
                params.height = val;
                contentLayout.setLayoutParams(params);
            });
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    ViewGroup.LayoutParams params = contentLayout.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    contentLayout.setLayoutParams(params);
                }
            });
            anim.setDuration(250);
            anim.start();
            arrowView.setImageResource(R.drawable.ic_chevron_up);
        }
    }

    private void openSessionFeed(String sessionName) {
        Intent intent = new Intent(this, SessionFeedActivity.class);
        intent.putExtra(SessionFeedActivity.EXTRA_SESSION_NAME, sessionName);
        startActivity(intent);
    }
}

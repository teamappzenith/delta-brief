package in.market.deltabrief.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import in.market.deltabrief.R;

public class BriefingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_briefings, container, false);

        view.findViewById(R.id.cardPreMarket).setOnClickListener(v -> openSessionFeed("Pre-Market"));
        view.findViewById(R.id.cardStabilized).setOnClickListener(v -> openSessionFeed("Stabilized"));
        view.findViewById(R.id.cardMidDay).setOnClickListener(v -> openSessionFeed("Mid-Day"));
        view.findViewById(R.id.cardPostMarket).setOnClickListener(v -> openSessionFeed("Post-Market"));

        return view;
    }

    private void openSessionFeed(String sessionName) {
        Intent intent = new Intent(requireContext(), SessionFeedActivity.class);
        intent.putExtra(SessionFeedActivity.EXTRA_SESSION_NAME, sessionName);
        startActivity(intent);
    }
}

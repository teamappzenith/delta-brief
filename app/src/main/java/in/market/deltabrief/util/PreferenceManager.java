package in.market.deltabrief.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private final SharedPreferences sharedPreferences;

    public PreferenceManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE);
    }

    public long getLocalVersion() {
        return sharedPreferences.getLong(AppConstants.KEY_LOCAL_VERSION, 0L);
    }

    public void setLocalVersion(long version) {
        sharedPreferences.edit().putLong(AppConstants.KEY_LOCAL_VERSION, version).apply();
    }
}

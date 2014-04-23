package eu.roklapps.fuellog.app.sharedprefs;

import android.content.Context;
import android.os.AsyncTask;

public class Prefs {
    public static final String PREFS_NAME = "fuelpref";
    public static final String KEY_SUBFRAGMENT_OPEN = "subfragment_car_active";

    public static void setCarSubFragment(final Context context, final boolean value) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().putBoolean(Prefs.KEY_SUBFRAGMENT_OPEN, value).apply();
                return null;
            }
        }.execute();
    }

    public static boolean isCarSubFragmentActive(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getBoolean(KEY_SUBFRAGMENT_OPEN, false);
    }
}

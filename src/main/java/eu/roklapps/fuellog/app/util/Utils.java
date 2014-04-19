package eu.roklapps.fuellog.app.util;

import android.os.StrictMode;

import eu.roklapps.fuellog.app.MainActivity;
import eu.roklapps.fuellog.app.ui.fragment.carpark.CarparkFragment;
import eu.roklapps.fuellog.app.ui.fragment.FuelRecordingFragment;
import eu.roklapps.fuellog.app.ui.fragment.NavigationDrawerFragment;

public class Utils {
    private Utils() {
    }

    public static void enableStrictMode() {
        StrictMode.ThreadPolicy.Builder threadPolicyBuilder =
                new StrictMode.ThreadPolicy.Builder()
                        .detectAll()
                        .penaltyLog();

        StrictMode.VmPolicy.Builder vmPolicyBuilder =
                new StrictMode.VmPolicy.Builder()
                        .detectAll()
                        .penaltyLog();

        threadPolicyBuilder.penaltyFlashScreen();
        vmPolicyBuilder
                .setClassInstanceLimit(MainActivity.class, 1)
                .setClassInstanceLimit(FuelRecordingFragment.class, 1)
                .setClassInstanceLimit(CarparkFragment.class, 1)
                .setClassInstanceLimit(NavigationDrawerFragment.class, 1);


        StrictMode.setThreadPolicy(threadPolicyBuilder.build());
        StrictMode.setVmPolicy(vmPolicyBuilder.build());
    }

}

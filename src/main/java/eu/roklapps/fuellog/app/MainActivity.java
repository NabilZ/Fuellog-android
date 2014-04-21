package eu.roklapps.fuellog.app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import eu.roklapps.fuellog.app.sharedprefs.Prefs;
import eu.roklapps.fuellog.app.ui.fragment.FuelListingFragmentFragment;
import eu.roklapps.fuellog.app.ui.fragment.FuelRecordingFragment;
import eu.roklapps.fuellog.app.ui.fragment.NavigationDrawerFragment;
import eu.roklapps.fuellog.app.ui.fragment.carpark.CarAddFragment;
import eu.roklapps.fuellog.app.ui.fragment.carpark.CarparkFragment;
import eu.roklapps.fuellog.app.util.Utils;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        CarparkFragment.OnFragmentInteractionListener,
        FuelListingFragmentFragment.OnFragmentInteractionListener {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utils.enableStrictMode();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        Fragment fragment = null;
        if (position == 0) {
            fragment = new FuelRecordingFragment();
        } else if (position == 1) {
            fragment = new FuelListingFragmentFragment();
        } else if (position == 2) {
            fragment = getCarFragment();
        }
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private Fragment getCarFragment() {
        boolean carFlag = Prefs.isCarSubFragmentActive(this);
        Fragment fragment;
        if (!carFlag) {
            fragment = new CarparkFragment();
        } else {
            fragment = getFragmentManager().findFragmentById(R.id.container);
        }
        return fragment;
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String id) {

    }

    @Override
    public void onAddNewCar() {
        Prefs.setCarSubFragment(this, true);
        getFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, new CarAddFragment())
                .commit();
    }
}

/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.roklapps.fuellog.app.ui.fragment.carpark;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import eu.roklapps.fuellog.app.R;
import eu.roklapps.fuellog.app.db.FuelDatabase;


public class CarAddFragment extends Fragment {
    private static final String TAG = "CarAddFragment";
    private Spinner mGasType;
    private EditText mVendor;
    private EditText mName;

    public CarAddFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_car_add, container, false);

        setupLayout(fragmentView);
        setRetainInstance(true);
        return fragmentView;

    }

    private void setupLayout(View fragmentView) {
        final LayoutInflater inflater = (LayoutInflater) getActivity().getActionBar().getThemedContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View customActionBarView = inflater.inflate(
                R.layout.actionbar_custom_view_done_cancel, null);
        customActionBarView.findViewById(R.id.actionbar_done).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prepareSaving();
                    }
                }
        );
        customActionBarView.findViewById(R.id.actionbar_cancel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        restoreDefaultActionbar();
                        dismissFragment();
                    }
                }
        );

        // Show the custom action bar view and hide the normal Home icon and title.
        final ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayOptions(
                ActionBar.DISPLAY_SHOW_CUSTOM,
                ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
                        | ActionBar.DISPLAY_SHOW_TITLE
        );
        actionBar.setCustomView(customActionBarView,
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT)
        );

        mGasType = (Spinner) fragmentView.findViewById(R.id.gas_type_spinner);
        mVendor = (EditText) fragmentView.findViewById(R.id.vendor);
        mName = (EditText) fragmentView.findViewById(R.id.name);
    }

    private void prepareSaving() {
        if (isSavingAllowed()) {
            restoreDefaultActionbar();
            new CarSaver().execute();
        }
    }

    private boolean isSavingAllowed() {
        if (!isVendorFilled() && !isNameFilled()) {
            Crouton.makeText(getActivity(), R.string.car_add_error_everything_empty, Style.ALERT).show();
            return false;
        } else if (!isVendorFilled()) {
            Crouton.makeText(getActivity(), R.string.car_vendor_empty, Style.ALERT).show();
            return false;
        } else if (!isNameFilled()) {
            Crouton.makeText(getActivity(), R.string.car_name_empty, Style.ALERT).show();
            return false;
        }
        return true;
    }

    private boolean isVendorFilled() {
        return mVendor.getText().toString().length() > 0 && !mVendor.getText().toString().isEmpty();
    }

    private boolean isNameFilled() {
        return mName.getText().toString().length() > 0 && !mName.getText().toString().isEmpty();
    }

    private void restoreDefaultActionbar() {
        getActivity().getActionBar().setDisplayShowCustomEnabled(false);
        getActivity().getActionBar().setDisplayOptions(
                ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
    }

    private void dismissFragment() {
        getActivity().getFragmentManager().popBackStack();
    }

    private class CarSaver extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(FuelDatabase.CARS_VENDOR, mVendor.getText().toString());
            contentValues.put(FuelDatabase.NAME, mName.getText().toString());
            contentValues.put(FuelDatabase.GAS_TYPE_TABLE, mGasType.getSelectedItemId());

            FuelDatabase database = new FuelDatabase(getActivity());
            database.saveNewCar(contentValues);
            database.close();
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            dismissFragment();
        }
    }
}

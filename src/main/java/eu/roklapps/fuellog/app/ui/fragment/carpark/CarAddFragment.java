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

import android.app.Fragment;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import eu.roklapps.fuellog.app.R;
import eu.roklapps.fuellog.app.db.FuelDatabase;
import eu.roklapps.fuellog.app.sharedprefs.Prefs;
import eu.roklapps.fuellog.app.ui.card.CarAddCard;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;

import static android.view.View.OnClickListener;


@SuppressWarnings("EmptyMethod")
public class CarAddFragment extends Fragment implements OnClickListener {
    private CarAddCard mCard;
    private Button mSaveButton;


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
        mCard = new CarAddCard(getActivity());
        CardHeader header = new CardHeader(getActivity());
        header.setTitle(getString(R.string.add_new_car));
        mCard.addCardHeader(header);
        CardView cardView = (CardView) fragmentView.findViewById(R.id.add_car_card);

        cardView.setCard(mCard);

        mSaveButton = (Button) fragmentView.findViewById(R.id.save_changes);
        mSaveButton.setOnClickListener(this);
    }

    private void prepareSaving() {
        if (isSavingAllowed()) {
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
        return mCard.getVendor().getText().toString().length() > 0 && !mCard.getVendor().getText().toString().isEmpty();
    }

    private boolean isNameFilled() {
        return mCard.getName().getText().toString().length() > 0 && !mCard.getName().getText().toString().isEmpty();
    }

    private void dismissFragment() {
        Prefs.setCarSubFragment(getActivity(), false);
        getActivity().getFragmentManager().popBackStack();
    }

    @Override
    public void onClick(View v) {
        prepareSaving();
    }

    private class CarSaver extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(FuelDatabase.CARS_VENDOR, mCard.getVendor().getText().toString());
            contentValues.put(FuelDatabase.NAME, mCard.getName().getText().toString());
            contentValues.put(FuelDatabase.GAS_TYPE_TABLE, mCard.getGasType().getSelectedItemId());

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

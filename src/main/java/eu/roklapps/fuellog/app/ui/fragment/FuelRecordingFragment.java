package eu.roklapps.fuellog.app.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import eu.roklapps.fuellog.app.R;
import eu.roklapps.fuellog.app.callbacks.AsyncTaskSaveResult;
import eu.roklapps.fuellog.app.db.FuelDatabase;
import eu.roklapps.fuellog.app.sharedprefs.Prefs;
import eu.roklapps.fuellog.app.ui.card.CardNewFuelCar;
import eu.roklapps.fuellog.app.ui.card.CardNewFuelRecord;
import it.gmariotti.cardslib.library.view.CardView;


public class FuelRecordingFragment extends Fragment implements View.OnClickListener,
        AdapterView.OnItemSelectedListener,
        AsyncTaskSaveResult {
    private static final String RECORD_ID = "record_id";

    private long mRecordId;
    private CardNewFuelCar mFuelCardCar;
    private CardView mCardViewFuel;
    private CardView mCardViewCar;
    private CardNewFuelRecord mNewFuelRecord;
    private ArrayAdapter<String> mCarAdapter;
    private OnFragmentInteractionListener mListener;

    public FuelRecordingFragment() {
    }

    public static Fragment newInstance(long recordId) {
        FuelRecordingFragment fragment = new FuelRecordingFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecordId = getArguments().getLong(RECORD_ID);
        }
        setRetainInstance(true);
        Prefs.setCarSubFragment(getActivity(), false);
        mNewFuelRecord = new CardNewFuelRecord(getActivity());
        mFuelCardCar = new CardNewFuelCar(getActivity());

        new CarParkLoader().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_fuel_recording, container, false);

        setupLayout(fragmentView);

        return fragmentView;
    }

    private void setupLayout(View fragmentView) {
        mCardViewFuel = (CardView) fragmentView.findViewById(R.id.fuel_add);
        mCardViewFuel.setCard(mNewFuelRecord);

        mCardViewCar = (CardView) fragmentView.findViewById(R.id.car_information);
        mCardViewCar.setCard(mFuelCardCar);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save_changes) {/*
            ContentValues contentValues = new ContentValues();
            contentValues.put(FuelDatabase.FUEL_MILEAGE, mMileage.getText().toString());
            contentValues.put(FuelDatabase.FUEL_PRICE_PER_UNIT, mPricePerUnit.getText().toString());
            contentValues.put(FuelDatabase.FUEL_TOTAL_BOUGHT_FUEL, mBoughtFuel.getText().toString());
            contentValues.put(FuelDatabase.FUEL_EVENT_DATE, mEventDate.getText().toString());
            contentValues.put(FuelDatabase.FUEL_USED_CAR, mCarSpinner.getSelectedItemId());
            contentValues.put(FuelDatabase.GAS_TYPE_TABLE, mGasType.getSelectedItemId());

            new FuelSaver(this, getActivity()).execute(contentValues);*/
        } else {

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void finished() {
        Crouton.makeText(getActivity(), R.string.save_successful_completed, Style.CONFIRM);
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    private class CarParkLoader extends AsyncTask<Void, Void, Void> {
        private List<String> mItems;

        @Override
        protected Void doInBackground(Void... params) {
            FuelDatabase database = new FuelDatabase(getActivity());

            mItems = database.getAllCarsAsList();

            database.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mCarAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mItems);
            mNewFuelRecord.getGasType().setAdapter(mCarAdapter);
        }
    }
}

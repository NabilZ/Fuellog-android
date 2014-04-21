package eu.roklapps.fuellog.app.ui.fragment;

import android.app.Fragment;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import eu.roklapps.fuellog.app.R;
import eu.roklapps.fuellog.app.asyncs.FuelSaver;
import eu.roklapps.fuellog.app.callbacks.AsyncTaskSaveResult;
import eu.roklapps.fuellog.app.db.FuelDatabase;
import eu.roklapps.fuellog.app.sharedprefs.Prefs;
import eu.roklapps.fuellog.app.ui.card.CardNewFuelCar;
import eu.roklapps.fuellog.app.ui.card.CardNewFuelRecord;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;


public class FuelRecordingFragment extends Fragment implements View.OnClickListener,
        AdapterView.OnItemSelectedListener,
        AsyncTaskSaveResult {
    private static final String RECORD_ID = "record_id";

    private long mRecordId;
    private Button mSaveButton;
    private CardNewFuelCar mFuelCardCar;
    private CardView mCardViewFuel;
    private CardView mCardViewCar;
    private CardNewFuelRecord mNewFuelRecord;
    private ArrayAdapter<String> mCarAdapter;

    public FuelRecordingFragment() {
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
        CardHeader header = new CardHeader(getActivity());
        header.setTitle(getString(R.string.fuel_informations));
        mNewFuelRecord.addCardHeader(header);
        mCardViewFuel.setCard(mNewFuelRecord);

        mCardViewCar = (CardView) fragmentView.findViewById(R.id.car_information);
        header = new CardHeader(getActivity());
        header.setTitle(getString(R.string.car_informations));
        mFuelCardCar.addCardHeader(header);
        mCardViewCar.setCard(mFuelCardCar);

        mSaveButton = (Button) fragmentView.findViewById(R.id.save);
        mSaveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save_changes) {
            new ErrorValidator().execute();
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

    private class ErrorValidator extends AsyncTask<Void, Void, Boolean> {
        private ContentValues mContentValues;

        @Override
        protected Boolean doInBackground(Void... params) {
            mContentValues = new ContentValues();

            if (validationOfString(mFuelCardCar.getMileAge(), FuelDatabase.FUEL_MILEAGE))
                return false;
            if (validationOfString(mNewFuelRecord.getPricePerUnit(), FuelDatabase.FUEL_PRICE_PER_UNIT))
                return false;

            if (validationOfString(mNewFuelRecord.getFuel(), FuelDatabase.FUEL_TOTAL_BOUGHT_FUEL))
                return false;

            if (validationOfString(mFuelCardCar.getDateSelector(), FuelDatabase.FUEL_EVENT_DATE))
                return false;

            mContentValues.put(FuelDatabase.FUEL_USED_CAR, mFuelCardCar.getCarSpinner().getSelectedItemId());
            mContentValues.put(FuelDatabase.GAS_TYPE_TABLE, mNewFuelRecord.getGasType().getSelectedItemId());

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result)
                new FuelSaver(FuelRecordingFragment.this, getActivity()).execute(mContentValues);
        }

        private boolean validationOfString(EditText editText, String contentValuesKey) {
            String content = editText.getText().toString();

            if (content.length() == 0 && content.isEmpty()) {
                return true;
            }
            mContentValues.put(contentValuesKey, content);

            return false;
        }
    }
}

package eu.roklapps.fuellog.app.ui.fragment.carpark;


import android.app.Fragment;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import eu.roklapps.fuellog.app.R;
import eu.roklapps.fuellog.app.db.FuelDatabase;


public class CarAddFragment extends Fragment implements View.OnClickListener{
    private Button mSaveButton;
    private Spinner mGasType;
    private EditText mVendor;
    private EditText mName;

    public CarAddFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_car_add, container, false);

        setupLayout(fragmentView);

        return fragmentView;
    }

    private void setupLayout(View fragmentView) {
        mSaveButton = (Button) fragmentView.findViewById(R.id.save_button);
        mGasType = (Spinner) fragmentView.findViewById(R.id.gas_type_spinner);
        mVendor = (EditText) fragmentView.findViewById(R.id.vendor);
        mName = (EditText) fragmentView.findViewById(R.id.name);

        mSaveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new CarSaver().execute();
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
            getActivity().getFragmentManager().popBackStack();
        }
    }
}

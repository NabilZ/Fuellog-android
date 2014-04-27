package eu.roklapps.fuellog.app.ui.card;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import eu.roklapps.fuellog.app.R;
import it.gmariotti.cardslib.library.internal.Card;

public class CardNewFuelCar extends Card {
    private EditText mDateSelector;
    private Spinner mCarSpinner;
    private EditText mMileAge;

    public CardNewFuelCar(Context context) {
        super(context, R.layout.card_add_fuel_car);
    }

    public EditText getDateSelector() {
        return mDateSelector;
    }

    public Spinner getCarSpinner() {
        return mCarSpinner;
    }

    public EditText getMileAge() {
        return mMileAge;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        mDateSelector = (EditText) parent.findViewById(R.id.dateselector);
        mCarSpinner = (Spinner) parent.findViewById(R.id.car_spinner);
        mMileAge = (EditText) parent.findViewById(R.id.mileage);
    }
}

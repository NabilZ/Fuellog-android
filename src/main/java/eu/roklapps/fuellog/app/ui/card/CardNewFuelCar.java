package eu.roklapps.fuellog.app.ui.card;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import eu.roklapps.fuellog.app.R;
import it.gmariotti.cardslib.library.internal.Card;

public class CardNewFuelCar extends Card {
    private TextView mDateSelector;
    private Spinner mCarSpinner;
    private EditText mMileAge;

    public CardNewFuelCar(Context context) {
        this(context, R.layout.card_add_fuel_car);
    }

    public CardNewFuelCar(Context context, int innerLayout) {
        super(context, innerLayout);
    }

    public TextView getmDateSelector() {
        return mDateSelector;
    }

    public Spinner getmCarSpinner() {
        return mCarSpinner;
    }

    public EditText getmMileAge() {
        return mMileAge;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        mDateSelector = (TextView) parent.findViewById(R.id.dateselector);
        mCarSpinner = (Spinner) parent.findViewById(R.id.car_spinner);
        mMileAge = (EditText) parent.findViewById(R.id.mileage);
    }
}

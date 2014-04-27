package eu.roklapps.fuellog.app.ui.card;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import eu.roklapps.fuellog.app.R;
import it.gmariotti.cardslib.library.internal.Card;

public class CardNewFuelRecord extends Card {
    private Spinner mGasType;
    private EditText mFuel;
    private EditText mPricePerUnit;

    public CardNewFuelRecord(Context context) {
        super(context, R.layout.card_add_fuel_informations);
    }

    public Spinner getGasType() {
        return mGasType;
    }

    public EditText getFuel() {
        return mFuel;
    }

    public EditText getPricePerUnit() {
        return mPricePerUnit;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        mPricePerUnit = (EditText) parent.findViewById(R.id.price_per_unit);
        mFuel = (EditText) parent.findViewById(R.id.fuel);
        mGasType = (Spinner) parent.findViewById(R.id.type_of_fuel_spinner);
    }
}

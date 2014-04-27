package eu.roklapps.fuellog.app.ui.card;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import eu.roklapps.fuellog.app.R;
import it.gmariotti.cardslib.library.internal.Card;

public class CarAddCard extends Card {
    private EditText mVendor;
    private EditText mName;
    private Spinner mGasType;

    public CarAddCard(Context context) {
        super(context, R.layout.card_car_add);
    }

    public EditText getVendor() {
        return mVendor;
    }

    public EditText getName() {
        return mName;
    }

    public Spinner getGasType() {
        return mGasType;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        mVendor = (EditText) parent.findViewById(R.id.vendor);
        mName = (EditText) parent.findViewById(R.id.name);
        mGasType = (Spinner) parent.findViewById(R.id.gas_type_spinner);
    }
}

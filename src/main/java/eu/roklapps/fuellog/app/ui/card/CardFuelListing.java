package eu.roklapps.fuellog.app.ui.card;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import eu.roklapps.fuellog.app.R;
import it.gmariotti.cardslib.library.internal.Card;

public class CardFuelListing extends Card {
    private TextView mDate;
    private TextView mCar;
    private String mDateText;
    private String mCarText;

    public CardFuelListing(Context context) {
        this(context, R.layout.card_fuellisting_single);
    }

    public CardFuelListing(Context context, int innerLayout) {
        super(context, innerLayout);
    }

    public void setDateText(String mDateText) {
        this.mDateText = mDateText;
    }

    public void setCarText(String mCarText) {
        this.mCarText = mCarText;
    }

    public TextView getDate() {
        return mDate;
    }

    public TextView getCar() {
        return mCar;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        mCar = (TextView) parent.findViewById(R.id.fuellisting_main_inner_title);
        mDate = (TextView) parent.findViewById(R.id.fuellisting_main_inner_secondaryTitle);

        mCar.setText(mCarText);
        mDate.setText(mDateText);
    }
}

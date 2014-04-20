package eu.roklapps.fuellog.app.ui.card;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import eu.roklapps.fuellog.app.R;
import it.gmariotti.cardslib.library.internal.Card;

public class CarCard extends Card {
    private TextView mCarVendorTitle;
    private TextView mCarName;

    private String mVendor;
    private String mName;

    public CarCard(Context context) {
        this(context, R.layout.card_carpark_single);
    }

    public CarCard(Context context, int innerLayout) {
        super(context, innerLayout);
    }

    public TextView getCarVendorTitle() {
        return mCarVendorTitle;
    }

    public void setCarVendorTitle(TextView mCarVendorTitle) {
        this.mCarVendorTitle = mCarVendorTitle;
    }

    public TextView getCarName() {
        return mCarName;
    }

    public void setCarName(TextView mCarName) {
        this.mCarName = mCarName;
    }

    public String getVendor() {
        return mVendor;
    }

    public void setVendor(String mVendor) {
        this.mVendor = mVendor;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    private void initCard() {
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {

            }
        });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        mCarVendorTitle = (TextView) parent.findViewById(R.id.carpark_main_inner_title);
        mCarName = (TextView) parent.findViewById(R.id.carpark_main_inner_secondaryTitle);

        mCarName.setText(mName);
        mCarVendorTitle.setText(mVendor);
    }
}
